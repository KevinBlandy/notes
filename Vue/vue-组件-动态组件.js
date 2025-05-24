----------------
动态组件
----------------
	# 使用内置的元组件 component
		<component :is="view" />

		* is 属性可以组件的名称（必须先对其进行注册）或者组件的实例
		* 甚至可以动态渲染原生的 HTML 元素
			<component :is="'input'" placeholder="请输入用户名密码"></component>
			<component is="button">我是按钮</component>
	

	# 当使用 <component :is="..."> 来在多个组件间作切换时，被切换掉的组件会被卸载
		* 可以通过 <KeepAlive> 组件强制被切换掉的组件仍然保持“存活”的状态。

	# 渲染原生属性时 v-model 的局限性
		
		* 渲染原生 HTML 属性于 v-model 不兼容

			{{ inputModel }}
			<div class="box">
				// 由于 'input' 是原生 HTML 元素，因此这个 v-model 不起作用
				<component :is="'input'" v-model="inputModel" placeholder="请输入用户名密码"></component>
			</div>

			
			// model 属性不生效
			const inputModel = ref(null);
		
		
		* 解决办法就是，自己拆解为属性和事件
			{{ inputModel }}
			<div class="box">
				<component 
					:is="'input'" 
					// 绑定属性
					:value="inputModel" 
					// 监听 input 事件，更新属性
					@input="event => inputModel = event.target.value" 
					placeholder="请输入用户名密码">
				</component>
			</div>

			const inputModel = ref(null);
	
	
	# 对于动态组件，不能直接使用 ref 封装组件实例
		
		* 会有警告：Vue received a Component that was made a reactive object.
		
			This can lead to unnecessary performance overhead and should be avoided by marking the component with `markRaw` or using `shallowRef` instead of `ref`. Component that was made reactive:  
		
		* 根据提示，要换成 markRaw 或 shallowRef
			
			<component :is="component"></component>

			import DemoUpdate from '@/components/DemoUpdate.vue';
			import { ref, shallowRef, markRaw } from 'vue';

			// const component = markRaw(DemoUpdate);
			const component = shallowRef(DemoUpdate);
		
		* 换成计算属性也可以

			<component :is="currentComponent"></component>


			import DemoUpdate from '@/components/DemoUpdate.vue';
			import { computed } from 'vue';

			const currentComponent = computed(() => {
				return DemoUpdate;
			});
