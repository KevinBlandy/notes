----------------------
calendar				|
----------------------
	* 日历

----------------------
calendar-模块函数		|
----------------------
	str month(year,month)
		* 会打印出指定年的指定月份的日历
		* 命名参数
			w:每日宽度间隔字符,每行长度为21* W+18+2*C
			l:每星期行数
		* demo
			var = calendar.month(2015,1)

			January 2015
			Mo Tu We Th Fr Sa Su
					  1  2  3  4
			 5  6  7  8  9 10 11
			12 13 14 15 16 17 18
			19 20 21 22 23 24 25
			26 27 28 29 30 31

	None prmonth(year,month)
		* 相当于执行了: print calendar.calendar(year,w,l,c)
		* 仅仅是打印
		* 命名参数
			w:每日宽度间隔字符,每行长度为21* W+18+2*C
			l:每星期行数

	str calendar(2017)
		* 指定年份,以字符串形式返回当年的日历...(参考上面) 
		* 命名参数
			c:间隔距离为
			w:每日宽度间隔字符,每行长度为21* W+18+2*C
			l:每星期行数
	
	None prcal(year)
		* 相当于执行了:print calendar.calendar(year,w,l,c)
		* 仅仅是打印
		* 命名参数
			c:间隔距离为。
			w:每日宽度间隔字符,每行长度为21* W+18+2*C
			l:每星期行数

	list monthcalendar(year,month)
		* 返回嵌套列表,每个子列表装载代表一个星期的整数
		* 就是把每个礼拜对应的day,都装到一个list
		* Year年month月外的日期都设为0;范围内的日子都由该月第几日表示,从1开始
		* demo
			var = calendar.monthcalendar(2017,9)
			[
				[0, 0, 0, 0, 1, 2, 3],
				[4, 5, 6, 7, 8, 9, 10], 
				[11, 12, 13, 14, 15, 16, 17], 
				[18, 19, 20, 21, 22, 23, 24],
				[25, 26, 27, 28, 29, 30, 0]
			]
	
	tuple monthrange(year,month)
		* 返回 tuple,包含两个整数
		* 第一个是该月的星期几的日期码
		* 第二个是该月的日期码
		* 日从0(星期一)到6(星期日)
		* 月从1到12

	bool isleap(year)
		* 判断是否是闰年
	
	int leapdays(s1,s2)
		* 返回在s1,s2两年之间的闰年总数
	
	int timegm(tuple)
		* tuple,结构化时间对象
		* 该值为 int,会自动删除后面的小数点
		* 返回该时间对应的时间戳(秒)
	
	int weekday(year,month,day)
		* 返回给定日期的时间码
		* 0(星期一) - 6(星期日)
		* 月份为 1(一月) - 12(12月)

	int firstweekday()
		* 返回当前每周起始日期的设置
		* 默认情况下,首次载入caendar模块时返回0,即星期一
	
	None setfirstweekday(weekday)
		* 设置每周的起始日期码
		* 0(星期一)-6(星期日)
