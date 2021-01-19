----------------------
函数				  |
----------------------
	# 基本的函数定义
		fun max(a:Int, b:Int): Int{
			return if (a > b) a else b;
		}

		fund [函数名]([变量名]:[类型]):[返回值类型]{
			// 函数体
		}

	
	# 表达式函数体
		* 在返回值类型后添加 = ,紧跟函数体
			fun min(a:Int, b:Int):Int = if (a > b) a else b;

		* 甚至可以省略返回值表达式
			fun min(a:Int, b:Int) = if(a > b) a else b;

		* 编译会推导出返回值的类型,所以,这种表达式函数不需要声明返回值类型,以及 return 语句
		* 而非表达式函数,如果存在返回值,必须要声明返回值类型,以及 return 语句
	
	
	# 函数的调用,可以使用命名参数
		fun foo(var1:String, var2:String, var3:String ){
			println("var1=$var1, var2=$var2, var3=$var3")
		}

		fun main(args:Array<String>) {
			foo(var3="1", var2="2", var1="3")	// var1=3, var2=2, var3=1
		}

		* 因为调用的时候使用命名参数,那么传递参数的时候,可以不按照顺序传递
		* 一个参数使用命名参数,必须的参数都使用命名参数
	
		* 调用 java 的方法,不能使用命名参数(java8以后才出现了参数名称保留机制,Kotlin为了兼容jdk6)
	
	# 参数可以有默认值
		* 在类型后面使用 = 定义默认值
			fun foo(var1:String, var2:String = "default" ){
				println("var1=$var1, var2=$var2")
			}
		
		* 使用常规的调用语法时, 必须按照函数声明中定义的参数顺序来给定参数, 可以省略的只有排在末尾的参数
		* 如果使用命名参数, 可以省略中间的一些参数, 也可以以想要的任意顺序只给定你需要的参数 
		* 参数的默认值是被编码到被调用的函数中,而不是调用的地方 

		* 从 Java 中调用 Kotlin 函数的时候,必须显式地指定所有参数值
		* 如果需要从 Java 代码中做频繁的调用,而且希望它能对 Java 的调用者更简便,可以用 @JvmOverloads 注解它
		* 这个注解指示编译器生成 Java 重载函数, 从最后一个开始省略每个参数
			fun foo(var1:String, var2:String,var3:String){
			}
			fun foo(var1:String, var2:String){
			}
			fun foo(var1:String){
			}
	

	# 可变参数
		* 在参数上使用关键字: vararg
			fun foo(var1:String, vararg values: String, var2:String){
				for ((index, value) in values.withIndex()){
					/*
						value=0, value=2
						value=1, value=3
					*/
					println("value=$index, value=$value")
				}
			}

			fun main(args:Array<String>){
				foo("1",* args,var2 = "")
			}
			* vararg 可以放置在任意位置
			* 如果它不是最后一个参数的话, 那么它后面的参数, 在调用的时候需要手动的通过命名参数来指定


		* 可变参数与Java还有一个不同, 可变参数其实就是一个参数数组
			* 调用Java的可变参数, 传递一个数组的话,这个数组参数就是可变参数
			* 调用Kotlin的可变参数, 传递一个数组的话, 需要自己展开数组, 不然的话, 整个数组参数只会作为可变参数的一个元素(使用 *结构数组 - 这个PY又TM一样)
	
	# 中缀调用
		* 调用函数的方法
			1.to("one")		一般调用
			1 to "one"		中缀调用
		
		* 所谓的中缀调用就是: [对象] [方法] [参数]
		* 在中缀调用中, 没有额外的分隔符, 函数名称是直接放在目标对象名称和参数之间的

		* 允许中缀函数调用的函数, 必须使用关键字: infix 修饰
		* 并且中缀函数, 只能有一个参数
			class Foo(){
				infix fun foo(value:String):String{
					return value.plus(value)
				}
			}

			fun main(args:Array<String>){
				var foo = Foo();

				var result = foo.foo("Hello")
				println(result)

				result = foo foo "Hello";
				println(result)
			}
		
		* 可以使用扩展方法, 来定义一个所有对象都可以使用的中缀调用的方法(其实系统已经定义过了)
			infix fun Any.to(value:Any) = Pair(this,value)
			fun main(args:Array<String>){
				var pair = "name" to "KevinBlandy"
				println(pair)
			}
	
	# 局部函数
		* 方法内部可以再次声明一个函数调用,跟js/py一样
			fun outer(){
				var number = 15
				fun inner (){
					println("number=$number")
				}
				inner()
			}
		*  内部函数,可以访问外部函数的局部变量


----------------------
扩展函数			  |
----------------------
	# 就是可以给已有的类,添加新的方法
	
	# 尝试给 java.lang.String 类,添加一个 lastChar() 方法,返回字符串的最后一个字符
		fun String.lastChar(): Char = this[this.length - 1]
		var lastChar = "KevinBlandy".lastChar()
		println(lastChar) // y
	
	# 尝试给 ArrayList 添加一个 foo 方法
		fun <T : Any?> ArrayList<T>.foo() = println("自己添加的方法")
		arrayListOf(1).foo();
	
	# 语法
		fun [目标类].[方法名称]([方法参数]) = [方法体]

		fun [目标类].[方法名称]([方法参数]): [返回值类型] { 
			[方法体]
		}

		* 在扩展方法体中,可以自由的使用 this ,或者不使用 来访问到内部的属性/方法
		* 但是,扩展方法不能访问到 private / protected 属性/方法
	

	# 扩展函数,需要导入才会生效(其实所谓的导入,就是执行到了那句扩展函数的设置代码)
		package io.kevinblandy.common
		fun String.lastChar(): Char = if (this.isEmpty()) ' ' else this[this.length - 1]

		import io.kevinblandy.common.lastChar as foo;
		var lastChar = "KevinBlandy".foo();
		println(lastChar)
	
	
	# 实质上,扩展函数是静态函数
		* 这个函数把第一个参数,设置为了当前调用的对象
		* 扩展函数不会生成代理对象,不会有额外的性能消耗
	
	# 从 Java 中调用扩展函数
		* 知道了扩展函数的本质,就是一个静态函数
		* 所以这个其实非常的简单,把扩展函数当成一个静态函数,然后把调用该函数的对象,传递给扩展函数的第一个参数

		@file:JvmName("CommonUtils")
		package io.kevinblandy.common
		fun String.foo(value:String) =  this + value;

		import io.kevinblandy.common.CommonUtils;
		public class Main {
			public static void main(String[] args) {
				String value = CommonUtils.foo("KevinBlandy","123456");
				System.out.println(value);		// KevinBlandy123456
			}
		}

	
	# 扩展函数是一个非常高效的语法糖,甚至还可以通过泛型,来限制扩展的对象
		import java.lang.StringBuilder
		fun Collection<String>.join(
			separator:String,
			prefix:String,
			suffix:String) : String {
			var stringBuilder = StringBuilder(prefix)
			for((index, value) in this.withIndex()){
				if (index > 0){
					stringBuilder.append(separator)

				}
				stringBuilder.append(value)
			}
			stringBuilder.append(suffix)
			return stringBuilder.toString()
		}

		fun main(){
			var result = arrayListOf("1","2","3","4","5").join(", ", "[", "]")
			println(result)
		}
	
	# 扩展函数不可以被重写
		* 扩展函数不存在重写,因为扩展函数会被编译成一个静态函数,调用该函数的对象作为第一个形参
		* 扩展函数并不是类的一部分,它是声明在类之外的

			open class View {
				open fun click () = println("View Click")
			}
			open class Button : View (){
				override fun click() = println("Button click")
			}
			
			// 两个类都扩展方法
			fun View.showOff() = println("Im a View")
			fun Button.showOff() = println("Im a Button")

			fun main(){
				var view: View = Button()
				// 普通函数, 执行时被重写
				view.click()				// Button click 

				// 扩展函数, 执行时没有被重写
				view.showOff()				// Im a View
			}
		
		* 如果一个类的成员函数和扩展函数有相同的签名(名称一样, 形参一样),那么成员函数会优先使用

----------------------
扩展属性			  |
----------------------
	# 扩展属性有必要和扩展函数一起了解
	# 扩展属性没有任何状态, 因为没地方存储, 因为不能给现有的Java对象添加额外的字段
	# 扩展属性本质上就是添加了一个: getter/setter 
	
	# 定义语法
		[var/val] [类].[属性名称] : [返回值] get() = [方法体]

	# 给 String 添加一个 lastChar 的 getter 属性
		val String.lastChar get() = this[this.length - 1]
		var lastChar = "KevinBlandy".lastChar;
		println(lastChar) // y
	
	# 给 StringBuilder 添加 getter/setter 属性(StringBuilder 允许修改)
		var StringBuilder.last : Char
			get() {
			   return this[this.length - 1]
			}
			set(value: Char) {
				this[this.length - 1] = value
			}
		val stringBuilder = StringBuilder("KevinBlandy")
		stringBuilder.last = 'I';
		println(stringBuilder.last)		// I
	
	# getter 扩展属性使用 val 声明, 因为只能读, setter 扩展属性必须用 var 声明, 因为可以写

	# 从Java中调用扩展属性,需要显示的调用getter函数(根据属性名称驼峰命名的get函数)
		@file:JvmName("CommonUtils")
		package io.kevinblandy.common
		val String.last  get() = this[this.length - 1]

		import io.kevinblandy.common.CommonUtils;
		public class Main {
			public static void main(String[] args) {
				// getLast
				Character character = CommonUtils.getLast("KevinBlandy");
				System.out.println(character);
			}
		}

	
	# 属性的扩展,不能在方法内部执行
		val String.lastChar get() = this[this.length - 1]; // 方法外部扩展属性, 正常

		fun main(){
			// val String.lastChar get() = this[this.length - 1]; 方法内部进行扩展属性, 编译异常
			println("123".lastChar)
		}





