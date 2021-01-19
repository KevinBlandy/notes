-------------------------
结构体
-------------------------
	# 类似对象，语法
		type [name] struct {
			[field] [type]
		}

		* name	类型名称，同一个package内不能重复
		* field	字段名称，同一个结构体中不能重复
		* type	字段类型
	

		type User struct {
			name  string
			age int8
			skills []string
		}
		
		func main(){
			// 先声明，后初始化
			var me User
			me.name = "KevinBlandy"
			me.age = 27
			me.skills = []string {"Java", "Javascript", "Python", "Go"}
			fmt.Println(me)			// {KevinBlandy 27 [Java Javascript Python Go]}
			fmt.Printf("%T\n", me)	// main.User

		}

		* 如果声明的时候没有指定默认值，则就使用类型的默认值
		* 结构体内部的字段内存，是连续分配的
	
	# 多个字段类型相同，可以写一行
		type User struct {
			name, desc  string
			age int8
			skills []string
		} 
	
	# 初始化方法
		* map形式的初始化
			me := User {
				name: "Rocck",
				age: 23,
				skills: []string{"JAVA"},
			}
			fmt.Println(me)			// {Rocck 23 [JAVA]}

		* 数组形式的初始化
			user := User {
				"Vin",
				23,
				[]string{"Java", "Rubyt"},
			}
			fmt.Println(user) // {Vin 23 [Java Rubyt]}

			* 属性需要挨个设置，一个不能少，否则异常
				user := User { "Vin", 23} // .\main.go:13:24: too few values in User literal
		

		* 直接创建，不初始化
			var x = User{}	// 此时字段类型都是默认值
			fmt.Println(x)
		
		* 不管是什么初始化，都要考虑到对字段是否有访问权限

	
	# 匿名结构体
		*  变量和结构体同时创建，只用一次

		var tmp struct {
			name string
			age int8
		}
		tmp.name = "Jonathan"
		tmp.age = 25
		fmt.Println(tmp)			// {Jonathan 25}
		fmt.Printf("%T\n", tmp)		// struct { name string; age int8 }
		
		* 声明的时候直接赋值，初始化方式可以是数组形式，也可以是Map形式
		    user := struct {
				Name string
				Age  int
			}{
				"张三", 20,
			}
			fmt.Println(user) 			// {张三 20}
			fmt.Printf("%T", user)		// struct { Name string; Age int }
		
	# 空结构体
		* 不存在任何字段的结构体，就是空结构体，它的大小是0
		* 在把map当做set的时候，就可以使用空结构体作为value
			set := make(map[string]struct{})
			set["name"] = struct{}{}
		
	
	# 结构体是值类型 
		u1 := User{
			name: "name1",
		}

		u2 := u1
		u2.name = "name2"
		
		fmt.Println(u1.name) // name1
		fmt.Println(u2.name) // name2
	
	# 结构体与指针
		* 指针可以通过 new 获取，也可以通过 & 获取

		var user *User = new(User)
		user.name = "小明"
		user.age = 23
		user.skills = []string {"唱", "跳", "rap", "篮球"}
		fmt.Println(user)	// &{小明 23 [唱 跳 rap 篮球]}
		fmt.Println(*user)	// {小明 23 [唱 跳 rap 篮球]}

		* 结构体的成员变量，也可以通过指针操作
			type User struct {
				name string
			}
			func main(){
				var user = User{"曹操"}
				var p *string = &user.name
				fmt.Println(*p)		// 曹操

				*p = "刘备"
				fmt.Println(user.name)		// 刘备
			}

	# 结构体指针的语法糖
		// 创建结构体
		user := User{"Kevin", 27, []string{"Java", "Python"}}

		// 获取结构体指针
		var p *User = &user;

		fmt.Printf("%p\n", p)	// 0xc0000b2330

		// 原始指针操作
		(*p).name = "凯文布兰迪"
		fmt.Println(user)		// {凯文布兰迪 27 [Java Python]}

		// 语法糖指针操作
		p.name = "KevinBlandy"
		fmt.Println(user)		// {KevinBlandy 27 [Java Python]}

		* 通俗说，就是结构体指针，可以通过.操作，直接读写属性
	
	# 构造方法
		* 结构体没得构造方法，可以直接模拟一个函数
			type User struct {
				name  string
				age int8
				skills []string
			}

			func newUser(name string, age int8, skills []string) *User {
				return &User{
					name, age, skills, 
				}
			}

			func main(){
				var user *User = newUser("小明", 23, []string{"唱", "跳", "rap", "篮球"})
				fmt.Println(user) // &{小明 23 [唱 跳 rap 篮球]}
			}

		
		* 一般建议返回指针
		* 因为返回的是结构体，那么会进行结构体的拷贝，如果结构体字段很多，会消耗额外的性能
		* 返回的是结构体指针，不会进行拷贝操作，只是返回一个固定大小的指针而已
	

	# 方法
		* 方法，跟OOP中的类方法一个德行，只能被这个类/类对象调用
			func (type Type) name(){
			
			}
	
			* 核心的东西，就是在 方法名称前面声明，能调用这个方法的类类型
			* 使用指针也可以，还可以减少性能消耗
				func (this *User) say(){
				}
			
			func (this User) say(){
				fmt.Printf("大家好，我是%s，今年%d，喜欢%s", this.name, this.age, this.skills)
			}

			func main(){
				var user *User = newUser("蔡徐坤", 23, []string{"唱", "跳", "rap", "篮球"})
				user.say() // 大家好，我是蔡徐坤，今年23，喜欢[唱 跳 rap 篮球]
			}

			* 谁调用方法，this指向谁
		
		
		* 其他类型调用，会异常
			type Foo struct {}
			func main(){
				foo := Foo{}
				foo.say() // foo.say undefined (type Foo has no field or method say)
			}
		
		
		* 只能给自己包里定义的类型，添加“方法”，不能给其他的类型添加（起别名也不行）
			// string 不是自己包的，异常
			func (this string) test() string {		// .\main.go:8:6: cannot define new methods on non-local type string
				return "Hello" + this
			}
			
			// 自定义一个string的类型，可以曲线救国
			type myString string
			func (this myString) test()  {
				fmt.Println("Hello " + this)
			}

			func main(){
				var val myString = "World"
				val.test()  // Hello World	
			}

			* 我想是因为这个涉及到一个“方法继承”的安全问题，子类可以调用父类的方法
			* 不能让用户给string定义一些危险的方法，别人可能会误调用啥的
	
		* 可以通过限定类，直接调用方法，传递执行对象，类似于反射
			type myString string
			func (this myString) test(p string)  {
				fmt.Println(p + "Hello " + string(this) + " ")
			}
			func main(){
				// 类型实例
				var val myString = "World"
				// 直接通过类型调用，第一个参数就是实例对象
				myString.test(val, "hi， ")
			}
		
		* nil也是一个合法的接收者
			type IntList struct {
				Val int				// 当前节点的值
				Next *IntList		// 下一个节点
			}
			func (i *IntList) Sum() int {
				if i == nil { // this 可能是nil
					return 0
				}
				return i.Val + i.Next.Sum()
			}
			func main(){
				linked := IntList{1, &IntList{2, &IntList{3, &IntList{4, &IntList{5, nil}}}}}
				sum := linked.Sum()
				fmt.Println(sum)	// 15
			
				// 通俗点的写法
				var p *IntList = nil
				fmt.Println(p.Sum()) // 0
			}
		
		* 方法，也可以当做变量一样传递
			type User struct {
				Name string
			}
			func (u User) Say(){
				fmt.Println(u.Name)
			}

			func main(){
				u := User{"Java"}
				u.Say()		// Java

				// 通过实例获取
				var f1 func() = u.Say
				f1()			// Java

				// 通过类获取，方法接受者是实体变量	T.F
				var f2 func(User) = User.Say
				f2(u)				// Java

				// 通过类获取，方法接受者是指针	(*T).F（这个方法会自动生成）
				var f3 func(*User) = (*User).Say
				f3(&u)		// Java
			}

			* 获取方法，如果方法接收者是实体，使用：T.F，如果方法接受者是指针使用：(*T).F
			* 相同的方法类型，可以相互赋值
				type User struct {
					Name string
				}
				func (u *User) F1(val string) string {
					return "f1-" + u.Name + val
				}
				func (u *User) F2(val string) string {
					return "f2-" + u.Name + val
				}
				func main(){
					u := User{"Java"}
					
					// 方法类型变量
					var f func(*User, string) string

					f = (*User).F1
					fmt.Println(f(&u, " 1"))		// f1-Java 1

					f = (*User).F2
					fmt.Println(f(&u, " 2"))		// f2-Java 2
				}

	# 匿名字段
		* 通俗理解就是，结构体的字段不需要声明名称
		* 字段的名称就是，类型的名称

			type User struct {
				string
				int
				bool
			}
			func main(){
				user := User{"Coco", 15, true}
				fmt.Println(user)			// {Coco 15 true}
				fmt.Println(user.string)  	// Coco

				user.bool = true
				fmt.Println(user.bool)  	// true
			}
			
		* 由于结构体的字段名称不允许重复，所以匿名字段，数据类型在结构体里面是唯一的
		* 可以混合定义，只要是名称不重复就OK
			type User struct {
				bar string
				int
				bool
				string
				foo int
			}
			func main(){
				user := User{"Coco", 15, true, "Ruby", 25}
				fmt.Println(user)			// {Coco 15 true Ruby 25}

				user = User{
					bar: "",
					int: 21,  // 对匿名字段定义值
					foo: 25,
				}
				fmt.Println(user)			// { 21 false  25}
			}

			
			// 名称是重复的
			type User struct {
				string string
				string			// duplicate field string
			}
		
		* 涉及到访问权限的时候，如果说匿名成员的属性是公开的，那么也是可以直接访问的
			* foo 包
				package foo
				type foo struct {
					FooName string
				}
				type Bar struct {
					foo
				}
			* main包
				bar := foo.Bar{}
				bar.FooName = "Hello"
				fmt.Println(bar)
		
	# 属性的可见性问题
		* 如果字段是大写开头，表示在任何包下都可以访问
		* 如果字段是小写开头，表示这个属性只能在当前包下才能访问

	# 属性的Tag
		* 类似于Java中的注解
		* 属性可以添加Tag，用于在某些场景下，对属性做一些特殊的处理，直接在属性后面使用 `` 设置
			`[tag]:[config]`

		* 例如，一些的Tag
			type Foo struct {
				Name string `json:"name"`		// 把这个Name字段，序列化成json后，json字段名称叫做：name
			}
			type Foo struct {
				name string `db:"u_name"`		// 在DB映射的时候，把name字段，映射为 u_name 列名
			}
			type Foo struct {
				Name string `db:"u_name" json:"name"`		// 可以一次性定义多个，使用空格分割
			}
		
		* 可以通过reflect反射获取到这些字段上的tag
	
	# 结构体的比较
		* 如果结构体的全部成员都是可以比较的，那么结构体也是可以比较的
			type User struct {
				Name string
			}
			func main() {
				fmt.Println(User{} == User{}) // true
			}
		
		* 如果结构体包含了不能比较的属性，就会变异异常
			type User struct {
				Name string
				Hobby []string
			}
			func main() {
				fmt.Println(User{} == User{}) // invalid operation: User literal == User literal (struct containing []string cannot be compared)
			}
		
		* 字段的顺序也必须是一样的，如果声明的顺序不一样，也会被认为是不可比
		* 可比较的结构体，可以作为map的key
		* 结构体不能和nil进行比较，因为它不可能为nil，声明即创建


-------------------------
OOP
-------------------------
	# 面向对象的模拟实现，使用结构体的嵌套
		* 正确的声明好关系，就能正确的按照导航语法进行访问
	
	# 匿名嵌套，可以是值，或者指针，本质上就是通过组合，实现了继承
		type Foo struct {
			name string
		}

		type Bar struct{
			*Foo
		}
		func main() {
			b := Bar{&Foo{"Foo Name"}}
			fmt.Println(b)
			fmt.Println(b.name)		// Foo Name
		}	
	
	# 匿名嵌套的结构体，可以直接访问到内部结构体的属性，这就是继承了
			type Bar struct {
				bar string
			}
			type Foo struct {
				foo string
				Bar
			}
			type User struct {
				Foo
				name string
				val int
			}
			func main(){
				user := User{Foo{"Foo name", Bar{"Bar name"}}, "Coco", 15}
				fmt.Println(user)					// {{Foo name {Bar name}} Coco 15}
				fmt.Println(user.Foo.Bar.bar)		// Bar name
				// 直接访问内部匿名结构体的匿名字段
				fmt.Println(user.foo)				// Foo name
				// 直接访问多层嵌套也OK
				fmt.Println(user.bar)				// Bar name
			}
	
	# 结构体属性，不能包含当前类型
		type User struct {  // invalid recursive type User
			Name string
			Parent User
		}

		* 指针可以
			type User struct {
				Name string
				Parent *User
			}
		
		
	# 方法，也可以被继承调用，也可以被覆写
		type Animal struct {
			name string
		}
		type Dog struct {
			Animal
		}
		type Cat struct {
			Animal
		}
		// 父级的方法
		func (this Animal) say(){
			fmt.Printf("Im %s\n", this.name)
		}

		// 子类覆写
		func (this Cat) say(){
			// 调用父类的方法
			this.Animal.say()
			// 执行自己的方法
			fmt.Println("miao miao")
		}

		func main(){
			cat := Cat{Animal{"cat"}}
			// 子类调用，会先执行父类的方法输出，再执行自己的
			cat.say()		// Im cat \r\n miao miao

			dog := Dog{Animal{"Dog"}}
			// 子类调用
			dog.say()		// Im Dog
		}
	
	
	# 深度与广度的问题
		* 深度问题：如果说外部结构体的某个字段名称和内部结构体的名称冲突，则越是外部的越优先
			// 属性 ==========================
			type Foo struct {
				name string
			}

			type User struct {
				Foo
				name string
				foo int
			}
			func main(){
				user := User{Foo{"Foo name"}, "Coco", 15}
				fmt.Println(user.Foo.name)	// Foo name

				// 同名属性，外部结构体的属性名称优先
				fmt.Println(user.name)		// Coco
			}

			// 方法 ==========================
			type Animal struct {
				name string
			}
			type Cat struct {
				Animal
			}
			// 覆写的函数
			func (this Cat) say(){
				fmt.Printf("Im %s, I say: miao~\n", this.name)
			}
			// 父级的函数
			func (this Animal) say(){
				fmt.Printf("Im %s\n", this.name)
			}
			func main(){
				cat := Cat{Animal{"cat"}}
				cat.say()		// Im cat, I say: miao~
			} 

			* 方法的深度问题，其实就是覆写问题了，越接近，权限越高
			* 同名的函数，但是调用方法的类型不同，就是覆写

	
		* 广度问题：如果说存在相同层级相同名称的匿名结构体，访问的时候，必须指出要访问的类型，不然异常
			// 属性 ==========================
			type Bar struct {
				val string
			}
			type Foo struct {
				val string
			}
			type User struct {
				Foo
				Bar
			}
			func main(){
				user := User{Foo{"foo val"}, Bar{"bar val"}}
				// 不指出要访问的类型，会异常
				// fmt.Println(user.val)					//ambiguous selector user.valtln()

				// 指出要访问字段的类型
				fmt.Println(user.Foo.val)					// foo val
			}
			
			// 方法 ==========================
			type Super1 struct {
				name string
			}
			type Super2 struct {
				name string
			}
			type Sub struct {
				Super1
				Super2
			}
			func (this Super1) foo(){
				fmt.Printf("Super1 im：%s\n", this.name)
			}
			func (this Super2) foo(){
				fmt.Printf("Super2 im：%s\n", this.name)
			}
			func main (){
				val := Sub{
					Super1{"s1"}, Super2{"s2"},
				}
				// 不指出要方法的类型，会异常
				// val.foo()  // ambiguous selector val.foo
				// 指出访问方法的类型
				val.Super1.foo();		// Super1 im：s1
				val.Super2.foo();		// Super2 im：s2
			}
		
	
	# 注意，struct的方法和字段，名字不能一样，否则异常
		type MyError struct {
			Val string
			Error error
		}

		func (this *MyError) Error () string {  // type MyError has both field and method named Error
			return "异常啦"
		}