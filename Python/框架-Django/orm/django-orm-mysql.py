
pip install mysqlclient
# 需要安装 mysqlclient 模块


DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',	# 指定DB引擎为MYSQL
        'NAME': 'demo',							# 数据库名称
        'HOST':'59.110.167.11',					# IP
        'PORT':'1124',							# 端口
        'USER':'root',							# 用户名
        'PASSWORD':'KevinBlandy_mysql'			# 密码
    }
}

