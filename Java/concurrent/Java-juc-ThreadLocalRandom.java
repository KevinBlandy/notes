-------------------------------------
ThreadLocalRandom					 |
-------------------------------------
	#  ThreadLocalRandom����JDK7��JUC���������������������
	# �������Random���ڶ��߳��¶���߳̾����ڲ�Ψһ��ԭ�������ӱ��������´����߳��������ԵĲ���
	
	# ����ʵ��
		ThreadLocalRandom random =  ThreadLocalRandom.current();
	
	
	# ʵ������
		int nextInt(int bound);
		public IntStream ints(long streamSize)
			* ����һ��ǰ������ص�stream��
		
-------------------------------------
ThreadLocalRandom	ʹ��
-------------------------------------
	# ��ȡ������ظ���ָ�����ȵ������ֵ
		ThreadLocalRandom.current().ints(0, 10).distinct().limit(10).mapToObj(i -> i + "").collect(Collectors.joining(","));

		* 0 - 10���������ȥ�أ�Ҫ10��
		* ע��ע��ע�⡣���limit��������10��Ҳ����ints�ڶ����������������ѭ������Ϊȥ�غ�������������Ҳ����limit����ֵ
		* ת��Ϊ int[] 
			int[]  ret = ThreadLocalRandom.current().ints(0, 10).limit(6).toArray();
		
		