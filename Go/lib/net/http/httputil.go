---------------------
httputil
---------------------
	# http相关的工具类

---------------------
var
---------------------
	var (
		ErrPersistEOF = &http.ProtocolError{ErrorString: "persistent connection closed"}
		ErrClosed = &http.ProtocolError{ErrorString: "connection closed by user"}
		ErrPipeline = &http.ProtocolError{ErrorString: "pipeline error"}
	)

	var ErrLineTooLong = internal.ErrLineTooLong

---------------------
type
---------------------
	# type BufferPool interface {
			Get() []byte
			Put([]byte)
		}
	
	# type ClientConn struct {
		}
		func NewClientConn(c net.Conn, r *bufio.Reader) *ClientConn
		func NewProxyClientConn(c net.Conn, r *bufio.Reader) *ClientConn
		func (cc *ClientConn) Close() error
		func (cc *ClientConn) Do(req *http.Request) (*http.Response, error)
		func (cc *ClientConn) Hijack() (c net.Conn, r *bufio.Reader)
		func (cc *ClientConn) Pending() int
		func (cc *ClientConn) Read(req *http.Request) (resp *http.Response, err error)
		func (cc *ClientConn) Write(req *http.Request) error
	
	# type ReverseProxy struct {
			Director func(*http.Request)
			Transport http.RoundTripper
			FlushInterval time.Duration
			ErrorLog *log.Logger
			BufferPool BufferPool
			ModifyResponse func(*http.Response) error
			ErrorHandler func(http.ResponseWriter, *http.Request, error)
		}
		func NewSingleHostReverseProxy(target *url.URL) *ReverseProxy
		func (p *ReverseProxy) ServeHTTP(rw http.ResponseWriter, req *http.Request)
	
	# type ServerConn struct {
		}
		func NewServerConn(c net.Conn, r *bufio.Reader) *ServerConn
		func (sc *ServerConn) Close() error
		func (sc *ServerConn) Hijack() (net.Conn, *bufio.Reader)
		func (sc *ServerConn) Pending() int
		func (sc *ServerConn) Read() (*http.Request, error)
		func (sc *ServerConn) Write(req *http.Request, resp *http.Response) error

---------------------
fauc
---------------------
	func DumpRequest(req *http.Request, body bool) ([]byte, error)
	func DumpRequestOut(req *http.Request, body bool) ([]byte, error)

	func DumpResponse(resp *http.Response, body bool) ([]byte, error)

	func NewChunkedReader(r io.Reader) io.Reader
	func NewChunkedWriter(w io.Writer) io.WriteCloser