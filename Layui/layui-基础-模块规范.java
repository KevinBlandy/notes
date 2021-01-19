--------------------------------
layui-模块规范					|
--------------------------------

--------------------------------
layui-预先加载					|
--------------------------------
	# Layui的模块加载采用核心的 layui.use(mods, callback)方法
	# 当 JS 需要用到Layui模块的时候,推荐采用预先加载,因为这样可以避免到处写layui.use的麻烦
	# 你应该在最外层如此定义:
		layui.use(['form', 'upload'], function(){  //如果只加载一个模块，可以不填数组。如：layui.use('form')
			var form = layui.form();	//获取form模块
			var upload = layui.upload;	//获取upload模块

			//监听提交按钮
			form.on('submit(test)', function(data){
				console.log(data);
			});

			//实例化一个上传控件
			upload({
				url: '上传接口url',
				success: function(data){
					console.log(data);
				}
			})
		});

--------------------------------
layui-按需加载					|
--------------------------------
	# 官方不推荐,所以不说了

--------------------------------
layui-模块命名空间				|
--------------------------------
	# Layui的全部模块绑定在 layui对象下
	# 内部由layui.define()方法来完成,每一个模块都会一个特有的名字,并且无法被占用
	# 无需担心模块的空间被污染,除非是你 delete layui.mod;
	# 调用一个模块也必须借助layui对象的赋值。如：

		layui.use(['layer', 'laypage', 'laydate'], function(){
			var layer = layui.layer;	//获得layer模块
			var laypage = layui.laypage; //获得laypage模块
			var laydate = layui.laydate;//获得laydate模块

			//使用模块
		});      
	
	# 一个模块一旦加载后,就会注册在layui对象下
	# 所以无法直接用模块名来获得
	# 借助layui对象,有时你可能会直接在元素的事件属性上去调用一个模块
		<input onclick="layui.laydate()">

--------------------------------
layui-扩展一个Layui模块			|
--------------------------------
	# Layui官方提供的模块有时可能还无法满足你
	# 或者你试图按照layer的模块规范来扩展一个模块.
	# 那么你有必要认识layui.define()方法
	# 在笔记:底层支持,中已有简单记载
	# 步骤
		1,定义模块
			layui.define(function(exports){ 
				//提示:模块也可以依赖其它模块如：layui.define('layer', callback);
				var obj = {
					hello: function(str){
						alert('Hello '+ (str||'test'));
					}
				};

				//输出test接口
				exports('test', obj);
			});    

		2,设定模块所在的目录,然后就可以在别的JS文件中使用了
			//config的设置是全局的
			layui.config({
				base: '/res/js/'		//假设这是test.js所在的目录
			}).extend({					//设定模块别名
				test: 'test',			
					//如果test.js是在根目录(base)下,可以不用设定别名
					//test这个模块,在base目录下的 test 目录下
				test1: 'admin/test1'	
					//相对于上述base目录的子目录
					//test1这个模块,在base目录下的 admin/test1 目录中
			}); 

			//使用test
			layui.use('test', function(){
				var test = layui.test;

				test.hello('World!'); //弹出Hello World!
			});

			//使用test1
			layui.use('test1', function(){
				var test = layui.test1;

				//……
			});