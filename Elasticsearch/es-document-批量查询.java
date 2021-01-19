----------------------------
mget 批量查询				|
----------------------------
	# 如果需要检索100数据,使用单次请求,那么就会进行100次的网络请求,消耗严重,如果使用批量查询,那么仅需要一次网络开销即可
	# mget 相当重要
		* 当一次性要检索多条数据的时候,一定要使用mget,减少网络开销可以大幅度提升性能

	# mget 基本语法
		GET /_mget
		{
		  "docs":[{
			  "_index":"user",			//通过 _index 指定index
			  "_id":1					//通过 _id 指定 id
			},{
			  "_index":"user",
			  "_id":2
			}]
		}
		
		* doc 是个数组,可以定义多个条件
		* 响应数据中的docs也是一个数组,返回的是符合条件的数据
			{
			  "docs": [
				{
				  "_index": "user",
				  "_id": "1",
				  "_version": 3,
				  "found": true,
				  "_source": {
					  ...
				  }
				},
				{
				  "_index": "user",
				  "_id": "2",
				  "_version": 1,
				  "found": true,
				  "_source": {
					  ...
				  }
				}
			  ]
			}
	
	
	# 如果批量查询的document,是在同一个index,可以使用另外两种语法
		GET /<index>/_mget
		{
			"docs":[{
				"_id":1
			}]
		}
		* 仅仅需要在docs中指定_id,因为url已经指定了index

		GET /<index>/_mget
		{
			"ids":[1,2]
		}
		* 如果只是根据id检索, 可以这样写, json格式更简单,只要定义ids字段即可
	
	# 也可以使用部分功能的检索参数
		_source
		_source_includes
		_source_excludes
		routing
