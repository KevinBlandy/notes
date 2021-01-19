----------------------------
object						|
----------------------------
	# object 在 Kotlin 里面是一个 关键字, 可以用它定义字面量形式的对象
		* 类似于, Javascript 定义一个 json 对象一样
		* 可以自由的定义属性, 方法, 而不需要定义类
	
		object User {
			var name = "KevinBlandy"
			fun say(){
				println(this.name)
			}
		}
		
		User.say()
	
		
		* 它甚至还可以包含 init 代码块, 但是不能包含构造函数(不需要构造)
		* init 代码块只会执行一次, 就是字面量对象代码载入的时候
	

	# 天生单例, 不用多说


	# 支持实现接口, 继承类
		abstract class Super {
			abstract fun foo()
		}

		object User : Comparator<String>, Super() {
			override fun foo() {
				println("impl...")
			}

			override fun compare(o1: String, o2: String): Int {
				return o1.compareTo(o2)
			}
		}
	

	# 可以假装静态成员(伴生对象)
		* Kotlin的类不存在静态成员这个说法, 但是可以在类内部创建这种字面量对象
		* 在类的内部,这种对象可以访问到所有的成员
		* 该对象没法被子类重写, 而且类对象,没法访问到其类的伴生对象
		* 直接通过类名就可以调用, 达到静态成员的效果
			class Foo {
				object Object {
					fun func(){
						println("Hello")
					}
				}
			}

			Foo.Object.func()
		
		* 可以利用它实现工厂方法
			
			data class Foo private constructor(var name:String){
				object InnerObject {
					fun getInstance (name:String):Foo {
						return Foo(name)
					}
				}
			}
			var single = Foo.InnerObject.getInstance("KevinBlandy")
		
		* 使用: companion 直达方法,更快捷
		* 在class中, 使用 companion 关键字修饰 object, 可以忽略object的名称,直接调用到方法
			class Foo {
				companion object InnerObject {
					fun foo() = println("Hello")
				}
			}
			Foo.foo() // 调用路径, 直接忽略掉 InnerObject
		
		
		* 在Java中访问类的伴生对象
		* 伴生对象,也会被编译为普通的对象,作为类的一个静态字段存在, 名称打死不改:INSTANCE
			@file:JvmName("CommonUtils")
			package io.kevinblandy.funcs

			class Foo {
				object Ruby {
					var name = "KevinBlandy"
				}
			}
			
			import io.kevinblandy.funcs.Foo;
			public class Demo {
				public static void main(String[] args) {
					String name = Foo.Ruby.INSTANCE.getName();
					System.out.println(name);
				}
			}

			
	# 用于匿名实现
		interface Function<T> {
			fun accept(value:T )
		}

		fun foo(function: Function<String>) {
			function.accept("Hello")
		}

		fun main(args:Array<String>) {
			var function = object : Function<String> { //创建一个字面量对象, 实现function接口
				override fun accept(value: String) {	// 实现接口抽象方法
					println(value)
				}
			}

			foo(function)
		}

		* 没错, 这玩意儿还可以使用一个变量接收, 非常的js(舒服)


	
	# 在Java中使用这种对象
		* 这种对象, 被编译成了通过静态字段来单一持有的对象
		* 字段的名称打死都是: INSTANCE

		@file:JvmName("CommonUtils")
		package io.kevinblandy.funcs
		object Foo {
			var name = "KevinBlandy"
		}

		import io.kevinblandy.funcs.Foo;
		public class Demo {

			public static void main(String[] args) {
				String name = Foo.INSTANCE.getName();
			}
		}
	
