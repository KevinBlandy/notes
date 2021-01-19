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
		* properties 属性下可以嵌套 properties
	


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
index 是否可以被索引	|
------------------------
	# 指定字段是否可以被索引
		* 默认既允许索引, 也要进行分词

	# not_analyzed
		* 可以被索引, 但是不分词

	# no
		* 不可以被索引, 也不进行分词

------------------------
analyzer 指定分词器		|
------------------------
	# 默认: standard

------------------------
settings				|
------------------------

------------------------
_source					|
------------------------

------------------------
_all					|
------------------------

------------------------
dynamic					|
------------------------

------------------------
mapping	字段属性		|
------------------------
	{
	  "<index>" : {
		"mappings" : {
		  "properties" : {
			"<field>" : {
			  "properties" : {
				"<field>" : {
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
			"<field>" : {
			  "type" : "text",
			  "fields" : {
				"keyword" : {
				  "type" : "keyword",
				  "ignore_above" : 256
				}
			  }
			},
			"<field>" : {
			  "type" : "date"
			},
			"<field>" : {
			  "type" : "long"
			}
		  }
		}
	  }
	}
