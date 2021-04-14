--------------------
文件系统
--------------------
	# 每个Vert.x 实例有一个文件系统对象，通过 fileSystem 方法获取它。
	# 每个操作都提供了阻塞和非阻塞版本，其中非阻塞版本接受一个处理器（Handler）， 当操作完成或发生错误时调用该处理器。

	# 阻塞版本的方法名为 xxxBlocking，它要么返回结果，要么直接抛出异常

	# 加载 io.vertx.core.file.FileSystemOptions 类时，这些系统属性将被加载一次。
		* 因此，在加载此类之前应该设置这些属性，或者在启动它时作为JVM系统属性来设置。

	# 从ClassPath访问资源
		* Vert.x找不到文件系统上的文件时，它尝试从类路径中解析该文件。 注意，类路径的资源路径不以 / 开头。

		* Java不提供对类路径资源的异步方法，所以当类路径资源第一次被访问时， 该文件将复制到工作线程中的文件系统。
		* 第二次访问相同资源时，访问的文件直接从 （工作线程的）文件系统提供。就算类路径下资源被修改了，也不会影响读取。
		* 可以通过： FileSystemOptions.setFileCachingEnabled 来限制，则其默认值为 true 

		* 文件缓存的路径默认为 .vertx，它可以通过设置系统属性 FileSystemOptions.setFileCacheDir 进行自定义
		* 如果想在系统级禁用整个classpath解析功能，可以将系统属性 FileSystemOptions.setClassPathResolvingEnabled 设置为 true。
