----------------------
UFW
----------------------
	# 防火墙
	# 安装
		apt install ufw 
	
	# 查看状态
		ufw status numbered

		Status: inactive // 未激活(默认)

		Status: active	 // 已激活

			 To                         Action      From
			 --                         ------      ----
		[ 1] 22/tcp                     ALLOW IN    Anywhere                  
		[ 2] 80/tcp                     ALLOW IN    Anywhere                  
		[ 3] 443/tcp                    ALLOW IN    Anywhere                  
		[ 4] 33322/tcp                  ALLOW IN    Anywhere                  
		[ 5] 22/tcp (v6)                ALLOW IN    Anywhere (v6)             
		[ 6] 80/tcp (v6)                ALLOW IN    Anywhere (v6)             
		[ 7] 443/tcp (v6)               ALLOW IN    Anywhere (v6)             
		[ 8] 33322/tcp (v6)             ALLOW IN    Anywhere (v6)   

		* 前面 [] 中的就是编号
	
	# 添加常用规则
		ufw allow 22/tcp
		ufw allow 80/tcp
		ufw allow 443/tcp
		ufw allow 8080/tcp
		ufw allow 3306/tcp
		ufw allow 6379/tcp
		
		* 放行指定范围的端口
			ufw allow 10010:10086/tcp

	
		* 对所有 IP 放行 TCP 端口
			ufw allow [端口]/tcp
		
		* 拒绝 IP 连接
			ufw deny from [ip]

	# 开启防火墙
		ufw enable
	
	# 禁用防火墙
		ufw disable
	
	# 要重载规则
		ufw reload
	
	# 删除规则
		 ufw delete 2

		 * 2 就是编号
		 * 删除后，规则编号会变化，需要重新查询新的编号再删除。
	
	# 重置
		ufw reset

		* 删除所有自定义策略并关闭UFW

	

	# 查看帮助
		ufw help

----------------------
UFW - 坑
----------------------
	# UFW(iptables)规则的匹配基于规则出现的顺序，
		* 一旦匹配某个规则，检查便会停止。
		* 因此，如果某个规则允许访问TCP端口22(如使用 udo ufw allow 22)，
		* 后面另一个规则指示拦截某个IP地址(如使用  ufw deny proto tcp from 202.54.1.1 to any port 22)。

		* 这都是由于规则的顺序造成的
	
	# 为避免这类问题，需要编辑 /etc/ufw/before.rule s文件，在 "# End required lines" 之后 "Block an IP Address" 添加规则。
		
		vim /etc/ufw/before.rules

		查找
		# End required lines

		# Block spammers
		-A ufw-before-input -s A.B.C.D -j DROP

		ufw reload
