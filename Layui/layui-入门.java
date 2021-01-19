----------------------------
layui-入门					|
----------------------------
	# 官网
		http://www.layui.com/

	# 在线文档
		http://www.layui.com/doc/

	# Github
		https://github.com/sentsin/layui/
	
	# 作者
		闲心

----------------------------
layui-基本使用				|
----------------------------
	1,导入
		<script type="text/javascript" src="/layui/layui.js"></script>
		<link rel="stylesheet" href="/layui/css/layui.css">

	2,定义模块入口
		<script>
			layui.config({
				base: '/js/modules/'	//模块目录
			}).use('index');			//加载指定的模块
		</script>   
	
	3,定义模块
		layui.define(['layer', 'form'], function(exports){

			var layer = layui.layer

			var form = layui.form();

			layer.msg('Hello World');

			exports('index', {});			//注意，这里是模块输出的核心,模块名必须和use时的模块名一致
		});    