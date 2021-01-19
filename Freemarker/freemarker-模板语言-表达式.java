--------------------
取值表达式			|
--------------------
	# 取值表达式的最终结果是"字符串"
	# 直接取值
		* model属性
			${name}
			${user.name}  ${user["mame"]}
			${user.author.name} ${user["author"].name} ${user["author"]["name"]}

			* 如果属性名称里面有特殊的属性,需要使用转义字符
				表达式为 data\-id,因为 data-id 将被解释成 "data 减去 id"
				这些转义仅在标识符中起作用,而不是字符串中。
		
		* 常量
			${"这是一个字符串"}

		* 字符串支持转义
			\"		引号 (u0022)
			\'		单引号(又称为撇号) (u0027)
			\{		起始花括号：{
			\\		反斜杠 (u005C)
			\n		换行符 (u000A)
			\r		回车 (u000D)
			\t		水平制表符(又称为tab) (u0009)
			\b		退格 (u0008)
			\f		换页 (u000C)
			\l		小于号：<
			\g		大于号：>
			\a		&符：&
			\xCode	字符的16进制 Unicode 码 (UCS 码)
		
		
	# 可以在文本表达式中使用
		<#include "/footer/${company}.html">
		
		* <#if ${big}>...</#if>,语法上的错误
	
	# 日期
		* 渲染Date必须要使用到内置的日期函数
		* '必须要'通过这些函数来告诉模板引擎,要渲染Date里面的哪部分数据
			date
			time
			datetime
			
			${now?datetime}
		
		* 设置全局的格式化
			configuration.setSetting(Configuration.DATETIME_FORMAT_KEY_SNAKE_CASE, "yyyy-MM-dd HH-mm-ss ");	//仅仅设置了 datetime 
			${now?datetime}

		* 可以使用 ?string 临时把date转换为指定的日期类型
			${now?string("yyyy年MM月dd日 HH时mm分ss秒")}

	
	# boolean 值
		* 直接输出boolean值会异常,可以使用内建函数 ?then 来将布尔值转换为字符串形
			${married?then("yes", "no")}
		
		* 也可以通过 ?c 直接输出'计算机'可以看懂的 true/false
			${someBoolean?c}	

		* 可以使用设置参数 boolean_format 来为 FreeMarker 配置默认的布尔值格式,这样就可以直接输出 boolean 类型的变量:${married}

		* string("true","false") 也可以用于格式化bool变量,但是已经废弃了。
	

	
	# Map
		* 如果map的key是string，可以直接使用 . 活着 [] 取值

		* 如果map的key不是string，比较麻烦，需要先设置

			configuration.setAPIBuiltinEnabled(true);
			DefaultObjectWrapper defaultObjectWrapper = (DefaultObjectWrapper) configuration.getObjectWrapper();
			defaultObjectWrapper.setUseAdaptersForContainers(true);
		
		* 取值表达式
			<#list mine?keys as key>
				${key?c} - ${mine?api.get(key)}<br/>
			</#list>

			* 使用: ?api.get() 来获取非字符串的value
		

--------------------
值域				|
--------------------
	# 用于表示出一个区间的循环
		start..end
			* 指定开始和结尾角标
			* 包含结尾的值域,比如 1..4 就是 [1, 2, 3, 4], 而 4..1 就是 [4, 3, 2, 1]
			* 注意, 包含结尾的值域不能是一个空序列,如果为空的话 0..length-1 就是错误的,因为当长度(length)为 0 了,序列就成了 [0, -1]
		
		start..<end 或 start..!end
			* 指定开始和结尾的角标(留头不留尾)
			* 不包含结尾的值域,比如 1..<4 就是 [1, 2, 3],4..<1 就是 [4, 3, 2],
			* 而 1..<1 表示 [],这表达式的结果结果可以是空序列,和 
			* ..< 和 ..! 没有区别,最后这种形式在应用程序中使用了 < 字符而引发问题(如HTML编辑器等)
		
		start..*length
			* 指定开始的角标以及要获取的长度
			* 限定长度的值域,比如 10..*4 就是 [10, 11, 12, 13],10..*-4 就是 [10, 9, 8, 7]
			* 而 10..*0 表示 [],当这些值域被用来切分时, 如果切分后的序列或者字符串结尾在指定值域长度之前,则切分不会有问题
		
		start..
			* 无右边界值域,这和限制长度的值域很像,只是长度是无限的
			* 比如 1.. 就是 [1, 2, 3, 4, 5, 6, ... ],直到无穷大
			* 但是处理(比如列表显示)这种值域时要万分小心,处理所有项时, 会花费很长时间,直到内存溢出应用程序崩溃
			* 和限定长度的值域一样,当它们被切分时,遇到切分后的序列或字符串结尾时,切分就结束了

--------------------
字符串的操作		|
--------------------
	# 字符串的拼接
		<#assign s = "Hello ${name}!"> //x = Kevin
		${s} //Hello Kevin
		
		<#assign s = "Hello " + user + "!">
		${s}//Hello Kevin
		
	
	# 根据索引获取字符
		${user[0]}		//获取字符串的第一个字符
		${user[4]}		//获取字符串的第三个字符
	
	# 字符串切割
		<#assign s = "ABCDEF">
		${s[2..3]}		CD		//从指定的角标到指定的角标
		${s[2..<4]}		CD		//从指定角标到指定角标一下
		${s[2..*3]}		CDE	
		${s[2..*100]}	CDEF
		${s[2..]}		CDEF	//从指定角标以后

		
		* 操作字符串的内置函数
			remove_beginning
			remove_ending
			keep_before
			keep_after
			keep_before_last
			keep_after_last


--------------------
序列的操作			|
--------------------
	# 序列取值
		${user.skills[0].id}

	# 序列的连接
		<#list ["Joe", "Fred"] + ["Julia", "Kate"] as user>
			- ${user}
		</#list>	
	
	# 序列切分
		<#assert seq = ["A", "B", "C", "D", "E"]>
		<#list seq[1..3] as i>
			${i}		//输出BCD
		</#list>	
		
		* seq[1..3] 表示仅仅变量序列1-3角标的元素
		* 切分后序列中的项会和值域的顺序相同,那么上面的示例中,如果值域是 3..1 将会输出 'DCB'
		* 值域中的数字必须是序列可使用的合法索引
			- seq[-1..0]
			- seq[-1]
			- seq[1..5]	异常,因为5超出了最大角标4
			- seq[100..<100] 合法
			- seq[100..*0]	合法	

--------------------
Hash				|
--------------------
	# 连接
		<#assign ages = {"Joe":23, "Fred":25} + {"Joe":30, "Julia":18}>
		- Joe is ${ages.Joe}
		- Fred is ${ages.Fred}
		- Julia is ${ages.Julia}
	
		* 右侧的会覆盖前面的同名属性

--------------------
运算				|
--------------------		
	# 支持 +-*/%等运算
	# 字符串与数值相加,会自动转换数值为字符串
	# 字符串与数值执行除了相加以外的所有操作,都会异常
	# 比较运算
		<#if user == "Big Joe">
		  It is Big Joe
		</#if>
		<#if user != "Big Joe">
		  It is not Big Joe
		</#if>
		
		* = 或 != 两边的表达式的结果都必须是标量,而且两个标量都必须是相同类型 (也就是说字符串只能和字符串来比较,数字只能和数字来比较等)
		
		* 对数字和日期类型的比较，也可以使用 <, <=,>= 和 >
	
	# 逻辑操作 || ,&&,!

		<#if x < 12 && color == "green">
		  We have less than 12 things, and they are green.
		</#if>
		<#if !hot> <#-- here hot must be a boolean -->
		  It's not hot.
		</#if>

--------------------
内建函数			|
--------------------
	# 内置函数的调用
		${变量?执行方法}
		${变量?执行方法(方法参数)}
		${变量?执行方法1(方法参数)?执行方法2}

		${testString?upper_case}
		${testString?html}
		${testString?upper_case?html}

		${testSequence?size}
		${testSequence?join(", ")}
	
	# 内建函数的左侧可以是任意的表达式,而不仅仅是变量名
		${testSeqence[1]?cap_first}
		${"horse"?cap_first}
		${(testString + " & Duck")?html}	
	
--------------------
方法调用			|
--------------------
	# 自定义的方法调用
		${repeat("Foo", 3)}
	
	# 方法调用也是普通表达式,和其它都是一样的
		${repeat(repeat("x", 2), 3) + repeat("Foo", 4)?upper_case}

--------------------
不存在的值处理		|
--------------------

	# 默认值操作符
		unsafe_expr!default_expr					${name!"默认值"}
		unsafe_expr! or (unsafe_expr)!default_expr	${name!}	${(name)!"默认值"}
		(unsafe_expr)!								${(name)!}
	
	# 默认值可以是任何类型的表达式,也可以不必是字符串
		hits!0 
		colors!["red", "green", "blue"]
		cargo.weight!(item.weight * itemCount + 10)
	
	
	# 空序列或空哈希表,如果想让默认值为 0 或 false,则不能省略默认值
	
	# 于非顶层变量时,默认值操作符可以有两种使用方式
		${product.color!"red"}		
			* 如果 color 属性不存在,默认返回 red
			* 如果 product 属性不存在,模板异常
		
		${(product.color)!"red"}
			* 如果 product 属性不存在或者 color 属性不存在,都会返回 red
		
		
		* 如果没有括号,仅仅允许表达式最后的一个属性可以未被定义
		

	# 不存在值检测操作符:unsafe_expr?? 或 (unsafe_expr)??
		<#if mouse??>
		  Mouse found
		<#else>
		  No mouse found
		</#if>
		Creating mouse...
		<#assign mouse = "Jerry">
		<#if mouse??>
		  Mouse found
		<#else>
		  No mouse found
		</#if>
	
	# 默认值操作也可以作用于序列子变量
		<#assign seq = ['a', 'b']>
		${seq[0]!'-'}
		${seq[1]!'-'}
		${seq[2]!'-'}
		${seq[3]!'-'}
				
		a
		b
		-			//角标不存在,输出--
		-
	
		* 如果序列索引是负数(比如 seq[-1]!'-') 也会发生错误,不能使用该运算符或者其它运算符去压制它


--------------------
赋值操作符			|
--------------------
	# 并不是表达式,只是复制指令语法的一部分,比如 assign, local 和 global
		<#assign x += y> 是 <#assign x = x + y> 的简写
		<#assign x *= y> 是 <#assign x = x * y> 的简写
		<#assign x--> 是 <#assign x -= 1> 的简写
	
		<#assign x++> 和 <#assign x += 1>不同,它只做算术加法运算 (如果变量不是数字的话就会失败)
	
--------------------
括号				|
--------------------
	//TODO

--------------------
表达式中的空格		|
--------------------
	# FTL 忽略表达式中的多余的 空格。下面的表示是相同的：

--------------------
特殊变量			|
--------------------