----------------------------
EasyUI-linkbutton			|
----------------------------
	# easyui-基础组键
	# 用法
		<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">easyui</a>  

----------------------------
EasyUI-linkbutton属性		|
----------------------------
	width
		* 宽带
	height
		* 高度
	id
		* 给组键设置一个ID值
	disabled
		* 是否是禁用摁钮
	toggle
		* 可以实现checkbox的效果,摁下就不弹上来,再点就弹上来
	selected
		* 定义摁钮的初始状态,true 选中,false 未选中
	group
		* 指定一个组,可以实现radio的效果
	plain
		* 是否显示简洁效果
	text
		* 摁钮文字
	iconCls
		* 摁钮左侧图标
	iconAlign
		* 摁钮位置
		* 'left','right','top','bottom'
	size
		* 摁钮大小
		* 值:'small','large'

----------------------------
EasyUI-linkbutton事件		|
----------------------------
	onClick
		* 在点击的时候触发

----------------------------
EasyUI-linkbutton方法		|
----------------------------
	options
		* 返回属性对象
	resize
		* 重置摁钮.参数就是一个属性对象
	disable
		* 禁用摁钮
	enable
		* 启用摁钮
	select
		* 选择摁钮
	unselect
		* 取消选择
