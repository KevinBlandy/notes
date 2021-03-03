------------------
模板引擎
------------------
	# 加载路径
		func (engine *Engine) LoadHTMLFiles(files ...string)
			* 加载指定的模板引擎，加载后才能在context使用

		func (engine *Engine) LoadHTMLGlob(pattern string)
			* 设置全局的模板引擎加载前缀
			* 指定目录下的所有文件
				router.LoadHTMLGlob("templates/*")
			* 指定目录下的所有目录的所有文件（不行）
				router.LoadHTMLGlob("templates/**/*")
	
	# 重新设置模板引擎分隔符
		func (engine *Engine) Delims(left, right string) *Engine
	
	# 自定义函数
		func (engine *Engine) SetFuncMap(funcMap template.FuncMap)
	
	# 使用自己定义的模板加载器
		func (engine *Engine) SetHTMLTemplate(templ *template.Template)
	
	# 响应模板引擎
		func (c *Context) HTML(code int, name string, obj interface{})

------------------
多模板引擎渲染
------------------
	