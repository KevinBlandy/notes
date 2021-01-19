----------------------------------
GC日志							  |
----------------------------------
	# 每个收集器的日志形式可能不一样
	# 虚拟机为了方便阅读, 把每个收集器的日志都维持了一定的共性

0.513: [GC (System.gc()) [PSYoungGen: 5012K->1258K(38400K)] 5012K->1266K(125952K), 0.0014624 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
0.196: [Full GC (System.gc()) [PSYoungGen: 1258K->0K(38400K)] [ParOldGen: 8K->1156K(87552K)] 1266K->1156K(125952K), [Metaspace: 3473K->3473K(1056768K)], 0.0054130 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 

	* 最前面的数字表示JVM的运行时间, 秒数

	* GC/Full GC 表示GC的类型, 而不是区分哪个年代的
	* 如果是Full GC, 肯定是发生了 stop the world 的

	* 收集器的类型,例如:ParNew
	* (System.gc()), 表示调用 System.gc() 方法触发的收集

	* 表示GC发生的区域, 这个区域名称和收集器的名称有密切的关系
		DefNew(Serial 收集器)
		ParNew(ParNew 收集器)
		PSYoungGen(Parallel Scavenge 收集器)
		Tenured
		Perm
	
	* 5012K->1258K(38400K)
		* GC前的内存已使用量 ->  GC后内存已使用量(该区域的内存总容量)
	
	* 5012K->1266K(125952K)
		* Java堆已使用的容量 -> GC后堆已使用的容量(Java堆总容量)
	
	* 0.0014624 secs
		* 该区域GC所占用的时间
	
	* [Times: user=0.00 sys=0.00, real=0.00 secs] 
		* 更为详细的时间数据, user,sys,real 跟Linux的time命名输出的时间含义一样
		user:用户态消耗CPU的时间
		sys:..
		radl..
	

	Heap
		PSYoungGen      total 38400K, used 333K [0x00000000d5a00000, 0x00000000d8480000, 0x0000000100000000)
			eden space 33280K, 1% used [0x00000000d5a00000,0x00000000d5a534a8,0x00000000d7a80000)
			from space 5120K, 0% used [0x00000000d7a80000,0x00000000d7a80000,0x00000000d7f80000)
			to   space 5120K, 0% used [0x00000000d7f80000,0x00000000d7f80000,0x00000000d8480000)
	ParOldGen       total 87552K, used 1156K [0x0000000080e00000, 0x0000000086380000, 0x00000000d5a00000)
		object space 87552K, 1% used [0x0000000080e00000,0x0000000080f21140,0x0000000086380000)
	Metaspace       used 3481K, capacity 4496K, committed 4864K, reserved 1056768K
		class space    used 376K, capacity 388K, committed 512K, reserved 1048576K