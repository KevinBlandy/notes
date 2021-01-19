-----------------------
jpa
-----------------------
	# JavaEE体系中的技术之一
		<!-- JPA规范接口 -->
		<dependency>
			<groupId>javax.persistence</groupId>
			<artifactId>javax.persistence-api</artifactId>
			<version>2.2</version>
		</dependency>
	
		<!-- junit -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>

		<!-- hibernate对jpa的支持包 -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>${project.hibernate.version}</version>
		</dependency>

		<!-- 连接池 -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-c3p0</artifactId>
			<version>${project.hibernate.version}</version>
		</dependency>

		<!-- log日志 -->
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>

		<!-- jdbc -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.6</version>
		</dependency>
	
	# JPA的操作步骤
		1.加载配置文件创建实体管理器工厂
			Persisitence：静态方法（根据持久化单元名称创建实体管理器工厂）
				createEntityMnagerFactory（持久化单元名称）

			作用：创建实体管理器工厂
			
		2.根据实体管理器工厂，创建实体管理器
			EntityManagerFactory ：获取EntityManager对象
			方法：createEntityManager
			* 内部维护的很多的内容
				内部维护了数据库信息，
				维护了缓存信息
				维护了所有的实体管理器对象
				再创建EntityManagerFactory的过程中会根据配置创建数据库表
			* EntityManagerFactory的创建过程比较浪费资源

			特点：线程安全的对象
				多个线程访问同一个EntityManagerFactory不会有线程安全问题
			* 如何解决EntityManagerFactory的创建过程浪费资源（耗时）的问题？
			思路：创建一个公共的EntityManagerFactory的对象
			* 静态代码块的形式创建EntityManagerFactory
			
		3.创建事务对象，开启事务
			EntityManager对象：实体类管理器
				beginTransaction : 创建事务对象
				presist ： 保存
				merge  ： 更新
				remove ： 删除
				find/getRefrence ： 根据id查询
				
			Transaction 对象 ： 事务
				begin：开启事务
				commit：提交事务
				rollback：回滚
		
		4.增删改查操作
		5.提交事务
		6.释放资源
	
	# 代码执行步骤
		// 加载 META-INF/persistence.xml, 根据 persistence-unit 创建 EntityManagerFactory
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
		// 获取到实体管理器
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		// 创建事务管理器
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		// 开始事务
		entityTransaction.begin(); 
		try {
			
			// 执行业务操作
			User user = new User();
			user.setName("KevinBlandy");
			user.setVersion(1);
			
			entityManager.persist(user);
			
			// 提交事务
			entityTransaction.commit();
		}catch (Exception e) {
			// 回滚事务
			entityTransaction.rollback();
			throw e;
		}finally {
			// 释放资源
			entityManager.close();
			entityManagerFactory.close();
		}