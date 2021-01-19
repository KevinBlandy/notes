----------------------------------------
函数									|
----------------------------------------
	# 天下函数都差不多
		void func(int var){}
	
	# 函数声明
		* 函数声明会告诉编译器函数名称及如何调用函数,函数的实际主体可以单独定义
			return_type function_name( parameter list );
		* demo

			int max(int num1, int num2){} => int max(int num1, int num2);

			//函数声明中,参数的名称并不重要,只有参数的类型是必需的,因此下面也是有效的声明
			int max(int, int);
		
		* 函数声明的作用就是告诉编译器,这个函数我是有定义的,但是放在了别的地方
		* 一个源文件中定义函数且在另一个文件中调用函数时,函数声明是必需的,在这种情况下,应该在调用函数的文件顶部声明函数
		* 在main函数中调用其他的函数,只会往前面找已经定义/声明的函数,如果没找到的话,C编译会给警告,C++会给错误
		* 一个函数,可以重复的声明多次
		* 声明中的形参变量名不仅仅可以省略,还可以不一样
			
	
	# 函数返回局部变量的指针
		* 返回值是指针类型的函数
		* 不能返回局部变量的地址,因函数执行完毕后,局部遍历内存释放,指针指向的内存地址被回收,此时返回的指针,就成了野指针
			int *foo(){
				int  x = 4;
				return &x;		//function returns address of local variable
			}
			* 在一些环境中(Linux测试ok),就算你返回了局部变量的指针,系统也会给你返回该指针的变量值
		
		*  可以使用全局变量,因为全局变量是在程序结束后才释放内存
			
			
		
		
		
----------------------------------------
函数参数								|
----------------------------------------
	# 调用函数时,有两种向函数传递参数的方式
		* 传值调用
			- 该方法把参数的实际值复制给函数的形式参数,在这种情况下,修改函数内的形式参数不会影响实际参数
			- 默认情况下,C 使用传值调用来传递参数,一般来说,这意味着函数内的代码不能改变用于调用函数的实际参数

		* 引用调用
			- 通过指针传递方式,形参为指向实参地址的指针,当对形参的指向操作时,就相当于对实参本身进行的操作
	
----------------------------------------
可变参数								|
----------------------------------------
	# 需要使用 stdarg.h 头文件,该文件提供了实现可变参数功能的函数和宏
		* 定义一个函数,最后一个参数为省略号,省略号前面可以设置自定义参数
		* 在函数定义中创建一个 va_list 类型变量,该类型是在 stdarg.h 头文件中定义的
		* 使用 int 参数和 va_start 宏来初始化 va_list 变量为一个参数列表。宏 va_start 是在 stdarg.h 头文件中定义的。
		* 使用 va_arg 宏和 va_list 变量来访问参数列表中的每个项。
		* 使用宏 va_end 来清理赋予 va_list 变量的内存。
	
	# 几个方法
		va_list			声明一个变量
		va_copy()		复制一个副本出来
		va_start()		初始化变量,该参数的第二个参数表示返回的数据类型(也就是可变参数的类型,可变参数允许类型不同)
		va_arg();		开始获取变量
		va_end()		释放保存数据的内存

	# Demo
		void foo(char *p, int num, ...) {
			//定义变长变量本质是一个集合
			va_list valist;

			//初始化该值,num参数表示变长参数的数量
			va_start(valist, num);

			printf("%s:", p);

			for (int x = 0; x < num; x++) {
				//每次调用该方法都会获取下一个变长变量
				int value = va_arg(valist, int);
				printf("[%d]", value);
			}

			//清空集合保存的内存
			va_end(valist);
		}

		int main(int argc, char **argv) {
			foo("Hello",4,1,2,3,4);		//Hello:[1][2][3][4]
		}

----------------------------------------
函数的常量								|
----------------------------------------
	
	# 函数的名称和行数
		* 函数的名称:__FUNCTION__
		* 当前的行号:__LINE__

		#include <stdlib.h>
		#include <stdio.h>

		void function(){
			printf("%s-%d",__FUNCTION__,__LINE__);		//function-5
		}


----------------------------------------
main函数的参数							|
----------------------------------------
	# main函数的参数定义
		int main(int argc,char *argv[]) {
			return EXIT_SUCCESS;
		}
		int main(int argc,char **argv) {
			return EXIT_SUCCESS;
		}
		
		* argc是第二个数组的元素个数
		* char *rgv[],表示参数是一个数组,数组的每个元素都是 char 的指针
		* **argv,表示参数是一个2级指针
	
	# 获取main函数的参数
		int main(int argc,char **argv) {
			printf("%d\n",argc);
			for(int x = 0 ;x < argc; x++){
				//*(argv + x)
				printf("%s\n",argv[x]);
			}
			return EXIT_SUCCESS;
		}
		
		* '第一个参数永远都是当前指向文件的名称,也就是说main函数无论如何都有一个参数'
		* 参数的传递用","号分割
			demo.exe 1 2 3
	
	# 使用双引号把多个单词合并为一个字符串
		//Demo.exe "Hello KevinBlandy" C
		int main(int argc, char **argv) {
			puts(argv[1]);		//Hello KevinBlandy
			puts(argv[2]);		//C
			return EXIT_SUCCESS;
		}

