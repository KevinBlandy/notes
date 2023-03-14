-----------------------------
一些表达式
-----------------------------
	# 隐藏手机号码中间4位
		Field<String> phone = concat(substring(ADMIN.ACCOUNT, 1, 3), inline("****"), substring(ADMIN.ACCOUNT, 8)).as("phone");
		Field<String> phone = overlay(ADMIN.ACCOUNT, "****", 4).as("phone");
	
	# MYSQL的 FIND_IN_SET
		field("FIND_IN_SET({0},  {1})", "1", "1,2,3,4,5,").cast(Boolean.class)
		// CAST(FIND_IN_SET('1',  '1,2,3,4,5,') AS unsigned)
	
	# 日期格式化
		field("date_format({0}, {1})", field("NOW()"), "%d/%m/%Y").cast(String.class).as("NOW")
		// CAST(date_format(NOW(), '%d/%m/%Y') AS char) AS `NOW`
		
	
	