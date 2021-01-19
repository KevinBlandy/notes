------------------------------
模块化
------------------------------
	# Java模块系统 (Jigsaw)
	# 没有模块化带来的问题
		* Java运行环境越来越膨胀
		* JVM加载 rt.jat, 不管其中的类是否被JVM加载, 整个jar文件都会被加载到内存中去
	
	# 模块化后的Java
		* 之前存放在 tool.jar 里面的合并到了 jdk.* 的模块里面
		* 替化 rt.jar,tools.jar, 是 JDK9/jmods 文件下 *.jmod 文件
	
	# 本质
		* 就是在 package 的基础上, 再提供了一个 module 的概念
	
	# 有了模块的概念后 public 的 class 不再是绝对能访问的了
		* exports: 控制着哪些包可以被其它模块访问到
		* equires: 指明对其它模块的依赖 
		
		* 所有不被导出的包默认都被封装在模块里面
		* 先有模块的可读性, 进一步才是模块内的可访问性(public)

	
	# 模块的名称
		* 跟包名一样, 不能重复
		* 荐方式是使用反转域名的方式
		* 模块的名字经常是它的输出的包的前缀
			module java.sql {
				exports java.sql;
				exports javax.sql;
			}
	
	# 模块可以作为一个模块工作(在java9以后), 也可以作为一个普通的jar文件工作(java8及其以前)
	
	# Java 9缺省情况下只能访问导出包的public类, 方法和字段
		* 即使使用反射访问非 public 成员并设置setAccessible(true), 也不能成功访问这些成员

------------------------------
模块制定
------------------------------
	# 在classpath的根目录创建文件: module-info.java
		module module1 {

			// 暴露一个java的包
			exports io.java.beans;
		}
		
		* module 声明当前模块的名称
		* exports 暴露的java包
	
	# 指定模块, 可以使用暴露的模块
		module module1 {
			exports io.java.beans to com.foo;
		}

		* 通过 to 关键字指定, 仅对 com.foo 模块 暴露当前模块中的 io.java.beans 包
		* 其他模块 requires 当前模块后, 不能访问 io.java.beans 包
		* to 后面可以指定多个, 使用逗号分隔
	
	# 允许反射操作非 public 成员
		* jdk9之前, 可以任意反射读取各种类中的'私有成员', 这样破坏了封装
		* 暴露出去的模块, 必须给反射'私有成员'的权限, 引用模块才能进行反射操作'私有成员'
		* 只有 public 权限不受影响
		* 否则会抛出异常
			 java.lang.reflect.InaccessibleObjectException: Unable to make field private xxx accessible: xxx does not "xxx" to module xxx
			
		* 允许操作所有暴露出去的模块的私有变量, 在 module 前面添加 open 关键字
			// 允许反射操作, 暴露出去的模块中, 所有类中的私有变量
			open module module1 {
				exports io.java.beans;
			}
		
		* 允许操作暴露出去模块中的, 指定包下的类的私有变量, 在 module 里面通过 opens 定义
			module module1 {
				// 允许反射操作 暴露出去的模块中,  io.java.beans 包下的类的私有变量
				opens io.java.beans;
			}
		
		* 仅仅对指定的模块暴露反色操作私有属性的权限,
		* 使用 to 关键字指定多个模块, 使用逗号分隔
			module module1 {
				opens io.java.beans to module2, module3;
			}

		
------------------------------
模块导入
------------------------------
	# 在classpath的根目录创建文件: module-info.java
		module module2 {

			// 引入一个模块
			requires module1;
		}

		* requires 引入指定的模块
	
	# 间接引用
		* 使用 transitive 关键字修饰当前模块的引用
		* 其他模块引用本模块, 会自动的引用 'transitive' 修饰的模块(类似于引用传递)
			module module1 {
				// 引用 model1 的模块, 会自动引入 transitive java.logging 模块
				requires transitive java.logging;
			}
	
	# 编译依赖
		* 跟maven一样, 仅仅需要编译的时候依赖, 程序中并不会使用
		* 使用 static 关键字修饰
			requires static java.logging;

		
------------------------------
模块的服务和消费模型
------------------------------
	# 生产者
		module module1 {
			// 暴露服务接口所在的包
			exports io.java.service;

			// 暴露服务接口, 以及提供服务的实现类。如果暴露多个，使用逗号分隔
			provides io.java.service.TestService with TestServiceImpl1, TestServiceImpl2;
		}

		* 抽象的接口
			public interface TestService {
				void call();
			}
	
	# 消费者
		module module2 {
			// 导入依赖
			requires module1;

			// 声明使用依赖中暴露的服务接口
			uses io.java.service.TestService;
		}

		* 通过 ServiceLoader 获取到服务实现
			// 通过 ServiceLoader 获取到服务实现
			ServiceLoader<TestService> serviceLoader = ServiceLoader.load(TestService.class);

			// 遍历所有的实现
			serviceLoader.forEach(testService -> {
				testService.call();
			});

			// 获取第一个实现
			serviceLoader.findFirst().orElseThrow().call();

---------------------------------
编译和运行模块
---------------------------------
	# 编译的目录
		|-src
			|-com
				|-foo
					|-main
						|-Main.java
			|-module-info.java
		|-mods
			|-com.foo
				|-com
					|-foo
						|-main
							|-Main.class
				|-module-info.class 
	
	# 编译: javac -d [model] [module-info.java] [main.java]
		javac -d mods/com.foo src/module-info.java src/com/foo/main/Main.java
			
			-d					指定模块的生成的目录和模块名称
			module-info.java	指定 model-info 类
			main.java			指定main函索类


			
		
	# 运行: java --module-path [path] -m [module]/[main]
		java --module-path mods -m com.foo/com.foo.main.Main

			--module-path	指定模块所在的目录
			-m				指定模块名称和Main函数类


---------------------------------
使用 jlink 工具 定制运行环境(打包)
---------------------------------
	jlink
		--module-path 
		--add-modules
		--output

---------------------------------
模块化的原理
---------------------------------