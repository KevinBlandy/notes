---------------------
i18n ���ʻ�			 |
---------------------
	# ���
		LocaleResolver
			AbstractLocaleResolver
				AbstractLocaleContextResolver
					FixedLocaleResolver
					SessionLocaleResolver
			LocaleContextResolver
				AbstractLocaleContextResolver
					FixedLocaleResolver(�̶���)
					SessionLocaleResolver(����session)
				CookieLocaleResolver(����cookie)
			AcceptHeaderLocaleResolver(����Header)


			LocaleChangeInterceptor(���ʻ���������)
	
	# ���� LocaleResolver ʵ��
		* ������Ҫѡ��ͬ��ʵ��(����Cookie)
			@Bean
			public LocaleResolver localeResolver() {
				CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
				cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
				//���������� cookie ��һ�������
				return cookieLocaleResolver;
			}

	# ���� LocaleChangeInterceptor ʵ��
		* ������Ҫ���ʻ�������
		   @Bean
			public LocaleChangeInterceptor localeChangeInterceptor() {
				LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
				// ָ���л����Ե��������
				localeChangeInterceptor.setParamName("_lang");
				return localeChangeInterceptor;
			}
		
		* �ǵ�Ҫ�� MvcConfigurator �����ã��Լ�����Ҫ���ص�·��
	
	# ��classpathĿ¼�´����ļ�
		i18n
		|-message.properties
		|-message_zh_CN.properties
		|-message_en_US.properties
		
		* message ���ļ���ǰ׺
		* _en_US ���ļ���local

	# yaml����
		spring:
		  # ���ʻ���������Ϣ
		  messages:
			# ָ����Դ�ļ����Լ��ļ���ǰ׺
			basename: i18n/message
			fallback-to-system-locale: false
		
		  # ʹ��freemarkerģ������
		  freemarker:
			enabled: true
			content-type: text/html
			charset: utf-8
			suffix: .ftl
			request-context-attribute: request
			expose-request-attributes: true
			expose-session-attributes: true
			check-template-location: true
			# һ��Ҫ��¶spring�ṩ�ĺ�
			expose-spring-macro-helpers: true
			template-loader-path:
			  - classpath:/templates/
			  - classpath:/email/
			settings:
			  datetime_format: yyyy-MM-dd HH:mm:ss
	
	# ����ͼ�е�ʹ�ù��ʻ�������
		<#import "/spring.ftl" as spring/>

		<#-- ����local������ȡname -->
		<@spring.message code='name'/>
	
	# �ڳ�����ʹ�ù��ʻ����֣�����ע�� MessageSource ��ʹ��
		    @Autowired
			private MessageSource messageSource;
			
			@GetMapping
			public ModelAndView index (HttpServletRequest request, HttpServletResponse response) {
				// ��ȡname
				log.info("{}", messageSource.getMessage("name", null, LocaleContextHolder.getLocale()));
				return new ModelAndView("index/index");
			}

	# ʹ����������л�����
		?_lang=zh_CN
		?_lang=en_US
	

	# ��ϸ��������
		MessageSourceProperties

	
---------------------
LocaleContextHolder	 |
---------------------
	# ��ǰ���Ի��������� ThreadLocal
	# �ṩ��һЩ��̬����
		Locale getLocale()
			* ��ȡ��ǰ������ Locale ����
			* ������������ͼҳ���жϵ�ǰ�����Ի���
		

		
	