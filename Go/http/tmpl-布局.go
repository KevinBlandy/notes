---------------------
布局
---------------------
	# 模板的定义
		{{ define "header" }}
			<meta charset="UTF-8">
		{{ end }}

		* 模板默认的名称，是文件名称
		* 模板名称还可以是计算结果
	
	# 引入其他模板
		{{template "header"}}
	
	# 模板之间传递变量
		* 定义模板，声明变量
			{{ define "header" }}
				<meta charset="{{ . }}">
			{{ end }}
		
		* 使用模板，传递变量
			{{template "header" "utf-8"}}		// 常量
			 {{ template "common/head.html" .val}}	// 模板变量
		

	# block
		* 使用 {{template "header" . }} 时如果 "header" 不存在，则会异常
		* 可以使用 block，它允许模板不存在，而且可以输出默认值
	
		{{ block "header" .}} header 模板没找到 {{ end }}
		
			* 如果 "header" 模板不存在，则临时定义一个{{define "header"}} header 模板没找到 {{end}}，并执行它。
		