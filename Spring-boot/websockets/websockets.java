----------------------------
spring-websocket
----------------------------
	# 文档
		https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket
	
	# Maven
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-websocket -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-websocket</artifactId>
			<version>5.2.6.RELEASE</version>
		</dependency>

	# 核心组件
		WebSocketHandler
			|-AbstractWebSocketHandler
				|-BinaryWebSocketHandler
				|-TextWebSocketHandler
					|-SockJsWebSocketHandler
			|-PerConnectionWebSocketHandler
			|-SubProtocolWebSocketHandler
			|-WebSocketHandlerDecorator
				|-ExceptionWebSocketHandlerDecorator
				|-LoggingWebSocketHandlerDecorator
		
		WebSocketMessage
			|-AbstractWebSocketMessage
				|-BinaryMessagee<ByteBuffer>
				|-PingMessage<ByteBuffer>
				|-PongMessage<ByteBuffer>
				|-TextMessage<String>

		ServletServerContainerFactoryBean
		


----------------------------
spring-websocket
----------------------------
	# Maven
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-websocket -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-websocket</artifactId>
			<version>2.3.0.RELEASE</version>
		</dependency>
	
	# 开启注解
		@EnableWebSocket
	
	# 配置接口类
		import org.springframework.web.socket.config.annotation.EnableWebSocket;
		import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
		import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

		@Configuration
		@EnableWebSocket
		public class WebSocketConfig implements WebSocketConfigurer {

			// 添加handler 以及设置监听路径
			@Override
			public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
				WebSocketHandlerRegistration webSocketHandlerRegistration = registry.addHandler(myHandler(), "/myHandler");

			}

			// 注册Handler
			@Bean
			public WebSocketHandler myHandler() {
				return new MyHandler();
			}

		}