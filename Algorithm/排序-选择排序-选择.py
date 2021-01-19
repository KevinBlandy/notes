
# 选择排序
	* 核心的思想就是, 遍历数组, 获取到最小的
	* 每次遍历都把最小的元素移动到头部



# 代码实现

def minIndex(arr, start):
    length = len(arr)
    if length == start:
        # 遍历到了最后
        return None

    minVal = arr[start]
    index = start

    for i in range(start + 1, length):
        if arr[i] < minVal:
            minVal= arr[i]
            index = i

    return index

def selectsSrt(arr):
    length = len(arr)

    for i in range(length):
        index = minIndex(arr, i)
        if index:
            if arr[index] < arr[i]:
                temp = arr[i]
                arr[i] = arr[index]
                arr[index] = temp

arr = [1, 3, 6, 9, 8, 5, 7, 2, 0, 4]

selectsSrt(arr)

print(arr)
