import hashlib
from tkinter import *

md5digest = hashlib.md5()

root =  Tk()
root.title("md5")

entry = Entry(root)
lable = Label(root,text='哈哈')
button = Button(root,text='计算')


lable.grid(row=0,sticky=E+W)
entry.grid(row=1)
button.grid(row=2,column=0)

root.mainloop()