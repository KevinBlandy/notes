-------------------
Date				|
-------------------
	# 创建
		var date = new Date();
		var date = new Date(milliseconds);
			* 参数是一个数字类型的时间戳

		var date = new Date(dateString);
			* var date = new Date("Wed May 15 2013 10:30:25 GMT+0800 (中国标准时间)");

		var date = new Date(year, month, day, hours, minutes, seconds, milliseconds);
			* 年,月,日,时,分,秒,时间戳

	# 两个日期对象之间可以实现加减法
		* 相减后,得到的是毫秒值
		var now = new Date();
		for(var x = 0 ;x < 1000; x++){
		}
		var then = new Date();
		out("毫秒值:"+(then - now));

	# 实例方法

		toLocaleString();		
			* 根据当前系统环境,返回一个时间的描述

		getYear();				
			* 以两位,返回年份,过期方法,不建议使用

		getFullYear();			
			* 以四位,返回年份

		getMonth();				
			* 获取月份,返回的是0-11,需要+1操作才能获取到准确的值

		getDay();				
			* 返回一周的中第几天0-6,需要+1操作才能获取到准确的值

		getDate();				
			* 返回一个月中第几天

		getHours();				
			* 获取小时(24H制)

		getMinutes();			
			* 获取当前的分钟

		getSeconds();			
			* 获取当前秒

		getMilliseconds()	
			* 获取毫秒(0 ~ 999)。

		getTime();				
			* 返回:1970-1-1日到如今的毫秒数

		toJSON();
			* 以 JSON 数据格式返回日期字符串。

		setDate()	
			* 设置 Date 对象中月的某一天 (1 ~ 31)。

		setFullYear()
			* 设置 Date 对象中的年份（四位数字）。

		setHours()
			* 设置 Date 对象中的小时 (0 ~ 23)。

		setMilliseconds()	
			* 设置 Date 对象中的毫秒 (0 ~ 999)。

		setMinutes()
			* 设置 Date 对象中的分钟 (0 ~ 59)。

		setMonth()
			* 设置 Date 对象中月份 (0 ~ 11)。

		setSeconds()
			* 设置 Date 对象中的秒钟 (0 ~ 59)。

		setTime()	
			* setTime() 方法以毫秒设置 Date 对象。
	
-------------------
Date-获取当前时间	|
-------------------
	function now(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;	
		if(month < 10){
			month = "0" + month;
		}
		var dayOfMonth = date.getDate();
		if(dayOfMonth < 10){
			dayOfMonth = "0" + dayOfMonth;
		}
		var hours = date.getHours();		
		if(hours < 10){
			hours = "0" + hours;
		}
		var minutes = date.getMinutes();	
		if(minutes < 10){
			minutes = "0" + minutes;
		}
		var seconds = date.getSeconds();
		if(seconds < 10){
			seconds = "0" + seconds;
		}
		return year + "年" + month + "月" + dayOfMonth + "日 " + hours + ":" + minutes + ":" + seconds;
	}