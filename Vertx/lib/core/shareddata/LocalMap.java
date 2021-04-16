-----------------
LocalMap
-----------------
	# 本地Map的接口 LocalMap<K, V> extends Map<K, V> 

-----------------
抽象
-----------------
	V get(Object key);
	V put(K key, V value);
	V remove(Object key);
	void clear();
	int size();
	boolean isEmpty();
	V putIfAbsent(K key, V value);
	boolean removeIfPresent(K key, V value);
	boolean replaceIfPresent(K key, V oldValue, V newValue);
	V replace(K key, V value);
	void close();
	Set<K> keySet();
	Collection<V> values();
	V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);
	V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);
	V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);
	boolean containsKey(Object key);
	boolean containsValue(Object value);
	Set<Entry<K, V>> entrySet();
	void forEach(BiConsumer<? super K, ? super V> action);
	V getOrDefault(Object key, V defaultValue);
	V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction);
	void putAll(Map<? extends K, ? extends V> m);
	boolean remove(Object key, Object value);
	boolean replace(K key, V oldValue, V newValue);
	void replaceAll(BiFunction<? super K, ? super V, ? extends V> function);
