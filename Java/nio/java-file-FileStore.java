---------------------------------
FileStore						 |
---------------------------------
	# 代表一块儿磁盘分区
	# 构造
		1,从 Files 实例获取,仅仅获取指定的
			FileStore	getFileStore(Path path);

		2,从 FileSystem 静态方法获取,获取的是所有
			Iterable<FileStore>	getFileStores();
			
---------------------------------
FileStore-方法					 |
---------------------------------
Object		getAttribute(String attribute);
				# 获取指定名称的属性值
boolean		supportsFileAttributeView(String name)
				# 是否支持指定名称的属性
boolean		supportsFileAttributeView(Class<? extends FileAttributeView> type)
				# 是否支持指定类型/自定义的属性
long		getUnallocatedSpace()
				# 获取未分配的字节大小
long		getUsableSpace() 
				# 获取已经分配的字节大小
long		getTotalSpace()
				# 获取分区的总大小
boolean		isReadOnly()
				# 是否是只读的
String		type();
				# 获取分区类型
String		name();
				# 获取分区名称
FileStoreAttributeView	getFileStoreAttributeView(Class<V> type);
				# 获取属性对象

