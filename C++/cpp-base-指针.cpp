------------------------------
指针
------------------------------
	# 指针，Pointer，是一种指向另一个数据的一种数据类型

		* 指针使用 *p 定义，p 是变量名称，使用 &v 取变量地址， v 是变量。

			//定义变量
			int x = 1024;

			// 定义 int 类型的指针，初始化其值为 x 的地址
			int *p = &x;

			// 读取指针指向的地址的值
			std::cout << *p << std::endl; // 1024

			// 读取指针中存放的地址值，地址值随机的
			std::cout << p << std::endl;  // 0xb976fffe14
		
		* 把 * 写在类型后也是合法的，但是不推荐，因为 * 本身是属于变量名的，而不是类型的
			
			int* p = NULL; // 合法，不推荐

			int* p1, p2; // 同时声名指针和值类型时容易混淆：p1 是指针，p2 是 int
		
		* 指针类型和要指向的对象的类型必须严格一致
		* 编译器不能检查无效指针，视图操作无效指针可能会导致错误
		* 引用不是对象，不可寻址，所以不能定义指向引用的指针

		* 指针只能存放地址值或 0（空指针），不能直接存放值

			int x = 0;

			int *p0 = &x;   // 正常，这是存放 x 的地址
			int *p1 = 0;    // 正常，这是设置为空指针
			int *p2 = x;    // 异常： error: invalid conversion from 'int' to 'int*' [-fpermissive]


	# 空指针

		* 没有指向任何对象的指针，其值是 nullptr, 推荐使用它来定义空指针

			int *p = nullptr;
			std::cout << p; // 0

			p = 0;  // 也可以通过设置其值为 0 来设置空指针
		
		* 也可以使用 <cstdlib> 中的 NULL 或者是 0 来设置空指针

			#include <cstdlib>

			int *p1 = NULL;			// 需要导入 cstdlib
			int *p2 = nullptr;
			int *p3 = 0;

			std::cout << (p1 == p2); // 1
			std::cout << (p3 == p2); // 1
	
		* 永远不要解引用空指针，这是未定义行为（UB），在实际运行中通常会导致程序崩溃（段错误）。
	
	# 指针的判断
		
		* 非空指针在条件中都是 true
		* 相同类型的指针，如果指向的同一个变量，则相等

			int x = 0;

			int *p1 = &x;
			int *p2 = &x;

			// 1
			std::cout << (p1 == p2) << std::endl;
	
		* 不同类型的指针不能直接比较

			int *p1 = &x;
			long *p2 = nullptr;

			// error: comparison between distinct pointer types 'int*' and 'long int*' lacks a cast
			if (p1 == p2){

			}
		

	# void* 指针
		
		* 使用 void 关键字定义可以存放任意类型变量地址的指针

			int x = 0;
			double y = 3.14;
			long z = 1024L;

			void *p = NULL;
			p = &x;     std::cout << p << std::endl;    // 0xfd2ffffb84
			p = &y;     std::cout << p << std::endl;    // 0xfd2ffffb78
			p = &z;     std::cout << p << std::endl;    // 0xfd2ffffb74
		
		* void 指针能做的事情有限，只能用于和别的指针比较、作为函数的输入输出，或者是赋值给另外的 void 指针
		* 不能直接操作 void 指针指向的对象，因为编译器不知道你到底指向了个什么玩意儿
	
	
	# 多级指针
		
		* 指针修饰符中的 * 没有限制，多个修饰符叠加的时候，按照其逻辑关系理解即可，指指针针无穷尽也。
			
		   int x = 0xFF;

		   // 指向 x 的指针
		   int *p1 = &x;
		   std::cout <<  p1 << " = " << *p1 << std::endl;   // 0xc3e7ffbe4 = 255

		   // 指向 x 指针的指针
		   int **p2 = &p1;
		   std::cout <<  p2 << " = " << *p2 << std::endl;   // 0xc3e7ffbd8 = 0xc3e7ffbe4

		   // 指向 x 指针的指针的指针
		   int ***p3 = &p2;
		   std::cout <<  p3 << " = " << *p3 << std::endl;   // 0xc3e7ffbd0 = 0xc3e7ffbd8
		   
		   std::cout << ***p3 << std::endl; // 255


	
	# const 指针
		
		* 指针可以指向 const 常量，此时指针必须也是 const 的，且不能修改其引用的值

			// 常量
			const int val = 0xFF;
			// const 指针
			const int *p = &val;

			std::cout << *p << std::endl;       // 255

			// const 指针不能修改其指向的值： error: assignment of read-only location '* p'
			*p = 1024;

			// 非 const 指针，不能指向 const 变量
			int *p1 = &val;  // error: invalid conversion from 'const int*' to 'int*' [-fpermissive]
	
		
		* const 指针可以指向变量，并且指向的地址值是可以修改的，

			// 变量
			int val = 0xFF;
			// const 指针
			const int *p = &val;

			std::cout << *p << std::endl;       // 255

			// 另一个变量或常量
			int otherVal = 1024;

			// 修改 p 的指针，指向另一个地址
			p = &otherVal;  
			std::cout << *p << std::endl;       // 1024
		

		* const 指针，其目的只是为了保证无法通过这个指针修改指定地址的值。至于地址是什么，无所谓，可以修改。


	
	# *const 指针

		* *const 指针更合乎对 const 的理解，不能修改其指向的地址

			// 变量
			int val = 0xFF;

			// *const 指针，指向 val 的地址
			int *const p = &val;
			std::cout << p << " = " << *p << std::endl;		// 0xa6ff5ffc74 = 255

			// 修改指针对应地址的值，OK
			*p = 1024;
			std::cout << p << " = " << *p << std::endl;		// 0xa6ff5ffc74 = 1024

			// 另一个变量
			int otherVal = 1024;
			// 修改 *const 指针指向的地址，异常
			p = &otherVal;      //  error: assignment of read-only variable 'p'


			* *const 指针其指向必须在定义时就初始化
			* 指针指向的地址不能变，但是指向地址的值可以修改


		* *const 指针指向 const 常量，就得用俩 const
		
			// 常量
			const int val = 0xFF;
			// 指针
			const int *const p = &val;

			* 表示指向的地址不变，并且不能通过指针修改其值。
		
	# constexpr 指针
		
		* constexpr 仅对指针有效，与指针所指的对象无关，也就是说和 *const 一样，不能修改指向，但是可以修改其指向的值
	
			 // 值
			int initVal = 1024;


			int main() {
			   
				// 初始常量值表达式： fooP 指针指向一个常量地址
				constexpr int *fooP = &initVal;

				// 尝试修改其指向地址的值，没问题
				*fooP = 512; 

				std::cout << *fooP << "\t" << initVal << std::endl; // 512     512

				int newVal = 0xFF;

				// 尝试修改其指向，异常
				fooP = &newVal; //  error: assignment of read-only variable 'fooP'

				return 0;
			}
		
	# 指针和数组

		* 存储指针的数组

			// 原数组
			int idList[] = {1, 2, 3, 4};

			// unsigned long long size
			// 计算出数组的长度
			auto size = sizeof(idList) / sizeof(idList[0]);

			// 存储 int 指针的数组
			int *intPtrs[4] = {};

			// 迭代原数组
			for (unsigned long i = 0; i != size; i ++){
				// 存储每个元素的指针
				intPtrs[i] = &idList[i];
			}

			// 迭代指针数组 [1, 2, 3, 4]
			for (auto c : intPtrs){
				cout << *c << endl;
				*c *= *c;       
			}

			cout << "===========" << endl;

			// 迭代指针数组 [1, 4, 9, 16]
			for (auto c : idList){
				cout << c << endl;
			}
			
		* 指向指针的数组
	
			// 原数组
			int idList[] = {1, 2, 3, 4};

			// 声名了一个指针 idListPtr，指向一个长度为 4 的 int 数组
			int (*idListPtr)[4] = &idList;

			// [1, 2, 3, 4]
			for (auto i : *idListPtr){
				cout << i << endl;
			}
		
