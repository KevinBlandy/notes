-------------------------------
jinja2							|
-------------------------------
	* Flask 使用 Jinja 2 作为模板引擎
	* 当然,你也可以自由使用其它的模板引擎
	* 但'运行Flask本身仍然需要Jinja2依赖,这对启用富扩展是必要的,扩展可以依赖Jinja2存在'
	* 本节只是快速地介绍 Jinja2 是如何集成到 Flask 中的, 如果你需要更多关于模板引擎本身的信息,请参考官方Jinja2模板文档

-------------------------------
jinja2-配置						|
-------------------------------
	* 默认的配置
		* 当使用render_template()时,所有扩展名为 .html, .htm, .xml以及.xhtml的模板会开启自动转义
		* 当使用render_template_string()时,所有字符串都开启字典转义
		* 模板可以利用{% autoescape %}标签选择自动转义的开关。
		* Flask 在 Jinja2 上下文中插入了几个全局函数和助手,另外还有一些 预定义值

-------------------------------
jinja2-预定义值					|
-------------------------------
	config
		* 当前的配置对象 (flask.config)

	request
		* 当前的请求对象 (flask.request), 当模版不是在活动的请求上下文中渲染时这个变量不可用

	session
		* 当前的会话对象 (flask.session), 当模版不是在活动的请求上下文中渲染时这个变量不可用

	g
		* 请求相关的全局变量 (flask.g), 当模版不是在活动的请求上下文中渲染时这个变量不可用

	url_for()
		* flask.url_for() url构造函数

	get_flashed_messages ()
		* flask.get_flashed_messages() 函数

-------------------------------
jinja2-过滤器					|
-------------------------------
	tojson 
		* 这个函数把给定的对象转换为 JSON 表示
	
	safe 
		* 安全的输出,不转义HTML
	
	* 过滤器参数传递使用()
		{% for x in users|reverse('Kevin') %}	# reverse('Kevin')
            {{ x.name }} - {{ x.age }}
        {% endfor %}

-------------------------------
jinja2-标签						|
-------------------------------
	* 循环迭代
		{% for x in users %}
            {{ x.name }} - {{ x.age }}
        {% endfor %}
		
	* 转义HTML代码
		{% autoescape false %}
			<p>autoescaping is disabled here
			<p>{{ will_not_be_escaped }}
		{% endautoescape %}

		* autoescape 是否转义HTML代码,通过参数 false / true 来控制

	

-------------------------------
jinja2-上下文处理器				|
-------------------------------
	* Flask 上下文处理器自动向模板的上下文中插入新变量
	* 上下文处理器在模板 渲染之前运行,并且可以在模板上下文中插入新值
	* 上下文处理器是一个返回字典的函数,使用@app.context_processor 标识
	* 这个字典的键值最终将传入应用中所有模板的上下文

		@app.context_processor
		def inject_user():
			return dict({'name':'Kevin'})
		* 上面的上下文处理器使得模板可以使用一个名为 name  值:	 
				{{ name }}	# Keviin

	* 变量不仅限于值,上下文处理器也可以使某个函数在模板中可用(由于 Python 允 许传递函数)

		@app.context_processor
		def utility_processor():
			def say_hello(some):
				return 'hello,' + some
			return dict({'say_hello':say_hello})

		* 上面的上下文处理器使得 say_hello 函数在所有模板中可用
			{{ say_hello('KevinBlandy') }}	# hello,KevinBlandy
		
