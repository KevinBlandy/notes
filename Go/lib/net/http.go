-----------------
http
-----------------

-----------------
变量
-----------------
	# 请求方法
		const (
			MethodGet     = "GET"
			MethodHead    = "HEAD"
			MethodPost    = "POST"
			MethodPut     = "PUT"
			MethodPatch   = "PATCH" // RFC 5789
			MethodDelete  = "DELETE"
			MethodConnect = "CONNECT"
			MethodOptions = "OPTIONS"
			MethodTrace   = "TRACE"
		)
	
	# Http状态码
		const (
			StatusContinue           = 100 // RFC 7231, 6.2.1
			StatusSwitchingProtocols = 101 // RFC 7231, 6.2.2
			StatusProcessing         = 102 // RFC 2518, 10.1
			StatusEarlyHints         = 103 // RFC 8297

			StatusOK                   = 200 // RFC 7231, 6.3.1
			StatusCreated              = 201 // RFC 7231, 6.3.2
			StatusAccepted             = 202 // RFC 7231, 6.3.3
			StatusNonAuthoritativeInfo = 203 // RFC 7231, 6.3.4
			StatusNoContent            = 204 // RFC 7231, 6.3.5
			StatusResetContent         = 205 // RFC 7231, 6.3.6
			StatusPartialContent       = 206 // RFC 7233, 4.1
			StatusMultiStatus          = 207 // RFC 4918, 11.1
			StatusAlreadyReported      = 208 // RFC 5842, 7.1
			StatusIMUsed               = 226 // RFC 3229, 10.4.1

			StatusMultipleChoices  = 300 // RFC 7231, 6.4.1
			StatusMovedPermanently = 301 // RFC 7231, 6.4.2
			StatusFound            = 302 // RFC 7231, 6.4.3
			StatusSeeOther         = 303 // RFC 7231, 6.4.4
			StatusNotModified      = 304 // RFC 7232, 4.1
			StatusUseProxy         = 305 // RFC 7231, 6.4.5

			StatusTemporaryRedirect = 307 // RFC 7231, 6.4.7
			StatusPermanentRedirect = 308 // RFC 7538, 3

			StatusBadRequest                   = 400 // RFC 7231, 6.5.1
			StatusUnauthorized                 = 401 // RFC 7235, 3.1
			StatusPaymentRequired              = 402 // RFC 7231, 6.5.2
			StatusForbidden                    = 403 // RFC 7231, 6.5.3
			StatusNotFound                     = 404 // RFC 7231, 6.5.4
			StatusMethodNotAllowed             = 405 // RFC 7231, 6.5.5
			StatusNotAcceptable                = 406 // RFC 7231, 6.5.6
			StatusProxyAuthRequired            = 407 // RFC 7235, 3.2
			StatusRequestTimeout               = 408 // RFC 7231, 6.5.7
			StatusConflict                     = 409 // RFC 7231, 6.5.8
			StatusGone                         = 410 // RFC 7231, 6.5.9
			StatusLengthRequired               = 411 // RFC 7231, 6.5.10
			StatusPreconditionFailed           = 412 // RFC 7232, 4.2
			StatusRequestEntityTooLarge        = 413 // RFC 7231, 6.5.11
			StatusRequestURITooLong            = 414 // RFC 7231, 6.5.12
			StatusUnsupportedMediaType         = 415 // RFC 7231, 6.5.13
			StatusRequestedRangeNotSatisfiable = 416 // RFC 7233, 4.4
			StatusExpectationFailed            = 417 // RFC 7231, 6.5.14
			StatusTeapot                       = 418 // RFC 7168, 2.3.3
			StatusMisdirectedRequest           = 421 // RFC 7540, 9.1.2
			StatusUnprocessableEntity          = 422 // RFC 4918, 11.2
			StatusLocked                       = 423 // RFC 4918, 11.3
			StatusFailedDependency             = 424 // RFC 4918, 11.4
			StatusTooEarly                     = 425 // RFC 8470, 5.2.
			StatusUpgradeRequired              = 426 // RFC 7231, 6.5.15
			StatusPreconditionRequired         = 428 // RFC 6585, 3
			StatusTooManyRequests              = 429 // RFC 6585, 4
			StatusRequestHeaderFieldsTooLarge  = 431 // RFC 6585, 5
			StatusUnavailableForLegalReasons   = 451 // RFC 7725, 3

			StatusInternalServerError           = 500 // RFC 7231, 6.6.1
			StatusNotImplemented                = 501 // RFC 7231, 6.6.2
			StatusBadGateway                    = 502 // RFC 7231, 6.6.3
			StatusServiceUnavailable            = 503 // RFC 7231, 6.6.4
			StatusGatewayTimeout                = 504 // RFC 7231, 6.6.5
			StatusHTTPVersionNotSupported       = 505 // RFC 7231, 6.6.6
			StatusVariantAlsoNegotiates         = 506 // RFC 2295, 8.1
			StatusInsufficientStorage           = 507 // RFC 4918, 11.5
			StatusLoopDetected                  = 508 // RFC 5842, 7.2
			StatusNotExtended                   = 510 // RFC 2774, 7
			StatusNetworkAuthenticationRequired = 511 // RFC 6585, 6
		)
	
	# 默认的Header大小
		const DefaultMaxHeaderBytes = 1 << 20 // 1 MB
	
	# Transport的MaxIdleConnsPerHost的默认值。
		const DefaultMaxIdleConnsPerHost = 2
	
	# 在HTTP头文件中生成时间时使用的时间格式
		const TrailerPrefix = "Trailer:"

		* 它类似于time.RFC1123，但硬编码为GMT作为时区。被格式化的时间必须是UTC，这样Format才能生成正确的格式。

	
	# 异常信息
		var (
			ErrNotSupported = &ProtocolError{"feature not supported"}	
				* 暂不支持，HTTP/2 Push 还不支持

			ErrUnexpectedTrailer = &ProtocolError{"trailer header without chunked transfer encoding"}
				* 废弃

			ErrMissingBoundary = &ProtocolError{"no multipart boundary param in Content-Type"}
				* 请求的Content-Type不包括 "multipart boundary "参数。

			ErrNotMultipart = &ProtocolError{"request Content-Type isn't multipart/form-data"}
				* 当前请求不是multipart 请求

			ErrHeaderTooLong = &ProtocolError{"header too long"}
				* 废弃
			ErrShortBody = &ProtocolError{"entity body too short"}
				* 废弃
			ErrMissingContentLength = &ProtocolError{"missing ContentLength in HEAD response"}
				* 废弃
		)
	
	# 异常
		var (
			ErrBodyNotAllowed = errors.New("http: request method or response status code does not allow body")

			ErrHijacked = errors.New("http: connection has been hijacked")

			ErrContentLength = errors.New("http: wrote more than the declared Content-Length")

			ErrWriteAfterFlush = errors.New("unused")
		)
	
	# ContextKey
		var (
			ServerContextKey = &contextKey{"http-server"}
				* 返回 *Server

			LocalAddrContextKey = &contextKey{"local-addr"}
				* 返回 net.Addr
		)
	
	# 默认客户端，被Get、Head和Post使用。
		var DefaultClient = &Client{}
	
	# Serve使用的默认ServeMux
		var DefaultServeMux = &defaultServeMux
	
	# 预定义的异常信息
		var ErrAbortHandler = errors.New("net/http: abort Handler")
		var ErrBodyReadAfterClose = errors.New("http: invalid Read on closed Body")
		var ErrHandlerTimeout = errors.New("http: Handler timeout")
		var ErrLineTooLong = internal.ErrLineTooLong
		var ErrMissingFile = errors.New("http: no such file")
		var ErrNoCookie = errors.New("http: named cookie not present")
		var ErrNoLocation = errors.New("http: no Location header in response")
		var ErrSchemeMismatch = errors.New("http: server gave HTTP response to HTTPS client")
			* 当服务器向 HTTPS 客户端返回 HTTP 响应时，会返回 ErrSchemeMismatch。
		
		var ErrServerClosed = errors.New("http: Server closed")
			* 服务器关闭

		var ErrSkipAltProtocol = errors.New("net/http: skip alternate protocol")
		var ErrUseLastResponse = errors.New("net/http: use last response")
	
	# 空的Body
		var NoBody = noBody{}
	
	# 默认的HTTP时间格式化
		const TimeFormat = "Mon, 02 Jan 2006 15:04:05 GMT"

		* time.RFC1123 定义的日期格式
		* 格式化时间
			httpTime := time.Now().Format(http.TimeFormat)
			log.Println(httpTime)
		
		* 解析时间
			func ParseTime(text string) (t time.Time, err error)
		
-----------------
type
-----------------
	# type Client struct {
			Transport RoundTripper
				* 连接控制
			
			CheckRedirect func(req *Request, via []*Request) error
			Jar CookieJar
			Timeout time.Duration
				* 超时设置，这个时间包含了: 连接时间、任何重定向，和读取响应体
		}
		func (c *Client) CloseIdleConnections()
		func (c *Client) Do(req *Request) (*Response, error)
		func (c *Client) Get(url string) (resp *Response, err error)
		func (c *Client) Head(url string) (resp *Response, err error)
		func (c *Client) Post(url, contentType string, body io.Reader) (resp *Response, err error)
		func (c *Client) PostForm(url string, data url.Values) (resp *Response, err error)
	
	# type CloseNotifier interface {
			CloseNotify() <-chan bool
		}

		* 废弃
	
	# type ConnState int
		* 连接状态
		* 预定义枚举
			const (
				StateNew ConnState = iota
				StateActive
				StateIdle
				StateHijacked
				StateClosed
			)
		
		func (c ConnState) String() string
	
	# type Cookie struct {
			Name  string
			Value string
			Quoted bool
				* Cookie 保留了 cookie 值周围的双引号。
				* Cookie.Quoted 字段表示 Cookie.Value 是否最初是带引号的。
			Path       string    // optional
			Domain     string    // optional
			Expires    time.Time // optional
			RawExpires string    // for reading cookies only
			MaxAge   int
			Secure   bool
			HttpOnly bool
			SameSite SameSite
			Partitioned bool
				* 是否带有 Partitioned 属性
			Raw      string
			Unparsed []string // Raw text of unparsed attribute-value pairs
		}

		func ParseCookie(line string) ([]*Cookie, error)
			* 解析 Cookie header 值，并返回其中设置的所有 cookie。由于相同的 cookie 名称可以出现多次，因此返回的值可以包含给定键的多个值。

		func ParseSetCookie(line string) (*Cookie, error)
			* 解析 Set-Cookie header 值并返回 cookie。如果语法错误，则返回错误信息。

		func (c *Cookie) String() string
		func (c *Cookie) Valid() error
	
	# type CookieJar interface {
			SetCookies(u *url.URL, cookies []*Cookie)
			Cookies(u *url.URL) []*Cookie
		}
	
	# type Dir string
		func (d Dir) Open(name string) (File, error)

		* 一个Dir实现了 FileSystem，使用本地文件系统限制在一个特定的目录树上。
		* 空的Dir会被视为"."

	
	# type File interface {
			io.Closer
			io.Reader
			io.Seeker
			Readdir(count int) ([]os.FileInfo, error)
			Stat() (os.FileInfo, error)
		}

		* http文件对象

	# type FileSystem interface {
			Open(name string) (File, error)
		}

		* 文件系统接口，本质上就是要求实现Open返回File

		func FS(fsys fs.FS) FileSystem
			* 转换 fs.FS 为 http.FileSystem
	
	# type Flusher interface {
			Flush()
		}
	
	# type Handler interface {
			ServeHTTP(ResponseWriter, *Request)
		}
		func FileServerFS(root fs.FS) Handler
			* 用文件系统 fsys 的内容为 HTTP 请求提供服务
			* 本质上就是调用：FileServer(FS(root))

		func AllowQuerySemicolons(h Handler) Handler
			* 返回一个支持 ; 分割查询参数的handler
			* 这个方法应该在Request.ParseForm被调用之前被调用。

		func FileServer(root FileSystem) Handler
			* 返回一个使用 FileSystem 接口 root 提供文件访问服务的 HTTP 处理器。可以方便的实现静态文件服务器
				http.ListenAndServe(":8080", http.FileServer(http.Dir("/files/path")))

		func NotFoundHandler() Handler
		func RedirectHandler(url string, code int) Handler
		func StripPrefix(prefix string, h Handler) Handler
			* 它通过从请求的URL的Path中删除给定的前缀并调用处理程序h来服务HTTP请求。
			* StripPrefix通过回复HTTP 404 not found错误来处理一个不是以前缀开始的路径请求。
				http.Handle("/tmpfiles/", http.StripPrefix("/tmpfiles/", http.FileServer(http.Dir("/tmp"))))
				// 服务器收到 /tmpfiles/ 开头的请求，然后会移除URL中的 /tmpfiles/，然后去 /tmp 目录找文件

		func TimeoutHandler(h Handler, dt time.Duration, msg string) Handler
			* 返回一个支持调用超时的Handler，如果超过时间还没处理完，则会给客户端响应503
			* 如果msg为空，则会响应默认的html代码消息。

		func MaxBytesHandler(h Handler, n int64) Handler
			* 快捷的返回“限制请求体大小”的handler
			* 源码
				return HandlerFunc(func(w ResponseWriter, r *Request) {
					r2 := *r
					r2.Body = MaxBytesReader(w, r.Body, n)
					h.ServeHTTP(w, &r2)
				})
	
	# type HandlerFunc func(ResponseWriter, *Request)
		func (f HandlerFunc) ServeHTTP(w ResponseWriter, r *Request)
	
	# type Header map[string][]string
		func (h Header) Add(key, value string)
		func (h Header) Clone() Header
		func (h Header) Del(key string)
		func (h Header) Get(key string) string
		func (h Header) Set(key, value string)
		func (h Header) Values(key string) []string
		func (h Header) Write(w io.Writer) error
		func (h Header) WriteSubset(w io.Writer, exclude map[string]bool) error
			* WriteSubset以wire格式写一个头。
			* 如果exclude不是nil，exclude[key] == true的键不会被写入。在检查exclude映射之前，键不会被规范化。

	
	# type Hijacker interface {
			Hijack() (net.Conn, *bufio.ReadWriter, error)
		}
	
	# type ProtocolError struct {
			ErrorString string
		}
		
		* 协议异常，已过时

		func (pe *ProtocolError) Error() string
		func (pe *ProtocolError) Is(err error) bool
	
	# type MaxBytesError struct {
			Limit int64
		}	
		
		* 请求体超出最大限制异常

		func (e *MaxBytesError) Error() string

	# type PushOptions struct {
			Method string
			Header Header
		}
		* 指定Push的方法和header
	
	# type Pusher interface {
			Push(target string, opts *PushOptions) error
		}

		* http2的push接口
	
	# type Request struct {
			Method string
			URL *url.URL
				* 请求的URL

				* 如果是Server中的Request，那么这个值是从 RequestURI 中的URI解析而来
				* 所以大多数情况这个对象里只有 Path 和 RawQuery 字段有值

			Proto      string // "HTTP/1.0"
			ProtoMajor int    // 1
			ProtoMinor int    // 0
			Header Header
			Body io.ReadCloser
			GetBody func() (io.ReadCloser, error)
			ContentLength int64
			TransferEncoding []string
			Close bool
			Host string
				* 客户端请求的HOST，如果有端口会包含端口信息

			Form url.Values
			PostForm url.Values
			MultipartForm *multipart.Form
			Trailer Header
			RemoteAddr string
			RequestURI string
				* 请求的URI，会包含查询字符串

			TLS *tls.ConnectionState
			Cancel <-chan struct{}
			Response *Response
			Pattern string
				* 对于入站请求Pattern 字段包含与请求匹配的 ServeMux 模式（如果有）。

		}
		func NewRequest(method, url string, body io.Reader) (*Request, error)
		func NewRequestWithContext(ctx context.Context, method, url string, body io.Reader) (*Request, error)
		func ReadRequest(b *bufio.Reader) (*Request, error)
		func (r *Request) AddCookie(c *Cookie)
		func (r *Request) BasicAuth() (username, password string, ok bool)
		func (r *Request) Clone(ctx context.Context) *Request
			* Clone一个Request请求

		func (r *Request) Context() context.Context
			* 返回当前Request上的Context，如果没有，则返回 context.Background()
			* 对于传出的客户端请求，Context 控制取消操作。
			* 对于传入的服务器请求，当客户端连接关闭、请求被取消（使用 HTTP/2），或 ServeHTTP 方法返回时，上下文将被取消。


		func (r *Request) Cookie(name string) (*Cookie, error)
		func (r *Request) Cookies() []*Cookie
		func (r *Request) CookiesNamed(name string) []*Cookie
		func (r *Request) FormFile(key string) (multipart.File, *multipart.FileHeader, error)
			* 返回 formdata 请求中的文件参数

		func (r *Request) FormValue(key string) string
			* 返回表单参数中的第一个值，包括查询参数，body 参数优先于查询参数。
			* FormValue 会调用 ParseMultipartForm 和 ParseForm
			* 如果要获取参数值列表，请调用 ParseForm()，然后直接使用 Request.Form 对象。

		func (r *Request) MultipartReader() (*multipart.Reader, error)
			* 返回mulripart的reader，可以自己进行迭代获取每一个Part项
			* 如果迭代到最后，返回 io.EOF 异常
			* 它不能和ParseMultipartForm一起使用
			* 建议使用这个，它是流式解析

		func (r *Request) ParseForm() error
		func (r *Request) ParseMultipartForm(maxMemory int64) error
			* 解析multipart请求，设置内存缓存大小，超过这个阈值，就会把数据IO到临时文件
		
		func (r *Request) PostFormValue(key string) string
			* 获取表单参数，注意，它会忽略查询参数。
			* PostFormValue 会调用 ParseMultipartForm 和 ParseForm
		
		func (r *Request) PathValue(name string) string
			* 获取 ServeMux 模式中指定路径通配符的值
			* 果请求未与模式匹配或模式中没有此类通配符，则返回空字符串。
		
		func (r *Request) SetPathValue(name, value string)
			* 在 Requst 上将 name 设置为 value，以便后续调用 request.PathValue(name) 时返回 value。

		func (r *Request) ProtoAtLeast(major, minor int) bool
		func (r *Request) Referer() string
		func (r *Request) SetBasicAuth(username, password string)
		func (r *Request) UserAgent() string

		func (r *Request) WithContext(ctx context.Context) *Request
			* 给当前Request设置 Context
			* clone 并且返回新的 Request

		func (r *Request) Write(w io.Writer) error
			* 写入 Write，写入一个 HTTP/1.1 请求，包括 Header 和 Body。此方法会参考请求的以下字段：
				Host
				URL
				Method （默认为 "GET"）
				Header
				ContentLength
				TransferEncoding
				Body
			* 如果存在 Body 且 Content-Length 小于等于 0 并且 Request.TransferEncoding 没有被设置为 "identity"，Write 会在头部添加 "Transfer-Encoding: chunked"。
			* Body 在发送后会被关闭。


		func (r *Request) WriteProxy(w io.Writer) error
			* 类似于 Request.Write，但它以 HTTP 代理预期的形式写入请求。
			* 特别地，Request.WriteProxy 会根据 RFC 7230 第 5.3 节的规定，在请求的初始 Request-URI 行写入一个绝对 URI，包括 Sheme 和 Host。
			* 在任何情况下，WriteProxy 还会写入一个 Host Header，使用 r.Host 或 r.URL.Host。

	
	# type Response struct {
			Status     string // e.g. "200 OK"
			StatusCode int    // e.g. 200
			Proto      string // e.g. "HTTP/1.0"
			ProtoMajor int    // e.g. 1
			ProtoMinor int    // e.g. 0
			Header Header
			Body io.ReadCloser
			ContentLength int64
			TransferEncoding []string
			Close bool
			Uncompressed bool
			Trailer Header
			Request *Request
			TLS *tls.ConnectionState
		}

		* 执行http请求后的服务器响应对象

		func Get(url string) (resp *Response, err error)
		func Head(url string) (resp *Response, err error)
		func Post(url, contentType string, body io.Reader) (resp *Response, err error)
		func PostForm(url string, data url.Values) (resp *Response, err error)
		func ReadResponse(r *bufio.Reader, req *Request) (*Response, error)
		func (r *Response) Cookies() []*Cookie
		func (r *Response) Location() (*url.URL, error)
		func (r *Response) ProtoAtLeast(major, minor int) bool
		func (r *Response) Write(w io.Writer) error
	
	# type ResponseController struct {
			// contains filtered or unexported fields
		}
		func NewResponseController(rw ResponseWriter) *ResponseController
		func (c *ResponseController) EnableFullDuplex() error
			* 表示允许同时读取请求和响应body

		func (c *ResponseController) Flush() error
		func (c *ResponseController) Hijack() (net.Conn, *bufio.ReadWriter, error)
		func (c *ResponseController) SetReadDeadline(deadline time.Time) error
		func (c *ResponseController) SetWriteDeadline(deadline time.Time) error
		


	# type ResponseWriter interface {
			Header() Header
				* 获取缓存的header
			Write([]byte) (int, error)
				* 写入header到缓存
			WriteHeader(statusCode int)
				* 把缓存的header写入到客户端。并且指定状态码
				* 这个方法，应该在其他header写入后执行
					jsonRet, _ := json.MarshalIndent(request.Header, "", "	")
					writer.Header().Set("Server", "Golang")
					writer.Header().Set("Content-Type", "application/json; charset=utf-8")
					writer.WriteHeader(http.StatusOK)  // 在其他Header被写入后执行
					writer.Write(jsonRet) // 写入响应体
				
				* 如果没有显式调用 WriteHeader ，第一次调用 Write 会触发隐式 WriteHeader(http.StatusOK)。
				* 因此，对 WriteHeader 的显式调用主要用于发送错误代码或 1xx 信息响应。
		}

		* http服务器中的Response对象，用于往客户端写入http响应
	
	# type RoundTripper interface {
			RoundTrip(*Request) (*Response, error)
				* 执行HTTP请求，返回Response
		}
		
		* RoundTripper 是一个接口，代表了执行单个HTTP事务的能力，为一个给定的请求获取响应。
		* 一个 RoundTripper 必须是安全的，可以被多个goroutine同时使用。

		func NewFileTransport(fs FileSystem) RoundTripper
		func NewFileTransportFS(fsys fs.FS) RoundTripper
	
	# type SameSite int
		* Cookie的SameSite策略
		* 枚举
			const (
				SameSiteDefaultMode SameSite = iota + 1
				SameSiteLaxMode
				SameSiteStrictMode
				SameSiteNoneMode
			)
	
	# type ServeMux struct {
		}
		
		* 一个HTTP请求复用器。
		* 它将每个传入请求的URL与注册模式列表进行匹配，并调用与URL最匹配的模式的处理程序

		func NewServeMux() *ServeMux
		func (mux *ServeMux) Handle(pattern string, handler Handler)
		func (mux *ServeMux) HandleFunc(pattern string, handler func(ResponseWriter, *Request))
		func (mux *ServeMux) Handler(r *Request) (h Handler, pattern string)
			* 获取与指定请求相匹配的 handler 和映射模式

		func (mux *ServeMux) ServeHTTP(w ResponseWriter, r *Request)
	
	# type Server struct {
			Addr string
			Handler Handler // handler to invoke, http.DefaultServeMux if nil
			DisableGeneralOptionsHandler bool
			TLSConfig *tls.Config
				* tls的配置

			ReadTimeout time.Duration
			ReadHeaderTimeout time.Duration
			WriteTimeout time.Duration
			IdleTimeout time.Duration
				* 读/读header/写/空闲的超时时间
				* 设置为复数表示不超时

			MaxHeaderBytes int
			TLSNextProto map[string]func(*Server, *tls.Conn, Handler)
			ConnState func(net.Conn, ConnState)
			ErrorLog *log.Logger
			BaseContext func(net.Listener) context.Context
			ConnContext func(ctx context.Context, c net.Conn) context.Context
		}
		func (srv *Server) Close() error
		func (srv *Server) ListenAndServe() error
		func (srv *Server) ListenAndServeTLS(certFile, keyFile string) error
		func (srv *Server) RegisterOnShutdown(f func())
			* 添加关闭时的钩子方法到Server
				onShutdown []func()
			
		func (srv *Server) Serve(l net.Listener) error
		func (srv *Server) ServeTLS(l net.Listener, certFile, keyFile string) error
		func (srv *Server) SetKeepAlivesEnabled(v bool)
		func (srv *Server) Shutdown(ctx context.Context) error
			* 关闭服务器
			* 当Shutdown被调用时，Serve、ListenAndServe和ListenAndServeTLS立即返回ErrServerClosed。
			* 请确保程序不退出，而是等待Shutdown的返回
	
	# type Transport struct {
			Proxy func(*Request) (*url.URL, error)
			OnProxyConnectResponse func(ctx context.Context, proxyURL *url.URL, connectReq *Request, connectRes *Response) error
			DialContext func(ctx context.Context, network, addr string) (net.Conn, error)
			Dial func(network, addr string) (net.Conn, error)
			DialTLSContext func(ctx context.Context, network, addr string) (net.Conn, error)
			TLSClientConfig *tls.Config
				* 客户端的TLS配置

			TLSHandshakeTimeout time.Duration
			DisableKeepAlives bool
				* 禁止保持长连接

			DisableCompression bool
				* 禁止压缩

			MaxIdleConns int
			MaxIdleConnsPerHost int
			MaxConnsPerHost int
			IdleConnTimeout time.Duration
			ResponseHeaderTimeout time.Duration
			ExpectContinueTimeout time.Duration
			TLSNextProto map[string]func(authority string, c *tls.Conn) RoundTripper
			ProxyConnectHeader Header
			GetProxyConnectHeader func(ctx context.Context, proxyURL *url.URL, target string) (Header, error) // Go 1.16
			MaxResponseHeaderBytes int64
			WriteBufferSize int
			ReadBufferSize int
			ForceAttemptHTTP2 bool // contains filtered or unexported fields
		}

		* Http通信协议相关的配置，主要是客户端使用
		* 线程安全，可重用

		func (t *Transport) CancelRequest(req *Request)
		func (t *Transport) Clone() *Transport
		func (t *Transport) CloseIdleConnections()
		func (t *Transport) RegisterProtocol(scheme string, rt RoundTripper)
		func (t *Transport) RoundTrip(req *Request) (*Response, error)
			* 执行HTTP请求，返回Response

-----------------
func
-----------------
	func CanonicalHeaderKey(s string) string
		* 返回规范化的Header Key
			accept-encoding 规范后 Accept-Encoding
		* 如果s包含一个空格或无效的头字段字节，则是不加修改地返回。

	func DetectContentType(data []byte) string
		* 通过 https://mimesniff.spec.whatwg.org/ 算法，返回data的ContentType类型
		* 最多只会判断data前512个字节，如果不知道是什么类型，则返回	application/octet-stream

	func Error(w ResponseWriter, error string, code int)
		* 异常响应客户端数据，会设置2个header
			"Content-Type", "text/plain; charset=utf-8"
			"X-Content-Type-Options", "nosniff"

	func Handle(pattern string, handler Handler)
	func HandleFunc(pattern string, handler func(ResponseWriter, *Request))
		* 根据路径，设置HTTP处理器，注册到默认的ServeMux (DefaultServeMux)

	func ListenAndServe(addr string, handler Handler) error
	func ListenAndServeTLS(addr, certFile, keyFile string, handler Handler) error
		* 启动一个HTTP服务
		* 如果handler为nil，在这种情况下，使用DefaultServeMux

	func MaxBytesReader(w ResponseWriter, r io.ReadCloser, n int64) io.ReadCloser
		* 类似于io.LimitReader，但目的是为了限制传入请求体的大小。
		* 与io.LimitReader相反，MaxBytesReader的结果是一个ReadCloser，对于超出限制的Read返回一个非EOF错误，并在调用其Close方法时关闭底层reader
		* 可以防止客户端意外或恶意发送大请求，浪费服务器资源。
			router.HandleFunc("/", func(writer http.ResponseWriter, request *http.Request) {
				request.Body = http.MaxBytesReader(writer, request.Body, 10)
				var data, err = io.ReadAll(request.Body)
				if err != nil {
					if err.Error() == "http: request body too large" {  // body过大
						writer.WriteHeader(http.StatusRequestEntityTooLarge)
					} else {
						writer.WriteHeader(http.StatusInternalServerError)
					}
				} else {
					writer.WriteHeader(http.StatusOK)
					writer.Header().Set("Content-Type", "text/plain; charset=ut-8")
					writer.Write(data)
				}
			})

		* 在异常发生后，如果w实现了 `requestTooLarge` 方法，会执行调用


	func NotFound(w ResponseWriter, r *Request)
		* 404 handler

	func ParseHTTPVersion(vers string) (major, minor int, ok bool)
		* 解析http版本
			"HTTP/1.0" => (1, 0, true)" 
		
	func ParseTime(text string) (t time.Time, err error)
		* 解析一个时间头（如Date:头），尝试HTTP/1.1允许的三种格式。TimeFormat、time.RFC850和time.ANSIC。

	func ProxyFromEnvironment(req *Request) (*url.URL, error)
	func ProxyURL(fixedURL *url.URL) func(*Request) (*url.URL, error)
		* 设置代理请求地址

	func Redirect(w ResponseWriter, r *Request, url string, code int)
		* 重定向handler

	func Serve(l net.Listener, handler Handler) error

	func ServeContent(w ResponseWriter, req *Request, name string, modtime time.Time, content io.ReadSeeker)	
		* 它能正确处理Range请求，设置MIME类型，并处理If-Match、If-Unmodified-Since、If-None-Match、If-Modified-Since和If-Range请求。
		* 响应HTTP内容
			name
				* 文件名称，没啥用其实。主要的作用是在没有设置ContentType头的情况下，根据文件名称检索Content-Type
				* 如果根据文件名称解析ContentType失败，那么会默认根据文件的魔术字节判断ContenType
			
			modtime
				* 文件最后修改时间，这个方法会根据这个时间正确的处理HTTP协议中跟缓存相关的Header
			
			content
				* 文件内容，必须是可seek的，这个方法通过seek来获取到文件的大小。并且支持Range请求


	func ServeFile(w ResponseWriter, r *Request, name string)
		* 响应磁盘上的指定文件
	func ServeFileFS(w ResponseWriter, r *Request, fsys fs.FS, name string)
		* 响应指定 fs 下的指定文件

	func ServeTLS(l net.Listener, handler Handler, certFile, keyFile string) error
	func SetCookie(w ResponseWriter, cookie *Cookie)
		* 设置Cookie
	func StatusText(code int) string
		* 根据http状态码返回描述
		* 有时候蛮有用
			http.Error(writer, http.StatusText(http.StatusInternalServerError), http.StatusInternalServerError)

	
