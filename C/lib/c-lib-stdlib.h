--------------------------------
stdlib							|
--------------------------------

FILE

NULL
	* null 

EXIT_SUCCESS = 0
	* 成功

EXIT_FAILURE = 1
	* 失败
	
exit()
	* 程序退出,参数为 int 值,表示程序的退出状态

system()
	* 执行系统命令
	* 返回值 int,该值就是程序的返回值,在不同的平台不一样

char *getenv (const char *);
	* 获取系统环境变量,如果不存在返回NULL
		char *p = getenv("JAVA_HOME");
		printf("%s",p);	//C:\Program Files\Java\jdk1.8.0_171

srand(unsigned int)
	* 设置随机数的种子

rand()
	* 该函数返回一个随机数 int
	* 如果种子一样,那么多次调用的结果也一样

atoi(const char *nptr);
	* 把字符串转换为整形,然后返回
	* 会跳过前面的空格,直到遇到数字或者正负号才开始转换,遇到非数字字符串或者\0就结束转换,并且返回转换结果

stof(const char *nptr);
	* 同上
	* 把字符串转换为float
	
atol(const char *nptr);
	* 同上
	* 把字符串转换为long

void *malloc (size_t)
	* 参数表示申请的空间是多少
	* 如果申请成功,返回的数据就是申请的堆空间的首元素地址(指针),申请失败,返回 NULL
	* 申请的堆空间,如果程序没有结束,那么不会释放,需要程序手动的释放
	* demo
		int *p = (int *) malloc(sizeof(int));
		*p = 15;
		printf("%d",p[0]);		//15

void free (void *);
	* 释放堆空间的内存,交还给操作系统
	* 同一块儿的堆内存,只能执行一次释放操作
	* 释放掉内存后,执行该内存的指针就是野指针了

fclose()
fopen()
freopen()
fflush()
atexit (void (*)(void));
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

void qsort(void *arr, size_t length, size_t size, int (*)(const void *, const void *));
	* 快速排序的实现
	* arr 待排序数组的首元素
	* lenngth 数组的长度
	* size 数组元素的大小
	*  int (*)(const void *v1, const void *v2) 排序方法的指针
		- 如果v1 > v2 返回整数
		- 如果v1 < v2 返回负数
		- 如果v1 = v2 返回0
	* 排序
		int compareTo(const void *v1, const void *v2) {
			//从大到小排序
			return *((int *) v1) < *((int *) v2);
		}

		int main(int argc, char **argv) {

			int arr[] = { 1, 5, 4, 7, 8, 2, 3, 9, 6 };

			size_t arrLength = sizeof(arr) / sizeof(arr[0]);
			size_t itemSize = sizeof(arr[0]);

			qsort(arr, arrLength, itemSize, &compareTo);

			for (int x = 0; x < arrLength; x++) {
				printf("%d\n", arr[x]);
			}

			return EXIT_SUCCESS;
		}

void *bsearch(const void *key, const void *base, size_t nitems, size_t size, int (*compar)(const void *, const void *))
	* 二分查找的实现,如果找到元素,则返回该元素的指针,否则返回空指针
	* key 查找元素的指针
	* base 数组
	* nitems 数组元素个数
	* size 数组元素的字节大小
	* compar 指针函数,用于对比元素
		int comparable(const void *arg1, const void *arg2) {
			int v1 = *(int *) arg1;
			int v2 = *(int *) arg2;
			if(v1 > v2){
				return 1;
			}else if(v1 < v2){
				return -1;
			}else{
				return 0;
			}
		}
	* demo
		int comparable(const void *arg1, const void *arg2) {
			int v1 = *(int *) arg1;
			int v2 = *(int *) arg2;
			if(v1 > v2){
				return 1;
			}else if(v1 < v2){
				return -1;
			}else{
				return 0;
			}
		}

		int main(int argc, char **argv) {
			int arr[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			size_t itemSize = sizeof(arr[0]);
			size_t arrLeng = sizeof(arr) / itemSize;
			int number = 6;
			int * index = (int *) bsearch(&number, arr, arrLeng, itemSize, &comparable);
			printf("index = %p\n", index);		//index = 0028FF24
			return EXIT_SUCCESS;
		}

	* 结构体的指针数组
		int compareTo(const void *v1, const void *v2) {
			//该指针实际上是一个二级指针,因为指针数组里面的元素每个都是指针
			struct User **user1 = (struct User **) v1;
			struct User **user2 = (struct User **) v2;
			int height1 = (*user1)->height;
			int height2 = (*user2)->height;
			return height1 > height2;
		}