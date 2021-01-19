-------------------
Nginx-Gzip压缩		|
-------------------
	# 提升网站的性能
	# gzip是常见的一个压缩算法,是大部分浏览器都支持的算法
		* 从HTTP请求头中可以看到浏览器支持的具体压缩算法
	# 比较小的文件不要压缩,特别是二进制就根本别压缩了	
	# 配置在location

-------------------
Nginx-开启压缩		|
-------------------
	# GZIP配置常用参数
		gzip			on
			* 开启压缩
		gzip_buffers	32 4k
			* 缓冲,一次最多缓存32块,每块儿4K
		gzip_comp_level[1-9]
			* 压缩级别,推荐6.该级别与计算机资源消耗成正比
		gzip_disable [正则]
			* 通过正则匹配的URI不进行压缩处理
		gzip_min_length	1000;
			* 压缩最小体积,单位是字节
		gzip_proxied	expired no-cache no-store private auth;
			* 应该写的时候服务器过载的时候一些方案
		gzip_types		text/plain application/xml;
			* 压缩哪几种类型
		gzip_vary on
			* 是否响应客户端GZIP压缩标识
	

	# 常规的配置姿势
		# 开启GZIP压缩
		gzip on;
		# 设置缓冲.32块儿,没块儿4K
		gzip_buffes 32 4k;
		# 设置压缩级别,推荐6
		gzip_comp_level 6;
		# 最小压缩体积
		gzip_min_length 200;
		# 需要被压缩的类型(Nginx高版本会报错)
		gzip_type text/css text/xml text/html application/x-javascript;
