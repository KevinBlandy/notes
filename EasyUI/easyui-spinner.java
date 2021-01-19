------------------------
EasyUI-spinner			|
------------------------
	# 微调控件结合了一个可编辑文本框和2个小按钮让用户选择一个值的范围。和下拉列表框类似，微调控件允许用户输入值，但是没有下拉列表。微调控件是创建其他高级微调控件的基础控件，比如：numberspinner, timespinner等。
	# 继承关系
		textbox
	
	# '这个控件只能通过JavaScritp创建,标签创建无效'

------------------------
EasyUI-spinner	属性	|
------------------------
	'微调控件的属性扩展自validatebox(验证框)，微调控件新增或重写的属性如下：'
	width
		* 组件宽度
	
	height
		* 组件高度
	
	value
		* 默认值
	
	min
		* 允许的最小值
	
	max
		* 允许的最大值
	
	increment
		* 点击微调摁钮的增量值
	
	editable
		* 定义用户是否可以直接输入值到字段
	
	disabled
		* 是否禁用字段
	
	readonly
		* 控件是否为只读
	
	spin
		* 在用户点击微调按钮的时候调用的函数。'down'参数对应用户点击的向下按钮。
		* function (down){}
	

------------------------
EasyUI-spinner	事件	|
------------------------
	onSpinUp
		* 用户点击向上微调摁钮的时候触发
	
	onSpinDown
		* 在用户点击向下微调摁钮的时候触发
	
	onChange
		* 在改变当前值的时候触发

------------------------
EasyUI-spinner	方法	|
------------------------
	'微调控件的方法扩展自validatebox(验证框)，微调控件新增的方法如下：'
	options
		* 返回属性对象
	
	destroy
		* 销毁微调组件
	
	resize
		* 设置组件的宽度
		* demo
			$('#ss').spinner('resize');         // 调整到原始宽度
			$('#ss').spinner('resize', 200);    // 调整到新宽度
	
	enable
		* 启用组件
	
	disable
		* 禁用组件
	
	getValue
		* 获取组件值
	
	setValue
		* 设置组件值
	
	readonly
		* 启用/禁用只读模式
		* demo
			$('#ss').spinner('readonly');			// 启用只读模式
			$('#ss').spinner('readonly', true);		// 启用只读模式
			$('#ss').spinner('readonly', false);	// 禁用只读模式
	
	clear
		* 清空组件值
	
	reset
		* 重置组件值
	
