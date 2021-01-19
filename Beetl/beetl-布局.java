------------------------
布局					|
------------------------
	# 布局可以通过Beetl提供的include,layout 以及模板变量来完成。
	# 模板变量能完成复杂的布局

------------------------
layout布局				|
------------------------
	
	
---------------------------------
继承布局：采用模板变量和include	 |
---------------------------------
	1,aaa.html
		<%
			var jsPart = {
		%>
				这些是JS的部分
		<%
			};
			var htmlPart = {
		%>
				这些是HTML部分
		<%
			};
			include('/bbb.html',{js:jsPart,html:htmlPart}){};
		%>
	
	2,bbb.html
		<html>
			<head>
				${js}
			</head>
			<body>
				${html}
			</body>
        </html>
	
	# 访问aaa,会创建模版变量,然后通过 include 引入 bbb.html,并且把模版变量传递给他/她
	# bbb收到模版变量后访问变量
	# include中指定的模板,前面尽量加上 "/"(个人经验)
