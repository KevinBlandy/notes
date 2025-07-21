---------------------------
异常处理
---------------------------
	# CPP 中的异常处理体系类似于 Java
		* throw 表达式，抛出异常
		* try{}catch (){} 表达式用于捕获和处理异常
		* 一套异常类，用于在 throw 和 catch 之间传递信息
	
	
	
	# 异常处理
		
		int arr[] = {1, 2, 3};

		try {
			for (auto r : arr){
				if (r  == 2){
					// 抛出异常
					throw std::runtime_error("Runtime Error");
				}
				cout << r << endl;
			}
		} catch (const std::runtime_error &e) {
			// 捕获异常，输出异常信息
			std::cerr << "error: " << e.what() << endl;
		}
		
		
		* 每个标准库异常类都定义了 waht() 成员函数，返回 C 语言风格的字符串 const char*
			* 如果异常类型有字符串的初始值，则返回该字符串
			* 对于无初始值的异常类型来说，what 返回的内容由编译器决定
			
		* 异常的 catch / throw 可以嵌套，如果最终异常没能找到匹配的 catch, 程序会转到 terminate 标准库函数
	
			* 这个函数的行为和系统相关，一般情况下执行该函数会导致程序非正常退出
		
	
	# 标准异常，定义在了几个头文件中
	
		stdexcept
			* 定义了常用的异常类

		exception
			* 定义了最通用的异常类，只报告异常的发生，不提供额外的任何信息

		new
			* 定义了 bad_alloc 类型
		
		typeinfo
			* 定义了 bad_cast 类型

