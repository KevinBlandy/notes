------------------------
JSONP					|
------------------------
	# 不多说,覆写 Converter 完成
	# 预定义JSONP实现
		AbstractJsonpResponseBodyAdvice
		 <bean class="org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice">
			<constructor-arg>
				<list>
					<value>callback</value>
					<value>jsonp</value>
				</list>
			</constructor-arg>
		</bean>
	
	# Fastjson也有对应的实现
		FastJsonpResponseBodyAdvice

	# Spring4.1对JSON跨域的支持
		* MappingJackson2JsonView提供的支持 

		<bean class="org.springframework.web.servlet.view.json.MappingJackson2JsonView">  
			<property name="jsonpParameterNames">  
				<set>  
					<value>jsonp</value>  
					<value>callback</value>  
				</set>  
		   </property>  
		</bean>  
 
	* 对使用HttpMessageConverter的@ResponseBody的支持 
		@Order(2)  
		@ControllerAdvice(basePackages = "com.github")  
		public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {  
			public JsonpAdvice() {  
				super("callback", "jsonp"); //指定jsonpParameterNames  
			}  
		}  

------------------------
CORS协议				|
------------------------
	# HTML5的新东西
	# 浏览器端还是 ajax,一样的,当发现请求是跨域,浏览器会自动添加一些特殊的请求头
	# 浏览器一旦发现AJAX请求跨源，就会自动添加一些附加的头信息，有时还会多出一次附加的请求，但用户不会有感觉。
	# 因此，实现CORS通信的关键是服务器。只要服务器实现了CORS接口，就可以跨源通信。
	# 纯粹是服务端的事情
	# HTTP 请求头
		
	# HTTP 响应头

	
	
	# 跨域的时候,把Cookie也发送给服务端
		* 客户端请求(XMLHttpRequest)
		xhr.withCredentials = true;
		* 服务端响应
			header("Access-Control-Allow-Credentials: true");
			* 如果服务器端的响应中，如果没有返回 Access-Control-Allow-Credentials: true 的响应头，那么浏览器将不会把响应结果传递给发出请求的脚本程序，以保证信息的安全。

------------------------
注解配置				|
------------------------
	# Spring 处理
		* 需要4.2.x以上的版本才支持
		* @CrossOrigin
			@AliasFor("origins")
			String[] value() default {};
				* 允许哪些域名来跨域来请求当前资源,可以使用通配符

			@AliasFor("value")
			String[] origins() default {};
				* 允许哪些域名来跨域来请求当前资源,可以使用通配符

			String[] allowedHeaders() default {};
				* 允许客户端请求携带的请求头
				* 
			String[] exposedHeaders() default {};
				* 允许客户端访问的响应头,不指定,就只能访问默认的6个
				* Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma

			RequestMethod[] methods() default {};
				* 允许请求的方法

			String allowCredentials() default "";
				* 需要的Cookie值

			long maxAge() default -1;

		1,在 @Controller/@RequestMapping 上添加注解,来标识支持跨域
			@CrossOrigin(origins = "http://kbiao.me")



------------------------
代码配置				|
------------------------
	# 代码配置1
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.web.cors.CorsConfiguration;
		import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
		import org.springframework.web.filter.CorsFilter;

		@Configuration
		public class CorsConfig {

			private CorsConfiguration buildConfig() {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.addAllowedOrigin("*"); // 1
				corsConfiguration.addAllowedHeader("*"); // 2
				corsConfiguration.addAllowedMethod("*"); // 3
				return corsConfiguration;
			}

			@Bean
			public CorsFilter corsFilter() {
				UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
				source.registerCorsConfiguration("/**", buildConfig()); // 4
				return new CorsFilter(source);
			}
		}
	
	#代码配置2 
		* SpringBoot可以用这个
		public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter{ 
			@Override public void addCorsMappings(CorsRegistry registry) { 
				registry.addMapping("/api/*").allowedOrigins("*"); 
			} 
		}

	# xml配置
		<bean id="corsConfiguration" class="org.springframework.web.cors.CorsConfiguration">
			<property name="allowCredentials" value=""/>
			<property name="allowedHeaders" value=""/>
			<property name="allowedMethods" value=""/>
			<property name="allowedOrigins" value=""/>
			<property name="exposedHeaders" value=""/>
			<property name="maxAge" value=""/>
		</bean>
		<bean id="corsFilter" class="org.springframework.web.filter.CorsFilter">
			<constructor-arg index="0">
				<bean class="org.springframework.web.cors.UrlBasedCorsConfigurationSource">
					<property name="corsConfigurations">
						<map key-type="java.lang.String" value-type="org.springframework.web.cors.CorsConfiguration">
							<!-- 一个 key (url), 对应一个 corsConfiguration (跨域配置)-->
							<entry key="/**" value-ref="corsConfiguration"/>
						</map>
					</property>
				</bean>
			</constructor-arg>
		</bean>



------------------------
官方mvc标签配置			|
------------------------
	* 需要schemaLocation在4.3以上
	<mvc:cors>
		<mvc:mapping path="/api/**"
			allowed-origins="http://domain1.com, http://domain2.com"
			allowed-methods="GET, PUT"
			allowed-headers="header1, header2, header3"
			exposed-headers="header1, header2" allow-credentials="false"
			max-age="123" />

		<mvc:mapping path="/resources/**"
			allowed-origins="http://domain1.com" />
	</mvc:cors>


------------------------
原生Servlet				|
------------------------
    public static void setCorsHeader(HttpServletRequest request,HttpServletResponse response) {
    	String origin = request.getHeader("Origin");
    	if (origin == null) {
    		origin = "*";
    	}
    	response.addHeader("Access-Control-Allow-Origin", origin);
    	response.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type, Accept,Authorization");
    	response.addHeader("Access-Control-Allow-Credentials", "true");
    	response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
    	/**
    	 * 允许客户端获取其他的响应头,不然,仅仅只能访问6个固定的:Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma
    	 */
    	//response.addHeader("Access-Control-Expose-Headers", "");	
    }

