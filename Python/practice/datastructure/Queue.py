

class Node(object):

    def __init__(self, value):
        self.value = value;
        self.next = None


class Queue(object):

    def __init__(self):
        self.size = 0
        self.head = None    # 头指针
        self.tail = None    # 尾指针
    
    def equeue(self, value):
        if self.head == None:
            self.head = Node(value)
            self.tail = self.head
        else:
            self.tail.next = Node(value)
            self.tail = self.tail.next
        self.size += 1
    
    def dequeue(self):
        assert self.size > 0 , 'Empty Queue'
        retValue = self.head.value
        self.head = self.head.next
        if self.head == None:
            self.tail = None
        self.size -= 1
        return retValue

    def __str__(self, *args, **kwargs):
        retValue = "head=[%s] ["%(self.head.value if self.head != None else None)
        
        node = self.head
        size = 0
        while node != None:
            retValue += "%s"%(node.value)
            if (size + 1) != self.size:
                retValue += ","
            node = node.next
            size += 1
       
        retValue += "] tail=[%s] size=[%s]"%(self.tail.value if self.head != None else None,self.size)
        return retValue
    

queue = Queue()

print(queue)

for i in range(100):
    queue.equeue(i)

print(queue)

for i in range(queue.size):
    print(queue.dequeue())

print(queue)

