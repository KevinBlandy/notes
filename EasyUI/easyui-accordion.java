------------------------
EasyUI-accordion		|
------------------------
	# 分类空间允许用户使用多面板，但在同一时间只会显示一个。每个面板都内建支持展开和折叠功能。点击一个面板的标题将会展开或折叠面板主体。面板内容可以通过指定的'href'属性使用ajax方式读取面板内容。用户可以定义一个被默认选中的面板，如果未指定，那么第一个面板就是默认的。
	# 继承关系
		panel


------------------------
EasyUI-容器属性			|
------------------------
	width
		* 分类容器的宽度
	
	height
		* 分类容器的高度
	
	fit
		* 如果该值为 true,分类容器会自动铺满父级容器
	
	border
		* 定义是否显示边框
	
	animate
		* 在展开和折叠边框的时候是否显示动画
	
	multiple
		* 如果该值为 true,则允许同时展开多个面板
	
	selected
		* 设置初始化的时候,默认选中的面板
	


------------------------
EasyUI-accordion属性	|
------------------------
	'分类面板属性继承自panel(面板)，分类面板新增的属性如下'
	selected
		* 如果该值为 true,则展开所有的面板
	
	collapsible
		* 如果该值为 true,则显示折叠摁钮

------------------------
EasyUI-accordion事件	|
------------------------
	onSelect
		* 在选择面板的时候触发
		* 具备两个参数:title,index  标题和下标
	
	onUnselect
		* 在面板被取消选中的时候触发
		* 具备两个参数:title ,index 标题和下标
	
	onAdd
		* 在新增面板的时候触发
		* 具备两个参数:titile,index 标题和下标
	
	onBeforeRemove
		* 在移除面板之前触发，返回false可以取消移除操作。
		* 具备两个参数:title,index
	
	onRemove
		* 在面板被移除的时候触发
		* 具备两个参数:title,index 


------------------------
EasyUI-accordion方法	|
------------------------
	options
		* 返回分类组件属性
	
	panels
		* 获取所有的面板
	
	resize
		* 调整分类组键大小
	
	getSelected
		* 获取选中的面板
	
	getSelections
		* 获取所有选中的面板
	
	getPanel
		* 获取指定的面板
		* 参数可以是标题或者下标
	
	getPanelIndex
		* 获取指定面板的索引
		* demo
			var p = $('#aa').accordion('getSelected');
			if (p){
				var index = $('#aa').accordion('getPanelIndex', p);
				alert(index);
			}

	select
		* 选择指定的面板
		* 参数可以是标题或者是下标
	
	unselect
		* 取消选择指定的面板
		* 参数是可以标题或者下标
	
	add
		* 添加一个新面板。
		* 在默认情况下，新增的面板会变成当前面板。
		* 如果要添加一个非选中面板，不要忘记将'selected'属性设置为false。 
		* demo
			$('#aa').accordion('add', {
				title: '新标题',
				content: '新内容',
				selected: false
			});
	
	remove
		* 移除一个面板
		* 参数可以是标题或者是面板的下标索引
	





