---------------------
内置函数			 |
---------------------
	# 内置函数的调用都是通过 ?func 来调用的
	

---------------------
字符串相关			 |
---------------------
	boolean
		* 转换字符串为 boolean 值
		* 字符串必须是 true 或 false (大小写敏感)
		* 也可以通过 boolean_format 设置的特定格式
	
	html
		*  把html字符转义输出
	
	cap_first
		* 首字母设置大写
	
	uncap_first
		* 设置字符串中所有单词的首字母小写
	
	capitalize
		* 字符串中所有单词的首字母都大写
	
	chop_linebreak
		* 如果字符串末尾没换行,则添加换行符
	
	contains
		* 判读字符串中是否包含指定的子串,返回 boolean 
	
	trim
		* 去掉首尾空格

	date, time, datetime
		* 对Date类型数据的格式化
		* 参数可以是格式化的格式
		* 也可以设置默认的格式化方法
	
	ends_with
		* 判断字符串是否以指定的子串儿结尾
	
	ensure_ends_with
		* 如果字符串没有以指定的字符串结尾
		* 则会把指定的字符串添加到结尾

	ensure_starts_with
		* 同上,字符串是否以指定的字符串开头

		* 如果它指定了两个参数,那么第一个就会被解析为'正则表达式'
		* 如果它不匹配字符串的开头,那么第二个参数指定的字符串就会添加到字符串开头
		* 该方法也接受第三个标志位参数,(也就是正则表达式模式)

	length
		* 字符串的数量

	index_of
		* 返回字字符串第一次出现的位置,没找到返回-1
	
	left_pad
		* 接收一个参数n,如果字符串不足n长度的话,会自动在前面添加空格
			${" a"?left_pad(5)}		//"    a"
		
		* 如果存在第二参数,也是一个字符, 那么这个字符串可以替换默认的空格填充策略
			${" a"?left_pad(5,"-")}		//"--- a"

		* 第二个参数也可以是个长度比1大的字符串,那么这个字符串会周期性的插入
			${" a"?left_pad(5,"abcd")}		//"abc a"	
	
	right_pad
		* 同上,不过是在后面填充

	lower_case
		* 转换为小写模式
	
	upper_case
		* 转换为大写模式
	
	url
		* 对数据进行url编码
		* 它会转义所有保留的URL字符 (/,=, &,等...)
			<a href="foo.cgi?x=${x?url}&y=${y?url}">Click here...</a>
		
		* 设置URI编码的字符集
			<#setting url_escaping_charset="UTF-8">

		* 也可以在参数中指定
			${x?url('UTF-8')}
	
	url_path
		* 同上,但是它不转义斜杠(/)字符
		* 也可以指定字符集
			url_path('utf-8')
	
	number
		* 转换为数字
	
	replace
		* 使用参数2,替换参数1的子串儿
	
	remove_beginning
		*  移除指定开头的字符串,如果开头的字符串不是指定的,则返回原串儿

	remove_ending
		* 同上,移除尾部分
	
	split
		* 把字符串按照指定的子串儿分割为序列
	
	starts_with
		* 如果以指定的字符串开始,返回 true
	

---------------------
数值相关			 |
---------------------
	abs
		* 绝对值
	c
		* 返回计算机能看懂的数据
	
	int
		* 转换为int数据
	
	long
		* 转换为long属性
	
	string 
		* 转换为字符串
		* 可以通过 number_format 和 locale 设置的默认格式
		* 四种预定义的数字格式
			computer
			currency
			number 
			percent

			<#assign x=42>
			${x}
			${x?string}  <#-- 无任格式化策略,输出和 ${x} 一样 -->
			${x?string.number}
			${x?string.currency}
			${x?string.percent}
			${x?string.computer}

		* 还可以使用 Java 数字格式语法(使用[]设置表达式) 中的任意数字格式(DecimalFormat)
			<#assign x = 1.234>
			${x?string["0"]}
			${x?string["0.#"]}
			${x?string["0.##"]}
			${x?string["0.###"]}
			${x?string["0.####"]}

			${1?string["000.00"]}
			${12.1?string["000.00"]}
			${123.456?string["000.00"]}

			${1.2?string["0"]}
			${1.8?string["0"]}
			${1.5?string["0"]} <-- 1.5, rounded towards even neighbor -->
			${2.5?string["0"]} <-- 2.5, rounded towards even neighbor -->

			${12345?string["0.##E0"]}

---------------------
日期相关			 |
---------------------
	date
		* 仅日期部分，没有一天当中的时间部分。
	time
		* 仅一天当中的时间部分，没有日期部分。
	datetime
		* 日期和时间都在
	
	# 以上三个函数都支持通过参数来设置日志的格式化
	# 也可以设置全局的格式化方式
		date_format,time_format,datetime_format

	string
		* 也可以用于格式化日期,可以用参数指定格式化
		* 尽量少用这种格式化方法,标识过期
			${time?string('yyyy-MM-dd HH:mm:ss')}

	number_to_date
	number_to_time
	number_to_datetime
		* 以上函数都适用于把数值(Timestamp)转换为日期

---------------------
boolean				 |
---------------------
	c
		* 输出计算机能看懂的"true"/"false"
		* 如果要渲染为javascript的代码,可以用它

	then
		* 格式化bool的输出
			${true?then('true','false')}

---------------------
序列				 |
---------------------
	chunk
		* 将序列分隔为多个子序列,每个子序列的长度为第一个参数给定的值 (arr?chunk(3))
			<#assign arr = [0,1,2,3,4,5,6,7,8,9]>
			<#list arr?chunk(3) as subArr>
				<#list subArr as item>
					${item}
				</#list><br/>
			</#list>			
			0 1 2 
			3 4 5 
			6 7 8 
			9 
		* 也可以指定第二个参数,来设置,当序列长度不足时以什么补充?
			<#assign arr = [0,1,2,3,4,5,6,7,8,9]>
			<#list arr?chunk(3,'-') as subArr>
				<#list subArr as item>
					${item}
				</#list><br/>
			</#list>			
			0 1 2 
			3 4 5 
			6 7 8 
			9 - -	
	
	first
		* 返回第一个元素
	last
		* 返回最后一个元素
	
	join
		* 使用指定的字符串链接序列里面的所有元素,形成一个新的字符串
			${[0,1,2,3,4,5,6,7,8,9]?join('-')}
			0-1-2-3-4-5-6-7-8-9
	
		* 它可以有三个参数
			1. 分隔符,是必须的:插入到每一项中间的字符串
			2. 空值,默认是 "" (空字符串),如果序列为空([]),使用该值
			3. 列表结尾,默认是 "" (空字符串): 如果列表序列不为空,该值在最后一个值后面输出
		
	reverse
		* 反转
	
	seq_contains
		* 判断序列中是否存在指定的值,返回 boolean
		* 使用 == 操作符
			${[1,2,3,4,6]?seq_contains(5)?then('包含','不包含')}
	
	seq_index_of
		* 返回指定元素第一次出现的角标,如果未找到,返回 -1
		* 搜索开始的索引值可以由第二个可选参数来确定
	
	seq_last_index_of
		* 返回指定元素最后一次出现的角标,如果未找到返回 -1
		* 搜索开始的索引值可以由第二个可选参数来确定
	
	size
		* 返回序列的长度
	
	sort
		* 以升序排列序列,仅仅在元素是:数值,字符串,boolean,Date 时有效
		* 要使用降序排列时,使用它之后使用 reverse 内建函数
			<#assign ls = ["whale", "Barbara", "zeppelin", "aardvark", "beetroot"]?sort>
			<#list ls as i>${i} </#list>

	sort_by
		* 以升序排列序列,仅仅元素是对象
		* 通过参数来指定用于排序的字段
			<#assign ls = [
			  {"name":"whale", "weight":2000},
			  {"name":"Barbara", "weight":53},
			  {"name":"zeppelin", "weight":-200},
			  {"name":"aardvark", "weight":30},
			  {"name":"beetroot", "weight":0.3}
			]>

			<#list ls?sort_by("name") as i>	</#list>
	
		* 用来排序的子变量的层次很深 (也就是说,它是子变量的子变量的子变量,以此类推), 那么可以使用序列来作为参数,它指定了子变量的名字
			<#assign members = [
				{"name": {"first": "Joe", "last": "Smith"}, "age": 40},
				{"name": {"first": "Fred", "last": "Crooger"}, "age": 35},
				{"name": {"first": "Amanda", "last": "Fox"}, "age": 25}]>
			<#list members?sort_by(['name', 'last']) as m></#list>

---------------------
对象/Hash			 |
---------------------
	keys
		* 返回所有的key,是一个序列
	
	values
		* 返回所有的value

---------------------
		其他		 |
---------------------
	switch
		* 基本内联:matchedValue?switch(case1, result1, case2, result2, ... caseN, resultN, defaultResult)
			<#list ['r', 'w', 'x', 's'] as flag>
				${flag?switch('r', 'readable', 'w' 'writable', 'x', 'executable', 'unknown flag: ' + flag)}
			</#list>
			readable
			writable
			executable
			unknown flag: s
		
		* 最后一个参数是没找到的默认值
		* result  不一定是常量,也可以是变量或者其他复杂的表达式

	has_content
		* 如果变量(不是Java的 null) 存在而且不是"空"就返回 true,否则返回 false
		* 空值
			长度为0的字符串
			没有子变量的序列或哈希表
			一个已经超过最后一项元素的集合
		
		${""?has_content?c}		//false
		${[]?has_content?c}		//false


---------------------
数据类型判断		 |
---------------------

	is_string		字符串
	is_sequence		序列
	is_enumerable	序列或集合
	is_collection	集合 (包含扩展的集合)
	is_collection_ex	扩展的集合 (支持 ?size)
	is_number		数字
	is_boolean		布尔值
	is_hash			哈希表 (包含扩展的哈希表)
	is_hash_ex		扩展的哈希表 (支持 ?keys 和 ?values)
	is_indexable	序列

	is_date			不要使用它！使用 is_date_like 来代替, 它们是相同的。往后也许会修改它的含义为 date_only。
	is_date_like	日期,也就似乎日期,时间或者日期-时间, 亦或者是未知精确类型的日期(从 FreeMarker 2.3.21 版本开始)
	is_date_only	日期 (没有时间部分) (从 FreeMarker 2.3.21 版本开始)
	is_time			时间 (没有年-月-日部分) (从 FreeMarker 2.3.21 版本开始)
	is_datetime		日期-时间 (包含年-月-日和时间两者)
	is_unknown_date_like	不清楚是日期或时间或日期-时间的日期
	is_method		方法
	is_transform	变换
	is_macro		宏或函数(当然,由于历史问题,也对函数有效)
	is_directive	指令类型 (例如宏 或 TemplateDirectiveModel, TemplateTransformModel, 等...), 或者函数 (由于历史问题)
	is_node			节点