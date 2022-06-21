-------------------
var
-------------------
	const (
		UNKNOWN = iota
		QUESTION	// ?
		DOLLAR		// $
		NAMED		// :name
		AT			// @
	)

		* 占位符类型

	var NameMapper = strings.ToLower
		* 列映射Mappper
		* 用于结果集映射，Named 检索映射
		* `db:""` 的优先级会比mapper高

-------------------
type
-------------------
	# type ColScanner interface {
			Columns() ([]string, error)
			Scan(dest ...interface{}) error
			Err() error
		}
	
	# type Conn struct {
			*sql.Conn
			Mapper *reflectx.Mapper
			// contains filtered or unexported fields
		}

		func (c *Conn) BeginTxx(ctx context.Context, opts *sql.TxOptions) (*Tx, error)
		func (c *Conn) GetContext(ctx context.Context, dest interface{}, query string, args ...interface{}) error
		func (c *Conn) PreparexContext(ctx context.Context, query string) (*Stmt, error)
		func (c *Conn) QueryRowxContext(ctx context.Context, query string, args ...interface{}) *Row
		func (c *Conn) QueryxContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)
		func (c *Conn) Rebind(query string) string
		func (c *Conn) SelectContext(ctx context.Context, dest interface{}, query string, args ...interface{}) error
	
	# type DB struct {
			*sql.DB
			Mapper *reflectx.Mapper
			// contains filtered or unexported fields
		}

		* 本身就继承了 sql.DB

		func Connect(driverName, dataSourceName string) (*DB, error)
		func ConnectContext(ctx context.Context, driverName, dataSourceName string) (*DB, error)
		func MustConnect(driverName, dataSourceName string) *DB
			* 创建新的数据库，并且立即创建连接
			
		func MustOpen(driverName, dataSourceName string) *DB
		func Open(driverName, dataSourceName string) (*DB, error)
			* 创建新得数据库，不立即创建连接

		func NewDb(db *sql.DB, driverName string) *DB
			* 使用sql.DB创建数据库

		func (db *DB) BeginTxx(ctx context.Context, opts *sql.TxOptions) (*Tx, error)
		func (db *DB) Beginx() (*Tx, error)
			* 开始事务，可以通过 opts 指定事务的隔离级别等等
			
		func (db *DB) BindNamed(query string, arg interface{}) (string, []interface{}, error)
		func (db *DB) Connx(ctx context.Context) (*Conn, error)
		func (db *DB) DriverName() string

		func (db *DB) MapperFunc(mf func(string) string)
			* 重新设置struct与列名映射关系的函数
			* 如果关系映射不上，可以通过 `db:""` 来设置。

		func (db *DB) MustBegin() *Tx
		func (db *DB) MustBeginTx(ctx context.Context, opts *sql.TxOptions) *Tx

		func (db *DB) MustExec(query string, args ...interface{}) sql.Result
		func (db *DB) MustExecContext(ctx context.Context, query string, args ...interface{}) sql.Result
			* 执行SQL更新/INSERT语句，如果出现异常则panic
			* 如果需要自己控制err，则可以使用父类的Exec方法

		func (db *DB) NamedExec(query string, arg interface{}) (sql.Result, error)
		func (db *DB) NamedExecContext(ctx context.Context, query string, arg interface{}) (sql.Result, error)
		func (db *DB) NamedQuery(query string, arg interface{}) (*Rows, error)
		func (db *DB) NamedQueryContext(ctx context.Context, query string, arg interface{}) (*Rows, error)
			* 命名查询/修改。返回多行多列

		func (db *DB) PrepareNamed(query string) (*NamedStmt, error)
		func (db *DB) PrepareNamedContext(ctx context.Context, query string) (*NamedStmt, error)
			* 命名预编译SQL

		func (db *DB) Preparex(query string) (*Stmt, error)
		func (db *DB) PreparexContext(ctx context.Context, query string) (*Stmt, error)
			* 预编译语句

		func (db *DB) QueryRowx(query string, args ...interface{}) *Row
		func (db *DB) QueryRowxContext(ctx context.Context, query string, args ...interface{}) *Row
			* 查询单行结果，异常发生到Row绑定的时候

		func (db *DB) Queryx(query string, args ...interface{}) (*Rows, error)
		func (db *DB) QueryxContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)
			* 查询多行结果，异常发生在查询执行的时候
			* 一定要记得关闭*Rows

		func (db *DB) Rebind(query string) string
			* Rebind将一个查询从?占位符查询转换为DB驱动的bindvar类型。

		func (db *DB) Get(dest interface{}, query string, args ...interface{}) error
		func (db *DB) GetContext(ctx context.Context, dest interface{}, query string, args ...interface{}) error
		func (db *DB) Select(dest interface{}, query string, args ...interface{}) error
		func (db *DB) SelectContext(ctx context.Context, dest interface{}, query string, args ...interface{}) error
			* 获取单行/多行数据，desct可以是可扫描的类型
			* select/get只支持当前数据库支持的占位符类型。
				* 如果是MYSQL的话，只支持?,需要想用命名参数，那么需要通过 Named 来对SQL进行转换
			* 它会一次性把数据都加载到内存

			* Select 中的 dest 参数，只能是struct类型的切片，并且检索结果中的所有列都要定义
				type User struct {
					Id   int
					Name string
				}
				var ret []User		// 切片，必须是strutc类型
				err = db.Select(&ret, "SELECT id, name FROM user WHERE id < ?", 100)  // 查询的id, name 列，必须在strutc中定义
				if err != nil {
					log.Fatalf(err.Error())
				}
				log.Println(ret)
			

		func (db *DB) Unsafe() *DB
	
	# type Execer interface {
			Exec(query string, args ...interface{}) (sql.Result, error)
		}
	
	# type ExecerContext interface {
			ExecContext(ctx context.Context, query string, args ...interface{}) (sql.Result, error)
		}
	# type Ext interface {
			Queryer
			Execer
		}
	# type ExtContext interface {
			QueryerContext
			ExecerContext
		}
	# type NamedStmt struct {
			Params      []string
			QueryString string
			Stmt        *Stmt
		}
		func (n *NamedStmt) Close() error
		func (n *NamedStmt) Exec(arg interface{}) (sql.Result, error)
		func (n *NamedStmt) ExecContext(ctx context.Context, arg interface{}) (sql.Result, error)
		func (n *NamedStmt) Get(dest interface{}, arg interface{}) error
		func (n *NamedStmt) GetContext(ctx context.Context, dest interface{}, arg interface{}) error
		func (n *NamedStmt) MustExec(arg interface{}) sql.Result
		func (n *NamedStmt) MustExecContext(ctx context.Context, arg interface{}) sql.Result
		func (n *NamedStmt) Query(arg interface{}) (*sql.Rows, error)
		func (n *NamedStmt) QueryContext(ctx context.Context, arg interface{}) (*sql.Rows, error)
		func (n *NamedStmt) QueryRow(arg interface{}) *Row
		func (n *NamedStmt) QueryRowContext(ctx context.Context, arg interface{}) *Row
		func (n *NamedStmt) QueryRowx(arg interface{}) *Row
		func (n *NamedStmt) QueryRowxContext(ctx context.Context, arg interface{}) *Row
		func (n *NamedStmt) Queryx(arg interface{}) (*Rows, error)
		func (n *NamedStmt) QueryxContext(ctx context.Context, arg interface{}) (*Rows, error)
		func (n *NamedStmt) Select(dest interface{}, arg interface{}) error
		func (n *NamedStmt) SelectContext(ctx context.Context, dest interface{}, arg interface{}) error
		func (n *NamedStmt) Unsafe() *NamedStmt
	
	# type Preparer interface {
			Prepare(query string) (*sql.Stmt, error)
		}
	# type PreparerContext interface {
			PrepareContext(ctx context.Context, query string) (*sql.Stmt, error)
		}
	# type Queryer interface {
			Query(query string, args ...interface{}) (*sql.Rows, error)
			Queryx(query string, args ...interface{}) (*Rows, error)
			QueryRowx(query string, args ...interface{}) *Row
		}
	# type QueryerContext interface {
			QueryContext(ctx context.Context, query string, args ...interface{}) (*sql.Rows, error)
			QueryxContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)
			QueryRowxContext(ctx context.Context, query string, args ...interface{}) *Row
		}
	# type Row struct {
			Mapper *reflectx.Mapper
		}
		func (r *Row) ColumnTypes() ([]*sql.ColumnType, error)
		func (r *Row) Columns() ([]string, error)
		func (r *Row) Err() error
		func (r *Row) MapScan(dest map[string]interface{}) error
		func (r *Row) Scan(dest ...interface{}) error
		func (r *Row) SliceScan() ([]interface{}, error)
		func (r *Row) StructScan(dest interface{}) error

		* 单列结果集
	
	# type Rows struct {
			*sql.Rows
			Mapper *reflectx.Mapper
		}
	
		* 多行多列结果集
		* 千万要记得Close，可以重复调用，多调用几次也无妨

		func NamedQuery(e Ext, query string, arg interface{}) (*Rows, error)
		func NamedQueryContext(ctx context.Context, e ExtContext, query string, arg interface{}) (*Rows, error)
		func (r *Rows) MapScan(dest map[string]interface{}) error
			* 绑定结果集到MAP
		func (r *Rows) SliceScan() ([]interface{}, error)
			* 绑定结果集到切片
		func (r *Rows) StructScan(dest interface{}) error
			* 绑定结果集到对象

	# type Stmt struct {
			*sql.Stmt
			Mapper *reflectx.Mapper
		}
		func Preparex(p Preparer, query string) (*Stmt, error)
		func PreparexContext(ctx context.Context, p PreparerContext, query string) (*Stmt, error)
		func (s *Stmt) Get(dest interface{}, args ...interface{}) error
		func (s *Stmt) GetContext(ctx context.Context, dest interface{}, args ...interface{}) error
		func (s *Stmt) MustExec(args ...interface{}) sql.Result
		func (s *Stmt) MustExecContext(ctx context.Context, args ...interface{}) sql.Result
		func (s *Stmt) QueryRowx(args ...interface{}) *Row
		func (s *Stmt) QueryRowxContext(ctx context.Context, args ...interface{}) *Row
		func (s *Stmt) Queryx(args ...interface{}) (*Rows, error)
		func (s *Stmt) QueryxContext(ctx context.Context, args ...interface{}) (*Rows, error)
		func (s *Stmt) Select(dest interface{}, args ...interface{}) error
		func (s *Stmt) SelectContext(ctx context.Context, dest interface{}, args ...interface{}) error
		func (s *Stmt) Unsafe() *Stmt

	# type Tx struct {
			*sql.Tx
			Mapper *reflectx.Mapper
		}

		
		* 事务

		func (tx *Tx) BindNamed(query string, arg interface{}) (string, []interface{}, error)
		func (tx *Tx) DriverName() string
		func (tx *Tx) Get(dest interface{}, query string, args ...interface{}) error
		func (tx *Tx) GetContext(ctx context.Context, dest interface{}, query string, args ...interface{}) error
		func (tx *Tx) MustExec(query string, args ...interface{}) sql.Result
		func (tx *Tx) MustExecContext(ctx context.Context, query string, args ...interface{}) sql.Result
		func (tx *Tx) NamedExec(query string, arg interface{}) (sql.Result, error)
		func (tx *Tx) NamedExecContext(ctx context.Context, query string, arg interface{}) (sql.Result, error)
		func (tx *Tx) NamedQuery(query string, arg interface{}) (*Rows, error)
		func (tx *Tx) NamedStmt(stmt *NamedStmt) *NamedStmt
		func (tx *Tx) NamedStmtContext(ctx context.Context, stmt *NamedStmt) *NamedStmt
		func (tx *Tx) PrepareNamed(query string) (*NamedStmt, error)
		func (tx *Tx) PrepareNamedContext(ctx context.Context, query string) (*NamedStmt, error)
		func (tx *Tx) Preparex(query string) (*Stmt, error)
		func (tx *Tx) PreparexContext(ctx context.Context, query string) (*Stmt, error)
		func (tx *Tx) QueryRowx(query string, args ...interface{}) *Row
		func (tx *Tx) QueryRowxContext(ctx context.Context, query string, args ...interface{}) *Row
		func (tx *Tx) Queryx(query string, args ...interface{}) (*Rows, error)
		func (tx *Tx) QueryxContext(ctx context.Context, query string, args ...interface{}) (*Rows, error)
		func (tx *Tx) Rebind(query string) string
		func (tx *Tx) Select(dest interface{}, query string, args ...interface{}) error
		func (tx *Tx) SelectContext(ctx context.Context, dest interface{}, query string, args ...interface{}) error
		func (tx *Tx) Stmtx(stmt interface{}) *Stmt
		func (tx *Tx) StmtxContext(ctx context.Context, stmt interface{}) *Stmt
		func (tx *Tx) Unsafe() *Tx



-------------------
func
-------------------
	func BindDriver(driverName string, bindType int)
	func BindNamed(bindType int, query string, arg interface{}) (string, []interface{}, error)
	func BindType(driverName string) int
	func Get(q Queryer, dest interface{}, query string, args ...interface{}) error
	func GetContext(ctx context.Context, q QueryerContext, dest interface{}, query string, ...) error

	func In(query string, args ...interface{}) (string, []interface{}, error)
		* 生成带有in表达式的SQL，会自动展开 args 中的切片来填充 ?
			

	func LoadFile(e Execer, path string) (*sql.Result, error)
	func LoadFileContext(ctx context.Context, e ExecerContext, path string) (*sql.Result, error)
	func MapScan(r ColScanner, dest map[string]interface{}) error
	func MustExec(e Execer, query string, args ...interface{}) sql.Result
	func MustExecContext(ctx context.Context, e ExecerContext, query string, args ...interface{}) sql.Result
	func Named(query string, arg interface{}) (string, []interface{}, error)
		* 解析 name 查询为 ? 查询
			var params = map[string]interface{}{
				"createAt": time.Now(),
				"names":    []string{"JPA", "MYBATIS"},
				"ids":      []int{15, 16},
				"enabled":  true,
			}

			query, args, err := sqlx.Named("SELECT `id`, `name` FROM `user` WHERE `create_at` < :createAt and `name` in (:names) and `id` IN (:ids) and `enabled` = :enabled ", params)
			log.Println(query, args, err)
			// SELECT `id`, `name` FROM `user` WHERE `create_at` < ? and `name` in (?) and `id` IN (?) and `enabled` = ?  [2022-06-20 12:45:52.0977691 +0800 CST m=+0.008774201 [JPA MYBATIS] [15 16] true] <nil>
		
		* 也可以把参数换成结构体
			var params = struct {
				CreateAt time.Time `db:"createAt"`
				Names    []string  `db:"names"`
				Ids      []int     `db:"ids"`
				Enabled  bool      `db:"enabled"`
			}{
				CreateAt: time.Now(),
				Names:    []string{"JPA", "MYBATIS"},
				Ids:      []int{15, 16},
				Enabled:  true,
			}
		
	func NamedExec(e Ext, query string, arg interface{}) (sql.Result, error)
	func NamedExecContext(ctx context.Context, e ExtContext, query string, arg interface{}) (sql.Result, error)
	func Rebind(bindType int, query string) string
	func Select(q Queryer, dest interface{}, query string, args ...interface{}) error
	func SelectContext(ctx context.Context, q QueryerContext, dest interface{}, query string, ...) error
	func SliceScan(r ColScanner) ([]interface{}, error)
	func StructScan(rows rowsi, dest interface{}) error