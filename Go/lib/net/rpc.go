-------------
rpc
-------------
	# https://pkg.go.dev/net/rpc
		* rpc 提供了通过网络或其他 I/O 连接访问对象导出方法的功能。


-------------
var
-------------
	var DefaultServer = NewServer()
	var ErrShutdown = errors.New("connection is shut down")


-------------
type
-------------
	type Call struct {
		ServiceMethod string     // The name of the service and method to call.
		Args          any        // The argument to the function (*struct).
		Reply         any        // The reply from the function (*struct).
		Error         error      // After completion, the error status.
		Done          chan *Call // Receives *Call when Go is complete.
	}

	type Client struct {
		// contains filtered or unexported fields
	}


		func Dial(network, address string) (*Client, error)
		func DialHTTP(network, address string) (*Client, error)
		func DialHTTPPath(network, address, path string) (*Client, error)
		func NewClient(conn io.ReadWriteCloser) *Client
		func NewClientWithCodec(codec ClientCodec) *Client
		func (client *Client) Call(serviceMethod string, args any, reply any) error
		func (client *Client) Close() error
		func (client *Client) Go(serviceMethod string, args any, reply any, done chan *Call) *Call


	type ClientCodec interface {
		WriteRequest(*Request, any) error
		ReadResponseHeader(*Response) error
		ReadResponseBody(any) error

		Close() error
	}

	type Request struct {
		ServiceMethod string // format: "Service.Method"
		Seq           uint64 // sequence number chosen by client
		// contains filtered or unexported fields
	}

	type Response struct {
		ServiceMethod string // echoes that of the Request
		Seq           uint64 // echoes that of the request
		Error         string // error, if any.
		// contains filtered or unexported fields
	}

	type Server struct {
		// contains filtered or unexported fields
	}
		func NewServer() *Server
		func (server *Server) Accept(lis net.Listener)
		func (server *Server) HandleHTTP(rpcPath, debugPath string)
		func (server *Server) Register(rcvr any) error
		func (server *Server) RegisterName(name string, rcvr any) error
		func (server *Server) ServeCodec(codec ServerCodec)
		func (server *Server) ServeConn(conn io.ReadWriteCloser)
		func (server *Server) ServeHTTP(w http.ResponseWriter, req *http.Request)
		func (server *Server) ServeRequest(codec ServerCodec) error
	
	type ServerCodec interface {
		ReadRequestHeader(*Request) error
		ReadRequestBody(any) error
		WriteResponse(*Response, any) error

		// Close can be called multiple times and must be idempotent.
		Close() error
	}

	type ServerError string
		func (e ServerError) Error() string

-------------
func
-------------
	func Accept(lis net.Listener)
	func HandleHTTP()
	func Register(rcvr any) error
	func RegisterName(name string, rcvr any) error
	func ServeCodec(codec ServerCodec)
	func ServeConn(conn io.ReadWriteCloser)
	func ServeRequest(codec ServerCodec) error