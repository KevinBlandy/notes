--------------------------------
Spring-boot WEB项目				|
--------------------------------
	# Spring boot 对WEB的开发提供的支持
	# spring-boot-starter-web,提供了嵌入式的Tomcat和Springmvc的依赖
	# Web相关的自动配置在:spring-boot-autoconfigure.jar 的 org.springframework.boot.autoconfigure.web 下
		ServerPropertiesAutoConfiguration 和 ServerProperties
			* 自动配置内嵌Servlet容器
		
		HttpEncodingAutoConfiguration 和 HttpEncodingProperties
			* 自动配置http的编码
		
		MultipartAutoConfiguration 和 MultipartProperties
			* 自动配置上传文件的属性

		JacksonHttpMessageConvertersConfiguration 
			* 配置,mappingJackson2HttpMessageConverter 和 mappingJackson2XmlHttpMessageConverter
		
		WebMvcAutoConfiguration 和 WebMvcProperties
			* 配置spring mvc


--------------------------------
Spring-boot 拦截器				|
--------------------------------
	# spring boot拦截器默认有 
		HandlerInterceptorAdapter
		AbstractHandlerMapping
		UserRoleAuthorizationInterceptor
		LocaleChangeInterceptor
		ThemeChangeInterceptor
	
	# 一些常用的组件
		AsyncHandlerInterceptor
			void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
			* 这个方法会在Controller方法异步执行时开始执行


	2.配置spring mvc的拦截器 WebMvcConfigurerAdapter 
		* 自定义类继承 WebMvcConfigurerAdapter 
			* 该类作为配置类,应该添加 :@Configuration 注解
			* 也可以直接让 @SpringBootApplication 去实现
		
		* 覆写 addInterceptors 方法,进行拦截器的添加

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				// 多个拦截器组成一个拦截器链
				// addPathPatterns 用于添加拦截规则
				// excludePathPatterns 用户排除拦截
				registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**").excludePathPatterns("/test/**");
				registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**").excludePathPatterns("/test/**");
				super.addInterceptors(registry);
			}
	

	
	3. 抽象一个常用的
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;

		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.method.HandlerMethod;
		import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


		public abstract class BaseHandlerInterceptor extends HandlerInterceptorAdapter {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				if (!(handler instanceof HandlerMethod)) {
					return true;
				}
				return this.preHandle(request, response, (HandlerMethod) handler);
			}

			public abstract boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception;


			/**
			 * 获取到mapping
			 * @param handlerMethod
			 * @return
			 */
			protected String getMapping(HandlerMethod handlerMethod){
				/**
				 * 获取方法上的第一个mapping
				 */

				String mapping = "";

				RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
				String[] methodMappings = methodRequestMapping.value();
				if (methodMappings != null && methodMappings.length > 0){
					System.err.println(methodMappings[0]);
					mapping = methodMappings[0];
				}

				/**
				 * 获取类上的第一个mapping
				 */
				RequestMapping classRequestMapping =  handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
				if (classRequestMapping != null){
					String[] classMappings = classRequestMapping.value();
					if (classMappings != null && classMappings.length > 0){
						System.err.println(classMappings[0]);
						mapping = classMappings[0] + mapping;
					}
				}
				return mapping;
			}
		}


--------------------------------
Spring-boot 静态资源			|
--------------------------------
	# 自动配置类的: addResourceHandlers 方法中定义了以下静态资源的自动配置
	# 类路径文件
		* /static 
		* /public
		* /resources
		* /META-INF/resources
	# 以上文件夹中的静态文件,通过配置静态资源pattern,来进行访问
		spring.mvc.static-path-pattern=/static/**	*/
	
	# webjar,的静态资源映射
		* webjar,就是jar中有jar.
		* /META-INF/resources/webjars/ 下的静态文件映射为: /web/jar/**						*/

	# 静态资源配置

		* 解决方案一
			* 自定义配置类,实现 WebMvcConfigurerAdapter ,覆写方法
				@Override
				public void addResourceHandlers(ResourceHandlerRegistry registry) {
					//static 静态资源目录
					registry.addResourceHandler("/static/**")
					//src/main/resources/static
					.addResourceLocations("classpath:/static/")		
					//本地d盘下的pic,
					.addResourceLocations("file:D:/pic/");
				}
			* 一个 ResourceHandler 映射了多个 ResourceLocations,如果出现相同路径,相同文件,则谁先映射谁优先
			* 上面代码:static/index.js 要优先于 pic/index.js 加载

		* 解决方案二
			* 在application.properties 添加配置,设置静态资源的访问路径
				spring.mvc.static-path-pattern=/static/**								*/
				*  '注意,后面不能有空格,这里完全是为了处理掉 /** Java的注释冲突'
			
			* 在application.properties 添加配置,指定静态资源的位置
				local.image.folder=d:/pic/
				spring.web.resources.static-locations[0]=classpath:/static/
				spring.web.resources.static-locations[1]=file:${local.image.folder}

--------------------------------
Spring-boot 视图映射			|
--------------------------------
	# 自定义配置类,实现 WebMvcConfigurerAdapter
	# 覆写方法
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/demo.html").setViewName("/demo.jsp");
		}

--------------------------------
Spring-boot View				|
--------------------------------
	# Jsp在内嵌的Servlet容器上运行也许有一些问题(官方不建议)
	# 所以建议以其他的模版引擎来运行
	# 关于 视图层技术单独列笔记讲


--------------------------------
Spring-boot 添加context-param	|
--------------------------------
	# 注册 ServletContextInitializer
	1,全局的context-param
		@Bean
		public ServletContextInitializer initializer() {
			return new ServletContextInitializer() {
				public void onStartup(ServletContext servletContext) throws ServletException {
					servletContext.setInitParameter("ClassLoaderLeakPreventor.threadWaitMs", "1000");
				}
			};
		}

--------------------------------
Spring-boot 跨域支持			|
--------------------------------
	@Configuration  
	public class CorsConfig extends WebMvcConfigurerAdapter {  
		@Override  
		public void addCorsMappings(CorsRegistry registry) {  
			registry.addMapping("/**")  
					.allowedOrigins("*")  
					.allowCredentials(true)  
					.allowedMethods("GET", "POST", "DELETE", "PUT")  
					.maxAge(3600);  
		}  
	}  
--------------------------------
Spring-boot 自定义参数类型转换器|
--------------------------------
	# 注册组件
		ConversionServiceFactoryBean 
		@Bean
		public ConversionServiceFactoryBean enumConversionService() {
			ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
			Set<Converter<?,?>> converters = new HashSet<>();
			converters.add(new EnumConverter());
			conversionServiceFactoryBean.setConverters(converters);
			return conversionServiceFactoryBean;
		}
		* Converter 参考springmvc知识点
		* '如果是Date转换器,记得要配置:spring.mvc.date-format'

--------------------------------
Spring-boot 注册WEB三大组件		|
--------------------------------
	# 当使用嵌入式的Servlet容器,需要使用三个组件的时候,有三种方式

	# 把Servlet,Filter,Listenner 声明为Spring Bean 而达到注册的效果
		@Bean
		public XxxServlet xxxServlet(){
			return new XxxServlet();
		}
		@Bean
		public XxxFilter xxxFilter(){
			return new XxxFilter();
		}
		@Bean
		public XxxListenner xxxListennr(){
			return new XxxListenner();
		}
		

		* 也可以在 组件实现上添加 @Component 注解,
		* 如果是 Filter, 还可以通过 @Order 来定义多个 Filter 的执行顺序, order 越小, 优先级越高

	# 注册对应的 RegistrationBean
		* 他们可以进行'参数'设置等操作
		* ServletRegistrationBean
		* FilterRegistrationBean
		* ServletListenerRegistrationBean
		
		@Configuration
		public class ServletConfig {
			/**
			 * 注册Servlet
			 * @return
			 */
			@Bean
			public ServletRegistrationBean myServlet(){
				return new ServletRegistrationBean(new TestServlet(),"/test/test");
			}
			
			/**
			 * 注册Filter
			 * @return
			 */
			@Bean
			public FilterRegistrationBean myFilter(){
				FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
				filterRegistrationBean.setFilter(new TestFileter());
				filterRegistrationBean.setOrder(2);
				return filterRegistrationBean;
			}
			
			/**
			 * 注册Listnner
			 * @return
			 */
			@Bean
			public ServletListenerRegistrationBean<MyListenner> myListenner(){
				return new ServletListenerRegistrationBean(new MyListenner());
			}
		}
	
	# 使用servlet3.0的注解
		@ServletComponentScan
			# 在 SpringBootApplication 上使用@ServletComponentScan 
			# Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
	