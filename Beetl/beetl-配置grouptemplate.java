---------------------------
GroupTeamplate				|
---------------------------
	# Beetl建议通过配置文件配置配置GroupTemplate
	# 主要考虑到未来可能IDE插件会支持Beetl模板，模板的属性，和函数等如果能通过配置文件获取，将有助于IDE插件识别。
	# 配置GroupTemplate有俩种方法
		1,配置文件
		2,API
	

---------------------------
GroupTeamplate-配置文件		|
---------------------------
	# 配置文件: 默认配置在/org/beetl/core/beetl-default.properties 里
	# Beetl首先加载此配置文件，然后再加载classpath里的beetl.properties,并用后者覆盖前者。
	# 配置文件分为三部分
	1,基本配置
		
	2,资源类配置
		RESOURCE_LOADER=org.beetl.core.resource.ClasspathResourceLoader
			# 资源配置，resource后的属性只限于特定ResourceLoader
			# 在beetl与其他框架集成的时候,模版加载器不一定根据这个配置(比如Spring,他的RESOURCE_LOADER以spring的配置为准)

		RESOURCE.root= /
			#  指定模版的目录,相对于 classpath, '/'表示就在根目录

		RESOURCE.autouCheck= true
			# 是否检测文件变化,默认为 true ,开发环境下自动检测模板是否更改
		
	3,扩展部分
		FN.date = org.beetl.ext.fn.DateFunction
		FN.nvl = org.beetl.ext.fn.NVLFunction
		.................
			# 内置的方法
		
		FNP.strutil = org.beetl.ext.fn.StringUtil
			# 内置的功能包

		FT.dateFormat =  org.beetl.ext.format.DateFormat
		FT.numberFormat =  org.beetl.ext.format.NumberFormat
		.................
			# 内置的格式化函数

		FTC.java.util.Date = org.beetl.ext.format.DateFormat
		FTC.java.sql.Date = org.beetl.ext.format.DateFormat
			# 内置的默认格式化函数

		TAG.include= org.beetl.ext.tag.IncludeTag
		TAG.includeFileTemplate= org.beetl.ext.tag.IncludeTag
		TAG.layout= org.beetl.ext.tag.LayoutTag
		TAG.htmltag= org.beetl.ext.tag.HTMLTagSupportWrapper
			# 标签类
		
		* FN前缀-表示Function
		* FNP前缀-表示FunctionPackage工具包
		* FT-表示format函数
		* FTC-表示指定类的默认Format函数
		* TAG-表示标签类。
		* Beetl强烈建议通过配置文件加载扩展。以便随后IDE插件能识别这些注册函数

---------------------------
GroupTeamplate-API			|
---------------------------
	# Configuration类加载，因此加载完成后，也可以通过此类API来修改配置信息
	# 通过调用GroupTemplate提供的方法来注册函数，格式化函数，标签函数等