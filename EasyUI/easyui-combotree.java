-----------------------
EasyUI-combotree		|
-----------------------
	# 树形下拉框结合选择控件和下拉树控件。它与combobox(下拉列表框)类似，但是将下拉列表框的列表替换成了树形控件。该控件支持树状态复选框，方便多选操作。 
	# 继承关系
		combo
		tree
-----------------------
EasyUI-combotree	属性|
-----------------------
	'树形下拉框属性扩展自combo(自定义下拉框)和tree(树形控件)，树形下拉框重写的属性如下：'
	editable
		* 定义用户是否可以直接把文本输入到字段中.默认值是 false

-----------------------
EasyUI-combotree	事件|
-----------------------
	'该控件的事件完全继承自combo(自定义下拉框)和tree(树形控件)。'

-----------------------
EasyUI-combotree	方法|
-----------------------
	'树形下拉框方法扩展自combo(自定义下拉框)，树形下拉框新增和重写的方法如下：'

	options
		* 返回属性对象
	
	tree
		* 返回树形对象
		* demo
			//以下的例子显示了如何得到选择的树节点。 
			var t = $('#cc').combotree('tree');	// 获取树对象
			var n = t.tree('getSelected');		// 获取选择的节点
			alert(n.text);

	loadData
		* 读取本地树形数据
		* demo
			$('#cc').combotree('loadData', [{
				id: 1,
				text: 'Languages',
				children: [{
					id: 11,
					text: 'Java'
				},{
					id: 12,
					text: 'C++'
				}]
			}]);
	
	reload
		* 再次请求远程数据,通过'url'参数重写请求的地址

	clear
		* 清空控件的值
	
	setValues
		* 设置组件值。值可以是节点的'id'值或'id'和'text' 键值对。
		* demo
			$('#cc').combotree('setValue', 6);
			// set value with {id,text} pairs
			$('#cc').combotree('setValue', {
				id: 61,
				text: 'text61'
			});

	setValue
		* 设置组件值数组
		* demo
			$('#cc').combotree('setValues', [1,3,21]);
			$('#cc').combotree('setValues', [1,3,21,{id:73,text:'text73'}]);
	

			
			
	