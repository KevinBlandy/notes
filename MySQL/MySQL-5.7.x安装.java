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
			-DMYSQL_UNIX_ADDR=/var/run/mysql/mysql.sock \           [MySQL的通讯目录]
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
-DMYSQL_UNIX_ADDR=/var/run/mysql/mysql.sock \
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
			socket = /var/run/mysql/mysql.sock 

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
