--------------------------------
Nginx-负载均衡					|
--------------------------------
	# 关键字:upstream
	# 定义在http模块中
	# upstream 可以被多个标签调用

--------------------------------
Nginx-最简单的负载均衡			|
--------------------------------
		http {
				include				mime.types;
				default_type		application/octet-stream;
				sendfile			on;
				keepalive_timeout	65;

				upstream web_pools {
						server 127.0.0.1:8081 weight=2;
						server 127.0.0.1:8082 weight=3;
				}

				server {
						listen       80;
						server_name  192.168.250.165;
						location / {
								proxy_pass http://web_pools;
						}
				}
		}

--------------------------------
Nginx-upstream 详解				|
--------------------------------
	# Nginx负责均衡功能依赖于:ngx_http_upstream_module  模块
	# 支持的代理方式有:proxy_pass,fastcgi_pass,memcached_pass
	# upstream server 后常用的参数
		weight
			# weight=2
			# 权重,默认值是1,权重越大,接受的请求越多

		max_fails
			# max_fails=2
			# 最大尝试失败的次数,默认为1,如果设置为0,则禁止失败尝试
			# 就是根据这个来判断服务器是不是挂了
			# 这个连接失败啊,就是后端服务器给Ngixn响应了一些啥500开头啊,400开头的HTTP码

		fail_timeout
			# fail_timeout=3s
			# 超时时间,默认是10s,通过超时时间来确定是否连接失败

		dowm
			# 该服务器永远不启用
			# 这个一般配合:ip_hash 来使用

		backup
			# '高可用',功能
			# 被该关键字标识的server,表示.该服务器是一个备份服务器.当其他的都宕机了.才会请求它
		
		max_conns
			# max_conns=10000
			# 最大的并发连接数,默认为0.表示没有最大连接限制
			# 这是一个'保护节点的方案',防止服务器超负荷



	# 通常负责均衡后的地址都是写IP,通过DNS解析来做负载均衡

--------------------------------
Nginx-proxy_pass 详解			|
--------------------------------
	# 这个应该是反向代理的范畴
	# prox_pass,指令属于:ngx_http_proxy_module模块,该模块可以把请求转发到指定的服务器
	# 该指令存在的位置
		* location
		* if in location
		* limit_except
	# 参数
		proxy_set_header
			* 设置由后端的服务器获取用户的主机名或者真实的IP地址,以及代理者的IP真实地址
		
		proxy_next_upstream http_502 http_504 error timeout invalid_header;
			* 如果后端服务器返回502,504执行超时等错误,自动把请求转发到负载均衡中其他的服务器.实现故障转移

		client_body_buffer_size
			* 用于指定客户端请求主体缓冲区大小,可以理解为.先保存到本地,再响应给客户端

		proxy_connect_timeout
			* 表示与后端服务器的连接超时时间,就是发起握手后等待响应的超时时间

		proxy_send_timeout
			* 表示后端服务器数据回传的时间,也就是说在这个参数指定的时间内,后端服务器必须传递完所有的数据.否则Nginx会断开链接

		proxy_read_timeout
			* 设置Nginx从代理的的后端服务器获取数据的时间,就是Nginx连接后.进入请求队列中,等待服务端的响应时间.
		
		proxy_buffers
			* 设置缓冲区的大小,Nginx从后端服务器获取到的数据,会先存放在该缓冲区中
		
		proxy_buffer_size
			* 设置缓区的大小,默认该缓冲区大小等于:proxy_bufers 设置的大小
		
		proxy_busy_buffers_size
			* 用于设置系统很忙时,可以使用的:proxy_buffers大小.官方推荐大小是:proxy_buffers * 2
		
		proxy_temp_file_write_size
			* 指定proxy缓存临时文件的大小

	# 简单演示
		location / {
			proxy_pass http://web_pool;
			//转发客户端请求的主机名
			proxy_set_header Host $host;
			//转发客户端的真实IP
			proxy_set_header X-Forwarded-For $remote_addr;
			//故障转移
			proxy_next_upstream http_502 http_504 error timeout invalid_header;

			proxy_set_header X-Real-IP $remote_addr;
			proxy_connect_timeout 90;
			proxy_send_timeout 90;
			proxy_read_timeout 90;
			proxy_buffer_size 4k;
			proxy_buffers 4 32k;
			proxy_busy_bufers_size 64k;
			proxy_temp_file_write_size 64k;
		}
	 
--------------------------------
Nginx-负载均衡算法				|
--------------------------------
	# 默认是采用'轮询算法'.
		* 百分比
		* 百分比,并不是几率,而是把请求次数按照百分比有规律的分配给服务器
		* 
	
	# Hash算法
		* 关键字:ip_hash
		* 就是说,一个IP(客户端),多次请求都只定位到一台机器.
		* 注意,当使用了iphash算法的时候,'backup',不能使用,会报错
		* 权重的配置不会报错,但是失效.
		* '这种负载均衡,主要就是解决会话(SESSION)保持的问题'
		* 但是,这东西也许会导致访问不均

		upstream web_pools {
			ip_hash; 
			server 127.0.0.1:8081 weight=2;
			server 127.0.0.1:8082 weight=3;
		}
	
	# fair(第三方),动态算法
		* 按照后端'服务器RS的响应时间'来分配请求,响应时间短的优先
		* 这种算法更叼,但是.Nginx本身不支持fair,如果需要使用这种算法.必须下载Nginx的:upstream_fair模块
		
		upstream web_pools {
			
		}
	
	# url_hash(第三方),算法
		* 当后端服务器为缓存服务器的时候效果更好.
		* 跟ip_hash有点类似
	
	# least_conn 算法
		* 最少连接数,计算出哪个机器的连接数最小.就分配给哪个机器
	
	# 一致性HASH算法
		* 属于高级JAVAEE程序架构师的范畴


--------------------------------
Nginx-节点健康					|
--------------------------------
	# Nginx 牛逼之处在于可以监控节点.自动的去判断.如果节点宕机了,会自动剔除.如果恢复了,又加入

--------------------------------
Nginx-监测节点状态				|
--------------------------------
	# 需要插件来实现