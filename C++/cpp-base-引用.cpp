---------------------
引用
---------------------

	# 通过 &d 形式来定义引用类型，d 是声名的引用变量名

		int val = 255;      // 定义变量 val
		int &refVal = val; // 定义引用 reVal

		std::cout << refVal << std::endl; // 255

		 // 修改变量 val，会影响到其引用变量
		val = 0;               
		std::cout << refVal << std::endl; // 0

		// 修改引用变量 refVal，也会影响到变量
		refVal = 128;           
		std::cout << refVal << std::endl; // 128
		std::cout << val << std::endl;  // 128

		// 把引用变量赋值给其他变量，会读取 val
		int bar = refVal;      
		std::cout << bar << std::endl;  // 128

		// 创建另一个引用变量，引用到 val 的引用变量。
		int &otherRefVal = refVal;      // 它俩指向同一个 val
		std::cout << otherRefVal << std::endl;  // 128

		otherRefVal = 200;  // 修改另一个引用变量，会影响所有关系的变量
		std::cout << val << " " << refVal << " " << otherRefVal << std::endl; // 200 200 200

		* 引用表达式，也可以把 & 写在类型后面
			 int& val = x; // 可以，但是不建议

		* 引用必须被初始化
			 int &refVal; // error: 'refVal' declared as reference but not initialized
	
		* 引用本身的类型和要引用的类型必须完全匹配（有两个例外）
		* 引用只能引用到变量，不能引用字面量、表达式


	# 引用指针
		* 引用本身不是一个对象，所以不能定义引用的引用。但是指针是对象，所以，可以定义对指针的引用。

			int x = 0xFF;

			// 指向 x 的指针
			int *p1 = &x;
			// 引用指向 x 的指针
			int *&refX = p1;

			// 0x11dcfffdf4=255
			std::cout << refX << "=" << *refX << std::endl;

			// 指向 x 指针的指针
			int **p2 = &p1;
			// 引用多级指针
			int **&refX2 = p2;

			// 0xb9f99ffcf0=0xb9f99ffcfc=255
			std::cout << refX2 << "=" << *refX2 << "=" << **refX2 << std::endl;
	
	# const 引用

		* const 类型的引用，引用变量

			double pi = 3.1415926;
			
			const double &piRef = pi;
			std::cout << piRef << std::endl;        // 3.14159

			// 修改变量值，会影响到常量引用
			pi = pi * 2;
			std::cout << piRef << std::endl;        // 6.28319

		
		* const 类型的引用，引用 const 常量

			const int val = 0xFF;

			const int &valRef = val; // 引用常量

			// 255
			std::cout << valRef << std::endl;
		
		* 常量引用不能被修改

			valRef = 1024; // error: assignment of read-only reference 'valRef'
		
		* 非常量引用不能引用常量或常量的引用

			int &valRef2 = val;             // 引用常量	error: binding reference of type 'int&' to 'const int' discards qualifiers
			int &valRef3 = valRef;          // 引用常量应用 error: binding reference of type 'int&' to 'const int' discards qualifiers

		* const 引用可以直接初始化为字面量、表达式

			 const int &valRef = 123 * 2;
			 // 246
			 std::cout << valRef << std::endl;
			
		* const 引用甚至可以引用其他的类型，只要这个类型可以转换为引用类型

			// double 类型的变量或者常量
			double pi = 3.1415926;
			// int 类型的 const 引用，直接引用 double 类型
			const int &piRef = pi;
			// 输出：3，没有问题，double 转换为 int 的时候丢失了小数精度
			std::cout << piRef << std::endl;
		
			
			* 为了让 piRef 绑定到一个 int，编译器会生成类似如下转换的中间代码

				double pi = 3.1415926;
				int temp = pi;              // 编译器生成的临时变量
				const int &piRef = temp;    // 使用临时变量绑定到 const 引用
		

		* const 引用的本质就是，它所引用的变量的关系，不能修改。引用的变量的值也不能通过这个引用修改。
		* 引用的变量，如果本身不是 const 的，那么变量本身的修改会同步影响到 const 引用的值。
