------------------------
模板引用
------------------------
	# 使用 ref 属性访问底层的 Dom 元素
		* 使用 ref 属性允许在一个特定的 DOM 元素或子组件实例被挂载后，获得对它的直接引用。
		* 通过 useTemplateRef 定义 ref 属性
		
			<input type="text" ref="input">

			import { onMounted, useTemplateRef} from 'vue';
			
			
			// useTemplateRef 中的 ref 名称要和 dom 节点中的 ref 属性值一致
			const inputEle = useTemplateRef('input');

			onMounted(() => {
				console.log(inputEle.value);  // 原始的 dom 对象： <input type="text">
				inputEle.value.focus(); // 聚焦
			});
			
		
		* 注意，只可以在组件挂载后才能访问模板引用。在
		* 监听器或者是模板中的表达式上访问 dom 元素的时候要考虑它为 null 的情况。

	
	# v-for 中的模板引用
		* 当在 v-for 中使用模板引用时，对应的 ref 中包含的值是一个数组，它将在元素被挂载后包含对应整个列表的所有元素。
		
			<ul>
				<li v-for="item in list" ref="items">
					{{ item }}
				</li>
			</ul>

			import { ref, useTemplateRef, onMounted } from 'vue'
			
			
			// 要渲染的列表
			const list = ref([
				/* TODO  */
			])

			const itemRefs = useTemplateRef('items')

			onMounted(() => console.log(itemRefs.value))
		
		* 注意，ref 数组并不保证与源数组相同的顺序。
	
	
	# 函数模板引用
		* 除了使用字符串值作名字，ref 还可以绑定为一个函数，会在每次组件更新时都被调用。
		* 该函数会收到元素引用作为其第一个参数
		
			// 匿名函数
			<input type="text" :ref="(ele) => ele.focus()"/>
			
			
			// 具名函数
			<input type="text" :ref="eleFocus"/>
			
			const eleFocus = ele => {
				console.log(ele);  // 原始的 dom 对象：<input type="text">
				ele.focus(); // 聚焦
			}


	
	# 组件上的 ref
		
		* 模板引用也可以被用在一个子组件上。这种情况下引用中获得的值是组件实例。
		
			<Child ref="child" />
			
			import { useTemplateRef, onMounted } from 'vue'
			import Child from './Child.vue'

			const childRef = useTemplateRef('child')

			onMounted(() => {
			  // childRef.value 持有 <Child /> 的实例
			})
		
		* 选项式 API 子组件
		
			* 如果子组件使用的是选项式 API 或没有使用 <script setup>，被引用的组件实例和该子组件的 this 完全一致
			* 这意味着父组件对子组件的每一个属性和方法都有完全的访问权
			
		
		* 组合式组件
			
			* 使用了 <script setup> 的组件是默认私有的。
			* 一个父组件无法访问到一个使用了 <script setup> 的子组件中的任何东西，除非子组件在其中通过 defineExpose 宏显式暴露：
			
				import { ref } from 'vue'
				
				// number 类型的基本变量和响应式变量
				const a = 1
				const b = ref(2)

				// 暴露出去 a, b 属性，可以让引用当前组件的父组件通过 ref 引用直接访问
				// defineExpose 必须在任何 await 操作之前调用。否则，在 await 操作后暴露的属性和方法将无法访问。
				defineExpose({
					a,
					b
				})
					
				
				// 在父组件中访问到的子组件实例如下
				{ a: number, b: number } // Ref 会自动解包，和一般的实例一样


