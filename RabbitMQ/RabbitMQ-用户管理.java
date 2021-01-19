-------------------------------
RabbitMQ-添加用户				|
-------------------------------
	# 用户,给外界使用

	1,进入WEB管理页面
		ADMIN 
		Users
		Add a user
			username
			password
			tags
				* 标签,其实就是角色
	
-------------------------------
RabbitMQ-添加主机			|
-------------------------------
	1,进入WEB管理界面
		ADMIN
		virtual hosts
		Add a new virtual host
			Name
			* 输入虚拟主机的名称
			* 一般都是以斜杠开头:/kevinblandy

	2,分配主机
		点击,已经存在的 virtual host
			Permissions
			Set permission
			User
				* 选择指定的用户就OK

-------------------------------
RabbitMQ-角色					|
-------------------------------
	# 系统预留角色
	1,administrator
		* 超级管理员,可登录管理控制台,并且可以对用户,策略(policy)进行操作

	2,monitorring
		* 监控者,可以登录,可以查看rabbitmq节点的相关信息(进程数,内存使用情况,磁盘使用情况等)

	3,policymaker
		* 策略定制者,可以登录,可以对 policy 进管理,但是无法查看节点的信息

	4,management
		* 普通管理者,仅可登录控制台.无法看到节点信息.也无法对策略进行管理

	5,其他
		* 无法登录控制台,通常就是普通的生产者和消费者
	
