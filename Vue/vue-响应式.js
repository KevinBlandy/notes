-------------------------
响应式
-------------------------
	
	# 监听 Dom 更新
		* 修改了响应式状态时，DOM 会被自动更新，但是需要注意，它不是同步更新的
		* Vue 会在 “next tick” 更新周期中缓冲所有状态的修改，以确保不管你进行了多少次状态修改，每个组件都只会被更新一次。
		
		* 通过 nextTick 函数监听 Dom 更改
		
			import { nextTick } from 'vue'

			async function increment() {
				
				// 修改响应式属性
				count.value++
					
				// 阻塞，await 到 nextTick 完成
				await nextTick()
				
				// 现在 DOM 已经更新了，可以执行 Dom 更新后的逻辑
			}
			
	
	

-------------------------
ref
-------------------------
	# ref 方法把值封装到一个暴露了 value 的属性的响应式对象中
	
		import {ref} from 'vue'
		const count = ref(0);
		
		* 在 JS 中需要通过 value 属性来访问、修改值
			
			count.value++;
		
		* 在模板中访问可以省略 value 属性
			
			<button @click="count++">点击了 {{ count  }} 次</button>
		
	
	
	# 深层次响应
		* Ref 可以封装任何类型的值，包括深层嵌套的对象、数组或者 JavaScript 内置的数据结构，比如 Map。
		* Ref 会使封装值具有深层响应性。这意味着即使改变嵌套对象或数组时，变化也会被检测到。
		
			const obj = ref({
				nested: { count: 0 },
				arr: ['foo', 'bar']
			})
			
			
			// 对嵌套数据的修改也会触发 UI 的响应
			
			// 修改嵌套对象的属性
			obj.value.nested.count++
			// 往嵌套数组 push 数据
			obj.value.arr.push('baz')
	
	# 通过 shallowRef 来实现浅响应
	
		* 对于浅层 ref，只有 .value 的访问会被追踪，这可以用于避免对大型数据的响应性开销来优化性能、或者有外部库管理其内部状态的情况
	
			const state = shallowRef({ count: 1 })

			// 修改 value 属性的属性不会触发响应
			state.value.count = 2

			// 修改 value 属性会触发响应
			state.value = { count: 2 }
	

-------------------------
reactive
-------------------------
	# reactive() 将非原始值将通过换为响应式代理，使对象本身具有响应性
	
		<button @click="reactObj.count++">点击了 {{ reactObj.count  }} 次</button>
	
		import { reactive } from 'vue'

		const reactObj = reactive({
			count: 0
		});
			
		* 当 ref 的值是一个对象时，ref() 也会在内部调用 reactive()。
			
	
	# reactive 也是深层次的响应封装，同理，也有一个 shallowReactive() API 可以选择退出深层响应性
	
		* 使用 shallowReactive() 的话，只有根级别的属性是响应式的。
	
	# reactive() 返回的是原始对象的 Proxy

		* 它和原始对象是不相等，且只有代理对象是响应式的
		
		* 对同一个原始对象调用 reactive() 会总是返回同样的代理对象
		
		  import { onMounted, reactive } from 'vue'
		
			// 原始对象
			const obj = {
			count: 0
			};

			const reactObj = reactive(obj);
			
			// 对同一个原始对象进行多次 reactive，返回的都是同一个响应式代理对象
			console.log(reactObj == reactive(obj));  // true
		
		* 对一个响应式代理对象调用 reactive() 会返回其本身
		
			console.log(reactObj === reactive(reactObj)); // true
		
		* 这个规则对嵌套对象也适用
			* 依靠深层响应性，响应式对象内的嵌套对象依然是代理
			
				const proxy = reactive({})

				const raw = {}
				
				// 给代理设置了 raw 属性
				proxy.nested = raw

				// 此时的 proxy.nested 已经是一个响应式代理对象了，并不是原对象
				console.log(proxy.nested === raw) // false
		
	
	# reactive() 的局限性
		* 它只能用于对象类型 (对象、数组和如 Map、Set 这样的集合类型)。它不能封装如 string、number 或 boolean 这样的原始类型。

		* 不能替换整个对象，Vue 的响应式跟踪是通过属性访问实现的，因此必须始终保持对响应式对象的相同引用。
		
			let state = reactive({ count: 0 })

			// 上面的 ({ count: 0 }) 引用将不再被追踪
			// 响应性丢失！
			state = reactive({ count: 1 })
		
		* 对解构操作不友好，将响应式对象的原始类型属性解构为本地变量时，或者将该属性传递给函数时，丢失响应性
	
			const state = reactive({ count: 0 })

			// 解构时，count 已经与 state.count 断开连接
			
			let { count } = state
			
			// 修改解构出来的属性不会影响原始的 state
			count++

			// 该函数接收到的是一个普通的数字
			// 并且无法追踪 state.count 的变化
			// 必须传入整个对象以保持响应性
			callSomeFunction(state.count)
		
	

	# 响应式对象的 ref 属性在访问或修改时会自动解包
		
		import { reactive, ref} from 'vue'
		
		
		// ref 对象
		const count = ref(0);
		
		// 把 ref 对象作为 reactive 的属性
		const obj = reactive({count})
		
		// ref 对象已经被解包，可以直接访问而不需要 .value
		obj.count = 1024;
		
		// 原始 ref 对象的 value 也被修改
		console.log(count.value);  // 1024
		
		* 如果给响应式对象设置了新的 ref 属性，则会替换旧的
			
			// 给响应式对象 obj 设置新的ref属性
			obj.count = ref(1)
			// 旧的 count.value 不会被修改
			console.log(obj.count, count.value);  // 1 1024

		
		* 注意，只有当嵌套在一个深层响应式对象内时，才会发生 ref 解包。当其作为浅层响应式对象的属性被访问时不会解包。

			import {shallowReactive, ref} from 'vue'

			const count = ref(0);
			
			// 设置浅层响应对象的属性为 ref 对象
			const obj = shallowReactive({count})
			
			// 修改浅层对象的值
			obj.count = 15;
			
			// 并不会影响原始的 ref 对象
			console.log(obj.count, count.value);  // 15, 0
	
	# 数组和集合的注意事项
		* 当 ref 作为响应式数组或原生集合类型 (如 Map) 中的元素被访问时，它不会被解包
		
		  import { reactive, ref} from 'vue'

		  const item = ref(0);

		  const obj = reactive({
			items:[item],  // ref 对象定义在了数组中
		  });

		  // 在原生容器的 ref，不能自动解包，仍要通过 value 访问
		  obj.items[0].value = 1;

		  console.log(item.value);  // 1
