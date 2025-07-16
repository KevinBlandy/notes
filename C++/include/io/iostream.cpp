-----------------------
<iostream>
-----------------------
	# 标准 IO 库
		* https://en.cppreference.com/w/cpp/header/iostream.html

-----------------------
简介
-----------------------

	#include <ios>
	#include <streambuf>
	#include <istream>
	#include <ostream>
	 
	namespace std {
	  extern istream cin;
	  	* 标准输入流

	  extern ostream cout;	
		* 标准输出流

	  extern ostream cerr;	
		* 标注错误流

	  extern ostream clog;
	  	* 标准日志流
	 
	  extern wistream wcin;
	  extern wostream wcout;
	  extern wostream wcerr;
	  extern wostream wclog;
	}
