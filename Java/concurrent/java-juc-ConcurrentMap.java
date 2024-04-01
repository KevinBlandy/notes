--------------------------------
ConcurrentMap
--------------------------------
	# 并发Map接口
		public interface ConcurrentMap<K, V> extends Map<K, V>
	
	# 方法
		V getOrDefault(Object key, V defaultValue)
		void forEach(BiConsumer<? super K, ? super V> action)
		V putIfAbsent(K key, V value);
			* 如果 K 不存在，则把 V 添加到 Map 中，返回  null
			* 如果 K 存在则返回已存在的 V，不执行 Put 操作
			* 类似于如下
				if (!map.containsKey(key))
					return map.put(key, value);
				else
					return map.get(key);

		boolean remove(Object key, Object value);
		boolean replace(K key, V oldValue, V newValue);
		V replace(K key, V value);
			* 替换数据，如果 K 已经存在则替换为 V，返回旧值
			* 如果 K 不存在，则不会执行替换，返回 null

		void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)

		V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
	
			* 不管K是否存在，都会调用 remappingFunction

			* 如果K存在，remappingFunction 形参就是V，并且会使用 remappingFunction 返回值替换旧值，如果 remappingFunction 返回值是 NULL，会删除元素
			* 如果K不存在，则 remappingFunction 形参是 NULL，并且会使用 remappingFunction 返回值添加到Map，如果 remappingFunction 返回值是null，则不会进行添加

			* 这个方法最终的返回值就是 remappingFunction 的返回值

		V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
			
			* 不存在，则调用

			* 如果 K 不存在，则调用 mappingFunction，把返回值和 KEY 存入到 Map，返回值就是新增的 V，如果返回 null 则不会执行新增，最终返回结果也是 null
			* 如果 K 已存在，则不会调用 mappingFunction，返回值为已经存在的 V
	
		V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)

			* 存在，则调用
		
			* 如果 K 已经存在，则会调用 remappingFunction，形参就是 K 和 V，返回值会替换原值，如果返回 null，则删除元素。
			* 如果 K 不存在，不会调用 remappingFunction，则这个函数返回值是 null


		V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) 

			* 如果 key 不存在，它就变成 了put(key, value) ，最后返回 value

			* 如果 key 已经存在，则调用 remappingFunction 执行 value 的合并（参数是旧值和 value），返回结果会覆盖旧值，返回合并后的结果
			* 如果返回 null ，则会删除 key ，最后返回 null

			
