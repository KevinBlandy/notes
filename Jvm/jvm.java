------------------------
jvm						|
------------------------
	# 参考
		https://blog.csdn.net/antony9118/article/details/51375662
		https://blog.csdn.net/coderlius/article/details/79272773
	
	# HotSpot VM
	
	# 堆内存的切分
		* 新生代	1/3
			- Eden					80%
				* 新生对象, 存放内存
				* 如果对象太大, 会直接放入老年代
				* 如果内存不足, 会触发一次: Minor GC

			- From Survivor			10%
				* 上一次GC的幸存对象, 作为这一GC的被扫描者

			- TO Survivor			10%
				* 保留了一次 Minor GC 中的幸存者
				

		* 老年代	2/3


------------------------
GC						|
------------------------
	# Minor GC
		* 从年轻代空间(包括 Eden 和 Survivor 区域)回收内存
		* Minor GC 都会触发 stop-the-world, 采用复制算法
		* GC过程
			1. 把存活对象从 Eden/From Survivor 复制到 To Survivor , 对象年龄 +1, 
				* 如果有对象年龄到达了上限, 移动到老年代
				* 如果 To Survivor 内存不足, 就放到老年代
			
			2. 清空 Eden/From Survivor 中的对象
			3. From Survivor 和 To Survivor 互换
				* 原来的 To Survivor 成为下一次 GC 的 From Survivor


	# Full GC/Major GC
		* Major GC 指的是, 清理老年代, 一般都会伴随一次 Minor GC(非绝对的), 也就形成了 Full GC
		* 速度很慢, 比 Minor GC 慢10倍以上, 采用标记清除算法
		* 它会扫描整个堆, 标记存活对象, 回收没被标记的对象, 会产生内存碎片
		* 为了减少内存消耗, 还需要进行合并

------------------------
可能涉及到的名词解释	|
------------------------
	# 并行
		* 多个GC收集器线程在同时的工作, 但是应用线程处于终止状态

	# 并发
		* 应用线程和GC收集线程同时(因为CPU核心数的问题,可能会交替执行)执行
	
	# promotion failed
		* Minor GC时, survivor space放不下, 对象只能放入老年代, 而此时老年代也放不下, 会触发 Full GC

	# Concurrent Mode Failure 
		* Concurrent Mode Failure 并发执行收集的时候, 不能腾出内存给正在运行的业务线程
		* 此时会临时启动:Serial Old 收集器来重新对老年代进行垃圾收集

------------------------
触发Full GC的可能		|
------------------------
	# 出现Full GC的时候经常伴随至少一次的Minor GC,但非绝对的
		* Major GC的速度一般会比Minor GC慢10 倍以上

	# 执行 System.gc()
		* 执行该方法是建议jvm进行 Full GC, 但是JVM不一定会真的执行 Full GC, 但是大部分都是会执行Full GC的

	# 老年代代空间不足
		* 老年代空间只有在新生代对象转入及创建为大对象, 大数组时才会出现不足的现象
		* 当执行Full GC后空间仍然不足, 则抛出异常:java.lang.OutOfMemoryError: Java heap space 
	
	
	# CMS GC时出现 promotion failed 和concurrent mode failure
		* promotion failed 是在进行Minor GC时, survivor space放不下, 对象只能放入老年代, 而此时老年代也放不下造成的
		* concurrent mode failure 是在执行CMS GC的过程中同时有对象要放入老年代, 而此时老年代空间不足造成的(浮动垃圾)
	
	# 担保失败
		* Minor GC 之前, JVM要检查老年代可用的最大连续空间是否大于新生代所有对象的总空间(为了应付新生代的所有对象都存活的情况)
		* 要老年代连续的内存空间小余新生代所有对象总内存大小空间, 或者小于历次晋升到老年代对象(总大小)的平均大小, 就执行 Full GC
	



------------------------
一般线程分析步骤		|
------------------------
	1. JSP 确定Java的进程ID: 18392
		
	2. 使用jstack导出java的线程列表
		jstack -l 18392 > 18392.stack

	3. 使用top -Hp 18392 -d1 命令查看java进程里的子线程的实际占用
		* 记录id后和导出的stack文件比对, 就能知道具体的是哪里占用
		* 在线程列表里面,线程的的id是以16进制表示的, 名称叫做:nid
			nid=0x47d8
	

	# 可以使用专业的在线线程dump分析平台
		https://fastthread.io/
