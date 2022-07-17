------------------------
模板引擎
------------------------
	# 使用 "html/template"
	# 参考
		https://www.cnblogs.com/f-ck-need-u/p/10035768.html
		https://cloud.tencent.com/developer/article/1683688


------------------------
加载
------------------------
	# 加载函数
		func New(name string) *Template
			* 在内存中新建一个模板，指定名称
				// 模板字符串定义了2个模板，分别指定了名称，如果文本中没有模板的定义，则整个字符串都是一个模板，使用name命名
				tpxText := `
					{{ define "bar" }} 
						Im Bar 
					{{ end }} 

					{{ define "foo" }} 
						Im Foo 
					{{ end }}
				`
				tpl := template.Must(template.New("foo").Parse(tpxText))
				fmt.Println(tpl.Name())		// foo
				tpl.Execute(os.Stdout, nil) // Im Foo
			

		func ParseFiles(filenames ...string) (*Template, error)
			* 解析一组模板，使用文件名作为模板的名字
		
		func ParseGlob(pattern string) (*Template, error)
			* 根据pattern解析所有匹配的模板并保存
		
	# 模板的命名，默认使用文件名称(base name)
		* 如果模板定义了 {{ define "templateName" }} 的话，则使用 templateName
		* 命名不能重复
		* 多个模板common命名重复，后来的会覆盖前面的
	
	
	# 文本中的HTML注释会被删除
		content := `
			{{ "<!-- 表达式里面的注释 -->"}} 
			<!-- 文本中的注释 --> 
			{{ htmlSafe  "<!-- 安全的表达式的注释 --> "}}
			Hi
		`

		tpl := template.New("t")
		tpl.Funcs(template.FuncMap{
			"htmlSafe": func(content string) template.HTML {
				return template.HTML(content)
			},
		})
		if _, err := tpl.Parse(content); err != nil {
			log.Fatalln(err.Error())
		}
		tpl.Execute(log.Writer(), nil)
		
		// out---------------
		
		&lt;!-- 表达式里面的注释 --&gt; 
										
		<!-- 安全的表达式的注释 -->     
		Hi                     
		
		* 如果需要输出注释，可以通过 {{ }} 表达式输出 template.HTML 安全属性

------------------------
数据渲染
------------------------
	# 当前作用于的顶层变量为: .
		
		Hello {{ .Name }}

		* 如果字段是对象，可以链式访问 {{ .Struct.StructTwo.Field }}
		* 如果传递的参数，就是单个数据，并不是复核数据，那么可以直接使用:.
			tpl.ExecuteTemplate(writer, "index.html", "KevinBlandy")
			Hello {{ . }}
		
		* 反正是用 String() 来访问
	
	# with用来重新设置"."的值
		* 当pipeline不为0值的时候，点"."设置为pipeline运算的值，执行T1的输出
			{{ with pipeline }} 
				T1 
			{{ end }}
		
		* 当pipeline为0值时，执行else语句块，否则"."设置为pipeline运算的值，并执行T1。
			{{ with pipeline }} 
				T1 
			{{ else }}
				T0 
			{{ end }}
	
	# 模板级别作用域的顶层变量是 $
		* 在某些局部作用域，顶层变量是. 如果此时想访问外部的全局变量，就必须依赖 $
			{{range .slice}}
				{{$.ArticleContent}}		// 访问外部的全局变量
			{{end}}
	

	# 模板变量，可以在模板中自定义变量，使用 $ 定义与访问
		{{$root := .}}
        Hello {{ $root.Name }}

		// 未定义过的变量
		$var := pipeline

		// 已定义过的变量
		$var = pipeline
	
	# 安全的HTML
		* 普通的HTML会被转义
			tpl.ExecuteTemplate(writer, "index.html", "<h1>Vin</h1>")
			Hello &lt;h1&gt;Vin&lt;/h1&gt;
		
		* 使用 template.HTML 可以输出HTML
			tpl.ExecuteTemplate(writer, "index.html", template.HTML("<h1>Vin</h1>"))
			Hello <h1>Vin</h1>

	# 空值问题
		* 渲染不存在的变量，会返回空格字符串
		* 对不存在的变量进行计算，会异常
		
	# 数组
		* 获取数组索引值使用内置函数 index
			 遣{{ index .Users 0 }}出战
			 Users[0]
	
		* 多维数组
			{{ index .Users 0 1 2 }}
			Users[0][1][2]
	
	# Map
		* 使用 . 导航访问
			err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
				"Foo": map[string] interface{} {
					"Bar": "Bar",
				},
			})

			{{ .Foo.Bar }}
	
	# 移除多余的空格，可以在 {{}} 添加 -
		* 左边添加表示移除左边的空格，右边添加表示移除右边的
			{{- .Foo.Bar -}}
		
		* '-' 和 '{' / '}' 之间不能有空格
	
	
	# 注释，使用 /**/
		{{/* a comment */}}
				

------------------------
遍历
------------------------
	# 使用Range遍历
		err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
			"Users": []string {"关羽", "张飞", "赵云", "马超", "黄忠"},
		})

		<ul>
			{{ range .Users }}
				<li>{{ . }}</li>
			{{ end }}
		</ul>

		* 在range中， . 表示当前迭代的元素
	

	# Rang赋值遍历
		{{ range $key, $val :=. }}
			{{$key}} = {{$val}}
		{{ end }}

		* 只有一个变量，则是遍历的当前值
		* 2个变量，第一个为key/index，第二个为当前值
	
	# else，判断，如果没有可遍历数据，就会执行
		{{ range . }}
			<li>{{ . }}</li>
		{{ else }}
			<li> 没有数据 </li>
		{{ end}}
	

	#  {{break}} 和 {{continue}}


------------------------------------------------
一个类似于spring视图的模板引擎加载方法
------------------------------------------------
	# loadTemplates
		func loadTemplates(templatesDir string) *template.Template {

			templates := template.New("templates")

			// 必须先添加方法，模板引擎中如果使用到了方法，不存在会异常
			templates.Funcs(map[string] interface{} {
			})

			// 遍历模板引擎目录，把所有文件都当做模板，以目录的相对路径作为模板名称
			filepath.WalkDir(templatesDir, func(path string, d fs.DirEntry, err error) error {
				if !d.IsDir() {
					absPath, err := filepath.Rel(templatesDir, path)
					if err != nil {
						return err
					}
					func (){
						file, err := os.Open(path)
						if err != nil {
							panic(fmt.Errorf("Open file err: %s\n", err.Error()))
						}
						defer file.Close()

						context, err := io.ReadAll(file)
						if err != nil {
							panic(fmt.Errorf("Read file err: %s\n", err.Error()))
						}
						// 把文件分隔符，统一替换为 “/”
						templates.New(strings.ReplaceAll(absPath, string(os.PathSeparator), "/")).Parse(string(context))
					}()
				}
				return nil
			})

			return templates
		}

		
		// 用这个
		func loadTemplates(templatesDir string) *template.Template {

			templates := template.New("templates")

			// 必须先添加方法，模板引擎中如果使用到了方法，不存在会异常
			templates.Funcs(map[string] interface{} {})

			// 遍历模板引擎目录，把所有文件都当做模板，以目录的相对路径作为模板名称
			err := filepath.WalkDir(templatesDir, func(path string, d fs.DirEntry, err error) error {
				if !d.IsDir() {
					absPath, err := filepath.Rel(templatesDir, path)
					if err != nil {
						return err
					}
					// 读取每一个文件到内存，使用相对路径作为“模板名称”
					err = func () error {
						file, err := os.Open(path)
						if err != nil {
							return err
						}
						defer file.Close()
						context, err := io.ReadAll(file)
						if err != nil {
							return err
						}
						// 把文件分隔符，统一替换为 “/”
						templates.New(strings.ReplaceAll(absPath, string(os.PathSeparator), "/")).Parse(string(context))
						return nil
					}()
					return err
				}
				return nil
			})
			if err != nil {
				log.Fatalf("加载HTML模板引擎异常: %s\n", err.Error())
			}
			return templates
		}

	
	# 在Gin中使用
		router := gin.New()

		// 设置自定义的模板引擎
		router.SetHTMLTemplate(loadTemplates("html"))

		// 模板引擎的名称，就是“相对于模板引擎目录的路径”
		router.GET("/", func(context *gin.Context) {
			context.HTML(200, "common/foo/bar/bar.html", nil)
		})