---------------------------
JVM-运行和类加载全过程		|
---------------------------
	# 研究这东西有啥用?
		1,有助于了解JVM运行过程
		2,更深入了解Java动态性(热部署,动态加载),提高编程的灵活性
	# java  xxx
		* 就是启动了JVM去加载类
	# 类加载机制
		* JVM把class文件加载到内存,并对数据进行校验解析和初始化.最终形成JVM可以直接使用的Java类型的过程
			加载 --> 验证 --> 准备 --> 解析 --> 初始化 --> 使用 --> 卸载
		* 大致分为三步:连接 --> 加载 --> 初始化
	# 加载
		把class文件字节码加载到内存中,并将这些静态数据转换成方法区中的运行数据结构.在堆中生成一个代表这个类的java.lang.Class对象,作为方法区类数据的访问入口

	# 连接
		把二进制代码合并到jvm的运行状态之中的过程
		* 验证:确保被加载的类符合jvm规范,没有安全方面的问题
		* 准备:正式为变量(static)分配内存并设置类变量初始值的阶段,这些内存都会在方法区中进行分配
		* 解析:虚拟机常量池内符合引用替换为直接引用的过程

	# 初始化
		* 初始化节点是执行类构造器的过
	
	# 类只有在引用的时候,才会被初始化
		1,主动引用(会发生类的初始化)
			* new 一个对象
			* 调用静态成员
			* 使用反射包中的工具类进行反射操作的时候
			* main方法所在的类,肯定是最先被加载的
			* 初始化一个类的时候,如果父类没有被初始化,则先会初始化父类

		2,被动引用(不会发生类的初始化)
			* 


---------------------------
JVM-类加载器				|
---------------------------
	# 引导类加载器(Bootstrap Class Loader)
		* 它用来加载java的核心库存(${java_home}/jre/lib/rt.jar),和 sun.boot.class.path 路径下的内容
		* 是原生代码来实现的,并不继承自 java.lang.ClassLoader
		* 它还负责加载'扩展类'和'应用程序类加载器'.并指定他们父类加载器
		* 这东西是用C++实现的
	

	# 扩展类加载器(Extensions Class Loader)
		* ClassLoader 子类
		* sun.misc.Launcher$ExtClassLoader 
		* 用来加载java的扩展库(${java_home}/jre/ext/x*x.jar),和 java.ext.dirs 路径下的内容
	
	# 应用程序类加载器(Application Class Loader)
		* ClassLoader 子类
		* sun.misc.Launcher$AppClassLoader
		* 它根据java应用的类路径(classpath,java.class.path)
		* 一般java应用类,都是由它加载的
	
	# 自定义类加载器
		* 直接继承 java.lang.ClassLoader 
		* 实现自己的类加载器,以便满足一些特殊的需求
	
---------------------------
JVM-java.lang.ClassLoader	|
---------------------------
	# 是扩展类加载器和应用程序加载器,以及自定义类加载器的父类
	# 它的任务就是根据一个类的名称,找到其字节代码,定义出一个java类,也就是 java.lang.Class 的实例
	# ClassLoader 还负责加载java应用所需的资源,如,图像文件和配置文件等
	# 方法
		getParent();		
			* 返回该类的父类加载器,谁加载的我,我就返回谁
		loadClass(String name);
			* 加载指定名称的类,返回的结果是 java.lang.Class 实例
		findClass(String name);
			* 加载指定名称的类,返回的结果是 java.lang.Class 实例
		findLoadedClass(String name);
			* 返回已经加载过的指定名称的类,返回 java.lang.Class 实例
		defineClass(String name,byte bytes[],int off,int len);
			* 把字节数组中的内容转换为JAVA类,返回结果是 java.lang.Class 实例
			* 该方法不允许被覆写, final 修饰
		resolveClass(Class<?> clazz);
			* 连接指定的JAVA类
	
	# 委托机制
		* ... ... 

---------------------------
JVM-自定义类加载器			|
---------------------------
	# 实现的思路/流程
		0,继承
			* 继承 ClassLoader
		1,检查
			* 首先检查请求的类型是否已经被这个类加载器加载到命名空间中了
			* 如果已经加载,直接返回
		2,委派
			* 委派加载请求给父类加载器,如果父类加载器能够完成,则返回父类加载器加载的实例
		3,findClass
			* 调用本类的的findClass方法,试图获取对应的字节码
			* 成功获取,则调用defineClass导入类型到方法区
			* 获取失败,则返回异常给loadClass,loadClass抛出异常,终止加载过程

	# 同一个类,被两个类加载器加载.JVM会认为是不同的类

	# 文件类加载器
		/**
		 * Created by KevinBlandy on 2017/1/25 15:52
		 * 自定义类加载器,实现 ClassLoader类
		 */
		public class MyClassLoader extends ClassLoader{
			/**
			 * 根路径
			 */
			private String rootDir;
			public MyClassLoader(String rootDir){
				this.rootDir = rootDir;
			}

			/**
			 * 覆写 findClass方法
			 * @param name
			 * @return
			 * @throws ClassNotFoundException
			 */
			@Override
			protected Class<?> findClass(String name)throws ClassNotFoundException{
				/**
				 * 1,先检索该类是否被加载
				 */
				Class<?> clazz = super.findLoadedClass(name);
				if(null != clazz){
					return clazz;       //已经被加载,直接返回
				}
				/**
				 * 2,委托父类加载器进行加载
				 */
				ClassLoader parent = this.getParent();
				clazz = parent.loadClass(name);
				if(null != clazz){
					return clazz;       //父类加载OK,直接返回
				}
				/**
				 * 3,读取文件,转化为字节数组
				 */
				byte[] classBytes = loadLoclaClass(name);
				if(null == classBytes){
					//没有成功加载,抛出异常
					throw new ClassNotFoundException(name+"类未找到");
				}
				//找到OK,进行加载操作
				clazz = super.defineClass(name,classBytes,0,classBytes.length);
				return clazz;
			}
			/**
			 * 加载本地Class字节码
			 * @return
			 */
			private byte[] loadLoclaClass(String classNme){
				BufferedInputStream in;
				ByteArrayOutputStream byteArrayOutputStream;
				String path = rootDir + "/" + classNme.replace(".","/") + ".class";
				try {
					in = new BufferedInputStream(new FileInputStream(path));
					byte[] bytes = new byte[1024];
					int len = 0;
					byteArrayOutputStream = new ByteArrayOutputStream();
					while ((len = in.read(bytes)) != -1){
						byteArrayOutputStream.write(bytes,0,len);
					}
					return byteArrayOutputStream.toByteArray();
				}catch (Exception e){
					return null;
				}finally {
					//关闭流
				}
			}
		}

	# 网络类加载器
		
	# 加密解密类加载器(取反,DES对称加密解密)




	



			

		
	