----------------------------
Redis Stream
----------------------------	
	# 参考文档
		
		https://docs.spring.io/spring-data/redis/reference/redis/redis-streams.html
		https://redis.io/commands/?group=stream
		https://redis.io/docs/data-types/streams/

----------------------------
Redis Stream
----------------------------	
	# 消费模式
		* 消费者直接消费：所有消费者都可以消费到消息，广播模式
		* 消费者组消费：所有消费组都可以消费同一条消息，但是消息只能被消费组中的一个消费者消费。
			* 同一个组中的消费者来说，就是负载消费，每个消费者消费的消息不一样
		
	
	# 消费者组
		* 创建消费组时指定从哪里开始消费
			* '$' 从创建时起开始消费
			* 'id' 从指定 ID 开始消费，创建后，就会从指定的 ID 开始消费
		
		* 消费 ACK 后，消息不会从 Stream 中删除
	

		
----------------------------
Redis Stream
----------------------------	