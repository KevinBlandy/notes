# 插值查找
	* 要求被查找的记录是有序的，并且是依次递增的
	* 类似于二分查找，唯一不同的是插值查找每次从自适应 mid 处开始查找
		mid = low + (high - low) * (key - arr[low]) / (arr[high] - arr[low])
	
		key: 就是检索的值
		arr: 就是数组
		low: 低角标
		high: 高角标
		mid: 计算出的自适应mid
		



	
# 代码实现


def insert_value_search(collection, low, high, value):
    if low > high or value < collection[0] or value > arr[len(arr) - 1]:
        return -1

    mid = low + (high - low) * (value - arr[low]) // (arr[high] - arr[low])
    mid_value = arr[mid]

    if value > mid_value:
        return insert_value_search(collection, mid + 1, high, value)
    elif value < mid_value:
        return insert_value_search(collection, low, mid - 1, value)
    else:
        return mid


arr = [i for i in range(100)]

retVal = insert_value_search(arr, 0, len(arr) - 1, 1000)
print(retVal)