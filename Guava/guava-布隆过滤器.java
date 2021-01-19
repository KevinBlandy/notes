----------------------------
BloomFilter					|
----------------------------
	# 布隆过滤的实现

	# Demo
		/**
         * 创建一个插入对象为一亿，误报率为0.01%的布隆过滤器
         */
		BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")),100000000, 0.0001);
		
		//可以添加一亿个元素
		bloomFilter.put("123456");
		bloomFilter.put("123456");
		bloomFilter.put("123456");
		
		//是否可能包含
		boolean result = bloomFilter.mightContain("121");
		System.out.println(result);
		

		// 返回添加的元素个数
		long approximateElementCount();

	# 注意
		* 元素可以是其他的数据类型:BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(), 100000000, 0.0001);
			Funnels.longFunnel() //表示元素类型是long
		
		* 误报率必须是正数,且小于 1.0
		
		* 提高数组长度以及 hash 计算次数可以降低误报率,但相应的 CPU,内存的消耗就会提高
		