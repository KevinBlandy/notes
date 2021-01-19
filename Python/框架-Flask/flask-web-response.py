--------------------------------
response						|
--------------------------------
	* HTTP响应对象
	* 视图函数的返回值会被自动转换为一个响应对象
		* 如果返回值是一个字符串,它被转换为该字符串为主体的,状态码为 200 OK``的 , MIME 类型是 ``text/html 的响应对象
	
--------------------------------
模版渲染						|
--------------------------------
	from flask import Flask,render_template

	app = Flask(__name__)

	@app.route('/test/<name>')
	def index(name=None):
		return render_template('hello.html', name=name)	
		''' hello.html
			<h1>{{ name }} <h1>
		'''
	
	* 返回 render_template(tempalte,**context) 实例,来完成模版的渲染响应
		tempalte:模版名称
		context	:上下文
	* 其实该函数返回的是 str,也就是渲染后的模板字符串数据
	* Flask 会在 templates 文件夹里寻找模板
	* 如果应用是个模块,这个文件夹应该与模块同级
		application.py
		templates
			|-hello.html
		
	* 如果它是一个包,那么这个文件夹作为包的子目录
		application
			|-__init__.py
			|-templates
				|-hello.html

--------------------------------
构建 response					|
--------------------------------
	* Flask 把返回值转换为响应对象的逻辑是这样:
		如果返回的是一个合法的响应对象,它会从视图直接返回
		如果返回的是一个字符串,响应对象会用字符串数据和默认参数创建
			return "Hello World"
		如果返回的是一个元组,且元组中的元素可以提供额外的信息,这样的元组必须是 (response, status, headers) 的形式,且至少包含一个元素, status 值会覆盖状态代码, headers 可以是一个列表或字典,作为额外的消息标头值
			 return ("{'name':'Kevin'}",201,{'Content-Type':'application/json;charset=UTF-8'})
		如果上述条件均不满足, Flask 会假设返回值是一个合法的 WSGI 应用程序,并转换为一个请求对象
	
	* 如果你想在视图里操纵上述步骤结果的响应对象,可以使用 make_response() 函数
		from flask import make_response

		@app.errorhandler(404)
		def not_found(error):
			# 只需要把返回值表达式传递给 make_response() ,获取结果对象并修改,然后再返回它
			response = make_response(render_template('error.html'), 404)
			# 设置响应头
			response.headers['X-Something'] = 'A value'
			# 设置cookie
			response.set_cookie('username', 'the username')
			return response
	
	* JSON响应构建
		from flask import abort, Flask,make_response
		app = Flask(__name__)

		@app.route('/',methods=['GET'])
		def hello_world():
			# 构建JSON响应体 & 状态码
			response = make_response("{'name':'Kevin'}",200)
			response.headers['Content-Type'] = 'application/json;charset=UTF-8'
			response.set_cookie('JSESSIONID','F8575532',max_age=-1)
			return response
	
	* make_response 响应的是一个 Response 对象
	* 也可以自己创建一个 Response对象
		from flask import Response
	
--------------------------------
重定向							|
--------------------------------
	* 用 redirect() 函数把用户重定向到其它地方
		from flask import redirect, url_for

		@app.route('/')
		def index():
			return redirect(url_for('login'))
		
		* url_for的第一个参数是处理方法的名称, 不是url
		* 还可以有第二个参数，用来添加重定向的url参数
			return redirect(url_for('name_view', id = user_id))
	
	* 也可以重定向到站外连接:return redirect('http://javaweb.io')

--------------------------------
错误响应						|
--------------------------------
	* 使用 abort() 函数,进行错误的HTTP响应
	* abort() 函数以后的代码不会执行
		from flask import abort

		@app.route('/login')
		def login():
			abort(401)
			print('这里不会执行')
	* 可以添加自定义的错误提示信息
		abort(401,'get out') 

--------------------------------
cookie							|
--------------------------------
	* 通过 set_cookie() 来设置cookie
		from flask import Flask,render_template
		from flask import make_response

		app = Flask(__name__)

		@app.route('/')
		def index():
			# 构建响应
			resp = make_response(render_template(...))
			# 设置cookie
			resp.set_cookie('username', 'the username')
			return resp
	
	* set_cookie(key, value='', max_age=None, expires=None, path='/',domain=None, secure=False, httponly=False)
	* 参数不必要多说,一看就懂
		

--------------------------------
文件下载						|
--------------------------------
	* 下载本地已有文件
	* 通过 send_from_directory() 来完成文件的下载
		from flask import abort, Flask,redirect,make_response,send_from_directory
		@app.route("/download", methods=['GET'])
		def download_file():
			# 目标文件
			file = 'D:\\中文.jpg'
			# 目录/文件
			dir,file_name = os.path.split(file)
			# 通过 send_from_directory(目录,文件,是否是下载) 构建响应体
			response = make_response(send_from_directory(dir, file_name, as_attachment=True))
			# 构建响应头,提醒浏览器是要进行文件的下载,并且指定文件的名称,使用 latin-1 编码防止乱码
			response.headers["Content-Disposition"] = "attachment; filename={0}".format(file_name.encode().decode('latin-1'))
			return response
	* send_from_directory(dir,filename,**args)

	* 直接响应二进制数据
		@app.route('/download')
		def down():
			with open('E:\\pom.xml','rb') as file:
				# 直接使用二进制数据来构建 response
				response = make_response(file.read())
				# 添加响应头
				response.headers["Content-Disposition"] = "attachment; filename={0}".format('文件名称.pom'.encode().decode('latin-1'))
				return response

		

		