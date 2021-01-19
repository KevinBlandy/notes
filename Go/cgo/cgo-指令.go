
--------------------
cgo 指令
--------------------
	# 可以在特殊的注释中，添加一些编译阶段和链接阶段的相关参数，使用 #cgo 指定，
		// #cgo CFLAGS: -DPNG_DEBUG=1
		// #cgo amd64 386 CFLAGS: -DX86=1
		// #cgo LDFLAGS: -lpng
		// #include <png.h>
		import "C"

		* 编译阶段的参数主要用于定义相关宏和指定头文件检索路径
		* 链接阶段的参数主要是指定库文件检索路径和要链接的库文件
	
	#cgo CFLAGS: -DPNG_DEBUG=1 -I./include
		* 用于针对C语言代码设置编译参数

		-D 定义了宏PNG_DEBUG，值为1；
		-I 定义了头文件包含的检索目录。
	
	#cgo LDFLAGS: -L/usr/local/lib -lpng
		* 设置链接时的参数
		* 在链接阶段，C和C++的链接选项是通用的，因此这个时候已经不再有C和C++语言的区别，它们的目标文件的类型是相同的

		-L
			* 指定了链接时库文件检索目录，-l指定了链接时需要链接png库。

		* 因为C/C++遗留的问题，C头文件检索目录可以是相对目录，但是库文件检索目录则需要绝对路径。
		* 在库文件的检索目录中可以通过${SRCDIR}变量表示当前包目录的绝对路径：
			#cgo LDFLAGS: -L${SRCDIR}/libs -lfoo
			展开后 ↓
			#cgo LDFLAGS: -L/go/src/foo/libs -lfoo
	
	#cgo CXXFLAGS
		* C++特有的编译选项

	#cgo FFLAGS
		* C语言特有的编译选项

	#cgo CPPFLAGS
		* C和C++共有的编译选项
	
	# 条件指令

		* 当满足某个操作系统或某个CPU架构类型时后面的编译或链接选项生效

		#cgo [os] CFLAGS: -DX86=1
			* 当在指定的os下，后面的指令生效
			* os支持
				windows
				darwin
				linux
			* 也可以通过!取反
				#cgo !windows LDFLAGS: -lm
				* 非windows环境下，后面的条件生效
		
		* 在不同的系统下cgo对应着不同的c代码
			* 可以先使用#cgo指令定义不同的C语言的宏，然后通过宏来区分不同的
			package main
			/*
			#cgo windows CFLAGS: -DCGO_OS_WINDOWS=1
			#cgo darwin CFLAGS: -DCGO_OS_DARWIN=1
			#cgo linux CFLAGS: -DCGO_OS_LINUX=1
			#if defined(CGO_OS_WINDOWS)
				const char* os = "windows";
			#elif defined(CGO_OS_DARWIN)
				const char* os = "darwin";
			#elif defined(CGO_OS_LINUX)
				const char* os = "linux";
			#else
			#    error(unknown os)
			#endif
			*/
			import "C"

			func main() {
				print(C.GoString(C.os))
			}

--------------------
 build tag 条件编译
--------------------
	