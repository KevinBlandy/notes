------------------------
mysql8
------------------------
	# 修改MYSQL的认证模式
		mysql>ALTER USER 'username'@'%' IDENTIFIED WITH mysql_native_password BY 'password';
		mysql>flush privileges;