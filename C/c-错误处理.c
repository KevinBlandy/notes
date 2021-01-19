------------------------
错误处理				|
------------------------
	# C 语言不提供对错误处理的直接支持,但是作为一种系统编程语言,它以返回值的形式允许您访问底层数据
	# 在发生错误时,大多数的 C 或 UNIX 函数调用返回 1 或 NULL,同时'会设置一个错误代码 errno,该错误代码是全局变量,表示在函数调用期间发生了错误'
	# 可以在 errno.h 头文件中找到各种各样的错误代码
	# 所以,C 程序员可以通过检查返回值,然后根据返回值决定采取哪种适当的动作
	# 开发人员应该在程序初始化时,把 errno 设置为 0,这是一种良好的编程习惯,0 值表示程序中没有错误

		#include <stdio.h>
		#include <errno.h>
		#include <string.h>

		//声明全局变量
		extern int errno;

		int main() {
			FILE * file;
			int errnum;
			file = fopen("unexist.txt", "rb");
			if (file == NULL) {

				errnum = errno;
				fprintf(stderr, "错误号: %d\n", errno);
				perror("通过 perror 输出错误");
				fprintf(stderr, "打开文件错误: %s\n", strerror(errnum));
			} else {
				fclose(file);
			}
			return 0;
		}


	
	# 根据异常代码获取其文字描述
		char *strerror (int)
		* <string.h>
		* 根据int(异常码)返回其文字描述
	
	
	
	# 打印错误信息
		void perror (const char *);
			* 标注的错误打印流,会在参数字符串后面添异常/正常的提示
				perror("hi");	//hi: No error

			* 它可以打印'库函数'调用失败的原因
				fclose(stdout);
				printf("Hello");
				perror("hi");	//hi: Bad file descriptor(坏的文件描述符,因为已经关闭了)
	
