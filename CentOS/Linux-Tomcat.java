-------------------------------
Linux-Tomcat					|
-------------------------------
	1,下载,上传,解压
		# 弱智操作,不解释
		wget http://mirrors.cnnic.cn/apache/tomcat/tomcat-8/v8.5.6/bin/apache-tomcat-8.5.6.tar.gz
		

	2,开放8080端口
		# vim /etc/sysconfig/iptables
			---------------------------------------------------
			-A INPUT -m state --state NEW -m tcp -p tcp --dport 20 -j ACCEPT			//给你个20的做参考
			-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT
		# service iptables restart		//让配置立即生效

	4,阿里云,设置端口转发
		* 这SB云盾会监听80
			iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
				> 该命令,会把80的请求转发到8080去处理,重启就没用了
			service iptables save
				> 写入iptables,永久生效

	5,开机启动
		# 编辑/etc/rc.d/rc.local 文件
		# 配置好变量和Tomcat的目录,以及启动脚本的目录
		
			export JAVA_HOME=/usr/local/java/jdk1.8.0_101
			export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
			export PATH=$JAVA_HOME/bin/:$PATH

			export CATALINA_HOME=/usr/local/tomcat/apache-tomcat-8.5.4
			/usr/local/tomcat/apache-tomcat-8.5.4/bin/startup.sh

			* 注意,不需要配置JRE_HOME
			* 而且要注意,CLASSPATH的配置要比/etc/sysconfig/profile 中的配置短
			

