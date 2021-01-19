-------------------------------
Linux-系统管理					|
-------------------------------

-------------------------------
Linux-进程管理					|
-------------------------------
	# 进程查看
		* 进程是正在执行的一个程序或者命令,每个进程都是自己的地址空间,耗费一定的系统资源
		* 通过查看进程可以判断服务器的健康状态
		
		ps -le
			# 查看系统中的所有进程,是标准的Linux命令格式

		ps aux
			# 查看系统中的所有进程,使用BSD操作系统(unix)格式
			-------------------------------------------------------------------------
			USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
			root          1  0.0  0.1  19364  1540 ?        Ss   17:58   0:00 /sbin/init
			root          2  0.0  0.0      0     0 ?        S    17:58   0:00 [kthreadd]
			root          3  0.0  0.0      0     0 ?        S    17:58   0:00 [migration/0]
			root          4  0.0  0.0      0     0 ?        S    17:58   0:00 [ksoftirqd/0]
			root          5  0.0  0.0      0     0 ?        S    17:58   0:00 [migration/0]
			root          6  0.0  0.0      0     0 ?        S    17:58   0:00 [watchdog/0]
			root          7  0.0  0.0      0     0 ?        S    17:58   0:02 [events/0]
			root          8  0.0  0.0      0     0 ?        S    17:58   0:00 [cgroup]
			root          9  0.0  0.0      0     0 ?        S    17:58   0:00 [khelper]
			root         10  0.0  0.0      0     0 ?        S    17:58   0:00 [netns]
			root         11  0.0  0.0      0     0 ?        S    17:58   0:00 [async/mgr]
			root         12  0.0  0.0      0     0 ?        S    17:58   0:00 [pm]
			root         13  0.0  0.0      0     0 ?        S    17:58   0:00 [sync_supers]
			root         14  0.0  0.0      0     0 ?        S    17:58   0:00 [bdi-default]
			root         15  0.0  0.0      0     0 ?        S    17:58   0:00 [kintegrityd/0]
			root         16  0.0  0.0      0     0 ?        S    17:58   0:00 [kblockd/0]
			root         17  0.0  0.0      0     0 ?        S    17:58   0:00 [kacpid]
			root         18  0.0  0.0      0     0 ?        S    17:58   0:00 [kacpi_notify]
			root         19  0.0  0.0      0     0 ?        S    17:58   0:00 [kacpi_hotplug]
			root         20  0.0  0.0      0     0 ?        S    17:58   0:00 [ata_aux]
			# 每一行都是一个单独的进程
			# USER:该进程是由哪个用户启动的
			# PID :进程标识,进程ID
			# CPU :消耗多少CPU(百分比)
			# MEM :消耗多少内存(百分百)
			# VSZ :单用虚拟内存的大小(KB)
			# RSS :该进程占用实际物理内存的大小(KB)
			# TTY :该进程是在哪个终端中运行的,如果是'?',则并不是由终端调用的,而是直接由内核产生的
			# STAT:进程状态
					* R:运行
					* S:睡眠
					* T:停止
					* s:包含子进程
					* +:位于后台
			# START:该进程启动的时间
			# TIME:该进程占用CPU的运算时间,注意,并不是系统时间
			# COMMAND:产生此进程的命令名

			* init进程是所有进程的爸爸,它是最先启动的,所以它几乎永远都是1

	# 查看进程树
		pstree [选项]
		# 选项
			-p:				//显示进程的PID
			-u:				//显示进程的所属用户

	# 查看系统健康状态
		top [选项 ]
		# 选项
			-d [秒数]:		//指定top每隔几秒刷新一次,默认是3秒
		# 在top命令模式中,可以使用使用的命令
			?或者h:					//显示交互模式的帮助
			P:						//以CPU使用率排序,默认项
			M:						//以内存的使用率排序
			N:						//以PID排序
			q:						//退出top
			---------------------------------------------------------
			//系统当前时间	 系统运行时间	在线用户	系统在之前1分钟, 5分钟,15分钟的平均负载,一般认为小余1,负载比较小.大于1,系统已经超出负荷 
			top - 18:59:53 up  1:01,  1 user,  load average: 0.00, 0.00, 0.00
			//系统中进程数	正在运行的进程数 睡眠的进程数 停止的进程数 僵尸进程(如果不是0需要收工检测僵尸进程)
			Tasks: 106 total,   1 running, 104 sleeping,   1 stopped,   0 zombie
			//用户模式占用CPU%,系统模式占CPU%,改变过优先级用户进程占用CPU%,空闲CPU% ,等待输入/出的进程占CPU%,硬中断请求服务占用CPU%,软中断请求服务占CPU%,虚拟时间百分百%
			//ID:最重要,CPU的空闲比
			Cpu(s):  0.1%us,  0.2%sy,  0.0%ni, 99.7%id,  0.0%wa,  0.0%hi,  0.1%si,  0.0%st
			//物理内存总大小,已经使用的物理内存数,空闲内存,作为缓冲的内存数量
			Mem:   1004412k total,   798492k used,   205920k free,    29720k buffers
			//交换分区的内存的信息... ...
			Swap:  2031608k total,        0k used,  2031608k free,   140704k cached

			   PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND                                                                                                                                                                    
			  1291 root      20   0  172m 7704 4492 S  0.2  0.8   0:04.84 vmtoolsd                                                                                                                                                                   
				 7 root      20   0     0    0    0 S  0.1  0.0   0:03.53 events/0                                                                                                                                                                   
			  2435 root      20   0 15036 1248  960 R  0.1  0.1   0:00.02 top                                                                                                                                                                        
			  1381 root      20   0  199m 5220 4316 S  0.1  0.5   0:01.97 ManagementAgent                                                                                                                                                            
			  2070 mysql     20   0 1050m 447m 6912 S  0.1 45.6   0:03.57 mysqld                                                                                                                                                                     
			  2272 gdm       20   0  267m 8656 6836 S  0.1  0.9   0:00.08 gnome-power-man                                                                                                                                                            
			  2342 root      20   0 98.0m 4028 3044 S  0.1  0.4   0:01.07 sshd                                                                                                                                                                       
				 1 root      20   0 19364 1540 1228 S  0.0  0.2   0:00.99 init                                                                                                                                                                       
				 2 root      20   0     0    0    0 S  0.0  0.0   0:00.00 kthreadd                                                                                                                                                                   
				 3 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 migration/0                                                                                                                                                                
				 4 root      20   0     0    0    0 S  0.0  0.0   0:00.03 ksoftirqd/0                                                                               
						 1 root      20   0 19364 1540 1228 S  0.0  0.2   0:00.99 init            
			
			# PID:
			# user:
			# PR:

			* 僵尸进程--这个进程正在终止,但是呢还没终止完成

-------------------------------
Linux-终止进程					|
-------------------------------
	# 查看所有的进程信号
		kill -l
			 1) SIGHUP       2) SIGINT       3) SIGQUIT      4) SIGILL       5) SIGTRAP
			 6) SIGABRT      7) SIGBUS       8) SIGFPE       9) SIGKILL     10) SIGUSR1
			11) SIGSEGV     12) SIGUSR2     13) SIGPIPE     14) SIGALRM     15) SIGTERM
			16) SIGSTKFLT   17) SIGCHLD     18) SIGCONT     19) SIGSTOP     20) SIGTSTP
			21) SIGTTIN     22) SIGTTOU     23) SIGURG      24) SIGXCPU     25) SIGXFSZ
			26) SIGVTALRM   27) SIGPROF     28) SIGWINCH    29) SIGIO       30) SIGPWR
			31) SIGSYS      34) SIGRTMIN    35) SIGRTMIN+1  36) SIGRTMIN+2  37) SIGRTMIN+3
			38) SIGRTMIN+4  39) SIGRTMIN+5  40) SIGRTMIN+6  41) SIGRTMIN+7  42) SIGRTMIN+8
			43) SIGRTMIN+9  44) SIGRTMIN+10 45) SIGRTMIN+11 46) SIGRTMIN+12 47) SIGRTMIN+13
			48) SIGRTMIN+14 49) SIGRTMIN+15 50) SIGRTMAX-14 51) SIGRTMAX-13 52) SIGRTMAX-12
			53) SIGRTMAX-11 54) SIGRTMAX-10 55) SIGRTMAX-9  56) SIGRTMAX-8  57) SIGRTMAX-7
			58) SIGRTMAX-6  59) SIGRTMAX-5  60) SIGRTMAX-4  61) SIGRTMAX-3  62) SIGRTMAX-2
			63) SIGRTMAX-1  64) SIGRTMAX
			# 1:让进程立即关闭,然后重新读取配置文件之后重启(service xxx restart)
			# 2:程序终止新号,用于终止前台进程.相当于输出ctrl+c快捷键
			# 8:..
			# 9:用来立即结束程序的运行,本新号不能被阻塞,处理和忽略.用于'强制终止进程'
			# 15:['默认']正常结束进程的信号,如果进程发生问题,这个信号是无法终止进程的,需要-9信号
			# 18:该新号可以让暂停的进程恢复执行,本信号不能被阻断
			# 19:暂停前台进程,相当于输入ctrl+z快捷键,本新号不能被阻断
	
	# 根据进程PID来弄死进程
		kill -[信号] [pid] 
			* pstree -pu |grep [服务名]		//可以使用这个来看指定服务的进程信息
	
	# 根据进程名字来弄死进程
		killall [信号] [选项] [进程名]
			# 按照进程名杀死进程
			# 选项
				-i:			//交互式,询问是否要杀死进程
				-I:			//忽略进程名的大小写
			# killall -9 vsftpd	--> 简单杀死FTP服务
	
	# 根据进程名来弄死进程(还能踢人)
		pkill [信号] [选项] [进程名]
			# 选项
				-t[终端号]:按照终端号,踢出用户
		# pkill -p vsftpd		--> 简单杀死FTP
		# 踢人
			w							//先查看本机登录的用户,找到终端号
			-----------------------------
			USER     TTY      FROM              LOGIN@   IDLE   JCPU   PCPU WHAT
			root     pts/0    pc201603271442.l 18:02    0.00s  0.38s  0.00s w
			KevinBla pts/1    pc201603271442.l 19:51    2:18   0.02s  0.02s -bash
			-----------------------------
			pkill -9 -t pts/1			//根据终端号,踢出这个用户

-------------------------------
Linux-工作管理					|
-------------------------------
	# 把进程放入后台执行,跟windows中的最小化窗口是一样的
	
	# 查看后台的工作
		jobs -l
			# 选项
				-l :		//显示PID
		-----------------------------------------------------
		[1]+  2431 Stopped (signal)        top -d 10  (wd: ~)
		# '+':代表最近一个放入后台的工作,也是工作恢复的时候,默认恢复的工作.
		# '-':代表倒数第二个放入后台的工作

	# 把命令放入后台,运行
		* 在输入完命令后,末尾添加: & 符号
		tar -zcf etc.tar.gz /etc &			//就把解压的这个过程,再后台执行了

	# 把命令放入后台,暂停
		* 在top命令执行的过程中,摁下ctrl + z快捷键
		top 
		* 这种方式,在后台的状况是暂停的
	
	# 恢复后台运行的服务
		fg %[工作号]
			* 其实%是可以省略的
	
	# 恢复服务到后台
		bg %[工作号]
				

-------------------------------
Linux-系统资源查看				|
-------------------------------
	# 监控系统资源
		vmstat [刷新延时] [刷新次数]
		* 其实就有点像把top和ps命令结合了一下
		//进程数		//内存状态			//虚拟内存	//磁盘读写	//系统		//cpu
		procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----
		 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
		 1  0      0 199736  30236 144744    0    0     6     1   32   68  0  0 100  0  0
		 0  0      0 199696  30236 144744    0    0     0     0   38   75  0  0 100  0  0
		 0  0      0 199696  30236 144744    0    0     0     0   34   67  0  0 100  0  0
		 0  0      0 199696  30236 144744    0    0     0     0   32   68  0  0 100  0  0
		
	# 查看开机时内核检测信息
		* 很重要的硬件查看命令
		dmesg
			* dmesg
	
	# 查看内存使用状态
		free [选项]
		# 选项
			-b:				//以字节为单位显示
			-k:				//以KB为单位显示
			-m:				//以MB为单位显示
			-g:				//以GB为单位显示
	
	# 查看CUP信息
		* /proc/cpuinfo
	
	# 查看系统启动时间和平均负载
		* 其实也就是top命令的第一行
		uptime 
	
	# 查看系统与内核相关信息
		uname [选项]
		# 选项
			-a:			//查看系统内核所有相关信息
			-r:			//查看内核版本
			-s:			//查看内核名称
	
	# 判断操作系统的位数
		file /bin/ls
	
	# 查看当前系统的发行版
		lsb_release -a
	
	# 列出进程调用的文件
		lsof [选项]
		# 选项 
			-c[字符串]:				//仅仅列出以指定字符串开头的进程所调用的文件
			-u[用户名]:				//仅仅列出指定用户的进程所调用的文件
			-p[pid]:					//列出某个PID进程打开的文件
			
-------------------------------
Linux-系统定时任务				|
-------------------------------
	# 需要使用系统定时任务,需要:crond  的服务
		* 一般系统开机,都会自动启动的
	
	# 设置 crontab
		crontab [选项]
			-e:			//编辑crontab定时任务
			-l:			//查询crontab定时任务
			-r:			//删除当前用户所有的crontab任务
	
	

	# ...复杂,用的时候再去查