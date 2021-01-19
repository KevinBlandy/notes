---------------------------
Linux-源码安装				|
----------------------------
	wget http://nginx.org/download/nginx-1.10.1.tar.gz

	# 源码安装必须依赖的库
		1,yum -y install gcc gcc-c++ autoconf automake
		2,yum -y install zlib zlib-devel openssl openssl-devel pcre pcre-devel

	1,官网下载源码包,上传至服务器
		nginx-1.10.1.tar.gz
	2,解压源码包
		tar zxvf nginx-1.10.1.tar.gz
	3,进入源码包目录
		* 指定安装目录,检查安装

./configure --prefix=/usr/local/nginx \
--with-http_stub_status_module \
--with-http_ssl_module \
--with-file-aio \
--with-http_realip_module \
--with-http_v2_module \
--with-stream \
--with-stream_ssl_module \
--with-http_slice_module \
--with-mail \
--with-mail_ssl_module \
--with-http_stub_status_module  \
--with-threads \
--with-http_gzip_static_module \
--with-http_sub_module


	4,确定无误后直接安装
		make && make install
	
	5,安装成功后,会有四个目录
		conf
			> 配置文件
		html
			> web服务的目录,网页文件
		logs
			> 日志
		sbin
			> 启动脚本
		* 运行时还会生成一些后缀名为:_temp的临时目录

	# 安装错误
		 the HTTP rewrite module requires the PCRE library
			* 缺少正则表达[PCRE]的库
			* 解决方案,直接安装依赖就是了
					yum install pcre
					yum install pcre-devel
		
		 the HTTP gzip module requires the zlib library.
			* 缺少:zlib依赖
			* 解决方案,直接安装就是了
					yum install zlib
					yum install zlib-devel
		yum -y install openssl openssl
		yum -y install openssl openssl-devel
	

	# zlib		:Nginx提供gzip模块,需要zlib库的支持
	# openssl	:Nginx提供ssl功能
	# pcre		:支持地址重写rewrite功能

	# 创建用户 & 用户组
		groupadd -r nginx
				> -r 表示是系统组
		useradd -r /sbin/nologin -g nginx
				> 不运行登录,且添加此用户到nginx用户组