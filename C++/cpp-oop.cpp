-----------------------------
OOP
-----------------------------
	
	# 类成员的定义
	
		struct Member {
			string getTitle (){
				return title;
			};
			unsigned long long id;
			string title;
			unsigned long long getId ()const {
				// return (*this).id;
				return this -> id;
			};
		};
	
		
		* 方法中的 this 是指向当前对象的常量指针 const
			// 伪代码：this 类似于这样，指向的地址不能改变
			unsigned long long getId(Member *const this);
		
		* 常量成员函数：方法的形参后添加一个 const 关键字，表示指向常量的只读指针*
			// 伪代码：方法后加了 const 后，this 就是只读的，指向的地址不能变，也不能通过 this 修改成员属性
			unsigned long long getId(const Member *const this);
		
			*  常量对象，常量对象的引用或指针都只能调用常量成员函数

			
		* 在结构体中定义的方法，可以直接访问成员变量，加不加 this 都可以。
		* 成员函数也可以重载，包括 const 的规则的重载
		* 类的编译，是先编译成员变量，再是成员函数，所以可以先定义函数。
	
		* 使用作用域运算符在外部定义成员函数

			struct Member {
				unsigned long long id;
				string title;

				// 在类中声明方法
				string getTitle();
				unsigned long long getId() const ;
			};

			// 在外部实现方法
			string Member::getTitle (){
				return title; // 可以省略 this
			}
			unsigned long long Member::getId() const {
				return this -> id;
			};

			
			* 必须在内部先声明，才能在外部定义
			* 内部和外部方法可以同时存在，但是不能冲突。

	# 返回 this 实现链式调用
		
		* 方法可以返回 this, 以实现链式调用
	
			struct Member {
				unsigned long long id;
				string title;
				Member* setTitle (string title){
					this->title = title;
					return this;
				};
				Member* setId(unsigned long long id){
					this->id = id;
					return this;
				}
			};
			
			Member m = {};
			m.setId(100000LL)->setTitle("Newwww");

			Member *mp = &m;
			mp->setId(99999LL)->setTitle("DDDDDD");
			
			
			// 也可以返回对象的引用
			Member& setTitle (string title)  {
				return *this;
			};
		
		* 从 const 方法返回 this 指针/引用，则返回值类型必须是 const

			class Member {
				public:
					long id;
					// 如果返回引用/指针，则需要添加 const，以保证返回值不可修改
					const Member& decr() const {
						return *this;
					}
					// 返回复制对象，修改不会影响 this，可以不需要加 const
					Member incr()const {
						return *this;
					}
					void foo(){};
			};

			* 对于返回 const this 方法，不能链式调用那些可能对结果进行修改的方法

				m.decr().foo();		// foo 是非 const 的，因此会编译异常
				m.decr().incr();	// incr 是 const 的，可以继续调用


	# 构造函数
		
		* 用于初始化对象的函数，不能是 const 的，因为构造函数涉及到了写入
		* 函数名称和类名一样，没有返回值，可以重载，也可以在类的内部进行声明，在外部实现
		* 如果未声明构造函数，会默认生成一个空的
		
			struct Member {
				unsigned long long id;
				string title;
				
				// 重载的构造函数
				Member (unsigned long long id, string title);
				Member(unsigned long long id);
				
				// 默认构造函数，这会要求编译器生成默认的构造函数
				// 等于 Member(){};
				Member() = default;
			};
					
			// 在类外部定义构造函数
			Member::Member(){}
			Member::Member () = default;
		
		* 构造函数初始化值列表

			struct Member {
				long id;
				string title;
				Member(long id, string title): /* 初始值列表 */ id(id), title(title + ":" + title)
				{
					// 初始化代码块
					cout << this -> id << endl;
					cout << this -> title << endl;
				}
			};
			
			* 初始值列表，就是把类成员字段当成函数使，来初始化类成员。
			* 也可以在初始化代码块中对成员进行初始化，没有被初始化的类成员，则使用默认值

	# 使用修饰符控制类成员的访问权限
		
		* public 公开，所有人都可以访问
		* private 私有，仅在类成员可以访问

			struct Member {
				// 公共成员
				public:
					long long id;
					string title;
				// 私有成员
				private:
					double balance;
				// 在公共成员中，访问私有成员
				public:
					double getBalance (){
						return this -> balance;
					}
			};
		
		* 访问说明符的范围直到类结束，或者下一个访问说明符。
		* 类中可以出现初次访问说明符
		* struct 和 class 的区别仅在于默认修饰符的不同

			struct 所有成员默认 public
			class 所有成员默认 private
		
		* 构造函数也可以私有化，从而会导致类实例不能创建
		
	
	# 友元
		* 类外面函数，不能直接访问内部 private 成员。
		* 可以在类的内部，通过 friend 把这些函数声明为友元，那么就可以访问类的 private 成员了
		
			class Member {
				//  声明友元函数的签名
				friend long long getId(Member);
				long long id;
				string title;
			};
			// 定义实现
			long long getId (Member m) {
				// 友元方法中，可以访问到类的私有成员
				return m.id;
			};
			int main(){
				Member m = {};
				cout << getId(m) << endl;
			}   
			
			* 友元可以声明在类中的任意位置
		
		* 可以把其他类的成员函数定义为友元

			class Member {
				//  Foo 函数的成员方法，可以访问私有成员
				friend class Foo;
				long long id;
				string title;
			};

			class Foo {
				public: void foo (Member m){
					// 访问私有变量
					cout << m.id << endl;
				}
			};

			Foo().foo(Member{});
		
		* 友元不具有传递性，仅限于当前类。
		* 也可以仅仅指定类中的某个函数，这个函数必须比当前类先声明

			class Foo {
				// 必须先声明
				public: void bar();
			};

			class Member {
				//  只有 Foo 的 bar 函数可以访问我的私有成员
				friend void Foo::bar();
				long long id;
				string title;
			};

			// 实现
			void Foo::bar (){}
		
		* 友元函数的声明是精准的，不具备重载！
		* 友元声明和作用域。



	# 可以在类的内部定义类型别名

		class Member {
			public:
				// 声明私有的类型 ID
				typedef unsigned long long int64;
				// 使用 using 也可以
				// using int64 = unsigned long long;
			private:
				// 使用类型声明成员变量
				int64 id;
		};
		int main(){
			// 在外部使用类中的别名声明类型，前提是这个类型是 public 的
			Member::int64 id = 123LL;
			cout << id << endl;
		}
		
		* 类型别名也遵从权限修饰符的规则
		* 类型必须先定义再使用

	
	# 内联的类成员函数
		
		* 类中的一些小函数，是可以被自动内联的
		* 也可以在内部/外部主动声明内联

			class Screen {
				public:
					typedef string::size_type pos;
					Screen() = default;
					Screen(pos ht, pos wd, char c): height(ht), width(wd), contents(ht * wd, c){};

					// 小函数，隐式内联
					char get() const {
						return this->contents[this->cursor];
					};
					// 声明函数，在声明处声明内联
					inline char get(pos ht, pos wd) const;
					// 声明函数，在定义处申明内联
					Screen &move(pos r, pos c);
				private:
					pos cursor = 0;
					pos height = 0;
					pos width = 0;
					string contents;
			};

			// 函数定义
			char Screen::get (pos r, pos c) const{
				pos row = r * width;
				return contents[row + c];
			}

			// 函数定义，推荐在这里声明
			inline Screen& Screen::move (pos r, pos c){
				pos row = r * width;
				cursor = row + c;
				return *this;
			}
		
	
	# 可变数据成员 mutable
		
		* 对于一些在 const 函数都可以进行修改的成员，可以使用 mutable 修饰
		* 即使它是 const 对象的成员

			class Member {
				// mutable
				public:
					mutable int count;
					void incr () const {
						++count; // 在 const 函数中修改 count
					}
			};

			int main()
			{
				const Member m = {}; // const 成员
				m.incr();
				// 1
				cout <<  m.count << endl;
			}

	
	# 类成员的初始值
		
		* 可以直接初始化类成员的值

			class Member {
				public:
					vector<string> members {"蔡徐坤"};
					long id = 1024;
					int creator {2048};
					// int val(123); // 类中不能用 () 来初始化
			};
		
		* 类中不能用 () 来初始化成员变量

	# 类的类型
		
		* 即使两个类有完全相同的结构，他们也不能直接比，会编译异常
		* 类实例的声明也可以添加 class/struct 关键字(从 C 继承而来)

			class Foo {};
			struct Bar {};

			class Foo foo;
			class Bar bar;
			struct Foo zoo;
	
		* 类也支持把声明和定义分开来，前向声明
			
			// 声明，前向声明并不知道类定义中会与哪些成员
			class Foo;

			// 定义
			class Foo {
				int id;
				string title;
			};
		
		* 前向声明，只能用作指针、引用，或以不完全类型作为参数或者返回类型的函数。
		
			// OK
			Foo* action (){};
			// return type 'class Foo' is incomplete
			// 错误，只有声明，不能知道 Foo 到底有哪些成员，到底要多大内存
			Foo doSome(){};
		
		* 同理，在类中，只有这个类被定义了才能直接声明它的类型作为成员

			class Bar {
				Foo foo;        // error: field 'foo' has incomplete type 'Foo'
				Foo *fooPtr;	// Ok
			};
		

		

		

					



	
