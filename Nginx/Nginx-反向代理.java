------------------------
Nginx-反向代理			|
------------------------
	1,在Nginx的安装目录的:conf目录中
	2,找到:nginx.conf文件

------------------------
Nginx-具体配置			|
------------------------

	server {
		//监听端口
		listen 80;

		//监听域名
		server_name manager.kevinblandy.com;

	//日志文件配置
	#access_log  logs/kevinblandy.com.access.log  main;

	//错误日志文件
	#error_log  logs/kevinblandy.com.error.log;


	//转发客户端请求的时候携带的域名
	proxy_set_header Host $host;

	proxy_set_header X-Forwarded-Host $host;
	proxy_set_header X-Forwarded-Server $host;
	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

	//转发客户端真实IP(通过 request.getHeader("X-Requested-For")获取)
	proxy_set_header X-Requested-For $remote_addr;
	proxy_set_header REMOTE-HOST $remote_addr;

		//映射
		location / {
			//转发地址+端口
			proxy_pass http://127.0.0.1:8081;
			//连接超时
			proxy_connect_timeout 600;
			//读取超时
			proxy_read_timeout 600;
		}
	}

	# 解决JAVA代码中获取客户端真实的访问IP
		String ip1 = request.getHeader("X-Requested-For");

	# 解决Tomcat 日志中,客户端的真实访问IP
		# 修改tomcat日志配置：(<host></host>中间)

		<Valve className="org.apache.catalina.valves.AccessLogValve"  
			directory="logs"  prefix="tomcat_access_log" suffix=".txt"  
			pattern="%{X-Requested-For}i %l %u %t &quot;%r&quot; %s %b" 
			resolveHosts="false"/>

		* 没有Nginx这一层的时候直接用%a就可以获得客户端IP，现在我们得用%{X-Requested-For}i 来获得真实的IP了。

		* 其实只需要修改:%h %l %u %t &quot;%r&quot; %s %b 原始值就OK

		* 替换为:%{X-Requested-For}i %l %u %t &quot;%r&quot; %s %b


------------------------
Nginx-静态文件			|
------------------------
	  server{
			listen 80;
			server_name static.kevinblandy.com;
			location / {
				root /usr/local/static;		
				autoindex on;               # 开启索引    
				charset utf-8,gbk;			# 解决文件名称中文乱码的问题
				autoindex_exact_size on;    # 显示文件大小        
				autoindex_localtime on;     # 显示最后修改时间     
			}
		}

------------------------
Nginx-WebSocket			|
------------------------
	# 反向代理WebSocket,需要添加一些配置

	 proxy_http_version 1.1;        
	 proxy_set_header Upgrade $http_upgrade;
	 proxy_set_header Connection "upgrade";

------------------------
Nginx-域名重定向		|
------------------------
	# 把 springboot.io 和 www.springboot.io 重定向到 www.javaweb.io
		server{
				listen 80;
				server_name www.springboot.io springboot.io;
				rewrite ^/(.*)$ http://www.javaweb.io/$1 permanent;  

				# return 301 https://$host$request_uri; 也可以用在这个
		}

------------------------
Nginx-https				|
------------------------
	server {
		listen 443 ssl;
		server_name springboot.io www.springboot.io;

		ssl_certificate      /usr/local/ssl/springboot/springboot.pem;
		ssl_certificate_key  /usr/local/ssl/springboot/springboot.key;

		access_log  logs/springboot.io.log  main;
		error_log  logs/springboot.io.error.log;

		proxy_set_header Host $host;
		proxy_set_header X-Forwarded-Host $host;
		proxy_set_header X-Forwarded-Server $host;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Requested-For $remote_addr;
		proxy_set_header REMOTE-HOST $remote_addr;

		proxy_http_version 1.1;
		proxy_set_header Upgrade $http_upgrade;
		proxy_set_header Connection "upgrade";

		location / {
			proxy_pass http://127.0.0.1:1024;
			proxy_connect_timeout 600;
			proxy_read_timeout 600;

		}
	}

	# http重定向到https
	server {
		listen       80;
		server_name  springboot.io www.springboot.io;
		return  301 https://springboot.io$request_uri;
	}

------------------------
Nginx-http2开启			|
------------------------
	# 需要依赖模块支持,必须开启https
	# 核心的配置
		listen 443 ssl http2;;
	
		server{
				listen 443 ssl http2;
				server_name *.c-lang.cn;
				
				ssl_certificate      /etc/letsencrypt/live/c-lang.cn/fullchain.pem;
				ssl_certificate_key  /etc/letsencrypt/live/c-lang.cn/privkey.pem;
				
				proxy_set_header Host $host;
				proxy_set_header X-Forwarded-Host $host;
				proxy_set_header X-Forwarded-Server $host;
				proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
				proxy_set_header X-Requested-For $remote_addr;
				proxy_set_header REMOTE-HOST $remote_addr;
				
				# websocket 非必须
				proxy_http_version 1.1;
				proxy_set_header Upgrade $http_upgrade;
				proxy_set_header Connection "upgrade";
				
				location / {
						root html;
						index index.html index.htm;
				}       
			}