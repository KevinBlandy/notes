package page

import (
	"context"
	"database/sql"
	"fmt"
	"io"
	"math"
	"strconv"
	"strings"
)

// Paged 分页结果
type Paged[T any] struct {
	Total int
	List  []T
}

func NewPaged[T any](total int, list []T) *Paged[T] {
	return &Paged[T]{
		Total: total,
		List:  list,
	}
}

func EmptyPaged[T any]() *Paged[T] {
	return &Paged[T]{
		Total: 0,
		List:  make([]T, 0), // 空集合
	}
}

// Pager 分页 & 排序参数
type Pager struct {
	Page  int
	Limit int
	Count bool // 是否进行 COUNT 检索
	Sort  []string
	Order []string
}

func (p *Pager) Offset() int {
	return (p.Page - 1) * p.Limit
}

// Count 查询总数量
func Count(ctx context.Context, tx *sql.Tx, query string, args ...any) (int, error) {

	countSql := "SELECT COUNT(*) FROM (" + query + ") AS `_tmp`"

	fmt.Println(countSql)

	stmt, err := tx.PrepareContext(ctx, countSql)
	if err != nil {
		return 0, err
	}

	defer safeClose(stmt)

	var count int64

	if err := stmt.QueryRowContext(ctx, args...).Scan(&count); err != nil {
		return 0, err
	}

	return int(count), nil
}

// List 分页查询
func List[T any](ctx context.Context, tx *sql.Tx, pager *Pager, query string, mapper func(*sql.Rows) (T, error), args ...any) (*Paged[T], error) {

	ret := EmptyPaged[T]()

	if pager.Count {
		// 查询总页数
		total, err := Count(ctx, tx, query, args...)

		ret.Total = total

		if err != nil {
			return ret, err
		}

		if total == 0 || (pager.Limit > 0 && pager.Page > int(math.Ceil(float64(total)/float64(pager.Limit)))) {
			// 总数量为0或者是页码不合法
			return ret, nil
		}
	}

	selectSql := "SELECT `_tmp`.* FROM (" + query + ") AS `_tmp`"

	// 排序
	if len(pager.Sort) > 0 {

		selectSql += " ORDER BY "

		for i, field := range pager.Sort {
			// TODO 非法的排序字段
			if i != 0 {
				selectSql += ", "
			}

			var direction string

			if len(pager.Order) > i && strings.EqualFold(pager.Order[i], "DESC") {
				direction = "DESC"
			} else {
				direction = "ASC"
			}

			selectSql += "`" + field + "` " + direction
		}
	}

	if pager.Limit > 0 {
		selectSql += " LIMIT " + strconv.Itoa(pager.Limit)
	}
	if pager.Page > 0 {
		selectSql += " OFFSET " + strconv.Itoa(pager.Offset())
	}

	fmt.Println(selectSql)

	stmt, err := tx.PrepareContext(ctx, selectSql)
	if err != nil {
		return ret, err
	}

	defer safeClose(stmt)

	rows, err := stmt.QueryContext(ctx, args...)
	if err != nil {
		return ret, err
	}

	defer safeClose(rows)

	for rows.Next() {
		record, err := mapper(rows)
		if err != nil {
			return ret, err
		}
		ret.List = append(ret.List, record)
	}
	return ret, nil
}

func safeClose(closer io.Closer) {
	_ = closer.Close()
}


-----------------------------------
使用
-----------------------------------


package main

import (
	"app/page"
	"context"
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"io"
	"log"
	"time"
)

func main() {
	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
	if err != nil {
		log.Panic(err)
	}

	defer func(closer io.Closer) {
		_ = closer.Close()
	}(db)

	if err = db.Ping(); err != nil {
		panic(err)
	}

	type Member struct {
		Id            int64
		CoinBalance   string
		CreditBalance string
		Version       int
		UpdateTime    *time.Time
	}

	tx, err := db.BeginTx(context.Background(), &sql.TxOptions{})
	if err != nil {
		panic(err)
	}

	r, err := page.List[*Member](context.Background(), tx, &page.Pager{
		Page:  0,
		Limit: 0,
		Count: true,
		Sort:  []string{"member_id"},
	}, "SELECT * FROM `v1_t_ums_member_financial`", func(rows *sql.Rows) (*Member, error) {
		r := new(Member)
		return r, rows.Scan(&r.Id, &r.CoinBalance, &r.CreditBalance, &r.Version, &r.UpdateTime)
	})

	if err != nil {
		panic(err)
	}

	jsonContent, _ := json.MarshalIndent(r, "", "	")
	fmt.Println(string(jsonContent))
}
