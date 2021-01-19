---------------------------
Radiobutton					|
---------------------------
	* 单选框
	* 构造函数
		  def __init__(self, master=None, cnf={}, **kw)

---------------------------
Radiobutton-属性			|
---------------------------
	text
		* 显示的名称
	value
		* 指定该radio的值
	variable
		* 指定当前单选框值的映射的对象,该值应该是一个tkvar的实例对象
		* 多个 Radiobutton 组合实例的 variable 都应该是同一个 tkvar 实例(当单选框被选择的时候,tkvar实例的值就是被选中的Radiobutton实例的值)
		* 如果修改了 tkvar 实例对象的值,也会反选到对应的 Radiobutton
	indicatoron
		* 是否显示选择框的前面的勾选框,默认为 True
		* 如果为 False,单选框会以按钮的形式出现,选中即是按钮按下状态,非选中,即时按钮未被按下状态

---------------------------
Radiobutton-方法			|
---------------------------
	