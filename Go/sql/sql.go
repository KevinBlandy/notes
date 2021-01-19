---------------------
sql
---------------------
	# 加载驱动
		_ "github.com/go-sql-driver/mysql"
	


	# 占位符
		* 不同语言的占位符不同
		MySQL                    
			WHERE col = ?		VALUES(?, ?, ?)   
		PostgreSQL       
			WHERE col = $1		VALUES($1, $2, $3)
		Oracle
			WHERE col = :col	VALUES(:val1, :val2, :val3)