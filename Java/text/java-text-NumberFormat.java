-------------------------
NumberFormat			 |
-------------------------
	# 数字的格式化处理, 抽象类

	# 构造/静态函数(工厂)
		NumberFormat getCurrencyInstance() 
		NumberFormat getCurrencyInstance(Locale inLocale)

		Locale[] getAvailableLocales()
		