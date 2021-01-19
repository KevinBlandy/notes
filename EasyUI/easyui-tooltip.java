----------------------------
easyui-tooltip				|
----------------------------
	# 信息提示框
	# 属性名
		easyui-tooltip

----------------------------
easyui-tooltip属性			|
----------------------------
	position
		* 定义提示消息出现的位置
		* 可用值有："left","right","top","bottom"
	
	content
		* 消息框显示的内容
	
	trackMouse
		* 值为 true 时,允许提示框跟随鼠标移动

	deltaX
		* 水平方向提示框位置
		* 参数为 Number
	
	deltaY
		* 垂直方向提示框位置
		* 参数为 Number
	
	showEvent
		* 当触发什么事件的时候显示提示框
		* 参数是 String,指定事件.默认值:mouseenter
	
	hideEvent
		* 当触发什么事件的时候隐藏提示框
		* 参数是 String,指定事件.默认值:mouseleave
	
	showDelay
		* 延迟多少毫秒显示提示框
		* 参数是 Number
	
	hideDelay
		* 延迟多少毫秒隐藏提示框
		* 参数是 Number

----------------------------
easyui-tooltip事件			|
----------------------------
	onShow
		* 在提示框出现的时候触发,有一个参数:e
		* 这是一个事件对象
	
	onHide	
		* 在隐藏提示框的触发.只有一个参数:e

	onUpdate
		* 在提示框内容更新的时候触发,参数是:content
	
	onPosition
		* 在提示框位置发生变化的时候触发
		* 参数:left,top
	
	onDestroy
		* 在提示框销毁的时候触发


----------------------------
easyui-tooltip方法			|
----------------------------
	options
		* 返回属性对象
	
	tip
		* 返回 tip元素对象
	
	arrow
		* 返回箭头元素对象

	show
		* 显示提示框
		* 参数:e
	
	hide
		* 隐藏提示框
		* 参数:e
	
	update
		* 更新提示框内容,参数是:content
	
	reposition
		* 重置提示框位置
	
	destroy
		* 销毁提示框

	
