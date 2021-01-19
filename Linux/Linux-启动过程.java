————————————————————————————————
6,Linux-启动过程					|
————————————————————————————————
	* boot sequence(important)
	1,load bios(hardware infomation)
		* 加电，自检，BIOS
	2,read MBR's config to find out the OS
		* 获取硬盘的第一个扇道的第一个磁头数据
	3,load the kernel of the OS
		* 确定我们要启动哪个操作系统
	4,init process starts...
		* 启动Linux主要进程
	5,execute /etc/rc.d/sysinit
		* 执行命令(run commd)，启动精灵进程
	6,start other modules(etc/modules.conf)
		* 开始加载其他的模块
	7,execute the run level scripts
		*　内核启动完成，开始加载其他层次的东西
	8,execute /etc/rc.d/rc.local
		* 加载脚本，这个脚本会自动的加载我们所配置的东西(Tomcat)
	9,execute /bin/login
		* 登陆界面
	10,shell started...
		* 登陆成功，Shell启动
