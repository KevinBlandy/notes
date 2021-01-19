----------------------------
注册过滤器					|
----------------------------
	* 如果你要在 Jinja2 中注册你自己的过滤器,你有两种方法
	* 有两种方式注册过滤器
	1,使用装饰器
		
		# 使用 app.template_filter 装饰器来注册
		# 如果该装饰器没有传递参数(@app.template_filter),则会以该函数来作为装饰器的名称
		@app.template_filter('reverse')
		def reverse_filter(s):
			return s[::-1]
	
	2, 添加到 jinja_env 上下文

		# 定义处理函数
		def reverse_filter(s):		
			return s[::-1]
		
		# 注册到上下文中,定义名称
		app.jinja_env.filters['reverse'] = reverse_filter

	* 在使用装饰器的情况下,如果你想以函数名作为过滤器名,参数是可选的
	* 注册之后, 你可以在模板中像使用 Jinja2 内置过滤器一样使用你的过滤器,例如你在上下文中有 一个名为 mylist 的 Python 列表:

		{% for x in mylist | reverse %}
			
		{% endfor %}