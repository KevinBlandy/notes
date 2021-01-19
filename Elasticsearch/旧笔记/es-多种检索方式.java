------------------------
多种检索方式			|
------------------------
	# 一般来说有6种类
		1,query string search
		2,query DSL
		3,query filter
		4,full-text search
		5,phrase search
		6,highlight search

------------------------
检索响应				|
------------------------
	# 响应体
		{
		  "took": 2,				
		  "timed_out": false,	
		  "_shards": {			
			"total": 5,
			"successful": 5,
			"skipped": 0,
			"failed": 0
		  },
		  "hits": {		
			"total": 1,	
			"max_score": null,
			"hits": [
			  {
				"_index": "user",
				"_type": "coder",
				"_id": "3",
				"_score": null,
				"_source": {
				  "id": 3,
				  "name": "Rocco",
				  "age": 21,
				  "gender": "girl",
				  "hobby": [
					"basketball",
					"Sing",
					"Write the code"
				  ],
				  "skill": {
					"java": {
					  "level": "9"
					},
					"python": {
					  "level": "9"
					},
					"javascript": {
					  "level": "6"
					}
				  }
				},
				"sort": [
				  21
				]
			  }
			]
		  }
		}
		
		took
			* 耗费毫秒
		timed_out
			* 是否超时
		_shards
			* 数据拆分为了5个分片,搜索请求会打到所有的primary shard(或者某个replica shar)
		hits
		hits.total
			* 总记录数据
		hits.max_score
			* document对于一个search的相关度的匹配系数,越相关,就越匹配,分数也越高
		hits.hits
			* document的详细数据

------------------------
query string search		|
------------------------
	GET /{index}/{type}/_search?q=[k]:[v]&sort=[k]:[desc/asc]
		* 检索k=v的所有记录,并且按照k进行desc/asc排序

		* /user/coder/_search?q=gender:boy&sort=age:asc
		- 检索出所有gender=boy的用户,并且按照age升序排序
	
	* 最简单的检索,所有的条件,参数都是通过uri参数构建
	* 一般生产环境极少的使用

	
------------------------
query DSL				|
------------------------
	# DSL:Domain Specied Language(特定的领域语言)
	
	# 检索gender=boy的所有数据,并且按照age升序排序,id降序排序
			{
				"query":{
					"match":{
						"gender":"boy"
					}
				},
				"sort":[{"age":"asc"},{"id":"desc"}]
			}
		
	# 分页查询
		{
			"query":{
				"match_all":{}
			},
			"from":1,		//从第几条数据开始检索
			"size":2		//检索第几条
		}
	
	# 仅仅检索指定的字段数据
		{
			"query":{
				"match_all":{}
			},
			"_source":["name","id","skill.java"]	//仅仅检索指定的字段
		}
		* 仅仅检索Documnet里面的name,id属性.以及skill属性(对象)里面的java属性
		


	# 请求体的定义详解

		{
			"query":{
				"match_all":{
					
				},
				"match":{
					"key":"value"
				}
			},
			"sort":[
				{"key1":"desc"},
				{"key2":"asc"}
			],
			"from":1,
			"size":2,
			"_source":[""]
		}
		
		query.match_all
			* 检索所有,该值是一个空对象{}

		query.match
			* 检索符合条件的
			* 该值是一个对象,通过kv来组合条件
		from
			* 从第几个数据开始检索,limit的第一个参数
		size
			* 每页显示的记录数
		_source
			* 该值是一个数组,指定要检索的字段,而不是检索所有
			* 通过字符串执行属性,支持.属性导航
			* "_source":["name","age","skill.java"]		//仅仅检索name,age和skill属性里面的java属性

------------------------
query filter			|
------------------------
	# 对数据进行过滤,是基于DSL进行的过滤
	
	# 检索所有性别为:girl,年龄 gt/lt/le/ge/ne 23的记录
		{
			"query":{
				"bool":{
					"must":{
						"match":{
							"gender":"girl"
						}
					},
					"filter":{
						"range":{
							"age":{"gt/lt/le/ge/ne":"23"}
						}
					}
				}
			}
		}

------------------------
full-text search		|
------------------------
	# 全文检索
		{
			"query":{
				"match":{
					"key":"value1 value2"
				}
			}
		}
		
		* 检索 key里面包含了 value1,value2关键字的记录
		* 包含,即满足

------------------------
full-phrase search		|
------------------------
	# 短语搜索
		{
			"query":{
				"match_phrase":{
					"key":"value"
				}
			}
		}

		* 与全文检索相反,要求key的value必须完全匹配,才会被检索出来
		* 必须全匹配,才满足
	

------------------------
full-highlight search	|
------------------------
	# 高亮
	{
		"query":{
			"match":{
				"key":"value"
			}
		},
		"highlight":{
			"fields":{
				"key":{}
			}
		}
	}

--------------------
聚合				|
--------------------
	{
		"aggs":{
			"group_by_tags":{
				"terms":{
					"field":"tags"
				}
			}
		}
	}

	* 根据tags字段进行聚合检索,返回每个tag下的商品数量

	{
		"aggs":{
			"group_by_tags":{
				"terms":{
					"field":"tags",
					"order":{
						"avg_price":"desc"
					}
				},
				"aggs":{
					"avg_price":{
						"field":"price"
					}
				}
			}
		}
	}

	* 根据tags字段计算分组,然后计算机每组的平均值
	* 根据 avg_price 来降序排序

	