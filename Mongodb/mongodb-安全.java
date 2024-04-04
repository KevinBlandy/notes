--------------------------
MongoBD 认证配置
--------------------------
	# MongoDB 默认不启用访问控制
		* 可以使用 --auth 或 security.authorization 设置启用授权，启用内部身份验证还可同时启用客户端授权。

	# 角色
		* 角色授予在资源上执行指定操作的权限。每个权限要么在角色中明确指定，要么从另一个角色继承，或者两者兼而有之。
		* 内置角色以及其权限

		* 数据库用户角色(在非系统 Collection 上读写的能力)
			read
			readWrite
		
		* 数据库管理角色
			dbAdmin
			dbOwner
			userAdmin
		
		* 集群管理角色
			clusterAdmin
			clusterManager
			clusterMonitor
			hostManager
		
		* 备份和恢复角色
			backup
			restore
		
		* 全数据库角色
			readAnyDatabase
			readWriteAnyDatabase
			userAdminAnyDatabase
			dbAdminAnyDatabase
		
		* 超级用户角色(超级角色)
			root
		
		* 内部角色
			__system
	
		

			

	