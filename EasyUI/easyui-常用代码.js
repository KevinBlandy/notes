---------------------
其他				 |
---------------------
	# 右击显示自己的菜单(具备右击事件的组件都可以使用)
		onContextMenu:function(e,node){
			//阻止浏览器的右击事件
			e.preventDefault();
			/*
				显示我们自定义的菜单
			*/
			$('#treeMenu').menu('show',{
				//菜单的位置是在鼠标右击点击的地方
				left:e.pageX,
				top:e.pageY
			});
		}
		
---------------------
tree				 |
---------------------
	# 异步全展开
		$('#tree').tree({
			//后台数据响应成功,会调用这个方法
			onLoadSuccess:function(node,data){
				/*
					执行展开所有节点
					而每次展开都要请求后台都会触发这个事件
					这个事件就相当于'递归'执行了
				*/
				if(data){
					//遍历后台响应的节点
					$(data).each(function(index,d){
						/*
							如果是关闭状态,则执行打开操作
							打开操作,又会请求后台服务器.又会触发这个事件
							后台的逻辑:如果有子节点,就是 closed 状态,如果没子节点,则是 open 状态
							tree如果是通过url进行加载的,那么在执行展开时候,会自动尝试把id值发送给服务器,来获取子节点
						*/
						if(this.state == 'closed'){
							$('#tree').tree('expandAll');
						}
					});
				}
			}
		});
	
	# 勾选的时候,如果有父级菜单,全部勾选,取消勾选的时候,如果有子菜单,全部取消勾选
		onCheck:function (node, checked) {
			if(checked){
				//勾选,存在父级菜单,则勾选所有父级菜单
				var parent = $(this).tree('getParent',node.target);
				if(parent != null){
					$(this).tree('check',parent.target);
				}
			}else {
				//取消勾选,存在子菜单则全部取消
				var childrens = $(this).tree('getChildren',node.target);
				if(childrens != null && childrens.length != 0){
					for(var x = 0;x < childrens.length ; x++){
						$(this).tree('uncheck',childrens[x].target);
					}
				}
			}
		},

	# 双击打开/关闭节点
		onDblClick:function (node) {
			$(this).tree('toggle',node.target);
		},

---------------------
datagrid			 |
---------------------
	# 动态的隐藏toolbar按钮
		/*
			把第3个摁钮隐藏
		*/
		$('div.datagrid-toolbar a').eq(2).hide();
		/*
			把第3个摁钮分隔符('|')隐藏
		*/
		$('.datagrid-btn-separator').eq(2).hide();
		
		* 同理的,既然可以获取到摁钮的jq对象,就可以执行linkbutton的方法,禁用掉它
			$('div.datagrid-toolbar a').eq(2).linkbutton('disable');

---------------------
validatebox			 |
---------------------
	# 扩展easyui的validatebox,新增验证
		$.extend($.fn.validatebox.defaults.rules,{
			name:{
				validator : function(value,param){
					console.log(value);
					return /^(?!\d+$)(?!_+$)\w{1,14}$/.test(value.replace(/[\u4e00-\u9fa5]/g,"aa"));
				},
				message:'名称格式错误'
			}
		});