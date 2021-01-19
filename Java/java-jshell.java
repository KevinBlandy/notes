------------------
jshell
------------------
	# bin目录下 jshell.exe 文件

	
	# 键入 Java 语言表达式, 语句或声明。
		|  或者键入以下命令之一:
		|  /list [<名称或 id>|-all|-start]
		|       列出您键入的源
		|  /edit <名称或 id>
		|       编辑源条目
		|  /drop <名称或 id>
		|       删除源条目
		|  /save [-all|-history|-start] <文件>
		|       将片段源保存到文件
		|  /open <file>
		|       打开文件作为源输入
		|  /vars [<名称或 id>|-all|-start]
		|       列出已声明变量及其值
		|  /methods [<名称或 id>|-all|-start]
		|       列出已声明方法及其签名
		|  /types [<名称或 id>|-all|-start]
		|       列出类型声明
		|  /imports
		|       列出导入的项
		|  /exit [<integer-expression-snippet>]
		|       退出 jshell 工具
		|  /env [-class-path <路径>] [-module-path <路径>] [-add-modules <模块>] ...
		|       查看或更改评估上下文
		|  /reset [-class-path <路径>] [-module-path <路径>] [-add-modules <模块>]...
		|       重置 jshell 工具
		|  /reload [-restore] [-quiet] [-class-path <路径>] [-module-path <路径>]...
		|       重置和重放相关历史记录 -- 当前历史记录或上一个历史记录 (-restore)
		|  /history [-all]
		|       您键入的内容的历史记录
		|  /help [<command>|<subject>]
		|       获取有关使用 jshell 工具的信息
		|  /set editor|start|feedback|mode|prompt|truncation|format ...
		|       设置配置信息
		|  /? [<command>|<subject>]
		|       获取有关使用 jshell 工具的信息
		|  /!
		|       重新运行上一个片段 -- 请参阅 /help rerun
		|  /<id>
		|       按 ID 或 ID 范围重新运行片段 -- 参见 /help rerun
		|  /-<n>
		|       重新运行以前的第 n 个片段 -- 请参阅 /help rerun
		|
		|  有关详细信息, 请键入 '/help', 后跟
		|  命令或主题的名称。
		|  例如 '/help /list' 或 '/help intro'。主题:
		|
		|  intro
		|       jshell 工具的简介
		|  keys
		|       类似 readline 的输入编辑的说明
		|  id
		|       片段 ID 以及如何使用它们的说明
		|  shortcuts
		|       片段和命令输入提示, 信息访问以及
		|       自动代码生成的按键说明
		|  context
		|       /env /reload 和 /reset 的评估上下文选项的说明
		|  rerun
		|       重新评估以前输入片段的方法的说明
	
