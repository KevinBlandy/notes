-------------------------
type 自定义类型
-------------------------
	# 自定义类型
		type [name] [underlying-type]

		* name				类型名称
		* underlying-type	已经存在的类型
		
		type myInt int
		var val myInt = 15
		fmt.Printf("%T\n", val) // main.myInt
		
		* 可以在函数内部，全局定义
		* 可以嵌套
			type myInt int
			type uInt myInt
			var val uInt = 15
			fmt.Printf("%T\n", val) // main.uInt
	

	# 自定义类型和已经存在的类型之间，可以强制转换
		type User struct {
			name string
		}
		type myUser User

		func (user *User) foo(){
			fmt.Println(user.name)
		}
		func main(){
			var user myUser = myUser{"Coco"}
			fmt.Printf("%T=%v\n", user, user)  								// main.myUser={Coco}
			fmt.Printf("%T=%v\n", User(user), User(user))  					// main.User={Coco}
			fmt.Printf("%T=%v\n", myUser(User(user)), myUser(User(user)))  	// main.myUser={Coco}

			// 自定义类型，不能直接使用底层类型的方法，会异常
			// user.foo() // user.foo undefined (type myUser has no field or method foo)

			var u = User(user)		// Coco
			u.foo()
		}

		* 自定义类型，不能直接使用底层类型的方法，会异常，需要先强制转换
				
	
	# 自定义类型，可以使用与底层类型相同的晕算法
		type myInt1 = int
		type myInt2 = int
		func main(){
			var i1 myInt1
			var i2 myInt2
			fmt.Println(i1 == 0)		// true
			fmt.Println(i1 == i2)		// true
			fmt.Println(i2 == 0)		// true
		}

	
	# 基于接口类型创建的 type 类型与原接口类型的方法集合是一致的
		
		* 而基于自定义非接口类型创建的 type 类型则并没有“继承”原类型的方法集合，新的 type 类型的方法集合是空的

		type Foo interface {
			M()  // 接口有个 M 方法
		}

		type Bar struct{}

		func (b Bar) M() {}// struct 有个 M 方法

		type MyFoo Foo // 接口的 Type 类型
		type MyBar Bar // struct 的 Type 类型

		func main() {
			var v1 MyFoo
			v1.M() // 可调用

			var v2 MyBar
			v2.M() // 异常，非接口的 Type 类型，不会继承它的方法
		}


-------------------------
type 类型别名
-------------------------
	# 定义类型别名
		type [alias name]=[name]

		* alias name	别名
		* name			已经存在的类型
	
		type myInt = int
		var val myInt = 5
		fmt.Printf("%T\n", val) // int
		
		* 可以在函数内部，全局定义
		* 可以嵌套
			type myInt = int
			type uInt = myInt
			var val uInt = 5
			fmt.Printf("%T\n", val) // int
		
	
	# runne/byte 其实就是别名
		var v rune = '1'
		fmt.Printf("%T", v)	// int32

		var v byte = 255
		fmt.Printf("%T", v)	// uint8
	
	
	# 仅存在于编写过程, 提高代码可读性
		* 编译后，该是什么类型，还是什么类型
	
	
	# 类型别名与原类型拥有完全相同的方法集合，无论原类型是接口类型还是非接口类型。
