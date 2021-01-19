--------------------------------
WebSocket-生命周期				|
--------------------------------
	# 打开事件
	# 消息事件
	# 错误事件
	# 关闭事件

--------------------------------
WebSocket-注解					|
--------------------------------
	@OnOpen
		# 参数
			Session
			EndpointConfig
			@PathParam

	@OnMessage
		# 可用接收消息的参数
			String
				* 处理文本信息
			byte[]
				* 处理二进制消息
			ByteBuffer
				* 处理二进制消息
			PongMessage(接口)
				|-WsPongMessage(实现)
				* 处理pong消息
			InputStream
				* 二进制流
			Reader
				* 文本流
			Object
				* 自己封装的消息对象('高级部分'),需要被编码
			Integer,Double....
				* 基本数据类型的包装类,会被内部预定义的解析器来完成解析
			boolean isLast
				* 当消息被分片的时候,如果后面还有消息,则该值为 false,当前消息为最后一个消息,该值为 true	
				* '该参数单独存在没有任何意义,必须且只能与':String,byte[],ByteBuffer 这仨中任意参数存在才有消息分片的意义

		# 其他参数
			Session
			EndpointConfig

		# 返回类型
			* 当方法有返回值的时候,websocket的实现,立即把方法的返回值作为消息返回给客户端
			String
				* 给客户端文本数据
			byte
				* 给客户端二进制数据
			ByteBuffer
				* 给客户端二进制数据
			Integer,Double....
				* 基本数据类型的包装类,会被内部预定义的解析器来完成解析
			Object
				* 自己封装的对象('高级部分'),需要被解码

    @OnError
		# 参数
			Throwable
				* 发生的异常对象
	
	@OnClose
		# 参数
			CloseReason
				* 描述关闭原因,等等大量的信息
			

--------------------------------
WebSocket-编程式				|
--------------------------------
	# 继承 Endpoint 抽象类
		* 只需要覆写 onOpen 方法即可,其他两个其实是空实现

	public abstract void onOpen(Session session, EndpointConfig config);

	public void onClose(Session session, CloseReason closeReason) {|

	public void onError(Session session, Throwable thr) {}