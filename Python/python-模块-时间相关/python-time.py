----------------------
time					|
----------------------
	* 时间
	* 关于时间的表达式方式
		1,Unix时间戳(1970-01-01)至今的毫秒数
		2,结构化时间(时间元组)
			time.localtime()
			time.gmtime()
		3,字符串(格式化时间)
	
	* 关于时间的加减
			
----------------------
time-内置属性			|
----------------------
	time.timezone
		* 当地时区(未启动夏令时)距离格林威治的偏移秒数(>0,美洲;<=0大部分欧洲,亚洲,非洲).

	time.tzname
		* 包含一对根据情况的不同而不同的字符串,分别是带夏令时的本地时区名称,和不带的.

----------------------
time-模块函数			|
----------------------
	float time();
		* 获取时间戳
		* 返回1970-01-01至今的秒数
	
	float mktime(tuple)
		* 传递一个结构化时间
		* 返回该时间的时间戳(会去除小数)
		*  demo
			var = time.mktime(time.localtime())
	
	str ctime(float)
		* 根据指定的时间戳(秒),转换为字符串形式的时间
		* s,默认为当前时间的时间戳
		* demo
			var = time.ctime(time.time()) # Thu Jun  8 21:56:20 2017

	str	asctime(tuple)
		* 把一个结构化时间对象,转换为字符串形式的时间
		* 如果不指定 tuple,则使用当前时间(time.localtime())
		* demo
			time.asctime() # Fri Jun  9 12:03:58 2017
			
	None sleep(second)
		* 当前线程停止s秒
	
	float clock()
		* 计算CPU执行的时间(不会包含 time.sleep() 的时间)
		* 返回的是一个科学计数法的 float 数据
		* 在不同的系统上含义不同

	str strftime(format,tuple)
		* 格式化时间
		* format,用于指定时间格式
		* tuple,指定结构化时间,如果不指定,则默认使用当前时间
		* demo
			var = time.strftime("%Y-%m-%d %H:%M:%S") # 17-06-08 21:36:03
	
		

	time.struct_time strptime(times,format)
		* 根据指定格式,把字符串时间转换为结构化时间
		* times,字符串形式的时间
		* format,把该字符串,以何种形式转换为结构化时间
		* demo
			var = time.strptime("2017-06-08 21:36:03", "%Y-%m-%d %H:%M:%S") # time.struct_time(tm_year=2017, tm_mon=6, tm_mday=8, tm_hour=21, tm_min=36, tm_sec=3, tm_wday=3, tm_yday=159, tm_isdst=-1)

	time.struct_time gmtime()
		* 返回的数据格式(结构化时间),其实是个元组
		* 如果传递了 float 则根据此时间戳,返回结构化时间
		* 返回当前UTC(格林威治)时间
		* 我们是东八区,所以小时(tm_hour)会少8个小时
		* time.struct_time(
			tm_year=2017,	# 年
			tm_mon=6,		# 月 
			tm_mday=8,		# 日
			tm_hour=13,		# 时
			tm_min=13,		# 分
			tm_sec=2,		# 秒
			tm_wday=3,		# 一周的第几天(星期四(0-6),0是周一)
			tm_yday=159,	# 1 到 366(一年中的第几天)
			tm_isdst=0)		# -1, 0, 1, -1是决定是否为夏令时的旗帜(时区)
	

	time.struct_time localtime(float)	
		* 返回当前本地时间
		* 如果传递了 float 则根据此时间戳,返回其结构化时间
		* 返回的数据格式(结构化时间),其实是个元组
		* time.struct_time(
			tm_year=2017,	# 年
			tm_mon=6,		# 月 
			tm_mday=8,		# 日
			tm_hour=13,		# 时
			tm_min=13,		# 分
			tm_sec=2,		# 秒
			tm_wday=3,		# 一周的第几天(星期四(0-6))
			tm_yday=159,	# 1 到 366(儒略历)
			tm_isdst=0)		# -1, 0, 1, -1是决定是否为夏令时的旗帜
		* demo
			import time
			x = time.localtime()
			print(x.tm_year)


----------------------
time-格式化表			|
----------------------
	%a 星期几的简写
	%A 星期几的全称
	%b 月分的简写
	%B 月份的全称
	%c 标准的日期的时间串
	%C 年份的后两位数字
	%d 十进制表示的每月的第几天
	%D 月/天/年
	%e 在两字符域中，十进制表示的每月的第几天
	%F 年-月-日
	%g 年份的后两位数字，使用基于周的年
	%G 年分，使用基于周的年
	%h 简写的月份名
	%H 24小时制的小时
	%I 12小时制的小时
	%j 十进制表示的每年的第几天
	%m 十进制表示的月份
	%M 十时制表示的分钟数
	%n 新行符
	%p 本地的AM或PM的等价显示
	%r 12小时的时间
	%R 显示小时和分钟：hh:mm
	%S 十进制的秒数
	%t 水平制表符
	%T 显示时分秒：hh:mm:ss
	%u 每周的第几天，星期一为第一天 （值从0到6，星期一为0）
	%U 第年的第几周，把星期日做为第一天（值从0到53）
	%V 每年的第几周，使用基于周的年
	%w 十进制表示的星期几（值从0到6，星期天为0）
	%W 每年的第几周，把星期一做为第一天（值从0到53）
	%x 标准的日期串
	%X 标准的时间串
	%y 不带世纪的十进制年份（值从0到99）
	%Y 带世纪部分的十制年份
	%z %Z 时区名称，如果不能得到时区名称则返回空字符。
	%% 百分号


------------------------
time-常用函数			|
------------------------
	# 获取当前时间
		import time
		now = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
		print(now)
		# 2018-04-03 12:27:44
	
	# 把字符串时间,按照指定格式转换为时间对象
		import time
		now = time.strptime('2018-04-03 12:27:44', '%Y-%m-%d %H:%M:%S')
		print(now)
