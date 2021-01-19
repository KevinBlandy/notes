-------------------------------------
curator								 |
-------------------------------------
	# Apache Curator是Apache的开源项目,封装了zookeeper自带的客户端,使用相对简便
		<dependency>
			<groupId>org.apache.curator</groupId>
			<artifactId>curator-framework</artifactId>
			<version>4.1.0</version>
		</dependency>

	# 各种Demo源码
		<dependency>
			<groupId>org.apache.curator</groupId>
			<artifactId>curator-examples</artifactId>
			<version>${curator-framework-version}</version>
		</dependency>

	# 文档
		http://curator.apache.org/index.html
	


-------------------------------------
curator	 客户端的创建				 |
-------------------------------------

	// 重试策略,1000毫秒后尝试重新连接,最后尝试连接3次
	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
	// 直接创建客户端
	CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy)
	
	
	// 使用Builder创建
	CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
		// 地址
		.connectString(host)
		// 重试策略
		.retryPolicy(new ExponentialBackoffRetry(1000, 3))
		.connectionTimeoutMs(3000)
		.sessionTimeoutMs(3000)
		// 权限
		.authorization(Arrays.asList(new AuthInfo("digest", "user:123456".getBytes())))
		.build();

	
	* Builder这种方式可以去设置权限啊,只读啊各种设置
	* 创建完毕客户端后要执行: start(); api

-------------------------------------
CuratorFramework					 |
-------------------------------------
	# 添加监听
		// 添加链接状态监听
		client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			@Override
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				// 链接是否正常
				boolean isConnected = newState.isConnected();
			}
		});

		// 添加事件监听
		client.getCuratorListenable().addListener(new CuratorListener() {
			@Override
			public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
				// 原生框架的event对象
				WatchedEvent watchedEvent = event.getWatchedEvent();
			}
		});
