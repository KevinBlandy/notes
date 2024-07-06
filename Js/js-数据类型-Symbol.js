-----------------
Symbol
-----------------
	# Symbol（符号）是ECMAScript 6新增的原始值数据类型。
		* 符号实例是唯一、不可变的。
		* 符号的用途是确保对象属性使用唯一标识符，不会发生属性冲突的危险。
	
		* 目的是用来创建唯一记号，进而用作 '非字符串形式' 的对象属性。
		* Symbol 值作为对象属性名时，不能用点运算符。
		* Symbol 值作为属性名，遍历对象的时候，该属性不会出现在 for...in、for...of 循环中，也不会被 Object.keys()、Object.getOwnPropertyNames()、JSON.stringify() 返回。


	# 使用 Symbol()函数初始化
		let symbol = Symbol();
		console.log(symbol);			// Symbol()
		console.log(typeof symbol);		// symbol
	
		
		* 该参数支持一个字符串参数作为描述符
		* 但这个字符串参数与符号定义或标识完全无关
	
		* Symbol() 函数不能与 new 关键字一起作为构造函数使用，是为了避免创建符号包装对象

			new Symbol(); // VM251:1 Uncaught TypeError: Symbol is not a constructor
	



	# 全局符号注册表
		* 如果运行时的不同部分需要 '共享' 和 '重用'  Symbol 实例，那么可以用一个字符串作为键，在全局符号注册表中创建并重用符号。
			let symbol1 = Symbol.for('myKey');
			let symbol2 = Symbol.for('myKey');

			console.log(symbol1 == symbol2);  // true
	
		
		* 全局注册表中的符号必须使用字符串 Key 来创建，因此作为参数传给 Symbol.for() 的任何值都会被转换为字符串。
			let symbol = Symbol.for();
			console.log(symbol); // Symbol(undefined)
		
		* 使用 Symbol.keyFor() 查询全局 Symbol 对应的 key ，如果参数不是全局 Symbol 则返回undefined。

			let symbol = Symbol.for("myKey");

			console.log(Symbol.keyFor(symbol));         // myKey
			console.log(Symbol.keyFor(Symbol('FOO')))   // undefined
		
			* 如果传给 Symbol.keyFor() 的不是 Symbol，则该方法抛出 TypeError


		* 凡是可以使用字符串或数值作为属性的地方，都可以使用符号。
	
	
	# 常用内置符号
		
		* 这些内置符号最重要的用途之一是重新定义它们，从而改变原生结构的行为。
		* 这些内置符号也没有什么特别之处，它们就是全局函数 Symbol 的普通字符串属性，指向一个符号的实例。
		* 所有内置符号属性都是不可写、不可枚举、不可配置的。

		* ECMAScript 规范经常会引用符号在规范中的名称，前缀为@@。比如，@@iterator 指的就是 Symbol.iterator。


		Symbol.asyncIterator
			* 一个方法，该方法返回对象默认的 AsyncIterator。由 for-await-of 语句使用。
			* 换句话说，这个符号表示实现异步迭代器API的函数。
		
		Symbol.hasInstance
		Symbol.isConcatSpreadable
		Symbol.iterator
		Symbol.match
		Symbol.replace
		Symbol.search
		Symbol.species
		Symbol.split
		Symbol.toPrimitive
		Symbol.toStringTag
		Symbol.unscopables

