


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
# mysqld_safe --user=mysql --skip-grant-tables --skip-networking &				//设置运行远程登录
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