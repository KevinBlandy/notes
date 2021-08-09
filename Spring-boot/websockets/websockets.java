----------------------------
spring-websocket
----------------------------
	# �ĵ�
		https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket
		https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket
	
	# Maven
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-websocket -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-websocket</artifactId>
			<version>5.2.6.RELEASE</version>
		</dependency>

	# �������
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
	
	# ����ע��
		@EnableWebSocket
	
	# ���ýӿ���
		import com.demo.user.websocket.DemoChannel;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.http.HttpStatus;
		import org.springframework.http.server.ServerHttpRequest;
		import org.springframework.http.server.ServerHttpResponse;
		import org.springframework.web.socket.WebSocketHandler;
		import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
		import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
		import org.springframework.web.socket.server.HandshakeFailureException;
		import org.springframework.web.socket.server.HandshakeInterceptor;
		import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
		import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
		import org.springframework.web.socket.sockjs.transport.handler.WebSocketTransportHandler;

		import java.io.IOException;
		import java.nio.charset.StandardCharsets;
		import java.util.Map;

		@Configuration
		public class WebSocketConfiguration implements WebSocketConfigurer {

			// ���handler �Լ����ü���·��
			@Override
			public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

				// ��Ӷ˵�
				registry.addHandler(this.demoChannel(), "/channel/demo")
						// ����������
						.addInterceptors(new HandshakeInterceptor() {
							@Override
							public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
								return true;
							}
							@Override
							public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
							}
						})
						// ���ִ�����
						.setHandshakeHandler(new WebSocketTransportHandler(new DefaultHandshakeHandler(){
							// �������ֱ������˵�
							@Override
							protected void handleInvalidUpgradeHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
								if (logger.isErrorEnabled()) {
									logger.error("Handshake failed due to invalid Upgrade header: " + request.getHeaders().getUpgrade());
								}
								response.setStatusCode(HttpStatus.BAD_REQUEST);
								response.getBody().write("BAD_REQUEST".getBytes(StandardCharsets.UTF_8));
							}
						}) {
							@Override
							public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> attributes) throws HandshakeFailureException {
								// ���֣�
								return super.doHandshake(request, response, handler, attributes);
							}
						})
						.setAllowedOriginPatterns("*");

			}

			@Bean
			public ServletServerContainerFactoryBean serverContainerFactoryBean() {
				ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
				container.setMaxTextMessageBufferSize(8192);
				container.setMaxBinaryMessageBufferSize(8192);
				container.setMaxSessionIdleTimeout(-1L);
				return container;
			}


			@Bean
			public DemoChannel demoChannel(){
				return new DemoChannel();
			}
		}
	
	# Handler��
		import org.springframework.web.socket.*;
		import org.springframework.web.socket.handler.TextWebSocketHandler;

		public class DemoChannel extends TextWebSocketHandler {
			@Override
			protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
				String payload = message.getPayload();
				System.out.println("�յ���Ϣ:" + payload);
				session.sendMessage(new TextMessage("[server][" + session.getTextMessageSizeLimit() + "] => " + payload));

			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
				System.out.println("���ӹر�:" + session.getId());
			}

			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				System.out.println("�µ�����:" + session.getId());
			}

			@Override
			public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
				super.handleTransportError(session, exception);
				System.out.println("�쳣��:" + exception.getMessage());
				session.close(CloseStatus.SERVER_ERROR);
			}
		}
