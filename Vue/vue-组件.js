----------------------
组件
----------------------
	# 定义组件
		* 组合式，使用 .vue 文件定义一个组件
		* 这叫做单文件组件（简称 SFC），组件将会以默认导出的形式被暴露给外部。
	
	# 使用组件

		* 通过 <script setup>，导入的组件都在模板中直接可用。
		* 也可以全局注册组件，不需要导入，全局可用（不推荐，不能进行 tree-shaking 优化）。
			
			// 链式调用，注册多个全局组件
			app.component('ComponentA', ComponentA)
			  .component('ComponentB', ComponentB)
			  .component('ComponentC', ComponentC)
		
		* 每当使用一个组件，就创建了一个新的实例，每个实例都有各自的状态。
	
		* 在单文件组件中，推荐使用驼峰标签名称，可以不关闭标签。
			<MyComponent >
		
		* 在 DOM 中写模板，引用组件的时候，需要遵循浏览器的约定，使用短横线分割，小写，并且显示关闭组件。
			<my-component></my-component>
			
	
		* 在 Dom 中写模板，还要注意元素位置限制
		
			* HTML 元素对于放在其中的元素类型有限制，例如 <ul>，<ol>，<table>
			
				<table>
					// 自定义的组件 <blog-post-row> 将作为无效的内容被忽略
					<blog-post-row></blog-post-row>
				</table>
			
			* 可以使用特殊的 is attribute 作为一种解决方案
			
				<table>
					// tr 是 table 下的合法标签
					// 通过 is 属性 和 vue 前缀指定真实要渲染的组件
					<tr is="vue:blog-post-row"></tr>
				</table>
				
				// 当使用在原生 HTML 元素上时，is 的值必须加上前缀 vue: 才可以被解析为一个 Vue 组件。这一点是必要的，为了避免和原生的自定义内置元素相混淆。
	
	# 通过 component 实现动态组件
		* 有些场景会需要在两个组件间来回切换，比如 Tab 界面。
		
			<!-- currentTab 改变时组件也改变 -->
			<component :is="tabs[currentTab]"></component>
			
		
		* :is 的值可以是以下几种：

			* 被注册的组件名
			* 导入的组件对象
		
		
		* 也可以使用 is attribute 来创建一般的 HTML 元素。
		
		* <component :is="..."> 来在多个组件间作切换时，被切换掉的组件会被卸载。
		* 可以通过 <KeepAlive> 组件强制被切换掉的组件仍然保持“存活”的状态。
		
			<KeepAlive> 
				<component :is="tabs[currentTab]"></component>
			</KeepAlive> 
		
		
----------------------
生命周期钩子
----------------------
	# 每个 Vue 组件实例在创建时都需要经历一系列的初始化步骤
		* 有一些钩子函数，会在实例生命周期的不同阶段被调用
		* 这些钩子函数应当在组件初始化时被同步注册

			setTimeout(() => {
			  onMounted(() => {
				// 异步注册时当前组件实例已丢失
				// 这将不会正常工作
			  })
			}, 100)
				
	# onMounted(callback: () => void): void
		* 在组件挂载完成后执行。

	# onUpdated()
	# onUnmounted(callback: () => void): void
		* 在组件实例被卸载之后调用。

	# onBeforeMount(callback: () => void): void
		* 在组件被挂载之前被调用。

	# onBeforeUpdate()
	# onBeforeUnmount()
	# onErrorCaptured()
	# onRenderTracked()
	# onRenderTriggered()
	# onActivated(callback: () => void): void
		* 若组件实例是 <KeepAlive> 缓存树的一部分，当组件被插入到 DOM 中时调用。

	# onDeactivated(callback: () => void): void
		* 若组件实例是 <KeepAlive> 缓存树的一部分，当组件从 DOM 中被移除时调用。

	# onServerPrefetch()


