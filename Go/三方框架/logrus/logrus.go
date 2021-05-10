----------------------
logrus
----------------------
	# Doc
		https://github.com/sirupsen/logrus
	
	# 日志等级
		Panic	记录日志，然后panic
		Fatal	致命错误，出现错误时程序无法正常运转。输出日志后，程序退出
		Error	错误日志，需要查看原因
		Warn	警告信息，提醒程序员注意
		Info	关键操作，核心流程的日志
		Debug	一般程序中输出的调试信息
		Trace	很细粒度的信息，一般用不到
	
		* logger实例会有一个日志级别，高于这个级别的日志不会输出
		* 默认的级别为 Info 
	

