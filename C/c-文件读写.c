----------------------------
文件编程					|
----------------------------
	# 文件的类型
		* 设备文件,设备也可以当做文件,具备io
		* 磁盘文件

	# 打开文件
		FILE *fopen( const char * filename, const char * mode );
		
		* 打开一个文件,name指定文件地址,model指定打开的类型
		* model的枚举字符串
			r	打开一个已有的文本文件,允许读取文件

			w	打开一个文本文件,允许写入文件,如果文件不存在,则会创建一个新文件
				在这里,程序会从文件的开头写入内容,如果文件存在,则该会被截断为零长度,重新写入

			a	打开一个文本文件,以追加模式写入文件,如果文件不存在,则会创建一个新文件
				在这里,程序会在已有的文件内容中追加内容

			r+	打开一个文本文件,允许读写文件

			w+	打开一个文本文件,允许读写文件,如果文件已存在,则文件会被截断为零长度
				如果文件不存在，则会创建一个新文件

			a+	打开一个文本文件,允许读写文件,如果文件不存在,则会创建一个新文件,读取会从文件的开头开始,写入则只能是追加模式
		
		*  处理的是二进制文件,则需使用下面的访问模式来取代上面的访问模式：
			"rb", "wb", "ab", "rb+", "r+b", "wb+", "w+b", "ab+", "a+b"
		
		* 如果打开失败,返回 NULL
			FILE *file = fopen("d.txt","r");
			if (file == NULL){
				perror("fopen");	//fopen: No such file or directory
				return EXIT_FAILURE;
			}
		* 注意,该结构体是在堆空间分配的,使用完成后要释放
		* 文件的打开,可以是相对路径,也可以是绝对路径
			fopen("./d.txt","r");


	# 关闭文件
		int  fclose (FILE *);
		* 关闭文件会刷出缓冲区
		* 成功返回 0,失败返回 -1
	
	# 刷出缓冲区
		int fflush (FILE *);
		* 成功返回 0,失败返回 -1
	
	# 重定向流到文件
		FILE * freopen (const char *filename, const char *mode, FILE *stream);
		* filename 需要重定向到的文件名或文件路径
		* mode 代表文件访问权限的字符串,例如，"r"表示"只读访问","w"表示"只写访问","a"表示"追加写入"
		* stream 需要被重定向的文件流
		* 如果成功，则返回该指向该输出流的文件指针，否则返回为NULL
			if(freopen("D:\\output.txt", "w", stdout) == NULL){
				fprintf(stderr,"error redirecting stdout\n");
			}
			/* this output will go to a file */
			printf("This will go into a file.\n");
			/*close the standard output stream*/
			fclose(stdout);
		
	# 设置文件的缓冲区
		int setvbuf (FILE *file, char *buf, int model, size_t size);
		* 设置文件的缓冲区,设置成功返回0
		* file 文件
		* buf 缓冲区指针
		* model 枚举值,缓冲器的模式
			_IOFBF		0x0000	/* 完全缓冲,在缓冲区满的时候刷出 */
			_IOLBF		0x0040	/* 行缓冲,在缓冲区满或者写入一个换行符时刷出 */
			_IONBF		0x0004	/* 无缓冲 */
		* size 缓冲区的大小
	

	# FILE 结构体的属性
		typedef struct _iobuf
		{
		  char	*_ptr;
		  int	 _cnt;
		  char	*_base;
		  int	 _flag;
		  int	 _file;
		  int	 _charbuf;
		  int	 _bufsiz;
		  char	*_tmpfname;
		} FILE;

		typedef struct 
		{
			short			level;		//缓冲区状态,满或者空
			unsigned		flags;		//文件状态标识
			char			fd;			//文件描述符
			unsigned char	hold;		//如无缓冲区不读取字符
			short			bsize;		//缓冲区大小
			unsigned char	*buffer;	//缓冲区数据位置
			unsigned		ar;			//指针,当前的指向
			unsigned		istemp;		//临时文件,指示器
			short			token;		//用于有效性的检查
		} FILE;
	
	# 把指定的字符放回流中
		int ungetc (int, FILE *)
		* 感觉没啥用...
			ch = getchar();
			ungetc(cg,stdin);
	
	# 异常判断
		int ferror (FILE *);
		* 如果文件IO发生了异常,该函数返回非0,否则返回0
	
	# 文件结尾判断
		* 文本文件的末尾会有一个隐藏的-1(EOF),表示文件已经结束
		* 二进制文件末尾没有-1标识,因为-1可能是文件中的数据,同过 -1 来判断不靠谱
		* 可以通过 feof() 来判断文件是否读取到了末尾,不论是二进制文件还是文本文件
		* feof(); 返回 bool
			FILE *file = fopen("c.txt","r");

			bool end = feof(file);
			printf("%d",end);		//0

		* 它其实是判断你读取后的数据,是不是末尾标识,也就是说要先读取了末尾标识,所以,一般先读取,再判断
			fgetc(file);
			if(feof(file)){
				break;
			}
			....

----------------------------
文本文件的读写				|
----------------------------
	# 读取/写入单个字符
		fgetc( FILE * fp );
			* fgetc() 函数从 fp 所指向的输入文件中读取一个字符,返回值是读取的字符
			* 如果发生错误则返回 EOF,(-1)
		
		fputc( int c, FILE *stream );
			* 用于把单个字符写入stream指定的文件中
			* 果写入成功,它会返回写入的字符,如果发生错误,或者读取到了末尾,则会返回 EOF(-1)
		
	# 读取/写入字符串
		fgets(char *s,int size,FILE stream)
			* 参数
				s: 字符串
				size:指定读取最大字符串的长度(默认 -1,不限制)
				stram:文件指针,'如果读取键盘输入的字符串,固定为stdin'
			* 从stream指定的文件内读入字符,保存到s指定的内存空间,直到出现换行字符,读取到文件结尾,或是已经读取了size - 1 个字符为止
			* 会自动在最后加上 '\0' 标识,会把换行符也一起读取进去
			* s最多只能装 length - 1个字符,因为必须要留一个给字符串结束符 '\0',如果输入内容大于了 size 或者 sizeof(s) 那么超出部分会被截断
			* 读取成功返回读取到的字符串,读取到文件末尾或者出错,返回 NULL
			* 读取文件
				FILE *file = fopen("E:\\c-lang.txt","r");
				char buf[1024];
				char *r = fgets(buf,sizeof(buf) - 1,file);
				while(r != NULL){
					printf("%s",buf);
					r = fgets(buf,sizeof(buf) - 1,file);
				}
		
		fputs(const char *str,FILE *stream)
			* 把 str 字符写入到stream指定的文件中,字符串结束符 '\0' 不写入文件
			* 成功返回 0,失败返回 EOF(-1)
			* 可以把stream替换为 stdout,使str被输出到屏幕
		
	

	# 格式化流输出		fprintf(FILE *fp,const char *format, ...)
		* 源是变量,目标是文件

		FILE *file = fopen("E:\\c-lang.txt","w+");
		fprintf(file,"Hello KevinBlany ,Im %s","Java");
		fclose(file);
		
	
	# 格式化流输入		int fscanf(FILE *fp, const char *format, ...) 
		* 源是文件,目标是变量
		* 函数来从文件中读取字符串,但是在遇到第一个空格字符时,它会停止读取

		FILE *file = fopen("E:\\c-lang.txt","r");
		int x,y,z;
		fscanf(file,"%d %d %d",&z,&y,&z);
		printf("z=%d,y=%d,z=%d",z,y,z);		//z=36,y=12,z=36
		fclose(file);
	
	
	
----------------------------
随机io						|
----------------------------
	int fseek (FILE *file, long offset, int whence); 
		* fseek 设置当前读写点到 offset 处
		* 如果操作成功返回0,操作失败返回-1(移动的范围超过了文件大小)
		* whence 可以是 SEEK_SET,SEEK_CUR,SEEK_END 这些值决定是从文件头,当前点,文件尾	计算偏移量 offset
		* 相对当前位置往后移动一个字节:fseek(fp,1,SEEK_CUR);
		* 往前移动一个字节.直接改为负值就可以:fseek(fp,-1,SEEK_CUR)

			FILE *file = fopen("E:\\c-lang.txt","r+");
			//指针移动到第10个字节
			fseek(file,10,SEEK_SET);
			//把第10个字节替换为'A'
			if(fputc('A',file) == EOF){
				printf("异常");
			}
			fclose(file);

		* 只有用 r+ 模式打开文件才能插入内容,w 或 w+ 模式都会清空掉原来文件的内容再来写
		* a 或 a+ 模式即总会在文件最尾添加内容,哪怕用 fseek() 移动了文件指针位置

	long ftell(FILE *file);
		* 获取文件光标的位置
		* 如果失败返回 -1
	
	void rewind(FILE *file);
		* 把文件光标移动到文件开头(重置指针位置)

	# 通过随机io获取文件的大小
		//打开文件
		FILE *file = fopen("D:\\springboot.sql","r+");
		//移动指针到末尾
		fseek(file,0,SEEK_END);
		//获取文件的光标位置,其实就是大小(KB)
		long size = ftell(file);
		printf("size=%ld",size);
	
	# fgetpos(); 和 fsetpos();
		int fgetpos (FILE *, fpos_t *);
			* 获取文件指针位置,注意,这里需要给变量的指针,是通过指针赋值的
		int fsetpos (FILE *, const fpos_t *);
			* 设置文件指针位置,注意,这里需要给变量的指针,是通过指针读取变量的

		* fseek() 和 fetll() 的问题是,他们都使用 long 这个数据类型,最多表示20亿个字节
		* 但是现在的文件越来越大,可能超过了 20 亿
		* 新的自定义数据类型: fpos_t  == typedef long long  fpos_t;
		* 如果执行成功返回0,失败返回-1

----------------------------
二进制文件的读写			|
----------------------------
	
	# 读取二进制文件
		* size_t fread(void *ptr, size_t size_of_elements, size_t number_of_elements, FILE *a_file);
			* ptr 存放读取数据的内存空间
			* size_of_elements 指定读取文件内容的块数据大小(unsigned int)
			* number_of_elements 读取文件的块数,读取文件数据总大小为: size_of_elements * number_of_elements
			* a_file 已经打开的文件指针
		* 成功返回，number_of_elements(读取到的文件块数),如果该值比number_of_elements小,但是大于0,则表示读取到了文件末尾
		* 如果用户指定读取的块儿大小,大于了文件可读的大小,返回可能为0
			* 读取1块儿(一块儿设置为了10个字节)
			* 但是文件只有5个字节了。只能读取0.5块儿。返回0.5，因为结果是int。被类型转换最终为 0
			* '建议块大小永远设置为1,则不会出现该问题'
		* 失败,返回0
	
	# 写入二进制数据
		* size_t fwrite(const void *ptr, size_t size_of_elements,size_t number_of_elements, FILE *a_file);
			* ptr 准备写入文件的数据
			* size_of_elements 指定写入文件内容的块数据大小(unsigned int)
			* number_of_elements 写入文件的块数,写入文件的总大小 = size_of_elements * number_of_elements
			* a_file 已经打开的文件指针
		* 成功返回，number_of_elements(成功写入文件数据的块数目),失败返回 0
		



	# 这两个函数都是用于存储块的读写 (写入和读取的通常是数组或结构体)

	# 简单的copy
		FILE *source = fopen("D:\\20181009153347.jpg","rb");
		FILE *target = fopen("D:\\cp.jpg","wb");

		char buf[1024];

		int result = fread(buf,1,1024,source);
		while(result > 0){
			printf("读取到了:%d\n",result);
			fwrite(buf,1,result,target);
			fflush(target);
			result = fread(buf,1,1024,source);
		}

		fclose(target);
		fclose(source);
	
	# struct 的读写
		struct Lang {
			unsigned int id;
			char name[10];
		};
		struct Lang lang = {2,"Python"};

		FILE *file = fopen("D:\\lang.d","wb");
		fwrite(&lang,sizeof(struct Lang),1,file);
		fflush(file);
		fclose(file);

		file = fopen("D:\\lang.d","rb");
		fread(&lang,sizeof(struct Lang),1,file);
		printf("lang=%d,name=%s\n",lang.id,lang.name);
	
	# struct 数组的读写
		struct Lang {
			unsigned int id;
			char name[11];
		};

		struct Lang langs[] = {
			{1,"Java"},
			{2,"Python"},
			{3,"C"},
			{4,"Javascript"}
		};

		FILE *file = fopen("D:\\langs.d","wb");
		fwrite(langs,sizeof(langs),1,file);
		fflush(file);
		fclose(file);

		file = fopen("D:\\langs.d","rb");
		fread(langs,sizeof(langs),1,file);
		for(int x = 0 ;x < 4 ; x++){
			printf("id=%d,name=%s\n",langs[x].id,langs[x].name);
		}
	

----------------------------
Linux与Window下的文件区别	|
----------------------------
	# fopen("c.txt","rb") 第二个参数中的 "b"
		* "b"表示以2进制模式打开文件,其实在Linux系统下,使用该函数不用加"b",也是可以的
			fopen("c.txt","r");	//在Linux下同样是以二进制的形式打开文件
		* 为了系统兼容,一般还是加一个"r"比较好
	
	# Unix和Linux下的所有文本文件都是以:"\n"结尾,而Windows下的文本文件以:"\r\n"结尾
	# 在Windows平台下,读取文本文件的时候,系统会把所有的"\r\n"转换为"\n"
	# 在Windows平台下,写入文本文件的时候,系统会把所有的"\n"转换为"\r\n"
	# 判断文件是Linux文件还是Windows文件

----------------------------
删除与重命名				|
----------------------------
	# 删除文件
		int remove (const char file*);
		* 成功返回0,失败返回-1

	# 重命名
		int rename (const char *old, const char *new_);
		* 成功返回0,失败返回-1

----------------------------
获取文件的属性				|
----------------------------
	# 通过 <sys/stat.h> 下的 int stat (const char *path, struct stat *); 函数
		* 获取文件的属性,成功返回0,失败返回-1
		* path 文件的路径,stat 保存状态的结构体
		* 结构体的定义
			{ _dev_t	st_dev; 	/* Equivalent to drive number 0=A 1=B ... */ 
			  _ino_t	st_ino; 	/* Always zero ? */			   
			  _mode_t	st_mode;	/* See above constants */		     
			   short 	st_nlink;	/* Number of links. */			     
			   short 	st_uid; 	/* User: Maybe significant on NT ? */	     
			   short 	st_gid; 	/* Group: Ditto */			    
			  _dev_t	st_rdev;	/* Seems useless (not even filled in) */    
			  __st_off_t	st_size;	/* File size in bytes */		    
			  __st_time_t	st_atime;	/* Access time (always 00:00 on FAT) */	    
			  __st_time_t	st_mtime;	/* Modified time */			    
			  __st_time_t	st_ctime;	/* Creation time */			    
			}

	# 通过该方法获取文件的大小
		#include <stdlib.h>
		#include <stdio.h>
		#include <sys/stat.h>

		int main(int argc, char **argv) {
			struct stat st;
			int result = stat("D:\\springboot.sql",&st);
			printf("size=%d\n",st.st_size);
			return EXIT_SUCCESS;
		}
	
	# 通过 'st_mode' 属性可以获取到更多关于文件的信息
		*  以下判断函数都是 <sys/stat.h> 宏定义的,用于判断,返回 bool
			S_ISDIR(m)	(((m) & S_IFMT) == S_IFDIR)			//是否是目录
			S_ISFIFO(m)	(((m) & S_IFMT) == S_IFIFO)
			S_ISCHR(m)	(((m) & S_IFMT) == S_IFCHR)
			S_ISBLK(m)	(((m) & S_IFMT) == S_IFBLK)
			S_ISREG(m)	(((m) & S_IFMT) == S_IFREG)			//是否是普通文件

		*  demo
			S_ISDIR(fileStat.st_mode);		//是否是一个目录
			S_ISREG(fileStat.st_mode)		//是否是一个普通文件
		
-----------------------------
操作目录(文件夹)			|
----------------------------
# <dirent.h> 
	# 打开一个目录,返回一个目录指针 typedef struct __dirstream_t DIR;
		DIR * opendir (const char *__dirname)
	
	# 读取目录信息,参数是一个目录指针,返回 struct dirent
		struct dirent*  readdir (DIR *__dir)
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

# <io.h>
	# 创建一个文件夹
		int mkdir (const char *);
		* 创建成功返回0,否则返回-1(目录已经存在)
	
	# 获取当前执行程序所在的目录
		char *getcwd (char *buf, int size);
		
		* 把当前路径写入buf,最大长度为size
			char buf[1024] = { 0 };
			getcwd(buf, 1024);
			printf("%s", buf);	//D:\workspace\clang-practice

----------------------------
关于文件缓冲区				|
----------------------------
	# ANSI C标准采用"缓冲区文件系统"
