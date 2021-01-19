--------------------------------
部署方式
--------------------------------
	# Java WebSocket API（JSR-356）提供了两种部署机制
		* 启动时的Servlet容器类路径扫描（Servlet 3）
		* 在Servlet容器初始化时使用的注册API
	
		* 这两种机制都无法将单个“前端控制器”用于所有HTTP处理（包括WebSocket握手和所有其他HTTP请求）（例如Spring MVC）DispatcherServlet。
