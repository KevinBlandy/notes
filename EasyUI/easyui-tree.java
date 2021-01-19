------------------------
Easyui-tree				|
------------------------
	# 树控件在web页面中一个将分层数据以树形结构进行显示。它提供用户展开、折叠、拖拽、编辑和异步加载等功能。
	# 继承关系
		draggable
		droppable

	# 创建tree的第一种方式
		1,直接写死代码
			<ul id="tt" class="easyui-tree">   
				//包含子菜单的一级菜单
				<li>   
					<span>Folder</span>   
					<ul>   
						//包含子菜单的二级菜单
						<li>   
							<span>Sub Folder 1</span>   
							<ul>   
								<li>   
									<span><a href="#">File 11</a></span>   
								</li>   
								<li>   
									<span>File 12</span>   
								</li>   
								<li>   
									<span>File 13</span>   
								</li>   
							</ul>   
						</li>   
						//不包含子菜单的二级菜单
						<li>   
							<span>File 2</span>   
						</li>   
						<li>   
							<span>File 3</span>   
						</li>   
					</ul>   
				</li>   
				//不包含子菜单的一级菜单
				<li>   
					<span>File21</span>   
				</li>   
			</ul>
		1,通过远程地址加载
			* 直接写一个死的<ul> -- <ul id="tt"> </ul>
			* 通过一个远程地址,请求tree的数据
			$(function(){
				$('#tt').tree({
					//请求地址
					url:'tree_data.json' ,
					lines:true,
				});
			});
	
	# easyui节点需要具备的属性
		id			节点ID，对加载远程数据很重要。
		text		显示节点文本。
		state		节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
		checked		表示该节点是否被选中。
		attributes	被添加到节点的自定义属性。
		children	一个节点数组声明了若干节点。
		attributes	这是一个对象属性,里面可以定义一些自定义的属性

		{
			id:"123",
			text:"节点名称",
			state:"opend",
			checked:true,
			attributes:{
				key1:"value",
				key2:"value"
			},
			children:[
				{
					id:"123",
					text:"节点名称",
					state:"opend",
				}	
			],
		}
					

------------------------
Easyui-属性				|
------------------------
	url
		* 定义远程加载 tree 数据的 url
	
	method
		* HTTP请求方式,默认POST
	
	animate
		* 定义节点在展开或折叠的时候是否显示动画效果。
	
	checkbox
		* 定义是否在每一个借点之前都显示复选框。
	
	cascadeCheck
		* 是否是级联选中,当你选中了父节点的任意子节点,那么父节点会是一个半选中状态.如果全部子节点都选中了,那么父节点就是一个全选中状态
		* 默认值为 false,也就是不级联.点谁,勾谁.其他的节点都没变化
	
	onlyLeafCheck
		* 是否只是在末节点之前显示复选框,其实就是说.只有最后的一级节点.会有复选框的存在
	
	lines
		* 是否显示虚线,默认值是 false,也就是不显示

	dnd
		* 就是可以任意的拖拽节点.把自己的儿子给别人,把别人的爸爸给我... ...

	data
		* 节点数据加载。 
		* demo
			$('#tt').tree({
				data: [{
					text: 'Item1',
					state: 'closed',
					children: [{
						text: 'Item11'
					},{
						text: 'Item12'
					}]
				},{
					text: 'Item2'
				}]
			});
	
	queryParams
		* 在请求远程数据的时候,额外提交的参数
		* 参数是一个对象
	
	formatter
		* 定义如何渲染节点的文本。
		* demo
			$('#tt').tree({
				formatter:function(node){
					return node.text;
				}
			});
	
	filter
		* 定义如何过滤本地展示的数据，返回true将允许节点进行展示。
		* function (q,node){}
	
	loader
		* 定义如何从远程服务器加载数据。返回false可以忽略本操作。该函数具备以下参数：\
			param			//发送到远程服务器的参数对象。
			success(data)	//当检索数据成功的时候调用的回调函数。
			error()			//当检索数据失败的时候调用的回调函数。
	
	loadFilter
		* 返回过滤过的数据进行展示。返回数据是标准树格式。该函数具备以下参数：
			data	//加载的原始数据。
			parent:	//DOM对象，代表父节点。 
	
	checked
		* 是否勾选当前节点,参数是:true/false
		* 前提肯定是开启了:checkbox : true


------------------------
Easyui-事件				|
------------------------
	很多事件的回调函数都包含'node'参数，其具备如下属性：
		id			//绑定节点的标识值。
		text		//显示的节点文本。
		iconCls		//显示的节点图标CSS类ID。
		checked		//该节点是否被选中。
		state		//节点状态，'open' 或 'closed'。
		attributes	//绑定该节点的自定义属性。
		target		//目标DOM对象。
	
	
	onClick
		* 在节点被点击的时候执行
		* 有一个参数:node,就是点击的这个节点的信息对象

			$('#tt').tree({
				onClick: function(node){
					alert(node.target);  // 在用户点击的时候提示
				}
			});

	onDblClick
		* 在节点被双击的时候触发
		* 有一个参数:Node,就是被双击的节点信息对象
			onDblClick:function(node){
				//双击打开/或者关闭节点节点
				$('#tt').tree('toggle',node.target);
			}
	
	onBeforeLoad
		* 在请求加载远程数据之前触发，返回false可以取消加载操作。
		* 参数:node, param

	onLoadSuccess
		* 在数据(节点)加载成功后触发
		* 可以通过这个来实现'异步全展开'的效果
		* 有两个参数:
			node		//传递给后台节点的值
			data		//就是后台响应给的值
		$('#tree').tree({
			//后台数据响应成功,会调用这个方法
			onLoadSuccess:function(node,data){
				/*
					执行展开所有节点
					而每次展开都要请求后台都会触发这个事件
					这个事件就递归执行了
				*/
				if(data){
					//遍历后台响应的节点
					$(data).each(function(index,d){
						/*
							如果是关闭状态,则执行打开操作
							打开操作,又会请求后台服务器.又会触发这个事件
							后台的逻辑:如果有子节点,就是 closed 状态,如果没子节点,则是 open 状态
						*/
						if(this.state == 'closed'){
							$('#tree').tree('expandAll');
						}
					});
				}
			}
		});

	onLoadError
		* 在远程数据加载失败的执行
		* 有一个参数:arguments
		* arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同。
		* arguments 其实就是服务器相应回来的JSON信息
		、

	onBeforeExpand
		* 在节点展开之前触发
		* 返回 false 可以取消该操作
		* 参数: node

	onExpand
		* 在节点展开的时候触发
		* 参数: node

	onBeforeCollapse
		* 在节点折叠之前触发，返回false可以取消折叠操作。
		* 参数: node

	onCollapse
		* 在节点折叠的时候触发。
		* 参数:node

	onBeforeCheck
		* 在用户点击勾选复选框之前触发，返回false可以取消选择动作。
		* 参数:node, checked

	onCheck
		* 在用户点击勾选复选框的时候触发。
		* 参数:node, checked
		* Demo
			//勾选的时候,如果有父级菜单,全部勾选
			//取消勾选的时候,如果有子菜单,全部取消勾选
			onCheck:function (node, checked) {
                    if(checked){
                        //勾选,存在父级菜单
                        var parent = $(this).tree('getParent',node.target);
                        if(parent != null){
                            $(this).tree('check',parent.target);
                        }
					}else {
                        //取消勾选,存在子菜单
                        var childrens = $(this).tree('getChildren',node.target);
                        if(childrens != null && childrens.length != 0){
                            for(var x = 0;x < childrens.length ; x++){
                                $(this).tree('uncheck',childrens[x].target);
                            }
                        }
					}
                },

	onBeforeSelect
		* 在用户点击勾选复选框之前触发，返回false可以取消选择动作
		* 参数:node, checked
	
	onSelect
		* 在用户选择节点的时候触发。
		* 参数:node
	

	onContextMenu
		* 右击节点的时候触发事件
		* 有两个参数:e:事件,node:被右击的节点
			onContextMenu:function(e,node){
				//阻止浏览器的右击事件
				e.preventDefault();
				//显示我们自定义的菜单
				$('#treeMenu').menu('show',{
					//菜单的位置是在鼠标点击的地方
					left:e.pageX,
					top:e.pageY
				});
			}
	
	onBeforeDrag
		* 在开始拖动节点之前触发，返回false可以拒绝拖动。
		* 参数:node
	
	onStartDrag
		* 在节点开始拖动的时候触发
		* 参数:node
	
	onStopDrag
		* 在节点停止拖动的时候除非
		* 参数:node

	onDragEnter
		* 在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。
			target		//释放的目标节点元素。
			source		//开始拖动的源节点。
	
	onDragOver
		* 在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。
			target		//释放的目标节点元素。
			source		//开始拖动的源节点。
	
	onDragLeave
		* 在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。
			target		//释放的目标节点元素。
			source		//开始拖动的源节点。		
	
	onBeforeDrop
		* 在拖动一个节点之前触发，返回false可以拒绝拖动。
			target	//释放的目标节点元素。
			source	//开始拖动的源节点。
			point	//表示哪一种拖动操作，可用值有：'append','top' 或 'bottom'。
	
	onDrop
		* 当节点位置被拖动时触发。
			target		//DOM对象，需要被拖动动的目标节点。
			source		//源节点。
			point		//表示哪一种拖动操作，可用值有：'append','top' 或 'bottom'。
	
	onBeforeEdit
		* 在编辑节点之前触发。
		* 参数:node
	
	onAfterEdit
		* 在节点编辑之后触发
		* 参数:node
	
	onCancelEdit
		* 在取消编辑操作的时候触发
	
	

------------------------
Easyui-方法				|
------------------------
	options
		* 返回树控件属性。
	
	loadData
		* 读取树控件数据。
		* 参数:data
	
	getNode
		* 获取指定节点对象。
		* 参数:target
	
	getData
		* 获取指定节点数据，包含它的子节点。
		* 参数:target
	
	reload
		* 重新载入树控件数据。
		* 参数:target
	
	getRoot
		* 获取通过“nodeEl”参数指定的节点的顶部父节点元素。
	
	getRoots
		* 获取所有根节点，返回节点数组。
	
	getParent
		* 获取父节点，'target'参数代表节点的DOM对象。
		* 参数:target
	
	getChildren
		* 获取所有子节点，'target'参数代表节点的DOM对象。
		* 参数:target
	
	getChecked
		* 获取所有选中的节点。
		* 'state'可用值有：'checked','unchecked','indeterminate'。
		* 如果'state'未指定，将返回'checked'节点。 
		* demo
			var nodes = $('#tt').tree('getChecked');	// get checked nodes
			var nodes = $('#tt').tree('getChecked', 'unchecked');	// 获取未选择节点
			var nodes = $('#tt').tree('getChecked', 'indeterminate');	// 获取不确定的节点
			译者注：（1.3.4新增获取方式）
			var nodes = $('#tt').tree('getChecked', ['unchecked','indeterminate']);
	
	getSelected
		* 获取选择节点并返回它，如果未选择则返回null。
	
	isLeaf
		* 判断指定的节点是否是叶子节点，target参数是一个节点DOM对象。
	
	find
		* 查找指定节点并返回节点对象。
		* 参数:id
		* demo
			// 查找一个节点并选择它
			var node = $('#tt').tree('find', 12);
			$('#tt').tree('select', node.target);
	
	select
		* 选择一个节点，'target'参数表示节点的DOM对象。
		* 参数:target
	
	check
		* 选中指定节点。
		* 参数:target
	
	uncheck
		* 取消选中指定节点。
		* 参数:target
	
	collapse
		* 折叠一个节点，'target'参数表示节点的DOM对象。
		* 参数:target
	
	expand
		* 展开一个节点
		* 'target'参数表示节点的DOM对象。
		在节点关闭或没有子节点的时候，节点ID的值(名为'id'的参数)将会发送给服务器请求子节点的数据。 
	
	collapseAll
		* 折叠所有节点。
		* 参数:target
	
	expandAll
		* 展开所有节点。
		* 参数:target
	
	expandTo
		* 打开从根节点到指定节点之间的所有节点。
		* 参数:target
	
	scrollTo
		* 滚动到指定节点。
		* 参数:target
	
	append
		* 追加若干子节点到一个父节点，param参数有2个属性：
			parent	//DOM对象，将要被追加子节点的父节点，如果未指定，子节点将被追加至根节点。
			data	//数组，节点数据。
	
	toggle
		* 打开或关闭节点的触发器，target参数是一个节点DOM对象。
	
	insert
		* 在一个指定节点之前或之后插入节点，'param'参数包含如下属性：
			before	//DOM对象，在某个节点之前插入。
			after	//DOM对象，在某个节点之后插入。
			data	//对象，节点数据。 
		
		* demo
			//下面的代码展示了如何将一个新节点插入到选择的节点之前：
			var node = $('#tt').tree('getSelected');
			if (node){
				$('#tt').tree('insert', {
					before: node.target,
					data: {
						id: 21,
						text: 'node text'
					}
				});
			}
			译者注：（1.3.4新增获取方式）var node = $('#tt').tree('getSelected');
			if (node){
				$('#tt').tree('insert', {
					before: node.target,
					data: [{
						id: 23,
						text: 'node23'
								},{
							text: 'node24',
									state: 'closed',
						children: [{
						text: 'node241'
						},{
						text: 'node242'
						}]
								}]
				});
			}
	
	remove
		* 移除一个节点和它的子节点，'target'参数是该节点的DOM对象。
	
	pop
		* 移除一个节点和它的子节点，该方法跟remove方法一样，不同的是它将返回被移除的节点数据。
	
	update
		* 更新指定节点。'param'参数包含以下属性：
			target(DOM对象，将被更新的目标节点)，id，text，iconCls，checked等。
		* demo
			// 更新选择的节点文本
			var node = $('#tt').tree('getSelected');
			if (node){
				$('#tt').tree('update', {
					target: node.target,
					text: 'new text'
				});
			}
	
	enableDnd
		* 启用拖拽功能。
	
	disableDnd
		* 禁用拖拽功能。
	
	beginEdit
		* 开始编辑一个节点。
		* 参数:target
	
	endEdit
		* 结束编辑一个节点
		* 参数:target
	
	cancelEdit
		* 取消编辑一个节点
		* 参数:target
	
	doFilter
		* 过滤操作，和filter属性功能类似
		* 参数:text
		* demo
			$('#tt').tree('doFilter', 'easyui');
			$('#tt').tree('doFilter', '');	// 清除过滤器
	


	# 关于Node和target
		target是 Node.target的返回值
		一般,事件会传递一个Node进来,而大多说的方法.都是需要target这个对象
		

-------------------
Demo				|
-------------------
	# 勾选的时候,会勾选所有的父级菜单
	# 取消勾选的时候,会取消勾选所有的子菜单
			
			onCheck:function (node, checked) {
                    if(checked){
                        //勾选,存在父级菜单
                        var parent = $(this).tree('getParent',node.target);
                        if(parent != null){
                            $(this).tree('check',parent.target);
                        }
					}else {
                        //取消勾选,存在子菜单
                        var childrens = $(this).tree('getChildren',node.target);
                        if(childrens != null && childrens.length != 0){
                            for(var x = 0;x < childrens.length ; x++){
                                $(this).tree('uncheck',childrens[x].target);
                            }
                        }
					}
                },
	
		
	# 统一数据的返回格式
	loadFilter:function (data,parent) {
	                if(!data.success){
	                    TD.showAlert(data.message);
	                    return [];
	                }
	                return data.data;
                },

