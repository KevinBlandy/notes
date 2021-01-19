--------------------
Nginx-地址重写		|
--------------------
	# Rewriter主要的功能就是实现URL重写,Nginx的Rewriter规则才用(Perl)兼容正则表达式的语法进行规则的匹配.如果需要Nginx的Rewriter功能,在编译Nginx之前,需要安装PCRE库
	# URL,统一资源定位符
	# URI,通用资源标识符
	# rewriter 可以出现的地方
		* location 
		* server
	# 常用命令
		if(条件){}
		set $变量
		return 500
		beak 跳出 rewrite
		rewrite 重写

--------------------
Nginx-Rewriter语法	|
--------------------
	# 变量名
		* 变量名可以用 "=" 或者 "!=" 运算符
	~
		表示区分大小写字母匹配
	!~
		跟楼上相反
	~*
		表示不区分大小写字母匹配
	!~*
		跟楼上相反
	-f	!-f
		文集是否存在
	-d	!-d
		目录是否存在
	-e	!-e
		判断文件或者目录,是否存在
	-x	!-x
		判断文件是否可执行
	
	# 支持 $1 - $9 位置化参数

		
--------------------
Nginx-Return指令	|
--------------------
	# 示例:如果以".sh",".bash"结尾,则返回状态码403

	location ~.*\.(sh|bash)?${
		return 403;
	}

--------------------
Nginx-set,rewrite指令|
--------------------
	set $var '1';
	rewrite指令的最后一个参数为flag标记,支持的flag标记主要有以下几种

	last	:相当于Apache的[L]标记,表示完成rewrite
	break	:本条规则匹配完成后,终止匹配,不再匹配后面的规则
	redirect:返回302重定向,浏览器地址会显示跳转后的URL地址
	permanent:返回永久301重定向,浏览器地址会显示跳转后的URL地址

	last和break用来实现URI重写,浏览器地址的URL不变
	redirect和permanent用来实现URL跳转,浏览器的地址会显示跳转后的URL地址

	# 一般在 location 中,或直接在server标签中编写 rewrite规则,推荐使用last标记
	  在非location中,则使用break标记
	
	# URL rewriter 和反向代理同时进行

	# nginx rewrite指令执行顺序：
		1.执行server块的rewrite指令(这里的块指的是server关键字后{}包围的区域，其它xx块类似)
		2.执行location匹配
		3.执行选定的location中的rewrite指令
			如果其中某步URI被重写，则重新循环执行1-3，直到找到真实存在的文件

			如果循环超过10次，则返回500 Internal Server Error错误

		break指令
		语法：break;
		默认值：无
		作用域：server,location,if

		停止执行当前虚拟主机的后续rewrite指令集
--------------------
Nginx-if指令		|
--------------------
	# 这个是没 else 的,只有 if

	if ($http_user_agent ~ MSIE)
	{
		rewrite ^(.*)$/msie/$1 break;
	}
	if (!-f $request_filename)
	{
		rewrite ^/img/(.*) /site/$host/images/$1 last;
	}