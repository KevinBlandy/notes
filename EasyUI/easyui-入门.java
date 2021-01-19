------------------------
EasyUI-入门				|
------------------------
	# 这是啥?
		* 其实就是,基于Jquery上的一堆插件
		* 非常容易实现一些页面常见的效果

	# 目录结构
		demo						//演示案例
		demo-mobile					//手机端演示案例
		locale						//国际化
		plugins						//插件
		src							//jquery easyui的源码
		themes
		changelog.txt
		easyloader.js
		jquery.easyui.min.js		//jqueryeasyui
		jquery.easyui.mobile.js		//手机端
		jquery.min.js				//jquery
		license_freeware.txt
		readme.txt
	
	# 基础的引入
		<script type="text/javascript" src="${pageContext.request.contextPath }/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/static/easyui/themes/icon.css">

		* jquery必须要首先引入.
	
	# 常用Demo演示地址
		http://www.jeasyui.com/demo/main/index.php

------------------------
EasyUI-渲染方式			|
------------------------
	# 不使用JavaScript渲染
		<div id="div" title="标题" class="easyui-dialog"></div>
		* 参数直接作为属性,写在HTML标签上
	
	# 使用JavaScript渲染
		$(function(){
			$('#div').dialog({
				title:'标题'
			});
		});
		* 参数作为JSON,传递给方法

------------------------
EasyUI-控件的属性配置	|
------------------------
	# 看上面

------------------------
EasyUI-控件方法的调用	|
------------------------
	$('#div').panel('open');
		* 选择到HTML元素,然后就是'控件类型',在参数里面输入'控件方法'.完成调用
	

	$('#div').panel('open','param');
		* 选择到HTML元素,然后就是'控件类型',在参数里面输入'控件方法'.完成调用.第二个参数就是传递给控件方法的参数

------------------------
EasyUI-控件的关系		|
------------------------
	依赖的关系,有点像 JAVA 中的 extends .子类可以有父类这中的所有属性,方法,事件
