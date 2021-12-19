------------------------
gorilla
------------------------
	# websocket的go实现
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

	* websocket 状态码

	const (
		TextMessage = 1
		BinaryMessage = 2
		CloseMessage = 8
		PingMessage = 9
		PongMessage = 10
	)

	* 消息类型

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
			* 创建一个新的websocket链接
		
		func Upgrade(w http.ResponseWriter, r *http.Request, responseHeader http.Header, ...) (*Conn, error)
			* 对一个http请求，升级为websocket
			* 过时不要用

		func (c *Conn) Close() error
			* 正常关闭

		func (c *Conn) EnableWriteCompression(enable bool)
			* 是否开启响应压缩
		
		func (c *Conn) LocalAddr() net.Addr
		func (c *Conn) NextReader() (messageType int, r io.Reader, err error)
		func (c *Conn) NextWriter(messageType int) (io.WriteCloser, error)
			* 获取消息的读写流

		func (c *Conn) PingHandler() func(appData string) error
		func (c *Conn) PongHandler() func(appData string) error

		func (c *Conn) ReadJSON(v interface{}) error
		func (c *Conn) ReadMessage() (messageType int, p []byte, err error)
			* 从conn中读取数据，返回数据类型，还有数据

		func (c *Conn) RemoteAddr() net.Addr
		func (c *Conn) SetCloseHandler(h func(code int, text string) error)
			* 设置，关闭处理器，当对方连接关闭后。这个方法会执行
			* 如果 h 是nil的话，那么会使用默认的
				func(code int, text string) error {
					message := FormatCloseMessage(code, "")
					c.WriteControl(CloseMessage, message, time.Now().Add(writeWait))
					return nil
				}

		func (c *Conn) CloseHandler() func(code int, text string) error
			
		func (c *Conn) SetCompressionLevel(level int) error
			* 设置压缩级别

		func (c *Conn) SetPingHandler(h func(appData string) error)
		func (c *Conn) SetPongHandler(h func(appData string) error)
			* 心跳Handler设置
		
		func (c *Conn) SetReadDeadline(t time.Time) error
		func (c *Conn) SetReadLimit(limit int64)
			* 设置最大读取消息大小，如果超过这个大小，那么连接的读取方法会返回 ErrReadLimit
			* 并且关闭客户端的连接

		func (c *Conn) SetWriteDeadline(t time.Time) error

		func (c *Conn) Subprotocol() string
		func (c *Conn) UnderlyingConn() net.Conn

		func (c *Conn) WriteControl(messageType int, data []byte, deadline time.Time) error
			* 写入控制消息，close，ping和pong

		func (c *Conn) WriteJSON(v interface{}) error
		func (c *Conn) WriteMessage(messageType int, data []byte) error
			* 写入数据
		
		func (c *Conn) WritePreparedMessage(pm *PreparedMessage) error
			* 写入预处理消息
	
	# type Dialer struct {
			NetDial func(network, addr string) (net.Conn, error)
				* 指定地址
			NetDialContext func(ctx context.Context, network, addr string) (net.Conn, error)
				* 指定地址以及context
			Proxy func(*http.Request) (*url.URL, error)
				* 设置代理
			TLSClientConfig *tls.Config
				* ssl配置
			HandshakeTimeout time.Duration
				* 超时时间
			ReadBufferSize, WriteBufferSize int
				* 缓冲区大小
			WriteBufferPool BufferPool
				* buffer池
			Subprotocols []string
				* 子协议
			EnableCompression bool
				* 是否开起压缩
			Jar http.CookieJar
				* CookieJar管理
		}
		
		* 客户端构建
		func (d *Dialer) Dial(urlStr string, requestHeader http.Header) (*Conn, *http.Response, error)
		func (d *Dialer) DialContext(ctx context.Context, urlStr string, requestHeader http.Header) (*Conn, *http.Response, error)
	
	# type HandshakeError struct {
		}
		func (e HandshakeError) Error() string

		* 握手异常
	
	# type PreparedMessage struct {
		}
		func NewPreparedMessage(messageType int, data []byte) (*PreparedMessage, error)

		* 预处理消息，使用PreparedMessage可以有效地将消息有效负载发送到多个连接。
		* 使用压缩时，PreparedMessage特别有用，因为对于一组给定的压缩选项，CPU和内存昂贵的压缩操作可以执行一次。

	
	# type Upgrader struct {
			HandshakeTimeout time.Duration
				* 握手超时时间
			ReadBufferSize, WriteBufferSize int
				* 缓存大小
				* 如果99％的消息小于256字节，并且最大消息大小为512字节，则256字节的缓冲区大小将导致比512字节的缓冲区大小多1.01个系统调用。内存节省为50％。

			WriteBufferPool BufferPool
				* 写bufferpoll
			Subprotocols []string
				* 子协议
			Error func(w http.ResponseWriter, r *http.Request, status int, reason error)
				* 异常处理
			CheckOrigin func(r *http.Request) bool
				* orgin检查
				* 如果CheckOrigin函数返回false，则Upgrade方法使WebSocket握手失败，HTTP状态为403。
				* 如果CheckOrigin字段为nil，则升级程序使用安全默认值：
					如果存在Origin请求头且Origin主机不等于Host request头，则握手失败。

			EnableCompression bool
				* 是否开启压缩，如果与连接的对等方成功协商了压缩，则以压缩形式收到的任何消息都将自动解压缩
		}
		func (u *Upgrader) Upgrade(w http.ResponseWriter, r *http.Request, responseHeader http.Header) (*Conn, error)
		
		* ws握手处理器，一般用于服务端
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
				// ws握手ok
			}
			


------------------------
func
------------------------
	func FormatCloseMessage(closeCode int, text string) []byte

	func IsCloseError(err error, codes ...int) bool
		* err 是CloseError 异常，并且是指定的异常之一

	func IsUnexpectedCloseError(err error, expectedCodes ...int) bool
		* err 是CloseError 异常，并且不是指定的任意异常之一

	func IsWebSocketUpgrade(r *http.Request) bool
		* 判断是否是websocket握手请求

	func JoinMessages(c *Conn, term string) io.Reader
	func ReadJSON(c *Conn, v interface{}) error
	func Subprotocols(r *http.Request) []string
	func WriteJSON(c *Conn, v interface{}) error


------------------------
demos
------------------------

	# 客户端
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
				fmt.Println("服务器关闭链接", code, text)
				return nil
			})

			for {
				t, bytes, err := con.ReadMessage()
				if err != nil{
					// 读取异常，可能是服务器断开了链接
					fmt.Println("err", err)
					break
				}
				if t == websocket.TextMessage {
					// 文本消息
					fmt.Print(string(bytes))
				} else {
					// 其他消息不支持
					con.Close()
				}
			}
		}
