
————————————————————————
Hibernate检索方式		|
————————————————————————
	* Hibernate提供了五种方式来进行查询
	1,导航对象图检索方式
		* 根据已经加载的对象导航到其他对象
	2,OID检索方式
		* 根据对象的OID,也就是唯一标识属性来进行检索
	3,HQL检索方式
		* 使用面向对象的HQL查询语言
	4,QBC检索方式
		* 使用QBC(Query By Criteria)API来检索对象,这种API封装了基于字符串形式的查询语句,提供了更加面向对象的查询接口
	5,本地SQL检索方式
		* 使用本地数据库的SQL查询语句
	
————————————————————————
导航对象图检索			|
————————————————————————
	Customer c = (Customer)session.get(Customer.class,"123");
	Set<Order> orders = c.getOrders();
	for(Order order : orders)
	{
		System.out.println(order);	//根据对象,导航到对象
	}

————————————————————————
OID检索方式				|
————————————————————————
	* 使用get/load,根据OID来进行检索.
	* 至于get/load的区别,这里不再赘述

————————————————————————
HQL检索方式				|
————————————————————————
	* HQL(Hibernate Query Language),面向对象查询语言.它和SQL查询语言有些相似,是Hiberante里面使用最广泛的一种查询语言.
	* HLQ语句,支持使用'?'问号占位符,也支持':id',别名占位符.使用问号,必须要按照顺序进行赋值.如果是别名.可读性比较强,且无视顺序.推荐使用
	* HQL不支持SQL的"SELECT *..",写法.不允许使用'*'号,可以使用起别名的方式代替,as 关键字可以省略,为了可读性,最好添加
	* 支持别名
		* "SELECT customer FROM com.kevin.domain.Customer as customer WHERE customer.id=:id"
	* HQL支持各种运算
		* =,<>,>=,<=,is null,is not null
		* in,not in,between,not between
	* HQL支持以下的检索方式

	1,查询语句中设置各种查询条件
		Query query = session.createQuery("FROM com.kevin.domain.Customer WHERE id=:id");
		query.setParameter("id","302A8E12E9C44B0BB7BBBE27CBD22D93");
		Customer c = (Customer) query.uniqueResult();

	2,投影查询,也就是仅仅检索出对象的部分属性
		* 需要使用'SELECT'语句.指定需要查询的属性字段.查询出来每条数据都封装到 Object[] 数组中.
		String hql = "SELECT customer.name,customer.age FROM com.kevin.domain.Customer as customer";
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();		//每一个Object[],就是查询到的一条记录.当然,也可以添加条件来显示查询的结果
		* 把投影查询结果封装到对象,这个就比较狠了,灰常的面向对象.直接在HQL语句中,添加 new 语句.
		String hql = "SELECT new com.kevin.domain.Customer(id,name) FROM com.kevin.domain.Customer as customer WHERE customer.id=:id";	
		//这HQL语句,是不是有点叼?要注意的是,类中必须要提供相应的构造函数.不然抛出异常.也可以不添加WHERE语句,查询出来的List里面的所有对象,也仅有HQL中指定的属性.
		Query query = session.createQuery(hql);
		query.setParameter("id","4EF777D506D94417B24FC3CFB0AE4B9F");
		Customer c = (Customer) query.uniqueResult();
		
	3,对查询结果进行排序
		* 直接把ORDER BY语句写入HQL语句 
		String hql = "SELECT customer FROM com.kevin.domain.Customer as customer ORDER BY customer.age DESC";
		Query query = session.createQuery(hql);
		List<Customer> list = query.list();

	3,分页查询
		* 使用两个方法来确定要查询的数据
		String hql = "SELECT customer FROM com.kevin.domain.Customer as customer";
		Query query = session.createQuery(hql);
		query.setFirstResult(0);			//设置查询数据的开始处
		query.setMaxResults(2);				//设置查询的数据总量
		List<Customer> list = query.list();

	4,检索单个对象
		String hql = "SELECT customer FROM com.kevin.domain.Customer as customer WHERE customer.id=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id","6008C15C8A464B989554F74B552805A5");
		Customer c = (Customer) query.uniqueResult();	//uniqueResult方法仅会返回单个对象,如果查询结果存在多个,那么抛出异常
	
	4,连接查询
		* 内连接
			//查询的是两个表的交集部分
			String hql = "SELECT customer FROM com.kevin.domain.Customer as customer INNER JOIN customer.orders";		//面向对象的思维去写HQL语句
			Query query = session.createQuery(hql);
			List<Customer> list = query.list();
		* 迫切内连接
			//添加一个关键字:fetch,该关键字仅是HQL里面可以使用.SQL里面并不具备
			String hql = "SELECT customer FROM com.kevin.domain.Customer as customer INNER JOIN FETCH customer.orders";
			Query query = session.createQuery(hql);
			List<Customer> list = query.list();
			* 迫切内连接,跟内连接查询出来的结果集是一样的.
			* 内连接:把数据封装到一个 List<Object[]>.迫切内连接:把数据封装到一个 List<Customer>中,迫切内连接得到的结果,将会有重复记录,需要去重操作.

		* 隐式内连接
			//就是普通的加一个WHERE关键字,去笛卡尔积.
		* 左外连接
		* 迫切左外连接
		* 右外连接
		* 交叉连接
		//跟SQL一样,就是查询多个表.取出笛卡尔积.

	5,添加实体参数进行查询
		* 更加面向对象的方式,来进行查询,传递的参数.就是一个对象
		//新建一个托管状态的Customer对象,仅仅需要唯一的IOD值.
		Customer customer = new Customer();						
		customer.setId("302A8E12E9C44B0BB7BBBE27CBD22D93");
		String hql = "SELECT order FROM com.kevin.domain.Order as order WHERE order.customer=:customer";
		//此HQL语句,更面向对象化,解释:order对象的customer属性=:customer
		Query query = session.createQuery(hql);
		query.setEntity("customer",customer);			//通过此方法,把指定对象添加到占位符,同样,也可以使用'?',占位符
		List<com.kevin.domain.Order> list = query.list();

	5,分组查询,允许使用 HAVING和GROUP BY关键字
	6,提供内置聚集函数,如sum(),min()和max()
		//count(),函数
		String hql = "SELECT COUNT(*) FROM com.kevin.domain.Customer";
		Query query = session.createQuery(hql);
		Long count = (Long) query.uniqueResult();		//注意,返回值是一个 long,并非int!
		//MAX()函数
		String hql = "SELECT MAX(customer.age) FROM com.kevin.domain.Customer as customer";
		Query query = session.createQuery(hql);		
		Integer count = (Integer) query.uniqueResult();	//查询的列属性是啥,返回的数据就是啥.

	7,命名查询
		* 其实,就是把SQL/HQL语句写在了映射文件中
		<query name="findAll">			//<query>标签,就是HQL语句,<sql-query>标签,是SQL语句
			SELECT customer FROM com.kevin.domain.Customer as customer
		</query>
		* 在程序中,只需要调用指定名称的HQL/SQL语句即可
		Query query = session.getNamedQuery("findAll");
		List<Customer> list = query.list();

	7,能够调用,用户自定义的SQL函数或者标准的SQL函数
	8,支持子查询
	9,支持动态参绑定
	10,多态检索
		* 没太大作用,但是要知道支持这种写法
		* 其实就是检索出,指定的类型,以及子类.前提肯定是已经被映射到了框架的配置中
		//查询所有的实体,以及其子类
		Query query = session.createQuery("FROM com.kevin.domain.Customer");
		//查询出所有实现了Serializable接口的实例
		Query query = session.createQuery("FROM java.io.Serialozable");
		//查询出所有的持久化对象
		Query query = session.createQuery("FROM java.lang.Object");

	11,离线条件查询
		* 在WEB层,创建一个:构筑好查询条件的,DetachedCriteria对象.传递给service --> dao层.
		* dao,层直接就可以操作该对象
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);	//创建对象
		criteria.add(Restrictions.like("name","%Kevin%"));						//提供条件
		criteria.add(Restrictions.eq("age",18));								//提供条件
		Criteria c = criteria.getExecutableCriteria(session);					//执行该对象的方法,传递session作为形参,获取Criteria对象
		List<Customer> list = c.list();											//操作Criteria对象,获取结果
		* 其实就是,在进行高级查询的时候.客户端提供的一些查询条件,是无法封装到JavaBean中的(也就JavaBean未提供这些字段)
		* 那么我们需要重写封装,或者分别传递给dao层,进行判断和操作.比较麻烦,就可以使用这种离线查询.直接WEB层,封装一个条件查询的对象.传递给DAO层就能执行

————————————————————————
QBC检索方式				|
————————————————————————
	* QBC支持以下查询方式
	
	1,对查询结果进行排序
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.addOrder(Order.asc("age"));		//通过Order对象的静态方法指定排序方式,把需要被排序的字段,作为形参传入
		List<Customer> list = criteria.list();

	2,分页查询
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.setFirstResult(0);		//设置查询开始处
		criteria.setMaxResults(2);		//设置查询的数据总量
		List<Customer> list = criteria.list();

	3,检索单个对象
		* 使用分页限定查询结果
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.setMaxResults(1);							//默认从0开始查询第一条记录
		Customer c = (Customer) criteria.uniqueResult();	//默认获取结果集的第一天数据
		* 使用条件限定查询结果
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.add(Restrictions.eq("id", "302A8E12E9C44B0BB7BBBE27CBD22D93"));	//Restrictions.eq(p,p);该方法表示添加一个'等于'查询条件,第一个参数表示属性,第二个字段表示值.
		Customer c = (Customer) criteria.uniqueResult();
	
	4,模糊查询
		* 很简单,一看就知道!但是要注意的是,参数要记得添加:%,不然就是等于了!
		Criteria c = session.createCriteria(Customer.class);
		c.add(Restrictions.like("id","%7%"));
		List<Customer> list = c.list();
	
	5,连接查询
		* 内连接
		* 迫切内连接['不支持']
		* 隐式内连接['不支持']
		* 左外连接
		* 迫切左外连接
		* 右外连接	['不支持']
		* 交叉连接	['不支持']
	
	6,投影查询
————————————————————————
本地SQL检索方式			|
———————————————————————
	* 查询所有
		SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM _customer");		//必须是SQL语句
		List<Object[]> list = sqlQuery.list();	//每一个Object[],中的每个数据,就是一个对象的散装数据!
		for(Object[] arr : list)
		{
			System.out.println(Arrays.toString(arr));
			//[302A8E12E9C44B0BB7BBBE27CBD22D93, Kevi, 22]
		}
	* 自动封装
		SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM _customer");
		sqlQuery.addEntity(Customer.class);		//提供一个Class类,框架自动反射创建对象.自动的进行数据封装.
		List<Customer> list = sqlQuery.list();
		for(Customer customer : list)
		{
			System.out.println(customer);
			System.out.println(customer.getOrders());	//自动的封装实体
		}
		

	# 自动封装分情况
	if(hasEntity){
		//如果这个clazz是由Hibernate管理的类
		query.addEntity(clazz);
	}else{
		//如果这个clazz不是由Hibernate管理的类
		query.setResultTransformer(Transformers.aliasToBean(clazz));
	}

————————————————————————
附:QBC检索,条件语句		|
———————————————————————
		例如:
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.add(Restrictions.eq("id", "302A8E12E9C44B0BB7BBBE27CBD22D93"));
	
		Restrictions.eq("字段","值");		//等于
		Restrictions.allEq();				//等于Map使用key/value进行多个等于的判断
		Restrictions.gt("字段","值");		//大于 >
		Restrictions.ge("字段","值");		//大于等于 >=
		Restrictions.lt("字段","值");		//小于
		Restrictions.le("字段","值");		//小于等于 <=
		Restrictions.between();				//对应SQL的between字句
		Restrictions.like("字段","值");		//like语句
		Restrictions.in("字段","值");		//in语句
		Restrictions.and();					//and关系
		Restrictions.or();					//or关系
		Restrictions.sqlRestriction();		//sql限定查询
		Restrictions.asc("字段");			//根据传入的字段进行升序排序
		Restrictions.desc("字段");			//根据传入的字段进行降序排序

		* 部分属性对于参数,有可能不正确!实际使用的时候建议先查询确定.
		



