------------------------
StandardSocketOptions	|
------------------------
	# nio包下系统预定义的socket选项	 StandardSocketOptions

		IP_MULTICAST_IF
		IP_MULTICAST_LOOP
		IP_MULTICAST_TTL
		IP_TOS
		SO_BROADCAST
		SO_KEEPALIVE
			* 表示对于长时间处于空闲状态的Socket是否要自动把它关闭
		SO_LINGER
			* 表示当执行Socket的close()方法时,是否立即关闭底层的socket
		SO_RCVBUF
			* 表示接收数据的缓冲区大小
		SO_REUSEADDR
			* 是否允许重用socket所绑定的本地地址
		SO_SNDBUF
			* 表示发送数据的缓冲区大小
		TCP_NODELAY
			* 是否立即发送数据
	
	# 设置
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);