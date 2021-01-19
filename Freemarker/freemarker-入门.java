--------------------
freemarker - 入门	|
--------------------
	# 文档
		http://freemarker.foofun.cn/toc.html

	# Maven
		<dependency>
			<groupId>org.freemarker</groupId>
			<artifactId>freemarker</artifactId>
			<version>2.3.28</version>
		</dependency>
	
	
	# Demo
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

		//设置模板路径
		configuration.setServletContextForTemplateLoading(super.getServletContext()	, "/WEB-INF/templates");
		
		//设置读取默认的编码
		configuration.setDefaultEncoding("UTF-8");
		
		//设置输出编码
		configuration.setOutputEncoding("UTF-8");
		
		//设置模板异常处理器,抛出异常,由程序处理
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		
		try {
			//设置模板引擎变化检测的间隔时间
			configuration.setSetting(Configuration.TEMPLATE_UPDATE_DELAY_KEY_SNAKE_CASE, "0ms");
			//Date的datetime默认格式化
			configuration.setSetting(Configuration.DATETIME_FORMAT_KEY_SNAKE_CASE, "yyyy-MM-dd HH-mm-ss ");
			//禁用本地化模板查找
			configuration.setLocalizedLookup(false);
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		//获模板
		Template template = this.configuration.getTemplate("index/index.ftl");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> hashMap = new HashMap<>();
		hashMap.put("x", 1);
		try {
			//填充对象并且渲染到目标 OutputStream
			template.process(hashMap, resp.getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	
	# 保留关键字
		true：布尔值"true"
		false：布尔值"false"
		gt：比较运算符"大于"
		gte：比较运算符"大于或等于"
		lt：比较运算符"小于"
		lte：比较运算符"小于或等于"
		as：由少数指令使用
		in：由少数指令使用
		using：由少数指令使用