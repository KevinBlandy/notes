-------------------------
Window					 |
-------------------------
	# Ext.window.Window
	# 面板组件


-------------------------
配置					 |
-------------------------
	title
		* 标题
	width
		* 宽度
	minWidth
		* 最小宽度
    height
		* 高度
	minHeight
		* 最小高度
	layout
		* 布局模式(默认Auto)
	renderTo
		* 渲染到哪里
	constrain
		* 如果该值为 true,则该创建只能在其容器内移动,默认为 false
	constrainHeader
		* 如果该值为 true, 则不运行该窗口的标题超出容器
	modal
		* 是否为模态窗口,默认为 false
	plain
		* 如果该值为 true,则窗口的背景是透明.默认为 false
	x
		* 窗口距离左上角坐标位置
	y
		* 窗口距离左上角坐标位置
	onEsc
		* 覆写onEsc函数,默认情况下摁Esc键会关闭窗口
		* 就是摁下Esc键的时候,默认执行关闭窗口,我们可以覆写

	closeAction
		* 当执行关闭的时候,进行的操作.
		* 枚举值
			destroy(默认)		//销毁组件
			hide				//隐藏组件
	autoScroll
		* 如果该值为 true,那么窗口中的内容溢出后,会出动条
	html
		* 窗口中显示的内容,可以是html代码
	icon
		* 设置图标,参数是图片的路径
	iconCls
		* 设置图标,参数是一个css样式
	draggable
		* 窗口是否允许拖动
	resizable
		* 是否允许改变大小
	closeable
		* 是否显示右上角的关闭 x 图标 默认 true
	collapsible
		* 是否显示收缩摁钮 默认 false
	maximizable
		* 是否显示最大化摁钮 默认 false
	minimizable
		* 是否显示最小化摁钮 默认 false
	shadow
		* 窗体是否有阴影,如果该值为 true,那么会自动创建一个  Ext.Shadow 对象
		* 也可以自己指定阴影模式 Ext.Shadow.mode
	bodyStyle
		* 自定义样式到组件的body上,可以是一个css描述对象,也可以直接就是css字符串
	bodyPadding
		* 组件内边距
	items 
		* 可以是一个组建对象,或者是一组组件对象
		* 如果没有配置layout属性，默认是按顺序将子组件渲染到在容器中，不考虑子组件的大小和定位。
	tbar
		* 在窗体的顶部,添加工具条,是一个数组,不一定非得是摁钮,也可以是其他的组件
		* 第一种定义方式
			tbar: [
				{ xtype: 'button', text: 'Button 1' }
			]
		* 第二种定义方式 
			dockedItems: [{
					xtype: 'toolbar',
					dock: 'top',
					items: [
						{ xtype: 'button', text: 'Button 1' }
					]
			}]
	bbar
		* 同上,不过工具条是在底部
	lbar
		* 同上,不过工具条是在左边
	rbar
		* 同上,不过工具条是在右边
	fbar
		* 同上,不过工具条是在底部(在面板内容之外添加)
							
	

-------------------------
属性					 |
-------------------------
	

-------------------------
方法					 |
-------------------------
	show
		* 显示组件
	up
		* 查找父级组件
		* 参数可以是组件的 xtype,或者是选择器

	down
		* 同上,不过是用于查找子组件
	
	destroy
		* 销毁当前方法

-------------------------
事件					 |
-------------------------


-------------------------
注意点					 |
-------------------------

-------------------------
其他特性				 |
-------------------------
	# windowGroup
		* 其实就是 Ext.ZIndexManager
		* 用这个对象,去操作多个窗口

	var windowGroup = new Ext.WindowGroup();
	

-------------------------
实战					 |
-------------------------
	/**
		点击创建window,组件每次都创建新的
	*/
	Ext.onReady(function(){
		//获取到btn元素,是经过ext包装过后的ext dom对象
		var btn = Ext.get("btn");
		//绑定点击事件
		btn.on('click',function () {
			//如果id=1的组件不存在就创建
			if(!Ext.getCmp("1")){
				Ext.create('Ext.window.Window',{
					id:'1',
					title:"标题",
					width:500,
					height:300,
					layout:'fit',
					renderTo:Ext.getBody(),
					modal:true,
					constrainHeader:true,
					//关闭组件,销毁组件
					closeAction:'destroy'
				}).show();
			}
		});
	});
	/**
		点击创建window,同一个组件,反复使用
	*/
	Ext.onReady(function(){
		//创建窗体
		var win = Ext.create('Ext.window.Window',{
			id:'1',
			title:"标题",
			width:500,
			height:300,
			layout:'fit',
			renderTo:Ext.getBody(),
			modal:true,
			constrainHeader:true,
			//关闭组件,隐藏组件
			closeAction:'hide'
		});
		//获取到btn元素,是经过ext包装过后的ext dom对象,绑定点击事件
		Ext.get('btn').on('click',function () {
			win.show();
		});
	});


