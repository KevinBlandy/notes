------------------
Cookie				|
------------------
	# JS 对于Cookie的操作
	# 使用 document 对象



------------------
Cookie		设置	|
------------------
	# 简单设置
		document.cookie = "name=value";
		
		document.cookie = "SESSION=F8575532";

	
	# 带属性的设置
		document.cookie = "name=value;key=value;key-value;key-value;"

		document.cookie = "SESSION=F8575532;max-age=-1;";

------------------
Cookie		获取	|
------------------
	document.cookie
		* 该属性会返回所有的Cookie,以字符串的形式.分割符为:';'


------------------
Cookie		删除	|
------------------
	# 其实就是重新设置
		document.cookie = "SESSION=F8575532;max-age=0;";
		* 设置指定名称Cookie,相同的域,路径,任意非空值,并且 max-age = 0


------------------
Cookie	-	Demo	|
------------------
	//获取所有的Cookie
	function getCookies (){
		var cookie = {};
		var all = document.cookie;
		if(all === ''){
			return cookie;				
		}
		var list = all.split(';');
		for(var x = 0; x < list.length ; x++){
			var cookieStr = list[x];
			var point = cookieStr.indexOf('=');
			var name = cookieStr.substring(0,point);
			var value = decodeURIComponent(cookieStr.substring(point + 1));
			cookie[name] = value;
		}
		return cookie;
	}