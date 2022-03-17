
----------------
go 泛型
----------------
	# 泛型本质上就是定义好了这个方法，channel，map可能会用到哪些“类型”。并且给这个类型取个名称
	# 使用的时候，必须指定出这个类型的具体的类型，这个类型必须只能是“可能用到的”类型之一
	# 语法
		[T1 <t1>, T2 <t2> | <t2>]

		* [] 可以声明多个泛型，使用逗号分割
		* 每个泛型后面声明泛型可能的类型
		* 多个类型可以使用 | 分割，表示or关系

	# 可以使用的一些预定义泛型类型关键字，包含在 constraints(golang.org/x/exp) 包中
		any
			* 表示任何类型
				type any = interface{}

		comparable
			* 必须是可以进行 == 比较的类型，map的key就必须是这个类型
		
		Interger
			* 整数数值
		
		Float
			* 小数数值
		

	# 自定义类型(constraint)
		type Foo interface {
			~int | ~int8 | ~int16 | ~int32 | ~int64 
		}

		* 多个类型使用 | 分割
		* ~ 符号的意思是“模糊匹配”，例如我自定义了类型: type MyInt int 如果使用了模糊匹配，那么改泛型在实际运行的的时候 MyInt 可以作为它的运行时类

		* 近似元素的类型T必须是底层类型(underlying type)自己，而且不能是接口类型
			// 错误的定义!
			type MyInt int
			type I0 interface {
				~MyInt // 错误! MyInt不是underlying type, int才是
				~error // 错误! error是接口
			}
		
		* 类型的嵌套
			func I2[K any, V interface{ int | int64 }]() {
			}

			* 联合(union)类型元素不能是类型参数(type parameter) 
				func I1[K any, V interface{ K }]() {} // // 错误, interface{ K }中K是类型参数
			
		* 联合(union)类型元素的非接口元素必须是空集
			int|string	空集
			int|~int	交集(int)

			func I3[K any, V interface{ int | any }](){} // 没有问题。any 本质上是 interface{}

			func I4[K any, V interface{ int | ~int }]() {}  // 错误！ int和!int相交

			type MyInt int 
			func I5[K any, V interface{ int | MyInt }]() {}		// 没有问题，int和MyInt是两个类型，不相交
			func I6[K any, V interface{ int | ~MyInt }]() {}	// 错误，int和~MyInt相交，交集是int
			
			type MyInt2 = int
			func I7[K any, V interface{ int | MyInt2 }]() {}	// 错误，int和MyInt2是相同类型，相交
		
		
		* 联合(union)类型元素如果包含多于一个元素
			* 不能包含包含非空方法的接口类型
			* 也不能是comparable或者嵌入comparable

			func I9[K interface{ io.Reader }]()		// 编译正常，因为这是正常的接口，没有联合元素
			func I10[K interface{ io.Reader | io.Writer }]() // 错误!不能编译。因为包含了两个元素，而且无论是`io.Reader`还是`io.Writer`都包含方法
			func I11[K interface {				// 编译正常，因为这是正常的接口，没有联合元素
				io.Reader
				io.Writer
			}]()
			func I12[K interface{ io.Reader | int }]()	// 错误! 联合类型多于一个元素，并且io.Reader包含方法
			func I13[K comparable | int]()  //错误! 不能编译.因为联合元素大于一个，并且不能是comparable
			func I14[K interface{ comparable } | int]() // 错误! 不能编译.因为联合元素大于一个，并且元素不能嵌入comparable
			
		* 包含非接口类型元素、近似元素和联合类型只能用作类型参数，或者其它用作约束接口的元素
		
		* 接口类型不定递归嵌入


	# 接口可包含约束
		type Foo interface {
			~int
			String() string
		}

----------------
go 泛型Map
----------------
	// StringIntMap K 必须是comparable类型，V 可以是任意类型
	type StringIntMap[K comparable, V any] map[K]V

	func main() {
		m := StringIntMap[string, any]{"name": "KevinBlandy"}
		log.Println(m)
		log.Println(make(StringIntMap[int, string]))
	}


----------------
go 泛型方法
----------------
	package main

	import (
		"log"
		"os"
	)

	func init() {
		log.Default().SetOutput(os.Stdout)
		log.Default().SetFlags(log.Ldate | log.Ltime | log.Lshortfile)
	}

	func Func[K comparable, V ~int | int64](p map[K]V) V {
		var ret V
		for _, val := range p {
			ret += val
		}
		return ret
	}

	func main() {
		log.Println(Func[string, int](map[string]int{
			"1": 1,
			"2": 2,
		}))

		// 编译器可以推断类型的时候，不需要指定类型
		log.Println(Func(map[string]int64{
			"1": 15,
			"2": 15,
		}))
	}


----------------
go 泛型channel
----------------
	type StringChan[T string | int] chan T

	func main() {
		ch := make(StringChan[string])
		go func() {
			ch <- "Hello"
			close(ch)
		}()
		log.Println(<-ch)
	}

----------------
go 泛型类
----------------
	type Foo[T any] struct {
		Val T
		Arr []T
	}

	func (f Foo[T]) foo() {
		log.Println(f.Val)
	}
	func (f Foo[string]) bar() string { // 这 string 不是字符串，而是泛型名称 "string"
		//return "" //cannot use "" (untyped string constant) as string value in return statement
		var ret string
		return ret
	}

	func main() {
		foo := Foo[string]{Val: "15", Arr: []string{"123"}}
		log.Println(foo)
		log.Println(foo.bar())
	}
