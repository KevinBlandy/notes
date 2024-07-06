-------------------------
Symbol
-------------------------
	# Symbol extends Object/Function
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Symbol
	
	# 构造函数
		Symbol(description)

			description
				* 字符串，用于描述 Symbol

-------------------------
this
-------------------------

	Symbol.prototype.description

	Symbol.prototype[@@toPrimitive]
	Symbol.prototype.toString()
	Symbol.prototype.valueOf()

-------------------------
static
-------------------------

	Symbol.for()
	Symbol.keyFor()

	Symbol.asyncIterator
		* 指定了一个对象的默认异步迭代器。如果一个对象设置了这个属性，它就是异步可迭代对象，可用于 for await...of 循环。

	Symbol.hasInstance
		* 指向一个内部方法。当其他对象使用instanceof运算符，判断是否为该对象的实例时，会调用这个方法。
		* 比如，foo instanceof Foo在语言内部，实际调用的是 Foo[Symbol.hasInstance](foo)。

	Symbol.isConcatSpreadable
		* 等于一个布尔值，表示该对象用于 Array.prototype.concat() 时，是否可以展开。

	Symbol.iterator
		* 指向该对象的默认遍历器方法。

	Symbol.match
		* 指向一个函数。当执行 str.match(myObject) 时，如果该属性存在，会调用它，返回该方法的返回值。
	
	Symbol.matchAll
	Symbol.replace
		* 向一个方法，当该对象被 String.prototype.replace 方法调用时，会返回该方法的返回值。

	Symbol.search
		* 指向一个方法，当该对象被 String.prototype.search 方法调用时，会返回该方法的返回值。

	Symbol.species
		* 指向一个构造函数。创建衍生对象时，会使用该属性。

	Symbol.split
		* 指向一个方法，当该对象被 String.prototype.split 方法调用时，会返回该方法的返回值。

	Symbol.toPrimitive
		* 指向一个方法。该对象被转为原始类型的值时，会调用这个方法，返回该对象对应的原始类型值。

	Symbol.toStringTag
	Symbol.unscopables
		* 指向一个对象。该对象指定了使用with关键字时，哪些属性会被with环境排除。
	