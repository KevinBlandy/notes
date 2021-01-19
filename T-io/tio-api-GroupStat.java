--------------------
GroupStat			|
--------------------
	# 专门用于统计数据的插件
	# 设置插件
		* 通过客户端和服务器端的 GroupContext 来设置
		ClientGroupContext
		ServerGroupContext

	# 子类
		ClientGroupStat
		ServerGroupStat

	# 属性
		private AtomicLong closed = new AtomicLong();
			* 关闭了多少连接

		private AtomicLong receivedPacket = new AtomicLong();
			* 接收到的消息包
		private AtomicLong receivedBytes = new AtomicLong();
			* 接收到的消息字节数

		private AtomicLong handledPacket = new AtomicLong();
			* 处理了的消息包数

		private AtomicLong sentPacket = new AtomicLong();
			* 发送了的消息包数
		private AtomicLong sentBytes = new AtomicLong();
			* 发送了的字节数

	# 方法
		getter & setter

--------------------
ClientGroupStat		|
--------------------
	# 客户端统计插件
	# 属性
		* 目前版本未添加新的属性

--------------------
ServerGroupStat		|
--------------------
	# 服务端统计插件
	# 属性
		private AtomicLong accepted = new AtomicLong();
			* 接受了多少连接