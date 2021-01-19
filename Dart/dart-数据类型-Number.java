--------------------------
Number
--------------------------
	int 
		* 根据平台的不同, 整数值不大于64位
		* 在Dart VM上, 值可以从 -2的63次方 到 2的63次方减1
		* 编译成JavaScript的Dart使用JavaScript代码, 允许值从 -2的53次方到2的53次方减1


	double
		* 64位(双精度)浮点数, 由IEEE 754标准指定
	
	
	# int 和 double 都继承抽象类: num

--------------------------
num 
--------------------------
	# 静态属性
	# 静态方法
	# 实例方法

--------------------------
int 
--------------------------
	# 静态属性
	# 静态方法
	# 实例方法
		String toRadixString(int radix)
			* 转换为指定进制的字符串

		int abs();
			* 返回绝对值
		
		int toUnsigned(int width);
		int toSigned(int width);

		int round();
		int floor();
		int ceil();
		int truncate();

		double roundToDouble();
		double floorToDouble();
		double ceilToDouble();
		double truncateToDouble();


--------------------------
double 方法
--------------------------
	# 静态属性
	# 静态方法
	# 实例方法