--------------------------
sqlx
--------------------------
	# SQL
		sqlx是一个go语言包，在内置database／sql包之上增加了很多扩展，简化数据库操作代码的书写。

	# 地址
		http://jmoiron.github.io/sqlx/
		https://github.com/jmoiron/sqlx
	

	# 数据库的初始化
		func Connect(driverName, dataSourceName string) (*DB, error)
		func ConnectContext(ctx context.Context, driverName, dataSourceName string) (*DB, error)
		func MustConnect(driverName, dataSourceName string) *DB
			* 创建新的数据库，并且立即创建连接
			
		func MustOpen(driverName, dataSourceName string) *DB
		func Open(driverName, dataSourceName string) (*DB, error)
			* 创建新得数据库，不立即创建连接

		func NewDb(db *sql.DB, driverName string) *DB
			* 使用sql.DB创建数据库
		
		* Demo
			db, err := sqlx.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root1", "localhost", 3306, "demo"))
			if err != nil {
				log.Fatalf("创建数据库异常：err=%s\n", err.Error())
			}
			if err := db.Ping(); err != nil {
				log.Fatalf("获取数据库连接异常：err=%s\n", err.Error())
			}
	
--------------------------
sqlx - 更新
--------------------------

	func (db *DB) MustExec(query string, args ...interface{}) sql.Result 
	func (db *DB) MustExecContext(ctx context.Context, query string, args ...interface{}) sql.Result