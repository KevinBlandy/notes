StringTemplateResourceLoader 
	# 字符串模板加载器，用于加载字符串模板
FileResourceLoader
	# 文件模板加载器，需要一个根目录作为参数构造
ClasspathResourceLoader
	# 文件模板加载器，模板文件位于Classpath里
WebAppResourceLoader
	# 用于webapp集成，假定模板根目录就是WebRoot目录
MapResourceLoader 
	# 可以动态存入模板
CompositeResourceLoader 
	# 混合使用多种加载方式

Configuration
	# 默认情况下,该类总是会先去加载默认的文件
	# 默认文件地址:/org/beetl/core/beetl-default.properties
	configuration.addPkg(String package);
		* 添加 package 地址,在模版中直接调用该包下的JAVA类不需要添加包名,直接写类名

GroupTemplate 
	setSharedVars(Map<String, Object> sharedVars);
		* 设置共享变量,在任何模版,任何地方都可以访问到
	enableStrict();
		* 禁用脚本,其实就是开启了严格MVC模式
	registerVirtualAttributeClass(Class<?> clazz,VirtualClassAttribute virtualClassAttribute);
		* 为某一个类,注册虚拟属性
	registerVirtualAttributeEval(VirtualAttributeEval virtualAttributeEval);
		* 为某一堆类注册虚拟属性
	registerTag(String tagName,Class<?> clazz);
		* 注册一个tag,tagName 表示标签名称,clazz表示 Tag 实现类

Template 
	tempalte.render() 
		* 返回渲染结果
	template.renderTo(Writer writer) 
		* 渲染结果输出到Writer里
	template.renderTo(OutputStream outputStream) 
		* 渲染结果输出到OutputStream里

Context
	# 在自定义方法,格式化函数中实现接口的参数
	# 代表模板上下文
		getGlobal(String key);
			* 获取全局变量
			* UserEntity userEntity = (UserEntity) ((SessionWrapper) context.getGlobal("session")).get(SessionCode.LOGIN_USER);




BeetlGroupUtilConfiguration 
	# 整合Spring/spring boot时需要给IOC管理的Bean
	setResourceLoader(ResourceLoader resourceLoader)
		* Beetl资源加载器，如果未指定，会自动依据ApplicationContext和配置文件识别
		* 

BeetlSpringViewResolver 
	# 整合Spring/spring boot时视图解析器
	setContentType(String contentType);
		* 不多解释
	

