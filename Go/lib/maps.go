-----------------
maps
-----------------
	# 用户处理 map 的工具包，可用于泛型

-----------------
var
-----------------

-----------------
type
-----------------

-----------------
func
-----------------

	func All[Map ~map[K]V, K comparable, V any](m Map) iter.Seq2[K, V]
	func Clone[M ~map[K]V, K comparable, V any](m M) M
	func Collect[K comparable, V any](seq iter.Seq2[K, V]) map[K]V
	func Copy[M1 ~map[K]V, M2 ~map[K]V, K comparable, V any](dst M1, src M2)
	func DeleteFunc[M ~map[K]V, K comparable, V any](m M, del func(K, V) bool)
	func Equal[M1, M2 ~map[K]V, K, V comparable](m1 M1, m2 M2) bool
	func EqualFunc[M1 ~map[K]V1, M2 ~map[K]V2, K comparable, V1, V2 any](m1 M1, m2 M2, eq func(V1, V2) bool) bool
	func Insert[Map ~map[K]V, K comparable, V any](m Map, seq iter.Seq2[K, V])
	func Keys[Map ~map[K]V, K comparable, V any](m Map) iter.Seq[K]
	func Values[Map ~map[K]V, K comparable, V any](m Map) iter.Seq[V]