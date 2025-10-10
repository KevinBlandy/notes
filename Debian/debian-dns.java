----------------------
DNS 的维护
----------------------
	
	# DNS 服务器配置
		
		* 编辑配置文件
			vim /etc/systemd/resolved.conf 

			[Resolve]
			# DNS 服务器的配置，多个的话空格分割
			DNS=

		* 重启
			systemctl restart systemd-resolved
	
	# HOST 映射
		
		* 直接编辑文件
	
			/etc/hosts
	