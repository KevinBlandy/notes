------------------------
Number					|
------------------------
	# 数字类型的包装值
		var x = 5;
		var y = new Number(5);
		x == y		//true
		x === y		//false

	# 创建
		var num = new Number(value);
			* 如果一个参数值不能转换为一个数字将返回 NaN (非数字值)。

	# 静态属性
		MAX_VALUE	
			* 可表示的最大的数

		MIN_VALUE	
			* 可表示的最小的数

		NEGATIVE_INFINITY	
			* 负无穷大,溢出时返回该值

		POSITIVE_INFINITY	
			* 正无穷大，溢出时返回该值

		NaN	
			* 非数字值
		
		Number.EPSILON
			* 它表示 1 与大于 1 的最小浮点数之间的差
		
		
		Number.MAX_SAFE_INTEGER
			* JavaScript 能够准确表示的最大整数

		Number.MIN_SAFE_INTEGER
			* JavaScript 能够准确表示的最小整数


	
	# 静态方法
		isFinite()
			* 判断数据是不是一个非无穷大的数据
			* !Infinity 
			* 如果参数类型不是数值，Number.isFinite一律返回false
		
		isNaN()
			* 判断数据是否是一个非数字
			* NaN
		
		parseInt()
		parseFloat()
			* 把指定数据转换为Int类型的数据
			* 其实就是那个全局函数
				Number.parseInt === parseInt	 // true
				Number.parseFloat === parseFloat // true
		
		isInteger()
			* 判断一个数是否为整数
			* '对数据精度的要求较高,不建议使用Number.isInteger()判断一个数值是否为整数'
		
		isSafeInteger()
			* 是否是一个安全的整数,也就是说值是否在 MAX_SAFE_INTEGER 和 MIN_SAFE_INTEGER 之间

	
	# 实例方法
		toExponential(x)	
			* 把对象的值转换为指数计数法。
			* 如果超出,4舍5入,不足,补0

		toFixed(x)
			* 把数字转换为字符串，结果的小数点后有指定位数的数字。
			* 如果超出,4舍5入,不足,补0

		toPrecision(x)
			* 把数字格式化为指定的长度。
			* 如果超出,4舍5入,不足,补0

		toString()
			* 把数字转换为字符串，使用指定的基数。

		valueOf()
			* 返回一个 Number 对象的基本数字值。
			* 返回的是10进制