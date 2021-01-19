----------------------------
不同Servlet服务器的设置		|
----------------------------
	# WebServerFactory 是一个标记接口,实现类如下
		TomcatServletWebServerFactory
		JettyServletWebServerFactory
		UndertowServletWebServerFactory
	
	# 自定义类继承类:WebServerFactoryCustomizer<T extends WebServerFactory>
		@FunctionalInterface
		public interface WebServerFactoryCustomizer<T extends WebServerFactory> {
			void customize(T factory);
		}
		
		* 通过不同的泛型来配置不同的Servlet容器
			@Configuration
			public class ServletContainerConfiguration implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

				@Override
				public void customize(TomcatServletWebServerFactory factory) {
					factory.setServerHeader("springboot.io");
					factory.addAdditionalTomcatConnectors(connector());
					factory.addContextCustomizers(new TomcatContextCustomizer() {
						@Override
						public void customize(Context context) {
							context.setCookieProcessor(new LegacyCookieProcessor());
						}
					});
				}

				private Connector connector()  {
					Connector connector = new Connector("org.apache.coyote.http11.Http11Nio2Protocol");
					return connector;
				}
			}
