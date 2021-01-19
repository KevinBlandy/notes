-------------------------
事务					 |
-------------------------
	# 事务操作
		Connection connection = sql2o.beginTransaction();
		try{
			// TODO 使用该Connection完成多个事务操作

		}catch (Exception e) {
			if(connection != null) {
				connection.rollback();
			}
		}finally {
			if(connection != null){
				connection.close();
			}
		}

		* beginTransaction() 获取一个事务连接
		* 可以在获取事务连接的时候,设置事务的隔离级别
			beginTransaction(int isolationLevel)
			java.sql.Connection
				int TRANSACTION_NONE             = 0;
				int TRANSACTION_READ_UNCOMMITTED = 1;
				int TRANSACTION_READ_COMMITTED   = 2; (Sql2o默认)
				int TRANSACTION_REPEATABLE_READ  = 4;
				int TRANSACTION_SERIALIZABLE     = 8;
		
		* 也可以从指定的链接源获取到事务连接
			 beginTransaction(ConnectionSource connectionSource)
			 beginTransaction(ConnectionSource connectionSource, int isolationLevel)

			 public interface ConnectionSource {
				java.sql.Connection getConnection() throws SQLException;
			 }

			 * 一般用于,第三方的框架管理事务
			
		
		* 使用 rollback(); 回滚事务,也可以使用: rollback(boolean closeConnection) 来设置,是否在回滚时关闭连接
	
