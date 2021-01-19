————————————————————————————————————
1,MyBatis开发DAO					|
————————————————————————————————————
	SqlSession 的使用范围
	
	SqlSessionFactoryBuilder --> SqlSessionFactory --> SqlSession
	
	* SqlSessionFactory 工厂类,只有一个就够.就能不断的创建SqlSession,
	  所有我们可以考虑使用'单例设计模式来管理SqlSessionFactory'
	  那么SqlSessionFactoryBuilder就只需要当作一个工具类来管理即可,不需要当作单例来管理.

	1,SqlSession
		* 是一个面向用户(开发者)的接口
		* 它是'线程不安全的'
		* 在它的实现类中除了有接口中的方法(操作数据库),还有数据域的属性.正是因为有了这些东西,导致了它线程不安全
		* '它的最佳应用场合,就是在方法体内(局部变量).就算是多个线程访问单例.那么每个线程都开启自己的方法,结束即释放.'
	2,它有两种开发DAO的方法
		1,原始,需要开发者自己写DAO接口和实现类
		2,mapper代理,开发者只需要学mapper接口

————————————————————————————————————
2,MyBatis开发DAO(mapper)			|
————————————————————————————————————	
	原始方法就不演示了,简直就是太简单
		在DAO实现类中注入SqlSessionFactory,在方法体内,来通过工厂来创建SqlSession
	
	原始方法开发问题总结
		1,代码大篇幅重复
		2,调用SqlSession方法的时候,把statement的id硬编码了
		3,SqlSession方法,的参数是Object,就算传递错误类型的参数,在编译阶段不会报错.不利于程序的开发
	
	mapper代理方法
	['开发步骤']
		1,编写mapper接口类
		2,编写mapper.xml映射文件
		3,把mapper.xml配置到核心配置文件
	['开发规范']
		1,在mapper.xml中,namespace(命名空间).等于mapper接口的地址
		2,接口中方法的名称要跟xml中对应的id一毛一样.
		3,接口中方法的输入参数类型和xml中statement的parameterType指定的值一样
		4,接口中方法的返回值的类型和xml中statement的resultType一致
		5,在核心配置文件中导入配置文件
			* <mapper resource="mapper/UserMapper.xml"/>	//单个导入
			* <mapper class="com.kevin.vo.User"/>			//需要满足条件
			* <mapper package="com.kevin.vo"/>				//批量导入,同样需要满足条件
		6,使用SqlSession的方法获取DAO的代理对象
			MapperUser mapper = session.getMapper(MapperUser.class);
	['一些注意']
		1,如果mapper方法返回单个POJO对象(非集合),那么代理对象内部通过selectOne();来查询数据库
		  如果mapper方法返回多个POJO对象(是集合),那么代理对象内部通过selectList();来查询数据库

		2,mapper接口方法的参数只有一个,是否会影响到系统的开发?
			* 系统框架中,dao层代码是被业务层公用的代码!就算Mapper接口参数只有一个!我们可以使用包装类型的POJO来满足不同业务方法的需求
			* 注意,持久层中方法参数,可以用包装类型.但是Service方法中建议不要使用这些包装类型(不利于业务层维护和可扩展性)

		3,不管是哪种方法开发,映射文件都是必须的.

	MyBatis就可以自动生成我们mapper接口实现类的代理对象



————————————————————————————————————
1,杂谈	数据模型的分析思路			|
————————————————————————————————————
	数据模型分析思路
	1,每张表记录的数据内容
		* 分模块的对每张表记录的内容进行熟悉
		* 相当于学习系统需求(功能)的过程
	2,每张表数据库字段的设置
		* 重要的字段的设置.非空,外键字段
	3,数据库级别与表之间的关系
		* 外键关系,子表,父表!
	4,表与表之间的业务关系
		* 在分析表与表之间的业务关系,一定要建立在某个业务意义上去分析


	

