---------------------------------
Cpp
---------------------------------
	# 为了考试学 C++（正经人谁用 C++ 啊？）
	
	# 书
		C++ Primer 中文版
		


---------------------------------
MinW
---------------------------------
	# 官网
		https://www.mingw-w64.org/
		

	# 下载地址
		https://www.mingw-w64.org/downloads/
			* 构建好的 https://github.com/niXman/mingw-builds-binaries/releases

		https://sourceforge.net/projects/mingw-w64/files/mingw-w64/mingw-w64-release/
	
	# Exe 在线安装
	
		# 打开下载地址，拉到下面，下载: MinGW-W64-install.exe 文件
		
		# 运行安装程序
			* version: 指的是 gcc 的版本，如果没有特殊的需求，一般选择最高的版本号即可。
			* Architecture: 系统架构，64位选择 x86_64，32位选择 i686。
			* Threads: 线程模型，开发 Windows 程序，需要选择 win32 ，而开发 Linux、Unix、Mac OS 等其他操作系统下的程序，则需要选择 posix。
				* posix：启用 POSIX 线程模型（pthread），适合跨平台代码。
				* win32：使用 Windows 原生线程模型（性能更高，但平台受限）。

			* Exception: 异常处理。
				* 如果之前选择了 64位，则这里有两个异常处理模型供你选择
					seh(Structured Exception Handling): 是新发明的，性能好，不支持 23 位
					sjlj: 则是古老的，稳定性好，支持 32 位。
				
				* 如果之前选择了 32位 ，则可以用 dwarf 和 sjlj 两种异常处理模型。
				* dwarf 的性能要优于 sjlj ，它不支持 64位 。
			
			* 系统运行时库的不同实现
				* UCRT（Universal C Runtime）：微软在 Windows 10 和后续系统中引入的新版运行时库，旨在统一不同版本的 Windows 运行时环境。支持更现代的 C/C++ 标准（如 C11、C17）。
				* MSVCRT（Microsoft Visual C++ Runtime）：传统的运行时库，旧版 Windows（如 Windows 7/8）默认自带。主要支持较旧的 C/C++ 标准（如 C89/C99 的部分特性）。
				
			* Build revision: 构建版本
	
	# 离线版本（推荐）
		# 也是打开下载地址，拉到下面，有列出可下载文件

			* 文件名称中定义了架构、线程模型以及异常处理方式（见 Exe 在线安装模式）
			* 下载对应的文件即可
			
			x86_64-posix-sjlj
			x86_64-posix-seh
			x86_64-win32-sjlj
			x86_64-win32-seh	// 推荐
			i686-posix-sjlj
			i686-posix-dwarf
			i686-win32-sjlj
			i686-win32-dwarf
		
		# 解压文件，把 mingw64 目录复制出去，并把 bin 目录添加到环境变量
			D:\mingw64\bin
		
		# 验证安装
			g++ --version



