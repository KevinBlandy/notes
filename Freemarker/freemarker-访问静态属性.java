----------------------------------
使用BeansWrapper访问静态属性	  |
----------------------------------
	# 代码配置
		BeansWrapper wrapper = new BeansWrapper(freemarker.template.Configuration.VERSION_2_3_28);
        TemplateHashModel templateHashModel = wrapper.getStaticModels();

        this.configuration.setSharedVariable("static", templateHashModel);
	
		* wrapper.getStaticModels() 会返回一个 TemplateHashModel
		* 它会利用反射公开所有的静态方法和静态成员变量, 包括 final 和 非final 的

		* 将这个 HashModel 添加到全局共享变量
	
	# 在视图中调用静态方法
		${static['org.springframework.context.i18n.LocaleContextHolder'].getLocale()}
		
		* 使用访问hash的方法去访问到类, 然后再调用它的静态属性/方法
