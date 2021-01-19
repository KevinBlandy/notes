
def binarySearch(arr,obj):
    
    start = 0;
    end = len(arr);
    
    while start <= end :
        mid = start + ((end - start) // 2)
        if arr[mid] == obj:
            return mid
        elif obj > arr[mid]:
            start = mid + 1
        else:
            end = mid - 1
            
    return -1

arr = [1,2,3,4,5,6,7,8,9]


index = binarySearch(arr, 4)

print(index)