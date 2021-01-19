----------------------------
发送消息					|
----------------------------
	# RemoteEndpoint
		void setBatchingAllowed(boolean allowed) throws IOException;
			* 开启消息批处理
		boolean getBatchingAllowed();
			* 是否开启了消息批处理
		void flushBatch() throws IOException;
			* 强制刷出消息批处理中已经采集,未发送的消息
		void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
		void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;


----------------------------
发送消息-同步				|
----------------------------
	# 同步消息就不多解释了
	# RemoteEndpoint.Basic
		void sendText(String text) throws IOException;
		void sendBinary(ByteBuffer data) throws IOException;

		void sendText(String partialMessage, boolean isLast) throws IOException;
		void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException; // or Iterable<byte[]>

		OutputStream getSendStream() throws IOException;
		Writer getSendWriter() throws IOException;

		void sendObject(Object data) throws IOException, EncodeException;


----------------------------
发送消息-异步				|
----------------------------
	# RemoteEndpoint.Async
		long getSendTimeout();
			* 获取超时时间
		void setSendTimeout(long timeoutmillis);
			* 设置超时时间

		void sendText(String text, SendHandler handler);
		void sendBinary(ByteBuffer data, SendHandler handler);
		void sendObject(Object data, SendHandler handler);

		Future<Void> sendText(String text);
		Future<Void> sendBinary(ByteBuffer data);
		Future<Void> sendObject(Object data);

	
	# 通过 SendHandler 来推送异步消息
		* 这种方式需要'额外传递'一个回调对象 SendHandler 接口的实例,可以用 Lambda 表达式哟
		* 在异步消息发送后,会回调 SendHandler 接口实例的 onResult 方法,并且传入 SendResult 对象
		* SendHandler API
			void onResult(SendResult result);
		
		* SendResult API
			public Throwable getException();
				* 在异常的时候获取异常,如果未发生异常,这该值为 null
			public boolean isOK();
				* 在异常的时候该值 为 false,反之为 true
	
		* Demo
			session.getAsyncRemote().sendText("response", (sendResult) ->{
				if(sendResult.isOK()){
					System.out.println("消息发送成功");
				}else {
					Throwable throwable = sendResult.getException();
				}
			});

		* 如果是通过 SendHandler 来发送自定义对象,则发送的API(方法)在编码和传输之前就已经返回了,如果编码的时候发生异常,	onResult的isOk()为false,可以调用其getException();来获取异常


	# 通过 Future 来推送异步消息
		* 这种方式通过返回的 Future 对象来获取信息
		* Future API
			boolean cancel(boolean mayInterruptIfRunning);
				* 取消发送行为
			boolean isCancelled();
				* 是否已经成功取消掉发送行为
			boolean isDone();
				* 确定消息是否已经发送
			V get() throws InterruptedException, ExecutionException;
				* 该方法会阻塞,直到发送消息的动作完成
				* 如果在执行的过程中发生异常,会抛出'ExecutionException'异常,可以通过'ExecutionException'的getCause();方法获取
			V get(long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, TimeoutException;
				* 同上,不过该方法有个超时时间

		* 如果是通过 Future 来发送自定义对象,则发送的API(方法)在编码和传输之前就已经返回了,如果编码的时候发生异常,该异常会由 ExecutionException 进行封装,通过 Future 的 get();方法来获取
		* Demo
	
	# 两种方式(SendHandler,Future)的对比,总结
		Future
			* 可以通过取消消息传输来干预传输过程
			..。
		SendHandler
			* 不可以干预传输过程
			...
		
	# 异步发送超时
		* RemoteEndpoint.Async
			void setSendTimeout(long timeoutmillis);
		* 如果该值为任何非正数,则表示没有超时时间
		* 在超时的清空下
			SendHandler	-> 产生 TimeoutException 
			Future		-> get();方法抛出 ExecutionException
		
		
	
----------------------------
发送消息API总结				|
----------------------------
	# 连接消息
		* ping
			void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
		* pong
			void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
	
	# 同步消息
		* 文本
			void sendText(String text) throws IOException;
			void sendText(String partialMessage, boolean isLast) throws IOException;
			Writer getSendWriter() throws IOException;
		
		* 二进制
			void sendBinary(ByteBuffer data) throws IOException;
			void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException; // or Iterable<byte[]>
			OutputStream getSendStream() throws IOException;
			

			void sendObject(Object data) throws IOException, EncodeException;
	
	# 异步消息
		* SendHandler 模式
			void sendText(String text, SendHandler handler);
			void sendBinary(ByteBuffer data, SendHandler handler);
			void sendObject(Object data, SendHandler handler);
		
		* Future 模式
			Future<Void> sendText(String text);
			Future<Void> sendBinary(ByteBuffer data);
			Future<Void> sendObject(Object data);