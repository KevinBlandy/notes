-----------------------
Nginx-Rewrite			|
-----------------------
	# rewrite
	# 这就是传说中的地址重写
	# 系统本身就定义了N多的变量,可以直接使用
		conf/fastcgi.conf

	# 可以出现的地方
	# 如果循环重定向,会服务器异常
		* location
		* server
	
-----------------------
Nginx-重写中会遇到的指令|
-----------------------
	if (条件){
		...
	}
			* if 判断
	set $flag '1';
			* 设置变量
	return 500;
			* 返回状态码
	break;
			* 跳出rewrite,本条规则匹配完成后,终止匹配,不再匹配后面的规则
	rewrite #重写
			* ... ...重写咯
	
-----------------------
Nginx-if				|
-----------------------
	# 语法
		if (条件){
			//执行重写
		}
		* '千万要注意,if后面有个空格'
	
	# 条件
		~
			表示区分大小写字母匹配
		!~
			跟楼上相反
		~*
			表示不区分大小写字母匹配
		!~*
			跟楼上相反
		-f	!-f
			文集是否存在
		-d	!-d
			目录是否存在
		-e	!-e
			判断文件或者目录,是否存在
		-x	!-x
			判断文件是否可执行
		
		# 支持 $1 - $9 位置化参数

	# 案例
		location / {
			if ($remote_addr = 192.168.250.171){
				//如果来访者的IP是192.168.250.171,直接响应404
				return 404;
			}
		}
	

-----------------------
Nginx-系统预定义的变量|
-----------------------
	# conf/fastcgi.cnf
		fastcgi_param  SCRIPT_FILENAME    $document_root$fastcgi_script_name;
		fastcgi_param  QUERY_STRING       $query_string;
		fastcgi_param  REQUEST_METHOD     $request_method;
		fastcgi_param  CONTENT_TYPE       $content_type;
		fastcgi_param  CONTENT_LENGTH     $content_length;

		fastcgi_param  SCRIPT_NAME        $fastcgi_script_name;
		fastcgi_param  REQUEST_URI        $request_uri;
		fastcgi_param  DOCUMENT_URI       $document_uri;
		fastcgi_param  DOCUMENT_ROOT      $document_root;
		fastcgi_param  SERVER_PROTOCOL    $server_protocol;
		fastcgi_param  REQUEST_SCHEME     $scheme;
		fastcgi_param  HTTPS              $https if_not_empty;

		fastcgi_param  GATEWAY_INTERFACE  CGI/1.1;
		fastcgi_param  SERVER_SOFTWARE    nginx/$nginx_version;

		fastcgi_param  REMOTE_ADDR        $remote_addr;
		fastcgi_param  REMOTE_PORT        $remote_port;
		fastcgi_param  SERVER_ADDR        $server_addr;
		fastcgi_param  SERVER_PORT        $server_port;
		fastcgi_param  SERVER_NAME        $server_name;