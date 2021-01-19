--------------------------------
django-命令行工具				|
--------------------------------
	django-admin.py 
		* 是django用于管理任务的一个命令行工具

	manage.py 
		* 是对 django-admin.py 的一个简单封装
		* 每一个django项目都会有一个 manage.py 文件 

--------------------------------
django-命令						|
--------------------------------
	django-admin startproject [name]
		* 创建一个指定名称的django项目
		* 生成的项目目录结构
		[name]
			[name]
				__init__.py
				settings.py	# 配置文件
				urls.py		# 路由映射
				wsgi.py		# web服务器
			manage.py	# 命令管理工具
	

	python manage.py startapp [app]
		* 在指定的django项目下创建一个web app 项目
		* 会在当前的django目录下生成项目文件夹
		[app]
			migrations	# 跟数据库相关
				__init__.py
			__init__.py
			admin.py	# 
			apps.py		# 
			models.py	# model
			tests.py	# 检测/测试
			views.py	# 视图函数
	
	python manage.py runserver [port]
		* 在指定的端口,运行django
	
	python manage.py makemigrations
		* 生成同步数据库的脚本
	
	python manage.py migrate
		* 同步数据库
	
	python manage.py flush
		* 清空数据库,会提示yes/no
		* 会清空表中的所有数据

	python manage.py shell
		* 进入django的shell命令行
		* 你可以在这个 shell 里面调用当前项目的 models.py 中的 API,对于操作数据,还有一些小测试非常方便
	
	python manage.py createsuperuser
		* 创建超级管理员
		* 按照提示输入用户名和对应的密码就好了邮箱可以留空
		* 用户名和密码必填
	 
	python manage.py changepassword username
		* 修改超级管理员的密码
	

	
	* 终端上输入 python manage.py 可以看到详细的列表,在忘记子名称的时候特别有用

	
