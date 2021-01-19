-----------------
ClassLoader		 |
-----------------
	# 类和类加载器
		* 每个类, 都要和加载它的加载器一起, 确立在jvm中的唯一性

		* 比较两个类是否相等, 只有在这两个类都是同一个类加载器加载的前提下才有意义
		* 否则, 一个class文件, 被一个jvm加载, 但是存在两个加载器进行加载, 那么这俩加载后的类就肯定不相等

		* 这里的相等包括了 Class 对象的: equals(), isAssignableForm(), instance() 返回的结果
		* 也包括使用关键字: instanceof 判断结果


		
	
	# 类加载类型
		* 启动类加载器 Bootstrap ClassLoader
			* 在 $JAVA_HOME\lib 目录中, 或者通过 -Xbootclasspath 参数指定
			* 使用 C++ 实现, 是JVM自身的一部分, 不能被程序代码获取到
			* 查看它的加载目录:System.getProperty("sun.boot.class.path")
		
		* 扩展类加载器 Extension ClassLoader
			* 由: sun.misc.Launcher$ExtClassLoader 实现, 实现接口:ClassLoader
			* 它负责加载 $JAVA_HOME\lib\ext 目录中的, 或者被 java.ext.dirs 系统变量指定的路径中的所有类库
			* 开发者可以直接使用扩展类加载器
			* 查看它的加载目录:System.getProperty("java.ext.dirs")

		* 应用类加载器 Application ClassLoader
			* 由: sun.misc.Launcher$AppClassLoader 实现, 实现接口:ClassLoader
			* 由于这个类加载器是 ClassLoader 中的 getSystemClassLoader() 方法的返回值, 所以也被称为系统类加载器
				ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			
			* 它负责加载用户类路径(CLASS_PATH)上所指定的类库
			* 如果程序中没自定义过类加载器, 一般情况下这个就是默认的类加载器
			* 查看它的加载目录:System.getProperty("java.class.path")
			
				Class<?> driverClass = Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println(driverClass.getClassLoader()); // sun.misc.Launcher$AppClassLoader@18b4aac2
		
		* 程序里面获取各个加载器
			ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
			System.out.println(appClassLoader); //sun.misc.Launcher$AppClassLoader@18b4aac2

			ClassLoader extClassLoader = appClassLoader.getParent();
			System.out.println(extClassLoader);	//sun.misc.Launcher$ExtClassLoader@cc34f4d

			ClassLoader bootstrapClassLoader = extClassLoader.getParent();
			System.out.println(bootstrapClassLoader);	// null ,C++实现,用户代码无法获取

		
	# 双亲委派机制
		* 双亲委派机制, 要求除了启动类加载器以外, 都必须要有父类加载器
		* 子父关系并不是由继承实现的, 而是使用组合的方式实现的

		* 如果一个类加载器收到了类加载请求, 它首先会把这个请求委托给父类加载器去完成
		* 每一个类加载器都是如此, 因此最后会委托到启动类加载器进行加载
		* 父级启动类加载器成功加载, 那么就直接返回加载后的结果
		* 如果父类无法加载, 就尝试自己加载, 如果加载失败, 则抛出异常: ClassNotFoundException

		* 这种机制的好处在于, 你没法使用自己的加载器,加载自己定义的那些与系统类重名的类, 安全
		* ClassLoader 的 loadClass(String name) 方法实现代码
			protected Class<?> loadClass(String name, boolean resolve)throws ClassNotFoundException {
				synchronized (getClassLoadingLock(name)) {
					// 首先, 检查类是否被加载过
					Class<?> c = findLoadedClass(name);
					if (c == null) {
						long t0 = System.nanoTime();
						try {
							if (parent != null) {
								c = parent.loadClass(name, false);
							} else {
								c = findBootstrapClassOrNull(name);
							}
						} catch (ClassNotFoundException e) {
							// 父级加载器抛出了 ClassNotFoundException , 说明父级加载失败
						}

						if (c == null) {
							// 父级加载器, 加载失败的情况下, 调用本身的 findClass() 进行加载

							long t1 = System.nanoTime();
							c = findClass(name);

							// 定义的类加载器的记录统计数据
							sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
							sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
							sun.misc.PerfCounter.getFindClasses().increment();
						}
					}
					if (resolve) {
						resolveClass(c);
					}
					return c;
				}
			}
		
		* 就算是自己定义了类加载器, 使用 defineClass() 去加载一个以 "java.lang" 开头的类, 也不会成功
		* 会得到JVM抛出的安全异常
	
	# 破坏双亲委托机制
		* 双亲委派机制, 并不是强制性的, 而是推荐给开发者的加载器实现模式
		
		* 双亲委派机制第一次被破坏, 是因为历史遗留问题
		* 该机制在 jdk1.2 才被引入, 但是类加载器和抽象类: ClassLoader 在 jdk1.0 就存在
		* 对于那些, 已经存在的用户自定义加载器实现, 不得不做出妥协,

		* 为了向前兼容, JDK1.2 后在 ClassLoader 添加了一个方法: protected Class<?> findClass(String name)
		* 在此之前, 用于继承 ClassLoader 方法, 唯一目的就是为了覆写: loadClass() 方法, 因为JVM在进行类加载的时候会调用私有方法:private Class<?> loadClassInternal(String name) 
		* 而这个方法的唯一逻辑就是去调用自己的: loadClass() 方法

		* JDK1.2后不建议覆写: loadClass() 方法, 而是把加载逻辑写到 findClass() 方法中
		* loadClass() 方法里面的逻辑, 当父加载器加载失败, 才会调用 findClass() 完成加载
		* 这样的规则是符合双亲委派机制的

	
	
		* 双亲委派机制第二次被破坏, 是因为这个模型自身的缺陷导致的
		* 该机制很好的解决了基础类的统一加载的问题(越是基础的类,越由最上层的加载器进行加载)

		* 但是基础的类, 可能需要回调用户的代码, 例如: JNDI
		* JNDI的目的是最资源进行集中管理和查找, 它需要调用由厂商实现, 并且部署在classpath下的JNDI接口提供者的代码
		* 但是启动类加载器, 不认识这些代码

		* 为了解决这个问题, Java设计团队鼓捣了一个 线程上下文加载: Thread Context ClassLoader(其实设计得不太优雅)
		* Thread 的实例有一个方法:public void setContextClassLoader(ClassLoader cl)
		* 如果创建线程的时候, 该加载器还没设置, 它将会从父级线程继承一个, 如果应用程序全局范围内都没设置过的话, 那么这个类加载器默认就是应用程序类加载器

		* JDNI服务使用这个线程上线我的加载器去加载所需要的SPI代码, 也就是父类加载器请求子类加载器去完成类加载
		* 这种行为, 实际上就是打通了双亲委派模型的层次结构来逆向使用加载器
		* 已经违背了双亲委派模型的一般性原则, 但是没则, JAVA涉及 SPI的加载动作, 都基本才用这种方式



		* 双亲委派机制的第三次被破坏, 是因为对程序的动态性追求导致的
		* 动态性: 代码热替换, 模块热部署
		* 目前OSGi已经是业务事实上的Java模块标准, 而OSGi实现模块化热部署的关键就是它自定义的类加载器机制的实现

		* 每一个程序模块都有一个自己的类加载器, 当需要更换一个 Bundle 的时候, 就把 Bundle 连加载器一起换掉, 实现代码的热替换
		* 在OSGi环境下, 类加载器不是双亲委派模型中的树结构,而是更加复杂的网状结构

		* OSGi加载类的搜索顺序
			1. 把 java.* 开头的类委派给父类加载器加载
			2. 否则, 把委派列表名单内的类委派给父类加载器加载
			3. 否则, 把 import 列表中的类委派给 Export 这个类的 Bundle 的类加载器加载
			4. 否则, 查找当前 Bundle 的 ClassPath, 使用自己的类加载器加载
			5. 否则, 查找类是否在自己的 Fragment Bundle 中, 如果在, 则委托给 Fragment Bundle 的类加载器加载
			6. 否则, 查找 Dynamic import 列表的 Bundle, 委派给对应的 Bundle 的类加载器加载
			7. 否则, 类查找失败

		* 只有开头2点符合双亲委派规则, 其余的类查找都是在平级的类加载器中进行的
		





		