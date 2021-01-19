----------------------------
RocketMQ-权重				|
----------------------------
	# Consumer 集群中的权重配置

	# Consumer 对象设置
		void setAllocateMessageQueueStrategy(AllocateMessageQueueStrategy allocateMessageQueueStrategy);

		
	# N种策略
		AllocateMessageQueueAveragely			
			* 其实是根据性能,做平均分配的负载均衡

		AllocateMessageQueueAveragelyByCircle
			* 轮询策略,abcd,挨着来.一条一条的分发
			* consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragelyByCircle());

		AllocateMessageQueueByConfig

		AllocateMessageQueueByMachineRoom
			