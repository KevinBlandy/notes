------------------------
类						|
------------------------
	# 简单的类的定义
		class User(
			val name:String,
			val age:Int
		)

		* 语法: class [类名称] (val [属性名]:[属性类型]);

		* 属性的访问权限默认为: public
	
	
	# Kotlin 中 不存在 static (静态)成员

	# Kotlin的类/方法默认都是 final,也就是说不允许被继承/覆写的(跟Java不一样),
		* 如果允许被子类继承, 类需要使用 open 修饰
		* 如果允许被子类覆写, 方法需要使用 open 修饰

		// 允许继承 Parent类
		open class Parent {
			// 允许子类覆写 bar 方法
			open fun  bar() = println("Im bar In Parent")
			// 不允许子类覆写 foo 方法
			fun foo() = println("Im Foo In Param")
		}
		
		* 子类对 open 方法进行覆写后(添加 override 关键字), 该覆写方法默认就是 open 的,子类可以对该方法进行覆写
		* 如果需要禁止子类覆写, 可以主动添加 final 修饰符
			open class Sub : Parent() {
				final override fun bar() = println("Im bar In Sub")
			}
		* 通俗理解就是: 没有标注 final 的 override 方法, 就是 open 的,可以被覆写

------------------------
getter/setter			|
------------------------
	# 定义 getter/setter
		* 跟 js 一个德行,getter 的值是计算得来的
			class User {
				var name : String
					get() {
						return "这个是读取的name属性"
					}
					set(value: String ){
						println("这个是设置的name属性:$name")
					}
			}
			println(user.name);
		
		* 语法: 
			val [属性名称] : [数据类型] get(){ 
				[计算代码]
				return [计算后的返回值] 
			}
		
		* 如果仅仅定义 getter, 那么属性的声明必须是: val
		* 如果需要定义 setter, 那么属性的声明必须是: var 
		* 也可以省略 {} ,使用简单的表达式
			val old : Boolean get() = this.age > 25;

			val old get() = this.age > 25;

			* 同理,甚至可以省略数据类型,编译会根据表达式的返回值自动推导出数据类型
		
	#  getter/setter 也可以使用 private 等权限修饰符来限制访问
		class Foo {
			var name : String = "Name Value"
				private set

			fun changeName(){
				this.name = "new Name"
			}
		}
		
		* name 属性的setter, 被设置为了 private, 外部不能执行 obj.name = ""  来修改数据
		* 可以调用用公共函数来完成数据的修改
	
	# 在 setter 方法中,可以使用 field 关键字来访问初始化的值
		class Foo {
			var name : String = "747692844"
				set (value : String) {
					// 设置为初始化的值为新值, 并且执行修改
					field = value + "Hello"
				}
		}


------------------------
类构造方法				|
------------------------
	# Kotlin 定义了主构造方法
		class User(val nme:String)

		* 这个括号就是主构造方法, 定义了构造函数的参数, 以及使用这些参数初始化实例的属性
		* 可以使用 constructor 关键字, 主动的声明一个主构造方法

		* 使用 init 关键字, 来引入一个初始化语句块, 对象创建的时候, 会调用 init 语句块的代码
		* init 代码块可以定义多个
			class User1 constructor(name:String){ 
				val name:String
				init {
					this.name = name
				}
			}
			* 手动声明 constructor 方法, 定义成员变量
			* 并且使用 init 初始化类成员变量

			class User2 (name: String){
				val name:String = name
			}
			* 省略 constructor关键字和init代码块, 直接定义初始化成员变量,然后初始化

			class User3(val name:String)
			* 直接连初始化的代码, 都省略
			* 注意要使用关键字: var / val
	
	# Kotlin 定义从构造方法
		* 使用 constructor 关键字定义

		open class Super(val name: String)
		class Foo : Super {
			// 在从构造方法, 调用父类的构造方法
			constructor(name: String) : super(name){
			}
			// 构造方法重载
			constructor() : this("Unknown")
		}
	
	# 构造函数, 也可以使用默认值
		class User3(val name:String ,val gender:String = "男")

		* 如果所有的构造函数参数都有默认值的话, 编译器会生成一个不带参数的构造方法, 来使用所有的默认值
		* 这机制会让Kotlin使用库的时变得简单, 因为可以通过无参构造器来实例化对象
	
	# 子类, 需要手动的调用父类构造函数完成初始化
		* 继承类的语法, 就是一个调用(接口不需要括号, 是因为接口不存在构造方法)
			open class Super
			class Sub: Super() // 父类构造函数, 没有参数, 所以子类也不用任何参数
		
		* 调用带有参数的父类构造器
			open class Super(var name:String)
			class Sub (name:String): Super(name)
		
	# 私有化构造函数, 可以阻止外面创建类实例
		class Foo private constructor() {}

	# 我最喜欢的写法, 这种表达要清楚点还是
		open class Super {
			private val name:String
			constructor(name: String){
				this.name = name
			}
		}
		class User : Super{
			private val name:String
			private constructor(name:String): super(name) {
				this.name = name
			}
			constructor(): this("Unknown")
		}
	
------------------------
抽象类					|
------------------------	
	# 抽象类的定义, 需要在类上添加修饰符 abstract , 类不能 直接实例化
	# 抽象方法, 也需要添加 abstract 关键字
		abstract class Foo {
			abstract fun bar()
		}
	
	# 抽象类和抽象方法, 不能用 final 修饰, 必须允许被继承, 被覆写 , 默认就有 open 修饰
	# 抽象类的普通方法, 默认是 final , 不能被覆写, 需要主动声明 open, 才能被覆写

------------------------
内部类					|
------------------------	
	# Kotlint的外部类, 不能访问到内部类的 private 属性(与Java不一样)
		class Outer {

			class Inner (private val name:String){}

			fun foo (){
				var inner = Inner("KevinBlandy")
				// Error:(6, 26) Kotlin: Cannot access 'name': it is private in 'Inner'
				var name = inner.name;
			}
		}
	
	# 嵌套类, 内部类在Java和Kotlin中的区别
		+-------------------------------+---------------------+---------------------------------------------+
		|类 A 在类 B 中声明				|	Java			  |	Kotlin										|
		+-------------------------------+---------------------+---------------------------------------------+
		|嵌套类(不需要存储外部类的引用)	|	static class A	  |	class A										|
		+-------------------------------+---------------------+---------------------------------------------+
		|内部类(需要存储外部类的引用)	|	class A			  |	inner class A								|
		+-------------------------------+---------------------+---------------------------------------------+

		* 通俗理解就是不加 inner 的内部类, 是属于类的,可以通过类名访问到(前提是有足够的访问权限)
		*  加了inner 的内部类, 是属于对象的, 需要通过类实例访问到
			class Outer {
				class Inner1         // 属性类
				inner class Inner2  // 属于对象
			}
			fun main(args:Array<String>) {
				var obj1 = Outer.Inner1()       //通过类访问
				var obj2 = Outer().Inner2()     //通过对象访问

			}
		
		* 可以在内部类中获取到外部类的对象引用, 使用: this@[外部类]
			class Outer {
				inner class Inner {
					// 获取到父对象
					fun getOuter() = this@Outer
				}
			}
			fun main(args:Array<String>) {
				var outer = Outer()
				var inner = outer.Inner()
				println(inner.getOuter() === outer) // true
			}
		
------------------------
密封类					|
------------------------
	# 使用 sealed 修饰的类, 限制其子类, 必须都嵌套在父类中

		sealed class Parent() {
			class Sub1(): Parent()
			class Sub2(): Parent()
		}
		
		* Kotlin 1.1 以前, 子类都必须嵌套定义在父类中
		* Kotlin 1.1 以后修改了限制, 可以在当前文件的任意位置定义 sealed 的子类, 而不是仅仅局限于 sealed 类中
	
	# when 表达式, 在对一个 sealed 类实例进行 is 匹配的时候,不需要 else 分支
		sealed class Parent() {
			class Sub1(): Parent()
			class Sub2(): Parent()
		}
		fun main(args:Array<String>) {
			var obj:Parent = Parent.Sub1()
			var result = when (obj){
				is Parent.Sub1 -> "sb1"
				is Parent.Sub2 -> "sb2" // 使用is的时候, 必须定义 sealed 类 中的所有实例匹配结果
			}
		}
		
		* 对于 sealed 类实例进行 is 匹配, when 里面的分支, 必须定义其所有的实例
		* 如果没有定义到任何一个, 则会抛出编译异常

------------------------
数据类					|
------------------------
	# 在 class 类上添加: data 关键字, 表示该类是有一个数据类

		data class Foo (var name: String,val email: String)

		* 数据类, 必须要有属性(没属性,哪里来的数据), 不然编译异常
	
	# 所谓的数据类, 就是帮你自动重写了 :equals,hashCode,toString 的一个类
		
		hashCode
			* 会根据所有的属性,生成一个hash值
			* 没有添加到主构造函数的属性, 不会参与hash计算

		equals
			* 会检测所遇的属性值是否相等
			* 没有添加到主构造函数的属性, 不会参与比较
		
		toString
			* 按照类声明的顺序, 把所有的属性序列化为字符串
				Foo(name=KevinBlandy, email=747692844@qq.com)
	
------------------------
类委托					|
------------------------
	# 类委托, 就是根据接口, 你指定一个实现, 编译器自动的生成实现调用方法
	# 装饰者设计模式, 就需要把方法处理请求, 委托给一个实现类, 使用委托机制, 可以很方便的实现
	# Kotlin 可以自己完成, 使用 by 关键字
		class MyCollection<String> (
			private val innerList : Collection<String> = ArrayList())
				:Collection<String> by innerList {
		}
		
		* 可以选择性的重写那些需要实现特殊功能的方法

	# 语法
		class [类] ([实例化接口实现对象]) : [继承接口]  by [接口实现对象]

	
	# 使用委托, 实现一个带add计数器的Set
		class CountSet<String> (
				private val innerSet : MutableCollection<String> = HashSet<String>())
				:MutableCollection<String> by innerSet {

			var count : Int = 0
				private set

			override fun add(element: String): Boolean {
				this.count ++
				return this.innerSet.add(element)
			}

			override fun addAll(elements: Collection<String>): Boolean {
				this.count += elements.size
				return this.innerSet.addAll(elements)
			}
		}
		fun main(args:Array<String>) {
			var set = CountSet<String> ()
			set.add("1")
			set.add("1")
			set.add("1")
			println(set.count)
		}

------------------------
权限修饰符				|
------------------------	
	# 类的访问修饰符
		+--------+-------------------------------+------------------------------------------------------------------+
		|修饰符  |								 |																	|
		+--------+-------------------------------+------------------------------------------------------------------+
		|final   |不能被重写					 |类中成员默认使用													|
		+--------+-------------------------------+------------------------------------------------------------------+
		|oepn    |可以被重写					 |需要明确的声明													|
		+--------+-------------------------------+------------------------------------------------------------------+
		|abstract|必须被重写					 |只能在抽象类中使用, 抽象成员不能有实现							|
		+--------+-------------------------------+------------------------------------------------------------------+
		|override|重写父类/接口中的方法			 |如果没有用 final 修饰, 重写的成员默认是 open 的					|
		+--------+-------------------------------+------------------------------------------------------------------+
	
	# 可见性修饰符
		* 与Java相似, 可以使用: public, protected, private 修饰符

		* Kotlin 默认的访问修饰符是: public, 哪里都访问
		* Java默认的访问修饰符是: protected, 同一个包下的类可以访问, 子类可以访问

		* 但是Kotlin只把包作为命名空间里组织代码的一种方式, 并没用作权限控制
		* Kotlin 使用一个新的关键字: internal ,来限制只能在当前'模块' 中使用
		
		* 一个模块, 就是一组一起编译的Kotlin文件
		* 通俗理解就是, 跟我一起编译的代码, 才能访问 internal 修饰的变量
	
		+------------+----------------------------------+-------------------------------------------------------------------+
		|修饰符		 |类成员							|顶层声明															|
		+------------+----------------------------------+-------------------------------------------------------------------+
		|public(默认)|所有地方都可见					|所有地方都可见														|
		+------------+----------------------------------+-------------------------------------------------------------------+
		|internal	 |模块中可见						|模块中可见															|
		+------------+----------------------------------+-------------------------------------------------------------------+
		|protected	 |子类中可见(仅仅子类, 同包都不行)  |不能使用 protected 关键字											|
		+------------+----------------------------------+-------------------------------------------------------------------+
		|private	 |类中可见							|当前文件中可见														|
		+------------+----------------------------------+-------------------------------------------------------------------+
	
		* 高访问权限元素, 不能暴露低权限元素
			internal class Foo()
			// 编译异常, 因为 public 的方法, 暴露了 internal 的类
			fun function ():Foo {
				return Foo()
			}
	
		