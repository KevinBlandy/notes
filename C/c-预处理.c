--------------------------------
预处理指令						|
--------------------------------
	#include
		* 包含一个源代码文件

	#define
		* 定义宏
	#undef
		* 取消已定义的宏
	#ifdef
		* 如果宏已经定义，则返回真
	#ifndef
		* 如果宏没有定义，则返回真

	#if
		* 如果给定条件为真，则编译下面代码
	#else
		* #if 的替代方案
	#elif
		* 如果前面的 #if 给定条件不为真，当前条件为真，则编译下面代码
	#endif
		* 结束一个 #if……#else 条件编译块
	#error
		* 当遇到标准错误时，输出错误消息
	#pragma	
		* 使用标准化方法，向编译器发布特殊的命令到编译器中


--------------------------------
#include						|
--------------------------------
	# 头文件的本质其实就是把指定的文件'copy'到当前文件的include处
	# 头文件的导入
		#include<xx.h>
	
	# 系统标准的头文件
		Windows 
			C:\MinGW\include

		Linux
			/usr/include
	
	# #include<xxx.h> 和 #include "xxx.h" 的区别
		* <> 用的是编译器的类库路径里面的头文件
		* 一般是引用自带的一些头文件

		* "" 引用的是程序目录的相对路径中的头文件
		* 一般是用来引用自己写的一些头文件
		* 它会先在你项目的当前目录查找是否有对应头文件,如果没有,它还是会去path(编译)环境中寻找
	
	# 多个文件(#include<xx>)中,函数名称不能重复(静态函数除外,静态函数仅在当前文件有效)

	# 防止头文件重复包含,有两种方式
		1,头文件添加 #pragma once
			* 你希望哪个头文件进制被其他文件重复包含,添加到哪个头文件顶部
			* 顶部添加了该代码的文件,在被重复包含时,也仅仅只有一次生效
		
		2,条件编译(老版本)
			#ifndef _NAME_.H
			#define _NAME_.H
				//函数声明
			#endif

			* 以上是 name.h 这个头文件的条件编译
	
	# 有条件的引用

		* 有时需要从多个不同的头文件中选择一个引用到程序中
		* 需要指定在不同的操作系统上使用的配置参数,可以通过一系列条件来实现这点

			#if SYSTEM_1
			   # include "system_1.h"
			#elif SYSTEM_2
			   # include "system_2.h"
			#elif SYSTEM_3
			   ...
			#endif

		* 但是如果头文件比较多的时候,这么做是很不妥当的,预处理器使用宏来定义头文件的名称
		* 这就是所谓的有条件引用,它不是用头文件的名称作为 #include 的直接参数,只需要使用宏名称代替即可

			 #define SYSTEM_H "system_1.h"
			 ...
			 #include SYSTEM_H

		* SYSTEM_H 会扩展,预处理器会查找 system_1.h,就像 #include 最初编写的那样
		* SYSTEM_H 可通过 -D 选项被 Makefile 定义

--------------------------------
#define							|
--------------------------------
	# 编译时替换常量
		#define name value

		* 在编译程序的时候,程序中的所有 name 都会被替换为 value
		* 这个过程称为编译时替换,在运行程序的时候,所有替换都已经完成
		* 这样定义的常量也被称为明示常量

		* 末尾不需要添加;,而且名称要大写(非强制约束)
--------------------------------
#undef							|
--------------------------------
	# 取消已经定义的宏
		#define MAX 15		
		#undef MAX			//取消已经定义的宏
		#define MAX 25		//重新定义该宏

--------------------------------
#pragma							|
---------------------------------
	# 比较复杂的一个指令,使用标准化方法,向编译器发布特殊的命令到编译器中
	# 保证头文件只被编译一次(用的比较多)
		#pragma once

--------------------------------
#ifdef							|
--------------------------------
	# 如果宏已经定义,则返回真
		#ifdef DEBUG
		   /* Your debugging statements here */
		#endif

		* 这个指令告诉 CPP 如果定义了 DEBUG,则执行处理语句
		* 在编译时,如果向 gcc 编译器传递了 -DDEBUG 开关量,这个指令就非常有用,它定义了 DEBUG,您可以在编译期间随时开启或关闭调试
			gcc -DDEBUG .....
	# demo
		#ifdef DEBUG
			#define MESSAGE "DEBUG"
		#endif

		#ifndef MESSAGE
			#define MESSAGE "NOT DEBUG"
		#endif

		int main(int argc, char **argv) {
			printf("%s",MESSAGE);
			return EXIT_SUCCESS;
		}
--------------------------------
#ifndef							|
--------------------------------	
	# 如果宏没有定义,则返回真
		#ifndef MESSAGE						//如果未定义 MESSAGE 宏
			#define MESSAGE "You wish!"		//则定义
		#endif								//结束

--------------------------------
#if								|
--------------------------------
	# 如果给定条件为真,则编译下面代码

--------------------------------
#else							|
--------------------------------
	# #if 的替代方案

--------------------------------
#elif							|
--------------------------------
	# 如果前面的 #if 给定条件不为真,当前条件为真,则编译下面代码

--------------------------------
#endif							|
--------------------------------
	# 结束一个 #if……#else 条件编译块

--------------------------------
#error							|
--------------------------------
	# 当遇到标准错误时，输出错误消息


--------------------------------
预处理器运算符					|
--------------------------------
	# 宏延续运算符(\)
		* 一个宏通常写在一个单行上,但是如果宏太长,一个单行容纳不下,则使用宏延续运算符(\)
		#define  message_for(a, b)  \
			printf(#a " and " #b ": We love you!\n")
	
	# 字符串常量化运算符(#)
		*  在宏定义中,当需要把一个宏的参数转换为字符串常量时,则使用字符串常量化运算符(#)
		* 在宏中使用的该运算符有一个特定的参数或参数列表

		#include <stdio.h>
		#define  message_for(a, b)  \
			printf(#a " and " #b ": We love you!\n")

		int main(void){
		   message_for(Carole, Debra);		//Carole and Debra: We love you!
		   return 0;
		}

	# 标记粘贴运算符(##)
		* 宏定义内的标记粘贴运算符(##)会合并两个参数
		* 它允许在宏定义中两个独立的标记被合并为一个标记
			#include <stdio.h>

			#define tokenpaster(n) \
				printf ("token" #n " = %d", token##n)	//token##n就是把token和n粘贴到一起了,实际上会被编译为：printf ("token34 = %d", token34);

			int main(void){
			   int token34 = 40;
			   tokenpaster(34);		//token34 = 40
			   return 0;
			}

	# defined() 运算符
		*  预处理器 defined 运算符是用在常量表达式中的,用来确定一个标识符是否已经使用 #define 定义过
		* 如果指定的标识符已定义则值为真(非零),如果指定的标识符未定义,则值为假(零)

		#include <stdio.h>
		#if !defined (MESSAGE)				//如果未定义 MESSAGE 宏
			#define MESSAGE "You wish!"		//则定义
		#endif

		int main(void) {
			printf("Here is the message: %s\n", MESSAGE);
			return 0;
		}

--------------------------------
带参数化的宏						|
--------------------------------
	# 在使用带有参数的宏之前,必须使用 #define 指令定义
	# 参数列表是括在圆括号内,且必须紧跟在宏名称的后边
	# 宏名称和左圆括号之间不允许有空格

	#include <stdio.h>
	#define MAX(x,y) ((x) > (y) ? (x) : (y))

	int main(void) {
		printf("Max between 20 and 10 is %d\n", MAX(10, 20));	//Max between 20 and 10 is 20
		return 0;
	}