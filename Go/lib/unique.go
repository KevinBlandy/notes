-------------------
unique
-------------------
	# unique 包灵感来自于 go4.org/intern
		* 是为了弥补Go语言缺乏对 interning 内置支持的空缺。
		* interning是按需重复使用具有同等值对象的技术，减少创建新对象的动作。这种创建模式经常用于不同编程语言中的数和字符串，可以避免不必要的对象重复分配的开销。
		* unique 包提供了一种高效的值去重和快速比较的机制，可以用于优化某些特定场景下的程序性能。
		
-------------------
var
-------------------

-------------------
type
-------------------

	type Handle[T comparable] struct {
		// contains filtered or unexported fields
	}

	func Make[T comparable](value T) Handle[T]
		* 创建 Handle 实例

	func (h Handle[T]) Value() T
		* 获取值的拷贝

-------------------
func
-------------------


-------------------
Demo
-------------------
	# 用法
		package main

		import (
			"fmt"
			"unique"
		)

		func main() {
			v1 := unique.Make("s1")
			v2 := unique.Make("s1")
			v3 := unique.Make("foo")

			fmt.Println(v1 == v2) // true
			fmt.Println(v1 == v3) // false
			fmt.Println(v2 == v3) // false

			fmt.Println(v1.Value()) // s1
			fmt.Println(v2.Value()) // s1
			fmt.Println(v3.Value()) // foo
		}

