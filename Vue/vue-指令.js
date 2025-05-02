---------------------------
指令
---------------------------

	# 指令的格式
		v-on:submit.prevent="onSubmit"
		
		v-on
			* 指令名称
		
		submit
			* 指令参数
		
		prevent
			* 指令修饰符
		
		onSubmit
			* 指令的值，可以是变量
	
	
	# 指令中的参数支持表达式，使用 []
	
		v-on:['click']="onClick"
		
		* 通过 [] 来动态的绑定事件，[] 中可以是常量，也可以是变量
		* 动态参数中表达式的值应当是一个字符串，或者是特殊值 null（意为显式移除该绑定）。其他非字符串的值会触发警告
		* 复杂的表达式，建议用计算属性
		* 使用 DOM 内嵌模板 (直接写在 HTML 文件里的模板) 时，要避免在 [] 中使用驼峰变量，因为浏览器会强制将其抓换为小写
			
			// 如果是在 html 中如此定义的话，最终会读取 datascope 这变量作为要绑定的属性名称
			// 在单文件中不受影响
			<a :[dataScope]="value"> ... </a>

---------------------------
v-bind
---------------------------
	# v-bind 用于绑定参数到指定的属性
		<div v-bind:id="idVal"></div> // 绑定 div 的 id 属性值为 idVal 的值
	
	
		* 支持快捷方式语法 :<attr>
			<div :id="idVal"></div>
			
		
		* 如果要绑定的属性和变量同名，甚至可以直接等号
			<div :id></div> // 绑定 div 的 id 属性值为 id 的值
		
		
	# Class 绑定
		* Vue 专门为 class 和 style 的 v-bind 用法提供了特殊的功能增强。
		* 除了字符串外，表达式的值也可以是对象或数组。
		
		* 给 class 传递对象来动态切换 class		
			
			// :class 中的对象，key 就是 class 属性，value 就是 bool 值。如果值为 true，则属性保留
			// 如果 isDisabled 的值是 true 的话，则会给 button 渲染：class="disabled"
			<button :class="{disabled: isDisabled}" @click="isDisabled = true">
				{{ isDisabled }}
			</button>

			import { ref } from 'vue'

			const isDisabled = ref(false);
		
		* 一次性给 class 传递多个属性
	
			<div
			  class="static"
			  :class="{ active: isActive, 'text-danger': hasError }"></div>
			  
			// :class 中的对象，key 就是 class 属性，value 就是 bool 值。如果值为 true，则属性保留
			// 如果 class 中的属性有横杠，则需要使用引号
		
		
		* 一般用的最多的就是给 class 绑定一个返回对象的计算属性
		
		* 可以给 class 绑定一个数组来渲染多个 CSS class
			
			const activeClass = ref('active')
			const errorClass = ref('text-danger')
			
			// 直接绑定属性，渲染结果是 <div class="active text-danger"></div>
			<div :class="[activeClass, errorClass]"></div>
			
			
			// 可以使用三元表达式进行条件渲染，只有 isActive 为 true 时才会渲染 activeClass 属性
			<div :class="[isActive ? activeClass : '', errorClass]"></div>
			
			
			// 嫌三元表达式麻烦，可以直接塞一个对象，key 是属性，value 是 bool 值，控制是否渲染 key（这里是 active）
			<div :class="[{ [activeClass]: isActive }, errorClass]"></div>
		
	
		* 在组件中绑定 class 属性时，这些 class 会被添加到根元素上并与该元素上已有的 class 合并。
			* 如果组件有多个根元素，则需要通过组件的 $attrs 属性来指定接收 class 属性的元素。
		
	# style 绑定
		
		* 绑定对象
			<button :style="{outline: 'none', borderRadius: 5 + 'px', 'border-color': 'skyblue'}">我是摁钮</button>
			
			// 1. 属性 key 的名称就是样式属性名称
			// 2. 属性名称推荐使用驼峰，会被自动转换为对应的中划线
			// 3. 非要使用中横线（CSS 中的实际名称)）也可以，需要用引号括起来
		
		
		* 也可以使用返回样式对象的计算属性。
		* 也可以给 style 绑定一个包含多个样式对象的数组。这些对象会被合并后渲染到同一元素上
			
			<div :style="[baseStyles, overridingStyles]"></div>
			// baseStyles 对象和 overridingStyles 中的所有属性会合并一起渲染到 div 上
		
		* 自动前缀，当在 :style 中使用了需要浏览器特殊前缀的 CSS 属性时，Vue 会自动为他们加上相应的前缀。
		* 样式多值，可以对一个样式属性提供多个（不同前缀的）值。

			<div :style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"></div>
			
			// 数组仅会渲染浏览器支持的最后一个值

---------------------------
条件指令
---------------------------

	# v-if / v-else-if / v-else 条件指令，只有在条件为 true 时才会渲染元素

		<h1 v-if="rate === 0">关闭</h1>
		<h1 v-else-if="rate === 1">开启</h1>
		<h1 v-else>不知道</h1>
		
		* 一个 v-else 元素必须跟在一个 v-if 或者 v-else-if 元素后面，否则它将不会被识别。
	
	# 如果条件中包含了多个元素，可以使用 template 封装
		<template v-if="ok">
		  <h1>Title</h1>
		  <p>Paragraph 1</p>
		  <p>Paragraph 2</p>
		</template>
	
	# 可以用来按条件显示一个元素的指令是 v-show
	
		* 其用法基本一样
		* 不同之处在于 v-show 会在 DOM 渲染中保留该元素；v-show 仅切换了该元素上名为 display 的 CSS 属性。
		* v-show 不支持在 <template> 元素上使用，也不能和 v-else 搭配使用。
		* 元素无论初始条件如何，始终会被渲染，只有 CSS display 属性会被切换。
		
		* v-if 有更高的切换开销，而 v-show 有更高的初始渲染开销。
		* 如果需要频繁切换，则使用 v-show 较好；如果在运行时绑定条件很少改变，则 v-if 会更合适。
		

	# 当 v-if 和 v-for 同时存在于一个元素上的时候，v-if 会首先被执行。
	

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
		
		