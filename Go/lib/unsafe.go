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

		* 任何类型的指针都可以被转化为Pointer
		* Pointer可以被转化为任何类型的指针
		* uintptr可以被转化为Pointer
		* Pointer可以被转化为uintptr

		func Add(ptr Pointer, len IntegerType) Pointer

-----------------
method
-----------------
	func Sizeof(x ArbitraryType) uintptr
		* 返回x在内存中的大小
		* 参数x可以是表达式，但是这个方法不会对表达式求职

	func Offsetof(x ArbitraryType) uintptr
		
	func Alignof(x ArbitraryType) uintptr
		* 返回参数类型需要对齐的倍数
	func String(ptr *byte, len IntegerType) string
	func StringData(str string) *byte


