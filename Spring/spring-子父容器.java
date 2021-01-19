-------------------------
SpringMVC开发中的子父容器|
-------------------------
	# 项目启动后,一般会有两个容器
		springmvc
			* 子容器,一般用于加载Controller组键

		spring
			* 父容器,一般用于加载Service,Dao等组键
	
	# 子容器能获取父容器的所有资源
		* springmvc中的controller可以注入spring容器中的service
		
	# 父容器不能访问子容器的资源
	

			