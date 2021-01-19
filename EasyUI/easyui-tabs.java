------------------------
Easyui-tabs				|
------------------------
	# 选项卡
	# 依赖关系
		panel
		linkbutton

------------------------
Easyui-tabs属性			|
------------------------
	width
		* 选项卡容器宽度
	
	height
		* 选项卡容器高度
	
	plain
		* 设置为 true 的时候将不显示控制面板背景。
	
	fit
		* 设置为 true 的时候.选项卡的大小将铺满它所在的容器。
	
	border
		* 设置为 true 的时候,会显示选项卡容器边框。
	
	scrollIncrement
		* 选项卡滚动条每次滚动的像素值
	
	scrollDuration
		* 每次滚动动画的持续时间.单位是毫秒
	
	tools
		* 工具栏添加在选项卡面板头的左侧或右侧。
		* 该属性值可以有两种形式
			1, 一个工具菜单数组，每个工具选项都和linkbutton相同。
				$('#tt').tabs({
					tools:[{
						iconCls:'icon-add',
						handler:function(){
							alert('添加')
						}
					},{
						iconCls:'icon-save',
						handler:function(){
							alert('保存')
						}
					}]
				});

			2, 一个指向<div/>容器工具菜单的选择器。
				$('#tt').tabs({
					tools:'#tab-tools'
				});
				<div id="tab-tools">
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add"></a>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-save"></a>
				</div>

	toolPosition
		* 设置工具栏的位置
		* 可选值:'left','right'
	
	tabPosition
		* 设置选项卡位置
		* 可用值：'top','bottom','left','right'
	
	headerWidth
		* 选项卡标题宽度
		* 在tabPosition属性设置为'left'或'right'的时候才有效
	
	tabWidth
		* 标签条的宽度
	
	tabHeight
		* 标签条的高度
	
	selected
		* 初始化选中一个标签页
		* 该值是一个 Number
		* 该值为标签页的 index
	
	showHeader
		* 设置为 true 时,显示标签页标题
	
	justified
		* 如果该值为 true ,那么所有标签页的标题都是一样长
		* 所有标题平分宽度,撑到容器大小
	
	narrow
		* 如果该值为 true ,那么每个标签页之间是没有间隙的
	
	pill
		* 如果该值为 true ,选项卡标题样式改为气泡状。



------------------------
Easyui-tabs事件			|
------------------------
	onLoad
		* 有一个参数:panel,在ajax选项卡面板加载完远程数据的时候触发。
	
	onSelect
		* 在用户选择一个面板的时候触发
		* 有两个参数:title,index
	
	onUnselect
		* 用户在取消选择一个选项卡面板的时候触发.
		* 有两个参数:title,index

	onBeforeClose
		* 在一个选项卡关闭的时候触发,返回 false,可以阻止该事件
		* 有两个参数:title,index
		* demo
			$('#tt').tabs({
				onBeforeClose: function(title){
					return confirm('您确认想要关闭 ' + title);
				}
			});
			// 使用异步确认对话框
			$('#tt').tabs({
			  onBeforeClose: function(title,index){
				var target = this;
				$.messager.confirm('确认','你确认想要关闭'+title,function(r){
					if (r){
						var opts = $(target).tabs('options');
						var bc = opts.onBeforeClose;
						opts.onBeforeClose = function(){};  // 允许现在关闭
						$(target).tabs('close',index);
						opts.onBeforeClose = bc;  // 还原事件函数
					}
				});
				return false;	// 阻止关闭
			  }
			});

	onClose	
		* 在用户关闭一个面板的时候触发
		* 参数是:title,index
	
	onAdd
		* 在新增一个面板的时候触发
		* 参数是:title,index
	
	onUpdate
		* 在更新一个选项卡面板的时候触发
		* 参数是:title,index
	
	onContextMenu
		* 在右击一个选项卡面板的时候触发
		* 参数:e, title,index

------------------------
Easyui-tabs方法			|
------------------------
	options
		* 返回选项卡属性
	
	tabs
		* 返回所有选项卡面板
	
	resize
		* 调整选项卡容器大小和布局
	
	add
		* 添加一个新选项卡面板，选项参数是一个配置对象.查看选项卡面板属性的更多细节。
		* 在添加一个新选项卡面板的时候它将变成可选的。
		* 添加一个非选中状态的选项卡面板时，记得设置'selected'属性为false。
		* demo
			// 添加一个未选中状态的选项卡面板
			$('#tt').tabs('add',{
				title: '新选项卡面板',
				selected: false
				//...
			});
			// 在索引为2的位置上插入一个选项卡面板
			$('#tt').tabs('add',{
				title: '插入的选项卡面板',
				index: 2
			});
	
	close
		* 关闭一个选项卡面板
		* 参数可以是选项卡面板的索引(index)或者是标题(title)
	
	getTab
		* 获取一个选项卡面板
		* 参数可以是选项卡面板的索引(index)或者是标题(title)
	
	getTabIndex
		* 获取指定选项卡面板的索引
		* 参数就是一个选项卡对象
	
	getSelected
		* 获取被选择的选项卡面板
		* demo
			var tab = $('#tt').tabs('getSelected');			//获取被选择的选项卡对象
			var index = $('#tt').tabs('getTabIndex',tab);	//使用getTabIndex获取到该选项卡的下标
			alert(index);
		
	select
		* 选择一个面板
		* 参数可以是面板标题或者是面板下标
	
	unselect
		* 取消选择一个面板
		* 参数可以是面板标题或者是面板下标
	
	showHeader
		* 显示选项卡的标签头
	
	hideHeader
		* 隐藏选显卡标签头
	
	showTool
		* 显示工具栏
	
	hideTool
		* 隐藏工具栏
	
	exists
		* 判断指定的面板是否存在.如果存在返回 true
	
	update
		* 更新指定选项卡面板
		* 参数是一个对象
			tab		//要更新的选项卡面板。
			type	//更新类型，可用值有：'header', 'body', 'all'。（该参数自1.4.1版开始可用）
			options	//面板的属性。
	
		* demo
			// 更新选择的面板的新标题和内容
			var tab = $('#tt').tabs('getSelected');  // 获取选择的面板
			$('#tt').tabs('update', {
				tab: tab,
				options: {
					title: '新标题',
					href: 'get_content.php'  // 新内容的URL
				}
			});

			// 调用 'refresh' 方法更新选项卡面板的内容
			var tab = $('#tt').tabs('getSelected');  // 获取选择的面板tab.panel('refresh', 'get_content.php');
	
	enableTab
		* 启用指定的选显卡面板
		* 参数是可以是面板的标题或者是面板的下标
	
	disableTab
		* 禁用指定的面板
		* 参数是可以面板的标题或者是面板的下标
	
	scrollBy
		* 滚动选项卡标题指定的像素数量，负值则向右滚动，正值则向左滚动
		* demo
			// 滚动选项卡标题到左边
			$('#tt').tabs('scroll', 10);
	


-----------------------------
选项卡面板属性				 |
-----------------------------
	'选项卡面板属性与panel组件属性的定义类似，下面是2个组件的一些公共属性。'

	id
		* 面板的ID属性
	
	title
		* 选项卡的面板的标题
	
	content
		* 选项板面板的内容
	
	href
		* 从URL加载远程数据到面板
	
	cache
		* 如果为true，在'href'属性设置了有效值的时候缓存选项卡面板。
	
	iconCls
		* 指定面板的小图标
	
	width
		* 选显卡面板的宽度
	
	height
		* 选项卡面板的高度
	
	collapsible
		* 如果该值为 true ,则允许选显卡面板折叠
	

	'下面的是选项卡面板新增且独有的属性。'
	closable
		* 如果该值为 true,则显示一个关闭摁钮
	
	selected
		* 如果该值为 true,则选项卡面板板会被选中
	
	disabled
		* 如果该值为 true,则选项卡面板会被禁用