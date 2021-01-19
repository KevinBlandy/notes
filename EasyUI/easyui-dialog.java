-----------------------
EasyUI-dialog			|
-----------------------
	# 依赖关系
		window 
		linkbutton
	
	# 对话框

-----------------------
EasyUI-dialog属性		|
-----------------------
	'对话框窗口的属性扩展自window(窗口)，对话框窗口重新定义的属性如下：'
	title		
		* 标题
	collapsible
		* 是否有折叠摁钮
	minimizable
		* 是否有最小化摁钮
	maximizable
		* 是否有最大化摁钮
	resizable
		* 是否可以更改大小
	toolbar
		* 在顶部定义一堆工具摁钮,参数是一个数组
		* 参数也可以是一个一个选择器:'#tools'
		toolbar:[
			{
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(){
					//点击事件
					alert('edit');
				}
			}
		]
		toolbar:'tools'	//指定工具是由指定节点来提供

	buttons
		* 对话框底部摁钮
		* 参数是一个数组,或者是一个选择器
		buttons:[
			{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){...}
			}
		]
		buttons:'#buttons'
	

-----------------------
EasyUI-dialog事件		|
-----------------------
	'对话框窗口事件完全继承自window(窗口)。'

-----------------------
EasyUI-dialog方法		|
-----------------------
	'对话框窗口的方法扩展自window(窗口)，对话框窗口新增的方法如下'
	dialog
		* 返回外部对话窗口对象
