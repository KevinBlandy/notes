-------------------------
数组
-------------------------
	# Go中的数组，必须指定数组长度以及数据类
		* 长度一旦初始化完毕后，不能修改

	# 基本的声明与初始化
		* 声明长度，后初始化。在不初始化的时候，默认值就是数组类型的默认值。
			var arr [3]bool
			arr[0] = true
			arr[1] = false
			arr[2] = true
			fmt.Println(arr)
			
			
			var arr [3]bool
			arr = [3]bool{true, true, true}

		
		* 不声明长度，直接初始化
			arr := [...]int {1,2,3,4,5,6}

			* [...] 初始化多少个元素，数组就多长


		* 同时声明长度和直接初始化
			arr := [100]int {1, 2}
			var arr [3]int = [3]int{1, 2, 3}
			
			* 元素个数不能大于长度，并且必须是连续设置，否则异常
				arr := [5]int {1, 2, ,4,5} // syntax error: unexpected comma, expecting expression

			* 如果元素个数小于长度，剩下的元素为默认值

		
		* 根据索引初始化，可以为不同下标设置不同的值
			arr := [3]int {0:1, 2, 2:3}
			arr := [5]int {0:1, 3:3, 1:2}

			* 在{}中，通过 [下标]:[值]，来为指定的下标赋值，其他未指定的位置，就是默认值
			* 不能重复声明下标的值
				arr := [3]int {0: 1, 1: 2, 1:3} // duplicate index in array literal: 1

			* 指定下标后面的值，默认往下标后移动一位
				arr := [5]int {0:1, 3:3, 1:2, 9}	// [1 2 9 3 0]  （9前面的下标是1，所以9在下标2）
		
		* 通过new初始化一个数组，返回的是一个数组的指针
			a := new([5]int)
			fmt.Println(a)	// &[0 0 0 0 0]

		
	# 长度和类型，是数组的一部分
		* 长度不同，或者类型不同，不能相互比较赋值。反之则可以。
			var arr0 [4]bool
			var arr1 [3]bool
			fmt.Println(arr0 == arr1)	// invalid operation: arr0 == arr1 (mismatched types [4]bool and [3]bool)
			arr0 = arr1					// cannot use arr1 (type [3]bool) as type [4]bool in assignment

		
	# 通过下标 []，访问元素
		* 使用 len() 全局函数来获取数组长度
		* 下标从0开始
		* 如果越界会抛出异常 // invalid array index 8 (out of bounds for 3-element array)
	
	
	# 遍历 
		* range 遍历，遍历值和下标
			arr := [5]int {1, 2, 3, 4, 5}
			for i, v := range arr {
				fmt.Printf("index=%d val=%d \n", i, v)
			}

		* range 只写一个参数，就是只遍历下标
			var arr [3]int
			for i := range arr {
				fmt.Println(i)
			}
		
		* 下标遍历
			arr := [5]int {1, 2, 3, 4, 5}
			for i := 0; i < len(arr); i++ {
				fmt.Printf("index=%d val=%d \n", i, arr[i])
			}
	
	# 多维数组
		arr := [2][2]int {{1, 2}, {1, 2}}		// [[1 2] [1 2]]
		var arr [3][2]int						// [[0 0] [0 0] [0 0]]
		var arr [2][0]int						// [[] []]
		var arr [0][0]int						// []
		
		* 基本跟一维数组一样，只是操作的元素变成了数组
		* 有几维，就添加几个[]
	
		* 维度数组的初始化，长度要跟变量中声明的一样
				var arr = [3][2]int {
					0: [2]int{1, 2},
					1: [...]int{3, 4}
				}
		
			var  arr = [3][2]int {
				[2]int {1, 2},
				1: [2]int {3, 4},
				[2]int {5, 6},
			}
	
	# 如果初始化的时候，大括号换行了，最后一行必须添加, 结尾
		var  arr = [3]int {
			1, 
			2, 
			3,		// 必须添加, 不然异常：syntax error: unexpected newline, expecting comma or }
		}
		fmt.Println(arr)
	
	# 数组是一个值类型
		arr1 := [3]int{1, 2, 3}
		arr2 := arr1
		arr2[0] = 9
		fmt.Println(arr1, arr2) // [1 2 3] [9 2 3]

		* 定义了数组arr1，它是值类型，赋值其实是复制给另一个变量arr2，当arr2发生变化后arr1并不会发生任何变化

	# 数组的运算
		* 支持 == ， != 运算
