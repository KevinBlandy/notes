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
		
		* 内部和外部方法可以同时存在，但是不能冲突。
		* 返回 this 实现链式调用
	
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
			
	# 构造函数
		
		* 用于初始化对象的函数，不能是 const 的，因为构造函数涉及到了写入
		* 函数名称和类名一样，没有返回值，可以重载
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


		
	

