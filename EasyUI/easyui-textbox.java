---------------------------
EasyUI-textbox				|
---------------------------
	# TextBox(文本框)是一个增强的输入字段组件， 它允许用户非常简单的创建一组表单。它是一个用于构建其他组合控件的基础组件，如：combo，databox、spinner等 
	# 继承关系
		validatebox 
		linkbutton

---------------------------
EasyUI-textbox			属性|
---------------------------
	width
		* 组件的宽度
	
	height
		* 组件的高度
	
	prompt
		* 在输入框显示的提示信息
	
	value
		* 默认值
	
	type
		* 文本框类型。可用值有："text"和"password"。
	
	multiline
		* 定义是否是多行文本框。
	
	editable
		* 定义用户是否可以直接在该字段内输入文字。
	
	disabled
		* 定义是否禁用该字段。
	
	readonly
		* 定义该控件是否只读
	
	icons
		* 参数是一个数组
		* 在文本框删贡献是图标。每一项都有以下属性：
			iconCls		//类型string，图标的class名称；
			disabled	//类型boolean，指明是否禁用该图标；
			handler		//类型function，用于处理点击该图标以后的动作。 
	
			$('#tb').textbox({
				icons: [{
					iconCls:'icon-add',
					handler: function(e){
						$(e.data.target).textbox('setValue', 'Something added!');
					}
				},{
					iconCls:'icon-remove',
					handler: function(e){
						$(e.data.target).textbox('clear');
					}
				}]
			})
					
	iconCls
		* 在文本框显示背景图标
	
	iconAlign
		* 背景图标位置.值有："left", "right"。
	
	iconWidth
		* 图标的宽度
	
	buttonText
		* 文本框附加按钮显示的文本内容。
	
	buttonIcon
		* 文本框附加按钮显示的图标。
	
	buttonAlign
		* 附件摁钮的位置,可拥有的值有："left", "right"。
	

---------------------------
EasyUI-textbox			事件|
---------------------------
	'事件扩展自 validatebox，以下是新增的文本框事件。'
	onChange
		* 在字段值更改的时候触发
		* newValue, oldValue
	
	onResize
		* 在文本框大小更改的时候触发
		* width, height
	
	onClickButton
		* 在用户点击摁钮的时候触发
	
	onClickIcon
		* 在用户点击图标的时候触发
		* index
	
	
---------------------------
EasyUI-textbox			方法|
---------------------------
	'方法扩展自 validatebox，以下是新增的文本框方法。'
	options
		* 返回属性对象
	
	textbox
		* 返回文本框对象
		* demo
			var t = $('#tt');
			t.textbox('textbox').bind('keydown', function(e){
				if (e.keyCode == 13){	// 当按下回车键时接受输入的值。
					t.textbox('setValue', $(this).val());
				}
			});
	
	button
		* 返回摁钮对象
	
	destroy
		* 销毁文本框组件
	
	resize
		* 调整文本框组件宽度
		* 参数是 width
	
	disable
		* 禁用组件
	
	enable
		* 启用组件
	
	readonly
		* 启用/禁用只读模式
		* demo
			$('#tb').textbox('readonly');        // 启用只读模式
			$('#tb').textbox('readonly',true);	// 启用只读模式
			$('#tb').textbox('readonly',false);	// 禁用只读模式
		
	clear
		* 清除组件中的值
	
	reset
		* 重置组件中的值
	
	initValue
		*　初始化组件值。调用该方法不会触发“onChange”事件。
	
	setText
		* 设置显示的文本值
		* 参数:text
	
	getText
		* 获取显示的文本值
	
	setValue
		* 设置组件的值
		* 参数:value

	getValue
		* 获取组件的值
	
	getIcon
		* 获取指定图标对象



		

	




