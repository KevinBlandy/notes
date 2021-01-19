-----------------
模块常量
-----------------

-----------------
type
-----------------
	# Interface
		type Interface interface {
			Len() int
			Less(i, j int) bool
			Swap(i, j int)
		}

		* 排序接口，实现了这个接口后，就具备了“可排序性”

	# type StringSlice []string
	# type Float64Slice []float64
	# type IntSlice []int
		* 这些类型都实现了 sort.Interface 排序接口，方便用于排序


-----------------
方法
-----------------
	func Sort(data Interface)
		* 对指定的数据进行排序
	func Reverse(data Interface) Interface 
		* 反转排序
	func IsSorted(data Interface) bool
		* 判断是否是有序的
	
	func Search(n int, f func(int) bool) int 
		* 2分搜索的实现， 下标范围是 0 - n，通过 f 判断是否命中，返回就是下标

		
	func SearchInts(a []int, x int) int 
	func SearchFloat64s(a []float64, x float64) int 
	func SearchStrings(a []string, x string) int 
	
	func Slice(slice interface{}, less func(i, j int) bool) 
		* 对指定的切片进行排序，不保证排序的稳定性。

	func SliceStable(slice interface{}, less func(i, j int) bool)
		* 对指定的切片进行排序，保证排序的稳定性

	func SliceIsSorted(slice interface{}, less func(i, j int) bool) bool
		* 测试切片是否是有序的，如果slice不是给的切片参数，抛出异常

	func Ints(a []int)
	func Float64s(a []float64) 
	func Strings(a []string)
		* 对指定的切片进行排序

	func IntsAreSorted(a []int) bool 
	func Float64sAreSorted(a []float64) bool 
	func StringsAreSorted(a []string) bool 
		* 判断指定的切片，是否是有序的
