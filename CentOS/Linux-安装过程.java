————————————————————————————
 Linux安装过程-CentOS		|
————————————————————————————
	# CentOS如果需要启动图形化安装界面,需要的内存起码是628MB
	# 下载CentOS镜像文件
	# 如果非虚拟机安装,那么可能要设置BOIS为光驱启动
	# VM快捷键
		ctrl + g			//切换到虚拟机
		ctrl + alt			//切换到真实机器
		ctrl + alt + enter	//虚拟机全屏

	1,欢迎安装界面
		install or upgrade an existing system
			* 安装或者升级现有系统(选择这个)
		install system with basic video driver
			* 安装过程采用基本的显卡驱动
		rescue installed system
			* 进入修复模式
		boot from local driver
			* 退出安装,从硬盘启动
		memory test
			* 存储介质检测
	
	2,选择了之后,系统会进行系统自检
	3,Disc Found弹窗
		* 意思是发现了光片,问你是否需要检测
		* 选择 Skip,不检测
	4,出现安装界面
		* 只要内存,设备没问题,直接等到读条结束即可
	5,语言选择界面
		* 中文，简体没得说
	6,选择键盘格式
		* 默认,美式键盘
	7,选择存储设备
		* 选择基本存储设备
	8,格式化警告,会清空当前磁盘的数据
		* 就虚拟机的,别怕.直接是.
	9,给虚拟机起主机名
		* 这东西对于Linux没啥用,随便
	10,选择时区
		* 默认
	11,设置管理员密码
		* 其实就是传说中的root密码
		* 八位字符以上
		* 不能是英文单词
		* 不能是用户相关的内容
	12,Linux分区
		# 使用所有空间
		# 替换现有的Linux系统
		# 缩小现有系统
		# 使用剩余空间
		# 创建自定义布局(选择手动)
	13,选择驱动器
		# sda		代表就是硬盘
		# 选择创建,标准分区
		# 挂载点:/		
		# boot分区只要分区,那么就默认就是第一个:sda1
		# swap在文件系统类型中
	14,引导程序设置,默认
	15,选择界面,需要在系统中安装哪些软件?
		# Desktop
			* 桌面(个人玩选这个)
		# Monimal Desktop
			* 最小化桌面
		# Minimal
			* 最小化(服务器选这个)
		# Basic Server
			* 基本服务器
		# Database Server
			* 数据库服务器
		# Web Server
			* 网页服务器
		# Virtual Host
			* 虚拟主机
		# Software development workstation
			* 软件开发工作站

