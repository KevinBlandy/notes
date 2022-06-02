---------------------------
FileSystem					|
---------------------------
	# 文集系统类
		* Zip 也属于一个文件系统，可以通过 FileSystem 操作
			FileSystem fileSystem = FileSystems.newFileSystem(Paths.get("C:\\eclipse-jee-2021-09-R-win32-x86_64.zip"));
			Path zipFile = fileSystem.getPath("/");
			// 遍历zip文件中的所有文件
			Files.walk(zipFile).forEach(System.out::println);

	# 构建方式
		FileSystem fileSystem = FileSystems.getDefault();
			* 通过 FileSystems 工厂获取
		FileSystem newFileSystem(Path path, ClassLoader loader);
			* 通过 FileSystems 根据指定的 path 创建新的文件系统
		FileSystem	getFileSystem();
			* 通过 path 实例获取
			
---------------------------
FileSystem-实例方法			|
---------------------------
		public abstract void close() throws IOException;
		public abstract Iterable<FileStore> getFileStores();
			* 获取根路径的磁盘(磁盘分区)
		
		public abstract Path getPath(String first, String... more);	
			* 根据路径,从当前文件系统获取Path对象
		
		public abstract PathMatcher getPathMatcher(String syntaxAndPattern);
		public abstract Iterable<Path> getRootDirectories();
			Iterable<Path> getRootDirectories();
		
		public abstract String getSeparator();
			* 获取跨平台文件分隔符
		
		public abstract UserPrincipalLookupService getUserPrincipalLookupService();
		public abstract boolean isOpen();
		public abstract boolean isReadOnly();
		public abstract Set<String> supportedFileAttributeViews();
		public abstract FileSystemProvider provider();
		public abstract WatchService newWatchService() throws IOException;
		


