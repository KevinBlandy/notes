
# ConcurrentHashMap 
	* 用于替代散列同步的 Map
	* 只有应用程序需要加锁对 Map 进行独占访问时,才应该放弃使用 ConcurrentHashMap
	* 额外的原子操作API
		V putIfAbsent(K key, V value)
			* 当 key ,不存在则插入 value

		boolean remove(Object key, Object value)
			* 当key映射到了value才执行移除
		
		boolean replace(K key, V oldValue, V newValue) 
			* 当key映射到了oldValue,才执行替换
		
		 V replace(K key, V value) {
			* 当key已经存在的时候,才执行替换

	

# ConcurrentSkipListMap 
	* 代替 SortedMap

# ConcurrentSkipListSet 
	* 代替 SortedSet 

# CopyOnWriteArrayList
	* 代替同步的 List
	* 它提供了更好的性能,在迭代期间不需要对容器进行加锁或者克隆、
	* 写入时复制返回的容器,返回的迭代器不会抛出 ConcurrentModificationException,并且返回的元素,与创建时的元素完全一致,而不必考虑之后修改操作所带来的影响
	* 每当修改容器,都会复制底层数组,这需要很打的开销
	* 当迭代操作多于修改操作时,才应该使用写入时复制容器(例如:通知系统)

# CopyOnWriteArraySet
	* 代替同步的 Set
	* 它提供了更好的性能,在迭代期间不需要对容器进行加锁或者克隆