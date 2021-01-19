------------------------
EasyUI-Pagination		|
------------------------
	# 分页
	# 属性
		easyui-pagination
	# 继承关系
		linkbutton

------------------------
EasyUI-Pagination属性	|
------------------------
	total
		* 总记录数,在分页空间初始化的时候赋值
		* 参数是 Number

	pageSize
		* 页面大小,也就是每页显示多少条记录
	
	pageNumber
		* 在分页控件创建的时候显示页数
	
	pageList
		* 用户可以改变的页面大小
		* 就是用户可以控制每页显示多少条数据的选项
		* demo
			$('#pp').pagination({
				pageList: [10,20,50,100]
			});
	
	loading
		* 定义数据是否正在载入
		* 参数是 boolean

	buttons
		* 定义一堆自定义的按钮
		* 参数是一个数组
			[
				{
					//摁钮的图标样式
					iconCls:'icon-add',
					handler:function(){
						//摁钮点击事件
					}
				},'-',		//分割线
				{
					iconCls:'icon-save',
					handler:function(){}
				}
			]
		* 参数也可以是一个选择器
			buttons:'#btnDiv'
		
	layout
		* 分页控件布局属性
		* 布局选项可以包含一个或多个值：
			1) list		//页面显示条数列表。
			2) sep		//页面按钮分割线。
			3) first	//首页按钮。
			4) prev		//上一页按钮。
			5) next		//下一页按钮。
			6) last		//尾页按钮。
			7) refresh	//刷新按钮。
			8) manual	//手工输入当前页的输入框。
			9) links	//页面数链接。
		* demo
			$('#pp').pagination({
				layout:['first','links','last']
			});
			
	links
		* 参数是一个 Number
		* 该链接数仅在“links”项包含在“layout”中的时候有效。
	
	showPageList
		* 是否显示页面导航列表
	
	showRefresh
		* 是否显示刷新摁钮
	
	beforePageText
		* 在输入组件之前显示一个 label 标签
	
	afterPageText
		* 在输入组件之后显示一个 label 标签
	
	displayMsg
		* 显示页面信息
		* 参数是一个 String


------------------------
EasyUI-Pagination事件	|
------------------------
	onSelectPage
		* 用户选择一个新页面的时候触发。回调函数包含2个参数
			pageNumber		//新的页数。
			pageSize:		//页面大小（每页显示的条数）。 
		* demo
			$('#pp').pagination({
				onSelectPage:function(pageNumber, pageSize){
					$(this).pagination('loading');
					alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
					$(this).pagination('loaded');
				}
			});
	
	onBeforeRefresh
		* 在点击刷新按钮刷新之前触发，返回false可以取消刷新动作。
		* 参数:pageNer, pageSizeumb
	
	onRefresh
		* 在刷新之后触发
		* 参数:pageNer, pageSizeumb
	
	onChangePageSize
		* 在页面更改页面大小的时候触发
		* 参数:pageSize


------------------------
EasyUI-Pagination方法	|
------------------------
	options
		* 返回参数对象
	
	loading
		* 提醒控件正在加载中
	
	loaded
		* 提醒分页空间加载完成
	
	refresh
		* 刷新并显示分页栏信息,参数是一个配置对象
		* demo
			$('#pp').pagination('refresh');	// 刷新分页栏信息
			$('#pp').pagination('refresh',{	// 改变选项并刷新分页栏信息
				total: 114,
				pageNumber: 6
			});
	
	select
		* 选择一个新页面,页面索引从1开始
		* demo
			$('#pp').pagination('select');            // 刷新当前页
			$('#pp').pagination('select', 2);        // 选择第二页
	


	




