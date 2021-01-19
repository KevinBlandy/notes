
# collection 必须为升序排序的有序数组,不存在返回负数

# 使用循环实现
def binary_search(collection, value):
    low = 0
    high = len(collection) - 1
    while low <= high:
        mid = (low + high) // 2
        item = collection[mid]
        if item > value:
            high = mid - 1
        elif item < value:
            low = mid + 1
        else:
            return mid
    return -1

# 使用递归实现
def binary_search(collection, left, right, value):
    if left > right:
        return -1
    mid = left + (right - left) // 2
    mid_val = collection[mid]
    if value > mid_val:
        return binary_search(collection, mid + 1, right, value)
    elif value < mid_val:
        return binary_search(collection,left, mid - 1, value)
    else:
        return mid


arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

ret = binary_search(arr, 0, len(arr) - 1, 15)

print(ret)

# 返回具有相同元素数值中，指定值的所有下标

def binary_search(collection, left, right, value):
    if left > right:
        return []
    mid = left + (right - left) // 2
    mid_val = collection[mid]
    if value > mid_val:
        return binary_search(collection, mid + 1, right, value)
    elif value < mid_val:
        return binary_search(collection,left, mid - 1, value)
    else:
        ret_val = []
        temp = mid - 1
        while True:
            if temp < 0 or not arr[temp] == value:
                break
            ret_val.append(temp)
            temp -= 1

        ret_val.append(mid)

        temp = mid + 1
        while True:
            if temp > len(collection) - 1 or not arr[temp] == value:
                break
            ret_val.append(temp)
            temp += 1

        return ret_val


arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9]

ret = binary_search(arr, 0, len(arr) - 1, 9)

print(ret) # [9, 10]





# 获取中位数的下标,如果 start 和 end 过大的话,  在某些语言中可能会导致溢出, 需要换一种计算方式
	mid = low + (high - low) // 2
	

# 如果支持无符号位移运算符号的话(Java), 更简单
	 mid = (low + high) >>> 1;