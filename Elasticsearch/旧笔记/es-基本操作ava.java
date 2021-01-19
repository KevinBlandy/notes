------------------------
基本操作				|
------------------------

------------------------
索引的操作				|
------------------------
	# 新建
		PUT		/{index_name}	
		{
			"acknowledged": true,
			"shards_acknowledged": true,
			"index": "test_index1"
		}
	
	# 删除
		DELETE	/{index_name}
		{
			"acknowledged": true
		}

------------------------
Document-新增			|
------------------------
	PUT /{index_name}/{type_name}/{id}
	{
	  "_index": "[index_name]",			//index名称
	  "_type": "[type_name]",			//type名称
	  "_id": "[id]",					//document的id
	  "_version": 1,					//版本号
	  "result": "created",				//执行结果
	  "_shards": {						
		"total": 2,	
		"successful": 1,
		"failed": 0
	  },
	  "_seq_no": 0,
	  "_primary_term": 1
	}

------------------------
Document-更新			|
------------------------
	# 更新-1
		POST /{index_name}/{type_name}/{id}	
		{
		  "_index": "test_index",
		  "_type": "product",
		  "_id": "1",
		  "_version": 2,			
		  "result": "updated",
		  "_shards": {
			"total": 2,
			"successful": 1,
			"failed": 0
		  },
		  "_seq_no": 1,
		  "_primary_term": 1
		}

		* 请求体直接提交需要修改的字段即可
		* 不管本次提交,是否有成功修改字段,result值永远为:'updated'
		* _version字段必会加1
		* 可以理解为强制更新
	
	# 更新-2
		POST /{index_name}/{type_name}/{id}/_update
		* 该种方式,提价的JSON体有所变化
			{
				"doc":{
					//需要修改的字段
				}
			}
		* 如果本次提交未修改数据的话,那么result字段值为:'noop',并且没有:'_seq_no'和'_primary_term'字段,version也不会+1
		* 可以理解为非强制更新,仅仅更新需要更新的字段


	# 替换
		PUT  /{index_name}/{type_name}/{id}	
	
	# 对比
		* 直接替换的话,version会被重置为1,更新的话,version会加1
		* 更新情况下,result字段值为"updated"
		* 直接替换不好,替换的情况下,必须带上所有的数据
		* 更新则,仅仅需要提供更新的字段即可

------------------------
Document-删除			|
------------------------
	DELETE /{index_name}/{type_name}/{id}
	{
	  "_index": "test_index",
	  "_type": "product",
	  "_id": "1",
	  "_version": 24,
	  "result": "deleted",
	  "_shards": {
		"total": 2,
		"successful": 1,
		"failed": 0
	  },
	  "_seq_no": 23,
	  "_primary_term": 1
	}

	* 如果未删除成功(Document不存在),那么result值为: not_found

------------------------
Document-检索			|
------------------------
	GET /{index_name}/{type_name}/{id}
	{
	  "_index": "[index_name]",
	  "_type": "[type_name]",
	  "_id": "1",
	  "_version": 1,
	  "found": true,		//是否成功检索到
	  "_source": {			//数据
		"id": 1,
		"name": "java",
		"price": 30,
		"producer": "this is producer",
		"tags": [
		  "t1",
		  "t2"
		]
	  }
	}
	