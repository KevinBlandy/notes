#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <windows.h>
//猜数字游戏
int main(void) {

    //设置种子为时间戳
    srand((unsigned int )time(NULL));

    //随机四个数
    int random_numbers[4];

    //输入的数字
    int input_number;

    //输入四个数
    int intput_numbers[4];

    for(int x = 0 ; x < 4 ; x++){
        random_numbers[x] = rand() % 10;
    }

    printf("已经生成了四个随机数\n");

    while(true){
        printf("请输入随机四位数\n");

        scanf("%d",&input_number);

        intput_numbers[0] = (input_number / 1000) % 10;
        intput_numbers[1] = (input_number / 100) % 10;
        intput_numbers[2] = (input_number / 10) % 10;
        intput_numbers[3] = input_number % 10;

        int flag = 0;

        for(int x = 0 ;x < 4 ; x++){
            if(intput_numbers[x] > random_numbers[x]){
                printf("%d 位大了\n", x + 1);
            }else if(intput_numbers[x] < random_numbers[x]){
                printf("%d 位小了\n", x + 1);
            }else{
                printf("%d 位猜对了\n", x + 1);
                flag ++;
            }
        }

        if(flag == 4){
            printf("恭喜你,全部猜对了。");
            Sleep(2000);
            break;
        }
    }
    
    return EXIT_SUCCESS;
}