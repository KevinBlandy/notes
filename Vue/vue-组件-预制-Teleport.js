-----------------------
Teleport
-----------------------
	# 用于将一个组件内部的一部分模板 “传送” 到该组件的 DOM 结构外层的位置去。

	
		* <Teleport> 接收一个 to 属性来指定传送的目标。
		* 值可以是一个 CSS 选择器字符串，也可以是一个 DOM 元素对象。
		
			// 把 Teleport 中的模板片段传送到 body 标签下
			<Teleport to="body">
			  <div v-if="open" class="modal">
				<p>Hello from the modal!</p>
				<button @click="open = false">Close</button>
			  </div>
			</Teleport>
		
		* <Teleport> 挂载时，传送的 to 目标必须已经存在于 DOM 中。
		* 如果目标元素也是由 Vue 渲染的，需要确保在挂载 <Teleport> 之前先挂载该元素。
		
	
	# 搭配组件使用
		
		* <Teleport> 只改变了渲染的 DOM 结构，它不会影响组件间的逻辑关系。
		* 也就是说，如果 <Teleport> 包含了一个组件，那么该组件始终和这个使用了 <Teleport> 的组件保持逻辑上的父子关系。传入的属性和触发的事件也会照常工作。

		* 这也意味着来自父组件的注入也会按预期工作，子组件将在 Vue Devtools 中嵌套在父级组件下面，而不是放在实际内容移动到的地方。
		
	
	
	# 禁用 Teleport
		
		* 可以通过对 <Teleport> 动态地传入一个 disabled 属性来启用、禁用它。

			<Teleport :disabled="isMobile">
			</Teleport>

	
	
	# 多个 Teleport 共享目标
		
		* 多个 <Teleport> 组件可以将其内容挂载在同一个目标元素上
		* 顺序就是简单的顺次追加，后挂载的将排在目标元素下更后面的位置上，但都在目标元素中。
		
			<Teleport to="#modals">
				<div>A</div>
			</Teleport>
			
			<Teleport to="#modals">
				<div>B</div>
			</Teleport>
			
			// 渲染结果
			
			<div id="modals">
				<div>A</div>
				<div>B</div>
			</div>
		
	
	# 延迟解析
		
		* 可以使用 defer 属性推迟 Teleport 的目标解析，直到应用的其他部分挂载。
		* 这允许 Teleport 将由 Vue 渲染且位于组件树之后部分的容器元素作为目标。
		
			<Teleport defer to="#late-div">...</Teleport>

			// 稍后出现于模板中的某处
			<div id="late-div"></div>
		
		
		* 注意，目标元素必须与 Teleport 在同一个挂载/更新周期内渲染，即如果 <div> 在一秒后才挂载，Teleport 仍然会报错。
		* 延迟 Teleport 的原理与 mounted 生命周期钩子类似。
		
		
		
