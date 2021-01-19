import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 动态数据源，实现通过集成Spring提供的AbstractRoutingDataSource(DataSource实现),只需要实现determineCurrentLookupKey方法即可
 * 由于DynamicDataSource是单例的,线程不安全的，所以采用ThreadLocal保证线程安全,由DynamicDataSourceHolder完成
 * */
public class DynamicDataSource extends AbstractRoutingDataSource{
	/**
	 * 该方法的返回值,会作为一个Map<String,DataSource>中的key,去获取对应的数据源.
	 * 所以,每次请求.只要改变这个key,就可以改变档次请求,在持久层会话中注入的数据源,实现自动的读写分离
	 * */
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSourceKey();
	}
}
