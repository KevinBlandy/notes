------------
函数
------------
	
	# 函数的定义
		
		// 定义
		int sum(int a, int b) {
			return a + b;
		}
		
		// 调用
		int ret = sum(1, 2);
		
		* 返回值类型，如果没返回值，则定义为 void
		* 函数名称
		* 参数列表
		* 函数体
		* 返回值
	

	# 参数
		
		* 如果没参数，可以为空或者是 void
		* 参数可以不命名，只提供类型，表示函数体内用不到，但是调用一定要传
		
			void foo (int, string title){}
			foo(0, "");
		
		* 函数的参数会发生类型的隐式转换

			int foo (int x){
				cout << x << endl;
			};

			foo(3.14); // 3
		
		* 形参的初始化方式和变量是一样的，所以当涉及到 const 指针、引用的时候也遵循相同的规则


	
	# 局部变量
	
		* 形参以及函数体内，定义在栈上的变量都属于局部变量。仅在函数内部可用，且会覆盖外层作用域中的同名变量。
		* 方法结束，这些栈上的数据会自动释放。这些数据成为：自动对象。
		
		* 未初始化的局部变量，会产生未定义的值。
		
		* static 的局部变量，不会被释放，而是全局存在，直到程序结束
		
			void foo (){
				// static 变量，仅在当前函数内部可以被访问
				// static 变量未初始化，默认值为 0
				static int;
				cout << ++ counter << endl;
			}
			foo();  // 1
			foo();	// 2
	
	
	# 函数声明
		
		* 和变量一样，函数也可以多次声明，但是只能定义一次。
		* 如果声明的函数用不到，则可以不用提供实现。
		* 函数的声明没有函数体，形参不需要声明变量的名称。
		
			// 声明函数
			void foo ();
			// 如果在编译时没有找到 foo 的实现，则会异常
			// undefined reference to `foo()'
			foo();
		
		* 函数声明应该放在头文件中，定义实现的源码文件应该包含声名的头文件（编译器负责检查声明和实现是否一致）。
		
	
	# 值传递还是引用传递
	
		* 在我的理解，本质上，都是值传递。只是说有些参数传递的是对象的引用，从而可以导致修改到引用的对象
		
		* 值传递，对形参的修改不会影响到外部的实参
			
			// 基本类型
			void foo (int x){	x = 12;	}
			
			// struct
			struct foo { int id; };
			void bar (foo foo) { foo.id = 10010; }; // 修改的是拷贝后的内存
			
			// 向量
			void bar (vector<int> list) {
				list.push_back(99);
			}
			
			
		
		* 引用传递，对形参的修改会影响到实参
		
			// 引用
			void bar (int &num) { num = 15; }
			
			// 指针
			void bar (int *num) { *num = 15; }
		
		* 遇到大型对象，例如 string 作为参数的时候，尽量用引用传递，避免内存拷贝
		* 如果函数内部不需要修改引用传递参数的值，最好将其设置为 const
		* 通过引用传递的参数，可以把输入参数，当成输出参数，类似于 go 中的用法
		
		
	# 数组参数
		
		* 数组的两个大特性：不能拷贝/会被编译为指针。
		* 数组作为参数的时候，实技上传递的就是指向首元素的指针
		* 合法的定义
		
			// 直接定义数组，合法
			void foo (int arr[]){}
			// 直接定义指针，合法
			void foo (int *arr){}
			// 直接定义数组，还指定长度，合法
			// 期望数组包含多少元素，实际上不一定
			void foo (int arr[10]){} 

			// 合法的调用
			int num = 12, arr[2] = {1, 2};
			foo(&num);	// 单个指针
			foo(arr);	// 数组，即指向首个元素的指针
		
		* 传递数组参数的时候需要明确数组的长度

			// 对于C风格的字符串，可以通过 '\0' 判断结束
			void foo (const char *cs){
				if (cs){
					// 迭代字符串，直到最后末尾的 '\0'
					while (*cs){
						cout << *cs++;
					}
				}
			}
			
			// 可迭代类型，传递开始和结束的指针
			void foo (const char *begin, const char *end){
				while (begin != end){
					cout << *begin++;
				}
			}
			char s[] = "123456";
			foo( begin(s),end(s));
		
			// 添加另外个参数，描述长度
			void foo (const char *str, size_t len){
				for (unsigned int i = 0; i < len; i ++){
					cout << i << "\t" <<  str[i] << endl; // 指针也可以用索引访问
				}
			}
						
			char str[] = "123456";
			size_t len =  sizeof(str) / sizeof(*str);
			foo(str, (len - 1));  // 减去最后的 '\0'
	
		* 形参也可以是数组的引用，此时必须传递与元素长度相等的数组
			
			void foo (int (&arr)[3]){
				for (int i : arr){
					cout << i;
				}
			}

			int i = 0, arr[] = {1, 2, 3};
			int arr1[] = {1, 2};
			
			// 类型不匹配
			foo(&i); //invalid initialization of non-const reference of type 'int (&)[3]' from an rvalue of type 'int*'
			// 元素长度不匹配
			foo(arr1); // invalid initialization of reference of type 'int (&)[3]' from expression of type 'int [2]'
			foo(arr);

		* 多维数组
			
			// arr 是一个指针，指向一个 int[3]
			void foo (int (*arr)[3], size_t len){
				// 迭代每个 int[3] 元素
				for (unsigned int i = 0; i < len; i ++){
					// 迭代每个 int[3] 中的每个元素
					int *subArr = arr[i];
					for (unsigned  i = 0; i < 3; i ++){
						cout << *subArr++;        
					}
					cout << endl;
				}
			}
			
			int arr[2][3] = {{1, 2, 3}, {4, 5, 6}};
			foo(arr, sizeof(arr) / sizeof(*arr));

	# 可变参数
		
		* 参数的数量未知，但是类型相同可以使用 initializer_list<T> (initializer_list 头文件中)

			void foo (std::initializer_list<int> args){
				// 参数数量
				cout << args.size() << endl;
				// 迭代
				for (auto *p = args.begin(); p != args.end(); p ++){
					cout << *p;
				}
				cout << endl;
			}

			// 先定义 initializer_list，再调用
			std::initializer_list<int> args = {1, 2, 4, 5};
			foo(args);

			// 直接使用字面量
			foo({1, 2, 3, 4, 5});
			

			* initializer_list 不挑参数位置
		
		* 经典的省略符，主要是兼容 C，不推荐使用
			
			* 适用于基本类型，但类型不安全，需要手动管理参数。
			* 需要配合 <cstdarg> 进行处理。
			* 只能出现在最后一个参数位置。
		
	
	# 返回值
		
		* 返回值也可以添加引用、const 修饰符
		* void 返回值的函数可以不包含 return
		* 返回值可以被隐式转换
		* 返回值和形参一样，也涉及到了值传递和引用传递。
		* 因此不要返回局部对象的引用，以及指针，因为函数结束，资源就会被释放。

			// 错误，返回局部引用
			const string &foo (){
				const string x = "123";
				return x;
			}
		
