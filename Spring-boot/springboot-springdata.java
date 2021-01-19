---------------------------
Spring boot - Spring Data	|
---------------------------
	# Spring Data 是一个简化数据库访问,并'支持云服务器的开源框架'
	# 依赖
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
	
	# Spring Boot 的支持
		* spring-boot-starter-data-jpa 依赖于 spring-boot-starter-jdbc
		* JDBC
			关于自动配置的源码在:org.springframework.boot.autoconfigure.jdbc 包下
			从源码可以看出,关于dataSource的属性配置前缀为: spring.datasource,
			spring 自动开始了注解事务的支持 @EnableTransactionManagement
		* JPA
			关于对JPA的自动配置在:org.springframework.boot.autoconfigure.orm 下
			'spring boot默认的JPA实现是 Hibernate'
			在properties中配置JPA的属性,是以: spring.jpa 为前缀
		
		* Spring Data Jpa
			对于Spring Data JPA 的自动配置在 org.springframework.boot.autoconfigure.data.jpa下
			会扫描所有标注 @Entity 的实体类
			

	# 主要功能
		* 访问数据库变得简单
		* 支持基于关系形数据库的数据服务,如Oracle RAC等
		* 支持map-reduce框架和云计算数据服务

	# 提供的 持久层接口关系
		Repository
			|
		CrudRepository
			|
		PagingAndSortingRepository
			|
		JpaRepository
	
	# 定义数据访问层的两种方法
		1,自定义接口,实现接口: JpaRepository<T, ID extends Serializable>
			public interface UserRepository extends JpaRepository<User,Integer>

		2,配置使用 Spring Data Jpa
			* 在spring环境中,使用SpringDataJPA可以通过 @EnableJpaRepositories 注解来开启 SpringDataJPA的支持
			* @EnableJpaRepositories 的 value 参数,定义respository接口所在的包

			@Configuration
			@EnableJpaRepositories(value="com.levin.repository")
			public class JpaConfiguration{
				@Bean
				public EntityManagerFactory entityManagerFactory(){
					//
				}	
				//还需要配置 DataSource,PlatformTransactionManager等相关Bean
			}
	
	# 定义查询方法
		public interface UserRepository extends JpaRepository<User,Integer>{
			/*
				根据名称检索出一堆数据,参数为name
				JPQL:FROM User WHERE name=:name
			*/
			List<User> findByName(String name);
			/*
				根据LIKE查询,参数为 name
				JPQL:FROM User WHERE name LIKE :name
			*/
			List<User> findByNameLike(String name);
			/*
				根据名称和年龄检索记录
				JPAL:FROM User WHERE name = :name AND age = :age
			*/
			List<User> findByNameWithAge(String name,int age);
			
			/*
				也可以使用@Query注解,来自定义更为高级的检索
			*/
			@Query("FROM User WHERE userName = :userName")
			public User queryByUserName(@Param(value="userName")String userName);
				
			/*
				获取符合条件的前10条记录
			*/
			List<User> findFirst10ByName(String name);

			/*
				获取符合条件的前30条数据
			*/
			List<User> findTop30ByName(String name);
		}
		* 查询方法,有很多的规则,例如: or,and,between....需要的时候,去百度
	
	# 注解
		@Query
			* 标识在某个 repository 的方法上,用于定义HQL语句
			* 可以是 INSERT,CREATE,UPDATE,DELETE 语句

		@Modifying
			* 和 @Query 组合使用
			* 标识在某个 repository 的方法上,表示当前的 @Query 是一个UPDATE 语句
			* 该方法返回的 int 值标签受到影响的行数

		@NamedQuery
			* 标识在 Entity 上
			* name	Sring类型的属性,用于指定检索名,例如 "User.findByName"
			* query	String类书的属性,用于HQL,例如 "FROM User WHERE name = :name"
			* 在该 Entity 的接口中定义的 findByName 方法,就是通过 query 属性的HQL来进行检索的

---------------------------
Spring boot JPA注解			|
---------------------------
	@Entity
		* 当系统检测到有 @Entity 注解标识的POJO,会在数据库中生成对应的表结构信息
		* 默认的字段名,是根据驼峰命名的

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
		* 标识ID字段,并且指定其生成策略
			GenerationType.TABLE		使用一个特定的数据库表格来保存主键。 
			GenerationType.SEQUENCE		根据底层数据库的序列来生成主键，条件是数据库支持序列。 
			GenerationType.IDENTITY		主键由数据库自动生成（主要是自动增长型） 
			GenerationType.AUTO			主键由程序控制
	

	@Column
		* 标识在字段
		* 属性
			name		//指定数据库字段的名称
---------------------------
Spring boot 配置			|
---------------------------
	spring.datasource.url=jdbc:mysql://localhost:3306/community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true
	spring.datasource.username=root
	spring.datasource.password=root
	spring.datasource.driver-class-name=com.mysql.jdbc.Driver
	spring.datasource.max-active=20
	spring.datasource.max-idle=8
	spring.datasource.min-idle=8
	spring.datasource.initial-size=10


	spring.jpa.database=mysql
	spring.jpa.show-sql=true
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.hibernate.naming.strategy=org.hibernate.cfg.DefaultComponentSafeNamingStrategy
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

---------------------------
Spring boot 最顶层接口		|
---------------------------

	public interface Repository<T, ID extends Serializable>
		* 最顶层的数据库访问接口,没有任何实现
		* 我们自定义的接口一旦实现了该接口,就会被IOC识别为一个 RepositoryBean 纳入到IOC中
		* 也可以使用 @RepositoryDefinition 注解来代替 Repository 接口
		* 涉及查询条件时,条件的属性用条件关键字连接,要注意的是条件属性的首字母,是大写
			> findByUserName(String userName);		//会自动的根据 userName来检索出一条记录

		* 使用 @Query 注解,可以自定义JPQL(HQL类似)语句实现更灵活的查询
			@Query("FROM User WHERE userName = :userName")
			public User queryByUserName(@Param(value="userName")String userName);
				
			@Query(value = "FROM User")
			public List<User> queryAll();

---------------------------
Spring boot 子接口			|
---------------------------
	@NoRepositoryBean
	public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {
		//保存单个实体
		<S extends T> S save(S var1);
		//保存集合
		<S extends T> Iterable<S> save(Iterable<S> var1);
		//根据ID检索实体
		T findOne(ID var1);
		//根据ID判断实体是否存在
		boolean exists(ID var1);
		//检索出所有的实体,慎用
		Iterable<T> findAll();
		Iterable<T> findAll(Iterable<ID> var1);
		//检索出实体的数量
		long count();
		//根据ID删除实体
		void delete(ID var1);
		//根据实体删除实体
		void delete(T var1);
		//删除一个实体的集合
		void delete(Iterable<? extends T> var1);
		//删除所有实体
		void deleteAll();
	}

---------------------------
Spring boot 分页排序接口	|
---------------------------
	@NoRepositoryBean
	public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
		//可以实现排序的检索
		Iterable<T> findAll(Sort var1);
		//可以实现分页以及排序的检索
		Page<T> findAll(Pageable var1);
	}
	
	# Sort
		new Sort(Direction.ASC,"age");
		
	# Pageable
		* PageaRequest 是 Pageable 子类
			new PageaRequest(1,20);
		
---------------------------
Spring boot 直接继承接口	|
---------------------------

	@NoRepositoryBean
	public interface JpaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {
		List<T> findAll();

		List<T> findAll(Sort var1);

		List<T> findAll(Iterable<ID> var1);

		<S extends T> List<S> save(Iterable<S> var1);

		void flush();

		<S extends T> S saveAndFlush(S var1);

		void deleteInBatch(Iterable<T> var1);

		void deleteAllInBatch();

		T getOne(ID var1);

		<S extends T> List<S> findAll(Example<S> var1);

		<S extends T> List<S> findAll(Example<S> var1, Sort var2);
	}

