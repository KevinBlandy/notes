-----------------
WebSocket
-----------------
	# 支持websocket反向代理
		spring:
		  cloud:
			gateway:
			  routes:
				- id: user_router_websocket
				  uri: lb:ws://user-service
				  predicates:
					- Path=/api/user/ws/**
				  filters:
					- RewritePath=/api/user/ws/?(?<segment>.*), /$\{segment}
		

*/
	
	# 配置类
		WebsocketRoutingFilter

