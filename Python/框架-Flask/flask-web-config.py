--------------------------------
congig							|
--------------------------------
	* 系统的全局配置,是用过 Flask 实例对象的 config 属性来完成
	* config 继承于字典,可以用操作字典的方式来操作它
	* 给定的配置值会被推送到 Flask 对象中，所以你可以在那里读写它 
		app.config['debug'] = True
		app.debug = True

	* 读取配置的方式

		1, 可以从配置文件填充配置	from_pyfile
			app.config.from_pyfile('yourconfig.cfg')
		
		2, 读取指定对象/模块的属性作为配置	from_object
			DEBUG = True
			SECRET_KEY = 'development key'
			app.config.from_object(__name__)	# __name__ 就是当前模块
			* 参数是可以模块/对象的字符串,也可以直接是对象/模块
			* 只会读取模块/变量中所有大写的变量
		
		3, 从指向文件的环境变量	from_envvar
			app.config.from_envvar('FLASKR_SETTINGS', silent=True)
			* 在这种情况下，在启动应用程序之前，您必须将此环境变量设置为要使用的文件(在Linux和OS X上,使用export语句)
			* export YOURAPPLICATION_SETTINGS='/path/to/config/file'
			* 就是在环境变量中添加一个值,该值指向了一个配置文件
	
	* config 具备的方法
		get_namespace(prefix)
			* 获取指定前缀的所有配置项
				app.config['IMAGE_STORE_TYPE'] = 'fs'
				app.config['IMAGE_STORE_PATH'] = '/var/app/images'
				app.config['IMAGE_STORE_BASE_URL'] = 'http://img.website.com'
				image_store_config = app.config.get_namespace('IMAGE_STORE_')
				{
					'type': 'fs',
					'path': '/var/app/images',
					'base_url': 'http://img.website.com'
				}

			* 关键字参数
				lowercase
					* 返回参数名称是否小写,默认为:True
				trim_namespace=
					* 返回参数名是够去前缀,默认为:True
	
		update(dict)
			* 一次性更新多个配置项目,参数就是要更新的key & value字典
	
	* 还可以使用继承的方式来定义配置属性
		class Config(object):
			DEBUG = False
			TESTING = False
			DATABASE_URI = 'sqlite://:memory:'

		class ProductionConfig(Config):
			DATABASE_URI = 'mysql://user@localhost/foo'

		class DevelopmentConfig(Config):
			DEBUG = True

		class TestingConfig(Config):
			TESTING = True
		
		* 启用这样的配置你需要调用 from_object()

		

--------------------------------
congig-配置项					|
--------------------------------
	'DEBUG': True,
		* 启用/禁用 调试模式
	'TESTING': False, 
		* 启用/禁用 测试模式
	'PROPAGATE_EXCEPTIONS': None, 
	'PRESERVE_CONTEXT_ON_EXCEPTION': None, 
	'SECRET_KEY': b'-\xc7\x81\xaf\\\xca\x7f\x0e\x0eT_\xcf9\xf8\x85\x9c{\xc2Z\x1b\xb7\xa5\x9d\x8f', 
		* 密钥
	'PERMANENT_SESSION_LIFETIME': datetime.timedelta(31), 
		* 以 datetime.timedelta 对象控制长期会话的生存时间,从 Flask 0.8 开始,也 可以用整数来表示秒
	'USE_X_SENDFILE': False, 
		* 	启用/禁用 x-sendfile
	'LOGGER_NAME': '__main__', 
		* 日志记录器的名称
	'LOGGER_HANDLER_POLICY': 'always',
		* 默认日志处理程序的策略,默认为'always',这意味着默认日志处理程序总是处于活动状态
		* 'debug'只会在调试模式下激活日志记录
		* 'production'只能登录生产
		* 'never'不会
	'SERVER_NAME': None, 
		* 服务器名和端口,需要这个选项来支持子域名 (例如： 'myapp.dev:5000' )
		* 注意 localhost 不支持子域名,所以把这个选项设置为 "localhost" 没有意义
		* 设置 SERVER_NAME 默认会允许在没有请求上下文 而仅有应用上下文时生成 URL
	'APPLICATION_ROOT': None,
	'SESSION_COOKIE_NAME': 'session',
		* 会话 cookie 的名称
	'SESSION_COOKIE_DOMAIN': None, 
		* 会话 cookie 的域,如果不设置这个值,则 cookie 对 SERVER_NAME 的全部子域名有效
	'SESSION_COOKIE_PATH': None, 
		* 会话 cookie 的路径, 如果不设置这个值,且 没有给 '/' 设置过,则 cookie 对 APPLICATION_ROOT 下的所有路径有效
	'SESSION_COOKIE_HTTPONLY': True,
		* 这个路径也会用于会 话 cookie 的路径值, 如果这个值被设置为 True ,你 只会得到常规的回溯
	'SESSION_COOKIE_SECURE': False,
		* 控制cookie是否应被设置安全标志,默认为False
	'SESSION_REFRESH_EACH_REQUEST': True,
		* 这个标志控制永久会话如何刷新,如果被设置为True(这是默认值)
		* 每一个请求 cookie 都会被刷新,如果设置为False,只有当会话被修改后才会发送一个set-cookie的标头, 非永久会话不会受到这个配置项的影响 
	'MAX_CONTENT_LENGTH': None, 
		* 如果设置为字节数,Flask 会拒绝内容长度大于 此值的请求进入，并返回一个 413 状态码
	'SEND_FILE_MAX_AGE_DEFAULT': datetime.timedelta(0, 43200),
	'TRAP_BAD_REQUEST_ERRORS': False,
	'TRAP_HTTP_EXCEPTIONS': False,
	'EXPLAIN_TEMPLATE_LOADING': False, 
	'PREFERRED_URL_SCHEME': 'http', 
	'JSON_AS_ASCII': True,
	'JSON_SORT_KEYS': True, 
	'JSONIFY_PRETTYPRINT_REGULAR': True, 
	'JSONIFY_MIMETYPE': 'application/json', 
	'TEMPLATES_AUTO_RELOAD': None, 
	'name': 'KevinBlandy'


		