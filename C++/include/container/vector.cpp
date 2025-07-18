-------------------
vector
-------------------
	# 向量（集合）

		* https://en.cppreference.com/w/cpp/header/vector.html

		* 其底层是动态数组（dynamic array），不是链表。
	
	# 集合的初始化
		
		vector<string> v1;

		vector<string> v2(v1);
		vector<string> v3 = v1; // 等价于  vector<string> v3(v1)
			* 需要注意，类型必须匹配

		vector<string> v4 (5, "H"); // 初始化为 5 个 H
		vector<string> v5(15);      // 初始化为 15 个空字符串

		vector<string> v6 = {"h", "el", "lo"};
		vector<string> v8 {"h", "el", "lo"};

		* 对于列表初始化方式，如果提供的值不能作为初始元素的值，编译器会尝试用默认值来初始化

			vector<int> v9 {2, 5}; // [2, 5]	
				* 列表中的元素，都是 int
			
			vector<string> v9 {2}; // ["", ""]
			vector<string> v9 {2, "A"}; // ["A", "A"]	
				* 第一个参数，是数字，不是 string
				* 编译器会尝试把第一个数字（和类型不匹配），作为长度
				
			
			// 异常：编译器没法推导
			vector<string> v9 {2, "A", "B"};  // error: no matching function for call to 'std::vector<std::__cxx11::basic_string<char> >::vector(<brace-enclosed initializer list>)'
	
	# 可索引
		
		* 可以通过索引 [index] 访问、修改元素。但是不能通过索引添加元素。
		* index 只能是无符号类型，不支持负数。


	# 集合的运算，支持相等比较和大小比较
		
		* 只有内部元素实现了相等性关系判断运算等操作后才能进行预算。
		* 相同性，比较的是每个元素、位置是否相同。大小比较的是字典顺序。
	
	# 集合返回的长度，也是带泛型的

		vector<string> strList;

		// using std::vector<int>::size_type = size_t 
		decltype(strList.size()) strLen;

		vector<int> intList;
		// using std::vector<int>::size_type = size_t 
		decltype(intList.size()) intLen;


	# 集合迭代过程中，不能修改其长度
		
		* 测试：range 迭代过程中新增的元素不会被迭代到。下标迭代时，新增的元素会被迭代到。
	
