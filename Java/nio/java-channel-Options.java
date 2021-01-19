------------------------------
StandardSocketOptions			|
------------------------------
	# 不允许被继承,不允许被创建实例
	# 在 NetworkChannel 接口的实例方法中使用
		<T> NetworkChannel setOption(SocketOption<T> name, T value) throws IOException;
		<T> T getOption(SocketOption<T> name) throws IOException;

	# 静态预定义对象
		SO_BROADCAST
		SO_KEEPALIVE
		SO_SNDBUF
		SO_RCVBUF
		SO_REUSEADDR
		SO_LINGER

		IP_TOS
		IP_MULTICAST_IF
		IP_MULTICAST_TTL
		IP_MULTICAST_LOOP

		TCP_NODELAY

