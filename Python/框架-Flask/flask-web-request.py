----------------------------
request						|
----------------------------
	* 在 Flask 中由全局的 request 对象来提供这些信息
	* 如果你有一定的 Python 经验,你会好奇,为什么这个对象是全局的,为什么 Flask 还能保证线程安全:答案是环境作用域
	* 'request' 是全局的

----------------------------
请求方法限制				|
----------------------------
	from flask import Flask
	app = Flask(__name__)
	@app.route('/login', methods=['GET', 'POST'])
	def login():
		return "success"
	
	* 通过 @app.route(methods=[]),来限定请求方式
	* 如果使用了指定method以外的请求方式,405
	* 从 Flask 0.6 起,实现了 OPTIONS 的自动处理


----------------------------
请求参数					|
----------------------------
	* demo
		@app.route('/',methods=['GET'])
		def hello_world():
			# 获取POST提交的表单项,实质上是一个dict
			form = request.form
			# 获取URL传递过来的key=value简直项
			args = request.args
			print(args.get('name')) # 如果name不存在,返回 None
			print(form['name'])     # 如果name不存在,响应400
			return 'Hello World!'

	request.form
		* 专门处理POST提交的表单体
	request.args
		* URL传递的key=value键值对
	request.values
		* 上面俩的整合
	* 当以['name']方式访问属性中的不存在的键会抛出一个特殊的 KeyError 异常
	* 你可以像捕获标准的 KeyError 一样来捕获它, 如果你不这么做,它会显示一个 响应 400 
	* 使用 get('name'),方式,如果参数(键)不存在,不会抛出异常,还可以设置默认值:get('name','默认的名称')
	* 不论是URL参数,还是POST表单体参数,如果存在多个同名的值,都可以用 getlist('name') api 来获取值
		names = request.args.getlist('name')	# 单个表单值也是可以的
		for i in names:
			print(i)
	

----------------------------
请求体						|
----------------------------
	* 通过 request.get_data() 来获取原始的HTTP请求体
	* get_data(),返回的是二进制数据
	* demo
		from flask import abort, Flask
		from flask import request
		app = Flask(__name__)

		@app.route('/',methods=['POST'])
		def hello_world():
			data = request.get_data()
			return "ok"
	

----------------------------
文件上传					|
----------------------------
	* demo
		from flask import Flask,render_template
		from flask import request
		from werkzeug.utils import secure_filename

		app = Flask(__name__)

		@app.route('/upload', methods=['POST'])
		def upload_file():
			# 获取所有文件表单项
			files = request.files

			# 获取单个文件
			pic = files['poc']

			# 获取文件的名称
			filename = pic.filename;

			# 获取单独的请求体
			item__headers = pic.headers;

			# 获取文件的二进制数据(bytes)
			datas = pic.read()

			# 获取上传文件的大小
			size = len(datas)

			# 保存,写入指定的地方
			# 如果先调用了 read 方法，那么在此执行 save会写入0字节数据，因为流不能重用
			pic.save('C:\\demo\\' + secure_filename(filename))

			 # 获取多文件,是一个[]
			heads = files.getlist('head')
			return render_template("hello.html")

	* 通过 filename 属性,获取文件的名称, 永远不要信任这个值,这个值是可以伪造的
	* 如果你要把文件按客户端提供的文件名存储在服务器上,那么请把它传递给 Werkzeu 模块,提供的 secure_filename() 函数
		from werkzeug import secure_filename
		fileName = '/var/www/uploads/' + secure_filename(file.filename)

	* 限制文件的上传大小
		from flask import Flask, Request
		app = Flask(__name__)
		app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024
			* 上面的代码将会把上传文件限制为最大 16 MB 
			* 如果请求传输一个更大的文件,Flask 会抛出一个 RequestEntityTooLarge 异常
	
	
----------------------------
读取cookie					|
----------------------------
	* 通过 cookies 属性来访问 Cookies,用响应对象的 set_cookie 方法来设置 Cookies
	* 请求对象的 cookies 属性是一个内容为客户端提交的所有 Cookies 的字典
	* demo
		@app.route('/',methods=['GET'])
		def hello_world():
			cookies = request.cookies
			for k,v in cookies.items():
				print(k,v)
			return render_template("hello.html")
		
		* 使用 [] 访问值的时候,如果数据不存在,抛出异常
		* get() 相当对于 [],取值永远是一个安全的api,如果数据不存在不会抛出异常,而是返回 None
	* 获取cookie的其他属性信息
		(未知)


----------------------------
客户端请求头				|
----------------------------
	* 通过 request.headers 来获取客户端的请求头信息
	from flask import abort, Flask,render_template
	from flask import request

	app = Flask(__name__)

	@app.route('/',methods=['GET'])
	def hello_world():
		headers = request.headers
		for name,value in headers.items():				# 迭代所有的请求头信息
			print(name,value)
		print(headers.get('Content-Type'))				# 获取单个
		print(headers.getlist('User-Agent'))			# 获取多个
		return render_template("hello.html")

