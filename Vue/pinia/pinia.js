----------------------
Pinia
----------------------
	# 代替 Vuex 的下一代、官方的 Vue 状态管理组件
		https://pinia.vuejs.org/zh/
	
	# 安装
		pnpm install pinia
	

	# 初始化
		* src/store 目录下创建 index.js

			// store/index.js

			import { createPinia } from "pinia";
			// 创建 Pinia
			let pinia = createPinia();
			export default pinia;
		
		* 在 main.js 中导入，以插件形式载入到 App 根组件

			import { createApp } from 'vue'
			import App from './App.vue'
			import store from '@/store/index.js'

			const app = createApp(App);
			app.use(store);
			app.mount('#app');

		
		* 在 src/store 目录下，创建不同的 store 资源
			// store/mystore/index.js

			import { defineStore } from 'pinia';

			// 使用 defineStore 创建 store，指定名称为 myStore
			const useMyStore = defineStore('myStore', {
				// state 方法返回数据对象
				state (){
					return {
						val: 1,
					}
				},
				getters: {
					doubleVal(){
						return this.val * this.val;
					},
					sameVal: (state) => state.val;
				},
				// actions 可以定义方法
				actions: {
					add(){
						// 通过 this 访问 state
						this.val += 1;
					}
				}
			});

			export default useMyStore;


			* 名字 ，也被用作 id ，是必须传入的，且必须全局唯一
			* 第二个参数可接受两类值：Setup 函数或 Option 对象。


			* Pinia 的目录一般被命名为 stores 而不是 store。这是为了强调 Pinia 可以使用多个 store

		
		* 在组件中导入使用

			  import useMyStore from '@/store/mystore/index.js';

			  // 获取 store
			  const myStore = useMyStore();

			  myStore.val; // 访问 store 中的数据
			  myStore.add(); // 调用 store 中的方法

	# 三个概念
		* store 有 state、getters 和 actions 三个概念（属性）。
		* 可以假设这些概念相当于组件中的 data、 computed 和 methods。

		* state
			* 通过 store 实例访问 state，直接对其进行读写。
			* 新的属性如果没有在 state() 中被定义，则不能被添加。
			* 可以调用 store 的 $reset() 方法将 state 重置为初始值。
				* 在 Setup Stores 中，需要创建自己的 $reset() 方法。
			
		* Getters
			* 全等同于 store 的 state 的计算值。
			* 推荐使用箭头函数，它接收 state 作为第一个参数（不使用箭头函数的话通过 this 访问 store 实例）。
			* 在 Getter 中也可以可以访问到其他任何 getter。
			
			* Getter 不建议接收参数，你非要的话要再封装一层
				  getters: {
					getUserById: (state) => {
					  // 从 getter 返回一个函数，该函数可以接受任意参数
					  return (userId) => state.users.find((user) => user.id === userId)
					},
				  },
				
				// 使用 {{ getUserById(2) }}

		* Action
			* 相当于组件中的 method，是定义业务逻辑的完美选择。
			* action 可通过 this 访问整个 store 实例。
			* action 可以是异步的，你可以在它们里面 await 调用任何 API，以及其他 action！
		
		* 两个或更多的 store 相互使用，它们不可以通过 getters 或 actions 创建一个无限循环。
		* 也不可以同时在它们的 setup 函数中直接互相读取对方的 state：
		
	
	# Setup 风格的 Store

		export const useCounterStore = defineStore('counter', () => {

			const count = ref(0)

			const doubleCount = computed(() => count.value * 2)

			function increment() {
				count.value++
			}
			return { count, doubleCount, increment }
		})

		* ref() 就是 state 属性
		* computed() 就是 getters
		* function() 就是 actions
		
		* 要让 pinia 正确识别 state，必须在 setup store 中返回 state 的所有属性。这意味着，不能在 store 中使用私有属性。
	
	# Store 是一个用 reactive 包装的对象
		* 这意味着不需要在 getters 后面写 .value
		* 也像 setup 中的 props 一样，不能对它进行解构
	

	# 从 Store 中解构出响应式属性

		import { storeToRefs } from 'pinia'

		const store = useCounterStore()

		// `name` 和 `doubleCount` 是响应式的 ref

		// 同时通过插件添加的属性也会被提取为 ref

		// 并且会跳过所有的 action 或非响应式 (不是 ref 或 reactive) 的属性
		const { name, doubleCount } = storeToRefs(store)

		// 作为 action 的 increment 可以直接解构
		const { increment } = store
	
	# 使用 $patch 方法，一次性修改多个属性（类似于 patch 请求的语义）
		store.$patch({
		  count: store.count + 1,
		  age: 120,
		  name: 'DIO',
		})
			 
		* 不过，用这种语法的话，有些变更真的很难实现或者很耗时
		* 不能完全替换掉 store 的 state，因为那样会破坏其响应性。但是，可以 patch 它。
	
	# 通过 $subscribe() 方法侦听 state 及其变化
		cartStore.$subscribe((mutation, state) => {
		  // import { MutationType } from 'pinia'


		  mutation.type // 'direct' | 'patch object' | 'patch function'

		  // 和 cartStore.$id 一样
		  mutation.storeId // 'cart'

		  // 只有 mutation.type === 'patch object'的情况下才可用
		  mutation.payload // 传递给 cartStore.$patch() 的补丁对象。

		  // 每当状态发生变化时，将整个 state 持久化到本地存储。
		  localStorage.setItem('cart', JSON.stringify(state))
		})
			 
		* 可以在 pinia 实例上使用 watch() 函数侦听整个 state。
			watch(
			  pinia.state,
			  (state) => {
				// 每当状态发生变化时，将整个 state 持久化到本地存储。
				localStorage.setItem('piniaState', JSON.stringify(state))
			  },
			  { deep: true }
			)
	
	# 通过 store.$onAction() 来监听 action 和它们的结果

----------------------
插件
----------------------
	# 支持插件
		* Pinia 插件是一个函数，可以选择性地返回要添加到 store 的属性。
		
			import { createPinia } from 'pinia'

			// 创建的每个 store 中都会添加一个名为 `secret` 的属性。
			// 在安装此插件后，插件可以保存在不同的文件中
			function SecretPiniaPlugin() {
			  return { secret: 'the cake is a lie' }
			}

			const pinia = createPinia()

			// 将该插件交给 Pinia
			pinia.use(SecretPiniaPlugin)

			// 在另一个文件中
			const store = useStore()
			store.secret // 'the cake is a lie'
		
		* 它接收一个可选参数，即 context。
			export function myPiniaPlugin(context) {
			  context.pinia // 用 `createPinia()` 创建的 pinia。
			  context.app // 用 `createApp()` 创建的当前应用(仅 Vue 3)。
			  context.store // 该插件想扩展的 store
			  context.options // 定义传给 `defineStore()` 的 store 的可选对象。
			  // ...
			}


			// 然后用 pinia.use() 将这个函数传给 pinia：
			pinia.use(myPiniaPlugin)
	

