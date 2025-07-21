---------------------
iterator
---------------------
	# 迭代器
		
		* https://en.cppreference.com/w/cpp/header/iterator.html
	
	# 迭代器的用法

		const vector<int> list = {1, 2, 3};
		auto begin = list.begin();
		auto end = list.end();

		* begin 指向头元素
		* end 指向最后一个元素的后一个元素，end 元素是一个不存在的元素，不应该操作这个元素！
		
		* 如果迭代器为空，则 begin == end

		
		// for 迭代
		for (auto next = list.begin(); next != list.end(); next ++){
			// 读取元素
			cout << *next << endl;
			// 修改元素
			*next *= *next;
		}

		// while 迭代
		auto it = list.begin();
		while (it != list.end()){
			cout << *it << endl;
			it ++;
		}
	
	# 迭代器的类型
		* 迭代器的类型和容器的 size 一样，不确定是什么类型
		* 实际上，那些拥有迭代器的标准库类型使用 iterator 和 const_iterator 来表示迭代器的类型
		* 容器是常量，则返回 const_iterator 很明显，它是只读的（类似于常量指针）

		* 也可以通过 cbegin 主动要求返回 const_iterator
			
			const  vector<int> list = {1, 2, 3};

			// std::vector<int>::const_iterator constIt
			list.cbegin();
			list.cend();
	
	# 解引用迭代的元素
		
		// 通过 * 运算获取到指针指向的元素
		// 然后访问元素的成员
		(*it).empty();

		// 通过 -> 来直接访问元素的成员
		// -> 包含了解引用操作
		it -> empty();


	# 迭代器的操作
		
		*iter
			* 返回当前元素
		
		iter -> mem
			* 解引用，并获取当前元素的 mem 成员，等于 (*iter).mem
		
		+-
			* 支持对迭代器进行 + 和 -，表示向前和向后移动 N 个元素
				list.end() - 1					// 最后一个元素
				list.begin() + (list.size() / 2) // 中间一个元素
			
			* 两个迭代器相减，返回距离，小减大的话返回的是负数
			* 返回的类型是 difference_type, 一个带符号的整数

				vector<string> list = {"h", "e", "l",  "l", "o" };

				// std::vector<std::string>::const_iterator::difference_type = ptrdiff_t
				// 5
				list.end() - list.begin();
				// -5
				list.begin() - list.end();
		
		
		>/</<=/>=/==/!
			* 判断迭代器的位置关系
			* 参与比较的两个迭代器必须合法，且属于同一个迭代器
	
	
	# 迭代过程中，切记不要往容器增删内容，否则都可能导致迭代器失效
		
		* 如果是预取了 end() 对象，但是在迭代过程中 end 对象发生了变化，那么可能会导致死循环。
		

------------------
Demo
------------------
	# 二分搜索

		vector<int> list = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		// 要检索的元素
		int item = 4;

		auto min = list.begin();
		auto max = list.end();

		while (max > min){
			
			auto mid = min + (max - min) / 2;     
			
			cout << "mid " << *mid << endl;

			if (item > *mid){
				// 过小
				min = mid + 1;
			} else if (item < *mid){
				// 过大
				max = mid;
			} else {
				cout << "Find IT" << endl;
				break;
			}
		}

