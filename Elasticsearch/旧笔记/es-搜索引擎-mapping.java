--------------------
mapping				|
--------------------
	# 自动或者手动为index中的type建立的一种数据结构和相关配置,简称为mapping
	# 查看es自动建立的mapping
		GET /index/_mapping/type
		{
		  "user": {
			"mappings": {
			  "coder": {
				"properties": {
				  "name": {
					"type": "text",
					"fields": {
					  "keyword": {
						"type": "keyword",
						"ignore_above": 256
					  }
					}
				  },
				  "age": {
					"type": "long"
				  },
				  "foo": {
					"type": "long"
				  },
		...
	
	# 搜索结果不一致的问题
		* ES自动建立mapping的试试,设置了不同的field不同的data type
		* 不同的data type的数据类型,分词,搜索行为是不一样的,所以出现了 _all,field的搜索表现不一样

	
	# 透彻理解mapping
		1,往es里面直接插入数据,es会自动建立索引,同时建立type以及对应的mapping
		2,mapping中就自动定义了每个field的数据类型
		3,不同的数据类型(text,data),可能有的是exact value,有的是full text
		4,exact value 在建立倒排索引,分词的时候,是将整个值一起作为一个关键字建立到倒排索引中的
		5,full text 就会进过各种处理(分词,normalizationm....),建立在倒排索引中
		6,exact value 和 full text 的field在被搜索的时候,query string 也会经过相同的行为处理
			* exact value 全值匹配
			* full text 先对query string进行分词,normalizationm,再进行匹配
		7,可以用es的dynamic mapping,让其自动建立mapping(自动设置数据类型),也可以手动的提前为index创建mapping,手动对每个field设置数据类型,索引行为,分词器...
		8,mapping,就是index的type的元数据,每个type都有一个自己的mapping,决定了数据类型,建立倒排索引的行为,还有进行搜索的行为

--------------------
查看mapping			|
--------------------
	GET /index/_mapping/type
	{
	  "user": {
		"mappings": {
		  "coder": {
			"properties": {
			  "birthday": {
				"type": "text",
				"fields": {
				  "keyword": {
					"type": "keyword",
					"ignore_above": 256
				  }
				}
			  }
			}
		  }
		}
	  }
	}


--------------------
mapping-核心数据类型|
--------------------
	# 核心数据类型
		Text
		Byte,Short,Integer,Long
		Float,Double
		Boolean
		Date
		KeyWord
	
	#  数据类型的推测规则
		true or false		--> Boolean
		123					--> Long
		123.54				--> Double
		2017-01-01			--> Date
		"Hello"				--> Text


	* 6.x 以前 text 叫做 String,在6.x以后,String 被移除


--------------------
mapping-创建		|
--------------------
	# 只能创建index时手动建立mapping,或者新增mapping
	# 不能修改 field mapping
		PUT /user
		{
			"mappings":{
				"coder":{
					"properties":{
						"desc":{
							"type":"text",
							"analyzer":"english"
						},
						"title":{
							"type":"text"
						},
						"createDate":{
							"type":"date"
						},
						"id":{
							"type":"long"
						},
						"userId":{
							"type":"long",
							"index":"true"
						}
					}
				}
			}
		}

		* coder 表示type
		* properties 中 key 来指定属性,value来设置分词器以及数据类型
			- index		属性指定字段的是否建立索引,boolean 值(在6.x以前,这个值是字符串枚举:analyzed,not_analyzed,no)
			- analyzer	属性指定字段建立索引时,使用的分词器
			- type		指定字段的数据类型
			- filed		...

		
	# 添加一个字段
		PUT /user/_mapping/coder
		{
			"properties":{
				"newField":{
					"type":"text",
					"analyzer":"english"
				}
			}
		}
		
		* 在url中指定index和type
		* 通过请求体指定新的字段和其mapping属性
		
--------------------------
mapping-复杂的数据类型转换|
--------------------------
	# 数组
		"tags":["Java","PHP"]

		* 建立索引的时候,跟String是一样的,只是说数据类型不能混,[]里面要么全是字符串,要么全是数字
	
	# 空
		null,[],[null]
		
	# Object
		* 底层的数据结构转换
			{
				"name":"Kevin",
				"skill":{
					"java":90,
					"python":70
				}
			}	
			======================
			{
				"name":"Kevin",
				"skill.java":90,
				"skill.python":70
			}

		* 更为复杂的数据结构
			{
				"authors":[
					{"age":23,"name":"Kevin"},
					{"age":25,"name":"Litch"},
					{"age":24,"name":"Rocco"}
				]
			}
			======================
			{
				"authors.age":[23,25,24],
				"authors.name":["Kevin","Litch","Rooc"]
			}
	