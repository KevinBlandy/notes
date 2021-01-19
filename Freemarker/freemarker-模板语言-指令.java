-----------
指令		|
-----------
	# 使用 FTL标签来调用 指令
	# 如果标签没有嵌套内容(在开始标签和结束标签之间的内容),那么可以忽略关闭标签
	# 存在两种指令
		* 自定义指令,使用@
			<@my parameters ></@my>
		* 系统指令,使用#
			<#if parameters ></if>
	
	# FreeMarker会忽略FTL标签中多余的空白标记
	



------------
assign		|
------------
	# 创建一个新的变量, 或者替换一个已经存在的变量
	# 注意仅仅顶级变量可以被创建/替换 (也就是说你不能创建/替换 some_hash.subvar, 除了 some_hash)
		<#assign seq = ["foo", "bar", "baz"]>
		<#assign x++>
		<#assign
			seq = ["foo", "bar", "baz"]
			x++
		>
	# 名称可以有双引号
	# 保存输出
		
------------
compress	|
------------
	# 移除多余的空白是很有用的,它捕捉在指令体中生成的内容,然后缩小所有不间断的空白序列到一个单独的空白字符
	# 如果被替代的序列包含换行符或是一段空间,那么被插入的字符也会是一个 换行符, 开头和结尾的空白符会被移除
	# 有点儿压缩内容的意思
	# 属性
		single_line 
			* bool值,如果该值为 true,则会连换行都一起移除

------------
escape		|
------------
	# 会对该指令标签下${}输出的内容进行指定的编码
	# 这是一个避免编写相似表达式的很方便的方法
		* 它不会影响在字符串形式的插值(比如在 <#assign x = "Hello ${user}!">)
		* 而且,它也不会影响数值插值 (#{...})

	# Demo
		<#escape x as x?html>
			First name: ${firstName}
			Last name: ${lastName}
			Maiden name: ${maidenName}
		</#escape>

		* x 表示${}输出的内容
		* x?html 对输出内容的处理,表示用html编码去处理,也可以换成自己定义的函数啥的
		* 甚至可以让x作为map的key
			<#escape x as skills[x]>....

		* 其实等于
			First name: ${firstName?html}
			Last name: ${lastName?html}
			Maiden name: ${maidenName?html}
		
	
	# 关闭转义,可以在escape中嵌套#noescape指令
		<#escape x as x?html>
			First name: ${firstName}
			<#noescape>Message: ${message}</#noescape>
		</#escape>
		
		 * message 不会被转义
		
	# 转义可以嵌套多个
		<#escape x as x?html>
		  Customer Name: ${customerName}
		  Items to ship:
		  <#escape x as itemCodeToNameMap[x]>
			${itemCode1}
			${itemCode2}
			${itemCode3}
			${itemCode4}
		  </#escape>
		</#escape>

		* 上代码等于
		  Customer Name: ${customerName?html}
		  Items to ship:
			${itemCodeToNameMap[itemCode1]?html}
			${itemCodeToNameMap[itemCode2]?html}
			${itemCodeToNameMap[itemCode3]?html}
			${itemCodeToNameMap[itemCode4]?html}

		* 嵌入的转义区块内使用非转义指令时,它仅仅不处理一个单独层级的转义
		* 因此,为了在两级深的转义区块内完全关闭转义,你需要使用两个嵌套的非转义指令


------------
ftl			|
------------
	# 对于模板引擎的一些设置
		<#ftl param1=value1 param2=value2 ... paramN=valueN>
		
		* 参数名称固定
		* 参数值是常量,不能使用变量
	
	# 属性
		encoding
			* 编码
		
		strip_whitespace
			* 这将开启/关闭 空白剥离
			* 合法的值是布尔值常量 true 和 false (为了向下兼容,字符串 "yes","no", "true","false" 也是可以的)
			* 默认值是 true。

	strip_text
		* 当开启它时,当模板被解析时模板中所有顶级文本被移除
		* 这个不会影响宏,指令,或插值中的文本
		* 合法值是布尔值常量 true 和 false (为了向下兼容,字符串 "yes","no", "true","false" 也是可以的)
		*  默认值(也就是当你不使用这个参数时)是 false


	strict_syntax
		* 这会开启/关闭"严格的语法"
		* 合法值是布尔值常量 true 和 false (为了向下兼容,字符串 "yes","no", "true","false" 也是可以的)
		* 默认值(也就是当你不使用这个参数时)是依赖于程序员对 FreeMarker 的配置, 但是对新的项目还应该是 true。


	attributes
		* 这是关联模板任意属性(名-值对)的哈希表, 属性的值可以是任意类型(字符串,数字,序列等)。
		* reeMarker 不会尝试去理解属性的含义,它是由封装 FreeMarker(比如Web应用框架)的应用程序决定的
		* 因此,允许的属性的设置是它们依赖的应用(Web应用框架)的语义
		* 可以通过关联 Template 对象的 getCustomAttributeNames 和 getCustomAttribute 方法 (从 freemarker.core.Configurable 继承而来)获得属性
		* 如当模板被解析时,关联 Template 对象的模板属性, 属性可以在任意时间被读取,而模板不需要被执行。 上面提到的方法返回未包装的属性值,也就是说, 使用 FreeMarker 独立的类型,如 java.util.List。

------------
global		|
------------
	# 设置全局变量,在所有命名空间中都可见
	# 语法
		<#global name=value>
		<#global name1=value1 name2=value2 ... nameN=valueN>
	# 如果变量名称包含特殊字符,可以用""包裹

--------------------
if, else, elseif	|
--------------------
	# 太简单了,不说
	# 判断尽量使用括号
		<#if (x > 0)>
		</#if>

		* 不然的话 ><会被解析为指令的结束符
	
	# 最终的结果必须是一个bool类型,不然异常

--------------------
import				|
--------------------
	# 用于导入一个库
		<#import "/libs/mylib.ftl" as my>
		<@my.foo/>
		${my.func("123")}
	

--------------------
include				|
--------------------
	# 包含
	# 语法
		<#include path>
		<#include path options>

		* path表示路径
		* ptions 一个或多个这样的选项
			encoding		编码
			parse			是否当作ftl解析,如果为false则把内容当作纯字符串
			ignore_missing	是否忽略异常,如果目标目标异常,则不会有任何输出
	
	# 本地化模板查找
		
		*  假设模板使用本地化 en_US 来加载, 就是美国英语,当包含其它模板时

			<#include "footer.ftl">
			
			//引擎实际上就会按照这个顺序寻找模板

			footer_en_US.ftl,
			footer_en.ftl
			footer.ftl
		
		* 通过程序关闭本地检索机制
			//禁用本地化模板查找
			configuration.setLocalizedLookup(false);
		
		*  也可以控制这个查找的过程
			configuration.setTemplateLookupStrategy(TemplateLookupStrategy);

--------------------
noparse				|
--------------------
	# 会忽略该指令中间的所有ftl表达式,会把它们直接当作字符串原样输出
	# 模板
		<#noparse>
		  <#list animals as animal>
		  <tr><td>${animal.name}<td>${animal.price} Euros
		  </#list>
		</#noparse>
	# 输出
		Example:
		--------

		  <#list animals as animal>
		  <tr><td>${animal.name}<td>${animal.price} Euros
		  </#list>

--------------------
setting				|
--------------------
	# 设置是影响 FreeMarker 行为的值
		<#setting name=value>
	
	# 可以设置的属性
		locale
		number_format
		boolean_format
		date_format
		time_format
		datetime_format
		time_zone
		sql_date_and_time_time_zone 
		url_escaping_charset
		output_encoding
		classic_compatible

--------------------
#switch				|
--------------------
	# 官方说不建议用,建议用if elseif
		<#switch value>
		  <#case refValue1>
			...
			<#break>
		  <#case refValue2>
			...
			<#break>
		  <#case refValueN>
			...
			<#break>
		  <#default>
			...
		</#switch>
	
------------------------
t, lt, rt				|
------------------------
	t 
		* 忽略本行中首和尾的所有空白。
	lt
		* 忽略本行中首部所有的空白。
	rt 
		* 忽略本行中尾部所有的空白