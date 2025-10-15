----------------------------
数组
----------------------------
	# 引用类型，有动态和静态之分

		T[K] // 固定大小，类型是 T 长度为 K
			* K 只能是常量，不能是变量

		T[]	 // 动态大小，类型是 T
			* 使用 new 操作符创建动态长度数组，注意，不能调整大小（只是初始化的长度值可以是变量、动态的）
				uint len = 1024;
				uint[] memory arr = new uint[](len);

				uint[2][] memory arrayOfPairs = new uint[2][](1024);
		
		* 动态和静态数组，不是相同的类型，不能相互赋值


	# 多维数组

		* 和一般的编程语言相反，嵌套方向是从右往左
			

			{
				{1, 2, 3}, 
				{4, 5},
				{6, 7},
				{8, 9, 8, 7, 6},
				{5, 4, 3, 2, 1}
			}
			uint[][5] memory = list;	// 一个固定长度为 5 的数组，每个数组成员都是动态 uint 数组
		
		* 索引访问方向是从左往右，0 开始

			list[3][2]		// 8 -> 第 4 个动态数组中的，第 3 个元素

			list[2]			// {6, 7} -> 第 3 个动态数组


	# 数组字面量
		* 字面量数组始终是一个静态大小的内存数组，其长度为表达式的数量。
		* 在 [] 中使用逗号分割，数组的基本类型是列表中第一个表达式的类型，以便所有其他表达式可以隐式转换为它。如果不能转换，则会出现类型错误。

			function foo(function()external pure  returns(uint) fn) public  pure  {
				uint number = 100;
				uint[3] memory arr = [number, 2, fn()] ;
			}

			[1, -1] // 无效，第一个成员是 uint8，第二个是 int8，除非强制 [int(1), -1]

			uint[3] memory arr = [uint(1), 2, 3, 4]; // 无效，长度不匹配
		
		* 固定大小的内存数据，不能赋值给动态大小的数组
			uint[] arr = [1, 2, 3];
		
		* 二维数组字面量，必须显式指定一个共同的基本类型

			uint24[2][4] memory x = [[uint24(0x1), 1], [0xffffff, 2], [uint24(0xff), 3], [uint24(0xffff), 4]];
			// 以下代码无效，因为某些内部数组的类型不正确。
			uint[2][4] memory x = [[0x1, 1], [0xffffff, 2], [0xff, 3], [0xffff, 4]];
		
		* 对于 storage 的动态数组，可以用静态数组来进行初始化
			
			contract Demo {

			   uint[] data;

			   uint[] list = [4,5,6];

			   function foo() public payable {
					data = [1, 2, 3];

					uint[] storage dataRef = data;
			   }
			}

			// 只有 storage 本身可以这样初始化，引用都不可以

			// dataRef 是 data 的引用
			uint[] storage dataRef = data;
			// 异常，试图把 dataRef 重新绑定到一个 memory 数组，
			dataRef = [5,6,7];


	# 实例成员
		length
			* 返回数组长度
		
		push()
			* 动态 storage 数组和 bytes 可用，用于在数组末尾添加一个元素（默认值），且返回该元素的引用
				list.push() = 15; 
		
			* 可以把元素作为 push 的参数，这样的话 push 方法不会返回内容
				list.push(15);
		pop()
			* 动态 storage 数组和 bytes 可用，从数组末尾移除一个元素，不返回任何内容。
			* 这也会隐式调用 delete 来删除被移除的元素。

	
	

	# 类成员
		concat
	

	# 悬空的 Storage 数组元素引用
		
		* 当调用 pop() 的时候，storage 动态数组中的元素并不会真的被销毁
		* 只是单纯的 length - 1，并不会清除尾部位置存储的数据，下次 .push() 时会复用旧数据位置。

			contract Demo {

			  // storage 的嵌套数组，即数组元素是引用
			  uint[][] list;

			  constructor(){
				list.push([1, 2]);
			  }


			  function foo() public payable  returns (uint)  {
			 
				// 获取 list 最后一个元素的引用，即 [1, 2]
				uint[] storage lastItem = list[list.length - 1];

				// 移除 list 最后一个元素
				// 并且会 delete 这个元素，即把原始的值修改为 0 值，空素组
				list.pop();

				// lastItem 就成了悬空引用
				// 通过最后一共元素的引用 push 数据
				lastItem.push(0xFF);

				// list 添加一个空引用
				list.push();

				// 访问 list 的第一个元素，会发现不是 0 值，而是 255
				return list[0][0];
			  }
			}

		
		* 当使用复杂表达式进行元组赋值时，悬空引用也可能会暂时发生

			// 用元组赋值 (tuple assignment) 
			// Solidity 为了在不创建中间拷贝的情况下交换值，会暂时创建中间的 storage 引用。
			// 在某些复杂表达式下，比如嵌套结构体或数组元素间的交换，这些中间引用在赋值结束前会暂时“悬空”——也就是说，它们在存储层面上暂时指向了已经被替换或释放的槽。
			// 虽然编译器会在内部正确地避免非法访问，但在理论上这种短暂状态属于“暂时悬空引用”。
			(a[i], a[j]) = (a[j], a[i]);
		
		* 任何具有悬空引用的代码都应被视为未定义行为，确保在代码中避免悬空引用。


	# 切片
		
		x[start:end]

		* 目前只能对 calldata 使用数组切片。 memory 和 storage 都不可以使用

		* start 和 end 是结果为 uint256 类型（或隐式可转换为它）的表达式。 
		* 第一个元素是 x[start]，最后一个元素是 x[end - 1]。
		* 如果 start 大于 end 或 end 大于数组的长度，将抛出异常。
		* start 和 end 都是可选的：start 默认为 0，end 默认为数组的长度。

		* 数组切片没任何成员，也就是说不能对一个数组切片调用 .length、.push() 或 .pop() 这样的成员函数。
		* 数组切片不是一个“变量”，而是一个表达式。
	
----------------------------
动态字节数组
----------------------------	
	# 动态字节数组
		
		* bytes 和 string 可以相互转换

		string memory str = "HelloWorld";
		bytes memory strBytes = bytes(str);
		string memory str1 = string(strBytes);

	# bytes
		* 类似于 bytes1[], 但在 calldata 和 memory 中紧密打包，bytesN 只有在 storage 中才是紧密打包
		* 在底层存储（storage）中，它有两种不同的布局形式

			1. 短布局（short layout）
				* 当 bytes.length ≤ 31 时，Solidity 会将整个字节数组直接存放在一个 32 字节的存储槽（slot）中。
				* 这时，数据和长度是 “紧密打包” 在一个槽内的。

			2. 长布局（long layout）
				* 当 bytes.length > 31 时，Solidity 会将实际字节数据存储在一个单独的存储区（类似动态数组 T[] 的方式），并在主槽中仅保存一个指针（或偏移信息）。


	# string 
		* 等同于 bytes, 但是不允许索引访问和访问长度
		* string 是动态字节数组 (bytes) 的 UTF-8 编码表示
	

