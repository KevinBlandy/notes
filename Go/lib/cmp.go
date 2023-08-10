----------------------
cmp
----------------------

----------------------
var
----------------------

----------------------
type
----------------------
	# type Ordered interface {
			~int | ~int8 | ~int16 | ~int32 | ~int64 |
				~uint | ~uint8 | ~uint16 | ~uint32 | ~uint64 | ~uintptr |
				~float32 | ~float64 |
				~string
		}

		# 可排序的泛型约束

----------------------
func
----------------------

	func Compare(x, y T) int
		* 比较，返回 -1,0,1

	func Less(x, y T) bool
		* x 是否小于y


	
