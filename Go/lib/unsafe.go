-----------------
unsafe
-----------------
	# 太过于底层的包，不建议轻易使用

-----------------
变量
-----------------

-----------------
type
-----------------
	# type ArbitraryType int
		func Slice(ptr *ArbitraryType, len IntegerType) []ArbitraryType
		func SliceData(slice []ArbitraryType) *ArbitraryType

	# type IntegerType int

	# type Pointer *ArbitraryType

		* 类似于C中的 void* 类型指针
		* 可以相互比较，可以和nil进行比较

		* 任何类型的指针都可以被转化为 Pointer
		* Pointer 可以被转化为任何类型的指针
		* uintptr 可以被转化为 Pointer
		* Pointer 可以被转化为 uintptr

		func Add(ptr Pointer, len IntegerType) Pointer

-----------------
method
-----------------
	func Sizeof(x ArbitraryType) uintptr
		* 返回x在内存中的大小
		* 参数x可以是表达式，但是这个方法不会对表达式求职

	func Offsetof(x ArbitraryType) uintptr
		* 返回结构体成员在内存中位置距离起始处的字节数
		* 参数必须是结构体成员

			type Member struct {
				Id       int64
				Name     string
				Birthday time.Time
			}
			fmt.Println(unsafe.Offsetof(Member{}.Id))       // 0
			fmt.Println(unsafe.Offsetof(Member{}.Name))     // 8
			fmt.Println(unsafe.Offsetof(Member{}.Birthday)) // 24

	func Alignof(x ArbitraryType) uintptr
		* 返回参数类型需要对齐的倍数
	
	func String(ptr *byte, len IntegerType) string
	func StringData(str string) *byte


