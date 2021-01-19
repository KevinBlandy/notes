---------------------
i18n 国际化			 |
---------------------
	# 类库
		LocaleResolver
			AbstractLocaleResolver
				AbstractLocaleContextResolver
					FixedLocaleResolver
					SessionLocaleResolver
			LocaleContextResolver
				AbstractLocaleContextResolver
					FixedLocaleResolver(固定的)
					SessionLocaleResolver(根据session)
				CookieLocaleResolver(根据cookie)
			AcceptHeaderLocaleResolver(根据Header)


			LocaleChangeInterceptor(国际化的拦截器)
	
	# 创建 LocaleResolver 实例
		* 根据需要选择不同的实现(建议Cookie)
			@Bean
			public LocaleResolver localeResolver() {
				CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
				cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
				//还可以设置 cookie 的一大堆属性
				return cookieLocaleResolver;
			}

	# 创建 LocaleChangeInterceptor 实例
		* 拦截需要国际化的请求
		   @Bean
			public LocaleChangeInterceptor localeChangeInterceptor() {
				LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
				// 指定切换语言的请求参数
				localeChangeInterceptor.setParamName("_lang");
				return localeChangeInterceptor;
			}
	
	# 在classpath目录下创建文件
		i18n
		|-message.properties
		|-message_zh_CN.properties
		|-message_en_US.properties
		
		* message 是文件的前缀
		* _en_US 是文件的local

	# yaml配置
		spring:
		  messages:
			# 指定资源文件夹以及文件的前缀
			basename: i18n/message
		  # 使用freemarker模版引擎
		  freemarker:
			enabled: true
			content-type: text/html
			charset: utf-8
			suffix: .ftl
			request-context-attribute: request
			expose-request-attributes: true
			expose-session-attributes: true
			check-template-location: true
			# 一定要暴露spring提供的宏
			expose-spring-macro-helpers: true
			template-loader-path:
			  - classpath:/templates/
			  - classpath:/email/
			settings:
			  datetime_format: yyyy-MM-dd HH:mm:ss
	
	# 在视图中的使用国际化的文字
		<#import "/spring.ftl" as spring/>

		<#-- 根据local环境读取name -->
		<@spring.message code='name'/>
	
	# 使用请求参数切换环境
		?_lang=zh_CN
		?_lang=en_US

	
---------------------
LocaleContextHolder	 |
---------------------
	# 当前语言环境的容器 ThreadLocal
	# 提供了一些静态方法
		Locale getLocale()
			* 获取当前环境的 Locale 对象
			* 可以用于在视图页面判断当前的语言环境
		

		
	