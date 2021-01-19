--------------------------------
socket							|
--------------------------------

--------------------------------
socket-模块属性					|
--------------------------------
	* 网络层属性
		socket.AF_UNIX		# 本地(Unix/Linux系统才有该属性,适用于不同进程之间的通信)
		socket.AF_INET      # IPV4
		socket.AF_INET6     # IPV6
	
	* 传输层属性
		socket.SOCK_STREAM  
			* tcp

		socket.SOCK_DGRAM   
			* udp

		socket.SOCK_RAW     
			* 原始套接字(普通套接字不能处理ICMP,IGMP等报文,而它可以)
			* 它也可以处理特殊的IPV4报文
			* 利用该套接字,可以通过 IP_HDRINCL 套接字选项,篡改IP头(伪造IP地址)
		
		socket.SOCK_RDM
			* 可靠的UDP,保证交付消息,但是不保证交付顺序
	
	* 协议
		socket.IPPROTO_TCP = 6
		socket.IPPROTO_IP = 0
		socket.IPPROTO_UDP = 17
		socket.IPPROTO_ICMP = 1
		socket.IPPROTO_RAW = 255
		
	
	* socket选项
		socket.IP_HDRINCL
		socket.SOL_SOCKET
			socket.SO_BROADCAST
			socket.SO_REUSEADDR
	
--------------------------------
socket-模块方法					|
--------------------------------
	socket.socket socket()
		* 实例化socket对象
		* 初始化参数
			 family=AF_INET		# 地址簇
			 type=SOCK_STREAM	# 连接类型(tcp/udp),默认tcp
			 proto=0			# 子协议
			 fileno=None		# 未知
	
	str gethostname()
			* 获取本地主机名
	
	float getdefaulttimeout()
	None setdefaulttimeout()
			* 读取/设置全局的连接超时时间
		
	socketpair()
	fromfd()
	fromshare()
	gethostname()
	gethostbyname()
	gethostbyaddr()
	getservbyname()
	getprotobyname()
		* 根据协议名称获取协议'对象'

	ntohs()
	ntohl()
	htons()
	htonl() 
	inet_aton()
	inet_ntoa()

	socket create_connection(address, timeout=_GLOBAL_DEFAULT_TIMEOUT,source_address=None) 


--------------------------------
socket-socket					|
--------------------------------
	
	# about client
		None connect(tuple)
			* 作为tcp客户端连接远程地址
			* 连接异常,会抛出
			* tuple,第一个参数是字符类型的IP,第二个参数是int类型的端口

		int connect_ex()	
			* connect()函数的扩展版本,出错时返回出错码,而不是抛出异常
	
	# about server
		None class bind(tuple)
			* 绑定本地信息
			* tuple,第一个参数是字符类型的IP,第二个参数是int类型的端口
		
		None listen(timeout)
			* 开始监听
			* timeout,是指操作系统可以挂起的最大连接数量
			* 该值至少为1,大部分应用程序设为5就可以了
		
		setblocking(flag)	
			* 如果flag为0,则将套接字设为非阻塞模式
			* 否则将套接字设为阻塞模式(默认)
			* 非阻塞模式下,如果调用 recv() 没有发现任何数据,或 send() 调用无法立即发送数据,那么将引起 socket.error 异常
		
		bool getblocking()
			* 获取当前的IO模式，是否是阻塞的。
		
		tuple accept()
			* 阻塞线程,直到有客户端的连接,就会返回 tuple
			* 第一个元素就是,客户端的 socket 对象,第二个元素,又是一个元组
				(	<
						socket.socket 
						fd=276, 
						family=AddressFamily.AF_INET,	# 客户端IP & 端口
						type=SocketKind.SOCK_STREAM,	# TCP
						proto=0,				
						laddr=('127.0.0.1', 1024),		# 本地服务器IP & 端口
						raddr=('127.0.0.1', 50142)		# 客户端IP & 端口
					>, 
					('127.0.0.1', 50142)				# 客户端IP & 端口
				)

	# common
		None setsockopt()
			* 设置 socket 的一些属性(例如:端口重用)
			* demo
				setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1) 
					# 设置端口可以重用
				setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST,1)
					# 设置UDP为广播模式

		int send(bytes)
			* 发送数据到目标,不一定一次性就把数据发送完毕,应该使用 sendall()
			* 返回已经发送的字节长度
		
		bytes recv(length)
			* 接收tcp传输过来的字节数据,一次也许接收不完,应该循环接收
			* length,决定了你要接收多长的数据
		
		int sendto(data,tuple)	
			* 发送UDP数据
			* data,是二进制数据,tuple,代表远程地址,形式为(ipaddr,port)的元组
			* 返回值是发送的字节数

		tuple recvform()	
			* 接收UDP数据,与 recv()类似 但返回值是(data,address)的元组
			* 其中 data 是包含接收数据的字符串,address是发送数据的套接字地址
			* (b'Hello,Python', ('127.0.0.1', 61207))
		
		None sendall()	
			* 完整发送TCP数据,完整发送TCP数据,就是循环的 send()
			* 将string中的数据发送到连接的套接字,但在返回之前会尝试发送所有数据
			* 成功返回None,失败则抛出异常
		
		None close()
			* 关闭与远程服务器的TCP连接
		
		None shutdown(socket.SHUT_WR)
			* 表示数据发送/接收完毕
			* 表示数据IO完毕
			* 参数 
				SHUT_RDWR	关闭读写，即不能使用send/write/recv/read等
				SHUT_RD		关闭读，即不能使用read/recv等
				SHUT_WR		关闭写功能，即不能使用send/write等

		tuple getpeername()	
			* 返回连接套接字的远程地址
			* 返回值通常是元组(ipaddr,port)
		
		tuple getsockname()
			* 返回套接字自己的地址
			* 是一个元组(ipaddr,port)
		
		setsockopt(level,optname,value)	
			* 设置给定套接字选项的值
		
		getsockopt(level,optname[.buflen])	
			* 返回套接字选项的值
		
		settimeout(timeout)	
			* 设置套接字操作的超时期
			* timeout是一个浮点数,单位是秒
			* 值为None表示没有超时期
			* 一般,超时期应该在刚创建套接字时设置，因为它们可能用于连接的操作(如 connect())

		gettimeout()	
			* 返回当前超时期的值,单位是秒,如果没有设置超时期,则返回None
		
		fileno()	
			* 返回套接字的文件描述符
			* Demo
				print(socket.socket())
				<socket.socket fd=408, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM, proto=0>
				* fd 值 就是socket的 fileno()
		
		makefile()	
			* 创建一个与该套接字相关连的文件
	
		
