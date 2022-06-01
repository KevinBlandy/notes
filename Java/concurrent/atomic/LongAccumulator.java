------------------
LongAccumulator
------------------
	# 构造方法
		public LongAccumulator(LongBinaryOperator accumulatorFunction,  long identity)
			* 要执行的操作，这是函数接口
				long applyAsLong(long left, long right);
			* identity 初始值
		
	
	# 实例方法
		public void accumulate(long x) 
			* 传入参数执行操作

		public long get()
			* 获取执行结果

		public void reset() 
			* 重置

		public long getThenReset()
			* 获取执行结果并且重置
		

	# 也以相同思想提供了: DoubleAccumulator 实现

	