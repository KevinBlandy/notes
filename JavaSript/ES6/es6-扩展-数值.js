------------------------
数值					|
------------------------
	1,二进制和八进制表示法
	2,Number.isFinite(), Number.isNaN()
	3,Number.parseInt(), Number.parseFloat()
	4,Number.isInteger()
	5,Number.EPSILON
	6,安全整数和 Number.isSafeInteger()
	7,Math 对象的扩展
	8,指数运算符

------------------------
二进制和八进制			|
------------------------
	# 二进制,八进制,十六进制表示
		let x = 0o000227
        let y = 0b010101
        let z = 0xF88481
		
		* 把它们转换为10进制
			Number('0b111')		// 7
			Number('0o10')		// 8

-----------------------------------
新增函数/常量						|
------------------------------------
	Number.isFinite()
		* 用来检查一个数值是否为有限的(finite),即不是Infinity(正的无穷大的数值)。
			Number.isFinite(15);	// true
			Number.isFinite(0.8);	// true
			Number.isFinite(NaN);	// false
			Number.isFinite(Infinity);	// false
			Number.isFinite(-Infinity); // false
			Number.isFinite('foo');		// false
			Number.isFinite('15');		// false
			Number.isFinite(true);		// false
		
			* 如果参数类型不是数值，Number.isFinite一律返回false
	

	Number.isNaN()
		* 判断数据是否是一个非数字

	Number.parseInt()
	Number.parseFloat()
		* 把指定数据转换为Int类型的数据
		* 其实就是那个全局函数
			Number.parseInt === parseInt	 // true
			Number.parseFloat === parseFloat // true
	
	Number.isInteger()
		* 判断一个数是否为整数
		* avaScript 内部,整数和浮点数采用的是同样的储存方法,所以 25 和 25.0 被视为同一个值
			umber.isInteger(25)		// true
			Number.isInteger(25.0)	// false
	
		* 如果参数不是数值，Number.isInteger返回false。
		* '对数据精度的要求较高,不建议使用Number.isInteger()判断一个数值是否为整数'
	
	Number.isSafeInteger()
		* 是否是一个安全的整数,也就是说值是否在 MAX_SAFE_INTEGER 和 MIN_SAFE_INTEGER 之间
		

	Number.EPSILON
		* 它表示 1 与大于 1 的最小浮点数之间的差
		* 对于 64 位浮点数来说,大于 1 的最小浮点数相当于二进制的1.00..001,小数点后面有连续 51 个零,这个值减去 1 之后,就等于 2 的 -52 次方
	
	Number.MAX_SAFE_INTEGER
		* JavaScript 能够准确表示的最大整数

	Number.MIN_SAFE_INTEGER
		* JavaScript 能够准确表示的最小整数


-----------------------------------
Math-扩展							|
------------------------------------
	Math.trunc()
		* 去除一个数的小数部分,返回整数部分
		* 对于非数值,Math.trunc内部使用Number方法将其先转为数值
		* 对于空值和无法截取整数的值,返回NaN
			Math.trunc(4.9)		// 4
			Math.trunc(-4.1)	// -4
	
	Math.sign()
		* 判断一个数到底是正数,负数,还是零,对于非数值,会先将其转换为数值
		* 它会返回五种值
			参数为正数	返回+1
			参数为负数	返回-1
			参数为0		返回；
			参数为-0	返回-0
			其他值		返回NaN
	
	Math.cbrt()
		* 计算一个数的立方根

	Math.clz32()
		* 返回一个数的 32 位无符号整数形式有多少个前导 0
	
	Math.imul()
		* 返回两个数以 32 位带符号整数形式相乘的结果，返回的也是一个 32 位的带符号整数
	
	Math.fround()
		* 返回一个数的32位单精度浮点数形式
	
	Math.hypot()	
		* 返回所有参数的平方和的平方根
	
	...

-----------------------------------
新的运算符							|
------------------------------------
	* S2016 新增了一个指数运算符 **
		2 ** 2 // 4
		2 ** 3 // 8

	
	* 指数运算符可以与等号结合，形成一个新的赋值运算符（**=）。

		let a = 1.5;
		a **= 2;
		// 等同于 a = a * a;

		let b = 4;
		b **= 3;
		// 等同于 b = b * b * b;




