----------------------
Vue 模板
----------------------
	
	# 在 HTML 中使用 {{ val  }} 渲染值
		<div>{{ title	}}</div>
		
		* 如果遇到 html 等属性，会被进行自动编码
	
	# 使用 v-html 指令渲染原始 HTML
		<div v-html="rawHtml"></div>
		
		* 这个 div 标签中的内容会被替换为 rawHtml 变量的值（原始 html 字符串）
	
	
	# 取值表达式中支持 JS 表达式
	
		{{ number + 1 }}

		{{ ok ? 'YES' : 'NO' }}

		{{ message.split('').reverse().join('') }}

		<div :id="`list-${id}`"></div>
		
		
		* 只要是能放在 return 语句后的表达式都可以放在 {{}} 中
	
	# 模板中的表达式是被沙盒化的，只有部分全局对象可以被访问
		
		* 例如：Date、Math
		* 可以自行在 app.config.globalProperties 上显式地添加其他的全局对象，供所有的 Vue 表达式使用。
	
	
	# 自动解构响应式对象
		* 对于 ref 类型的属性，不需要写 value
	
			const item = ref(0);

			<div class="box">
				{{ item }} // 直接访问即可
			</div>
		
		* 注意，只有顶级的 ref 属性才会被解包
		
		  import { reactive, ref} from 'vue'

		  // count 是顶级的
		  const count = ref(0)
		  // object 也是顶级的
		  const object = { 
			id: ref(1)  // id 不是顶级的
		  }
				
		  <div class="box">
			// 顶级 ref 计算，没问题，结果是：1 
			{{ count + 1 }}     
			
			// 修改非顶级 ref，出事儿了，结果是：[object Object]1 
			{{ object.id + 1}}
			
			// 通过 value 属性修改非定义 ref，没问题，结果是：2
			{{ object.id.value + 1 }}
		  </div>
		  
		 
		* 如果 ref 是 {{  }} 最终的渲染值，都可以解包
			
			// object.id 虽然是非顶级属性，但是它本身是个 ref 值，且是最终渲染的（就是直接渲染，没任何运算）
			// 该特性仅仅是文本插值的一个便利特性，等价于 {{ object.id.value }}
			{{ object.id }}
		
		* 可以先把 ref 解构到顶级，再进行计算
			  //从 object 中解构出 id ref 对象，它就成顶级的了
			const {id} = object;
			
			// 再对顶层的 ref 进行计算，没问题，会自动解包
			{{ id + 1}}


