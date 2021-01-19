------------------------
Nginx-include			|
------------------------
	# 在配置的参数过多后,可以把这些配置都以键值对的形式配置在外面
	# 然后在需要的地方通过include引入进来
	

------------------------
Nginx-demo				|
------------------------
	1,在nginx/conf中新建一个文件:proxy.conf

	2,在该文件中配置代理参数
		proxy_next_upstream http_502 http_504 error timeout invalid_header;
		proxy_set_header Host $host;
		proxy_set_header X-Forwarded-For $remote_addr;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_connect_timeout 90;
		proxy_send_timeout 90;
		proxy_read_timeout 90;
		proxy_buffer_size 4k;
		proxy_buffers 4 32k;
		proxy_busy_bufers_size 64k;
		proxy_temp_file_write_size 64k;

	3,在location中引入
		location / {
			...
			include proxy.conf;
		}
		