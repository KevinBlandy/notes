# -*- coding: UTF-8 -*-

goods = list(["Java","Python","Linux","PHP","JavaScript","Ruby","Perl","C","C++","GO"])
prices = list([25,56,54,52,36,48,55,22,69,54])

money = int(input("请输入资金 \n"));

balance = money

if(money < 1 or money > 99999):
    print("金额不符合标准")
    exit()

car = list()

while True:
    switch = input("请输入操作 : 1=购买商品,2=结账 : ")
    if(switch == '1'):
        print("请输入商品名称来选择商品,输入end,表示选择完毕")
        while True:
            for x in range(0,len(goods)):
                print("《%s》(%s)-%s$"%(goods[x],x,prices[x]),end="")
            print()
            select = input()
            if(select == 'end'):
                break
            if select not in goods:
                print("%s 商品不存在"%(select))
                continue
            price = prices[goods.index(select)]
            if(balance < price):
                print("余额不足 ,商品价格=%s,余额=%s"%(price,balance))
                continue;
            balance = balance - price
            car.append(select)
            print("购买成功:商品=%s,单价=%s,余额=%s"%(select,price,balance))
    elif(switch == '2'):
        print("本次购物,一共花费 %s$,还剩余 %s$"%((money - balance),balance))
        for item in car:
            print("商品:%s,单价:%s"%(item,prices[goods.index(item)]))
        print("再见")
        break;
    else:
        print("选择有误,%s 不是有效的指令"%(switch))
        continue;
    