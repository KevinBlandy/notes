----------------------
内存分区			  |
----------------------
	# GCC编译过程
		1,预处理
			* 宏定义展开,头文件展开,条件编译,注释删除
		2,编译
			* 语法检查,将预处理后文件编程成汇编文件
		3,汇编
			* 将汇编文件生成目标文件(二进制文件)
		4,链接
			* C语言写的程序需要依赖各种库
			* 编译之后还需要把库链接到最终可执行程序中去
			* 在 linux 里面使用命令: 'ldd [可执行文件]'  ,可以列出来该程序运行需要的动态库
			* 在 windows 下可以使用 Dependency Walker 去列出可执行文件运行需要的动态库
	
	# 在Linux下查看编译后文件的内存分区
		size Demo.out

		text    data     bss     dec     hex filename
		1224     548       4    1776     6f0 Demo.out

		text
			* 代码区,只读,函数
			* 加载的可执行代码段,所有可执行代码都加载到代码区,这块内存是只读的,不能在运行期间修改

		data
			* 初始化的数据:全局变量,静态变量,文字常量区(只读)
			* 加载的是可执行文件数据段
			* 存储于数据段(全局初始化,静态初始化数据,文字常量(只读))的数据的生命周期为整个程序运行过程

		bss
			* 没有初始化的数据:全局变量,静态变量
			* 加的是可执行文件BSS段,位置可以分开也可以紧靠数据段
			* 存储于数据段的数据(全局未初始化,静态未初始化数据)的生命周期为整个程序过程

		* 在程序执行之前,有几个内存分区已经确定,但是没有加载
			* text,data,bss

		* 运行程序,加载内存,首先会根据前面确定的内存分区先加载
			* text,data,bss
			* 还会额外加载两个区:一个栈,一个堆
		
		* 栈区(stack)
			* 普通局部变量,自动管理内存
			* 先进后出
		
		* 堆区(heap)
			* 手动申请空间,需要手动释放,整个程序结束,系统也会自动的回收
			* 位于BSS区和栈区之间
		
	
	# 栈越界
		* 递归,耗光了栈内存的空间的
		* 查看系统(linux)栈空间的大小
			ulimit -a

			core file size          (blocks, -c) 0
			data seg size           (kbytes, -d) unlimited
			scheduling priority             (-e) 0
			file size               (blocks, -f) unlimited
			pending signals                 (-i) 15088
			max locked memory       (kbytes, -l) 64
			max memory size         (kbytes, -m) unlimited
			open files                      (-n) 65535
				* 最多只能打开 65535个文件
			pipe size            (512 bytes, -p) 8
			POSIX message queues     (bytes, -q) 819200
			real-time priority              (-r) 0
			stack size              (kbytes, -s) 8192
				* 栈的大小是 8192 字节,就是 1024 KB ,也就是 1 MB
			cpu time               (seconds, -t) unlimited
			max user processes              (-u) 15088
				* 一个用户最大的进程数量
			virtual memory          (kbytes, -v) unlimited
			file locks                      (-x) unlimited
		
		* 栈的生长方向以及存放方向
			int a;
			int b;
			int arr[2] = { };
			printf("%p\n", &a);			//0028FF3C
			printf("%p\n", &b);			//0028FF38
			printf("%p\n", &arr[0]);	//0028FF30
			printf("%p\n", &arr[1]);	//0028FF34

			/* 栈内存
				 -----------
				 a			0028FF3C
				 -----------	
				 b			0028FF38
				 -----------
				 arr[1]		0028FF34
				 -----------
				 arr[0]		0028FF30
				 -----------
			 */

			
-------------------------------
操作内的函数					|
-------------------------------
	# 这些函数都是在 <string.h> 库中

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
	

-------------------------------
堆区内存的操作					|
-------------------------------
	# 申请一个堆空间
		void *malloc (size_t)
		* 参数表示申请的空间是多少(就算是0也能申请成功,但是操作该内存会越界)
		* 如果申请成功,返回的数据就是申请的堆空间的首元素地址(指针),申请失败,返回 NULL
		* 申请的堆空间,如果程序没有结束,那么不会释放,需要程序手动的释放
		* demo
			int *p = (int *) malloc(sizeof(int));
			*p = 15;
			printf("%d",p[0]);		//15

		void *calloc(int num, int size);
		* 在内存中动态地分配 num 个长度为 size 的连续空间,并将每一个字节都初始化为 0
		* 所以它的结果是分配了 num*size 个字节长度的内存空间,并且每个字节的值都是0
		* 与 malloc() 除了参数不同,它会出申请的内存进行初始化(设置为0)操作,malloc不会,在申请后可能内存存在遗留数据
	
	# 释放堆空间内存
		void free (void *);
		* 释放堆空间的内存,交还给操作系统
		* 同一块儿的堆内存,只能执行一次释放操作
		* 释放掉内存后,执行该内存的指针就是野指针了
		* 标准的释放代码,要释放内存,并且
			if(p != NULL){
				free(p);
				p = NULL;
			}
	

	# 重新分配内存
		void *realloc(void *address, int newsize);
		* 该函数重新分配内存,把内存扩展到 newsize
		* demo
			char *p = (char *) calloc(1, 1);
			*p = 'a';

			char *p1 = (char *)realloc(p,2);
			printf("%p %p\n", p, p1);			//009F2128 009F2128

			*(p1 + 1) = 'b';
			printf("%c %c", *p, *(p + 1));		//a b


	# 堆空间的越界
		* 编译器不会检查堆空间的越界,开发的时候需要注意

		char *p = (char *)malloc(0);
		*p = 'a';
		printf("%c",*p);		//a
	
	
	# 函数返回堆区的指针
		void *foo(int size){
			return malloc(size);
		}

		int main() {
			int *p = (int *)foo(4);
			*p = 4;
			printf("%d",*p);	//4
			return EXIT_SUCCESS;
		}
		* 允许函数返回堆内存的指针,只要是没有被回收,该内存都可以被操作
	
	

	
	

	







		


