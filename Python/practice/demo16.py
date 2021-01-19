

def test(str):
    length = len(str)
    if length % 2 != 0:
        return False
    
    mid = length // 2
    
    leftPoint = mid - 1
    rightPoint = mid
    
    left = []
    while leftPoint >= 0:
        left.insert(0, str[leftPoint])
        leftPoint -= 1
    
    right = []
    while rightPoint < length:
        right.insert(0, str[rightPoint])
        rightPoint += 1
    
    for i, v in enumerate(left):
        if (v == '('):
            if right[i] != ')':
                return False
        elif (v == '{'):
            if right[i] != '}':
                return False
        elif (v == '['):
            if right[i] != ']':
                return False
        else:
            return False
    return True


def test1(str):
    arr = []
    for i in str:
        if i == '[' or i == '(' or i == '{':
            arr.insert(0, i);
        else:
            item = arr.pop(0)
            if item == '[' and i != ']':
                return False
            elif item == '(' and i != ')':
                return False
            elif item == '{' and i != '}':
                return False
    return True if len(arr) == 0 else False


s = '{[([{[([{[([{[([{[([[]])]}])]}])]}])]}])]}'
print(test(s))
print(test1(s))
