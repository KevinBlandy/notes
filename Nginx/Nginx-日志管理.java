--------------------
Nginx-日志管理		|
--------------------
	# Nginx可以针对不同的Server做不同的log
	# 全局的log配置


--------------------
Nginx-Servlet访问日志|
--------------------
	# 配置在Server中 
		#access_log  logs/host.access.log  main;
		* main代表一个格式,是已经定义好的

	server{
		...
		/*
			定义该服务的日志记录文件,与格式,与缓冲区大小
			主要,这里使用 main 格式,那么conf上面的main,定义就要解除注释
		*/
		access_log logs/ywp.access.log main buffer=32k;
	}

--------------------
Nginx-log_format指令|
--------------------
	
	# 具体日志格式如下
		log_format mylog "....";
	
	$http_x_forwarded_for 
	remote_user 
		用于记录IP地址
	$remote_user 
		用来记录远程客户端用户名称
	$time_local 
		用来记录,访问时间以及时区
	$request
		用来记录请求的URL与HTTP协议
	$status
		用来记录请求的状态(HTTP响应码),
	$body_bytes_sent
		用于记录响应客户端的主体文件大小
	$http_referer用
		于记录是从哪个页来的
	$http_user_agent
		用于记录客户端浏览器的相关信息

	

--------------------
Nginx-日志切割		|
--------------------
	# 访问量过大后,日志文件会过大,需要切割
	# shell脚本,和Linux的定时任务
		... ... 老子不会
	