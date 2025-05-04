----------------------------------
Vue 组合式函数
----------------------------------
	# 组合式函数
		
		* 利用 Vue 的组合式 API 来封装和复用有状态逻辑的函数。
	
			// lib/mouse.js
		
			import { onMounted, onUnmounted, ref } from "vue";

			export function useMouseTrace (){
				const x = ref(0);
				const y = ref(0);

				function onMouseMove ({pageX, pageY}){
					x.value = pageX;
					y.value = pageY;
				}
				
				// 在组件挂载的时候监听
				onMounted(() => {
					window.addEventListener('mousemove', onMouseMove);
				});
				// 在组件卸载的时候卸载监听
				onUnmounted(() => {
					window.removeEventListener('mousemove', onMouseMove);
				});

				return {x, y};
			}
			
			// App.vue
			
			 {{ x }} / {{ y }}
			 
			import { useMouseTrace } from './lib/mouse';
			const {x, y} = useMouseTrace();
		
		* 组合式函数只能在 <script setup> 或 setup() 钩子中被调用。在这些上下文中，它们也只能被同步调用。
		
		* 组合式函数也可以挂靠在所属组件的生命周期上来启动和卸载副作用
	
		* 可以在组合式函数中使用所有的组合式 API
		
		* 组合式函数约定用驼峰命名法命名，并以 "use" 作为开头。
		
		* 推荐使用 toValue() 工具方法来获取输入的参数，以保持响应性
		
		* 推荐组合式函数始终返回一个包含多个 ref 的普通的非响应式对象，这样该对象在组件中被解构为 ref 之后仍可以保持响应性。
			
			// 如果你非要返回的结果是对象，你可以在调用时用 reactive 再封装一遍
			// reactive 会对 ref 参数进行自动解构，不需要 .value 就可以直接访问
			const mouse = reactive(useMouse())
		
		