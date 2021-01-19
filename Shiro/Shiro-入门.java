----------------------------
Shiro-入门					|
----------------------------
	# 文档
		http://shiro.apache.org/reference.html

	# shiro 简介
		来自于Apache的神器,是Apache的顶级项目.是一个易用强大的JAVA安全框架,提供认证,授权,加密,会话管理等功能
	
	# 它能干嘛
		* 认证
			验证用户的身份

		* 授权
			对用于执行访问控制,判断用户是否被运行执行某个事件

		* 会话管理
			在任何环境下使用Session API,就算是不在WEB容器或者EJB容器中

		* 加密
			以更简介的方式,使用加密功能,保护或隐藏隐私数据,防止偷窥

		* Realms
			聚集一个或多个用户安全数据的数据源

		* 单点登录(SSO)
			
		* 为没有关联到登录的用户启用"Remember me",功能
	
	# shiro四大核心
		Authentication			--身份验证
		Authoriztion			--授权
		Session Management		--会话管理
		Cryptography			--加密

		WebSuport				--针对于WEB架构的一些功能
		Caching					--缓存
		Cincurrency				--多线程
		RunAs					-- 在得到授权的情况下,允许访问其他用户的资源
		RemmberMe				--记住用户身份... ...
	
	# shiro 三大核心组键
		Subject
			* 代表当前用户,或者正在与系统进行交互的人,或者一个第三方服务

		SecurityManager
			* shiro架构的心脏,当Shiro与一个Subject进行交互的时候,实质上是幕后的SecurityManager处理所有繁重的Subject安全操作
		Realm
			* 当配置shiro时,必须指定至少一个Realm,用来进行身份验证和/或授权.
			* shiro提供了多种可用的Realms,来获取安全相关的数据(如果,jdbc,ini及属性配置文件等),可以定义自己的Realm,来实现自定义的数据源

			