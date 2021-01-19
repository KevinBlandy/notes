------------------------
Linux-服务管理			|
------------------------
	# Linux的服务分类
		1,RPM包默认安装的服务(默认)
			* 独立的服务
			* 基于xinetd(超级守护进程)的服务(中间件的感觉)
		2,源码包安装的服务

	# 服务启动与自启动
		* 服务启动:就是在当前系统用让这个服务运行,并且提供功能
		* 服务自启:让服务,跟随机器的启动而启动
	
	# 查看正在运行的服务
		ps aux
		* 其实这个是查看进程...
	
	# 操作独立的服务
		/etc/init.d/[独立服务名] [选项]
		# 选项
			start
			stop
			status
			restart
		
		service [独立服务名] [选项]
		# 这个是Redhat系列专有
		# 选项
			start
			stop
			status
			restart

	# 查看所有安装的RPM包,的服务的状态
		service --status-all
	
------------------------
Linux-RPM包安装的服务	|
------------------------
	# 查看已经安装的服务
		* 该命令可以查看所有的RPM安装包的服务状态,不能查看源码包安装的服务
		chkconfig --list

			NetworkManager  0:off   1:off   2:on    3:on    4:on    5:on    6:off
			abrt-ccpp       0:off   1:off   2:off   3:on    4:off   5:on    6:off
			abrtd           0:off   1:off   2:off   3:on    4:off   5:on    6:off
			acpid           0:off   1:off   2:on    3:on    4:on    5:on    6:off
			atd             0:off   1:off   2:off   3:on    4:on    5:on    6:off
			auditd          0:off   1:off   2:on    3:on    4:on    5:on    6:off
			blk-availability0:off   1:on    2:on    3:on    4:on    5:on    6:off
			bluetooth       0:off   1:off   2:off   3:on    4:on    5:on    6:off
			cpuspeed        0:off   1:on    2:on    3:on    4:on    5:on    6:off
			cups            0:off   1:off   2:on    3:on    4:on    5:on    6:off
			# 0-6是代表系统默认的7个运行级别
	
	# 查看指定服务的启动状态
		chkconfig --list |grep httpd
	
	# RPM安装的位置
		/etc/init.d
			* (独立服务)启动脚本位置
			* 这个目录其实是:/etc/rc.d/init.d 目录的链接目录
		/etc/sysconfig
			* 初始化环境配置文件地址
		/etc
			* 配置文件地址
		/etc/xinetd.conf
			* xinetd配置文件
		/etc/xinetd.d
			* 基于xinetd服务的启动脚本
		/var/lib
			* 服务产生的数据放在这里
		/var/log
			* 日志
		# 以上只是常规的目录,也有一些RPM软件会把一些东西放在其他的地方

------------------------
Linux-源码包安装的服务	|
------------------------
	# 查看安装的服务
		* 查看服务安装位置,一般是在/usr/local下
	
	# 把启动文件copy到/etc/rc.d/init.d目录下,也是可以使用service命令来管理服务的(不建议这么搞)
	
	# 源码包安装的服务启动
		* 使用绝对路径来调用启动脚本(每个软件的启动脚本肯定不同)
		/home/opt/tomcat/bin/start.sh
	
	# 源码包自启动
		* 编辑:/etc/rc.d/rc.local 文件
		* 加入启动命令(启动脚本的绝对路径)
		/home/opt/apache2/apachectl start
	
	# 让源码包服务器,被服务管理命令识别
		# 让Service命令之类的来管理源码包安装的服务
			* 很简单,做个软连接,连接到/etc/init.d就好了
			ln -s [启动脚本路径] /etc/init.d/[启动脚本名字]
	
	# 让某些服务在后台运行
		* 没错,在运行脚本的后面添加一个  & 符号 执行
		/usr/local/redis/redis-server &

------------------------
Linux-RPM服务自启动		|
------------------------
	# 设置自启动
		1,使用命令
			chkconfig --level [运行级别] [独立服务名] [on/off]
				* chkconfig --level 2345 httpd on
				* --level 可以省略,默认的就是:2345	可以直接:chkconfig httpd off
				* 不支持源码包安装的服务

		3,修改配置文件(标准)
			/etc/rc.d/rc.local
			* 这个文件,会在你登录系统的时候输入用户名和密码之前加载(执行)
			* 里面是可以写命令的
			* 直接在里面写入标准的文件启动命令,那么服务器会在登录之前就启动服务
				/etc/rc.d/init.d/[服务名] start

	# 管理自启动
		* 使用ntsysv命令管理自启动服务
		* 这个牛逼了,图形化界面.redhat的东西
		* 你想启动啥,就在哪个选项前面加*号.反之去掉就好
	
	# xinetd服务自启动
		1,chkconfig telnet on
		2,也可以用ntsysv来折腾
	

------------------------
Linux-总结				|
------------------------
	