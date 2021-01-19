----------------------------
Beetl-模版资源加载器		|
----------------------------
	# 资源加载器是根据String值获取Resource实例的工场类
	# 同时资源加载器还要负责响应模板引擎询问模板是否变化的调用。
	# 对于新手来说，无需考虑模板资源加载器如何实现，只需要根据自己场景选择系统提供的三类模板资源加载器即可

----------------------------
Beetl-字符串模版资源加载器	|
----------------------------
	# StringTemplateResourceLoader
	# 在创建 GroupTempalte 过程中,如果传入的是 StringTemplateResourceLoader
	# 则允许通过调用 getTemplate(String template); 来uoqu模版实例对象
		StringTemplateResourceLoader stringTemplateResourceLoader = new StringTemplateResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate groupTemplate = new GroupTemplate(stringTemplateResourceLoader,configuration);
		Template template = groupTemplate.getTemplate("hello,${name}");
	
----------------------------
Beetl-文件资源模版资源加载器|
----------------------------
	# FileResourceLoader
	# 通常情况下,模版资源是以文件形式管理的,集中放在某一个文件目录下
		* 如: webapp/WEB-INF/template
	# 因此可以使用 FileResourceLoader 来加载模版实例
		String root = "E:\\workspace\\kevin-beetl\\src\\main\\resources\\template";
		/*
			指定文件的目录,编码默认就是UTF-8.可以不用指定
		*/
		FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, configuration);
		/*
			从目录中获取模板文件,也可以是多级目录,使用目录分隔符
		*/
		Template template = groupTemplate.getTemplate("/hello.txt");
		template.binding("name","KevinBlandy");
		String result = template.render();
		System.out.println(result);

		* hello.txt
			helo,${name}
	
--------------------------------
Beetl-Classpath资源模板加载器	|
--------------------------------
	# ClasspathResourceLoader
	# 通常清空下,模版资源是打包到jar文件或者和Class放在一起
	# 可以使用ClasspathResourceLoader来加载模板实例
		/*
			classpath目录下的  template,也就是 src/main/resources/template
			此处用空构造函数，表示搜索路径是根路径，且字符集默认字符集UTF-8.
		*/
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/template");
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, configuration);
		/*
			template目录下的 hello.txt文件
		*/
		Template template = groupTemplate.getTemplate("/hello.txt");
		template.binding("name","KevinBlandy");
		String result = template.render();
		System.out.println(result);

		* * hello.txt
			helo,${name}

--------------------------------
Beetl-WebApp资源模板加载器		|
--------------------------------
	# WebAppResourceLoader
	# 用于web应用的资源模板加载器，默认根路径是WebRoot目录。
	# 也可以通过制定root属性来设置相对于WebRoot的的模板根路径
	# 从安全角考虑，建议放到WEB-INF目录下
		Configuration configuration = Configuration.defaultConfiguration();
		WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader();
		GroupTemplate groupTemplate = new GroupTemplate(webAppResourceLoader, configuration);
	# WebAppResourceLoader 假定 beetl.jar 是位于 WEB-INF/lib 目录下，因此，可以通过WebAppResourceLoader类的路径来推断出WebRoot路径从而指定模板根路径。
	# 所有线上环境一般都是如此，如果是开发环境或者其他环境不符合此假设，你需要调用 resourceLoader.setRoot() 来指定模板更路径

--------------------------------
Beetl- 使用额外的资源加载器		|
--------------------------------
	# 某些情况下，模板来源不止一处，GroupTemplate配置了一个默认的资源加载器，
	# 如果通过gt.getTemplate(key),将调用默认的ResourceLoader，获取模板内容，然后转化为beetl脚本放入到缓存里。
	# 你也可以传入额外的资源管理器加载模板，通过调用gt.getTemplate(key,otherLoader)来完成;

	GroupTemplate gt = new GroupTemplate(conf,fileLoader)
	//自定义，参考下一节
	MapResourceLoader dbLoader = new MapResourceLoader(getData());
	Template t = gt.getTemplate("db:1", dbLoader);

	private Map getData(){
			Map data = new HashMap();
			data.put("db:1", "${a}");
			return data;
	}
	对于更复杂的模板资源来源，也可以自定义一个资源加载来完成，

--------------------------------
自定义资源模板加载器			|
--------------------------------
	# 有时候模板可能来自文件系统不同目录，或者模板一部分来自某个文件系统，另外一部分来自数据库，
	# 还有的情况模板可能是加密混淆的模板，此时需要自定义资源加载
	# 继承 ResouceLoader才能实现模板功能，单独列笔记