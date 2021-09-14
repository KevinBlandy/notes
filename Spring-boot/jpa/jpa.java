---------------------
jpa					 |
---------------------
	# �ο�
		https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#reference
		https://ityouknow.gitbooks.io/spring-data-jpa-reference-documentation/content/
		https://www.ibm.com/developerworks/cn/java/j-typesafejpa/
		https://my.oschina.net/zhaoqian/blog/133500
	
	# Maven
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
		<dependency>
			<groupId>javax.annotation</groupId>
			<artifactId>javax.annotation-api</artifactId>
		</dependency>
	
	# ʹ��
		1,�Զ���ӿڼ̳�:JpaRepository �� JpaSpecificationExecutor
			public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor <UserEntity>, QuerydslPredicateExecutor<UserEntity>
			{
			}
			
			* ���ûʹ��querydsl, ��ô���Բ��ü̳�: QuerydslPredicateExecutor �ӿ�

		2,����ɨ��Repository��Entity
			@EnableJpaRepositories(basePackages = {"io.springboot.jpa.repository"})
				basePackages
					* ָ�� Repository ���ڵİ�
				repositoryBaseClass
					* �������ظ�����ָ���� class ������


			@EntityScan(basePackages = {"io.springboot.jpa.dto"})
				* ָ�� Entity ʵ�������ڵİ�

		4,Entit���ע��
			@Entity
			@Table(name = "user")
			@Id
			@Column

			


		5,����Ҫ�ĵط�ע��
			@Autowired
			private UserRepository userRepository;
			
		
	