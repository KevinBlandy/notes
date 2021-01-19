--------------------------------
stdio							|
--------------------------------

EOF
	* -1,表示文件结尾

BUFSIZ
	* 缓冲区大小

stdin
	* 标准输入流指针
stdout
	* 标准输出流指针
stderr
	* 标准错误流指针


int printf()
	* 用于标注输出(打印)
	* 支持字符串占位符,必须严格按照占位符的数据类型传递参数
		printf("Hello %d,%d",5,6);
	* 返回打印的字节数量,如果发生异常返回 -1

void perror (const char *);
	* 标注的错误打印流,会在参数字符串后面添异常/正常的提示
		perror("hi");	//hi: No error

	* 它可以打印'库函数'调用失败的原因
		fclose(stdout);
		printf("Hello");
		perror("hi");	//hi: Bad file descriptor(坏的文件描述符,因为已经关闭了)

sprintf(char dst*, const char *, ...)
	* 把 字符串格式化后,写入到dst中
		int x = 10;
		char y = 'H';
		char str[] = "Java";
		char dst[100];
		sprintf(dst,"x = %d,y = %c,buf = %s",x,y,str);
		printf("dst = %s\n",dst);	//dst = x = 10,y = H,buf = Java

puts()
	* 函数只用来输出字符串,不能使用占位符,自动添加换行
	* 里面的参数可以直接是字符串或者是存放字符串的字符数组名
	* 作用与 printf("%s\n",s);的作用形同

scanf()
	* 获取屏幕输入,跟 printf 一样,也可以使用格式字符串和参数列表
	* scanf 在读取的时候,会跳过所有非空白符前面的空白符
	* printf函数使用便利,常量和表达式,scanf函数使用变量的指针
		int var1;
		int var2;
		scanf("%d %d",&var1,&var2);
		printf("%d %d",var1,var2);

	* 当用户通过scanf输入字符时,编译器默认先把内容放在缓冲区
	* scanf自动在缓冲区读取内容

getchar()
	* 读取下一个字符串输入,并且返回,它只处理字符
	* 等同于
		char ch;
		scanf("%c",&ch);

putchar()
	* 该函数的作用就是,打印该函数的参数,它只处理字符
	* 等同于
		char ch = '1';
		printf("%d",ch)

gets(char *s)
	* 从标准输入读取字符,并保存到s指定的内存空间直到出现换行符或者文件结尾为止
	* 返回读取成功的字符串,如果读取失败返回 NULL
	* 它与scanf的区别,它允许输入的数据有空格
	* 它与scanf都无法知道输入字符串的大小,必须遇到换行符或者读取到文件的结尾才接收输入因此容易导致数组越界(缓冲区溢出)
	* 该api已经废弃,不建议使用( warning: the `gets' function is dangerous and should not be used.)

fgets(char *s,int size,FILE *stream)
	* 参数
		s: 字符串
		size:指定读取最大字符串的长度(默认 -1,不限制)
		stram:文件指针,'如果读取键盘输入的字符串,固定为stdin'
	* 从stream指定的文件内读入字符,保存到s指定的内存空间,直到出现换行字符,读取到文件结尾,或是已经读取了size - 1 个字符为止
	* 会自动在最后加上 '\0' 标识,会把换行符也一起读取进去
	* s最多只能装 length - 1个字符,因为必须要留一个给字符串结束符 '\0',如果输入内容大于了 size 或者 sizeof(s) 那么超出部分会被截断
	* 读取成功返回读取到的字符串,读取到文件末尾或者出错,返回 NULL
	* 无法读取中文???
	* 读取键盘输入demo
		char buf[100];
		fgets(buf,sizeof(buf),stdin);
		printf("你输入的是:%s\n",buf);

puts(const char *s)
	* 标准设备输出s字符串,会自动添加换行符 \n
	* 成功返回非负数,失败返回 -1


fputs(const char *str,FILE *stream)
	* 把 str 字符写入到stream指定的文件中,字符串结束符 '\0' 不写入文件
	* 无法输出中文???

fprintf(FILE *fp,const char *format, ...)
	* '源是多个变量,目标是文件'
	* 可以把格式化的内容,输出到指定的流
		FILE *file = fopen("E:\\c-lang.txt","w");
		fprintf(file,"Hello %s","Java");
		fclose(file);
	* printf("Hello %s","Java") == fprintf(stdout,"Hello %s","Java")

fscanf(FILE *fp, const char *format, ...) 
	* '源是文件,目标是多个变量'
	* 函数来从文件中读取字符串,但是在遇到第一个空格/换行字符时,它会停止读取
		FILE *file = fopen("E:\\c-lang.txt","r");
		int x,y,z;
		fscanf(file,"%d %d %d",&z,&y,&z);
		printf("z=%d,y=%d,z=%d",z,y,z);		//z=36,y=12,z=36
		fclose(file);

sprintf(char dst*, const char *, ...)
	* '源是多个变量,目标是缓冲区'
	* 把 字符串格式化后,写入到dst中

sscanf (const char *, const char *temp, ...)
	* '源是缓冲区,目标是多个变量'
	* 把从dst读取到的字符串,填充到temp模版
		//定义一个"输入的字符串"
		char dst[] = "1 2 3";
		//定义变量
		int a,b,c;
		//使用 cccanf 把 输入的字符,赋值给变量
		sscanf(dst,"%d %d %d",&a,&b,&c);
		printf("a=%d,b=%d,c=%d\n",a,b,c);	//a=1,b=2,c=3

	* 从字符串中提取整形变量是最方便的
		char inputs[] = "a=10,b=20";
		int a , b;
		sscanf(inputs,"a=%d,b=%d",&a,&b);
		printf("a=%d,b=%d\n",a,b);  //a=10,b=20

	* 提取字符串,默认以空格分割
		char temp[] = "abc def 123";
		char str1[4],str2[4],str3[4];
		sscanf(temp,"%s %s %s",str1,str2,str3);
		printf("str1=%s,str2=%s,str3=%s",str1,str2,str3);//str1=abc,str2=def,str3=123
	

FILE * fopen (const char *name, const char *model);
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

int fclose (FILE *);
	* 关闭文件

int fseek (FILE *file, long offset, int whence); 
	* 随机io

