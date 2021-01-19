----------------------------
Menu						|
----------------------------
	* 菜单栏组键
	* 构造函数
		  def __init__(self, master=None, cnf={}, **kw)


----------------------------
Menu-属性					|
----------------------------
	'tearoff'
		* 是否不显示一根虚线,点击后可以单独弹出子菜单的
		* 值应该是 True/False ,默认为 False(显示)
	
----------------------------
Menu-方法					|
----------------------------
	add_commmand()
		* 添加菜单项
		* 如果该菜单是顶层菜单,则添加的菜单项依次向右添加
		* 如果该菜单是顶层菜单的一个菜单项,则它添加的是下拉菜单的菜单项
		* 关键字参数
			label
				* 菜单的名称
			command
				* 事件函数
			acceletor
				* 
			underline
				* 
	
	add_cascade()
		* 添加子菜单
		* 关键字参数
			label
				* 菜单的名称
			menu
				* 子菜单实例对象
	
	add_radiobutton()
		* 添加单选菜单
	
	add_checkbutton()
		* 添加多选菜单
	
	add_separator()
		* 添加分割线
	
	pop()
		* 弹出菜单
	


----------------------------
Menu-demo					|
----------------------------
	* 下拉级联菜单
		# 创建父级Menu组件
		root_menu = Menu()

		sub_menu_1 = Menu(tearoff = False)      # 下拉菜单1
		sub_menu_2 = Menu(tearoff = False)      # 下拉菜单2
		sub_menu_3 = Menu(tearoff = False)      # 下拉菜单3

		# 初始化下拉菜单3的菜单
		sub_menu_3.add_command(label='3-1')
		sub_menu_3.add_command(label='3-2')

		# 初始化下拉菜单2的菜单
		sub_menu_2.add_command(label='2-1')
		sub_menu_2.add_cascade(label='2-2',menu = sub_menu_3)

		# 初始化下拉菜单1的菜单
		sub_menu_1.add_cascade(label='1-1',menu = sub_menu_2)
		sub_menu_1.add_command(label='1-2')

		# 父级菜单添加子菜单
		root_menu.add_cascade(label='菜单1',menu=sub_menu_1,underline=True)
		root_menu.add_cascade(label='菜单2',menu=sub_menu_1,underline=True)

		root['menu'] = root_menu
		
		# -----------------------
		菜单
		1-1	->	2-1
		1-2		2-2	->	3-1
						3-2

				
				

	
	