<div>
	<#-- 总记录数 -->
	<#assign total = total/>
	<#-- 当前页码 -->
	<#assign page = page/>
	<#-- 每页显示数量 -->			
	<#assign rows = rows/>
	
	<#-- 计算出总页数 -->
	<#if (total % 10 == 0)>
		<#assign totalPage = (total / rows)?int />
		<#else>
		<#assign totalPage = ((total / rows) + 1)?int />
	</#if>
	
	<#-- 预定义分页变量 -->
	<#assign begin = 0/>
	<#assign end = 0/>
	
	<#if (totalPage <= 10)>
		<#assign begin = 1/>
		<#assign end = totalPage/>
		<#else>
		<#assign begin = page - 5/>
		<#assign end = page + 4/>
		<#if (begin < 1)>
			<#assign begin = 1/>
			<#assign end = 10/>
		</#if> 
		<#if (end > totalPage)>
			<#assign begin = totalPage - 9/>
			<#assign end = totalPage/>
		</#if>
	</#if>
	
	<#-- 显示首页 -->
	<#if (begin > 1)>
		<a href="/test/page?page=1">首页</a>
	</#if>
	
	<#-- 显示上一页 -->
	<#if (page > 1)>
		<a href="/test/page?page=${page - 1}">上一页</a>
	</#if>
	
	<#-- 遍历页码 -->
	<#list begin..end as val>
		<#if (val == page)>
			<#-- 当前页，不渲染连接 -->
			<a>[${val}]</a>
			<#else>
			<#-- 非当前页 -->
			<a href="/test/page?page=${val}">[${val}]</a>
		</#if>
	</#list>
	
	<#-- 显示下一页 -->
	<#if (totalPage > page)>
		<a href="/test/page?page=${page + 1}">下一页</a>
	</#if>
	
	<#-- 显示尾页 -->
	<#if (totalPage > end)>
		<a href="/test/page?page=${totalPage}">最后一页</a>
	</#if>
</div>