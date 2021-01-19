--------------------------
subversion					|
--------------------------
	1,安装
		yum -y install subversion

	2,创建目录
		mdir /var/svn

	3,初始化一个仓库
		svnadmin create /var/svn/repository
		* repository 就是仓库的名称

	4,配置
		* /var/svn/repository/conf/
			svnserve.cnf			//svn常规配置
				password-db = passwd	
					* 指定passwd文件中配置的那些人才能访问SVN服务器
				anon-access = none
					* 是否允许匿名访问(默认为read,也就是只读)
					* 更改为:none ,则不允许匿名访问
			passwd					//svn用户名和密码配置
				username=password
					* 前面是用户名,后面是密码
			authz
				username=r			//svn用户读写权限控制
					* 对指定用户仅仅赋予读权限

				username=rw
					* 对指定用户赋予读写权限
				--------------------
				[/]
				Litch=rw
				Rocco=r
			
			* '注意,这里的配置,前面都不能有空格,不然SVN有可能无法访问'

	5,自启动
		systemctl enable svnserve.service

	6,远程连接
		svn://ip/repository
