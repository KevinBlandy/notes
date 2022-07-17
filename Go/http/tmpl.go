------------------------
ģ������
------------------------
	# ʹ�� "html/template"
	# �ο�
		https://www.cnblogs.com/f-ck-need-u/p/10035768.html
		https://cloud.tencent.com/developer/article/1683688


------------------------
����
------------------------
	# ���غ���
		func New(name string) *Template
			* ���ڴ����½�һ��ģ�壬ָ������
				// ģ���ַ���������2��ģ�壬�ֱ�ָ�������ƣ�����ı���û��ģ��Ķ��壬�������ַ�������һ��ģ�壬ʹ��name����
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
			* ����һ��ģ�壬ʹ���ļ�����Ϊģ�������
		
		func ParseGlob(pattern string) (*Template, error)
			* ����pattern��������ƥ���ģ�岢����
		
	# ģ���������Ĭ��ʹ���ļ�����(base name)
		* ���ģ�嶨���� {{ define "templateName" }} �Ļ�����ʹ�� templateName
		* ���������ظ�
		* ���ģ��common�����ظ��������ĻḲ��ǰ���
	
	
	# �ı��е�HTMLע�ͻᱻɾ��
		content := `
			{{ "<!-- ���ʽ�����ע�� -->"}} 
			<!-- �ı��е�ע�� --> 
			{{ htmlSafe  "<!-- ��ȫ�ı��ʽ��ע�� --> "}}
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
		
		&lt;!-- ���ʽ�����ע�� --&gt; 
										
		<!-- ��ȫ�ı��ʽ��ע�� -->     
		Hi                     
		
		* �����Ҫ���ע�ͣ�����ͨ�� {{ }} ���ʽ��� template.HTML ��ȫ����

------------------------
������Ⱦ
------------------------
	# ��ǰ�����ڵĶ������Ϊ: .
		
		Hello {{ .Name }}

		* ����ֶ��Ƕ��󣬿�����ʽ���� {{ .Struct.StructTwo.Field }}
		* ������ݵĲ��������ǵ������ݣ������Ǹ������ݣ���ô����ֱ��ʹ��:.
			tpl.ExecuteTemplate(writer, "index.html", "KevinBlandy")
			Hello {{ . }}
		
		* �������� String() ������
	
	# with������������"."��ֵ
		* ��pipeline��Ϊ0ֵ��ʱ�򣬵�"."����Ϊpipeline�����ֵ��ִ��T1�����
			{{ with pipeline }} 
				T1 
			{{ end }}
		
		* ��pipelineΪ0ֵʱ��ִ��else���飬����"."����Ϊpipeline�����ֵ����ִ��T1��
			{{ with pipeline }} 
				T1 
			{{ else }}
				T0 
			{{ end }}
	
	# ģ�弶��������Ķ�������� $
		* ��ĳЩ�ֲ������򣬶��������. �����ʱ������ⲿ��ȫ�ֱ������ͱ������� $
			{{range .slice}}
				{{$.ArticleContent}}		// �����ⲿ��ȫ�ֱ���
			{{end}}
	

	# ģ�������������ģ�����Զ��������ʹ�� $ ���������
		{{$root := .}}
        Hello {{ $root.Name }}

		// δ������ı���
		$var := pipeline

		// �Ѷ�����ı���
		$var = pipeline
	
	# ��ȫ��HTML
		* ��ͨ��HTML�ᱻת��
			tpl.ExecuteTemplate(writer, "index.html", "<h1>Vin</h1>")
			Hello &lt;h1&gt;Vin&lt;/h1&gt;
		
		* ʹ�� template.HTML �������HTML
			tpl.ExecuteTemplate(writer, "index.html", template.HTML("<h1>Vin</h1>"))
			Hello <h1>Vin</h1>

	# ��ֵ����
		* ��Ⱦ�����ڵı������᷵�ؿո��ַ���
		* �Բ����ڵı������м��㣬���쳣
		
	# ����
		* ��ȡ��������ֵʹ�����ú��� index
			 ǲ{{ index .Users 0 }}��ս
			 Users[0]
	
		* ��ά����
			{{ index .Users 0 1 2 }}
			Users[0][1][2]
	
	# Map
		* ʹ�� . ��������
			err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
				"Foo": map[string] interface{} {
					"Bar": "Bar",
				},
			})

			{{ .Foo.Bar }}
	
	# �Ƴ�����Ŀո񣬿����� {{}} ��� -
		* �����ӱ�ʾ�Ƴ���ߵĿո��ұ���ӱ�ʾ�Ƴ��ұߵ�
			{{- .Foo.Bar -}}
		
		* '-' �� '{' / '}' ֮�䲻���пո�
	
	
	# ע�ͣ�ʹ�� /**/
		{{/* a comment */}}
				

------------------------
����
------------------------
	# ʹ��Range����
		err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
			"Users": []string {"����", "�ŷ�", "����", "��", "����"},
		})

		<ul>
			{{ range .Users }}
				<li>{{ . }}</li>
			{{ end }}
		</ul>

		* ��range�У� . ��ʾ��ǰ������Ԫ��
	

	# Rang��ֵ����
		{{ range $key, $val :=. }}
			{{$key}} = {{$val}}
		{{ end }}

		* ֻ��һ�����������Ǳ����ĵ�ǰֵ
		* 2����������һ��Ϊkey/index���ڶ���Ϊ��ǰֵ
	
	# else���жϣ����û�пɱ������ݣ��ͻ�ִ��
		{{ range . }}
			<li>{{ . }}</li>
		{{ else }}
			<li> û������ </li>
		{{ end}}
	

	#  {{break}} �� {{continue}}


------------------------------------------------
һ��������spring��ͼ��ģ��������ط���
------------------------------------------------
	# loadTemplates
		func loadTemplates(templatesDir string) *template.Template {

			templates := template.New("templates")

			// ��������ӷ�����ģ�����������ʹ�õ��˷����������ڻ��쳣
			templates.Funcs(map[string] interface{} {
			})

			// ����ģ������Ŀ¼���������ļ�������ģ�壬��Ŀ¼�����·����Ϊģ������
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
						// ���ļ��ָ�����ͳһ�滻Ϊ ��/��
						templates.New(strings.ReplaceAll(absPath, string(os.PathSeparator), "/")).Parse(string(context))
					}()
				}
				return nil
			})

			return templates
		}

		
		// �����
		func loadTemplates(templatesDir string) *template.Template {

			templates := template.New("templates")

			// ��������ӷ�����ģ�����������ʹ�õ��˷����������ڻ��쳣
			templates.Funcs(map[string] interface{} {})

			// ����ģ������Ŀ¼���������ļ�������ģ�壬��Ŀ¼�����·����Ϊģ������
			err := filepath.WalkDir(templatesDir, func(path string, d fs.DirEntry, err error) error {
				if !d.IsDir() {
					absPath, err := filepath.Rel(templatesDir, path)
					if err != nil {
						return err
					}
					// ��ȡÿһ���ļ����ڴ棬ʹ�����·����Ϊ��ģ�����ơ�
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
						// ���ļ��ָ�����ͳһ�滻Ϊ ��/��
						templates.New(strings.ReplaceAll(absPath, string(os.PathSeparator), "/")).Parse(string(context))
						return nil
					}()
					return err
				}
				return nil
			})
			if err != nil {
				log.Fatalf("����HTMLģ�������쳣: %s\n", err.Error())
			}
			return templates
		}

	
	# ��Gin��ʹ��
		router := gin.New()

		// �����Զ����ģ������
		router.SetHTMLTemplate(loadTemplates("html"))

		// ģ����������ƣ����ǡ������ģ������Ŀ¼��·����
		router.GET("/", func(context *gin.Context) {
			context.HTML(200, "common/foo/bar/bar.html", nil)
		})