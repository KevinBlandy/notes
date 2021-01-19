----------------------------------------
整合Spring-简单整合						|
----------------------------------------
	# 引入依赖
		<dependency>
			<groupId>javax.websocket</groupId>
			<artifactId>javax.websocket-api</artifactId>
			<version>1.1</version>
			<scope>provided</scope> 
		</dependency>
	
	# 该是怎么开发就怎么开发,直接无视掉spring的存在,因为 @Endpoint 是容器在扫描加载,要正确的配置: ServerApplicationConfig
	# 这种方式是把 端点交给了 Servlet 容器管理
	# 在Tomcat8下测试OK

----------------------------------------
整合Spring-xml配置						|
----------------------------------------
	# Spring 约束
		xmlns:websocket="http://www.springframework.org/schema/websocket"
		xsi:schemaLocation=
			http://www.springframework.org/schema/websocket
			http://www.springframework.org/schema/websocket/spring-websocket.xsd

	# 引入依赖
		<dependency>
			<groupId>javax.websocket</groupId>
			<artifactId>javax.websocket-api</artifactId>
			<version>1.1</version>
			<!-- 注意,scope必须为provided,否则runtime会冲突，如果使用tomcat 8,还需要将TOMCAT_HOME/lib下的javax.websocket-api.jar一并删除 -->
			<scope>provided</scope> 
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-websocket</artifactId>
			<version>4.2.8.RELEASE</version>
		</dependency>
	
	# 文档
		https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket

	# 创建 MessageHandler
		* 主要是用于处理消息
		* 支持两种方式，一种是实现org.springframework.web.socket.WebSocketHandler 接口
		* 一种则是继承 TextWebSocketHandler 或 BinaryWebSocketHandler 抽象类
	
	# 创建拦截器
		* 可选的操作,主要是在握手前和握手后可以完成一些操作
		* 主要作用是用于客户端与服务端之间握手的一些配置
		* 继承抽象类 HttpSessionHandshakeInterceptor 
	
	# 创建自定义握手规则
		* 可选操作,定义握手类,包括验证客户端来源，协商子协议和其他
		* 实现接口 HandshakeHandler 

	# xml配置
		<!--spring javadoc的说明是默认情况下，允许所有来源访问，但我们跑下来发现不配置 allowed-origins 的话总是报403错误。-->
		<websocket:handlers allowed-origins="*">
			<!-- handler -->
			<websocket:mapping path="/springws/websocket.ws" handler="demoWSHandler"/>

			<!-- 握手处理器起 -->
			<websocket:handshake-handler ref="test"/>	
			
			<!-- 握手拦截器 -->
			<websocket:handshake-interceptors>
				<bean class="com.ld.net.spider.demo.ws.HandshakeInterceptor"/>
			</websocket:handshake-interceptors>
		</websocket:handlers>
		
		<!-- 配置messageHandler -->
		<bean id="demoWSHandler" class="com.ld.net.spider.demo.ws.DemoWSHandler"/>

		<!-- 配置 -->
		<bean class="org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean">
			<property name="maxTextMessageBufferSize" value="8192"/>		
			<property name="maxBinaryMessageBufferSize" value="8192"/>
			<property name="maxSessionIdleTimeout" value="1000"/>
			<property name="asyncSendTimeout" value="1000"/>
		</bean>
	
	# Web.xml
		在web.xml中增加*.ws映射即可（如果原来不是/**/的话）	
		<servlet-mapping>
			<servlet-name>springMVC</servlet-name>
			<url-pattern>*.ws</url-pattern>
		</servlet-mapping>

----------------------------------------
整合Spring-无xml配置(注解)				|
----------------------------------------
	# 自定义配置类
		* 实现接口:WebSocketConfigurer
		* 添加注解:@Configuration ,@EnableWebSocket
		* 覆写方法后,通过 registerWebSocketHandlers 的 addHandler 来完成
			1,添加新的端点及其映射(可以有多个)
			2,添加握手拦截器(可以有多个)
			3,添加握手处理器
			4,允许访问的站点(可以有多个)
			5,开启 socketjs的支持

		@Configuration
		@EnableWebSocket
		public class WebSocketConfig implements WebSocketConfigurer {

			@Override
			public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
				registry.addHandler(myHandler(), "/ws.ws")
						.addInterceptors(new HttpSessionHandshakeInterceptor())
						.setHandshakeHandler(handshakeHandler())
						.setAllowedOrigins("*")
						.withSockJS();
			}

			@Bean			//消息处理类
			public WebSocketHandler myHandler() {
				MessageHandler messageHandler = new MessageHandler();
				System.err.println("HAND="+messageHandler);
				return messageHandler;
			}
			
			@Bean			//握手类
			public DefaultHandshakeHandler handshakeHandler() {
				return new DefaultHandshakeHandler();
			}

			@Bean			//配置类
			public WebSocketContainer webSocketContainer(){
				WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
				webSocketContainer.setAsyncSendTimeout(5000);                   //异步消息超时时间
				webSocketContainer.setDefaultMaxBinaryMessageBufferSize(1000);  //二进制消息最大缓冲区
				webSocketContainer.setDefaultMaxTextMessageBufferSize(10000);   //文本消息最大缓冲区
				webSocketContainer.setDefaultMaxSessionIdleTimeout(1000);       //session超时时间
				return webSocketContainer;
			}
		}

----------------------------------------
整合Spring-sockjs						|
----------------------------------------
	# 则是通过sockjs（也就是js）访问

	
		
----------------------------------------
API										|
----------------------------------------
	WebSocketHandler
		# 消息处理器顶层接口
		# 方法
			void afterConnectionEstablished(WebSocketSession session) throws Exception;
				* 打开连接执行
			void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception;
				* 收到消息
			void handleTransportError(WebSocketSession session, Throwable exception) throws Exception;
				* 异常
			void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception;
				* 连接关闭
			boolean supportsPartialMessages();
				* 是否支持分片消息
		|-AbstractWebSocketHandler
			# 抽象实现,其实就是新增了几个针对于消息类型的处理方法
			# 方法
				protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception 
					* 文本消息
				protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception 
					* 二进制消息
				protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception 
					* pong消息
			|-TextWebSocketHandler
				# 文本消息处理器
				# 方法
					* 没有新方法,就是在处理	handleBinaryMessage 消息的时候会抛出异常	

			|-BinaryWebSocketHandler
				# 二进制消息处理器
				# 方法
					* 没有新方法,就是在处理	handleTextMessage 消息的时候会抛出异常	

	DefaultHandshakeHandler
		# 默认的 HandshakeHandler 实现

	HttpSessionHandshakeInterceptor
		# HandshakeInterceptor 的实现之一
	

	WebSocketSession
		# 连接会话
			

	WebSocketMessage<T>
		# 消息,是泛型
		# 方法
			T getPayload();
			int getPayloadLength();
			boolean isLast();
		|-AbstractWebSocketMessage
			# 抽象实现
			# 方法
			|-BinaryMessage
			|-PingMessage
			|-PongMessage
			|-TextMessage

	CloseStatus
		# 消息关闭的一些信息


----------------------------------------
XML										|
----------------------------------------
	<websocket:handlers>
        <websocket:mapping path="/ws.ws" allowed-origins="*" order="5"/>			
			* 用于配置一个 WebSocketHandler 的子类,并且指定URI
			* allowed-origins	:表示允许什么地方的人连接我,可以使用通配符,表示允许所有
			* order				:未知
		<websocket:handshake-handler ref="test"/>							
			* 配置 HandshakeHandler 的子类,定义握手类,包括验证客户端来源，协商子协议和其他
        <websocket:handshake-interceptors>
			* 配置握手前后的拦截器,HandshakeInterceptor 子类,可以有多个
            <bean class="com.tedi.community.web.utils.Interceprot"/>	
				* 该拦截器可以有多个
        </websocket:handshake-interceptors>
    </websocket:handlers>


----------------------------------------
注意									|
----------------------------------------
	# spring的拦截器也许会对wbsocket的路径生效
	# MessageHandler 是以单例的形式存在于应用中的