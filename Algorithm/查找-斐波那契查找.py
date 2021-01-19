# 斐波那契查找
	* 斐波那契也叫做黄金分割法
	* 查找的数组，必须是有序的
	* 这都是些啥骚操作啊...学不动


# 实现
def fibonacci(max_length):
    ret_val = [i for i in range(max_length)]
    ret_val[0] = 1
    ret_val[1] = 1
    for i in range(2, max_length):
        ret_val[i] = ret_val[i - 1] + ret_val[i - 2]
    return ret_val


def array_copy(source, max_length):
    ret_val = []
    for i in range(max_length):
        if i >= len(source):
            ret_val.append(0)
        else:
            ret_val.append(source[i])
    return ret_val


def fibonacci_search(collection, key):
    low = 0
    high = len(collection) - 1
    k = 0
    mid = 0
    fibonacci_arr = fibonacci(20)
    while high > fibonacci_arr[k] - 1:
        k += 1
    temp = array_copy(collection, fibonacci_arr[k])

    for i in range(high + 1, len(temp)):
        temp[i] = collection[high]

    while low <= high:
        mid = low + fibonacci_arr[k - 1] - 1
        if key < temp[mid]:
            high = mid - 1
            k -= 1
        elif key > temp[mid]:
            low = mid + 1
            k -= 2
        else:
            if mid <= high:
                return mid
            else:
                return high

    return -1


arr = [1, 8, 10, 89, 1000, 1234]


retVal = fibonacci_search(arr, 89)

print(retVal)

