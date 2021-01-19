ConnectionSource
	# 数据源
	# 直接创建
		ConnectionSource source = ConnectionSourceHelper.getSimple(String driverClass, String url, String userName, String passWord);
	# 通过已有的数据源创建
		ConnectionSource source = ConnectionSourceHelper.single(DataSource dataSource);
	# 如果是主从数据源
		ConnectionSource source = ConnectionSourceHelper.getMasterSlave(DataSource master,DataSource[] slaves)
	
DBStyle
	# 

SQLLoader
	# 

UnderlinedNameConversion
	# 

SQLManager
	# 方法
		genPojoCodeToConsole(String tableName);
			* 根据数据表名称,创建POJO,并且把代码输出到控制台
		genSQLTemplateToConsole(String tableName);
			* 根据数据表名称创建template模版,并且把SQL输出到控制台
		
