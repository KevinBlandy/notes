------------------
运算表达式
------------------
	# 基本的都有
		+,-,*,/,%
		!,||,&&
		==,>,<,>=,<=
		+=,-=,/=,*=....
	
	# 几个特殊的
		~/ 
			* 表示取整除, 类似于py里面的地板除 //
				10 ~/ 3 = 3
		
		??=
			* 如果变量为null, 则进行赋值操作
				int x = null;
				x??=65; // x为nulll, 则赋值为65
				print(65);
	
	# 三目运算符
		var x = 10;
		var r = x > 5 ? '大于5' : '不大于5';
	
	# switch ,跟java差不多
		var x = 2;
		switch (x) {
			case 1: {
				print(1);
				break;
			} default :{
				print('啥也不是');
			}
		}
	
	# 自增自减
		var i = 0;
		i++;
		i--;

		++i;
		--i;
		
		* 先自增, 先运算,跟java一样
	
	
	# 位移运算
		>>,<<,|,^,&
	
	# 循环
		for (int x = 0;x < 10; x ++){
		}

		var i = 10;
		while (i > 0 ){
				i --;
			}
		}

		do{
		}
		while ();

		dynamic val = [1, 2, 3];
		for (var i in val){
			print(i);
		}
		
		* 这种遍历, 必须要求被遍历的对象, 实现了迭代接口: Iterable<?>
		* 否则抛出异常:type 'String' is not a subtype of type 'Iterable<dynamic>'type 'String' is not a subtype of type 'Iterable<dynamic>'

		* 支持 break, continue 关键字

	
	# 强制转换类型表达式
		* 使用 as 关键字, 强制转换某个数据类型
		  dynamic val = 1;
		  String result = (val as int).toString();
		  print(result);

		* 如果转换失败, 抛出异常
			dynamic val = 'Hello';
			String result = (val as int).toString(); // type 'String' is not a subtype of type 'int' in type cast

------------------
判断表达式
------------------
	# 判断表达式不会自动转型, 跟js不一样
		if ('1' == 1){
			// 不会执行
		}
	
	
	# 类型判断表达式: is/is!
		'1' is String // true
		 1 is num	// true
		 1 is double // false
		 1 !s double // true
		
	
