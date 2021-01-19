-------------------------------
RabbitMQ-安装 Windows			|
-------------------------------
	# 安装注意
		* 尽量使用默认路径
		* 系统用户名不能是英文
		* 安装路径不能出现英文
		* 计算机名必须是英文
		* 当前安装用户必须是管理员

	1,从下载地址,下载Windos版本(exe程序)
		http://www.rabbitmq.com/download.html

	2,安装Erlang环境
		* 下载地址 http://www.erlang.org/downloads(下载windows版本,exe执行程序)
		* 弱智系列,全部默认.一直下一步
		* 可能出现的异常
			Erlang OPT XX Setup
			Error opening file for writing
			...
			* 解决方案,忽略即可
	
	4,安装RabbitMQ
		* 第一个界面,全部打勾(默认)
		* 第二个界面默认路径
		* 傻瓜式的下一步
	
	5,打开控制台
		* 如果是Wind7,直接进入安装目录.找到:RabbitMQ Command Prompt 这个cmd,运行即可
		* 如果是Win8--搜
		* 如果说没都有找到?(建议方法)
			1,进入安装路径
				C:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.5\sbin
				* 在这个目录,以管理员身份打开CMD,这个CMD跟上面那个也是一样的
	
	6,执行命令
		rabbitmq-plugins enable rabbitmq_management
		* 启动管理
		* 看到这个界面就OK
			Applying plugin configuration to rabbit@DESKTOP-UHNU5C4... started 6 plugins.
	
	
	7,进入界面
		http://127.0.0.1:15672
		
	8,使用默认账户登录
		guest/guest
		* 看起来是个来宾权限,其实是RabbitMQ中最大的权限

	9,如果登录成功,则表示安装成功

	# 如果说 Windows 安装失败,就尝试下 Linux,或者使用别人提供的MQ服务

	
-------------------------------
RabbitMQ-安装 Linux				|
-------------------------------
	1,基础环境
		yum install gcc gcc-c++
		yum install zlib zlin-devel

	2,安装Erlang环境
		yum -y install erlang

	3,下载安装包
		http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm

	4,安装
		rpm -ivh rabbitmq-server-3.6.5-1.noarch.rpm
			* service rabbitmq-server start
			* service rabbitmq-server stop
			* service rabbitmq-server restart
		* 如果安装异常,则在安装命令后台添加参数: --force --nodeps


	5,开机启动
		chkconfig rabbitmq-server on

	6,设置配置文件
		cd /etc/rabbitmq																		//进入配置文件目录
		cp /usr/share/doc/rabbitmq-server-3.4.1/rabbitmq.config.example /etc/rabbitmq/			//复制指定目录下的案例配置到当前目录
		mv rabbitmq.config.example rabbitmq.config												//改名复制到 /etc 目录下的配置文件
	
	7,开启用户远程可访问
		vim /etc/rabbitmq/rabbitmq.config
		 {loopback_users, []}
			* 去掉该行前面的注释符号"%%",并且删除最后面的逗号','

	8,开启WEB界面管理工具以及基本的维护
		
		rabbitmq-plugins enable rabbitmq_management

		systemctl start rabbitmq-server.service 
		systemctl stop rabbitmq-server.service 
		systemctl restart rabbitmq-server.service 
		

	9,防火墙开启15672端口
		/sbin/iptables -I INPUT -p tcp --dport 15672 -j ACCEPT
		/etc/rc.d/init.d/iptables save

		firewall-cmd --add-port=15672/tcp --permanent 



	# 如果yum源无法安装erlang,则需要添加yum源支持
		wget http://packages.erlang-solutions.com/erlang-solutions-1.0-1.noarch.rpm
		rpm -Uvh erlang-solutions-1.0-1.noarch.rpm
		rpm --import http://packages.erlang-solutions.com/rpm/erlang_solutions.asc

		
