-------------------------------
自定义过滤器					|
-------------------------------
	* 整个过程跟自定义tag一摸一样
	* 过滤器可以用于 {% if %}等标签,tag不能
	* 过滤仅仅可以传递一个值,tag可以传递n个
	* 只有两个地方不同
		1,装饰器不同
			@register.simple_tag	-> @register.filter
		
		2,调用方式不同
			{% my_demo name %}		-> {{ name|my_filter }}
			* 传递值的调用
				{{ name|my_filter:'KevinBlandy' }}	# 常量
				{{ name|my_filter:var }}			# 上下文变量
	
	* dmeo
		from django import template
		from django.utils.safestring import mark_safe

		# 变量名称固定,不能修改
		register = template.Library()

		'''
			使用 @register.filter 向系统注册一个过滤器
		'''
		@register.filter
		def my_filter(value,arg):	# value,就是调用过滤器时传递尽量的参数,arg是通过":"传递进来的参数
			mark_safe(value)		# mark_safe()可以对含有HTML标签的字符串进行转义
			return "filter" + value


	