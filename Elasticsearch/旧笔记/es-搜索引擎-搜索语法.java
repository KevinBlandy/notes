------------------------------------
_all metadata的原理和作用			|
------------------------------------
	GET /_search?q=elaticsearch
		* 所有的index,所有的type,所有的document,所有的field里面有elaticsearch就符合条件
		* 这个在生产环境用得最多
		* 也可以通过url去限制index,type
	
	# 原理
		* 在建立索引的时候,插入一条document,它包含了多个field,此时es会把多个field的值用字符串串联,变成一个很长的字符串
			{
				"name":"KevinBlandy",
				"age":23,
				"gender":"男"
			}
			=============== 元数据
			"KevinBlandy 23 男"

		* 这个字符串叫做 _all 元数据


------------------------------------
Query String 基础语法				|
------------------------------------
	GET /index/type/_search?q=content:elaticsearch
		* content必须包含elaticsearch关键字	
		
	GET /index/type/_search?q=+content:elaticsearch
		*  同上
		
	GET /index/type/_search?q=-content:elaticsearch
		* content必须没包含elaticsearch关键字
	
	GET /_search
	GET /_index1,index2/type1,type2/_search
		* 针对于多个index,type的检索
	
	
	* HTTP协议规定GET没有请求体,一般也不允许GET请求带有body,但GET更加适合于检索场景
	* 如果遇到不支持的场景,也可以使用:POST /_search


------------------------------------
Query String - 分页	& 排序			|
------------------------------------
	* 分页
		GET /_search?from=0&size=10

		* from 表示从第几条数据开始
		* size 表示取几条数据
	
	* 排序
		GET /_search?sort=age:asc

		* 按照age升序排序


------------------------------------
DSL	- query							|
------------------------------------

	# 检索所有
		{
			"query":{
				"match_all": {}    
			}
		}
	
	# 根据字段数据检索
		{
			"query":{
				"match":{
					"name":"KevinBlandy"
				}
			}
		}
		* 检索 name 属性里面包含了 KevinBlandy 的记录
		* 一个match只能有一个属性,不支持多个属性
	
	# 多个字段的相同值的检索
		{
		 "query": {
		   "multi_match":{
			 "query":"1",
			 "fields": ["id","name"]
		   }
		 }
		}
		* 检索 id 包含了 1 或者 name包含了 1 的索引
	
	# boolean 属性
		* boolean 属性是一个或者多个条件
		* 属性名称枚举固定,属性值可以为数组或者对象
			* must		一个或者多个条件,必须全部满足
			* should	一个或者多个条件,满足一个即可
			* must_not	一个或者多个条件,必须全部不满足
			* filter	过滤(大于，等于，小余)
		
		* must	必须
		* should 任何一个条件满足即可
		* minimm_should_match 至少要匹配到一条
			
	# boolean - filter
		{
		  "query": {
			"bool": {
			  "filter": {
				"range": {
				  "id": {			//字段
					"gte": 10,		//条件和值
					"lte": 20
				  }
				}
			  }
			}
		  }
		}
		* range 也可以直接放在query里面,这样的话,会进行相关度的计算

	#  query 与 filter
		* filter仅仅按照搜索条件过滤出需要的数据
		* query,会去计算每个document相对于搜索条件的相关度,并且按照相关度进行排序
		
		* filter不需要计算相关度分数,不需要按照相关度分数进行排序,同时还有内置的,自动cache最常使用filter的功能
		* query相反,需要计算相关度分数,按照分数进行排序,而且无法cache结果
	


------------------------------------
DSL	- 定制搜索结果					|
------------------------------------
	"query":{
		"_source":[],
	}

	* 该值是一个数组,指定要检索的字段,而不是检索所有
	* 通过字符串执行属性,支持.属性导航

------------------------------------
DSL	- 排序							|
------------------------------------
	"query":{
		"sort":[
			{"field1":"desc"},
			{"field2":"asc"}
		],
	}

	* desc,倒序,asc 正序

------------------------------------
DSL	- 限制结果数量					|
------------------------------------
	"query":{
		"from":1,
		"size":2,
	}

	* 从第一条记录开始,检索出2条结果

------------------------------------
代码高亮							|
------------------------------------
	"query":{
		"highlight":{
			"fields":{
				"field":{}
			}
		}
	}

------------------------------------
判断你的检索是否可用				|
------------------------------------
	GET /index/type/_validate/query?explain

	{
	  "_shards": {
		"total": 1,
		"successful": 1,
		"failed": 0
	  },
	  "valid": true,
	  "explanations": [
		{
		  "index": "user",
		  "valid": true,
		  "explanation": "+ConstantScore(id:[2 TO 9223372036854775807]) #*:*"
		}
	  ]
	}

	* 在特别复杂,庞大的检索下,可以先使用该api测试一下检索语句是否有问题



------------------------------------
解决字符串排序的问题				|
------------------------------------
	# 把一个field索引两次来解决字符串的排序问题
		PUT /user
		{
			"mappings":{
				"coder":{
					"properties":{
						"title":{
							"type":"text",
							"analyzer":"english",
							"field":{						//再一次建立索引
								"raw":{
									"type":"string",		//类型
									"index":"false",		//不索引
									"fielddata":true
								}
							}
						}
					}
				}
			}
		}
		
		* 没看懂是啥骚操作
