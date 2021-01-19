-----------------------
编译安装rtmp模块		|
-----------------------
	# 下载stmp模块
		https://github.com/arut/nginx-rtmp-module

	# 下载Nginx源码
		..略

	# 编译
./configure --prefix=/usr/local/nginx \
--add-module=/opt/nginx-rtmp-module \
--with-http_stub_status_module \
--with-http_ssl_module \
--with-file-aio \
--with-http_realip_module \
--with-http_v2_module \
--with-stream \
--with-stream_ssl_module \
--with-http_slice_module \
--with-mail \
--with-mail_ssl_module

	make && make install

	* add-module 指向的是从git clone下来的目录

	# 查看Nginx编译安装的模块
		nginx -V

-----------------------
配置详解				|
----------------------
	