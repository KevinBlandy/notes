
InputStream input = Resources.getResourceAsStream("SqlMapConfig.xml");	//读取配置文件,返回InputStream流对象

SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();		//创建工厂类获取对象
		
SqlSessionFactory sessionFactory = builder.build(input);				//读取配置文件InputStream流,获得工厂类
		
SqlSession session = sessionFactory.openSession();						//获取Session会话类

-------------------------------------------------------------------------------------
SqlSessionFactory
	openSession();
		//返回一个连接对象,SqlSession,事务需要手动提交
	openSession(true);
		//返回一个连接对象,SqlSession,事务自动提交
-------------------------------------------------------------------------------------
SqlSession 
	selectOne(命名空间.id,与配置文件匹配的参数);
		// 返回的就是配置文件中指定返回类型的对象,取出一条记录.如果记录不唯一,抛出异常!返回结果不唯一
	selectList(命名空间.id,与配置文件匹配的参数);
		//返回就是一个 List 类型的集合,泛型就是映射文件中指定的返回类型对象.查询出多条记录,或单个记录
	getMapper(Class clazz);
		//传递一个满足了mapper开发规范的接口类,返回一个代理类对象
	close();
		//关闭连接
	commit();
		//执行提交
	rollback ();
		//执行回滚
	getMapper(T.class);
		//返回符合mapper开发规范的接口,代理对象