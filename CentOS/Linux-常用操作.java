-------------------------------
Linux-常用操作					|
-------------------------------
	# 查看本机IP地址
		ifconfig

	# 关机

	# 重启

	# 切换到root账户
		su root
	
	# 以root身份执行命令
		su [命令]
	
	# 查看指定命令的帮助信息
		man [命令]
	
	# 查看某个配置文件的信息
		man [配置文件]
		* 注意,直接写配置文件的名字.不管在哪里.不要写路径

	# 查看系统已经连续运行多久
		uptime
	
	# 连续执行一大波命令
		其实就是多个命令之间使用 && 连接,会依次执行.注意有空格
	
	# wget
		* 直接从远程的地址下载数据
		* 参数 
			-b								//在后台运行
			-O name							//下载文件保存的名称
			--no-check-certificate			//如果是HTTPS连接,可以使用该参数来
	
	# rpm 安装异常
		warning: rabbitmq-server-3.6.5-1.noarch.rpm: Header V4 RSA/SHA1 Signature, key ID 6026dfca: NOKEY
		error: Failed dependencies:socat is needed by rabbitmq-server-3.6.5-1.noarch

		* 在安装命令后添加参数:--force --nodeps
			 rpm -ivh xxxxx.rpm  --force --nodeps
	
	# Linux直接文件复制
		1,主动复制
			scp 选项 源文件/目录 用户名@远程地址:/目标文件/目录

			scp -P22 -v /usr/local/music.mp3 root@www.kevinblandy.com:/home/kevinblandy/music.mp3

		
		2,主动拉取
			scp 选项 用户名@远程地址:/目标文件/目录 源文件/目录 
			scp root@www.kevinblandy.com:/home/root/others/1.mp3  /home/space/music/1.mp3 
	
		-P	:指定端口(SSH端口)
		-v	:显示详细信息
		-r	:复制目录

	# 后台运行程序
		* 直接在脚本后添加 & 符号
		* 但是这种方式,当前用户退出终端,程序就会停止
		* 使用 nohup 命令
			nohup [启动命令] [参数]
		* demo
			nohup ./command.sh > output
				* 把标准输出都输出到 output 文件
			
			nohup ./command.sh 2> output 
				* 把错误,输出到 output 文件

			nohup ./command.sh > output 2>&1 & 
				* 把标准输出和错误,都输出到output文件
			
			nohup ./command.sh > /dev/null 2>&1 
				* 把所有的输出都定向到无底洞
	
	# 查看登录记录
		last
			* 所有用户最后登录时间
		lastlog
			* 当前用户最后登录时间

	
