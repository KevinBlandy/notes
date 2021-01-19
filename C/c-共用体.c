------------------------------------
共同体								|
------------------------------------
	# 共用体是一种特殊的数据类型,允许在'相同的内存位置存储不同的数据类型'
	# 可以定义一个带有多成员的共用体,但是'任何时候只能有一个成员带有值'
	# 共用体提供了一种使用相同的内存位置的有效方式
		union Data {
			int id;
			char name[20];
		} data;

		data.id = 1;
		printf("%d\n",data.id);					//1

		strcpy(data.name,"KevinBlandy");
		printf("%d %s\n",data.id,data.name);	//1769366859 KevinBlandy	(因为公用体重复赋值,所以id变量的值被擦除)
	
	# 公用体的合法声明跟结构体一样

	# 共用体占用的内存应足够存储共用体中最大的成员
		union Data {
			int i;
			float f;
			char str[20];
		} data;

		printf("%d\n", sizeof(data));		//20

		* 换句话说,公用体的最大内存,取决于里面定义的最大内存变量
	