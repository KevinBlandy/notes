---------------------
异常体系
---------------------
	# DataAccessException
		DataAccessException：一般异常通常源自java.sql.SQLException
		DataChangedException : 一个异常，表明数据库的底层记录同时发生了变化（参见乐观锁定）
		DataTypeException：类型转换过程中出现问题
		DetachedException : SQL 语句是在“分离的” UpdatableRecord或“分离的” SQL 语句上执行的。
		InvalidResultException：执行的操作只需要一个结果，但返回了多个结果。
		MappingException：从POJO加载记录或将记录映射到 POJO 时出错