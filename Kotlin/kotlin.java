------------------------
Kotlin					|
------------------------
	# 相关网站
		https://kotlinlang.org/
		https://kotlinlang.org/docs/reference/
		https://www.kotlincn.net/

	
	# 一个德行
		* 局部函数跟 js/py 一样
		* 解构赋值,[]展开跟 py 一样
		* getter/setter 跟js差不多
		* raw 字符串, 跟py一样
		* object, 跟js的字面量对象差不多
		* with/apply 跟 js 一样
		* 重载运算符, 跟py 一样
		* 协程,Py有,js也有 Promise
		* 高阶函数,其实就是js/py一样, 方法也是一个变量
		* use 函数跟 py 的 with ... as .. 一样
------------------------
Kotlin	- 征途			|
------------------------

	# 为什么在方法内部扩展属性会编译异常, 但是在方法内部扩展方法就没事儿
		val String.lastChar get() = this[this.length - 1]; // 方法外部扩展属性, 正常
		fun main(){
			// val String.lastChar get() = this[this.length - 1]; // 方法内部扩展属性, 异常
			println("123".lastChar)

			fun String.foo(value: String) = this + "_" + value // 方法内部扩展方法, 正常
			println("123".foo("321"))
		}
	
