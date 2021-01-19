ConcurrentMap
	# 这是一个接口,下面有两个很重要的实现
	
	ConcurrentHashMap
		* 内部使用段( Segment )来表示这些不同的部分,每个段其实就是一个小的 HashTable ,它们有自己的锁,只要多个修改操作发生在不同的段上,它们就可以并发进行
		* 把一个整体分成16段(Segment).也就是最高支持16个线程的并发修改操作
		* 这也是在多线程场景时减小锁的粗粒度,从而降低锁竞争的一种方案,并且代码中大多共享变量是哟个 volatile 声明,目的是第一时间获取修改内容,性能较好

	ConcurrentSkipListMap
		* 支持并发排序功能,弥补 ConcurrentHashMap