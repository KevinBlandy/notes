------------------------
BigDecimal				|
------------------------
	# 静态变量
		public final static int ROUND_UP =           0;
		public final static int ROUND_DOWN =         1;
		public final static int ROUND_CEILING =      2;
		public final static int ROUND_FLOOR =        3;
		public final static int ROUND_HALF_UP =      4;
		public final static int ROUND_HALF_DOWN =    5;
		public final static int ROUND_HALF_EVEN =    6;
		public final static int ROUND_UNNECESSARY =  7;

			* 各种舍入模式
	# 构造函数
		BigDecimal(char[] in)
		BigDecimal(char[] in, int offset, int len)
		BigDecimal(char[] in, int offset, int len, MathContext mc)
		BigDecimal(char[] in, MathContext mc)
		BigDecimal(double val)
		BigDecimal(double val, MathContext mc)
		BigDecimal(int val)
		BigDecimal(int val, MathContext mc)
		BigDecimal(String val)
		BigDecimal(String val, MathContext mc)
		BigDecimal(BigInteger val)
		BigDecimal(BigInteger unscaledVal, int scale)
		BigDecimal(BigInteger unscaledVal, int scale, MathContext mc)
		BigDecimal(BigInteger val, MathContext mc)
		BigDecimal(long val)
		BigDecimal(long val, MathContext mc)
	

------------------------
BigDecimal-静态方法		|
------------------------
	BigDecimal valueOf(double val)
		* 如果是通过 double 构建 BigDecimal, 那么不能直接使用构造函数来创建, 可能会有精度丢失的问题
			BigDecimal val = new BigDecimal(0.1D); // 0.1000000000000000055511151231257827021181583404541015625
		
		* 建议使用该静态方法来创建
			BigDecimal val = BigDecimal.valueOf(0.1D) // 0.1
		
		* 源码, 本质上就是转换为字符串来构建 BigDecimal 
			public static BigDecimal valueOf(double val) {
				return new BigDecimal(Double.toString(val));
			}

	BigDecimal valueOf(long val)
	BigDecimal valueOf(long unscaledVal, int scale)


------------------------
BigDecimal-实例方法		|
------------------------
	BigDecimal add(BigDecimal augend)
	BigDecimal add(BigDecimal augend, MathContext mc)
	
	BigDecimal subtract(BigDecimal subtrahend)
	BigDecimal subtract(BigDecimal subtrahend, MathContext mc)

	BigDecimal multiply(BigDecimal multiplicand)
	BigDecimal multiply(BigDecimal multiplicand, MathContext mc)

	BigDecimal divide(BigDecimal divisor)
	BigDecimal divide(BigDecimal divisor, int roundingMode)
	BigDecimal divide(BigDecimal divisor, int scale, int roundingMode)
	BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode)
	BigDecimal divide(BigDecimal divisor, MathContext mc)
	BigDecimal divide(BigDecimal divisor, RoundingMode roundingMode)
		* 加减乘除
			newScale					表示保留的小数位数
			roundingMode				指定舍入模式(可以是int或者枚举)
			mc							通过指定的 MathContext 处理精度和舍入
	
	BigDecimal remainder(BigDecimal divisor, MathContext mc)
	BigDecimal remainder(BigDecimal divisor) 
	BigDecimal[] divideAndRemainder(BigDecimal divisor) 
		* 返回的数组包含两个BigDecimal, 分别是商和余数, 其中商总是整数, 余数不会大于除数
		* 可以利用这个方法判断两个BigDecimal是否是整数倍数
			BigDecimal n = new BigDecimal("12.75");
			BigDecimal m = new BigDecimal("0.15");
			BigDecimal[] dr = n.divideAndRemainder(m);
			if (dr[1].signum() == 0) {
				// n是m的整数倍
			}

	boolean equals(Object x) 
		* 比较相等的时候, 不仅仅要比较值, 连末尾的0长度也要一样
			new BigDecimal("123.456").equals(new BigDecimal("123.45600")) // false

	int compareTo(BigDecimal val)
		* 比较大小

	int scale()
		* 返回小数位数, 该值包括了最后面的0
			new BigDecimal("123.4500").scale() // 4

	BigDecimal setScale(int newScale, int roundingMode)
		* 格式化小数
			newScale		表示保留的小数位数
			roundingMode	舍入模式
		
	
	BigDecimal stripTrailingZeros()
		* 可以将一个BigDecimal格式化为一个相等的, 但去掉了末尾0的BigDecimal
		* 值不会受到影响, 受到影响的是 scale

	long longValue()
		* 转换为long, 如果有小数会被丢弃, 超出范围会溢出

	long longValueExact()
		* 转换成long型, 如果有小数, 或者超出了范围, 会抛出异常	
	
	String toString();
		* 有必要时使用科学计数法

	String toPlainString();
		* 不使用任何指数

	String toEngineeringString();
		* 有必要时使用工程计数法
		* 工程记数法是一种工程计算中经常使用的记录数字的方法, 与科学技术法类似. 但要求10的幂必须是3的倍数
 
	BigInteger unscaledValue()
	BigDecimal ulp()
	int signum()
	int precision()
	BigDecimal plus(MathContext mc)
	BigDecimal plus()

	BigDecimal negate(MathContext mc)
	BigDecimal negate()
		* 取反


-------------------
要点
-------------------
	# BigDecimal 比较相等用 compareTo
		BigDecimal v1 = new BigDecimal("5.0");
		BigDecimal v2 = new BigDecimal("5");
		System.out.println(v1.equals(v2));			// false
		System.out.println(v1.compareTo(v2));		// 0