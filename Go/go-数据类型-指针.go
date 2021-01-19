---------------------------
指针
---------------------------
	# 指针
		* 是指向变量内存地址的一个变量
		* 在Go中，不存在指针的操作
		* 指针的类型
			*T
	
	# 获取变量的指针
		x := 4
		var p = &x				// 获取变量的指针		0xc000012090
		z := &x					// 获取变量的指针		0xc000012090

		var p *int;
		val := 0
		p = &val
		fmt.Printf("%T", p)			// *int
	
	# 操作变量的指针
		x := 4
		z := &x

		*z = 5				// 通过指针修改地址的值
		val := *z			// 读取指针地址的值
		fmt.Println(val)	
	
	# 指针的默认类型是: nil
	
	# 指针可以比较
		* 只有指向相同的变量，或者是都为 nil，才会相等
	
	
	# 多级指针
		* 多级指针，就是指向指针的指针
		* 对于声明和读取变量来说，有多少级，就写多少个 *
		* 对于获取指针来说，只能对上级“变量”，“指针”进行 & 获取

		// 数据
		val := "hello"

		// 各级指针
		var p1 *string
		var p2 **string
		var p3 ***string

		// 指针初始化
		p1 = &val	// 1级指针
		p2 = &p1	// 2级指针（从指针获取）
		p3 = &p2	// 3级指针（从指针获取）

		// v := &&val 异常，非法表达式：syntax error: unexpected &&, expecting expression
		fmt.Printf("p1=%d, val=%s\n", p1, *p1)		// p1=824634286560, val=hello
		fmt.Printf("p2=%d, val=%s\n", p2, **p2)		// p2=824634564632, val=hello
		fmt.Printf("p3=%d, val=%s\n", p3, ***p3)	// p3=824634564640, val=hello
	

---------------------------
内存分配
---------------------------
	new(Type) *Type
		* 创建一个指定类型的指针，在内存中开辟空间，值是默认值
			var p *int = new(int)
			fmt.Println(*p)			// 0
		* 一般用来给基本的数据申请内存

	make(t Type, size ...IntegerType) Type
		* 跟new一样 ，也是用于申请内存，但是它只用于；map, slice, chan 的内存申请
		* 它的返回值，就是这几个类型的本身，就是引用，不是指针
	

---------------------------
数组和指针
---------------------------
	# 数组指针
		* 一个指针，指向的是数组
		* 数组指针，要定义数组的长度，如果和指向数组长度不匹配则异常
			var p *[5]int;
			arr := [...]int{1, 2, 3, 4, 5}
			p = &arr
			// 指针类型
			fmt.Printf("type=%T\n", p)	// type=*[5]int
			// 通过指针访问长度
			fmt.Printf("size=%d\n", len(*p))	// size=5
			// 通过指针访问元素
			fmt.Printf("[1]=%d\n", (*p)[1])	// [1]=2
			// 通过指针的语法糖，访问长度以及元素
			fmt.Printf("size=%d, index[4]=%d", len(p), p[4]) // size=5, index[4]=5
	
	# 指针数组
		* 一个数组，和普通数组没有什么区别，只是它的元素都是指针
		* 一个二维的指针数组
			i1 := 1
			i2 := 2
			var pArr [1][2]*int = [1][2] *int{{&i1, &i2}}
			fmt.Println(pArr) // [[0xc0000140b0 0xc0000140b8]]
	
	
	# 数组/切片的元素可以取指针
		arr := [...]int {1, 2, 3}
		p := &arr[0]
		*p = 9
		fmt.Println(arr) // [9 2 3]
	
	# struct的属性，也可以取指针

	# map的key/value不可以取指针
		