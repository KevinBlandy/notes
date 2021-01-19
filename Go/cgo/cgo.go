--------------------
cgo
--------------------
	# Go语言通过自带的一个叫CGO的工具来支持C语言函数调用，同时可以用Go语言导出C动态库接口给其它语言使用
		https://golang.org/cmd/cgo/
		https://github.com/golang/go/wiki/cgo
		https://blog.golang.org/cgo
		https://golang.org/misc/cgo/
		https://golang.org/src/runtime/cgocall.go

	# 学习参考
		https://colobu.com/2018/06/13/cgo-articles/

	# 通过 import "C" 语句启用CGO特性，导入即开启
		package main
		import (
			"C" 		// go build命令会在编译和链接阶段启动gcc编译器
			"fmt"
		)
		func main() {
			fmt.Println("Hello")
		}
		
		* 这个程序并没有任何与C有关的代码，但毫无疑问，它的确是一个Cgo程序
	

	
	# import "C"语句
		* 如果在Go代码中出现了import "C"语句则表示使用了CGO特性，紧跟(不能有空格)在这行语句前面的注释是一种特殊语法，里面包含的是正常的C语言代码。
		* 当确保CGO启用的情况下，还可以在当前目录中包含C/C++对应的源文件。
			package main

			/*
			#include <stdio.h>
			void printint(int v) {
				printf("printint: %d\n", v);
			}
			*/
			import "C"

			func main() {
				v := 42
				C.printint(C.int(v))
			}


		* 头文件被include之后里面的所有的C语言元素都会被加入到 "C" 这个虚拟的包中
		* 需要注意的是，import "C" 导入语句需要单独一行，不能与其他包一同import。
	
	# CGO构建程序会自动构建当前目录下的C源文件
		* 要注意C函数名冲突
	
	# 要保证环境变量 CGO_ENABLED 被设置为1
		* 这表示CGO是被启用的状态
		* 在本地构建时CGO_ENABLED默认是启用的，当交叉构建时CGO默认是禁止的

	
	# cgo将当前包引用的C语言符号都放到了虚拟的C包中
		* 尝试在其他包定义一个辅助类型，提供一个方法
			package cgo_helper
			//#include <stdio.h>
			import "C"

			type CChar C.char

			func (p *CChar) GoString() string {
				return C.GoString((*C.char)(p))
			}

			func PrintCString(cs *C.char) {
				C.puts(cs)
			}

		* 在main包调用，会失败
			package main
			/*
			static const char* cs = "hello Cgo";
			*/
			import "C"

			import (
				"go-project/cgo_helper"
			)

			func main() {
				cgo_helper.PrintCString(C.cs)  // undefined reference to `cs'
			}


		* 当前包依赖的其它Go语言包内部可能也通过cgo引入了相似的虚拟C包，不同的Go语言包引入的虚拟的C包之间的类型是不能通用的
		* 在Go语言中方法是依附于类型存在的，不同Go包中引入的虚拟的C包的类型却是不同的（main.C不等cgo_helper.C），这导致从它们延伸出来的Go类型也是不同的类型（*main.C.char不等*cgo_helper.C.char）
		* 换言之，一个包如果在公开的接口中直接使用了*C.char等类似的虚拟C包的类型，其它的Go包是无法直接使用这些类型的，除非这个Go包同时也提供了*C.char类型的构造函数




		
	

