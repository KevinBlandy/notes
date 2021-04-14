------------------
EventBus
------------------
	# 每一个 Vert.x 实例都有一个单独的 Event Bus 实例
		* 通过 Vertx 实例的 eventBus方法来获得对应的 EventBus 实例
		* 应用中的不同组成部分可以通过 Event Bus 相互通信，无需关心它们由哪一种语言实现， 也无需关心它们是否在同一个 Vert.x 实例中
		* Event Bus构建了一个跨越多个服务器节点和多个浏览器的分布式点对点消息系统
		* Event Bus支持发布/订阅、点对点、请求-响应的消息传递方式
	
	# Event Bus的API很简单
		* 注册处理器
		* 注销处理器
		* 发送和发布(publish)消息
	
	# 核心概念
		* 寻址
			* 消息的发送目标被称作 地址(address)，字符串组成，建议使用.分隔命名空间
		
		* 处理器
			* 注册到某个地址上，监听这个地址的消息
			* 一个处理器可以，同一个地址可以注册许多不同的处理器，一个处理器也可以注册在多个不同的地址上
		
		* 发布/订阅消息
			* 消息将被发布到一个地址上。 发布意味着信息会被传递给所有注册在该地址上的处理器
		
		* 点对点消息传递 
			* 消息将被发送到一个地址上，Vert.x仅会把消息发给注册在该地址上的处理器中的其中一个。
			* 若这个地址上注册有不止一个处理器，那么Vert.x将使用 不严格的轮询算法 选择其中一个
		
		* 请求-响应消息传递
			* 点对点消息传递模式下，可在消息发送的时候指定一个应答处理器（可选）。
			* 当接收者收到消息并且处理完成时，它可以选择性地回复该消息。 若回复，则关联的应答处理器将会被调用。
			* 当发送者收到应答消息时，发送者还可以继续回复这个“应答”，这个过程可以 不断 重复。 
			* 通过这种方式可以在两个不同的 Verticle 之间建立一个对话窗口。
			* 这也是一个常见的消息传递模式：请求-响应 模式。

		* 尽力传输
			* Vert.x会尽它最大努力去传递消息，并且不会主动丢弃消息。这种方式称为 尽力传输(Best-effort delivery)。
			* 当 Event Bus 发生故障时，消息可能会丢失，应当编写具有幂等性的处理器， 并且发送者应当在故障恢复后重试
		
		* 消息类型
			* 默认允许任何基本/简单类型、String 类型、 buffers类型的值 作为消息发送。
			* Vert.x 中更规范且更通用的做法是使用 JSON 格式来发送消息
		
		* Vert.x会按照发送者发送消息的顺序，将消息以同样的顺序传递给处理器。

		* 消息对象，是：Message
			
	# Verticle 中的自动清理
		*  Verticle 中注册了 Event Bus 的处理器，那么这些处理器在 Verticle 被撤销（undeploy）的时候会自动被注销。

	# Codec
		* 消息编解码器
	
	# 集群
		* 消息总线的配置
			VertxOptions options = new VertxOptions()
				.setEventBusOptions(new EventBusOptions()
					.setSsl(true)
					.setKeyStoreOptions(new JksOptions().setPath("keystore.jks").setPassword("wibble"))
					.setTrustStoreOptions(new JksOptions().setPath("keystore.jks").setPassword("wibble"))
					.setClientAuth(ClientAuth.REQUIRED)
				);
			
				
			

