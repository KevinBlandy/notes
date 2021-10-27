------------------------------
聚合检索					  |
------------------------------

------------------------------
聚合检索					  |
------------------------------
	# aggs
		{
		  "aggs":{
			"<name>":{
			  "<agg>": {
				"field": "<field>",
				"order": {
					"<name>" : "<desc/asc>"
				}
			  }
			}
		  }
		}
		
		* name, 对分组起名,自定义
		* agg, 聚合的统计操作
		* field , 用于分组的属性, 可以是数组, 字段, 不能是对象,否则检索不出来数据
	

	# terms
		* 统计总数量
	
	# ranges
		* 根据某个区间值来进行分组

	# avg
		* 求平均值
	
	
	
	# 聚合检索可以嵌套计算
		GET /goods/_search
		{
		  "aggs":{
			"groupByAuthorAge":{
			  "terms": {
				"field": "author.age" // 先根据作者的年龄来聚合检索
			  },
			  "aggs": {
				"avgd": {
				  "avg": {
					"field": "price"	// 根据聚合检索的结果, 对价格字段再次进行平均值的计算
				  }
				}
			  }
			}
		  }
		}
		* 类似于, 先group by ,然后再执行count,avg啥的计算

	
	# 使用排序
		GET /goods/_search
		{
		  "aggs":{
			"groupByAuthorAge":{
			  "terms": {
				"field": "author.age",
				"order":{
					"avgd":"desc"  // 根据子聚合结果进行降序排序
				}
			  },
			  "aggs": {
				"avgd": {
				  "avg": {
					"field": "price"
				  }
				}
			  }
			}
		  }
		}
	
	# 可以通过设置: size : 0, 来限制显示的结果
		* 仅仅只查看聚合的统计结果, 不查看doc
	
