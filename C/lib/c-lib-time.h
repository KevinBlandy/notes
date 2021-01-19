------------------------
time					|
------------------------

CLK_TCK
	* 机器时钟每秒所走的时钟打点数(一秒有多少个clock_t)
	* printf("%d",CLK_TCK);  //1000

time()
	* 返回当前系统时间(秒数),四个字节

clock_t  clock (void);
	* 返回程序从开始执行到执行该函数的耗费时间
	* 这个时间单位是: clock tick 即 "时钟打点"
	* 可以理解为返回的是毫秒
	* Demo
		//记录开始和结束
		clock_t start,stop;
		//运行时间,单位为秒
		double duration;

		int main(int argc, char **argv) {
			start = clock();
			//TODO 执行目标函数函数
			stop = clock();
			duration = ((double)(stop - start)) / CLK_TCK;
			printf("耗费：%f秒\n",duration);
			return EXIT_SUCCESS;
		}
