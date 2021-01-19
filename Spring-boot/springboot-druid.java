----------------------------
Druid						|
----------------------------
	1,配置文件
		# 指定数据源类型
		spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
		spring.datasource.driver-class-name=com.mysql.jdbc.Driver
		spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springboot?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&serverTimezone=GMT%2b8
		spring.datasource.username=root
		spring.datasource.password=KevinBlandy_mysql
		
		# 连接池配置,对所有dataSource属性都生效
		spring.datasource.initialSize=10
		spring.datasource.minIdle=5
		spring.datasource.maxActive=20
		spring.datasource.maxWait=60000
		spring.datasource.timeBetweenEvictionRunsMillis=60000
		spring.datasource.minEvictableIdleTimeMillis=300000
		spring.datasource.validationQuery=SELECT 1 FROM DUAL
		spring.datasource.testWhileIdle=true
		spring.datasource.testOnBorrow=false
		spring.datasource.testOnReturn=false
		spring.datasource.poolPreparedStatements=true
		spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
		spring.datasource.filters=stat,wall,log4j
		spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

	3,DataSourceConfiguration
		@Configuration
		public class DataSourceConfiguration {

			@Bean(initMethod = "init")
			@ConfigurationProperties(prefix = "spring.datasource")
			public DataSource druidDataSource() {
				DruidDataSource druidDataSource = new DruidDataSource();
				return druidDataSource;
			}
		}
	
	4,监控与过滤
		
		* 记得在主线程添加,@ServletComponentScan(basePackages = {"com.xx"}) 来扫描 web组件
		* 也可以使用 springboot的web组件注册bean去注册
			ServletRegistrationBean
			FilterRegistrationBean
			ServletListenerRegistrationBean

		@WebServlet(urlPatterns = "/druid/*",initParams={@WebInitParam(name="loginUsername",value="KevinBlandy"), @WebInitParam(name="loginPassword",value="F8575532")})
		public class DruidStatViewServlet extends StatViewServlet {
			private static final long serialVersionUID = -1625661402500921518L;
		}
		
		@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        initParams={@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")})
		public class DruidStatFilter extends WebStatFilter {
		}

----------------------------
starter						|
----------------------------
	# 文档
		https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter

	# 依赖
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>1.1.10</version>
		</dependency>

	# 配置:配置类:DruidStatProperties,DataSourceProperties
spring:
  datasource:
    #　spring提供的通用(常用)配置
    driver-class-name:
    url:
    username:
    password:
    
    # druid配置
    druid:
      # 连接池配置(DruidDataSource实例属性)
      initial-size:
      max-active:
      min-idle:
      max-wait:
      pool-prepared-statements:
      max-pool-prepared-statement-per-connection-size:
      max-open-prepared-statements:
      validation-query:
      validation-query-timeout:
      test-on-borrow:
      test-on-return:
      test-while-idle:
      time-between-eviction-runs-millis:
      min-evictable-idle-time-millis:
      max-evictable-idle-time-millis:
      filters:
      
      # Spring监控AOP切入点(x.y.z.service.*)是一个数组 String[]
      aop-patterns:
      
      # 监控Servlet
      stat-view-servlet:
        enabled:
        url-pattern:
        allow:
        deny:
        login-username:
        login-password:
        reset-enable:
      
      # 监控Filter
      web-stat-filter:
        enabled:
        url-pattern:
        exclusions:
        session-stat-max-count:
        session-stat-enable:
        principal-session-name:
        principal-cookie-name:
        profile-enable:
        
    
    
