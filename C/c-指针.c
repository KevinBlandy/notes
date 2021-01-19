--------------------------------
指针							|
--------------------------------
	# 指针是一个变量,其值为另一个变量的地址
	# 指针变量的声明
		[合法的C语言类型] *变量名称

		int    *ip;    /* 一个整型的指针 */
		double *dp;    /* 一个 double 型的指针 */
		float  *fp;    /* 一个浮点型的指针 */
		char   *ch;     /* 一个字符型的指针 */

	# 所有指针的值的实际数据类型,不管是整型,浮点型,字符型,还是其他的数据类型,都是一样的,都是一个代表内存地址的长的十六进制数
		* 不同数据类型的指针之间唯一的不同是,指针所指向的变量或常量的数据类型不同
		* 32位编译器,用32位大小保存地址(4字节)
		* 64位编译器,用64位大小保存地址(8字节)

		printf("%d",sizeof(int *));		//4


	# 指针的简单使用
			int number = 10;
			int *pointer = &number;
			printf("number的内存地址是:%p\n",pointer);
			printf("通过指针访问值是:%d\n",*pointer);
	
	# 野指针
		* 值栈保存了一个毫无意义的地址(非法的地址)
			int *p;
			p = 0x00000FFF;
			printf("%p",p);     //00000FFF

		* 只有定义了变量后,此变量的地址才是合法地址
		* 操作野指针本身不会有任何问题
		* 操作野指针指向的内存,这种直接操作系统未授权的内存,是非法的,就会有问题

	#  NULL 指针
		* 赋为 NULL 值的指针被称为空指针
		* 它可以尽量的避免野指针
		*  NULL 指针是一个定义在标准库中的值为零的常量: #define NULL((void *)0)
			int  *pointer = NULL;
			printf("pointer 的地址是 %p\n", pointer);		//00000000
		
		* 在大多数的操作系统上,程序不允许访问地址为 0 的内存,因为该内存是操作系统保留的
		* 然而,内存地址 0 有特别重要的意义,它表明该指针不指向一个可访问的内存位置,但按照惯例,如果指针包含空值(零值),则假定它不指向任何东西
			if(pointer)     /* 如果 p 非空,则完成 */
			if(!pointer)    /* 如果 p 为空,则完成 */

	# 还可以通过[]操作指针
		* 下标只能是 0,越界的话会出现不可预料的错误
			int x = 10;
			int *p = &x;

			//p[0] 等同于 *p 等同于 *p(p + 0)
			printf("%d\n",p[0]);    //10
		
			//p[0] 操作的是指针p指向的内存
			p[0] = 250;
			printf("%d\n",*p);      //250
		
		* 如果是 p[1],其实就是等于 *(p + 1),属于指针运算了
	
	# 万能指针
		* 也就是 void 指针
		* void *p; 可以指向任何类型的变量
		* 在使用指针指向的内存时,最好强制转换为其当前的指针类型
		* 因为 void 指针指向的数据类型不确定,在使用时,通过强制转换来确定最终的数据类型从而确定内存大小
			void *p = NULL;
			int x = 10;
			p = &x;

			//强制转换后读取内存
			printf("%d\n",*((int *)p));     //10

			//强制转换后写入内存
			* ((int *)p) = 20;
			printf("%d\n",x);               //20       
		* 按照 ANSI(American National Standards Institute) 标准,不能对 void 指针进行算法操作( + , -)
	
	# 指针步长
		* 其实就是指针的运算,可以通过运算符( +,- )来操作指针
		* 指针在执行运算的步长,由指针指向的数据类型决定
			int x = 0; 
			int *p = &x;
			printf("p = %p\n",p);       //p = 0061FF04

			p = p + 1;		//+1操作,当前类型是int,所以内存地址会 +4
			printf("p = %p\n",p);       //p = 0061FF08
			
		* 通过指针来排序数组
			void sort(int *p,int len){
				for(int x = 0 ;x < len ; x++){
					for(int y = x ; y < len ; y++){
						int *_x = p + x;
						int *_y = p + y;
						if(*_x < *_y){
							*_x = *_x ^ *_y;
							*_y = *_x ^ *_y;
							*_x = *_x ^ *_y;
						}
					}
				}
			}

	# const 修饰的指针
		* const 修饰 * ,只能对指向的内存进行读操作(const 在*号前面)
			int x = 0;
			const int *p;
			p = &x;
			*p = 100;	// :assignment of read-only location

		* const 修饰指针变量,代表指针变量值为制度(const 在*号后面)
			int x = 0;
			int * const p = &x;
			p = 0xFFFF;		//assignment of read-only variable
		
		* const 修饰指针变量 以及 *,啥都改不了(俩 const 在 * 前后)
			int x = 0;
			int const * const p = &x;
			p = 0xFFFF;		//assignment of read-only variable
			*p = 15;		//assignment of read-only variable
	
	# 只要是合法的内存地址,都可以使用强制转换
		int x = 1;
		printf("p = %p\n",&x);		//获取变量x的地址:0061FF2C
		* ((int *)0x0061FF2C) = 9;	//强制把地址数据0061FF2C,转换为地址,并且操作该地址的内存
		printf("x = %d\n",x);
	
	# 字符串指针
		//打印数组
		char buf[] = "Hello Java";
		printf("%s %p %p %c\n",buf,buf,&buf[0],*buf);	//Hello Java 0028FF2D 0028FF2D H

		//以指针的方式打印
		int i = 0;
		while(buf[i] != '\0'){
			putchar(buf[i]);			//Hello Java
			i ++;
		}

		printf("\n");

		//以指针方式打印2
		int x = 0;
		while(*(buf + x) != '\0'){
			putchar(*(buf + x));		//Hello Java
			x++;
		}

	
--------------------------------
多级指针						|
--------------------------------
	# 指向指针的指针
		int x = 15;
		int *p1 = &x;			//p1 -> x
		int **p2 = &p1;			//p2 -> p1
		int ***p3 = &p2;		//p3 -> p2
		printf("x=%d",***p3);		//15
	
	# 多级指针与指针数组
		int *arr[10];
		int **p = arr;
		
		- int *arr[10],表示是一个 int 指针的数组
		- 首元素其实就是个二级指针 ,首元素本身是个指针,指向数组第一个元素,而数组的第一个元素也是指针,指向了 int 变量

	# 多级指针在函数中的使用
		//**p 表示参数是一个2级指针，因为数组传递进来是一个指针，而第一个元素也是指针，所以就成了指针指向的指针
		void func(char **p,int len){
			for(int x = 0 ;x < len ;x++){
				//p[x] 返回的也是一个指针
				printf("%s\n",p[x]);
			}
		}

		//另一种声明方式，表 *p 是一个指针数组。其实本质上也是二级指针
		void func(char *p[],int len){
			for(int x = 0 ;x < len ;x++){
				//p[x] 返回的也是一个指针
				printf("%s\n",p[x]);
			}
		}

		int main(int argc,char *argv[]) {
			//p是一个指针,p里面的元素也是一个指针，也就是2级指针
			char *p[] = {"a","b","c"};
			func(p,3);
			return EXIT_SUCCESS;
		}


	
--------------------------------
指针数组						|
--------------------------------
	# 专门保存指针的数组
		int arr[5] = {1,2,3,4,5};
		int *ps[5];
		for(int x = 0 ; x < 5;x++ ){
			ps[x] = &arr[x];
		}
		for(int x = 0 ; x < 5;x++ ){
			printf("%d",*ps[x]);		//12345
		}


--------------------------------
指针函数						|
--------------------------------
	# 指向函数的指针
		#include <stdlib.h>
		#include <stdio.h>
		
		//定义函数
		int max(int a,int b){
			return a > b ? a : b;
		}

		int main(void){

			//m 就是函数 max 的指针
			int (* m)(int,int) = &max;

			int a,b,c;

			scanf("%d %d %d",&a,&b,&c);

			//等同于 max(max(a,b),c)
			int r = m(m(a,b),c);

			printf("%d\n",r);
		}
		
		* 定义方法
			[返回值类型] (* 指针变量)(形参类型)
			int (* pointer)(void)
			int (* pointer)(int,int)
			void (* pointer)(void)

	
	# 回调函数 
		* 函数指针作为某个函数的参数
		* 函数指针变量可以作为某个函数的参数来使用的,回调函数就是一个通过函数指针调用的函数
	
			#include <stdio.h>
			#include <stdlib.h>

			void foo(int value,int (*printf)(const char *, ...)){
				printf("%d\n",value);
			}

			int main(int argc, char **argv) {
				foo(15,&printf);		//15
				return EXIT_SUCCESS;
			}
		