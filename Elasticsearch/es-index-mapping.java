------------------------
mapping					|
------------------------
	# 文档
		https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html
	
	# 跟关系型数据库的DDL一样, 定义存储的数据类型, 索引, 使用的分词器等等
		* 不同的数据类型, 在建立索引, 分词的时候可能有所不同
		* 会影响到索引的建立和搜索行为

		* mapping 可以在创建 index 之前创建
		* mapping 可以在创建 index 之后编辑, 编辑只能是新增, 不能是修改


	# 查看指定索引的mapping
		GET /<index>/_mapping

	# mapping的结构
		{
			"<index>":{
				"mappings":{
					"properties":{
						"<field>":{

						}
					}
				}
			}
		}

		* properties 下就是每个字段对应的'配置'
			- 数据类型
			- 如何分词
			- ... ...
		* properties 属性下可以嵌套 properties, 表示文档的嵌套字段
	


	# 建立索引的时候, 创建 mapping
		PUT /<index>
		{
			"mappings":{
				"properties":{
					"<field>":{
						...
					}
				}
			}
		}
	
	# 创建索引后, 修改 mapping
		PUT /<index>/_mapping
		{
			"properties":{
				"<field>":{
					...
				}
			}
		}
	
	# fileds 设置 多字段
		* 所谓的多字段，就是可以给某个字段，添加上另一个属性字段
		* 对于这个新的属性字段可以设置不同的索引策略等等
			"properties": {
				"tags": {
					"type": "string"
					"index": "analyzed"
					"fileds": {
						"verbatim": {
							"type": "string"
							"index": "no_analyzed"
						}
					}
				}
			}
		
		* tags			字段，分词+索引
		* tags.verbatim	字段，索引，不分词


------------------------
type 数据类型			|
------------------------
	# 指定字段的数据类型, 如果不手动设置, 则自动推测
		
	# long
	# boolean
	# double
	# string
	# byte
	# short
	# integer
	# float
	# text
	# date
	# keyword


------------------------
字符串
------------------------
	# index 索引策略
		analyzed
			* 默认，先分词，再索引
		not_analyzed
			* 可以被索引, 但是不分词，需要完整匹配的时候，可以用这个
			* 整个字段作为“一个词儿”被索引
		no
			* 不可以被索引, 也不进行分词


------------------------
日期
------------------------
	# 日期类型的数据，会被ES转换为时间戳(long)进行存储, 因为数值的存储和处理更快

	# format 日期的格式化方式
		* 默认的格式化是ISO8601，类似于: 2021-09-26T12:21:24+08:00
			time.Now().Format(time.RFC3339)
		


------------------------
数组
------------------------
	# 所有的核心类型，都支持数组


------------------------
analyzer 
------------------------
	# 指定分词器，默认: standard

------------------------
store 
------------------------
	# 存储指定的字段

------------------------
include_in_all
------------------------
	# 是否要包含这个字段到 _all 中


------------------------
settings
------------------------

-----------------------
预定义字段
-----------------------
	# 预定义字段是 _ 开头

	_all
	_source
	_uid
	_id
	_type
	_index
	_timestamp
	_size
	_routing
	_parent



------------------------
mapping	字段属性		|
------------------------
	# Doc
		{
		  "id": 1,
		  "name": "KevinBlandy",
		  "hobby": ["唱", "跳", "rap"],
		  "lang": [{
			"name": "Java",
			"site": "https://java.com"
		  }, {
			"name": "Golang",
			"site": "https://golang.io"
		  }],
		  "blog": {
			"host": "https://springboot.io",
			"name": "SpringBoot中文社区"
		  }
		}
	
	# Mapping
		{
		  "users" : {
			"mappings" : {
			  "properties" : {
				"blog" : {
				  "properties" : {
					"host" : {
					  "type" : "text",
					  "fields" : {
						"keyword" : {
						  "type" : "keyword",
						  "ignore_above" : 256
						}
					  }
					},
					"name" : {
					  "type" : "text",
					  "fields" : {
						"keyword" : {
						  "type" : "keyword",
						  "ignore_above" : 256
						}
					  }
					}
				  }
				},
				"hobby" : {
				  "type" : "text",
				  "fields" : {
					"keyword" : {
					  "type" : "keyword",
					  "ignore_above" : 256
					}
				  }
				},
				"id" : {
				  "type" : "long"
				},
				"lang" : {
				  "properties" : {
					"name" : {
					  "type" : "text",
					  "fields" : {
						"keyword" : {
						  "type" : "keyword",
						  "ignore_above" : 256
						}
					  }
					},
					"site" : {
					  "type" : "text",
					  "fields" : {
						"keyword" : {
						  "type" : "keyword",
						  "ignore_above" : 256
						}
					  }
					}
				  }
				},
				"name" : {
				  "type" : "text",
				  "fields" : {
					"keyword" : {
					  "type" : "keyword",
					  "ignore_above" : 256
					}
				  }
				}
			  }
			}
		  }
		}
