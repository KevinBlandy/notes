------------------
注释指令
------------------
	# 所谓“注释指令”，指的是采用 JS 双斜杠注释的形式，向编译器发出的命令。

	// @ts-nocheck
		* 告诉编译器不对当前脚本进行类型检查，可以用于 TypeScript 脚本，也可以用于 JavaScript 脚本。
	
	// @ts-check
		* 如果一个 JavaScript 脚本顶部添加了 '// @ts-check'，那么编译器将对该脚本进行类型检查，不论是否启用了checkJs编译选项。
	
	// @ts-ignore
		* 告诉编译器不对下一行代码进行类型检查，可以用于 TypeScript 脚本，也可以用于 JavaScript 脚本。
	
	// @ts-expect-error
		* 主要用在测试用例，当下一行有类型错误时，它会压制 TypeScript 的报错信息（即不显示报错信息），把错误留给代码自己处理。
	

	
------------------
JS 文档
------------------
	# TypeScript 直接处理 JS 文件时，如果无法推断出类型，会使用 JS 脚本里面的 JSDoc 注释。
	# 使用 JSDoc 时，有两个基本要求。
		（1）JSDoc 注释必须以 '/**' 开始，其中星号（*）的数量必须为两个。若使用其他形式的多行注释，则 JSDoc 会忽略该条注释。
		（2）JSDoc 注释必须与它描述的代码处于相邻的位置，并且注释在上，代码在下。

		/**
		 * @param {string} somebody
		 */
		function sayHello(somebody) {
		  console.log('Hello ' + somebody);
		}
	
		// @param是一个 JSDoc 声明，表示下面的函数sayHello()的参数somebody类型为string。
	

