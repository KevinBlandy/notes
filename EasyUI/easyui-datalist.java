------------------------
EasyUI-datalist			|
------------------------
	# datalist 组件专用于在列表数据上渲染，这是一个特殊的datagrid组件，专用于渲染1列数据时使用。您可以定义列当中每一行的展示格式及样式。
	# 继承
		datagrid

------------------------
EasyUI-datalist	属性	|
------------------------
	'数据列表属性扩展自datagrid，数据列表新增的属性如下：'
	lines
		* 是否显示行号

	checkbox
		* 是否在每行开头显示复选框
	
	valueField
		* 行绑定的字段值名称。
	
	textField
		* 行绑定的字段名名称。
	
	groupField
		* 指定分组名称
	
	textFormatter
		* (value,row,index) 字段名称格式化器，返回格式化后的字段内容，共3个参数：
			value	//字段值
			row		//行记录数据
			index	//行索引 
	
	groupFormatter
		* 分组名称格式化器，返回格式化后的分组内容，共2个参数：
			value	//通过“groupField”属性定义的分组字段名称
			rows	//指定分组字段名称对应的数据行记录数组


------------------------
EasyUI-datalist	事件	|
------------------------
	'数据列表事件扩展自datagrid。'

------------------------
EasyUI-datalist	方法	|
------------------------
	'数据列表方法扩展自datagrid。'