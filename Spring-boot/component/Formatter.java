----------------------
Formatter			  |
----------------------
	# 一个数据类型的转换接口
		* 可以用于请求参数的格式化

	# 抽象接口
		String print(T object, Locale locale);
		T parse(String text, Locale locale) throws ParseException;