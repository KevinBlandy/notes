----------------------------
检查连接					|
----------------------------
	# ping,Pong
	# 最多可以携带 125 字节大小的数据,事实上,响应ping消息的pong消息中的数据量大小,必须与ping消息相同
	# 用于测试连接是否正常,还可以用于'保持连接的活跃',因为一旦超时,连接就会被关闭
		long session.getMaxIdleTimeout();
	
	# Ping消息的处理
		* 接收Ping
			* 在收到ping消息的时候,WebSocket实现会默认的响应pong消息,不需手动的去监听ping消息以及手动的进行pong消息响应

		* 发送Ping消息
			void RemoteEndopoint.sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
		
	
	# pong消息的处理
		* 注解式接收
			@OnMessage
			public void catchPong(PongMessage pongMessage){
				//TODO
			}
		
		* 编程式接收
			PongMessage
			session.addMessageHandler(new MessageHandler.Whole<PongMessage>() {
				public void onMessage(PongMessage message) {
					//TODO
				}
			});
		
		* 发送pong消息
			* 主动的推送pong消息,其实是作为一类单向心跳
			* 是保持连接的一种方式,或者用于嗅探失效的连接
			* 'WebSocket中定义,pong消息的另一端没有义务要进行主动pong消息应答,所以当主动的发送了pong消息,一般别指望有响应了'
			* '当然,你也可以去监听pong消息(上面俩方法),然后手动的调用API响应'
				void RemoteEndopoint.sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;

----------------------------
消息批处理					|
----------------------------
	# 在发送消息的时候,应用认为已经完成了消息的发送.其实没有,消息被采集起来
	# 当采集的消息到达某个临界点后,就会按照顺序推送出去
	# 这种方式可以提高一点点的性能,如果你想要该技术,需要调用一个API
		* RemoteEndpoint
			void setBatchingAllowed(boolean allowed) throws IOException;
		* 并且需要保证的是,WebSocker的实现(Servlet容器)支持消息的批处理,不然就算是调用了也没用
	
	# 相关的API
		* RemoteEndpoint
			void setBatchingAllowed(boolean allowed) throws IOException;
				* 开启消息批处理
			boolean getBatchingAllowed();
				* 是否开启了消息批处理
			void flushBatch() throws IOException;
				* 强制刷出消息批处理中已经采集,未发送的消息

	# 当使用消息批处理的时候,很大性能改进,依赖于以下因素
		* 应用发送消息的大小
		* 应用发送消息的频率
		* 应用发送消息的特定网络
		* 接收消息的对等端WebSocket实现的性能特征
	
	# 在异步消息遇到批处理的时候
		* SendHandler的回调和Future的get()方法在消息被写入批处理的时候就返回
		* 它可能实际上已经把消息写入底层连接,也有可能没写入

----------------------------
缓冲,消息分片,数据帧		|
----------------------------
	# WebSocket协议可以把文本信息,二进制消息拆解为一系列的小数据帧
	# 使得可以基于自身能力和网络能力在WebSocker连接上进行实际的发送
	# 注解式
		@OnMessage		//分片消息
		public void (String message,boolean isLast){}
			* 通过该方法获取的数据片段,跟发送的片段有可能不一样
			* 收到的片段,也许是多个片段组成的片段,只要是最后一个 isLast 就是 true
		@OnMessage		//非分片消息
		public void (String message){}
			* 如果通过该消息来获取数据片段
			* WebSocket实现没有选择,只能是在内部接收完毕所有的片段后,来调用该方法
	
	# 编程式
		MessageHandler.Partial<T>		//分片消息
		void onMessage(T messagePart, boolean last);
			* 同上
		MessageHandler.Whole<T>			//非分片消息
		void onMessage(T messagePart);
			* 同上
	
	# 控制消息缓冲区的大小
		* 第一种方式,对于编程式和注解式都起作用,通过Session的API来完成
			Session
				int session.getMaxBinaryMessageBufferSize();
					* 传入的二进制消息的最大长度
				int session.getMaxTextMessageBufferSize();
					* 传入的文本消息的最大长度
		
		* 第二种方式,对于注解式作用
			@OnMessage 注解的一个属性
				public long maxMessageSize() default -1;
			
			* 该值默认为-1,表示没有限制,单位是字节

----------------------------
保证消息传递				|
----------------------------
	# WebSocket协议并不能保证所发送的WebSocket消息能够被连接的另一端正确的接收
	# 在大多数的情况下,传输消息中发生错误,WebSocket实现将会知道,但是消息并没有传递给另一端,且没有任何错误给出的情况也有发生
	# 在编程时处理某种回执,表示成功的接收到的发送者的消息
	# 在 @OnMessage 方法上添加返回值



