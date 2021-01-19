------------------------
Scrollbar				|
------------------------
	* 滚动条组件
	* 该组件几乎不会单独的使用,都是配合其他的组件一起使用的
	* 构造函数
		 def __init__(self, master=None, cnf={}, **kw)
	* 使用滚动条的步骤
		1,创建滚动条实例
		2,可以使用滚动条的组件都会有 yscrollcommand/xscrollcommand 属性
			* 该属性的值应该是,滚动条实例的 set 方法
		3,通过滚动条实例的 config 方法,来对滚动条设置属性
			* command 关键字参数的值应该是,受滚动条控制的组件实例的 yview 实例方法

------------------------
Scrollbar-属性			|
------------------------

------------------------
Scrollbar-方法			|
------------------------
	set(first, last)

	config()
		
------------------------
Scrollbar-demo			|
------------------------

from tkinter import *

root = Tk()

# 1,创建滚动条实例,pack布局靠右y轴拉伸填充
scrollbar = Scrollbar(root)
scrollbar.pack(side=RIGHT,fill=Y)

# 2,可以使用滚动条的组件都会有 yscrollcommand/xscrollcommand 属性
# 该属性的值应该是,滚动条实例的 set 方法
listbox = Listbox(root,selectmode=EXTENDED,yscrollcommand=scrollbar.set)

for i in range(20):
    listbox.insert(END,i)

# 3,通过滚动条实例的 config 方法,来对滚动条设置属性,
# command 关键字参数的值应该是,受滚动条控制的组件实例的 yview 实例方法
scrollbar.config(command=listbox.yview)

listbox.pack()

root.mainloop()
