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
		
		* 返回引用/指针是可以直接修改的

			int* foo(int arr[], int index) { return &arr[index];};
			int arr[] = {1, 2, 3};
			// 修改返回值，数组成了 [1, 2, 9]
			*foo(arr, 2) = 9;
		

		* 可以直接用 {} 返回用于列表初始化的返回值

			vector<string> foo (){
				return {"a", "b", "c"};
			}

			// 对于基本类型，需要遵循列表初始化的规则，即返回值不能丢失精度，否置异常
			int bar (){
				return {12};
			}

	# 返回数组

		* 函数不能直接返回数组（数组特性），但是可以返回数组的指针或引用

		* 最直接的办法就是，使用数组别名作为返回值
		
			using MyArr = int[10];
			MyArr* foo(int x);
		
		* 声明返回数组指针的函数: type (*function(parameter)) [dimension]

			type 元素类型
			dimension 数组大小
			function 两端括号必须存在
			
			// 声明
			int (*foo(int i))[10];

			// 数组
			int arr[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
			
			// 实现
			// 方法返回一个指针，指向一个长度为 10 的 int 数组
			int (*foo(int i))[10] {
				cout << i << endl;
				return &arr;
			}

			int main()
			{   

				int (*ret)[10] = foo(998);

				// 迭代内容
				for (auto i : *ret){
					cout << i << endl;
				}

				return 0;
			}

			* 整个函数签名，就好像是声明指向数组指针时的变量。
		
		* 使用 decltype 来推断返回值类型也可以

			// 数组
			int arr[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

			// 方法返回一个指针，指向一个长度为 10 的 int 数组
			decltype(arr) *foo(int i) {
				cout << i << endl;
				return &arr;
			}

			* decltype 并不负责把数组转换为指针，它的结果仍然是数组，所以需要在后面添加 * 表示是一个指针


		* 使用尾置返回类型，CPP 11 的新标准
			
			// 返回一个指针，指向长度为 10 的 int 数组
			auto bar (int parameter) -> int(*) [10];

			* 在原返回值的地方，声明 auto
			* 在参数后面通过 -> 指定返回值类型，
							
			// 方法返回一个指针，指向一个长度为 10 的 int 数组
			auto bar (int parameter) -> int(*)[10] {
				cout << parameter << endl;
				return &arr;
			}
		

	# 重载
		
		* 拥有相同的函数名称，不同的参数，编译器可以根据参数推导出要调用的函数。和返回值无关。
		* 类型别名可以重载

			typedef long long int64;

			// 类型别名，可以重载
			void foo (int i){};
			void foo (int64 i){};
		
		* 顶层 const 参数没法重载

			void foo (const int i){};
			void foo (int i){};			//  error: redefinition of 'void foo(int)'
	
		* 对于指针、引用来说，底层 const 参数，可以重载

			void foo (int*){};
			void foo (const int*){};

			void foo (int&){};
			void foo (const int&){};

			// 可以通过 const_cast 对参数进行转换后，再调用重载方法
			const string &shorterString (const string &s1, const string &s2){
				cout << "uncast" << endl;
				return s1.size() <= s2.size() ? s1 : s2;
			}
			string& shorterString (string &s1, string s2){
				cout << "cast" << endl;
				// 通过 const_cast 转换调用 const 版的重载函数
				return const_cast<string&>(shorterString(const_cast<const string&>(s1), const_cast<const string&>(s2)));
			}
					
		* 当函数调用存在类型转换时，如果存在二义性，则会异常，可以通过强制类型转换来实现函数匹配

			void foo (float s){};
			void foo(long i) {};

			int main()
			{   
				//  error: call of overloaded 'foo(double)' is ambiguous
				// 3.14 是 double，既可以（向下）转换为 float 也可以转换为 long
				// 这调用出现了二义性
				// foo(3.14);

				// 强制转换
				foo(static_cast<long>(3.14));
			}   

	
	# 重载和作用域
		
		* 在函数内部调用方法的时候，如果找到了局部的声明，就会忽略外层作用域中的同名实体
			
			void print(int);
			void print(string);

			int main()
			{   
				// 函数内部声明的方法，覆盖了全局的 print 重载定义
				void print(double);

				print(123);  // 调用到 void print(double); Ok,参数发生隐式转换
				print("");    // 调用到 void print(double); 错误，string 没法隐式转换为 double
			}   
		
		* 在 CPP 中，名字检索发生在类型检查之前。
	

	# 默认参数
		
		* 可以为参数指定默认值

			void foo (int w = 150, int h = 50, string title = "captcha"){
				cout << "w=" << w << " h=" << h << " t=" << title << endl;
			}
			foo(1);                 // foo(1, 50, "captcha");
			foo(1,2);               // foo(1, 2, "captcha");
			foo(1,2, "verifyCode"); // foo(1, 2, "verifyCode");
		
		* 只能省略末尾的默认值参数
			
			// 想要覆盖最后的 title, 则必须要为前面的 w / h 赋值
			foo(1,2, "verifyCode"); // foo(1, 2, "verifyCode");
		

		* 带默认值参数的函数声明，不能重复声明默认值参数
		
			// 最后一个参数声明了默认值
			void bar(int, int, string = "default");

			// 异常，只能对前两个 int 声明默认值
			// void bar(int, int, string = "default");  //  error: default argument given for parameter 3 of 'void bar(int, int, std::__cxx11::string)' [-fpermissive]

			// 正常：对其他参数声明默认值
			void bar(int = 10, int = 20, string);

			* 当给一个参数设置了默认值后，后续的相同声明，只能为没有默认值的参数，添加默认值
			* 并且默认值形参的右边的参数，都必须要有默认值
		
		* 局部变量不能作为默认实参，除此外，只要表达式的类型能转换为形参所需的类型，就能作为作为默认实参
		
			int defaultW = 10;
			int defaultH = 20;
			int defaultSize();

			// 使用全局变量、表达式
			void foo(int w = defaultW, int h = defaultH, int defaultSize = defaultSize());

			int main()
			{   
				int localX = 10;

				// 使用局部变量，异常
				//  error: local variable 'localX' may not appear in this context
				void bar(int x = localX);
			}   
		


			* 用于默认实参的名字在函数声明所在的区域内解析，求值发生在调用时

				int main()
				{   
					// 修改了全局变量，会影响到默认值
					defaultW = 99;
					// 局部变量覆盖了全局变量，但是不能覆盖默认值
					int defaultH = 188l;

					foo(); // 相当于 foo(99, 20, defaultSize());
				}   

	
	# 内联函数
		
		* 函数调用意味着更多的开销：寄存器状态保存、参数拷贝
		* 通过 inline 关键字声明内联函数，编译器会把函数的实现代码 copy 到调用出形成表达式，而不是函数调用

			inline int max (int a, int b){
				return a > b ? a : b;
			}
			int main()
			{   
				// 函数调用
				cout << max(1, 2) << endl;

				// 编译后的内联语句
				cout <<  (1 > 2 ? 1 : 2) << endl;
			}  
		
		* inline 不一定会真的执行，看编译器的实现
		* 编译器一般不支持内联递归函数，而且函数实现代码太长了，也可能不会执行内联
	

	# constexpr 函数
		
		* 指的是能用于常量表达式的函数，使用 constexpr 修饰
		* 要求，返回的类型是字面值类型，且函数体中只有一个 return 语句
		* 给 constexpr 实参传递常量表达式时，返回值也是常量表达式，反之不然（constexpr 函数不一定返回常量表达式）。

			constexpr int doubleLen (int a){
				return a * 2;
			}

			constexpr int defaultLen = doubleLen(16);

			int main()
			{   
				cout << defaultLen << endl;

				int arr1[defaultLen] = {};
				cout << sizeof(arr1) / sizeof(*arr1) << endl;

				int arr2[doubleLen(32)] = {};
				cout << sizeof(arr2) / sizeof(*arr2) << endl;

				// 非常量表达式，也能通过编译？？？
				// 高版本进行了优化吗？
				int len = 512;
				int arr3[doubleLen(len)];
				cout << sizeof(arr3) / sizeof(*arr3) << endl;
			}

			// 201709
			cout << __cplusplus << endl;

	
	# 函数指针
		
		* 指向函数的指针，和函数名称无关，由返回值类型和参数类型决定。

			[return_type] (*[ptrName])([param_types...])

			int sum(int a, int b){
				return a + b;
			}

			int main()
			{
				// 初始化指针为空指针
				int (*sumFn)(int, int) = nullptr;

				// 取地址赋值
				sumFn = &sum;

				// 直接赋值
				sumFn = sum;

				// 直接执行指针
				int ret = sumFn(1, 1);
				cout << ret << endl;

				// 先解引用，再执行
				cout << (*sumFn)(2, 2) << endl;

				// 1
				cout <<(*sumFn == sum) << endl; 
			}   
	
		* 把函数赋值给指针的时候，可以直接赋值。执行函数指针的时候，也无需解引用。
		* 对于重载函数的指针，必须明确的定义到底指向哪个函数

			void func(int*) {};
			void func(unsigned int){};


			int main()
			{
				// 指向 void func(int*)
				void (*funcPtr)(int*) = func;
				// 指向 void func(unsigned int);
				void (*funcPtr1)(unsigned int) = func;

				// 异常，没有一个 func 重载匹配 (int) 参数
				// no matches converting function 'func' to type 'void (*)(int)'
				void (*funcPtr2)(int) = func;

				// 异常，没有一个 func 返回 bool
				// error: no matches converting function 'func' to type 'bool (*)(unsigned int)'
				bool (*funcPtr3)(unsigned int) = func;
			}   
		
	# 函数类型的形参
		
		* 类似于数组，可以在函数参数中声明函数，会被编译器转换为函数指针

			int add (int a, int b){
				return a + b;
			}
			int sub (int a, int b){
				return a - b;
			}
			int muti(int a, int b){
				return a * b;
			}

			int calc(int a, int b, int ope(int, int)){
				return ope(a, b);
			}

			// 声明成指针也可以
			// int calc1(int a, int b, int (*ope)(int, int))

			int main()
			{
				cout << calc(1, 2, add) << endl;        // 3
				cout << calc(9, 2, sub) << endl;        // 7
				cout << calc(9, 9, muti) << endl;       // 81
				
				// 调用的时候，传递函数指针也可以
				cout << calc(9, 9, &muti) << endl;       // 81
			}   
		
		* 函数指针，描述起来又臭又长，可以考虑用别名
			
			// 类型声明
			typedef int Handler(int, int);
			using Handler = int(int, int);
			typedef decltype(add) Handler;

			// 指针声明
			typedef int (*Handler)(int, int);
			using Handler = int(*)(int, int);
			typedef decltype(add) *Handler;

			int calc(int a, int b, Handler ope);
		
	# 函数类型的返回值
		
		* 函数不能直接返回，只能返回指针
	
		* 直接声明类型

			// ope(string) 返回 int(int, int) 函数的指针
			int (*ope(string type))(int, int) {
				if (type == "add"){
					return add;
				} else if (type == "sub"){
					return sub;
				} else if (type == "muti"){
					return muti;
				}
				return nullptr;
			}
			cout <<  ope("add")(1, 2) << endl;      // 3
			cout <<  ope("sub")(1, 1) << endl;      // 0

			// 使用后置类型
			auto ope(string type) -> int(*)(int, int);
	
		* 使用别名
				
			// 声明指针
			typedef int (*Handler)(int, int);
			using Handler = int(*)(int, int);
			typedef decltype(add) *Handler;

			// 声明返回值
			Handler ope(string type);
			// 尾置返回类型
			auto ope(string type) -> Handler;
		
			// 声明类型，则需要在函数返回值上添加 * 表示是指针
			using Handler = int(int, int);

			auto ope(string type) -> Handler*;
			Handler* ope(string type);
		
