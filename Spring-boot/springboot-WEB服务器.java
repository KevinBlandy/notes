-------------------------------
Spring-boot Web服务器			|
-------------------------------
	# Spring boot 默认以tomcat 作为容器
	# 关于Tomcat的配置都在一个类中
		* org.springframework.boot.autoconfigure.web.ServerProperties
	# 我们仅仅需要在:application.properties 中进行配置即可
		* tomcat的属性,都有一个:server.tomcat 的前缀

-------------------------------
Spring-boot Web服务器	配置项	|
-------------------------------
	# 通用配置
		server.port=80
			* 访问端口,默认是8080
		server.session-timeout=1800
			* session过期事件,单位是秒
		server.context-path=/
			* 访问路径,默认是 /
	
	#  Tomcat配置
		server.tomcat.uri-encoding=UTF-8
			* Tomcat编码,默认是 UTF-8
		server.tomcat.compression=on
			* Tomcat是否开启压缩,默认是 off

	# Jetty配置
		
-------------------------------
Spring-boot通过代码配置Servlet容器|
-------------------------------
	# 不使用properties ,则可以使用代码的方式来进行配置
	# '该配置对所有的Servlet容器都生效'
		1,自定义类,实现接口,
			EmbeddedServletContainerCustomizer
			* '添加@Component注解,交给spring管理'

		2,覆写方法
			void customize(ConfigurableEmbeddedServletContainer container);
		3,进行自定义的配置
			* 其实就是对 ConfigurableEmbeddedServletContainer 对象进行一些列的参数配置
			setPort(int port);
				* 配置端口
			addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
				* 配置错误页面
			setSessionTomeout(10,TimeUnit.MINUTES);
				* 配置Session生命
	
	# 也可以根据不同的Servlet容器进行特定的配置
		* 需要去操作不同的类
		EmbeddedServletContainerFactory(父级接口)
			TomcatEmbeddedServletContainerFactory
				* Tomcat
			JettyEmbeddedServletContainerFactory
				* Jetty
			UndertowEmbeddedServletContainerFactory
				* Undertow


-------------------------------
Spring-boot 特定配置 Tomcat		|
-------------------------------
	# 通过配置类 @Configuration 把 TomcatEmbeddedServletContainerFactory 交给Spring 进行管理
		@Configuration
		public class TomcatConfiguration {
			@Bean
			public EmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory(){
				TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory();
				//端口
				tomcatEmbeddedServletContainerFactory.setPort(1234);
				//异常页面
				tomcatEmbeddedServletContainerFactory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404/404.html"));
				//session生命
				tomcatEmbeddedServletContainerFactory.setSessionTimeout(30, TimeUnit.MINUTES);
				return tomcatEmbeddedServletContainerFactory;
			}
		}

-------------------------------
Spring-boot 特定配置 Jetty		|
-------------------------------
	
	# 通过配置类 @Configuration 把 JettyEmbeddedServletContainerFactory 交给Spring 进行管理
	# 跟Tomcat一个德行的配置,只是API不同
	

-------------------------------
Spring-boot 使用Jetty服务器		|
-------------------------------
	# Spring boot 默认使用 Tomcat 作为服务器
	# 替换为 Jetty 服务器,只需要替换相关的依赖即可
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
				<!-- 排除Tomcat依赖 -->
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-tomcat</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<!-- 添加jetty依赖 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jetty</artifactId>
		</dependency>

-------------------------------
Spring-boot 配置SSL				|
-------------------------------
	# HTTPS,的配置,仅仅需要在 properties 文件中进行配置
		server.ssl.key-store=.keystore
		server.ssl.key-store-password=111111
		server.ssl.keyStoreType=JKS
		server.ssl.keyAlias=tomcat

	# http转向HTTPS
		* 很多时候,地址栏输入的http,但是会被转发到https
		* 实现这个功能需要'服务器的特定'配置来实现,就是上面说的特定配置(不同服务器用不同的)
			TomcatEmbeddedServletContainerFactory
			JettyEmbeddedServletContainerFactory

		* 代码
				import org.apache.catalina.Context;
				import org.apache.catalina.connector.Connector;
				import org.apache.tomcat.util.descriptor.web.SecurityCollection;
				import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
				import org.springframework.beans.factory.annotation.Value;
				import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
				import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
				import org.springframework.boot.web.server.WebServerFactoryCustomizer;
				import org.springframework.context.annotation.Configuration;
				/**
				 * 
				 * 
				 * @author Administrator
				 *
				 */
				@Configuration
				public class TomcatConfiguration implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

					@Value("${server.ssl.enabled:false}")
					private boolean sslEnable;

					@Value("${server.port}")
					private Integer port;

					static final Integer HTTP_PORT = 80;

					@Override
					public void customize(TomcatServletWebServerFactory factory) {
						if (!sslEnable) {
							return;
						}
						factory.addContextCustomizers(new TomcatContextCustomizer() {

							@Override
							public void customize(Context context) {
								
								SecurityConstraint securityConstraint = new SecurityConstraint();
								securityConstraint.setUserConstraint("CONFIDENTIAL");
								
								SecurityCollection collection = new SecurityCollection();
								collection.addPattern("/*");
								
								securityConstraint.addCollection(collection);
								
								context.addConstraint(securityConstraint);
							}

						});
						Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
						connector.setPort(HTTP_PORT);
						connector.setRedirectPort(this.port);
						factory.addAdditionalTomcatConnectors(connector);
					}
				}

-------------------------------
Spring-boot Favicon				|
-------------------------------
	# 小图标,spring boot 提供了一个小的图标
	# 关闭 favicon
		spring.mvc.favicon.enabled=false
	# 自定义 favicon
		*只要把　favicon.ico (文件名不能修改),放置在一下目录都可以
			1,类路径跟目录
			2,类路径 /META-INF/resources
			3,类路径 resources 目录
			4,类路径 static 目录
			5,类路径 public 目录