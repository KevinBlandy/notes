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

	func Clear(m M)
	func Clone(m M) M
	func Copy(dst M1, src M2)
	func DeleteFunc(m M, del func(K, V) bool)
	func Equal(m1 M1, m2 M2) bool
	func EqualFunc(m1 M1, m2 M2, eq func(V1, V2) bool) bool
	func Keys(m M) []K
	func Values(m M) []V

