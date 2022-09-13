--------------------------------------
SpringBoot Initializr
--------------------------------------
	# 地址
		https://start.spring.io/
		https://github.com/spring-io/start.spring.io
	
	# 自己安装
		1. cloen代码：git clone https://github.com/spring-io/start.spring.io
		2. 打包编译：cd start.spring.io ，执行./mvnw clean package -Dmaven.test.skip=true
		3. target 文件下两个jar 用*-exec.jar 可以直接java -jar -Dserver.port=8080 target\start-site-exec.jar 运行

		* 需要安装nodejs和node-gyp
			* 安装nodejs
				yum -y install nodejs

				npm install -g n
			
			* 升级到指定的最新版本(node版本过低可能会构建失败)
				n 16.13.0 stable
				n 14.18.1 stable

			
			* 安装这玩意儿
				npm install -g node-gyp
			
			* 你妈的可能还要python3的环境
	

	# Nginx 反向代理配置

		#PROXY-START/
		#location  ~* \.(gif|png|jpg|css|js|woff|woff2)$
		#{
		#    proxy_pass https://start.spring.io;
		#    proxy_set_header Host $host;
		#    proxy_set_header X-Real-IP $remote_addr;
		#    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		#    proxy_set_header REMOTE-HOST $remote_addr;
		#    expires 12h;
		#}
		location /
		{
			proxy_pass https://start.spring.io/;
			# proxy_pass http://localhost:8080/;
			#proxy_set_header Host $host;
			#proxy_set_header X-Real-IP $remote_addr;
			#proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			#proxy_set_header REMOTE-HOST $remote_addr;
			
			#add_header X-Cache $upstream_cache_status;
			
			#Set Nginx Cache
			
				#add_header Cache-Control no-cache;
		}

		#PROXY-END/