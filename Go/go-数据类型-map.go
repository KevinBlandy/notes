--------------------
map
--------------------
	# Hash表，无序的，key/value结构，是引用类型，可以自动扩容
		map[keyType]valueType
	
	# Map的key，必须是可hash的数据的类型
		config := map[interface{}]interface{}{
			"":" ",
			// 尝试把切片作为key，异常
			[]int {1, 2}: "",  // panic: runtime error: hash of unhashable type []int
		}
		fmt.Println(config)

		* key必须是，可以用==操作的类型，可以扩容的，都不行

	# map初始化，与创建
		* 字面量形式的初始化
			config := map[string]string {
				"name": "Vin",
				"lang": "Java",
			}
		
		* 使用 make 函数初始化，可以通过make的第三个参数，指定hash表的初始化长度
			config := make(map[string]int)
			var config map[string]int = make(map[string]int, 16)

	# 字面量形式初始化的语法糖
		* value，可以被推断出来，所以value初始化的时候不需要去声明变量了 

		type User struct {
			name string
		}
		func test(){
			config1 := map[string] [2]string {
				"1": {"1", "2"},	// 直接初始化数组
				"2": [2]string {"1", "2"},	// 声明初始化
				"3": [...]string {"1", "2"},	// 声明初始化
			}
			fmt.Println(config1)

			config2 := map[string] []string {
				"1": {"1", "2"},	// 直接初始化切片
				"2": []string{"1", "2"},	// 声明初始化切片
			}
			fmt.Println(config2)

			config3 := map[string] User {
				"1": {"hh"},		// 直接初始化对象
				"2": User{"hh"},	// 声明初始化对象
			}
			fmt.Println(config3)

			config4 := map[string] *User {
				// "1": User{"hh"},	// cannot use User literal (type User) as type *User in map value
				"1": {"hh"},		// 直接初始化指针
				"2": &User{"hh"},	// 声明，初始化，再取指针也是OK
			}
			fmt.Println(config4)
		}

	
	
	# 读/写
		config := make(map[string]string, 16)
		
		* 使用[]进行key/value的读写
			// 写入
			config["name"] = "Helllo"
			// 读取
			name := config["name"]
		
		* 获取时判断是否存在，返回2个参数，第1个参数是值，第2个参数是bool表示，是否存在
			val, exists := config["foo"]
			fmt.Println(val, exists) // false
	
		* 如果value不存在，或者map是nil，那么返回的是value的默认数据类型
			config := map[string]int {"name": 1,}
			fmt.Println(config["foo"]) // 0

			var m map[int]int
			fmt.Println(m == nil)		// true
			fmt.Println(m[0])			// 0
		
		* 写入的时候，map不能为nil
			var m map[int]int
			m[0] = 10		// panic: assignment to entry in nil map
			fmt.Println(m)

		
	# 删除元素
		* 使用内置函数 delete 完成
			delete(map, key)
			
		* 如果删除的元素不存在，或者key是 nil，不会报错
		* map不能是nil，否则报错
			delete(nil, "name") // first argument to delete must be map; have nil
		

	# 遍历
		* 使用 range 遍历 key 和 value
			for key, value := range config {
				fmt.Printf("key=%s value=%s \n", key, value)
			}
		
		* 使用 range 仅仅遍历key或者value
			for key := range config {
				fmt.Printf("key=%s \n", key)
			}
		
			* 如果仅仅需要遍历value，那么使用匿名变量： for _, value := range config
				
	
	# Map的比较和赋值
		* 只能跟nil进行==比较
			fmt.Println(make(map[int]int) == make(map[int]int)) // invalid operation: (make(map[int]int)) == (make(map[int]int)) (map can only be compared to nil)
		
		* 相同Key/valu类型的map可以相互赋值
			m1 := make(map[int]int)
			m2 := make(map[int]int)
			m2 = m1
			fmt.Println(m1, m2)
	
	# map中的元素不是变量，不能获取它们的指针
		m := make(map[int]int)
		fmt.Println(&m[1]) // cannot take the address of m[1]

--------------------
map - 方法
--------------------
	# 比较2个map是否相等
		func equals(x, y map[interface{}] interface{}) bool {
			if len(x) != len(y) {
				return false
			}
			for key, xVal := range x {
				if yVal,ok := y[key]; !ok || yVal != xVal {
					return false
				}
			}
			return true
		}