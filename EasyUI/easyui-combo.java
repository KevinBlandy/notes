------------------------
EasyUI-combo			|
------------------------
	# 下拉框
	# 这东西的强大之处在于,下拉框的东西可以是图片等信息,不一定只是文字
	# 依赖关系
		textbox
		panel
	# 初始化方法
		//直接对input进行初始化
		<input id="cc" value="001">  
		$('#cc').combo({    
			required:true,    
			multiple:true   
		});  
		//对panle进行初始化


------------------------
EasyUI-combo属性		|
------------------------
	'属性扩展自validatebox(验证框)，自定义下拉框新增的属性如下'

	width
		* 组键的宽度
	
	height
		* 组键的高度
	
	panelWidth
		* 下拉面板的宽度
	
	panelHeight
		* 下拉面板的高度
	
	panelMinWidth
		* 下拉面板的最小宽度
	
	panelMaxWidth
		* 下拉面板的最大宽带
	
	panelMinHeight
		* 下拉面板的最小高度
	
	panelMaxHeight
		* 下来面板的最大高度
	
	panelAlign
		* 面板的对齐方式
		* 可用值有：'left','right'
	
	multiple
		* 当该值为 true,则该选项支持多选
	
	separator
		* 定义多选的时候,多个选项之间使用的分隔符

	selectOnNavigation
		* 是否可以使用键盘来导航选项
	
	editable
		* 用户是否可以在输入框中输入内容
	
	disabled
		* 是否禁用该字段
	
	readonly
		* 是否是只读模式
	
	hasDownArrow
		* 定义是否显示向下的一个箭头摁钮
	
	value
		* 该字段的默认值
	
	delay
		* 执行搜索的延迟.就是用户输入数据后.延迟多久发送到服务器进行检索
	
	keyHandler
		* 在用户按下键的时候调用一个函数。该按键处理器被定义为：
			keyHandler: {
				up: function(e){},
				down: function(e){},
				left: function(e){},
				right: function(e){},
				enter: function(e){},
				query: function(q,e){}
			}

------------------------
EasyUI-combo事件		|
------------------------
	onShowPanel	
		* 在下拉面板的时候触发
	
	onHidePanel
		* 在隐藏面板的时候触发
	
	onChange
		* 当字段值改变的时候触发
		* 有两个参数:newValue,oldValue 新值和旧值
	

------------------------
EasyUI-combo方法		|
------------------------
	'自定义下拉框的方法扩展自validatebox(验证框)。自定义下拉框新增的方法如下'
	options
		* 返回属性对象

	panel
		* 返回下拉面板对象
	
	textbox
		* 返回文本框对象
	
	destroy
		* 销毁该组键
	
	resize
		* 调整组键宽带
		* 具备一个参数,就是组键的宽度值
	
	showPanel
		* 显示下拉面板
	
	hidePanel
		* 隐藏下拉面板
	
	disable
		* 禁用组键
	
	enable
		* 启用组键
	
	readonly
		* 开启只读模式,具备一个 boolean 参数
		* demo
			$('#cc').combo('readonly');			// 启用只读模式
			$('#cc').combo('readonly', true);	// 启用只读模式
			$('#cc').combo('readonly', false);	// 禁用只读模式
		
	validate
		* 验证输入的值
	
	isValid
		* 返回验证的结果
	
	clear
		* 清空控件的值
	
	reset
		* 重置控件的值
	
	getText
		* 获取输入的文本
	
	setText
		* 设置输入的文本
	
	getValues
		* 获取组件值的数组
	
	setValues
		* 设置组件值的数据
	
	getValue
		* 获取组件值
	
	setValue
		* 设置组件值
	

