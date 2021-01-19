# 基本的文本渲染
	{{name}}

# 通过 v-once 指令,让渲染不在响应式(仅仅渲染一次,属性修改后视图不会修改)
	<span v-once>这个将不会改变: {{ msg }}</span>


# 使用v-html属性处理原始的html,慎重,可能有xss风险
	//{{rwaHtml}} 会把该html以字符串形式输出(转义)
	<p>Using mustaches: {{ rawHtml }}</p>
	//这个 <span> 的内容将会被替换成为属性值 rawHtml
	<p>Using v-html directive: <span v-html="rawHtml"></span></p>
	
	* 直接作为 HTML——会忽略解析属性值中的数据绑定
		data:{
			//仅仅只会渲染出红色的 {{message}},而不会把message渲染为data里面的数据
			html:`<span style='color:red'>{{message}}</span>`,
			message : 'Hi'
		}
	* 不能使用 v-html 来复合局部模板,因为 Vue 不是基于字符串的模板引擎、

# html特殊属性
	* {{name}},不能用作于HTML的特性上,应该使用 v-bind 
		<div v-bind:id="id">{{id}}</div>
	
	* 布尔特性的情况下,它们的存在即暗示为 true,v-bind 工作起来略有不同
		<button v-bind:disabled="isButtonDisabled">Button</button>
		
		* 如果 isButtonDisabled 的值是 null undefined 或 false 则 disabled 特性甚至不会被包含在渲染出来的 <button> 元素中
		* 说白了,如果bind的属性是一个bool值,而且该值为:false 的话,则该属性都不会出现html标签里面
	

# 使用 javascript 表达式
	* 模版提供了完全的 JavaScript 表达式支持
		{{ number + 1 }}
		{{ ok ? 'YES' : 'NO' }}
		{{ message.split('').reverse().join('') }}
		<div v-bind:id="'list-' + id"></div>
	* 表达式会在所属 Vue 实例的数据作用域下作为 JavaScript 被解析
	* 每个绑定都只能包含单个表达式,所以下面的例子都不会生效
		// 这是语句，不是表达式
		{{ var a = 1 }}
		// 流控制也不会生效，请使用三元表达式
		{{ if (ok) { return message } }}
	* 模板表达式都被放在沙盒中,只能访问全局变量的一个白名单,如 Math 和 Date 
	* 不应该在模板表达式中试图访问用户定义的全局变量


		