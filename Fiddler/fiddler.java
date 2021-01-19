----------------
fiddler
----------------
	# 官网
		https://www.telerik.com/fiddler
	
	# 文档
		https://docs.telerik.com/fiddler


----------------
fiddler 配置项
----------------
	# 修改监听的端口
		Tools -> Options -> Connections -> Fiddler Listens On Port
	
	# 系统启动时, 是否设置系统代理
		Tools -> Options -> Connections -> Act As SystemProxy On Start Up


----------------
fiddler 快捷键
---------------
	r
		* 快速重复请求

	shift + r
		* 弹出输入框, 可以指定重复请求的次数

	shift + delete
		* 删除未选择的请求记录

	delete
		* 删除选中的请求
	
	ctrl + x
		* 删除所有请求
	

----------------
fiddler 断点
---------------
	# 全局断点
		* 可以点击左下角的端点开关, 通过点击切换: 全局请求, 响应, 取消断点
		* 可以选择菜单, Rules ->  Automatic Breakpoints
			选择全局请求, 响应, 禁用断点
	
	# 局部端点
		* 在控制台输入: 
			请求前断点	bpu [表达式]			
			请求后断点	bpafter [表达式]

		* 可以通过表达式, 指定uri等等信息, 只要匹配到, 就会开启断点
		* 断点开启后, 底部会有提示,

		* 再次输入相同的指令: bpu/bpafter 就可以取消断点(不需要表达式)
	
		
	# 在 Filter 中设置断点
		* 可以针对请求方式, 是否带queryString, 是否Ajax, 以及指定的 ContentType 设置断点



		

		

