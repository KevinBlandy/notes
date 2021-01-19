------------------------
SVN-入门				|
------------------------
	* 安装之类的就不说了,太弱智
	* Windows下的SVN安装
		* 创建仓库(其实就是Windows下的一个目录而已)
-----------------------
配置文件				|
-----------------------
	svnserve.cnf
		* 该配置文件控制svn的常用设置
		password-db = passwd	
			* 指定passwd文件中配置的那些人才能访问SVN服务器
		* anon-access = none
			* 是否允许匿名访问(默认为read,也就是只读)
			* 更改为:none ,则不允许匿名访问
	passwd
		* 该配置文件就是配置用户名和密码
		username=password
			* 前面是用户名,后面是密码
	authz
		* 该配置文件控制着用户的读写权限等
		username=r	
			* 对指定用户仅仅赋予读权限
		username=rw
			* 对指定用户赋予读写权限
	* '注意,这里的配置,前面都不能有空格,不然SVN无法方法'
-----------------------
冲突解决				|
-----------------------
	





-----------------------
Linux	操作			|
-----------------------
	重启:/etc/init.d/svnserve restart


-----------------------
Linux	设置忽略文件	|
-----------------------
	# Eclipse执行提交的时候,会有一大堆的文件.跟项目没有关系
	1,Windows
	2,Preferences
	3,Team
	4,Ignored Resources
		* 点击右边的[Add Patteren]摁钮
		* 输入需要过滤的文件类型
			.project
			.settings
			.classpath