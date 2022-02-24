----------------------------------
freemarker						  |
----------------------------------
	# �ٷ�����
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-freemarker</artifactId>
		</dependency> 
	
	# ������:FreeMarkerAutoConfiguration
spring:
  freemarker:
    # �Ƿ�����Ĭ��:true
    enabled: true
    # �Ƿ���ģ�����滺�棬Ĭ��:false
    cache: true
    # Ĭ��:text/html
    content-type: text/html
    # ���룬Ĭ��: urf-8
    charset: utf-8
    # ָ��ʹ��ģ�����ͼ�б�����
    view-names:
      - views
    # �Ƿ���ģ������Ŀ¼�Ƿ���ڣ�Ĭ��:true
    check-template-location: true
    # ��ͼǰ׺
    prefix: /temp
    # ��ͼ��׺ 
    suffix: .ftl
    # ʹ�ø��������õ�request����(ʵ������:RequestContext ����)
    request-context-attribute: req
    # �Ƿ��request���������ӵ�ģ�����棬Ĭ��:false
    expose-request-attributes: true
    # �Ƿ��session���������ӵ�ģ�����棬Ĭ��:false
    expose-session-attributes: true
    # request�������Ƿ���Ը���controller��model��ͬ���Ĭ��:false,�������ͬ�����Ը��ǵ�������׳��쳣
    allow-request-override: true
    # session�������Ƿ���Ը���controller��model��ͬ���Ĭ�� false,�������ͬ�����Ը��ǵ�������׳��쳣
    allow-session-override: false
    # �Ƿ�Ҫ��¶spring�ṩ�ĺ꣬Ĭ��:true: /org/springframework/web/servlet/view/freemarker/spring.ftl
    expose-spring-macro-helpers: true
    # ģ���������Ŀ¼��Ĭ��:classpath:/templates/
    template-loader-path:
      - classpath:/templates/
    # �Ƿ����ȴ��ļ�ϵͳ����ģ�����棬֧���ȼ��ء�Ĭ��:true
    prefer-file-system-access: true
    # freemarker��Configuration���õ����á�Ҳ���Ƕ�����:freemarker.coreConfigurable �е���Щ��̬����ֵ
    settings:
      datetime_format: yyyy-MM-dd HH:mm:ss

	
	# һ������
spring:
  freemarker:
    enabled: true
    cache: false
    content-type: text/html
    charset: utf-8
    suffix: .ftl
	# ����request
    request-context-attribute: request
	# ��¶request���е�����
    expose-request-attributes: true
	# ��¶session���е�����
    expose-session-attributes: true
	# ��¶�ٷ��ĺ�
    expose-spring-macro-helpers: true
    check-template-location: true
	# ���ȼ����ļ�ϵͳ��ģ��
	prefer-file-system-access: true
    template-loader-path:
	  - file:${user.dir}/templates/
      - classpath:/templates/
    settings:
      datetime_format: yyyy-MM-dd HH:mm:ss
	  template_update_delay: 30m # ģ������ˢ��ʱ��
      default_encoding: utf-8 # Ĭ�ϱ���
	
	# Demo
		* ģ���ļ�
			|-src/main/resources
				|-templates
					|-index
						|-index.ftl

		* ����
			import org.springframework.stereotype.Controller;
			import org.springframework.web.bind.annotation.GetMapping;
			import org.springframework.web.bind.annotation.RequestMapping;
			import org.springframework.web.servlet.ModelAndView;

			@Controller
			@RequestMapping
			public class IndexController {

				@GetMapping(value = {"/index", "/"})
				public ModelAndView index (){
					return new ModelAndView("index/index");
				}
			}

	
----------------------------------
����							  |
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
			// ���ȫ�ֱ���
			this.configuration.setSharedVariable("ctx", this.servletContext.getContextPath());

			// ���ģ�建��
			this.configuration.clearTemplateCache();
		}
	}