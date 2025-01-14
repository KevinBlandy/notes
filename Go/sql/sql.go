---------------------
sql
---------------------
	# 参考
		https://github.com/golang/go/wiki/SQLInterface
		https://www.jianshu.com/p/5e7477649423
	
	# 驱动
		* 所有数据库对应的驱动列表
			https://github.com/golang/go/wiki/SQLDrivers
		
		* 驱动的加载
			_ "github.com/go-sql-driver/mysql"
	
	# URL 链接参数

		https://github.com/go-sql-driver/mysql
			parseTime
				* parseTime=true 会将 DATE 和 DATETIME 值的输出类型更改为 time.Time，而不是 []byte / string 日期或日期时间（如 0000-00-00 00:00:00）会转换为 time.Time 的零值。
				* 如果是 false 则返回 []uint8 ，就是字符串 2025-01-14 13:43:54
			
			loc
				* 指定时区，解析/写入 Timestamp 时使用的时区

					loc=UTC		使用 UTC 时区
					loc=Local	使用本地时区

		

	
	
	# 大致API流程
		DB 执行SQL
		DB 获取*Stmt	->	*Stmt填充参数	-> *Stmt执行SQL
		DB 获取*Tx		->	*Tx执行SQ;
		DB 获取*Tx		->	*Tx获取*Stmt	->	*Stmt填充参数 -> *Stmt执行SQL

		DB 获取*Conn	->	*Conn可以当做一个DB对象使用，执行上面的流程


	# 占位符
		* 不同语言的占位符不同
		MySQL                    
			WHERE col = ?		VALUES(?, ?, ?)   
		PostgreSQL       
			WHERE col = $1		VALUES($1, $2, $3)
		Oracle
			WHERE col = :col	VALUES(:val1, :val2, :val3)
		
	
	# DB的对象的创建
		const (
			host string = "localhost"	// 主机
			port int = 3306					// 端口
			db string = "demo"				// 数据库
			username string = "root"		// 用户名
			password string = "root"		// 密码
		)
		func main() {
			db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
			if err != nil {
				log.Panic(err)
			}
			fmt.Println(db)
		}
		
		* 创建成功并不代表可用，底层连接是延迟创建的，只有在第一次使用的时候才会创建
		* 可以使用 Ping() 来检查，连接是否正常，如果它返回 err 则表示有问题
	
	# 通过Driver来判断异常类型
			if driverErr, ok := err.(*mysql.MySQLError); ok { // 确定是MYSQL的异常
				if driverErr.Number == 1045 {
					// 通过异常状态码来判断
					var _ string = driverErr.Message // 异常的消息提示
				}
			}
	
	# 事务处理
		func Service (db *sql.DB) () {
			// 开始事务，指定隔离级别和只读属性
			tx, err := db.BeginTx(context.Background(), &sql.TxOptions{
				Isolation: sql.LevelRepeatableRead,
				ReadOnly: true,
			})
			if err != nil {
				log.Fatal(err)
			}

			// TODO 执行业务
			stmt, err := tx.Prepare("SELECT * FROM `user` WHERE `id`= ?;")

			// 最后关闭stmt，之前Golang1.4关闭*sql.Tx将与之关联的连接返还到池中
			// 如果它先于 Rollback 之前执行的话，在并发环境下可能有问题（stmt关闭后，换给了连接池，其他协程获取了连接，然后此时执行了回滚）
			defer stmt.Close()

			// 始终回滚，如果已经提交了，回滚不受任何影响
			defer tx.Rollback()

			if err != nil {
				log.Fatal(err)
			}
			rows, err := stmt.Query(1)
			if err != nil {
				log.Fatal(err)
			}

			// 释放资源
			defer rows.Close()

			for rows.Next() {
				// 遍历数据
				rows.Scan()
			}

			// 最后提交事务
			if err := tx.Commit(); err != nil {
				log.Fatalf("rollback error: %s\n", err.Error())
			}
		}	
	
	# Demo
		package main
		import (
			"database/sql"
			"fmt"
			_ "github.com/go-sql-driver/mysql"
			"log"
		)
		const (
			host string = "localhost"	// 主机
			port int = 3306					// 端口
			db string = "demo"				// 数据库
			username string = "root"		// 用户名
			password string = "root"		// 密码
		)

		func main() {
			db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
			if err != nil {
				log.Panic(err)
			}

			// 执行SQL获取到结果集
			result, err := db.Query("SELECT * FROM `user` LIMIT ?, ?;", 0, 9)
			if err != nil {
				log.Fatal(err)		// 执行异常
			}
			defer func() {	// 关闭，释放资源
				if err := result.Close(); err != nil {
					log.Println(err)
				}
			}()

			var id int
			var name string
			var date string

			// 遍历每一行
			for result.Next() {
				err := result.Scan(&id, &name, &date)
				if err != nil {
					log.Fatal(err) // 封装异常
				}
				log.Println(id, name, date)
			}

			if err := result.Err(); err != nil {
				log.Fatal(err)	// 迭代过程中的错误，可能迭代过程被中断了
			}
		}


---------------------
sql结果集的封装
---------------------
	# Scan 方法，参数应该是指针
		* 参数数量必须与Rows中的列数相同
		* 数据类型
			*string
			*[]byte
			*int, *int8, *int16, *int32, *int64
			*uint, *uint8, *uint16, *uint32, *uint64
			*bool
			*float32, *float64
			*interface{}
			*RawBytes
			*Rows (cursor value)
			任何实现了 sql.Scanner 的类型
	
	# Null值的处理
		* 使用预定义的几个类型来表示Null值
			NullBool
			NullFloat64
			NullInt32
			NullInt64
			NullString
			NullTime

		* Demo
			for rows.Next() {
				var s sql.NullString
				err := rows.Scan(&s)
				// check err
				if s.Valid {
					// 非null
				} else {
					// null值
				}
			}