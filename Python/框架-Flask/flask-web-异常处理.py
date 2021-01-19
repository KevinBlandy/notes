----------------------------
HTTP异常处理				|
----------------------------
	* 通过 @app.errorhandler() 来处理指定的HTTP状态异常
	* 处理方式是转发,不是重定向
		from flask import render_template,request

		@app.errorhandler(404)
		def page_not_found(error):
			print(error)		# error是系统默认的异常提示信息
			# return "找不到了"
			return render_template('404.html')
		
		* render_template() 调用之后的 404 ,这告诉 Flask,该页的错误代码是 404 ,即没有找到,默认为 200,也就是一切正常
		* 也可以使用模块预定好的异常类来代替错误码(显得更加的直观)
			@app.errorhandler(werkzeug.exceptions.BadRequest)
			* werkzeug.exceptions 模块提供了很多异常类
	
	* 也可以通过 app.register_error_handler 函数来完成异常处理器的注册
		app.register_error_handler(400, lambda e: 'bad request!')

