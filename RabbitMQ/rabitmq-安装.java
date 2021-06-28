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
		https://github.com/rabbitmq/rabbitmq-server/releases

		* 找到EXE文件就行
			https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.18/rabbitmq-server-3.8.18.exe

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
	# 【推荐】使用packagecloud安装
		curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.rpm.sh | sudo bash
			* 运行Package Cloud提供的RabbitMQ Server快速安装脚本

		curl -s https://packagecloud.io/install/repositories/rabbitmq/erlang/script.rpm.sh | sudo bash
			* 运行Package Cloud提供Erlang环境快速安装脚本

		yum  -y install erlang
			* 使用yum安装Erlang环境

		yum install socat logrotate -y
			* 安装socat, logrotate依赖

		yum install -y rabbitmq-server
			* 使用yum安装RabbitMQ Server
	
	# 配置文件
		* 需要自己创建
		* 配置文件所在目录: /etc/rabbitmq
		
		* 配置文件
			rabbitmq.conf
			advanced.config
			rabbitmq-env.conf(rabbitmq-env.conf.bat(windows))
		
		
		* 参考
			https://rabbitmq.com/configure.html
		
	
	# 维护
		systemctl start rabbitmq-server
		systemctl stop rabbitmq-server
		systemctl restart rabbitmq-server
		systemctl status rabbitmq-server
		systemctl enable rabbitmq-server
		systemctl disable rabbitmq-server
	
	# 安装WEB控制插件
		rabbitmq-plugins enable rabbitmq_management


		* 安装后，可以重启一下服务
		* 服务地址
			http://host:15672

		* 默认账户和密码
			guest
			guest
		
		* 默认只允许通过localhost登录

		* 添加新的账户进行登录
			* 添加用户和密码
				rabbitmqctl add_user admin 123456

			* 设置administrator(超级管理员)角色
				rabbitmqctl set_user_tags admin administrator
			
			* 设置权限，授予virtual hosts="/" 的所有资源配置的读写权限
				rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"

				* 查看设置权限的命令详细信息	
					rabbitmqctl help set_permissions  
		

---------------------------------
Docker 安装
---------------------------------
	# Docker
		https://registry.hub.docker.com/_/rabbitmq/
	

