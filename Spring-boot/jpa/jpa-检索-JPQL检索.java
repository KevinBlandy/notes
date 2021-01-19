----------------
JPQL检索
----------------
	# 参数的绑定
		* 通过位置绑定	WHERE id = ?1
		* 通过名称绑定	WHERE id = :id
	
	# JPQL的修改要在事务下进行否则可能异常: InvalidDataAccessApiUsageException
		 Executing an update/delete query; nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query

	# 抽象方法返回值支持使用 Optional 封装

	
	
-------------------
JPQL动态更新
-------------------
	@DynamicUpdate
		* 标识在实体类上
		* 比较更新要使用的实体类中的字段值与从数据库中查询出来的字段值, 判断其是否有修改
		* 通俗理解就是, 把数据库中的记录查出来, 跟这个对象的值进行对比, 仅仅更新有修改字段

	@DynamicInsert
		* 标识在实体类上
		* 在执行插入的时候, 仅仅根据对实体的非null字段生成SQL语句
	


----------------
JPQL检索语法
----------------
	# 语法
		* 操作的是对象 @Entity, 不是表, 操作的是对象属性, 也不是字段
		* 默认Entity名称就是类名称大写, 也可以通过修改 @Entity 属性的注解来修改

		* 不支持使用 `` 符号, 不支持在末尾添加分号: ;
		* 支持使用 AS 关键字起别名

	
	# LIKE
		FROM User u WHERE u.name LIKE %:name%
	
	# IN
		FROM User u WHERE u.id in :ids
			* ids是一个集合
	
	# COUNT
		SELECT COUNT(1) FROM User AS u WHERE u.name LIKE %:#{#user.name}%
	
	# ORDER BY
		FROM User u WHERE u.id = :id ORDER BY id DESC
	
	# INSERT INTO 
		//TODO
	
	# 使用 new 关键字来创建指定类型的返回对象
		SELECT new io.springboot.jpa.domain.model.UserModel(u.name) FROM User AS u WHERE u.id = :id
		
			* 通过构造方法, 来创建对象
			* 如果对象是被EntityManager管理的, 可以只写其类名, 否则需要声明完整的类路径
			* 通过这种方式可以做到: 仅仅只检索部分字段
		
	# 使用Map作为检索结果

		SELECT new map(u.name AS name, u.id AS id) FROM User AS u WHERE id = :id
			* 需要对检索的列, 使用AS起别名, 作为key的名称
			* 多行多列返回的结果类型是: List<Map<String, Object>> (任何情况都适用)
			* 单行多列返回的结果类型是: Map<String, Object>

			* 如果不设置别名, 单行单列的情况下, key = null, 多行的情况下, key = 序号(从0开始)
				{
					"0":"Kevin",
					"1":1
				}
	
	# 仅仅检索部分字段
		SELECT u.name, u.id FROM User AS u
			* 返回 List<Object[]>
			* 上述的 new 对象方法和使用Map作为结果集也可以完成
	
	
	# 仅仅检索单个字段
		SELECT u.name FROM User AS u WHERE u.id = :id

			* 返回值类型, 就根据字段的类型定义
	
	# 检索单个/多个字段的总结
		* 单个字段单条记录时, 返回类型最好用对应字段的类型或者 Object。
		* 单个子弹多条记录时, 返回类型最好用 List<Object> 或者 List<字段对应类型>
		* 多个字段时, 不论是多条记录还是单条记录, 返回类型都应该是 List<Object[]>
	

	# 定义常量属性
		SELECT new UserModel('unknow' AS name, u.id) FROM User AS u
			
			* 通过 默认值 AS 属性 定义

----------------
JPQL检索函数
----------------
	LENGTH()
		* 计算长度: @Query("SELECT LENGTH(u.name) AS length FROM User AS u WHERE id = :id")

----------------
JPQL锁
----------------
	# 在执行方法添加注解: @Lock

	# 设置锁的超时时间
		@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
	
	# 动态加锁
		//TODO

----------------
JPQL检索CAS更新
----------------
	//TODO
	
----------------
SPEL
----------------
	# 支持使用spel表达式进行对象属性的导航
		@Query(value = "FROM user u WHERE u.id = :#{#user.id}")
		Page<UserEntity> testSelect (@Param("user")UserEntity userEntity, Pageable pageable);
	
	# 支持使用SPEL表达式读取某些特殊的变量
		#{#entityName}
			
			* 读取到entity实体的名称, 默认是实体名称的小写
			* 也可以在 @Entity(name = "user")中, 通过name属性设置

			@Entity
			class User

			@Query("select u from #{#entityName} u where u.lastname = ?1")
			List<User> findByLastname(String lastname);

				

----------------
JPQL检索注解
----------------
	Query
		String value() default "";
			* 执行语句, 可以是CREUD, 可以是本地SQL, JPQL

		String countQuery() default "";
			* 查询总数量的语句
			* 方法最后一个参数是 Pageable , 返回值是: Page<T>
				@Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1",
					countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
					nativeQuery = true)
				Page<User> findByLastname(String lastname, Pageable pageable);

		String countProjection() default "";
			* 根据哪个字段计算count

		boolean nativeQuery() default false;
			* 是否是本地SQL查询
			* 本地查询, 不支持使用动态的排序: Sort api
			* 本地查询, 不直接支持 Pageable 自动检索总记录数, 需要自己通过 countQuery, 属性设置统计查询

		String name() default "";
			* 指定当前query的名称, 必须唯一
			* 默认: ${domainClass}.${queryMethodName}

		String countName() default "";
			* 指定一个 countQuery 的名称, 必须唯一
			* 默认: ${domainClass}.${queryMethodName}.count

	
	@Param
		* 通过命名参数的绑定 , 可以忽略参数的位置
			@Query(value = "select * from #{#entityName} where id > :id", nativeQuery = true)
			Page<UserEntity> testSelect (@Param("id")Integer id, Pageable pageable);

		* 如果在JDK8中保留了参数名称, 可以省略该注解
	
	
	@Modifying
		boolean flushAutomatically() default false;
		boolean clearAutomatically() default false;
			* 如果配置了一级缓存, 并且该属性=true, 更新操作, 就会立即刷新一级缓存
			* 如果设置为 false, 可能会导致一个问题, 在同一个接口中, 更新一个对象, 再次查询, 可能读取到的是缓存中没刷新的对象

		* 和 @Query 组合使用
		* 标识在某个 repository 的方法上,表示当前的 @Query 是一个UPDATE 语句
		* 该方法返回的 int 值标签受到影响的行数

	@NamedQuery
		String name();
		String query();
		LockModeType lockMode() default NONE;
		QueryHint[] hints() default {};

		* 标识在 Entity 上
		* name	Sring类型的属性,用于指定检索名,例如 "User.findByName"
		* query	String类书的属性,用于HQL,例如 "FROM User WHERE name = :name"
		* 在该 Entity 的接口中定义的 findByName 方法,就是通过 query 属性的HQL来进行检索的

	@QueryHints
		QueryHint[] value() default {};
		boolean forCounting() default true;
		
		* QueryHint 就俩属性
			String name(); 
			String value();

		* 很多数据库都支持 query hints 语法(逐渐被淘汰)
		* 可以配置的选项都在: QueryHints 类中定义
	
	@Lock
		LockModeType value();

		* 加锁模式
		* LockModeType 枚举
			READ,
			WRITE,
			OPTIMISTIC,
			OPTIMISTIC_FORCE_INCREMENT,
			PESSIMISTIC_READ,					Lock In Share Mode
			PESSIMISTIC_WRITE,					FOR UPDATE
			PESSIMISTIC_FORCE_INCREMENT,
			NONE

	@Procedure
		String value() default "";
		String procedureName() default "";
			* 指定数据库存储过程的名称
		String name() default "";
			* 指定 @NamedStoredProcedureQuery 中的 String name(); 的存储过程名称
		String outputParameterName() default "";
			* 输出参数的名字


		* 调用存储过程, procedureName 和 name 可以只存在一个

----------------
奇奇怪怪
----------------
	# dtype字段
		* 因为继承关系导致出现的字段
		* 因为有对象继承了entity, 并且在在对象上声明了 @Entity 注解
	
	
