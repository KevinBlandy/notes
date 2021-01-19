# 插入排序
	* 核心思想就是把n个待排序的元素, 看成一个有序表和一个无序表
	* 开始排序的时候, 有序表中只包含一个元素, 无序表中包含 n - 1 个元素(就是随机选择一个元素作为有序的)
	* 然后每次从无序表中取出一个元素, 把它放入到有序表中合适的位置, 形成新的有序表
	* 从而完成排序



# 代码实现

def insertSort(arr):
    length = len(arr)
    for i in range(1, length):
        val = arr[i]
        # 有序列表的最后一个下标
        valIndex = i - 1

        while valIndex >= 0 and val < arr[valIndex]:
            arr[valIndex + 1] = arr[valIndex]
            valIndex -= 1

        if not valIndex + 1 == i:
            arr[valIndex + 1] = val


arr = [0, 5, 4, 8, 1, 2, 3, 7, 9, 6]

insertSort(arr)

print(arr)


	