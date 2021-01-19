----------------------------
集群状态					|
----------------------------
	# 请求
		GET /_cat/health?
	
		epoch      timestamp cluster      status node.total node.data shards pri relo init unassign pending_tasks max_task_wait_time active_shards_percent
		1560149716 06:55:16  elaticsearch green           1         1      2   2    0    0        0             0                  -                100.0%

		epoch
		timestamp
		cluster
			* 集群名称

		status
			* 表示集群的状态, 使用英文的颜色词儿表示
				Green	:一切都很好
				Yellow	:所有数据都可用,但尚未分配一些副本
				Red		:某些数据由于某种原因不可用
		
		node.total
			* 集群中的节点数量
		
		node.data
		shards 
		pri
		relo
		init
		unassign
		pending_tasks
		max_task_wait_time
		active_shards_percent

----------------------------
查看集群中的节点信息		|
----------------------------
	# 请求
		GET /_cat/nodes?v

		ip        heap.percent ram.percent cpu load_1m load_5m load_15m node.role master name
		127.0.0.1           12          51   8                          mdi       *      KEVINBLANDY

		ip
		heap.percent
		ram.percent
		cpu
		load_1m
		load_5m
		load_15m
		node.role
		master
		name