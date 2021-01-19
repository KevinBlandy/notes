-------------------------
目录与包结构			 |
-------------------------
	# 包的声明,跟java一样,不多说

	# 导入类库
		* Kotlin 对变量的啥的管理单位是 package,也就是说 kt 文件的名称是啥,显得不那么重要
		* Kotlin一个 kt 文件里面可能定义了N多的类,函数,需要使用什么,就导入什么

			package io.kevinblandy.funcs
			fun max(a:Int, b:Int) = if (a > b) a else b;

			import io.kevinblandy.funcs.max
			fun main(args:Array<String>){
				println(max(1,2));
			}

		*  相同 package 下的所有 kt 文件中,不能重复定义相同名称的变量,否则异常

		* 可以使用: * 来导入所有

		* 当导入的变量与当前包环境的变量名称冲突的时候

			* 如果是使用 * 导入的变量冲突,则当前包的变量优先级大,反之,则 import 的变量优先级大

				import io.kevinblandy.funcs.x;
				import io.kevinblandy.funcs.*;
				var x = "inner";
				var y = "inner";
				fun main(args:Array<String>){
					print("x=$x, y=$y")		// x=outer, y=inner
				}
		
		* 可以使用 as 来设置类库/属性的 别名,python一个德行
			
		
	# 顶层函数与属性
		* Kotiin 编译生成的类的名称, 对应于包含函数的文件的名称, 这个文件中的所有顶层函数编译为这个类的静态函数

		* 要改变包含 Kotlin 顶层函数的生成的类的名称, 需要为这个文件添加 @file:JvmName 的注解, 将其放到这个文件的开头, 位于包名的前面
		
		* 顶层属性也是一样,也是作为类成员变量
			var 声明的属性,会生成 getter/setter 方法: getXxx/setXxx
			val 声明的属性,只会生成 getter 方法 方法: getXxx
			const val 声明的属性,生成 public static final .... 属性,可以直接访问,不需要方法

			@file:JvmName("CommonUtils")
			package io.kevinblandy.funcs

			var var1 = "var1";
			val var2 = "var2";
			const val VAR3 = "var3";

			fun foo(){
				println("Hello")
			}

			import io.kevinblandy.funcs.CommonUtils;
			public class Demo {

				public static void main(String[] args) {

					CommonUtils.foo();

					// getter/setter
					String var1 = CommonUtils.getVar1();
					CommonUtils.setVar1("new value");
					
					// getter
					String var2 = CommonUtils.getVar2();
					
					// 相当于 public static final String VAR3 = "var3"
					String var3  = CommonUtils.VAR3;
				}
			}

	
