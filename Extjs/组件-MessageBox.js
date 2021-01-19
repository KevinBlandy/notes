-------------------------
MessageBox				 |
-------------------------
	# Ext.window.MessageBox
	# 消息提示框组件

-------------------------
方法					 |
-------------------------
	alert(String titile,String message,function callback,Object scope);
		# 弹出一个消息提示框
		# 参数
			title
				* 提示框显示的标题
			message
				* 提示的消息
			callback(可选参数)
				* 回调函数 function(e){},如果点击确定,则e="ok",点击的x,则e="cancel"
			scope(可选参数)
				* 回调函数的范围

	confirm(String titile,String mesage,function callback);
		# 弹出一个带有"是"和"否"的确定框
		# 参数
			title
				* 提示框显示的标题
			message
				* 提示的消息
			callback(可选参数)
				* 回调函数 function(e){},如果点击"是",则e="yes",点击的"否",则e="no"

	prompt(String titile,String message,function fun,null none,boolean multiline,String initText);
		# 带文本框的对话框
		# 参数
			title
				* 提示框显示的标题
			message
				* 提示的消息
			fun
				 * 回调删除,function (btn, text) {}
				 * btn:点击的摁钮
				 * text:文本框的内容
			none
				* 直接写null先不管
			multiline
				* 是否为多行文本框 
				* 该值为 boolean 或者是 Number(行数),

			initText
				* 文本框中的初始化文本(默认文本)

    },
	
	wait();
		# 参数
			message
				* 提示消息
			title
				* 标题
			config
				* 配置对象
				* 配置项	
					interval: 500, 
						* 刷新时间
					duration: 50000,
						* 生命周期时间
					increment: 15,
						* 进度条的长度,
					text: 'Updating...',
						* 进度条文本
					scope: this,
						* 作用域
					fn
						* duration完毕后执行的对象

	show();
		# 可以自定义类型的对话框
		# 参数,是一个json对象
			animEl
				* 
			buttons
				* 弹出框的摁钮设置,主要有一下几种
					Ext.Msg.OK,
					Ext.Msg.OKCANCEL,
					Ext.Msg.CAMCEL,
					Ext.Msg.YESNO,
					Ext.Msg.YESNOCANCEL
				* 该参数是一个对象
			closable
				* 是否显示右上角关闭x,默认true
			cls
				* 自定义的css
			defaultTextHeight
				* 多行文本中文本高度
			fn
				* 关闭弹出框后执行的函数
			icon	
				* 弹出框的图标,常用的图标有
					Ext.MessageBox.INFO，
					Ext.MessageBox.ERROR,
					Ext.MessageBox.WARNING,
					Ext.MessageBox.QUESTION

			maxWidth
				* 最大宽度(默认600)
			minWidth
				* 最小宽度(默认100)
			modal
				* 是否模态
			msg
				* 消息的内容
			multiline
				* 是否是多行文本框
			progress
				* 是否显示进度条
			progressText
				* 进度条上显示的文本信息
			prompt
				* 是显示输入框
			proxyDrag
				* 如果该值为true,则为拖拽的时候显示一个轻量级的代理标题
			title
				* 标题
			value
				* 文本框中的值
			wait
				* 该值为true,则动态显示 progress
			waitConfig
				* 该参数是json对象,用于控制显示 progress
				*　参数
					interval
						* 进度的频率,好多秒动一次
					duration
						* 执行进度的持续时间，超过这个时间后，interval失效，不再产生进度效果，但进度狂也不会消失。
						* 这个进度条会持续多久
					increment: 
						* 进度条的长度,
					scope
						* 作用域
					fn
						* duration的时间到后执行的函数
			width
				* 弹出框的宽度,不带单位
	

	hide();
		# 隐藏对话框
	
	updateProgress(Number,progressText,msg);
		# 更新进度条
		# 参数
			Number
				* 0和1之间的任何数 (默认0),反正就是0.x - 1
			progressText
				* 进度条里面的显示内容 (默认'')
			msg
				* 消息框主体内容被指定的消息代替 (默认没有定义)
			Returns
				Ext.MessageBox