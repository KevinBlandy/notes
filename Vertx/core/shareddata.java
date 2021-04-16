---------------------------
shareddata
---------------------------
	# 共享数据的访问接口，它是线程安全的
		SharedData sharedData = vertx.sharedData();

	# 核心的组件
		synchronous maps (local-only)
		asynchronous maps
		asynchronous locks
		asynchronous counters
	
	# Map的格式有2种
		LocalMap	同步读写
		AsyncMap	异步读写
	
---------------------------
map
---------------------------
	# Local maps
		* 允许在同一个 Vert.x 实例中的不同事件循环（如不同的 verticle）之间安全地共享数据。
			LocalMap<String, String> localMap = sharedData.getLocalMap("m1");
			localMap.put("name", "vvvvvv");
			
			localMap = sharedData.getLocalMap("m1");
			System.out.println(localMap.get("name"));
		
		* 允许存放的数据
			* 不可变的类型 （如 String、boolean），等等

			* 实现了 Shareable 接口的类型 （比如Buffer，JSON数组，JSON对象，或您写的Shareable实现类）。
			* 这种情况下，键/值将被复制，然后再放到Map中。
				map2.put("eek", Buffer.buffer().appendInt(123)); // Buffer将会在添加到Map之前拷贝
		
		
	# 异步共享的 maps
		* 允许数据被放到 map 中，并从本地或任何其他节点读取。
		* 当 Vert.x 是集群模式时, 放进map的数据，从本地以及从集群中的其他成员那里都可以访问到。



---------------------------
lock
---------------------------
	# 本地锁
	# 共享锁

---------------------------
counter
---------------------------
	# 本地计数器
	# 异步计数器
