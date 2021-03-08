--------------------------
原生SQL
--------------------------
	# 原生API
		* 检索
			func (db *DB) Raw(sql string, values ...interface{}) (tx *DB)
		
		* 修改
			func (db *DB) Exec(sql string, values ...interface{}) (tx *DB)
		
	
	# 原生API支持大部分orm的功能
		* 命名参数
		* map作为填充参数载体
		* 结构体作为填充参数载体
	
	
	# DryRun 模式
		* 获取到orm最终执行的SQL，但是并不会执行，用于调试
			stmt := db.Session(&Session{DryRun: true}).First(&user, 1).Statement
			stmt.SQL.String() //=> SELECT * FROM `users` WHERE `id` = $1 ORDER BY `id`	// 最终执行的SQL
			stmt.Vars         //=> []interface{}{1}										// 填充的参数
		
	
	# 获取到Row/Rows，可以自己处理检索结果
		func (db *DB) Rows() (*sql.Rows, error)
		func (db *DB) Row() *sql.Row
	
		
		* gorm提供了针对于rows的快捷封装方法
			func (db *DB) ScanRows(rows *sql.Rows, dest interface{}) error 
		
		* 对于Row的封装，可以使用 Scan
			db.Raw("...", "...").Scan()
		
