————————————————————————————
	Hibernate 二级缓存		|
————————————————————————————
	* 二级缓存:SessionFactory级别缓存,可以在多个Session之间共享数据
	* 二级缓存的结构
		1,类缓存区
		2,集合缓存区
		3,更新时间戳
		4,查询缓冲区
	* 二级缓存适合放入的资源
		> 不经常修改的,允许偶尔出现并发问题.
		> 不敏感数据(例如:财务数据,就不能缓存)
	* 二级缓存配置
		> 在Hibernate中开启缓存,二级缓存默认是关闭的
		> 配置二级缓存的一个提供商.Hibernate支持很多缓存
			* 我们使用:EHCache
		> 配置哪些类使用缓存
			1,在映射文件中配置
			2,在核心配置文件中配置(推荐,更加集中科学的管理)
	
	['类缓存散装数据']
		get/load出来的对象,会在一级缓存中保存,如果开启了二级缓存那么也会在二级缓存中保存!
		但是,一级缓存中保存的是一个对象的引用的!其实就是真正的保存了一个对象!当你下次请求同一个的时候,那么就把这个给你!
		也就是说,其实多次请求同一个对象.其实真的是同一个对象!
		但是,二级缓存中保存的就是,把这个对象'拆散后的数据.'!当你下次还请求获取者对象的时候.它会把这些数据都拼凑起来!重新 new 一个给你!
		所以,你二次请求得到的对象.只是数据相同,但其实已经不是你第一次获得的那个对象了!

	['集合缓存区特点']
		集合缓存区中缓存的是.集合中元素的,OID!当再次获取集合数据的时候,就会根据'OID,去类缓存区找相应的对象'!如果没有,就去翻数据库了
		集合缓存区的使用,需要依赖类缓存区!

	
	['query.llist();']
	Query query = session.createQuery(HQL);
	query.list();	
		* list();方法会向二级缓存中放数据,但是它不会用

	['query.iterate();']
	Query query = session.createQuery(HQL);
	Iterator it = query.iterate();
		* iterate()();返回迭代器的这个方法,它会使用二级缓存
		* 这哥们会发送n+1条语句! 先发送一条查询所有id的查询语句,再发送你需要数据的查询语句,效率不高
	
	['一级缓存更新自动同步到二级缓存']

	['更新时间戳缓存区域']
	
	['查询缓存']
		比二级缓存功能更加的强大.查询缓存必须依赖二级缓存!
		二级缓存:主要是对类,对象的缓存
			from Customer where id=?;
		查询缓存:是针对对象,还可以针对类中的属性
			* 要求,多次查询的SQL语句一样,而且连参数都要一样,才会被缓存.
			select name from Customer where id=?
		
		查询缓存,也需要配置(一定要有二级缓存)!
		在核心的配置文件中:
		<property name="hibernate.cache.user_query_cache">true</property>
	
		
————————————————————————————
	Hibernate 二级缓存的使用|
————————————————————————————
	查看hibernate.properties文件,可以知道二级缓存的配置

	<property name="cache.use_second_level_cache">true</property>		//启用缓存
	<property name="cache.use_query_cache">true</property>				//开启查询缓存
	

	二级缓存框架的实现(Hibernate自带)
	hibernate.cache.provider_class=org.hibernate.cache.HashtableCacheProvider			//在properties中有N多个供你选择

	<property name="cache.provider_class">org.hibernate.cache.HashtableCacheProvider</property>


	你也可是使用其他的第三方的开源缓存框架!通过上面的配置,指定地址就好!
	

	['指定需要使用到二级缓存的类']
	<class-cache usage="read-only" class="指定类的全路径"/>
		* usage:对缓存操作的限制(缓存策略)
		* class:类的全限定地址
	* 其实也可以单独的在映射文件中配置,但是!为了方便统一管理,都建议放在这里!

	['缓存策略']
	usage
		nonstrict-read-write
			* 非严格的读写,这种非严谨的效率会高那么一丢丢
		read-only
			* 放入二级缓存的对象,只能读取
		read-write
			* 放入二级缓存的对象,可以读写
		transactional
			* 基于事务的策略
	


	
	

		
List
ArrayList
LinkedList
Set
HashSet
TreeSet
Map
HashMap
TreeMap
			