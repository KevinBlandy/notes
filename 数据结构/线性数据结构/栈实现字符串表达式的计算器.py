# 使用实现的计算器

def priority(operation):
    if operation == '*' or operation == '/':
        return 1
    elif operation == '+' or operation == '-':
        return 0
    else:
        return -1


# 判断是否是一个运算符
def isOperation(operation):
    return operation in ['+', '-', '*', '/']


def calculation(val1, val2, operation):
    retVal = 0
    if operation == '+':
        retVal = val1 + val2
    elif operation == '-':
        # 出栈顺序
        retVal = val2 - val1
    elif operation == '*':
        retVal = val1 * val2
    elif operation == '/':
        # 出栈顺序
        retVal = val2 / val1
    else:
        raise Exception("unknown operation %s" % operation)
    return retVal


class Stack(object):
    def __init__(self):
        self.queue = []

    def push(self, item):
        self.queue.insert(0, item)

    def pop(self):
        if len(self.queue) == 0:
            raise Exception("empty stack")
        return self.queue.pop(0)

    def peek(self):
        if len(self.queue) == 0:
            raise Exception("empty stack")
        return self.queue[0]

    @property
    def size(self):
        return len(self.queue)

    @property
    def empty(self):
        return self.size == 0

    def foreach(self, action):
        for i in self.queue:
            action(i)


# 表达式
express = '30+500*2'

# 数字栈
numberStack = Stack()
# 运算符号栈
operationStack = Stack()

# 临时容器
temp = []

for i, v in enumerate(express):
    if isOperation(v):
        if operationStack.empty:
           # 符号栈为空，直接入栈
           operationStack.push(v)
        else:
            # 当前操作符的优先级小余等于栈顶操作符的优先级
            if priority(v) <= priority(operationStack.peek()):
               val1 = numberStack.pop()
               val2 = numberStack.pop()
               operation = operationStack.pop()
               retVal = calculation(val1, val2, operation)
               # 结果入栈
               numberStack.push(retVal)
               # 当前操作符入栈
               operationStack.push(v)
            else:
               operationStack.push(v)
    else:
        temp.append(v)
        if i == len(express) - 1:
            # 最后一个元素，直接入栈
            numberStack.push(int(''.join(temp)))
        else:
            # 尝试访问下一个元素
            nextVal = express[i + 1]
            if isOperation(nextVal):
                # 下一个元素是符号，数据入栈
                numberStack.push(int(''.join(temp)))
                # 清空临时容器
                temp.clear()



while True:
    if operationStack.empty:
        break
    val1 = numberStack.pop()
    val2 = numberStack.pop()
    operation = operationStack.pop()
    retVal = calculation(val1, val2, operation)
    # 结果入栈
    numberStack.push(retVal)

# 数字栈中最后一个元素，就是最终的结果
result = numberStack.pop()
print(result)







