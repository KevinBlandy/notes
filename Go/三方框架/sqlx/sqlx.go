--------------------------
sqlx
--------------------------
	# SQL
		sqlx是一个go语言包，在内置database／sql包之上增加了很多扩展，简化数据库操作代码的书写。

	# 地址
		http://jmoiron.github.io/sqlx/
		https://github.com/jmoiron/sqlx
	
		"github.com/jmoiron/sqlx"
	
	# 参考博客
		https://colobu.com/2024/05/10/sqlx-a-brief-introduction/

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
	
	# 命名参数
		* 在SQL中使用 :name 作为占位符
		* 可以使用Map,结构体填充。
		* 如果SQL中的占位命名参数是小写，可以在结构体字段上添加 `db:"name"` 来定义占位参数名称
		
		* IN 不能直接用于 IN 查询，需要通过 sqlx.In 转换

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

			type User struct {
				Id       int        `db:"id"`
				Name     string     `db:"name"`
				CreateAt *time.Time `db:"create_at"`
				UpdateAt *time.Time `db:"update_at"`
				Balance  string     `db:"balance"`
				Enabled  bool       `db:"enabled"`
			}

			// 命名查询，返回的结果是带 ? 占位的
			query, args, err := sqlx.Named("SELECT * FROM `user` WHERE `create_at` < :createAt and `name` in (:names) and `id` IN (:ids) and `enabled` = :enabled ", params)
			log.Println(query, args, err)

			// 因为有IN，需要扩张开里面的切片，并且自动添加?。返回结果是带占位符的
			query, args, err = sqlx.In(query, args...)
			log.Println(query, args, err)

			// 如果当前数据库不支持 ? 占位符，则需要重新绑定
			query = db.Rebind(query)

			// 执行检索，获取到返回值
			var ret []User
			err = db.Select(&ret, query, args...)
			if err != nil {
				log.Fatalln(err.Error())
			}
			log.Println(ret)

--------------------------
sqlx - 批量插入
--------------------------
	func BatchInsertUsers(users []*User) error {
		_, err := DB.NamedExec("INSERT INTO user (name, age) VALUES (:name, :age)", users)
		return err
	}


--------------------------
sqlx - 列名驼峰转小写下划线
--------------------------
	func(s string) string {
		sb := strings.Builder{}
		for i, v := range s {
			if unicode.IsUpper(v) {
				if i != 0 {
					sb.WriteString("_")
				}
				v = unicode.ToLower(v)
			}
			sb.WriteRune(v)
		}
		log.Println(sb.String())
		return sb.String()
	}
