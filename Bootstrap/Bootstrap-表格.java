------------------------
Bootstrap-表格			|
------------------------
	table
		* 标识在 <table>
		* 少量padding和水平方向分割线,就是没一行都有分割线

	table-striped
		* 有条纹的背景颜色(隔行变色)

	table-bordered
		* 带边框的表格

	table-hover
		* 鼠标悬停变色,移开恢复

	table-condensed
		* 紧凑的表格,单元格内补减半

	table-responsive
		* 响应式表格,把table元素包裹在这个属性的元素内,就可以创建响应式表格
		* 要注意的是,这个东西是加在'包裹表格'的元素上,当窗口过小,就会出现滚动条
	
	table-striped 
		* <tbody>会有条纹

	# 行,或单元格背景色(下面这些仅仅是改变颜色)
		active		
		success
		info
		warning
		danger
		* 它们只能给<tr>,<td>,<th>添加,除此之外一个都不能
