----------------------------
MySQL-5.7.x 安装			|
----------------------------
	1,安装编译环境
		yum -y install cmake ncurses ncurses-devel bison bison-devel boost boost-devel
		yum install -y gcc
		yum install -y gcc-c++

	2,源码下载地址
		http://ftp.ntu.edu.tw/MySQL/Downloads/MySQL-5.7/
		http://ftp.ntu.edu.tw/MySQL/Downloads/MySQL-5.7/mysql-5.7.17.tar.gz
	
	3,进入解压目录后,执行编译配置
		* 解决boost下载过慢的问题
		* 可以先下载(tar.gz包)到指定的目录(-DWITH_BOOST参数目录)
			http://www.boost.org/
			https://nchc.dl.sourceforge.net/project/boost/boost/1.59.0/boost_1_59_0.tar.gz
			http://sourceforge.net/projects/boost/files/boost/1.59.0/boost_1_59_0.tar.gz
				
		cmake \
			-DCMAKE_INSTALL_PREFIX=/usr/local/mysql  \              [MySQL安装的根目录]
			-DMYSQL_DATADIR=/usr/local/mysql/data  \                   [MySQL数据库文件存放目录]
			-DSYSCONFDIR=/etc \                                     [MySQL配置文件所在目录]
			-DMYSQL_USER=mysql \                                    [MySQL用户名]   
			
			-DWITH_MYISAM_STORAGE_ENGINE=1 \                        
			-DWITH_INNOBASE_STORAGE_ENGINE=1 \                      
			-DWITH_ARCHIVE_STORAGE_ENGINE=1 \                       
			-DWITH_FEDERATED_STORAGE_ENGINE=1 \						[MySQL的数据库引擎]
			-DWITH_BLACKHOLE_STORAGE_ENGINE=1 \
			-DWITH_MEMORY_STORAGE_ENGINE=1 \                        
			-DWITH_PARTITION_STORAGE_ENGINE=1  \				
			
			-DWITH_READLINE=1 \                                     [MySQL的readline library]
			-DMYSQL_UNIX_ADDR=/run/mysql/mysql.sock \				[MySQL的通讯目录]
			-DMYSQL_TCP_PORT=1124 \                                 [MySQL的监听端口]
			-DENABLED_LOCAL_INFILE=1 \                              [启用加载本地数据]
			-DENABLE_DOWNLOADS=1 \                                  [编译时允许自主下载相关文件]
			-DEXTRA_CHARSETS=all \                                  [使MySQL支持所有的扩展字符]
			-DDEFAULT_CHARSET=utf8mb4 \                             [设置默认字符集为utf8]
			-DDEFAULT_COLLATION=utf8mb4_general_ci \                [设置默认字符校对]
			-DWITH_DEBUG=0 \                                        [禁用调试模式]
			-DMYSQL_MAINTAINER_MODE=0 \
			-DWITH_SSL:STRING=bundled \                             [通讯时支持ssl协议]
			-DWITH_ZLIB:STRING=bundled \                            [允许使用zlib library]
			-DDOWNLOAD_BOOST=1 \
			-DWITH_BOOST=/usr/local/mysql/boost_1_59_0				[boost库安装目录]
		
cmake \
-DCMAKE_INSTALL_PREFIX=/usr/local/mysql \
-DMYSQL_DATADIR=/usr/local/mysql/data \
-DSYSCONFDIR=/etc \
-DMYSQL_USER=mysql \
-DWITH_MYISAM_STORAGE_ENGINE=1 \
-DWITH_INNOBASE_STORAGE_ENGINE=1 \
-DWITH_ARCHIVE_STORAGE_ENGINE=1 \
-DWITH_FEDERATED_STORAGE_ENGINE=1 \
-DWITH_BLACKHOLE_STORAGE_ENGINE=1 \
-DWITH_MEMORY_STORAGE_ENGINE=1 \
-DWITH_PARTITION_STORAGE_ENGINE=1 \
-DWITH_READLINE=1 \
-DMYSQL_UNIX_ADDR=/run/mysql/mysql.sock \
-DMYSQL_TCP_PORT=1124 \
-DENABLED_LOCAL_INFILE=1 \
-DENABLE_DOWNLOADS=1 \
-DEXTRA_CHARSETS=all \
-DDEFAULT_CHARSET=utf8mb4 \
-DDEFAULT_COLLATION=utf8mb4_general_ci \
-DWITH_DEBUG=0 \
-DMYSQL_MAINTAINER_MODE=0 \
-DWITH_SSL:STRING=bundled \
-DWITH_ZLIB:STRING=bundled \
-DDOWNLOAD_BOOST=1 \
-DWITH_BOOST=/usr/local/mysql/boost_1_59_0

	4,配置OK后,执行编译安装	
		make && make install
	
		* 解决编译中内存不足的问题
			dd if=/dev/zero of=/swapfile bs=64M count=16
				//dd if=/dev/zero of=/swapfile bs=1k count=2048000
			mkswap /swapfile
			swapon /swapfile

			编译完成之后
			
			swapoff /swapfile
			rm /swapfile -f
	
	5,将MySQL数据库的动态链接库共享至系统链接库
		* 这样MySQL服务就可以被其它服务调用了
		ln -s /usr/local/mysql/lib/libmysqlclient.so.20 /usr/lib/libmysqlclient.so.20 
	
	6,添加环境变量
		PATH=/usr/local/mysql/bin:/usr/local/mysql/lib:$PATH
		export PATH
		
		source /etc/profile

	7,复制启动脚本
		cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql
	
	8,编辑配置文件
		* 先复制到目录
			cp /usr/local/mysql/support-files/my-default.cnf /etc/my.cnf

		* 编辑
			basedir = /usr/local/mysql
			datadir = /usr/local/mysql/data
			port = 1124
			server_id = 1
			socket = /run/mysql/mysql.sock 

	9,执行授权操作
		chown mysql /usr/local/mysql -R
		chmod 775 /usr/local/mysql -R

		mkdir /var/run/mysql
		chown mysql /var/run/mysql -R
		chmod 775 /var/run/mysql -R
	
	10,执行数据库初始化/设置开机启动
		mysqld --initialize-insecure --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data 
		chkconfig mysql on
	
	11,启动mysql
		service mysql start
		* 如果启动失败,先检查是否mariadb已经删除
			rpm -qa | grep mariadb 
			yum -y remove mariadb-libs-5.5.52-1.el7.x86_64
	
	12,执行登录/修改密码
		mysql -uroot
		ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';

	13,修改密码/登录权限
		GRANT ALL PRIVILEGES ON *.* TO root@'%' IDENTIFIED BY 'new_password';
		FLUSH PRIVILEGES;
	
	14,开启访问端口
		firewall-cmd --add-port=1124/tcp --permanent 
		firewall-cmd --reload  



c++: internal compiler error: Killed (program cc1plus)
Please submit a full bug report,
with preprocessed source if appropriate.
See <http://bugzilla.redhat.com/bugzilla> for instructions.
make[2]: *** [unittest/gunit/CMakeFiles/merge_small_tests-t.dir/merge_small_tests.cc.o] Error 4
make[1]: *** [unittest/gunit/CMakeFiles/merge_small_tests-t.dir/all] Error 2
make: *** [all] Error 2

------------------------ yum安装	
http://blog.csdn.net/xyang81/article/details/51759200

1,下载
	https://dev.mysql.com/downloads/repo/yum/
	Red Hat Enterprise Linux 7 / Oracle Linux 7 (Architecture Independent), RPM Package		25.1K	
	(mysql57-community-release-el7-11.noarch.rpm)

	* mysql 8
		https://repo.mysql.com//mysql80-community-release-el7-1.noarch.rpm

2,安装yum源
	yum localinstall -y mysql57-community-release-el7-11.noarch.rpm

	* 查看yum源是否安装成功/etc/yum.repos.d/mysql-community.repo
		yum repolist enabled | grep "mysql.*-community.*"

		mysql-connectors-community/x86_64    MySQL Connectors Community               45
		mysql-tools-community/x86_64         MySQL Tools Community                    59
		mysql57-community/x86_64             MySQL 5.7 Community Server              247

	* 修改默认安装的mysql版本
		vim /etc/yum.repos.d/mysql-community.repo

		# Enable to use MySQL 5.5
		[mysql55-community]
		enabled=0					# 不安装5.5

		# Enable to use MySQL 5.6
		[mysql56-community]
		enabled=0					# 不安装5.6

		# Enable to use MySQL 5.7
		[mysql57-community]
		enabled=1					# 安装5.7
		
		enabled=1		//表示安装
		enabled=0		//表示不安装

3,安装mysql服务器
	yum install -y mysql-community-server

4,启动mysql服务
	systemctl start mysqld
	
	* 查看mysql服务状态
		systemctl status mysqld
	
	* 设置开机启动
		systemctl enable mysqld
		systemctl daemon-reload


5,修改root本地登录密码
	* 查看临时密码
		less /var/log/mysqld.log | grep 'temporary password'
	
	* 使用临时密码进行登录
		mysql -uroot -p
	
	* 执行修改密码方式1
		ALTER USER 'root'@'localhost' IDENTIFIED BY 'new pass'; 
	
	* 执行修改密码方式2
		set password for 'root'@'localhost' = password('new pass'); 
	
	* 注意
		mysql5.7默认安装了密码安全检查插件(validate_password),默认密码检查策略要求密码必须包含:大小写字母,数字和特殊符号,并且长度不能少于8位
		否则会提示ERROR 1819 (HY000):Your password does not satisfy the current policy requirements

6,授权用户在远程登录
	GRANT ALL PRIVILEGES ON *.* TO 'KevinBlandy'@'%' IDENTIFIED BY 'pass' WITH GRANT OPTION; 
	
		*.*			任意数据库下的任意数据表
		KevinBlandy 用户名
		%			任意ip
		pass		密码
	
	flush privileges;
	
	# mysql 8的需要通过ROLE机制来进行授权
		1,创建1个或者多个角色
			CREATE ROLE 'app_developer', 'app_read', 'app_write';
		
		2,对创建的角色进行授权
			* 语法 
				GRANT [权限] ON [db].[tb] TO [角色]
			
			* 权限是枚举,可以有多个,或者直接用 ALL 代替,表示所有权限
				SELECT,INSERT, UPDATE, DELETE
			
			* demo
				GRANT ALL		ON app_db.* TO 'app_developer';
				GRANT SELECT	ON app_db.* TO 'app_read';
				GRANT INSERT, UPDATE, DELETE ON app_db.* TO 'app_write';
		
		3,创建用户
			CREATE USER 'KevinBlandy'@'%' IDENTIFIED BY '123456';
		
		4,授权角色到用户
			* 语法
				GRANT [角色] TO [用户名]@[ip]
			
			* demo
				GRANT 'app_developer' TO 'dev1'@'localhost';
					* 授权用户在指定的ip可以使用一个角色
				GRANT 'app_read' TO 'read_user1'@'localhost', 'read_user2'@'localhost';
					* 授权用户在不同的IP可以使用不同的角色
				GRANT 'app_read', 'app_write' TO 'rw_user1'@'localhost';
					* 授权用户在一个ip可以使用多个角色

		# 通用
			 CREATE ROLE 'app_developer';
			 GRANT ALL ON *.* TO 'app_developer';
			 CREATE USER 'KevinBlandy'@'%' IDENTIFIED BY '123456';
			 GRANT 'app_developer' TO 'KevinBlandy'@'%';



7,设置默认编码
	* 编辑:vim /etc/my.cnf,在 [mysqld] 配置项中添加配置
		character_set_server=utf8
		init_connect='SET NAMES utf8'
my.cnf-----------------------------------------
[client]
default-character-set = utf8mb4

[mysql]
default-character-set = utf8mb4

[mysqld]
character-set-server = utf8mb4
collation-server = utf8mb4_general_ci
-----------------------------------------------

	
	* 重启mysql服务
		systemctl restart mysqld

	* 登录,查看编码
		show variables like '%character%';



8,默认配置文件路径
	配置文件:	/etc/my.cnf 
	日志文件:	/var/log//var/log/mysqld.log 
	服务启动脚本:	/usr/lib/systemd/system/mysqld.service 
	socket文件:		/var/run/mysqld/mysqld.pid

9,维护命令
	* 启动服务
		systemctl start mysqld

	* 停止服务
		systemctl stop mysqld

	* 重启服务
		systemctl restart mysqld
	
	* 查看mysql服务状态
		systemctl status mysqld
	
	* 设置开机启动
		systemctl enable mysqld
		systemctl daemon-reload

10, 卸载

	* 查看安装好的mysql相关的服务
		rpm -qa | grep -i mysql
	
	* 把检索到所有mysql服务都执行卸载：yum remove -y xxx
		yum remove -y mysql-community-client-5.7.27-1.el7.x86_64
		yum remove -y mysql57-community-release-el7-11.noarch
		yum remove -y mysql-community-libs-5.7.27-1.el7.x86_64
		yum remove -y mysql-community-server-5.7.27-1.el7.x86_64
		yum remove -y mysql-community-common-5.7.27-1.el7.x86_64
	
	
	* 删除遗留的数据目录
		rm -rf /var/lib/mysql
	


 