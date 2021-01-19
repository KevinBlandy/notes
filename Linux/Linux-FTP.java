————————————————————————————————
1,Linux-FTP的常规操作				|
————————————————————————————————
	* RedHatLinux,其实已经默认安装了一个FTP(vsftp)
	* 启动ftp
		service vsftpd start
			* vsftpd,后面参数d表示在后台运行
			* 出现类似如下提示，则表示ftp启动成功
			  Starting vsftpd for vsftpd
			  You have mail in /var/spool/mail/root
	* 验证FTP是否启动
		ftp localhost
			* 如果出现Connected to localhost.localdomain...等提示，就表示ftp服务器启动成功
			* 此命令执行后会出现用户名和密码输入的提示
	* 退出ftp
		bye
			* 没错，就一个Bye
	* 关闭ftp服务
		service vsftpd stop
			* 不多做解释
	*　ftp配置文件
		/etc/vsftpd/vsftpd.conf
		* anonymous_enable=YES				//运行匿名登陆
		* anonymous_enable=NO
	* 配置允许root用户上传文件
		* 修改配置文件：/etc/vsftpd/user_list
		* 该文件里面指定了哪些用户是被拒绝访问的,编辑它
		* 注释掉root(前面加#号)
		* 修改配置文件:/etc/vsftpd.ftpusers
		* 注释掉root
		* 重启ftp服务器
		* service vsftpd restart
		* OK,此时root用户已经可以登陆以及进行上传(其实也可以使用命令)
————————————————————————————————
２,Linux-FTP文件上传				|
————————————————————————————————
	* 首先要知道Linux服务器的ip地址
		ifconfig
			* 注意,不是windows那ipconfi,人家是if
		　　　　* 这个命令还可以配置ip地址
	* 在Windows中使用命令链接Linux的FTP服务器
		1,ftp ip
		2,如果允许匿名登陆,输入:anonymous
		3,如果运行匿名登陆,不需要密码，直接回车
		4,输入:ls			//查看远端的目录(pub)
			* 它在Linux中的位置在配置文件中有指定
			* /var/ftp/pub
	* 其实很有可能连不上，就算能ping通FTP服务器
		1, 防火墙
			* 关闭Linux防火墙，或者打开端口
			service iptables stop			//关闭防火墙
			
————————————————————————————————
２,Linux-FTP设置开机启动			|
————————————————————————————————
	1,chkconfig vsftpd on
		* 不同的启动级别都是on，都会启动
	
			
	
	
	
		
