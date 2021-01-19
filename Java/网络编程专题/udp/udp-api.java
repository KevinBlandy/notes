——————————————————————
DatagramSocket		  |
——————————————————————
	# 接收和发送UDP数据,既是服务又是客户端
	# 构造函数
		DatagramSocket()
			* 随机端口
		DatagramSocket(int port)
			* 指定端口
		DatagramSocket(int port, InetAddress laddr)
		DatagramSocket(SocketAddress bindaddr)
			*  指定ip和端口
	
	# 方法
		setReuseAddress(true);
			* 是否允许端口重用

		bind(new InetSocketAddress(1024));	
			* 绑定端口

		receive(DatagramPacket p)
			*  接收一个udp报文

		send(DatagramPacket p)
			*  发送一个udp报文
		
		setSoTimeout(int timeout)
			* 设置超时时间
		
		close()
			* 关闭释放资源
		
——————————————————————
DatagramPacket		  |
——————————————————————
	# UDP数据包实体
	# 构造函数
		DatagramPacket(byte buf[], int length) 
		DatagramPacket(byte buf[], int offset, int length)
		DatagramPacket(byte buf[], int offset, int length,InetAddress address, int port)
		DatagramPacket(byte buf[], int offset, int length, SocketAddress address)
		DatagramPacket(byte buf[], int length,InetAddress address, int port)
		DatagramPacket(byte buf[], int length, SocketAddress address)
	
	# 方法
		..不多,都很简单
		

		