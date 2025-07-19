--------------------
数组
--------------------
	# 数组，存储同一数据类型的连续内存块

	# 数组初始化
		
		* 数组的长度在编译时就必须要被确定
		
			constexpr unsigned size = 1024;
			int arr1[size];  // size 必须要是 const 的，否咋异常
			string arr2[102];
		
		* 数组的初始化
			
			int arr1[] = {1, 2, 3};     // [1, 2, 3]
			int arr2[3] = {0, 1, 2};    // [0, 1, 2] 
			string arr3[3] = {"h", "e"};// ["h", "e", ""]

			* 如果指定了长度，那么元素数量不能超出长度，否则异常。元素数量少于长度，则
		
		* 字符数组的特性，对于字符串字面量，会自动展开，但是千万要注意，会在末尾添加 '\0' 空字符（不是空白符）。
			
			// 列表初始化，不会添加空字符
			char str1[] = {'A', 'B', 'C'}; // ['A', 'B', 'C']

			// 实际长度 +1, 最后会写入一个空字符
			char str2[] = "ABC";		   // ['A', 'B', 'C', '\0']

			// 指定了长度，最后一位是空字符
			// arr[4] == arr[5] = true
			// arr[4] == arr[6] = false
			char str3[6] = "ABC";		   // ['A', 'B', 'C', '', '', '\0']

			// 错误，指定了长度，但是没有多余空间放下空字符
			char str2[3] = "ABC";			// error: initializer-string for 'char [3]' is too long [-fpermissive]
		
		* 数组之间不能直接拷贝和赋值

			char arr[] = {'A', 'B', 'C'};

			char arr1[] = arr;  //  error: initializer fails to determine size of 'arr1'
			char arr2[3] = arr;  // error: array must be initialized with a brace-enclosed initializer

			char arr4[] = {'A', 'B', 'C'};
			arr4 = arr;  //  error: invalid array assignment
		
	
	# 理解数组的含义
		
		* 引用和数组、指针和数组，搅合在一起比较烦
			// 引用 - 引用 int[4] 数组
			int (&arr)[4]
			// 引用 - 引用 *int[4] 数组
			int *(&arr)[4]
			
			// 指针数组 - 存储 *int 指针
			int *arr[4];
			// 指针 - 指向 int[4] 数组
			int (*arr)[4];

		* 要想理解数组声明的含义，最好的办法是从数组的名字开始按照由内向外的顺序阅读。

	
	# 通过索引访问下标
		
		* 千万要注意下标越界的情况
		* 索引的类型是 <cstddef> 中的 size_t, 是一种和机器无关的无符号类型, 足够大

			// typedef unsigned long long size_t
			std::size_t s;
			
			// 数组
			int arr[] = {1, 2, 3, 4};
			// 计算出数组长度，数组大小 / 数组元素大小
			// unsigned long long size
			auto size = sizeof(arr) / sizeof(arr[0]);
			// 4
			cout << "size = " << size << endl;
			for (unsigned int i = 0; i != size; i ++){
				cout << arr[i] << endl;
			}
	
	
	# 数组和指针
		
		* 数组和指针有非常紧密的联系，使用数组的时候编译器一般会把它编译成指针。

		* 数组的下标并不是无符号类型

			char arr[] = {'A', 'B', 'C'};

			// 指向 ['C']
			char *p1 = &arr[2];
			cout << *p1 << " <-> " << p1[0] << endl;  // C <-> C

			// 神奇的来的了，可以用负索引，访问前面的元素
			// 注意，这种方式只能访问同一个数组下的合法元素
			cout << p1[-1] << endl;     // B
			cout << p1[-2] << endl;     // A

		* 而且在大多数表达式中，使用数组类型的对象，其实就是使用一个指向该数组首元素的指针。

			// arr，用到 arr 的地方，都会被编译器编译成指针
			char arr[] = "HelloWorld!";
			// charPtr = &arr[0];
			char *charPtr = arr; 
			// 指针指向的就是第一个元素的地址
			cout << *charPtr << endl;  // H

			// char *a
			// a 是一个 char 指针，指向数组的第一个元素
			auto a(arr); // char *a(arr);

			
		* 取地址符，也可以用于数组元素，获取到指向这个元素的指针

			// arr 
			char arr[] = "HelloWorld!";
			char *charPtr = &arr[5];
			cout << *charPtr << endl; // W
	
		* 指针本身也是迭代器，支持迭代器的各种运算。比较、大小、+、-
		* 指针与指针相减，得到的是相距，类型是 ptrdiff_t(有符号), 定义在了 <cstddef> 中
			
			char arr[] = {'A', 'B', 'C'};

			auto length = sizeof(arr) / sizeof(arr[0]);

			cout << "lenth " << length << endl;  // 3

			// 注意，这里访问的是最后一个元素的后一个元素的地址
			// 获取最后元素的后一个指针，类似于迭代器中的 end
			char *lastNext = &arr[length];

			// 获取第一个指针
			char *first = arr;

			// 迭代每个元素
			while (first != lastNext){  // first < lastNext, 也可以，指针可以比较大小
				cout << *first;     // ABC
				first ++;
			}

			* 特别要注意的是，end 指针指向的最后一个元素的后一个元素。这是一个越界的元素，所以千万不能对这个指针进行解引用操作。

		
		* CPP 11 在 <iterator> 中新增了两个方法 begin 和 end 分别用于获取头和尾后指针

			char arr[] = {'A', 'B', 'C'};
			// 头指针
			auto *pBegin = begin(arr);
			// 尾后指针
			char *pPend = end(arr);
			while (pBegin != pPend){
				cout << *pBegin;  // ABC
				pBegin ++;
			}
			return 0;



	# 数组的类型推断

		* 数组不能使用 auto 进行类型推断
		* 数组可以使用 decltype 进行类型推断
			
			char arr[] = "HelloWorld!";

			// 使用 decltype 也可以推断出类型
			decltype(arr) list = {}; // char list[12] 
	
	# char[] C 风格的字符串
		
		* C 字符串，以空字符结尾 '\0'
		* C++ 支持，但是不推荐使用。因为 C 字符串使用不方便，而且极易引发程序漏洞。
		* 在 <cstring> 中定义了很多操作 C 风格字符串的函数。
			
			// 字符串有严重的错误，没有以 '\0' 结尾
			char arr[] = {'A', 'B', 'C'};
			// 得到的结果可能不准确
			size_t len = std::strlen(arr);  // 4
			cout << len << endl;
			
			// 如下方式的定义，才是 C 风格的字符串
			char arr[] = {'A', 'B', 'C', '\0'};

		
		* string 风格的字符串比较，比较的是大小
			
			string str1 = "H";
			string str2 = "H!";

			// 0
			cout << (str1 > str2) << endl;
		
		* C 风格的字符串，直接比较的是指针大小（首元素），其结果毫无意义

			char str1[] = "H";
			char str2[] = "H!";

			// 直接比较，结果毫无意义。
			//  warning: comparison between two arrays [-Warray-compare]
			// 1
			cout << (str1 > str2) << endl;

			// 需要用 cstring 中的 strcmp 进行比较
			// -1
			cout << std::strcmp(str1, str2) << endl;
		
		* 任何出现字符串字面量值的地方，都可以使用以空字符结束的字符数组来代替。

			// [以空字符结尾的字符串数组] 来初始化 string
			string s ("Hello");

			// string 可以直接和 [以空字符结尾的字符串数组] 拼接
			string s1 = s + " World";

			// 通过 string 的 c_str 方法，可以返回 C 风格的字符串
			const char *cs = s.c_str();

----------------------------
多维数组
----------------------------
	# 多维数组
		* 严格来说，CPP 中并没有所谓的多维数组，所谓的多维数组其实就是数组中的数组

	
	# 声明和初始
		
		int arr[3][2]; // 一个长度为 3 的数组，其中每个元素都是长度为 2 的数组

		// 长度为 10 的数组（每个元素都是长度为 20 的数组（每个元素都是长度为 30 的数组））
		// 每个元素的值都初始化为 0
		int arr[10][20][30] = {0};
		
		* 直接初始化多维数组
		
			int arr[2][3][4] = 
			// 一维数组，2个元素
			{
				// 二维数组 3 个元素
				{
					// 三维数组 4 个元素
					{1, 2, 3, 4},
					{5, 6, 7, 8},
					{9, 8, 7, 6},
				},
				{
					{5, 4, 3, 2},
					{1, 2, 3, 4},
					{5, 6, 7, 8},
				},
			};
				
		* 初始化多维数组也可以省略大括号，先行后列

			// [[1,2,3,4],[5,6,7,8],[9,8,7,6]],[[5,4,3,2],[1,2,3,4],[5,6,7,8]]
		
		    int arr[2][3][4] =  {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8};

			// [[1,2,3,4],[5,0,0,0],[0,0,0,0]],[[0,0,0,0],[0,0,0,0],[0,0,0,0]]
			int arr[2][3][4] = {1, 2, 3, 4, 5};

	
		* 只初始化数组中的指定元素，没有列出的元素，值为默认值

			// [[1,0,0,0],[0,0,0,0],[0,0,0,0]],[[2,0,0,0],[0,0,0,0],[0,0,0,0]]
			int arr[2][3][4] =  {{{1}}, {{2}}};
			
	
	# 使用 CPP 11 的 for range 迭代多维数组

		* 通过引用来自动管理数组的索引

			int arr[2][3][4];

			int count = 0;

			// int (&row)[3][4]
			for (auto &row : arr){
				// int (&col)[4]
				for (auto &col : row){
					// int item
					for (auto &item : col){
						item = count; // 在元素的指定位置写入序号
						count ++;        // 序号 ++
					}
				}
			}

		* 如果不需要修改元素，则可以把引用声名为 const 类型
		* 使用引用的好处在于，避免数组被自动转换为指针（上文说过，使用数组的时候编译器一般会把它编译成指针）！！
			/*
				[
					[1,2,3,4],[5,6,7,8],[9,8,7,6]
				],
				[
					[5,4,3,2],[1,2,3,4],[5,6,7,8]
				]
			*/ 
			
			int arr[2][3][4] =  {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8};

			// 编译器会把 “数组形式的元素”，转换为指向该数组首元素的指针
			// 迭代数组，是一个 int[4] 指针，指向第一个元素 [1,2,3,4]
			//  int (*row)[4]
			for (auto row : arr){
				// 编译失败 error: 'begin' was not declared in this scope
				// 没法对指针进行迭代
				for (auto col : row){

				}
			 }
			
		* 要使用 range for 语句处理多维数组，除了最内层的循环外，其他所有循环的控制变量都应该是引用类型
	
	# 指针
		
		* 当在程序中使用多维数组变量名时，也会自动将其转换为指向第一个元素的指针

		    int arr[2];
			int *arrPtr = arr;  // 指向第一个元素 

			int arr[2][3];
			int (*arrPtr)[3] = arr;	// 指向第一个元素

			int arr[2][3][4];
			int (*arrPtr)[3][4] = arr;		// 指向第一个元素
			int (*arrPtr)[3][4] = &arr[2];	// 指向第二个元素
		
		* 一定要理解，数组，就是指向第一个元素的指针。

			int arr[2][3][4] =  {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8};

			// row，也就是 arr 指向数组的第一个元素

			// int (*p)[3][4]
			for (auto row = arr; row != (arr + 2); row ++){
				
				// 解引用 row, 得到嵌套数组，也就是指向嵌套数组首元素的指针
				// row  int (*col)[4]
				for (auto col = *row; col != (*row + 3); col ++ ){

					// 解引用 col, 得到嵌套数组，也就是指向嵌套数组首元素的指针
					// int item
					for (auto item = *col; item != (*col + 4) ; item ++){
						cout << *item ;
					}   
					cout << endl;
				}
			}
		
		* 使用 begin 和 end 也可以实现多维数组的迭代
	
		    int arr[2][3][4] =  {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8};
			
			// arr 本身就是，指向数组首元素的指针

			// int (*p)[3][4]
			for (auto p = begin(arr); p != end(arr) ; p ++ ){

				// 解引用 p, 得到嵌套数组，也就是指向嵌套数组首元素的指针
				// int (*p1)[4]

				for (auto p1 = begin(*p); p1 != end(*p); p1 ++){

					// 解引用 p1, 得到嵌套数组，也就是指向嵌套数组首元素的指针
					// int *item
	
					for (auto item = begin(*p1); item != end(*p1); item ++){
						cout << *item;
					}
					cout << endl;
				}
			}
		
		* 可以考虑用类型别名来简化类型表达式

			int arr[3][4] = {0};

			// 定义一个 int[4] 的别名
			using row = int[4];

			// 也可以
			// typedef int row[4];

			// arr 指向第一个 int[4] 元素
			for (row *r = arr; r != (arr + 3); r ++){
				// *r, 解指针，得到嵌套数组，即指向第一个 int 元素的指针
				// int *p
				for (auto *p = *r; p != (*r + 4) ; p ++){
					cout << *p ;
				}
			}


	
	# 下标和引用
		
		* 如果下标的索引数比数组的维度小，那么表达式的结果将是给定索引出的一个内层数组

			int arr[3][4] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 1, 2, 3}};

			// 引用了 {5, 6, 7, 8}
			int (&subArrRef)[4] = arr[1];
			// 5
			cout << subArrRef[0] << endl;

			// 一个 *int 指针，指向了第三个元素的 {9, 1, 2, 3} 的首元素
			int *subIntPtr = arr[2];
			// 2
			cout << *(subIntPtr + 2) << endl;

			// 一个 *int[4] 指针，指向第一个元素 {1, 2, 3, 4}
			int (*subArrPtr)[4] = &arr[0];

			// 指针一样，只是指针大小不一样
			// 0xce4a9ff6f0    0xce4a9ff6f0
			cout << subArrPtr + 2 << "\t" << subIntPtr << endl;


