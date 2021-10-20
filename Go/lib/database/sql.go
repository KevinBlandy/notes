-----------------
sql
-----------------
	# SQL的驱动需要导入，跟Java一样，加载到内存就行
		import (
			_ "github.com/go-sql-driver/mysql"
		)
	


-----------------
var
-----------------
	# 异常信息
		var ErrConnDone = errors.New("sql: connection is already closed")
			* 连接一个关闭
		
		var ErrNoRows = errors.New("sql: no rows in result set")
			* 空结果异常
				var name string
				err = db.QueryRow("select name from users where id = ?", 1).Scan(&name)
				if err != nil {
					if err == sql.ErrNoRows {
						// 没检索到任何数据
					} else {
						log.Fatal(err)
					}
				}
				fmt.Println(name)
		
		var ErrTxDone = errors.New("sql: transaction has already been committed or rolled back")
			* 异常已经回滚或者提交


-----------------
type
-----------------
	# type ColumnType struct {
		}

		* 列类型
		func (ci *ColumnType) DatabaseTypeName() string
			* 返回数据库的列类型名称
		
		func (ci *ColumnType) DecimalSize() (precision, scale int64, ok bool)
			* 返回小数类型的比例和精度。如果不适用或不支持ok则返回false。

		func (ci *ColumnType) Length() (length int64, ok bool)
			* Length 返回可变长度列类型（如文本和二进制字段类型）的列类型长度。
			* 如果类型长度是无限制的，那么该值将是math.MaxInt64（任何数据库限制仍然适用）。
			* 如果列类型不是可变长度的，例如int，或者如果驱动程序不支持，ok为false。

		func (ci *ColumnType) Name() string
			* 列名称

		func (ci *ColumnType) Nullable() (nullable, ok bool)
			* Nullable报告列是否为空。
			* 如果一个驱动程序不支持这个属性，那么ok将为false。

		func (ci *ColumnType) ScanType() reflect.Type
			* 该类型，对应的Go类型
			* 如果一个驱动程序不支持这个属性，ScanType将返回一个空接口的类型。
	
	# type Conn struct {
		}
	
		* 数据库连接

		func (c *Conn) BeginTx(ctx context.Context, opts *TxOptions) (*Tx, error)
		func (c *Conn) Close() error
		func (c *Conn) ExecContext(ctx context.Context, query string, args ...interface{}) (Result, error)
		func (c *Conn) PingContext(ctx context.Context) error
		func (c *Conn) PrepareContext(ctx context.Context, query string) (*Stmt, error)
		func (c *Conn) QueryContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)
		func (c *Conn) QueryRowContext(ctx context.Context, query string, args ...interface{}) *Row
		func (c *Conn) Raw(f func(driverConn interface{}) error) (err error)
	
	# type DB struct {
		}
		
		* 数据库连接池

		func Open(driverName, dataSourceName string) (*DB, error)
			* 返回DB，自己检索驱动，驱动需要先被加载进内存
				db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))

		func OpenDB(c driver.Connector) *DB
			* 根据驱动返回DB

		func (db *DB) Begin() (*Tx, error)
		func (db *DB) BeginTx(ctx context.Context, opts *TxOptions) (*Tx, error)
			* 开始事务
			
		func (db *DB) Close() error
			* 关闭数据源

		func (db *DB) Conn(ctx context.Context) (*Conn, error)
			* 获取一个数据库的连接

		func (db *DB) Driver() driver.Driver

		func (db *DB) Exec(query string, args ...interface{}) (Result, error)
		func (db *DB) ExecContext(ctx context.Context, query string, args ...interface{}) (Result, error)
			* 执行操作，一般是修改删除插入操作，SQL获取到执行结果
			* 如果执行了检索操作，不会异常，result的2个值都是0

		func (db *DB) Ping() error
			* 发送ping，检测连接是否OK
			* Open()方法，并不会创建连接，连接是延迟加载的，可以通过这个方法来判断连接是否正常

		func (db *DB) PingContext(ctx context.Context) error
			* 获取数据库链接

		func (db *DB) Prepare(query string) (*Stmt, error)
		func (db *DB) PrepareContext(ctx context.Context, query string) (*Stmt, error)
			* 解析SQL为预编译语句

		func (db *DB) Query(query string, args ...interface{}) (*Rows, error)
		func (db *DB) QueryContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)‘’
			* 执行查询，获取多行多列结果集

		func (db *DB) QueryRow(query string, args ...interface{}) *Row
		func (db *DB) QueryRowContext(ctx context.Context, query string, args ...interface{}) *Row
			* 执行查询，获取单行多列结果集
			* 如果结果集有多行，则会只取第一行，不会异常
			* 它的返回只有一个结果，没有异常参数，如果查询出现了异常，那么会推迟到Scan()

		func (db *DB) SetConnMaxIdleTime(d time.Duration)
			* 连接最大空闲时间空闲超过这个时间的连接，在下次被重复使用前会被延迟关闭。<= 0 表示不超时
		func (db *DB) SetConnMaxLifetime(d time.Duration)
			* 连接最长存活时间，重复使用超过了这个时间的连接，在下次被重复使用前会被延迟关闭。 <= 0 表示不超时
		func (db *DB) SetMaxIdleConns(n int)
			* 最大空闲连接数量,默认的最大空闲连接数是2。
		func (db *DB) SetMaxOpenConns(n int)
			* 数据库打开的最大连接数,默认是0，表示无限制。
		func (db *DB) Stats() DBStats
	
	# type DBStats struct {
			MaxOpenConnections int // Maximum number of open connections to the database.

			// Pool Status
			OpenConnections int // The number of established connections both in use and idle.
			InUse           int // The number of connections currently in use.
			Idle            int // The number of idle connections.

			// Counters
			WaitCount         int64         // The total number of connections waited for.
			WaitDuration      time.Duration // The total time blocked waiting for a new connection.
			MaxIdleClosed     int64         // The total number of connections closed due to SetMaxIdleConns.
			MaxIdleTimeClosed int64         // The total number of connections closed due to SetConnMaxIdleTime.
			MaxLifetimeClosed int64         // The total number of connections closed due to SetConnMaxLifetime.
		}

		* 数据库状态
	
	# type IsolationLevel int
		* 事务隔离级别

		const (
			LevelDefault IsolationLevel = iota
			LevelReadUncommitted
			LevelReadCommitted
			LevelWriteCommitted
			LevelRepeatableRead
			LevelSnapshot
			LevelSerializable
			LevelLinearizable
		)
		func (i IsolationLevel) String() string

	
	# type NamedArg struct {
			Name string
			Value interface{} // contains filtered or unexported fields
		}
		* SQL中的具名参数
		func Named(name string, value interface{}) NamedArg
	
	# type NullBool struct {
			Bool  bool
			Valid bool // Valid is true if Bool is not NULL
		}
		func (n *NullBool) Scan(value interface{}) error
		func (n NullBool) Value() (driver.Value, error)
	
	# type NullFloat64 struct {
			Float64 float64
			Valid   bool // Valid is true if Float64 is not NULL
		}
		func (n *NullFloat64) Scan(value interface{}) error
		func (n NullFloat64) Value() (driver.Value, error)
	
	# type NullInt32 struct {
			Int32 int32
			Valid bool // Valid is true if Int32 is not NULL
		}
		func (n *NullInt32) Scan(value interface{}) error
		func (n NullInt32) Value() (driver.Value, error)
	
	# type NullInt64 struct {
			Int64 int64
			Valid bool // Valid is true if Int64 is not NULL
		}
		func (n *NullInt64) Scan(value interface{}) error
		func (n NullInt64) Value() (driver.Value, error)
	
	# type NullString struct {
			String string
			Valid  bool // Valid is true if String is not NULL
		}
		func (ns *NullString) Scan(value interface{}) error
		func (ns NullString) Value() (driver.Value, error)
	
	# type NullTime struct {
			Valid bool // Valid is true if Time is not NULL
		}
		func (n *NullTime) Scan(value interface{}) error
		func (n NullTime) Value() (driver.Value, error)

	# type NullInt16 struct {
			Int16 int16
			Valid bool // Valid is true if Int16 is not NULL
		}
		func (n *NullInt16) Scan(value interface{}) error 
		func (n NullInt16) Value() (driver.Value, error)
	
	# type NullByte struct {
			Byte  byte
			Valid bool // Valid is true if Byte is not NULL
		}
		func (n *NullByte) Scan(value interface{}) error
		func (n NullByte) Value() (driver.Value, error)


	# type Out struct {
			Dest interface{}
			In bool // contains filtered or unexported fields
		}
	
	# type RawBytes []byte
		* 最原始的字节数据
		* 如果不知道数据列的字段类型，那么可以使用RawBytes，返回原始的字节数据

	# type Result interface {
			LastInsertId() (int64, error)
				* 自增ID，和异常信息

			RowsAffected() (int64, error)
				* 受影响的行数，和异常信息
		}
		* SQL的修改执行结果
	
	# type Row struct {
		}

		* 单行多列数据

		func (r *Row) Err() error
			* 异常信息
		func (r *Row) Scan(dest ...interface{}) error
			* 如果结果集不存在，没有数据，那么会返回异常: ErrNoRows
	
	# type Rows struct {
		}

		* 多行多列数据

		func (rs *Rows) Close() error
			* 关闭Rows，这个操作要记的执行
			* Rows会保留数据库连接，直到sql.Rows关闭
				_, err := db.Query("DELETE FROM users") // 这种操作，会导致连接不会被释放
			* 这个方法可以被多次调用，无所谓

		func (rs *Rows) ColumnTypes() ([]*ColumnType, error)
		func (rs *Rows) Columns() ([]string, error)
		func (rs *Rows) Err() error
			* 返回异常信息，可能在跌到过程中产生了异常，导致迭代提前终止
			* 可以在迭代完成后，尝试获取到这个异常信息进行处理
			* 如果提前终止了迭代，那么可能没有关闭资源，那么需要自己Close()

		func (rs *Rows) Next() bool
			* 在没有数据后，执行rows.Next() 会遇到 EOF 异常，此时会自动调用 rows.Close() 关闭资源
			* 通俗理解就是，通过next正常遍历完毕数据后，会自动释放链接资源

		func (rs *Rows) NextResultSet() bool
		func (rs *Rows) Scan(dest ...interface{}) error

		* Rows会保留数据库连接，直到sql.Rows关闭
	
	# type Scanner interface {
			Scan(src interface{}) error
		}
		
		* 把数据库值写入到Go的接口
		* src的值可能是
			int64
			float64
			bool
			[]byte
			string
			time.Time
			nil - for NULL values

	# type Stmt struct {
		}
	
		* 预编译SSQL

		func (s *Stmt) Close() error
		func (s *Stmt) Exec(args ...interface{}) (Result, error)
		func (s *Stmt) ExecContext(ctx context.Context, args ...interface{}) (Result, error)
			* 执行修改SQL，填充参数，获取到结果

		func (s *Stmt) Query(args ...interface{}) (*Rows, error)
		func (s *Stmt) QueryContext(ctx context.Context, args ...interface{}) (*Rows, error)
			* 填充参数，执行检索，获取多行多列结果

		func (s *Stmt) QueryRow(args ...interface{}) *Row
		func (s *Stmt) QueryRowContext(ctx context.Context, args ...interface{}) *Row
			* 填充参数，执行检索，获取单行多列结果
	
	# type Tx struct {
		}
		* 事务
		func (tx *Tx) Commit() error
			* 提交事务

		func (tx *Tx) Exec(query string, args ...interface{}) (Result, error)
		func (tx *Tx) ExecContext(ctx context.Context, query string, args ...interface{}) (Result, error)
			* 执行SQL，获取结果

		func (tx *Tx) Prepare(query string) (*Stmt, error)
		func (tx *Tx) PrepareContext(ctx context.Context, query string) (*Stmt, error)
			* 执行预编译，返回*Stmt

		func (tx *Tx) Query(query string, args ...interface{}) (*Rows, error)
		func (tx *Tx) QueryContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)
			* 执行检索返回多行多列结果集

		func (tx *Tx) QueryRow(query string, args ...interface{}) *Row
		func (tx *Tx) QueryRowContext(ctx context.Context, query string, args ...interface{}) *Row
			* 执行检索返回单行多列结果集

		func (tx *Tx) Rollback() error
			* 回滚操作
		
		func (tx *Tx) Stmt(stmt *Stmt) *Stmt
		func (tx *Tx) StmtContext(ctx context.Context, stmt *Stmt) *Stmt


-----------------
func
-----------------
	func Drivers() []string
	func Register(name string, driver driver.Driver)


-----------------
demo
-----------------
	# 自定义Go数据类型和SQL类型的转换
		* 主要是实现2个接口
			type Scanner interface {
				Scan(src interface{}) error
			}
				
				* 把数据库值写入到Go的接口
				* src的值可能是
					int64
					float64
					bool
					[]byte
					string
					time.Time
					nil - for NULL values


			type Valuer interface {
				// Value returns a driver Value.
				// Value must not panic.
				Value() (Value, error)
			}
			type Value interface{}
				* value表示数据库的值，类型可以是:
					int64
					float64
					bool
					[]byte
					string
					time.Time
		
		* 实现
			import (
				"database/sql"
				"database/sql/driver"
				"encoding/json"
				"fmt"
				_ "github.com/go-sql-driver/mysql"
				"log"
			)

			/*
			CREATE TABLE `demo` (
			  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
			  `user` json DEFAULT NULL COMMENT 'JSON',
			  PRIMARY KEY (`id`)
			) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
			*/

			type User struct {
				Id int
				Name string
			}
			func (u *User) Scan (src interface{}) error {
				switch src.(type) {
					case string: {
						return json.Unmarshal([]byte(src.(string)), u)
					}
					case []byte: {
						return json.Unmarshal(src.([]byte), u)
					}
				}
				return nil
			}

			func (u User) Value() (driver.Value, error){
				data, err := json.Marshal(u)
				if err != nil {
					return nil, err
				}
				return string(data), nil
			}

			func main(){
				db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
				if err != nil {
					log.Fatalln(err.Error())
				}
				if err := db.Ping(); err != nil {
					log.Fatalln(err.Error())
				}

				result, err := db.Exec("INSERT INTO `demo`(`id`, `user`) VALUES(?, ?)", nil,&User{
					Id:   888,
					Name: "Cat",
				})
				if err != nil {
					log.Fatalln(err.Error())
				}

				rowsAffected, err := result.RowsAffected()
				if err != nil {
					log.Fatalln(err.Error())
				}
				log.Printf("RowsAffected=%d\n", rowsAffected)

				lastInsertId, err := result.LastInsertId()
				if err != nil {
					log.Fatalln(err.Error())
				}
				log.Printf("LastInsertId=%d\n", lastInsertId)

				row := db.QueryRow("SELECT `id`, `user` FROM `demo` WHERE `id` = ?", lastInsertId)

				var id int
				var user User

				if err := row.Scan(&id, &user); err != nil {
					log.Fatalln(err.Error())
				}

				log.Printf("id=%d, user=%v\n", id, user)
			}
