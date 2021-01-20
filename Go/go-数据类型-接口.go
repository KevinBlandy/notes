------------------------
接口
------------------------
	# 接口，只包含方法，不包含属性

	# 接口的定义
		type [name] interface {
			[method]
		}

		* name		接口的名称
		* method	接口方法的定义


		type Foo struct{
			string
		}
		func (this *Foo) FooCall(){
			fmt.Printf("我执行了:%s\n", this.string)
		}
		// 定义了一个接口，指定了要实现的方法名称以及签名
		type Bar interface {
			FooCall()
		}
		func main (){
			// 创建子类
			foo := new(Foo)
			foo.string = "Im Foo"
			
			// 接口实现子类
			var bar Bar = foo
			// 调用接口，执行子类实现
			bar.FooCall();		// 我执行了:Im Foo
		}
			
	
	# 非入侵式的接口
		* 接口只需要描述方法，名称，参数，返回值等等
		* 实现类，并不需要与这个接口有任何语法上的关系，例如：imple, extends 等等
		* 像鸭式辩型法，结构体只要实现了接口中的所有方法，就可以被当作是接口的实现了
		* 接口的“实现类”，必须全部实现了接口中定义的方法，不然没法初始化
	
	# 把对象赋值给接口
		* 直接把对象的指针赋值给接口就行，只要对象实现了接口的所有方法
			type Animal interface {
				Name() string
				Say(msg string)
			}

			type Cat struct {
				name string
			}

			// 这个实现，使用指针传递this
			func (this *Cat) Name() string {
				return this.name
			}

			// 这个实现，使用值赋值传递this，Go会根据这个函数自动生成：func (this *Cat) Say(msg string)
			func (this Cat) Say(msg string){
				fmt.Printf("Im:%s，I say:%s\n", this.name, msg)
			}

			func main(){
				// 把实现的指针，赋值给接口
				var animal Animal = &Cat{"阿喵"}
				animal.Say("Hello")
			}
		
		* 对于是赋值指针还是值（也叫做实例）给接口，首先要这么去理解，把一个对象或是指针赋值给接口的时候，接口就会保存它，暂且叫做“目标对象”
		* 接口在调用方法的时候，就会把“目标对象”作为this传递给方法，如果类型不匹配，就会被认为是没实现
			如果方法this声明的是值，那么“目标对象”必须是值
			如果方法this声明的是指针，“目标对象”必须是指针
		* 注意，Go会自动为“this是值的方法”，生成新的“this是指针的函数”，反之则不会
			type Integer int
			// Less方法，使用的是值，系统会自动生成，指针的方法
			func (a Integer) Less(b Integer) bool {
				return a < b
			}
			// Add方法，使用的是指针
			func (a *Integer) Add(b Integer) {
				*a += b
			}
			// 接口1
			type LessAdder interface {
				Less(b Integer) bool
				Add(b Integer)
			}
			// 接口2
			type Lesser interface {
				Less(b Integer) bool
			}
			func main(){
				var a Integer = 1
				// 赋值指针没问题，Go会自动给“Less”方法生成一个指针函数
				var b LessAdder = &a
				// 赋值实例异常，因为“Integer”中实现了LessAdder接口的“Add”方法，这个方法this的接收是指针，而不是值。所以认为没实现
				// var c LessAdder = a   // Integer does not implement LessAdder (Add method has pointer receiver)go

				fmt.Println(a, b, c)

				var a1 Integer = 1
				// 赋值指针没有问题，Go会自动给“Less”方法生成一个指针函数
				var b1 Lesser = &a1
				// 赋值值也没问题，本身这个函数就是接收值作为this
				var c1 Lesser = a1
				fmt.Println(a1, b1, c1)
			}
		
		* 总结这个行为
			* 赋值给接口的是指针，那么无所谓方法的this接收是指针还是实例，如果是实例的话，Go会自动生成指针
			* 赋值给接口的是实例，那么如果方法是的接收是指针，则会抛出异常


	# 把接口赋值给接口
		* 不同的接口，只要是方法一样，无所谓次序，就可以随便相互赋值
		* 赋值操作，只要左边的方法，在右边都有声明，就能赋值（左边是右边的子集）
			type I1 interface {
				m1()
			}
			type I2 interface {
				m1()
				m2()
			}

			func main(){
				var t1 I1
				var t2 I2
				// 合法的情况下，可以直接赋值
				t1 = t2
				// 异常
				t2 = t1  // I1 does not implement I2 (missing m2 method)go
			}
		
	# 强制验证接口实现
		* 一个结构体，可以只实现接口中的部分方法
		* 如果需要结构体实现所有，那么可以使用一个表达式
			var _ Inter = (*Obj)(nil)

			* 声明 _ 变量（没人用）会把一个 nil 的空指针从 Obj 转成 Inter
			* 如果Obj没有实现完Inter的接口方法，编译器就会报错
		
		* demo
			type Bar interface {
				Foo()
				Bar()
			}
			type Foo string
			func (f *Foo) Foo(){}
			func (f *Foo) Bar(){}
			func main() {
				var _ Bar = (*Foo)(nil)  // 实现了所有方法，不会报错
			}

				
	# 查询
		* 查询语法
			foo, ok := foo.(Bar)

			* foo，最左边的值，一定要是一个接口，接口的指针都不行
			* 右边可以判断是否是子接口，某个实例类型或者某个实例类型的指针

			* 可以省略ok参数，如果省略的话，类型转换失败，则会抛出异常
				var w io.Writer
				w = os.Stdout
				f := w.(*os.File) // success: f == os.Stdout
				c := w.(*bytes.Buffer) // interface conversion: io.Writer is *os.File, not *bytes.Buffer

		* 定义
			// 动物接口
			type Animal interface {
				Say() // 动物都会说话
			}
			// 猫科接口
			type Cat interface {
				Play() // 猫会玩耍
			}
			// 犬科接口
			type Dog interface {
				Bark() // 狗会吠
			}

			// 狸花猫
			type TabbyCat struct {
				name string
			}
			func (this *TabbyCat) Say(){
				fmt.Println("我是猫，我叫：" + this.name)
			}
			func (this *TabbyCat) Palay(){
				fmt.Println("我是猫，我在玩耍")
			}

			// 哈士奇
			type Husky struct {
				name string
			}
			func (this *Husky) Say(){
				fmt.Println("我是狗，我叫：" + this.name)
			}
			func (this *Husky) Bark(){
				fmt.Println("我是狗，我在玩耍")
			}

		* 接口查询：把接口，转换为接口
			func main(){
				// 创建动物接口的实现，实现类是猫和狗
				var cat Animal = &TabbyCat{"阿喵"}
				var dog Animal = &Husky{"啊汪"}
				cat.Say()
				dog.Say()

				// 把动物强制转换为子类接口，狗
				husky, ok := dog.(Dog)
				fmt.Println(husky, ok)

				if husky, ok := dog.(Dog); ok {
					fmt.Println(husky, ok)
				}
		
			}
		
		* 实例查询：接口，转换为实例
			// 赋值的是实例
			var dog Animal = Husky{"啊汪"}
			
			// 指针判断，是否是指定实例的指针
			if husky, ok := dog.(*Husky); ok{
				husky.Bark()	// 不会执行
			}
			// 实例判断，是否是指定类型的实例
			if husky, ok := dog.(Husky); ok{
				husky.Bark()		// 我是狗，我在玩耍
			}
		
		* 方法查询：接口，转换为“可以执行某些方法”的实例
		* 仅仅查询，接口是否实现了某些方法，而不必在意是实现的什么接口

			type Foo int
			func (* Foo) Test(){
				fmt.Println("Test")
			}
			func (* Foo) NewP(){
				fmt.Println("NewP")
			}
			func main(){
				var foo interface{} = new(Foo)
				fmt.Println(foo)

				//foo.Test()		// foo.Test undefined (type interface {} is interface with no methods)
				//foo.NewP()		// foo.NewP undefined (type interface {} is interface with no methods)

				// 仅仅查询，是否实现了某些方法
				if r, ok := foo.(interface {
					Test()
					NewP()
				}); ok {
					// OK的情况下，r就可以调用查询方法了
					fmt.Printf("%T\n", r)  // *main.Foo
					r.Test()			// Test
					r.NewP()			// NewP
				}
			}
					
	# 接口继承接口
		* 接口的组合和结构体的组合是一样的
			type Foo1 interface {
				Fun1()
			}
			type Foo2 interface {
				Fun1()
				Fun2()
				Foo1
			}

			type Bar struct {
			}
			func (this *Bar) Fun1(){
				fmt.Println("fun1 执行")
			}
			func (this *Bar) Fun2(){
				fmt.Println("fun2 执行")
			}

			func main(){
				var val1 Foo1 = &Bar{}
				val1.Fun1()		// fun1 执行

				var val2 Foo2 = &Bar{}
				val2.Fun1()		// fun1 执行
				val2.Fun2()		// fun2 执行
			}
	
		* 相同的方法，会覆盖，只留一个
	
	# 结构体继承接口
		* 结构体也可以继承自接口
			package foo
			type Foo interface {
				Foo()
				foo()
			}

			type Bar struct {
				foo.Foo // 继承foo
			}
			func (* Bar) test(){ // 自己定义了一个方法
				fmt.Print("test")
			}
			func main(){
				var b foo.Foo = Bar{}
				b.Foo() // 异常，这个结构体没实现Foo方法: Bar does not implement foo.Foo (missing Foo method)
			}

	
	# Any类型
		* 就像是Java中的Object一样，可以指向任何数据
			var val interface{}
		
		* 指向任何数据类型
			var val interface{}
			val = 1
			val = "2"
			val = 2.6
			val = true
			val = struct {
				string
			}{"1"}
			fmt.Println(val)
		
	# interface{} 之间的比较
		* 如果接口的值，非同一个类型，返回false
		* 如果接口的值，是同一个类型，那就返回这个值的比较结果
		* 如果接口的值不能比较，返回异常
				var v1 interface{} = "1"
				var v2 interface{} = "1"
				var v3 interface{} = 5
				var v4 interface{} = []int {1, 2, 3}
				fmt.Println(v1 == v2) 	// 类型相同，值相同，true
				fmt.Println(v1 == "0")	// 类型不同，值相同，false
				fmt.Println(v1 == v3)	// 类型不同，值不同，false
				fmt.Println(v4 == v4)	// 不可比较的类型，comparing uncomparable type []int

		* 接口可以作为map的key，以及switch，前提是它的值必须是可比较的，不然会异常
				config := map[interface{}]struct{} {
				}
				config[[]string{"1"}] = struct{}{} // hash of unhashable type []string
			
	# 需要注意接口非空，但是值是空的问题
			var val io.Writer
			
			var buf *bufio.Writer
			
			// 值是nil
			val = buf	

			// 接口并不是nil
			fmt.Println(val == nil)  // false

			// 操作接口，会抛出异常，因为本质上上值是nil
			val.Write([]byte("Hello"))  // runtime error: invalid memory address or nil pointer dereference
	
	# 接口的私有方法
		* 在接口中定义小写开头的方法，就是私有方法了
			type Foo interface {
				Say()
				private()  // 私有方法
			}

			* 一般来说，只有在当前包，才能创建使用这个接口

		* 可以通过继承来实现在其他包使用私有方法接口
			type Bar struct {
				foo.Foo // 继承了带有私有方法的接口
			}
			func (b *Bar) Say(){
				fmt.Println("Hello")
			}
			func main(){
				// 使用了带有私有方法的接口
				var b *Bar = &Bar{}
				b.Say()
			}
	
