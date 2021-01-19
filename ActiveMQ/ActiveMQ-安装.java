-------------------------------
ActiveMQ-Windows安装			|
--------------------------------
	


-------------------------------
ActiveMQ-Linux安装				|
--------------------------------
	1,下载
		* 自己去官网找地址
	
	2,解压
		* 不教
	
	3,启动activeMQ
		/usr/local/activemq/apache-activemq-5.14.1/bin/linux-x86-64/activemq start
		* 找到脚本,执行
	
	4,防火墙开放
		* vim /etc/sysconfig/iptabls
			-A INPUT -m state --state NEW -m tcp -p tcp --dport 8161 -j  ACCEPT   
			-A INPUT -m state --state NEW -m tcp -p tcp --dport 61616 -j  ACCEPT  
		* service iptables restart




