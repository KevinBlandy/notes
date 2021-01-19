----------------------------
EasyUI-combogrid			|
----------------------------
	# 数据表格下拉框结合了可编辑文本框控件和下拉数据表格面板控件，该控件允许用户快速查找和选择，并且该控件提供了键盘导航支持，对行进行筛选。
	# 继承关系
		combo
		datagrid

----------------------------
EasyUI-combogrid		属性|
----------------------------
	'数据表格下拉框的属性扩展自combo(自定义下拉框)和datagrid(数据表格)，树形下拉框新增属性如下：'
	loadMsg
		* 在数据表格加载远程数据的时候显示的提示消息
	
	idField
		* ID字段名称
	
	textField
		* 要显示在文本框中的文本字段
	
	mode
		* 智能检索
		* 定义在文本改变的时候如何读取数据网格数据。设置为'remote'，数据表格将从远程服务器加载数据。当设置为'remote'模式的时候，用户输入将会发送到名为'q'的http请求参数，向服务器检索新的数据。
	
	filter
		* 定义在'mode'设置为'local'的时候如果选择本地数据.返回 true,则选择该行
		* demo
			$('#cc').combogrid({
			filter: function(q, row){
					var opts = $(this).combogrid('options');
					return row[opts.textField].indexOf(q) == 0;
				}
			});
	

----------------------------
EasyUI-combogrid		事件|
----------------------------
	'数据表格下拉框事件完全扩展自combo(自定义下拉框)和datagrid(数据表格)。'

----------------------------
EasyUI-combogrid		方法|
----------------------------
	'数据表格下拉框的方法扩展自combo(自定义下拉框)，数据表格下拉框新增或重写的方法如下：'

	options
		* 返回属性对象
	
	grid
		* 返回数据表格对象
		* demo
			//获取选择的行
			var g = $('#cc').combogrid('grid');	// 获取数据表格对象
			var r = g.datagrid('getSelected');	// 获取选择的行
			alert(r.name);

	setValues
		* 设置组件值数组
		* demo
			$('#cc').combogrid('setValues', ['001','007']);
			$('#cc').combogrid('setValues', ['001','007',{id:'008',name:'name008'}]);
	
	setValue
		* 设置组件值
		* demo
			$('#cc').combogrid('setValue', '002');
			$('#cc').combogrid('setValue', {id:'003',name:'name003'});
	
	clear
		* 清楚组件的值
