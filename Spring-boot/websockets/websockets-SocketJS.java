----------------------------------------
websockets socketJS
----------------------------------------
	# SockJsServiceRegistration 

	# 实现WebSocketConfigurer配置类，添加端点方法会返回 WebSocketHandlerRegistration 对象
		public interface WebSocketHandlerRegistry {
			WebSocketHandlerRegistration addHandler(WebSocketHandler webSocketHandler, String... paths);
		}

	
	# WebSocketHandlerRegistration
		SockJsServiceRegistration withSockJS();
			* 支持 SocketJS
	
	# 心跳
	# 链接断开
	# CORS跨域


----------------------------------------
websockets socketJS Clientt
----------------------------------------
