----------------------
URLClassLoader		  |
----------------------
	# 自定义类加载器一般都实现该接口
	
	# 构造函数
		public URLClassLoader(URL[] urls)
		public URLClassLoader(URL[] urls, ClassLoader parent) 
		public URLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory)
		
