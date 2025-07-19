-----------------------
语句
-----------------------
	# while 循环
		while ([condition])
		{
			[statement]
		}

		* 支持 break, continue;
	
	# for 循环
		for ([init-statement]; [condition] ; [expression] )
		{
			[statement]
		}
		

		* 对于索引迭代，在判断语句中，推荐采用 !=, 而非其他语言风格的 <
		* 因为某些迭代器实现，可能未实现大小比较，但是基本上都实现了相等性比较
		* 支持 break, continue;
		
	# range 迭代
		
		for ([declaration] : [expression] )
		{
			[statement]
		}
	
		* expression 表示一个序列对象
		* declaration 表示序列中的每个元素
			
			* 支持使用引用来迭代，从而可以直接修改元素
				 for (auto &i : [expression]) {} // i 就是每个元素的别名
	
	# if ([condition]) {[statement]}
		
		* condition 中的表达式结果会被转换为 bool 类型
		* 非 bool 类型和 bool 进行运算的时候，会把 bool 类型转换为比较的类型
			if (val == true) {
				// 如果val 不是 bool 则会把 true 转换为 val 的类型后进行比较
			}
		
		* CPP 允许赋值语句作为条件，如果赋值后的结果转换为 bool 是 true ，则会执行
		    int i = 0;
			if (i = 1){
				// true
			}
	
	# 三目运算

		* [condition] ? [expression] : [expression]
		* 和其他语言一样，可以嵌套


	# 成员访问
		
		* 对于对象，使用 . 访问
		* 对于指针，可以使用 -> 快捷方式

		string str = "Hello";
		// 对象 . 访问
		size_t size = str.size();
		// 指针 -> 访问
 		size = (&str) -> size();
	
	
	# sizeof
		
		* 返回表达式或者指定类型所占的字节数。返回值是 size_t 类型
		* 对于表达式来说，sizeof 并不实际计算其运行对象的值

		    size_t typeSize = sizeof(int);
			// 4 字节
			cout << typeSize << endl;

			// 4 字节
			size_t expreSize = sizeof (1 + 1);
			cout << expreSize << endl;
		
		* 注意， sizeof 运算符的优先级要高于算数运算符，有些时候需要用括号
			
			size_t expreSize = sizeof 1 + 1; // 结果为5，因为先计算了 sizeof 1 然后把结果 4 加 1。
		
		* 常用于计算数组的长度，如果数组长度为 0，则所占字节也会为 0
			
			int arr[2][3] = {0};
			size_t len = sizeof(arr) / sizeof(*arr); // 数组对象，本身就是指向第一个元素的指针，所以直接 sizeof(*arr) 就可以获取到第一个元素的大小
			// 2
			cout << len << endl;
		
		* sizeof 的结果是一个常量表达式，所以可以用来初始化数组长度
	
	# 逗号 , 运算符
		* 先求左边的值，然后丢掉求职结果。逗号运算符真正的结果是右侧表达式的值。

		int a = 1, b = 2;
		int c = (a++, b++, a + b); // a -> 2, b -> 3, c = 2 + 3 = 5
		// 5 
		cout << c << endl;
	

-----------------------
算数
-----------------------
	# +-/% 和一般语言没啥太大差别

		* bool 不应该参与计算

			bool flag = true;
			bool newFlag = -flag; // 仍然是 true

			* 对于大多数运算符来说，bool 会被转换为 int
			* true -> int = 1, 负号取反为 -1
			* -1 作为 bool 值，很明显不会为 0，转换为 bool 后为 1
	
	# --/++ 自增
		
		* 和其他语言一样，运算符在前先自增/减，在运算。运算符在后，先运算，再自增/减。
		* CPP 推荐用前置的方式
		* 自增/减的表达式优先级高级解引用

		int i = 0;

		int y = ++i;
		// 1 1
		cout << y << "\t" << i << endl;

		int z = i++;
		// 1 2
		cout << z << "\t" << i << endl;

-----------------------
位移
-----------------------	
	>>
	<<
		* IO 运算符的优先级比算数低，比关系、赋值高。
		* 有必要时，可以用括号

			cout << 1 + 1 << endl; // 没问题，算数会先计算
			cout << 1 > 2 << endl;  // error: invalid operands of types 'int' and '<unresolved overloaded function type>' to binary 'operator<<'
			cout << (1 > 2) << endl; // 没问题，使用括号保证关系运算先执行

	^
	&
	|
	~
	
	* 位运算的符号位依赖于机器，关于符号位如何处理没有明确的规定，所以强烈建议仅将位运算符用于处理无符号类型。
	* 小整形位移后，会提升


------------------------
类型转换
------------------------
	# 隐式转换
		
		* 在大多数表达式中，比int类型小的整型值首先提升为较大的整数类型。
		* 在条件中，非布尔值转换成布尔类型。
		* 初始化过程中，初始值转换成变量的类型：在赋值语句中，右侧运算对象转换成左侧运算对象的类型。
		* 如果算术运算或关系运算的运算对象有多种类型，需要转换成同一种类型。
		* 函数调用时也会发生类型转换。
		* 数组转换为首元素指针
			int arr[10] = {5};
			int *p = arr; // *p = 5
			
			* 当数组被用于 decltype, sizeof, &, typeid 等运算的时候，这个转换不会发生
		
		* 指针转换
			* 0, nullptr 可以转换为任意指针
			* 指向任意非常量的指针都能转换为 void*
			* 指向任意对象的指针能转换为 const void*

	# 算术转换
		
		* 算术转换的规则定义了一套类型转换的层次，其中运算符的运算对象将转换成最宽的类型。
		* 整形提升：把小整数类型转换为较大的正数类型。
		* 如果一个运算对象是无符号类型、另外一个运算对象是带符号类型
			* 无符号类型 >= 带符号类型，那么带符号的运算对象转换成无符号的。
			* 无符号类型 <  带符号类型，此时转换的结果依赖于机器。
	
	# 显示转换 cast-name<type>(expression);

		static_cast
			* 任何具有定义的类型转换，只要不包含底层 const, 都能使用
				// 3
				int num = static_cast<int>(3.14);
			
			* 不能修改底层 const

				// const 常量
				const char* pc;
			
				// 只能转换类型，不能把 const 常量，转换为非 const 常量
				//  error: invalid 'static_cast' from type 'const char*' to type 'char*'
				char *p = static_cast<char*>(pc);

				// 保留 const ，可以转换
				const char *p = static_cast<const char*>(pc);
				// 转换为另一个 const 类型，可以站换
				string s = static_cast<string>(pc);

			* void* 指针转换, 如果指针类型转换失败，会产生意外的结果，千万要注意
			    int num = 10010;
				// void* 指针
				void* any = &num;
				// 把 void* 指针转换回  int*
				// int *p
				auto p = static_cast<int*>(any);
			


		dynamic_cast
			* 支持运行时识别

		const_cast
			* 只能改变运算对象的底层 const, 不能改变类型
				// const 常量
				const char* pc;
				//OK, 对 p 进行写入时候，是未定义行为
				char *p = const_cast<char*>(pc);

				// 异常，尝试改变了类型
				const_cast<string>(pc);

		reinterpret_cast
			* 通常为运算对象的位模式提供较低层次上的重新解释，它直接重新解释底层二进制数据，不进行任何类型检查或运行时安全验证。

				int num = 42;
				int* ptr = &num;
				// 将 int* 强制转换为 char*（重新解释内存）
				char* charPtr = reinterpret_cast<char*>(ptr);
			
			* 函数指针转换，用于动态调用绝对地址的函数（如操作系统内核开发）

				void (*funcPtr)() = reinterpret_cast<void(*)()>(0xDEADBEEF);


			* 本质上依赖于机器。要想安全地使用 reinterpret_cast 必须对涉及的类型和编译器实现转换的过程都非常了解。
	
	
	# 旧版本的强制转换
		
		* 早期 CPP 支持显示地强制转换
			
			type (expr)	// 函数式强制转换
			(type) expr	// C 风格式强制转换

			int *ip = new int(155);
			// 强制转换为 char*
			char *c = (char*) ip;
		
		* 这种转换方式，有类似于 const_cast 和 static_cast 的行为，如果转换不合法，则类似于 reinterpret_cast


------------------------
表达式
------------------------
	# 未定义行为，Undefined Behavior, UB

		* 大多数运算符都没有规定运算对象的求值顺序，这在一般情况下不会有什么影响。
		* 然而，如果一条子表达式改变了某个运算对象的值，另一条子表达式又要使用该值的话，运算对象的求值顺序就很关键了。
		* 因为递增运算符和递减运算符会改变运算对象的值，所以要提防在复合表达式中错用这两个运算符。

		* 在同一表达式中对变量既读取又修改（没有序列点保证顺序）。

			int i = 0;
			cout << i << "\t" << (i++) << endl;

			* 当编译 cout 这一行的时候，编译器可以自由决定 i 和 i++ 的求值顺序。
			* 可能输出 0 0（如果先求值 i++，再求值 i）。
			* 可能输出 1 0（如果先求值 i，再求值 i++）。
			* 甚至其他结果（因为 UB 允许任何行为）。
		
	
	# 处理复合表达式的建议
		
		* 拿不准的时候，用括号强制优先运算，以符合需求。
		* 如果在表达式中修改了某个对象的值，在表达式中的其他地方不要再使用这个运算对象。
	
