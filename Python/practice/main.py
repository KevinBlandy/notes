'''
    非排序集合,找出第二大的元素
'''
def second_biggest(arr):
    max = 0;
    second = 0;
    for i in arr:
        if i > max:
            second = max
            max = i
        elif i > second:
            second = i
    return second

print(second_biggest([1,5,47,5,3,6,96]))

