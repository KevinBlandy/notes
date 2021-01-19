----------------------------
Svn-Linux安装				|
----------------------------
	1,尽量的更新系统
		yum update

	2,重启
		reboot now

	3,安装Apache
		yum -y install httpd httpd-devel

		Service httpd start	//启动
		chkconfig httpd on	//开机启动

	4,修改配置文件
		/etc/httpd/conf/httpd.conf

		ServerName localhost:80
			* 把 ServerName 改为 localhost:80

	5,开启80端口
		略
	
	6,安装SVN
		yum -y install mod_dav_svn subversion
			*必须安装 mod_dav_svn 模块,需要整合Apache
		
		* 安装OK后会在:/etc/httpd/conf.d 目录下多出一个 subversion.conf 的配置文件
		
	7,重启Apache
		Service httpd restart
	
	8,测试是否安装SVN模块
		ls /etc/httpd/modules | grep svn

		mod_authz_svn.so
		mod_dav_svn.so
		* 如上所示,则OK

		svn --version
		* 查看版本
	
	9,创建SVN库主目录
		* 多库模式,一份配置文件管理多个库
		mkdir /svn
	
	10,编辑配置文件
		* 安装 mod_dav_svn 产生的文件
			/etc/httpd/conf.d/subversion.conf

		* 在最后添加如下内容
		#Include /svn/httpd.conf
		<Location /svn/>
			DAV svn
			SVNListParentPath on
			SVNParentPath /svn
				* 表示父级目录,以后所有的库都是这个目录的子目录
			AuthType Basic
			AuthName "Subversion repositories"
			AuthUserFile /svn/passwd.http
				* Svn的用户文件,指定用户名和密码,需要自己创建
			AuthzSVNAccessFile /svn/authz
				* 用户的授权,哪些用户可以进行RW操作,需要自己创建
			Require valid-user
		</Location>
		RedirectMatch ^(/svn)$ $1/
			* 访问路径
			
	11,创建配置文件
		* 创建/svn/passwd.http 和/svn/authz
		touch /svn/passwd.http
		touch /svn/authz
	
	12,重启Apache
		service httpd restart

	13,安装Jsvnadmin
		Jsvnadmin
			* JAVA开发的一个管理SVN服务区项目的WEB应用
			* 部署OK后,可以通过WEB浏览器来管理SVN项目,用户,权限
			* 使SVN的管理变得简单,而不用每次都去修改配置文件
		
			* Svn项目的配置数据,保存在数据库(支持MYSQL/ORACLE)
			* 管理员可以任意分配权限
			* '需要用到mysql数据库,而且尽量单独安装.专门为SVN提供服务--至于MYSQL怎么安装,看相应笔记'
		
			* '需要使用到Tomcat7/8,'
	
	14,安装Tomcat
		* 不多说,两件事.
			1,修改端口(非必须)
			2,设置容器编码
				<Connector port="8080" protocol="HTTP/1.1"
				connectionTimeout="20000"
				redirectPort="8443" URIEncoding="UTF-8" />
		
	15,上传Jsvnadmin到Tomcat
		svnadmin-3.0.5.zip
		* 解压后会有个 svnadmin.war,直接部署在容器中就OK
	
	16,修改WEB-INF目录下的 jdbc.properties 配置文件
		* 一看就懂,指定好数据库,配置JDBC就OK
	
	17,数据库导入相应的数据
		* 初始化数据库的一些配置
			db/mysql5.sql 
			db/lang/en.sql
		* 在svnadmin-3.0.5.zip文件的db目录中
	
	18,启动Tomcat
		略
	
	19,访问页面
		http://123.207.122.145:8080/svnadmin/
		* 此处是使用Apache服务器的端口进行项目访问的

	
---------------------------
Jsvnadmin-使用				|
---------------------------
	# 添加项目
		项目
			* 名称自定义
			* demo
		路径
			* 绝对路径,以SVN父目录开头
			* 多库,所有的库,都应该以svn开头
			* /svn/demo
		URL
			* http://http://123.207.122.145/svn/demo
			* 端口是Apache监听的端口
		描述
			* 项目简单的描述
		
		* 注意,新建库,一定要对该目录进行权限操作
			chmod 777 demo

		
	

	

		
	
			
	
