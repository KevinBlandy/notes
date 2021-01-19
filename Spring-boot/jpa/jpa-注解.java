--------------------
JPA的注解			|
--------------------
	@EnableJpaRepositories
		String[] value() default {};
		String[] basePackages() default {};
			* 配置 Repository 扫描的包

		Class<?>[] basePackageClasses() default {};
			

		Filter[] includeFilters() default {};
		Filter[] excludeFilters() default {};
		String repositoryImplementationPostfix() default "Impl";
		String namedQueriesLocation() default "";
			* namedQuery存放的位置, 默认: META-INF/jpa-named-queries.properties
				
		Key queryLookupStrategy() default Key.CREATE_IF_NOT_FOUND;
			* 方法的查询策略, 枚举
				CREATE
					* 根据方法名称创建查询, 如果方法名称不符合规则, 启动时则会抛出异常
				USE_DECLARED_QUERY,
					* 声明式创建, 使用 @Query 注解声明JPQL或者SQL
					* 如果启动的时候, 方法没有声明合法的 @Query , 则会抛出异常
				CREATE_IF_NOT_FOUND
					* 综合方式, @Query 优先, 没有query, 就根据方法名称创建检索


		Class<?> repositoryFactoryBeanClass() default JpaRepositoryFactoryBean.class;
			* 设置 Repository 工厂类

		Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;

		String entityManagerFactoryRef() default "entityManagerFactory";
			* 事务管理器的IOC引用

		boolean considerNestedRepositories() default false;
		boolean enableDefaultTransactions() default true;
		BootstrapMode bootstrapMode() default BootstrapMode.DEFAULT;
		char escapeCharacter() default '\\';

	@EntityScan	
		* 配置实体类扫描的包

	@NoRepositoryBean
		* 标识在 Repository 上,表示该接口不是一个Repository
		* 不需要生成动态代理对象

	@Modifying
		* 标识在方法上, 表示该方法是一个更新/删除方法

		boolean flushAutomatically() default false;
		boolean clearAutomatically() default false;
	
