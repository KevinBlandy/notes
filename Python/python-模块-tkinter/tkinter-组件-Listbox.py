--------------------
Listbox				|
--------------------
	* 列表选择框
	* 构造函数
		 def __init__(self, master=None, cnf={}, **kw)

--------------------
Listbox-属性		|
--------------------
	selectmode
		* 选择模式,枚举值
			SINGLE
				* 单选
			BROWSE
				* 单选,拖动鼠标或者方向键,可以改变选项(默认)
			MULTIPLE
				* 多选
			EXTENDED
				* 多选,但是需要同时按住shift或者ctrl键拖拽鼠标才能实现
		
	height
		* 定义选择框有多少行,默认是10
	yscrollcommand
		* 垂直滚动条控制属性
	xscrollcommand
		* 水平滚动条控制属性



--------------------
Listbox-方法		|
--------------------
	insert(index,name)
		* 在指定的下标插入一个选项,其他选项向后移动
		* index,可以使用常量 END,执行指向了最后一个位置
	
	delete(index,end)
		* 从index开始删除,删除end个项目
		* 如果只给index,则仅仅删除index下标的选择项
		* 参数可以直接是常量:ACTIVE,表示当前选中的值
	
	



