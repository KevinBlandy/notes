------------------------------
服务器配置
------------------------------
	# 每个基础的WebSocket引擎都公开控制运行时特征的配置属性，例如消息缓冲区大小的大小，空闲超时等

------------------------------------------------------
ServletServerContainerFactoryBean
------------------------------------------------------
	#  ServletServerContainerFactoryBean implements FactoryBean<WebSocketContainer>, ServletContextAware, InitializingBean
	
	# 属性
		@Nullable
		private Long asyncSendTimeout;				// 异步发送消息的超时时间

		@Nullable
		private Long maxSessionIdleTimeout;			// session超时时间

		@Nullable
		private Integer maxTextMessageBufferSize;	// 最大文本消息缓冲区大小

		@Nullable
		private Integer maxBinaryMessageBufferSize;	// 最大二进制消息缓冲区大小

		@Nullable
		private ServletContext servletContext;

		@Nullable
		private ServerContainer serverContainer;
	
	# 配置到IOC
		@Bean
		public ServletServerContainerFactoryBean createWebSocketContainer() {
			ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
			container.setMaxTextMessageBufferSize(8192);
			container.setMaxBinaryMessageBufferSize(8192);
			return container;
		}


------------------------------------------------------
针对于 Jetty的配置
------------------------------------------------------
	# Jetty服务器需要额外的配置
		@Configuration
		@EnableWebSocket
		public class WebSocketConfig implements WebSocketConfigurer {

			@Override
			public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
				registry.addHandler(echoWebSocketHandler(), "/echo").setHandshakeHandler(handshakeHandler());
			}

			@Bean
			public DefaultHandshakeHandler handshakeHandler() {

				WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
				policy.setInputBufferSize(8192);
				policy.setIdleTimeout(600000);

				return new DefaultHandshakeHandler(
						new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
			}

		}