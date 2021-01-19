

def indexedBinarySearch(collection, value):
    low = 0
    high = len(collection) - 1
    while(low <= high):
        mid = (low + high) // 2
        item = collection[mid]
        if item > value:
            high = mid - 1
        elif item < value:
            low = mid + 1
        else:
            return mid
    return -(low + 1)

def size(number):
    size = 0
    while(number > 0):
        number = number // 10
        size += 1
    return size

print(indexedBinarySearch([5,8,9],7))
