--------------------
session
--------------------
	# 会话的概念
		* Session/WithContext/Debug 方法都会新建一个会话
		* db单独链式调用，会新建一个会话


	# 通过 Session 新创建一个会话
		func (db *DB) Session(config *Session) *DB

	
	# type Session struct {
			DryRun                   bool		
			PrepareStmt              bool
			NewDB                    bool
				* 忽略之前的条件

			SkipHooks                bool
				* 跳过钩子程

			SkipDefaultTransaction   bool
				* 是否关闭默认事务
				* 默认 GORM 会在事务里执行写入操作（创建、更新、删除）

			DisableNestedTransaction bool
				* 是否禁用嵌套事务

			AllowGlobalUpdate        bool
				* 是否允许全局更新/删除

			FullSaveAssociations     bool
				* 创建、更新记录时，是否级联保存关联的对象

			QueryFields              bool
				* 检索的时候，是否完整给出检索的列

			Context                  context.Context
				* 指定Context

			Logger                   logger.Interface
				* 指定日志

			NowFunc                  func() time.Time
				*  GORM 获取当前时间的实现

			CreateBatchSize          int
				* 批量插入大小
		}


	# DryRun
		* 开起调试模式，不会执行SQL
			stmt := db.Session(&Session{DryRun: true}).First(&user, 1).Statement
			stmt.SQL.String() //=> SELECT * FROM `users` WHERE `id` = $1 ORDER BY `id`	// 最终生成的语句
			stmt.Vars         //=> []interface{}{1}										// 填充的变量
		

		* 执行最终生成的SQL
			// 注意：SQL 并不总是能安全地执行，GORM 仅将其用于日志，它可能导致会 SQL 注入
			db.Dialector.Explain(stmt.SQL.String(), stmt.Vars...)
			// SELECT * FROM `users` WHERE `id` = 1

	# PreparedStmt 
		* 是否要缓存SQL预编译，可以提高效率

