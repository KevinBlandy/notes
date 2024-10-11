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

	# type ProxyRequest struct {
			In *http.Request
				* In 是代理接收到的请求。重写函数不得修改 In。
			Out *http.Request
				* Out 是代理将发送的请求。Rewrite 函数可以修改或替换该请求。在调用 Rewrite 之前，会从该请求中删除 “逐级跳转的网关” 信息。
		}
		func (r *ProxyRequest) SetURL(target *url.URL)
			* 设置要代理的请求地址
			* 会使用 target 中的数据，重写 outReq
			* 并且把 out 的 Host 字段设置为 ""，即重写出站 Host 头信息，使其与目标主机相匹配。

		func (r *ProxyRequest) SetXForwarded()
			* 设置代理头
	
	# type ClientConn struct {
		}
		
		* 已过期

		func NewClientConn(c net.Conn, r *bufio.Reader) *ClientConn
		func NewProxyClientConn(c net.Conn, r *bufio.Reader) *ClientConn
		func (cc *ClientConn) Close() error
		func (cc *ClientConn) Do(req *http.Request) (*http.Response, error)
		func (cc *ClientConn) Hijack() (c net.Conn, r *bufio.Reader)
		func (cc *ClientConn) Pending() int
		func (cc *ClientConn) Read(req *http.Request) (resp *http.Response, err error)
		func (cc *ClientConn) Write(req *http.Request) error
	
	# type ReverseProxy struct {
			Rewrite func(*ProxyRequest)
				* 取代了 Director 钩子
				* 示例
					proxyHandler := &httputil.ReverseProxy{ 
					  Rewrite: func(r *httputil.ProxyRequest) { 
						r.SetURL(outboundURL) // 将请求转发到 outboundURL。
						r.SetXForwarded() // 设置 X-Forwarded-* 标头。
						r.Out.Header.Set("X-Additional-Header", "代理设置的头部") 
					  }, 
					}
				
			Director func(*http.Request)
				* 这个方法会把当前请求修改成代理请求
				* Rewrite 和 Director 只能设置一个

			Transport http.RoundTripper
				* HTTP执行器

			FlushInterval time.Duration

			ErrorLog *log.Logger
				* 异常 logger

			BufferPool BufferPool

			ModifyResponse func(*http.Response) error
				* 可选的，用于修改响应数据

			ErrorHandler func(http.ResponseWriter, *http.Request, error)
				* 异常处理，用于处理代理后端服务的错误或来自 ModifyResponse 的错误。
				* 如果为空，默认情况下会在 logger 输出所提供的错误并返回 502 Status Bad Gateway 响应。
		}
		
		* 简单而又强大的代理服务器

		func NewSingleHostReverseProxy(target *url.URL) *ReverseProxy
			* 创建新的代理服务器，指定要代理的URL地址
			* 该代理会根据 target 中提供的 schema、host 和 base path 路由 URL。如果目标路径是 “/base”，而传入请求是 “/dir”，则目标请求将是 /base/dir。
			* NewSingleHostReverseProxy 不会重写 Host 头信息。

		func (p *ReverseProxy) ServeHTTP(rw http.ResponseWriter, req *http.Request)
			* 执行代理请求
	
	# type ServerConn struct {
		}
		
		* 已过期

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



---------------------
Demo
---------------------
	# 一个代理服务器

		package main

		import (
			"log/slog"
			"net/http"
			"net/http/httptest"
			"net/http/httputil"
			"net/url"
			"os"
			"time"
		)

		func init() {
			slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
				AddSource: true,
				Level:     slog.LevelDebug,
				ReplaceAttr: func(groups []string, a slog.Attr) slog.Attr {
					if a.Value.Kind() == slog.KindTime {
						return slog.String(a.Key, a.Value.Time().Format(time.DateTime))
					}
					return a
				},
			})))
		}

		func main() {
			targetUrl, err := url.Parse("http://localhost:8080/")
			if err != nil {
				panic(err.Error())
			}
			proxy := httputil.ReverseProxy{
				Rewrite: func(request *httputil.ProxyRequest) {
					// 根据请求设置要代理的 URL
					request.SetURL(targetUrl)
					// 重置 Out Req 的 Host 为 In Req 的 Host
					request.Out.Host = request.In.Host
					// 设置代理 Header
					request.SetXForwarded()
				},
				// 异常处理器
				ErrorHandler: func(writer http.ResponseWriter, request *http.Request, err error) {
					slog.Error("Service Error", slog.String("err", err.Error()))
				},
				// 异常日志输出
				ErrorLog: slog.NewLogLogger(slog.Default().Handler(), slog.LevelDebug),
			}
			proxy.ServeHTTP(httptest.NewRecorder(), httptest.NewRequest("GET", "/", nil))
		}
