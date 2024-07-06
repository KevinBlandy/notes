--------------------
Function
--------------------
	# 在 JavaScript 中，每个函数实际上都是一个 Function 对象。
		* 这意味着函数也是对象。由于函数是对象，因此也就具有能够增强自身行为的方法。

	# 构造函数

		Function(functionBody)
		Function(arg0, functionBody)
		Function(arg0, arg1, functionBody)
		Function(arg0, arg1, /* …, */ argN, functionBody)
	
--------------------
this
--------------------

	length
	Function.name
	Function.prototype.prototype

	Function.prototype[@@hasInstance]()

	Function.prototype.apply(thisArg, argsArray)
		* thisArg 调用 func 时提供的 this 值。如果函数不处于严格模式，则 null 和 undefined 会被替换为全局对象，原始值会被转换为对象。
		* argsArray 一个类数组对象，用于指定调用 func 时的参数，或者如果不需要向函数提供参数，则为 null 或 undefined。
	
	Function.prototype.bind(thisArg, arg1, arg2, /* …, */ argN)
		* thisArg 在调用绑定函数时，作为 this 参数传入目标函数 func 的值
		* arg bind 在调用 func 时，插入到传入绑定函数的参数前的参数。 

		* 返回绑定后的函数

	Function.prototype.call(thisArg, arg1, arg2, /* …, */ argN)
		* thisArg 在调用 func 时要使用的 this 值。
		* arg 函数的参数。

	Function.prototype.toString()

--------------------
static
--------------------
