-------------------------
消费模式
-------------------------
	# 被动消费，一般实现接口:  Consumer
	# 主动消费，使用 Channel 的 Api: 
		 GetResponse basicGet(String queue, boolean autoAck) throws IOException;
		

-------------------------
确认机制
-------------------------
	# autoAck 等于 true 表示自动确认
		* RabbitMQ 会自动把发送出去的消息置为确认，然后从内存(或者磁盘)中删除，而不管消费者是否真正地消费到了这些消息 。
	
	# 2个队列
		* 当 autoAck 参数置为 false ，对于 RabbitMQ 服务端而言 ，队列中的消息分成了两个部分
			一部分是等待投递给消费者的消息
			一部分是己经投递给消费者，但是还没有收到消费者确认信号的消息。
		
		* 如果 RabbitMQ 一直没有收到消费者的确认信号，并且消费此消息的消费者己经断开连接，则 RabbitMQ 会安排该消息重新进入队列，等待投递给下一个消费者
		* 当然也有可能还是原来的那个消费者
	
	# 超时时间
		* RabbitMQ 不会为未确认的消息设置过期时间，它判断此消息是否需要重新投递给消费者的唯一依据是'消费该消息的消费者连接是否己经断开'
		* 这么设计的原因是 RabbitMQ 允许消费者消费一条消息的时间可以很久很久
	

------------------------
备份交换机
------------------------
	# 备份交换机
		* 在消息不能匹配到投递队列的时候，就会投递给备份交换机
	
	# 设置备份交换机
		* exchangeDeclare(...) 的方法中，添加arguments参数 (优先级最高)
			Map<String, Object> properties = new HashMap<>();
			properties.put("alternate-exchange", "lost-message");  // 指定备份交换机，如果说消息不能成功投递，则投送到lost-message这个交换机
			channel.exchangeDeclare("myexchange", BuiltinExchangeType.DIRECT, false, false, false, properties);
	
		* 通过Policy方式设置
			rabbitmqctl set_policy AE "^myexchange$" `{"alternate-exchange": "lost-message"}`
	

	# 总结
		1. 果设置的备份交换器不存在，客户端和 RabbitMQ 服务端都不会有异常出现，此时消息会丢失
		2. 如果备份交换器没有绑定任何队列，客户端和 RabbitMQ 服务端都不会有异常出现，此时消息会丢失
		3. 果备份交换器没有任何匹配的队列，客户端和 RabbitMQ 服务端都不会有异常出现，此时消息会丢失
		4. 如果备份交换器和 mandatory 参数一起使用，那么 mandatory 参数无效
	


------------------------
过期时间
------------------------
	# 设置队列
		* 队列中的所有消息都遵守这个时间，当消息在队列中生存超过这个时间就会成为“死信”，消费者就不能消费了。
		* 在声明队列的时候添加参数: x-message-ttl(单位是毫秒)
			Map<String, Object> properties = new HashMap<>();
			properties.put("x-message-ttl", TimeUnit.HOURS.toMillis(1));  // ttl 设置为 1小时
			channel.queueDeclare("myqueue", false, false, false, properties);
		
		* 通过Policy方式设置(略)
		* 通过WEB控制台的HTTP接口设置(略)

			

	# 设置消息
		* 设置单独一条消息的过期时间，一个队列中的消息，可以有不同的ttl时间
		* 通过 Properties 设置，单位是毫秒，数据类型是字符串
			channel.basicPublish("myexchange", "order", true, false,  new BasicProperties.Builder()
					.expiration(TimeUnit.HOURS.toMillis(1) + "")  // 设置 TTL 为1小时
					.build(), message.getBytes(StandardCharsets.UTF_8));
			
	
	# 如果队列和消息都设置了TTL，则消息的实际TTL以两者之间较小的那个数值为准
	
	# 如果 ttl 设置为: 0, 则表示要求立即被消费，如果不能则会立即删除消息(经常发生于没有消费者的情况)

	# 2种设置方式，对于过期消息的处理不同
		1. 队列的方式，一旦消息过期就会立即从队列中抹去
		2. 消息的方式，即使消息过期也不会立即抹去，因为消息的过期判断是在“投递在消费者”之前判断的

		* 因为1的方式，队列中已过期的消息在队列的头部，MQ只要定期扫描，有过期消息就删除。
		* 第2种方式，每条消息可能TTL时间不同，如果要删除所有消息，需要遍历整个队列，很费劲。不如等到消费的时候再判断。

------------------------
死信队列
------------------------
	# DLX(Dead-Letter-Exchange), 一个消息在队列中变成死信后, 它就会被重新投递到死信交换机
	# 怎么变成死信
		1. 消息被消费者拒绝消费，并且设置了 requeue 为 false
		2. 消息TTL到期
		3. 队列达到了最大长度
	
	# 设置死信队列
		* 在创建队列的时候通过参数设置
			Map<String, Object> properties = new HashMap<>();
			properties.put("x-dead-letter-exchange", "my-queue-dead-exchange"); // 指定队列的死信交换机
			channel.queueDeclare("myqueue", false, false, false, properties);
		
			* 也可以为这个 DLX 指定路由键，如果没有特殊指定，则使用原队列的路由键
				properties.put("x-dead-letter-routing-key" , "dlx-routing-key");
	
		* Policy 方式设置(略)
	
	
	# 可以用这个实现一个延迟队列
		1. 新增队列, 设置TTL时间(可以按照需求细分为多个TTL队列:5秒,10秒,1分钟,1小时,1天), 这个队列不设置消费者
		2. 设置队列的死信队列 DLX
		3. 监听消费死信交换机队列即可
	

------------------------
优先级队列
------------------------
	# 优先级的队列有被优先消费的权利
		* 通过properties设置
			Map<String, Object> properties = new HashMap<>();
			properties.put("x-rnax-priority", "10"); // 设置优先级为10
			channel.queueDeclare("myqueue", false, false, false, properties);

	# 优先级的消息有被优先消费的权利
		* 通过 BasicProperties 设置
			channel.basicPublish(EXCHANGE_NAME, "order", true, false,  new BasicProperties.Builder()
					.priority(10) // 设置消息优先级
					.build(), message.getBytes(StandardCharsets.UTF_8));
			
		* 对于消费速度大于生产速度的时候，没啥意义，因为一生产就被消费了
		* 对于一条一条消息消费的队列，也没意义

		

------------------------
持久化问题
------------------------
	# Rabbitmq持久化分为3个
		1. 交换机持久化
		2. 队列持久化
		3. 消息持久化

	# 交换机的持久化
		* 声明交换机的时候设置 durable = true
		* 如果交换器不设置持久化，那么在 RabbitMQ 服务重启之后，相关的交换器元数据会丢失，不过消息不会丢失，只是不能将消息发送到这个交换器中了。
		* 对一个长期使用的交换器来说，建议将其置为持久化的。


	# 队列的持久化
		* 声明队列的时候设置 durable = true

		* 如果队列不设置持久化，那么在 RabbitMQ 服务重启之后，相关队列的元数据会丢失，此时数据也会丢失。
		* 队列的持久化能保证其本身的元数据不会因异常情况而丢失，但是并不能保证内部所存储的消息不会丢失。
		* 要确保消息不会丢失，需要将消息设置为持久化
	
	# 消息持久化
		* 通过 BasicProperties 设置持久化
			String message = "我是消息!" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			channel.basicPublish("my-exchange", "order", true, false,  new BasicProperties.Builder()
					.deliveryMode(2) // 设置持久化模式
					.build(), message.getBytes(StandardCharsets.UTF_8));
		
	# 总结
		1. 设置了队列和消息的持久化，当 RabbitMQ 服务重启之后，消息依旧存在。
		2. 单单只设置队列持久化，重启之后消息会丢失;单单只设置消息的持久化，重启之后队列消失，继而消息也丢失。
		3. 单单设置消息持久化而不设置队列的持久化显得毫无意义。
		
		* 性能和可靠性取舍：可以将所有的消息都设直为持久化，但是这样会严重影响 RabbitMQ 的性能(随机)。写入磁盘的速度比写入内存的速度慢得不只一点点。
	
	# 就算是设置了全部持久化，也有几个可能会丢失消息
		1. 消费者设置了自动ACK，在消费消息的过程中宕机，导致消息没有被成功消息，但是已经ACK被MQ删除
			* 手动消费可以解决

		2. MQ对消息的持久化不是立即进行的，不会对每条消息都进行同步存盘，而保存在系统缓存中，如果这个时候系统宕机，就会导致消息丢失
			* 通过publisher的confirm机制能够确保客户端知道哪些message已经存入磁盘
			* 可以用镜像队列，也就是主从，主节点挂掉后，从节点还可以继续提供服务
			* 一般关键的业务队列都要设置镜像队列
		

		







	
