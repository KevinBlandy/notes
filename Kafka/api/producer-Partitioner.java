----------------------
Partitioner			  |
----------------------
	# 分区器接口
	# 抽象方法
		void configure(Map<String, ?> configs)
			* 获取配置信息以及初始化数据

		int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster);
			* 定义分区的分配逻辑

		void close();
	
	# 提供的实现类
		DefaultPartitioner