----------------------------
String
----------------------------
	# 字符串包装对象
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/String

	# 构造函数
		String(thing)
			
			* thing 任何要转换为字符串的内容。

----------------------------
this
----------------------------
	length

	String.prototype[@@iterator]()
	String.prototype.anchor()
		* 已弃用
	String.prototype.at(index)
		* 返回参数指定位置的字符，支持负索引（即倒数的位置）。
			index
				* 搜因

	String.prototype.big()
		* 已弃用
	String.prototype.blink()
		* 已弃用
	String.prototype.bold()
		* 已弃用
	String.prototype.charAt()
	String.prototype.charCodeAt()
	String.prototype.codePointAt()
	String.prototype.concat()
	String.prototype.endsWith(searchString, endPosition)
		* 字符串是否以指定的字串结尾
			searchString
				* 指定的子串
			endPosition
				* 找到 searchString 的末尾位置（即 searchString 最后一个字符的索引加 1）。默认为 str.length。

	String.prototype.fixed()
		* 已弃用
	String.prototype.fontcolor()
		* 已弃用
	String.prototype.fontsize()
		* 已弃用
	String.prototype.includes(searchString, position)
		* 是否包含子串
			searchString
				* 要搜索的子串
			position
				* 在字符串中开始搜索 searchString 的位置。默认值为 0。
		
	String.prototype.indexOf()
	String.prototype.isWellFormed()
	String.prototype.italics()
		* 已弃用
	String.prototype.lastIndexOf()
	String.prototype.link()
		* 已弃用
	String.prototype.localeCompare()
	String.prototype.match()
	String.prototype.matchAll(regexp)
		* 返回正则表达式在当前字符串的所有匹配，返回的是一个迭代器
			regexp
				* 正则表达式


	String.prototype.normalize()

	String.prototype.padEnd(targetLength, padString)
		* 在字符串，尾部补全 padString 到 targetLength 的长度
			targetLength
				* 长度
			padString
				* 长度不足时，补全用的字符串

	String.prototype.padStart(targetLength, padString)
		* 在字符串，头部补全 padString 到 targetLength 的长度
			targetLength
				* 长度
			padString
				* 长度不足时，补全用的字符串


	String.prototype.repeat(count)
		* 把字符串重复 count 次后返回 

	String.prototype.replace(pattern, replacement)
		* 替换第一个匹配。
			pattern
				* 匹配的字符串
			replacement
				* 替换匹配的的字符串

	String.prototype.replaceAll(pattern, replacement)
		* 替换字符串中的所有匹配字符串
			pattern
				* 匹配的字符串/或者是正则
			replacement
				* 替换匹配的的字符串
				* 也可以是一个返回字符串的函数

	String.prototype.search()
	String.prototype.slice()
	String.prototype.small()
		* 已弃用
	String.prototype.split()
	String.prototype.startsWith(searchString, position)
		* 是否以指定的字符串开头
			searchString
				* 指定的字符串
			position
				* 在字符串中开始搜索 searchString 的位置。默认值为 0。
			
	String.prototype.strike()
		* 已弃用
	String.prototype.sub()
		* 已弃用
	String.prototype.substr()
		* 已弃用
	String.prototype.substring(indexStart, indexEnd)
		* 切割字串，留头不留尾
			indexStart
				* 开始的索引
			indexEnd
				* 结束的索引，默认为 length

	String.prototype.sup()
		* 已弃用
	String.prototype.toLocaleLowerCase()
	String.prototype.toLocaleUpperCase()
	String.prototype.toLowerCase()
	String.prototype.toString()
	String.prototype.toUpperCase()
	String.prototype.toWellFormed()
	String.prototype.trim()
		* 删除字符串开头和结尾的空白
	String.prototype.trimEnd()
		* 删除字符串结尾的空白后返回
		* 还有个别名方法 trimRight()
	String.prototype.trimStart()
		* 删除字符串开头的空白后返回
		* 还有个别名方法 trimLeft()

	String.prototype.valueOf()


----------------------------
static
----------------------------

	String.fromCharCode()
	String.fromCodePoint(num1, num2, /* …, */ numN)
		* 于从 Unicode 码点返回对应字符，是 fromCharCode 的进阶版
			String.fromCharCode(0x20BB7) // "ஷ"
		
		* 如果传递多个参数，则它们会被合并成一个字符串返回。
		
	String.raw(strings, ...substitutions)
		* 返回一个斜杠都被转义（即斜杠前面再加一个斜杠）的字符串，往往用于模板字符串的处理方法。
			strings
				* 模板字符串数组对象，例如 { raw: ['foo', 'bar', 'baz'] }，应该是一个具有 raw 属性的对象，其值是一个类数组的字符串对象。

			...substitutions
				* 包含的替换表达式对应的值。
		* 可以作为处理模板字符串的基本方法，它会将所有变量替换，而且对斜杠进行转义，方便下一步作为字符串来使用。

