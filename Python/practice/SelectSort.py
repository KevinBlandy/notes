

def getMinIndex(arr, position):
    
    length = len(arr)
    
    min = arr[position]
    minIndex = position
    
    position += 1
    
    while(position < length):
        if arr[position] < min:
            min = arr[position]
            minIndex = position
        position += 1
    return minIndex


def selectSort(arr):
    for i, v in enumerate(arr):
        minIndex = getMinIndex(arr, i)
        temp = arr[i]
        arr[i] = arr[minIndex]
        arr[minIndex] = temp


arr = [4, 5, 7, 8, 9, 6, 1]
selectSort(arr)
print(arr)
        
