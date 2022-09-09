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
			Director func(*http.Request)
				* ���������ѵ�ǰ�����޸ĳɴ�������

			Transport http.RoundTripper
				* HTTPִ����

			FlushInterval time.Duration
			ErrorLog *log.Logger
				* �쳣��־����

			BufferPool BufferPool

			ModifyResponse func(*http.Response) error
				* ��ѡ�ģ������޸���Ӧ����

			ErrorHandler func(http.ResponseWriter, *http.Request, error)
				* �쳣����
		}
		
		* �򵥶���ǿ��Ĵ��������

		func NewSingleHostReverseProxy(target *url.URL) *ReverseProxy
			* �����µĴ����������ָ��Ҫ�����URL��ַ

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
			// ����ĵ�ַ
			targetURL, err := url.Parse("https://start.spring.io/")
			if err != nil {
				log.Fatalf(err.Error())
			}
			reverseProxy := httputil.NewSingleHostReverseProxy(targetURL)

			// �޸�����
			director := reverseProxy.Director
			reverseProxy.Director = func(request *http.Request) {
				director(request)
			}

			// �쳣����
			reverseProxy.ErrorHandler = func(writer http.ResponseWriter, request *http.Request, err error) {
				log.Printf("�쳣: %s\n", err.Error())
			}
			// �޸���Ӧ
			reverseProxy.ModifyResponse = func(response *http.Response) error {
				return nil
			}

			server := &http.Server{
				Addr: ":8080",
				Handler: http.HandlerFunc(func(writer http.ResponseWriter, request *http.Request) {
					// ִ�д�������
					// ������������HOST�ֶ�ΪĿ���ַ��host����Ȼ���ܵ���403����Щ��������У�����header��
					request.Host = "start.spring.io"
					reverseProxy.ServeHTTP(writer, request)
				}),
			}

			server.ListenAndServe()
		}
