-------------------------
Falsk					 |
-------------------------
	# 构造函数
		def __init__(
			self,
			import_name,
			static_url_path=None,
				* 访问静态资源的URL
			static_folder='static',
				* 静态资源目录
			static_host=None,
			host_matching=False,
			subdomain_matching=False,
			template_folder='templates',
				* 模板引擎目录

			instance_path=None,
			instance_relative_config=False,
			root_path=None
		)
	
-------------------------
实例方法				 |
-------------------------
	run(self, host=None, port=None, debug=None, load_dotenv=True, **options)
		* 启动服务
