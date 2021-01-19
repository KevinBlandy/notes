————————————————————————————————
1,Linux入门-搭建Server			|
————————————————————————————————
	* Linux,始终只是用来当做服务器使．

————————————————————————————————
2,Linux入门-JDK					|
————————————————————————————————
————————————————————————————————
3,Linux入门-TOMCAT				|
————————————————————————————————
————————————————————————————————
4,Linux入门-MYSQL				|
————————————————————————————————
	1,安装remi软件源
	rpm -Uvh http://rpms.famillecollet.com/enterprise/remi-release-6.rpm  
	2,查看mysql版本号
		yum --enablerepo=remi,remi-test list mysql mysql-server  
	3,安装
		yum --enablerepo=remi,remi-test install mysql mysql-server  


	/etc/init.d/mysqld start			//启动服务
	service mysqld start				//启动服务(另一种方法)
	chkconfig --levels 235 mysqld on	//开机启动



	# /etc/init.d/mysql stop
	# mysqld_safe --user=mysql --skip-grant-tables --skip-networking &				//设置允许远程登录
	# mysql -u root mysql
		mysql> UPDATE user SET Password=PASSWORD('newpassword') where USER='root';	//设置root用户名密码
		mysql> FLUSH PRIVILEGES;
		mysql> quit

	# /etc/init.d/mysql restart														//重启
	# mysql -uroot -p
	Enter password: <输入新设的密码newpassword>


	修改字符编码
			[client]
			default-character-set = utf8
			[mysqld]
			character-set-server = utf8
————————————————————————————————
5,Linux入门-FTP					|
————————————————————————————————
	* 文件的传递
		Windows --> Linux
		* 共享文件夹
			* 需要在Linux安装一个局域网的共享软件-xamba,但是很少用,你的机器会跟服务器在局域网？
		* FTP
			1,在Windows建立Server,Linux去下载
				* Window一般都是在内网下操作,Linux是找不到你的
			2,在Linux建立Server,Windows去上传(常用)
				* 推荐方式，其实也是唯一的方式
		


————————————————————————————————
5,Linux入门-SVN					|
————————————————————————————————
	llall svnserve							//结束SVN进程
	svnserve -d -r /home/KevinBlandy/SVN/	//开启SVN服务
