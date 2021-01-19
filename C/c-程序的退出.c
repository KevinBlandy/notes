
-------------------
exit				|
-------------------
	void exit (int)
		* 退出程序
		* 它会执行 atexit() 添加的所有回调函数
		* 刷新所有输出流,关闭所有打开的流,关闭 tempfile() 创建的临时文件,把控制权返回主机环境,向主机环境报告终止状态

-------------------
atexit				|
-------------------
	int atexit (void (*)(void));

	* 该函数参数是一个方法指针
	* 该方法可以多次调用,最少可以放32个回调函数
	* 在程序退出的时候,会调用该方法(最后添加的先执行)
	
	void f1(){
		printf("第一个执行\n");
	}
	void f2(){
		printf("第二个执行\n");
	}

	int main(int argc, char **argv) {
		atexit(&f1);
		atexit(&f2);

		/*
			第二个执行
			第一个执行
		*/
		return EXIT_SUCCESS;
	}
