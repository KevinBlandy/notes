-------------------------------
如何使用内部jQuery				|
-------------------------------
	# 由于Layui部分内置模块依赖jQuery,所以jQuery1.11最稳定的一个版本作为一个内置的DOM模块(唯一的一个第三方模块)
	# 只有你所使用的模块有依赖到它,它才会加载,并且如果你的页面已经script引入了jquery,它并不会重复加载
	# 内置的jquery模块去除了全局的$和jQuery,是一个符合layui规范的标准模块.所以你必须通过以下方式得到:
		//主动加载jquery模块
		layui.use(['jquery', 'layer'], function(){ 
			var $ = layui.jquery;	//重点处
			var layer = layui.layer;

			//后面就跟你平时使用jQuery一样
			$('body').append('hello jquery');
		});

	# 如果内置的模块本身是依赖jquery,那么无需去use jquery,
		* 所以上面的写法其实可以是：
		layui.use('layer', function(){ 
			var $ = layui.jquery;	//由于layer弹层依赖jQuery,所以可以直接得到
			var layer = layui.layer;

			//……
		});

-------------------------------
为什么表单不显示				|
-------------------------------
	# 使用表单时,Layui会对select,checkbox,radio等原始元素隐藏,从而进行美化修饰处理。
	# 但这需要依赖于form组件,所以你必须加载 form,并且执行一个实例。
	# 值得注意的是:导航的Hover效果,Tab选项卡等同理(它们需依赖 element 模块)
	# Demo
		layui.use('form', function(){
			var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功

			//……
		});      
