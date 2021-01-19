----------------------------
EasyUI-class				|
----------------------------
	# 参数可以直接作为HTML元素的属性.
		<div id="div" title="标题" class="easyui-diaglog"> <div>

	# 属性和参数也可以通过JS来完成
		$(function(){
			//函数名称就是easyui的属性名称
			$('#div').dialog({
				//JSON数据,来配置参数
				title:'标题'
			});
		});
		
	easyui-draggable
		* 被标识的元素,可以自由的拖动
	
	easyui-dialog
		* 对话框
		modal:true		//是否遮罩... ...,就是下面的东西动不了.默认是false
		title:'标题'	//窗口标题
	
