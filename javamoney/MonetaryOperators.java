----------------------------
MonetaryOperators
----------------------------
	# 定义了一些常用的MonetaryOperator的实现

	public static MonetaryOperator reciprocal() 
		* 取倒数
    public static MonetaryOperator permil(BigDecimal decimal) 
    public static MonetaryOperator permil(Number number) 
    public static MonetaryOperator permil(Number number, MathContext mathContext) 
		* 获取千分比例值
	
    public static MonetaryOperator percent(BigDecimal decimal) 
    public static MonetaryOperator percent(Number number) 
		* 获取百分比例值
    public static MonetaryOperator minorPart() 
		* 获取小数部分
	public static MonetaryOperator majorPart() 
		* 获取整数部分
	public static MonetaryOperator rounding() 
	public static MonetaryOperator rounding(RoundingMode roundingMode) {
	public static MonetaryOperator rounding(RoundingMode roundingMode, int scale) 
	public static MonetaryOperator rounding(int scale);
		* 获取舍入模式

