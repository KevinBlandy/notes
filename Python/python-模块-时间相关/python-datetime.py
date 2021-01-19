-----------------------
datetime				|
-----------------------
	* 日期与时间
	* 强大之处,可以对时间进行一些计算
	* 可以进行一些时间的 + - 操作
		datetime.datetime.now() + datetime.timedelta(3)
			# 当前时间减去加上3天,如果为负数,则是减去3天
		
		datetime.datetime.now() + datetime.timedelta(hours=-15)
			# 当前时间进去15个小时


----------------------
datetime-模块函数		|
----------------------
	
	datetime.datetime datetime.datetime(y,m,d)
		* 自定义时间创建实例对象
		* demo
			diy = datetime.datetime(2015, 4, 19, 12, 20)	# 2015-04-19 12:20:00

	datetime.datetime datetime.datetime.now()
		* 返回当前时间,精确到毫秒
		* 该返回值,可以和指定的对象进行 +  运算
		* demo
			var = datetime.datetime.now() # 2017-06-08 22:05:55.195936
		
	datetime.date datetime.date.fromtimestamp(timestamp)
		* 对指定时间戳进行格式化,返回 datetime.date  对象
		* Demo
			var = datetime.date.fromtimestamp(time.time()) # 2017-07-01
	
	datetime.datetime datetime.datetime.strptime(str,parten)
		* 尝试根据 parten 反格式化时间 str
		* demo
			diy = datetime.datetime.strptime('2015-6-1 18:19:59', '%Y-%m-%d %H:%M:%S')

	datetime.timedelta datetime.timedelta(num)
		* 返回一个 对象,该对象可以跟 datetime.datetime 实例进行 +  操作
		* 第一个参数,表示要添加/减去几天
		* 关键字参数
			hours
				* 指定要加减的小时
			minutes
				* 指定要加减的分钟
	

----------------------
datetime-datetime		|
----------------------
	int timestamp()
		* 把 datetime 转换为时间戳

	tuple timetuple()
		* 把 datetime 转换为结构化时间
	
	str strftime(str)
		* 把 datetime,转换为str格式的时间字符串
		* demo
			datetime.datetime.now().strftime('%a, %b %d %H:%M') # Fri, Jul 14 09:44
	

