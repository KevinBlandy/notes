Date	2016年1月2日 20:27:22
Author	KevinBlandy
————————————————————————————
一,Hibernat入门				|
————————————————————————————
	> Hibernate是一个开源免费的ORM框架,应用于持久层.它对JDBC进行了非常轻量级的对象封装,可以适用于任何场合.WEB,桌面程序...都可以!
	> Hibernate是一个轻量级的JAVAEE应用层解决方案,是一个关系数据库ORM框架
		* ORM就是通过吧java对象映射到数据库,通过操作java对象,就可以完成对数据表的操作
	> Hibernate提供了对关系型数据的增删改错操作
	> 一些主流的数据库框架
		* JPA(Java Persistence API)JPA通过JDK5.0注解,或者XML描述一个对象关系映射(只有接口规范)
		* Hibernate 最流量的ORM框架,通过对象-关系映射配置.可以完全脱离底层SQL
		* MyBatis	本来是Apache的一个开源项目ibatis,支持普通的SQL查询,存储过程和高级映射的优秀持久层框架
		Apache DBUtils,Spring JDBCTemplate ... ...

	> ORM:Object Relational Mapping  对象关系映射
		* 传统方式的开发,需要自己去编写实体类对象,在DAO层,自己去编写一些连接数据库的工作
		* JAVA是面向对象的语言,而数据库是一个关系型数据库!存储的都是实体之间的关系!
	['优点']
		* Hibernate对JDBC访问数据库的代码进行了封装,大大简化了数据库访问层繁琐的重复性代码
		* Hibernate是一个基于JDBC的主流持久化框架,是一个优秀的ORM实现,它很多程度的简化了DAO层编码工作
		* Hibernate使用java的反射机制,而不是字节码增强程序实现透明性
		* Hibernate'性能优异',因为它是一个轻量级框架.映射的灵活性很出色,它支持很多关系型数据库.从一对一,到多对多.的各种复杂关系
	> 目前Hierbate 已经更新到5.x版本!
	> 这里主要讲3.X!因为版本过高,有些地方不向下兼容!如果是Hiernate4.x版本以上的话,在跟Spring整合的时候,对Spring也要求更高的版本!

————————————————————————————
二,环境搭建					|
————————————————————————————
['目录结构']
	documentation	 Hibernate帮助文档
	lib				 Hibernate开发jar包
		bytecode	--> 操作字节码上使用的一个jar包
		jpa			--> Hibernate的实现JPA的规范的一个jar包
		optional	--> 可选jar包
		required	--> 核心jarg包,必须
	project			测试工程
	hibernate3.jar	核心jar包

	> 下载这东西也要我给你说的话,有点碾压智商了
	> 导入必须的jar包
		* hibernate3
		* required目录下的所有jar包
		* jpa目录下的所有jar包
		* 数据库驱动


————————————————————————————
三,引入约束					|
————————————————————————————
	> 实体类映射文件与主配置文件约束
		在Hiernate的核心包下有就映射xml文件的dtd以及主配置文件的dtd.复制,顶部约束就OK
	> 主配置文件约束
		<!DOCTYPE hibernate-configuration PUBLIC
		"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
		"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
	> 映射文件约束
		<!DOCTYPE hibernate-mapping PUBLIC 
		"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
		"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

————————————————————————————
四,需要掌握的知识点			|
————————————————————————————
	1,基础部分
		★ JDBC缺点
		★ Hibernate优点
		★ 查询
		★ 修改
		★ 删除
		★ 增添
		★ 执行过程
	2, 缓存部分
		★ Hibernate一级缓存
			* get
			* load
			* save
			* update
			* flush
			* evict
			* clear
		★ Hibernate二级缓存
		★ Hibernate查询缓存
	3, 关系部分
		★ 一对多单项
		★ 一对多双向
		★ 多对多双向
	4, 性能部分
		★ 延迟加载
			> class 上的lazy属性
			> 持久化类上设置 final
			> 调用方法,初始化代理对象Hibernate.initialize(Object);
			> 类级别延迟,关联属性级别延迟(映射文件,对应标签设置属性)
		★ 抓取策略
			> <set> <many-to-one>
			> fetch   lazy
	5,HQL
		★ createQuery();
		* 条件查询
		* 分页查询
		* 聚合函数查询
	5,QBC
		★ createCriteria();
		* 条件查询
		* 分页查询
	7,SQL
		★ createSQLQuery();

————————————————————————————
五,Hello World				|
————————————————————————————
	1,搭建环境
	2,配置文件以及映射文件
	3,通过 Configuration 对象来读取配置文件
		Configuration cfg =  new Configuration();
		cfg.configure();
	3,获取SessionFactory工厂对象
		SessionFactroy sessionFactory = cfg.buildSessionFactory();
	5,通过SessionFactroy获取Session连接对象
		Session session = sessionFactory.openSession();
	6,开启事务
		Transaction ts = session.beginTransaction();
	7,执行逻辑代码
	8,提交事务
		ts.commit();
	9,关闭Session
		session.close();
	

['完整代码']
	//创建读取配置文件的对象
	Configuration cfg = new Configuration();
	//读取配置文件
	cfg.configure();
	//获取Session工厂类
	SessionFactory sessionFactory = cfg.buildSessionFactory();
	//从工厂类中获取一个Session连接
	Session session = sessionFactory.openSession();
	//开启事务
	Transaction ts = session.beginTransaction();

	//逻辑代码

	//提交事务
	ts.commit();
	//关闭Session连接
	session.close();