------------------------
easyloader				|
------------------------
	# 号称一个顶N个
	# EasyUI的插件都是单独存在的JS文件,你要使用哪个插件,就需要引入哪个插件,太累
	# 这个东西可以动态的引入.说白了,你只要引入它就OK,然后想使用哪个插件,只是使用这个js里面的对象可以调用.动态的引入
	
	# <script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery-easyui-1.5/easyloader.js"></script>

	# easyloader.load('',function(){})

	# demo
		easyloader.load('dialog',function(){
			$('#div').dialog({
				title:'卧槽',
				collapsible:true,
				resizable:true,
				minimizable:true,
				modal:true
			});
		});

		easyloader.load('模块/插件名称',function(){
			//回调函数
		});
	
	# 也可以一次性的引入多个模块
		easyloader.load(['dialog','messager'],function(){
			$('#div').dialog({
				title:'卧槽',
				collapsible:true,
				resizable:true,
				minimizable:true,
				modal:true
			});
			$.messager.alert('卧槽');
		});

		* easyloader.load(),第一个参数,就是一个数组
	
	# 牛逼之处在于,引入了一个:easyloader.js,直接在HTML元素上标识easyui的类,也会自动的引入相应的模板
		<div class="easyui-dialog" id="div" style="width: 300px;height: 100px;">
		</div>
		* 直接就这么写,不用引入dialog模块
	

	# using ,loader的简写而已
		using(['dialog','messager'],function(){
			//回调函数
		});

		using('http://xxxxx.js',function(){
			//回调函数
		});

		* 可以去引入指定 URL下的东西
		* 可以是CSS,JS... ...等等
	
	# 原理分析
		* '导入模块很多'						--> 导入指定模块的时候,那个模块会依赖其他的模块..参考maven,于是乎会引进一大堆的东西
		* '不用JS,也可以渲染未导入的模板'		--> 就算是不用JS也会渲染HTML标签的easyui-xxx属性
			//最后几行代码
			window.using = easyloader.load;
			if (window.jQuery) {
				jQuery(function() {
					easyloader.load("parser",
					function() {
						jQuery.parser.parse();
					});
				});
			}
			* 最后加载parser组键,然后调用了它的parse();方法
			* 这个方法就牛逼了
				(function($){
					//这个变量,只要是遇到了class是easyui的,就会进行渲染
					$.parser : true,
					onComplete : function(){
						//整篇文档解析完之后触发的事件
					}
				});
			