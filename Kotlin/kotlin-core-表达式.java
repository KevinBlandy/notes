----------------------------
表达式						|
----------------------------
	# Kotlin的大多数表达式都是有返回值的
		* 一般分支代码块的最后一个表达式结果,会被当作返回值

	# if
		* if 是有返回值的表达式
		* 如果只有一行代码,可以省略大括号
	
	# while 与 do while 还是跟Java和大多数语言一样

	# as
		* 用于把一个数据类转换为另一个数据类型(cast 强制转换)
		* 如果不能转换,则抛出异常
			interface Parent
			class Sub:Parent

			var x = Sub();
			var y = x as Parent;
	
	# == 和 ===
		* Kotlin 会把 == 编译为 equals 调用
		*  ==== 才是比较的内存地址
	



----------------------------
区间迭代					|
----------------------------
	# 类似于 python 的 for i in range()....
	# 区间迭代本质上就是在迭代:kotlin.ranges.IntRange
		var rannge = 1..10;
		for (i in rannge){
			println(i)
		}
		
	# 区间的表达式,留头又留尾

	# 带步长的迭代(递减的迭代)
		for (i in 100 downTo -5 step 1){
			println(i)
		}
		
		* 从 100 开始, 到 -5 结束
		* 迭代的步长为 5

	# 使用 until 可以不包含结尾
		for (i in 0 until 10){
			println(i)
		}

		* 仅仅迭代 0 - 9
		* 相当于
			for (i in 0..(10 - 1)){
				println(i)
			}

	# 对于 Map 的迭代,可以使用两个变量,同时迭代 key 和 value
		var map = HashMap<String,String>();
		map["key1"] = "value1"
		map["key2"] = "value2"
		map["key3"] = "value3"

		for((key,value) in map){
			println("key=$key, value=$value")
		}
	
	# 可以使用两个变量迭代带下标的集合
		var arr = arrayOf("1","2","3","4","5");
		for ((index,value)  in arr.withIndex()){
			println("index=$index, value=$value")
		}
		
		* 关键点在于,本质上迭代的是集合的 withIndex() 返回的带下标的迭代器: kotlin.collections.IndexingIterable

----------------------------
is 表达式					|
----------------------------
	# 使用 is 判断指定的变量,是否是某种数据类型
		var x = 15;
		var isInt = x is Int;
		println(isInt);
	
	# is 可以自动的转换数据类型
		* 与 Java的 instanceof 相似,但是它可以自动的强制转换数据类型

		class Sub1(): Parent
		class Sub2(): Parent

		fun foo(value: Parent){
			if (value is Sub1){
				println(value)		// 自动的cast为Sub1
			}else if(value is Sub2){
				println(value)		// 自动的cast为Sub2
			}
		}

----------------------------
in 表达式					|
----------------------------
	# 使用 in 或者 !in 来判断值是否存在于某个区间/集合

		fun isLetter(char:Char) = char in 'a'..'z' || char in 'A'..'Z'

		var exists = "Java" in setOf<String>("Java","Groovy","Kotlin","Scala")
	
----------------------
解构赋值,与[]展开	 |
----------------------
	# 跟js/py差不多
	# [] 展开, 使用 * 运算符
		fun foo(var1:String, vararg values: String, var2:String){
			for ((index, value) in values.withIndex()){
				println("value=$index, value=$value")
			}
		}

		fun main(args:Array<String>){
			foo("1",* args,var2 = "")
		}

	# 解构赋值
		var (key, value) = Pair("name", "KevinBlandy")
		println("key=$key, value=$value")
		

----------------------------
when 表达式					|
----------------------------
	# 可以说是 case 语句的升级版
	# 表达式用来匹配的变量,可以是任何对象
	# 该表达式可以返回一个结果
		fun bar (number: Int):String {
			return when (number){
				1 -> {
					"奇数";
				}
				2 -> {
					"偶数"
				}
				else -> {
					"啥也不是";
				}
			}
		}

		* 代码块可以省略
	
	# 支持在函数中使用表达式
		fun getLang(lang :Lang) = when (lang){
			Lang.JAVA -> "java"
			Lang.C -> "c"
			Lang.JAVASCRIPT -> "javascript"
			Lang.PYTHON -> "python"
		}
	
	# 支持相同结果的多个匹配值
		fun foo(number:Int) = when(number){
			1,3,5,7,9 -> "奇数"
			2,4,6,8,10 -> "偶数"
			else -> "啥也不是"
		}
	
	# 当 when 的变量非枚举时,必须要指定 else 

	# 支持除了 == 匹配模式以外的区间匹配模式
		* 要求匹配变量的数据类型,可以参与表达式的计算
		* 并且结果是 boolean 即可

		fun foo(number:Int) = when(number){
			in 0..9 -> "在0-9范围内"
			is Int -> "是Int"
			else -> "不在0-9范围内"
		}

	# 也可以用来取代 if-else if链
		* 如果不提供参数,所有的分支条件都是简单的布尔表达式
		* 当一个分支的条件为真时则执行该分支的代码块

		var x = 5;
		when {
			x > 5 -> println("大于5");

			x < 5 -> println("小于5");
			
			else -> println("等于5");
		}

	
	# when 中的条件使用 ==(equals) 进行判断
		fun foo(v1:Int, v2:Int) :String {
			return when(setOf(v1,v2)) {
				setOf(1, 2) -> "1和2"
				setOf(3, 4) -> "3和4"
				else -> throw Exception("啥也不是")
			}
		}

		fun main(args:Array<String>){
			println(foo(2,1))           // 1和2
			var v1 = setOf(1, 2);
			var v2 = setOf(1, 2);
			println(v1 === v2)          // false
		}

----------------------------
位移运算					|
----------------------------
	# 没有Java提供的那些符号
	shl		左移				<<
	shr		右移				>>

	ushr	无符号右移			>>>
	and		与					&
	xor		异或				|
	inv		取反				^

