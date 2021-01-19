----------------------------
整合spring - xml配置		|
----------------------------
	<!-- 数据源 -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName">com.mysql.jdbc.Driver</property>
		<property name="url">jdbc:mysql://localhost:3306/testDB</property>
		<property name="username">user</property>
		<property name="password">pass</property>
	</bean>

	<!-- 开启事务的注解驱动 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>

	<!-- 注册事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>

	<!-- 注册Sql2o -->
	<bean id="sql2o" class="org.sql2o.Sql2o">
		<!-- 注入 dataSource -->
		<constructor-arg ref="dataSource"/>
	</bean>

----------------------------
整合spring - 代码配置		|
----------------------------
	// 实现 TransactionManagementConfigurer 接口
	@Configuration
	@EnableTransactionManagement
	public class DatabaseContext implements TransactionManagementConfigurer {

		// 注册数据源
	   @Bean
	   public DataSource dataSource() {
		  final BasicDataSource dataSource = new BasicDataSource();
		  dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		  dataSource.setUrl("jdbc:mysql://localhost:3306/testDB");
		  dataSource.setUsername("user");
		  dataSource.setPassword("pass");
	   }

		// 覆写抽象方法,注册事务管理器
	   @Bean
	   @Override
	   public PlatformTransactionManager annotationDrivenTransactionManager() {
		  return new DataSourceTransactionManager(this.dataSource());
	   }

		// 注册sql2o
	   @Bean
	   public Sql2o sql2o() {
		  return new Sql2o(this.dataSource());
	   }
	}

----------------------------
整合spring - 注入使用		|
----------------------------
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Repository;

	@Repository
	public class UserDAO {

		@Autowired
		private Sql2o sql2o;

		public User getById(Integer userId){

			String sql = "SELECT * FROM `user` WHERE `id` = :id";

			Connection connection = sql2o.open();

			User user = connection.createQuery(sql)
					.addParameter("id",userId)
					.executeAndFetchFirst(User.class);

			return user;
		}
	}