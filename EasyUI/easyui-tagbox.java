------------------
tag-box				|
------------------
	# 继承
		combobox

------------------
tag-box	属性		|
------------------
	hasDownArrow
		* 是否显示向下摁钮,默认 false
	tagFormatter
		* 格式化
		* function(value,row)
	tagStyler
		* 样式

------------------
tag-box	方法		|
------------------
	# 继承自combobox
------------------
tag-box	事件		|
------------------
	# 继承 combobox
	
	onClickTag	
		* 点击的时候触发
	onBeforeRemoveTag	
		* 删除之前触发,返回 false 可以阻止
	onRemoveTag	
		*删除的时候触发
	