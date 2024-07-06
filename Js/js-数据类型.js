------------------------
数据类型
------------------------
	# ECMAScript有6种简单数据类型（也称为原始类型），和 Object 引用类型。

	# Undefined
		* 只有一个值，就是特殊值 undefined
		* 当使用 var 或 let 声明了变量但没有初始化时，就相当于给变量赋予了 undefined 值：
			let foo;
			console.log(foo)                // undefined
			console.log(foo == undefined)   // true
			console.log(typeof foo)         // 'undefined'
		
		* 这个特殊值的目的就是为了正式明确空对象指针（null）和未初始化变量的区别。

	# Null
		* 只有一个值，即特殊值 null
		* undefined 值是 由 null 值派生而来的，因此ECMA-262将它们定义为表面上相等
				console.log(null == undefined);   // true

	# Boolean
		* 有两个字面值: true 和 false
		* 其他数据类型可以转换为 boolean
			type		true					false
			String		非 "" 字符串			"" 字符串
			Number		非零数值（包括负无穷）	0、NaN
			Object		任意对象				null
			Undefined	N/A						undefined
		
		* 可以通过 Boolean(...) 进行转换
			var flag =  Boolean(undefined);
			console.log(flag);  // false

	# Number
	# String
	# Symbol
	# Object


------------------------
typeof
------------------------
	# 使用 typeof 可以获取到对象的数据类型

		let val = "123";
		let valType = typeof val; // typeof 是一个操作符而不是函数，所以不需要参数

		console.log(valType); // string

		"undefined"	表示值未定义
		"boolean"	表示值为布尔值
		"string"	表示值为字符串
		"number"	表示值为数值
		"object"	表示值为对象（不是函数）或 null
		"function"	表示值为函数
		"symbol"	表示值为符号
	
		
		* typeof 返回的结果本身就是字符串
		* typeof null 返回的是 "object"，为特殊值 null 被认为是一个对空对象的引用。
		* 对未声明的变量进行 typeof 计算返回的就是 "undefined"


		* 严格来讲，函数在 ECMAScript 中被认为是对象，并不代表一种数据类型。
		* 但，函数也有自己特殊的属性。为此，就有必要通过 typeof 操作符来区分函数和其他对象。

------------------------
instanceof
------------------------
	# 使用 instanceof 判断是否是指定的类型

		console.log(person instanceof Object);   // 变量person是Object吗？
		console.log(colors instanceof Array);    // 变量colors是Array吗？
		console.log(pattern instanceof RegExp); // 变量pattern是RegExp吗？

		* 如果变量是给定引用类型（由其原型链决定）的实例，则 instanceof 操作符返回 true。

