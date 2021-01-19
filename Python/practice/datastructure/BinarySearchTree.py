

class Node():

    def __init__(self, value):
        self.value = value;
        self.left = None
        self.right = None
    

class BinarySearchTree():

    def __init__(self, comparator):
        self.__root = None
        self.__size = 0
        self.__comparator = comparator
    
    @property
    def size(self):
        return self.__size
    
    @property
    def empty(self):
        return self.__size == 0
    
    def __add(self, node, value):
        if(node == None):
            self.__size += 1
            return Node(value)
        result = self.__comparator(value, node.value)
        if(result < 0):
            node.left = self.__add(node.left, value)
        elif(result > 0):
            node.right = self.__add(node.right, value)
        return node

    def add(self, value):
        self.__root = self.__add(self.__root, value);
    
    def __contains(self, node, value):
        if node == None:
            return False
        result = self.__comparator(value, node.value)
        if(result < 0):
            return self.__contains(node.left, value)
        elif(result > 0):
            return self.__contains(node.right, value)
        return True

    def contains(self, value):
        return self.__contains(self.__root, value)
    
    def foreach(self, consumer):
        assert not self.empty, 'Empty Map'
        stack = []
        stack.append(self.__root)
        while len(stack) > 0:
            node = stack.pop()
            consumer(node.value)
            if node.left != None:
                stack.append(node.left)
            if node.right != None:
                stack.append(node.right)
    
    def forEach(self, consumer):
        assert not self.empty, 'Empty Map'
        queue = []
        queue.insert(0, self.__root);
        while len(queue) > 0:
            node = queue.pop()
            consumer(node.value)
            if node.left != None:
                queue.insert(0, node.left)
            if node.right != None:
                queue.insert(0, node.right) 
    
    def __min(self, node):
        if node.left == None:
            return node
        return self.__min(node.left)
    
    def min(self):
        assert not self.empty, 'Empty Map'
        return self.__min(self.__root).value
    
    def __max(self, node):
        if node.right == None:
            return node
        return self.__max(node.right)
    
    def max(self):
        assert not self.empty, 'Empty Map'
        return self.__max(self.__root).value
    
    def __removeMin(self, node):
        if node.left == None:
            rightNode = node.right
            node.right = None
            self.__size -= 1
            return rightNode
        node.left = self.__removeMin(node.left)
        return node

    def removeMin(self):
        assert not self.empty, 'Empty Map'
        minValue = self.min()
        self.__root = self.__removeMin(self.__root)
        return minValue
    
    def __removeMax(self, node):
        if node.right == None:
            leftNode = node.left
            self.left = None
            self.__size -= 1
            return leftNode
            
        node.right = self.__removeMax(node.right)
        return node

    def removeMax(self):
        assert not self.empty, 'Empty Map'
        value = self.max()
        self.__root = self.__removeMax(self.__root)
        return value
    
    def __remove(self, node, value):
        if node == None:
            return node
        result = self.__comparator(value, node.value)
        if(result < 0):
            node.left = self.__remove(node.left, value)
            return node
        elif(result > 0):
            node.right = self.__remove(node.right, value)
            return node
        else:
            if node.left == None:
                rightNode = node.right
                node.right = None
                self.__size -= 1
                return rightNode
            
            if node.right == None:
                leftNode = node.left
                node.left = None
                self.__size -= 1
                return leftNode
            
            rightMinNode = self.__min(node.right)
            rightMinNode.right = self.__removeMin(node.right)
            rightMinNode.left = node.left
            
            node.left = node.left = None
            return rightMinNode
        
    def remove(self, value):
        self.__root = self.__remove(self.__root, value)

    
def comparator(v1 , v2):
    if v1 > v2:
        return 1
    elif v1 < v2:
        return -1
    return 0


tree = BinarySearchTree(comparator)
for i in [5, 3, 6, 8, 4, 2]:
    tree.add(i)

tree.foreach(lambda x:print(x))
print('size=%d' % (tree.size))
    
