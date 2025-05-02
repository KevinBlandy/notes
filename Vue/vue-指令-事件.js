--------------------------
事件
--------------------------
	# 使用 v-on:<event> 绑定事件
		
		* 快捷方式： @<event>="<handler>"
	
	
		<button @click="() => {}">戳我</button>

	
	# 内联事件处理器
		* 事件被触发时执行的内联 JavaScript 语句 (与 onclick 类似)。
		
			// 直接执行 JS 语句
			<button @click="counter++;">{{ counter }}</button>
			// 直接调用方法，可以传递自定义参数
			<button @click="inreCounter(1);">{{ counter }}</button>
			
		
		* 通过 $event 传递原始 Dom 事件
			<button @click="onClick($event);">{{ counter }}</button>
		
		* 使用箭头函数直接访问 event
			<button @click="(event) => {console.log(event)}">{{ counter }}</button>
		
	
	# 方法事件处理器
		* v-on 接受一个方法名或对某个方法的调用。
		
			<button @click="onClick">{{ counter }}</button>
			
			// 自动接收原生 DOM 事件
			const onClick = (event) => {
				console.log(event)
			}
	
	
	# 事件修饰符
		
		* Vue 为 v-on 提供了事件修饰符，用 . 表示的指令后缀
		
			.stop
			.prevent
			.self
			.capture		// 与原生 addEventListener 事件相对应
			.once			// 与原生 addEventListener 事件相对应
			.passive		// 与原生 addEventListener 事件相对应
	
		* demo
		
			// 单击事件将停止传递
			<a @click.stop="doThis"></a>

			// 提交事件将不再重新加载页面
			<form @submit.prevent="onSubmit"></form>

			// 修饰语可以使用链式书写
			<a @click.stop.prevent="doThat"></a>

			// 也可以只有修饰符
			<form @submit.prevent></form>

			// 仅当 event.target 是元素本身时才会触发事件处理器
			// 例如：例如点击事件来自于子元素，则不会响应
			<div @click.self="doThat">...</div>
		
		* 使用修饰符时需要注意调用顺序，因为相关代码是以相同的顺序生成的。
		* 因此使用 @click.prevent.self 会阻止元素及其子元素的所有点击事件的默认行为，而 @click.self.prevent 则只会阻止对元素本身的点击事件的默认行为。
	
	
	# 按键修饰符
		
		* 允许在 v-on 或 @ 监听按键事件时添加按键修饰符。
		
			// 仅在 `key` 为 `Enter` 时调用 `submit`
			<input @keyup.enter="submit" />
			
		
		* 可以直接使用 KeyboardEvent.key 暴露的按键名称作为修饰符，但需要转为 kebab-case 形式。
		
			// 仅会在 $event.key 为 'PageDown' 时调用事件处理。
			<input @keyup.page-down="onPageDown" />
	
		
		* Vue 为一些常用的按键提供了别名
			.enter
			.tab
			.delete // 捕获 “Delete” 和 “Backspace” 两个按键
			.esc
			.space
			.up
			.down
			.left
			.right
		
		* 可以使用以下系统按键修饰符来触发鼠标或键盘事件监听器，只有当按键被按下时才会触发。
		
			.ctrl
			.alt
			.shift
			.meta
					
		
			// Alt + Enter
			<input @keyup.alt.enter="clear" />

			// Ctrl + 点击
			<div @click.ctrl="doSomething">Do something</div>
			
			* 系统按键修饰符和常规按键不同。与 keyup 事件一起使用时，该按键必须在事件发出时处于按下状态。
			* 换句话说，keyup.ctrl 只会在你仍然按住 ctrl 但松开了另一个键时被触发。若你单独松开 ctrl 键将不会触发。
			
		
		* 用 .exact 修饰符，精确控制触发事件所需的系统修饰符的组合
		
			// 当按下 Ctrl 时，即使同时按下 Alt 或 Shift 也会触发
			<button @click.ctrl="onClick">A</button>

			// 仅当按下 Ctrl 且未按任何其他键时才会触发
			<button @click.ctrl.exact="onCtrlClick">A</button>

			// 仅当没有按下任何系统按键时触发
			<button @click.exact="onClick">A</button>
		
		
		* 鼠标按键修饰符，将处理程序限定为由特定鼠标按键触发的事件。

			.left
			.right
			.middle
			
			
			* .left，.right 和 .middle 实际上分别指代设备事件触发器的“主”、”次“，“辅助”，而非实际的物理按键。
			