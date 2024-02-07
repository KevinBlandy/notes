-----------------
slices
-----------------
	# 用户处理 slice 的工具包，可用于泛型

-----------------
var
-----------------

-----------------
type
-----------------

-----------------
func
-----------------

	func BinarySearch(x S, target E) (int, bool)
	func BinarySearchFunc(x S, target T, cmp func(E, T) int) (int, bool)
	func Clip(s S) S
	func Clone(s S) S
	func Compact(s S) S
	func CompactFunc(s S, eq func(E, E) bool) S
	func Compare(s1, s2 S) int
	func CompareFunc(s1 S1, s2 S2, cmp func(E1, E2) int) int
	func Concat[S ~[]E, E any](slices ...S) S
		* 拼接切片

	func Contains(s S, v E) bool
	func ContainsFunc(s S, f func(E) bool) bool
	func Delete(s S, i, j int) S
	func DeleteFunc(s S, del func(E) bool) S
	func Equal(s1, s2 S) bool
	func EqualFunc(s1 S1, s2 S2, eq func(E1, E2) bool) bool
	func Grow(s S, n int) S
	func Index(s S, v E) int
	func IndexFunc(s S, f func(E) bool) int
	func Insert(s S, i int, v ...E) S
	func IsSorted(x S) bool
	func IsSortedFunc(x S, cmp func(a, b E) int) bool
	func Max(x S) E
	func MaxFunc(x S, cmp func(a, b E) int) E
	func Min(x S) E
	func MinFunc(x S, cmp func(a, b E) int) E
	func Replace(s S, i, j int, v ...E) S
	func Reverse(s S)
	func Sort(x S)
	func SortFunc(x S, cmp func(a, b E) int)
	func SortStableFunc(x S, cmp func(a, b E) int)