-------------------
String
-------------------
	# 使用单引号、双引号、反引号表示。
	# 通过 .length 属性获取字符串的长度。

	# 字符串转换
		* 几乎所有的值都有 toString() 方法（除了 null 和 undefined）
		* 使用 String() 函数，始终会返回表示相应类型值的字符串，包括 null 和 undefined
			* 如果值有 toString()方法，则调用该方法（不传参数）并返回结果。
			* 如果值是 null，返回 "null"。
			* 如果值是 undefined，返回 "undefined"。
		
		* 用加号操作符给一个值加上一个空字符串 "" 也可以将其转换为字符串。
	

-------------------
模板
-------------------
	# 使用 ${} 在 `` 中插入值。

		* 技术上讲，模板字面量不是字符串，而是一种特殊的 JavaScript 句法表达式，只不过求值后得到的是字符串。
		* 所有插入的值都会使用 toString() 强制转型为字符串，而且任何 JavaScript 表达式都可以用于插值。
		
		* 嵌套的模板字符串无须转义
			console.log(`Hello ${ `now is ${new Date()}` }`)  // Hello now is Wed May 15 2024 17:12:47 GMT+0800 (中国标准时间)`
		
		
		* 而且可以自己插入自己
			let content = "Hello";
			content = `${content + content}`; // HelloHello
		
	
	# 标签函数（tag function）
		* 邪门的表达式：[标签函数][字面量字符串]
		* 标签函数接收到的参数依次是原始字符串数组和对每个表达式求值的结果。

			function MyTagFunc (strs, exp1, exp2, exp3){
				// [Hello , , ,]
				for (let str of strs){
					console.log(str);
				}
				console.log(exp1);
				console.log(exp2);
				console.log(exp3);
				return "result val";
			}

			let ret = MyTagFunc`Hello ${'W'} ${'o'} ${'R'}`;

			console.log(ret);  // result val
		
		* 标签函数的第一个参数是表达式中非模板参数的字符子串。
		* 后面的参数，依次表示每个模板计算后的结果。
		* 该函数返回的结果就是最后的表达式结果。

		* 模板数量是可变的，所以通常应该使用剩余操作符（rest operator）将它们收集到一个数组中：
			function MyTagFunc (strs, ...expres){
				console.log(strs);		// ['Hello ', ' ', ' ', '']
				console.log(expres);	// ['W', 'o', 'R']
				return "result val";
			}

			let ret = MyTagFunc`Hello ${'W'} ${'o'} ${'R'}`;

			console.log(ret);  // result val
	
		* 使用默认的 String.raw 标签函来数获取原始的模板字面量内容（如换行符或Unicode字符）
			let r = String.raw`\r\n`;
			console.log(r);  //\r\n
		
		* 标签函数的第一个参数，即字符串数组的 `raw` 属性也可以获取到每个字符串的原始内容数组。
			function MyTagFunc (strs, ...expres){
				for (let rawStr of strs.raw) { // strs.raw 返回每个子串对应的原始字面量内容数组
					console.log(rawStr);
				}
			}
		