--------------------------------
Spring-boot WEB��Ŀ				|
--------------------------------
	# Spring boot ��WEB�Ŀ����ṩ��֧��
	# spring-boot-starter-web,�ṩ��Ƕ��ʽ��Tomcat��Springmvc������
	# Web��ص��Զ�������:spring-boot-autoconfigure.jar �� org.springframework.boot.autoconfigure.web ��
		ServerPropertiesAutoConfiguration �� ServerProperties
			* �Զ�������ǶServlet����
		
		HttpEncodingAutoConfiguration �� HttpEncodingProperties
			* �Զ�����http�ı���
		
		MultipartAutoConfiguration �� MultipartProperties
			* �Զ������ϴ��ļ�������

		JacksonHttpMessageConvertersConfiguration 
			* ����,mappingJackson2HttpMessageConverter �� mappingJackson2XmlHttpMessageConverter
		
		WebMvcAutoConfiguration �� WebMvcProperties
			* ����spring mvc
	
	# Web��������
		WebProperties

--------------------------------
Spring-boot ������				|
--------------------------------
	# spring boot������Ĭ���� 
		HandlerInterceptorAdapter
		AbstractHandlerMapping
		UserRoleAuthorizationInterceptor
		LocaleChangeInterceptor
		ThemeChangeInterceptor
	
	# һЩ���õ����
		AsyncHandlerInterceptor
			void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
			* �����������Controller�����첽ִ��ʱ��ʼִ��


	2.����spring mvc�������� WebMvcConfigurerAdapter 
		* �Զ�����̳� WebMvcConfigurerAdapter 
			* ������Ϊ������,Ӧ����� :@Configuration ע��
			* Ҳ����ֱ���� @SpringBootApplication ȥʵ��
		
		* ��д addInterceptors ����,���������������

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				// ������������һ����������
				// addPathPatterns ����������ع���
				// excludePathPatterns �û��ų�����
				registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**").excludePathPatterns("/test/**");
				registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**").excludePathPatterns("/test/**");
				super.addInterceptors(registry);
			}
	

	
	3. ����һ�����õ�
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
			 * ��ȡ��mapping
			 * @param handlerMethod
			 * @return
			 */
			protected String getMapping(HandlerMethod handlerMethod){
				/**
				 * ��ȡ�����ϵĵ�һ��mapping
				 */

				String mapping = "";

				RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
				String[] methodMappings = methodRequestMapping.value();
				if (methodMappings != null && methodMappings.length > 0){
					System.err.println(methodMappings[0]);
					mapping = methodMappings[0];
				}

				/**
				 * ��ȡ���ϵĵ�һ��mapping
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
Spring-boot ��̬��Դ			|
--------------------------------
	# �Զ��������: addResourceHandlers �����ж��������¾�̬��Դ���Զ�����
	# ��·���ļ�
		* /static 
		* /public
		* /resources
		* /META-INF/resources
	# �����ļ����еľ�̬�ļ�,ͨ�����þ�̬��Դpattern,�����з���
		spring.mvc.static-path-pattern=/static/**	*/
	
	# webjar,�ľ�̬��Դӳ��
		* webjar,����jar����jar.
		* /META-INF/resources/webjars/ �µľ�̬�ļ�ӳ��Ϊ: /web/jar/**						*/

	# ��̬��Դ����

		* �������һ
			* �Զ���������,ʵ�� WebMvcConfigurerAdapter ,��д����
				@Override
				public void addResourceHandlers(ResourceHandlerRegistry registry) {
					//static ��̬��ԴĿ¼
					registry.addResourceHandler("/static/**")
					//src/main/resources/static
					.addResourceLocations("classpath:/static/")		
					//����d���µ�pic,
					.addResourceLocations("file:D:/pic/");
				}
			* һ�� ResourceHandler ӳ���˶�� ResourceLocations,���������ͬ·��,��ͬ�ļ�,��˭��ӳ��˭����
			* �������:static/index.js Ҫ������ pic/index.js ����

		* ���������
			* ��application.properties �������,���þ�̬��Դ�ķ���·��
				spring.mvc.static-path-pattern=/static/**								*/
				*  'ע��,���治���пո�,������ȫ��Ϊ�˴���� /** Java��ע�ͳ�ͻ'
			
			* ��application.properties �������,ָ����̬��Դ��λ��
				local.image.folder=d:/pic/
				spring.web.resources.static-locations[0]=classpath:/static/
				spring.web.resources.static-locations[1]=file:${local.image.folder}
	
	# ������
		ResourceProperties(������)
		WebProperties.Resources

--------------------------------
Spring-boot ��ͼӳ��			|
--------------------------------
	# �Զ���������,ʵ�� WebMvcConfigurerAdapter
	# ��д����
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/demo.html").setViewName("/demo.jsp");
		}

--------------------------------
Spring-boot View				|
--------------------------------
	# Jsp����Ƕ��Servlet����������Ҳ����һЩ����(�ٷ�������)
	# ���Խ�����������ģ������������
	# ���� ��ͼ�㼼�������бʼǽ�


--------------------------------
Spring-boot ���context-param	|
--------------------------------
	# ע�� ServletContextInitializer
	1,ȫ�ֵ�context-param
		@Bean
		public ServletContextInitializer initializer() {
			return new ServletContextInitializer() {
				public void onStartup(ServletContext servletContext) throws ServletException {
					servletContext.setInitParameter("ClassLoaderLeakPreventor.threadWaitMs", "1000");
				}
			};
		}

--------------------------------
Spring-boot ����֧��			|
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
Spring-boot �Զ����������ת����|
--------------------------------
	# ע�����
		ConversionServiceFactoryBean 
		@Bean
		public ConversionServiceFactoryBean enumConversionService() {
			ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
			Set<Converter<?,?>> converters = new HashSet<>();
			converters.add(new EnumConverter());
			conversionServiceFactoryBean.setConverters(converters);
			return conversionServiceFactoryBean;
		}
		* Converter �ο�springmvc֪ʶ��
		* '�����Dateת����,�ǵ�Ҫ����:spring.mvc.date-format'

--------------------------------
Spring-boot ע��WEB�������		|
--------------------------------
	# ��ʹ��Ƕ��ʽ��Servlet����,��Ҫʹ�����������ʱ��,�����ַ�ʽ

	# ��Servlet,Filter,Listenner ����ΪSpring Bean ���ﵽע���Ч��
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
		

		* Ҳ������ ���ʵ������� @Component ע��,
		* ����� Filter, ������ͨ�� @Order �������� Filter ��ִ��˳��, order ԽС, ���ȼ�Խ��

	# ע���Ӧ�� RegistrationBean
		* ���ǿ��Խ���'����'���õȲ���
		* ServletRegistrationBean
		* FilterRegistrationBean
		* ServletListenerRegistrationBean
		
		@Configuration
		public class ServletConfig {
			/**
			 * ע��Servlet
			 * @return
			 */
			@Bean
			public ServletRegistrationBean myServlet(){
				return new ServletRegistrationBean(new TestServlet(),"/test/test");
			}
			
			/**
			 * ע��Filter
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
			 * ע��Listnner
			 * @return
			 */
			@Bean
			public ServletListenerRegistrationBean<MyListenner> myListenner(){
				return new ServletListenerRegistrationBean(new MyListenner());
			}
		}
	
	# ʹ��servlet3.0��ע��
		@ServletComponentScan
			# �� SpringBootApplication ��ʹ��@ServletComponentScan 
			# Servlet��Filter��Listener ����ֱ��ͨ�� @WebServlet��@WebFilter��@WebListener ע���Զ�ע�ᣬ�����������롣
	