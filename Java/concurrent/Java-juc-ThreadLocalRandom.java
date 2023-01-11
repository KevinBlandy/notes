-------------------------------------
ThreadLocalRandom					 |
-------------------------------------
	#  ThreadLocalRandom类是JDK7在JUC包下新增的随机数生成器
	# 它解决了Random类在多线程下多个线程竞争内部唯一的原子性种子变量而导致大量线程自旋重试的不足
	
	# 创建实例
		ThreadLocalRandom random =  ThreadLocalRandom.current();
	
	
	# 实例方法
		int nextInt(int bound);
		public IntStream ints(long streamSize)
			* 返回一个前开，后关的stream流
		
-------------------------------------
ThreadLocalRandom	使用
-------------------------------------
	# 获取随机不重复，指定长度的随机数值
		ThreadLocalRandom.current().ints(0, 10).distinct().limit(10).mapToObj(i -> i + "").collect(Collectors.joining(","));

		* 0 - 10，随机数，去重，要10个
		* 注意注意注意。如果limit，超出了10（也就是ints第二个参数），这会死循环，因为去重后的总随机数量，也不够limit个数值
		* 转换为 int[] 
			int[]  ret = ThreadLocalRandom.current().ints(0, 10).limit(6).toArray();
		
		