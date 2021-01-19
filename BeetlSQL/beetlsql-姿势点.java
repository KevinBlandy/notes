-----------------------
生成代码				|
-----------------------
	# 依赖于 SqlManager 核心对象
		sqlManager.genPojoCodeToConsole(String tableName);
			* 根据表名称输出POJO代码到控制台
		sqlManager.genSQLTemplateToConsole(String tableName);
			* 根据表名称输出SQL模版到控制台
	
	# 生成属性的时候，id总是在前面，后面依次是类型为Integer的类型，最后面是日期类型，剩下的按照字母排序放到中间。

