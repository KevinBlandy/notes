--------------------
状态管理
--------------------
	# 可以用响应式 API 做简单状态管理
	
		* 使多个组件共享一个共同的状态
		
			// store.js
			import { reactive } from 'vue'
			
			// 暴露出一个 store 响应式对象，全局共享
			export const store = reactive({
				count: 0,
				// 修改方法
				increment() {
					this.count++
				}
			})
		
		
		* 可以使用其他响应式 API 例如 ref() 或是 computed()，或是甚至通过一个组合式函数来返回一个全局状态。
		
			import { ref } from 'vue'

			// 全局状态，创建在模块作用域下
			const globalCount = ref(1)

			export function useCount() {

				// 局部状态，每个组件都会创建
				const localCount = ref(1)

				return {
					globalCount,
					localCount
				}
			}
		
		* Vue 的响应性系统与组件层是解耦的，这使得它非常灵活。
	
