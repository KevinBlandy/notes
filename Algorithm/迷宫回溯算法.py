


'''
二维数组表示迷宫 ，8行，7列 [8][7]
元素: 1 表示墙
元素: 0 表示可以穿过
'''
arr = []

for i in range(8):
    arr.append([])
    for x in range(7):
        arr[i].append(0)


# 初始化
for i in range(7):
    arr[0][i] = 1
    arr[7][i] = 1

for i in range(8):
    arr[i][0] = 1
    arr[i][6] = 1


# 设置墙
arr[3][1] = 1
arr[3][2] = 1


def setWay(map, i, j):
    '''
    :param map:         地图
    :param i:       开始横坐标
    :param j:         开始纵坐标
    :return:            找到返回 Ture 反之返回 False

    map[i][j] =
        0 : 没有走过
        1 ：墙
        2 ：可以通过
        3 ：已经走过，但是不通

    移动策略
        下 -> 右 -> 上 -> 左
    '''
    if map[6][5] == 2:
        return True
    else:
        if map[i][j] == 0: # 还没走过
            map[i][j] = 2 # 假定该点可以走通
            if setWay(map, i + 1, j): # 向下走
                return True
            elif setWay(map, i, j + 1 ): # 向右走
                return True
            elif setWay(map, i - 1, j): # 向上走
                return True
            elif setWay(map, i, j - 1): # 向上走
                return True
            else:
                map[i][j] = 3   # 死路
                return False
        else:
            return False

setWay(arr, 1, 1)


for i in arr:
    print(i)
