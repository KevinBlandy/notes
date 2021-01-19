-----------------------------
汇编						 |
-----------------------------

	#include<stdio.h>
	#include<stdlib.h>

	int main(void){

	   int a;
	   int b;
	   int c;

	   __asm {
		   mov a,3;         //3的值放在a对应内存的位置
		   mov b,4;         //4的值放在b对应内存的位置
		   mov eax,a;       //把a内存的值放在eax寄存器
		   add eax,b;       //把eax和b相加,结果放在eax
		   mov c,eax;       //把eax的值放在c中
	   }
	   printf("%d\n",c);
	   return EXIT_SUCCESS;
	}
