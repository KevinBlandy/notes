----------------
httptest
----------------
	# http测试

----------------
var
----------------
	const DefaultRemoteAddr = "1.2.3.4"


----------------
type
----------------
	# type ResponseRecorder struct {
			Code int
			HeaderMap http.Header
			Body *bytes.Buffer
			Flushed bool
		}

		func NewRecorder() *ResponseRecorder
		func (rw *ResponseRecorder) Flush()
		func (rw *ResponseRecorder) Header() http.Header
		func (rw *ResponseRecorder) Result() *http.Response
		func (rw *ResponseRecorder) Write(buf []byte) (int, error)
		func (rw *ResponseRecorder) WriteHeader(code int)
		func (rw *ResponseRecorder) WriteString(str string) (int, error)

	# type Server struct {
			URL      string
			Listener net.Listener
			EnableHTTP2 bool
			TLS *tls.Config
			Config *http.Server
		}

		func NewServer(handler http.Handler) *Server
		func NewTLSServer(handler http.Handler) *Server
		func NewUnstartedServer(handler http.Handler) *Server
		func (s *Server) Certificate() *x509.Certificate
		func (s *Server) Client() *http.Client
		func (s *Server) Close()
		func (s *Server) CloseClientConnections()
		func (s *Server) Start()
		func (s *Server) StartTLS()
	
----------------
func
----------------
	func NewRequest(method, target string, body io.Reader) *http.Request
		* 快速生成一个Reuqet对象
		* Response状态是400，method 不写默认是 GET
	
