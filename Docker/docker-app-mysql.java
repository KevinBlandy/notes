-------------------
Docker 安装 MYSQL
-------------------
	# 创建本地配置文件
		mkdir -p ~/docker/mysql/data    # 用于存储数据文件
		mkdir -p ~/docker/mysql/conf    # 用于存储配置文件
		mkdir -p ~/docker/mysql/logs    # 用于存储日志文件
	
	
	# 创建 MySQL 配置文件

		vim ~/docker/mysql/conf/my.cnf

		[mysqld]
		character-set-server=utf8mb4
		collation-server=utf8mb4_unicode_ci
		sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION,TIME_TRUNCATE_FRACTIONAL


		# 日志配置
		log_error=/var/log/mysql/error.log
		general_log_file=/var/log/mysql/mysql.log
		general_log=1
		slow_query_log=1
		slow_query_log_file=/var/log/mysql/slow.log
		long_query_time=2


		[client]
		default-character-set=utf8mb4


		* 很关键，如果没有这个配置的话 MYSQL 会启动失败
		* 日志配置是可选的
	
	# 启动

		docker run -d \
		  --name mysql8 \
		  --restart=unless-stopped \
		  -p 3307:3306 \
		  -v ~/docker/mysql/data:/var/lib/mysql \
		  -v ~/docker/mysql/conf:/etc/mysql/conf.d \
		  -v ~/docker/mysql/logs:/var/log/mysql \
		  -e MYSQL_ROOT_PASSWORD=root \
		mysql:8


		* MYSQL_ROOT_PASSWORD 指定的是本机登录密码
	
	
	# 设置 root 的远程登录用户名和密码
		
		docker exec -it mysql8 mysql -uroot -p

		* 输入本机登录密码
		
		// 创建 root 账户
		ALTER USER 'root'@'%' IDENTIFIED BY 'root';
		// 授所有权限
		GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
		// 刷出权限
		FLUSH PRIVILEGES;
	
	# 查看日志
		docker logs mysql8 -f
	
	# 重启容器
		docker restart mysql8
