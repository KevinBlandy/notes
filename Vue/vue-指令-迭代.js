---------------------------
循环指令
---------------------------

	# 使用 v-for 指令进行迭代
		<li v-for="item in items">
		  {{ item.message }}
		</li>
		
		* 也可以使用 of 作为分隔符来替代 in，这更接近 JavaScript 的迭代器语法
			<div v-for="item of items"></div>
		
		* 在 v-for 块中可以完整地访问父作用域内的属性和变量。
			// 类似于
			let parentMessage = ....;
			items.forEach((item, index) => {
			  // 可以访问外层的 `parentMessage`
			  // 而 `item` 和 `index` 只在这个作用域可用
			  console.log(parentMessage, item.message, index)
			})
		
		* 对于多层嵌套的 v-for，作用域的工作方式和函数的作用域很类似。每个 v-for 作用域都可以访问到父级作用域。
		
		* 支持对迭代的项目进行解构
					
		* v-for 也支持使用可选的第二个参数表示当前项的位置索引。
			 v-for="(item, index) in items"
		
		* 可以使用 v-for 来遍历一个对象的所有属性。遍历的顺序基于对该对象调用 Object.values() 的返回值。
			<li v-for="(value, key, index) in myObject">
			  {{ index }}. {{ key }}: {{ value }}
			</li>
			
			// 只有 value 是必须的，属性名称和索引都可以省略
		
		* 支持直接迭代数值，从 1 到 n
			<li v-for="n of 10">{{ n }}</li> // 渲染 10 个 li
		
		
		* 要迭代渲染多个元素，同样使用 <template> 标签进行封装
	
		* v-if 比 v-for 的优先级更高，也就是说 v-if 的条件表达式无法访问到 v-for 作用域内定义的变量
			
			// ！！异常，v-if 表达式无法访问 v-for 表达式中的 todo 属性
			<li v-for="todo in todos" v-if="!todo.isComplete">
			  {{ todo.name }}
			</li>
					
			// 解决办法就是，把 v-for 通过 template 封装到 v-if 外面
			<template v-for="todo in todos">
			  <li v-if="!todo.isComplete">
				{{ todo.name }}
			  </li>
			</template>
		
		
		* 同时使用 v-if 和 v-for 是不推荐的，因为这样二者的优先级不明显。
	
	# 通过 key 管理状态
		* 要让可以跟踪每个节点的标识，从而重用和重新排序现有的元素，需要为每个元素对应的块提供一个唯一的 key 属性
						
			<div v-for="item in items" :key="item.id">
				<!-- 内容 -->
			</div>
	
			<template v-for="todo in todos" :key="todo.name">
				<li>{{ todo.name }}</li>
			</template>
		
		* key 绑定的值期望是一个基础类型的值，例如字符串或 number 类型，推荐在任何可行的时候为 v-for 提供一个 key。
	
	
	# 可以直接在组件上使用 v-for，和在一般的元素上使用没有区别
	
	# 数组变化侦测
		* Vue 可以检测数组的如下方法，自动渲染迭代元素
			push()
			pop()
			shift()
			unshift()
			splice()
			sort()
			reverse()
		
		* 对于返回新数组元素的方法，需要将旧的数组替换为新的
		
			// items 是一个数组的 ref
			items.value = items.value.filter((item) => item.message.match(/Foo/))
		
		* 可以考虑使用返回数组的计算属性来实现过滤、转换等操作。
		* 但是要注意，使用 reverse() 和 sort() 的时候务必小心！这两个方法将变更原始数组，计算函数中不应该这么做。
		* 应该在调用这些方法之前创建一个原数组的副本。
		
		