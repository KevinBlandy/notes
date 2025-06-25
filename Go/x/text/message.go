-----------------------------
Message
-----------------------------
	# message 为本地化字符串实现了格式化 I/O，其函数类似于 fmt 的 print 函数。它可以直接替代 fmt。

-----------------------------
var
-----------------------------
	var DefaultCatalog catalog.Catalog = defaultCatalog


-----------------------------
type
-----------------------------
	# type Option func(o *options)
		func Catalog(c catalog.Catalog) Option
	
	# type Printer struct {
			// contains filtered or unexported fields
		}
		func NewPrinter(t language.Tag, opts ...Option) *Printer
		func (p *Printer) Fprint(w io.Writer, a ...interface{}) (n int, err error)
		func (p *Printer) Fprintf(w io.Writer, key Reference, a ...interface{}) (n int, err error)
		func (p *Printer) Fprintln(w io.Writer, a ...interface{}) (n int, err error)
		func (p *Printer) Print(a ...interface{}) (n int, err error)
		func (p *Printer) Printf(key Reference, a ...interface{}) (n int, err error)
		func (p *Printer) Println(a ...interface{}) (n int, err error)
		func (p *Printer) Sprint(a ...interface{}) string
		func (p *Printer) Sprintf(key Reference, a ...interface{}) string
		func (p *Printer) Sprintln(a ...interface{}) string

	# type Reference interface {
		}
		func Key(id string, fallback string) Reference

-----------------------------
func
-----------------------------

	func MatchLanguage(preferred ...string) language.Tag
	func Set(tag language.Tag, key string, msg ...catalog.Message) error
	func SetString(tag language.Tag, key string, msg string) error