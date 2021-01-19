----------------------
数据类型
----------------------
	# 类型自动推断, 使用 var 关键字
		var number = 15;
		var name = "KevinBlandy";
	
	# 手动声明数据类型
		int number = 15;
		String name = "KevinBlandy";

	# 变量再次赋值其他类型的数据会异常
		String name = "KevinBlandy";
		name = 27; //  Error: A value of type 'int' can't be assigned to a variable of type 'String'.

		var number = 15;
		number = "Hello"; // Error: A value of type 'String' can't be assigned to a variable of type 'int'.
	
	# 动态的数据类型的变量, 使用 dynamic  关键字
		dynamic obj = null;
		obj = "123456";
		obj = 15;
		print(obj);

		* dynamic 有点儿类似于java中的 Object, 可以把任意数据都赋值给它(Dart中, 万物皆对象)
	

----------------------
常量
----------------------
	# const 常量
		* const 是编译时的常量, 在编译的时候就需要确定值
		* const 变量的声明必须就要初始化值
			const NAME = "name";
		
		* const 的初始化值, 必须是一个 '常量表达式'
			const now = new DateTime.now(); // New expression is not a constant expression.


	# final 常量
		* 运行时常量, 它惰性初始化(懒加载), 在第一次使用的时候才初始化值
	
		
	
	# 实例变量可以是 final, 但不能是 const


			
		
	
		