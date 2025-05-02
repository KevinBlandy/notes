---------------------------
条件指令
---------------------------

	# v-if / v-else-if / v-else 条件指令，只有在条件为 true 时才会渲染元素

		<h1 v-if="rate === 0">关闭</h1>
		<h1 v-else-if="rate === 1">开启</h1>
		<h1 v-else>不知道</h1>
		
		* 一个 v-else 元素必须跟在一个 v-if 或者 v-else-if 元素后面，否则它将不会被识别。
	
	# 如果条件中包含了多个元素，可以使用 template 封装
		<template v-if="ok">
		  <h1>Title</h1>
		  <p>Paragraph 1</p>
		  <p>Paragraph 2</p>
		</template>
	
	# 可以用来按条件显示一个元素的指令是 v-show
	
		* 其用法基本一样
		* 不同之处在于 v-show 会在 DOM 渲染中保留该元素；v-show 仅切换了该元素上名为 display 的 CSS 属性。
		* v-show 不支持在 <template> 元素上使用，也不能和 v-else 搭配使用。
		* 元素无论初始条件如何，始终会被渲染，只有 CSS display 属性会被切换。
		
		* v-if 有更高的切换开销，而 v-show 有更高的初始渲染开销。
		* 如果需要频繁切换，则使用 v-show 较好；如果在运行时绑定条件很少改变，则 v-if 会更合适。
		

	# 当 v-if 和 v-for 同时存在于一个元素上的时候，v-if 会首先被执行。
	