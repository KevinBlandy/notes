------------------------
EasyUI-searchbox		|
------------------------
	# 搜索框
	# 搜索框提示用户需要输入搜索的值。它可以结合一个菜单，允许用户选择不同的搜索类别。在用户按下回车键或点击组件右边的搜索按钮的时候会执行搜索操作。
	# 继承关系
		textbox
		menubutton

------------------------
EasyUI-searchbox属性	|
------------------------
	width
		* 组件宽度
	
	height
		* 组件高度
	
	prompt
		* 输入框中显示的提示信息
	
	value
		* 输入框中的值
	
	menu
		* 搜索类型菜单。每个菜单项都具备一下属性：
			name		//搜索类型名称。
			selected	//自定义默认选中的搜索类型名称。 
		* demo
			<input class="easyui-searchbox" style="width:300px" data-options="menu:'#mm'" />
			<div id="mm" style="width:150px">
				<div data-options="name:'item1'">Search Item1</div>
				<div data-options="name:'item2',selected:true">Search Item2</div>
				<div data-options="name:'item3'">Search Item3</div>
			</div>
	
	searcher
		* 在用户按下搜索按钮或回车键的时候调用searcher函数。
		* 该属性的参数是一个: function(value,name){}
	
	disabled
		* 是否禁用搜索框
	
------------------------
EasyUI-searchbox事件	|
------------------------


------------------------
EasyUI-searchbox方法	|
------------------------
	options
		* 返回属性对象
	
	menu
		* 返回搜索类型菜单对象。下面的例子显示了更改菜单项图标。
		* demo
			var m = $('#ss').searchbox('menu');                    // 获取菜单项var item = m.menu('findItem', 'Sports News');     // 查找菜单项
			// 更改菜单项图标
			m.menu('setIcon', {
				target: item.target,
				iconCls: 'icon-save'
			});
			// 选择搜索类型名
			$('#ss').searchbox('selectName', 'sports');
	
	textbox
		* 返回文本框对象
	
	getValue
		* 返回当前搜索值
	
	setValue
		* 设置新的搜索值
		* 参数是 String
	
	getName
		* 返回当前搜索类型名。

	selectName
		* 选择当前搜索类型名
		* 参数就是搜索类型名
		* demo
			$('#ss').searchbox('selectName', 'sports');

	destroy
		* 销毁该控件
	
	resize
		* 重置控件的宽度,参数是 Number
	
	disable
		* 禁用搜索框
	
	enable
		* 启用搜索框
	
	clear
		* 清除搜索框
	
	reset
		* 重置搜索框

