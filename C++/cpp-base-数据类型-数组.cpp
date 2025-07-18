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
	

	
--------------------
C 风格的字符串
--------------------
