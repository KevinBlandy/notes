-----------------
WebSocket
-----------------
	# ֧��websocket�������
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
	
	# ������
		WebsocketRoutingFilter

