-------------------
Docker 安装 MYSQL
-------------------
	# 创建本地配置文件
		mkdir -p ~/docker/redis/data    # 用于存储数据文件
		mkdir -p ~/docker/redis/conf    # 用于存储配置文件

	
	# 创建 Redis 配置文件

		* vim ~/docker/redis/conf/redis.conf

		* 写入配置信息
		* 完整的配置信息：
			https://raw.githubusercontent.com/redis/redis/unstable/redis.conf
	
	# 启动
		docker run -d \
		  --name redis7 \
		  --restart=unless-stopped \
		  -p 6380:6379 \
		  -v ~/docker/redis/data:/data \
		  -v ~/docker/redis/conf/redis.conf:/etc/redis/redis.conf \
		  redis:7 \
		  redis-server /etc/redis/redis.conf
	
	
	# 测试

		docker exec -it redis7 redis-cli
		# 输入认证密码
		auth your_password_here
		# 测试 ping
		ping
		
	
	# 查看日志
		docker logs redis7 -f
	
	# 重启容器
		docker restart redis7
