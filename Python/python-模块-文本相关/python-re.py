------------------------
re						|
------------------------
	* 正则表达式
	* 用于对字符串的操作
	* 在 string 中涉及到正则的操作
		replace(reg,new)	# 替换
		find(reg,start,end)	# 检索
		rfind(reg,start,end)# 检索
		split(reg,coun)		# 切割
	* 但是 string 本身对于正则操作的 api 有限,对于其他要求苛刻的匹配,就需要使用  re 模块
	* 操作体系
		一,直接使用re模块函数方法进行正则操作
			* 看模块函数

		二,创建正则对象(pattern)来进行正则操作(效率比较高)
			1,获取到正则对象(pattern)
				_sre.SRE_Pattern compile(pattern,flags)
				var pattern = compile('')

			2,进行匹配,检索操作
				_sre.SRE_Match match(str,start,end)
				_sre.SRE_Match search(str,start,end)
				callable_iterator finditer(str,start,end)
				...
			
			3,从结果 _sre.SRE_Match 中获取数据
			

			
------------------------
re-内置属性				|
------------------------

------------------------
re-模块级别函数			|
------------------------
	list findall(pattern, string, flags)
		* 使用 pattern 表达式去匹配出 string 中的所有符合规则的元素
		* flags,用于指定匹配模式,默认值为 0
		* flags,标志位,用于控制正则表达式的匹配方式,如:是否区分大小写,多行匹配等等
		
	list split(pattern, string)
		* 分隔
	
	str sub(pattern,repl,string,count=0,flags=0)
		* 参数
			pattern	正则
			repl	是一个函数,会把匹配到的值传递给它,把计算结果替换原来的值
			string	目标字符串
			count	替换值,替换多少次(默认 0 表示替换所有的匹配)
			flags	...

	callable_iterator finditer(pattern, string, flags)
		* 使用 pattern 表达式去匹配出 string 中的所有符合规则的元素,返回迭代器
	
	_sre.SRE_Match search(pattern, string, flags)
		* 返回 string 第一个符合 pattern 的子串儿,匹配失败,返回 None

	_sre.SRE_Match match(pattern, string, flags)
		* 判断 string 是否符合 pattern,符合返回 _sre.SRE_Match ,否则返回 None
		* '仅仅匹配开头,匹配成功后,string还有剩余字符,也视为匹配成功'

	_sre.SRE_Pattern compile(pattern,flags)
		* 把一个字符串编译成 pattern 对象用于匹配或搜索
	

	search(string)
		* 将string中的正则表达式元字符如*/+/?等之前加上转义符再返回,在需要大量匹配元字符时有那么一点用

------------------------
_sre.SRE_Pattern		|
------------------------
	* 方法
		_sre.SRE_Match match(str,start,end)
			* 匹配 str 字符串,匹配成功,返回 _sre.SRE_Match 对象,匹配失败返回 None
			* start,end 默认值为字符串的第一个最后一个
		
		_sre.SRE_Match search(str,start,end)
			* 返回 str 字符串,匹配到的第一个值,匹配失败返回 None
			* start,end 默认值为字符串的第一个最后一个
			* '仅仅匹配开头,匹配成功后,string还有剩余字符,也视为匹配成功'
		
		list findall(str,start,end)
			* 返回 str 字符串,匹配到的所有值
			* start,end 默认值为字符串的第一个最后一个
		
		callable_iterator finditer(str,start,end)
			* 返回 str 字符串,匹配到的所有值,返回的是一个迭代器
			* start,end 默认值为字符串的第一个最后一个

------------------------
_sre.SRE_Match			|
------------------------
	* Match对象是一次匹配的结果,包含了很多关于此次匹配的信息
	* 属性
		string		匹配时使用的文本
		re			匹配时使用的Pattern对象
		pos			文本中正则表达式开始搜索的索引,值与Pattern.match()和Pattern.seach()方法的同名参数相同
		endpos		文本中正则表达式结束搜索的索引,值与Pattern.match()和Pattern.seach()方法的同名参数相同
		lastindex	最后一个被捕获的分组在文本中的索引。如果没有被捕获的分组，将为None。
		lastgroup	最后一个被捕获的分组的别名。如果这个分组没有别名或者没有被捕获的分组，将为None。
	
	* 方法
		str group(num)
			* 返回 match object 中的字符串.
			* 每一个 ( ) 都是一个分组,分组编号从1开始,从左往右,每遇到一个左括号,分组编号+1
			* 组 0 总是存在的,它就是整个表达式(原字符串)
			* 没有参数时,num默认为0这时返回整个匹配到的字符串
			* 指定一个参数(整数)时,返回该分组匹配到的字符串
			* 指定多个参数时,返回由那几个分组匹配到的字符串组成的 tuple
		
		tuple groups()
			* 获取到所有的匹配项
		
		dict groupdict()

		start()
		end()
		span()
			* 获取匹配成功的第一个子串在字符串中的角标,留头不留尾
			* 返回元组,其实就是:(start(group), end(group))
		expand()

------------------------
正则表达式修饰符		|	
------------------------
	* 多数正则方法的第三个参数(flags)
	* 正则表达式可以包含一些可选标志修饰符来控制匹配的模式
	* 修饰符被指定为一个可选的标志,多个标志可以通过按位 OR(|) 它们来指定
		如 re.I | re.M 被设置成 I 和 M 标志

	* 修饰符	描述
		re.I	使匹配对大小写不敏感
		re.L	做本地化识别(locale-aware)匹配
		re.M	多行匹配，影响 ^ 和 $
		re.S	使 . 匹配包括换行在内的所有字符(全文匹配)
		re.U	根据Unicode字符集解析字符,这个标志影响 \w, \W, \b, \B.
		re.X	该标志通过给予你更灵活的格式以便你将正则表达式写得更易于理解
					* 这个模式下正则表达式可以是多行，忽略空白字符，并可以加入注释
