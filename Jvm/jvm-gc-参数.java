------------------------
收集器的参数总结		|
------------------------	
	-XX:+UseSerialGC
		* Client 模式的默认值, 开启该模式后使用:Serial + Serial Old 组合
		* 新生代串行, 老年串行

	-XX:+UseParNewGC
		* 打开后, 使用:ParNew + Serial Old 组合
		* 新生代并行, 老年代串行
	
	-XX:+UseConcMarkSweepGC 
		* 使用:ParNew + CMS + Serial Old 组合
		* 当CMS出现:Concurrent Mode Failure的时候, 会临时使用 Serial Old 收集器来重新对老年代进行垃圾收集
		* 新生代并行, 老年代并行(并行失败,临时转换为串行)
		* 可以跟业务线程并行

	-XX:+UseParallelGC 
		* 在Server模式下的默认值, 使用:Parallel Scavenge + Serial Old  组合(PS MarkSweep)组合

	-XX:+UseParallelOldGC
		* 使用:Parallel Scavenge + Parallel Old

	-XX:SurvivorRatio
		* 新生代Eden区域和Survivor区域的容量比值, 默认为:8 (也就是说Eden=80%)

	-XX:PretenureSizeThreshold
		* 晋升到老年代的对象大小, 设置该参数后, 体积大于该参数的对象, 直接在老年代分配
		* 默认值是0, 意思是不管多大都是先在Eden中分配内存
		* 仅仅只对:Serial 和 ParNew 两个收集器有效
	
	-XX:MaxTenuringThreshold
		* 晋升到老年代的对象年龄, 每个对象在坚持过一次:Minor GC 后, 年龄就会 +1
		* 当超过该值的时候, 就会进入老年代, 默认为 15 
		

	-XX:+UseAdaptiveSizePolicy
		* 打开自适应 GC 策略, 在这种模式下, 其他的一些属性不需要自己去设置, 参数会被自动调整, 以达到在堆大小, 吞吐量和停顿时间之间的平衡点
			-Xmn(新生代大小)
			-XX:+SuivivorRatio(Eden和Survivor区的比例)
			-XX:+PretenureSizeThreshold(晋升老年代对象年龄)
		* 使用自适应GC策略, 只需要把基本的内存数据设置好,例如堆内存大小值
		* 然后仅仅关注/设置最大停顿时间:-XX:MaxGCPauseMillis 
		* 或者给JVM设置一个最大吞吐量 -XX:GCTimeRatio 的优化目标, 具体的工作细节就由jvm完成
	
	-XX:HandlePromotionFailure
		* 是否允许分配担保失败, 即老年代的剩余空间不足以应付整个新生代所有对象都存活的极端情况
		* 担保失败后会执行Full GC
	
	-XX:ParallelGCThreads
		* 设置 ParNew 收集器的收集线程数量

	-XX:GCTimeRatio
		* 设置吞吐量大小, 它的值是一个大于 0 小于 100 之间的整数
		* 可以理解为: 垃圾收集时间占总时间的比例
		* 默认 GCTimeRatio 的值为 99, 那么系统将花费不超过 1 / (1 + 99) = 1% 的时间用于垃圾收集
	
	-XX:MaxGCPauseMillis
		* 置最大垃圾收集停顿时间, 它的值是一个大于 0 的整数
		* 收集器在工作时, 会调整 Java 堆大小或者其他一些参数,尽可能地把停顿时间控制在 MaxGCPauseMillis 以内
		* 停顿时间的缩短, 是牺牲了吞吐量(以前10s一次100ms的GC, 现在5s一次70ms的GC)和新生代空间(对体积小的内存收集比较快)换来的, 这也导致GC发生得更加的频繁
		* 过小的话, GC停顿时间确实下降了,　但是吞吐量也下降了
	
	-XX:CMSInitiatingOccupancyFraction
			* 设置CMS收集器在老年代空间被使用多少后出发垃圾收集,默认68%
			* 如果中老年代增长不是很快, 可以适当的调高参数(0 - 100 百分比), 降低GC次数
			* 如果CMS运行期间,预留的内存不足以业务线程的使用, 就会出现一次:Concurrent Mode Failure 失败
			* 这是JVM会启动预案, 临时启动:Serial Old 收集器来重新对老年代进行垃圾收集
			* 也就是说设置太高, 可能会导致大量的Concurrent Mode Failure 失败, 性能反而降低
		
	-XX:+UseCMSCompactAtFullCollection
		* 用于在CMS顶不住要进行Full GC的时候, 开启内存碎片的合并整理过程
		* 内存整理的国产没法并发, 空间随便问题解决了, 但是业务线程停顿时间不得不变长了
	
	-XX:CMSFullGCsBeforeCompaction
		* 设置执行多少次不压缩的FullGC后, 跟着来一次带压缩的
		* 默认为0, 表示每次进入Full GC时都进行碎片整理

	-XX:+UseG1GC
		* 使用G1收集器