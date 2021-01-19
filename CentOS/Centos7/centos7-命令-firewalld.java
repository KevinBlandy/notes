------------------------
firewall 防火墙			|
------------------------
	# 防火墙的启动/关闭
		systemctl start firewalld.service
		systemctl stop firewalld.service
		systemctl restart firewalld.service
		systemctl reload firewalld.service
	
	# 自启动
		systemctl enable firewalld.service
		systemctl disable firewalld.service
		systemctl is-enabled firewalld.service

------------------------
firewall 端口			|
------------------------
	firewall-cmd --list-all
		# 查看所有的端口

	firewall-cmd --list-ports
		# 查看所有已经开放的端口
	
	firewall-cmd --query-port=[端口]/[协议] --permanent 
		# 查看指定端口是否开放
		# 参数
			--permanent //表示是否是永久生效,无此参数,则重启失效
		# firewall-cmd --query-port=80/tcp

	firewall-cmd --add-port=[端口]/[协议] --permanent 
		# 开放指定的端口/协议
	
	firewall-cmd --remove-port=[端口]/[协议] --permanent
		# 关闭自定的端口/协议

	firewall-cmd --reload  
		# 重新载入防火墙
	
