-------------------------
java.util.Random		 |
-------------------------
	# 随机数
	Random r = new Random();		//未给种子。那么是根据当前时间的毫秒值来获取的随机数。
	Random r = new Random(long);	//给定种子。


------------------------
实例方法/字段			|
------------------------
	r.nextInt();					//返回 0 - 1 之间的任何数。
	r.netxInt(int num);				//返回0 - num之间的随机数。不包含 num.
		|--给定种子之后。返回的随机数。是相同的。
	
	
	IntStream ints()
	IntStream ints(long streamSize)
	IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound)
	IntStream ints(int randomNumberOrigin, int randomNumberBound)
		* 返回stream流
		
		streamSize			流的最大长度
		randomNumberOrigin	随机数开始
		randomNumberBound	随机数结束

