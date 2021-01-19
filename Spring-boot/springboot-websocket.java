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
		@Component
		@ServerEndpoint(value = "/channel/test")
		public class TestEndpoint {

			private static final Logger LOGGER = LoggerFactory.getLogger(TestEndpoint.class);

			private Session session;

			@OnMessage(maxMessageSize = 10)
			public void onMessage(byte[] message){
				//skip
			}

			@OnOpen
			public void onOpen(Session session, EndpointConfig endpointConfig){
				LOGGER.info("新的连接,id={}",session.getId());
				session.setMaxIdleTimeout(0);
				this.session = session;
			}

			@OnClose
			public void onClose(CloseReason closeReason){
				LOGGER.info("连接断开,id={} reason={}",this.session.getId(),closeReason);
			}

			@OnError
			public void onError(Throwable throwable) throws IOException {
				LOGGER.info("连接异常,id={},throwable={}",this.session.getId(),throwable);
				this.session.close();
				throwable.printStackTrace();
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