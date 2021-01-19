-------------------------
String					 |
-------------------------
	# 字符串模板
		* 类似于 ES6 的模版
			var name = "KevinBlandy";
			var age = 25;
			println("$name's age is $age")
		
		* 使用 $ 访问变量,如果变量不存在,无法访问,会抛出异常
		* 如果需要输出 $ 符号, 那么需要使用转义字符: "\$"
	
		* 字符串的模板,允许使用简单的表达式: ${表达式}
			var age = 25;
			println("${if (age > 25) "老男人" else "年轻人"}");

			* 在表达式中, 允许合理的嵌套双引号的字符串表达式
		
		* 支持对象的属性导航,需要使用 {} 包括
			var user = User("KevinBlandy", 25);
			println("${user.name},${user.age}");
	

		* 编译后的代码,其实是创建了一个 StringBuilder 对象
	
	# 三引号字符串
		* 类似于 js 里面的 `` 字符串, python里面的 ''' ''''
		* 里面的字符不需要转义,包括反斜线,还可以包含任何字符, 包括换行
			val name : String = """
				Hello
					World
					"
			"""
			println(name)

		* 特别适合用于正则表达式
		* 可以用它来画一些ascii图
		* 可以使用字符串的模板, 因为不支持转义字符,所以需要输出: $ 符号的话,需要使用: ${'$'}
			val name : String = """我就是为了输出:${'$'}"""

-------------------------
String	- 实例方法		 |
-------------------------
	trimMargin(marginPrefix: String = "|"): String
		# 删除字符串前面的空格,以及指定的前缀
		# 参数
			marginPrefix
				* 需要和空格一起删除的前缀,默认为 "|" (为啥默认值是这个?)
	

	

	substringBefore(delimiter: String, missingDelimiterValue: String = this): String
	substringBefore(delimiter: Char, missingDelimiterValue: String = this): String
	substringBeforeLast(delimiter: String, missingDelimiterValue: String = this): String
	substringBeforeLast(delimiter: Char, missingDelimiterValue: String = this): String
		# 截取指定字符/字符串之前的字符串, 返回的字符串不包含指定的字符/字符串
			* substringBefore 截取到第一次出现的位置
			* substringBeforeLast 截取到最后一次出现的位置
		
		# 参数
			missingDelimiterValue
				* 如果指定的字符串, 字符不存在,返回的数据
				* 默认值为 this , 也就说,如果指定的字符, 字符串不存在,默认返回原始字符串

	substringAfter(delimiter: String, missingDelimiterValue: String = this): String
	substringAfter(delimiter: Char, missingDelimiterValue: String = this): String
	substringAfterLast(delimiter: String, missingDelimiterValue: String = this): String
	substringAfterLast(delimiter: Char, missingDelimiterValue: String = this): String
		# 同上, 截取的是,指定字符串之后的字符串


	split(vararg delimiters: Char, ignoreCase: Boolean = false, limit: Int = 0): List<String>
	split(delimiter: String, ignoreCase: Boolean, limit: Int): List<String>
	split(regex: Regex, limit: Int = 0): List<String>
		# 按照字符串, 或者正则切割字符串
		# 参数: delimiters 
			* 可以设置多个分割字符

			val name : String = "1_2-3_4.5"
			val result = name.split("_", "-", ".")
			println(result) // [1, 2, 3, 4, 5]


	toRegex(): Rege
	toRegex(option: RegexOption): Regex
		# 转换当前字符串为 Regex 对象
		# 参数 RegexOption(枚举)
			* 可选的一些正则属性

			IGNORE_CASE
			MULTILINE
			LITERAL
			UNIX_LINES
			COMMENTS
			DOT_MATCHES_ALL
			CANON_EQ
	


			