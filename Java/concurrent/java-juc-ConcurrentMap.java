--------------------------------
ConcurrentMap
--------------------------------
	# 并发Map接口
		public interface ConcurrentMap<K, V> extends Map<K, V>
	
	# 方法
		V getOrDefault(Object key, V defaultValue)
		void forEach(BiConsumer<? super K, ? super V> action)
		V putIfAbsent(K key, V value);
			* 如果K不存在，则把V添加到Map中
			* 如果K才不存在，返回null，如果已经存在，则返回已存在的V

		boolean remove(Object key, Object value);
		boolean replace(K key, V oldValue, V newValue);
		V replace(K key, V value);
			* 替换数据，如果K已经存在则替换为V，返回旧值
			* 如果K不存在，则不会执行替换，返回NULL

		void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)

		V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
			* 不管K是否存在，都会调用 remappingFunction

			* 如果K存在，remappingFunction形参就是V，并且会使用 remappingFunction 返回值替换旧值，如果remappingFunction返回值是NULL，会删除元素
			* 如果K不存在，则 remappingFunction 形参是NULL，并且会使用 remappingFunction 返回值添加到Map，如果remappingFunction返回值是null，则不会进行添加

			* 如果 remappingFunction返回null的话，会删除Map中对应的K

			* 这个方法最终的返回值就是 remappingFunction 的返回值

		V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
			* 不存在调用
			* 如果K不存在，则调用 mappingFunction，把返回值和KEY存入到Map，这个方法返回值就是新增的V
			* 如果K已经存在，则不会调用 mappingFunction，返回值为已经存在的V
	
		V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
			* 存在调用
			* 如果K已经存在，则会调用 remappingFunction，形参就是K和V，返回值V如果是NULL，则删除元素，不是NULL则替换
			* 如果K不存在，不会调用 remappingFunction，则这个函数返回值是NULL


		V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) 
