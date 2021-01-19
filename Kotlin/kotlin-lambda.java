-------------------------
lambda					 |
-------------------------
	# 基本的语法
		* 使用大括号包裹整个表达式: {[形参]:[类型] -> 方法体 }

			var func = {x:Int -> x * x}
			println(func(5))

		* lambda表达式可以被赋值给一个变量, 最终当作一个函数来执行(本身就是一个函数)
	
		* lambda表达式,支持直接调用,有点像是js的语法: (function(){})()
			{ println("KevinBlandy") }()

			{ value:String -> println(value) }("KevinBlandy")
		
		* lambda 并不局限于单行代码, 可以有多行代码的
		* 最后一行代码的结果, 就是lambda表达式的结果
			 var func = {x:String -> {
				var x = 15
				println("Hello World")
				x
			}}
		

	# 如果 lambda 表达式是函数的最后一个实参, 那么可以写在括号外面
		rrayOf("").maxBy() { i:String -> i.length }

		* 这语法怪怪的


	# 如果 lambda 表达式是函数的唯一实参, 那么连括号都可以不要了
		arrayOf("").maxBy { i:String -> i.length }

	
	# 如果参数类型是可以推导的, 那么连参数类型都是可以忽略的
		arrayOf("").maxBy { i:String -> i.length }
		arrayOf("").maxBy { i  -> i.length }

		* arraOf(""), 返回的是一个 Array<String>, 可以推导出来, 所有的数据类型都是字符串, 所以这个 lambda 可以省略参数的数据类型
		* 也有可能推导不出来参数类型, 反正一个规则: 先被推导, 咋简单咋写, 编译报错了, 再改(书上教的)
	
	# 使用默认的参数名称
		* 这个是简化到了最高级的地步了
		* 如果lambda只有一个参数, 并且可以推导出数据类型, 那么省略lambda参数的声明, 使用默认的变量名称代替
		* 使用默认的参数名称: it 来表示参数的引用, it 这个变量名称打死不变

			arrayOf("").maxBy { it.length }
		
	
	# 在作用域中访问变量
		* 跟Java不同 ,Kotlin的 lambda 表达式不进可以访问外部的 final 变量, 甚至还可以修改它们

			fun foo(list:Collection<String>,prefix:String):Int{
				var count = 0
				list.forEach {
					println("$prefix$it")
					count ++  // 在lambda修改外面的局部变量
				}
				return count
			}
			fun main(){
				var result = foo(arrayListOf("1","2","3"), "-")
				println(result)
			}

		* 当访问了一个外部的 final 变量时, 它的值会被拷贝下来, 跟 lambda 表达式存储在一起(Java也是这个原理)

		* 当访问了一个外部的非 final 变量的时候, 它的值会被作为 Raf 实例对象的一个属性保存下来, Raf 是 final 修饰的, 可以被轻易的捕捉
		* 然后 Raf 存储的值, 就可以在 lambda 执行时进行修改
	
	# Kotlin 的lambda 表达式里面 一般是不支持 this 的
		

-------------------------
成员引用				 |
-------------------------
	# 跟 Java一样, 可以把某些已经定义好的方法, 作为一个 lambda

	# 成员引用的代码: [类]::[成员]
		var length = String::length
		arrayOf("").maxBy(length)

		arrayOf("").maxBy(String::length)
	
	# 还可以引用到顶层函数: ::[方法]
		fun foo(){
			println("Hello")
		}
		fun main(){
			var f = ::foo // 获取到顶层函数的引用
			run(f)
		}

		* 因为没有类, 所以不需要前面的类声明
	

	# 构造方法的引用: ::[类]
		data class User(val name:String)

		// 获取到指定类的构造引用
		var userCreate = ::User
		fun main(){

			// 执行
			var result = userCreate("12345")
			println(result) // User(name=12345)
		}

	
	# Kotlin 1.1 以前, 需要手动的指定运行函数引用的实例对象
		* 有点儿类似于反射的意思, 我获取到执行函数, 但是该以哪个对象的身份来执行这个函数, 需要手动的指定
			data class User(val name:String) {
				fun foo(value:String) {
					println("$name,$value")
				}
			}
			fun main(){
				// 获取指定类的 foo, 
				var foo = User::foo
				// 执行该引用的时候, 第一个参数为该引用的上下文对象(this)
				foo(User("KevinBlandy"),"Hello")
			}
		
		* 注意点就是, 这个方法引用, 是通过类来获取到的
	
	# 也可以引用到扩展方法
		fun String.foo() = println("Hello")
		fun main() {
			// 根据对象, 获取到扩展方法
			var foo = ""::foo
			foo()
		}
	
	# Kotlin 1.1 以后, 支持从实例对象获取到方法引用
		* 从指定的实例获取到的方法引用, 是名花有主的,所以可以直接运行, 而不用手动的去指定运行时的对象

			data class User(val name:String) {
				fun foo(value:String) {
					println("$name,$value")
				}
			}
			fun main(){
				var user = User("KevinBlandy")
				// 获取指定对象的方法引用
				var foo = user::foo
				foo("Hello")
			}

		* 注意点就是, 这个方法引用, 是通过对象来获取到的




		

	# Kotlin 的 lambda 可以无缝的和 Java api 对接
		* 把一个 Lmabda 传给 Java 方法

			public class Main {
				public static void foo(Runnable runnable){
					new Thread(runnable).start();
				}
			}

			fun main() {
				Main.foo {println("Hello")} // 编译器会自动的吧这个lambda转换为 Runnable 的实现
			}
		
			
			* 也可以使用 object 这种字面量对象
			* 但是每次调用, 都会创建一个新的字面量对象
				Main.foo(object : Runnable {
					override fun run() {
						println("Hello World")
					}
				})
			
	
	# SAM构造方法,把 lambda 转换为 接口
		* 啥玩意儿啊这
			fun callDone() : Runnable {
				return Runnable { println("done") }
			}
			fun main() {
				var runnable = callDone()
				runnable.run()
			}
			
			* 我的理解就是把 lambda 这个表达式, 作为一个实现存在,可以赋值给变量
		
		* 语法: [类型] [lambda]
			var runnable = Runnable { println("Hello World") }
		
	
-------------------------
with / apply			 |
-------------------------
	# 带接收者的lambda
		* 非常好理解, 这TM不就是 Javascript 里面函数的 call/apply 么?
		* 可以指定 lambda 运行时的 this 指向(上下文)

			fun alphabet () : String {
				val stingBuilder = StringBuilder()
				for(letter in 'A'..'Z'){
					stingBuilder.append(letter)
				}
				return stingBuilder.toString()
			}
			* 需要多次去使用 stingBuilder 变量名称, 去执行这个对象的方法

			fun alphabetWith () : String {
				val stingBuilder = StringBuilder()
				with(stingBuilder, {        
					for(letter in 'A'..'Z'){
						this.append(letter)
					}
					return toString() // 省略 this, 执行访问到帮到对象的上下文方法
				})
			}
			* 使用 with 绑定了 this 指向的对象, 那么在 lambda 里面可以痛快的使用 this
			* 也可以省略 this 关键字, 因为上下文已经确定了, 可以直接访问方法
		
		* 语法: with ([this对象] , [代码块( lambda 表达式 )])
	
	
	# 其实扩展函数,就是一种带了接收者(执行上下文)的lambda
		* 编译后, 是一个静态方法, 第一个参数就是执行的对象, 就是上下文
	

	# With 运算,也是有结果的
		* 也就是说,这个表达式可以返回一个值
		* 代码块的最后一行结果, 作为返回值
		
		var letters = with(StringBuilder(), {
			for(letter in 'A'..'Z'){
				this.append(letter)
			}
			toString()
		})
	
	
	# 方法名冲突的问题
		class Bar {
			fun foo (){
				println("Im Foo In Bar")
			}
		}

		class Foo {
			fun foo (){
				println("Im Foo In Foo")
			}
			fun bar (){
				with (Bar()){
					foo()            // Im Foo In Bar
					this.foo()      // Im Foo In Bar
					this@Foo.foo() // Im Foo In Foo
				}
			}
		}

		fun main() {
			var foo = Foo()
			foo.bar()
		}
		
		* 就是一个 with 的lambda 表达式,在一个类方法中, 使用 this 或者不使用, 调用的方法名, 在当前类中也存在
		* 如果需要在当前的with中调用类中的同名方法,需要使用: this@[类].[方法]
	

	# apply
		* apply 跟 with 的作用都是一样的, 都是为了修改运行时环境
		* 它被设计为一个扩展方法 , 也就是说很多对象都可以执行这个方法
			val stringBuilder = StringBuilder().apply {
				for(letter in 'A'..'Z'){
					this.append(letter)
				}
			}

			fun main() {
				println(stringBuilder.toString())
			}
		
		* apply,需要对象主动的去执行
		* apply, 始终会返回接收者对象(上下文)

		* 可以用这种方式, 在创建完毕对象后设置一些初始化属性

	

