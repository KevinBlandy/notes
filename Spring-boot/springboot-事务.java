---------------------------
Spring-boot 事务			|
---------------------------
	# spring 的事务机制提供了一个统一的接口
		PlatformTransactionManager 
	
	# 接口的实现
		JDBC		-->		DataSourceTransactionManager		//同样还适合MyBatis
		JPA			-->		JpaTransactionManager				//JPA
		Hibernate	-->		HibernateTransactionManager			//适合Hibernate3.0
		JDO			-->		JdoTransactionManager
		分布式事务	-->		JtaTransactionManager				//JTA

	# 在程序中定义事务管理器
		@Bean
		public PlatformTransactionManager platformTransactionManager(){
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
			dataSourceTransactionManager.setDataSource(dataSource());
			return dataSourceTransactionManager;
		}
	
	# 声明式事务
		* spring 支持声明式事务.使用注解 @Transactional
		* 该注解的详细属性,看spring的资料
	
	# 启用声明式事务
		* spring提了一个注解 @EnableTransactionManagement
		* 注解在配置陪上,来开枪声明式事务的支持
		* 使用该注解后,spring容器会自动扫描注解 @Transactional 的方法和类
			@Configuration
			@EnableTransactionManagement
			public class transactionManagementAutoConfiguration		{
			}
		* 需要引入依赖
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-tx</artifactId>
			</dependency>

		* 该注解可以标识在方法上
		* 也可以标识在类上,那么该类中所有的 public 方法,都会有事务
		
	# spring boot的事务支持
		* 当使用JDBC作为数据访问技术的时候,spring boot为我们定义了 PlatformTransactionManager 的实现 DataSourceTransactionManager,源码:DataSourceTransactionManagerAutoConfiguration
		* 当使用JPA....
		* spring boot专门用户配置事务的类是:org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
		* 'spring boot中不需要显示开启使用'@EnableTransactionManagement 注解,'直接在哪里标注'
	
		* 添加依赖
			 <dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-tx</artifactId>
			</dependency>
		
		* 添加注解
			@EnableTransactionManagement
	
	# 使用监听器, 监听事务的状态
		* 使用:@TransactionalEventListener 注解, 标识在监听方法
			@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
			@Retention(RetentionPolicy.RUNTIME)
			@Documented
			@EventListener
			public @interface TransactionalEventListener {

				TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

				boolean fallbackExecution() default false;

				@AliasFor(annotation = EventListener.class, attribute = "classes")
				Class<?>[] value() default {};

				 */
				@AliasFor(annotation = EventListener.class, attribute = "classes")
				Class<?>[] classes() default {};

				String condition() default "";

			}
			
			* phase 枚举, 表示感兴趣的事件
				BEFORE_COMMIT,
				AFTER_COMMIT,
				AFTER_ROLLBACK,
				AFTER_COMPLETION
			
			* fallbackExecution 
