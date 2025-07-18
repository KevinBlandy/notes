----------------------
cctype
----------------------
	# <cctype>
		* 此头文件最初作为 <ctype.h> 存在于 C 标准库中，该头文件是空终止字节字符串（NTBS）库的一部分。
		* https://en.cppreference.com/w/cpp/header/cctype.html


-----------------------
简介
-----------------------

	namespace std {
	  int isalnum(int c);
	  	* 检查字符是否为字母数字	

	  int isalpha(int c);
	  	* 检查字符是否为字母	

	  int isblank(int c);
	  	* 检查字符是否为空白（空格/制表符）	C++11 新增

	  int iscntrl(int c);
	  	* 检查字符是否为控制字符

	  int isdigit(int c);
	  	* 检查字符是否为数字	

	  int isgraph(int c);
	  	* 检查字符是否为图形字符

	  int islower(int c);
	  	* 检查字符是否为小写字母	
	
	  int isprint(int c);
		* 检查字符是否为可打印字符	

	  int ispunct(int c);
	  	* 检查字符是否为标点符号

	  int isspace(int c);
	  	* 检查字符是否为空白字符

	  int isupper(int c);
	  	* 检查字符是否为大写字母

	  int isxdigit(int c);
	  	* 检查字符是否为十六进制数字	

	  int tolower(int c);
	  	* 将字符转换为小写

	  int toupper(int c);
	  	* 将字符转换为大写
	}