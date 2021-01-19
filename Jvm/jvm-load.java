--------------------------
类加载机制				  |
--------------------------
	# 与编译时需要进行连接工作的语言不同
		* Java语言, 类型的加载, 连接, 和初始化过程都是运行时完成的
		* 这种策略会让类的加载增加性能开销, 但是可以为Java应用程序提供很高的灵活性

		* Java是天生可以动态扩展的语言, 特性就是依赖运行期间的动态加载和动态连接这个特点实现的
	
	# 类的生命周期
		1.加载(Loading)
		2.验证(Verification)
		3.准备(Preparation)
		4.解析(Resolution)
		5.初始化(Initialization)
		6.使用(Using)
		7.卸载(Unloading)

		* 2,3,4 步骤就是连接步骤
		* 通常 1,2,3,5,7 是确定的, 类的加载顺序不会改变
		* 解析阶段不一样, 它在某些情况下可以在初始化后解析, 这是为了支持Java语言的动态绑定
	
	# JVM规范规定了只有5种情况必须对类进行初始化
		* 加载, 验证, 准备 肯定是需要先执行的
		* 除了以下五种情况外, 所有引用类的方式都不会触发初始化
		
		1. new ,getstatic, putstatic, invokestatic
			* 通俗理解就是创建对象, 读取, 写入 static 属性, 执行 static 方法
			* 注意, 被 final 修饰属性的除外, final 修饰的属性已经在编译时候就把结果放入了常量池
		
		2. 使用 java.lang.reflect 包的api对象类进行反射调用的时候, 如果类没初始化, 必须先初始化

		3. 初始化一个类的时候, 如果其父类还没初始化, 就会先初始化其父类
		
		4. 当JVM启动的时候, 用户需要指定一个执行的主类, main 函数类, JVM会先初始化这个类

		5. 对使用JDK7的动态语言支持的时候,
			* 如果一个 java.lang.invoke.MethodHandle 实例最后的解析结果是: REF_getStatic,REF_putStatic,REF_invokeStatic的方法句柄
			* 并且这个方法句柄所对应的类还没有初始化, 就会执行初始化
	
	# 打印JVM的类加载/卸载
		-XX:+TraceClassLoading
			* 打印类加载信息

		-XX:+TraceClassUnLoading
			* 打印类卸载信息
	
	# 接口的加载过程和类的加过程有点不同
		* 接口也有初始化过程, 跟类一致
		* 接口不能使用 static {} 语句块,
		* 编译器仍然会为接口生成 <cinit>() 类构造器, 用于初始化接口中所定义的成员变量

		* 接口在初始化的时候, 并不要求其父接口都完成了初始化, 只有用到父接口的时候, 才会初始化(如:引用接口中定义的变量)
		* 这点跟JVM规范中的第3点相悖

-------------------------------
类被引用, 但是不会初始化场景   |
-------------------------------
	# 对于静态字段, 只有直接定义这个字段的类才会被初始化
		* 通过子类触发父类的静态字段, 只有父类会被初始化, 子类不会初始化

		public class SuperClass {
			public static int value = 1;
			static{
				System.out.println("SuperClass Load...");
			}
		}
		public class SubClass extends SuperClass {
			static {
				System.out.println("SubClass Load...");
			}
		}

		// main函数执行
		System.out.println(SubClass.value);

		//打印结果
		SuperClass Load...
		1
	
	# 通过数组来使用引用类, 不会触发类的初始化
		package io.javaweb.jvm;
		public class Foo {
			static {
				System.out.println("Foo load...");
			}
		}
		Foo[] foos = new Foo[10];
		// 没有任何输出
	
		* 但是该改代码触发了另外一个名为: [Lio.javaweb.jvm.Foo 的类初始化阶段
		* 这是一个由JVM自动生成, 直接继承 java.lang.Object 的子类, 创建动作由字节码指令 newarray 触发
		* 这个类表示了一个元素类型为:io.javaweb.jvm.Foo的一维数组, 数组中应用的属性和方法都实现在该类
			* 可以直接使用的, 被 public 修饰的属性和方法
			length 属性
			clone() 方法
		* java比C/C++相对安全是因为这个类封装了数组元素的访问方法, 而C/C++直接翻译为指针的移动
		* Java检查到数组越界就会给出异常: ArrayIndexOutOfBoundsException
	

	# 访问 final 修饰的属性不会触发类的初始化
		* 常量在编译阶段就会存入调用类的常量池中, 本质上并没有引用到定义常量的类, 因为不会触发定义常量的类的初始化

		class Foo {
			public static final int value = 0;
			static {
				System.out.println("Foo load...");
			}
		}

		public class Main {
			public static void main(String[] args) {
				System.out.println(Foo.value); // 0
			}
		}


		
		* 访问类的 final 修饰方法, 会触发初始化
		* 在编译阶段通过常量的传播优化, 已经把常量的值: 1 ,存储到了 Main 的常量池中
		* 以后 Main 类对常量:Foo.value的引用, 实际上都被转换为 Main 类对自身常量池的引用

		* 实际上Main的class文件之中并没 Foo 类的符合引用入口, 这两个类在编译成 class 后就没得任何联系了
	
	

	
				