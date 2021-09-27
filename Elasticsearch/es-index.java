-----------------------
index				   |
-----------------------

-----------------------
index	- 创建		   |
-----------------------
	# 请求
		PUT /<index> 
		{
			...config
		}
	
		{
			"acknowledged" : true,
			"shards_acknowledged" : true,
			"index" : "<index>",
			"settings": {	
				"number_of_shards": 5			// 设置 primary shard的数量
				"number_of_replicas": 1			// 设置 relicas shard的数量
			}
		}

-----------------------
index	- 删除		   |
-----------------------
	# 请求
		DELETE /<index>?pretty
	
		{
		  "acknowledged" : true
		}
	
	# 一次性删除所有索引	
		DELETE /_all

-----------------------
index	- 关闭/打开	   |
-----------------------
	# 关闭索引
		* 索引被关闭后不能进行读取和写入，但是数据不会被删除（类似于归档）
		* 可以选择开放索引，那么又可以继续读取和写入了
	
	# 关闭
		POST /<index>/_close
	
	# 打开
		POST /<index>/_open

----------------------------
查看集群中索引信息			|
----------------------------
	# 请求
		GET /_cat/indices?v

	# 响应
		health status index                uuid                   pri rep docs.count docs.deleted store.size pri.store.size
		green  open   .kibana_task_manager cEN_d-7TTwiYBn7xE1LkpQ   1   0          2            0     12.7kb         12.7kb
		green  open   .kibana_1            RpX3IRZEQKag3H8xyWq4eQ   1   0          4            0     17.6kb         17.6kb
		yellow open   customer             R4mXIa-_QKiDngr7bKG-OA   1   1          0            0       230b           230b

		health
		status
		index
		uuid
		pri
		rep
		docs.count
		docs.deleted
		store.size
		pri.store.size