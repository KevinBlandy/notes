----------------------------
watch
----------------------------
	# 使用 watch api 监听响应式状态
	
		import {ref, watch} from 'vue'

		const color = ref('red');
		
		// 监听函数中第一个参数是新的状态，第二个参数是旧的状态
		watch(color, (newColor, oldColor) => {
			console.log(newColor, oldColor);
		});
	
	
		
	# watch 的第一个参数可以是不同形式的“数据源”

		* 它可以是一个 ref （包括计算属性）、一个响应式对象、一个 getter 函数、或多个数据源组成的数组
		

			const x = ref(0)
			const y = ref(0)

			// 单个 ref
			watch(x, (newX) => {
				console.log(`x is ${newX}`)
			})

			// getter 函数
			watch(
				() => x.value + y.value,
				(sum) => {
					console.log(`sum of x + y is: ${sum}`)
				}
			)

			// 多个来源组成的数组
			watch([x, () => y.value], ([newX, newY]) => {
				console.log(`x is ${newX} and y is ${newY}`)
			})
	
	# 响应式对象

		* 不能直接监听响应式对象的属性，必须要用 getter
			// 响应式对象
			const obj = reactive({ count: 0 })

			// obj.count 是错的，因为 watch() 得到的参数是一个 number
			watch(obj.count, (count) => {
			  console.log(`Count is: ${count}`)
			})
				  
			 
			// () => obj.count 没毛病
			watch(() => obj.count, (count) => {
				console.log(`Count is: ${count}`)
			})
		
		
		* watch 会监听传入的响应式对象的所有层级的属性更改
	
			import {watch, reactive} from 'vue'

			const obj = reactive({ counter: {
				count: 0
			} })

			watch(obj, (newObj, oldObj) => {
				// 新旧对象都是同一个
				console.log(newObj === oldObj);         // true
				
				console.log(JSON.stringify(newObj));    // {"counter":{"count":1}}
				console.log(JSON.stringify(oldObj));    // {"counter":{"count":1}}
			})

			obj.counter.count = 1;
		
			
		* 返回响应式对象的 getter 函数，只有在返回不同的对象时，才会触发回调
			
			watch(
				() => obj.counter, // 只有在 counter 对象的指向发生了改变时才会触发
				(newCounter, oldCounter) => {
					console.log(newCounter === oldCounter);         // false
					console.log(JSON.stringify(newCounter));    // {"count":9}
					console.log(JSON.stringify(oldCounter));    // {"count":12}
				})
			
			
			// 修改监听对象的属性，不会触发
			obj.counter.count = 12;
			
			
			// 直接替换整个监听对象，会触发
			obj.counter = {
				count: 9,
			}
							
		
		* 也可以给显式地加上 deep 选项，强制转成深层侦听器
		
			watch(
				() => obj.counter, (newCounter, oldCounter) => {
					console.log(newCounter === oldCounter);         // true
					console.log(JSON.stringify(newCounter));		// {"count":12}
					console.log(JSON.stringify(oldCounter));		// {"count":12}
				}, 
				{deep: true}  // deep 选项
			)

			// 修改监听对象的属性，也会触发监听
			obj.counter.count = 12;
		
			
			* deep 选项值还可以是一个数字，表示最大遍历深度——即 Vue 应该遍历对象嵌套属性的级数。
	
	# 使用 immediate: true 选项实现立即执行
		
		watch(
		  source,
		  (newValue, oldValue) => {
			// 立即执行，且当 `source` 改变时再次执行
		  },
		  { immediate: true }
		)
	
	
	# 使用 once: true 选项实现一次性监听

		watch(
		  source,
		  (newValue, oldValue) => {
			// 当 `source` 变化时，仅触发一次
		  },
		  { once: true }
		)			
				
	
----------------------------
watchEffect
----------------------------
	# watchEffect() 可以自动跟踪回调函数中的响应式依赖
		
		* 不再需要指定要监听什么属性，你只管用属性，用了啥监听器就会监听啥（只跟踪回调中被使用到的属性，而不是递归地跟踪所有的属性。）
		* 监听属性一旦更改，就会自动再次执行
		* watchEffect() 定义完毕后就会立即执行一次
		
		import { watchEffect, reactive} from 'vue'

		const obj = reactive({ counter: {
			count: 0
		} })

		watchEffect(() => {
			console.log('改变了');
			
			// 会自动监听 counter 对象，只要 counter 发生了改变就会执行
			console.log(obj.counter.count);
		});

		// 会触发
		obj.counter.count = 12;

		// 会触发
		obj.counter = {
			count: 99,
		}
	
	
	# watch vs. watchEffect

		* watch 只追踪明确侦听的数据源。它不会追踪任何在回调中访问到的东西。
		* 另外，仅在数据源确实改变时才会触发回调。
		* watch 会避免在发生副作用时追踪依赖，因此，能更加精确地控制回调函数的触发时机。
		
		
		* watchEffect，则会在副作用发生期间追踪依赖。
		* 它会在同步执行过程中，自动追踪所有能访问到的响应式属性。
		* 这更方便，而且代码往往更简洁，但有时其响应性依赖关系会不那么明确。


-------------------------
停止监听
-------------------------
	# 自动清理
		* 在 setup() 或 <script setup> 中用同步语句创建的监听器，会自动绑定到宿主组件实例上，并且会在宿主组件卸载时自动停止。
		* 注意，监听器必须用同步语句创建：如果用异步回调创建一个监听器，那么它不会绑定到当前组件上，你必须手动停止它，以防内存泄漏。
		
			<script setup>
			import { watchEffect } from 'vue'

			// 它会自动停止
			watchEffect(() => {})

			// 异步创建的则不会！
			setTimeout(() => {
				watchEffect(() => {})
			}, 100)
			</script>
		
		
	# 手动清理
		* watch 或 watchEffect 返回的函数即停止函数，调用它就会停止监听
		
		const unwatch = watchEffect(() => {})

		// ...当该监听器不再需要时
		unwatch()
	
	
	
	# 尽量用同步监听
	
		* 需要异步创建监听器的情况很少，请尽可能选择同步创建。
		* 如果需要等待一些异步数据，你可以在监听器中使用逻辑条件。
		
		
			// 需要异步请求得到的数据
			const data = ref(null)

			watchEffect(() => {
			  if (data.value) {
				// 数据加载后执行某些操作...
			  }
			})
		
		

----------------------------
副作用清理
----------------------------	
	# 副作用
		* 有时我们可能会在监听器中执行副作用，例如异步请求：
	
			watch(id, (newId) => {
			  fetch(`/api/${newId}`).then(() => {
				// 回调逻辑
			  })
			})
				  
		
		* 但是如果在请求完成之前 id 发生了变化怎么办？当上一个请求完成时，它仍会使用已经过时的 ID 值触发回调。
		* 理想情况下，我们希望能够在 id 变为新值时取消过时的请求。
	
	
	# 使用 onWatcherCleanup()  API 注册清理函数
	
		* 它会在监听器失效并准备重新运行时会被调用
		
			import { watch, onWatcherCleanup } from 'vue'

			watch(id, (newId) => {

				const controller = new AbortController()

				fetch(`/api/${newId}`, { signal: controller.signal }).then(() => {
					// 回调逻辑
				})
			
				onWatcherCleanup(() => {
					// 终止过期请求
					controller.abort()
				})
			})
	
	# onWatcherCleanup 函数还能作为参数传递给监听器
	
		// 作为第三个参数传递给 watch 监听器回调
		watch(id, (newId, oldId, onCleanup) => {
		  // ...
		  onCleanup(() => {
			// 清理逻辑
		  })
		})
		
		// 作为第一个参数传递给 watchEffect 监听器回调
		watchEffect((onCleanup) => {
		  // ...
		  onCleanup(() => {
			// 清理逻辑
		  })
		})
	
	# 注意
		* onWatcherCleanup 必须在 watchEffect 效果函数或 watch 回调函数的同步执行期间调用
		* 不能在异步函数的 await 语句之后调用它
		
-----------------------------
回调的触发时机
-----------------------------
	# 更改了响应式状态后，可能会同时触发 Vue 组件更新和监听器回调。
	
		* 类似于组件更新，用户创建的监听器回调函数也会被批量处理以避免重复调用。
		
		* 默认情况下，监听器回调会在父组件更新（如有）之后、所属组件的 DOM 更新之前被调用。
		* 这意味着如果你尝试在监听器回调中访问所属组件的 DOM，那么 DOM 将处于更新前的状态。
	
	
	# 通过 flush: 'post' 选项访问被 Vue 更新之后的所属组件的 DOM
	
		* 它在 Vue 进行任何更新之后触发
	
			watch(
				source, 
				callback, 
				{flush: 'post'}
			)

			watchEffect(
				callback, 
				{flush: 'post'}
			)
		
		
		* 后置刷新的 watchEffect() 有个更方便的别名 watchPostEffect()
		
			import { watchPostEffect } from 'vue'

			watchPostEffect(() => {
			  /* 在 Vue 更新后执行 */
			})
	
	
	# 通过 flush: 'sync' 选项创建一个同步触发的监听器

		* 它在 Vue 进行任何更新之前触发
		
			watch(
				source, 
				callback, 
				{flush: 'sync'}
			)

			watchEffect(
				callback, 
				{flush: 'sync'}
			)
	
		
		* 同步触发的 watchEffect() 有个更方便的别名 watchSyncEffect()：
		
			import { watchSyncEffect } from 'vue'

			watchSyncEffect(() => {
			  /* 在响应式数据变化时同步执行 */
			})
	
	
	