-----------------------
template
-----------------------

-----------------------
var
-----------------------

-----------------------
type
-----------------------
	# type ExecError struct {
			Name string // Name of template.
			Err  error  // Pre-formatted error.
		}
		func (e ExecError) Error() string
		func (e ExecError) Unwrap() error
	
	# type FuncMap map[string]interface{}
	
	# type Template struct {
			*parse.Tree
		}
		func Must(t *Template, err error) *Template
		func New(name string) *Template
		func ParseFiles(filenames ...string) (*Template, error)
		func ParseGlob(pattern string) (*Template, error)
		func (t *Template) AddParseTree(name string, tree *parse.Tree) (*Template, error)
		func (t *Template) Clone() (*Template, error)
		func (t *Template) DefinedTemplates() string
		func (t *Template) Delims(left, right string) *Template
		func (t *Template) Execute(wr io.Writer, data interface{}) error
		func (t *Template) ExecuteTemplate(wr io.Writer, name string, data interface{}) error
		func (t *Template) Funcs(funcMap FuncMap) *Template
		func (t *Template) Lookup(name string) *Template
		func (t *Template) Name() string
		func (t *Template) New(name string) *Template
		func (t *Template) Option(opt ...string) *Template
		func (t *Template) Parse(text string) (*Template, error)
		func (t *Template) ParseFiles(filenames ...string) (*Template, error)
		func (t *Template) ParseGlob(pattern string) (*Template, error)
		func (t *Template) Templates() []*Template


-----------------------
func
-----------------------
	func HTMLEscape(w io.Writer, b []byte)
	func HTMLEscapeString(s string) string
	func HTMLEscaper(args ...interface{}) string
	func IsTrue(val interface{}) (truth, ok bool)
	func JSEscape(w io.Writer, b []byte)
	func JSEscapeString(s string) string
	func JSEscaper(args ...interface{}) string
	func URLQueryEscaper(args ...interface{}) string