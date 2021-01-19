/*****************								RPM								*********************/
------------------------
Linux- MYSQL RPM		|
------------------------
	1,查询mysql是否安装
		rpm -qa | grep -i mysql
	2,如果有安装,卸载之
		* yum -y remove mysql-libs*
		* rpm -e [查询到包名]

	3,下载文件
		MySQL-5.6.31-1.el6.i686.rpm-bundle.tar		//本笔记针对于这个版本
		下载页面：http://dev.mysql.com/downloads/mysql/
		此处选择“Red Hat Enterprise Linux 6 / Oracle Linux 6 (x86, 32-bit), RPM Bundle”下载
		下载至/root/Downloads/目录下，下载文件名为“MySQL-5.6.31-1.el6.i686.rpm-bundle.tar”

		# wget http://ftp.ntu.edu.tw/MySQL/Downloads/MySQL-5.6/MySQL-5.6.31-1.el6.i686.rpm-bundle.tar

	2. 解压tar包
		cd /root/Downloads/
		tar -xvf MySQL-5.6.31-1.el6.i686.rpm-bundle.tar

	3. 以RPM方式安装MySQL
		在RHEL系统中，必须先安装“MySQL-shared-compat-5.6.31-1.el6.i686.rpm”这个兼容包，然后才能安装server和client，否则安装时会出错。
		yum install MySQL-shared-compat-5.6.31-1.el6.i686.rpm		# RHEL兼容包
		yum install MySQL-server-5.6.31-1.el6.i686.rpm              # MySQL服务端程序
		yum install MySQL-client-5.6.31-1.el6.i686.rpm              # MySQL客户端程序
		yum install MySQL-devel-5.6.31-1.el6.i686.rpm               # MySQL的库和头文件
		yum install MySQL-embedded-5.6.31-1.el6.i686.rpm			# 我也不知道是啥
		yum install MySQL-shared-5.6.31-1.el6.i686.rpm              # MySQL的共享库

	4. 配置MySQL登录密码
		cat /root/.mysql_secret  # 获取MySQL安装时生成的随机密码
		service mysql start      # 启动MySQL服务
		mysql -uroot -p          # 进入MySQL，使用之前获取的随机密码
		SET PASSWORD FOR 'root'@'localhost' = PASSWORD('password');  # 在MySQL命令行中设置root账户的密码为password
		quit  # 退出MySQL命令行
		service mysql restart  # 重新启动MySQL服务
	
	5,允许root远程登录
		mysql> use mysql;
		mysql> select host,user,password from user;
			+-----------------------+------+-------------------------------------------+
			| host                  | user | password                                  |
			+-----------------------+------+-------------------------------------------+
			| localhost             | root | *6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9 |
			| localhost.localdomain | root | *1237E2CE819C427B0D8174456DD83C47480D37E8 |
			| 127.0.0.1             | root | *1237E2CE819C427B0D8174456DD83C47480D37E8 |
			| ::1                   | root | *1237E2CE819C427B0D8174456DD83C47480D37E8 |
			+-----------------------+------+-------------------------------------------+
		mysql> update user set password=password('密码') where user='root';
		mysql> update user set host='%' where user='root' and host='localhost';
		mysql> flush privileges;
		mysql> exit
	
	6,设置开机启动
		chkconfig mysql on
	-------------------------------------- 以上OK
	7,配置文件地址 
		/var/lib/mysql/		#数据库目录
		/usr/share/mysql	#配置文件目录
		/usr/bin			#相关命令目录
		/etc/init.d/mysql	#启动脚本
		/usr/my.cnf			#配置文件(修改字符集)
	
	8,设置字符集
		# 5.6以后:my.cnf 配置文件会在:/usr/my.cnf 而不是在/etc目录下了
		1,在[mysqld]下添加:
			character-set-server=utf8
			collation-server=utf8_general_ci
		2,在[client]下添加
			default-character-set=utf8
			* 如果没有[client],则自己添加一个
		Demo ------------------------------------------------------------------
			[mysqld]
			sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
			character-set-server=utf8
			collation-server=utf8_general_ci

			[client]
			default-character-set=utf8
	
	9,修改服务端口
		... ...
	


	10,开放防火墙(端口)
		# vim /etc/sysconfig/iptables
		# 在后添加
			-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
		---------------------------------------------------------------------
		-A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT			//意思是可以照着22的抄
		-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT


/*****************								源码安装								*********************/

---------------------------
1,安装cmake环境				|
---------------------------
	# 该套安装方式适用版本
		5.6.22
		5.6.33

	# 部分系统安装的时候就已经具备cmake
	# cmake --version 可以查看版本,确定是否安装cmake
	# yum -y install cmake,如果没有安装,则从 yum 源安装
	# 也可以自己下载源码安装,自己百度
	# 通用的一波
		yum -y install gcc-c++ &
		yum -y install gdb & 
		yum -y install ncurses-devel & 
		yum -y install bison bison-devel &
		yum -y install cmake 

---------------------------
2,添加运行用户				|
---------------------------
	useradd -r mysql

---------------------------
2,准备编译MySQL				|
---------------------------
	# 下载mysql5.6.22源码包
		wget http://101.110.118.23/dev.mysql.com/get/Downloads/MySQL-5.6/mysql-5.6.22.tar.gz
		http://ftp.ntu.edu.tw/MySQL/Downloads/MySQL-5.6/
	
	# 解压后,进入目录,执行编译.详细参数如下.根据自己的需求配置
		cmake   \
			-DCMAKE_INSTALL_PREFIX=/usr/local/mysql   			\ 
				* mysql 安装目录
			-DMYSQL_DATADIR=/usr/local/mysql/db					\ 
				* 数据库文件目录
			-DSYSCONFDIR=/etc									\ 
				* 设置my.cnf所在目录,默认为安装目录
			-DMYSQL_UNIX_ADDR=/tmp/mysql.sock					\ 
				* 监听套接字路径,这个是绝对路径,默认:/tmp/mysql.sock

			-DWITH_MYISAM_STORAGE_ENGINE=1  					\ 
				* 支持MyIASM引擎
			-DWITH_INNOBASE_STORAGE_ENGINE=1   					\ 
				* 支持InnoDB引擎
			-DWITH_MEMORY_STORAGE_ENGINE=1						\ 
				* 支持Memory引擎
				* 会给警告
			-DWITH_ARCHIVE_STORAGE_ENGINE=1						\ 
				* 支持Archive引擎
			-DWITH_BLACKHOLE_STORAGE_ENGINE=1					\ 
				* 支持Blackhole引擎

			-DWITH_READLINE=1									\ 
				* 快捷键功能(没用过)
				* 会给警告
			-DMYSQL_TCP_PORT=3306								\ 
				* 设置监听端口
			-DENABLED_LOCAL_INFILE=1							\ 
				* 运行从本地导入数据
			-DWITH_PARTITION_STORAGE_ENGINE=1					\ 
				* 安装支持数据库分区
			-DEXTRA_CHARSETS=all								\ 
				* 安装所有字符集
			-DDEFAULT_CHARSET=utf8								\ 
				* 设置默认字符集
			-DDEFAULT_COLLATION=utf8_general_ci					\ 
				* 设置默认的排序规则(校验规则)

			-DENABLE_DOWNLOADS=1								\
				* 自动下载可选文件，比如自动下载谷歌的测试包


		* 重新运行配置，需要删除CMakeCache.txt文件
		rm CMakeCache.txt

	
	# 编译OK后执行
		make && make install 
	
	# 使用脚本初始化数据库,进入安装目录
		./scripts/mysql_install_db --basedir=/usr/local/mysql --datadir=/usr/local/mysql/db --user=mysql
		* 分别指定:安装目录,数据库目录,运行用户
	
	# 拷贝my.cnf文件
		cp ./my.cnf /etc/my.cnf
		
	# 拷贝启动脚本,让service可以操控它
		cp support-files/mysql.server /etc/init.d/mysql

	# 目录权限设置
		chown mysql /usr/local/mysql -R

	# 修改/etc/profile,环境变量
		* 末尾添加
			PATH=/usr/local/mysql/bin:/usr/local/mysql/lib:$PATH
			export PATH
		* source /etc/profie
	
	# 修改/etc/my.cnf文件
		 basedir = /usr/local/mysql
		 datadir = /usr/local/mysql/db
		 port = 3306
		 server_id = 1
		 socket = /tmp/mysql.sock


	# 开机启动
		conkconfig mysql on

	# 设置root密码,以及允许远程登录
		* 登录MYSQL
		mysql -uroot
		mysql> SET PASSWORD = PASSWORD('密码');
		mysql> use mysql;
		mysql> select host,user,password from user;
			+-----------------------+------+-------------------------------------------+
			| host                  | user | password                                  |
			+-----------------------+------+-------------------------------------------+
			| localhost             | root | *6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9 |
			| localhost.localdomain | root | *1237E2CE819C427B0D8174456DD83C47480D37E8 |
			| 127.0.0.1             | root | *1237E2CE819C427B0D8174456DD83C47480D37E8 |
			| ::1                   | root | *1237E2CE819C427B0D8174456DD83C47480D37E8 |
			+-----------------------+------+-------------------------------------------+
		mysql> update user set password=password('密码') where user='root';
		mysql> update user set host='%' where user='root' and host='localhost';
		mysql> flush privileges;
		mysql> exit
		service mysql restart

		* 这个设置方法有问题,会导致root在当前机器上无法登录,在其他远程机器可以登录... ...
	
	
	# 开放防火墙(端口)
		# vim /etc/sysconfig/iptables
		# 在后添加
			-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
		---------------------------------------------------------------------
		-A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT			//意思是可以照着22的抄
		-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
	
	# 修改字符集
		* 如果字符集不对,则自己修改/etc/my.cnf文件
		1,在[mysqld]下添加:
			character-set-server=utf8
			collation-server=utf8_general_ci
		2,在[client]下添加
			default-character-set=utf8
			* 如果没有[client],则自己添加一个
		Demo ------------------------------------------------------------------
			[mysqld]
			sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
			character-set-server=utf8
			collation-server=utf8_general_ci

			[client]
			default-character-set=utf8

	# 大功告成