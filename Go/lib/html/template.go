-----------------------
template
-----------------------
	# text/template 提供了基于模板输出文本内容的功能。html/template则是产生 安全的HTML格式的输出。这两个包使用相同的接口
	# 参考
		https://colobu.com/2019/11/05/Golang-Templates-Cheatsheet/

-----------------------
var
-----------------------

-----------------------
type
-----------------------
	# type CSS string

	# type Error struct {
			ErrorCode ErrorCode
			Node parse.Node
			Name string
			Line int
			Description string
		}
		func (e *Error) Error() string

		* 异常信息

	# type ErrorCode int
		
		* 异常状态码

		const (
			OK ErrorCode = iota
			ErrAmbigContext
			ErrBadHTML
			ErrBranchEnd
			ErrEndContext
			ErrNoSuchTemplate
			ErrOutputContext
			ErrPartialCharset
			ErrPartialEscape
			ErrRangeLoopReentry
			ErrSlashAmbig
			ErrPredefinedEscaper
		)
	
	# type FuncMap map[string]interface{}
		* 函数的map

	# type HTML string
		* 模板默认会把任何需要编码的字符都能被正确的进行编码
		* 使用HTML作为字符串，内容不会被编码
			template.HTML("<h1>A Safe header</h1>")

	# type HTMLAttr string
		* 封装一个来源可信的HTML属性，如 `dir="ltr"`

	# type JS string
	# type JSStr string
	# type Srcset string
	# type Template struct {
			Tree *parse.Tree  // 模板树
		}

		func Must(t *Template, err error) *Template
			* Must是一个帮助程序，它对返回(*Template, error)的函数进行封装可以
			* 在返回err不为nil的时候，panic
				if err != nil {
					panic(err)
				}
				return t
			* demo
				var t = template.Must(template.New("name").Parse("html"))


		func New(name string) *Template
			* 创建一个空的、无解析数据的模板，指定名称

		func ParseFiles(filenames ...string) (*Template, error)
			* 直接解析一个或多个文件的内容，并返回第一个文件名的basename作为Template的名称
			* 也就是说返回的 tempate 的 name 是第一个文件名称
		
		func ParseGlob(pattern string) (*Template, error)
			* 根据pattern解析所有匹配的模板，并返回第一个文件名的basename作为Template的名称
		
		func ParseFS(fs fs.FS, patterns ...string) (*Template, error)


		func (t *Template) AddParseTree(name string, tree *parse.Tree) (*Template, error)
		func (t *Template) Clone() (*Template, error)
			* 克隆一个完全一样的模板，包括common结构也会完全克隆。

		func (t *Template) DefinedTemplates() string
			* 返回该模板的定义

		func (t *Template) Delims(left, right string) *Template
			* 修改默认的标识符，默认是 {{  }}

		func (t *Template) Execute(wr io.Writer, data interface{}) error
			* 执行输出，填充数据
		
		func (t *Template) ExecuteTemplate(wr io.Writer, name string, data interface{}) error
			* 执行输出，填充数据，指定模板的名称

		func (t *Template) Funcs(funcMap FuncMap) *Template
			* 设置全局函数
		
		func (t *Template) Lookup(name string) *Template
			* 根据名称在模板组中找寻模板，如果不存在返回nil

		func (t *Template) Name() string
		func (t *Template) New(name string) *Template
			* 创建一个新的名为name的模板对象，并将此对象加入到this模板组中。返回

		func (t *Template) Option(opt ...string) *Template
		func (t *Template) Parse(text string) (*Template, error)
			* 解析指定的文本作为模板，关联到当前模板
		
		func (t *Template) ParseFiles(filenames ...string) (*Template, error)
		func (t *Template) ParseGlob(pattern string) (*Template, error)
			* 解析指定的文件

			* 解析过程是独立于t之外的，它们只解析文件内容，不解析字符串。而New()出来的模板，需要Parse()方法来解析才会加入到common中。

		func (t *Template) Templates() []*Template
			* 返回模板组的所有模板




-----------------------
func
-----------------------
	func HTMLEscape(w io.Writer, b []byte)
	func HTMLEscapeString(s string) string
		* URL编码进行

	func HTMLEscaper(args ...interface{}) string
		* 其所有参数文本表示的HTML转义等价表示字符串

	func IsTrue(val interface{}) (truth, ok bool)
	func JSEscape(w io.Writer, b []byte)
	func JSEscapeString(s string) string
	func JSEscaper(args ...interface{}) string
	func URLQueryEscaper(args ...interface{}) string 