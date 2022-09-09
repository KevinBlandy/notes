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
			Director func(*http.Request)
				* 这个方法会把当前请求修改成代理请求

			Transport http.RoundTripper
				* HTTP执行器

			FlushInterval time.Duration
			ErrorLog *log.Logger
				* 异常日志处理

			BufferPool BufferPool

			ModifyResponse func(*http.Response) error
				* 可选的，用于修改响应数据

			ErrorHandler func(http.ResponseWriter, *http.Request, error)
				* 异常处理
		}
		
		* 简单而又强大的代理服务器

		func NewSingleHostReverseProxy(target *url.URL) *ReverseProxy
			* 创建新的代理服务器，指定要代理的URL地址

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
			"log"
			"net/http"
			"net/http/httputil"
			"net/url"
			"os"
		)

		func init() {
			log.Default().SetOutput(os.Stdout)
			log.Default().SetFlags(log.Ldate | log.Ltime | log.Lshortfile)
		}

		func main() {
			// 代理的地址
			targetURL, err := url.Parse("https://start.spring.io/")
			if err != nil {
				log.Fatalf(err.Error())
			}
			reverseProxy := httputil.NewSingleHostReverseProxy(targetURL)

			// 修改请求
			director := reverseProxy.Director
			reverseProxy.Director = func(request *http.Request) {
				director(request)
			}

			// 异常处理
			reverseProxy.ErrorHandler = func(writer http.ResponseWriter, request *http.Request, err error) {
				log.Printf("异常: %s\n", err.Error())
			}
			// 修改响应
			reverseProxy.ModifyResponse = func(response *http.Response) error {
				return nil
			}

			server := &http.Server{
				Addr: ":8080",
				Handler: http.HandlerFunc(func(writer http.ResponseWriter, request *http.Request) {
					// 执行代理请求
					// 考虑重新设置HOST字段为目标地址的host，不然可能导致403（有些服务器会校验这个header）
					request.Host = "start.spring.io"
					reverseProxy.ServeHTTP(writer, request)
				}),
			}

			server.ListenAndServe()
		}
