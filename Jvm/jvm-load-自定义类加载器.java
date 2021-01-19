-----------------
ClassLoader		 |
-----------------
	# 类加载器的几个关键方法
		protected final Class<?> defineClass(String name, byte[] b, int off, int len)
		protected final Class<?> defineClass(String name, byte[] b, int off, int len,ProtectionDomain protectionDomain)
		protected final Class<?> defineClass(String name, java.nio.ByteBuffer b, ProtectionDomain protectionDomain)
			* 用于把字节互数据解析为JVM能够识别的Class对象
			* 该方法只是会生成对象, 但是还没 resolve

		protected Class<?> findClass(String name) 
			* 自定义加载器, 一般覆写父类的 findClass 方法来完成类的加载

		
		protected Class<?> loadClass(String name, boolean resolve)
		public Class<?> loadClass(String name)

		protected final void resolveClass(Class<?> c) 
			* 解析初始化类, 就是链接

	# 自定义类加载器
		class MyClassLoader extends ClassLoader {
			@Override
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				// 尝试委托加载
				Class<?> clazz = super.loadClass(name);
				if (clazz != null){
					return clazz;
				}
				/**
				 * TODO 自己尝试加载, 如果不存在, 抛出异常:ClassNotFoundException
				 * TODO 如果存在则获取到字节数据, 转换为类对象
				 */

				byte[] data = new byte[1024];
				clazz = defineClass("", data, 0, data.length);
				return clazz;
			}
		}

		* 最好不要重写 loadClass 方法, 因为这样容易破坏双亲委托模式
	
	# 一般都还可以考虑继承:URLClassLoader
		* 因为它帮我们完成了大部分的加载工作

---------------------------------------------
应用场景-加载自定义路径下的class文件		 |
---------------------------------------------

---------------------------------------------
应用场景-需要对自己加载的类做特殊处理		 |
---------------------------------------------

---------------------------------------------
应用场景-热部署								 |
---------------------------------------------