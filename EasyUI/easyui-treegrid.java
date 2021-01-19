-------------------------
EasyUI-treegrid			 |
-------------------------
	# 树形表格用于显示分层数据表格。它是基于数据表格、组合树控件和可编辑表格。树形表格允许用户创建可定制的、异步展开行和显示在多列上的分层数据。 
	# 继承关系
		datagrid

-------------------------
EasyUI-treegrid		属性 |
-------------------------
	'树形表格扩展自datagrid(数据表格)，树形表格新增的属性如下：'
	idField
		* 定义关键字段来标识树节点。（必须的）
	
	treeField
		* 定义树节点字段。（必须的）
	
	animate
		* 定义在节点展开或折叠的时候是否显示动画效果。
	
	checkbox
		* 定义在每一个节点前显示复选框，也可以指定一个函数来动态指定是否显示复选框，但函数返回true的时候则显示，否则不显示。
		* demo
			$('#tg').treegrid({
				checkbox: function(row){
					var names = ['Java','eclipse.exe','eclipse.ini'];
					if ($.inArray(row.name, names)>=0){
						return true;
					}
				}
			})
	
	cascadeCheck
		* 是否级联检查
	
	onlyLeafCheck
		* 是否仅在叶子节点显示复选框
	
	lines
		* 定义是否显示treegrid行。
	
	loader
		* 定义以何种方式从远程服务器读取数据。返回false可以忽略该动作。该函数具有一下参数：
			param			//传递到远程服务器的参数对象。
			success(data)	//当检索数据成功的时候调用的回调函数。
			error()			//当检索数据失败的时候调用的回调函数。
	
	loadFilter
		* 返回过滤后的数据进行展示。
		* function(data,parentId){}


-------------------------
EasyUI-treegrid		事件 |
-------------------------
	'树形表格的事件扩展自datagrid(数据表格)，树形表格新增的时间如下：'
	onClickRow
		* 在用户点击节点的时候触发。
		* 参数:row

	onDblClickRow
		* 在用户双击节点的时候触发。
		* 参数:row
	
	onClickCell
		* 在用户点击单元格的时候触发。
		* 参数:field,row
	
	onDblClickCell
		* 在用户双击单元格的是时候触发
		* 参数:field,row
	
	onBeforeLoad
		* 在请求数据加载之前触发，返回false可以取消加载动作。
		* 参数:row, param
	
	onLoadSuccess
		* 数据加载完成之后触发。
		* 参数:row, data
	
	onLoadError
		* 数据加载失败的时候触发，参数和jQuery的$.ajax()函数的'error'回调函数一样。
	
	onBeforeSelect
		* 在用户选择一行之前触发，返回false则取消该动作。
		* 参数:row
	
	onSelect
		* 在用户选择的时候触发，返回false则取消该动作。
		* 参数:row
	
	onBeforeUnselect
		* 在用户取消选择一行之前触发，返回false则取消该动作。（
		* 参数:row
	
	onUnselect
		* 在用户选中一行节点之前触发，返回false则取消该动作
		* 参数:row
	
	onBeforeCheckNode
		* 在用户选中一行节点之前触发，返回false则取消该动作
		* 参数:row,checked
	
	onCheckNode
		* 在用户选中一行节点的时候触发，返回false则取消该动作。
		* 参数:row,checked
	
	onBeforeExpand
		* 在节点展开之前触发，返回false可以取消展开节点的动作。
		* 参数:row

	onExpand
		* 在节点被展开的时候触发。
		* 参数:row
	
	onBeforeCollapse
		* 在节点折叠之前触发，返回false可以取消折叠节点的动作。
		* 参数:row
	
	onCollapse
		* 在节点被折叠的时候触发。
		* 参数:row
	
	onContextMenu
		* 在右键点击节点的时候触发。
		* 参数:e, row
	
	onBeforeEdit
		* 在用户开始编辑节点的时候触发。
		* 参数:row
	
	onAfterEdit
		* 在用户完成编辑的时候触发。
		* 参数:row,changes
	
	onCancelEdit
		* 在用户取消编辑节点的时候触发。
		* 参数:row
	

-------------------------
EasyUI-treegrid		方法 |
-------------------------
	'很多方法都使用'id'命名参数，而'id'参数代表树节点的值。 '

	options
		* 返回树形表格属性
	
	resize
		* 设置树形表格大小，options包含2个属性：
			width	//树形表格的新宽度。
			height	//树形表格的新高度。
	
	fixRowHeight
		* 修正指定的行高。
		* 参数:id
	
	load
		* 读取并显示首页内容
		* 参数:param
		* demo
			// 读取并发送请求参数
			$('#tg').treegrid('load', {
				q: 'abc',
				name: 'name1'
			});
	
	loadData
		* 读取树形表格数据。
		* 参数:data
	
	reload
		* 重新加载树形表格数据。如果'id'属性有值，将重新载入指定树形行，否则重新载入所有行。 
		* demo
			$('#tt').treegrid('reload', 2);	// 重新载入值为2的行
			$('#tt').treegrid('reload');	// 重新载入所有行
	
	reloadFooter
		* 重新载入页脚数据。
		* 参数:footer
	
	getData
		* 获取载入数据。
	
	getFooterRows
		* 获取页脚数据。
	
	getRoot
		* 获取根节点，返回节点对象。
	
	getRoots
		* 获取所有根节点，返回节点数组。
	
	getParent
		* 获取父节点。
		* 参数:id
	
	getChildren
		* 获取子节点
		* 参数:id
	
	getSelected
		* 获取选择的节点并返回它，如果没有节点被选中则返回null。
	
	getSelections
		* 获取所有选择的节点。
	
	getLevel
		* 获取指定节点等级。
		* 参数:id
	
	find
		* 查找指定节点并返回节点数据。
		* 参数:id
	
	select
		* 选择一个节点。
		* 参数:id
	
	unselect
		* 反选一个节点。
		* 参数:id
	
	selectAll
		* 选择所有节点。
	
	unselectAll
		* 反选所有节点。
	
	collapse
		* 这堆一个节点
		* 参数:id
	
	expand
		* 展开一个节点。
		* 参数:id
	
	collapseAll
		* 折叠所有节点。
		* 参数:id
	
	expandAll
		* 展开所有节点。
		* 参数:id
	
	expandTo
		* 打开从根节点到指定节点之间的所有节点。
		* 参数:id
	
	toggle
		* 节点展开/折叠状态触发器。
	
	append
		* 追加节点到一个父节点，'param'参数包含如下属性：
			parent	//父节点ID，如果未指定则追加到根节点。
			data	//数组，节点数据。
	
		* demo
			// 追加若干节点到选中节点的后面var node = $('#tt').treegrid('getSelected');
			$('#tt').treegrid('append',{
				parent: node.id,  // the node has a 'id' value that defined through 'idField' property
				data: [{
					id: '073',
					name: 'name73'
				}]
			});
	
	insert
		* 插入一个新节点到指定节点。'param'参数包含一下参数：
			before	//插入指定节点ID值之前。
			after	//插入指定节点ID值之后。
			data	//新节点数据。 
		* demo
			// 插入一个新节点到选择的节点之前
			var node = $('#tt').treegrid('getSelected');
			if (node){
				$('#tt').treegrid('insert', {
					before: node.id,
					data: {
						id: 38,
						name: 'name38'
					}
				});
			}
	
	remove
		* 移除一个节点和他的所有子节点。
		* 参数:id
	
	pop
		* 弹出并返回节点数据以及它的子节点之后删除。
		* 参数:id

	refresh
		* 刷新指定节点。
		* 参数:id
	
	update
		* 更新指定节点。'param'参数包含以下属性：
			id		//要更新的节点的ID。
			row		//新的行数据
	
		* demo
			$('#tt').treegrid('update',{
				id: 2,
				row: {
					name: '新名称',
					iconCls: 'icon-save'
				}
			});
	
	beginEdit
		* 开始编辑一个节点。
		* 参数:id
	
	endEdit
		* 结束编辑一个节点。
		* 参数:id
	
	cancelEdit
		* 取消编辑一个节点。
		* 参数:id
	
	getEditors
		* 获取指定行编辑器。每个编辑器都包含以下属性：
			actions	//编辑器执行的动作。
			target	//目标编辑器的jQuery对象。
			field	//字段名称。
			type	//编辑器类型。
	
	getEditor
		* 获取指定编辑器，'param'参数包含2个属性：
			id		//行节点ID。
			field	//字段名称。
	
	showLines
		* 显示treegrid行。



	
