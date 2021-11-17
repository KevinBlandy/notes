----------------------------
AbstractRoutingDataSource
----------------------------
	# 动态路由数据源抽象类:
		public abstract class AbstractRoutingDataSource extends AbstractDataSource implements InitializingBean
	
	# 实例方法
		public void setTargetDataSources(Map<Object, Object> targetDataSources)
			* 设置数据源

		public void setDefaultTargetDataSource(Object defaultTargetDataSource)
			* 设置默认数据源
			* 如果 determineCurrentLookupKey 返回的Key没有找到对应的数据源，则返回该默认的

		public void setLenientFallback(boolean lenientFallback)
			* 如果 determineCurrentLookupKey 返回的Key没有找到对应的数据源，是否返回默认的
			* 设置设置为false，则会在没有找到数据源的情况下抛出异常：IllegalStateException

		public void setDataSourceLookup(@Nullable DataSourceLookup dataSourceLookup) 
			* DataSourceLookup 实现，用于解决 targetDataSources Map中的数据源名称字符串。
			* 默认是JndiDataSourceLookup，允许直接指定应用服务器DataSources的JNDI名称。

		public void afterPropertiesSet()

		protected Object resolveSpecifiedLookupKey(Object lookupKey)
			* 对 lookupkey进行解析，返回新的key
			* 实现
				return lookupKey;

		protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException
			* 对 DataSource进行解析，返回新的DataSource
			* 实现
				if (dataSource instanceof DataSource) {
					return (DataSource) dataSource;
				}
				else if (dataSource instanceof String) {
					return this.dataSourceLookup.getDataSource((String) dataSource);
				}
				else {
					throw new IllegalArgumentException(
							"Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
				}

		public Map<Object, DataSource> getResolvedDataSources()
			* 返回数据源Map，但是不允许更改
			* 源码
				return Collections.unmodifiableMap(this.resolvedDataSources);

		@Nullable
		public DataSource getResolvedDefaultDataSource()
			* 返回默认数据源

		public Connection getConnection() throws SQLException
		public Connection getConnection(String username, String password) throws SQLException

		public <T> T unwrap(Class<T> iface) throws SQLException
			* 解包装为指定的类

		public boolean isWrapperFor(Class<?> iface) throws SQLException
			* 当前类是否是指定类的包装

		protected DataSource determineTargetDataSource()
			* 获取一个数据源，默认就是调用 determineCurrentLookupKey 来获取

		protected abstract Object determineCurrentLookupKey()
			* 获取一个key


	# DataSourceLookup
		@FunctionalInterface
		public interface DataSourceLookup {
			DataSource getDataSource(String dataSourceName) throws DataSourceLookupFailureException;
		}
	
	# 反射，暴力访问: targetDataSources
		Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
		field.setAccessible(true); 
		try {
			Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
		} catch (Exception e) {

		}