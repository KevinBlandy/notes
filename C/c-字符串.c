------------------------
字符串常量				|
------------------------
	# C語言中没有字符串,使用的是字符数组
	# 字符串是内存中一段连续的char空间,以'\0'(数字0)结尾
	# 字符串一定是字符数组,字符数组不一定是字符串
		//字符数组
		char name[5] = {'K','e','v','i','n'};
		//字符串
		char name[10] = {'K','e','v','i','n','\0'};
		//字符串
		char name[10] = {'K','e','v','i','n',0};
		//字符串
		char a1[10] = {'a','b','c'};(因为后面的角标默认值为0,0就是结束符)

		* 字符数组以'\0'结尾,那么这个字符数组就是字符串
	
	# '%s'遇到结束符便结束
		    char a1[] = {'a','b','c','\0','d','e','f'};
			printf("%s",a1);        //abc
	
	# 最常用的初始化
		char string[] = "你好啊";
		
		* 字符串的结尾,编译器会自动添加结束符

			char string[] = "你好";
			printf("%s size=%d",string,sizeof(string));
			//你好 size=7  
			//UTF8编码一个汉字占3个字节,最后还有一个隐藏的0,一个字节表示结束符
		
		* 就算是自己主动添加结束符,编译器还是会帮你添加一个
			const char names[] = "Hello\0";
			printf("string = %s,size = %d",names,sizeof(names));
			//string = Hello,size = 7
		
		* 遇到 \0 便结束
			char string[] = "Hello\0c";
			printf("%s size=%d",string,sizeof(string));
			//Hello size=8
			//遇到\0结束,所以只打印了Hello,最后自动添加了\0结束符,所以也会占一个字节
		
		* \0后别跟数字,可能刚好组成一个转义字符
			char string[] = "\0123Hello";
			printf("%s size=%d",string,sizeof(string));
			//
			//3Hello size=8
			// '\012' 被转义成了一个换行符(012是一个八进制,在ascii中表示换行)
		
		* 如果限定字符长度,一定要给结束符留一个位置
			char string[6] = "Hello";
			printf("%s size=%d",string,sizeof(string));
			//Hello size=5
			//如果[]小于6,会有乱码,因为没有留给结束符位置
	
	# 获取字符串的长度(字节大小)
		* 使用 strlen(const char *s) 函数
		* 计算并返回字符串s的长度,不包含结束符 '\0',size_t 类型
		* 仅仅计算第一个文件分隔符以前的字符串长度
	
	# 字符串的copy
		* 使用 strcpy(char *dst,char *src) 函数
		* 如果src的内容长度大于dst的,那么会产生数据溢出
		* 只会拷贝第一个'\0'前的数据
			char name[11] = "KevinBlandy";
			char cp_name[11];

			strcpy(cp_name,name);
			printf("%s\n",cp_name);		//KevinBlandy
		
		* strncpy(char *dst,char *src,int size) 
		* 把 src 的数据拷贝到 dst,size参数可以限制copy的长度
		* 建议只复制 sizeof(dst) - 1 个元素到目标,因为要留一个位置给'\0'
		* 如果 size 超过了 sizeof(dst) - 1,那么会发生溢出
			char str[] = "123456";
			char dst[5];
			strncpy(dst,str,sizeof(dst) - 1);
			printf("%s",dst);       //123

		* 如果 '\0' 在拷贝的范围之内,那么'\0'以后的数据全部会被丢弃
			char str[] = "Hello\0Java";
			char dst[100];

			strncpy(dst,str,sizeof(str));

			printf("dst = %s\n",dst);                       //Hello
			printf("dst = %s\n",dst + strlen("Hello") + 1); //
	
	# 字符串的比较
		* 字符串的比较,strcmp(s1,s2)
		* 如果 s1 和 s2 是相同的,则返回 0
		* 如果 s1<s2 则返回小于 0,如果 s1 > s2 则返回大于 0
			char s1[11] = "Hello";
			char s2[11] = "Hi";
			char s3[11] = "Hello";
			printf("%d %d %d\n",strcmp(s1,s3),strcmp(s1,s2),strcmp(s2,s1));	//0 -1 1
		* 其实是比较字符的ascii码,谁大就是谁大
		
		* 可以指定比较的字符串长度使用 strncmp(s1,s2,length);
		* 仅仅比较 s1,s2的前length个字符
			char src[] = "HelloC";
			char dst[] = "HelloJava";
			int result = strncmp(src,dst,5);
			printf("result = %d\n",result);	//result = 0
			
	
	# 字符串的追加
		* strcat(s1, s2)连接字符串 s2 到字符串 s1 的末尾
			char s1[11] = "Hello";
			char s2[11] = " C";
			strcat(s1,s2);
			printf("%s\n",s1);	//HelloC
		
		* 仅仅追加指定的长度可以使用 strncat(s1,s2,length);
		* 把s2的前length个字符追加到s1
			char src[] = "Hello";
			char dst[] = "clang";
			strncat(src,dst,1);
			printf("result = %s\n",src);		//result = Helloc

	
	# 获取格式化后的字符串(序列化)
		* sprintf(char dst*, const char *, ...)
		*  把 字符串格式化后,写入到dst中
			int x = 10;
			char y = 'H';
			char str[] = "Java";
			char dst[100];
			sprintf(dst,"x = %d,y = %c,buf = %s",x,y,str);
			printf("dst = %s\n",dst);	//dst = x = 10,y = H,buf = Java
	
	# 预定义从键盘的输入的格式化字符串(反序列化)
		* sscanf (const char *, const char *, ...)
		* 把从屏幕读取到的字符串格式化后,写入到dst中
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
	
	# 字符串的查询
		* 检索字符第一次出现的位置(内存地址) strchr(temp,'x');
		* 如果查询失败,返回 NULL

		* 检索字符串第一次出现的位置(内存地址) strstr(temp,"def");
		* 如果查询失败,返回 NULL
		
		    char temp[] = "abcdefg";

			char *result = strchr(temp,'a');
			printf("result = %p,char = %c \n",result,*result);  //result = 0061FF24,char = a

			result = strstr(temp,"def");
			printf("result = %p,char = %c \n",result,*result);  //result = 0061FF27,char = d
		
	
	# 字符串切割
		* strtok(char *str,const char *delmi);
		* 以 delmi 来切割字符串str
		* 返回值为切割后的字符串
		* 匹配切割字符串的地方,换成结束符
		* 可以通过多次调用该方法来获取到字符串中所有被切割的字段
		* 除了第一次调用,余下的N次调用第一个参数都必须是NULL,当最后一次匹配不出结果返回 NULL
			char temp[] = "ab-cd-ef";
			//第一次匹配
			char *p = strtok(temp,"-");
			printf("p = %s\n",p);           //ab

			//第二次匹配
			p = strtok(NULL,"-");
			printf("p = %s\n",p);           //cd

			//第三次匹配
			p = strtok(NULL,"-");
			printf("p = %s\n",p);           //ef

			//第四次匹配
			p = strtok(NULL,"-");
			printf("p = %s\n",p);           //NULL 第四次匹配,没有了返回NULL

			printf("原字符串:%s\n",temp);   //被切割的字符串会因为切割操作而被修改(插入了\0)
		* 读取出所有的分割段
			char temp[] = "ab-cd-ef-gh-ij";
			char delmi[] = "-";
			char *p = strtok(temp,delmi);

			while(p != NULL){
				printf("find:%s \n",p);
				p = strtok(NULL,delmi);
			}
			/*
				find:ab
				find:cd
				find:ef
				find:gh
				find:ij
			*/
		
		# 把字符串转换为数字类型
			* 转换为整形: atoi(const char *nptr);
			* 会跳过前面的空格,直到遇到数字或者正负号才开始转换,遇到非数字字符串或者\0就结束转换,并且返回转换结果
			
			* 转换为浮点数: stof();
			* 转换为long: atol();


		# 字符串常量
			* 每个字符串都是一个首地址，这个地址是指字符串首元素地址
				printf("%s\n","Hello" + 1);		//ello		+1，首地址向后偏移1
				printf("%c\n",*("Hello"));		//H			取出了首地址的字符

				char *p = "123456789";				//字符串可以直接赋值给指针，因为字符串本身就是个指针

			* 字符串常量放在内存的data区，文字常量区
				void func(char *p){
					printf("%p",p);
				}
				int main() {
					printf("%p\n","Hello");
					func("Hello");
					/*
						00405067
						00405067
					*/
					return EXIT_SUCCESS;
				}
			
			* 字符串常量，文字常量区的字符串，只读，不能修改
				char *p = "123456";
				*p = 'z';
				printf("%s\n",p);	//error

				char *p = "123456";
				strcpy(p, "abc");	//error
			
			* 文字常量区的生命周期跟程序的生命周期一样，程序结束才释放内存
		
		# 字符串常量初始化的问题
			//p 指针，保存了常量 "KevinBlandy" 的地址
			//常量不能修改
			char *p = "KevinBlandy";
			*p = "123";		//error

			//把"KevinBlandy"保存在buf数组中，内存位置非常量池，数组它是可以修改的
			char buf[] = "KevinBlandy";
			buf[0] = 'z';	//ok

------------------------
字符的输入/输出			|
------------------------
	# scanf 和 printf
		char ch;
		scanf("%c",&ch);
		printf("%c",ch);

		char name[100];
		char buf[100] = {0};
		scanf("%s",buf);
		printf("你是输入的是:%s\n",buf);

		* 默认以空格为分割,仅仅读取空格符前面的数据,后面的字符会被添加到缓冲区
		* 'scanf() 这种方式输入不会有越界检查,不安全'
		* scanf 会自动对输入的字符添加 '\0'
	
	# getchar() / putchar()
		* 专门用于处理单个字符的函数
		* 一次性的读取/输出单个字符,效率比较高,因为专门处理的就是字符
			char ch;
			scanf("%c",&ch)

			char ch = '1';
			printf("%d",ch)

	
	# 使用 gets / fgets 读取字符串
		gets(char *s)
			* 已经废弃了,从标准输入读取字符,并保存到s指定的内存空间直到出现换行符或者文件结尾为止
			* 可能会发生数组越界的情况,因为不知道用户输入的内容大小,不建议使用
		
		fgets(char *s,int size,FILE stream)
			* 参数
				s: 字符串
				size:指定读取最大字符串的长度(默认 -1,不限制)
				stram:文件指针,'如果读取键盘输入的字符串,固定为stdin'
			* 从stream指定的文件内读入字符,保存到s指定的内存空间,直到出现换行字符,读取到文件结尾,或是已经读取了size - 1 个字符为止
			* 会自动在最后加上 '\0' 标识,会把换行符也一起读取进去
			* s最多只能装 length - 1个字符,因为必须要留一个给字符串结束符 '\0',如果输入内容大于了 size 或者 sizeof(s) 那么超出部分会被截断
			* 读取成功返回读取到的字符串,读取到文件末尾或者出错,返回 NULL
			* 读取键盘输入demo
				char buf[100];
				fgets(buf,sizeof(buf),stdin);
				printf("你输入的是:%s\n",buf);
		
		fgetc( FILE * fp );
			* fgetc() 函数从 fp 所指向的输入文件中读取一个字符,返回值是读取的字符
			* 如果发生错误则返回 EOF,(-1)

	# 使用 puts / fputs 来输出字符串
		puts(const char *s)
			* 标准设备输出s字符串,会自动添加换行符 \n
			* 成功返回非负数,失败返回 -1

		fputs(const char *str,FILE *stream)
			* 把 str 字符写入到stream指定的文件中,字符串结束符 '\0' 不写入文件
			* 成功返回 0,失败返回 -1
			* 可以把stream替换为 stdout,使str被输出到屏幕
		
		fputc( int c, FILE *stream );
			* 用于把单个字符写入stream指定的文件中
			* 果写入成功,它会返回写入的字符,如果发生错误,则会返回 EOF
	
	# 打印流
		fprintf(FILE *fp,const char *format, ...)
			* 可以把格式化的内容,输出到指定的流
			* printf("Hello %s","Java") == fprintf(stdout,"Hello %s","Java")
	
	
	# 从文件读取一行字符
		int fscanf(FILE *fp, const char *format, ...) 
		* 函数来从文件中读取字符串,但是在遇到第一个空格/换行字符时,它会停止读取
		* 可以从指定的流读取数据,填充模版,序列化到指定的缓冲区
			FILE *file = fopen("E:\\c-lang.txt","r");
			char buf[1024];
			fscanf(file,"%s",buf);	//读取到第一行,存入buf
		
	

----------------------------
常用的字符串函数			|
----------------------------
	# 去除字符串两边的空格
		void trim(char *p,char *dst){
			char *start = p;
			char *end = p + (strlen(p) - 1);
			while((*start == ' ' || *start == '	' || *start == '\n') && start != '\0'){
				start++;
			}
			while((*end == ' ' || *end == '	' || *end == '\n') && start != end){
				end --;
			}
			*(end + 1) = '\0';
			strcpy(dst, start);
			//strcpy(p,start); 可以在原字符串上修改,但是原字符串不能是字符串常量
		}

		void trim(char *str){
			char *start = str;
			char *end = str + (strlen(str) - 1);
			while((*start == ' ' || *start == '\n' || *start == '	') && start < end){
				start ++;
			}
			while((*end == ' ' || *end == '\n' || *end == '	') && end > start){
				end --;
			}
			*(end + 1) = '\0';
			strcpy(str, start);
		}
	
	# 反转字符串
		void reversal(char *p) {
			int start = 0;
			int end = strlen(p) - 1;
			while (start < end) {
				p[start] = p[start] ^ p[end];
				p[end] = p[start] ^ p[end];
				p[start] = p[start] ^ p[end];

				start++;
				end--;
			}
		}
	# 检索字符串中，子串出现的次数
		int count(const char *p,const char *s){
			char *temp = strstr(p,s);
			int count = 0;
			while(temp != NULL){
				p = temp + strlen(s);
				temp = strstr(p,s);
				count ++;
			}
			return count;
		}

		int count(char *p, char *sub) {
			int count = 0;
			char *index = strstr(p, sub);
			int position = strlen(sub);
			while (index != NULL) {
				index = strstr(index + position, sub);
				count++;
			}
			return count;
		}