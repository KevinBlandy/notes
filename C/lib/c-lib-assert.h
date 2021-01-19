--------------
assert			|
--------------
	# assert 断言库

assert(int )
	* 如果参数表达式非0(假),assert就会标准的错误流中写入错误信息
	* 并且会调用 abort() 函数(stdlin)停止程序

		assert(1 == 0);		//Assertion failed: 1 == 0, file Demo.c, line 6