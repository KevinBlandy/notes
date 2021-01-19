------------------------
io.h					|
------------------------
	# 系统io相关
	# 在Linux环境中,这个库的位置在:/usr/include/sys/io.h

	int mkdir (const char *);
		* 创建一个文件夹
		* 创建成功返回0,否则返回-1(目录已经存在)

	char *getcwd (char *buf, int size);
		* 获取当前执行程序所在的目录
		* 把当前路径写入buf,最大长度为size
			char buf[1024] = { 0 };
			getcwd(buf, 1024);
			printf("%s", buf);	//D:\workspace\clang-practice
