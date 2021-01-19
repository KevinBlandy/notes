------------------------
Linux-SvnServlet		|
------------------------
	1,查看是否安装了svnserve
		rpm -qa | grep -i svnserve

	2,安装svn服务
		yum -y install subversion

	3,启动svn
		service svnserve start
		# SVN在CentOS中的服务器名叫:'svnserve'

	4,创建一个仓库
		svnadmin create /repository
	
	5,修改配置
		/repository/conf/
	
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
		
		* '注意,这里的配置,前面都不能有空格,不然SVN无法方法'
		
	6,设置开机启动
		chkconfig svnserve on
	
	7,修改默认监听端口
		# svn默认端口:3690
	
	8,Centos7的话,要手动的创建 /var/svn 目录,不然启动失败

	9,Centos7 启动svn 
		svnserve -d -r /repository
	

