-----------------------
CPP 标准库
-----------------------
	# 参考
		https://en.cppreference.com/w/cpp/header.html
	
	
	# 建议:使用 C++ 版本的 C 标准库头文件

		* C++ 标准库中除了定义 C++ 语言特有的功能外，也兼容了C语言的标准库。C语言的头文件形如 name.h，C++ 则将这些文件命名为 cname。也就是去掉了.h 后缀，而在文件名 name 之前添加了字母 c，这里的 c 表示这是一个属于 C 语言标准库的头文件。
		* 因此，cctype 头文件和 ctype.h 头文件的内容是一样的，只不过从命名规范上来讲更符合 C++ 语言的要求。
		* 特别的，在名为 cname 的头文件中定义的名字从属于命名空间 std，而定义在名为 .h 的头文件中的则不然。
		* 一般来说，C++程序应该使用名为 cname 的头文件而不使用 name.h 的形式，标准库中的名字总能在命名空间 std 中找到。如果使用 .h 形式的头文件，程序员就不得不时刻牢记哪些是从 C 语言那儿继承过来的，哪些又是 C++ 语言所独有的。

