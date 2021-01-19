----------------------------
1,通过Sencha构建Ext项目		|
----------------------------
	1,安装Sencha cmd工具,需要JDK
	2,下载ext.js,解压到目录
	3,执行命令
		sencha -sdk d:/ext-6.2.0 generate app myapp e:/myapp

	4,启动内置服务器
		进入生成的目录,执行:sencha app watch
		http://localhost:1841/ 

	5,编译工程
		* 进入生成的目录,执行:sencha app build
		* 编译成功后在目录./build/production
----------------------------
2,目录说明					|
----------------------------
	.sehcha
	app
		|-model
		|-store
			Personnel.js
		|-view
			|-main
				MainController.js
				MainModel.js
		Application.js
	build
	classic
	ext
	modern
	overrides
	packages
	resources
	sass
	index.html
	app.js
	bootstrap.js
	app.json
	classic.json
	modern.jhson
	workspace.json
	classic.jsonp
	modern.jsonp
	Readme.md
	build.xml
	bootstrap.css