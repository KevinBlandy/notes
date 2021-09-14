------------------------
Querydsl				|
------------------------
	# ����
		http://www.querydsl.com/

	
	# ���� & �������
		<!-- https://mvnrepository.com/artifact/com.querydsl/querydsl-jpa -->
		<dependency>
			<groupId>com.querydsl</groupId>
			<artifactId>querydsl-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.querydsl</groupId>
			<artifactId>querydsl-apt</artifactId>
			<scope>provided</scope>
		</dependency>


		<build>
			<plugins>
				<!-- https://mvnrepository.com/artifact/com.mysema.maven/apt-maven-plugin -->
				<plugin>
					<groupId>com.mysema.maven</groupId>
					<artifactId>apt-maven-plugin</artifactId>
					<version>1.1.3</version>
					<executions>
						<execution>
							<goals>
								<goal>process</goal>
							</goals>
							<configuration>
								<outputDirectory>target/generated-sources/java</outputDirectory>
								<processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
							</configuration>
						</execution>
					</executions>
				</plugin>
			</plugins>
		</build>
		
		* ��Ҫ����������jpa
	
	# ���� JPAQueryFactory
		import javax.persistence.EntityManager;

		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;

		import com.querydsl.jpa.impl.JPAQueryFactory;

		@Configuration
		public class JPAQueryFactoryConfiguration {

			@Bean
			public JPAQueryFactory jPAQueryFactory (@PersistenceContext EntityManager entityManager) {
				return new JPAQueryFactory(entityManager);
			}
		}

		* EntityManager ���̰߳�ȫ���⣬����������
	
	# Repository ʵ�ֽӿ�: QuerydslPredicateExecutor
		* ͨ���ýӿ�, �� Repository ��չqueryDsl�ļ���api
	
	# ����Ҫ JPAQueryFactory �ĵط�, ע��
		@Autowired
		JPAQueryFactory jPAQueryFactory

------------------------
mvc ֧��				|
------------------------
	# Spring ����֧�ֽ��� com.querydsl.core.types.Predicate, �����û�����Ĳ����Զ����� Predicate
		@GetMapping("posts")
		public Object posts(@QuerydslPredicate(root = Post.class) Predicate predicate) {
			return postRepository.findAll(predicate);
		}

		// ����˳����Ϸ�ҳ
		@GetMapping("posts")
		public Object posts(@QuerydslPredicate(root = Post.class) Predicate predicate, Pageable pageable) {
			return postRepository.findAll(predicate, pageable);
		}

		/posts?title=title01				// �������� title Ϊ title01 ������
		/posts?id=2							// �������� id Ϊ 2 ������
		/posts?category.name=Python			// ���ط���Ϊ Python �����£�����Ƕ�ף����ʹ�ϵ���и������ԣ�
		/posts?user.id=2&category.name=Java // �����û� ID Ϊ 2 �ҷ���Ϊ Java ������
	


		@QuerydslPredicate
			Class<?> root() default Object.class;
				* ָ������ѯ����

			Class<? extends QuerydslBinderCustomizer> bindings() default QuerydslBinderCustomizer.class;
				* 

		
	# ʵ�� QuerydslBinderCustomizer �Զ��������, �����߼�,
		void customize(QuerydslBindings bindings, T root);

		@Repository
		public interface PostRepository extends JpaRepository<Post, Long>, QueryDslPredicateExecutor<Post>, QuerydslBinderCustomizer<QPost> {
			default void customize(QuerydslBindings bindings, QPost post) {
				bindings.bind(post.title).first(StringExpression::containsIgnoreCase);
			}
		}
