---------------------------
国际化						|
---------------------------
	Locale locale = Locale.CHINA;
	//多个语言配置文件
	//res_zh_CN.properties
	//res_en_US.properties
	ResourceBundle rb = ResourceBundle.getBundle("res", locale);
	//获取配置里面的属性
	System.out.println(rb.getString("name"));

---------------------------
获取所有支持的国家地区		|
---------------------------
	//获取所支持的国家和语言
	Locale[] localeList = Locale.getAvailableLocales();
	for (int i = 0; i < localeList.length; i++) {
		// 打印出所支持的国家和语言
		System.out.println(localeList[i].getDisplayCountry() + "=" + localeList[i].getCountry() + " " + localeList[i].getDisplayLanguage() + "=" + localeList[i].getLanguage());
	}
	/*
		阿拉伯联合酋长国=AE 阿拉伯文=ar
		约旦=JO 阿拉伯文=ar
	*/