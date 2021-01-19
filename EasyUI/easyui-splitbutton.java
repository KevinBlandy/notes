------------------------
EasyUI-splitbutton		|
------------------------
	# 类似菜单按钮，分割按钮也与linkbutton和菜单有关系。menubutton和splitbutton之间的区别是,splitbutton分为两部分。它只会在鼠标移动到splitbutton按钮右边的时候才会显示出“分割线”。
	# 继承关系
		menu
		linkbutton

------------------------
EasyUI-splitbutton	属性|
------------------------
	'分割按钮属性扩展自linkbutton，分割按钮新增的属性如下：'
	plain
		* 是否显示简洁样式
	
	menu
		* 用来创建一个对应的菜单选择器
	
	duration
		* 定义鼠标划过按钮时显示菜单所持续的时间，单位为毫秒。

------------------------
EasyUI-splitbutton	事件|
------------------------


------------------------
EasyUI-splitbutton	方法|
------------------------
	options
		* 返回属性对象
	
	disable
		* 禁用分割摁钮
	
	enable
		* 启用分割摁钮
	
	destroy
		* 销毁分割摁钮
	