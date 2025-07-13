-------------------------
封装组件
-------------------------
	# MyInput.vue
		
		* 封装 Element Plus 中的 <el-input> 组件

		<template>
			<div>
				<!-- 使用 component 动态组件，可以很方便的处理属性、方法、插槽的传递问题 -->
				<component :is="h(ElInput, {...$attrs, ...props, ref: changeRef}, $slots)"></component>
			</div>
		</template>

		<script setup>

		import { h, getCurrentInstance} from 'vue';
		import { ElInput } from 'element-plus';
		import { inputProps } from 'element-plus';

		// 定义属性，继承自 Input 的属性定义
		const props = defineProps({...inputProps, foo: {type: String}})

		// 当前组件
		const vm = getCurrentInstance();

		// 获取到子组件的 ref，暴露它的所有暴露的方法到当前组件
		function changeRef(inputInstance){
			// 当组件有效的时候，设置当前组件暴露的方法为子组件对象
			vm.exposed = inputInstance || {};
			vm.exposeProxy = inputInstance || {};
		}
		</script>
	
	# Index.vue
		
		* 导入、使用自定义的 MyInput 组件

		<template>

			<div class="w-[200px]">

				<!-- 使用自定义的组件 -->
				<MyInput 
					ref="myIn" 
					placeholder="嗨嗨嗨嗨嗨嗨！" 
					foo="Bar" 
					v-model="myInputModel"
				>

					<!-- 传递插槽 -->
					<template #suffix>
						<el-icon>
							<Edit></Edit>
						</el-icon>
					</template>
				</MyInput> 
				
				<el-button @click="clear">清理</el-button>
			</div>
		</template>

		<script setup>

		// 导入自定义组件
		import MyInput from '../components/MyInput.vue';
		import { ref, useTemplateRef } from 'vue';

		// 绑定 Model
		const myInputModel = ref('');

		// 获取到自定义组件
		const myInputRef = useTemplateRef('myIn');

		// 调用暴露的组件的方法
		function clear(){
			myInputRef.value.clear();
		}

		</script>

		<style scoped>
		</style>


