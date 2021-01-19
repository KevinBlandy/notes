-------------------------
java.util.Calendar		 |
-------------------------
	# 无法直接创建,需要通过工厂来获取
		Calendar calendar = Calendar.getInstance();


-------------------------
实例属性/字段			 |
-------------------------
	# 字段 
		YEAR MONTH DAT WEEK
		
	# 方法
		int get(Calendar.WEEK_OF_YEAR);
			|--获取指定字段的值，返回 int 类型数据。
		void add(Calendar.DAY_OF_MONTH,1);
			|--据日历的规则，为给定的日历字段添加或减去指定的时间量。
		int get(Calendar.DAY_OF_MONTH);
			|--获取，月份中的第几天。返回 int 类型。
		void set(Calendar.DAY_OF_MONTH,23);
			|--设置月份中的天数为23。
		void setTime(Date);
			|--把当前 Calendar 设置为Date 时间
		Date getTime();
			|--把日历类转换成 Date 类。返回的是 Date 对象。
		long getTimeInMillis()
			|-转换为毫秒值
		
		getActualMaximum(int field)
			|-获取指定'数据的'的最大值
			|-数据可以是月份,(获取指定月最多只有多少天)
		getActualMinimum(in field)
			|--获取指定'数据的'的最小值