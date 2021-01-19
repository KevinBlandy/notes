-----------------
集群			 |
-----------------
	# 集群的本质, 是所有的节点都通过一个数据源来实现集群的

	# 配置
		org.quartz.scheduler.instanceName = MyClusteredScheduler
			* 集群所有的节点, 该属性名称必须一致

		org.quartz.scheduler.instanceId = AUTO
			* 集群的实例ID, 设置为自动
		
