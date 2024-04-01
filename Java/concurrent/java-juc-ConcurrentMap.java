--------------------------------
ConcurrentMap
--------------------------------
	# ����Map�ӿ�
		public interface ConcurrentMap<K, V> extends Map<K, V>
	
	# ����
		V getOrDefault(Object key, V defaultValue)
		void forEach(BiConsumer<? super K, ? super V> action)
		V putIfAbsent(K key, V value);
			* ��� K �����ڣ���� V ��ӵ� Map �У�����  null
			* ��� K �����򷵻��Ѵ��ڵ� V����ִ�� Put ����
			* ����������
				if (!map.containsKey(key))
					return map.put(key, value);
				else
					return map.get(key);

		boolean remove(Object key, Object value);
		boolean replace(K key, V oldValue, V newValue);
		V replace(K key, V value);
			* �滻���ݣ���� K �Ѿ��������滻Ϊ V�����ؾ�ֵ
			* ��� K �����ڣ��򲻻�ִ���滻������ null

		void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)

		V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
	
			* ����K�Ƿ���ڣ�������� remappingFunction

			* ���K���ڣ�remappingFunction �βξ���V�����һ�ʹ�� remappingFunction ����ֵ�滻��ֵ����� remappingFunction ����ֵ�� NULL����ɾ��Ԫ��
			* ���K�����ڣ��� remappingFunction �β��� NULL�����һ�ʹ�� remappingFunction ����ֵ��ӵ�Map����� remappingFunction ����ֵ��null���򲻻�������

			* ����������յķ���ֵ���� remappingFunction �ķ���ֵ

		V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
			
			* �����ڣ������

			* ��� K �����ڣ������ mappingFunction���ѷ���ֵ�� KEY ���뵽 Map������ֵ���������� V��������� null �򲻻�ִ�����������շ��ؽ��Ҳ�� null
			* ��� K �Ѵ��ڣ��򲻻���� mappingFunction������ֵΪ�Ѿ����ڵ� V
	
		V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)

			* ���ڣ������
		
			* ��� K �Ѿ����ڣ������� remappingFunction���βξ��� K �� V������ֵ���滻ԭֵ��������� null����ɾ��Ԫ�ء�
			* ��� K �����ڣ�������� remappingFunction���������������ֵ�� null


		V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) 

			* ��� key �����ڣ����ͱ�� ��put(key, value) ����󷵻� value

			* ��� key �Ѿ����ڣ������ remappingFunction ִ�� value �ĺϲ��������Ǿ�ֵ�� value�������ؽ���Ḳ�Ǿ�ֵ�����غϲ���Ľ��
			* ������� null �����ɾ�� key ����󷵻� null

			
