-------------------
Number
-------------------
	# 使用 IEEE 754 格式（浮点数计算存在精度丢失）表示整数和浮点值（在某些语言中也叫双精度值）。
	# 直接写，默认是 10 进制
		let age = 10;
	
	# 可以使用 '_' 进行分割
		* 不能放在数值的最前面（leading）或最后面（trailing）。
		* 不能两个或两个以上的分隔符连在一起。
		* 小数点的前后不能有分隔符。
		* 科学计数法里面，表示指数的e或E前后不能有分隔符。
		* 不能紧跟着进制的前缀。

		const val = 1_000_000_000;

		console.log(val);  //1000000000

	
	# 0 开头表示八进制，但是如果 0 后面的数值超过了 7（八进制最大值），就会被当作是十进制处理
			let octalNum1 = 070;   // 八进制的56
			let octalNum2 = 079;   // 无效的八进制值，当成 79 处理
			let octalNum3 = 08;    // 无效的八进制值，当成 8 处理

	# 严格模式下，前缀 0 会被视为语法错误，如果要表示八进制值，应该使用前缀0o。
			let octalNum1 = 0o7; // 56
	
	# 0x 开头表示十六进制，十六进制的字母大小写都行
			let hexNum1 = 0xA;    // 十六进制10
			let hexNum2 = 0x1f;   // 十六进制31

	# 浮点值，数值中必须包含小数点，而且小数点后面必须至少有一个数字。虽然小数前面的整数不是必须的，但是推荐加上。
		let floatNum1 = 1.1;
		let floatNum2 = 0.1;
		let floatNum3 = .1;    // 有效，但不推荐
	
	# 存储浮点值使用的内存空间是存储整数值的两倍，所以ECMAScript总是想方设法把值转换为整数
		let floatNum1 = 1.;    // 小数点后面没有数字，当成整数1 处理
		let floatNum2 = 10.0; // 小数点后面是零，当成整数10 处理
	
	# 对于非常大或非常小的数值，浮点值可以用科学记数法（表示一个应该乘以10的给定次幂的数值）来表示。
			// e7 表示 3.125 要乘的 10 的 7 次幂
			let floatNum = 3.125e7; // 等于 31250000
	
	# 最大值和最小值
		* 常量
			Number.MIN_VALUE;			// 5e-324
			Number.MAX_VALUE;			// 1.7976931348623157e+308
	
		* 计算得到的数值结果超出了JavaScript可以表示的范围，那么这个数值会被自动转换为一个特殊的 Infinity（无穷）值
			Infinity			// 正无穷大
			-Infinity			// 负无穷大

			Number.POSITIVE_INFINITY;  // Infinity 的常量
			Number.NEGATIVE_INFINITY;	// -Infinity 的常量

			console.log(Number.POSITIVE_INFINITY + 1)  // Infinity

	
		* 可以使用 isFinite() 函数确定一个值是不是介于JavaScript能表示的最小值和最大值之间
			let result = Number.MAX_VALUE + Number.MAX_VALUE;
			console.log(isFinite(result));   // false 超出了范围
	
	# NaN 表示 “不是数值”（Not a Number），任何涉及 NaN 的操作始终返回 NaN（如NaN/10），NaN 不等于包括 NaN 在内的任何值。
		console.log(NaN == NaN); // false
	
		* 可以通过 isNaN()函数判断是否是合法的数值，该函数会尝试把参数转换为数值。
		* 某些非数值的值可以直接转换成数值，如字符串 "10" 或布尔值，任何不能转换为数值的值都会导致这个函数返回 true
			
			console.log(isNaN(NaN));      // true
			console.log(isNaN(10));       // false，10 是数值
			console.log(isNaN("10"));     // false，可以转换为数值10
			console.log(isNaN("blue"));   // true，不可以转换为数值
			console.log(isNaN(true));     // false，可以转换为数值1

		
		* 也可以使用 Number 对象的 isNaN() 方法
			Number.isNaN()
		
		* isNaN()可以用于测试对象。此时，首先会调用对象的 valueOf() 方法，然后再确定返回的值是否可以转换为数值。
		* 如果不能，再调用 toString() 方法，并测试其返回值。

	# Number()、parseInt()、parseFloat() 用于把非数值转换未数值
		* Number() 是转型函数，可用于任何数据类型。后两个函数主要用于将字符串转换为数值。
		
		* Number() 函数基于如下规则执行转换。
			* 布尔值，true转换为1，false转换为0。
			* 数值，直接返回。
			* null，返回0。
			* undefined，返回NaN
			* 字符串
				* 如果字符串包含数值字符，包括数值字符前面带加、减号的情况，则转换为一个十进制数值。
						// Number("1") 1
						// Number("123") 123
						// Number("011") 11 // 忽略前面的零
				* 如果字符串包含有效的浮点值格式如"1.1"，则会转换为相应的浮点值（同样，忽略前面的零）。
				* 如果字符串包含有效的十六进制格式如"0xf"，则会转换为与该十六进制值对应的十进制整数值。
				* 如果是空字符串（不包含字符），则返回 0。
				* 如果字符串包含除上述情况之外的其他字符，则返回 NaN。
			* 对象，调用 valueOf() 方法，并按照上述规则转换返回的值。如果转换结果是NaN，则调用 toString()方法，再按照转换字符串的规则转换。