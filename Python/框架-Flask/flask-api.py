-----------------------------
flask						 |
-----------------------------
	request		
		* HttpRequest
	abort
		* HTTP异常响应
	session
		* HttpSession
	Flask
		* application
	render_template
		* 模版渲染
	redirect
		* 重定向
	make_response
		* HttpResponse 
	send_from_directory
		* 文件下载
	url_for
		* URL构建
	escape
		* 转义HTML代码
	

	
-----------------------------
from flask import request	 |
-----------------------------
	* 全局的,代表HttpRequest 对象
	* 属性
		str method
			* 请求方式
	
		dict form
			* 获取POST提交过来的表单参数体 dict		(werkzeug.datastructures.ImmutableMultiDict)
			* 获取多个同名值,也可以用于单个值
				names = request.form.getlist('name')
		
		dict args
			* 获取通过URL传递过来的key=value参数体	(werkzeug.datastructures.ImmutableMultiDict)
			* 获取多个同名值,也可以用于单个值
				names = request.args.getlist('name')
		
		dict values
			* 包含了 form 与 args 的所有参数		(werkzeug.datastructures.CombinedMultiDict)
			* CombinedMultiDict([ImmutableMultiDict([]), ImmutableMultiDict([])])
	
		dict files
			* 获取上传的文件 dict					(werkzeug.datastructures.ImmutableMultiDict)
			* key是文件的表单项名称,value 则是文件体(werkzeug.datastructures.FileStorage)
			* 如果获取多文件表单项,那么使用 getlist() api来获取
				files = getlist('name')
				* 该值是一个文件集合 <FileStorage>[]
				* 单文件也可以使用该api
			
			* 单个文件的属性 & 方法(FileStorage)
				filename
					* 获取文件的名称

				save(path)
					* 保存到本地
				
				read()
					* 获取文件的字节数据(bytes)
					* 可以通过它来获取上传文件的大小
					size = len(upload_file.read())
				
		dict cookies 
			* 获取请求的cookie,dict					(werkzeug.datastructures.ImmutableTypeConversionDict)
			
			headers
			* 有点像,dict子类,但是不是的。			(werkzeug.datastructures.EnvironHeaders)
			* 客户端的请求头信息
			* api
				items()		
				get()
				getlist()
		
		is_xhr
			* 是否是Ajax请求
	* 方法
		get_data()
			* 获取原始的二进制HTTP请求体
		


