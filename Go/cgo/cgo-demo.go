----------------------------
直接在Go中写C，调用
----------------------------
package main

/*
#include <stdio.h>
void hello(char* s){ // 声明函数
	puts(s);
}
*/
import "C"

func main() {
	// 直接调用函数
	C.hello(C.CString("Hello Cgo"))
}

----------------------------
通过c接口，调用任意实现
----------------------------
	# Go 调用方
		package main

		/*
		#include "hello.h"  // 导入声明好的接口
		*/
		import "C"

		func main() {
			// 调用接口中的函数
			C.hello(C.CString("Hello Cgo"))
		}
	
	# hello.h 负责定义接口
		// hello.h
		void hello(const char* s);  // 声明就行
	
	# hello.c 作为C的实现
		// hello.c
		#include <stdio.h>
		#include "hello.h"		// 导入接口

		// 使用C语言实现接口中的方法
		void hello(const char* s){
			puts(s);
		}
	
	# hello.cpp 作为c++的实现
		#include <iostream>
		extern "C" {
			#include "hello.h"		// 导入C接口
		}

		// 使用c++ 实现接口中的方法
		void hello(const char* s) {
			std::cout << s;
		}
	
	# hello.go 作为go的实现
		package main

		import "C"
		import "fmt"

		// 使用 export 导出为c函数
		//export hello
		func hello(s *C.char){
			fmt.Println(C.GoString(s))
		}
		
		* export 必须紧跟//之后，不能有空格，并且独占一行，后面不能有任何其他内容
	
	# 多个实现，只能存在一个

----------------------------
面向C接口的Go编程
----------------------------
	package main
	/*
	void hello(_GoString_ s); // 声明函数
	*/
	import "C"

	import "fmt"

	func main() {
		// 调用函数
		C.hello("Hi Cgo")
	}

	// 实现并且导出函数
	//export hello
	func hello(s string){
		fmt.Println(s)
	}

	* _GoString_预定义的C语言类型，用来表示Go语言字符串