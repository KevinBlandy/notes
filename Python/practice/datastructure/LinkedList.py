

class Node (object):

    def __init__(self, value=None, next=None,):
        self.value = value
        self.next = next


class LinkendList(object):

    def __init__(self):
        self.__header = None
        self.__size = 0
        
    def add(self, value):
        if self.__header == None:
            self.__header = Node(value)
        else:
            node = self.__header
            while node.next != None:
                node = node.next
            node.next = Node(value, None)
        self.__size += 1
    
    @property
    def size(self):
        return self.__size

    def get(self, index):
        assert index > -1, "index must bigger than -1"
        
        node = self.__header
        if node == None:
            return None
        
        flag = 0
        while node != None:
            if flag == index:
                return node.value
            node = node.next
            flag += 1
        return None
    
    def index(self,value):
        node = self.__header
        if node == None:
            return -1
        flag = 0
        while node != None:
            if node.value == value:
                return flag
            node = node.next
            flag += 1
        return -1
    
    def __iter__(self):
        if self.__header == None:
            return None
        node = self.__header
        while node != None:
            yield node.value
            node = node.next

    
linkedList = LinkendList()
linkedList.add("Java")
linkedList.add("Python")
linkedList.add("C")
linkedList.add("Javascript")

for i, v in enumerate(linkedList):
    print(i, v)

print(linkedList.size)
print(linkedList.get(2))

