--------------------------------
nio								|
--------------------------------
	# 四种IO模式
		同步 阻塞		
		同步 非阻塞

		异步 阻塞
		异步 非阻塞

		select	->	poll	->	epoll
	
	# 相关文档
		https://docs.python.org/3/library/selectors.html

--------------------------------
NIO-select-模块函数				|
--------------------------------
	select(input,output,error,time)
		* 创建select实例对象
		* 参数
			input	:
			output	:
			error	:
			time	:隔多少时间轮询一次

--------------------------------
NIO-select-epoll				|
--------------------------------
import socket
import select

# 创建套接字
server = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
# 设置可以重复使用绑定的信息
server.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
# 绑定本机信息
server.bind(("0.0.0.0",7788))
# 变为被动
server.listen(10)
# 创建一个epoll对象
epoll = select.epoll()

# 注册事件到epoll中
# epoll.register(fd[, eventmask])
# 注意，如果fd已经注册过，则会发生异常
# 将创建的套接字添加到epoll的事件监听中
epoll.register(server.fileno(),select.EPOLLIN|select.EPOLLET)

connections = {}
addresses = {}

# 循环等待客户端的到来或者对方发送数据
while True:
    # epoll 进行 fd 扫描的地方 -- 未指定超时时间则为阻塞等待
    epoll_list = epoll.poll()
    # 对事件进行判断
    for fd,events in epoll_list:
        # 如果是socket创建的套接字被激活
        if fd == server.fileno():
            conn,addr = server.accept()
            print('有新的客户端连接%s'%str(addr))
            # 将 conn 和 addr 信息分别保存起来
            connections[conn.fileno()] = conn
            addresses[conn.fileno()] = addr
            # 向 epoll 中注册 连接 socket 的 可读 事件
            epoll.register(conn.fileno(), select.EPOLLIN | select.EPOLLET)
        elif events == select.EPOLLIN:
            # 从激活 fd 上接收
            recvData = connections[fd].recv(1024)
            if len(recvData) > 0:
                print('recv:%s'%recvData)
            else:
                # 从 epoll 中移除该 连接 fd
                epoll.unregister(fd)
                # server 侧主动关闭该 连接 fd
                connections[fd].close()
                print("%s---offline---"%str(addresses[fd]))
	




	