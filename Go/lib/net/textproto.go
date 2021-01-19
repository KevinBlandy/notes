------------------------
textproto
------------------------
	# 提供了以HTTP，NNTP和SMTP的样式实现了对基于文本的请求/响应协议的通用支持

------------------------
var
------------------------

------------------------
type
------------------------
	# type Conn struct {
			Reader
			Writer
			Pipeline
		}
		func Dial(network, addr string) (*Conn, error)
		func NewConn(conn io.ReadWriteCloser) *Conn
		func (c *Conn) Close() error
		func (c *Conn) Cmd(format string, args ...interface{}) (id uint, err error)

	# type Error struct {
			Code int
			Msg  string
		}
		func (e *Error) Error() string
	
	# type MIMEHeader map[string][]string
		func (h MIMEHeader) Add(key, value string)
		func (h MIMEHeader) Del(key string)
		func (h MIMEHeader) Get(key string) string
		func (h MIMEHeader) Set(key, value string)
		func (h MIMEHeader) Values(key string) []string
	
	# type Pipeline struct {
		}
		func (p *Pipeline) EndRequest(id uint)
		func (p *Pipeline) EndResponse(id uint)
		func (p *Pipeline) Next() uint
		func (p *Pipeline) StartRequest(id uint)
		func (p *Pipeline) StartResponse(id uint)
	
	# type ProtocolError string
		func (p ProtocolError) Error() string
	
	# type Reader struct {
			R *bufio.Reader
		}
		func NewReader(r *bufio.Reader) *Reader
		func (r *Reader) DotReader() io.Reader
		func (r *Reader) ReadCodeLine(expectCode int) (code int, message string, err error)
		func (r *Reader) ReadContinuedLine() (string, error)
		func (r *Reader) ReadContinuedLineBytes() ([]byte, error)
		func (r *Reader) ReadDotBytes() ([]byte, error)
		func (r *Reader) ReadDotLines() ([]string, error)
		func (r *Reader) ReadLine() (string, error)
		func (r *Reader) ReadLineBytes() ([]byte, error)
		func (r *Reader) ReadMIMEHeader() (MIMEHeader, error)
		func (r *Reader) ReadResponse(expectCode int) (code int, message string, err error)
	
	# type Writer struct {
			W *bufio.Writer
		}
		func NewWriter(w *bufio.Writer) *Writer
		func (w *Writer) DotWriter() io.WriteCloser
		func (w *Writer) PrintfLine(format string, args ...interface{}) error

------------------------
func
------------------------
	func CanonicalMIMEHeaderKey(s string) string
	func TrimBytes(b []byte) []byte
	func TrimString(s string) string