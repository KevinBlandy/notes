------------------------
函数
------------------------
	# 模板里面调用的方法，只能返回一个参数，如果有2个，那么第二个必须是 error
	
	# 函数/方法的调用
		* 内置函数，声明即调用(可以通过Api把普通函数注册为内置函数)
		* 普通函数（把普通函数当做变量，放入了上下文），通过 call 指令调用(call指令本身就是个内置函数)
		* 成员方法，可以直接通过 [对象].[方法] 调用


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

		* 也就是注册未了内置函数，跟 call, html 是一样的
		
		// FuncMaps 系统中用到的模板引擎函数
		var FuncMaps = template.FuncMap{
			"Global": func() map[string]any {
				return map[string]any{
					// struct
					"Title": struct {
						Name string
					}{Name: "PPP"},

					// func
					"Key": func(val string) string {
						return "Hello " + val
					},
				}
			},
			// func
			"Test": func() string {
				return "Im From Func"
			},
		}
		
		// 调用
        {{ Global.Title.Name }} <br/>
        {{ call Global.Key "Go's hh ;" | html }}<br/>			// 通过 call 调用 Global.Key 函数，传递参数，并且把返回值给 html再次调用
        {{ html (call Global.Key "Go's template") }}<br/>		// 调用 call Global.Key 函数，传递参数，并且把返回值给 html再次调用
        {{ Test }}<br/>			// 注册的全局内置函数，直接调用

		// 输出
        PPP <br/>
        Hello Go&#39;s hh ;<br/>
        Hello Go&#39;s template<br/>
        Im From Func<br/>
	
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
		* 没有参数直接调用，有参数使用call
	
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