————————————————————————————
Hibernat检索方式			|
————————————————————————————
	> 其实所谓的检索方式,就是Hibernate有几种查询方式!
	
	1,导航对象图检索方式
		* 根据已经加载的对象导航到其他对象
	2, OID检索方式
		* 根据对象的OID来检索对象
		* get(0)/load()
	3,HQL检索方式
		* 使用面向对象的HQL查询语言
		* session.createQuery(HQL);
	4,QBC检索方式
		* 使用QBC(Query By Criteria)API来检索对象
		* 这种API封装了基于字符串形式的查询语句,提供了面向对象的查询接口!
		* session.createCriteria();
	5,本地SQL检索方式
		* 使用本地数据库的SQL查询语句
		* session.createSQLQuery(SQL);
————————————————————————————
一,HQL检索方式				|
————————————————————————————
	> HQL(Hibernate Query Language)是面向对象的查询语言,它和SQL查询语言有些相似,在Hibernate提供的各种检索方式中,HQL是使用最广泛的一种检索方式!
	> 有如下强大的功能
	1,在查询语句中设定各种查询条件
	2,支持投影查询,即:仅检索出部分对象的属性
	3,支持分页查询 
	4,支持连接查询
	5,支持分组查询,允许使用 having和group by关键字
	6,提供内置聚集函数,如 sum(),min(),max()
	7,能够调用用户定义的SQL函数或标准 SQL 函数
	8,支持子查询
	9,支持动态参数绑定

	* HQL检索方式包括以下步骤
		* 通过Session的createQuery()方法创建一个Query对象,它包括一个HQL查询语句,HQL查询语句中可以包含命名参数
		* 动态参数绑定
		* 调用Query的list()方法执行查询语句,该方法返回java.util.List类的查询结果.在List集合中存放了符合查询条件的持久化对象
	* Query接口支持方法链编程风格它的setXxxx();返回的就是自身的实例,而不是void
	* HQL & SQL
		* HQL查询语言是面向对象的,Hibernate负责解析HQL查询语句,然后根据对象关系映射文件中的映射信息.把HQL查询语句翻译成相应的SQL语句.HQL,查询语句中的主题是域模型中的类以及类的属性
		* SQL查询语句是与关系数据库绑定一起的,sql查询语句中的主体是数据库的表以及表的字段
	注意:
		QHL,支持:select * 这种写法!可以这么写:select c from Customer c;起别名的方式

['查询所有']
	Query query = session.createQuery("from 类名");
	List<T> list = query.list();
['结果排序']
	Query quer = session.createQuery("from 类名 别名 order by 别名.属性名 排序方式");
	List<T> list = query.list();
['分页查询']
	Query query = session.createQuery("from 类名");
	query.setFirstResult(0);	//设置分页,从第几条记录开始
	query.setMaxResults(2);		//设置分页,要几条数据
	List<!-- <T> --> list = query.list();
['查询单个对象']
	//返回对象
	Query query = session.createQuery("from 类名 别名 where 别名.属性名=:name");
	query.setParameter("name","Kevin");
	T t = (T)query.uniqueResult();
	//使用聚合函数
	Query query = session.createQuery("select count(*) from 类名");
	Long count = query.uniqueResult();
['查询单个属性']
	Query query = session.createQuery("select s.name from Student s");
	List<String> names = query.list();
['查询多个属性']
	Query query = session.createQuery("select s.name,s.age from Student s");
	List<Object[]> all = query.list();
['很无解--只返回带有指定属性的对象']
	Query query = session.createQuery("select new Student(s.name,s.id) from Student s");
	List<T> list = query.list();		//返回一组对象,只拥有HQL中指定的属性!但是,千万要提供对应的构造函数
['对象类型的参数']
	Query query = session.createQuery("from Order o where o.customer=?");
	query.setEntity(new Costomer(略));		//根据传递进去的客户类,返回该客户的所有订单类
	List<Order> list = query.list();
['普通类型的参数']
	Query query = session.createQuery("from Order o where o.id=?");
	query.setParameter(0,"10028");	
	List<Order> list = query.list();	//返回的结果只有一个,可以替换为:Object obj = query.uniqueResult();
['连接查询']
	内连接和迫切内连接的区别
	内连接	  :把数据封装到一个 List<Object[]>中
	迫切内连接:把数据封装到一个 List<Object>中!会有重复的值!需要加上 distinct去除重复元素
['离线条件查询 ']
	DetachedCriteria
	用途:在WEB层,获得一个离线的 Criteria!
	DetachedCriteria 
————————————————————————————
二,QBC检索方式				|
————————————————————————————
['查询所有']
	Criteria c = session.createCriteria(Class T);
	List<T> list = c.list();
['结果排序']
	Criteria c = session.createCriteria(Class T);
	c.addOrder(Order.desc("指定属性")); //对指定字段添加排序条件
	c.addOrder(Order.asc("指定属性"));  //对指定字段添加排序条件
	List<T> list = c.list();
['分页查询']
	Criteria c = session.createCriteria(Class T);
	c.setFirstResult(1);	//分页开始标记
	c.setMaxResults(5);		//分页数据量
	List<T> list = c.list();
['查询单个对象']
	Criteria c = session.createCriteria(Class T);
	c.add(Restrictions.eq("属性名","属性值"));		//等于:select * from tb where 属性名=属性值.返回的就是单个对象
	T c = (T)c.uniqueResult();
['各种条件']
	Restrictions.条件(值);
	* 这个得自己去查百度了,太多我没有整理!
	* 就是把HQL中的各种运算,比较.都换成了方法!更加的面向对象
	* 例如:
		   HQL: where id='1005';
	       QBC: add(Restrictions.eq("id","1005"));
		   HQL: where name is null;
		   QBC: add(Restrictions.isNull("name"));
['连接查询']

	
————————————————————————————
三,SQL本地检索方式			|
————————————————————————————
['查询所有']
	SQLQuery s = session.createSQLQuery("select * from tb_name");
	List<Object[]> list = s.list();
		* 返回Objcet数组类型的List集合
	SQLQuery s = session.createSQLQuery("select * from tb_name");
	s.addEntity(Class T);	//可以指定类型,就会自动封装
	List<T> list = s.list();
		* 返回的就是指定类型的对象集合



————————————————————————————
四,旧笔记					|
————————————————————————————
	Hibernate提供了以下几种检索对象的方式
	1,导航对象图检索:根据已经加载的对象导航到其他的对象
		> employee.getDeparement().getName();//根据员工,获取部门,再获取部门名称!这个部门名称其实就是数据库里面的部门名称
	2,OID检索:根据对象的OID来检索对象
		> session.get(),session.load()!!
	3,HQL检索:使用面向对象的HQL查询语言	***
		> 重点学习的,这个东西是Hibernate定义的标准.
	4,QBC检索:使用QBC(Query By Criteria)API来检索对象.这种APIC封装了基于字符串形式的查询语句,提供了更加面向对象的查询接口
		> 
	5,本地SQL检索:使用本地数据库的SQL查询语句
		> 非常不推荐,你一旦写了原始的数据库语句!那么在修改数据库的时候,就需要修改源码

----------------------HQL
HQL特点
1,与SQL相似,SQL中的语法基本上都可以直接使用
2,SQL查询的是表和表中的列,HQL查询的是对象,与对象中的属性
3,HQL关键字,不区分大小写,但是JAVA的类名和属性名要区分大小写!
4,SELECT可以省略!
-------------------------------查询(select)
select from Employee
	> 查询Employee类对应的表,没有带条件,就查出来所有的,返回一个List<Object>
	* 可以加as,也就是说可以加别名(列名称跟关键字冲突的时候,就可以使用别名)
select from Employee as e where e.name='Kevin'
	> 别名,带上条件
	* 返回的也是一个List<Object>
select e.name from Employee e
	> 查询单个指定的字段
	* 返回的集合的元素类型,就是这个属性类型(字段是String,那就是List<String>)
select e.name,e.id from Employee e
	> 查询多个指定的字段
	* 返回的是一个数组的集合!每个数组,都是一条查询出来的结果!List<String[]>
select new Employee(e.name,e.id) from Employee e
	> 查询多个指定的字段
	* 这个就比较叼了,返回来的是对象集合!不过这些对象只填充了HQL语句中指定的字段!
	* 注意啊,使用这种方法,那么指定的类,要有符合的构造方法啊!
-------------------------------修改(Update)和删除(delete)
修改:
	Query q = session.createQuery("update Employee e set e.name=? where e.id=?");
	q.setParameter(0,"demo");
	q.setParameter(1,"CF2F2907E19340629A8424B2E67E4037");
	int x = q.executeUpdate();//返回的是被影响的行数
删除
	Query q = session.createQuery("delete Employee e where e.id=?");
	q.setParameter(0,"11111");
	int x = q.executeUpdate();
> 在update或者delete后,Session都要refresh刷新一下！保持跟数据库同步！

-------------------------------进阶
SQL函数
Long count = (Long) session.createQuery("select count(*) from Employee e").uniqueResult();
	> count()演示 返回long!	
String count = (String) session.createQuery("select max(id) from Employee e").uniqueResult();
	> max()演示,根据字段类型返回最大的一个!
	* min(),avg(),sun(),一样的,不多做演示
	* 如果是数字类型,那么返回Number,自己要根据情况进行转换
分组
List list = session.createQuery("from Employee e order by e.id").list();
	> 就在HQL语句中加上order by !
内连接查询
List<Object> list = session.createQuery("select e.id,e.name,d.name from Employee e join e.department d ").list();
	> 这东西跟mysql的语句有一丢丢区别！在join后面,不是跟的表名,而是对象的一个属性!
	* 代表查询这个对象,以及它的属性的表!多表查询,结果是List<Object[] Object>
内连接查询(完全面向对象)
List<Object> list = session.createQuery("select e.id,e.name,e.department.name from Employee e ").list();
	> 看懂了没?e是对象的别名,e.department.name!!就是对象的一个属性的属性！直接可以用这种方式表达,跟上面的意思是一样的
	* 也是返回了一个List<Object[]>.它会自动去查找Employee类对应的表,再查询Employee的deparetmen属性对应的表,的对应的name字段

-----------------------------使用命名查询
这东西吧, 你就这么理解！我们现在的阶段还是要把HQL语句,写在代码内！如果需要修改,还需要去修改源代码!命名查询--就是把HQL语句写在配置文件中!可以在不修改源代码的情况下修改HQL语句
1,在当前对象的hbm.xml的配置文件中的根元素下。添加<query>标签
	<query name="queryById">from Employee e where id=:xxx</query>
	* name:自定义名称,后面的就是HQL语句.一定是要定义在<hibernate-mapping>标签中,不是<class>标签中
	* 要注意在xml文件中,"<",">"等一些在QHL里面要用到的符合,需要转义！
2,在获取Query的时候,session执行的不再是一个HQL语句,而是一个"名称".!这个名称就代表了一句HQL语句
	Query q = session.getNamedQuery("queryById");//参数,就是hbm.xml中<query>标签的name属性名称,就可以获取到对应的HQL语句
	Employee e = (Employee) q.setParameter("xxx","CF2F2907E19340629A8424B2E67E4037").uniqueResult();//对xml中的HQL语句的变量进行赋值

-----------------------------Query的一些方法详解
Query的一些方法详解
	setFirstResult(0);//设置分页,从第几条记录开始
	setMaxResults(2);//设置分页,要几条数据
		> 例:List list = session.createQuery("select from Employee e").setFirstResult(0).setMaxResults(2).list();
	uniqueResult();//查询的结果是唯一的一个结果,返回Object
		* 如果结果不唯一,有多个.就抛出异常
	setParameter(0,"Kevin");//格式化HQL语句,替代HQL中的?值,要注意的是,这里第一个是从0开始!而JDBC里面是从1开始!
		> Object o = session.createQuery("from Employee where id=?").setParameter(0,"Kevin").uniqueResult();
	setParameter("xxx",""Kevin);//这种方式,是给变量名称赋值,不是给问号了,变量在哪里？在下面
		> Object o = session.createQuery("from Employee where name=:xxx").setParameter("xxx","Kevin").uniqueResult();
		* 可以使用":xxx"这种方式给未知参数进行赋值,那么在设置的时候,就可以不按照顺序的进行赋值了!只要变量名称对上就好.注意格式--:xxx
	setParameterList("xxx",Object[]);//在一些场合会用到,把Object[]里面的值赋到变量xxx中.也就是说,HQL语句中的xxx是一集合类型的参数
		> List list = session.createQuery("from Employee e where id in(:ids)").setParameterList("ids",new Object[]{"E6A66CB9530D4B45AF55914DAEC3897D","CF2F2907E19340629A8424B2E67E4037"}).list();
		* :ids很显然就是多个参数,挨个写问号或者变量名吧，很恼火！就可以用这个方法!
	setEntity();
		> 把参数与一个持久化类绑定
		* List list = session.createQuery("from Order o where o.customer=?").setEntity(new Costomer(略)).list();
		* 订单类中有一个客户类!当你查询订单的的时候,直接可以把传递一个客户的对象给这个HQL语句!就能返回该客户对应的所有订单!
	executeUpdate();
		> 执行的是update或者是delete语句,返回的是一个int值,代表被影响的行数
		* 要求session.createQuery(HQL);里面的HQL是一个update/delete语句!


-----------------------------使用QBC(Query By Criteria)查询
> 一看就懂
	Criteria c = session.createCriteria(Employee.class);//这里是一个Class类型,它会自动的去寻找匹配的xml,文件找到匹配的数据库表
> 增加过滤条件
	c.add(Restrictions.ge("id",1));
		> Restrictions是一个类,里面有很多的静态方法用于过滤查询结果,这里表示的是id>1
	c.add(Restrictions.le("id",5));
		> Restrictions是一个类,里面有很多的静态方法用于过滤查询结果,这里表示的是id<5
> 增加排序条件
	c.addOrder(Order.desc("id"));
		> Order是一个类,里面有很多静态方法用于排序,这里表示的是按照id字段升序排序
	c.addOrder(Order.desc("name"));
		> Order是一个类,里面有很多静态方法用于排序,这里表示的是按照name字段降序排序
> 执行查询
	c.setFirstResult(1);	//分页开始标记
	c.setMaxResults(5);	//分页数据量
	c.uniqueResult();	//唯一结果
	c.list();		//返回的是结果集合
	c.iterate();		//返回的是一个迭代器Iterator
	setCacheable(boolean)	//是否缓存


---------------
知识小结		|
---------------
HQL
多态查询(查询某个类的子类... ...在表中对应的所有记录)
别名查询
构造方法("from new xxx(zzz,ccc) where id=xxx")
投影查询(只查询对象的某些属性,)
命名查询(在配置文件中,配置HQL语句,在Session中调用)
多表查询
	交叉连接
	内连接
	迫切内连接
	左外连接		检索出来是数组
	迫切左外连接	检索出来是对象
QBC
简单查询
排序查询
条件查询
分页查询
离线条件查询
SQL
检索出来是数组 List<Object[]>
addEntity(Object);//可以转换为对象