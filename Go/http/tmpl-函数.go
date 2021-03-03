------------------------
函数
------------------------
	# 模板里面调用的方法，只能返回一个参数，如果有2个，那么第二个必须是 error
		

	# 结构体方法调用，使用:. 
		type User struct {
			Name string
		}
		func (u *User) Say(val string) string {
			return fmt.Sprintf("%s, Im %s\n", val, u.Name)
		}
		
		
		err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
			"user": &User{Name: "KevinBlandy"},
		})

		{{ .user.Say "Hello" }}


		* 如果没有参数，省略即可
	
	# 函数变量调用，使用 call 指令
		err := tpl.ExecuteTemplate(writer, "index.html", map[string] interface{} {
			"say": func(val string) string {
				return fmt.Sprintf("Hello %s\n", val)
			},
		})

		{{ call .say "陆伯言" }}
	

	# 注册全局函数，任意地方都可以调用，声明即调用，参数写在后面，多个的话使用逗号分隔
		testTemplate, err = template.New("hello.gohtml").Funcs(template.FuncMap{
		"hasPermission": func(user User, feature string) bool {
		  if user.ID == 1 && feature == "feature-a" {
			return true
		  }
		  return false
		},
	  }).ParseFiles("hello.gohtml")
	 
	
	# 管道调用
		* 使用 | 把变量作为参数，传递给函数
		* 函数的返回值可以作为另一个函数的参数
			<a href="/search?q={{. | urlescaper | attrescaper}}">{{. | htmlescaper}}</a>
		
		* 如果函数有多个参数，那么管道符会把变量传递给最后一个参数
			{{ . | printf "%s\n" "abcd" }}  // {.}}的结果将传递给printf，且传递的参数位置是"abcd"之后。
				
	# 直接输出原始内容
		{{`"output"`}}


------------------------
内置函数
------------------------
	js
		* 返回与其参数的文本表示形式等效的转义JavaScript
	
	len
		* 返回长度
	
	index
		* 根据索引返回值
	
	call
		* 执行结果是调用第一个参数的返回值，该参数必须是函数类型，其余参数作为调用该函数的参数
	
	html
		* 对参数进行HTML编码

	urlquery
		* 对检索参数进行编码
			{{ urlquery "name=刘备&age=65" }}
			name%3D%E5%88%98%E5%A4%87%26age%3D65
	

------------------------
第三方的函数库
------------------------
	# https://github.com/Masterminds/sprig