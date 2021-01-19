--------------------------
jvm参数					  |
--------------------------

--------------------------
jvm参数	统计			  |
--------------------------

-verbose:gc -Xms20m -Xmx20m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
-verbose:class -XX:+TraceClassLoading -XX:+TraceClassUnloading

-Xmx
	* 堆内存最大值

-Xms
	* 堆初始化大小
	* 此值可以设置与-Xmx相同, 以避免每次垃圾回收完成后JVM重新分配内存

-Xmn
	* 新生代内存大小

-Xss
	* 每个线程的栈的大小

-Xnoclassgc
	* 关闭Class的回收

-verbose:gc
-verbose:class

-XX:PermSize
-XX:MetaspaceSize
-XX:MaxPermSize
-XX:MaxMetaspaceSize
-XX:MaxDirectMemorySize
	* 设置最大的堆外内存大小

-XX:MinMetaspaceFreeRatio
-XX:MaxMetaspaceFreeRatio

-XX:+DisableExplicitGC
	* 禁止显示的调用GC(System.gc())

-Xloggc
	* 指定gc日志文件的地址, 而不是输出到标准输出流
		-Xloggc:../logs/gc.log

-XX:+HeapDumpOnOutOfMemoryError
	* 在内存溢出的时候, 生成内存快照

-XX:HeapDumpPath
	* 在内存溢出的时候, 存储内存快照的文件夹
		-XX:HeapDumpPath=C:\Users\KevinBlandy\Desktop
	
	* 文件名称格式: java_pid[pid].hprof
		java_pid7560.hprof


-XX:+PrintGC
	*  打印GC
-XX:+PrintHeapAtGC
	* 在GC前后都打印日志信息

-XX:+PrintGCDetails
	* 打印GC日志详情
	* 并且在JVM退出的时候, 打印各个内存区域的使用情况

-XX:+PrintGCTimeStamps  
	* 打印GC时间,JVM启动时间

-XX:+PrintGCDateStamps 
	* 输出GC的时间戳(以日期的形式, 如 2013-05-04T21:53:59.234+0800)

-Xloggc:
	* 保存GC信息到指定的文件
		-Xloggc:C:\Users\KevinBlandy\Desktop\gc.log
	* 如果文件已经存在, 会被覆写

-XX:+TraceClassLoading
	* 打印类加载信息

-XX:+TraceClassUnLoading
	* 打印类卸载信息

-XX:+PrintFlagsFinal
	* 打印出JVM所有的最终配置信息
	* 它包含了所有的配置信息, 包括手动设置的, 以及系统默认的


	










-XX:ParallelGCThreads
	* 设置 ParNew 收集器的收集线程数量

-XX:MaxGCPauseMillis
	* 置最大垃圾收集停顿时间, 它的值是一个大于 0 的整数
	* 收集器在工作时, 会调整 Java 堆大小或者其他一些参数,尽可能地把停顿时间控制在 MaxGCPauseMillis 以内
	* 停顿时间的缩短, 是牺牲了吞吐量(以前10s一次100ms的GC, 现在5s一次70ms的GC)和新生代空间(对体积小的内存收集比较快)换来的, 这也导致GC发生得更加的频繁
	* 过小的话, GC停顿时间确实下降了,　但是吞吐量也下降了

-XX:GCTimeRatio
	* 设置吞吐量大小, 它的值是一个大于 0 小于 100 之间的整数
	* 可以理解为: 垃圾收集时间占总时间的比例
	* 默认 GCTimeRatio 的值为 99, 那么系统将花费不超过 1 / (1 + 99) = 1% 的时间用于垃圾收集

-XX:NewRatio
	* 设置新生代和老年代的内存比例
	* 设置为4 -XX:NewRatio=4, 则年轻代与年老代所占比值为 1 : 4, 年轻代占整个堆栈的1/5

-XX:SurvivorRatio
	* 设置新生代内存中 Eden 和 Survivor的比例值
	* 默认 -XX:SurvivorRatio=8 , 也就是说 Eden 占 80%内存

-XX:PretenureSizeThreshold
	* 晋升到老年代的对象大小, 设置该参数后, 体积大于该参数的对象, 直接在老年代分配
	* 默认值是0, 意思是不管多大都是先在Eden中分配内存
	* 仅仅只对:Serial 和 ParNew 两个收集器有效

-XX:MaxTenuringThreshold
	* 晋升到老年代的对象年龄, 每个对象在坚持过一次:Minor GC 后, 年龄就会 +1
	* 当超过该值的时候, 就会进入老年代
	* 当超过该值的时候, 就会进入老年代, 默认为 15 

-XX:+HandlePromotionFailure
	* 是否允许分配担保失败, 即老年代的剩余空间不足以应付整个新生代所有对象都存活的极端情况
	* 担保失败后会执行Full GC

-XX:+UseConMarkSweepGC
	* 使用Concurrent Mark Sweep(CMS)作为老年代收集器
	* 如果使用该参数, 默认就会使用: ParNew 作为新生代的收集器

-XX:+UseParNewGC
	* 强制系统使用 ParNew 作为新生代的收集器

-XX:+UseAdaptiveSizePolicy
	* 打开自适应 GC 策略, 在这种模式下, 其他的一些属性不需要自己去设置, 参数会被自动调整, 以达到在堆大小, 吞吐量和停顿时间之间的平衡点
		-Xmn(新生代大小)
		-XX:+SuivivorRatio(Eden和Survivor区的比例)
		-XX:+PretenureSizeThreshold(晋升老年代对象年龄)
	* 使用自适应GC策略, 只需要把基本的内存数据设置好,例如堆内存大小值
	* 然后仅仅关注/设置最大停顿时间:-XX:MaxGCPauseMillis 
	* 或者给JVM设置一个最大吞吐量 -XX:GCTimeRatio 的优化目标, 具体的工作细节就由jvm完成


-XX:+SerialGC
-XX:+UseParallelGC
-XX:+UseParallelOldGC
-XX:+UseG1GC