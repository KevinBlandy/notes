--------------------
Kotlin 重载运算符	| 
--------------------
	# 跟py其实一样
		* 实现了某些固定名称的方法后, 就可以使用特定的运算符号直接运算
		* 这些类型的方法需要添加关键字:operator
		* 而且使用该关键字后, 方法的命名一定要符合规范
		* 该方法发返回值就是最终计算的结果
	
	# Demo
		data class Foo (val num:Int){
			operator fun plus(other:Foo) : Foo{
				return Foo(this.num + other.num)
			}
			operator fun plus(other:Int) : Int{
				return this.num + other
			}
		}

		fun main() {
			var result = Foo(1) + Foo(2)
			println(result)         // Foo(num=3)
			println(Foo(1) + 5) //6
		}
	
	# 还可以使用扩展函数来定义
		operator fun Foo.plus(other:Foo) : Foo {
			return Foo(this.num + other.num)
		}
		
	# 不支持运算两边的交换, 需要重新定义函数
		* 为 Foo 定义方法
			operator fun plus(other:Int) => Foo(5) + 5
		
		* 为Int定义方法
			operator fun plus(other:Foo) => 5 + Foo(5)

	
	
	# 可重载的函数
		a * b		times
		a / b		div
		a % b		mod
		a + b		plus
		a - b		minus