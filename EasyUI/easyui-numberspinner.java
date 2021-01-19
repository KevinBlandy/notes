----------------------------
EasyUI-numberspinner		|
----------------------------
	# 数字微调控件的创建是基于微调控件和数值输入框控件的。他可以转换输入的值，比如：数值、百分比、货币等。它也允许使用上/下微调按钮调整到用户的期望值。
	# 继承关系
		spinner 
		numberbox

----------------------------
EasyUI-numberspinner属性	|
----------------------------
	'本组件的属性完整继承自spinner(微调)和numberbox(数值输入框)。'

----------------------------
EasyUI-numberspinner事件	|
----------------------------
	'本组件的事件完整继承自spinner(微调)。'	

----------------------------
EasyUI-numberspinner方法	|
----------------------------
	'本组件的方法扩展自spinner(微调)，本组建新增的方法如下：'

	options
		* 返回属性对象
	
	setValue
		* 设置数字微调控件的值
		*demo
			$('#ss').numberspinner('setValue', 8234725);  // 设置值
			var v = $('#ss').numberspinner('getValue');      // 获取值
			alert(v);
	
