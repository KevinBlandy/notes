--------------------------------
validate						|
--------------------------------
	# 类似于SQL中的 explain, 不仅可以判断搜索是否合法
		* 在使用非常复杂的搜索DSL情况下, 可以先通过这种方式来验证是否合法

	# 语法
		GET /<index>/_validate/query?explain
		{
			"query":....
		}
	
		{
		  "_shards" : {
			"total" : 1,
			"successful" : 1,
			"failed" : 0
		  },
		  "valid" : true,
				* 是否合法
		  "explanations" : [
			{
			  "index" : "<index>",
			  "valid" : true,
			  "explanation" : "ConstantScore(NormsFieldExistsQuery [field=modify_date])"
			}
		  ]
		}
		
