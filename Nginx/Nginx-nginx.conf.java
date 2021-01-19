------------------------
Nginx-nginx.conf配置文件|
------------------------
	* 在nginx的/conf目录下:nginx.conf
		
#user  nobody;

//工作子进程数,设置为CPU的核心数最妥
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;

events {

	//IO模块,仅用于linux2.6以上内核,可以大大提高nginx的性能
    use   epoll; 

	//单个进程最大的连接数
    worker_connections  1024;
}

/*
	配置HTTP服务器
*/
http {
		include       mime.types;
		default_type  application/octet-stream;
		/** 
			main格式的日志输出格式说明 
				$remote_addr			--> 远程IP
				$body_bytes_sen			--> 响应字节大小
				$http_referer			--> 是从哪个页面连接到这里
				$http_user_agent		--> 浏览器版本,操作系统
				$http_x_forwarded_for	--> ... ...自己去查
			# 如果需要在server里面使用 main 格式,那么需要解除注释
		**/
		#log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
		#                  '$status $body_bytes_sent "$http_referer" '
		#                  '"$http_user_agent" "$http_x_forwarded_for"';

		#access_log  logs/access.log  main;

		sendfile        on;
		#tcp_nopush     on;

		#keepalive_timeout  0;
			
		keepalive_timeout  65;
			
		//是否启用压缩,在并发大的时候才会提现出来效果
		#gzip  on;
		
		//最大HTTP请求体
		client_max_body_size     500m;	

		//最大HTTP请求头缓存大小
        client_body_buffer_size  218k;	
		
			
		//虚拟主机的配置
		server {
			//监听的端口	
			listen       80;

			//监听的域名	
			server_name  localhost;

			#charset koi8-r;

			//日志文件,以及输出格式
			#access_log  logs/host.access.log  main;


			location / {
				root   html;
				index  index.html index.htm;
			}
			
			/* 404页面 */
			#error_page  404              /404.html;

			# redirect server error pages to the static page /50x.html
			#
			/** 50X页面 **/
			error_page   500 502 503 504  /50x.html;

			location = /50x.html {
				root   html;
			}

			# proxy the PHP scripts to Apache listening on 127.0.0.1:80
			#
			#location ~ \.php$ {
			#    proxy_pass   http://127.0.0.1;
			#}

			# pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
			#
			#location ~ \.php$ {
			#    root           html;
			#    fastcgi_pass   127.0.0.1:9000;
			#    fastcgi_index  index.php;
			#    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
			#    include        fastcgi_params;
			#}

			# deny access to .htaccess files, if Apache's document root
			# concurs with nginx's one
			#
			#location ~ /\.ht {
			#    deny  all;
			#}
		}


		# another virtual host using mix of IP-, name-, and port-based configuration
		#
		#server {
		#    listen       8000;
		#    listen       somename:8080;
		#    server_name  somename  alias  another.alias;

		#    location / {
		#        root   html;
		#        index  index.html index.htm;
		#    }
		#}

		/*		HTTPS虚拟主机		*/
		# HTTPS server
		#
		#server {
		#    listen       443 ssl;
		#    server_name  localhost;

		#    ssl_certificate      cert.pem;
		#    ssl_certificate_key  cert.key;

		#    ssl_session_cache    shared:SSL:1m;
		#    ssl_session_timeout  5m;

		#    ssl_ciphers  HIGH:!aNULL:!MD5;
		#    ssl_prefer_server_ciphers  on;

		#    location / {
		#        root   html;
		#        index  index.html index.htm;
		#    }
		#}
}

------------------------
Nginx-配置解构图解		|
------------------------
			...		//全局配置
			events{
				
			}
			http{
				server{
					...
				}
				server{
					...
				}
					...
			}
