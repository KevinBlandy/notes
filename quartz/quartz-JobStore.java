--------------------------
JobStore
--------------------------
	# Job的存储规则
		* 不建议直接在代码中使用 JobStore 实例，而是通过配置来完成
	
	# 通过配置指定sotre
		org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore

--------------------------
RAMJobStore 
--------------------------
	# 内存Job存储，性能最高

--------------------------
JDBCJobStore 
--------------------------
	# 存储到数据库
		* 几乎适用于任何数据库，它已广泛用于 Oracle、PostgreSQL、MySQL、MS SQLServer、HSQLDB 和 DB2。
		* 可以在 Quartz 发行版的 "docs/dbTables" 目录中找到创建表的 SQL 脚本
	
	# 事务配置，是通过不同 JobStore 实现完成的
		* 不需要关联其他事务
			org.quartz.impl.jdbcjobstore.JobStoreTX
		* 需要关联其他事务(Quartz 与其他事务一起工作，即在 J2EE 应用程序服务器中)
			org.quartz.impl.jdbcjobstore.JobStoreCMT 
		
		org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
	
	# 指定DriverDelegate
		* DriverDelegate 负责执行特定数据库可能需要的任何 JDBC 工作
		* 通用的: StdJDBCDelegate
			* 如果没有找到定制的，就用它

		* 不同数据数据库可能有定制的
			PostgreSQLDelegate
			WeblogicDelegate
			MSSQLDelegate
			HSQLDBDelegate
			DB2v6Delegate

		org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
	
	# 表前缀
		* 在这些脚本中，所有表都以前缀“QRTZ_”开头（例如表“QRTZ_TRIGGERS”和“QRTZ_JOB_DETAIL”）。
		* 前缀可以修改，在配置中指定就可以。使用不同的前缀可能有助于创建多组表、多个调度程序实例、

		org.quartz.jobStore.tablePrefix = QRTZ_
	
	# 指定数据源
		org.quartz.jobStore.dataSource = myDS
	
	# 配置数据源
		org.quartz.dataSource.myDS.driver = oracle.jdbc.OracleDriver
		org.quartz.dataSource.myDS.URL = jdbc:oracle:thin:@10.132.81.134:1521:dsdb1
		org.quartz.dataSource.myDS.user = masmf
		org.quartz.dataSource.myDS.password = masmf
		org.quartz.dataSource.myDS.maxConnections = 10
