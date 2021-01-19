----------------------------
session						|
----------------------------
	* 除请求对象之外,还有一个 session 对象
	* 它允许你在不同请求间存储特定用户的信息,它是在 Cookies 的基础上实现的,并且对 Cookies 进行密钥签名
	* 这意味着用户可以查看你 Cookie 的内容,但却不能修改它,除非用户知道签名的密钥
	* 要使用会话,你需要设置一个密钥,这里介绍会话如何工作:
	* 没错,它也是一个全局对象
		from flask import session
		<class 'werkzeug.local.LocalProxy'>
		* 有点像dict,实际上非dict子类

		from flask import session, Flask,render_template,make_response

		app = Flask(__name__)
		
		# 必须要定义 app.secret_key 
		app.secret_key = 'A0Zr98j/3yX R~XHH!jmN]LWX/,?RT'

		@app.route('/',methods=['GET'])
		def hello_world():
			print(type(session))
			response =  make_response(render_template("index.html"),200)
			return response

	
	* 添加数据
		session['name'] = 'KevinBlandy'
	
	* 删除数据
		session.pop('name',None)
		del session['name']
	
	* 判断是否存在
		'name' in session
	
	* 获取数据
		session['name']
		* 如果数据不存在,抛出异常 KeyError
	
	* session部分方法
		session.get(name,default)
			* 安全的取值
		session.keys()
		session.values()
		session.items()
		session.fromkeys()
		session.copy()
		session.clear
	

	* session属性
		new
			* 如果会话是新的,则该值为 True

		modified
			* session是否被修改(进行了CUD)
			* 如果是对session里面的对象,进行修改,该值不会发生变化,需要手动的修改该值
				session['objects'].append(42)
				session.modified = True
		permanent
			* 如果设置为True,则会话持续app.permanent_session_lifetime秒.默认值为31天
			* 如果设置为False(这是默认值),当用户关闭浏览器时,会话将被删除

	* 密钥的定义
		* 随机的问题在于很难判断什么是真随机
		* 一个密钥应该足够随机,操作系统可以基于一个密钥随机生成器来生成漂亮的随机值,这个值可以用来做密钥
		
		from flask import Flask
		import os

		app = Flask(__name__)

		app.secret_key = os.urandom(24)
	
