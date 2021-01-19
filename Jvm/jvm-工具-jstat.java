-------------------------
jstat					 |
-------------------------
	# JVM Statistics Tool
		* 收集 HotSpot 虚拟机各方面的运行数据
	
	# 监控JVM运行状态的命令行工具
		* 可以显示本地或者远程虚拟机中的类装载, 内存, 垃圾收集, JIT编译等运行参数

	# 在Windows是GUI图形界面,在Linux是文本统计行, 类似于  top 命令的结果

	# 命令格式
		jstat [option vmid [interval[s|ms] [count]] ]

		vmid
			* 如果是本地的JVM, 则就是进程ID, 如果是远程的: [protocal:][//]lvmid[@hostname:[:port]/servername]
		
		interval
			* 表示查询间隔, 时间单位是毫秒
		
		count
			* 表示查询总次数
			* 默认只会查询一次
			* 如果设置为 : -1, 则无限输出
		
	
	# 可选参数(option)
		-class
			* 监视类装载, 卸载数量, 总空间以及所耗费的时间
			* 输出内容
				Loaded  Bytes  Unloaded  Bytes     Time   
				1913  3906.6        8     6.7       4.74
				1913  3906.6        8     6.7       4.74

		-gc
			* 监视堆状况, 包括各个分区的容量, 已经使用的空间, GC时间合计等信息
			* 输出内容
				S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
				1408.0 1408.0  0.0   1408.0 11840.0  10459.3   29264.0    21233.5   13056.0 12766.8 1536.0 1407.4   9885   12.705  65      1.641   14.346

		-gccapacity
			* 监视内容与 -gc 一样, 但是输出主要关注 Java 堆各个区域使用到的最大, 最小空间
			* 输出内容
				NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC 
				10240.0 156992.0  14656.0 1408.0 1408.0  11840.0    20480.0   314048.0    29264.0    29264.0      0.0 1060864.0  13056.0      0.0 1048576.0   1536.0   9885    65

		-gcutil
			* 监视内容与 -gc 一样, 但主要输出关注已使用空间占总空间的百分比
			* 输出内容
				S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
				0.00 100.00  88.34  72.56  97.79  91.62   9885   12.705    65    1.641   14.346

				S0:Survivor0 是空的
				S1:Survivor1 是装满的
				E: Eden 已经使用了 88.34% 的空间
				O: Old, 老年代, 已经使用了 72.56% 的空间
				M: Metaspace, 元空间, 已经使用了 97.79% 的空间
				P: JDK8以前的永久区
				CCS:
				YGC: 程序运行期间, 发生MinorGC 9885 次
				YGCT:程序运行期间, 发生MinorGC 共耗时: 12.705 秒 
				FGC:程序运行期间, 发生FullGC 65 次 
				FGCT:程序运行期间, 发生FullGC 共耗时:1.641 秒
				GCT:程序运行期间, 所有GC总耗时: 14.346 秒

				

		-gccause
			* 监视内容与 -gcutil 一样, 但是会额外输出导致上一次GC的原因
			* 输出内容
				S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT    LGCC                 GCC                 
				0.00 100.00  88.34  72.56  97.79  91.62   9885   12.705    65    1.641   14.346 Allocation Failure   No GC 
		
		-gcnew
			* 监视新生代GC状态
			* 输出内容
				S0C    S1C    S0U    S1U   TT MTT  DSS      EC       EU     YGC     YGCT  
				1408.0 1408.0    0.0 1408.0  1  15  704.0  11840.0  10459.3   9885   12.705

		-gcnewcapacity
			* 监视内容和 -gcnew 相同, 输出主要关注使用到的最大, 最小空间
			* 输出内容
				NGCMN      NGCMX       NGC      S0CMX     S0C     S1CMX     S1C       ECMX        EC      YGC   FGC 
				10240.0   156992.0    14656.0  15680.0   1408.0  15680.0   1408.0   125632.0    11840.0  9885    65

		-gcold
			* 监视老年代GC状态
			* 输出内容
				MC       MU      CCSC     CCSU       OC          OU       YGC    FGC    FGCT     GCT   
				13056.0  12766.8   1536.0   1407.4     29264.0     21233.5   9885    65    1.641   14.346

		-gcoldcapacity
			* 监视内容可以 -gcold 相同, 输出主要关注使用到的最大, 最小空间
			* 输出内容
				OGCMN       OGCMX        OGC         OC       YGC   FGC    FGCT     GCT   
				20480.0    314048.0     29264.0     29264.0  9885    65    1.641   14.346

		-gcpermcapacity
			* 输出永久代使用到的最大, 最小空间
			* JDK8后, 没有永久代了, 使用元空间 metaspace 代替
			
		-compiler
			* 输出JIT 编译器编译过的方法, 耗时等信息
			* 输出内容
				Compiled Failed Invalid   Time   FailedType FailedMethod
				2234      0       0     9.15          0    

		-printcompilation
			* 输出已经被JIT编译的方法
			* 输出内容
				Compiled  Size  Type Method
				2234     17    1 io/netty/channel/AbstractChannel$AbstractUnsafe voidPromise
				2234     17    1 io/netty/channel/AbstractChannel$AbstractUnsafe voidPromise

