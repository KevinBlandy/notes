---------------------
Nginx作为网关
---------------------
	server {
		listen 80;
		server_name example.com;
		location / {
			proxy_set_header Host $http_host;
			proxy_pass http://localhost:9000;
		}
	}

	server {
		listen 80;
		server_name bucket1.example.com;
		location / {
			proxy_set_header Host $http_host;
			proxy_pass http://localhost:9000/bucket1/;
		}
	}

	server {
		listen 80;
		server_name bucket2.com;
		location / {
			proxy_set_header Host $http_host;
			proxy_pass http://localhost:9000/bucket2/;
		}
	}

	# 使用一个Server代理管理控制台
	# 使用N个Server(二级域名或者其他的域名)来代理bucket
		* 主要要在后面添加 / 不然，可能导致无限重定向

	# 在http上下文添加配置, 客户端最大请求体
		client_max_body_size 1000m;
	
