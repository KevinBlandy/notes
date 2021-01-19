------------------------
Tuple
------------------------
	# 一个通用结果集的封装对象接口
	# 抽象方法
		<T> T get(int index, Class<T> type);
			* 根据下标和数据类型获取数据
			* 下标从0开始

		<T> T get(Expression<T> expr);
			* 根据实体属性获取到数据

		int size();
			* 条目数量

		Object[] toArray();
			* 转换为 Object[]

		boolean equals(Object o);
		int hashCode();

