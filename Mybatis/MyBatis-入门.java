
————————————————————————
1,MyBatis介绍			|
————————————————————————
	1,持久层框架
	2,消除了几乎所有的JDBC代码和参数的收工设置,以及对结果的检索封装
	3,可以使用简单的'xml'或者'注解',用于配制和原始映射,把JAVA中的普通POJO映射成数据库中的记录
	4,与一些框架的关系
		JDBC --> DButils --> MyBatis --> Hibernate
	5,它并非真正意义上的ORM框架,需要开发者自己去编写SQL语句
	6,核心知识
		* 输入(参数)映射
		* 输出(结果)映射
		* 动态SQL
	7,MyBatis开发DAO的两种方法
		* 原始dao方法(需要程序编写dao接口以及dao实现类)
		* Mybatis的 mapper 代理开发方法(只需要写dao接口)

————————————————————————
2,MyBatis目录结构		|
————————————————————————
	mybatis-3.2.7.jar
		* 核心jar包 -- > '必须'

————————————————————————
3,MyBatis入门操作		|
————————————————————————
	SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	//从classpath读取配置文件(InputStream),创建会话工厂
	SqlSessionFactory sessionFactory = builder.build(Resources.getResourceAsStream("mybatis/SqlMapConfig.xml"));
	//通过工厂得到SqlSession
	SqlSession session = sessionFactory.openSession();
	/* 第一个参数:命名空间.mappedstatementid
	 * 第二个参数:指定和映射文件中,所匹配的参数的值.
	 */
	User user = session.selectOne("User.findById","43BCB463B6374BD5960FBFB917D0E2F7");
	System.out.println(user);
	//关闭资源
	session.close();

	
	// sql session有多个API可以获取到 sqlSession实例

	SqlSession openSession(boolean autoCommit);
		* 是否自动提交事务
	SqlSession openSession(Connection connection);
		* 从指定的Connection创建session
	SqlSession openSession(TransactionIsolationLevel level);
		* 事务隔离级别
	SqlSession openSession(ExecutorType execType,TransactionIsolationLevel level)
		* 执行类型,和事务隔离级别
	SqlSession openSession(ExecutorType execType);
		* 执行类型
	SqlSession openSession(ExecutorType execType, boolean autoCommit);
		* 执行类型
	SqlSession openSession(ExecutorType execType, Connection connection);
		* 执行类型
	
	ExecutorType 执行类型,是一个枚举
		SIMPLE, 
		REUSE, 
		BATCH(获取一个批处理的Session)

————————————————————————
4,MyBatis XML配置约束	|
————————————————————————
	1,核心配置文件约束 & 命名空间
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
			"http://mybatis.org/dtd/mybatis-3-config.dtd">
		<configuration>
		</configuration>
	2,映射文件约束 & 命名空间
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
			"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<mapper namespace="User">
		</mapper>

————————————————————————
5,MyBatis SQL映射占位符	|
————————————————————————
	1,#{}表示一个占位符,#{}接收输入参数,类型可以是简单类型,POJO,HashMap
		* 如果是简单数据类型,那么#{}中可以写成value或者其他名称
		* 如果是POJO,那么必须是POJO的属性名称

	2,${}表示一个拼接符号,使用不慎会导致SQL注入,不建议使用
		* 会把${}中的参数,不加任何修饰的添加到SQL语句
		* 它也可以接收很多类型,但是如果他接收的类型是简单数据类型的话
		* '简单数据类型,${value,名称必须是value}'

	3,${},#{}.他们都是通过OGNL来读取对象中的属性值
		* 属性.属性.属性... ...

————————————————————————
6,MyBatis日志处理		|
————————————————————————
	1,打印SQL语句,显示状态等功能
	2,lo4j.properties配置
		# Global logging configuration
		#\u5728\u5f00\u53d1\u73af\u5883\u4e0b\u65e5\u5fd7\u7ea7\u522b\u8981\u8bbe\u7f6e\u6210DEBUG\uff0c\u751f\u4ea7\u73af\u5883\u8bbe\u7f6e\u6210info\u6216error
		log4j.rootLogger=DEBUG, stdout
		# Console output...
		log4j.appender.stdout=org.apache.log4j.ConsoleAppender
		log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
		log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n

		开发环境:DEBUG
		生成环境:info

————————————————————————
7,MyBatis 一些总结		|
————————————————————————
	1,解决数据表字段名称跟POJO属性名不一样
		* 解决方案---起!别!名!
		* 其实mybatis很聪明,一些'长得像'的属性,都会被成功的映射
		* 可以在全局配置文件中添加一个设置:<setting name="mapUnderscoreToCamelCase" value="true" />
		* 开启驼峰命名,数据库的字段,随便大小写都行。POJO的字段,还是按照驼峰规则来
	
	2,ID回填
		* 其实就是插入一个对象,ID是自增长,你想要保存了以后.再获取到这个ID
		* 这个值会反写到你mapper接口的形参中

			<insert id="saveUser" parameterType="User" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
			useGeneratedKeys="true"		//开启ID回填
			keyColumn="id"				//列名称
			keyProperty="id"			//POJO属性名称
	