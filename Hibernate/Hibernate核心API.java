————————————————————————————
	Hibernate 核心API		|
————————————————————————————
	Configuration
	SessionFactory
	Session
	Transaction
	Query
	Criteria

————————————————————————————
一,Configuration 核心对象	|
————————————————————————————
	* 千万要注意,JAVASE的API中也有一个类叫做 Configuration 
	* 而我们使用的是 Hibernate的 Configuration.千万别导错包
	* org.hibernate.cfg.Configuration;

	> 它负责管理Hibernate中的那些配置信息
		* 加载hiberante.cfg.xml以及hibernate.properties
		* 加载持久化类与数据包的映射关系(*.hbm.xml)

	> 创建 Configuration 的两种方式
		* Configuration cfg = new Configuration();
			这种方式,就是默认加载了:hibernate.properties核心配置文件,那么你的映射文件,就需要通过代码进行配置
		* Configuration cfg = new Configuration().configure();
			这种方式,就是加载了xml核心配置文件,配置文件有映射.那么就不用手动的添加代码
			也可以给configure方法指定路径名称,也就是说.核心的xml配置文件,可以定义名称

	['常用方法']	
	configure();						//加载核心的配置文件:hibernate.cfg.xml
	addClass(Class clazz);				//根据指定的类,去加载对应的映射文件(要求映射文件符合规定,且跟类在同一个包下)
	addResource(String resourceName);	//加载指定路径的映射文件
	buildSessionFactory();				//创建SessionFactory对象
	setProperty("name","value");		//这种东西就是不读配置文件,通过程序来设置相关的属性,不过一般不用.硬编码!你会把数据库的账户密码写这里?

————————————————————————————
二,SessionFactory 核心对象	|
————————————————————————————
	* SesionFactory是 Configuration 对象根据当前配置信息生成的.
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	* 它保存了当前数据库配置信息,和所有映射关系以及预定义的SQL语句
	* 它是线程安全的
	* 它还负责维护Hibernate的二级缓存(后面详解)
	* 构造SessionFactory很消耗资源,一般而言!一个应用,只初始化一个足以
	* 内部还有一个连接池,默认的是Hibernate自带的连接池,我们可以换成c3p0等... ...
	* 它负责创建Session对象,其实就可以理解为一个: Connection

	['常用方法']
	openSession();						//打开一个与数据的连接,返回Session对象	(可以理解为Connection)	
	getCurrentSession();				//获取与当前线程绑定的Session
		* 现在知道有这个东西就行,别用要挂！使用这个需要在主配置文件中配置！
		* <property name="current_session_context_class">thread</property>
		* 如果线程没有绑定,那么执行这个就立即绑定一个新的并且返回!
	close();							//关闭连接池,没啥好解释

————————————————————————————
三,Session		 核心对象	|
————————————————————————————
	* Session相当于JDBC中的Connection
	* Session是应用程序与数据库之间交互操作的一个单线程对象!是Hibernate的运作中心
	* Session是线程不安全的
	* 所有持久化对象都必须在Session的管理才可以进行持久化操作
	* Session对象有一个一级缓存,显示执行flush之前,所有的持久化操作数据,都换成在session对象处!
	* 持久化类与Session关联起来,就有了持久化的能力
	* Session是通过SessionFactory得到
		Session = sessionFactory.openSession();

	['常用方法']

	* 操作对象的方法
		persist();						//其实跟save一样,不过.它是由jpa提供的.Hibernate实现了jpa接口,实现了这个规范的一个实现类
		save(Object);					//insert into ...会返回一个:Serializable ,就是保存这条记录的ID
		update(Object);					//update...
		delete(Object);					//delete...
		saveOrUpdate(Object);			//保存或者更新,不存在就保存,存在就更新
		close();						//关闭拦截,并非是释放资源.而是把资源还给了连接池
		clear();						//情况Session域中保存的对象
		evict(Object);					//把指定的对象从Session中清除
	* 查询的方法
		get(Class,id);					//select....,Class就是返回的实体类对象的类型,id,一般都是数据库中的主键,其实就是搜索条件
		load(Class ,id);				//同上,只是load采用延迟加载,下面详解	
		createQuery(hql);				//HQL查询,返回一个HQL查询对象(Query),这个是根据配置里面的方言来生成不同数据的SQL语句
		createSQLQuery();				//SQL查询,返回一个SQL查询对象(SQLQuery),就是上面的子类其实
		createCriteria(Class);			//QBC查询,
	* 管理事务的方法
		beginTransaction();				//开始事务,并且返回一个事务对象
		getTransaction();				//获取当前Session关联的这个事务

	['注意']
		get和load的区别
		1,发送SQL查询语句的时机
			* get :当执行session.get();的时候立即执行查询.
			* load:它采用了一个技术.lazy延迟加载(懒加载),在使用这个对象属性的时候(不包括主键),才会去发送SQL语句查询
		2,返回的对象
			* get :返回的是一个真实的对象
			* load:返回的是一个代理对象(javassist-x.xx.x.GA.jar这个包生成的),也就是说要想实现懒加载.javaBean不能是final的!
		3,当查询对象找不到的时候
			* get :返回一个 null ,不会抛出异常!但是你使用 null ,就肯定空指针了!
			* load:会抛出异常(ObjectNotFoundException)
	
————————————————————————————
四,Transaction	核心对象	|
————————————————————————————
	* Transaction,代表数据库操作的事务对象,用于管理事务
	* Transaction tx = session.beginTransaction();
	
	['常用方法']
	commit();							//提交事务
	rollback();							//回滚事务
	wasCommitted();						//返回boolean值,检查事务是否提交
	wasRolledBack();					//返回boolean值,检查事务是否回滚
	['注意']
	如果没有开启事务,那么Session的每个操作,都相当于一个事务


————————————————————————————
五,Query 核心对象			|
————————————————————————————
	* Query	代表的是面向对象的Hibernate操作
	* Session.createQuery(String HQL);接受一个HQL语句,返回一个 Query 对象
	* HQL(Hibernate Query Language),语法很像SQL语法,但是他是面向对象的
	* 使用 Query 对象的步骤
		1,获得Hibernate Session对象
		2,编写HQL语句
		3,调用session.createQuery(HQL)创建查询对象
		4,如果HQL语句包含参数
			setXXX(int index,value);	//对指定位置的?进行指定类型的赋值
		5,调用Query对象的list,或者uniqueResult();执行查询
			list();						//得到一个集合的结果
			uniqueResult();				//得到一个唯一的结果,如果没有结果返回null,如果结果有多个,直接抛异常!它不知道返回哪个
	* Query 还包含两个方法,用于控制返回结果
		setFirstResult(int firstResult);//设置返回结果从第几条开始(从第几条开始)
		setMaxResults(int maxResults);	//设置本次返回结果记录条数(要多少条记录)
		* 这东西就是分页,框架会自动帮我们带上limit(select * from tbname limit 0,15;)

	['demo']
	无条件查询所有
		Query query = session.createQuery("from Customer");
			* 要注意的是,后面的"Customer"!是一个类名,不是表名!可以写成:com.kevin.domian.Customer
		List<Customer> list = query.list();
			* 返回来的就是一 List 集合,可以进行遍历
	带条件查询所有
		Query query = session.createQuery("from Customer where name=?");
			* 要注意的是,后面的"Customer"!是一个类名,不是表名!可以写成:com.kevin.domian.Customer
		query.setParameter(0,"kevin");
			* 对第一个?号进行赋值(下标从0开始)!其实还可以用另外一种方式.给HQL参数起别名,通过别名来赋值,更具有可读性!where name=:ddd 赋值!query.setString("ddd","Kevin");
		List<Customer> list = query.list();
			* 返回来的就是一 List 集合,包含了对应表中的所有 name 属性为 Kevin 的记录.可以进行遍历
	分页查询
		Query query = session.createQuery("from Customer");

————————————————————————————
五,Criteria	核心对象		|
————————————————————————————
	* 这是一个条件查询的接口(就是 Query 的一个子接口)
		> 按照我们当年的做法就是,判断表单传递的字段是否为null,不为null!就拼接字符串!达到模糊查询的效果
	* Criteria criteria = session.createCriteria(Class clazz);
	* 使用Criteria对象步骤
		1,获得Hiernate的Session对象
		2,通过Sesison获得Criteria对象
		3,使用Restrictions的静态方法创建Criterion条件对象
		4,向Criteria对象中添加Criterion查询条件
		5,执行Criterite的list()或者uniqueResult();获取结果
	* 它也包含两个方法,可以用于分页
		setFirstResult(int firstResult);//设置返回结果从第几条开始(从第几条开始)
		setMaxResults(int maxResults);	//设置本次返回结果记录条数(要多少条记录)
		* 这东西就是分页,框架会自动帮我们带上limit(select * from tbname limit 0,15;)

	['demo']
		无条件查询所有
		Criteria c = session.createCriteria(Customer.class);
		List<Customer> list = c.list();
		带条件查询所有
		Criteria c = session.createCriteria(Customer.class);
		c.add(Restrictions.eq("name","Keviin"));	
			* 不难看出,执行这个方法的时候就给查询添加了条件.而添加方式就是一个类的静态方法.
			* eq方法,表示=,第一个参数就是字段名,第二个参数就是具体的值 
		c.add(Restrictions.isNull("字段名"));
			* isNull 方法表示.指定的字段是空的
		List<Customer> list = c.list();

		['常用的条件方法']
		add(Restrictions.条件方法());
		... ...自己慢慢去查吧,我的确不懂了!

		