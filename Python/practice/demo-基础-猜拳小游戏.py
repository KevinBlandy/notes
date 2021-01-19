# -*- coding: UTF-8 -*-
'''
Created on 2017年6月15日

@author: Kevin
'''

import random

def guess():
    num = random.randrange(100)
    print("已经随机生成一个数,你可以开始猜了")
    while True:
        result = int(input())
        if result > num :
            print("[%s] 大了"%(result))
        elif result < num:
            print("[%s] 小了"%(result))
        else:
            print("猜中啦，就是[%s]"%(result))
            break;

while True:
    order = input("输入[o]开始游戏,输入[q]退出 \n")
    if order == 'o':
        guess()
    elif order == 'q':
        print("再见,欢迎下次继续玩")
        break
    else:
        print("指令错误")
        