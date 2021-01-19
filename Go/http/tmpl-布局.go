---------------------
布局
---------------------
	# 模板的定义
		{{ define "header" }}
			<meta charset="UTF-8">
		{{ end }}

		* 模板默认的名称，是文件名称
	
	# 引入其他模板
		{{template "header"}}
	
	# 模板之间传递变量
		* 定义模板，声明变量
			{{ define "header" }}
				<meta charset="{{ . }}">
			{{ end }}
		
		* 使用模板，传递变量
			{{template "header" "utf-8"}}
		

