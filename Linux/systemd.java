-----------------------
Systemd
-----------------------
	# 参考
		* 官方文档 https://www.freedesktop.org/software/systemd/man/systemd.unit.html
	
	# 命令
		* Systemd 并不是一个命令，而是一组命令，涉及到系统管理的方方面面。
	
		
		systemctl
			* Systemd 的主命令，用于管理系统。
		systemd-analyze
			* 用于查看启动耗时。
	
	
	# 单元
		* Systemd 可以管理所有系统资源。不同的资源统称为 Unit（单位）。
		
		Service unit
			* 系统服务
		Target unit
			* 多个 Unit 构成的一个组
		Device Unit
			* 硬件设备
		Mount Unit
			* 文件系统的挂载点
		Automount Unit
			* 自动挂载点
		Path Unit
			* 文件或路径
		Scope Unit
			* 不是由 Systemd 启动的外部进程
		Slice Unit
			* 进程组
		Snapshot Unit
			* Systemd 快照，可以切回某个快照
		Socket Unit
			* 进程间通信的 socket
		Swap Unit
			* swap 文件
		Timer Unit
			* 定时器

-----------------------
systemctl
-----------------------
	systemctl start [service]
		* 启动服务
	
	systemctl stop [service]
		* 停止服务
	
	systemctl kill [service]
		* 杀死服务

	systemctl restart [service]
		* 重新启动服务

	systemctl enable [service]
	
		* 用于建立符号链接关系。
		* 开机时，Systemd只执行 /etc/systemd/system 目录里面的配置文件。
		* 如果配置文件里面设置了开机启动，systemctl enable 命令相当于激活开机启动。
	
	systemctl disable [service]
		* 在两个目录之间撤销符号链接关系，相当于撤销开机启动。
	
	systemctl status [service]
		* 判断 Unit 是否在运行

	systemctl daemon-reload
		* 重新加载配置文件
	

-----------------------
Systemd 配置文件
-----------------------
	# 每一个 Unit 都有一个配置文件，告诉 Systemd 怎么启动这个 Unit 。
	
		* Systemd 默认从目录 /etc/systemd/system/ 读取配置文件。
		* 里面存放的大部分文件都是符号链接，指向目录 /usr/lib/systemd/system/ 真正的配置文件存放在那个目录（新建时都是写在这里）。
		
		
		
	# 配置文件格式
		
		* 配置文件分成几个区块。每个区块的第一行，是用方括号表示的区别。
		* 配置文件的区块名和字段名，都是大小写敏感的。
		* 每个区块内部是一些等号连接的键值对。
	
	
		[Unit]
			* 通常是配置文件的第一个区块，用来定义 Unit 的元数据，以及配置与其他 Unit 的关系。它的主要字段如下。
			
			Description
				* 简短描述
			Documentation
				* 文档地址
			Requires
				* 当前 Unit 依赖的其他 Unit，如果它们没有运行，当前 Unit 会启动失败
			Wants
				* 与当前 Unit 配合的其他 Unit，如果它们没有运行，当前 Unit 不会启动失败
			BindsTo
				* 与Requires类似，它指定的 Unit 如果退出，会导致当前 Unit 停止运行
			Before
				* 如果该字段指定的 Unit 也要启动，那么必须在当前 Unit 之后启动
			After
				* 如果该字段指定的 Unit 也要启动，那么必须在当前 Unit 之前启动
			Conflicts
				* 这里指定的 Unit 不能与当前 Unit 同时运行
			Condition...
				* 当前 Unit 运行必须满足的条件，否则不会运行
			Assert...
				* 当前 Unit 运行必须满足的条件，否则会报启动失败
			
			StartLimitIntervalSec
				* 检测窗口时间（默认 10s）
			StartLimitBurst
				* 窗口里，允许失败的次数
				
				* systemd 的防抖/限流机制，专门用来防止服务疯狂崩溃、重启，形成“重启风暴”。
				* 如果失败次数超过阈值，systemd 会把服务标记为 failed，并停止再尝试启动。
				* 如果超过 StartLimitBurst 次失败 → 服务进入 failed 状态，不再自动重启
				* 必须手动 systemctl reset-failed [service] && systemctl start [service] 才能恢复
		
		[Install]
			* 配置文件的最后一个区块，用来定义如何启动，以及是否开机启动。它的主要字段如下。
			
			WantedBy
				* 它的值是一个或多个 Target，当前 Unit 激活时（enable）符号链接会放入 /etc/systemd/system 目录下面以 Target 名 + .wants 后缀构成的子目录中
			RequiredBy
				* 它的值是一个或多个 Target，当前 Unit 激活时，符号链接会放入/etc/systemd/system目录下面以 Target 名 + .required后缀构成的子目录中
			Alias
				* 当前 Unit 可用于启动的别名
			Also
				* 当前 Unit 激活（enable）时，会被同时激活的其他 Unit
		
		[Service]
			
			* 用来 Service 的配置，只有 Service 类型的 Unit 才有这个区块。它的主要字段如下。
			
			Type
				* 定义启动时的进程行为。它有以下几种值。

					Type=simple
						* 默认值，执行 ExecStart 指定的命令，启动主进程
						* 管理简单、日志可控、PID systemd 已经跟踪，无需额外 PID 文件
	
					Type=forking
						* 以 fork 方式从父进程创建子进程，创建后父进程会立即退出
					Type=oneshot
						* 一次性进程，Systemd 会等当前服务退出，再继续往下执行
						* ExecStart 命令结束（退出码 0） → 服务就 成功完成

					Type=dbus
						* 当前服务通过D-Bus启动
	
					Type=notify
						* 当前服务启动完毕，会通知Systemd，再继续往下执行
						* 程序主动调用 sd_notify(READY=1)

					Type=idle
						* 若有其他任务执行完毕，当前服务才会运行

			ExecStart
				* 启动当前服务的命令
				* 不能使用相对路径，必须使用绝对路径

			ExecStartPre
				* 启动当前服务之前执行的命令
			ExecStartPost
				* 启动当前服务之后执行的命令
			ExecReload
				* 重启当前服务时执行的命令
			ExecStop
				* 停止当前服务时执行的命令

			ExecStopPost
				* 停止当其服务之后执行的命令

			RestartSec
				* 自动重启当前服务间隔的秒数
			
			PIDFile
				* 指定 PID 文件文件的位置，
				* 当 Type=forking 时，必须指定 PIDFile，systemd 才能跟踪真正的主进程，Stop/Restart 时通过 PIDFile 找到后台进程发送信号
				* 适合传统 daemon 风格程序（像很多老服务都会 fork 到后台）

			Restart
				* 定义何种情况 Systemd 会自动重启当前服务。
				
				no（默认值）
					* 退出后不会重启
				on-success
					* 只有正常退出时（退出状态码为0），才会重启
				on-failure
					* 非正常退出时（退出状态码非0），包括被信号终止和超时，才会重启
				on-abnormal
					* 只有被信号终止和超时，才会重启
				on-abort
					* 只有在收到没有捕捉到的信号终止时，才会重启
				on-watchdog
					* 超时退出，才会重启
				always
					* 不管是什么退出原因，总是重启

			TimeoutSec
				* 定义 Systemd 停止当前服务之前等待的秒数

			EnvironmentFile
				* 指定环境变量文件，该文件内部的 key=value 键值对，可以用 $key 的形式，在当前配置文件中获取。
			
			RemainAfterExit
	
	[Install] 
		
		* 定义如何安装这个配置文件，即怎样做到开机启动。
		
		
-----------------------
Systemd Demo
-----------------------

//================================
// 服务单元
//================================

[Unit]
Description=BSC Node

# 在网络可用后启动
After=network-online.target
Wants=network-online.target

# 监控单元，非法退出后执行
OnFailure=bsc-node-recovery.service

[Service]

# 守护进程
Type=simple

# 执行目录
WorkingDirectory=/data/bsc/fullnode

# 启动脚本
ExecStart=/data/bsc/fullnode/geth \
	--ws --ws.api eth,net,web3,debug \
	--config config.toml \
	--datadir data \
	--cache 20000 \
	--history.blocks=30000 \
	--tries-verify-mode none \
	--history.transactions=30000

# 重启间隔
RestartSec=5

# 失败时重启
Restart=on-failure

# 杀死子进程
KillMode=control-group

# Std/Err 输出
StandardOutput=append:/data/bsc/fullnode/std.out
StandardError=inherit

[Install]
# 自启动
WantedBy=multi-user.target

//========================
// 监控单元
//========================

[Unit]
Description=BSC Node Recovery

[Service]
Type=oneshot
ExecStart=echo '进程停止'
