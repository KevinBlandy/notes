-------------------------------------
ThreadLocalRandom					 |
-------------------------------------
	#  ThreadLocalRandom类是JDK7在JUC包下新增的随机数生成器
	# 它解决了Random类在多线程下多个线程竞争内部唯一的原子性种子变量而导致大量线程自旋重试的不足
	
	# 创建实例
		ThreadLocalRandom random =  ThreadLocalRandom.current();
	
	
	# 实例方法
		int nextInt(int bound);
