--------------------------
sqlx
--------------------------
	# SQL
		sqlx��һ��go���԰���������database��sql��֮�������˺ܶ���չ�������ݿ�����������д��

	# ��ַ
		http://jmoiron.github.io/sqlx/
		https://github.com/jmoiron/sqlx
	

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
	
--------------------------
sqlx - ����
--------------------------

	func (db *DB) MustExec(query string, args ...interface{}) sql.Result 
	func (db *DB) MustExecContext(ctx context.Context, query string, args ...interface{}) sql.Result