----------------------------
配置设置					|
----------------------------
	* Scrapy 默认在 scrapy.cfg 文件中查找配置参数(优先级别从低到高)

		系统范围	/etc/scrapy.cfg 或 c:\scrapy\scrapy.cfg
		用户范围	~/.config/scrapy.cfg ($XDG_CONFIG_HOME)  和  ~/.scrapy.cfg ($HOME)
		项目内范围	scrapy.cfg

		* 项目范围的设置将覆盖所有其他文件的设置,用户范围内定义的设置的覆盖系统范围内的设置

	* Scrapy 也可以接受来自环境变量的配置。目前有

		SCRAPY_SETTINGS_MODULE (见 Designating the settings)
		SCRAPY_PROJECT
		SCRAPY_PYTHON_SHELL (见 Scrapy shell)

----------------------------
全局命令					|
----------------------------
	scrapy startproject [name]
		* 创建一个scrapy爬虫项目
		* 创建好的目录结构
			name
				|-name
					|-spiders
						|-__init__.,py
					|-__init__.,py
					|-items.py
					|-middlewares.py
					|-pipelines.py
					|-settings.py
				|-scrapy.cfg
	
	
	scrapy genspider [name] [url]
		* 创建一个指定name的爬虫,目标地址是url
	
	scrapy settings
	scrapy runspider
	scrapy shell
	scrapy fetch
	scrapy view
	scrapy version

----------------------------
项目命令					|
----------------------------
	scarpy crawl [name]
		* 开始启动指定名称的爬虫
		* 参数
			-o
				* 把spider的parse方法返回的数据序列化到本地
				* -o指定文件的名称,会生成在当前命令目录
				* 可以是 .json /.csv /.xml

			


	
	scrapy check
	scrapy list
	scrapy edit
	scrapy parse
	scrapy bench

----------------------------
自定义命令					|
----------------------------
	* 实验性功能