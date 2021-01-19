
------------------------
cat	- api				|
------------------------
	# 系统提供的一套快速cat - api
	# 显示额外的列头部信息
		* 在api地址后面添加参数: ?v

/_cat/health
	# 快速的查看服务健康状况
		epoch      |timestamp|cluster		|status |node.total|node.data|shards|pri|relo|init|unassign|pending_tasks|max_task_wait_time|active_shards_percent
		1529503744 |22:09:04 |elasticsearch |green  | 1        |1        |1     |1  |0   |0   |0       | 0           | -                |100.0%

		时间戳 时间 集群名称

/_cat/indices
	# 快速的查看索引信息
		health status index   uuid                   pri rep docs.count docs.deleted store.size pri.store.size
		green  open   .kibana TiiXPteWSB6eLJ0GwTbMsQ   1   0          1            0        4kb            4kb