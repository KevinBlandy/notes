------------------------
函数
------------------------
	# 函数的定义
		func [函数名称]([参数列表]) ([返回值]) {

		}
	
		* 函数名称不能重复
		* 形参：形参名称，形参类型组成，多个参数使用逗号分隔，可以省略
		* 返回值：如果有多个，使用小括号包裹，并且使用逗号分隔，可以省略，表示无返回值，如果有返回值，必须要写 return 关键字
	
		func test(name string, age int)(int, string){
			return age, name
		}
	
	# 命名返回值变量
		func sum(p1 int, p2 int)(ret1 int, ret2 int){ // 返回值变量命名
			// 操作变量
			ret1 = 1
			ret2 = 2
			// 直接写return，不用指定要返回的变量（指定也不会错：return ret1, ret2）
			return 
		}
		
		* 相当于在函数中声明了一个，多个变量 
		* 可以在函数内部使用，函数执行完毕后，这个变量就会作为返回值
		* 对于多个返回值，要么都命名，要么都不命名

		* 返回值变量，也可以忽略掉不使用，返回的时候，返回相同类型的变量也可以
			func main() {
				r := test()
				fmt.Println(r)  // 1
			}
			func test() (y int) { // 这里声明了y，但是忽略了这个变量，没使用
				x := 1
				return x
			}
		
		* 在函数返回的时候，没有明确赋值的返回值都会被设置为默认值
			func foo() (result int, err error) {
				return 
			}
			func main() {
				result, err := foo()
				fmt.Println(result, err) // 0 <nil>
			}
		
		* 多个命名返回值存在的时候，return的数据必须要跟声明的数据一样
			// 声明了俩，返回了一个
			func foo() (result int, err error) {
				return 1 // not enough arguments to return
			}


	# 多个参数类类型相同时，可以简写
		func main() {
			r1, r2, r3, r4 := f1(1, 2, "3", "4")
			fmt.Println(r1, r2, r3, r4)
		}
		func f1(a, b int, c, d string) (e, f int, g , h string) {
			fmt.Println(a, b, c, d)
			e = a
			f = b
			g = c
			h = d
			return 
		}
				
		* 参数之间使用逗号分隔，最后声明参数类型
		* 对于返回参数列表也同样使用
	
	
	# 可变长参数
		func f1(a, b int, c ...string) {
			fmt.Println(a, b)		// 1 2
			fmt.Printf("%T\n",  c)  // []string
		}

		* 变长参数只能是最后一个，在类型前使用三个.声明
		* 它在函数中，实际会被封装成一个：切片
		* 变长参数，如果调用没给值，则是空切片
		
		* 调用的时候，要使用 ...解构赋值切片，只能是切片
			func foo(val ...int){
				fmt.Println(val)
			}
			
			foo([]int{1, 2, 3}...)		// 直接写切片
			arr := [...]int {1, 2}
			// foo(arr...) // 不能用数组cannot use arr (type [2]int) as type []int in argument to foo
			foo(arr[:]...)		// 把数组转换为切片
		
		* 参数也是切片的时，要注意使用解构表达式
			func main(){
				val := []interface {}{1, 2, 3}
				foo(val)			// [[1 2 3]]
				foo(val...)			// [1 2 3]
			}
			func foo(val ...interface{}){
				fmt.Println(val)
			}

	
	# 作用域
		* 全局作用域
			* 函数外部定义的变量，所有函数都能访问，这是全局

		* 局部作用域
			* 函数/大括号中定义的变量，只能在当前函数/大括号中访问，这是局部
			* 函数/大括号中操作变量，优先查找局部，局部没找到就往外，一直找到全局
	
------------------------
defer 语句
------------------------
	# defer 语句，就像是java中的 finally 代码块一样
		* 函数中 defer 后面的语句并不会立即执行，而是会“被添加到一个队列”（类似于FILO的栈）
		* 当函数在 return 前，会对这个队列中的语句，挨个执行，最后defer的语句，最先执行
		
			// 函数定义
			func test(){
				fmt.Println("start")
				defer fmt.Println("1")
				defer fmt.Println("2")
				defer fmt.Println("3")
				fmt.Println("end")
			}

			// 输出结果
			start
			end
			3
			2
			1
		
		* 可以用来关闭资源
		* 如果语句比较复杂，可以使用匿名立即执行函数
			func test()  {
				fmt.Println("开始")
				defer  func(){
					fmt.Println("defer1 执行")
				}()
				defer  func(){
					fmt.Println("defer2 执行")
				}()
				fmt.Println("结束")
				return
			}
	
	# defer 的执行时机
		* 在go中，函数的 return，底层并不是一个原子操作，而是分为了2步
			1. 给返回值，赋值
			2. 执行RET指令，返回值
		
		*  defer的执行时机，就是在给返回值赋值后，RET指令执行之前
	
		* 非命名函数，不会影响结果
			func main() {
				r := test()
				fmt.Println(r) // 1
			}
			func test() int {
				val := 1	
				defer  func(){
					val ++	
				}()
				return val
			}

		* 命名函数，可能会影响结果
			func main() {
				r := test()
				fmt.Println(r) // 2
			}
			func test() (retVal int) {
				val := 1
				defer  func(){
					retVal ++
				}()
				retVal = val
				return
			}

	# 函数返回局部变量指针，不用担心变量内存被释放
		func main(){
			fmt.Printf("%p\n", foo())	// 0xc00009c058
			fmt.Printf("%p\n", foo())	// 0xc00009c090
		}
		func foo () *int {
			retVal := 1
			return &retVal
		}

		* 因为有指针指向这个变量，所以它不会被回收
		* 编译器会自动选择在栈上还是在堆上分配局部变量的存储空间
	
	# 不能直接获取方法的指针，但是方法类型的变量可以
		func main(){
			var call func(string) int = foo
			_ = call("Hello")	// Hello

			// 获取到变量指针
			var p *func(string) int = &call
			fmt.Printf("%T\n", p)  // *func(string) int
			// 通过指针，执行方法
			(*p)("Hi")				// Hi

			// 不能直接获取方法的指针
			// fmt.Println(&foo)  // cannot take the address of foo
		}
		func foo (val string) int {
			fmt.Println(val)
			return 5
		}
	
	# 函数返回的是值，不是变量，不能获取它的指针


------------------------
函数类型
------------------------
	# 函数也是有类型的概念，核心的元素就是；形参类型，返回值类型
		func func1(){
			fmt.Println("Hello")
		}
		func func2(val int){
			fmt.Println("Hello")
		}
		func func3(val ...int) []int {
			fmt.Println("Hello")
			return  val
		}
		func main() {

			f1 := func1
			var f2 func(int) = func2
			var f3 func(...int)[]int = func3

			fmt.Printf("%T\n", f1) // func()
			fmt.Printf("%T\n", f2) // func(int)
			fmt.Printf("%T\n", f3) // func(...int) []int
		}

		* 函数类型默认值为nil，并且它只能和nil进行 == 比较
		* 具有相同参数，相同返回值的函数，视为相同类型的函数
		* 不同类型的函数，不能相互赋值
		* func(val ...int) 和 func(val []int)，并不是同一个类型，不能相互赋值

	# 函数作为参数
		func call(v1, v2 int, v3 string){
			fmt.Println(v1,  v2, v3)
		}
		func test(call func(int, int, string)){
			call(1, 2, "123")
		}
		func main() {
			test(call)
		}
	
	# 函数作为返回值
		* 函数内部不能使用func再次定义命名函数
			func bar(){
				func foo(){  // unexpected foo, expecting (
				}
			}
		
		* 函数返回函数，只能返回外部已经声明了的命名函数，或者是内部创建的匿名函数
	
	# 立即执行函数
		* 匿名函数定义完成后立即加括号，就表示直接调用
			func main() {
				func(){
					fmt.Println("Hello")
				}()
			}
		
		* 可以有返回值和参数
			func main() {
				retVal := func(val string) string {
					return "Hello" + val
				}("World")
				fmt.Println(retVal) // HelloWorld
			}
		

	# 匿名函数
		* 就是没名字的函数，可以使用变量引用
		* 然后可以传递该变量
			var fun = func (){
				fmt.Println("Hello")
			}
			func main() {

				fun()
				
				// 完整的声明出类型
				var sum func(int, int) int = func(p1, p2 int)(int){
					return p1 + p2
				}
				fmt.Println(sum(3, 5))
			}

			
	
		* 这种匿名函数可以在函数内部定义
			func main() {
				var f = func() string {
					fmt.Println("Hello")
					return "233"
				}()
				fmt.Println(f)
			}
		
		* 匿名一般都是用在函数内部的


	# 闭包
		* 函数内部嵌套函数，保护局部变量
			func outter() func() int {
				val := 0
				return func () int {
					val ++
					return val
				}
			}

			func main() {
				call := outter()
				fmt.Println(call())	// 1
				fmt.Println(call())	// 2
				fmt.Println(call())	// 3
			}

		* 内部函数，就是要注意一个作用域的问题
			var arr []func()
			for _, v := range []int {1, 2, 3} {
				// v := v 没有这行代码，结果输出全部是3
				arr = append(arr, func(){
					fmt.Println(v)
				})
			}
			for _, f := range arr {
				f() // 输出全部是3
			}
		
------------------------
函数
------------------------
	# 使用defer，统计函数执行时间
		func foo() {
			defer bar()()
			// 模拟延迟
			time.Sleep(2 * time.Second)
		}

		func bar () func() {
			start := time.Now()
			fmt.Printf("开始执行：%v\n", start)
			return func(){
				fmt.Printf("执行完毕：%v\n", time.Now())
			}
		}
		// 开始执行：2020-12-10 20:19:41.6448597 +0800 CST m=+0.007009001
		// 执行完毕：2020-12-10 20:19:43.6709946 +0800 CST m=+2.033143901