----------------------------
Files						|
----------------------------
	# 操作文件的静态工具类,很牛逼


----------------------------
Files-静态方法				|
----------------------------
	//=======================文件信息
	String			probeContentType(Path path)
						* 获取文件类型
	boolean			isHidden(Path path);
						* 判断指定的path是否是隐藏文件/目录
	boolean			exists(Path path, LinkOption... options);
						* 判断指定的文件是否存在
	boolean			notExists(Path path, LinkOption... options)
						* 判断指定的文件是否存在
	boolean			isDirectory(Path path, LinkOption... options);
						* 是否是目录
	boolean			isReadable(Path path);
						* 是否可读
	boolean			isWritable(Path path);
						* 是否可写
	boolean			isRegularFile(Path path);
						* 判断文件是否是正常文件
	boolean			isExecutable(Path path);
						* 判断文件是否可执行
	boolean			isSameFile(Path path, Path path2);
						* 判断文件是否是同一个文件
	long			size(Path path);
						* 返回指定文件的大小
	FileStore		getFileStore(Path path);
						* 获取指定 path 所在的盘符

	//=======================文件信息(属性相关)
	<V extends FileAttributeView> V getFileAttributeView(Path path,Class<V> type,LinkOption... options)
						* 获取path的文件的指定类型的属性描述对象
						* 根据 type 接口来返回不同类型的属性描述对象
							* 基本属性			BasicFileAttributeView
							* 文件所有者		FileOwnerAttributeView	
							* 自定义属性		UserDefinedFileAttributeView
							...

	Path			setAttribute(Path path, String attribute, Object value,LinkOption... options)
						* 给指定文件的指定属性设置值
	Object			getAttribute(Path path, String attribute,LinkOption... options)
						* 获取指定文件的指定名称的属性

	FileTime		getLastModifiedTime(Path path, LinkOption... options)
						* 获取文件最后的修改时间
	Path			setLastModifiedTime(Path path, FileTime time);
						* 设置最后修改时间

	UserPrincipal	getOwner(Path path, LinkOption... options);
						* 返回文件所有者
						* 返回接口:UserPrincipal 的实例
	Path			setOwner(Path path, UserPrincipal owner);
						* 设置文件所有者
	
	Path			setPosixFilePermissions(Path path,Set<PosixFilePermission> perms)
						* 设置文件的POSIX权限。
	Set<PosixFilePermission>	getPosixFilePermissions(Path path, LinkOption... options);
						* 获取文件的权限信息

	//=======================连接
	boolean			isSymbolicLink(Path path);
						* 判断是否是软连接额
	Path			readSymbolicLink(Path path);
						* 通过软连接获取真实的文件对象

	Path			createSymbolicLink(Path link,Path target, FileAttribute<?>... attrs)
						* 创建软连接
	Path			createLink(Path link,Path existing);
						* 创建硬连接
						* link 快捷方式地址,existing 已经存在的文件

	//=======================读取
	byte[]			readAllBytes(Path path);	
						* 把指定Path表示的文件读取到字节数组
	List<String>	readAllLines(Path path,Charset charset);
						* 把指定的文本文件,以指定的字符集一行一行的读取到List<String>中
	
	//=======================写入
	Path			write(Path path,byte[] bute,OpenOption penOption...);
						* 把字节写入到 path
						* penOption是一个标记接口,用于指定写入的模式

	Path			write(Path path, Iterable<? extends CharSequence> lines,OpenOption penOption...);
						* 同上,写入的是 lines 子类中的数据
						* 例如:List<String> 中的数据
	
	//=======================删除
	void			delete(Path path);
						* 删除,如果非空文件夹.则删抛出异常

	boolean			deleteIfExists(Path path)
						* 删除文件,如果不存在.不会抛出异常

	//=======================获取流
	InputStream		newInputStream(Path path,OpenOption penOption...);
						* 获取指定Path的InputStream
	OutputStream	newOutputStream(Path path,OpenOption penOption...);
						* 获取指定的OutputStream
	BufferedReader	newBufferedReader(Path path,OpenOption penOption...);
						* 获取指定的 BufferedReader
						* 有个重载方法,可以指定 Charset
	BufferedWriter	newBufferedWriter(Path path,OpenOption penOption...);
						* 获取BufferedWriter
						* 有个重载,可以指定 Charset
	Stream<String>	lines(Path path);
						* 返回流
						* 有个重载,可以设置 Charset
	SeekableByteChannel newByteChannel(Path path, OpenOption... options);
						* 获取 SeekableByteChannel 管道
						* SeekableByteChannel 是 FileChannel 的父类
	SeekableByteChannel newByteChannel(Path path,Set<? extends OpenOption> options,FileAttribute<?>... attrs);
						* 同上,可以设置属性
	DirectoryStream<Path>	newDirectoryStream(Path dir);
						* 获取目录流,有俩重载.可以以字符串形式和函数接口的方式设置过滤器
	Stream<Path>	find(Path start, int maxDepth, BiPredicate<Path,BasicFileAttributes> matcher, FileVisitOption... options)
						* 在 start 目录中检索文件,层次深度为:maxDepth
						* 通过调用 matcher 函数接口来过滤文件/文件夹
	
	//=======================遍历
	DirectoryStream<Path> newDirectoryStream(Path dir);
							* 获取 dir 下的所有一级目录和文件
	DirectoryStream<Path> newDirectoryStream(Path dir,String parrten);
							* 获取 dir 下的所有一级目录和文件,可以通过 parrten 表达式来过滤文件或者目录
	DirectoryStream<Path> newDirectoryStream(Path dir,DirectoryStream.Filter<? super Path> filter)
							* 获取 dir 下的所有一级目录和文件,可以通过 filter 表达式来过滤文件或者目录

	Stream<Path>	walk(Path start, FileVisitOption... options)
						* 递归目录,返回 Stream
	Stream<Path>	walk(Path start, int maxDepth, FileVisitOption... options)
						* 同上,多一个 maxDepth 来限制遍历的层次
						* options 来决定是否要跟踪连接

	Path			walkFileTree(Path start, FileVisitor<? super Path> visitor);
						* 通过自己实现 FileVisitor 来完成遍历指定的目录,递归,
	Path			walkFileTree(Path start, Set<FileVisitOption> options,int maxDepth,FileVisitor<? super Path> visitor)
						* 同上,多一个 maxDepth 来限制遍历的层次
						* options 来决定是否要跟踪连接

	//=======================复制
	long			copy(Path path,OutputStream in);
						* 复制 Path 到in流
	Path			copy(InputStream in,Path target,CopyOption... options);
						* 复制 in 中的数据到  target
	Path			copy(Path source, Path target, CopyOption... options)
						* 复制 source 到 target
						* CopyOption 指定复制的规则
	
	//=======================移动
	Path			move(Path source, Path target, CopyOption... options)
						* 移动 source 到 target

	//=======================删除
	void			delete(Path path);
						* 删除,文件不存在会抛出异常
	boolean			deleteIfExists(Path path);
						* 如果文件存在,才执行删除

	//=======================创建(目录)
	Path			createDirectory(Path dir, FileAttribute<?>... attrs)
						* 创建,除了最后一层可以不存在,其他的必须存在,不然异常
						* 说白了,只能创建一级目录
						* attrs 表示文件的属性
	Path			createDirectories(Path dir, FileAttribute<?>... attrs);
						* 中间可以有多个层都不存在,说白了,可以创建N级目录
						* attrs 表示文件的属性
	//=======================创建(文件)
	Path			createFile(Path path, FileAttribute<?>... attrs);
						* 创建文件,如果文件已经存在,则抛出异常
	//=======================创建(临时文件)
	Path			createTempFile(String prefix,String suffix,FileAttribute<?>... attrs)
						* 创建临时文件夹
						* prefix 和 suffix 可以是为null的字符串儿
	Path			createTempFile(Path dir,String prefix,String suffix,FileAttribute<?>... attrs)
						* 创建临时文件夹,基于 dir
	//=======================创建(临时文件夹)
	Path			createTempDirectory(String prefix,FileAttribute<?>... attrs)
						* 创建临时文件夹
	Path			createTempDirectory(Path dir,String prefix,FileAttribute<?>... attrs)
						* 创建临时文件夹,基于 dir
						
	//=======================比较
	long mismatch(Path path, Path path2)
				* 查找并返回内容中第一个不匹配字节的位置
				* 如果找不到返回 -1

----------------------------
Files-Options				|
----------------------------
	CopyOption(接口)
		StandardCopyOption
			REPLACE_EXISTING,		//如果目标已经存在,则覆盖目标
			COPY_ATTRIBUTES,		//复制文件的属性
			ATOMIC_MOVE				//原子性操作,要么移动成功,要么文件还在原处
	
	OpenOption(接口)
		StandardOpenOption(枚举)
			READ,					//打开阅读权限
			WRITE,					//打开写权限
			APPEND,					//在末尾添加,不覆盖以前的数据
			TRUNCATE_EXISTING,		//如果文件已经存在，并且打开进行WRITE 访问，则其长度将被截断为0。
			CREATE,					//如果不存在,则创建新的
			CREATE_NEW,				//创建一个新的文件，如果该文件已经存在失败。
			DELETE_ON_CLOSE,		//在jvm关闭的时候删除文件
			SPARSE,					//稀疏文件
			SYNC,					//要求将文件内容或元数据的每次更新都同步写入底层存储设备。
			DSYNC;					//要求将文件内容的每次更新都与底层存储设备同步写入

		LinkOption
			* 实现类
			* '实现了OpenOption 和 CopyOption'
			NOFOLLOW_LINKS;
	
	FileVisitOption
		FOLLOW_LINKS

----------------------------
Files-FileAttribute			|
----------------------------
	# 表示文件的属性接口,需要自己实现子类

----------------------------
PosixFilePermission			|
----------------------------
	# 文件权限枚举类
		* 所有者权限
			OWNER_READ,
			OWNER_WRITE,
			OWNER_EXECUTE,
		* 所属组权限
			GROUP_READ,
			GROUP_WRITE,
			GROUP_EXECUTE,
		* 其他人权限
			OTHERS_READ,
			OTHERS_WRITE,
			OTHERS_EXECUTE;