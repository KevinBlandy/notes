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