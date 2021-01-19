--------------------
document - 更新		|
--------------------
	# 更新时, Elasticsearch都会删除旧文档, 然后一次性对应用了更新的新文档编制索引

--------------------
document - 更新		|
--------------------
	# 全量替换更新(PUT)
		PUT /<index>/_doc/<id>
		{...}

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 2,
		  "result" : "updated",
		  "_shards" : {
			"total" : 2,
			"successful" : 1,
			"failed" : 0
		  },
		  "_seq_no" : 1,
		  "_primary_term" : 1
		}
		
		* 相当于执行了一次覆盖
		* 如果id已经存在,那么原来的document不会被立即删除,而是会被标记为: delete
		* 当es中数据越来越多的时候,es会在后台自己动的把标记为:delete 的document物理删除掉
		* _version 始终会 +1
	
	# 强制更新(全部更新)
		POST /<index>/_doc/<id>
		{...}

		* 请求体需要提交所有字段,不存在的字段会被删除
		* 不管本次提交,是否有成功修改字段,result值永远为:'updated'
		* 不管是有修改,_version字段必会加1
		* 可以理解为强制更新
		* 如果指定id的数据不存在(或者未指定id), 则会创建, 则 "result" = "created"
	
	# 非强制更新(部分更新)
		POST /<index>/_update/<id>
		{"doc":{...}}

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 5,
		  "result" : "noop",
		  "_shards" : {
			"total" : 0,
			"successful" : 0,
			"failed" : 0
		  }
		}


		* 该种方式,提交的JSON体有所变化
			{
				"doc":{
					//需要修改的字段
				}
			}

		* 可以仅仅提交更新需要更新的字段
		* 如果本次提交未修改数据的话,那么result字段值为:'noop',并且没有:'_seq_no'和'_primary_term'字段,
			- noop更新
		
		* 只有在数据有修改的时候,version +1
		* 可以理解为非强制更新
		* partial update(部分更新)
	
	# 也支持使用脚本语言更新
		POST /<index>/_update/<id>
		{
		  "script" : "ctx._source.age += 5"
		}

		* 具体看脚本
	
	
--------------------
使用查询API更新		|
--------------------
	# 使用 _update_by_query 对匹配到的文档进行更新
		POST /<index>/_update_by_query
		{
		  "query": { 
			"term": {
			  "<filed>": "<value>"
			}
		  }
		}
		* 把index中的所有field的值都修改为value
	
	# 此更新通过查询API也支持不分功能查询参数
		refresh
		wait_for_completion
		wait_for_active_shards
		timeout
		scroll
	
	# 更新操作, 支持的查询功能参数
		retry_on_conflict
			* 在更新的get和indexing阶段之间, 另一个进程可能已经更新了同一文档
			* 默认情况下, 更新将因版本冲突异常而失败, 该retry_on_conflict 参数控制在最终抛出异常之前重试更新的次数

		routing
		timeout
		wait_for_active_shards
		refresh
		_source
			* 是否以及如何在响应中返回更新的数据
			* 默认情况下, 不会返回更新的数据

		version
			* 更新API不支持内部版本以外的版本控制

--------------------
使用Task API		|
--------------------
	# Task API是新功能, 目前仍视为测试版功能
