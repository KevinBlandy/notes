----------------------------
RegExp
----------------------------
	# 正则表达式
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/RegExp
	
	# 字面量形式创建
		* 由斜杠 (/) 包裹而不是引号包围。
		* 语法：let expression = /pattern/flags;

		let reg = /ab+c/i; //字面量形式;
	
	# 构造函数创建
		RegExp(pattern[, flags])
		
		* pattern 格式，可以是正则字符串，或者是正则的字面量对象
		* flags 可选的flag

			g: 全局模式，表示查找字符串的全部内容，而不是找到第一个匹配的内容就结束。
			i: 不区分大小写，表示在查找匹配时忽略pattern和字符串的大小写。
			m: 多行模式，表示查找到一行文本末尾时会继续查找。
			y: 粘性匹配，表示只查找从lastIndex开始及之后的字符串。
			u: Unicode模式，启用Unicode匹配。
			s: dotAll模式，表示元字符．匹配任何字符（包括\n或\r）。
		
		new RegExp("ab+c", "i"); // 构造函数
	
----------------------------
实例
----------------------------
	RegExp.prototype.dotAll
	RegExp.prototype.flags
		* 返回正则表达式的修饰符。

	RegExp.prototype.global
	RegExp.prototype.hasIndices
	RegExp.prototype.ignoreCase
	RegExp.prototype.multiline
	RegExp.prototype.source
	RegExp.prototype.sticky
		* 正则表达式是否设置了 y 修饰符。

	RegExp.prototype.unicode
		* 正则表达式是否设置了 u 修饰符。


	RegExp.prototype.unicodeSets

	RegExp.prototype[@@match]()
		* 对给定字符串执行匹配并返回匹配结果。

	RegExp.prototype[@@matchAll]()
		* 对给定字符串执行匹配，返回所有匹配结果。

	RegExp.prototype[@@replace]()
		* 给定新的子串，替换所有匹配结果。

	RegExp.prototype[@@search]()
		* 在给定字符串中搜索匹配项，并返回在字符串中找到字符索引。

	RegExp.prototype[@@split](str[, limit])
		* 通过将给定字符串拆分为子字符串，并返回字符串形成的数组。
			str：切割操作的目标字符串
			limit：可选。一个为了限制切割数量的特定整数。

	RegExp.prototype.compile()
		* 运行脚本的期间（重新）编译正则表达式。
		* 已弃用
	
	RegExp.prototype.exec(str)
		* 在 str 字符串中执行匹配项的搜索。

	RegExp.prototype.test(str)
		* 查看正则表达式与指定的字符串是否匹配。返回 true 或 false。

	RegExp.prototype.toString()

----------------------------
静态
----------------------------
	RegExp.lastIndex
