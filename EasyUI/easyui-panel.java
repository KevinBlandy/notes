------------------------
EasyUI-pnael			|
------------------------
	# 基础的面板
	# 很多组键都是继承于它
	# 面板,作为承载其他内容的容器(layout,tabs,accordion等),还提供了折叠,关闭,最大化,最小化和自定义行为.
	# 它可以很容易的嵌入到WEB页面的任何位置
	# 属性
		easyui-panel
	
------------------------
EasyUI-控件属性			|
------------------------
	id
		* 给panel设置一个唯一标识的ID值
		* 参数是String
	
	title
		* 面板顶部显示的标题
	
	iconCls:'icon-reload'
		* 在右上角出现一个小图标.
		* 值是一个class属性,是从:icon.css中获取的
	
	width
	height
		* 宽高不多说.上面是两个属性

	left
	top
		* 上面也是两个属性,设置面板距离左边(x),以及上面(y)的距离

	cls
		* 指定一个css类到面板
	
	headerCls
		* 添加一个css类到面板的顶部
	
	bodyCls
		* 添加一个css类到面板的主体


	style
		* 添加一个样式到面板主体
		* 注意,值是一个对象
			style:{borderWidth:2}

	fit:true
		* 默认值是false,自适应.撑到父级元素一样大
	
	border
		* 是否显示面板的边框

	doSize
		* 如果设置为true,在面板被创建的时候将重置大小和重新布局。
	
	noheader
		* 如果设置为true，那么将不会创建面板标题。
	
	content
		* 面板的主体内容
	
	collapsible
		* 是否显示可折叠摁钮
	
	minimizable
		* 是否显示最小化摁钮
	
	maximizable
		* 是否显示最大化摁钮
	
	closable
		* 是否显示关闭摁钮
	
	tools
		* 自定义工具栏,里面是一个数组对象
		* tools:[
				{
					iconCls:'icon-add',
					handler:function(){alert('add')}
				},{
					iconCls:'icon-edit',
					handler:function(){alert('edit')}
				}]

	header
		* 定义面板的标题,里面的值是一个select选择器.指定标题是哪个
		* demo
			<div class="easyui-panel" style="width:300px;height:200px"
					title="我的面板">
				<header>面板标题</header>
			</div>
	
	footer
		* 定义面板页脚
			<div class="easyui-panel" style="width:300px;height:200px"
				title="我的面板" data-options="iconCls:'icon-ok',tools:[
                        {
                                    iconCls:'icon-add',
                                    handler:function(){alert('添加')}
                        },{
                                    iconCls:'icon-edit',
                                    handler:function(){alert('编辑')}
                        }]">
			</div>
	
	openAnimation
		* 定义打开面板的动画
		* 可选值:'slide','fade','show'
	
	closeAnimation
		* 定义关闭面板的动画
		* 可选值:'slide','fade','show'

	openDuration
		* 定义打开面板的持续时间.参数是 Number
	
	closeDuration
		* 定义关闭面板的持续事件.参数是 Number
	
	collapsed
		* 定义是否在初始化的时候折叠面板
	
	minimized
		* 定义是否在初始化的是最小化面板
	
	maximized
		* 定义是否在初始化的时候最大化面板
	
	closed
		* 定义是否在初始化的时候关闭面板
	
	href
		* 从远程地址读取数据到面板
		* 注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
		* demo
			<div id="pp" class="easyui-panel" style="width:300px;height:200px"
					data-options="href='get_content.php',closed:true">
			</div>
			<a href="#" onclick="javascript:$('#pp').panel('open')">Open</a>
	

	cache
		* 如果值为true,在超链接载入时缓存面板内容。
	
	loadingMessage
		* 在加载远程数据的时候显示的信息
	
	extractor
		* 定义如何从ajax应答数据中提取内容，返回提取数据。
		* demo
			extractor: function(data){
				var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
				var matches = pattern.exec(data);
				if (matches){
					return matches[1];	// 仅提取主体内容,不包含<body></body>中的内容
				} else {
					return data;
				}
			}
	
	method
		* 请求远程地址时使用的请求方法
	
	queryParams
		* 在请求远程地址时额外添加的参数
	
	loader
		* 定义了如何从远程服务器加载内容页，该函数接受以下参数：
		*	param：参数对象发送给远程服务器。
			success(data)：在检索数据成功的时候调用的回调函数。
			error()：在检索数据失败的时候调用的回调函数。

------------------------
EasyUI-控件事件			|
------------------------
	onBeforeLoad
		* 在加载内容页之前触发，返回false将忽略该动作。
	
	onLoad
		* 在加载远程数据的时候触发
	
	onLoadError
		* 在加载数据异常的时候触发
	
	onBeforeOpen
		* 在打开面板之前触发,返回 false,可以阻止该动作
	
	onOpen
		* 在打开面板之后触发
	
	onBeforeClose
		* 在关闭面板之前触发,返回 false 可以取消关闭面板操作
		* <div class="easyui-panel" style="width:300px;height:200px;"
				title="My Panel" data-options="onBeforeClose:function(){return false}">
			面板将不能关闭
		</div>
	
	onClose
		* 在关闭面板之后触发
	
	onBeforeDestroy
		* 在面板销毁之前触发.返回 false 可以阻止该动作
	
	onDestroy
		* 在面板销毁之后触发
	
	onBeforeCollapse
		* 在面板折叠之前触发,返回 false 可以阻止该动作
	
	onCollapse
		* 在面板折叠之后触发
	
	onBeforeExpand
		* 在面板展开之前触发,返回 false 可以阻止该动作
	
	onExpand
		* 在面板展开之后触发
	
	onResize
		* 在调整了面板大小之后触发
		* 有两个参数:width, height 一个是新的宽度值和新的高度值
	
	onMove
		* 在面板移动之后触发
		* 有两个参数:left,top 一个是新的left(x)值,一个是新的top(y)值
	
	onMaximize
		* 在窗口最大化之后触发
	
	onRestore
		* 在窗口恢复到原始大小后触发
	
	onMinimize
		* 在窗口最小化之后触发
	

------------------------
EasyUI-控件方法			|
------------------------
	options
		* 返回属性对象
	
	panel
		* 返回面板对象
	
	header
		* 返回面板头部对象
	
	footer
		* 返回面板页脚对象
	
	body
		* 返回面板主体对象
	
	setTitle
		* 设置面板头部的标题文字
	
	open
		* 打开面板.具备一个 boolean 的参数.如果该值为 true,则会跳过面板的 onBeforeOpen 事件
	
	close
		* 关闭面板.具备一个 boolean 的参数.如果该值为 true,则会跳过面板的 onBeforeClose 事件
	
	destroy
		* 销毁面板.举报一个 boolean 的参数.如果该值为 true,则会跳过面板的 onBeforeDestory 事件
	
	clear
		* 清除面板内容
	
	refresh
		* 重新刷新面板内容.
		* 有一个参数.是一个URL,如果不指定.则使用旧的URL来进行刷新操作
		* demo
			// 打开面板且刷新其内容。
			$('#pp').panel('open').panel('refresh');
			// 刷新一个新的URL内容$('#pp').panel('open').panel('refresh','new_content.php');
	
	resize
		* 重置面板的大小
		* 参数是一个属性对象
		* 参数列表
			width		//新的面板宽度。
			height		//新的面板高度。
			left		//新的面板左边距位置。
			top			//新的面板上边距位置。 
		* demo
			$('#pp').panel('resize',{
					width: 600,
					height: 400
				});

	doLayout
		* 设置面板内子组件的大小。
	
	move
		* 移动面板到一个新的位置
		* 参数是一个对象.包含如下属性
			left		//新的左边距位置。
			top			//新的上边距位置。
	
	maximize
		* 最大化面板到容器的大小
	
	minimize
		* 最小化面板
	
	restore
		* 恢复最大化面板回到原来的大小和位置。
	
	collapse
		* 折叠面板主体
	
	expand
		* 展开面板主体
	

	




	


