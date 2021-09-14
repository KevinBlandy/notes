------------------------
Querydsl				|
------------------------
	# 官网
		http://www.querydsl.com/

	
	# 依赖 & 插件配置
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
		
		* 需要先配置整合jpa
	
	# 配置 JPAQueryFactory
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

		* EntityManager 有线程安全问题，不能这样用
	
	# Repository 实现接口: QuerydslPredicateExecutor
		* 通过该接口, 给 Repository 扩展queryDsl的检索api
	
	# 在需要 JPAQueryFactory 的地方, 注入
		@Autowired
		JPAQueryFactory jPAQueryFactory

------------------------
mvc 支持				|
------------------------
	# Spring 参数支持解析 com.querydsl.core.types.Predicate, 根据用户请求的参数自动生成 Predicate
		@GetMapping("posts")
		public Object posts(@QuerydslPredicate(root = Post.class) Predicate predicate) {
			return postRepository.findAll(predicate);
		}

		// 或者顺便加上分页
		@GetMapping("posts")
		public Object posts(@QuerydslPredicate(root = Post.class) Predicate predicate, Pageable pageable) {
			return postRepository.findAll(predicate, pageable);
		}

		/posts?title=title01				// 返回文章 title 为 title01 的文章
		/posts?id=2							// 返回文章 id 为 2 的文章
		/posts?category.name=Python			// 返回分类为 Python 的文章（可以嵌套，访问关系表中父类属性）
		/posts?user.id=2&category.name=Java // 返回用户 ID 为 2 且分类为 Java 的文章
	


		@QuerydslPredicate
			Class<?> root() default Object.class;
				* 指定根查询对象

			Class<? extends QuerydslBinderCustomizer> bindings() default QuerydslBinderCustomizer.class;
				* 

		
	# 实现 QuerydslBinderCustomizer 自定义参数绑定, 检索逻辑,
		void customize(QuerydslBindings bindings, T root);

		@Repository
		public interface PostRepository extends JpaRepository<Post, Long>, QueryDslPredicateExecutor<Post>, QuerydslBinderCustomizer<QPost> {
			default void customize(QuerydslBindings bindings, QPost post) {
				bindings.bind(post.title).first(StringExpression::containsIgnoreCase);
			}
		}
