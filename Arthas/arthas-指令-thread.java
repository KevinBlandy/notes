--------------------------
thread					  |
--------------------------
	# 查看当前线程信息, 查看线程的堆栈

	# 面板
		Threads Total: 13, NEW: 0, RUNNABLE: 6, BLOCKED: 0, WAITING: 4, TIMED_WAITING: 3, TERMINATED: 0                                                                                        
		ID             NAME                                          GROUP                          PRIORITY       STATE          %CPU           TIME            INTERRUPTED    DAEMON         
		22             as-command-execute-daemon                     system                         10             RUNNABLE       100            0:0             false          true           
		10             AsyncAppender-Worker-arthas-cache.result.Asyn system                         9              WAITING        0              0:0             false          true           
		8              Attach Listener                               system                         9              RUNNABLE       0              0:0             false          true           
		3              Finalizer                                     system                         8              WAITING        0              0:0             false          true           
		2              Reference Handler                             system                         10             WAITING        0              0:0             false          true           
		4              Signal Dispatcher                             system                         9              RUNNABLE       0              0:0             false          true           
		12             job-timeout                                   system                         9              TIMED_WAITING  0              0:0             false          true           
		1              main                                          main                           5              TIMED_WAITING  0              0:0             false          false          
		13             nioEventLoopGroup-2-1                         system                         10             RUNNABLE       0              0:0             false          false          
		17             nioEventLoopGroup-2-2                         system                         10             RUNNABLE       0              0:0             false          false          
		14             nioEventLoopGroup-3-1                         system                         10             RUNNABLE       0              0:0             false          false          
		15             pool-1-thread-1                               system                         5              TIMED_WAITING  0              0:0             false          false          
		16             pool-2-thread-1                               system                         5              WAITING        0              0:0             false          false       
		
	
	# 参数
		id
			* 查看指定id线程的堆栈
				thread 16
		-n
			* 指定最忙的前N个线程, 并打印堆栈
				thread -n 3
		-b
			* 找出当前阻塞其他线程的线程

			* 有时候发现应用卡住了, 通常是由于某个线程拿住了某个锁, 并且其他线程都在等待这把锁造成的
				thread -b
			* 该参数可以一键找出阻塞了其他线程的线程

		-i
			* 指定cpu占比统计的采样间隔, 单位为毫秒
				thread -n 3 -i 1000
	

	