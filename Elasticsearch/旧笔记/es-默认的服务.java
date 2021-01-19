
----------------------
查看服务的健康状态	  |
----------------------
	# /_cluster/health
		{
		  "cluster_name": "elasticsearch",
		  "status": "green",
				* 状态等级,以颜色划分:green,yellow,red
		  "timed_out": false,
		  "number_of_nodes": 1,
				* 节点数量
		  "number_of_data_nodes": 1,
		  "active_primary_shards": 1,
		  "active_shards": 1,
		  "relocating_shards": 0,
		  "initializing_shards": 0,
		  "unassigned_shards": 0,
		  "delayed_unassigned_shards": 0,
		  "number_of_pending_tasks": 0,
		  "number_of_in_flight_fetch": 0,
		  "task_max_waiting_in_queue_millis": 0,
		  "active_shards_percent_as_number": 100
		}

	# 账户状态
		green
			* 每个索引的primary shard和replica shard都是active状态的
		yellow
			* 每个索引的primary shard都是active状态的,但是部分replica shard不是active状态
		red
			* 不是所有索引的primary shard都是acvive状态的,部分索引有可能数据丢失
	


/ecommerce/_mapping/{index}