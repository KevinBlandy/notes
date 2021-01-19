
class Filter(object):
    # 下一个要执行的对象
    def __init__(self,name):
        self.name = name
        
    
    def set(self,next):
        self.next = next
    
    def work(self):
        if hasattr(self, "next"):
            print('%s 执行了'%(self.name))
            self.next.work()

a = Filter('A')
b = Filter('B')
c = Filter('C')
d = Filter('D')
e = Filter('E')

a.set(b)
b.set(c)
c.set(d)
d.set(e)

a.work()