---------------------
Weigher
---------------------
	# 计算缓存条目的权重的接口。总权重阈值用于确定何时需要删除。

		interface Weigher<K, V>
	
	@NonNegative
	int weigh(K key, V value);

	static <K, V> Weigher<K, V> singletonWeigher()
	static <K, V> Weigher<K, V> boundedWeigher(Weigher<K, V> delegate)
