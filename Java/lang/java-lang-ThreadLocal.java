--------------------
ThreadLocal			|
--------------------

	�������������ʵ��һ��Map����
	�Ե�ǰ�߳�Ϊ��!
	ÿ���߳�ֻ��ȡ��/�����Լ��̵߳Ķ�����
	Map<Thread,t> map = new HashMap<Thread,t>();
	set();
	get();
	remove();

	ͨ������һ����ĳ�Ա��,����̷߳�����ʱ
	ÿ���̶߳����Լ��ĸ�������������
	Spring��,�� Connection �ŵ��� ThreadLocal �С�

	
	# ��̬����
		public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier)
			* �״ε���Get�����supplier���г�ʼ��

	# ���췽��
		public ThreadLocal()
	
	# ʵ������
		
		