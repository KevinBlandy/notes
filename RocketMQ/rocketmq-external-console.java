
# 下载地址
	https://github.com/apache/rocketmq-externals
	

# 进入目录，修改配置
	* application.properties

		rocketmq.config.loginRequired=true
			* 使用登录的方式打开控制台
		
		rocketmq.config.isVIPChannel=false
			* 如果rocketmq版本低于3.5.8, 需要设置为 false, 默认为 true
		
	* users.properties
		admin=r0cketmq1123,1
			* 定义一个或者多个用户
				[用户名]=[密码],[角色]
			* 角色。0:普通用户，1:管理员
				

# 使用Maven编译
	rocketmq-externals-master/rocketmq-console



