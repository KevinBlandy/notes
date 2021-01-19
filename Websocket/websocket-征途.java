
# 对象编解码
	* Encoder
	* Decoder
	* 生命周期
		* 服务器启动的时候创建实例
		* 每个连接仅仅会创建一个实例
		* 在第一次连接的时候执行 init,在连接关闭的时候执行 destroy

# 消息处理器个数
	* 一个Endpoint 在注解式中对应类型的 message(二进制/文本/pong),只能有一个 @OnMessage 出现
	* 一个Endpoint 在编程式中对应类型的 message(二进制/文本/pong),只能有一个 MessageHandler出现( MesssageHandler,addMessageHandler(MessageHandler handler));

# WebSocket 消息通信和线程
	* 每一个新的连接,都会创建新的 Endpoint 实例
	* 每个WebSocket端点实例,在任意时刻都只能被一个线程调用
	* 当消息问分片形式到达的时候,WebSocket API 会保证以正确的顺序调用相应的端点,并且消息不会与其他的消息紊乱

# 重要的API
	* ServerEndpointConfig
		ServerEndpointConfig.Configurator
	

# Session

# 消息高级处理
	* ping/pong
	* 同步消息/异步消息
	* 消息批处理

# 获取HTTP组件
	* 自定义 ServerEndpointConfig.Configurator 类,覆写方法:modifyHandshake
	* 调用形参:HandshakeRequest 的API HttpSession getHttpSession(); 来获取到HTTP,Session
	* 往该Session里面添加的对象,最好都去实现监听器,这样的话,当Session失效,就可以通过监听器通知到WebSocket


	