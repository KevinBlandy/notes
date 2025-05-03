------------------------
model
------------------------
	# 要实现组件的双向绑定，需要通过自定义事件和 props
	
		* Foo 子组件
		
			<p>{{ content }}</p>
			<input type="text" @input="onChange"/>

			// 定义属性
			const props = defineProps(['content']);

			// 定义事件
			const emits = defineEmits(['inputChange']);

			const onChange = event => {
				// 在原生的 input 事件中，触发自定义事件
				// 把修改信息报告给高层组件
				emits('inputChange', event.target.value);
			}
		
		* App 父组件
			
			<Foo :content="content" 
			
				// 监听组件的 inputChange 事件
				@input-change="onChange"/>


			import { ref } from 'vue';
			import Foo from './components/Foo.vue';

			const content = ref(null);

			const onChange = val => {
				// 自上而下地修改属性
				content.value = val;
			}
	
	# 使用 defineModel 便捷宏
		
		* 在子组件中定义 model
			
			// 定义 model
			const model = defineModel();
			
			// 在原生事件中直接修改 model 的值
			<button @click="model++;">{{ model }}</button>
		
		* 在父组件中，通过 v-model 绑定
			
			// 通过 v-model 绑定双向模型
			<Foo v-model="model"/>
			
			// 传递给子组件的 model
			// 子父组件可以双向同步更新
			const model = ref(0);
	
		
		* defineModel() 返回的值是一个 ref。
		* defineModel 是一个便利宏，在底层编译器将其展开为以下内容：
		
			* 一个名为 modelValue 的 prop，本地 ref 的值与其同步；
			* 一个名为 update:modelValue 的事件，当本地 ref 的值发生变更时触发。
			
			// 子组件
			 <input
				:value="props.modelValue"
				@input="emit('update:modelValue', $event.target.value)"
			  />
					
			const props = defineProps(['modelValue'])
			const emit = defineEmits(['update:modelValue'])
			
			// 父组件
			<Child
			  :modelValue="foo"
			  @update:modelValue="$event => (foo = $event)"
			/>
		
		
		* 由于 defineModel 声明了一个 prop，可以通过给 defineModel 传递选项，来声明底层 prop 的选项：
		
			// 使 v-model 必填
			const model = defineModel({ required: true })

			// 提供一个默认值
			const model = defineModel({ default: 0 })
			
			
			* 注意，如果为 defineModel prop 设置了一个 default 值且父组件没有为该 prop 提供任何值，会导致父组件与子组件之间不同步。
	
	# 多个 v-model 绑定

		* 组件上的 v-model 可以接受一个参数（命名）
		
			// 子组件，定义一个指定名称的 model
			const title = defineModel('title')
			
			// 把 bookTitle 和子组件中指定名称（title）的 model 双向绑定
			<MyComponent v-model:title="bookTitle" />
		
		
		* 命名 model 定义，也可以使用 props 选项
		
			const title = defineModel('title', { required: true })
			

	# v-model 修饰符
		* v-model 支持自定义的修饰符。
		
		* 子组件中，定义 model，解构出 model 实例和修饰符对象
			
			const [model, modifiers] = defineModel()
			
			// 修饰符对象中的 key 就是修饰符名称，bool 类型的 value 值表示修饰符是否设置了
			// { capitalize: true }
			// 在父组件中设置了：v-model.capitalize="...."
			console.log(modifiers);
		
		
		* 还可以给 defineModel() 传入 get 和 set 这两个选项
			const [model, modifiers] = defineModel({
				set(value) {
					// 在 setter 方法中判断修饰符
					if (modifiers.capitalize) {
						return value.charAt(0).toUpperCase() + value.slice(1)
					}
					return value
				}
			})
	
	