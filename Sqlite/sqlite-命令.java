----------------------------
sqlite常用命令				|
----------------------------
	.backup [db] [file]	
		* 备份 db 数据库(默认是 "main")到 file 文件

	.open [file]
		* 打开指定的文件
	
	.databases
		* 列出数据库的名称及其所依附的文件
	
	.dump [table]	
		* 以 SQL 文本格式转储数据库
		* 如果指定了 table 表,则只转储table表(table支持模糊表达式)
	
	.exit
		* 退出 SQLite 提示符
	
	.header(s) ON|OFF
		* 开启或关闭头部显

	.import [file] [tables]
		* 导入来自 file 文件的数据到 tables 表中
	
	.show
		* 查看sqlite默认的配置

	.timer ON|OFF	
		* 开启或关闭 CPU 定时器
	
	.mode [mode]
		* 设置输出模式,[mode] 可以是下列之一
			csv			逗号分隔的值
			column		左对齐的列
			html		HTML 的 <table> 代码
			insert		TABLE 表的 SQL 插入（insert）语句
			line		每行一个值
			list		由 .separator 字符串分隔的值
			tabs		由 Tab 分隔的值
			tcl			TCL 列表元素


