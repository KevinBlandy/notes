------------------------
url映射					|
------------------------
	* 如果url的映射重名的话,那么后定义则会被无视
	* 关于唯一 URL 
		* Flask 的 URL 规则基于 Werkzeug 的路由模块
		* 这个模块背后的思想是基于 Apache 以及更早的 HTTP 服务器主张的先例，保证优雅且唯一的 URL
	* url的重定向行为
		@app.route('/projects/')
		def projects():
			return 'The project page'
			
			http://127.0.0.1:5000/projects
				* 会被重定向到:http://127.0.0.1:5000/projects/

		@app.route('/about')
		def about():
			return 'The about page'

			http://127.0.0.1:5000/about/
				* 404

------------------------
简单映射				|
------------------------
	from flask import Flask
	app = Flask(__name__)
	@app.route('/')
	def hello_world():
		return 'Hello World!'
	

------------------------
RESTFull参数映射		|
------------------------
	1.基本url参数映射(默认)
		@app.route('/user/<username>/page/<page>')
		def show_user_profile(username,page):
			return 'User: %s,page:%s'%(username,page)
		
		* 使用<>来定义url变量
		* http://127.0.0.1:5000/user/kevinBlandy/page/12
			KevinBlandy 会被映射到变量 username 上
			page		会被映射到变量 page 上
		* controller形参参数名必须和<>中的变量一样
		* 如果预定义参数没有传递,则404

		
	
	2,带数据类型转换器的参数映射
		@app.route('/post/<int:post_id>')
		def show_post(post_id):
			return 'Post %d'%(post_id)

		* 转换器有下面几种：
			int		接受整数
			float	同 int ,但是接受浮点数
			path	和默认的相似,但也接受斜线
			uuid	接受UIID
		
		* 如果传递的参数无法转换(int,传递了汉字),则404

------------------------
构造 URL				|
------------------------
	* 使用构造URL好处
		1,反向构建通常比硬编码的描述性更好,更重要的是,它允许你一次性修改 URL, 而不是到处边找边改
		2,URL 构建会转义特殊字符和 Unicode 数据,免去你很多麻烦
		3,如果你的应用不位于 URL 的根路径(比如,在 /myapplication 下，而不是 / ) url_for() 会妥善处理这个问题
	
	* 使用 url_for() ,来构造URL
		from flask import abort, redirect, url_for
		@app.route('/')
		def index():
			return redirect(url_for('login'))
	
