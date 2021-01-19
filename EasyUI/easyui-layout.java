----------------------------
EasyUI-layout				|
----------------------------
	* 依赖于panel
	* 使用 使用完整页面创建布局
		<body class="easyui-layout">   
			<div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>   
			<div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
			<div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
			<div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   
			<div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;"></div>   
		</body> 

	* 通过标签创建布局(给DIV添加:easyui-layout 属性)
		<div id="cc" class="easyui-layout" style="width:600px;height:400px;">   
			<div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>   
			<div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
			<div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
			<div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   
			<div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;"></div>   
		</div>  
	
	* 面板还可以嵌套使用

	* center 是其他的面板根据计算得到的.不给center是不行的

	# 继承关系
		panel
		resizable
 
		
	
----------------------------
EasyUI-layout属性			|
----------------------------
	fit
		* 如果该值为 true,则窗体会随着浏览器的大小而自动的缩放
		* 默认值为 false ,也就说窗体是不会随着浏览器大小进行缩放的
		* '当使用'body'标签创建布局的时候，整个页面会自动最大'

----------------------------
EasyUI-layout事件			|
----------------------------
	onCollapse 
		* 参数:region 在折叠区域面板的时候触发。

	onExpand 
		* 参数:region 在展开区域面板的时候触发。
		
	onAdd 
		* 参数:region 在新增区域面板的时候触发。 

	onRemove 
		* 参数:region 在移除区域面板的时候触发。 


	
----------------------------
EasyUI-layout方法			|
----------------------------
	resize
		* 设置布局的大小
	
	panel
		* 获取指定的模块
		* 参数可选:'north','south','east','west','center'
			var north = $('#body').layout('panel','north');
			north.panel({
				title:'啊哈哈哈'
			});

	collapse
		* 折叠指定的面板
		* 参数是 String,可选值:'north','south','east','west','center'
	
	expand
		* 展开指定的面板
		* 参数是 String,可选值:'north','south','east','west','center'
	
	add
		* 添加面板
		* 属性参数是一个'配置对象'，更多细节请查看选项卡面板属性。
	
	remove
		* 移除面板,可用值有：'north','south','east','west'。
	
	split
		* 分割指定的面板
		* 参数值 : 'north','south','east','west'。
	
	unsplit
		* 移除面板,可用值有：'north','south','east','west'。



----------------------------
EasyUI-区域面板属性			|
----------------------------
	'区域面板属性定义与panel组件类似，下面的是公共的和新增的属性：'
	title
		* 设置面板标题
	
	region
		* 定义面板的位置
		* 可选值:north, south, east, west, center
	
	border
		* 该值为true的时候,显示布局面板的边框

	split
		* 该值为 true 的时候,用户可以更改面板的大小
	
	iconCls
		* 指定面板的图标,，该图标将会显示到面板标题上。不多说
	
	href
		* 用于读取远程站点数据的URL连接,注意.仅仅只会读取<body>中的内容</body>,而且不包含  body
	
	collapsible
		* 是否显示折叠摁钮
	
	minWidth
		* 最小面板宽度
	
	minHeight
		* 最小面板高度
	
	maxWidth
		* 最大面板宽度
	
	maxHeight
		* 最大面板高度
	
	expandMode
		* 在点击折叠面板时候的扩展模式。可用值有："float","dock"和null
		float		//区域面板将展开并浮动在顶部，在鼠标焦点离开面板时会自动隐藏。
		dock		//区域面板将展开并钉在面板上，在鼠标焦点离开面板时不会自动隐藏。
		null		//什么也不会发生。
	
	collapsedSize
		* 定义折叠后的面板大小
	
	hideExpandTool
		* 为true时隐藏折叠面板上的扩展面板工具。（
	
	hideCollapsedContent
		* 为true时隐藏折叠面板上的标题栏。
	
	collapsedContent
		* 定义在折叠面板上要显示标题内容。
		* 该属性是一个 function,这个 function 有一个参数就是标题文本
		* demo
			collapsedContent: function(title){
				var region = $(this).panel('options').region;
				if (region == 'north' || region == 'south'){
					return title;
				} else {
					return '<div class="mytitle">'+title+'</div>';
				}
			}
	



	


	



	