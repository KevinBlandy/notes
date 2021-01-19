---------------------------
处理渲染中的循环			|
---------------------------
	# 简单的渲染
		<#list arr as item>
			...
		<#else>
			... arr 为[]
		</#list>
	
	# 在循环开始和结尾处添加输出
		<#list sequence>
			//在循环之前执行一次
			<#items as item>
				// 开始循环每一个元素
			</#items>
			//在循环之后执行一次
		<#else>
			//空集合
		</#list>
	
	# 在第一项之厚,最后一项之前添加东西
		<#list users as user>
			${user}<#sep>, </#sep>
		</#list>

		* 使用<#sep>指令,该指令的字符会被添加到每个遍历项后面
		* 但是会忽略最后一个遍历项的后面
		* 非常方便
	
	# 中断循环
		<#list 1..10 as x>
		  ${x}
		  <#if x == 3>
			<#break>
		  </#if>
		</#list>
	
	# 访问迭代状态
		* 通过内置函数操作迭代项
			<#list ['a','b'] as item>
				${item}-${item?index}-${item?has_next?c}<br/>
			</#list>

		* 可以使用的函数
			counter
				* 返会从1开始的索引
			has_next
				* 是否还有下一个
			index
				* 返回从0开始的索引
			is_even_item
				* 是否是奇数个
			is_first
				* 是否是第一个
			is_last
				* 是否是最后一个
			is_odd_item
				* 是否为偶数个
			item_cycle
				*  以指定何值来代替 "odd" 和 "even", 它也允许多余两个值来循环
				*  取模算法
					<#list ['a', 'b', 'c', 'd', 'e', 'f', 'g'] as i>
					  ${i}-${i?item_cycle('row1', 'row2', 'row3')}</br>
					</#list>
					* 第一个值为:row1
					* 第四个值为:row1
				

			item_parity
				* 返回当前是奇数还是偶数
				*  返回字符串值 "odd" 或 "even"

			item_parity_cap
				* 同上,返回的是大写开头
				* 返回字符串值 "Odd" 或 "Even"
					
	
