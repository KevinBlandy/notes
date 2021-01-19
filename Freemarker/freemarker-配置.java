-----------------------
freemarker-配置			|
-----------------------
	# 实例化
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

		*  它应该是一个单例对象
	

	# 共享变量
		* 是为所有模板定义的变量,可以使用 setSharedVariable 方法向配置中添加共享变量
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			cfg.setSharedVariable("warp", new WarpDirective());
			cfg.setSharedVariable("company", "Foo Inc.");
		
		* 用户自定义的同名变量会覆盖全局变量
		* 在多线程环境中使用,不要使用 TemplateModel 实现类来作为共享变量, 因为它是不是线程安全的

		* 出于向后兼容的特性，共享变量的集合初始化时 (就是对于新的 Configuration 实例来说)不能为空, 它包含下列用户自定义指令(用户自定义指令使用时需要用 @ 来代替#)

			capture_output		freemarker.template.utility.CaptureOutput
			compress			freemarker.template.utility.StandardCompress
			html_escape			freemarker.template.utility.HtmlEscape
			normalize_newlines	freemarker.template.utility.NormalizeNewlines
			xml_escape			freemarker.template.utility.XmlEscape
	
	# 配置信息的设置
		* 配置信息可以被想象成3层(Configuration, Template,Environment), 级别由低到高,高级别会覆盖低级别的配置
		* Configuration 的配置
			* 原则上设置配置信息时使用 Configuration 对象的setter方法
			*  在真正使用 Configuration 对象 (通常在初始化应用程序时)之前来配置它,后面必须将其视为只读的对象

		* Template 的配置
			
		* Environment 的配置
			* 使用Java API:使用 Environment 对象的setter方法
			* 当然想要在模板执行之前来做,然后当调用 myTemplate.process(...) 时会遇到问题, 因为在内部创建 Environment 对象后立即就执行模板了, 导致没有机会来进行设置
			* 这个问题的解决可以用下面两个步骤进行
				Environment env = myTemplate.createProcessingEnvironment(root, out);	//创建env
				env.setLocale(java.util.Locale.ITALY);			//设置属性
				env.setNumberFormat("0.####");		
				env.process();  // 渲染模板

			* 在模板中(通常这被认为是不好的做法)直接使用 setting 指令
				<#setting locale="it_IT">
				<#setting number_format="0.####">

	
	# 模板加载
		*  从文件路径加载
			void setDirectoryForTemplateLoading(File dir);
		
		* 从classpath路径加载
			void setClassForTemplateLoading(Class cl, String prefix);

			* 传入的class参数会被 Class.getResource() 用来调用方法来找到模板
			* prefix 是给模板的名称来加前缀的
			* 类加载机制是首选用来加载模板的方法,通常情况下,从类路径下加载文件的这种机制,要比从文件系统的特定目录位置加载安全而且简单
			* 在最终的应用程序中,所有代码都使用 .jar 文件打包也是不错的, 这样用户就可以直接执行包含所有资源的 .jar 文件了
			* 如果 prefix 以"/"开头,则从classpath根路径开始搜索,否则以当前类的路径开始搜索

			* 最佳实践
				//配置
				configuration.setClassForTemplateLoading(this.getClass(), "templates/");
				//模板目录
				{this.getClass()..getName()}/templates/
				//获取模板
				Template template = this.configuration.getTemplate("foo.ftl");


				//配置
				configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
				//模板目录
				{classpath}/templates/
				//获取模板
				Template template = this.configuration.getTemplate("foo.ftl");



		* 相对于WEB应用的'/WEB-INF目录的上级目录'(项目路径)的路径加载
			void setServletContextForTemplateLoading(Object servletContext, String path);

			* 需要Web应用的上下文和一个基路径作为参数, 这个基路径是Web应用根路径(WEB-INF目录的上级目录)的相对路径
			* 该加载方法对非war包的程序没用,因为它使用了 ServletContext.getResource() 方法来访问模板
			* 如果忽略了第二个参数(或使用了""),那么就可以混合存储静态文件(.html.jpg等) 和 .ftl 文件,只是只有 .ftl 文件会被模板渲染
				* 当然必须在 WEB-INF/web.xml 中配置一个Servlet来处理URI格式为 *.ftl 的用户请求,否则客户端无法获取到模板
				
			* 最佳实践
				//配置
				configuration.setServletContextForTemplateLoading(super.getServletContext()	, "/WEB-INF/templates");
				//模板目录
				/WEB-INF/templates
				//获取模板
				Template template = this.configuration.getTemplate("foo.ftl");

		
		* 从多个位置加载模板
			* TODO
		
		*  从URL加载模板
			* TODO
		
	#  模板缓存
		* 会缓存模板的(假设使用 Configuration 对象的方法来创建 Template 对象)
		* 这就是说当调用 getTemplate方法时,FreeMarker不但返回了 Template 对象,而且还会将它存储在缓存中
		* 当下一次再以相同(或相等)路径调用 getTemplate 方法时, 那么它只返回缓存的 Template 实例, 而不会再次加载和解析模板文件了
		
		* 如果更改了模板文件,当下次调用模板时，FreeMarker 将会自动重新载入和解析模板
		* 然而,要检查模板文件是否改变内容了是需要时间,可以配置这个时间 其默认值是5秒,如果想要看到模板立即更新的效果,那么就要把它设置为0
			configuration.setSetting(Configuration.TEMPLATE_UPDATE_DELAY_KEY_SNAKE_CASE, "0ms");

		* 某些模板加载器也许在模板更新时可能会有问题, 例如,典型的例子就是在基于类加载器的模板加载器就不会注意到模板文件内容的改变
		

		* 将一个缓存模板清除的实际应用策略是由配置的属性 cache_storage 来确定的
			//TODO configuration.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250))		?二级缓存

		* 还可以使用 Configuration 对象的 clearTemplateCache 方法手动清空缓存
			configuration.clearTemplateCache();

	
	# 错误控制
		*  FreeMarker 发生的异常,可以分为如下几类
		
		* 初始化异常
		* 加载解析模板异常
			* 调用了 Configuration.getTemplate(...) 方法,FreeMarker就要把模板文件加载到内存中然后来解析它 (除非模板已经在 Configuration 对象中被 缓存 了)。
			* 在这期间,有两种异常可能发生:
				- 因模板文件没有找到而发生的 IOException 异常,或在读取文件时发生其他的I/O问题,比如没有读取文件的权限,或者是磁盘错误
				- 根据FTL语言的规则,模板文件发生语法错误时会导致 freemarker.core.ParseException 异常
		* 渲染模板异常
			* 是当调用了 Template.process(...) 方法时会发生的两种异常
				- 当试图写入输出对象时发生错误而导致的 IOException 异常
				- 当执行模板时发生的其它问题而导致的 freemarker.template.TemplatException 异常
					* 比如,一个频繁发生的错误,就是当模板引用一个不存在的变量
		
		* 默认情况下,当 TemplatException 异常发生时,FreeMarker会用普通文本格式在输出中打印出FTL的错误信息和堆栈跟踪信息, 然后再次抛出 TemplatException 异常而中止模板的执行 
		* 可以捕捉到 Template.process(...) 方法抛出的异常,而这种行为是可以定制的,FreeMarker也会经常写 TemplatException 异常的 日志

		*  FreeMarker 本身带有这些预先编写的错误控制器

			TemplateExceptionHandler.DEBUG_HANDLER
				* 打印堆栈跟踪信息(包括FTL错误信息和FTL堆栈跟踪信息)和重新抛出的异常
				* 这是默认的异常控制器(也就是说,在所有新的 Configuration 对象中,它是初始化好的)
				* '打印页面'

			TemplateExceptionHandler.HTML_DEBUG_HANDLER
				* 和 DEBUG_HANDLER 相同，但是它可以格式化堆栈跟踪信息, 那么就可以在Web浏览器中来阅读错误信息
				* 当你在制作HTML页面时，建议使用它而不是 DEBUG_HANDLER。
				* '打印html页面'

			TemplateExceptionHandler.IGNORE_HANDLER
				* 简单地压制所有异常(但是要记住，FreeMarker 仍然会写日志)。 它对处理异常没有任何作用，也不会重新抛出异常。
				* '不处理,不抛出'

			TemplateExceptionHandler.RETHROW_HANDLER
				* 简单重新抛出所有异常而不会做其它的事情, 这个控制器对Web应用程序(假设你在发生异常之后不想继续执行模板)来说非常好, 因为它在生成的页面发生错误的情况下,给了你很多对Web应用程序的控制权 (因为FreeMarker不向输出中打印任何关于该错误的信息)
				* '再次抛出异常,可以被调用者捕获而自定义逻辑'
			
		
-----------------------
freemarker-配置项		|
-----------------------