


--------------------------------
ssl								|
--------------------------------
	# app.run(),方法,通过 ssl_context 关键字参数指定证书文件的地址
	from flask import Flask, request, render_template
	app = Flask('flask-application')

	app.config.DEBUG = True


	@app.route('/')
	def hello_world():
		return render_template('index.html')


	if __name__ == '__main__':
		app.run(debug=True, ssl_context=('ssl/fullchain.cer','ssl/javaweb.key'))
