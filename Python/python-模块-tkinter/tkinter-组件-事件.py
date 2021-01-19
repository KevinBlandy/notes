------------------------
组键级别事件绑定		|
------------------------
	1,在组件实例创建的时候,传递关键字参数 command,来指向要绑定的时间函数
		
		btn = Button(root,command = lambda : print('摁钮被点击'))
		* 这种方式,仅有部分组件支持(只有部分组件实例化的时候具备 command 关键字)
		* 该方式,不会传递任何的参数给回调函数
		
	2,使用 bind 方法,该方法是 Misc 这个类的一个方法
		* 'bind 函数是定义在 Misc 类里面的,也就是说，这个bind 可以被绝大多数组件类所使用'
		* 这种方式绑定的事件,会传递一个参数(必须参数)给事件方法,就是当前的时间对象:<class 'tkinter.Event'>
		* 该方法接收三个参数
			1,监听的事件类型,它采用的描述方式是这样的(字符串): <MODIFIER-MODIFIER-TYPE-DETAIL> 
				* MODIFIER 即修饰符,它的全部取值如下
					Control, 
					Mod2,
					M2,
					Shift, Mod3, M3, Lock, Mod4, M4, Button1, B1,
					Mod5, M5 Button2, B2, Meta, M, Button3,B3, Alt,
					Button4, B4, Double, Button5, B5 Triple， Mod1, M1

				* TYPE 表示类型,它的全部取值如下
					Activate,
					Enter, Map, ButtonPress, Button, Expose, Motion,
					ButtonRelease， FocusIn, MouseWheel, Circulate,
					FocusOut, Property, Colormap, Gravity Reparent,
					Configure, KeyPress, Key, Unmap, Deactivate,
					KeyRelease Visibility, Destroy,Leave
				
				* DETAIL 表示细节,其实也就是对第二个参数的一些辅助说明
				* 常用的一些事件参数表示
					'<Button-1>'
						* 左键单击
					'<Button-3>'
						* 右键被单击
					'<Button-2>'
						* 鼠标中键被单击
					'<KeyPress-A>' - '<KeyPress-Z>'							
						* 对应的键被摁下,注意,分区大小写:'<KeyPress-a>' - '<KeyPress-z>'	
					'<Control-V>'
						* 表示按下的是 Ctrl 和 V 键, V 可以换成其他键位
					'<F1>'
						* 表示按下的是 F1 键
						* 对于 Fn 系列的,都可以随便换
					
			2,就是用于执行事件的函数,第一个参数就是事件对象:<class 'tkinter.Event'>

------------------------
全局事件绑定			|
------------------------
	* 全程序级别的绑定,使用 bind_all,它的参数类型和 bind 一样
	* 它通常用于全局的快捷键,比如 F1 通常是用来打开帮助文档

------------------------
类别组件事件绑定		|
------------------------
	* 使用 bind_class,它接受三个参数，
		* 第一个参数是类名
		* 第二个参数是事件类型
		* 第三个参数是相应的操作
	* demo
		bind_class("Entry" ,"<Control-V>" ,my_paste)
		* 它就是绑定了所有的所有的输入框的 Ctrl+V 表示粘贴

------------------------
解除事件绑定			|
------------------------
	* 解除绑定使用 unbind 方法,它和 bind 的使用很相似
	* unbind 方法只需要一个参数就可以了,它只需要解除绑定的事件类型,它会解除该绑定事件类型的所有回调函数
