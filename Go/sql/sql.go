---------------------
sql
---------------------
	# �ο�
		https://github.com/golang/go/wiki/SQLInterface
		https://www.jianshu.com/p/5e7477649423
	
	# ����
		* �������ݿ��Ӧ�������б�
			https://github.com/golang/go/wiki/SQLDrivers
		
		* �����ļ���
			_ "github.com/go-sql-driver/mysql"
	
	# URL ���Ӳ���

		https://github.com/go-sql-driver/mysql
			parseTime
				* parseTime=true �Ὣ DATE �� DATETIME ֵ��������͸���Ϊ time.Time�������� []byte / string ���ڻ�����ʱ�䣨�� 0000-00-00 00:00:00����ת��Ϊ time.Time ����ֵ��
				* ����� false �򷵻� []uint8 �������ַ��� 2025-01-14 13:43:54
			
			loc
				* ָ��ʱ��������/д�� Timestamp ʱʹ�õ�ʱ��

					loc=UTC		ʹ�� UTC ʱ��
					loc=Local	ʹ�ñ���ʱ��

		

	
	
	# ����API����
		DB ִ��SQL
		DB ��ȡ*Stmt	->	*Stmt������	-> *Stmtִ��SQL
		DB ��ȡ*Tx		->	*Txִ��SQ;
		DB ��ȡ*Tx		->	*Tx��ȡ*Stmt	->	*Stmt������ -> *Stmtִ��SQL

		DB ��ȡ*Conn	->	*Conn���Ե���һ��DB����ʹ�ã�ִ�����������


	# ռλ��
		* ��ͬ���Ե�ռλ����ͬ
		MySQL                    
			WHERE col = ?		VALUES(?, ?, ?)   
		PostgreSQL       
			WHERE col = $1		VALUES($1, $2, $3)
		Oracle
			WHERE col = :col	VALUES(:val1, :val2, :val3)
		
	
	# DB�Ķ���Ĵ���
		const (
			host string = "localhost"	// ����
			port int = 3306					// �˿�
			db string = "demo"				// ���ݿ�
			username string = "root"		// �û���
			password string = "root"		// ����
		)
		func main() {
			db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
			if err != nil {
				log.Panic(err)
			}
			fmt.Println(db)
		}
		
		* �����ɹ�����������ã��ײ��������ӳٴ����ģ�ֻ���ڵ�һ��ʹ�õ�ʱ��Żᴴ��
		* ����ʹ�� Ping() ����飬�����Ƿ���������������� err ���ʾ������
	
	# ͨ��Driver���ж��쳣����
			if driverErr, ok := err.(*mysql.MySQLError); ok { // ȷ����MYSQL���쳣
				if driverErr.Number == 1045 {
					// ͨ���쳣״̬�����ж�
					var _ string = driverErr.Message // �쳣����Ϣ��ʾ
				}
			}
	
	# ������
		func Service (db *sql.DB) () {
			// ��ʼ����ָ�����뼶���ֻ������
			tx, err := db.BeginTx(context.Background(), &sql.TxOptions{
				Isolation: sql.LevelRepeatableRead,
				ReadOnly: true,
			})
			if err != nil {
				log.Fatal(err)
			}

			// TODO ִ��ҵ��
			stmt, err := tx.Prepare("SELECT * FROM `user` WHERE `id`= ?;")

			// ���ر�stmt��֮ǰGolang1.4�ر�*sql.Tx����֮���������ӷ���������
			// ��������� Rollback ֮ǰִ�еĻ����ڲ��������¿��������⣨stmt�رպ󣬻��������ӳأ�����Э�̻�ȡ�����ӣ�Ȼ���ʱִ���˻ع���
			defer stmt.Close()

			// ʼ�ջع�������Ѿ��ύ�ˣ��ع������κ�Ӱ��
			defer tx.Rollback()

			if err != nil {
				log.Fatal(err)
			}
			rows, err := stmt.Query(1)
			if err != nil {
				log.Fatal(err)
			}

			// �ͷ���Դ
			defer rows.Close()

			for rows.Next() {
				// ��������
				rows.Scan()
			}

			// ����ύ����
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
			host string = "localhost"	// ����
			port int = 3306					// �˿�
			db string = "demo"				// ���ݿ�
			username string = "root"		// �û���
			password string = "root"		// ����
		)

		func main() {
			db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "root", "localhost", 3306, "demo"))
			if err != nil {
				log.Panic(err)
			}

			// ִ��SQL��ȡ�������
			result, err := db.Query("SELECT * FROM `user` LIMIT ?, ?;", 0, 9)
			if err != nil {
				log.Fatal(err)		// ִ���쳣
			}
			defer func() {	// �رգ��ͷ���Դ
				if err := result.Close(); err != nil {
					log.Println(err)
				}
			}()

			var id int
			var name string
			var date string

			// ����ÿһ��
			for result.Next() {
				err := result.Scan(&id, &name, &date)
				if err != nil {
					log.Fatal(err) // ��װ�쳣
				}
				log.Println(id, name, date)
			}

			if err := result.Err(); err != nil {
				log.Fatal(err)	// ���������еĴ��󣬿��ܵ������̱��ж���
			}
		}


---------------------
sql������ķ�װ
---------------------
	# Scan ����������Ӧ����ָ��
		* ��������������Rows�е�������ͬ
		* ��������
			*string
			*[]byte
			*int, *int8, *int16, *int32, *int64
			*uint, *uint8, *uint16, *uint32, *uint64
			*bool
			*float32, *float64
			*interface{}
			*RawBytes
			*Rows (cursor value)
			�κ�ʵ���� sql.Scanner ������
	
	# Nullֵ�Ĵ���
		* ʹ��Ԥ����ļ�����������ʾNullֵ
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
					// ��null
				} else {
					// nullֵ
				}
			}