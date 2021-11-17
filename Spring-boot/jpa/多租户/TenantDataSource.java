import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;

public class TenantDataSource extends AbstractRoutingDataSource {
	
	private Map<Object, DataSource> dataSources = null;
	
	@Override
	protected Object determineCurrentLookupKey() {
		return TenantIdHolder.get();
	}
	
	/**
	 * 返回可运行时修改的 resolvedDataSources
	 */
	@Override
	public Map<Object, DataSource> getResolvedDataSources() {
		return this.dataSources;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() {
		
		super.afterPropertiesSet();
		
		/**
		 * 反射读取 resolvedDataSources
		 */
		Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
		field.setAccessible(true); 
		try {
			this.dataSources = (Map<Object, DataSource>) field.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
