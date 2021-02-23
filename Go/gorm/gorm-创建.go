-------------------------
grom的创建
-------------------------
	# 通过 gorm.Open 创建
		func Open(dialector Dialector, config *Config) (db *DB, err error)
		dialector
			* 指定SQL方言实现
		config
			* 配置信息
		
	# 使用已经存在的数据源创建
		import (
		  "database/sql"
		  "gorm.io/gorm"
		)
		// 自己创建DB
		sqlDB, err := sql.Open("mysql", "mydb_dsn")
		// 创建GROM
		gormDB, err := gorm.Open(mysql.New(mysql.Config{
		  Conn: sqlDB,	// 通过配置类的Conn指定数据源
		}), &gorm.Config{})
	
	# 方言的使用（MYSQL为例）
		* 根据链接创建方言
			import "gorm.io/driver/mysql"
			const (
				host string = "localhost"		// 主机
				port int = 3306					// 端口
				db string = "demo"				// 数据库
				username string = "root"		// 用户名
				password string = "root"		// 密码
			)

			mysql.Open(fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", username, password, host, port, db))
		
		* 根据配置创建方言
			mysql.New(mysql.Config{
			  DSN: fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", username, password, host, port, db), // DSN data source name
			  DefaultStringSize: 256, // string 类型字段的默认长度
			  DisableDatetimePrecision: true, // 禁用 datetime 精度，MySQL 5.6 之前的数据库不支持
			  DontSupportRenameIndex: true, // 重命名索引时采用删除并新建的方式，MySQL 5.7 之前的数据库和 MariaDB 不支持重命名索引
			  DontSupportRenameColumn: true, // 用 `change` 重命名列，MySQL 8 之前的数据库和 MariaDB 不支持重命名列
			  SkipInitializeWithVersion: false, // 根据当前 MySQL 版本自动配置
			}
	
	# config
		type Config struct {
			SkipDefaultTransaction bool
				* 跳过默认事务，默认情况下GORM 会在事务里执行写入操作（创建、更新、删除）

			NamingStrategy schema.Namer
				* 更改命名约定
				* Namer 接口
					type Namer interface {
						TableName(table string) string
						ColumnName(table, column string) string
						JoinTableName(table string) string
						RelationshipFKName(Relationship) string
						CheckerName(table, column string) string
						IndexName(table, column string) string
					}
				* 默认的一个实现配置 schema.NamingStrategy
					type NamingStrategy struct {
						TablePrefix   string			// 表名称前缀
						SingularTable bool				// 使用单数表名
						NameReplacer  *strings.Replacer	// 在转为数据库名称之前，使用NameReplacer更改结构/字段名称。
					}
				
			FullSaveAssociations bool
			Logger logger.Interface
				* 日志记录器

			NowFunc func() time.Time
				* 更改创建时间使用的函数
			
			DryRun bool
				* 生成 SQL 但不执行，可以用于准备或测试生成的 SQL
			
			PrepareStmt bool
				*  在执行任何 SQL 时都会创建一个 prepared statement 并将其缓存，以提高后续的效率
			
			DisableAutomaticPing bool
				* 在完成初始化后，GORM 会自动 ping 数据库以检查数据库的可用性

			DisableForeignKeyConstraintWhenMigrating bool
				* 在 AutoMigrate 或 CreateTable 时，GORM 会自动创建外键约束，若要禁用该特性，可将其设置为 true

			DisableNestedTransaction bool
				* 禁止嵌套事务

			AllowGlobalUpdate bool
				* 启用全局 update/delete
			
			QueryFields bool
			CreateBatchSize int
			ClauseBuilders map[string]clause.ClauseBuilder
			ConnPool ConnPool
			Dialector
			Plugins map[string]Plugin
		}
	
	# 连接池的设置
		* 可以通过 DB() 方法获取到原始的连接池对象，进行池参数的设置
			func (db *DB) DB() (*sql.DB, error)

			// SetMaxIdleConns 用于设置连接池中空闲连接的最大数量。
			sqlDB.SetMaxIdleConns(10)

			// SetMaxOpenConns 设置打开数据库连接的最大数量。
			sqlDB.SetMaxOpenConns(100)

			// SetConnMaxLifetime 设置了连接可复用的最大时间。
			sqlDB.SetConnMaxLifetime(time.Hour)
	

	# Demo
		import (
			"fmt"
			"gorm.io/driver/mysql"
			"gorm.io/gorm"
			"gorm.io/gorm/schema"
			"log"
		)

		func main(){
			const (
				host string = "localhost"		// 主机
				port int = 3306					// 端口
				db string = "demo"				// 数据库
				username string = "root"		// 用户名
				password string = "root"		// 密码
			)
			gormDB, err := gorm.Open(mysql.Open(fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", username, password, host, port, db)),
				&gorm.Config{
					NamingStrategy: schema.NamingStrategy{
						SingularTable: true,		// 表名称单数
					},
				})
			if err != nil {
				log.Fatal(err)
			}

			log.Println(gormDB.Name())
		}