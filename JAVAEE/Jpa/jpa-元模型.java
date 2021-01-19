--------------------------
元模型
--------------------------
	# 用于描述实体类的属性
		* 必须也要被 EntityManager 管理, 跟 Entity 放在同一个包下
		* 跟 Entity 类同名, 最后添加_
		* 规范元模型类的属性全部是 static 和 public 的
	
	# Demo
		* 实体类
			@Entity
			@Table
			public class Employee {  
				private int id;   
				private String name;
				private int age;
				@OneToMany
				private List<Address> addresses;
			}
	
		* 实体类的元模型
			import javax.annotation.Generated;
			import javax.persistence.metamodel.SingularAttribute;
			import javax.persistence.metamodel.ListAttribute;
			import javax.persistence.metamodel.StaticMetamodel;

			@StaticMetamodel(Employee.class)
			public abstract class Employee_ {     
				public static volatile SingularAttribute<Employee, Integer> id;   
				public static volatile SingularAttribute<Employee, Integer> age;   
				public static volatile SingularAttribute<Employee, String> name;    
				public static volatile ListAttribute<Employee, Address> addresses;
			}
	
		* 不能对 @MappedSuperclass 继承的属性设置静态的描述属性


	# 其他的属性描述接口
		SingularAttribute
			* 普通单属性
		CollectionAttribute
		MapAttribute
		SetAttribute
		ListAttribute
			* 一对多/多对多属性

--------------------------
自动生成元模型
--------------------------
	# 自动生成元模型的maven插件
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-jpamodelgen</artifactId>
			<version>5.4.10.Final</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>6.1.0.Final</version>
		</dependency>
		<plugin>
			<artifactId>maven-compiler-plugin</artifactId>
			<configuration>
				<source>1.8</source>
				<target>1.8</target>
				<compilerArguments>
					<processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
				</compilerArguments>
			</configuration>
		</plugin>

