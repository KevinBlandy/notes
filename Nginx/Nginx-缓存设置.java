-----------------------
Nginx-缓存设置			|
-----------------------
	# Nginx 缓存设置,提高缓存性能
	# 把一些静态资源缓存在客户端
	# expires(过期)


-----------------------
Nginx-expires			|
-----------------------
	# 定义格式
		expires 30s;
			// 30秒过期
		expires 30m;
			// 30分钟过期
		expires 2h;
			// 俩小时过期
		expires 30d;
			// 30天过期

	# 可以写在location中
		location iamge {
			//只要是访问image目录,全部缓存30天
			expires 30d;
		}
		location ~^ \.(jpg|gif|png|jepg){
			expires 1d;
		}
	
	
		