----------------------------
EasyUI-resizable			|
----------------------------
	# 使用$.fn.resizable.defaults重写默认值对象。
	# 调整大小

----------------------------
EasyUI-resizable属性		|
----------------------------
	disabled
		* 如果该值为 true,则进制调整大小

	handles
		* 声明调整的单位,参数是一个 String
		* 'n'=北,'e'=东,'s'=南
		* 所有可配置参数:n, e, s, w, ne, se, sw, nw, all
	
	minWidth
		* 当调整大小时候的最小宽度。
		* 参数是 Number
	
	minHeight
		* 当调整大小时候的最小高度。
		* 参数是 Number
	
	maxWidth
		* 当调整大小时候的最大宽度。
		* 参数是 Number
	
	maxHeight
		* 当调整大小时候的最大高度。
		* 参数是 Number
	
	edge
		* 边框边缘大小
		* 参数是 Number
	

----------------------------
EasyUI-resizable事件		|
----------------------------
	'所有事件的参数都是e'
	onStartResize
		* 在开始改变大小的时候触发。
	
	onResize
		* 在调整大小期间触发。当返回false的时候，不会实际改变DOM元素大小
	
	onStopResize
		* 在停止改变大小的时候触发


----------------------------
EasyUI-resizable方法		|
----------------------------
	options
		* 返回调整大小的属性
	
	enable
		* 启用调整大小功能
	
	disable
		* 禁用大小调整功能

	
