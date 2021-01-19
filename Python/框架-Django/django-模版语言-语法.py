----------------------------
取值						|
----------------------------
	* 普通取值
		{{name}}
			* 它是一个安全的取值表达式
	
	* 序列取值
		{{names.0}}
			* 序列.下标
			* 没错,就是这么有意思
	
	* 字典取值
		{{name.key}}
			* 简单,通过.进行导航
	
	* 日期格式化
	* 对象属性取值
		* 直接.导航,不解释
	

		

----------------------------
循环/迭代					|
----------------------------
	{% for key in seria %}
		{{key.name}}
	{% endfor %}

	* 给标签增加一个 reversed 使得该列表被反向迭代：
		{% for key in seria reversed %}
			...
		{% endfor %}
	
	* 循环标签可以嵌套使用
	* 循环特殊值
		{% for i in users%}
			{{forloop.counter}}
		{% endfor %}

		forloop.counter
			* 当前迭代到的数据个数,是从1开始(非下标)
		
		forloop.counter0
			* 当前迭代到的数据个数,是从0开始(非下标)
		
		forloop.revcounter
			* 逆向打印迭代数据个数,到1结束
			* 9,8,7,6,5,4,3,2,1
		
		forloop.revcounter0
			* 逆向打印迭代数据个数,到0结束
			* 8,7,6,5,4,3,2,1,0

		forloop.first	
			* 当遍历的元素为第一项时为真
		forloop.last	
			* 当遍历的元素为最后一项时为真
		forloop.parentloop	
			* 用在嵌套的 for 循环中,获取上一层 for 循环的 forloop
		


----------------------------
判断						|
----------------------------

	{% if condition %}
		
	{% elif condition %}
		
	{% else %}
		
	{% endif %}

	* {% if %} 标签接受 and , or 或者 not 关键字来对多个变量做判断 ,或者对变量取反(not)
		{% if athlete_list and coach_list %}
			 athletes 和 coaches 变量都是可用的
		{% endif %}
	
	
----------------------------
比较						|
----------------------------
	{% ifequal %} 

	{% endifequal %}

	* 标签比较两个值,当他们相等时，显示在 {% ifequal %} 和 {% endifequal %} 之中所有的值
		{% ifequal user currentuser %}
			<h1>Welcome!</h1>
		{% endifequal %}

	* 和 {% if %} 类似,{% ifequal %} 支持可选的 {% else %} 标签
		{% ifequal section 'sitenews' %}
			<h1>Site News</h1>
		{% else %}
			<h1>No News Here</h1>
		{% endifequal %}

	
----------------------------
注释						|
----------------------------
	{# 这是一个注释 #}

----------------------------
过滤器						|
----------------------------
	{{name|lower}}
	
	* 其实就是快捷方法调用,把name传递给了lower方法,转换为了小写
	* 过滤管道可以被套接,既是说,一个过滤器管道的输出又可以作为下一个管道的输入
		{{ name|first|upper }}
	
	* 有些过滤器有参数, 过滤器的参数跟随冒号之后并且总是以双引号包含
		{{ name|truncatewords:"30" }}
	
	* 常用过滤器
		first	
			* 获取第一个字符
		upper
			* 全部转换为大写
		lower
			* 全部转换为小写
		capfirst
			* 首字母转换为大写
		add:''
			* 给变量加上相应的值
			* add 的值可以是 int/str
			* 该过滤器会把两个值都转换为 int 后进行相加操作
		addslashes
			* 给变量中的引号前加上斜线
		cut:''
			* 从字符串中移除指定的字符
		date:''
			* 对时间(datetime)进行格式化
		default:''
			* 如果值为 False,就会被替换为该过滤器的值
		default_if_none:''
			* 如果值是 None ,就替换成该过滤器设置的值
		length
			* 会获取到数据的长度
		safe
			* 不转义HTML,确定是安全的HTML标签


----------------------------
包含						|
----------------------------
	{% include "nav.html" %}

	* {% include %} 标签允许在模板中包含其它的模板的内容

----------------------------
继承						|
----------------------------
	* 定义模版,以及可以被覆写的 block
		{% block [name]%}
			body..
		{% endblock %}
	
	* 继承模版,并且覆写指定名称的 block
	* extends 标签必须是在模版的第一行,否则不起作用
		{% extends [template]%}
		{% block [name] %}
			Override body...
		{% endblock %}


	* 模板可以用继承的方式来实现复用

		<body>
			<h1>Hello World!</h1>
			<p>菜鸟教程 Django 测试</p>

			{% block mainbody %}
				<p>original</p>
			{% endblock %}

		</body>
		* 名为 mainbody 的 block 标签是可以被继承者们替换掉的部分
		* {% block %} 标签告诉模板引擎,子模板可以重载这些部分
			# base.html
				<body>
					<h1>Hello World!</h1>
					<p>菜鸟教程 Django 测试。</p>
					{% block mainbody %}
					   <p>original</p>
					{% endblock %}
				</body>
			
			#extend.html
				{% extends "base.html" %}
				{% block mainbody %}
					<p>继承了 base.html 文件</p>
					{{ block.super }}	# 使用 block.supe 魔术变量访问父级模块中的内容(<p>original</p>)
				{% endblock %}
	
		* 访问父级模块中的变量,
			{{ block.super }}
----------------------------
html标签转义渲染			|
----------------------------
	* 在普通的取值操作,就算值是HTML标签,也会基于安全考虑,在渲染时会被渲染为HTML中的普通字符串
	* 说白了,会把HTML等特殊标签进行转义操作
		name = "<a href='http://javaweb.io'>name</a>"
		{{name}}
		#  &lt;a href=&#39;http://javaweb.io&#39;&gt;name&lt;/a&gt;
	* 可以关闭这种设置
		{% autoescape off %}
            {{ name }}
			#  <a href='http://javaweb.io'>name</a>
		{% endautoescape %}
	
	* 使用 safe 过滤器也可以完成
		{{name|safe}}

----------------------------
禁止渲染					|
----------------------------
	{% verbatim %}
		{{name}}
	{% endverbatim %}
		* {{name}},会原样输出,不会被渲染

----------------------------
简短变量名					|
----------------------------
	{% with name = longname %}
		{{ name }}
	{% endwith %}
		* 把名称过长的变量,重新命名



----------------------------
加载标签库					|
----------------------------
	{% load %}
		* 加载标签库

----------------------------
预定义值标签				|
----------------------------

	{%csrf_token%}
		* 用于生成一个防止csrf攻击的token值
		*  <input type='hidden' name='csrfmiddlewaretoken' value='8MBu2xfM6Xn10rTV1jfu0MZ05w33rrRFSUBPF0cNVzhGRt9EVJRQiAjqN3W8EimN' />
	
	{% url %}
		* 当前URL

	
	

	

	
