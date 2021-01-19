步骤略
	Configuration cfg = new Configuration();
	cfg.configure();
	SessionFactory sessionFactory = cfg.buildSessionFactory();
	Session session = sessionFactory.openSession();
	Transaction ts = session.beginTransaction();

	//逻辑代码

	ts.commit();
	session.close();



————————————————————————————
一,Hiernate的查询单条记录	|
————————————————————————————
	* 查询不需要开启事务 

	session.get(Class, id);
	session.load(Class, id);
	> get和load的区别
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
二,Hiernate的查询多条记录	|
————————————————————————————
		* 查询不需要开启事务 

	HQL查询 --> Hiernate QueryLanguage
		['无条件查询所有']
		Query query = session.createQuery("from Customer");
			* 要注意的是,后面的"Customer"!是一个类名,不是表名!可以写成:com.kevin.domian.Customer
		List<Customer> list = query.list();
			* 返回来的就是一 List 集合,可以进行遍历
		['带条件查询所有']
		Query query = session.createQuery("from Customer where name=?");
			* 要注意的是,后面的"Customer"!是一个类名,不是表名!可以写成:com.kevin.domian.Customer
		query.setString(0,"kevin");
			* 对第一个?号进行赋值!其实还可以用另外一种方式.给HQL参数起别名,通过别名来赋值,更具有可读性!where name=:ddd 赋值!query.setString("ddd","Kevin");
		List<Customer> list = query.list();
			* 返回来的就是一 List 集合,包含了对应表中的所有 name 属性为 Kevin 的记录.可以进行遍历
	QBC查询
		['无条件查询所有']
		Criteria c = session.createCriteria(Customer.class);
		List<Customer> list = c.list();
		['带条件查询所有']
		Criteria c = session.createCriteria(Customer.class);
		c.add(Restrictions.eq("name","Keviin"));	
			* 不难看出,执行这个方法的时候就给查询添加了条件.而添加方式就是一个类的静态方法.
			* eq方法,表示=,第一个参数就是字段名,第二个参数就是具体的值 
		c.add(Restrictions.isNull("字段名"));
			* isNull 方法表示.指定的字段是空的
		List<Customer> list = c.list();
	SQL查询
		['无条件查询所有']
		SQLQuery sql = session.createSQLQuery("SELECT * FROM customer");
			* 直接写sql语句,要注意里面是表名了,不是类名.这是正儿八经的SQL语句
		List<Object[]> list = sql.list();
			* 返回的是一个Object的数组,里面的每一个数组,都是一条记录
		['无条件查询所有']
		SQLQuery sql = session.createSQLQuery("SELECT * FROM customer");
		sql.addEntity(Customer.class);
			* 跟上面唯一不同的就是,多了这个方法!其实就是SQL查询方式也可以直接返回对象!只要经过这个方式设置就好
		List<Customer> list = sql.list();
			* 那么返回的结果集就不在是Object的数组,而是指定的对象
		
		
	
————————————————————————————
三,Hiernate的修改			|
————————————————————————————
	session.update(Object obj);
	> 修改记录有两种方式可以进行修改
		1,手动创建对象的方式
			* 手动的创建一个对象,主键与数据库中的记录对应!然后传递给update();执行修改,那么数据库中的记录就会被修改成跟我们自己建立的对象的属性
			* 这种修改方式,会把数据库中对应记录的所有字段都设置为 new 出来这个对象的所有属性!那么我如果只是更改某一个字段的话,这种方式就显得特别不方便!甚至有可能忘记设值,而导致数据库空字段的存在!
		2,先查询,再修改(推荐方法)
			* 先get();出来要修改的对象!然后直接对这个对象进行修改!也是可以修改到数据库的
			* 会有两条SQL语句的生成.先select ,在你操作完毕对象后,又会执行update语句!

 ————————————————————————————
四,Hiernate的删除			 |
—————————————————————————————
	关于删除,也有两种方法
	1,先查询,再删除	['推荐']
			session.delete(session.get(Class,id));
	2,自己创建对象,把对象传递进去进行删除
			session.delete(Object obj);

	> 其实就是根据obj的主键,去删除对应表的指定记录!至于这个obj,你可以自己创建设置主键值!也可以从数据库查询出来!
	> '但是'多表操作的时候,需要注意一些问题.例如:级联删除之类的!
	* 这种情况最好就使用第一种,先从数据库中查询数据!那么返回来的对象!是已经具备了关联关系的对象(里面含有其他对象,集合之类的)!
	* 此时执行删除,就会级别删除.如果你是自己创建...里面的集合,对象啊都是空的!数据库也蒙!
