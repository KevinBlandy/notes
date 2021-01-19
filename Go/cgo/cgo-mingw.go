----------------------------
mingw
----------------------------
	# Mingw
		* MinGW 就是 GCC 的 Windows 版本 
		* MinGW-w64 与 MinGW 的区别在于 MinGW 只能编译生成32位可执行程序，而 MinGW-w64 则可以编译生成 64位 或 32位 可执行程序。
		* MinGW 现已被 MinGW-w64 所取代，且 MinGW 也早已停止了更新，内置的 GCC 停滞在了 4.8.1 版本，而 MinGW-w64 内置的 GCC 则更新到了 6.2.0 版本
	
	# 网站
		http://mingw-w64.org/
		https://sourceforge.net/projects/mingw-w64/files/
	
	# 安装
		* 下载 MinGW-W64-install.exe
			https://sourceforge.net/projects/mingw-w64/files/
			
		* 运行在线安装(国内要翻墙)
			version			8.1.0
			Architecture	x68_64
			Threads			posix
			Exection		seh
			Build			revision 0
		
		* 添加到环境变量	
			D:\mingw64\bin