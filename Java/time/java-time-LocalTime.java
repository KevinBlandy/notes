----------------------------
LocalTime					|
----------------------------
	# 本地时间
	# LocalTime类用来表示一天中的时间
	# 大部分方法都有 LocalDate 相似,换汤不换药
	# 构造(通过LocalTime静态方法)
		LocalTime LocalTime.now();
			* 获取系统当前时间
			* '会精确到毫秒级别'
			* 22:34:43.851

		LocalTime LocalTime.of(int hour,int minute,int second);
			* 根据指定时间创建LocalTime对象
		
		LocalTime parse(String time);
			* 根据字符串,解析成LocalDate对象
			* LocalTime time = LocalDate.parse("13:25:11");

		LocalTime parse(String time,DateTimeFormatter matter);
			* 根据字符串,和matter的格式,解析成LocalDate对象

----------------------------
LocalTime-方法				|
----------------------------
	# 读取相关
		int getHour();
			* 返回小时

		int getMinute();
			* 返回分钟

		int getSecond();
			* 返回秒


	# 配置相关
		LocalDateTime atDate(LocalDate date);
			* 设置 LocalDate 对象,加上当前的 LocalTime 
			* 组合为 LocalDateTime
		
		LocalTime plusHours(int year);
			* 添加多少小时
		
		LocalTime plusMinutes(int month);
			* 添加多少分钟
			* '还有很多添加的API,都一样'
		
		LocalTime minusHours(long hoursToSubtract);
			* 减去指定的小时数
			* '还有很多减去的API,都一样'

	# 格式化相关
		String formar(DateTimeFormatter matter);
			* 根据 DateTimeFormatter 返回被格式化后的时间字符串
		


