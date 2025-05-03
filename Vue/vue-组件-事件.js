-------------------
事件
-------------------
	# 触发事件
		* 在模板中使用 $emit 来触发子自定义事件
		
			// 点击则会发布一个 'someEvent' 的自定义事件，并且传递一个字符串参数
			<button @click="$emit('someEvent', '我是参数')">点我</button>
		
		* 所有传入 $emit() 的额外参数（除了第一个参数是事件名称）都会被直接传向监听器。
		
	
	# 监听事件
		
		* 通过 v-on / @ 来监听事件
		
			// 这里使用内联函数，也可以使用定义的函数
			<Foo @some-event="arg => console.log(arg)"/>
			
		* 要监听的事件名称，同样建议使用短横线命名
		* 自定义事件也支持一系列的修饰符，例如 .once
		
		* 注意，和原生 DOM 事件不一样，组件触发的事件没有冒泡机制。也就是说只能监听直接子组件触发的事件。
		* 平级组件或是跨越多层嵌套的组件间通信，应使用一个外部的事件总线，或是使用一个全局状态管理方案。
	
	
	# 声明触发的事件
		* 组件可以通过 defineEmits() 宏主动声明要触发的事件
		* 并且可以通过返回的句柄来触发事件
		
			// 声明了会触发两个事件
			const emit = defineEmits(['inFocus', 'submit'])

			function buttonClick() {
				// 触发自定义事件
				emit('submit')
			}
		
		* 声明触发的事件，还可以使用对象，用于描述、校验事件的参数
				
				const emit = defineEmits({
				  submit(payload: { email: string, password: string }) {
					// 通过返回值为 `true` 还是为 `false` 来判断
					// 验证是否通过
				  }
				})
		
		
		* 注意，如果一个原生事件的名字 （例如 click） 被定义在 emits 选项中，则监听器只会监听组件触发的 click 事件而不会再响应原生的 click 事件。
					

				









