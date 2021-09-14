------------------------------------
DSLContext
------------------------------------
	# ���Ŀ�
		interface DSLContext extends Scope


------------------------------------
DSLContext
------------------------------------
	Configuration configuration();
		* ��ȡ��������Ϣ
	
	SelectSelectStep<Record1<Integer>> selectCount();
		* ִ��count��ѯ
	
	int fetchCount(Select<?> query) throws DataAccessException;
		* ���� query �� count��ѯ
	
	boolean fetchExists(Select<?> query)
		* ִ�� query �� exists��ѯ
	
	int fetchCount(Table<?> table, Collection<? extends Condition> conditions) throws DataAccessException;
		* ��ѯָ��Table��������������condition
	
	<R extends Record> R newRecord(Table<R> table, Object source);
		* �����µı���¼����ʹ�� source �������
	
	<T> T connectionResult(ConnectionCallable<T> callable);
	void connection(ConnectionRunnable runnable);
		* ��ȡ��JDBC Connection ����