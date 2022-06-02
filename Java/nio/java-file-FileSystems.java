-----------------------
FileSystems				|
-----------------------
	# 文件系统的工具(厂)类
	# 不能实例化
	# 都是静态方法

-----------------------
FileSystems	-方法		|
-----------------------
	public static FileSystem getDefault()
		* 获取默认的文件系统对象,就是当前系统的文件系统

	public static FileSystem getFileSystem(URI uri) 
	public static FileSystem newFileSystem(URI uri, Map<String,?> env)
	public static FileSystem newFileSystem(URI uri, Map<String,?> env, ClassLoader loader)throws IOException
	public static FileSystem newFileSystem(Path path) throws IOException
	public static FileSystem newFileSystem(Path path, ClassLoader loader)
	public static FileSystem newFileSystem(Path path, Map<String,?> env)
	public static FileSystem newFileSystem(Path path, Map<String,?> env, ClassLoader loader)

	