------------------------
Linux-vsftpd			|
------------------------
	1,检查本地是否已经安装VSFTPD
		rmp -qa | grep -i vsftpd
			# 卸载:yum -y remove [查询到的包名]
	
	3,在线安装
		yum -y install vsftpd
	
	3,配置文件
		# 修改配置文件:/etc/vsftpd/vsftpd.cnf
		anonymous_enable=NO
			> 禁止匿名用户访问
		

		listen=YES
			> 必须为YES,监听客户端请求
		
		annon_root=/home/ftp
			>
		
		no_anon_password=YES

		write_enable=YES
		anon_upload_enable=YES
		anon_mkdir_write_enable=YES


vsftpd.cnf
	anonymous_enable=NO
	local_enable=YES
	write_enable=YES
	local_umask=022
	dirmessage_enable=YES
	xferlog_enable=YES
	connect_from_port_20=YES
	xferlog_std_format=YES
	chroot_local_user=YES
	chroot_list_enable=YES
	chroot_list_file=/etc/vsftpd/chroot_list
	listen=YES
		* 在Centos7中要修改为NO
	pam_service_name=vsftpd
	userlist_enable=YES
	tcp_wrappers=YES


chroot_list			//写在这里的人,可以访问自己家目录外的目录,这个文件是需要自己创建的
	root
	#ftpuser

ftpusers			//要登录的人,不能写在这里
	# Users that are not allowed to login via ftp
	#root
	bin
	daemon
	adm
	lp
	sync
	shutdown
	halt
	mail
	news
	uucp
	operator
	games
	nobody

user_list			//要登录的人,不能写在这里
	#root
	bin
	daemon
	adm
	lp
	sync
	shutdown
	halt
	mail
	news
	uucp
	operator
	games
	nobody


