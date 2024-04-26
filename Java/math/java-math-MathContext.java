------------------------
MathContext				|
------------------------
	# 主要的作用是处理运算过程中的精度问题
	# 静态变量
		MathContext UNLIMITED = new MathContext(0, RoundingMode.HALF_UP)
			* 不限制长度, 使用四舍五入的方式来舍入小数 

		MathContext DECIMAL32 = new MathContext(7, RoundingMode.HALF_EVEN)
		MathContext DECIMAL64 = new MathContext(16, RoundingMode.HALF_EVEN)
		MathContext DECIMAL128 = new MathContext(34, RoundingMode.HALF_EVEN)
	
	# 构造方法
		MathContext(int setPrecision)
		MathContext(int setPrecision,RoundingMode setRoundingMode)
		MathContext(String val)

		setPrecision
			* 精度,一共最长最少长度(包含小数)
		
		setRoundingMode
			* 舍入模式
	
	# 实例方法

		int getPrecision()
		RoundingMode getRoundingMode()
	

	# 在乘法中，不一定能保证正确的精度设置

		BigDecimal number1 = new BigDecimal("0.01");
		BigDecimal number2 = new BigDecimal("0.2");

		// 保留 2 位精度
		BigDecimal result = number1.multiply(number2, new MathContext(2, RoundingMode.DOWN));

		// 结果却是 3 位
		// Result: 0.002
		System.out.println("Result: " + result);
		
		// 可以直接用 setScale 修改精度
		// 0.00
		System.out.println(result.setScale(2, RoundingMode.DOWN));