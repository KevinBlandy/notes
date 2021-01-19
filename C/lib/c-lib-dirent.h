------------------------
dirent					|
------------------------
	# 操作目录的库

	DIR * opendir (const char *__dirname)
		* 打开一个目录,返回一个目录指针 typedef struct __dirstream_t DIR;
		* 如果目录不存在,返回 NULL
	
	dirent*  readdir (DIR *__dir)
		* 读取目录信息,参数是一个目录指针
		* 返回 struct dirent
			struct dirent
			{
			  long            d_ino;				/* Always zero. */
			  unsigned short  d_reclen;				/* Always sizeof struct dirent. */
			  unsigned short  d_namlen;				/* 文件/文件夹名称的字符长度 */
			  unsigned        d_type;				/* File attributes */
			  char            d_name[FILENAME_MAX]; /* 文件/文件夹名称 */
			};

		* 它应该循环调用
		* 因为每次调用,它会逐渐遍历出目录指针下的所有文件/目录信息,如果遍历到末尾,返回 NULL
	
		* 遍历某个目录下的所有文件
			DIR *dir = opendir("E:\\letsencrypt");
			struct dirent *dirent = NULL;
			while ((dirent = readdir(dir)) != NULL) {
				printf("%s\n", dirent->d_name);
			}
	

