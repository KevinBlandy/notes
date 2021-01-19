------------------------
ClassLoader				|
------------------------
	# 类加载器, 是一个抽象类
	
	# 构造函数
		protected ClassLoader()
		protected ClassLoader(ClassLoader parent)
	
	# 静态方法
		protected static boolean registerAsParallelCapable()
		protected final void resolveClass(Class<?> c) 
		
		public static ClassLoader getSystemClassLoader() 
		public static URL getSystemResource(String name)
		public static InputStream getSystemResourceAsStream(String name)
		public static Enumeration<URL> getSystemResources(String name)
	
	# 实例函数

		@Deprecated
		protected final Class<?> defineClass(byte[] b, int off, int len)
		protected final Class<?> defineClass(String name, byte[] b, int off, int len)
		protected final Class<?> defineClass(String name, byte[] b, int off, int len,ProtectionDomain protectionDomain)
		protected final Class<?> defineClass(String name, java.nio.ByteBuffer b, ProtectionDomain protectionDomain)
			* 根据字节数据创建一个类

		protected Class<?> findClass(String name) 
			* 空的方法, 自己实现的加载器建议覆写该方法

		protected Class<?> loadClass(String name, boolean resolve)
		public Class<?> loadClass(String name)
			* 加载方法
				name 类方法名称
				resolve 是否解析初始化类 


		protected Package definePackage(String name, String specTitle,String specVersion, String specVendor, String implTitle, String implVersion,String implVendor, URL sealBase)
		protected String findLibrary(String libname) 
		protected final Class<?> findLoadedClass(String name)
		protected URL findResource(String name)
		protected Enumeration<URL> findResources(String name)
		protected final Class<?> findSystemClass(String name)
		protected Object getClassLoadingLock(String className
		protected Package getPackage(String name)
		protected Package[] getPackages()
		
		protected final void setSigners(Class<?> c, Object[] signers)


		public void clearAssertionStatus()
		public final ClassLoader getParent()
		public URL getResource(String name)
		public InputStream getResourceAsStream(String name)
		public Enumeration<URL> getResources(String name) 
		
		public void setClassAssertionStatus(String className, boolean enabled)
		public void setDefaultAssertionStatus(boolean enabled)
		public void setPackageAssertionStatus(String packageName, boolean enabled)

		
		
	


