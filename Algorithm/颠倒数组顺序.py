
arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

length = len(arr)

for i in range(0,length // 2):
    temp = arr[i]
    arr[i] = arr[length - 1 - i]
    arr[length - i - 1] = temp

print(arr)

