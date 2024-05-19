--------------------------
sqlx
--------------------------
	# SQL
		sqlx��һ��go���԰���������database��sql��֮�������˺ܶ���չ�������ݿ�����������д��

	# ��ַ
		http://jmoiron.github.io/sqlx/
		https://github.com/jmoiron/sqlx
	
		"github.com/jmoiron/sqlx"
	
	# �ο�����
		https://colobu.com/2024/05/10/sqlx-a-brief-introduction/

	# ���ݿ�ĳ�ʼ��
		func Connect(driverName, dataSourceName string) (*DB, error)
		func ConnectContext(ctx context.Context, driverName, dataSourceName string) (*DB, error)
		func MustConnect(driverName, dataSourceName string) *DB
			* �����µ����ݿ⣬����������������
			
		func MustOpen(driverName, dataSourceName string) *DB
		func Open(driverName, dataSourceName string) (*DB, error)
			* �����µ����ݿ⣬��������������

		func NewDb(db *sql.DB, driverName string) *DB
			* ʹ��sql.DB�������ݿ�
		
		* Demo
			db, err := sqlx.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root1", "localhost", 3306, "demo"))
			if err != nil {
				log.Fatalf("�������ݿ��쳣��err=%s\n", err.Error())
			}
			if err := db.Ping(); err != nil {
				log.Fatalf("��ȡ���ݿ������쳣��err=%s\n", err.Error())
			}
	
	# ��������
		* ��SQL��ʹ�� :name ��Ϊռλ��
		* ����ʹ��Map,�ṹ����䡣
		* ���SQL�е�ռλ����������Сд�������ڽṹ���ֶ������ `db:"name"` ������ռλ��������
		
		* IN ����ֱ������ IN ��ѯ����Ҫͨ�� sqlx.In ת��

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

			// ������ѯ�����صĽ���Ǵ� ? ռλ��
			query, args, err := sqlx.Named("SELECT * FROM `user` WHERE `create_at` < :createAt and `name` in (:names) and `id` IN (:ids) and `enabled` = :enabled ", params)
			log.Println(query, args, err)

			// ��Ϊ��IN����Ҫ���ſ��������Ƭ�������Զ����?�����ؽ���Ǵ�ռλ����
			query, args, err = sqlx.In(query, args...)
			log.Println(query, args, err)

			// �����ǰ���ݿⲻ֧�� ? ռλ��������Ҫ���°�
			query = db.Rebind(query)

			// ִ�м�������ȡ������ֵ
			var ret []User
			err = db.Select(&ret, query, args...)
			if err != nil {
				log.Fatalln(err.Error())
			}
			log.Println(ret)

--------------------------
sqlx - ��������
--------------------------
	func BatchInsertUsers(users []*User) error {
		_, err := DB.NamedExec("INSERT INTO user (name, age) VALUES (:name, :age)", users)
		return err
	}


--------------------------
sqlx - �����շ�תСд�»���
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
