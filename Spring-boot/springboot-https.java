
--------------------------------------
springboot配置	https				  |
--------------------------------------
	# 使用jdk系统工具生成证书
		* 使用的是$JAVA_HOME/bin/keytool 工具(JAVA里面自带的工具)

		* keytool -genkey -alias tomcat -validity 36500 -keystore D:\home\tomcat.keystore -keyalg RSA

			* -genkey		:表示产生密钥对
			* -alias		:表示起个别名
			* -keyalg		:指定密钥算法
			* -validity		:密钥有效时间,默认为90天,36500.表示100年
			* -keystore		:指定密钥保存的路径

		
	
		* 输入 keystore 密码
			产生的证书,系统会使用一个密钥库来保存,这里就是设置密钥库的密码
		
		* 您的名字与姓氏是什么?
			这一步很重要,表示为哪个网站生成数字证书,填写域名
		
		* 您的组织单位名称是什么？
			* 无视
		
		* 您的组织名称是什么？
			* 无视
		
		* 您所在的城市或者区域名称是什么?
			* 无视
		
		* 您所在的洲,或省份是什么?
			* 无视
		
		* 该单位的两字母国家代码是什么?
			* 无视
		
		* CN=localhost,OU=Unknow,O=Unknow,L=Unknow,ST=Unknow,C=Unknow 正确吗?
			* 确定输入: y
		
		* 输入 <tomcat> 的主密码(如果和 keystore 密码相同,直接回车)
			* 数字证书的密钥,和密钥库的密码是否相同.
			* 这项较为重要,会在tomcat配置文件中使用,建议输入与keystore的密码一致,设置其它密码也可以
		
		* OK,在~目录下,会生成 .keystore 一个证书文件
			* 至此,证书创建成功
		

	# 配置
		server.ssl.enabled=true
		server.ssl.key-store=classpath:ssl/springboot.io.p12
		server.ssl.key-store-type=PKCS12/JKS
		server.ssl.key-store-password=[key.store的密码]
	
	# http转向HTTPS
		* 很多时候,地址栏输入的http,但是会被转发到https
		* 实现这个功能需要'服务器的特定'配置来实现,就是上面说的特定配置(不同服务器用不同的)
			TomcatEmbeddedServletContainerFactory
			JettyEmbeddedServletContainerFactory

		* 代码
				@Configuration
				public class TomcatConfiguration {
					
					@Bean
					public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
						TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory(){
							@Override
							protected void postProcessContext(Context context) {
								SecurityConstraint securityConstraint = new SecurityConstraint();
								securityConstraint.setUserConstraint("CONFIDENTIAL");
								SecurityCollection securityCollection = new SecurityCollection();
								securityCollection.addPattern("/*");
								securityConstraint.addCollection(securityCollection);
								context.addConstraint(securityConstraint);
							}
						};
						tomcatEmbeddedServletContainerFactory.addAdditionalTomcatConnectors(httpConnectot());
						return tomcatEmbeddedServletContainerFactory;
					}
					
					@Bean
					public Connector httpConnectot(){
						//NIO连接器
						Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
						connector.setScheme("http");
						connector.setPort(8080);            //监听端口
						connector.setSecure(false);
						connector.setRedirectPort(8443);    //转发端口
						return connector;
					}
				}


--------------------------------------
springboot配置	http2				  |
--------------------------------------
	# 必须先配置https
	# 而且目前好像只有 undertow 支持

		server:
		  port: 443
		  servlet:
			context-path: /
		  ssl:				//开启http2必须要开启https	
			enabled: true
			key-store: classpath:dev_ssl/javaweb.io.keystore
			key-store-type: PKCS12
			key-store-password: a12551255
		  http2:		//开启HTTP2  
			enabled: true	

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-tomcat</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-undertow</artifactId>
		</dependency>

	# 通过谷歌浏览器查看http2是否开启成功
		chrome://net-internals/#http2
	
	# undertow配置80端口转发443
		import org.springframework.beans.factory.annotation.Value;
		import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
		import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
		import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
		import org.springframework.boot.web.server.WebServerFactoryCustomizer;
		import org.springframework.context.annotation.Configuration;

		import io.undertow.Undertow.Builder;
		import io.undertow.server.HttpServerExchange;
		import io.undertow.servlet.api.ConfidentialPortManager;
		import io.undertow.servlet.api.DeploymentInfo;
		import io.undertow.servlet.api.SecurityInfo;
		import io.undertow.servlet.api.SecurityConstraint;
		import io.undertow.servlet.api.TransportGuaranteeType;
		import io.undertow.servlet.api.WebResourceCollection;

		@Configuration
		public class UndertowConfiguration implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
			
			@Value("${server.ssl.enabled:false}")
			private boolean sslEnable;
			
			@Value("${server.port}")
			private Integer port;
			
			private static final Integer HTTP_PORT = 80;
			
			@Override
			public void customize(UndertowServletWebServerFactory factory) {
				
				factory.setServerHeader("Apache/2.2.21");
				
				//开启了https,则监听80端口，重定向
				if(sslEnable) {
					
					factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
						@Override
						public void customize(Builder builder) {
							builder.addHttpListener(HTTP_PORT, "0.0.0.0");
							builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);				// 开启http2
							builder.setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH,true);	// 开启Server Push
						}
					});
					
					factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
						@Override
						public void customize(DeploymentInfo deploymentInfo) {
							
							SecurityConstraint securityConstraint = new SecurityConstraint();
							
							WebResourceCollection webResourceCollection = new WebResourceCollection();
							webResourceCollection.addUrlPattern("/*");
							
							securityConstraint.addWebResourceCollection(webResourceCollection);
							securityConstraint.setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL);
							securityConstraint.setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT);
							
							deploymentInfo.addSecurityConstraint(securityConstraint);
							deploymentInfo.setConfidentialPortManager(new ConfidentialPortManager() {

								@Override
								public int getConfidentialPort(HttpServerExchange exchange) {
									return port;
								}
							});
						}
						
					});
				}
			}
		}


--------------------------------------
springboot配置	ssl双向验证			  |
--------------------------------------
	# 配置
server:
  ssl:
    enabled: true
    key-store: classpath:ssl/localhost.keystore
    key-store-type: JKS
    key-store-password: 123456
    
    # 需要验证客户端
    client-auth: NEED
    trust-store: classpath:ssl/localhost.keystore
    trust-store-type: JKS
    trust-store-password: 123456
