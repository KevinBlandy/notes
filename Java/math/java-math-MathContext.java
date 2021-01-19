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
	