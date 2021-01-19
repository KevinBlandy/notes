---------------------------
FileSystem					|
---------------------------
	# 文集系统类
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
Iterable<Path>		getRootDirectories();
						* 获取根路径的磁盘(磁盘分区)

Iterable<FileStore>	getFileStores();
						* 获取根路径的磁盘(磁盘分区)

Path				getPath(String first, String... more);
						* 根据路径,从当前文件系统获取Path对象
