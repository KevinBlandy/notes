----------------------------
oop	- 反射					|
----------------------------
	* 说来说去就四个方法
		getattr(obj,attr)
		getattr(obj,attr,default)
		hasattr(obj,attr)
		setattr(obj,attr,value)
		delattr(obj,attr)
	
	* 这些方法不可仅仅可以反射操作对象,也可以操作模块
		
	

----------------------------
oop	- Demo						|
----------------------------

class Foo():
    def __init__(self):
        self.name = 'Kevin'
        self.age = 23
    
    def say(self):
        print("你好,我是%s"%(self.name))
    

foo = Foo()
print(hasattr(foo, 'say'))  # true
print(hasattr(foo, 'name')) # true
say = getattr(foo, 'say')
say()                       # 你好,我是Kevin
none = getattr(foo, 'none','默认值')
print(none)                 # 默认值

def func(var):
    print(var)
    
setattr(foo, 'getX', func)
foo.getX('动态添加的方法')     # 动态添加的方法 # 默认值