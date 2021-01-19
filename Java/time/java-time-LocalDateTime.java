-------------------------
LocalDateTime			 |
-------------------------
	# 表示时间+日期
	# 构造(通过静态方法)
		LocalDateTime LocalDateTime.now();
			* 获取系统当前时间
			* 2016-05-24T22:41:30.852

		LocalDateTime LocalDateTime.of(int year,int month,int dayOfMonth,int hour,int minute,int second);
			* 根据指定的日期和时间返回LocalDateTime对象
		
		 LocalDateTime parse(CharSequence text, DateTimeFormatter formatter);
			* 根据 formatter 来格式出 LocalDateTime 对象
		
		LocalDateTime of(LocalDate date, LocalTime time)
			* 根据日期和时间构造
			* 可以构造一个指定日期(今天)的最小时间和最大时间
				LocalDateTime.of(LocalDate.now(),LocalTime.MAX);	2019-04-18 23:59:59.999999999
				LocalDateTime.of(LocalDate.now(),LocalTime.MIN);	2019-04-18 00:00
			
		LocalDateTime ofInstant(Instant instant, ZoneId zone)
			* 根据时间戳和时区，转换为本地时间

-------------------------
LocalDateTime-api		 |
-------------------------
	# 读取相关
		int getYear();
			* 返回日期中的年份
		int getMonth();
			* 返回日期中的月份
		int getDayOfMonth();
			* 返回月份中的第几天
		int getHour();
			* 返回小时
		int getMinute();
			* 返回分钟
		int getSecond();
			* 返回秒
		
		int get(TemporalField field)
			* 根据参数获取指定的数据
			* 可以使用枚举:ChronoField

	# 设置相关
		LocalDateTime withDayOfMonth(int num);
			* 设置月中的天数

		LocalDate toLocalDate();
			* 转换为 LocalDate 对象

		LocalTime toLocalTime();
			* 转换为 LocalTime 对象
		
		LocalDateTime plusYears(int year);
			* 添加多少年

		LocalDateTime plusMonths(int month);
			* 添加多少个月
			* '还有很多添加时分秒的API,都一样'
		
		LocalDateTime minusWeeks(long weeks)
			* 返回N周之前的现在时间
			* '还有很多添加时分秒的API,都一样'

	# 格式化相关
		String formart(DateTimeFormatter matter);
			* 根据 DateTimeFormatter 返回被格式化后的时间字符串
		

-------------------------
与时间戳的转换
-------------------------
	#  转换为时间戳的几种方式
		LocalDateTime now = LocalDateTime.now();
		
		long timestamp = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
		System.out.println(timestamp);
		
		timestamp = now.atOffset(ZoneOffset.of("+08:00")).toInstant().toEpochMilli();
		System.out.println(timestamp);
		
		timestamp = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		System.out.println(timestamp);
	
	# 时间戳转换为LocalDateTime
		
		LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())