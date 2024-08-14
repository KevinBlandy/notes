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

	func All[Slice ~[]E, E any](s Slice) iter.Seq2[int, E]
	func AppendSeq[Slice ~[]E, E any](s Slice, seq iter.Seq[E]) Slice
	func Backward[Slice ~[]E, E any](s Slice) iter.Seq2[int, E]
	func BinarySearch[S ~[]E, E cmp.Ordered](x S, target E) (int, bool)
	func BinarySearchFunc[S ~[]E, E, T any](x S, target T, cmp func(E, T) int) (int, bool)
	func Chunk[Slice ~[]E, E any](s Slice, n int) iter.Seq[Slice]
	func Clip[S ~[]E, E any](s S) S
	func Clone[S ~[]E, E any](s S) S
	func Collect[E any](seq iter.Seq[E]) []E
	func Compact[S ~[]E, E comparable](s S) S
	func CompactFunc[S ~[]E, E any](s S, eq func(E, E) bool) S
	func Compare[S ~[]E, E cmp.Ordered](s1, s2 S) int
	func CompareFunc[S1 ~[]E1, S2 ~[]E2, E1, E2 any](s1 S1, s2 S2, cmp func(E1, E2) int) int
	func Concat[S ~[]E, E any](slices ...S) S
		* 拼接切片

	func Contains[S ~[]E, E comparable](s S, v E) bool
	func ContainsFunc[S ~[]E, E any](s S, f func(E) bool) bool
	func Delete[S ~[]E, E any](s S, i, j int) S
	func DeleteFunc[S ~[]E, E any](s S, del func(E) bool) S
	func Equal[S ~[]E, E comparable](s1, s2 S) bool
	func EqualFunc[S1 ~[]E1, S2 ~[]E2, E1, E2 any](s1 S1, s2 S2, eq func(E1, E2) bool) bool
	func Grow[S ~[]E, E any](s S, n int) S
	func Index[S ~[]E, E comparable](s S, v E) int
	func IndexFunc[S ~[]E, E any](s S, f func(E) bool) int
	func Insert[S ~[]E, E any](s S, i int, v ...E) S
	func IsSorted[S ~[]E, E cmp.Ordered](x S) bool
	func IsSortedFunc[S ~[]E, E any](x S, cmp func(a, b E) int) bool
	func Max[S ~[]E, E cmp.Ordered](x S) E
	func MaxFunc[S ~[]E, E any](x S, cmp func(a, b E) int) E
	func Min[S ~[]E, E cmp.Ordered](x S) E
	func MinFunc[S ~[]E, E any](x S, cmp func(a, b E) int) E
	func Repeat[S ~[]E, E any](x S, count int) S
	func Replace[S ~[]E, E any](s S, i, j int, v ...E) S
	func Reverse[S ~[]E, E any](s S)
	func Sort[S ~[]E, E cmp.Ordered](x S)
	func SortFunc[S ~[]E, E any](x S, cmp func(a, b E) int)
	func SortStableFunc[S ~[]E, E any](x S, cmp func(a, b E) int)
	func Sorted[E cmp.Ordered](seq iter.Seq[E]) []E
	func SortedFunc[E any](seq iter.Seq[E], cmp func(E, E) int) []E
	func SortedStableFunc[E any](seq iter.Seq[E], cmp func(E, E) int) []E
	func Values[Slice ~[]E, E any](s Slice) iter.Seq[E]