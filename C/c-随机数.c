--------------------------
随机数					  |
--------------------------
		# 获取随机数
		#include<stdio.h>
		#include<stdlib.h>
		#include<time.h>

		int main(void) {
			//设置种子,使用当前时间,保证每次调用获取的随机数都不一致
			srand((unsigned int)time(NULL));
			//产生随机数
			for(int i = 0 ; i < 10 ; i++){
				int rand_number = rand();
				printf("获取随机数:%d\n",rand_number);
			}
			return EXIT_SUCCESS;
		}

