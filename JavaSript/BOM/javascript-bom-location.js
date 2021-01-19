------------------
location			|
------------------
	# 浏览器的连接
	# window.location === document.location

------------------
location		属性|
------------------
	href="http//:www.baidu.com";	
		* 让浏览器打开指定的地址
	href;							
		* 获取浏览器请求地址
	hostname 
		* 返回WEB主机的域名
	pathname 
		* 返回当前页面的路径和文件名
	port 
		* 返回 web 主机的端口 （80 或 443）
	protocol 
		* 返回所使用的 web 协议（http:// 或 https://）
	search
		* 返回URL后的查询部门
		* ? 后面的部分

------------------
location		方法|
------------------
	assign();
		* 加载新的文档
		* 参数指定URL
	
------------------
location		事件|
------------------
	

------------------
location		demo|
------------------
	//获取URI中的请求参数
	function urlArgs(){
		var args = {};
		//获取参数字符串,删除?号
		var query = location.search.substring(1);
		//根据 & 符合分割字符串
		var pairs = query.split("&");
		for(var x = 0;x < pairs.length;x++){
			//查找name=value
			var pos = pairs[x].indexOf("=");
			if(pos == -1){
				continue;
			}
			var name = pairs[x].substring(0,pos);
			var value = pairs[x].substring(pos + 1);
			//URI解码
			value = decodeURIComponent(value);
			if(args.hasOwnProperty(name)){
				//属性已经存在,以','号分隔
				value = args[name] + "," + value;
			}
			args[name] = value;
		}
		return args;
	}


	
	//获取项目的跟路径
	getRootPath : function (){
		var pathName = window.document.location.pathname;
		var projectName = pathName.substring(0,pathName.substr(1).indexOf('/') + 1);
		return projectName;
	},
	