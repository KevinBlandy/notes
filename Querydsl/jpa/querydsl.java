-------------------
querydsl			|
-------------------
	# Maven
		<dependency>
		    <groupId>com.querydsl</groupId>
		    <artifactId>querydsl-jpa</artifactId>
		    <version>4.2.1</version>
		</dependency>
	
		<dependency>
		    <groupId>com.querydsl</groupId>
		    <artifactId>querydsl-apt</artifactId>
		    <version>4.2.1</version>
		    <scope>provided</scope>
		</dependency>
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


-------------------
查询类的生成	   |
-------------------
	# 需要通过 apt-maven-plugin 插件和querydsl-apt配合来生成检索类
		* 不同Entity注解可以使用不同的生成策略(<processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>)
			com.querydsl.apt.jpa.JPAAnnotationProcessor					//jpa的注解
			com.querydsl.apt.hibernate.HibernateAnnotationProcessor		//Hibernate的注解
		
		* 使用maven命令: mvn compile -DskipTests
		* 会在目录: target/generated-sources/java 生成与entity同包的查询类(类名都添加了Q字符)

	
	# 检索对象类
		* UserEntity 对象生成的检索对象
			@Generated("com.querydsl.codegen.EntitySerializer")
			public class QUserEntity extends EntityPathBase<UserEntity> {
				private static final long serialVersionUID = -1352166679L;
				public static final QUserEntity userEntity = new QUserEntity("userEntity");
				public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);
				public final NumberPath<Integer> id = createNumber("id", Integer.class);
				public final StringPath name = createString("name");
				public QUserEntity(String variable) {
					super(UserEntity.class, forVariable(variable));
				}
				public QUserEntity(Path<? extends UserEntity> path) {
					super(path.getType(), path.getMetadata());
				}
				public QUserEntity(PathMetadata metadata) {
					super(UserEntity.class, metadata);
				}
			}
		
		* 继承: EntityPathBase<T>
		* 具备一个单例的常量属性, 就是Entity类名称首字母小写, 也可以自己根据构造函数创建

			
		
	
-------------------
测试			   |
-------------------
	package io.springboot.jpa.test;

	import io.springboot.jpa.JpaApplication;
	import io.springboot.jpa.domain.entity.QUserEntity;
	import io.springboot.jpa.domain.entity.UserEntity;

	import javax.persistence.EntityManager;

	import org.junit.Test;
	import org.junit.runner.RunWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.test.context.junit4.SpringRunner;

	import com.querydsl.jpa.impl.JPAQueryFactory;

	import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

	// 在springboot的jpa环境中使用
	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = JpaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
	public class JpaApplicationTest {

		// 注入 entityManager
		@Autowired
		private EntityManager entityManager;

		@Test
		public void test() {

			// 从检索对象获取到实例
			QUserEntity qUserEntity = QUserEntity.userEntity;

			// 创建 JPAQueryFactory
			JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
			
			// 使用 jpaQueryFactory 检索
			UserEntity userEntity = (UserEntity) jpaQueryFactory.from(qUserEntity).where(qUserEntity.name.eq("Hello")).fetchOne();

			System.out.println(userEntity);
		}
	}

