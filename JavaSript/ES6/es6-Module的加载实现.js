--------------------------------
Module 的加载实现				|
--------------------------------
	1,浏览器加载
	2,ES6 模块与 CommonJS 模块的差异
	3,Node 加载
	4,循环加载
	5,ES6 模块的转码
	6,上一章介绍了模块的语法,本章介绍如何在浏览器和 Node 之中加载 ES6 模块,以及实际开发中经常遇到的一些问题(比如循环加载)

--------------------------------
浏览器加载						|
--------------------------------
	# HTML 网页中,浏览器通过<script>标签加载 JavaScript 脚本
		<!-- 页面内嵌的脚本 -->
		<script type="application/javascript">
		  // module code
		</script>

		<!-- 外部脚本 -->
		<script type="application/javascript" src="path/to/myModule.js">
		</script>

		* 浏览器是同步加载 JavaScript 脚本,即渲染引擎遇到<script>标签就会停下来,等到执行完脚本,再继续向下渲染
		* 如果是外部脚本,还必须加入脚本下载的时间
	
	# 通过<script>标签的defer和async属性,来控制脚本的异步加载
		<script src="path/to/myModule.js" defer></script>
		<script src="path/to/myModule.js" async></script>

		* defer要等到整个页面在内存中正常渲染结束(DOM 结构完全生成,以及其他脚本执行完成),才会执行
			'defer是渲染完再执行'
		* async一旦下载完,渲染引擎就会中断渲染,执行这个脚本以后,再继续渲染
			'async是下载完就执行'
		
		* 如果有多个defer脚本,会按照它们在页面出现的顺序加载,而多个async脚本是不能保证加载顺序的
	
	
	# 加载规则
		* 览器加载 ES6 模块,也使用<script>标签,但是要加入type="module"属性
			<script type="module" src="./foo.js"></script>

			* foo.js 有几点需要注意

				1,代码是在模块作用域之中运行,而不是在全局作用域运行,模块内部的顶层变量,外部不可见
				2,模块脚本自动采用严格模式,不管有没有声明use strict
				3,模块之中,可以使用import命令加载其他模块(.js后缀不可省略,需要提供绝对 URL 或相对 URL),也可以使用export命令输出对外接口
				4,模块之中,顶层的this关键字返回undefined,而不是指向window,也就是说,在模块顶层使用this关键字,是无意义的
					* 利用顶层的this等于undefined这个语法点,可以侦测当前代码是否在 ES6 模块之中
						const isNotModuleScript = this !== undefined;
				5,同一个模块如果加载多次,将只执行一次

		* 浏览器对于带有type="module"的<script>,都是异步加载,不会造成堵塞浏览器
		* 即等到整个页面渲染完,再执行模块脚本,等同于打开了<script>标签的defer属性
			<script type="module" src="./foo.js"></script>
			<!-- 等同于 -->
			<script type="module" src="./foo.js" defer></script>
		* 如果网页有多个<script type="module">,它们会按照在页面出现的顺序依次执行

		* <script>标签的async属性也可以打开,这时只要加载完成,渲染引擎就会中断渲染立即执行.执行完成后,再恢复渲染
			<script type="module" src="./foo.js" async></script>

			* 一旦使用了async属性,<script type="module">就不会按照在页面出现的顺序执行,而是只要该模块加载完成,就执行该模块
		
		* ES6 模块也允许内嵌在网页中,语法行为与加载外部脚本完全一致
			<script type="module">
			  import utils from "./utils.js";

			  // other code
			</script>
	
