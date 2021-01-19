------------------------
EasyUI-droppable		|
------------------------
	# 放置
	# 使用$.fn.droppable.defaults重写默认值对象。

------------------------
EasyUI-droppable属性	|
------------------------
	accept
		* 确定哪些拖拽元素将被接受
		* 参数是一个选择器: selector
	
	disabled
		* 如果该值为 true,则禁止放置

------------------------
EasyUI-droppable事件	|
------------------------
	'所有的事件都具备两个参数:e,source'
	onDragEnter
		* 在被拖拽元素到放置区内的时候触发，source参数表示被拖拽的DOM元素。
	
	onDragOver
		* 在被拖拽元素经过放置区的时候触发，source参数表示被拖拽的DOM元素。
	
	onDragLeave
		* 在被拖拽元素离开放置区的时候触发，source参数表示被拖拽的DOM元素。
	
	onDrop
		* 在被拖拽元素放入到放置区的时候触发，source参数表示被拖拽的DOM元素。
	

------------------------
EasyUI-droppable方法	|
------------------------
	options
		* 返回属性对象
	
	enable
		* 启用放置功能
	
	disable
		* 禁用放置功能
