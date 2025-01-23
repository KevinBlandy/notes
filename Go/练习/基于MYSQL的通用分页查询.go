package page

import (
	"context"
	"database/sql"
	"errors"
	"log/slog"
	"reflect"
	"strconv"
	"strings"
)

// Paged 分页结果
type Paged[T any] struct {
	Total int `json:"total,omitempty"`
	List  []T `json:"list,omitempty"`
}

// Sort 排序
type Sort struct {
	Column string `json:"column"`
	Order  Order  `json:"order"`
}

func NewSort(column string, order Order) *Sort {
	return &Sort{Column: column, Order: order}
}

// ValidColumn 是否是合法的列名称
func (s *Sort) ValidColumn() bool {
	// TODO 是否是合法的排序字段，防止 SQL 注入
	return true
}

// Order 排序策略
type Order string

const (
	OrderAsc  Order = "ASC"
	OrderDesc Order = "DESC"
)

// Request 分页查询
type Request struct {
	Page  int     `json:"page,omitempty"`
	Rows  int     `json:"rows,omitempty"`
	Count bool    `json:"count,omitempty"`
	Sorts []*Sort `json:"sorts,omitempty"`
}

// Offset 返回偏移量
func (req *Request) Offset() int {
	return (req.Page - 1) * req.Rows
}

func NewRequest(page, rows int, sorts ...*Sort) *Request {
	if page < 1 {
		panic("page must be greater than 1")
	}
	if rows < 1 {
		panic("rows must be greater than 1")
	}
	return &Request{
		Page:  page,
		Rows:  rows,
		Count: true, // 默认要查询总记录数量
		Sorts: sorts,
	}
}

// Query 执行分页查询
func Query[T any](ctx context.Context, request *Request, tx *sql.Tx, query string, args ...any) (*Paged[T], error) {

	var ret Paged[T]

	// 检索总数量
	if request.Count {
		if err := tx.QueryRowContext(ctx, "SELECT COUNT(1) FROM ("+query+") __tmp", args...).Scan(&ret.Total); err != nil {
			return nil, err
		}
		if ret.Total == 0 {
			return &ret, nil
		}
	}

	// 子查询
	buf := &strings.Builder{}
	_, _ = buf.WriteString("SELECT __tmp.* FROM (" + query + ") __tmp")

	// ORDER BY
	if len(request.Sorts) > 0 {
		buf.WriteString(" ORDER BY ")
		for i, sort := range request.Sorts {
			if i > 0 {
				_, _ = buf.WriteString(" ,")
			}
			_, _ = buf.WriteString("__tmp." + sort.Column + " " + string(sort.Order))
		}
	}

	// LIMIT
	_, _ = buf.WriteString(" LIMIT " + strconv.Itoa(request.Offset()) + ", " + strconv.Itoa(request.Rows))

	slog.Info("Page Query", slog.String("query", buf.String()))

	rows, err := tx.QueryContext(ctx, buf.String(), args...)
	if err != nil {
		return nil, err
	}
	defer func() {
		_ = rows.Close()
	}()

	ret.List = make([]T, 0)

	if err := scanStructSlice(rows, &ret.List); err != nil {
		return nil, err
	}

	return &ret, rows.Err()
}

// scanStructSlice 使用反射将 sql.Rows 的结果写入到 destSlice 中
func scanStructSlice(rows *sql.Rows, destSlice any) error {
	// 获取目标切片的反射值
	destValue := reflect.ValueOf(destSlice)
	if destValue.Kind() != reflect.Ptr || destValue.Elem().Kind() != reflect.Slice {
		return errors.New("destSlice must be a pointer to a slice")
	}

	// 获取切片元素的类型
	sliceValue := destValue.Elem()
	elemType := sliceValue.Type().Elem()

	// 确保元素类型是结构体或结构体指针
	var structType reflect.Type
	if elemType.Kind() == reflect.Ptr {
		structType = elemType.Elem()
	} else {
		structType = elemType
	}
	if structType.Kind() != reflect.Struct {
		return errors.New("slice elements must be structs or struct pointers")
	}

	// 获取列名
	columns, err := rows.Columns()
	if err != nil {
		return err
	}

	// 创建一个存放列值的切片
	values := make([]interface{}, len(columns))
	valuePtrs := make([]interface{}, len(columns))
	for i := range values {
		valuePtrs[i] = &values[i]
	}

	// 遍历 rows
	for rows.Next() {
		// 扫描行数据到 values 中
		if err := rows.Scan(valuePtrs...); err != nil {
			return err
		}

		// 创建一个新的 struct 实例
		var elemValue reflect.Value
		if elemType.Kind() == reflect.Ptr {
			elemValue = reflect.New(elemType.Elem())
		} else {
			elemValue = reflect.New(elemType).Elem()
		}

		// 将每列的数据写入到 struct 实例的字段中
		for i, col := range columns {
			for j := 0; j < structType.NumField(); j++ {
				field := structType.Field(j)
				if field.Tag.Get("db") == col {
					var fieldValue reflect.Value
					if elemType.Kind() == reflect.Ptr {
						fieldValue = elemValue.Elem().Field(j)
					} else {
						fieldValue = elemValue.Field(j)
					}
					if fieldValue.CanSet() {
						val := reflect.ValueOf(values[i])
						fieldValue.Set(val.Convert(fieldValue.Type()))
					}
				}
			}
		}

		// 将 struct 实例附加到目标切片中
		if elemType.Kind() == reflect.Ptr {
			sliceValue.Set(reflect.Append(sliceValue, elemValue))
		} else {
			sliceValue.Set(reflect.Append(sliceValue, elemValue.Addr()))
		}
	}
	return nil
}


// -------------用法

package main

import (
	"context"
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"log/slog"
	"os"
	"simple-server/page"
	"time"
)

func init() {
	slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		AddSource: true,
		Level:     slog.LevelDebug,
		ReplaceAttr: func(groups []string, a slog.Attr) slog.Attr {
			if a.Value.Kind() == slog.KindTime {
				return slog.String(a.Key, a.Value.Time().Format(time.DateTime))
			}
			return a
		},
	})))
}

type Area struct {
	Id       int64  `json:"id,omitempty" db:"id"`
	ParentId int64  `json:"parent_id,omitempty" db:"parent_id"`
	Title    string `json:"title,omitempty" db:"title"`
	Code     string `json:"code,omitempty" db:"code"`
}

func main() {
	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
	if err != nil {
		panic(err)
	}

	tx, err := db.Begin()
	if err != nil {
		panic(err)
	}


	// 分页查询
	result, err := page.Query[*Area](context.Background(),
		page.NewRequest(
			1,
			5,
			page.NewSort("depth", page.OrderAsc),
			page.NewSort("id", page.OrderDesc),
		), tx, "SELECT * FROM t_area")
	if err != nil {
		panic(err)
	}

	content, err := json.MarshalIndent(result, "", "	")
	if err != nil {
		panic(err)
	}

	fmt.Println(string(content))
}
