----------------------------
wsgiref						|
----------------------------
	* WSGI工具与引用实现
	* 子模块
		simple_server

----------------------------
wsgiref						|
----------------------------

----------------------------
wsgiref						|
----------------------------

----------------------------
wsgiref.simple_server		|
----------------------------
	* 模块函数
		wsgiref.simple_server.WSGIServer make_server(host, port, app, server_class=WSGIServer, handler_class=WSGIRequestHandler)
			* 创建 WSGIServer 服务实例
			* host		:当前IP地址,可以是空字符串
			* port		:监听端口
			* app		:处理请求与响应的函数

----------------------------
wsgiref.simple_server.WSGIServer|
----------------------------
	* 实例方法
		serve_forever()
			* 启动服务器,开始监听HTTP请求
		

