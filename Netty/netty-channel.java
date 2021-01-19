----------------------------
channel						|
---------------------------
	# Channel 生命周期
		channelRegistered	channel	注册到一个 EventLoop
		channelActive		channel	变为活跃状态(连接到了远程主机),现在可以接收和发送数据了
		channelInactive		channel	处于非活跃状态,没有连接到远程主机
		channelUnregistered	channel	已创建但未注册到一个 EventLoop	(或者从EventLoop中移除)
	
