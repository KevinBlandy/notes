------------------
Label				|
------------------
	* 标签,其实就是存放文字信息的一个容器
	* 用来显示信息
		label = Label(root,text='这个就是要显示的文字信息')
	
	* 构造函数
		 def __init__(self, master=None, cnf={}, **kw)
			* master
				* 指定容器
			*cnf
				* 用于控制实例的一些属性
	
	* demo
		from tkinter import Tk,Label
		root = Tk()
		lable = Label(root,text='哈哈',background='blue')
		lable.pack()
		root.mainloop()
	

------------------
Label-属性			|
------------------
	text
		* 显示的文本信息
	textvariable
		* 显示的文本信息,它的优先级比 text 属性高
		* 该值的对象应该是一个 tkvar 实例对象,直接调用该对象的api,可以修改文字(直接修改Label的 text属性也可以修改)
	background
		* 背景色
	fg
		* 文字的颜色
	image
		* 指定 PhotoImage 实例对象,用于显示图片信息
		* text,文字信息会被忽略掉
	justify
		* 设置多行时,文字的对齐方式: left, right, or center
	cpmpound
		* 设置图文混排
		* 可选值:LEFT, TOP, RIGHT, BOTTOM, CENTER(表示图片作为背景)
	font
		* 设置字体,该值可以是一个 tuple,可以一次性的设置 字体和大小
		* font=('微软雅黑',25)


------------------
Label-方法			|
------------------


