--------------------------
server
--------------------------
	# HTTP服务器
		type Server struct {
			Addr string
			Handler Handler
				* 指定处理器，如果为空，则使用 http.DefaultServeMux

			TLSConfig *tls.Config
			ReadTimeout time.Duration
			ReadHeaderTimeout time.Duration
			WriteTimeout time.Duration
			IdleTimeout time.Duration
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
		func (srv *Server) Serve(l net.Listener) error
		func (srv *Server) ServeTLS(l net.Listener, certFile, keyFile string) error
			* 开始服务
			* 如果是正常的 Shutdown() 停止服务，则返回 ErrServerClosed 异常信息

		func (srv *Server) SetKeepAlivesEnabled(v bool)
		func (srv *Server) Shutdown(ctx context.Context) error
			* 关闭服务器，Servexx 等方法会返回 ErrServerClosed 异常信息

	# 一些快速启动服务器的方法
		func ListenAndServe(addr string, handler Handler) error
		func ListenAndServeTLS(addr, certFile, keyFile string, handler Handler) error
			* 指定handler接口，启动一个http/https服务
			* 如果handler为nil，在这种情况下，使用 DefaultServeMux
				server := &Server{Addr: addr, Handler: handler}
				return server.ListenAndServe()
		
		func Serve(l net.Listener, handler Handler) error
		func ServeContent(w ResponseWriter, req *Request, name string, modtime time.Time, ...)
		func ServeFile(w ResponseWriter, r *Request, name string)
		func ServeTLS(l net.Listener, handler Handler, certFile, keyFile string) error

	
	# HTTP请求复用器
		type ServeMux struct {
		}
		
		* 一个HTTP请求复用器。
		* 它将每个传入请求的URL与注册模式列表进行匹配，并调用与URL最匹配的模式的处理程序

		func NewServeMux() *ServeMux
		func (mux *ServeMux) Handle(pattern string, handler Handler)
		func (mux *ServeMux) HandleFunc(pattern string, handler func(ResponseWriter, *Request))
			* 根据pattern设置http处理器

		func (mux *ServeMux) Handler(r *Request) (h Handler, pattern string)
			* 根据指定请求，返回用于处理该请求的handler以及pattern
			* 这个方法就是路由算法的实现

		func (mux *ServeMux) ServeHTTP(w ResponseWriter, r *Request)
			* 根据Handller返回的处理器，执行处理

		
		* 路径的映射规则
			* 如果被绑定的URL非/结尾，那么它只会与完全相同的URL匹配
			* 如果被绑定的URL以/结尾，那么即使请求的URL只有前缀部分与被绑定URL相同，ServeMux也会认定这两个URL是匹配的。
		
	# Http处理器接口
		type Handler interface {
			ServeHTTP(ResponseWriter, *Request)
		}

		func FileServer(root FileSystem) Handler
			* 返回一个文件HTTP服务

		func NotFoundHandler() Handler
			* 返回一个404服务

		func RedirectHandler(url string, code int) Handler
			* 返回一个重定向服务

		func StripPrefix(prefix string, h Handler) Handler
		func TimeoutHandler(h Handler, dt time.Duration, msg string) Handler
	
	# Http处理方法, 实现了 处理器接口
		type HandlerFunc func(ResponseWriter, *Request)

		func (f HandlerFunc) ServeHTTP(w ResponseWriter, r *Request)
	
	# Header
		type Header map[string][]string

		func (h Header) Add(key, value string)
		func (h Header) Clone() Header
		func (h Header) Del(key string)
		func (h Header) Get(key string) string
		func (h Header) Set(key, value string)
		func (h Header) Values(key string) []string
		func (h Header) Write(w io.Writer) error
		func (h Header) WriteSubset(w io.Writer, exclude map[string]bool) error
	

	# Cookie
		type Cookie struct {
			Name  string
			Value string
			Path       string    // optional
			Domain     string    // optional
			Expires    time.Time // optional
			RawExpires string    // for reading cookies only
			MaxAge   int
			Secure   bool
			HttpOnly bool
			SameSite SameSite
				* 同域策略
					type SameSite int
					const (
						SameSiteDefaultMode SameSite = iota + 1
						SameSiteLaxMode
						SameSiteStrictMode
						SameSiteNoneMode
					)
			
			Raw      string
			Unparsed []string // Raw text of unparsed attribute-value pairs
		}
		func (c *Cookie) String() string
	
		* Cookie管理
			type CookieJar interface {
				SetCookies(u *url.URL, cookies []*Cookie)
				Cookies(u *url.URL) []*Cookie
			}

			* 实现必须是安全的，可以被多个goroutine同时使用
			* 系统提供的实现
				cookiejar.Jar
		
		* 往客户端写入cookie，通过 http.SetCookie 完成
			func SetCookie(w ResponseWriter, cookie *Cookie)
			// w.Header().Add("Set-Cookie", v)

		
	
	# 快速启动一个 HTTP 服务器

		package main

		import (
			"context"
			"errors"
			"log/slog"
			"net/http"
			"os"
			"os/signal"
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

			router := http.NewServeMux()

			server := http.Server{
				Addr:    ":8080",
				Handler: router,
			}

			serverClosed := make(chan struct{})

			go func() {
				ctx, cancel := signal.NotifyContext(context.Background(), os.Kill, os.Interrupt)
				defer cancel()

				<-ctx.Done()

				func() {
					ctx, cancel := context.WithTimeout(context.Background(), time.Second*5)
					defer cancel()

					slog.Info("Server Shutdown...")

					if err := server.Shutdown(ctx); err != nil {
						slog.Error("Server Shutdown Error", slog.String("err", err.Error()))
					}
				}()

				// 暂停 1s
				time.Sleep(time.Second)

				close(serverClosed)
			}()

			slog.Info("Server Start...")

			if err := server.ListenAndServe(); err != nil && !errors.Is(err, http.ErrServerClosed) {
				slog.Error("Server Start Error", slog.String("err", err.Error()))
				return
			}

			<-serverClosed

			slog.Info("Bye")
		}
