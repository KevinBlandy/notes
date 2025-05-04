-----------------------
自定义指令
-----------------------
	
	# 自定义指令
		
		* 自定义指令主要是为了重用涉及普通元素的底层 DOM 访问的逻辑。
		
		* 只有当所需功能只能通过直接的 DOM 操作来实现时，才应该使用自定义指令。
		
		* 自定义指令由一个包含类似组件生命周期钩子的对象来定义，钩子函数会接收到指令所绑定元素作为其参数。
		
			// 定义一个指令，注意，变量是以 v 开头的驼峰
			const vAutoFocus = {
				
				// 在组件挂载时执行，参数就是原生 dom 
				mounted: el => {
					el.focus();
				}
			}
			
			// 在元素上使用指令
			<input v-auto-focus/>
	
		
		* 在 <script setup> 中，任何以 v 开头的驼峰式命名的变量都可以当作自定义指令使用。
		
		* 可以全局注册，在整个应用中都可用
		
			const app = createApp({})

				// 使 v-auto-focus 在所有组件中都可用
			app.directive('autoFocus', {
				mounted: el => {
					el.focus();
				}
			})
		
		* 和内置指令类似，自定义指令的参数也可以是动态的。
			<div v-example:[arg]="value"></div>
	
	# 简化定义
		* 很常见的情况是仅仅需要在 mounted 和 updated 上实现相同的行为
		* 可以直接用一个函数来简化指令的定义方式
			
			// 使用指令
			<div v-color="'red'"></div>
			
			app.directive('color', (el, binding) => {
				// 这会在 `mounted` 和 `updated` 时都调用
				el.style.color = binding.value
			})
	
	
	# 组件上使用
		* 不推荐在组件上使用自定义指令。当组件具有多个根节点时可能会出现预期外的行为。
		* 不推荐在组件上使用自定义指令。当组件具有多个根节点时可能会出现预期外的行为。
		* 不推荐在组件上使用自定义指令。当组件具有多个根节点时可能会出现预期外的行为。
		
		
		* 在组件上使用自定义指令时，它会始终应用于组件的根节点，和透传 attributes 类似。
		* 如果组件有多个根节点，指令会被忽略且抛出警告信息。
		
		* 和属性不同，指令不能通过 v-bind="$attrs" 来传递给一个不同的元素。
				
	
	# 指令的钩子函数
	
		const myDirective = {
		  // 在绑定元素的 attribute 前
		  // 或事件监听器应用前调用
		  created(el, binding, vnode) {
			// 下面会介绍各个参数的细节
		  },
		  // 在元素被插入到 DOM 前调用
		  beforeMount(el, binding, vnode) {},
		  // 在绑定元素的父组件
		  // 及他自己的所有子节点都挂载完成后调用
		  mounted(el, binding, vnode) {},
		  // 绑定元素的父组件更新前调用
		  beforeUpdate(el, binding, vnode, prevVnode) {},
		  // 在绑定元素的父组件
		  // 及他自己的所有子节点都更新后调用
		  updated(el, binding, vnode, prevVnode) {},
		  // 绑定元素的父组件卸载前调用
		  beforeUnmount(el, binding, vnode) {},
		  // 绑定元素的父组件卸载后调用
		  unmounted(el, binding, vnode) {}
		}
		
		* el：指令绑定到的元素。这可以用于直接操作 DOM。
		* binding：一个对象，包含以下属性。

			* value：传递给指令的值。例如在 v-my-directive="1 + 1" 中，值是 2。
			* oldValue：之前的值，仅在 beforeUpdate 和 updated 中可用。无论值是否更改，它都可用。
			* arg：传递给指令的参数 (如果有的话)。例如在 v-my-directive:foo 中，参数是 "foo"。
			* modifiers：一个包含修饰符的对象 (如果有的话)。例如在 v-my-directive.foo.bar 中，修饰符对象是 { foo: true, bar: true }。
			* instance：使用该指令的组件实例。
			* dir：指令的定义对象。
			
		* vnode：代表绑定元素的底层 VNode。
		* prevVnode：代表之前的渲染中指令所绑定元素的 VNode。仅在 beforeUpdate 和 updated 钩子中可用。
		
		
		* 除了 el 外，其他参数都是只读的，不要更改它们。若需要在不同的钩子间共享信息，推荐通过元素的 dataset attribute 实现。
		
		