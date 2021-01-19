--------------------
JAVA-动态编译		|
--------------------
	# 传统的运行JAVA程序的方式
		1,编写源代码
		2,编译源代码
		3,调用main函数开始执行程序
	# 实际上,JAVA提供了API用于动态编译的功能.
	# 动态编译的两种做法
		1,通过Runtime调用JAVA,启动新的进程去操作
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("javac -cp -D:/MyJava/ HelloWorld.java");
		2,通过JavaCompiler动态的进行编译(6.0以后引入的新功能)
			public static int compileFile(String sourceFile){
				//模拟JAVA源文件地址
				sourceFile = "E:/MyJava/HelloWorld.java";
				//获取编译器对象
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				/*	执行编译操作
					第一个参数inputStream	:为JAVA编译器提供参数
					第二个参数outputStream	:得到JAVA编译器的输出信息
					第三个参数OutputStream	:接收编译器的错误信息
					第四个参数:可变参数(String[]),能传入一个或者多个JAVA源文件的路径
					返回值:0表示成功编译,非0表示编译失败
				*/
				int result = compiler.run(null,	System.out, System.out, sourceFile);
				if(result == 0){
					System.out.println("编译成功");
				}else{
					System.out.println("编译失败");
				}
				return result;
			}

--------------------
JAVA-动态运行		|
--------------------
	# 还是那个概念,通过JAVA程序去运行JAVA的class文件
	# 两种办法
		1,通过RuntimeAPI启用新的线程去执行
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("java -cp -D:/MyJava/ HelloWorld.java");
		2,通过反射,运行编译好的类
			public static void run(String dir,String classFile)throws Exception{
				//目标目录
				dir = "E:/MyJava/";
				//目标class文件
				classFile = "HelloWorld";
				//创建目录url类
				URL[] urls = new URL[]{new URL("file:/"+dir)};
				//获取url类加载器
				URLClassLoader classLoader = new URLClassLoader(urls);
				//加载指定的class文件
				Class clazz = classLoader.loadClass(classFile);
				//获取其main方法
				Method mainMethod = clazz.getMethod("main", String[].class);
				/*
					执行main方法,传递字空符数组参数
					因为main方法是static方法,所以可以不用传递对象
					# 强制转换的原因
						* 可变参数是jdk5之后的东西
						* 如果说不把这个String[]转换为Object的话
						* String[]里面的每个参数,都会被当作一个新的String[]被main方法加载
						* 而main方法的参数只有一个
				*/
				mainMethod.invoke(null, (Object)new String[]{});
			}