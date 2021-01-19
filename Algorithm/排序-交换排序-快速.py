# 快速排序
	* 快速排序是对冒泡排序的一种改进

	* 通过一趟排序，把要排序的数据分割成独立的两部分
	* 其中一部分的所有数据都比另外一部分的所有数据都要小
	* 然后按照此方法对这两部分数据分别进行快速排序
	* 整个排序过程可以递归进行，以此达到整个数据变成有序序列


# 代码实现

def quickSort(arr, left, right):
    l = left
    r = right
    mid = arr[(left + right) // 2]
    while l < r:
        while arr[l] < mid:
            l += 1
        while arr[r] > mid:
            r -= 1
        if l >= r:
            break

        temp = arr[l]
        arr[l] = arr[r]
        arr[r] = temp

        if arr[l] == mid:
            r -= 1

        if arr[r] == mid:
            l += 1

    if l == r:
        l += 1
        r -= 1

    # 左递归
    if left < r:
        quickSort(arr, left, r)
    # 右递归
    if right > l:
        quickSort(arr, l, right)


arr = [5, 4, 2, 9, 8, 7, 6, 1, 2, 3, 0]
quickSort(arr, 0, len(arr) - 1)
print(arr)