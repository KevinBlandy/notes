
# 斐波那契数列,从第3项开始,每一项都等于前两项之和
# 1,1,2,3,5,8,13,21,34,55.....

# 使用递归实现

def fibonacci(value):
    if value <= 2:
        return 1
    return fibonacci(value - 2) + fibonacci(value - 1)


fibonacci_arr = [fibonacci(i) for i in range(1, 11)]

print(fibonacci_arr)

# 使用非递归实现

def fibonacci(max_length):

    ret_val = [i for i in range(max_length)]
    
    ret_val[0] = 1
    ret_val[1] = 1

    for i in range(2, max_length):
        ret_val[i] = ret_val[i - 1] + ret_val[i - 2]

    return ret_val


fibonacci_arr = fibonacci(15)

print(fibonacci_arr)

