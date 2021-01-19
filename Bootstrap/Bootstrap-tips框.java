-------------------------
Bootstrap-tips框		 |
-------------------------	
	# 某些文字,或者图标上添加说明。当鼠标指向的时候.会弹出来
	# 需要在js里面启用插件
		$(function(){
			$("[data-toggle='tooltip']").tooltip();
		});
	# 在需要的元素上添加俩属性
		data-toggle="tooltip"
		title="提示的文字"
	
	# 演示
		<li class="article_info" data-toggle="tooltip" title="浏览量">
			<span class="glyphicon glyphicon-eye-open"> 999</span>
		</li>
