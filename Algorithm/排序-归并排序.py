# 归并排序
	* Merge Sort, 是利用归并的思想实现的排序方法
	
	* 采用经典的分治策略，把问题分解成小问题
	* 递归求解，最后把内容整合到一起
	

# 代码实现


def merge(arr, left, mid, right, temp):
    '''
    :param arr:     原始需要排序的数组
    :param left:    左边初始索引
    :param mid:     中间索引
    :param right:   右边索引
    :param temp:    中转的数组
    :return:
    '''

    i = left    # 左边有序序列的初始索引
    j = mid + 1 # 右边有序序列的初索引
    t = 0       # 指向当前temp的索引

    while i <= mid and j <= right:
        if arr[i] <= arr[j]:
            temp[t] = arr[i]
            t += 1
            i += 1
        else:
            temp[t] = arr[j]
            t += 1
            j += 1

    while i <= mid:
        temp[t] = arr[i]
        t += 1
        i += 1

    while j <= right:
        temp[t] = arr[j]
        t += 1
        j += 1

    t = 0
    tempLeft = left
    while tempLeft <= right:
        arr[tempLeft] = temp[t]
        t += 1
        tempLeft += 1


def mergeSort(arr, left, right, temp):
    if left < right:
        mid = (left + right) // 2
        # 左递归分解
        mergeSort(arr, left, mid, temp)
        # 右递归分解
        mergeSort(arr, mid + 1, right, temp)
        # 合并
        merge(arr, left, mid, right, temp)



arr = [8, 4, 5, 7, 1, 3, 6, 2]
temp = [0 for i in range(len(arr))]

mergeSort(arr, 0, len(arr) - 1, temp)

print(arr)

