-----------------------
Linux-Nginx源码安装		|
-----------------------
	1,官网下载源码包,上传至服务器
		nginx-1.10.1.tar.gz
	2,解压源码包
		tar zxvf nginx-1.10.1.tar.gz
	3,进入源码包目录
		* 指定安装目录,检查安装
			./configure --prefix=/usr/local/nginx 
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
					yum -y install pcre
					yum -y install pcre-devel
		
		 the HTTP gzip module requires the zlib library.
			* 缺少:zlib依赖
			* 解决方案,直接安装就是了
					yum -y install zlib
					yum -y install zlib-devel


	# rpm安装方式我就不说了,那东西简单.而且.可以用service 命令来进行维护