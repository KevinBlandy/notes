-------------------------
Panel					 |
-------------------------
	# 容器组件
	# 构建
		var panel = new Ext.Panel({

		});



-------------------------
属性					 |
-------------------------
	autoLoad
		* 有效的URL,其实就是加载远程HTML页面
		* 仅仅加载<body><body/>中的数据
	autoScroll
		* 如果该值为 true 那么内容溢出,则显示滚动条.默认为 false
	autoShow
		* 如果该值为 true,那么显示设为 "x-hidden"的元素,默认为 false
	bbar
		* 底部条,显示在主体内
		* 该值可以是一个数组
			[{text:"摁钮1"},{text:"摁钮2"}]
		* 该值也可以是Ext.toobar
			[new Ext.Toolbar.Button({
				text:"摁钮",
				handler:function(){
					Ext.MessageBox.alert("提示","点击了一个摁钮");
				}
			})],
	tbar
		* 顶部条,显示在主体内
		* 该值可以是一个数组
			[{text:"摁钮1"},{text:"摁钮2"}]
		* 该值也可以是Ext.toobar
			[new Ext.Toolbar.Button({
				text:"摁钮",
				handler:function(){
					Ext.MessageBox.alert("提示","点击了一个摁钮");
				}
			})],
	buttons
		* 摁钮集合,自动添加到footer,footer显示在主体外
	buttonAlign
		* footer中摁钮的位置,枚举:left,rigth,center
	collapsible
		* 如果该值为 true,则显示最小化摁钮,默认 false
	draggable
		* 如果该值为 true,则可以拖动,默认为 false
	html
		* 主体内容
	id
		* 该组件的ID值,可以通过ID找到这个组件
	width
		* 宽度
	heigth
		* 高度
	title
		* 标题
	titleCollapse
		* 如果该值为 true,则点击标题的任何地方,都可以最小化.默认为 false
	applyTo
		* 呈现在哪个HTML元素里面(ID值)
	renderTo
		* 呈现在哪个HTML元素里面(ID值) ,跟上面哥们儿一样
	contentEl
		* 呈现哪个HTML元素里面(ID值),把EL内的内容呈现
		* 别瞎,这个属性,代表的是:'当前panel,内的内容,是来自于哪个组件(id值)'
	
	