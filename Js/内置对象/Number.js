--------------------
Number
--------------------
	# 数值类型包装类

	# 构造函数
		Number(value)

			* 所创建对象的数值。
	


---------------------
实例
---------------------

	Number.prototype.toExponential()
		* 返回以科学记数法（也称为指数记数法）表示的数值字符串

	Number.prototype.toFixed(digits)
		* 返回包含指定小数点位数的数值字符串
		* 如果数值本身小数点长度超过了，那么就进行四舍五入，如果小数位数不足，则小数部分用零填充

	Number.prototype.toLocaleString()
	Number.prototype.toPrecision(precision)
		* 返回一个以指定精度表示该数字的字符串。

	Number.prototype.toString()
	Number.prototype.valueOf()

---------------------
静态
---------------------

	Number.EPSILON
		* 极小的常量Number.EPSILON。根据规格，它表示 1 与大于 1 的最小浮点数之间的差。

	Number.MAX_SAFE_INTEGER
	Number.MAX_VALUE
	Number.MIN_SAFE_INTEGER
	Number.MIN_VALUE
	Number.NaN
	Number.NEGATIVE_INFINITY
	Number.POSITIVE_INFINITY

	Number.isFinite(testValue)
		* 检查一个数值是否为有限的（finite），即不是Infinity。
		* 对于非数值一律返回 false

	Number.isInteger()
		* 辨别一个数值是否保存为整数。有时候，小数位如果全是 0 ，则也会认为是一个整数。
		    console.log(Number.isInteger(1));     // true
			console.log(Number.isInteger(1.00)); // true
			console.log(Number.isInteger(1.01)); // false
	
	Number.isNaN(value)
		* 检查一个值是否为 NaN。
		* 如果参数类型不是 NaN，一律返回false。

	Number.isSafeInteger(testValue)
		* 判断是否为安全整数的值，即能够准确表示的整数范围（-2^53到2^53之间）

	Number.parseFloat()
	Number.parseInt()
