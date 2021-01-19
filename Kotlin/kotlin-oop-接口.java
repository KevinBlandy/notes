----------------------------
接口						|
----------------------------
	# 使用 interface 声明一个接口
		interface Parent {
			fun foo()		// 声明的抽象方法
			fun bar() = println("默认的实现")
		}

		* 可以使用默认的实现方法, 不用声明 default 关键字, 直接写方法体就完事儿了
		
	# 如果实现了多个具有相同签名默认方法的接口, 那么必须要求实现类重写该方法(Java对于这种情况咋处理的???)
		interface Parent1 {
			fun bar() = println("Parent2")
		}
		interface Parent2 {
			fun bar() = println("Parent1")
		}
		class Sub : Parent1, Parent2 {
			override fun bar() {
				// 必须要重写 bar 方法, 因为这个方法在俩接口中都有默认的实现
				TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
			}
		};
		
		* 可以在子类中, 使用 super 关键字 + 尖括号, 类似于泛型的格式, 去指定父要调用哪个父级接口的方法
			class Sub : Parent1, Parent2 {
				override fun bar() {
					return super<Parent1>.bar()
				}
			};
	
	# Kotlin 是兼容Java6的,而Java的接口默认方法在Java8才有
		* Kotlin会把接口的默认方法,编译成一个抽象函数, 然后再用默认实现的方法体, 生成一个静态函数
		* Java 实现Kotlin 的接口, 必须要覆写所的方法, 包括默认方法
	
	# 接口, 可以声明抽象属性, 子类必须实现该属性
		interface Super {
			var name : String // 抽象属性
		}

		class Sub(override var name: String) : Super ;
		* 直接在构造函数参数, 声明 override 关键字

		class Sub1(var args: String) : Super{
			override var name : String
				get (){
					return args
				}
				set (value:String){
					args = value
				}
		}
		* 自己实现getter
		* 还需要实现 setter, 因为接口中使用 var, 而不是 val 定义

		class Sub2 : Super{
			override var name = foo();
			private fun foo () = ""
		}
		* 自己初始化属性

	
	# 接口允许有 getter 和 setter 属性
		* 可以被子类继承

		interface Super {
		var name : String
			get(){
				return ""
			}
			set(value:String) {
			}
		}
		
			

----------------------------
接口的实现					|
----------------------------
	# 实现语法
		import java.io.Serializable
		interface Parent {
			fun foo()
		}
		class Sub : Parent, Serializable {
			override fun foo() {
				println("我是实现")
			}
		}
		fun main(args:Array<String>) {
			var obj :Parent = Sub()
			obj.foo()
		}
		
		* 使用 override 关键字修饰方法,表示覆写, 并且它是强制添加的
		* 使用冒号 : 来代替 implements 关键字, 可以通过逗号分隔n个接口, 表示多实现
		* 冒号 : 也可以代替 extends 关键字, 但是只能有一个抽象类/父类

	

