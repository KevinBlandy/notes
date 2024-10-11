---------------------
httputil
---------------------
	# http��صĹ�����

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
				* In �Ǵ�����յ���������д���������޸� In��
			Out *http.Request
				* Out �Ǵ������͵�����Rewrite ���������޸Ļ��滻�������ڵ��� Rewrite ֮ǰ����Ӹ�������ɾ�� ������ת�����ء� ��Ϣ��
		}
		func (r *ProxyRequest) SetURL(target *url.URL)
			* ����Ҫ����������ַ
			* ��ʹ�� target �е����ݣ���д outReq
			* ���Ұ� out �� Host �ֶ�����Ϊ ""������д��վ Host ͷ��Ϣ��ʹ����Ŀ��������ƥ�䡣

		func (r *ProxyRequest) SetXForwarded()
			* ���ô���ͷ
	
	# type ClientConn struct {
		}
		
		* �ѹ���

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
				* ȡ���� Director ����
				* ʾ��
					proxyHandler := &httputil.ReverseProxy{ 
					  Rewrite: func(r *httputil.ProxyRequest) { 
						r.SetURL(outboundURL) // ������ת���� outboundURL��
						r.SetXForwarded() // ���� X-Forwarded-* ��ͷ��
						r.Out.Header.Set("X-Additional-Header", "�������õ�ͷ��") 
					  }, 
					}
				
			Director func(*http.Request)
				* ���������ѵ�ǰ�����޸ĳɴ�������
				* Rewrite �� Director ֻ������һ��

			Transport http.RoundTripper
				* HTTPִ����

			FlushInterval time.Duration

			ErrorLog *log.Logger
				* �쳣 logger

			BufferPool BufferPool

			ModifyResponse func(*http.Response) error
				* ��ѡ�ģ������޸���Ӧ����

			ErrorHandler func(http.ResponseWriter, *http.Request, error)
				* �쳣�������ڴ�������˷���Ĵ�������� ModifyResponse �Ĵ���
				* ���Ϊ�գ�Ĭ������»��� logger ������ṩ�Ĵ��󲢷��� 502 Status Bad Gateway ��Ӧ��
		}
		
		* �򵥶���ǿ��Ĵ��������

		func NewSingleHostReverseProxy(target *url.URL) *ReverseProxy
			* �����µĴ����������ָ��Ҫ�����URL��ַ
			* �ô������� target ���ṩ�� schema��host �� base path ·�� URL�����Ŀ��·���� ��/base���������������� ��/dir������Ŀ�������� /base/dir��
			* NewSingleHostReverseProxy ������д Host ͷ��Ϣ��

		func (p *ReverseProxy) ServeHTTP(rw http.ResponseWriter, req *http.Request)
			* ִ�д�������
	
	# type ServerConn struct {
		}
		
		* �ѹ���

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
	# һ�����������

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
					// ������������Ҫ����� URL
					request.SetURL(targetUrl)
					// ���� Out Req �� Host Ϊ In Req �� Host
					request.Out.Host = request.In.Host
					// ���ô��� Header
					request.SetXForwarded()
				},
				// �쳣������
				ErrorHandler: func(writer http.ResponseWriter, request *http.Request, err error) {
					slog.Error("Service Error", slog.String("err", err.Error()))
				},
				// �쳣��־���
				ErrorLog: slog.NewLogLogger(slog.Default().Handler(), slog.LevelDebug),
			}
			proxy.ServeHTTP(httptest.NewRecorder(), httptest.NewRequest("GET", "/", nil))
		}
