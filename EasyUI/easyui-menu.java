------------------------
EasyUI-menu				|	
------------------------
	# 菜单组件通常用于快捷菜单。他是构建其他菜单组件的必备基础组件。比如：menubutton和splitbutton。它还可以用于导航和执行命令。

------------------------
EasyUI-menu	菜单项		|	
------------------------
	id
		* 菜单项ID属性
	
	text
		* 菜单项文本
	
	iconCls
		* 显示在菜单项左侧的16x16像素图标的CSS类ID
	
	href
		* 设置点击菜单项时候的页面位置。
	
	disabled
		* 定义是否显示菜单项目
	
	onclick
		* 点击菜单时,执行的回调函数


------------------------
EasyUI-menu	菜单属性	|	
------------------------
	zIndex
		* 参数是 Number
		* 菜单z-index样式，增加它的值。
	
	left
		* 菜单的左边距位置
	
	top
		* 菜单的上边距位置
	
	align
		* 菜单的对齐方式.
		* 参数有:'left' 和 'right'
	
	minWidth
		* 菜单的最小宽度
	
	itemHeight
		* 菜单项高度
	
	duration
		* 隐藏菜单的持续时间
		* 单位是毫秒
	
	hideOnUnhover
		* 设置为 true 的时候,在鼠标离开菜单的时候将自动隐藏菜单。
	
	inline
		* 参数是 boolean
		* 当设置为true时，菜单将相对于父级标签进行定位，否则相对于body进行定位。
	
	fit
		* 当设置为true时，菜单尺寸将自动适配父容器
	
	noline
		* 当设置为true时，菜单将不显示图标和文字之间的分割线。
	


------------------------
EasyUI-menu	事件		|	
------------------------
	onShow
		* 在菜单显示之后触发
	
	onHide
		* 在菜单隐藏之后触发
	
	onClick
		* 在菜单项被点击的时候触发
		* demo
			//下面的例子显示了如何处理所有菜单项的点击：
			<div class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">
				<div data-options="name:'new'">新建</div>
				<div data-options="name:'save',iconCls:'icon-save'">保存</div>
				<div data-options="name:'print',iconCls:'icon-print'">打印</div>
				<div class="menu-sep"></div>
				<div data-options="name:'exit'">退出</div>
			</div>
			<script type="text/javascript">
				function menuHandler(item){
					alert(item.name)
				}
			</script>
	


------------------------
EasyUI-menu	方法		|	
------------------------
	options
		* 返回属性对象
	
	show
		* 显示菜单到指定的位置
		* 参数是一个对象{left:15,top:55}
	
	hide
		* 隐藏菜单
	
	destroy
		* 销毁菜单
	
	getItem
		* 获取指定的菜单项。得到的是一个菜单项的DOM元素。
		* demo
			//下面的例子展示了如何根据ID获取指定的项： 
			<div id="mm" class="easyui-menu" style="width:120px">
				<div>New</div>
				<div id="m-open">Open</div>
				<div>Save</div>
			</div>

			var itemEl = $('#m-open')[0];  // 获取菜单项
			var item = $('#mm').menu('getItem', itemEl);
			console.log(item);
	
	setText
		* 设置指定菜单项的文本
		* 参数是一个对象:{target:xx,text:'字符串'}
		* demo
			var item = $('#mm').menu('findItem', '保存');
			$('#mm').menu('setText', {
				target: item.target,
				text: '修改'
			});
	
	setIcon
		* 设置指定菜单项图标。'param'参数包含2个属性：
			target		//DOM对象，要设置的菜单项。
			iconCls		//新的图标CSS类ID。 
		* demo
			$('#mm').menu('setIcon', {
				target: $('#m-open')[0],
				iconCls: 'icon-closed'
			});
	
	findItem
		* 查找指定的菜单项,返回的对象和getItem方法是一样的。 
		* demo
			// 查找“打开”项并禁用它
			var item = $('#mm').menu('findItem', '打开');
			$('#mm').menu('disableItem', item.target);
	
	appendItem
		* 追击新的菜单项,'options'参数代表新菜单项属性。默认情况下添加的项在菜单项的顶部。追加一个子菜单项，'parent'属性应该设置指定的父项元素，并且该父项元素必须是已经定义在页面上的。 
		* demo
			// 追加一个顶部菜单
			$('#mm').menu('appendItem', {
				text: '新菜单项',
				iconCls: 'icon-ok',
				onclick: function(){alert('提示：新菜单项！')}
			});
			// 追加一个子菜单项
			var item = $('#mm').menu('findItem', '打开');  // 查找“打开”项
			$('#mm').menu('appendItem', {
				parent: item.target,  // 设置父菜单元素
				text: '打开Excel文档',
				iconCls: 'icon-excel',
				onclick: function(){alert('提示：打开Excel文档！')}
			});
	
	removeItem
		* 移除指定菜单项
		* 参数:itemEl
	
	enableItem
		* 启用菜单项目
		* 参数:itemEl
	
	disableItem
		* 禁用菜单项
		* 参数:itemEl
	
	showItem
		* 显示菜单项
		* 参数:itemEl
	
	disableItem
		* 禁用菜单项
		* 参数:itemEl
	
	showItem
		* 显示菜单项
		* 参数:itemEl
	
	hideItem
		* 隐藏菜单项
		* 参数:itemEl
	
	resize
		* 重置指定的菜单项
		* 参数:itemEl










