------------------------
gorilla
------------------------
	# websocket��goʵ��
		https://github.com/gorilla/websocket
	

------------------------
var
------------------------
	const (
		CloseNormalClosure           = 1000
		CloseGoingAway               = 1001
		CloseProtocolError           = 1002
		CloseUnsupportedData         = 1003
		CloseNoStatusReceived        = 1005
		CloseAbnormalClosure         = 1006
		CloseInvalidFramePayloadData = 1007
		ClosePolicyViolation         = 1008
		CloseMessageTooBig           = 1009
		CloseMandatoryExtension      = 1010
		CloseInternalServerErr       = 1011
		CloseServiceRestart          = 1012
		CloseTryAgainLater           = 1013
		CloseTLSHandshake            = 1015
	)

	* websocket ״̬��

	const (
		TextMessage = 1
		BinaryMessage = 2
		CloseMessage = 8
		PingMessage = 9
		PongMessage = 10
	)

	* ��Ϣ����

	var DefaultDialer = &Dialer{
		Proxy:            http.ProxyFromEnvironment,
		HandshakeTimeout: 45 * time.Second,
	}

	var ErrBadHandshake = errors.New("websocket: bad handshake")
	var ErrCloseSent = errors.New("websocket: close sent")
	var ErrReadLimit = errors.New("websocket: read limit exceeded")
			

------------------------
type
------------------------
	# type BufferPool interface {
			Get() interface{}
			Put(interface{})
		}
	
	# type CloseError struct {
			Code int
			Text string
		}
		func (e *CloseError) Error() string

	# type Conn struct {
		}
		func NewClient(netConn net.Conn, u *url.URL, requestHeader http.Header, ...) (c *Conn, response *http.Response, err error)
			* ����һ���µ�websocket����
		
		func Upgrade(w http.ResponseWriter, r *http.Request, responseHeader http.Header, ...) (*Conn, error)
			* ��һ��http��������Ϊwebsocket
			* ��ʱ��Ҫ��

		func (c *Conn) Close() error
			* �����ر�

		func (c *Conn) EnableWriteCompression(enable bool)
			* �Ƿ�����Ӧѹ��
		
		func (c *Conn) LocalAddr() net.Addr
		func (c *Conn) NextReader() (messageType int, r io.Reader, err error)
		func (c *Conn) NextWriter(messageType int) (io.WriteCloser, error)
			* ��ȡ��Ϣ�Ķ�д��

		func (c *Conn) PingHandler() func(appData string) error
		func (c *Conn) PongHandler() func(appData string) error

		func (c *Conn) ReadJSON(v interface{}) error
		func (c *Conn) ReadMessage() (messageType int, p []byte, err error)
			* ��conn�ж�ȡ���ݣ������������ͣ���������

		func (c *Conn) RemoteAddr() net.Addr
		func (c *Conn) SetCloseHandler(h func(code int, text string) error)
			* ���ã��رմ����������Է����ӹرպ����������ִ��
			* ��� h ��nil�Ļ�����ô��ʹ��Ĭ�ϵ�
				func(code int, text string) error {
					message := FormatCloseMessage(code, "")
					c.WriteControl(CloseMessage, message, time.Now().Add(writeWait))
					return nil
				}

		func (c *Conn) CloseHandler() func(code int, text string) error
			
		func (c *Conn) SetCompressionLevel(level int) error
			* ����ѹ������

		func (c *Conn) SetPingHandler(h func(appData string) error)
		func (c *Conn) SetPongHandler(h func(appData string) error)
			* ����Handler����
		
		func (c *Conn) SetReadDeadline(t time.Time) error
		func (c *Conn) SetReadLimit(limit int64)
			* ��������ȡ��Ϣ��С��������������С����ô���ӵĶ�ȡ�����᷵�� ErrReadLimit
			* ���ҹرտͻ��˵�����

		func (c *Conn) SetWriteDeadline(t time.Time) error

		func (c *Conn) Subprotocol() string
		func (c *Conn) UnderlyingConn() net.Conn

		func (c *Conn) WriteControl(messageType int, data []byte, deadline time.Time) error
			* д�������Ϣ��close��ping��pong

		func (c *Conn) WriteJSON(v interface{}) error
		func (c *Conn) WriteMessage(messageType int, data []byte) error
			* д������
		
		func (c *Conn) WritePreparedMessage(pm *PreparedMessage) error
			* д��Ԥ������Ϣ
	
	# type Dialer struct {
			NetDial func(network, addr string) (net.Conn, error)
				* ָ����ַ
			NetDialContext func(ctx context.Context, network, addr string) (net.Conn, error)
				* ָ����ַ�Լ�context
			Proxy func(*http.Request) (*url.URL, error)
				* ���ô���
			TLSClientConfig *tls.Config
				* ssl����
			HandshakeTimeout time.Duration
				* ��ʱʱ��
			ReadBufferSize, WriteBufferSize int
				* ��������С
			WriteBufferPool BufferPool
				* buffer��
			Subprotocols []string
				* ��Э��
			EnableCompression bool
				* �Ƿ���ѹ��
			Jar http.CookieJar
				* CookieJar����
		}
		
		* �ͻ��˹���
		func (d *Dialer) Dial(urlStr string, requestHeader http.Header) (*Conn, *http.Response, error)
		func (d *Dialer) DialContext(ctx context.Context, urlStr string, requestHeader http.Header) (*Conn, *http.Response, error)
	
	# type HandshakeError struct {
		}
		func (e HandshakeError) Error() string

		* �����쳣
	
	# type PreparedMessage struct {
		}
		func NewPreparedMessage(messageType int, data []byte) (*PreparedMessage, error)

		* Ԥ������Ϣ��ʹ��PreparedMessage������Ч�ؽ���Ϣ��Ч���ط��͵�������ӡ�
		* ʹ��ѹ��ʱ��PreparedMessage�ر����ã���Ϊ����һ�������ѹ��ѡ�CPU���ڴ氺���ѹ����������ִ��һ�Ρ�

	
	# type Upgrader struct {
			HandshakeTimeout time.Duration
				* ���ֳ�ʱʱ��
			ReadBufferSize, WriteBufferSize int
				* �����С
				* ���99������ϢС��256�ֽڣ����������Ϣ��СΪ512�ֽڣ���256�ֽڵĻ�������С�����±�512�ֽڵĻ�������С��1.01��ϵͳ���á��ڴ��ʡΪ50����

			WriteBufferPool BufferPool
				* дbufferpoll
			Subprotocols []string
				* ��Э��
			Error func(w http.ResponseWriter, r *http.Request, status int, reason error)
				* �쳣����
			CheckOrigin func(r *http.Request) bool
				* orgin���
				* ���CheckOrigin��������false����Upgrade����ʹWebSocket����ʧ�ܣ�HTTP״̬Ϊ403��
				* ���CheckOrigin�ֶ�Ϊnil������������ʹ�ð�ȫĬ��ֵ��
					�������Origin����ͷ��Origin����������Host requestͷ��������ʧ�ܡ�

			EnableCompression bool
				* �Ƿ���ѹ������������ӵĶԵȷ��ɹ�Э����ѹ��������ѹ����ʽ�յ����κ���Ϣ�����Զ���ѹ��
		}
		func (u *Upgrader) Upgrade(w http.ResponseWriter, r *http.Request, responseHeader http.Header) (*Conn, error)
		
		* ws���ִ�������һ�����ڷ����
			var upgrader = websocket.Upgrader{
				ReadBufferSize:  1024,
				WriteBufferSize: 1024,
			}

			func handler(w http.ResponseWriter, r *http.Request) {
				conn, err := upgrader.Upgrade(w, r, nil)
				if err != nil {
					log.Println(err)
					return
				}
				// ws����ok
			}
			


------------------------
func
------------------------
	func FormatCloseMessage(closeCode int, text string) []byte

	func IsCloseError(err error, codes ...int) bool
		* err ��CloseError �쳣��������ָ�����쳣֮һ

	func IsUnexpectedCloseError(err error, expectedCodes ...int) bool
		* err ��CloseError �쳣�����Ҳ���ָ���������쳣֮һ

	func IsWebSocketUpgrade(r *http.Request) bool
		* �ж��Ƿ���websocket��������

	func JoinMessages(c *Conn, term string) io.Reader
	func ReadJSON(c *Conn, v interface{}) error
	func Subprotocols(r *http.Request) []string
	func WriteJSON(c *Conn, v interface{}) error


------------------------
demos
------------------------

	# �ͻ���
		package main

		import (
			"fmt"
			"github.com/gorilla/websocket"
		)

		func main() {
			dialer := websocket.Dialer{}
			con, _, err := dialer.Dial("ws://127.0.0.1:80/channel/logger", nil)
			fmt.Println(err)

			con.SetCloseHandler(func(code int, text string) error {
				fmt.Println("�������ر�����", code, text)
				return nil
			})

			for {
				t, bytes, err := con.ReadMessage()
				if err != nil{
					// ��ȡ�쳣�������Ƿ������Ͽ�������
					fmt.Println("err", err)
					break
				}
				if t == websocket.TextMessage {
					// �ı���Ϣ
					fmt.Print(string(bytes))
				} else {
					// ������Ϣ��֧��
					con.Close()
				}
			}
		}
