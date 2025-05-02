---------------------------
表单
---------------------------
	# 表单输入绑定的原始实现
		
		1. 创建 ref 响应式变量
		2. 监听表单的 input 事件，把 event 中的 value 赋值给 ref
	
		<div>
			<input type="text" :value="content" @input="onInput">
			<p>{{ content }}</p>
		</div>
		
		import { ref } from 'vue'

		// ref 响应式对象
		const content = ref(null);

		// 监听 input 事件
		const onInput = (event) => {
			// 读取输入的内容，赋值给 ref
			content.value = event.target.value;
		}

	
	# v-model 指令可以简化原始的步骤
	
		// 给表单绑定 v-model 变量
		<input type="text" v-model="content">
		// 创建 v-model 变量
		const content = ref(null);
	
		
		* v-model 可用于各种不同类型的输入，它会根据所使用的元素自动使用对应的 DOM 属性和事件组合。
		* v-model 不会在 IME 输入还在拼字（音）阶段时触发更新，如果要，则自己实现 input 事件监听器和 value 绑定而不要使用 v-model。
		* v-model 会忽略任何表单元素上初始的 value、checked 或 selected attribute。它将始终将当前绑定的 JavaScript 状态视为数据的正确来源。
		
		* 注意 <textarea> 中是不支持 {{  }}表达式的，要 v-model 来替代：
		
			// 错误
			<textarea>{{ text }}</textarea>

			// 正确
			<textarea v-model="text"></textarea>
	
	
	# 复选框
		* 单一的复选框，绑定布尔类型值
		
			<input type="checkbox" id="checkbox" v-model="checked" />
			<label for="checkbox">{{ checked }}</label>
			
			const checked = ref(true);
		
		* 多个复选框绑定到同一个数组或集合
			
			<p>选择你的英雄：{{ checked }}</p>
			
			<input type="checkbox" value="丁真" v-model="checked" /> 丁真
			<input type="checkbox" value="王源" v-model="checked" /> 王源
			<input type="checkbox" value="孙笑川" v-model="checked" /> 孙笑川
			
			// 会包含已选择的 checkbox 的 value
			const checked = ref([]);

		
	# 单选
		* 绑定到单个变量。
	
	# 选择器
		
		* 单选绑定到变量，多选绑定到数组。
		* 注意：如果 v-model 表达式的初始值不匹配任何一个选择项，则 <select> 元素会渲染成一个“未选择”的状态。
			* 在 iOS 上，这将导致用户无法选择第一项，因为 iOS 在这种情况下不会触发一个 change 事件。
			* 因此，建议提供一个空值的禁用选项。
			
				<div>Selected: {{ selected }}</div>

				<select v-model="selected">
					// 空值的禁用选项，提示用户选择
					<option disabled value="">Please select one</option>
					<option>A</option>
					<option>B</option>
					<option>C</option>
				</select>
	
		
	
	# 值绑定
		* 如果需要动态绑定当前组件实例上的值，可以通过使用 v-bind 来实现。
		* 还可以将选项值绑定为非字符串的数据类型。
	
		* 复选框
			<input
				  type="checkbox"
				  v-model="toggle"
				  true-value="yes"		// 选中的值是 'yes'
				  false-value="no"		// 未选中的值是 'no'
			/>
			// 同样可以通过 v-bind 将其绑定为其他动态值：
			
			
		* 单选按钮
				
			<input type="radio" v-model="pick" :value="first" />
			<input type="radio" v-model="pick" :value="second" />
			// 二选一，选中谁，最终 pick 的值就是谁
		
		
		* 选择器选项
			
			<select v-model="selected">
				// 绑定值为内联对象字面量
				<option :value="{ number: 123 }">123</option>
			</select>
		
	
	# 修饰符
		
		.lazy
			* 默认情况下，v-model 会在每次 input 事件后更新数据（拼音阶段不算）。
			* 可以添加 lazy 修饰符来改为在每次 change 事件后更新数据。
			
		
		.number
			* 让用户输入自动转换为数字，如果该值无法被 parseFloat() 处理，那么将返回原始值。
			* 当输入为空时 (例如用户清空输入字段之后)，会返回一个空字符串，这种行为与 DOM 属性 valueAsNumber 有所不同。
			* 该修饰符会在输入框有 type="number" 时自动启用。
		
		.trim
			* 自动去除用户输入内容中两端的空格。
		
	
	
		