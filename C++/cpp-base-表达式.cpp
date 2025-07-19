---------------------------
变量
---------------------------
	# 变量定义
		* 一次定义名多个，未赋值的为默认值

			// 202
			int x = 1, y, z = ++x;
			

			// 0
			int i, j;
			int z = i = j;
		
		* 定义并初始化的方式有几种

			int y = {1};
			int z{1};
			int v(1);

			
			* {} 是列表初始化方式
			* 注意，使用内置类型的时候，如果列表初始化的值，存在精度丢失的风险，则编译器会报错

				long double val = 3.1415926;

				int x{val}, y = {val};      //  列表初始化时，警告：warning: narrowing conversion of 'val' from 'long double' to 'int' 
				std::cout << x << y << std::endl;// 结果出现了精度丢失 3 3

				int k(val), v = val;        // 其他方式初始化时，没有警告  
				std::cout << k << v << std::endl;// 结果出现了精度丢失 3 3

		
		* 内置类型的全局变量未初始化，其默认值为 0。局部变量未初始化，其值是未定义，如果尝试拷贝或者是其他形式访问可能会导致异常（编译器行为可能不一致）

			// 全局变量为初始化，默认值为 0
			int global_var;

			int main() {

				std::cout << global_var << std::endl; // 0

				// 局部变量未初始化，值是垃圾数据（可能是任意值）
				int local_var;  // note: 'local_var' was declared here 

				// 危险！可能输出随机值或导致崩溃
				std::cout << local_var << std::endl; //0 warning: 'local_var' is used uninitialized [-Wuninitialized]  
				return 0;
			}
	
	# 变量声名
		
		* CPP 支持分离式编译，也就是说可以把程序分割为若干个文件，每个文件可以被独立的编译。
		* 为了支持分离式编译，CPP 支持把变量的 "定义" 和 "声名" 分开来。

		* 通过 extern 关键字告诉编译器：“这个变量/函数在别处定义，当前文件只是引用它，不要在此分配存储空间。但是，当我编译的时，需要候确保某个源文件中有这个声名的定义。”
	
			// 声名：在 foo.h 中声名了一个 int 类型的 foo 变量
			extern int foo;


			// 定义：导入 foo.h，其中包含了一个 int foo 声名
			#include "foo.h"

			// 在 bar.cpp 中定义了变量 foo类似于实现
			int foo = 128;
		
			// 在 main.cpp 中导入 bar.cpp，并且访问它的定义
			#include "bar.cpp"
			int main() {
				std::cout << foo << std::endl;
				return 0;
			}
		
		* 如果在编译的时候，不能找到声名变量的定义，则会编译失败
			 undefined reference to xxx
		
		* 如果直接初始化了一个声名，那么就成了定义了。并且编译器会给警告

			extern int foo = 21;  // : warning: 'foo' initialized and declared 'extern'

			int main() {
				std::cout << foo << std::endl;  //21
				return 0;
			}
		
		* 变量可以多次声名，包括在全局或局部

			int foo = 123;

			extern int foo;		// 全局多次声名
			extern int foo;

			int main() {
				extern int foo; // 局部声名
				extern int foo;

				std::cout << foo << std::endl;  //21
				return 0;
			}
		
		* 局部的变量声名不能被初始化，否则异常
	
			int main() {
				extern int foo = 21;			// error: 'foo' has both 'extern' and initializer
				std::cout << foo << std::endl;  //  error: 'foo' was not declared in this scope
				return 0;
			}


		
		* 最佳实践
			* 一般，把 extern 声明放在头文件中，定义放在一个源文件中，避免重复定义。
			* 在单个源文件中定义全局变量。
	
---------------------------
常量
---------------------------	
	# const 常量
		
		* 使用 const 修饰符声名常量，常量只读，修改会异常

			const double pi = 3.1415926;
		
		* 所以 const 变量的值在声明时就必须要初始化，初始值可以是字面量或者是表达式

			const double pi; //error: uninitialized 'const pi' [-fpermissive]
		
		* 默认情况下， const 对象仅在当前文件中有效
			
			* const 常量的本质是，在预编译阶段会被全局替换为对应的常量值。当多个文件中出现同名 const 的时候，等同于在不同文件中定义了相同的变量。
			* 如果想在多个文件之间共享 const 常量，则必须添加 extern 关键字。

				// file_1.h
				extern const int bufSize;		// 声名常量
				// file_1.cpp
				extern const int bufSize = 512; // 声名并初始化常量
	
	# 常量表达式
		* 用 constexpr 关键字来声名常量表达式，即在编译过程就能计算得到结果的表达式

			constexpr int size = 512;					
			constexpr int doubleSize = size * 2;		
			constexpr int otherSize = getSize(); // 只有 getSize 是一个 constexpr 函数时（编译时就可以计算出值），才是一条正确的声名

			* 指针和引用都可以定义为常量表达式，单只指针的初始化值必须是 nullptr 或 0，或是某个 '固定地址' 中的对象
		
		*常见的误区

			// p 本质上是 *const 指针，可以修改其地址的值，但是不能修改其地址
		    constexpr int *p = nullptr;
			int i = 100;
			p = &i;					// error: assignment of read-only variable 'p'
		   
			
			// 局部变量，不是一个常量表达式，因为其地址不固定
			const int i = 100;
			constexpr int *p = &i;  //  error: '& i' is not a constant expression
			
			// constexpr 本质上是 *const 指针，当引用 const 变量的时候需要添加 const
			const int i = 100;
			int main() {
				constexpr int *p = &i;  //  error: invalid conversion from 'const int*' to 'int*' [-fpermissive]
				// const constexpr int *p = &i; // ok
				return 0;
			}
	
	# 顶层和底层
	
		* 顶层 const: 指针本身是不是一个常量
		* 底层 const: 指针指向的值是不是一个常量

---------------------------
别名
---------------------------	
	# typedef 别名，给一种类型取一个好记的名字

		* 使用 typedef 定义类型的别名

			// int64 就是 long long 的别名
			typedef long long int64;

			// bigInt 就是 int64 的别名
			// *intP 就是 intP 就是 *int64
			typedef int64 bigInt, *intP;
			
			int64 val = 10000LL;

			bigInt bVal = val;

			// 使用 &bigInt 也可以，
			intP p = &bVal;

			std::cout << *p << "\t" << bVal << std::endl; // 10000   10000

	# 也可以使用 using 定义别名

		using int64 = long long;            // long long
		using int64P = int64*;              // long long*
		using constInt64P = const int64P; // const long long*

		constInt64P p = nullptr;            // const long long *p = nulltr;
		std::cout << p << std::endl;        // 0

		int64 val = 100000LL;
		int64P valP = &val;

		std::cout << *valP << "\t" << val << std::endl; // 100000   100000
	
	# 原理
		* 本质上也是编译前把别名替换为指定的类型
		* 理解了这一点后，就可以理解能用哪些修饰符、关键字，替换的时候会一并替换过去


---------------------------
auto
---------------------------	
	# CPP 11 引入的新特性
		
		* 让编译器去推断表达式的类型， auto 定义的变量必须要有初始值

			auto val = 1 + 1;
		
		* 使用 auto 也可以在一行中声名多个变量，要求变量的类型必须一致
			
			auto i = 1, *p = &i; //i = int, *p = int*
			// z 的类型是 double，和之前的 y 不一致
			auto y = 1, z = 3.14; //  error: inconsistent deduction for 'auto': 'int' and then 'double'
	
	# 编译器推断出来的类型可能和初始值类型不一致，编译器会进行适当的修改

		* 引用类型

			auto i = 0xFF, &iRef =i;
			auto a = iRef; // a 的类型是 int

			std::cout << a << std::endl; // 255
		
		* auto 会忽略掉顶层的 const, 但是会保留底层 const
		
			// 变量
			int i = 0xFF;
			
			// 常量
			const int ci = i, &cr = ci;

			auto b = ci;        // int b

			// cr 是 ci 的别名，ci 是一个顶层 const
			auto c = cr;        // int c

			auto d = &i;        // int *d

			// 对常量取地址是一种底层 cost
			auto e = &ci;       // const int *e
		
			// 可以明确指定 const
			const auto f = ci;  // const int f
		
		
		* 切记：&、* 只属于某个声名符，而非基本类型的一部分。

---------------------------
decltype
---------------------------	
	# decltype 用户返回操作数的数据类型
		
		// decltype 返回的类型就是 fn 函数返回值的类型
		decltype(fn()) number = x;
	
		* 编译器并不会调用 fn 函数，而是假设 fn 被调用后会返回什么类型
	
	# decltype 处理顶层 const 和引用的方式和 auto 有些许不同
		
		* 如果 decltype 中是变量，则 decltype 返回该变量的类型（包括顶层 const 和引用在内）

			const int ci = 0, &cj = ci;

			decltype(ci) x = 0;     // const int x = 0
			decltype(cj) y = x;     // const int &y

			// 异常：z 是引用，必须要初始化。
			decltype(cj) z;         //  error: 'z' declared as reference but not initialized
		

		* 如果 decltype 中的是表达式，则返回该表达式结果对应的类型


			int i = 42, *p = &i, &r = i;

			decltype(r + 0) b; // int b

			* 如果表达式是解引用操作，则返回得到的引用类型

				// 错误：c 是 int& c，必须初始化
				decltype(*p) c; // error: 'c' declared as reference but not initialized
			
			* 表达式是否使用 () 括号，得到的类型可能不同

				* 如果给变量加上了一层或多层括号，编译器就会把它当成是一个表达式。变量是一种可以作为赋值语句左值的特殊表达式，所以这样的 decltype 就会得到引用类型。

				int i = 42;

				// 错误，b 是 int&
				decltype((i)) b; // error: 'b' declared as reference but not initialized
				decltype(i) c;      // int c


---------------------------
using
---------------------------	
	# 使用 using 声名命名空间中的成员

		#include <iostream>
		#include <string>


		// 全局声名
		using std::string;

		int main() {
			{
				// 局部声名
				using std::cout;
				using std::endl;

				string x = "HelloWorld";

				cout << x << endl;
			}

			string v = "outer string";

			// 在外部，没法访问内部的 using 声名，因此要带上命名空间
			std::cout << v << std::endl;;

			return 0;
		}

		
		* 不建议在 header 文件中使用 using 声名
	
---------------------------
作用域
---------------------------
	# 全局
		* 函数以外的变量、函数是全局作用域，可以被全局访问
	
	# 局部

		* {} 块中的变量，只能在当前 {} 内部访问
			{
				int bar = 123;
				 std::cout << bar << std::endl; // Ok
			}

			std::cout << bar << std::endl; //  error: 'bar' was not declared in this scope
	
	# 作用域可以嵌套和覆盖
		
		// 全局foo
		int foo = 123;

		int main() {

			// 局部的 foo，覆盖了全局的
			int foo = 21;
			std::cout << foo << std::endl;  //21

			// 通过全局作用域运算符 ::访问全局变量
			std::cout << ::foo << std::endl; // 123
			return 0;
		}

		* 一般在函数内部不建议定义和全局变量冲突的变量
	

