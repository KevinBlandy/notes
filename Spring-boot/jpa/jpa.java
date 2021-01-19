---------------------
jpa					 |
---------------------
	# 参考
		https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#reference
		https://ityouknow.gitbooks.io/spring-data-jpa-reference-documentation/content/
		https://www.ibm.com/developerworks/cn/java/j-typesafejpa/
		https://my.oschina.net/zhaoqian/blog/133500
	
	# Maven
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
	
	# 使用
		1,自定义接口继承:JpaRepository 和 JpaSpecificationExecutor
			public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor <UserEntity>, QuerydslPredicateExecutor<UserEntity>
			{
			}
			
			* 如果没使用querydsl, 那么可以不用继承: QuerydslPredicateExecutor 接口

		2,配置扫描Repository和Entity
			@EnableJpaRepositories(basePackages = {"io.springboot.jpa.repository"})
				basePackages
					* 指定 Repository 所在的包
				repositoryBaseClass
					* 仅仅加载该属性指定的 class 的子类


			@EntityScan(basePackages = {"io.springboot.jpa.dto"})
				* 指定 Entity 实体类所在的包

		4,Entit添加注解
			@Entity
			@Table(name = "user")
			@Id
			@Column

			


		5,在需要的地方注入
			@Autowired
			private UserRepository userRepository;
			
		
	