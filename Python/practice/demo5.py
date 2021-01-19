from functools import reduce
print("sum=%d"%(reduce(lambda v1,v2:v1 + v2, [i for i in range(1,int(input()) + 1)])))
'''
package com.zfx.basics.test;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        scanner.close();
        int sum = 0;
        for(int x = 1 ; x <= number ; x++) {
            sum += x;
        }
        System.out.println("sum=" + sum);
    }
}
'''
'''
#include<stdio.h>
#include<stdlib.h>
int main(void){
    int number;
    scanf("%d",&number);
    int sum = 0;
    for(int x = 1 ; x <= number ; x++){
        sum += x;
    }
    printf("sum=%d\n",sum);
    return EXIT_SUCCESS;
}
'''