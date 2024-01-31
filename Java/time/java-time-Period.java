------------------------------
Period							|
------------------------------
	# 计算两个日期之间的间隔
		* 在 ISO-8601 日历系统中以日期为基础的时间量，如 "2 年 3 个月零 4 天"。
		* 该类是以年、月、日为单位的时间量模型。


		
	# 构造(使用Preiod静态方法)
		Preiod between(LocalDate locaDate1,LocalDate localDate2);
			* 计算两个日期之间的间隔
				Period period = Period.between(LocalDate.of(2024, 01, 01), LocalDate.of(2025, 04, 03));
		
				// 相距 1 年 3 日 2 天
				System.out.printf("相距 %d 年 %d 日 %d 天\n", period.getYears(), period.getMonths(), period.getDays());
			
			* 计算年龄
				Period age = Period.between(LocalDate.of(1993, 11, 9), LocalDate.now());
				System.out.println(age.getYears());  //30


------------------------------
Period-API						|
------------------------------
	getYears();
		* 获取年份
	getMonths();
		* 获取月份
	getDays();
		* 获取天数

	