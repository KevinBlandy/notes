---------------------------------
String
---------------------------------
	# n种定义方式
		var str = 'Hello';
		String str  = "Hello";
		String str = """Hello""";
		String str = '''Hello''';
	
	# 字符串使用 + 拼接
		String str = "Hello" + "World";
	
	# 使用占位符: ${expression}
		String name = "KevinBlandy";
		print("Hello ${name}"); //Hello KevinBlandy

		* 跟js一样, 支持方法的访问和简单的计算
		* 默认访问对象的: toString() 方法
	
	# 创建原始字符串
		String reg = r'^\d{1,15}$';
		String reg = '^\\d{1,15}\$'; // 结果同上

		* 以 r 标识的字符串, 内容里面的特殊符号不需要转义

	
	# 判断字符串是否相等, 使用 == 
		print("Hello" == "Hello"); // true
	



---------------------------------
String - 方法
---------------------------------
	