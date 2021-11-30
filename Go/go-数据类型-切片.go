--------------------------
切片
--------------------------
	# 切片，selice
		* 基于数组的一种封装
		* 只能存储相同的元素，自动扩容
	
	
	# 切片的声明与初始化
		* 声明变量，不初始化，默认值为nil
			var arr []int
		
		* 声明变量并且初始化，初始的长度和容量会基于初始化时提供的元素的个数确定
			var arr = []int {}
			arr := []int {1, 2, 3}
		
		* 设置初始化长度/容量
			var arr = []int {9: 5}
			fmt.Println(arr) // [0 0 0 0 0 0 0 0 0 5]

			* 使用5，初始化下标为9的这个元素
			* 前面没初始化的元素，值为默认值

		
		* 通过数组初始化一个切片
			arr := [...]int {1, 2, 3, 4, 5}
			val := arr[1:3]
		
		* 通过make创建切片
			slice := make([]int, 5)
			slice := make([]int, 5, 6)

			* 第1个参数指定类型
			* 第2个参数指定初始长度，第3个参数指定容量，不填的话，默认是长度值（如果容量小于长度，会抛出异常）
			* 初始化值为切片类型的默认值
	
	# 从可切片的类型上获取切片
			arr := [...]int {1, 2, 3}
			val := arr[0:3]
			fmt.Println(val)		// [1 2 3]
			fmt.Println(val[:])		// [1 2 3]
			fmt.Println(val[1:])	// [2 3]
			fmt.Println(val[:1])	// [1]
		
		* val[start:end:cap]
		* start表示开始角标，默认值是0，end表示结束角标，默认值是len（不会包含在切片结果中，左闭右开）
		* start最小值是0，end最大值就是元素的长度，并且start不能大于end，否则异常
		
		* cap 控制切片的容量(cap-start)，如果没有给定 cap，则表示切到底层数组的最尾部。

		* 切片，本身也是一个可切片的类型

	
	# 切片是引用类型，除了nil以外，不支持直接比较
		var arr1 = []int {}
		var arr2 []int

		fmt.Println(arr1 == nil)		// false
		fmt.Println(arr2 == nil)		// true
		fmt.Println(arr1 == arr2) // invalid operation: arr1 == arr2 (slice can only be compared to nil)
	
	
	# 切片是一个引用类型
		arr1 := []int{1, 2, 3}
		arr2 := arr1
		arr2[0] = 9
		fmt.Println(arr1, arr2) // [9 2 3] [9 2 3]
		
		arr := [...]int {1, 2, 3}
		val := arr[:]
		val[0] = 9
		fmt.Println(arr) // [9 2 3]

		* 两个变量，都指向同一个引用的切片对象，底层使用的是同一个数组
		* 在 64 位架构的机器上，一个切片需要 24 字节的内存：指针字段需要 8 字节，长度和容量字段分别需要 8 字节。
		* 由于与切片关联的数据包含在底层数组里，不属于切片本身，所以将切片复制到任意函数的时候，对底层数组大小都不会有影响。
		* 复制时只会复制切片本身，不会涉及底层数组

	# 切片只能访问到其长度(length)内的元素
		* 试图访问超出其长度的元素将会导致语言运行时异常（编译时看不出）
			panic: runtime error: index out of range
		
	
	# 切片的核心概念
		* 地址
		* 长度
			* 使用 len 函数读取
			* 已经存储的资源长度

		* 容量
			* 使用 cap 函数读取
			* 底层数组的容量，切片可能是从底层数组切片而来的
			* 它的计算是从，切片开始的位置，一直到数组(切片目标)结束的长度
				arr := [...]int {1, 2, 3, 4, 5}
				val := arr[1:3]
				fmt.Println(val)		// [2 3]
				fmt.Println(len(val))	// 2			// length 是1
				fmt.Println(cap(val))	// 4			// 底层数组的长度是3，len(arr) - start = 4
		
		* nil的切片，长度可容量都是0

	# 相同类型的切片，可以相互赋值，不管长度，容量是否一样
		slice1 := make([]int, 5)
		slice2 := make([]int, 15)
		slice1 = slice2
	
	# 切片的遍历
		* 使用 range 遍历，或者索引遍历
		* 跟数组一个德行
	
	# 切片元素的删除
		* 目前没有直接删除的方法，只有自己通过N个切片，合并操作来完成某个元素的删除
			// 原始切片
			arr1 := []int {1, 2, 3}
			// 删除角标为1的元素，获取结果
			arr2 := append(arr1[0:1], arr1[2:]...)
			// 成功删除
			fmt.Println(arr2)		// [1 3]
			// 因为共享同一个数组，所以底层数组也被改变
			fmt.Println(arr1)		// [1 3 3]
	
	# 切片与指针
		* 切片本身就是一个引用类型了，其实没多大必要使用指针
			*[]T
		
		arr := [...]string {"H", "E", "L", "L", "o"}
		s1 := arr[:]
		p1 := &s1
		fmt.Printf("%T=%v\n", p1, p1) // *[]string=&[H E L L o]

		s2 := (*p1)[1:]
		p2 := &s2
		fmt.Printf("%T=%v\n", p2, p2) // *[]string=&[E L L o]
		
		fmt.Println(p2 == p1)	// false

--------------------------
切片扩容
--------------------------
	# 切片扩容，使用 append 函数
		append(arr, val...)

		* 往arr里面，添加一个或者多个val，是从length起开始添加
		* 可以使用解构赋值，把指定元素的值都添加到arr
	
	
	# 在底层数组里还有额外的容量可用
		* append() 函数将可用的元素合并入切片的长度，并对其进行赋值
		* 由于和原始的切片共享同一个底层数组，所以修改可见
		
			// 原始数组
			arr := []int {1, 2, 3, 4, 5}

			// 获取到对数组的切片
			val := arr[:2]
			fmt.Println(val) // [1, 2]

			// 对切片添加一个新的元素，返回新切片，并不会修改原始切片，由于还有足够的cap，所以不会创建新的底层数组，还是在原来的基础上添加
			newVal := append(val, 10)	

			// 打印新的切片，元素成功添加
			fmt.Println(newVal)	// [1 2 10]
			// 原始切片并没发生任何改变
			fmt.Println(val)	// [1 2]
			// 底层数组由于是共享，所以也跟着发生了修改
			fmt.Println(arr)	// [1 2 10 4 5]


	# 当切片的底层数组没有足够的可用容量
		* append() 函数会创建一个新的底层数组，将被引用的现有的值复制到新数组里，再追加新的值
		* 此时 append 操作同时增加切片的长度和容量

			// 原始数组
			arr := []int {1, 2}
			// 获取切片，直接切全部
			val := arr[:]
			// 尝试添加新的元素，会超出底层数组的长度，于是创建新的数组，复制元素，并且返回新切片
			newVal := append(val, 3)
			// 新切片中，新的元素内容已经添加
			fmt.Println(newVal)			// [1 2 3]
			// 新切片的cap是旧元素cap的2倍
			fmt.Println(cap(newVal))	// 4
			// 旧数组不会受到影响
			fmt.Println(arr)	// [1 2]

			s := []int {1, 2, 3}
			newS := append(s, 4)

			// 新的切片，创建新的数组，长度是原始切片cap的2倍4，并且复制内容
			fmt.Println(newS)		// [1 2 3 4]
			fmt.Println(cap(newS))	// 6

			// 原始切片不受影响
			fmt.Println(s)		// [1 2 3]
			fmt.Println(cap(s))	// 3


		* append() 会智能地处理底层数组的容量增长。在切片的容量小于 1000 个元素时，总是会成倍地增加容量。
		* 一旦元素个数超过 1000，容量的增长因子会设为 1.25，也就是会每次增加 25%的容量(随着语言的演化，这种增长算法可能会有所改变)。
	


--------------------------
切片的常用操作
--------------------------
	# 删除切片指定位置的元素
		func remove(slice []int, i int) []int {
			copy(slice[i:], slice[i+1:])
			return slice[:len(slice)-1]
		}
	

	# 在头部添加元素
		var a = []int{1,2,3}
		a = append([]int{0}, a...)        // 在开头添加1个元素
		a = append([]int{-3,-2,-1}, a...) // 在开头添加1个切片

		* append默认在尾部添加元素，可以通过这种方式在头部添加
	
	# 在中间插入元素
		* 在i角标处插入元素
			a = append(a, 0)     // 切片扩展1个空间
			copy(a[i+1:], a[i:]) // a[i:]向后移动1个位置
			a[i] = x             // 设置新添加的元素
		
		* 在i角标处插入切片
			a = append(a, x...)       // 为x切片扩展足够的空间
			copy(a[i+len(x):], a[i:]) // a[i:]向后移动len(x)个位置
			copy(a[i:], x)            // 复制新添加的切片