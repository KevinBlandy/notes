----------------------------
socketserver				|
----------------------------
	* 该模块是,网络服务器框架,其实就是对 socket 的一个封装
	* 模块类
		BaseServer(Server最顶层类)
			|-TCPServer
				|-ThreadingTCPServer(支持多线程的TCPServer)
				|-ForkingTCPServer(支持多进程的TCPServer,仅仅在unix有效)
				|-UnixStreamServer
				|-UDPServer
					|-ThreadingUDPServer(支持多线程的UDPServer)
					|-ForkingUDPServer(支持多进程的UDPServer,仅仅在unix有效))
					|-UnixDatagramServer
		BaseRequestHandler(Handle顶层类)
		ThreadingMixIn

----------------------------
BaseServer					|
----------------------------
	* 所有网络服务的顶层基类
	* 实例方法
		fileno()
			* 返回文件描述符
		handle_request()
			* 处理单个请求
		serve_forever(poll_interval=0.5)
			* 处理N个请求
			* 关键字参数
				poll_interval	# 默认的0.5,意思是每隔0.5描述,检查一下,是不是给我发送了 shutdown 的信号
		service_actions()
			* 
		shutdown()
			* 关闭服务

		server_close()
			* 关闭服务
		

----------------------------
socketserver-TCPServer		|
----------------------------
	* tcp 
	* 创建步骤
		1,创建请求处理类,继承于:socketserver.BaseRequestHandler 类
			class BaseRequestHandler:
				def __init__(self, request, client_address, server):
					self.request = request					# 客户端socket对象
					self.client_address = client_address	# 客户端地址 
					self.server = server					# 当前Server
					self.setup()							# 在处理之前执行
					try:
						self.handle()						# 执行逻辑方法
					finally:
						self.finish()						# 再处理之后执行

				def setup(self):
					pass

				def handle(self):
					pass

				def finish(self):
					pass

		2,覆写: handle() 方法,它是业务逻辑处理
		3,实例化 TCPServer Server
		4,执行 handle_request()/serve_forever(),准备接收客户端请求

	* TCPServer 实例创建
		TCPServer(server_address, RequestHandlerClass, bind_and_activate=True)
			* 绑定本地地址 & 监听端口, tuple
			* RequestHandlerClass 我们自定义的请求处理类
			* bind_and_activate
	
	* TCPServer 实例方法
		handle_request()
			* 仅仅处理一个请求,执行完毕后退出程序
		
		serve_forever(poll_interval=0.5)
			* 处理多个请求
			* 关键字参数
				poll_interval	# 
		
		server_close()
			* 关闭服务
	* demo
		import socketserver
		class Handler(socketserver.BaseRequestHandler):
			def handle(self):
				self.request			# 客户端socket对象
				# socket.socket fd=552, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM, proto=0, laddr=('127.0.0.1', 1024), raddr=('127.0.0.1', 3875)>
				
				self.client_address		# 客户端地址 
				# ('127.0.0.1', 3875)
				
				self.server				# 当前Server
				# <socketserver.TCPServer object at 0x029583D0>

		server = socketserver.TCPServer(('localhost',1024),Handler)
		server.serve_forever()	# 启动服务器
	
	* 每一个请求,都会创建一个 handle 实例,并且调用 handle() 方法执行逻辑处理
	* 仍然是单线程,所有请求全部是有由主线程(MainThread)执行
	* 如果需要使用多线程,则 可以创建: ThreadingTCPServer 实例
	* 如果需要使用多进程,则 可以创建: ForkingTCPServer 实例(仅仅在unix有效))

----------------------------
socketserver-UDPServer		|
----------------------------
	* udp
	* 跟Tcp一样,仅仅是创建Server实例的方法不一样
	* demo
		import socketserver
		class Handler(socketserver.BaseRequestHandler):
			def handle(self):
				self.request			# 客户端socket对象
				self.client_address		# 客户端地址 
				self.server				# 当前Server
		server = socketserver.ThreadingUDPServer(('localhost',1024),Handler)



	