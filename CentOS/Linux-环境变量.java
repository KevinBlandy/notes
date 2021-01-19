------------------------
Linux-环境变量			|
------------------------
	# 修改完配置文件后,让其立即生效(不然必须要重启服务器)
		* source [配置文件]
		* . [配置文件]
	
	# 设置值
		export [变量名]=[变量值]		//申明变量
		env								//查询变量
		unset							//删除变量

------------------------
Linux-环境变量配置文件	|
------------------------
	# 对所有用户都生效的配置文件
		/etc/profile
			* 最主要的
				USER
				LOGNAME
				MAIL
				PATH
				HOSTNAME
				HISTSIZE
				umask
		/etc/profile.d/*.sh															*/
			* 一组文件
		/etc/bashrc

	# 对自己生效的配置文件
		~/.bash_profile
		~/.bashrc
	

------------------------
Linux-其他配置文件		|
------------------------
	1,注销时生效的环境变量配置文件
		~/.bash_logout
	2,历史命令
		~/bash_history
	3,本地终端欢迎信息
		/etc/issue
	4,远程终端欢迎信息
		/etc/issue.net
	4,登录最后欢迎信息(对于本地和远程都有效)
		/etc/motd
		