-------------------------
EntityTransaction
-------------------------
	# 实体事务管理接口
	# 抽象方法
		public void begin();
		public void commit();
		public void rollback();
		public void setRollbackOnly();
		public boolean getRollbackOnly();
		public boolean isActive();
	
