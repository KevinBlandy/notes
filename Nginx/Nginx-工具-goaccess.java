--------------------
goaccess
--------------------
	# Nginx的日志解析工具
		http://goaccess.io/
		https://www.goaccess.cc/?mod=features
		https://github.com/allinurl/goaccess
	
	# 开始监控命令
		goaccess -f /usr/local/nginx/logs/access.log -p /etc/goaccess.conf -o /usr/local/nginx/html/goaccess.html --real-time-html --daemonize

		-f 指定访问日志
		-p 指定配置文件
		-o 指定生成的文件（通过后缀确定文件的类型）
		--real-time-html	开启实时刷新
		--daemonize			后台运行
	
	# 处理配置文件中日期格式化异常
		* 编辑配置文件: /etc/goaccess.conf

		time-format %H:%M:%S
		date-format %d/%b/%Y
		log-format %h %^[%d:%t %^] "%r" %s %b "%R" "%u"
	
	# 开启中文显示
		* 编辑文件: /etc/sysconfig/i18n

		LANG="zh_CN.UTF-8"

		* 执行命令，立即生效

		LANG="zh_CN.UTF-8"

		* 重启goaccess和Nginx
	
