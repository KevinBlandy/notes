----------------------------
几个组件					|
----------------------------
	1,配置文件,可以实现动态的配置
	2,DataSourceAspect				//AOP切面,根据事务注解来标识读/写库
	3,DynamicDataSource				//动态数据源的实现
	4,DynamicDataSourceHolder		//用于标识读/写库
	5,DataSourceConfig				//外部属性注入
	6,DataSourceAutoConfiguration	//配置到IOC

----------------------------
1,配置文件					|
----------------------------
daynamic:
  datasources:
    # master库,唯一只能有一个且名称不可修改
    master:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://127.0.0.1:3306/springcloud?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8
      username: KevinBlandy
      password: 123456

      public-key: 
      initialSize: 2
      maxActive: 50
      filters: stat,config
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000;config.decrypt=true;config.decrypt.key=${daynamic.datasources.master.public-key}

    # slave库,名称唯一可以有多个
    slave_1:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://127.0.0.1:3306/springcloud?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8
      username: KevinBlandy
      password: 123456
    
      public-key: 
      initialSize: 2
      maxActive: 50
      filters: stat,config
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000;config.decrypt=true;config.decrypt.key=${daynamic.datasources.slave_1.public-key}
    slave_2:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://127.0.0.1:3306/springcloud?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8
      username: KevinBlandy
      password: 123456
    
      public-key: 
      initialSize: 2
      maxActive: 50
      filters: stat,config
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000;config.decrypt=true;config.decrypt.key=${daynamic.datasources.slave_2.public-key}

----------------------------
2,DataSourceAspect			|
----------------------------
	import java.lang.reflect.Method;
	import java.lang.reflect.ParameterizedType;
	import java.lang.reflect.Type;

	import org.aspectj.lang.JoinPoint;
	import org.aspectj.lang.annotation.Aspect;
	import org.aspectj.lang.annotation.Before;
	import org.aspectj.lang.annotation.Pointcut;
	import org.aspectj.lang.reflect.MethodSignature;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.core.annotation.Order;
	import org.springframework.stereotype.Component;
	import org.springframework.transaction.annotation.Transactional;

	import io.springcloud.datasource.DynamicDataSourceHolder;

	@Order(-9999)
	@Aspect
	@Component
	public class DaynamicDatasouceAop {
		
		private static final Logger LOGGER = LoggerFactory.getLogger(DaynamicDatasouceAop.class);

		@Pointcut(value = "execution(* io.springcloud.service.*.*(..))")
		public void service() {
		}

		@Before("service()")
		public void beforService(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
			
			Object target = joinPoint.getTarget();
			
			String methodName = joinPoint.getSignature().getName();
			
			Object[] args = joinPoint.getArgs();
			
			Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
			
			Method method = null;
			
			method = target.getClass().getMethod(methodName, parameterTypes);
			
			if (method.isBridge()) {
				for (int i = 0; i < args.length; i++) {
					Class<?> genClazz = getSuperClassGenricType(target.getClass(), 0);
					if (args[i].getClass().isAssignableFrom(genClazz)) {
						parameterTypes[i] = genClazz;
					}
				}
				method = target.getClass().getMethod(methodName, parameterTypes);
			}
			
			LOGGER.debug("当前事务方法  " + methodName);
			
			Transactional transactional = method.getAnnotation(Transactional.class);
			
			if(transactional != null && transactional.readOnly()){
				LOGGER.debug("动态数据源 - 读库");
				DynamicDataSourceHolder.markSlave();
			}else {
				LOGGER.debug("动态数据源 - 写库");
				DynamicDataSourceHolder.markMaster();
			}
		}

		public Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
			Type genType = clazz.getGenericSuperclass();
			if (!(genType instanceof ParameterizedType)) {
				return Object.class;
			}
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class<?>) params[index];
		}
	}

----------------------------
3,DynamicDataSource			|
----------------------------
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
	import org.springframework.util.ReflectionUtils;

	import javax.sql.DataSource;
	import java.lang.reflect.Field;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	import java.util.concurrent.atomic.AtomicInteger;

	/**
	 * Created by KevinBlandy on 2017/10/30 14:05
	 */
	public class DynamicDataSource extends AbstractRoutingDataSource {

		private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

		private AtomicInteger counter = new AtomicInteger(-1);

		private List<Object> slaveDataSources = new ArrayList<>(0);

		@Override
		protected Object determineCurrentLookupKey() {
			Object key = null;
			if (DynamicDataSourceHolder.isMaster() || this.slaveDataSources.isEmpty()) {
				key = DynamicDataSourceHolder.MASTER;
			} else {
				key = this.getSlaveKey();
			}
			LOGGER.debug("动态数据源 dataSourceKey = {}", key);
			return key;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void afterPropertiesSet() {
			super.afterPropertiesSet();
			Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
			field.setAccessible(true); 
			try {
				Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
				for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {
					if (DynamicDataSourceHolder.MASTER.equals(entry.getKey())) {
						continue;
					}
					slaveDataSources.add(entry.getKey());
				}
			} catch (Exception e) {
				LOGGER.error("afterPropertiesSet error! ", e);
			}
		}

		public Object getSlaveKey() {
			Integer index = counter.incrementAndGet() % this.slaveDataSources.size();
			if (counter.get() > 9999) {
				counter.set(-1);
			}
			return slaveDataSources.get(index);
		}
	}


----------------------------
3,DynamicDataSourceHolder	|
----------------------------
	public class DynamicDataSourceHolder {

		public static final String MASTER = "master";

		private static final String SLAVE = "slave";

		private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

		private static void putDataSourceKey(String key) {
			HOLDER.set(key);
		}

		public static String getDataSourceKey() {
			return HOLDER.get();
		}
		
		public static void markMaster() {
			putDataSourceKey(MASTER);
		}

		public static void markSlave() {
			putDataSourceKey(SLAVE);
		}

		public static boolean isMaster() {
			return MASTER.equals(HOLDER.get());
		}
	}


----------------------------
4,DataSourceConfig			|
----------------------------
	import java.util.HashMap;
	import java.util.Map;

	import org.springframework.boot.context.properties.ConfigurationProperties;

	import com.alibaba.druid.pool.DruidDataSource;


	@ConfigurationProperties(prefix = "daynamic")
	public class DynamicDataSourceConfig {
		
		private Map<String,DruidDataSource> datasources = new HashMap<>();
		
		public Map<String,DruidDataSource> getDatasources() {
			return datasources;
		}

		public void setDatasources(Map<String,DruidDataSource> datasources) {
			this.datasources = datasources;
		}
	}



----------------------------
5,DataSourceAutoConfiguration|
----------------------------
	import java.sql.SQLException;
	import java.util.HashMap;
	import java.util.Map;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.context.properties.EnableConfigurationProperties;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	import com.alibaba.druid.pool.DruidDataSource;

	import io.springcloud.datasource.DynamicDataSource;
	import io.springcloud.datasource.DynamicDataSourceConfig;
	import io.springcloud.datasource.DynamicDataSourceHolder;

	/**
	 * 
	 * 读写分离数据源
	 * @author KevinBlandy
	 *
	 */

	@Configuration
	@EnableConfigurationProperties(DynamicDataSourceConfig.class)
	public class DynamicDataSourceConfiguration {
		
		private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceConfiguration.class);
		
		@Autowired
		private DynamicDataSourceConfig dynamicDataSourceConfig;
		
		@Bean
		public DynamicDataSource dynamicDataSource() throws SQLException {
			
			DynamicDataSource dynamicDataSource = new DynamicDataSource();
			
			Map<Object, Object> dataSources = new HashMap<>();
			
			for(Map.Entry<String, DruidDataSource> entry : dynamicDataSourceConfig.getDatasources().entrySet()) {
				
				String key = entry.getKey();
				
				DruidDataSource dataSource = entry.getValue();
				
				if(!key.equals(DynamicDataSourceHolder.MASTER)) {
					dataSources.put(key, dataSource);
				}
				
				dataSource.init();
				
				LOGGER.info("数据源初始化:key={}",key);
			}
			
			DruidDataSource masterDataSource = dynamicDataSourceConfig.getDatasources().get(DynamicDataSourceHolder.MASTER);
			
			if(masterDataSource == null) {
				throw new IllegalArgumentException("未定义,master库(" + DynamicDataSourceHolder.MASTER + ")");
			}
			
			dataSources.put(DynamicDataSourceHolder.MASTER, masterDataSource);
			
			dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
			dynamicDataSource.setTargetDataSources(dataSources);
			
			return dynamicDataSource;
		}
	}

