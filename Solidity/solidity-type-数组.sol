----------------------------
数组
----------------------------
	# 引用类型，有动态和静态之分

		T[K] // 固定大小，类型是 T 长度为 K
		T[]	 // 动态大小，类型是 T

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

	# bytes 和 string
		
		bytes
			* 类似于 bytes1[], 但在 calldata 和 memory 中紧密打包
			* bytesN 只有在 storage 中才是紧密打包
		
		string 
			* 等同于 bytes, 但是不允许索引访问和访问长度
			* string 是动态字节数组 (bytes) 的 UTF-8 编码表示，

	# 初始化 Memory 数组

		* 使用 new 操作符创建动态长度的内存数组，注意，不能调整大小
		* 每个成员的默认值都是默认值
			
			 uint[] memory arr = new uint[](12);

			 uint[2][] memory arrayOfPairs = new uint[2][](1024);


	# 数组字面量
		* 字面量数组始终是一个静态大小的内存数组，其长度为表达式的数量。
		* 在 [] 中使用逗号分割，数组的基本类型是列表中第一个表达式的类型，以便所有其他表达式可以隐式转换为它。如果不能转换，则会出现类型错误。

			function foo(function()external pure  returns(uint) fn) public  pure  {
				uint number = 100;
				uint[3] memory arr = [number, 2, fn()] ;
			}

			[1, -1] // 无效，第一个成员是 uint8，第二个是 int8，除非强制 [int(1), -1]
		
		* 固定大小的内存数据，不能赋值给动态大小的数组
			uint[] arr = [1, 2, 3];
		
		* 二维数组字面量，必须显式指定一个共同的基本类型

			uint24[2][4] memory x = [[uint24(0x1), 1], [0xffffff, 2], [uint24(0xff), 3], [uint24(0xffff), 4]];
			// 以下代码无效，因为某些内部数组的类型不正确。
			uint[2][4] memory x = [[0x1, 1], [0xffffff, 2], [0xff, 3], [0xffff, 4]];


	# 实例成员
		length
			* 返回数组长度
		
		push()
			* 动态数组和 bytes 可用，用于在数组末尾添加一个元素（默认值），且返回该元素的引用
				list.push() = 15; 
		
			* 可以把元素作为 push 的参数，这样的话 push 方法不会返回内容
				list.push(15);
		pop()
			* 动态数组和 bytes 可用，从数组末尾移除一个元素，不返回任何内容。
			* 这也会隐式调用 delete 来删除被移除的元素。

	
	

	# 类成员
		concat
	

	# 悬空的 Storage 数组元素引用

