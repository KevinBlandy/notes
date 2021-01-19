DCL(理解)
1,创建用户 
	create user 用户名@IP地址 identified by '密码';[用户只能在指定IP上登录]
	create user 用户名@'%' identified by '密码';	[用户可以在任意IP登录]
	
	# 新建的用户,只能看到一个: information_schema的数据库
	# 需要root用户给它分配权限

2,给用户授权
	grant 权限1,权限n... on 数据库.* to 用户名@地址; [给指定用户分配指定数据库的权限]
		--权限:grant,create,alter,drop,insert,update,delete,select
	grant all on 数据库.* to 用户名@地址;			[给用户分派指定数据库上的所有权限]
		'数据库.*',是因为.数据中不仅仅有表,还有视图,触发器等等东西.我们都要进行统一的配置

3,撤销授权
	revoke 权限1,权限n.. on 数据库.* from 用户名@地址; [撤销指定用户在指定数据库上的权限]

4,查看权限
	show grants for 用户名@地址; [查看指定用户权限]

5,删除用户
	drop user 用户名@IP地址; [删除指定用户]