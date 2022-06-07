-----------------------
template
-----------------------
	# text/template �ṩ�˻���ģ������ı����ݵĹ��ܡ�html/template���ǲ��� ��ȫ��HTML��ʽ���������������ʹ����ͬ�Ľӿ�
	# �ο�
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

		* �쳣��Ϣ

	# type ErrorCode int
		
		* �쳣״̬��

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
		* ������map

	# type HTML string
		* ģ��Ĭ�ϻ���κ���Ҫ������ַ����ܱ���ȷ�Ľ��б���
		* ʹ��HTML��Ϊ�ַ��������ݲ��ᱻ����
			template.HTML("<h1>A Safe header</h1>")

	# type HTMLAttr string
		* ��װһ����Դ���ŵ�HTML���ԣ��� `dir="ltr"`

	# type JS string
	# type JSStr string
	# type Srcset string
	# type Template struct {
			Tree *parse.Tree  // ģ����
		}

		func Must(t *Template, err error) *Template
			* Must��һ�������������Է���(*Template, error)�ĺ������з�װ����
			* �ڷ���err��Ϊnil��ʱ��panic
				if err != nil {
					panic(err)
				}
				return t
			* demo
				var t = template.Must(template.New("name").Parse("html"))


		func New(name string) *Template
			* ����һ���յġ��޽������ݵ�ģ�壬ָ������

		func ParseFiles(filenames ...string) (*Template, error)
			* ֱ�ӽ���һ�������ļ������ݣ������ص�һ���ļ�����basename��ΪTemplate������
			* Ҳ����˵���ص� tempate �� name �ǵ�һ���ļ�����
		
		func ParseGlob(pattern string) (*Template, error)
			* ����pattern��������ƥ���ģ�壬�����ص�һ���ļ�����basename��ΪTemplate������
		
		func ParseFS(fs fs.FS, patterns ...string) (*Template, error)


		func (t *Template) AddParseTree(name string, tree *parse.Tree) (*Template, error)
		func (t *Template) Clone() (*Template, error)
			* ��¡һ����ȫһ����ģ�壬����common�ṹҲ����ȫ��¡��

		func (t *Template) DefinedTemplates() string
			* ���ظ�ģ��Ķ���

		func (t *Template) Delims(left, right string) *Template
			* �޸�Ĭ�ϵı�ʶ����Ĭ���� {{  }}

		func (t *Template) Execute(wr io.Writer, data interface{}) error
			* ִ��������������
		
		func (t *Template) ExecuteTemplate(wr io.Writer, name string, data interface{}) error
			* ִ�������������ݣ�ָ��ģ�������

		func (t *Template) Funcs(funcMap FuncMap) *Template
			* ����ȫ�ֺ���
		
		func (t *Template) Lookup(name string) *Template
			* ����������ģ��������Ѱģ�壬��������ڷ���nil

		func (t *Template) Name() string
		func (t *Template) New(name string) *Template
			* ����һ���µ���Ϊname��ģ����󣬲����˶�����뵽thisģ�����С�����

		func (t *Template) Option(opt ...string) *Template
		func (t *Template) Parse(text string) (*Template, error)
			* ����ָ�����ı���Ϊģ�壬��������ǰģ��
		
		func (t *Template) ParseFiles(filenames ...string) (*Template, error)
		func (t *Template) ParseGlob(pattern string) (*Template, error)
			* ����ָ�����ļ�

			* ���������Ƕ�����t֮��ģ�����ֻ�����ļ����ݣ��������ַ�������New()������ģ�壬��ҪParse()�����������Ż���뵽common�С�

		func (t *Template) Templates() []*Template
			* ����ģ���������ģ��




-----------------------
func
-----------------------
	func HTMLEscape(w io.Writer, b []byte)
	func HTMLEscapeString(s string) string
		* URL�������

	func HTMLEscaper(args ...interface{}) string
		* �����в����ı���ʾ��HTMLת��ȼ۱�ʾ�ַ���

	func IsTrue(val interface{}) (truth, ok bool)
	func JSEscape(w io.Writer, b []byte)
	func JSEscapeString(s string) string
	func JSEscaper(args ...interface{}) string
	func URLQueryEscaper(args ...interface{}) string 