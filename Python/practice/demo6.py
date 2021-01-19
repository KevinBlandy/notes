import random
import time

random_numbers = []
input_number = None

for i in range(4):
    random_numbers.append(random.randrange(10))
    
print("已经生成四个随机数")

while True:
    
    input_number = int(input("输入四位数字:\n"));
    
    inputs_numbers = []
    inputs_numbers.append((input_number // 1000) % 10)
    inputs_numbers.append((input_number // 100) % 10)
    inputs_numbers.append((input_number // 10) % 10)
    inputs_numbers.append(input_number % 10)
    
    flag = 0
    
    for i,v in enumerate(inputs_numbers):
        if v > random_numbers[i]:
            print("[%d] 大了"%(i + 1))
        elif v < random_numbers[i]:
            print("[%d] 小了"%(i + 1))
        else:
            print("[%d] 对了"%(i + 1))
            flag += 1
            
    if flag == 4:
        print("全部猜对了,2s后退出")
        time.sleep(2)
        break
'''c-lang
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <windows.h>
#define SIZE 4
int main(void){
    //随机种子
    srand((unsigned int)time(NULL));
    int random_numbers[SIZE];
    int inputs_numbers[SIZE];
    int input_number;
    //获取四个随机数
    for(int x = 0 ;x < SIZE ;x ++){
        random_numbers[x] = rand() % 10;
    }
    printf("已经生成四个随机数\n");
    while(true){
        printf("输入四个数字:\n");
        scanf("%d",&input_number);
        inputs_numbers[0] = (input_number / 1000) % 10;
        inputs_numbers[1] = (input_number / 100) % 10;
        inputs_numbers[2] = (input_number / 10) % 10;
        inputs_numbers[3] = input_number % 10;
        int flag = 0;
        for(int x = 0 ;x < SIZE ; x++){
            if(inputs_numbers[x] > random_numbers[x]){
                printf("[%d] 位大了\n",x+1);
            }else if(inputs_numbers[x] < random_numbers[x]){
                printf("[%d] 位小了\n",x+1);
            }else{
                printf("[%d] 位对了\n",x+1);
                flag ++;
            }
        }
        if(flag == 4){
            printf("全部猜对了,2s后退出\n");
            Sleep(2000);
            break;
        }
    }
    return EXIT_SUCCESS;
}
'''