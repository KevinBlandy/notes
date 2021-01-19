----------------------------
JAVA8新特性					|
----------------------------
	* JDK5是一个里程碑的迭代,那么JDK8也可以算是里程碑的迭代了
	* 新特性
		1,Lamda表达式
		2,Nashorn JavaScript引擎
		3,新的日期与时间API
		4,一套简洁的配置文件
		5,从JVM中去除了'永久代(permanent generation)'
		6,增强的注解功能
	* 学习网站
		http://blog.csdn.net/ioriogami/article/details/12782141/

		http://www.jb51.net/article/48304.htm
	
		http://www.importnew.com/11908.html#methodReferences
	* 知识点
		* Lambda条件,语法,注解
		* 预定义 Lambda 函数接口
		* 方法引用
		* 复合Lambda表达式

----------------------------
Lambda-初识					|
----------------------------
	* Lamda表达式,是一种基于函数的编程语言.
	* 典型的代表就是Haskell,这个语言就是基于函数的编程语言
	* 从JAVA最早开始,提倡的都是面向对象编程
	  也就是说,所有的功能都是起始于类,我们把功能写在类中
	  于是,很多习惯了函数编程的开发者就觉得不好用.于是在大约20年后,java就推出了Lamda表达式
	* 我们要明确的是,并不是有了Ladma表达式出现之后才有基于函数的编程.其实之前JAVA就具备,只是实现的方式不一样
	* 其实当年的实现方式就是传说中的:匿名内部类
		interface Test{
			void test(String str);
		}
		public class Main{
			public static void main(String[] args){
				new Test(){
					public void test(String str) {
						System.out.println(str);
					}
				} .test("你好");
			}
		}
	* Ladam表达式
		package com.kevin.main;
		interface Test{
			void test(String str);
		}
		public class Main{
			public static void main(String[] args){
				Test test = (str)-> System.out.println(str);
				test.test("你好");
			}
		}
		* 很显然,使用了Ladam表达式后,代码量少了
	*　(str)-> System.out.println(str);  详解
		1,(str)			//参数部分,名称随意,但是该类型与接口方法定义的方法一致,不用进行数据类型的声明
			* 追求完美的用户,可以手动的声明类型
			* Test test = (String s)-> System.out.println(s);
		2,->			//固定语法,表示把我们的参数指向我们的方法体
		3,System.out...	//方法体
			* 其实就是我没最早在编写匿名内部类的方法实现代码
			* 这里可以操作(变量),参数部分中传递进行的变量
			* 在使用Ladma表达式的时候,有一个非常重要的要求.
	  '接口里面,只能定义一个抽象方法'
	* 其实Lamda表达式最重要的目的就是解决匿名内部类的问题,顺道吸收点开发人员

----------------------------
Lambda-表达式	详解		|
----------------------------
	* Lamda的语法包括三个部分
		1,参数列表
		2,箭头符号
		3,代码块
	* 使用 Runnable 来演示Lamda表达式
		* 原始方式就略过不说了
			public class Main{
				public static void main(String[] args){
					int num = 1;
					Runnable runnable = () -> {
						/*
							Lamda表达式可以直接访问外部方法中的变量
							如果是匿名内部类,那么该变量必须是final的
							但是要注意的是:Lamda表达式对外部变量仅仅只能是读取,不能进行修改
							* 如果在这里执行num++那么就会报错
						*/
						System.out.println("你好:"+num);
					};
					runnable.run();
				}
			}
			* 很显然,直接就执行了

	* 关于代码块大括号的问题
		1,如果只有一行话,那么可以不加
		2,如果出现 return 关键字,必须加
	
	* return 关键字的问题
		2,如果里面的表达式,可以一句话完成,都不用加 return 
			(x,y) -> x + y;

----------------------------
Lambda-目标类型				|
----------------------------
	# 初步识别
		Runnable run = ()-> {};					
		Object obj = run;						//Ok

		Object obj = () -> {};					//异常

		Object obj = (Runnable)() -> {};		//OK
	
	# 所谓的目标类型就是说,一个 Lambda表达式,所在的类,的类型!
	# 一个 Lambda 表达式,必须存在一个目标类型('废话呢,Lambda是方法,方法必须存在于类')

----------------------------
Lambda-函数接口				|
----------------------------
	# 如果一个接口,仅仅只有一个显式声明的函数,那么该接口可以被定义为函数接口 
	# 可以使用 @FunctionalInterface 标识该接口(也可以不标识),可以用作检查
	# demo
		@FunctionalInterface
		public interface Runnable{
			void run();
		}

		@FunctionalInterface
		public interface MyRunable{
			void run();
			boolean equals(Object obj);
		}
		* 该接口也是属于函数接口,虽然里面多定义了个 equals ,但是这个 equals 是属于 Object

----------------------------
Lambda-子父类				|
----------------------------
	

----------------------------
JAVA8-	java.util.functin 	|
----------------------------
	# jdk定义了N多的目标类型,防止我们重复的定义 lanmbda 表达式
	# 统一在 java.util.functin 包下

		@FunctionalInterface	//函数形接口
		public interface Function<T, R> {
			R apply(T t);
		}
		* 有参数,且需要返回值
	
		@FunctionalInterface	//供给形接口
			public interface Supplier<T> {

			T get();
		}
		* 无参数,指定返回值类型,经常用于只注重过程的代码

		@FunctionalInterface	//消费形接口
		public interface Consumer<T> {
			void accept(T t);
		}
		* 不需要返回值,有参数,经常用于迭代

		@FunctionalInterface	//判断形接口
		public interface Predicate<T> {
			boolean test(T t);
		}
		* 返回 true/false 经常用于判断
	
	# 这几个类都定义了一些静态方法,在使用 Lambda 表达式的时候可以用到
	# 还有许多许多的
	# 为了避免泛型的拆装箱带来的性能消耗，java提供了原始数据类型的各种函数接口
		IntPredicate  // boolean test(int value);
		LongConsumer  // void accept(long value);
		...

		* 不同的数据类型，和不同的函数接口，都有对应的基本数据类型版本实现
	
	# 为了避免在返回数据的时候，有拆装箱的问题，还提供了不同基本数据类型返回值的函数接口
		 ToIntFunction<T> // int applyAsInt(T value);
		 ToDoubleBiFunction<T, U>	// double applyAsDouble(T t, U u);
		 ...
		
	# 甚至还有各种基本数据类型转换的函数接口
		DoubleToLongFunction // long applyAsLong(double value);
		....

	
----------------------------
JAVA8-	方法引用			|
----------------------------
	# Lambda 表达式,其实就是一个方法,而这个仅仅是Lambda的语法糖
	# 当这个Lambda表达式存在的意义,就是仅仅是调用另一个方法的时候,我们可以直接忽略掉这个 lambda 表达式.
	# 也就是说,传递的不是Lambda表达式,而直接是要调用的那个方法,只是说代码的书写形式不同
	# 什么时候适合用/
		* 当一个Lambda表达式仅仅调用了一个已存在的方法
		* 而且这个行法的参数列表以及返回值要与当前Lambda接口方法的参数列表与返回值一样

	# 什么时候不适合用
		* 当我们需要往引用的方法传其它参数的时候，不适合，如下示例：
		IsReferable demo = () -> ReferenceDemo.commonMethod("向这个方法传递参数");

	# 几种调用方式
	1,构造器引用
		* 说白了,Labmda里面其实就是创建对象
		* 会根据Lambda接口的形参,去调用对应的构造函数
		* 格式
			类名::new
		* Demo
			Function<Integer,User> fun = User:new;	//Function<Integer,User> fun = (age) -> {return new User(age)};

	2,静态方法引用
		* Lambda里面只是调用一个静态方法
		* 格式
			类名::静态方法名
		* Demo
			Consumer<String> consumer = System.out:println;		//Consumer<String> consumer = (str) -> {System.out.println(str)};


	3,特定类(任意对象)的方法引用
		* Lambda表达式中是调用了一类对象(一个类,任意对象),的方法
		* Lambda接口方法中,如果第一个参数是方法的调用者,而第二个参数则是第一个参数调用方法的形参
		* 格式
			类名::实例方法名
			BiPredicate<String,String> test = String::equals;	//BiPredicate<String,String> test = (s1,s2) -> s1.equals(s2};


	4,特定对象的方法引用
		* Lambad 里面只是调用了另一个对象,(指定了对象)的方法
		* 格式
			对象::实例方法名
			Object obj = o::method;
		
		* Demo
			User user = new User();
			Supplier<String> supplier = user::getName;	//Supplier<String> supplier = () -> user.getName();
			String name = supplier.get();
	
	5,数组引用
		*  lambda接口方法如果传递参数,就会被当作数组的长度
		* 格式
			Type:new
		* Demo
			Function<Integer,String[]> fun = String[]::new; //Function<Integer,String[]> fun = (x) -> {return new String[x]};

----------------------------
JAVA8-	复合Lambda表达式	|
----------------------------
	# 其实就是'把多个简单的Lambda表达式组合成为复杂的表达式'


		

	
	