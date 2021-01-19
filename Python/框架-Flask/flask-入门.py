----------------------------
flask						|
----------------------------
	* 学习地址
		http://www.pythondoc.com/flask-mega-tutorial/helloworld.html#flask-hello-world
	
	* Flask依赖两个外部库
		Jinja2模板引擎
		Werkzeug WSGI工具集
	
	

----------------------------
flask-目录结构				|
----------------------------
	application
		static
		templates
		application.py


----------------------------
flask-hello world			|
----------------------------
	from flask import Flask

	app = Flask(__name__)


	@app.route('/')
	def hello_world():
		return 'Hello World!'


	if __name__ == '__main__':
		app.run(debug=True)

----------------------------
征途						|
----------------------------
	1,下载文件时,非本地文件,响应二进制数据

