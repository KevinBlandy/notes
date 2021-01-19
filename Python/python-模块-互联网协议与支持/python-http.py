------------------------
http					|
------------------------
	* 该模块提供了关于http通信的的一些对象

	* http状态码对象: http.HTTPStatus
		http.HTTPStatus.OK
		http.HTTPStatus.CREATED
	

	

------------------------
http.server				|
------------------------
	* 类
		HTTPServer(socketserver.TCPServer)
		BaseHTTPRequestHandler(socketserver.StreamRequestHandler)
		SimpleHTTPRequestHandler(BaseHTTPRequestHandler)
		CGIHTTPRequestHandler(SimpleHTTPRequestHandler)

	
	* 方法
		nobody_uid()
		executable(path)
		test(HandlerClass=BaseHTTPRequestHandler,ServerClass=HTTPServer, protocol="HTTP/1.0", port=8000, bind="")


