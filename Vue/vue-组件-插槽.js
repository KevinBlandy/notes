----------------------------
插槽
----------------------------
	# 在子组件中通过 <slot> 元素定义一个插槽
		
		
		// 在子组件 Foo 中通过 <slot></slot> 定义一个插槽
		<div class="content">
			<slot></slot>
		</div>
		
		
		// 在父组件中，使用子组件时传递插槽（在组件中间定义内容）
		<Foo>
			最是人间留不住，朱颜辞镜花辞树。
		</Foo>
		
		// 最终渲染结果
		<div class="content">
			最是人间留不住，朱颜辞镜花辞树。 
		</div>
	
		
		* 插槽内容可以是任意合法的模板内容，不局限于文本（可以是多个元素，甚至是组件）
	
	
	# 作用域
		
		* 在父组件中定义插槽内容的时候，可以访问到父组件的作用域
		
			{{  content  }}
			
			// 传递给子组件插槽的内容，访问了当前组件中的 content 
			<Foo>{{  content  }}</Foo>
			
			const content = ref("最是人间留不住，朱颜辞镜花辞树。");
		
		
		* 插槽内容无法访问子组件的数据。Vue 模板中的表达式只能访问其定义时所处的作用域，这和 JavaScript 的词法作用域规则是一致的。
		* 换言之：父组件模板中的表达式只能访问父组件的作用域；子组件模板中的表达式只能访问子组件的作用域。
	
	
	# 默认内容
		
		* 可以直接在 slot 标签中定义默认内容

			<div class="content">
				// 当父组件未传递插槽的时候，就会渲染 slot 中的默认内容
				<slot>嘛也没</slot>
			</div>
	
	# 具名插槽
		
		* 如果组件包含了多个插槽，可以通过给 slot 设置 name 属性来给每个插槽命名
		
		    <div class="content">
				<slot></slot>
				<header>
					<slot name="header"></slot>
				</header>
				<div class="main">
					<slot name="body"></slot>
				</div>
				<footer>
					<slot name="footer"></slot>
				</footer>
			</div>
			
			* 未命名的插槽，默认名称为 default
		
		* 父元素要传递插槽时，就需要配置 template 标签和  v-slot:<slotName> 指令指定插槽的名称了
			
			 <Foo>
				// 默认插槽
				// v-slot:default 可以省略其中的 :default 参数
				<template v-slot:default>
					我是默认的
				</template>
				
				// header 插槽
				<template v-slot:header>
					我是 Header
				</template>
			</Foo>
		
			
			* v-slog 的简写为 #，因此上面的写法可以简化为如下
			
				// 默认的插槽
				<template #default>
					我是默认的
				</template>
				
				// header 插槽
				<template #header>
					我是 Header
				</template>
		
		* 当组件同时接收默认插槽和具名插槽时，所有位于顶级的非 <template> 节点都被隐式地视为默认插槽的内容
		
			<Foo>
				// 隐式的默认插槽
				<p>我会被渲染到默认节点中</p>

				<template #header>
					我是 Header
				</template>
				
				// 隐式的默认插槽
				<p>我也会被渲染在默认插槽中</p>
			</Foo>
		
		* 插槽名称也可以是动态的
			
			// 子组件动态定义 slot 名称，这里的 slotName 是变量
			<slot :name="[slotName]"></slot>
			
			// 父组件动态传递 slot 名称，这里的 header 是常量
			<template #['header']>我是 Header</template>

			// 也可以是这样
			<template v-slot:['header']>我是 Header</template>
		
	# 条件插槽
		
		* 在子模板中，根据父组件传递的插槽进行逻辑判断，决定是否要渲染插槽
		* 可以通过 $slots 访问到父组件传递过来的所有插槽，配合 v-if 标签进行判断
		
			// 如果父组件传递了 header 插槽，则渲染
			<template v-if="$slots.header">
				// 渲染父组件提交的 header 插槽
				<slot name="header"></slot>
			</template>
	
	# 插槽内容可以多次渲染
		
		// 子组件循环渲染一个插槽
		<slot name="item" v-for="n of 10" :index="n"></slot>
		
		// 子组件多次渲染一个插槽
		<slot name="item" :index="1"></slot>
        <slot name="item" :index="2"></slot>
		
		// 父组件传递一个插槽
		<Foo >
			<template #item="{index}"> {{ index }}</template>
		</Foo>
	
	# 作用域插槽
		
		* 可以在子组件中，往插槽传递一些数据
		* 在父组件中，定义插槽内容时就可以访问这些数据了
			
			// 子组件往插槽上定义一些属性
			<slot :prefix="'【'" :suffix="'】'"></slot>
			
			// 父组件，通过 v-slot 的 value 值来访问子组件定义的属性
			<template v-slot="slotProps">
				{{ slotProps.prefix }} HellO {{ slotProps.suffix }}
			</template>
				
			// 最终的渲染结果
			【 HellO 】
	
		
		* 在父组件中支持对子组件传递的参数进行解构
			
			// 直接在 v-slot 的值中进行解构
			<template v-slot="{ prefix, suffix }">
				{{ prefix }} HellO {{ suffix }}
			</template>
		
		* 具名作用域的插槽用法类似
			
			// 具名插槽
			<slot 
				name="header" 
				:prefix="'【'" 
				:suffix="'】'">
			</slot>
			
			// 使用快捷方式
			<template #header="{ prefix, suffix }">
				{{ prefix }} HellO {{ suffix }}
			</template>
			
			// 使用 v-slot 指令
			<template v-slot:header="{ prefix, suffix }">
				{{ prefix }} HellO {{ suffix }}
			</template>
		
		* 直接把插槽属性定义在组件上也可以

		    <Foo v-slot:header="{ prefix, suffix }">
				{{ prefix }} HellO {{ suffix }}
			</Foo>
		
		
		* 注意：插槽上的 name 属性是一个 Vue 特别保留的属性，不会作为 props 传递给插槽。
	
	* 如果同时使用了具名插槽与默认插槽，则需要为默认插槽使用显式的 <template> 标签
	
		<div>
			// 默认插槽
			<slot :message="hello"></slot>
			
			// footer 插槽
			<slot name="footer" />
		</div>
		
		<MyComponent>
	
			// 显式指定默认插槽
			<template #default="{ message }">
				<p>{{ message }}</p>
			</template>
			
			// footer 插槽
			<template #footer>
				<p>Here's some contact info</p>
			</template>
		</MyComponent>
	
	