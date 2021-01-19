----------------------------
Notification				|
----------------------------
	# 浏览器的桌面通知对象
	# Notification 对象继承自 EventTarget 接口
	# 这东西必须在https协议下才能弹出
	# window 对象
	
	# 大致步骤
		1,判断浏览器是否支持
			Boolean(window.Notification)
		
		2,判断是否已经授权
			if (Notification.permission==='granted') {
				//已经授权,执行代码
			} else {
				Notification.requestPermission();    //否则弹出开启权限提示框
			} 

		3,设置提示内容
			var notify = new Notification('title' , config );
				* config是一个对象，有5个属相，可以设置显示内容        
				var config  =   { 
					body :'正文内容',
					dir  :  '文本显示方向，auto,  ltr,  rtl',
					lang : '文本语言',
					tag : '为通知栏分配一个id，方便对提示操作，检索，替换，移除等',
					icon : '图片url'
				 }

		4,事件
			notify.onclick				//点击提示框时触发
			notify.onshow				//显示提示框时触发
			notify.onerror				//显示提示框出错时触发
			notify.onclose				//关闭提示框时触发
		
		5,关闭
			notify.close()

----------------------------
Notification-属性			|
----------------------------
	# 类属性
		permission
			* 返回当前用户对弹窗通知的的授权状态
				granted	//已经授权
				denied	//拒绝
				default	//其实就是拒绝
	
	# 实例属性
		title
			* 标题
		lang
			* 通知语言
			* en-US
		dir
			* 文本的显示方向
				auto: 继承浏览器的语言设置里制定的方向 (默认)
				ltr	: 从左到右
				rtl : 从右到左

		body 
			* 正文内容
		tag
			* 通知的id
			* 赋予通知一个ID,以便在必要的时候对通知进行刷新,替换或移除

		icon 
			* 图片地址

----------------------------
Notification-方法			|
----------------------------
	# 构造方法
		Notification(title,options)
			* title,标题
			* options,配置对象
	# 类方法
		requestPermission(call)
			* 弹出提示框,征求用户是否允许弹出桌面通知
			* call方法的第一个参数,就是用户的意见结果
			* 返回 Promise() 对象
	

	
	# 实例方法
		close()
			* 关闭通知
		
			
	# 实例事件
		onclick
			* 处理 click 事件的处理
		onshow
			* 处理 show 事件的处理
		onerror
			* 处理 error 事件的处理
		onclose
			* 处理 close 事件的处理

----------------------------
Notification-demo			|
----------------------------
		if(!window.Notification){
			//不支持弹窗
		}	
		if(window.Notification.permission != 'granted'){
			//征求授权
			window.Notification.requestPermission(function(permission){
				if(permission === 'granted'){
					//同意,弹个窗压压惊
					let notification = new window.Notification('你好',{
						body:'Hello',
						lang:'zh-ch',
						tag:'1',
						icon:'http://static.javaweb.io/e/1/7/5/0F8B0280ED034E65B602C2F923187F2B.gif'
					});
					notification.onshow = function(event){
					}
					notification.onclick = function(event){
					}
				}else{
					//拒绝,那就算了
				}
			});
		}