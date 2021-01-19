------------------------
layui-底层方法			|
------------------------
	# 主要介绍layui.js所发挥的作用
	# 其中过滤了大部分在外部可能不是太常用的API,侧重罗列了最常用的框架支撑

------------------------
全局配置				|
------------------------
	# 在使用模块之前,全局化配置一些参数
	# 尽管大部分时候它不是必须的
	# 所以目前提供的全局配置项非常少
	# 这也是为了减少一些不必要的工作,尽可能让使用变得更简单
	# 目前支持的全局配置项如下
		layui.config({
			dir: '/res/layui/', //layui.js 所在路径(注意,如果是script单独引入layui.js，无需设定该参数),一般情况下可以无视
			version: false ,	//一般用于更新模块缓存,默认不开启.设为true即让浏览器不缓存.也可以设为一个固定的值.如:201610
			debug: false ,		//用于开启调试模式,默认false,如果设为true，则JS模块的节点会保留在页面
			base: '' ,			//设定扩展的Layui模块的所在目录,一般用于外部模块扩展
		});

------------------------
定义模块				|
------------------------
	# 定义一个layui模块语法
		layui.define([mods], callback)

		mods
			* 用于声明该模块所依赖的模块
			* 可选参数

		callback
			* 即为模块加载完毕的回调函数,它返回一个exports参数,用于输出该模块的接口.
		
	# Demo
		layui.define(function(exports){
			//do something
			exports('demo', function(){
				alert('Hello World!');
			});
		});
		* exports是一个函数,它接受两个参数,第一个参数为模块名,第二个参数为模块接口
		* 当声明了上述的一个模块后,你就可以在外部使用了
		* demo就会注册到layui对象下,即可通过 layui.demo() 去执行该模块的接口.
      
	# 定义模块时,声明所需要的依赖
		layui.define(['layer', 'laypage'], function(exports){
			//do something

			exports('demo', function(){
				alert('Hello World!');
			});
		});

		* 如果仅仅是依赖一个模块,mods 可以不用传递数组,直接传递单个字符串.

------------------------
加载所需模块			|
------------------------
	# 语法
		layui.use([mods], callback)
			* Layui的内置模块并非默认就加载的,他必须在你执行该方法后才会加载,它的参数跟上述的 define 方法完全一样
			* 另外请注意,mods里面必须是一个合法的模块名,不能包含目录
			* 如果需要加载目录,建议采用extend建立别名(详见模块规范)
	
	# Demo
		layui.use(['laypage', 'layedit'], function(){
			var laypage = layui.laypage;
			var layedit = layui.layedit;
			//do something
		});
	
	# 也可以不通过layui对象赋值获得接口
		layui.use(['laypage', 'layedit'], function(laypage, layedit){
			//使用分页
			laypage();
			//建立编辑器
			layedit.build();
		});


------------------------
动态加载CSS				|
------------------------
	# 语法
		layui.data(table, settings)
			table
				* 表名
			settings
				* 设置项,包括 key,value
	
	# 该方法对localStorage进行了良好的封装,在Layui的多个内置模块(比如layim)中发挥了重要的作用,可以使用它存储一些本地数据
	# CRUD Demo
		
		* 插入新的数据
			layui.data('test', {
				key: 'nickname',
				value: 'Vin'
			});
		
		* 删除表中的数据
			layui.data('test', {
				key: 'nickname',
				remove: true
			});
		
		* 删除整张表
			layui.data('test', null); 
		
		* 修改
			* 跟插入一样,同名会覆盖
			
		* 检索数据
			//1,获取数据表
			var localTest = layui.data('test');
			//2,根据key从数据表中获取value
			console.log(localTest.nickname);	


------------------------
获取设备信息			|
------------------------
	# 语法
		layui.device(key)

		key
			* 可选的参数
		
		* 由于Layui的一些功能进行了兼容性处理和响应式支持,因此该方法同样发挥了至关重要的作用
		* 尤其是在layui mobile模块中的作用可谓举足轻重.该方法返回了丰富的设备信息
	
	# Demo
		var device = layui.device();

		{
			os: "windows",	//底层操作系统，windows、linux、mac等
			ie: false,		//ie6-11的版本，如果不是ie浏览器，则为false
			weixin: false,	//是否微信环境
			android: false,	//是否安卓系统
			ios: false,		//是否ios系统
		}

	# 有App可能会对userAgent插入一段特定的标识 
		* Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 myapp/1.8.6 Safari/537.36
		* 要验证当前的WebView是否在你的App环境,即可通过上述的myapp(即为Native给Webview插入的标识,可以随意定义)来判断.

		var device = layui.device('myapp');
		if(device.myapp){
			alert('在我的App环境');
		}      

------------------------
其它					|
------------------------
	# layui.js内部还提供了许多底层引擎
	# 他们同样是整个Layui框架体系的有力支撑,有必要露个脸,尽管可能并不会用到

	layui.cache	
		* 静态属性,获得一些配置及临时的缓存信息

	layui.getStyle(node, name)	
		* 获得一个原始DOM节点的style属性值
		* 如：layui.getStyle(document.body, 'font-size')

	layui.img(url, callback, error)	
		* 图片预加载

	layui.extend(options)	
		* 拓展一个模块别名,如:layui.extend({test: '/res/js/test'})

	layui.router()	
		* 获得location.hash路由,目前在Layui中没发挥作用.对做单页应用会派上用场

	layui.hint()	
		* 向控制台打印一些异常信息,目前只返回了error方法:layui.hint().error('出错啦')

	layui.each(obj, fn)	
		* 对象(Array,Object,DOM对象等)遍历,可用于取代for语句

	layui.stope(e)	
		* 阻止事件冒泡

	layui.onevent(modName, events, callback)	
		* 自定义模块事件,属于比较高级的应用
		* 有兴趣的同学可以阅读layui.js源码以及form模块

	layui.event(modName, events, params)	
		* 执行自定义模块事件,搭配onevent使用

------------------------
第三方支撑				|
------------------------
	# Layui部分模块依赖jQuery(比如layer),但是并不用去额外加载jQuery
	# Layui已经将jQuery最稳定的一个版本改为Layui的内部模块
	# 当你去使用layer的时候,它会首先判断你的页面是否已经引入了jQuery,如果没有，则加载内部的jQuery模块,如果有,则不会加载.
	# 另外,图标取材于阿里巴巴矢量图标库(iconfont),构建工具采用 Gulp .除此之外,不依赖于任何第三方工具
