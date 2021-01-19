---------------------------------------
django.core.handlers.wsgi.WSGIRequest	|
---------------------------------------

---------------------------------------
实例属性								|
---------------------------------------
	dict environ
		* 查看终端的所有信息

	str method
		* 当前HTTP的请求方式,该数据类型是 str
		* 'GET','POST','DELETE','PUT'

	QueryDict POST
		* 从request.body里拿数据再封装到request.POST里面
		* 获取POST提交的过来的key=value参数体
		* 该数据类型是 dict 子类
	
	QueryDict GET
		* 从request.body里拿数据再封装到request.POST里面
		* 同上,该属性由GET情况下获取
	
	bytes body
		* 获取原始的HTTP请求体,是二进制数据
		* 'Content-Type","application/json',请求体就是它
		* 可以直接使用 json.loads(),来获取JSON请求体
	
	str path
		* 当前的请求路径-URI
	
	dict COOKIES
		* 当前请求的所有 cookie,该值是一个 dict

	MultiValueDict FILES
		* 返回的是上传的文件对象(dict),key就是input="name",value 是一个文件描述对象(InMemoryUploadedFile)
		* value (InMemoryUploadedFile)对象具备三个属性:
			name			str		文件的名称
			size			int		文件的大小
			content_type	str		文件的Content-Type
			file			BytesIO	二进制文件实体对象

		* 在多文件上传的情况下
			* 单个文件上传,也可以使用该方法
			* 通过 FILES(MultiValueDict)的 getlist(input_name),api来获取指定名称的文件描述对象集合(list)
				request.FILES.getlist('name')
			* 文件描述对象 InMemoryUploadedFile	--> TemporaryUploadedFile
			* 集合中的每个元素都是:TemporaryUploadedFile,具备和 InMemoryUploadedFile 一样的属性
				name			str				文件的名称
				size			int				文件的大小
				content_type	str				文件的Content-Type
				file			TemporaryFile	二进制文件实体对象(BytesIO	-->	TemporaryFile)
			
		* file 二进制文件实体对象 api
			generator chunks()
				* 读取该文件,并生成块大小为字节的字节数(默认为UploadedFile.DEFAULT_CHUNK_SIZE ),
				* 返回的是一个迭代器
				* 一般用于迭代的写入文件
					file = FILES.get('file')
					with open("E:\\updload.img",'wb') as target_file:
						for i in file.chunks():
							target_file.write(i)

	dict META
		* 返回 dict,里面包含了HTTP请求头等很多信息
		* 关于HTTP请求头信息,所有的HTTP请求头都被转换为了大写,并且前面添加了 HTTP_ 前缀
			HTTP_HOST localhost:8000
			HTTP_CONNECTION keep-alive
			HTTP_CACHE_CONTROL max-age=0
			HTTP_USER_AGENT Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36
			HTTP_UPGRADE_INSECURE_REQUESTS 1
			HTTP_ACCEPT text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
			HTTP_ACCEPT_ENCODING gzip, deflate, br
			HTTP_ACCEPT_LANGUAGE zh-CN,zh;q=0.8
			HTTP_COOKIE UM_distinctid=15dbf6268872dd-081fb9df4ae9be-323f5c0f-1fa400-15dbf626888485; CNZZDATA1263289496=695830195-1502152055-%7C1502152055; _ga=GA1.1.1468046053.1499215491
	
	str scheme
		* 返回协议类型.http/https
	
	str encoding
		* 编码格式

	SimpleLazyObject user
		* TODO

	SessionStore session
		* django.contrib.sessions.backends.db.SessionStore
		* session中间件
	

---------------------------------------
实例方法								|
--------------------------------------
	bool is_ajax()
		* 判断是不是ajax请求
		* 源码
			def is_ajax(self):
				return self.META.get('HTTP_X_REQUESTED_WITH') == 'XMLHttpRequest'
	
	bool is_secure()
		* 判断是不是https请求
	