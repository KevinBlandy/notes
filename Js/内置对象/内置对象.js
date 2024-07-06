---------------
内置对象
---------------
	# JavaScript 中所有的标准内置对象、以及它们的方法和属性。
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects
	
	# globalThis 顶层对象
		* JavaScript 语言存在一个顶层对象，它提供全局环境（即全局作用域），所有代码都是在这个环境中运行。

			* 浏览器里面，顶层对象是 window ，但 Node 和 Web Worker 没有window。
			* 浏览器和 Web Worker 里面， self 也指向顶层对象，但是 Node 没有self。
			* Node 里面，顶层对象 是global，但其他环境都不支持。
		
		* 同一段代码为了能够在各种环境，都能取到顶层对象，现在一般是使用 this 关键字，但是有局限性。
			* 全局环境中，this 会返回顶层对象，但 ES6 模块中 this 返回的是 undefined。Node.js 模块中 this 返回的是当前模块
			
		* ES2020 在语言标准的层面，引入 globalThis 作为顶层对象。
		* 也就是说，任何环境下，globalThis 都是存在的，都可以从它拿到顶层对象，指向全局环境下的 this。


---------------
内置对象类型
---------------
	# 基本对象
		Object
		Function
		Boolean
		Symbol
	
	# 错误对象

		Error
		AggregateError
		EvalError
		RangeError
		ReferenceError
		SyntaxError
		TypeError
		URIError
		InternalError	// 非标准
	
	# 数字和日期对象

		Number
		BigInt
		Math
		Date
	
	# 字符串

		String
		RegExp
	
	# 可索引的集合对象

		Array
		Int8Array
		Uint8Array
		Uint8ClampedArray
		Int16Array
		Uint16Array
		Int32Array
		Uint32Array
		BigInt64Array
		BigUint64Array
		Float32Array
		Float64Array
	
	# 使用键的集合对象

		Map
		Set
		WeakMap
		WeakSet
	
	# 结构化数据

		ArrayBuffer
		SharedArrayBuffer
		Atomics
		DataView
		JSON
	
	# 内存管理对象
		WeakRef
		FinalizationRegistry
	
	# 控制抽象对象

		Iterator
		AsyncIterator
		Promise
		GeneratorFunction
		AsyncGeneratorFunction
		Generator
		AsyncGenerator
		AsyncFunction

	# 反射
		Reflect
		Proxy
	
	# 国际化

		Intl
		Intl.Collator
		Intl.DateTimeFormat
		Intl.DisplayNames
		Intl.DurationFormat
		Intl.ListFormat
		Intl.Locale
		Intl.NumberFormat
		Intl.PluralRules
		Intl.RelativeTimeFormat
		Intl.Segmenter