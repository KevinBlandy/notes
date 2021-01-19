----------------------------------
使用BeansWrapper访问枚举		  |
----------------------------------
	# 跟访问静态方法一样
		this.configuration.setSharedVariable("_enum", new BeansWrapper(freemarker.template.Configuration.VERSION_2_3_29).getEnumModels());
	
	# 模板引擎
		${_enum['org.springframework.http.HttpMethod'].GET}
		