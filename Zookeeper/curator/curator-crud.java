
----------------------------------------
crud									|
----------------------------------------
	# 总结
		* 基本的语法:操作().options1().option2(value)..optionN().forPath("/");
			curatorFramework.start();
			curatorFramework.create().forPath("/");
			curatorFramework.getChildren().forPath("/");
			curatorFramework.getACL().forPath("/");
			curatorFramework.setACL().withACL(null).forPath("/");
			curatorFramework.delete().forPath("/");
			curatorFramework.setData().forPath("/");
			curatorFramework.sync().forPath("/");
			curatorFramework.checkExists().forPath("/");

		* 根据操作的不同,可以有N多个options

	# 普通创建
		client.create().forPath(String path, byte[] data);
		
		*  默认创建的永久,非序列化节点

	# 创建其他模式的节点节点
		client.create().withMode(CreateMode.EPHEMERAL).forPath(String path, byte[] data);
	
	# 创建受保护的节点
		String client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(String path, byte[] data);

		* 未知
	
	#  递归的创建多级节点
		//TODO

	# 修改节点
		client.setData().forPath(String path, byte[] data);
	
	# 异步修改节点
		CuratorListener listener = new CuratorListener(){
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
            }
        };
		// 设置全局的 CuratorListener
        client.getCuratorListenable().addListener(listener);

		// inBackground 表示异步设置,修改结果会通知给全局的CuratorListener
        client.setData().inBackground().forPath(String path, byte[] data);

	
	# 回调修改节点
		// 设置临时的回调接口
		BackgroundCallback backgroundCallback = new BackgroundCallback() {
			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				
			}
		};
		// inBackground 表示异步设置,修改结果会通知给临时的Callback
		client.setData().inBackground(backgroundCallback).forPath(String path, byte[] data);
	
	# 删除节点
		client.delete().forPath(String path);
	

	# 必须删除
		client.delete().guaranteed().forPath(String path);

		* 因为网络链接问题,删除操作可能失败,如果这个节点是锁相关的节点的话,可能会破坏锁机制
		* 一旦执行了必须删除,那么Curator将记录删除失败的节点,并尝试删除它们,直到成功为止
		* 删除失败时仍会出现异常,但是只要CuratorFramework实例打开就会尝试删除
	
	# 获取子节点
		List<String> client.getChildren().watched().forPath(String path);
	
	# 获取节点,并且添加watcher
		
		List<String> client.getChildren().usingWatcher(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//zk的watcher
			}
		}).forPath(String path);
	
	# 读取节点信息,并且监听
		byte[] data = curatorFramework.getData().usingWatcher(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				
			}
		}).forPath(String path);
	

	# 递归的删除指定节点
		client.delete().deletingChildrenIfNeeded().forPath(String path);
