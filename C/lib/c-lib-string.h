--------------------------------
string							|
--------------------------------

strcpy(char *dst,char *src);
	* 把 src 的数据拷贝到 dst
	* 如果src的内容长度大于dst的,那么会产生数据溢出
	* 只会拷贝第一个'\0'前的数据,并且会把 '\0' 拷贝到目标数组
		char name[11] = "KevinBlandy";
		char cp_name[11];

		strcpy(cp_name,name);
		printf("%s\n",cp_name);		//KevinBlandy

 strncpy(char *dst,char *src,int size) 
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

strcat(s1, s2)
	* 连接字符串 s2 到字符串 s1 的末尾
		char s1[11] = "Hello";
		char s2[11] = " C";
		strcat(s1,s2);
		printf("%s\n",s1);	//HelloC

strncat(s1,s2,length)
	* 同上,可以限制追加字符串的长度
	* 把s2的前length个字符追加到s1
		char src[] = "Hello";
		char dst[] = "clang";
		strncat(src,dst,1);
		printf("result = %s\n",src);		//result = Helloc

strlen(s1)
	* 返回字符串的长度(字节大小),不包含结束符,仅仅计算到第一个分割符
	* 返回 size_t 类型
		char s1[11] = "Hello";
		char s2[11] = "He\0llo";
		printf("%d %d\n",strlen(s1),strlen(s2));		//5 2

strcmp(s1,s2)
	* 字符串的比较
	* 如果 s1 和 s2 是相同的,则返回 0
	* 如果 s1<s2 则返回小于 0,如果 s1 > s2 则返回大于 0
		char s1[11] = "Hello";
		char s2[11] = "Hi";
		char s3[11] = "Hello";
		printf("%d %d %d\n",strcmp(s1,s3),strcmp(s1,s2),strcmp(s2,s1));	//0 -1 1
	
strncmp(s1,s2,length)
	* 同上,也是字符串的比较,该方法可以指定比较的字符串长度
		char src[] = "HelloC";
		char dst[] = "HelloJava";
		int result = strncmp(src,dst,5);
		printf("result = %d\n",result);	//result = 0

strchr(s1, ch);
	* 返回一个指针,指向字符串 s1 中字符 ch 的第一次出现的位置
	* 如果没找到返回0
		char s1[11] = "Hello";
		printf("%p\n",strchr(s1,'H'));		//28FF35

strstr(s1, s2);
	* 返回一个指针,指向字符串 s1 中字符串 s2 的第一次出现的位置
	* 如果没找到返回0
		char s1[11] = "Hello";
		char s2[11] = "ll";
		printf("%p",strstr(s1,s2));	//0028FF37


strtok(char *str,const char *delmi);
	* 以 delmi 来切割字符串str
	* 返回值为切割后的字符串
	* 匹配切割字符串的地方,换成结束符
	* 被切割的字符串会因为切割操作而被修改(插入了\0)
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

void *memset (void *p, int v, size_t s)
	* 将p所指向的某一块内存中的'每个字节'的内容全部设置为v指定的ASCII值,块的大小由第三个参数s指定
	* 参数
		- p 操作的内存首地址
		- c 填充的数据看起来是整形,其实是当作 ascii 码值,unsigned int,可以是 0 - 255
			* 其实它的值只有是0 才有意义,或者说p是一个数组
		- n 填充的数据大小(以p开始填充多少个字节)
	* 返回 p 的首地址地址
	* demo
		int a;
		memset(&a,0,sizeof(a));		//其实是四个字节每个字节都写入了 97
		printf("%d\n",a);	//0

		memset(&a,97,sizeof(a));	//其实是四个字节每个字节都写入了 97
		printf("%c\n",a);			//a(%c仅仅读取一个字节)

		int arr[10];
		memset(arr,97,sizeof(arr));	//40个字节,每个字节都写入了0
		printf("%c\n",arr[0]);	//a
	
	* 这个函数多用来清空数组
		int arr[] = {1,2,3,4,5};
		memset(arr,0,sizeof(arr) * 5);
	

void *memcpy (void *dst, const void *src, size_t size);
	* 把src中的size个字节数据copy到dst中
	* 使用该函数,最好不要出现内存重叠(拷贝源和目的都是一个)
	* demo
		int src[] = {1,2,3};
		int dst[3];
		//把src的数据拷贝到dst中，拷贝dst大小个字节
		memcpy(dst,src,sizeof(dst));
		for(int x = 0 ;x < 3 ;x++){
			printf("%d\n",dst[x]);	//1 2 3
		}

void *memmove (void *dst, const void *src, size_t szie);
	* 同上,从src拷贝szie字节到dest,它的使用场景是'内存重叠cpy'的时候
	* 它能够保证'src在被覆盖之前将重叠区域的字节拷贝到目标区域中',但复制完成后src内容会被更改
	* 当目标区域与源区域没有重叠则和memcpy函数功能相同

int memcmp (const void src*, const void dst*, size_t size)
	* 用来比较俩内存块儿是否相等
	* 比较src和dst内存块开始的size个字节数据是否相同
	* 如果相同返回 0,如果 dst > src 返回 1,如果 dst 小于 src 返回 -1


char *strerror (int)
	* 根据int(异常码)返回其文字描述