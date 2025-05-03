-------------------
透传
-------------------
	# 透传：意思是传递给组件的未声明的属性/事件。
	
		* 最常见的例子就是 class、style 和 id。
		
		* 默认情况下，透传的属性会被添加到根元素上（对于 class 或 style 属性，会合并）。
		* 对于透传的事件，也会绑定到根元素上（如果子组件也监听了根元素的相同事件，则子、父事件都会触发）。
		
	
	# 深度透传
		
		* 如果组件的根组件，只是单单渲染了另一个组件
		* 那么透传还会继续，未定义的属性和事件继续透传给渲染的这个根组件。
	
		
		* 注意
			1. 透传的属性，不会包含当前组件上声明过的 props 或是 emits 事件，换句话说，声明过的 props 和事件被当前组件 “消费” 了。
			2. 透传的属性或事件若符合子组件的声明，也可以作为 props 或 emits 传入子组件。
		
	
	# 禁用继承
		
		* 可以在组件选项中设置 inheritAttrs: false
		* 也可以直接在 <script setup> 中使用 defineOptions
		
			<script setup>
			defineOptions({
				inheritAttrs: false		// 禁止继承属性
			})
			</script>
	
		* 禁用了继承后，可以在模板的表达式中直接用 $attrs 访问到这些透传的属性/事件
			
			// $attrs 对象包含了除组件所声明的 props 和 emits 之外的所有其他 attribute，例如 class，style，v-on 监听器等等。
			<span>{{ $attrs }}</span>
			
			* 和 props 有所不同，透传属性在 JavaScript 中保留了它们原始的大小写，所以像 foo-bar 这样的一个 attribute 需要通过 $attrs['foo-bar'] 来访问。
			* 像 @click 这样的一个 v-on 事件监听器将在此对象下被暴露为一个函数 $attrs.onClick。
		
			
			<div class="btn-wrapper">
				// 手动把透传的属性绑定到指定的元素上
				<button class="btn" v-bind="$attrs">Click Me</button>
			</div>
	
	# 多根节点的继承
		* 多个根节点的组件没有自动透传行为。如果 $attrs 没有被显式绑定，将会抛出一个运行时警告。
	
	# 在 JS 中访问透传属性

		* 使用 useAttrs() API 来访问一个组件的所有透传 attribute：
		
			import { useAttrs } from 'vue'

			const attrs = useAttrs()
		
		* 注意，虽然这里的 attrs 对象总是反映为最新的透传 attribute，但它并不是响应式的（考虑到性能因素）。
		* 也就是说不能通过监听器去监听它的变化。
		* 如果需要响应性，可以使用 prop。或者也可以使用 onUpdated() 使得在每次更新时结合最新的 attrs 执行副作用。

		
	