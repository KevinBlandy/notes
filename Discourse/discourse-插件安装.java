--------------------------------
插件安装						|
--------------------------------
	# 官方教程
		https://meta.discourse.org/t/install-plugins-in-discourse/19157
	
	# 步骤
		1. 获取到插件的git地址(github/bitbucket)
		2. 进入程序目录, 编辑: app.yml
			cd /var/discourse
			vim containers/app.yml
		
		3. 添加插件地址到hock
			hooks:
			  after_code:
				- exec:
					cd: $home/plugins
					cmd:
					  - git clone https://github.com/discourse/docker_manager.git
					  - git clone https://github.com/discourse/discourse-spoiler-alert.git
		4. 重构容器
			cd /var/discourse
			./launcher rebuild app
		
--------------------------------
一些插件统计					|
--------------------------------
	# 官方的插件统计
		https://meta.discourse.org/c/plugin
		https://meta.discourse.org/tag/official
	
	# 提问/解决插件
		https://github.com/discourse/discourse-solved.git
	
	# 谷歌广告插件
		https://github.com/discourse/discourse-adplugin

		* CSP策略需要添加白名单
			pagead2.googlesyndication.com
			adservice.google.com
			www.googletagservices.com
			'unsafe-inline'
	
	# 顶部的导航插件
		https://github.com/discourse/discourse-custom-header-links
	
	# matomo 统计插
		https://github.com/discourse/discourse-matomo-analytics.git 
		
	# Discourse使用的一堆常见组件和UX元素
		https://github.com/discourse/discourse-styleguide
	
	# Sitemap插件
		https://github.com/discourse/discourse-sitemap
	
	# 数学表达式支持插件
		https://github.com/discourse/discourse-math
	
	# 用户的“专家”标识插件
		https://github.com/discourse/discourse-category-experts
	
	# 允许用户在界面上切换主题
		https://github.com/discourse/discourse-hamburger-theme-selector

--------------------------------
一些组件						|
--------------------------------
	Custom Header Links
		* 自定义header
	
