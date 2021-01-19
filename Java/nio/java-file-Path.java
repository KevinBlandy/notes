------------------------
Path					|
------------------------
	# 是一个接口,jdk1.7的新东西
	# 表示一个文件或者目录,也许它不存在
	# 构造(三种方式)
		1, FileSystems 静态方法
			Path path = FileSystems.getDefault().getPath("F:/test/jdk7", "test.txt");  

		2,通过 File 对象获取
			File file = new File("F:/test/jdk7/test.txt");  
			Path path = file.toPath();  

		3,Paths 静态方法
			Path path = Paths.get("F:/test/jdk7", "test.txt"); 
				* 第二个参数是可变长参数,标识层级目录

------------------------
Path-实例方法			|
------------------------
	boolean			endsWith(Path other)
	boolean			endsWith(String other)
						* 是否以指定的形式结尾
	boolean			startsWith(Path other)
	boolean			startsWith(String other)
						* 是否以指定的形式结尾
	boolean			isAbsolute();
						* 判断是否不是绝对路径
	Iterator<Path>	iterator();
						* 返回迭代器
						* 未知
	Path			resolve(Path path);
	Path			resolve(String path);
						* 如果参数是绝对路径,就返回参数,不然返回当前对象+参数路径的对象

	Path			resolveSibling(Path path);
	Path			resolveSibling(String path);
						* 如果 path 是绝对路径,就返回path表示的对象
						* 不然返回 this 的父路径 + path 表示的路径对象

	Path			relativize(Path path);
						* 使用 this 进行解析,相当于的 path 的路径

	Path			normalize();
						* 删除一些符号[.],[..]
						* 冗余的路径表示元素

	Path			toAbsolutePath();
						* 返回绝对路径
	File			toFile();
						* 转换为file对象
	URI				toUri();
						* 转换为URI
	int				compareTo(Path path)
						* 判断两个文件对象是否相同
	int				getNameCount();
						* 获取文件所在的节点层数,可以理解为在目录的第几层了
	Path			getFileName();
						* 获取路径中文件的名称
	Path			getRoot();
						* 返回根目录,如果没有根目录,返回null
	FileSystem		getFileSystem();
						* 获取文件系统对象
	Path			getParent();
						* 获取当前Path的父级path
	Path			getName(int x );
						* 获取指定层次的Path对象
						* 如果超过了或者为负数,会抛出异常
	Path			subpath(int beginIndex,int endIndex);
						* 获取子路径的Path
						* demo
							Path path = Paths.get("E:\\ext-6.2.0\\index.html");
					    	System.out.println(path.subpath(1, 2));		//index.html
	Path			toAbsolutePath();
						* 转换为绝对路径
	Path			toRealPath(LinkOption... options);
						* 获取真实路径
	
	Spliterator<Path>	path.spliterator();
						* 未知
	WatchKey register(WatchService watcher,WatchEvent.Kind<?>... events)
						* 绑定监听	

------------------------
Path-静态方法			|
------------------------
	