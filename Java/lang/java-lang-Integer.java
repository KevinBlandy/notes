------------------------
Integer					|
------------------------
	# int 包装类


------------------------
Integer-属性			|
------------------------
	# 静态属性
		Integer.MAX_VALUE;
			* int最大值
		Integer.MIN_VALUE;
			* int最小值

	# 成员属性

------------------------
Integer-方法			|
------------------------
	# 静态方法
		static Integer getInteger(String key, Integer defaultValue);
			* 尝试从系统环境中读取数据
			* 如果数据不存在,或者是转换异常,则返回默认值
		

		static int highestOneBit(int i)
			* 返回一个小于等于 i 的 2 的次方数
		
	# 成员方法
		
