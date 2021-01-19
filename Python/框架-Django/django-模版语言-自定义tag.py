--------------------------------
模版语言自定义标签				|
--------------------------------
	1,在app中创建目录:templatetags
		* 该目录名称固定,不可修改
	
	2,在该目录创建任意名称的py文件,用于编写tag函数
		from django import template
		from django.utils.safestring import mark_safe

		# 变量名称固定,不能修改
		register = template.Library()
		'''
			使用 @register.simple_tag 向系统注册一个标签
			参数
				takes_context
					* 默认为None,该值如果为True,则会在执行标签函数时传递上下文对象,由系统传入
		'''
		@register.simple_tag(takes_context=True)
		# 函数名称则是标签名称,
		# 如果takes_context=True,那么第一个参数则是 context,名称不能变
		# 如果标签传递了多个值,那么必须有对应个数的形参变量来接收
		def my_demo(context,value):
			mark_safe(value)    # mark_safe()可以对含有HTML标签的字符串进行转义
			print(context)
			return value + " test"	# 返回值就为渲染的结果
	
	2,向django系统注册当前的项目
		* 在settings.py文件中进行注册当前app
		INSTALLED_APPS = [
			'django.contrib.admin',
			'django.contrib.auth',
			'django.contrib.contenttypes',
			'django.contrib.sessions',
			'django.contrib.messages',
			'django.contrib.staticfiles',
			'root',		# 当前app的名称
		]

	3,在模版中使用标签
		{% load tag_file.py %}		# 使用load标签导入自定义的标签库,tag_file 就是自定义标签的py文件名称
		
		{% my_demo 'KevinBlandy' 'Kevin'%}	# 直接传递常量值使用标签,可以传多个值,使用空格分隔
		{% my_demo name%}					# 或者传递上下文中的变量使用标签
	
	* tag 不能用于{% if %}等标签,因为它本身就是个标签
	* tag 可以传递多个值,过滤器不能


		

	