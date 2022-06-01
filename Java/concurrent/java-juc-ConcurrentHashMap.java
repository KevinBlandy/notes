----------------------------
ConcurrentHashMap			|
----------------------------
	# JDK1.8��ǰʹ�� Segment �ֶ���
		* Segment �̳��� ReentrantLock,������ HashTable ���������� put ���� get ��������Ҫ��ͬ������
		* ������ ConcurrentHashMap ֧�� CurrencyLevel (Segment ��������)���̲߳���,ÿ��һ���߳�ռ��������һ�� Segment ʱ,����Ӱ�쵽������ Segment
		* Ĭ���� 16 ����(Segment)
		* ÿ�������� 16 ��Ԫ��
		* ������Ȼ����,��ѯ��������Ч��̫�͵�����
	

	# JDK1.8��,����������ԭ�е� Segment �ֶ���,�������� CAS(�ֹ���) + synchronized ����֤������ȫ��
		* Hash��ͻ������������һ���������ת��Ϊ�����
		* ȡ���� ReentrantLock ��Ϊ�� synchronized(���Կ������°�� JDK �ж� synchronized �Ż��Ǻܵ�λ��)

	
			
	# ��̬����
		public static <K> KeySetView<K,Boolean> newKeySet()
		public static <K> KeySetView<K,Boolean> newKeySet(int initialCapacity) 
			* ����Set��Setʹ�õ�Map����ConcurrentHashMap
	
	# ʵ������
		public KeySetView<K,V> keySet(V mappedValue) 
			
	
	# ��������
		search
			* Ϊÿ��Ԫ��ִ�в�����ֱ��������������һ����null�Ľ��
			* Ȼ�󷵻������null�������������
				// �ҵ���һ��v����100��ֵ
				map.search(1, ((k, v) -> v > 100 ? v : null));
		reduce
			* ����
		
		forEach
			* ��������
		
		* ÿ�����������ĸ��汾
			xxxKeys(searchKeys)
			xxx
			xxxValues
			xxxEntries
		
		* ÿ����������һ�� parallelismThreshold ����
		* ���map������Ԫ���������������ֵ���ͻᲢ�������������
		* ʹ�� Long.MAX_VALUE ��֤���߳�ִ��
		* ʹ�� 1 ��֤���߳�ִ��


