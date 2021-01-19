-----------------------
Beetl-入门				|
-----------------------
	# 国人开源模版引擎
	# 吹牛逼说性能是Freemarker的6倍,JSP的两倍
	# 官网
		http://www.ibeetl.com/
	# 作者:闲.大赋
	# Maven
		<dependency>
			<groupId>com.ibeetl</groupId>
			<artifactId>beetl</artifactId>
			<version>2.7.13</version>
		</dependency>
	
	# Hello World
		StringTemplateResourceLoader stringTemplateResourceLoader = new StringTemplateResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate groupTemplate = new GroupTemplate(stringTemplateResourceLoader,configuration);
		Template template = groupTemplate.getTemplate("hello,${name}");
		template.binding("name","KevinBlandy");
		String result = template.render();
		System.out.println(result);


-----------------------
Beetl-常用配置			|
-----------------------
	DELIMITER_STATEMENT_START=@
	DELIMITER_STATEMENT_END=
	RESOURCE.root= /
		* 模版目录
	RESOURCE.autoCheck = TRUE
		* 上线的时候,记得修改为false,这样有助于提高性能
	DIRECT_BYTE_OUTPUT = TRUE
		* 指定IO输出模式，默认是FALSE,即通常的字符输出，
		* 在考虑高性能情况下，可以设置成true。详细请参考高级用法
