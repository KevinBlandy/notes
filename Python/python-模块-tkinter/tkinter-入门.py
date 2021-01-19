--------------------------------
tkiner - 入门					|
--------------------------------
	* 学习地址
		http://bbs.fishc.com/forum.php?mod=collection&action=view&ctid=155
	* tkiner Python的标准Tk GUI工具包的接口
	* 包结构
		tkiner
			|-test
				|__init__.py
			|-__init__.py
			|-__main__.py
			|-colorchooser.py
			|-commondialog.py
			|-constants.py
			|-dialog.py
			|-dnd.py
			|-filedialog.py
			|-font.py
			|-messagebox.py
			|-scrolledtext.py
			|-simpledialog.log
			|-tix.py
			|-ttk.py
	* 征途
		73

--------------------------------
tkiner - 组件					|
--------------------------------
	* 21个核心
		Toplevel
		Label
			* 文字显示框
		Button
			* 摁钮
		Canvas
		Checkbutton
		Entry
			* 文字输入框
		Frame
		LabelFrame
		Listbox
		Menu
		Menubutton
		Message
		OptionMenu
		PaneWindow
		Radiobutton
		Scale
		Scrollbar
		Spinbox
		Text
		Bitmap
		Image
		PhotoImage
		StringVar

	* 组件的使用
		1.各个组件都有相应的类,我们可以通过面向对象的方式去使用它们。
		2.这些组件的使用也很相似,在实例化这些组件的时候,第一个参数都是父窗口或者父组件,后面跟着的就是该组件的一些属性
		3.多个组件的位置控制方式也很相似,我们可以用 pack 方法来进行简单的布局
		4.组件也会有些方法是共用的
		5.他们都是通过初始化的时候传递配置关键字参数,也可以对实例对象使用类似dict的方式去设置属性 []
	
	* 公共属性

	* 公共方法

