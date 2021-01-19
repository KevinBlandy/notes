-----------------------
文件监视				|
-----------------------
	# 监视某个目录下文件的
		* 创建
		* 删除
		* 修改
	
	# 涉及类库
		WatchService
		WatchKey
		WatchEvent<?>
		WatchEvent.Kind
	
	# 事件
		StandardWatchEventKinds.ENTRY_CREATE	文件创建
		StandardWatchEventKinds.ENTRY_DELETE	文件删除
		StandardWatchEventKinds.ENTRY_MODIFY	文件修改
		StandardWatchEventKinds.OVERFLOW		事件丢失，一般不关注

-----------------------
WatchService			|
-----------------------
	# 接口方法
		@Override
		void close() throws IOException;
		WatchKey poll();
		WatchKey poll(long timeout, TimeUnit unit) throws InterruptedException;
		WatchKey take() throws InterruptedException;


-----------------------
WatchKey				|
-----------------------
	# 接口方法
		boolean isValid();		
		List<WatchEvent<?>> pollEvents();
		boolean reset();
		void cancel();
		Watchable watchable();

-----------------------
WatchEvent				|
-----------------------	
	# 接口方法
		Kind<T> kind();
		int count();
		T context();
	
	# 内部接口
		public static interface Kind<T> {
			String name();
			Class<T> type();
		}
		public static interface Modifier {
			String name();
		}

-----------------------
Demo					|
-----------------------
	//WatchService 是线程安全的，跟踪文件事件的服务，一般是用独立线程启动跟踪
    public static void watchRNDir(Path path) throws Exception {
        //创建 WatchService 对象
        WatchService watchService = FileSystems.getDefault().newWatchService();
        //给path路径加上文件观察服务
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        // 开始监视路径
        while (true) {
            //线程阻塞
            final WatchKey key = watchService.take();
            //获取事件集合
            List<WatchEvent<?>> watchEventList = key.pollEvents();
            //遍历
            for (WatchEvent<?> watchEvent : watchEventList) {
                // 获取事件
                final WatchEvent.Kind<?> kind = watchEvent.kind();
                // handle OVERFLOW event
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }
                //创建事件
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {

                }
                //修改事件
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {

                }
                //删除事件
                if (kind == StandardWatchEventKinds.ENTRY_DELETE) {

                }
                //把当前事件强制转换泛型为 Path 的事件
                final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                //获取事件文件名称
                final Path filename = watchEventPath.context();
                // print it out
                System.out.println(kind + " -> " + filename);
            }
            // reset the keyf
            boolean valid = key.reset();
            // exit loop if the key is not valid (if the directory was
            // deleted, for
            if (!valid) {
                break;
            }
        }
    }
------------------------------------
WatchService						|
------------------------------------	
	# 构建
		WatchService watchService = FileSystems.getDefault().newWatchService();
	# 方法
		WatchKey		take();
			* 检索并移除下一个watch key。若没有可检索的则阻塞。

------------------------------------
WatchKey							|
------------------------------------	
	# 构建
		通过 WatchService 实例的 take()方法获取
	# 方法
		List<WatchEvent<?>>		pollEvents();
			* 检索并移除所有该watch key

------------------------------------
WatchEvent							|
------------------------------------
	# 构建
	# 方法
		Kind<?>			kind();
			* 返回事件种类
		

------------------------------------
Kind								|
------------------------------------
	# 方法
		String	name();
			* 返回事件的名称
	
------------------------------------
StandardWatchEventKinds				|
------------------------------------	
	# 事件类
	# 静态字段
		OVERFLOW
		ENTRY_CREATE	
		ENTRY_DELETE
		ENTRY_MODIFY

