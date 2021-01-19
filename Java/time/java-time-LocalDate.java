------------------------
LocalDate				|
------------------------
	# LocalDate类使用ISO日历表示年月日.
	# 这个东西吧跟 Calendar 相比就是比较精确
			* Calendar 获取到的数据,月份会小一个月,我们需要加1操作.而这个就不用了

	# 构造(根据LocalDate静态方法获取)
		LocalDate LocalDate.now();
			* 获取系统当前日期

		LocalDate LocalDate.of(int year,int month,int dayOfMonth);
			* 根据指定日期创建LocalDate对象
		
		LocalDate parse(String date);
			* 根据字符串,解析成LocalDate对象
			* LocalDate date = LocalDate.parse("2014-03-18");
		
		LocalDate parse(String time,DateTimeFormatter matter);
			* 根据字符串和 matter 的格式,解析成LocalDate对象
		

------------------------
LocalDate-api				|
------------------------
	# 读取相关
		int getYear();
			* 返回日期中的年份

		Month month = localDate.getMonth();
			* 返回日期中的月份,返回值是 Month 对象

		int getMonthValue();
			* 返回日期中的月份,返回值是 int 

		int getDayOfMonth();
			* 返回月份中的日

		boolean isLeapYear();
			* 是否是闰年

		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
			* 获取星期几,返回值是 DayOfWeek 枚举对象

		int get(ChronoField chronoField);
			* 根据参数(枚举),返回指定类型的数据
			* 可选值 
				ChronoField.YEAR				//年份
				ChronoField.MONTH_OF_YEAR		//月
				ChronoField.DAT_OF_MONYH		//日

		int lengthOfMonth()
			* 获取当前月的天数
		
		int lengthOfYear()
			* 获取当前年的天数


	# 设置调整相关

		LocalDateTime atTime(int hour, int minute, int second)
			* 传入 时分秒,返回 LocalDateTime 对象
		
		LocalDateTime atTime(LocalTime time);
			* 传入 LocalTime 对象,返回 LocalDateTime 对象

		LocalDate with(ChronoField filed,int num);
			* 修改 filed 表示的参数,num 为修改的值
		
		
		LocalDate plus(int num,ChronoField filed);
			* 根据 filed 表示的参数,添加 num 个单位
		
		LocalDate plusWeeks(int num);
			* 当前时间,添加n周

		LocalDate plusYears(int year);
			* 添加多少年

		LocalDate plusMonths(int month);
			* 添加多少个月
			* '还有很多添加年月日的API,都一样'
		
		LocalDate minusYears(int num);
			* 当前时间减去n年
			* '还有很多减去年月日的API,都一样'

		LocalDate withYear(int year);
			* 返回一个新的 LocalDate 对象
			* 新对象除了 year被修改,其他的都跟原来对象的属性一摸一样
		
		LocalDate withDateOfMonth(int day);
			* 同上,返回的是不同月份的 LocalDate
		
		LocalDateTime atStartOfDay()
		ZonedDateTime atStartOfDay(ZoneId zone)
			* 返回今日开始的LocalDateTime 

	# 格式化先关
		String formar(DateTimeFormatter matter);
			* 根据 DateTimeFormatter 返回被格式化后的时间字符串
