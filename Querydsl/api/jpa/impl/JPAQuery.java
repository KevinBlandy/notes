--------------------
JPAQuery
--------------------
	# JPA��������
	
	# ʵ������
		List<T> fetch()
			* �������м�¼

		T fetchOne() throws NonUniqueResultException
			* ����һ����¼, �����¼������, ���߼�¼����Ψһ��, ���׳��쳣
		
		T fetchFirst()
			* ����������һ����¼, ��¼������, ���� null
			* SQL���: SELECT .... LIMIT 1;

		QueryResults<T> fetchResults()
			* ���ط�ҳ��Ϣ
			* QueryResults ����, ��������˷�ҳ��Ϣ
				private final long limit, offset, total;
				private final List<T> results;
		
		long fetchCount()
			* ���ؽ�������ܼ�¼����
		
