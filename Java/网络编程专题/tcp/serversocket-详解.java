------------------------
ServerSocket			|
------------------------
	# 端口绑定
		* 如果是绑定  1 - 1023端口,必须使用管理器权限执行程序,否则可能抛出异常
		* 如果构造参数为0,那么系统随机选择端口进行绑定
			ServerSocket(0)

	# 设置客户端请求队列的长度,默认为50
		* 构造设置
			ServerSocket(1024,50)
		* 如果链接数据量超过,新的客户端连接就会抛出异常
		* ServerSocket 构造函数中的 backlog 参数来限制队列长度,会覆盖系统限定的队列最大长度
		* 在几个情况下,仍然会采用操作系统限定的队列最大长度
			* backlog 参数的值大于操作系统限定的队列的最大长度
			* backlog 参数的值小于或等于0
			* ServerSocket 构造方法中没有设置 backlog 参数

	# 绑定主机地址
		* 一般主机只有一个地址,默认就会绑定它
		* 如果主机具备多个网卡,一个网卡连接局域网网(192.168.3.4),一个网卡连接互联网(59.110.167.11)
		  如果你的程序,只想给局域网用户访问,则需要绑定ip到局域网
			new ServerSocket(1024,50,InetAddress.getByName("192.168.3.4"));
		* 如果你绑定的地址是:0.0.0.0,则系统会随机绑定到一个ip地址
			new ServerSocket(1024,50,InetAddress.getByName("0.0.0.0"));

	# 无参的构造方法
		* 有一个无參的构造方法
			ServerSocket();
		* 使用这种方式创建了实例对象,那么还需要通过 bind() api 来完成绑定
			 void bind(SocketAddress endpoint)
			 void bind(SocketAddress endpoint, int backlog)
		* 这个无參构造的作用是,允许服务器在绑定到指定端口之前,进行一些 Socket 的选项设置
		  因为有些设置,一旦服务器与端口绑定了,就不能再更改了

		* 设置端口重用demo
			ServerSocket serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress("0.0.0.0",1024));
			
		
	# 接收客户端的连接
		* 通过api accept() 来获取新的连接,该api会一直阻塞,直到新的连接
		* 如果客户端与服务器断开了连接,服务器端会抛出一个:SocketException,这个异常应该被捕获,不能影响服务端继续为其他客户端提供服务
	
	# 关闭 ServerSocket
		* 服务器一般不会关闭,24H * 7 都对外提供服务
		* 在一些情况下,希望及时释放服务器的端口,以便让其他的程序占用,可以执行 close() api
		* isBound() api仅仅判断是否已经绑定了端口,即时 ServerSocket 已经被关闭
		* isClosed() api仅仅判断是否关闭,只有在执行了 close() api 后,该方法返回 true.
		* 确定一个 ServerSocket 已经绑定了端口,并且没有被关闭
			boolean isService = serverSocket.isBound() && !serverSocket.isClose();
	
	# 获取 ServerSocket 的信息
		* 获取服务器绑定的ip地址/端口
			InetAddress getInetAddress()
			int getLocalPort()
		
	# ServerSocket 选项
		* ServerSocket 具备三个选项
			1,SO_TIMEOUT
				* 表示等待客户端链接的超时时间
			2,SO_REUSEADDR
				* 表示是否允许地址重复
			3,SO_RCVBUF
				* 表示接收数据的缓冲区大小
		* SO_TIMEOUT
			* accept()等待客户端连接的超时时间,单位是毫秒
			* api
				void setSoTimeout(int timeout)
				int getSoTimeout()
			* 如果该值为:0,则永远不是超时,这也是默认值
		
		* SO_REUSEADDR
			* 不多解释
		
		* SO_RCVBUF
			* api
				void setReceiveBufferSize (int size)
				int getReceiveBufferSize()
			* 设置缓冲区大小,必须在绑定端口之前进行设置才有效
			* setReceiveBufferSize() 方法,相当于对所有由 accpet()方法返回的 Socket 设置接收数据的缓冲区大小

	# 设置连接时间,延迟,带宽的相对重要性
		* api
			void setPerformancePreferences(int connectionTime,int latency,int bandwidth)
		* 不多解释
	

		

			