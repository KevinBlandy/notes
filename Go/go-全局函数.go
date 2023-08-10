---------------------
全局函数
---------------------
	# 在 builting 模块中


---------------------
全局变量
---------------------

---------------------
全局函数
---------------------
	func new(Type) *Type
		* 根据参数类型，创建一个新的值，返回的是它的指针
	
	func make(t Type, size ...IntegerType) Type
		* 用于创建：map, slice, chan 的函数
		
	func len(v Type) int
		* 返回指定数据的长度，如果切片是nil，返回0
	
	func cap(v Type) int
		* 返回指定数据的cap，如果切片是nil，返回0

	func append(slice []Type, elems ...Type) []Type
		* 往arr里面，添加一个或者多个val，返回新的集合
		* 如果切片是nil，则会创建一个新的切片，再往里面添加数据
	
	func delete(m map[Type]Type1, key Type)
		* 从map删除元素，如果元素不存在，不会有任何操作
		
	func copy(dst, src []Type) int
		* src 中的元素拷贝到 dst 中，返回值为拷贝成功的元素个数
		* 如果dst为nil，则返回0，不会执行copy操作
		* 如果 src 比 dst 长，就截断
		* 如果 src 比 dst 短，则只拷贝 src 那部分
	
	func complex(r, i FloatType) ComplexType
	func real(c ComplexType) FloatType
	func imag(c ComplexType) FloatType
	func close(c chan<- Type)
	func panic(v interface{})
	func recover() interface{}
		
	func print(args ...Type)
	func println(args ...Type)
		* 输出信息到标准错误流

	func clear[T ~[]Type | ~map[Type]Type1](t T)
		* 清空 slice，或者是 map
		* 对于数组，会把所有元素都设置为0值，不会改变长度和容量
				arr := []string{"1", "2", "3"}
				clear(arr)
				fmt.Println(len(arr)) // 3
		* 对于 map，会清空所有元素，变成一个空map
				dict := map[string]any{
					"1": 1,
					"2": 2,
				}
				clear(dict)
				fmt.Println(len(dict)) // 0

	func max[T cmp.Ordered](x T, y ...T) T
	func min[T cmp.Ordered](x T, y ...T) T
		* 计算最小，最大值
	


