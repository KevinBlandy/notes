---------------
Fetchable
---------------
	# �����ӿڼ��Ľӿ�
		List<T> fetch();
		T fetchFirst();
		T fetchOne() throws NonUniqueResultException;
		 CloseableIterator<T> iterate();
		 QueryResults<T> fetchResults();
		 long fetchCount();