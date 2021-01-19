'''
    计算器
'''
from tkinter import *

root = Tk()

root.title('简易计算器')

# 主屏幕当前值
current_value = StringVar()

def on_num_key_donw():
    pass

# 摁钮事件闭包
def key_press_wapper(value):
    def key_press ():
        print(value)
    return key_press

# 摁钮工厂
def button_factory(parent,**options):
    button = Button(parent,**options)
    return button

# 数字摁钮
num_buttons = {

}

# 初始化 0 - 9 个摁钮
for i in range(10):
    num_buttons[i] = button_factory(root,text=i,command=key_press_wapper(i))

# 初始化操作摁钮
operations_buttons = {
    'c':button_factory(root,text='c',command=key_press_wapper('c')),
    '+/-':button_factory(root,text='+/-',command=key_press_wapper('+/-')),
    '%':button_factory(root,text='%',command=key_press_wapper('%')),
    '÷':button_factory(root,text='÷',command=key_press_wapper('÷')),
    '×':button_factory(root,text='×',command=key_press_wapper('×')),
    '-':button_factory(root,text='-',command=key_press_wapper('-')),
    '+':button_factory(root,text='+',command=key_press_wapper('+')),
    '=':button_factory(root,text='=',command=key_press_wapper('=')),
    '.':button_factory(root,text='.',command=key_press_wapper('.'))
}

# 初始化主屏幕显示lable
label = Label(root)


for i,v in num_buttons.items():
    v.pack()
for i,v in operations_buttons.items():
    v.pack()

root.mainloop()

