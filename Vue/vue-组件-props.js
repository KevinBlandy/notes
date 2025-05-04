------------------
Props
------------------
	# 组件需要使用 defineProps() 宏来显示声明接收的属性
	
		// 通过数组定义接收一个 'foo' 属性
		const props = defineProps(['foo'])
		
		// 访问夫组件传递的 foo 属性
		console.log(props.foo)
	
	
	# 属性的命名
		
		* 定义属性推荐使用驼峰
		* 在组件上传递属性，推荐使用短横线
		
		// 子组件定义属性
		defineProps({
			greetingMessage: String
		})
		
		// 传递属性给子组件
		<MyComponent greeting-message="hello" />
	
	#  还可以用对象来描述要接收的属性
	
		// key 是属性名称，value 则是属性类型的构造函数
		const props = defineProps({
			id: Number,
			title: String,
		})

		console.log(props.id);
		
		// 如果在使用组件时传递了错误的类型，会在浏览器控制台中抛出警告
	
	
	# 响应式 prop 解构
		
		* 在计算属性或监听器中访问 props 响应式属性时，属性将被跟踪为依赖项。
		
			// 解构出需要的属性
			const { id, title } = defineProps({
				id: Number,
				title: String,
			})
		
			// 监听属性的变更
			watchEffect(() => {
				console.log('ID  改变了:' + id);
			});
			
			// foo 是一个实际的常量，永远不会改变。在 3.5 及以上版本，当在同一个 <script setup> 代码块中访问由 defineProps 解构的变量时，Vue 编译器会自动在前面添加 props。
		
		
		* 可以使用 JavaScript 原生的默认值语法声明 props 
			
			const { val = 'undefined'} = defineProps(['val']);
		
		* 将解构的 props 传递到函数中
			
			// 使用 getter 传递，是推荐的做法
			// 外部函数可以调用 getter（或使用 toValue 进行规范化）来追踪提供的 prop 的变更
			
			watch(() => id, /* ... */)
			
			useComposable(() => id)
		
		
			
	# 传递不同的值类型
		* 静态方式传递，永远都是字符串
		
			// 子组件中获取的 id 和 title 属性都是字符串
			<Foo id="123" title="我是标题"/>
		
		* 动态方式传递，可以传递任何类型
		
			// id 在这里是 number
			// title 在这里是取变量 title 的值
			<Foo :id="123" :title="title"/>
			
			* 只要记住 v-bind 指令的值本质上是一个 js 表达式就行
			* 在表达式里面可以用字面量、变量、响应式变量
		
		* boolean 类型
				
			// 稍微有点特殊的是，声明即是 true
			// 等于 :enabled="false"
			<Foo  enabled/>
			
			// 显示设置为 false
			<Foo :enabled="false"/>
		
		
		* 使用 v-bind 绑定对象的所有属性
				
			// 把对象的所有属性传递给子组件
			<Foo v-bind="props" />
			
			// 相当于 <Foo :id="props.id" :title="props.title" :enabled="props.enabled"/>
			
			// 定义对象
			const props = reactive({
				id: 1,
				title: '0xFF',
				enabled: true
			});
			
	
	# 单向数据流
		* Vue 所有的 props 都遵循着单向绑定原则，props 因父组件的更新而变化
		* 不要在子组件中修改 props
		
		* 非要修改，推荐的做法
		
			1. 根据 props 初始化自己的变量，要修改，修改自己的
			
				const props = defineProps(['initialCounter'])

				// 计数器只是将 props.initialCounter 作为初始值
				// 像下面这样做就使 prop 和后续更新无关了
				const counter = ref(props.initialCounter)
			
			2. 使用计算属性，对 props 属性进行计算（模拟出修改后的结果）
			
				const props = defineProps(['size'])

				// 该 prop 变更时计算属性也会自动更新
				const normalizedSize = computed(() => props.size.trim().toLowerCase())
		
		* 对于对象 / 数组类型的 props，不建议 props 修改其属性。而建议通过事件向上反馈，由父组件来进行修改。
		* 总之 '谁的属性谁修改'。
	
	
	# 属性的校验，花样有点多
	
		defineProps({
		  // 基础类型检查
		  // （给出 `null` 和 `undefined` 值则会跳过任何类型检查）
		  propA: Number,
		  // 多种可能的类型
		  propB: [String, Number],
		  // 必传，且为 String 类型
		  propC: {
			type: String,
			required: true
		  },
		  // 必传但可为 null 的字符串
		  propD: {
			type: [String, null],
			required: true
		  },
		  // Number 类型的默认值
		  propE: {
			type: Number,
			default: 100
		  },
		  // 对象类型的默认值
		  propF: {
			type: Object,
			// 对象或数组的默认值
			// 必须从一个工厂函数返回。
			// 该函数接收组件所接收到的原始 prop 作为参数。
			default(rawProps) {
			  return { message: 'hello' }
			}
		  },
		  // 自定义类型校验函数
		  // 在 3.4+ 中完整的 props 作为第二个参数传入
		  propG: {
			validator(value, props) {
			  // The value must match one of these strings
			  return ['success', 'warning', 'danger'].includes(value)
			}
		  },
		  // 函数类型的默认值
		  propH: {
			type: Function,
			// 不像对象或数组的默认，这不是一个
			// 工厂函数。这会是一个用来作为默认值的函数
			default() {
			  return 'Default function'
			}
		  }
		})
		
		* 注意，defineProps() 宏中的参数不可以访问 <script setup> 中定义的其他变量，因为在编译时整个表达式都会被移到外部的函数中。