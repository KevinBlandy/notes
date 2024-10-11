---------------
CASE 表达式
---------------
	# 语法
		* 语法 1，匹配值
			CASE t.gender 
				WHEN 1 THEN 'Boy'
				WHEN 0 THEN 'GIRL'
				ELSE 'Unknown'
			END
		
		* 语法 2，匹配第一个 true 的条件

			CASE 
				WHEN t.gender  = 1 THEN 'Boy'
				WHEN  t.gender = 0 THEN 'GIRL'
				ELSE 'Unknown'
			END
	
		
		* 匹配到第一个结果为 true 的 WHEN 子句时，CASE 表达式的真假值判断就会中止，剩余的 WHEN 子句会被忽略。
		* CASE 表达式里各个分支返回的数据类型必须要一致


