----------------------
变量				  |
----------------------
	# 变量的声明关键字:var
		var name = "KevinBlandy";

		* 自动的推导出数据类型,不需要手动声明数据类型
		* 可以修改变量的值,但是不能修改变量的数据类型,如果尝试重新赋一个不同数据类型的值,则异常
			
	
	# 手动的声明数据类型
		var name: String = "KevinBlandy";
		var age: Int = 25;
	
		* 如果变量只是声明,并没有初始化,那么必须要声明类型
			var name:String ;
			name = "KevinBlandy" ;
		
		* 变量必须要初始化后才能使用,不然编译异常
	
	# 常量的声明关键字: val
		* 跟 java 的 final 一样,初始化后就不能修改,允许先声明,再初始化

			val name:String;
			name = "KevinBlandy";
		
		* 该变量的引用值不能修改,但是引用的对象,是可以任意修改自身属性值的
	

----------------------
基本数据类型		  |
----------------------
	# 根Java不一样, Kotlin 不区分基本类型和包装类型

	# 整数类型
		Byte,Short,Int,Long

	# 浮点数类型
		Float,Double

	# 字符类型
		Char

	# 布尔类型
		Boolean
	

	# 每一种基本的数据类型都包含了一系列的装函数
		toChar()
		toInt()
		toLong()
		..

		* 可以进行不同数据类型的转换

----------------------
Any					  |
----------------------
	# Any 其实就是 java.lang.Object 
		* 它是Kotlin一些对象的根对象
		* Kotlin用Any的时候, 最后它会被编译为 Object
	
	# 跟Java一样, 把基本数据类型赋值给它, 会自动的装箱
		var n:Any = 2
	
	# Any 默认是非空值, 不能赋值为 null, 可以使用 Any? 表示允许 null 值

	# Any 不能调用 Object 的 wait() / notify() 等方法
		* 但是可以手动的把值转换为 Object 后调用
	

----------------------
Unit				  |
----------------------
	# 这个就是 Java 中的 void, 表示不需要返回值
		fun foo():Unit {}
		fun foo() {}

		* Unit 类型的返回值, 可以省略, 代码块里面不需要 return 语句
	
	# Java 的 Void 先进
		* Java 的方法, 定义了一个泛型返回方法
		* 子类覆写的时候, 使用 Void, 也必须要手动的 return 一个数据
			interface Foo <T> {
				T process();
			}
			class FooImpl implements Foo<Void> {
				@Override
				public Void process() {
					return null;  // 必须要使用 return 关键字
				}
			}

		* 但是Kotlin 不需要
			interface Foo <T>{
				 fun process(): T
			}
			class FoolImp : Foo<Unit> { // 泛型指定为 Unit
				override fun process() {
					// 不需要 return 语句
				}
			}
	
----------------------
Noting				  |
----------------------
	# 该返回值类型表示, 这个方法永远不会返回
		* 用在会抛出异常的方法
			fun foo(): Nothing{
				throw Exception("")
			}
		* 用在死循环的方法
			fun bar (): Nothing {
				while (true){}
			}
	
	# 它没任何值, 只能当做函数的返回值用, 或者当做泛型才有意义


