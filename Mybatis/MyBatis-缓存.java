————————————————————————
1,MyBati缓存			|
————————————————————————
	* MyBatis,提供查询缓存.用于减轻数据库的压力,提升系统性能
	* MyBatis缓存分为
		1,一级缓存
			* SqlSession级别的缓存
		2,二级缓存
			* mapper级别缓存(允许整合第三方缓存框架)
	* 一级缓存和二级缓存都是默认开启的
————————————————————————
2,MyBati-一级缓存		|
————————————————————————
	* 其实就是SqlSession的缓存(SqlSession 级别的缓存)
	  在操作数据库的时候,需要构造SqlSession对象
	  在对象中有一个数据结构 (HashMap) 用于存储缓存数据
	  不同的SqlSession之间的缓存数据区域是'互不影响的'

	* 一级缓存工作原理
		第一次根据id查询数据的时候,就把查询到的数据存入缓存
		当查询出的数据,在系统中执行了修改,删除的时候,执行commit
		当第二根据相同id查询的时候,直接从缓存区中取出数据

	* 如果SqlSession执行了commit操作(执行插入,删除,更新),会清空SqlSession中的一级缓存
	  这样做的目的是,为了让缓存中永远存储的就是最新的信息,避免'脏读'.
	
	* SqlSession对于缓存的管理
		session.clearCache();		//清除SqlSession中的缓存

	* 一级缓存的应用
		> 在正式开发的时候,是把MyBatis是和Spring整合开发
		> 事务控制是在service层,一个service方法中,就有可能会包括很多方法的调用
			service()
			{
				dao.findById();
				dao.findById();	//此时再次调用,就会从一级缓存中获取数据,因为事务控制,俩方法使用的是同一个SqlSession
			}
		> service在开始执行的时候,就开启事务,创建SqlSession对象,当这个方法结束的时候.SqlSession就会close();
		> service方法所有使用的就是同一个SqlSession,那就有可能'使用到一级缓存'

————————————————————————
3,MyBati-二级缓存		|
————————————————————————
	* 二级缓存是mapper级别的缓存,本质还是一个 HashMap 数据结构.
	* 多个SqlSession去操作用一个mapper的sql语句,
	* 多个SqlSession去操作数据库得到的数据都会存在二级缓存区域
	* 二级缓存是跨SqlSession的,多个SqlSession可以公用二级缓存
	* 每个namespace的mapper都有一个二级缓存区域,俩mapper的namespace相同?那么他们所查询到的数据会存放在一个缓存当中
	* 当有SqlSession执行了commit();就会清空对应的二级缓存下的所有的缓存.
	* SqlSession执行了close();操作,才会把查询的数据写入缓存中,且要求缓存的POJO实现 Serializable 序列化接口
	
	1,在SqlMapConfig中开启二级缓存
		<settings>
				<setting name="cacheEnabled" value="true"/>
		</settings>
		* 默认值为 true,默认就是开启的

	2,在mapper映射文件中,开启缓存
		<!-- 开启缓存 -->
		<cache/>
		* 也就是指定了本命名空间mapper,下的查询会添加到二级缓存
		* 重要参数详解
			1,flushInterval		刷新间隔,值为毫秒,每隔一段时间自动自动清理缓存
			2,size				缓存中的对象数目
			3,readOnly			缓存策略-只读,值为 true/false
			4,type				指定二级缓存接口的实现类(MyBatis默认:PerpetualCache)
				* 当使用EHCache的时候,就需要指定EHCache的实现类
			
	3,设置需要禁用二级缓存的查询
		<!-- 禁用二级缓存 -->
		<select id="" ... useCache="false">
		</select>
		* 在select标签上使用:useCache标签来实现该查询不存入二级缓存,默认值为 true
		* 一些比较敏感的查询,数据变化较快.就有必要设置'禁止缓存'
	
	4,设置刷新缓存
		* 在mapper的同一个namespace中,如果有其他的insert,update,delete操作数据后需要刷新缓存,不然有可能出现脏读
		* 默认值为:true,即,要刷新.可以修改为 false,即不刷新
		* 刷新,就是清空缓存.
		<select id="" ... flushCache="true">
		</select>

	* POJO类尽量实现序列化接口,缓存数据可能会存储到本地.存储介质不一定

————————————————————————
3,MyBati-整合EHCache	|
————————————————————————
	* EHCache,是一个优秀的开源缓存框架.支持集群,分布式
	* 所谓的分布式缓存:就是专门弄个缓存数据的服务器,用于缓存数据的读取.该缓存服务器可以被多个应用服务器共享.
	* Redis也是一个开源的缓存框架,比EHCache大.
	* MyBatis本身不支持分布式缓存,需要整合其他的缓存框架,而且它本身对于二级缓存的处理也不专业,很有必要进行整合
	* MyBatis本身提供了一个 Cache 接口,我们可以实现这个 Cache 接口去进行开发
		* org.apache.ibatis.cache.Cache
	* MyBatis,默认使用一个Cache实现类.作为二级缓存
		* org.apache.ibatis.cache.impl.PerpetualCache
	* MyBatis,和EHCache整合,
		1,加入EHCache的jar包和整合包
			* ehcache-core-x.x.x.jar
			* mybatis-ehcache-x.x.x.jar
			
		2,主要使用整合包中的Cache实现类
			* org.mybatis.caches.ehcache.EhcacheCache

		3,在mapper缓存配置中指定缓存实现类
			* <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
		
		4,在classpath下加入EHCache配置文件
			ehcache.xml
			* 其实这些配置也可以写在xml配置文件中
			 <cache type="org.mybatis.caches.ehcache.EhcacheCache">
				<property name="" value="">
			 </cache>
			
————————————————————————
4,MyBati-缓存总结		|
————————————————————————
	1,一级缓存:基于PrpetualCache的HashMap本地缓存,其存储作用域为Session
	  当Session执行flush或close之后,该Session中的Cache就会被清空

	2,二级缓存与一级缓存的机制相同,默认采用PerpetualCache,HashMap存储.
	  不同在于其存储作用域为Mapper(NameSpace),并且可以自定义框架框架,如:Ehcache

	3,对于缓存数据更新机制,当某一个作用域(一级缓存Session/二级缓存NameSpace),进行了CUD操作后
	  默认该作用域下所有的 select 中的缓存将会被 clear.
