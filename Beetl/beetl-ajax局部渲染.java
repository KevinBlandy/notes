----------------------------
Beetl-ajax局部渲染			|
----------------------------
	# beetl支持局部渲染技术
	# 允许后台处理返回的是一个完成的html片段，这前端浏览器可以直接将这个html片段追加到表格里。


<#menu/>
<#top10> ....</#top10>
<div id="table-container" >
	<%
		//ajax片段开始
		#ajax userTable: {
	%>

		<table>
			<tr><td width=100>id</td><td width=100>姓名</td></tr>
			<% 
				for(user in users){ 
			%>
				<tr><td>${user.id}</td><td>${user.name}</td></tr>
			<% 
				} 
			%>
		</table>

		当前页面<span id="current">${page!1}</span><span style="width:20px"></span>
		<a href="#"><span  class="page">next</span></a> <a href="#" ><span  class="page">pre</span></a>

	<%
		//ajax片段结尾
		}
	%>