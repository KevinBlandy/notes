------------------------
input-通用				|
------------------------
	# 新增类型
		color
		date
		datetime
		datetime-local
		email
		month
		number
		range
		search
		tel
		time
		url
		week

	# 属性
		autocomplete
		autofocus
		form
		formaction
		formenctype
			* 定义在button,重新指定action和编码
		formmethod
		formnovalidate
			* 定义在button,重新指定请求方式,以及不需要校验
		formtarget
		height 
		width
		list
		min 
			* 最小值
		max
			* 最大值
		multiple
		pattern 
			* 设置验证内容的正则表达式
		placeholder
			* 在用户没有输入的时候,提示信息
		required
			* 是否是必输入的框
		step	
			* step 属性为输入域规定合法的数字间隔。
			* 如果 step="3"，则合法的数是 -3,0,3,6 等
			* step 属性可以与 max 和 min 属性创建一个区域值.
		validity
			* 返回验证对象


	# 方法
	# 事件

------------------------
input-file				|
------------------------
	# 文件框
	# 属性
		multiple="multiple"
			* 是否支持多选
		accept="image/gif,image/jpeg,image/png"
			* 允许选择的文件类型
		files
			* * 返回一个file对象数组
			* file
				* 属性
					lastModified		最后一次修改的时间戳
					lastModifiedDate	最后一次修改时间
					name				文件名称
					size				文件大小
					type				文件类型
					webkitRelativePath	相对路径
	# 方法
	