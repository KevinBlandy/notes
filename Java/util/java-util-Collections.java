-------------------------
Collections				 |
-------------------------
	# 用于操作 Set,List,Map 的静态工具类


-------------------------
Collections-静态字段/方法|
-------------------------
	boolean addAll(Collection<? super T> c, T... elements)
		* 把变长数据都存入c
	
	void shuffle(List<?> list);
		* 随机打乱集合数据
	
	void shuffle(List<?> list, Random rnd)
		* 同上,指定的 Random
	
	<E> Set<E> newSetFromMap(Map<E, Boolean> map) 
		* 使用指定的Map来构造一个Set集合
	
	
	<T> Collection<T> unmodifiableCollection(Collection<? extends T> c)
		* 很多重载方法, 把一个集合, 变成一个不可修改的集合
	