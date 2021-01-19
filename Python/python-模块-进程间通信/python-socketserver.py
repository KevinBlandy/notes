---------------------------
socketserver				|
---------------------------
	* socketserver 模块是对 socket 的封装
	* 有点事件驱动的意思
	* 类体系
		BaseServer(Server最顶层类)
			|-TCPServer
				|-ThreadingTCPServer(支持多线程的TCPServer)
				|-ForkingTCPServer(支持多进程的TCPServer,仅仅在unix有效)
				|-UnixStreamServer
				|-UDPServer
					|-ThreadingUDPServer(支持多线程的UDPServer)
					|-ForkingUDPServer(支持多进程的UDPServer,仅仅在unix有效))
					|-UnixDatagramServer
				|-HTTPServer
					|-WSGIServer
		BaseRequestHandler(Handle顶层类)
			|-StreamRequestHandler
				|-BaseHTTPRequestHandler
					|-WSGIRequestHandler
	
	* BaseServer 的一些方法,在有特殊需求的时候可以自己覆写
		server_bind()
			* 绑定监听
	
	* BaseServer 的一些属性
		allow_reuse_address
			* 默认为 False,是否允许端口复用
			* 该属性是类属性,踩过坑....
				socketserver.UDPServer.allow_reuse_address = True

---------------------------
tcp							|
---------------------------
	import socketserver
	class Handler(socketserver.BaseRequestHandler):
		def handle(self):
			self.request			# 客户端socket对象
			# socket.socket fd=552, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM, proto=0, laddr=('127.0.0.1', 1024), raddr=('127.0.0.1', 3875)>
			
			self.client_address		# 客户端地址 
			# ('127.0.0.1', 3875)
			
			self.server				# 当前Server
			# <socketserver.TCPServer object at 0x029583D0>

	server = socketserver.ThreadingTCPServer(('localhost',1024),Handler)
	server.serve_forever()

---------------------------
udp							|
---------------------------
	import socketserver
	class Handler(socketserver.BaseRequestHandler):
		def handle(self):
			self.request			# 客户端socket对象,第一个参数就是udp的数据
			# (b'Hello,Python', <socket.socket fd=200, family=AddressFamily.AF_INET, type=SocketKind.SOCK_DGRAM, proto=0, laddr=('127.0.0.1', 3025)>)
			
			self.client_address		# 客户端地址 
			# ('127.0.0.1', 3875)
			
			self.server				# 当前Server
			# <socketserver.TCPServer object at 0x029583D0>

	server = socketserver.ThreadingUDPServer(('localhost',1024),Handler)
	server.serve_forever()

---------------------------
http文件目录				|
---------------------------
	from http.server import SimpleHTTPRequestHandler
	import socketserver

	httpd = socketserver.TCPServer(('localhost', 3025), SimpleHTTPRequestHandler)
	httpd.serve_forever()

---------------------------
文件上传					|
---------------------------
# 客户端
import socket
import os
client = socket.socket()
client.connect(('localhost',1024))
target_file = "D:\\eclipse-jee-oxygen-M7-win32-x86_64.zip"
with open(target_file,'rb') as file:
    print('文件大小:%s'%(os.stat(target_file).st_size))
    client.sendall(file.read())
    client.shutdown(socket.SHUT_WR)


# 服务端
from socketserver import ThreadingTCPServer,BaseRequestHandler
target_file = "E:\\eclipse-jee-oxygen-M7-win32-x86_64.zip"
class Handle(BaseRequestHandler):
    def handle(self):
        request = self.request
        with open(target_file,'wb') as file:
            while True:
                data = request.recv(2048)
                print('已经接收:%s'%(len(data)))
                if len(data) == 0:
                    break
                file.write(data)
                file.flush()
        request.close()
server = ThreadingTCPServer(('localhost',1024),Handle)
server.serve_forever()