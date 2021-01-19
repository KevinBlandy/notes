------------------------
mdui-入门				|
------------------------
	# 官网
		https://www.mdui.org

------------------------
mdui-规范				|
------------------------
	# HTML规范
		* MDUI 中的 CSS 类名全部以 mdui- 作为前缀
		* 组件的命名为 mdui-{组件名},组件的子元素命名为 mdui-{组件名}-{子元素名}
		* 例如一个对话框的结构为:
			<div class="mdui-dialog">
				<div class="mdui-dialog-title"></div>
				<div class="mdui-dialog-content"></div>
				<div class="mdui-dialog-actions"></div>
			</div>
			mdui-dialog			//组件
			mdui-dialog-title	//组件-子元素
	

	# Javascript 规范
		* MDUI 中大部分组件都需要使用 JavaScript 进行初始化,初始化方式包括下面三种情况

		1,使用事件委托，在触发事件时初始化。
			* 这类组件只需编写 HTML 即可,无需额外代码

		2,使用自定义属性初始化
			* 在组件上添加自定义属性 mdui-{组件名} 即可在页面加载完后自动初始化.
			* 也可以为自定义属性传入参数:mdui-{组件名}="{key1: 'val1', key2: 'val2'}"

		3,使用 JavaScript 初始化
			* 使用方法通常为 var inst = new mdui.{组件名}();
			* 该方法返回组件实例,然后可以调用实例的方法 inst.method()