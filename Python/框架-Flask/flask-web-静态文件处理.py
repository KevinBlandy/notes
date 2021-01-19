--------------------------------
静态文件处理					|
--------------------------------
	* 在你包中或是模块的所在目录中创建一个名为 static 的文件夹,在应用中使用 /static 即可访问
		application
			static
				index.js
			templates
			application.py
		
		http://127.0.0.1:5000/static/index.js
	
	* 给静态文件生成 URL,使用特殊的 'static' 端点名:
		url_for('static', filename='style.css')
			* 这个文件应该存储在文件系统上的 static/style.css
	

		

	

