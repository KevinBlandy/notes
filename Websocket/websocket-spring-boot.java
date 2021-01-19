----------------------------
Spring-boot websocket		|
----------------------------
	# 关于WebSocket的自动配置包
		org.springframework.boot.autoconfigure.websocket
	
	# 相关的教程
		https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-websocket-tomcat
		https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-websocket-jetty

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
		* Endpoint 被 @Component 注解标识,被IOC管理,但是每次连接请求都会创建新的对象
		
		* @OnError 要添加参数:Throwable ,不然启动异常

	
