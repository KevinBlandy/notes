
# 指令是带有 v- 前缀的特殊特性
	* 指令特性的值预期是单个 JavaScript 表达式

# 指令参数
	* 一些指令能够接收一个"参数",在指令名称之后以冒号表示

# 指令修饰符
	* 修饰符是以 . 指明的特殊后缀,用于指出一个指令应该以特殊方式绑定
		<form v-on:submit.prevent="onSubmit">...</form>

	.prevent 
		* 修饰符告诉 v-on 指令对于触发的事件调用 event.preventDefault()：
	


# v-if
	* 如果表达式值为 true,则渲染节点
		<p v-if="seen">现在你看到我了</p>

# v-bind
	* 绑定属性到html属性/模版属性
		<a v-bind:href="url">...</a>
	* 缩写 :id="id"
	
# v-on
	* 监听dom事件
		<a v-on:click="doSomething">...</a>
	* 属性: @click="id"


# v-for
	* 循环渲染