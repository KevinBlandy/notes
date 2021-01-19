----------------------------
EasyUI-datagrid				|
----------------------------
	# DataGrid
	# DataGrid以表格形式展示数据，并提供了丰富的选择、排序、分组和编辑数据的功能支持。DataGrid的设计用于缩短开发时间，并且使开发人员不需要具备特定的知识。它是轻量级的且功能丰富。单元格合并、多列标题、冻结列和页脚只是其中的一小部分功能。
	# 依赖关系
		panel
		resizable
		linkbutton
		pagination


----------------------------
EasyUI-datagrid属性			|
----------------------------
	columns
		* 重点,设置数据的显示.
		* 它具体的属性看'columns'属性
		* 值是一个二维数组,每一个数组,都是一个表头.支持多级表头
			columns:[
						[	
							{field:'labelid',title:'标题主键',width:100,align:'center',sortable:true},    
							{field:'name',title:'标签名称',width:100,align:'center',sortable:true},    
							{field:'code',title:'标签码',width:100,align:'center',sortable:true},
							{field:'createdate',title:'创建时间',width:100,align:'center',sortable:true},    
							{field:'modifydate',title:'修改时间',width:100,align:'center',sortable:true},    
							{field:'createuser',title:'创建用户',width:100,align:'center',sortable:true},
							{field:'status',title:'记录状态',width:100,align:'center',sortable:true},    
							{field:'remark',title:'备注',width:100,align:'center',sortable:true}    
						]
					]
		* field:JSON的字段
		* title:标题/名称
		* align:文字表格内所在位置
		* sortable:是否是可排序字段

	frozenColumns
		* 跟上面一样,不过这些列会被冻结
		* 冻结列,有这个属性的话,就不能有'fitColumns'这个属性,或者值为false
		* 里面指定的列都会被冻结.下面不会有滚动条出现.其他列如果是超出了屏幕,就会出现滚动条
			frozenColumns:[
				[
					{field:'labelid',title:'标题主键',width:100,align:'center',sortable:true},    
					{field:'name',title:'标签名称',width:100,align:'center',sortable:true},    
					{field:'code',title:'标签码',width:100,align:'center',sortable:true},
					{field:'createdate',title:'创建时间',width:100,align:'center',sortable:true},   
				]
			]

	fitColumns
		* true / false
		* true:表格下方不会出现滚动条,表格渲染完毕会自适应容器的宽度
		* 如果说列数太多,这个就建议是 false 了
	
	resizeHandle
		* 调整列的位置
		* 可用的值有：'left','right','both'。
		* 在使用'right'的时候用户可以通过拖动右侧边缘的列标题调整列，等等。
	
	autoRowHeight
		* 定义设置行的高度.，根据该行的内容。设置为false可以提高负载性能。
		* 就是说当一行内容过多后,是不是要换行显示
	
	toolbar
		* 值是一个数组,定义一大堆的工具.以摁钮形式出现
		* 它的参数还可以是一个选择器.指定某一个'div'作为toolbar的形式存在
		* toolbar:[
					{
						text:'添加',
						iconCls:'icon-add',
						handler:function(){
							alert('添加摁钮')
						}
					},'-',
					{
						text:'编辑',
						iconCls:'icon-edit',
						handler:function(){
							alert('编辑摁钮')
						}
					},'-',
					{
						text:'删除',
						iconCls:'icon-remove',
						handler:function(){
							alert('删除摁钮')
						}
					},
				]
	
	striped
		* 是否显示斑马线效果
	
	method
		* 请求远程数据的请求类型
	
	nowrap
		* 如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
	
	idField
		* 指定数据中哪一个数据是记录的 primarykey
		* 只要有了这个,你可以选择N条记录,就算是翻页.也能给你记住.统一的操作.跨页删除
	
	data
		* 数据加载
		* demo
			$('#dg').datagrid({
				data: [
					{f1:'value11', f2:'value12'},
					{f1:'value21', f2:'value22'}
				]
			});
	
	loadMsg
		* 加载数据时候的提示文字
	
	pagination
		* 如果该值为 true,则在底部显示一个 分页栏
	
	rownumbers
		* 如果该值为 true,则显示行号
	
	singleSelect
		* 如果该值为 true,则值只允许单选一行
	
	ctrlSelect
		* 在启用多行选择的时候允许使用Ctrl键+鼠标点击的方式进行多选操作。
	
	checkOnSelect
		* 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。
		* 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
	
	selectOnCheck
		* 如果为true，单击复选框将永远选择行。
		* 如果为false，选择行将不选中复选框。
	
	pagePosition
		* 定义分页栏的位置
		* 可选值:'top','bottom','both'
	
	pageNumber
		* 在设置分页属性的时候初始化页码。
	
	pageSize
		* 在设置分页属性的时候初始化页面大小。
	
	pageList
		* 值是一个数组,表示可以有哪些显示(数量)选择
		* pageList:[10,20,30,40,50,60,70,80,90,100]
	
	queryParams
		* 在请求远程数据的时候,额外的提交的参数、
		* $('#table').datagrid({
			queryParams:{
				key:value,
				key:value
			}
		});
	
	sortName
		* 定义排序的列
	
	sortOrder
		* 定义排序方法.值是'asc'或'desc'。
	
	multiSort
		* 是否运行多列排序
	
	remoteSort
		* 是否从服务器对数据进行排序。
	
	showHeader
		* 定义是否显示行头
	
	showFooter
		* 定义是否显示页脚
	
	scrollbarSize
		* 滚动条的宽度(当滚动条是垂直的时候)或高度(当滚动条是水平的时候)。
	
	rowStyler
		* 当一些数据,需要'特别引人注目',的就可以通过该属性完成
		* 返回样式如'background:red'。带2个参数的函数：
			index		//该行索引从0开始
			row			//与此相对应的记录行。 
		* demo
			$('#dg').datagrid({
				rowStyler: function(index,row){
					if (row.listprice>80){
						return 'background-color:#6293BB;color:#fff;';
					}
				}
			});

			$('#dg').datagrid({
				rowStyler: function(index,row){
					if (row.listprice>80){
						return 'rowStyle';    // rowStyle是一个已经定义了的ClassName(类名)
					}
				}
			});
	
	loader
		* 定义如何从远程服务器加载数据。返回false可以放弃本次请求动作
		* 该函数接受以下参数：
			param			//参数对象传递给远程服务器。
			success(data)	//当检索数据成功的时候会调用该回调函数。
			error()			//当检索数据失败的时候会调用该回调函数。
	
	loadFilter
		* 返回过滤数据显示。该函数带一个参数'data'用来指向源数据（即：获取的数据源，比如Json对象）。您可以改变源数据的标准数据格式。这个函数必须返回包含'total'和'rows'属性的标准数据对象。 
		* demo
			// 从Web Service（asp.net、java、php等）输出的JSON对象中移除'd'对象
			$('#dg').datagrid({
				loadFilter: function(data){
					if (data.d){
						return data.d;
					} else {
						return data;
					}
				}
			});
	
	editors
		* 定义编辑行的时候使用的编辑器
	
	view
		* 定义DataGrid的视图。




----------------------------
EasyUI-列属性				|
----------------------------
	# DataGrid列是一个数组对象，该元素也是一个数组对象。元素数组里面的元素是一个配置对象，它用来定义每一个列字段。 
	
	title
		* 列标题文本,比方 :name
	
	field
		* 列字段名称,比方 :kevin
	
	width
		* 列的宽度
		* 如果没有定义，宽度将自动扩充以适应其内容。
	
	rowspan
		* 指明将占用多少行单元格（合并行）。
	
	colspan
		* 指明将占用多少列单元格（合并列）。
	
	align
		* 指明如何对齐列数据。可以使用的值有：'left','right','center'。
	
	halign
		* 指明如何对齐列标题。可以使用的值有：'left','right','center'。
		* 如果没有指定，则按照align属性进行对齐。
	
	sortable
		* 如果该值为 true,则该列允许排序
	
	order
		* 默认的排序数列,只能是'asc'或'desc'
	
	resizable
		* 如果为true，允许列改变大小。
	
	fixed
		* 如果为true，在"fitColumns"设置为true的时候阻止其自适应宽度。
	
	hidden
		* 如果为true，则隐藏列。
	
	checkbox
		* 如果为true，则显示复选框。该复选框列固定宽度。
	
	formatter
		* 单元格formatter(格式化器)函数，带3个参数：
			value	//字段值。
			row		//行记录数据。
			index	//行索引。 
	
	styler
		* 单元格styler(样式)函数，返回如'background:red'这样的自定义单元格样式字符串。该函数带3个参数：
				value	//字段值。
				row		//行记录数据。
				index:	//行索引。 
		
		* demo
			$('#dg').datagrid({
				columns:[[
					{field:'listprice',title:'List Price', width:80, align:'right',
						styler: function(value,row,index){
							if (value < 20){
								return 'background-color:#ffee00;color:red;';
							}
						}
					}
				]]
			});

	sorter
		* 用来做本地排序的自定义字段排序函数，带2个参数：
			a		//第一个字段值。
			b		//第二个字段值。 
		* demo
			$('#dg').datagrid({
				remoteSort: false,
				columns: [[
					{field:'date',title:'Date',width:80,sortable:true,align:'center',  
						sorter:function(a,b){  
							a = a.split('/');  
							b = b.split('/');  
							if (a[2] == b[2]){  
								if (a[0] == b[0]){  
									return (a[1]>b[1]?1:-1);  
								} else {  
									return (a[0]>b[0]?1:-1);  
								}  
							} else {  
								return (a[2]>b[2]?1:-1);  
							}  
						}  
					}
				]]
			});
	editor
		* 指定编辑类型
		* 指明编辑类型。当字符串指明编辑类型的时候，对象包含2个属性：
			type
				字符串，该编辑类型可以使用的类型有：text,textarea,checkbox,numberbox,validatebox,datebox,combobox,combotree。
			options
				对象，object, 该编辑器属性对应于编辑类型。 undefined .其实就是上面的属性的属性
			
	
----------------------------
EasyUI-Editor				|
----------------------------	
	'每一个编辑器都有下面的动作：'
	init
		* 初始化编辑器,并返回对象
		* 参数:container, options
	
	destroy
		* 如果有必要,销毁编辑器
		* 参数:target
	
	getValue
		* 从编辑器中获取值
		* 参数:target
	
	setValue
		* 向编辑器中写入值
		* 参数:target , value

	resize
		* 如果有必要,调整编辑器大小
		* 参数:target , width

----------------------------
EasyUI-DataGrid View		|
----------------------------
	'该视图是一个对象，将告诉DataGrid如何渲染行。该对象必须定义下列函数：'
	render
		* 数据加载时调用。
		* 具备三个参数:
			target		//DOM对象，DataGrid对象。
			container	//行容器。
			frozen		//指明如何渲染冻结容器。
	
	renderFooter
		* 这是一个选择函数来渲染行页脚。
		* 参数:target, container, frozen
	
	renderRow
		* row 这是一个属性功能，将调用render函数。 
		* 参数:target, fields, frozen, index, row
		
	refreshRow
		* 定义如何刷新指定的行。
		* 参数:target, index
	
	onBeforeRender
		* 在视图被呈现之前触发
		* 参数:target, rows
	
	onAfterRender
		* 在视图呈现之后触发
		* 参数:target

	
----------------------------
EasyUI-datagrid事件			|
----------------------------
	onLoadSuccess
		* 在数据加载成功的时候触发
		* 参数:data
	
	onLoadError
		* 在远程数据加载异常的时候触发
	
	onBeforeLoad
		* 在载入请求数据数据之前触发，如果返回false可终止载入数据操作。
	
	onClickRow
		* 在用户点击一行的时候触发，参数包括：
			index	//点击的行的索引值，该索引值从0开始。
			row		//对应于点击行的记录。
	
	onDblClickRow
		* 在用户双击一行的时候触发,参数包括：
			index	//点击的行的索引值，该索引值从0开始。
			row		//对应于点击行的记录。
		
	onClickCell
		* 在用户单击一个单元格的时候触发
		* 参数:index, field, value
	
	onDblClickCell
		* 在用户双击一个单元格的时候触发
		* 参数:index, field, value
		* demo
			// 在双击一个单元格的时候开始编辑并生成编辑器，然后定位到编辑器的输入框上
			$('#dg').datagrid({
				onDblClickCell: function(index,field,value){
					$(this).datagrid('beginEdit', index);
					var ed = $(this).datagrid('getEditor', {index:index,field:field});
					$(ed.target).focus();
				}
			});
	
	onBeforeSortColumn
		* 在用户排序一个列之前触发
		* 返回 false,可以阻止该事件
		* 参数:sort, order
		
	onSortColumn
		* 在用户排序一个列的时候触发
		* sort, order
	
	onResizeColumn
		* 在用户调整列大小的时候触发
		* 参数:field, width
	
	onBeforeSelect
		* 在用户选择一行之前触发.返回false则取消该动作
	
	onSelect
		* 在用户选择一行的时候触发，参数包括：
			index	//选择的行的索引值，索引从0开始。
			row		//对应于取消选择行的记录。
	
	onBeforeUnselect
		* 在用户取消选择一行的的时候触发,返回false则取消该动作。
		* 参数:index, row
	
	onUnselect
		* 在用户取消选择一行之前触发,，参数包括：
			index		//选择的行的索引值，索引从0开始。
			row			//对应于取消选择行的记录。
	
	onSelectAll
		* 在用户选择所有行的时候触发
		* 参数:rows
	
	onUnselectAll
		* 在用户取消选择所有行的时候触发
		* 参数:rows
	
	onBeforeCheck
		* 在用户校验一行之前触发,返回false则取消该动作。
		* 参数:index, row
	
	onCheck
		* 在用户勾选一行的时候触发
		* 参数:index, row
	
	onBeforeUncheck
		* 在用户取消校验一行的时候触发
		* 参数:index, row
	
	onUncheck
		* 在用户取消勾选一行的时候触发，参数包括：
			index	//选中的行索引，索引从0开始。
			row		//对应于取消勾选行的记录。

	onCheckAll
		* 在用户勾选所有行的时候触发。
		* 参数:rows
	
	onUncheckAll
		* 在用户取消勾选所有行的时候触发
		* 参数:rows
	
	onBeforeEdit
		* 在用户开始编辑一行的时候触发，参数包括：
			index	//编辑行的索引，索引从0开始。
			row		//对应于编辑行的记录。
	
	onBeginEdit
		* 在一行进入编辑模式的时候触发
		* 参数:index, row
	
	onEndEdit
		* 在完成编辑,但是编辑器还没销毁的时候触发
		* 参数:index, row, changes

	onAfterEdit
		* 在用户完成编辑一行的时候触发，参数包括：
			index		//编辑行的索引，索引从0开始。
			row			//对应于完成编辑的行的记录。
			changes		//更改后的字段(键)/值对。
	
	onCancelEdit
		* 在用户取消编辑一行的时候触发，参数包括：
			index		//编辑行的索引，索引从0开始。
			row			//对应于编辑行的记录。
	
	onHeaderContextMenu
		* 在鼠标右击DataGrid表格头的时候触发。
		* 参数:e, field
	
	onRowContextMenu
		* 右键事件,就是说右击某一行数据,会触发的事件 - 一般都是做右击菜单
		* 有三个参数:e:事件,rowIndex:行号,从0开始.rowData:右击的那一行的数据
			onRowContextMenu : function(e,rowIndex,rowData){
				//e:是事件.需要通过它来阻止浏览器的右键弹窗
				//阻止浏览器右键事件
				e.preventDefault();
				//取消所有选择
				$(this).datagrid('unselectAll');
				//选择指定的行(肯定就是点击的那一行)
				$(this).datagrid('selectRow',rowIndex);
				//找到菜单,执行'show'方法
				$('#menu').menu('show',{
					//设置菜单出现的位置,该值通过e参数来获取
					left:e.pageX,
					top:e.pageY
				})
			}
			<div id="menu">

			</div>
	

	
----------------------------
EasyUI-datagrid方法			|
----------------------------
	options
		* 返回属性对象
	
	getPager
		* 返回页面对象
	
	getPanel
		* 返回面板对象
	
	getColumnFields
		* 返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
		* demo
			var opts = $('#dg').datagrid('getColumnFields');               // 获取解冻列
			var opts = $('#dg').datagrid('getColumnFields', true);       // 获取冻结列

	getColumnOption
		* 返回指定列属性
		* 参数是:field
	
	resize
		* 做调整和布局
		* 参数是:param

	load
		* 重新加载表单数据.以当前页为页码
		* 可以提交一些参数到后台,在执行表单查询的时候会用到.一旦提交了参数,则页码是从1开始
			$('#table-user').datagrid('load',{
				key:value,		//提交给后台的参数
				key:value
			});
	
	reload
		* 跟上面其实是一样的
		* '这个方法会记住当前页,不管传递什么参数.都是以当前页为页码进行查询'
	
	reloadFooter
		* 重载页脚行。代码示例： 
		// 更新页脚行的值并刷新var rows = $('#dg').datagrid('getFooterRows');
		rows[0]['name'] = 'new name';
		rows[0]['salary'] = 60000;
		$('#dg').datagrid('reloadFooter');

		// 更新页脚行并载入新数据$('#dg').datagrid('reloadFooter',[
			{name: 'name1', salary: 60000},
			{name: 'name2', salary: 65000}
		]);
	
	loading
		* 显示载入状态
	
	loaded
		* 隐藏载入状态
	
	fitColumns
		* 使列自动展开/收缩到合适的DataGrid宽度。
	
	fixColumnSize
		* 固定列大小。如果'field'参数未配置，所有列大小将都是固定的。
		* demo
			$('#dg').datagrid('fixColumnSize', 'name');        // 固定'name'列大小
			$('#dg').datagrid('fixColumnSize');                    // 固定所有列大小
	
	fixRowHeight
		* 固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
	
	freezeRow
		* 冻结指定行，当DataGrid表格向下滚动的时候始终保持被冻结的行显示在顶部
	
	autoSizeColumn
		* 自动调整列宽度以适应内容。
	
	loadData
		* 加载本地数据，旧的行将被移除。
		* 参数:data
	
	getData
		* 返回加载完毕后的对象
	
	getRows
		* 返回当前页所有行
	
	getFooterRows
		* 返回页脚行。
	
	getRowIndex
		* 返回指定行的索引号，该行的参数可以是一行记录或一个ID字段值。
	
	getChecked
		* 在复选框呗选中的时候返回所有行
	
	getSelected
		* 返回第一个被选中的行或如果没有选中的行则返回null.
	
	getSelections
		* 返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
	
	clearSelections
		* 清除所有选择的行。

	clearChecked
		* 清除所有勾选的行
	
	scrollTo
		* 滚动到指定的行。
	
	gotoPage
		* 跳转到指定页
		* demo
			// 跳转到第3页
			$('#dg').datagrid('gotoPage', 3);

			// 跳转到第3页并执行回调函数
			$('#dg').datagrid('gotoPage', {
				page: 3,
				callback: function(page){
					console.log(page)
				}
			})
	
	highlightRow
		* 高亮一行。
		* 参数:index
	
	selectAll
		* 选择当前页中所有行
	
	unselectAll
		* 取消选择所有当前页中所有的行。
	
	selectRow
		* 选择一行，行索引从0开始。
	
	selectRecord
		* 通过ID值参数选择一行。
		* 参数为 ID
	
	unselectRow
		* 取消选择一行
	
	checkAll
		* 勾选当前页中的所有行。
	
	uncheckAll
		* 取消勾选当前页中的所有行
	
	checkRow
		* 勾选一行，行索引从0开始
	
	uncheckRow
		* 取消勾选一行，行索引从0开始。
	
	beginEdit
		* 开启编辑模式,参数:index
	
	endEdit
		* 结束编辑一行,参数:index
	
	cancelEdit
		* 取消编辑行,参数:index
	
	getEditors
		* 获取指定行的编辑器。每个编辑器都有以下属性：
			actions		//编辑器可以执行的动作，同编辑器定义。
			target		//目标编辑器的jQuery对象。
			field		//字段名称。
			type		//编辑器类型，比如：'text','combobox','datebox'等。
	

	getEditor
		* 获取指定编辑器，options包含2个属性：
			index	//行索引。
			field	//字段名称。 
		* demo
			// 获取日期输入框编辑器并更改它的值
			var ed = $('#dg').datagrid('getEditor', {index:1,field:'birthday'});
			$(ed.target).datebox('setValue', '5/4/2012');
	
	refreshRow
		* 刷新指定行,参数为:index
	
	validateRow
		* 验证指定行,参数是:index.如果验证通过.则返回 true
	
	updateRow
		* 更新指定行，参数包含下列属性：
			index		//执行更新操作的行索引。
			row			//更新行的新数据。 
		* demo
			$('#dg').datagrid('updateRow',{
				index: 2,
				row: {
					name: '新名称',
					note: '新消息'
				}
			});
	
	appendRow
		* 追加一个新行。新行将被添加到最后的位置。 
		* 参数:row
		* demo
			$('#dg').datagrid('appendRow',{
				name: '新名称',
				age: 30,
				note: '新消息'
			});
	
	insertRow
		* 插入一个新行，参数包括一下属性：
			index	//要插入的行索引，如果该索引值未定义，则追加新行。
			row		//行数据。
		* demo
			$('#dg').datagrid('insertRow',{
				index: 1,	// 索引从0开始
				row: {
					name: '新名称',
					age: 30,
					note: '新消息'
				}
			});
	
	deleteRow
		* 删除行
		* 参数:index
	
	getChanges
		* 从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，
		* 可以使用的值有：inserted,deleted,updated等。
		* 当类型参数未配置的时候返回所有改变的行。
	
	acceptChanges
		* 提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
	
	rejectChanges
		* 回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
	
	mergeCells
		* 合并单元格，options包含以下属性：
			index	//行索引。
			field	//字段名称。
			rowspan	//合并的行数。
			colspan	//合并的列数。
	
	showColumn
		* 显示指定的列
		* 参数:field
	
	hideColumn
		* 隐藏指定的列
		* 参数:field
	
	sort
		* 排序datagrid表格。
		* 参数:param
		* demo
			$('#dg').datagrid('sort', 'itemid');    // 排序一个列
			$('#dg').datagrid('sort', {				// 指定了排序顺序的列
				sortName: 'productid',
				sortOrder: 'desc'
			});



	

	
----------------------------
EasyUI-datagrid案例			|
----------------------------
	添加新的记录
		1,设置toolbar
		2,点击'添加',显示隐藏的panel,里面是一个form表单
		3,添加完毕后数据后.提交->校验.
		4,添加成功后.清空表单数据 -> 隐藏表单 -> 使用datagrid reload(确保是在当前页) 方法重新载入数据
		5,messager弹窗提示
	
	编辑一条记录
		1,设置toobar
		2,点击'编辑',调用 datagrid 的 'getSelections'方法,获取到选择的行.
			* 这个方法返回是个数组.如果是空数组.就是没选择,弹出警告
			* 如果length != 1,那就是选择了多行.弹出警告
		3,获取到这个条记录的id值,从后台加载数据
		4,load 进表单中.
		5,编辑OK,提交 - > 校验
		6,使用datagrid reload(确保是在当前页) 方法重新载入数据
		7,messager弹窗提示
	
	删除N条记录	
		1,设置toobar
		2,点击'删除',调用 datagrid 的 'getSelections'方法,获取到选择的行.
			* 如果返回的是空数组.则弹出警告:'必须选择一行'
		3,循环遍历.获取ID值,拼凑字符串.(记得删除最后一个分割字符串)传给后台.后台切割.删除即可
		4,使用datagrid reload(确保是在当前页) 方法重新载入数据
		5,messager弹窗提示
		6,注意,删除成功后.一定要记得清空:idfiled
			* 当你选择了一了一条记录后,框架会记录这条记录的ID值.就算你把这记录给删除了.但是这个ID值仍然在内存中
			* 当你再次执行某些选择操作的时候.这个ID值还会被带上.
			$('#users').datagrid('unselectAll');		//执行取消选择当前页中的所有行
		
	

	# 返回数据统一格式
		loadFilter:function (data) {
			if(!data.success){
				TD.showAlert(data.message);
				//返回空元素
				return {total:0,rows:[]};
			}
			return data.data;
		},
