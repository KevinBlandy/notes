import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class TenantDataSourceConfiguration {
	
	/**
	 * 主数据库
	 * @param dataSourceProperties
	 * @return
	 */
	@Bean(destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariDataSource hikariDataSource(DataSourceProperties dataSourceProperties){
		HikariDataSource hikariDataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		if (StringUtils.hasText(dataSourceProperties.getName())){
			hikariDataSource.setPoolName(dataSourceProperties.getName());
		}
		return hikariDataSource;
	}
	
	/**
	 * 动态租户数据源
	 * @param dataSource
	 * @return
	 */
	@Bean
	@Primary
	public TenantDataSource tenantDataSource(@Autowired HikariDataSource dataSource){
		TenantDataSource tenantDataSource = new TenantDataSource();
		tenantDataSource.setTargetDataSources(Collections.singletonMap("main", dataSource));  // TargetDataSources 不能为空
		tenantDataSource.setDefaultTargetDataSource(dataSource);
		return tenantDataSource;
	}
}
