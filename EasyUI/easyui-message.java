----------------------------
EasyUI-message				|
----------------------------
	# 消息提示框
	# 依赖关系
		window 
		linkbutton 
		progressbar
 
	# 用法
		* 仅仅是消息提示
			$.messager.alert('警告','警告消息');		//第一个参数为标题,第二个参数为正文
		* 请求确定摁钮
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){ 
				if (r){    
					alert('确认删除');    
				}    
			});  


----------------------------
EasyUI-message属性			|
----------------------------
	ok
		* 显示OK的字符串,默认值就是OK
	
	cancel
		* 显示cancel的字符串,默认就是Cancel
	

----------------------------
EasyUI-message事件			|
----------------------------


----------------------------
EasyUI-message方法			|
----------------------------
	$.messager.show
		* 在屏幕右下角显示一条消息窗口。该选项参数是一个可配置的对象：
			showType	//定义将如何显示该消息。可用值有：null,slide,fade,show。默认：slide。
			showSpeed	//定义窗口显示的过度时间。默认：600毫秒。
			width		//定义消息窗口的宽度。默认：250px。
			height		//定义消息窗口的高度。默认：100px。
			title		//在头部面板显示的标题文本。
			msg			//显示的消息文本。
			style		//定义消息窗体的自定义样式。
			timeout		//如果定义为0，消息窗体将不会自动关闭，除非用户关闭他。如果定义成非0的树，消息窗体将在超时后自动关闭。默认：4000毫秒。 

	
	$.messager.alert
		* 显示警告窗口。参数
		* 注意这堆参数,并不是一个参数对象.而是依次写入的
			title	//在头部面板显示的标题文本。
			msg		//显示的消息文本。
			icon	//显示的图标图像。可用值有：error,question,info,warning 。
			fn:		//在窗口关闭的时候触发该回调函数。 
		* demo
			$.messager.alert('这是一个标题','这是提示消息','error',function(){
				//点击确定后,执行的参数
			});

	$.messager.confirm
		* 一个带有确定和取消的窗口
		* 参数,并非是一个参数对象.而是依次写入
			title	//在头部面板显示的标题文本。
			msg		//显示的消息文本。
			fn(b)	//当用户点击“确定”按钮的时侯将传递一个true值给回调函数，否则传递一个false值。 
		* demo
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){ 
				if (r){    
					alert('确认删除');    
				}    
			});  
	

	$.messager.prompt
		* 显示一个用户可以输入文本的并且带“确定”和“取消”按钮的消息窗体。
		* 参数
			title	//在头部面板显示的标题文本。
			msg		//显示的消息文本。
			fn(val): 在用户输入一个值参数的时候执行的回调函数。//val就是用户输入的值
	
	$.messager.progress
		* 显示一个进度消息窗体。就是假装取数据的时候读个条
		* 参数,这个参数是一个对象
			title	//在头部面板显示的标题文本。默认：空。
			msg		//显示的消息文本。默认：空。 
			text	//在进度条中显示的文本。默认：undefined。
			interval//每次进度更新的间隔时间。默认：300毫秒。
		
		* 方法
			bar			//获取进度条对象。
			close		//关闭进度窗口。
		
		* demo
			$.messager.progress();			//显示进度窗口

			$.messager.progress('close');	//关闭窗口





		

