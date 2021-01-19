---------------
Fetchable
---------------
	# 检索接口集的接口
		List<T> fetch();
		T fetchFirst();
		T fetchOne() throws NonUniqueResultException;
		 CloseableIterator<T> iterate();
		 QueryResults<T> fetchResults();
		 long fetchCount();