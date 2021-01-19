----------------------------------
freemarker						  |
----------------------------------
	# 官方依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-freemarker</artifactId>
		</dependency> 
	
	# 配置项:FreeMarkerAutoConfiguration
spring:
  freemarker:
    # 是否开启，默认:true
    enabled: true
    # 是否开启模版引擎缓存，默认:false
    cache: true
    # 默认:text/html
    content-type: text/html
    # 编码，默认: urf-8
    charset: utf-8
    # 指定使用模板的视图列表，数组
    view-names:
      - views
    # 是否检查模板引擎目录是否存在，默认:true
    check-template-location: true
    # 视图前缀
    prefix: /temp
    # 视图后缀 
    suffix: .ftl
    # 使用该属性引用到request对象(实质上是:RequestContext 对象)
    request-context-attribute: req
    # 是否把request域的属性添加到模板引擎，默认:false
    expose-request-attributes: true
    # 是否把session域的属性添加到模板引擎，默认:false
    expose-session-attributes: true
    # request的属性是否可以覆盖controller的model的同名项。默认:false,如果发生同名属性覆盖的情况会抛出异常
    allow-request-override: true
    # session的属性是否可以覆盖controller的model的同名项。默认 false,如果发生同名属性覆盖的情况会抛出异常
    allow-session-override: false
    # 是否要暴露spring提供的宏，默认:true: /org/springframework/web/servlet/view/freemarker/spring.ftl
    expose-spring-macro-helpers: true
    # 模板引擎加载目录，默认:classpath:/templates/
    template-loader-path:
      - classpath:/templates/
    # 是否优先从文件系统加载模板引擎，支持热加载。默认:true
    prefer-file-system-access: true
    # freemarker中Configuration设置的配置。也就是定义在:freemarker.coreConfigurable 中的那些静态变量值
    settings:
      datetime_format: yyyy-MM-dd HH:mm:ss

	
	# 一般设置
spring:
  freemarker:
    enabled: true
    cache: false
    content-type: text/html
    charset: utf-8
    suffix: .ftl
	# 引用request
    request-context-attribute: request
	# 暴露request域中的属性
    expose-request-attributes: true
	# 暴露session域中的属性
    expose-session-attributes: true
	expose-spring-macro-helpers: true
    check-template-location: true
    template-loader-path:
      - classpath:/templates/
    settings:
      datetime_format: yyyy-MM-dd HH:mm:ss
	
----------------------------------
配置							  |
----------------------------------
	import javax.annotation.PostConstruct;
	import javax.servlet.ServletContext;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Configuration;

	import freemarker.template.TemplateModelException;

	@Configuration
	public class FreemarkerConfiguration {

		@Autowired
		private ServletContext servletContext;
		
		@Autowired
		private freemarker.template.Configuration configuration;
		
		@PostConstruct
		public void configuration() throws TemplateModelException {
			//自定义配置信息
			this.configuration.setSharedVariable("ctx", this.servletContext.getContextPath());
		}
	}