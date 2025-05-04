-------------------
KeepAlive
-------------------
	# 用于在多个组件间动态切换时缓存被移除的组件实例
	
		
		* 通过 <component :is="MyComponent"> 来实现动态组件的时候，组件实例在被替换掉后会被销毁。
		* 这会导致组件丢失已变化的状态
		
		* 可以用 <KeepAlive> 内置组件将这些动态组件包装起来，从而保留被切换出去的组件的状态
		
			<!-- 非活跃的组件将会被缓存！ -->
			<KeepAlive>
				<component :is="activeComponent" />
			</KeepAlive>
	
	
	# 包含/排除
		* <KeepAlive> 默认会缓存内部的所有组件实例，但可以通过 include 和 exclude 属性来定制该行为。
		
		* 这两个属性的值都可以是一个以英文逗号分隔的字符串、一个正则表达式，或是包含这两种类型的一个数组
		* 它会根据组件的 name 选项进行匹配，所以组件如果想要条件性地被 KeepAlive 缓存，就必须显式声明一个 name 选项。
		
			<!-- 以英文逗号分隔的字符串 -->
			<KeepAlive include="a,b">
				<component :is="view" />
			</KeepAlive>

			<!-- 正则表达式 (需使用 `v-bind`) -->
			<KeepAlive :include="/a|b/">
				<component :is="view" />
			</KeepAlive>

			<!-- 数组 (需使用 `v-bind`) -->
			<KeepAlive :include="['a', 'b']">
				<component :is="view" />
			</KeepAlive>
	
	# 最大缓存实例数
		* 可以通过传入 max 属性来限制可被缓存的最大组件实例数。
		* 缓存的实例数量即将超过指定的那个最大数量，则最久没有被访问的缓存实例将被销毁，以便为新的实例腾出空间（LRU）
		
		<KeepAlive :max="10">
			<component :is="activeComponent" />
		</KeepAlive>
	
	# 缓存实例的生命周期
	
		* 组件可以通过 onActivated() 和 onDeactivated() 注册相应的两个状态的生命周期钩子。
		
			import { onActivated, onDeactivated } from 'vue'

			onActivated(() => {
				// 调用时机为首次挂载
				// 以及每次从缓存中被重新插入时
			})

			onDeactivated(() => {
				// 在从 DOM 上移除、进入缓存
				// 以及组件卸载时调用
			})
		
		* onActivated 在组件挂载时也会调用，并且 onDeactivated 在组件卸载时也会调用。
		* 这两个钩子不仅适用于 <KeepAlive> 缓存的根组件，也适用于缓存树中的后代组件。


