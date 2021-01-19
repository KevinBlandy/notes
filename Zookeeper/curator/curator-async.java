------------------------------------
异步								|
------------------------------------
	# 异步创建
		//装饰原始的客户端对象
		AsyncCuratorFramework asyncCuratorFramework = AsyncCuratorFramework.wrap(client);
		//执行创建,whenComplete 添加回调函数
		asyncCuratorFramework.create().forPath("/", new byte[] {}).whenComplete((name, exception) -> {
			if ( exception != null ){
				exception.printStackTrace();
			}else{
				System.out.println("Created node name is: " + name);
			}
		});
	
	# 异步的创建并且添加Watcher
		AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);
		async.create().forPath("/").whenComplete((name, exception) -> {
			if (exception != null) {
				exception.printStackTrace();
			} else {
				handleWatchedStage(async.watched().checkExists().forPath("/").event());
			}
		});
		
		private static void handleWatchedStage(CompletionStage<WatchedEvent> watchedStage) {
		 // async handling of Watchers is complicated because watchers can trigger multiple times
        // and CompletionStage don't support this behavior

        // thenAccept() handles normal watcher triggering.
        watchedStage.thenAccept(event -> {
            System.out.println(event.getType());
            System.out.println(event);
            // etc.
        });

        // exceptionally is called if there is a connection problem in which case
        // watchers trigger to signal the connection problem. "reset()" must be called
        // to reset the watched stage
        watchedStage.exceptionally(exception -> {
            AsyncEventException asyncEx = (AsyncEventException)exception;
            asyncEx.printStackTrace();    // handle the error as needed
            handleWatchedStage(asyncEx.reset());
            return null;
        });		
	}