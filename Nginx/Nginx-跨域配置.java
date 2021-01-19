-----------------------------
Nginx cors跨域配置
-----------------------------

server {
    listen 80;
    server_name localhost 127.0.0.1;
	location / {
		# 允许跨域请求的“域”
		add_header 'Access-Control-Allow-Origin' $http_origin;
		# 允许客户端提交Cookie
		add_header 'Access-Control-Allow-Credentials' 'true';
		# 允许客户端的请求方法
		add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS, DELETE, PUT';
		# 允许客户端提交的的请求头
		add_header 'Access-Control-Allow-Headers' 'Origin, x-requested-with, Content-Type, Accept, Authorization';
		# 允许客户端访问的响应头
		add_header 'Access-Control-Expose-Headers' 'Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma';
		# 处理预检请求
		if ($request_method = 'OPTIONS') {
			# 预检请求缓存时间
			add_header 'Access-Control-Max-Age' 1728000;
			add_header 'Content-Type' 'text/plain; charset=utf-8';
			add_header 'Content-Length' 0;
			return 204;
		}
		
		# SpringBoot 应用访问路径
		proxy_pass http://127.0.0.1:8080;
		
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		
		proxy_connect_timeout 600;
		proxy_read_timeout 600;
	}
}
