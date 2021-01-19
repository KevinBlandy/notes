----------------------------
url							|
----------------------------
	* 全局片配置文件:urls
		urlpatterns = [
			url(r'^admin/', admin.site.urls),
		]

	* URL配置函数
		url(url,controller,params)
			* url,可以是一个正则表达式
				* 如果正则表达式中有以指定名称对正则结果进行命名,那么controller应该具备同名的参数,来接收url中正则匹配到的值
			* controller,视图函数,就是一个函数
			* params,可选参数,传递给视图函数额外的参数,该参数应该是一个 dict
				* 该参数中的所有 key,在controller中都必须要有对应的参数,名称必须相同
			* 关键字参数
				name
					* 别名,可选的name参数(是URL的别名),可以反向获取URL参数
					* 在controller里面,可以通过 name 参数的值,来获取到该url()url信息
					* 在模版引擎里面也可以获取到: {% url "name" %}
								不带参数的：
									{% url 'name' %}
								带参数的,参数可以是变量名
									{% url 'name' 参数 %}
					* 在 templates, models, views ……中都可以得到对应的网址
			
	
	* 当系统使用请求的URL匹配到了第一个符合规则的URL正则,就会执行该controller,不会往下匹配
	
	* RESTFull风格的参数获取

	* url映射分发

		from django.conf.urls import url,include
		from django.contrib import admin

		urlpatterns = [
			url(r'^admin/', admin.site.urls),
			
			# 当遇到任何以root开头的路径,就会把当前请求分配到 root.urls 模版下找路径
			url(r'^root/', include('root.urls'))
		]
