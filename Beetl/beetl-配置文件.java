-----------------------
Beetl默认的配置文件		|
-----------------------
	# 位于:/org/beetl/core/beetl-default.properties

	#######默认配置

	ENGINE=org.beetl.core.engine.FastRuntimeEngine
		* 引擎的默认实现类
	DELIMITER_PLACEHOLDER_START=${
	DELIMITER_PLACEHOLDER_END=}
	DELIMITER_STATEMENT_START=<%
	DELIMITER_STATEMENT_END=%>
		* 以上四行都是属于beetl边界符的定义
		* DELIMITER_STATEMENT_END= 或者 DELIMITER_STATEMENT_END=null 表示以回车为结尾符
	DIRECT_BYTE_OUTPUT = FALSE
		* 指定IO输出模式，默认是FALSE,即通常的字符输出，
		* 在考虑高性能情况下，可以设置成true。详细请参考高级用法
	HTML_TAG_SUPPORT = true
	HTML_TAG_FLAG = #
	HTML_TAG_BINDING_ATTRIBUTE = var
	NATIVE_CALL = TRUE
	TEMPLATE_CHARSET = UTF-8
		* 字符集的配置
	ERROR_HANDLER = org.beetl.core.ConsoleErrorHandler
		* 指定异常解析类
	NATIVE_SECUARTY_MANAGER= org.beetl.core.DefaultNativeSecurityManager
	RESOURCE_LOADER=org.beetl.core.resource.ClasspathResourceLoader
	MVC_STRICT = FALSE
		* 是否严格MVC

	### 资源配置，resource后的属性只限于特定ResourceLoader ####
	#classpath 跟路径
	RESOURCE.root= /
	#是否检测文件变化
	RESOURCE.autoCheck = TRUE
	#自定义脚本方法文件位置
	RESOURCE.functionRoot = functions
	#自定义脚本方法文件的后缀
	RESOURCE.functionSuffix = html
	#自定义标签文件位置
	RESOURCE.tagRoot = htmltag
	#自定义标签文件后缀
	RESOURCE.tagSuffix = tag

	#如果采用beetl集成的web应用，可以在渲染模板前调用如下类,此类必须实现WebRenderExt接口
	WEBAPP_EXT = 

	#允许html function or Tag 使用特殊的定界符
	FUNCTION_TAG_LIMITER=

	#####  扩展 ##############
	## 内置的方法
	FN.date = org.beetl.ext.fn.DateFunction
	FN.nvl = org.beetl.ext.fn.NVLFunction
	FN.debug = org.beetl.ext.fn.DebugFunction
	#兼容以前版本，用has代替
	FN.exist = org.beetl.ext.fn.CheckExistFunction
	FN.has = org.beetl.ext.fn.CheckExistFunction
	FN.printf = org.beetl.ext.fn.Printf
	FN.decode = org.beetl.ext.fn.DecodeFunction
	FN.assert = org.beetl.ext.fn.AssertFunction
	FN.print = org.beetl.ext.fn.Print
	FN.println = org.beetl.ext.fn.Println
	FN.trunc = org.beetl.ext.fn.TruncFunction
	#兼容以前版本 empty，用isEmpty代替
	FN.empty = org.beetl.ext.fn.EmptyFunction
	FN.qmark = org.beetl.ext.fn.QuestionMark
	FN.isEmpty = org.beetl.ext.fn.EmptyExpressionFunction
	FN.isNotEmpty = org.beetl.ext.fn.IsNotEmptyExpressionFunction
	FN.parseInt = org.beetl.ext.fn.ParseInt
	FN.parseLong = org.beetl.ext.fn.ParseLong
	FN.parseDouble= org.beetl.ext.fn.ParseDouble
	FN.range = org.beetl.ext.fn.Range
	FN.flush = org.beetl.ext.fn.Flush
	FN.json = org.beetl.ext.fn.Json
	FN.pageCtx = org.beetl.ext.fn.PageContextFunction
	FN.type.new=org.beetl.ext.fn.TypeNewFunction
	FN.type.name=org.beetl.ext.fn.TypeNameFunction
	FN.global=org.beetl.ext.fn.DynamicGlobalValueFunction

	##内置的功能包
	FNP.strutil = org.beetl.ext.fn.StringUtil
	FNP.reg = org.beetl.ext.fn.RegxFunctionUtil

	FNP.array = org.beetl.ext.fn.ArrayUtil

	##内置的格式化函数
	FT.dateFormat =  org.beetl.ext.format.DateFormat
	FT.numberFormat =  org.beetl.ext.format.NumberFormat
	##内置的默认格式化函数
	FTC.java.util.Date = org.beetl.ext.format.DateFormat
	FTC.java.sql.Date = org.beetl.ext.format.DateFormat
	FTC.java.sql.Time = org.beetl.ext.format.DateFormat
	FTC.java.sql.Timestamp = org.beetl.ext.format.DateFormat
	FTC.java.lang.Short = org.beetl.ext.format.NumberFormat
	FTC.java.lang.Long = org.beetl.ext.format.NumberFormat
	FTC.java.lang.Integer = org.beetl.ext.format.NumberFormat
	FTC.java.lang.Float = org.beetl.ext.format.NumberFormat
	FTC.java.lang.Double = org.beetl.ext.format.NumberFormat
	FTC.java.math.BigInteger = org.beetl.ext.format.NumberFormat
	FTC.java.math.BigDecimal = org.beetl.ext.format.NumberFormat
	FTC.java.util.concurrent.atomic.AtomicLong = org.beetl.ext.format.NumberFormat
	FTC.java.util.concurrent.atomic.AtomicInteger = org.beetl.ext.format.NumberFormat

	##虚拟属性 无
	## 标签类
	TAG.include= org.beetl.ext.tag.IncludeTag
	TAG.includeFileTemplate= org.beetl.ext.tag.IncludeTag
	TAG.layout= org.beetl.ext.tag.LayoutTag
	TAG.delete= org.beetl.ext.tag.DeleteTag
	TAG.htmltag= org.beetl.ext.tag.HTMLTagSupportWrapper
	TAG.htmltagvar= org.beetl.ext.tag.HTMLTagVarBindingWrapper
	TAG.cache= org.beetl.ext.tag.cache.CacheTag

-----------------------
Beetl常用的				|
-----------------------
	












