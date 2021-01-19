----------------------------
string						|
----------------------------
	1,字符的 Unicode 表示法
	2,codePointAt()
	3,String.fromCodePoint()
	4,字符串的遍历器接口
	5,at()
	6,normalize()
	7,includes(), startsWith(), endsWith()
	8,repeat()
	9,padStart()，padEnd()
	10,matchAll()
	11,模板字符串
	12,实例:模板编译
	13,标签模板
	14,String.raw()
	15,模板字符串的限制

----------------------------
字符串与 Unicode			|
----------------------------
	codePointAt();
		codePointAt方法是测试一个字符由两个字节还是由四个字节组成的最简单方法。

		function is32Bit(c) {
			return c.codePointAt(0) > 0xFFFF;
		}

		is32Bit("𠮷") // true
		is32Bit("a") // false
	
	String.fromCharCode()
		
----------------------------
字符串的遍历接口			|
----------------------------
	# ES6 为字符串添加了遍历器接口(详见《Iterator》一章),使得字符串可以被for...of循环遍历
		for (let codePoint of 'foo') {
		  console.log(codePoint)
		}
		// "f"
		// "o"
		// "o"
	
	# 除了遍历字符串,这个遍历器最大的优点是可以识别大于0xFFFF的码点,传统的for循环无法识别这样的码点
			let text = String.fromCodePoint(0x20BB7);
			for (let i = 0; i < text.length; i++) {
			  console.log(text[i]);
			}
			// " "
			// " "

			for (let i of text) {
			  console.log(i);
			}
			// "𠮷"
	
----------------------------
at()						|
----------------------------
	# ES5 对字符串对象提供charAt方法,返回字符串给定位置的字符,该方法不能识别码点大于0xFFFF的字符
		'abc'.charAt(0)		// "a"
		'𠮷'.charAt(0)		// "\uD842"

		* 上面代码中的第二条语句,charAt方法期望返回的是用2个字节表示的字符
		* 但汉字"𠮷"占用了4个字节,charAt(0)表示获取这4个字节中的前2个字节,很显然,这是无法正常显示的
	
	# 符串实例的at方法,可以识别 Unicode 编号大于0xFFFF的字符,返回正确的字符
		'abc'.at(0) // "a"
		'𠮷'.at(0)	// "𠮷"

----------------------------
normalize()					|
----------------------------
	许多欧洲语言有语调符号和重音符号,为了表示它们..........
	不表了...

------------------------------------
includes(), startsWith(), endsWith()|
-------------------------------------
	includes()
		* 返回布尔值,表示是否找到了参数字符串
	startsWith()
		* 返回布尔值,表示参数字符串是否在原字符串的头部
	endsWith()
		* 返回布尔值,表示参数字符串是否在原字符串的尾部
	
	* 这三个方法都支持第二个参数,表示开始搜索的位置
	* 使用第二个参数时,endsWith的行为与其他两个方法有所不同,它针对前n个字符
	  而其他两个方法针对从第n个位置直到字符串结束

----------------------------
repeat()					|
----------------------------
	# repeat方法返回一个新字符串,表示将原字符串重复n次
		'x'.repeat(3)		// "xxx"
		'hello'.repeat(2)	// "hellohello"
		'na'.repeat(0)		// ""
	
	# 参数如果是小数会被取整
		'na'.repeat(2.9) // "nana"
	
	# 如果repeat的参数是负数或者Infinity,会报错
		'na'.repeat(Infinity)
		// RangeError
		'na'.repeat(-1)
		// RangeError

	# 如果参数是 0 到-1 之间的小数,则等同于 0
		* 这是因为会先进行取整运算,0 到-1 之间的小数,取整以后等于-0,repeat视同为 0
			'na'.repeat(-0.9) // ""
		* 参数NaN等同于 0
			'na'.repeat(NaN) // ""
	
	# 如果repeat的参数是字符串,则会先转换成数字
		'na'.repeat('na') // ""
		'na'.repeat('3') // "nanana"

----------------------------
padStart(),padEnd()			|
----------------------------
	# 首补齐,和尾补齐
		'x'.padStart(5, 'ab')	// 'ababx'
		'x'.padStart(4, 'ab')	// 'abax'

		'x'.padEnd(5, 'ab')		// 'xabab'
		'x'.padEnd(4, 'ab')		// 'xaba'
	
	# 如果原字符串的长度,等于或大于指定的最小长度,则返回原字符串
		'xxx'.padStart(2, 'ab')		// 'xxx'
		'xxx'.padEnd(2, 'ab')		// 'xxx'
	
	# 如果用来补全的字符串与原字符串,两者的长度之和超过了指定的最小长度,则会截去超出位数的补全字符串
		'abc'.padStart(10, '0123456789')
		// '0123456abc'
	
	# 如果省略第二个参数,默认使用空格补全长度
		'x'.padStart(4) // '   x'
		'x'.padEnd(4)	// 'x   '
	
	# padStart的常见用途是为数值补全指定位数
		'1'.padStart(10, '0') // "0000000001"
		'12'.padStart(10, '0') // "0000000012"
		'123456'.padStart(10, '0') // "0000123456"

	# 另一个用途是提示字符串格式
		'12'.padStart(10, 'YYYY-MM-DD') // "YYYY-MM-12"
		'09-12'.padStart(10, 'YYYY-MM-DD') // "YYYY-09-12"

----------------------------
matchAll()					|
----------------------------
	* 返回一个正则表达式在当前字符串的所有匹配,详见《正则的扩展》的一章

