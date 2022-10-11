-----------------------
本地方法
-----------------------
	# 使用 Template 模板

		* Expressions 类定义了很多

	# BooleanTemplate
		BooleanTemplate booleanTemplate =
				Expressions.booleanTemplate("find_in_set({0}, {1}) > 0", 1, "1,2,3");
					// 1,2,3可以更换为数据库字段(querydsl表达式)

		
		List<Long> ret = query.select(qSysDepartment.id)
			.from(qSysDepartment)
			.where(Expressions.booleanTemplate("find_in_set({0}, {1}) > 0", 6, qSysDepartment.path))
			.fetch();
		

		* ">"  符号很关键，没有这个，结果就不会是boolean，会抛出异常:
	
