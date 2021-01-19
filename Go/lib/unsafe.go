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
	type ArbitraryType int
	type Pointer *ArbitraryType
		* 类似于C中的 void* 类型指针
		* 可以相互比较，可以和nil进行比较


-----------------
method
-----------------
	func Sizeof(x ArbitraryType) uintptr
		* 返回x在内存中的大小
		* 参数x可以是表达式，但是这个方法不会对表达式求职

	func Offsetof(x ArbitraryType) uintptr
		
	func Alignof(x ArbitraryType) uintptr
		* 返回参数类型需要对齐的倍数
	


