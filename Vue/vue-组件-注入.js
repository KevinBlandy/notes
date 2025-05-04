---------------
依赖注入
--------------

	# 对于深层嵌套的组件而言，通过 props 逐级透传属性比较麻烦
	# 这种情况下可以使用 provide/inject 进行注入
	
		
		 // 父组件提供值
		import { provide } from 'vue'
		provide(/* 注入名 */ 'message', /* 值 */ 'hello!')
		
		// 子组件获取（注入）值
		import { inject } from 'vue'
		const message = inject('message')
		
		// 还可以通过第二个参数提供默认值
		const value = inject('message', '这是默认值')
		
		const value = inject('key', () => new ExpensiveClass(), true);
	
	
		provide()
			* 接收两个参数。第一个参数被称为注入名，可以是一个字符串或是一个 Symbol。
			* 可以多次调用 provide()，使用不同的注入名，注入不同的依赖值。
			* 第二个参数是提供的值，值可以是任意类型，包括响应式的状态，比如一个 ref（后代组件可以由此和提供者建立响应式的联系）。
		
		inject()
			* 可以通过第二个参数提供默认值
				const value = inject('message', '这是默认值')
			
			* 还可以使用工厂函数来创建默认值
			
				// 第二个参数是工厂函数，表示默认值是由复杂的计算逻辑计算出来的
				// 如果用不到默认值，则不会调用工厂函数，不会浪费计算
				const value = inject('key', () => new ExpensiveClass(), true)
				
				// 第三个参数表示默认值应该被当作一个工厂函数。
		
	
	# 只读属性
		
		* 避免子组件直接修改值，可以使用 readonly() 来包装提供的值
		
			import { ref, provide, readonly } from 'vue'

			const count = ref(0)
			provide('read-only-count', readonly(count))
		
	
	# 可以在应用级别提供值

		import { createApp } from 'vue'
	
		// 创建应用
		const app = createApp({})
		
		// 在应用级别进行注入
		app.provide(/* 注入名 */ 'message', /* 值 */ 'hello!')
		
		
		
		* 这在该应用内的所有组件中都可以注入
	

	# 最佳的实践
		
		* 还是遵循谁的数据，谁负责修改约定，提供值的时候，同时提供修改器
			
			// 父组件 --------------------
			import { provide, ref } from 'vue'

			// 值
			const location = ref('North Pole')

			// 修改器
			function updateLocation() {
				location.value = 'South Pole'
			}

			// 打包提供出去
			provide('location', {
				location,
				updateLocation
			})

			  
			 
			// 子组件 --------------------
			import { inject } from 'vue'
			
			// 获取到值和修改器
			const { location, updateLocation } = inject('location')
			
			// 直接调用父组件提供的修改器进行修改
			<button @click="updateLocation">{{ location }}</button>
		
		* 考虑使用 Symbol 作为属性名来避免潜在的冲突
			
			* 通常推荐在一个单独的文件中导出这些注入名 Symbol。
			
			// keys.js -------------------
			export const myInjectionKey = Symbol()

			// 在供给方组件中 -------------------
			import { provide } from 'vue'
			import { myInjectionKey } from './keys.js'
			provide(myInjectionKey, { /* 要提供的数据 */})

			// 注入方组件 -------------------
			import { inject } from 'vue'
			import { myInjectionKey } from './keys.js'
			const injected = inject(myInjectionKey)
