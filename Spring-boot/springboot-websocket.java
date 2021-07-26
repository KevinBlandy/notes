----------------------------
Spring-boot websocket		|
----------------------------
	# 关于WebSocket的自动配置包
		org.springframework.boot.autoconfigure.websocket
	
	# 如果是war形式运行的websocket,那么 endpoint 就应该交给容器管理,而不是 ServerEndpointExporter

----------------------------
Spring-boot websocket整1	|
----------------------------
	# 导入依赖
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
	
	# 注册Bean
		* ServerEndpointExporter
		@Configuration  
		public class WebSocketConfiguration {  
			@Bean  
			public ServerEndpointExporter serverEndpointExporter (){  
				return new ServerEndpointExporter();  
			}  
		}  
	
	# 使用注解开发WebSocket端点
		 
		@Component 
		@ServerEndpoint("/websocket")		//必须以斜线开头,不然启动报错
		public WebSocketEndpoint{
			@OnMessage
			@OnOpen
			@OnClose
			@OnError
		}

	# 注意
		* @OnError 要添加参数:Throwable ,不然启动异常
	
	# Demo
		import java.io.IOException;
		import java.util.concurrent.ConcurrentHashMap;
		import java.util.concurrent.ConcurrentMap;

		import javax.websocket.CloseReason;
		import javax.websocket.CloseReason.CloseCode;
		import javax.websocket.CloseReason.CloseCodes;
		import javax.websocket.EndpointConfig;
		import javax.websocket.OnClose;
		import javax.websocket.OnError;
		import javax.websocket.OnMessage;
		import javax.websocket.OnOpen;
		import javax.websocket.Session;
		import javax.websocket.server.PathParam;
		import javax.websocket.server.ServerEndpoint;

		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;

		/**
		 * 
		 * 
		 * @author KevinBlandy
		 *
		 */
		@ServerEndpoint(value = "/channel/test")
		public class TestEndpoint { 

			private static final Logger LOGGER = LoggerFactory.getLogger(TestEndpoint.class);
			
			// 重复登录的 CloseReason
			public static final CloseReason REPEAT_CONNECTION = new CloseReason(new CloseCode() {
				@Override
				public int getCode() {
					return 4000;  // 4000–4999 可以自由使用
				}
			}, "重复登录");
			
			public static final ConcurrentMap<Long, Session> SESSIONS = new ConcurrentHashMap<>(); 

			private Session session;
			
			private Long userId;

			@OnMessage(maxMessageSize = -1)
			public void onMessage(String message, boolean isFinal){
				
				LOGGER.info("收到客户端消息:{}", message);
				
				this.session.getAsyncRemote().sendText("我收到了:" + message);
				
			}

			@OnOpen
			public void onOpen(Session session, @PathParam("path") String path, EndpointConfig endpointConfig){
				
				// TODO 从Session解析到用户ID
				Long userId = 1L;
				
				Session finalSession = SESSIONS.compute(userId, (key, existsChannel) -> {
					if (existsChannel != null) {
						// 已经存在，则替换为最新的，并且关闭原Channel
						try {
							// close 会触发 onClose 事件，这个CloseReason很重要
							existsChannel.close(REPEAT_CONNECTION);
						} catch (IOException e) {
						}
					}
					return session;
				});
				
				
				this.session = finalSession;
				this.userId = userId;
				
				// 不超时
				this.session.setMaxIdleTimeout(0);
			}

			@OnClose
			public void onClose(CloseReason closeReason){
				
				LOGGER.info("连接断开,id={} reason={}",this.session.getId(),closeReason);
				
				LOGGER.info("hash: {} = {}", closeReason.hashCode(), REPEAT_CONNECTION.hashCode());
				
				if (REPEAT_CONNECTION.getCloseCode().getCode() == closeReason.getCloseCode().getCode()) {
					/**
					 * 如果是重复登录的情况下，不要执行remove，因为在 @OnOpen 已经执行了Remove
					 * 这里重复执行，可能会把新添加的合法连接给Remove掉
					 */
					return;
				}
				
				Session session = SESSIONS.remove(this.userId);
				
				LOGGER.info("移除会话:{}", session.getId());
			}

			@OnError
			public void onError(Throwable throwable) throws IOException {
				LOGGER.info("连接异常,id={},throwable={}",this.session.getId(),throwable);
				this.session.close(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
			}
		}
	
	# 可以手动的注册端点, 不使用 Spring 扫描
		@Configuration
		public class WebSocketConfiguration {
			
			@Bean  
			public ServerEndpointExporter serverEndpointExporter (){  
				ServerEndpointExporter serverEndpointExporter =  new ServerEndpointExporter();
				// 添加多个标识了 @ServerEndpoint 注解的端点类
				serverEndpointExporter.setAnnotatedEndpointClasses(TestChannel.class);
				return serverEndpointExporter;
			}  
		}

		* 不需要在 TestChannel 类上声明 @Component