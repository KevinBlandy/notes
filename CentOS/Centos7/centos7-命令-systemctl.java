------------------------
systemctl				|
------------------------
	# 版本查看
		systemctl --version
			# 查看当前系统的 systemctl 版本
		
	# 开关机
		systemctl reboot
			# 重启

		systemctl halt
		systemctl poweroff
			# 关机
	
	# 重新载入配置文件
		systemctl daemon-reload
		* 当修改了 xx.service 配置文件后,执行该命令来重新载入

	# 服务相关
		systemctl start xx.service
		systemctl stop xx.service
		systemctl restart xx.service
		systemctl reload xx.service
			# 服务的启动,停止,重启,重新载入

		systemctl enable xxx
			# 使某个服务自启动
			# 6:chkconfig xxx on	
		
		systemctl is-enabled xxx.service
			# 检查某个服务是否是自启动

		systemctl disable xxx
			# 关闭某个服务的自启动
			# 6:chkconfig xxx off
		
		systemctl status xxx.service 
			# 显示服务的详细信息
			# 参数
				* is-active			//仅仅显示活动的服务-	systemctl is-active xxx.service 
			# 6:service xxx status	
		
		systemctl list-unit-files --type=service
			# 列出所有服务（包括启用的和禁用的）

		systemctl --failed
			# 显示出所有失败的服务
		

		systemctl kill xxx
			# 后面也可以添加.service
			# 杀死服务

	# 其他
		systemd-analyze blame
			# 查看各个进程启动花费的时间
		
		systemd-analyze critical-chain
			# 分析系统启动的进程关系链




------------------------
systemctl-自定义服务	|
------------------------
	# 文件地址
		/usr/lib/systemd/system
		/etc/systemd/system

	[Unit]
		Description=mysql
		After=network.target remote-fs.target nss-lookup.target
	[Service]
		Type=forking
		PIDFile=/node.js/pid
		ExecStart=/etc/init.d/mysql start
		ExecReload=/etc/init.d/mysql restart
		ExecStop=/etc/init.d/mysql stop
		PrivateTmp=true
	[Install]
		WantedBy=multi-user.target

	[Unit]部分主要是对这个服务的说明，内容包括Description和After，Description用于描述服务，After用于描述服务类别

	[Service]部分是服务的关键，是服务的一些具体运行参数的设置，这里Type=forking是后台运行的形式，PIDFile为存放PID的文件路径，ExecStart为服务的具体运行命令，ExecReload为重启命令，ExecStop为停止命令，PrivateTmp=True表示给服务分配独立的临时空间，注意：[Service]部分的启动、重启、停止命令全部要求使用绝对路径，使用相对路径则会报错！

	[Install]部分是服务安装的相关设置，可设置为多用户的

	服务脚本按照上面编写完成后，以754的权限保存在/usr/lib/systemd/system目录下，这时就可以利用systemctl进行配置了

	首先，使用systemctl start [服务名（也是文件名）]可测试服务是否可以成功运行，如果不能运行则可以使用systemctl status [服务名（也是文件名）]查看错误信息和其他服务信息，然后根据报错进行修改，直到可以start，如果不放心还可以测试restart和stop命令。

	接着，只要使用systemctl enable xxxxx就可以将所编写的服务添加至开机启动即可。