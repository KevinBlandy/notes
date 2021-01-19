----------------------------
入门						|
----------------------------
	# 官网
		http://www.ehcache.org/	

	# 特点
		快速
		简单
		多种缓存策略
		缓存数据有两级:内存和磁盘,因此无需担心容量问题
		缓存数据会在虚拟机重启的过程中写入磁盘
		可以通过RMI,可插入API等方式进行分布式缓存
		具有缓存和缓存管理器的侦听接口
		支持多缓存管理器实例,以及一个实例的多个缓存区域
		提供Hibernate的缓存实现
	
	# 集成
		* 可以单独使用.一般在第三方库中被用到的比较多(如mybatis,shiro等)
		* ehcache 对分布式支持不够好,多个节点不能同步,通常和redis一块使用
	

	# 配置文件
		* 默认情况下Ehcache会自动加载classpath根目录下名为ehcache.xml文件
		* 也可以将该文件放到其他地方在使用时指定文件的位置


----------------------------
代码形式初始化				|
----------------------------
	//CacheConfiguration,用于配置Cache
	CacheConfigurationBuilder<Long, String> cacheConfigurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,String.class, ResourcePoolsBuilder.heap(10));
	
	//创建cacheManagerBuilder
	CacheManagerBuilder<?> cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder();
	
	//通过build()方法获取cacheManager实例对象,build()方法可以传递一个bool值,表示是否立即初始化cacheManager
	CacheManager cacheManager = cacheManagerBuilder.build();
	
	//初始化cacheManager实例
	cacheManager.init();
	
	//通过cacheManager创建新的cache对象
	Cache<Long, String> myCache = cacheManager.createCache("myCache", cacheConfigurationBuilder);
	
	//往缓存添加一个值
	myCache.put(1L, "da one!");
	
	//从缓存获取一个值
	String value = myCache.get(1L);
	
	System.out.println(value);

	//删除 Cache,还会将其关闭,Cache释放所有本地保存的瞬态资源(如内存),此Cache变得不可用
	cacheManager.removeCache("preConfigured");

	//关闭cacheManager
	cacheManager.close();

----------------------------
配置文件形式初始化			|
----------------------------
	//配置文件地址
	URL url = Main.class.getResource("/ehcache.xml");
	
	//实例化xml配置对象
	Configuration xConfiguration = new XmlConfiguration(url);
	
	//根据xml配置对象创建CacheManager
	CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xConfiguration);
	
	//初始化cacheManager
	cacheManager.init();
	
	//获取指定名称的cache
	Cache<String, String> cache = cacheManager.getCache("foo", String.class, String.class);
	
	cache.put("1", "KevinBlandy");
	System.out.println(cache.get("1"));
	cache.remove("1");
	
	//从cacheManager移除cache,但是该cache还可以被其他的CacheManager使用
	cacheManager.removeCache("foo");
	
	//关闭cacheManager,它实现了Closeable接口
	cacheManager.close();

----------------------------
存储层						|
----------------------------
	# 代码构建
		//创建 ResourcePoolsBuilder
		ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder();

		/**
			可以仅仅使用一种类型的 resourcePoolsBuilder,例如:可以使用缓存中的数据只能在offheap(堆外)中使用
			也可以像下面一样组合
		**/

		resourcePoolsBuilder = resourcePoolsBuilder.heap(10, EntryUnit.ENTRIES);		//堆存储属性控制
		resourcePoolsBuilder = resourcePoolsBuilder.offheap(1, MemoryUnit.MB);			//堆外存储属性控制
		resourcePoolsBuilder = resourcePoolsBuilder.disk(20, MemoryUnit.MB, true);		//硬盘存储属性控制
		
		//创建 cacheConfigurationBuilder
		CacheConfigurationBuilder<Long, String> cacheConfigurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, resourcePoolsBuilder);
		
		//创建 CacheConfigurationBuilder
		CacheManagerBuilder<CacheManager> cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder();
		
		//创建 persistentCacheManagerBuilder
		CacheManagerBuilder<PersistentCacheManager> persistentCacheManagerBuilder = cacheManagerBuilder.with(CacheManagerBuilder.persistence(new File("e:\\ehcache", "myData")));
		
		//创建 PersistentCacheManager
		PersistentCacheManager persistentCacheManager = persistentCacheManagerBuilder.build(true);
		
		//使用 cacheConfigurationBuilder 创建 cache
		Cache<Long,String> cache = persistentCacheManager.createCache("foo", cacheConfigurationBuilder);
		
		cache.put(1L, "KevinBlandy");
		
		System.out.println(cache.get(1L));
	
	# 三层
		1,堆内
		2,堆外
		3,磁盘
		4,集群(不推荐)

		* 当缓存在堆外的数据,必须实现序列化接口
	
		* 所有分层选项都可以单独使用,例如,可以使用缓存中的数据只能在offheap中使用
			CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(2, MemoryUnit.GB)).build(); 
	
	# 堆层
		* 每个缓存的起点,也是更快的,因为不需要序列化
		* 可以按条目或按大小调整堆层的大小
			ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, EntryUnit.ENTRIES);			//堆上只允许有10个条目
			ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10);							//堆上只允许有10个条目(默认)
			ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, MemoryUnit.MB);				//只允许10 MB

	# 堆外
		* 使用堆外,必须定义一个资源池,给你想要分配的内存大小
			ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(10, MemoryUnit.MB);	//只有10MB大小
		* 堆外数据必须被序列化和反序列化 - 因此比堆更慢
		* 倾向于堆大量的数据,这些数据堆上的垃圾收集,会有太严重的影响(几乎不会被GC)
		* -XX:MaxDirectMemorySize:根据打算使用的堆外大小,不要忘记在java选项中定义该选项
	
	# 磁盘层
		* 数据存储在磁盘上,磁盘越快,越专用,访问数据的速度就越快
			PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder() 
				.with(CacheManagerBuilder.persistence(new File(getStoragePath(), "myData"))) 
				.withCache("persistent-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
					ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB)) 
				).build(true);
			persistentCacheManager.close();
		
		* 设置磁盘永久存储,默认非永久
			ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB, true)
		
		* 磁盘层不能在缓存管理器之间共享,持久性目录当时专用于一个高速缓存管理器

		* Ehcache 3仅在正常关闭的情况下提供持久性
			* 必须执行了 CacheManager的 close() api
			* 如果JVM崩溃,则不存在数据完整性保证
			* 在重新启动时,Ehcache会检测到它CacheManager没有完全关闭,并在使用之前擦除磁盘存储器
		
		* 磁盘存储分为多个段,提供并发访问,但也保存打开的文件指针,缺省值是16
		* 在某些情况下,可能希望通过减少段的数量来降低并发性并节省资源

			ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder();
			resourcePoolsBuilder = resourcePoolsBuilder.disk(20, MemoryUnit.MB, true);
			
			CacheConfigurationBuilder<Long, String> cacheConfigurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, resourcePoolsBuilder);
			//设置分段大小
			cacheConfigurationBuilder.add(new OffHeapDiskStoreConfiguration(2));
		
		
	# 多层设置
		* 使用多个层次,有一些限制
			1,在多层设置中必须始终有一个堆层
			2,您不能合并磁盘层和集群层
			3,层级的大小应该是金字塔形的,也就是说金字塔层级越高,层级越低
		
		* 它们之间的大小关系:堆内存大小 < 堆外内存大小 < 磁盘大小
	
	# 资源池
		* 多层使用资源池进行配置,大多数时间使用一个ResourcePoolsBuilder
			ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder();
			//堆内存的设置
			resourcePoolsBuilder = resourcePoolsBuilder.heap(10, EntryUnit.ENTRIES);
			//堆外内存设置
			resourcePoolsBuilder = resourcePoolsBuilder.offheap(1, MemoryUnit.MB);
			//磁盘设置
			resourcePoolsBuilder = resourcePoolsBuilder.disk(20, MemoryUnit.MB, true);

			CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, resourcePoolsBuilder);

			* 使用3层的缓存(堆,offheap,磁盘)他们创建并链接使用 ResourcePoolsBuilder 声明顺序无关紧要(例如offheap可以在堆之前声明)
		
		* 资源池'仅用来指定配置',它不是可以在缓存之间共享的实际池
			ResourcePools pool = ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10).build();
			CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			  .withCache("test-cache1", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class, pool))
			  .withCache("test-cache2", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class, pool))
			  .build(true);
			
			* test-cache1和test-cache2将是两个缓存
		
		* 更新ResourcePools
			* updateResourcePools()允许更改堆层大小,而不是池类型
			* 因此,'不能更改堆外层或磁盘层的大小'

				ResourcePools pools = ResourcePoolsBuilder.newResourcePoolsBuilder().heap(20L, EntryUnit.ENTRIES).build(); 

				cache.getRuntimeConfiguration().updateResourcePools(pools); 

				assertThat(cache.getRuntimeConfiguration().getResourcePools()
				  .getPoolForResource(ResourceType.Core.HEAP).getSize(), is(20L));
	
	# 摧毁
		* 堆外和磁盘,是两个持续层,这意味着当JVM停止时,所有创建的缓存及其数据仍存在于磁盘或群集中
		* 需要完全删除它们,他们的定义 PersistentCacheManager 提供了以下方法
			destroy()
				* 此方法销毁与缓存管理器相关的所有内容(当然包括缓存)
				* 缓存管理器必须关闭或未初始化才能调用此方法
				* 另外,对于群集层,当前没有其他缓存管理器应该连接到相同的缓存管理器服务器实体

			destroyCache(String cacheName)
				* 此方法销毁给定的缓存,该缓存不应该被另一个缓存管理器使用
			


	# 多层缓存操作的顺序流程
		1,当向缓存中添加一个值时，它会直接进入最低层的授权层。
		2,以下内容get将会在缓存层中向上推送该值。
		3,当然，只要授权层中有值，所有更高级别的缓存层都会失效。
		4,完全缓存未命中（该值不在任何层上）始终会一直延伸到授权层。


----------------------------
总结						|
----------------------------
	