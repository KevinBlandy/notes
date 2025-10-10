------------------
GRPC
------------------
	# Golang 的 GRPC 实现

		https://pkg.go.dev/google.golang.org/grpc


------------------
var
------------------

	const (
		SupportPackageIsVersion3 = true
		SupportPackageIsVersion4 = true
		SupportPackageIsVersion5 = true
		SupportPackageIsVersion6 = true
		SupportPackageIsVersion7 = true
		SupportPackageIsVersion8 = true
		SupportPackageIsVersion9 = true
	)

	const Version = "1.72.2"
	
	var (
		// ErrClientConnClosing indicates that the operation is illegal because
		// the ClientConn is closing.
		//
		// Deprecated: this error should not be relied upon by users; use the status
		// code of Canceled instead.
		ErrClientConnClosing = status.Error(codes.Canceled, "grpc: the client connection is closing")

		// PickFirstBalancerName is the name of the pick_first balancer.
		PickFirstBalancerName = pickfirst.Name
	)

	var DefaultBackoffConfig = BackoffConfig{
		MaxDelay: 120 * time.Second,
	}

	var EnableTracing bool

	var ErrClientConnTimeout = errors.New("grpc: timed out when dialing")

	var ErrServerStopped = errors.New("grpc: the server has been stopped")

------------------
type
------------------
	# type AuthorityOverrideCallOption struct {
			Authority string
		}

	# type BackoffConfig struct {
			// 返回延迟的上限。
			MaxDelay time.Duration
		}

	# type BidiStreamingClient[Req any, Res any] interface {
			Send(*Req) error
			Recv() (*Res, error)
			ClientStream
		}

	# type BidiStreamingServer[Req any, Res any] interface {
			Recv() (*Req, error)
			Send(*Res) error
			ServerStream
		}

	# type CallOption interface {
		}

		func CallAuthority(authority string) CallOption
		func CallContentSubtype(contentSubtype string) CallOption
		func CallCustomCodec(codec Codec) CallOption
		func FailFast(failFast bool) CallOption
		func ForceCodec(codec encoding.Codec) CallOption
		func ForceCodecV2(codec encoding.CodecV2) CallOption
		func Header(md *metadata.MD) CallOption
		func MaxCallRecvMsgSize(bytes int) CallOption
		func MaxCallSendMsgSize(bytes int) CallOption
		func MaxRetryRPCBufferSize(bytes int) CallOption
		func OnFinish(onFinish func(err error)) CallOption
		func Peer(p *peer.Peer) CallOption
		func PerRPCCredentials(creds credentials.PerRPCCredentials) CallOption
		func StaticMethod() CallOption
		func Trailer(md *metadata.MD) CallOption
		func UseCompressor(name string) CallOption
		func WaitForReady(waitForReady bool) CallOption

	# type ClientConn struct {
			// contains filtered or unexported fields
		}

		func Dial(target string, opts ...DialOption) (*ClientConn, error)
		func DialContext(ctx context.Context, target string, opts ...DialOption) (conn *ClientConn, err error)
		func NewClient(target string, opts ...DialOption) (conn *ClientConn, err error)

		func (cc *ClientConn) CanonicalTarget() string
		func (cc *ClientConn) Close() error
		func (cc *ClientConn) Connect()
		func (cc *ClientConn) GetMethodConfig(method string) MethodConfig
		func (cc *ClientConn) GetState() connectivity.State
		func (cc *ClientConn) Invoke(ctx context.Context, method string, args, reply any, opts ...CallOption) error
		func (cc *ClientConn) NewStream(ctx context.Context, desc *StreamDesc, method string, opts ...CallOption) (ClientStream, error)
		func (cc *ClientConn) ResetConnectBackoff()
		func (cc *ClientConn) Target() string
		func (cc *ClientConn) WaitForStateChange(ctx context.Context, sourceState connectivity.State) bool

	# type ClientConnInterface interface {
			Invoke(ctx context.Context, method string, args any, reply any, opts ...CallOption) error
			NewStream(ctx context.Context, desc *StreamDesc, method string, opts ...CallOption) (ClientStream, error)
		}


	# type ClientStream interface {
			Header() (metadata.MD, error)
			Trailer() metadata.MD
			CloseSend() error
			Context() context.Context
			SendMsg(m any) error
			RecvMsg(m any) error
		}

		func NewClientStream(ctx context.Context, desc *StreamDesc, cc *ClientConn, method string, ...) (ClientStream, error)
	

	# type ClientStreamingClient[Req any, Res any] interface {
			Send(*Req) error
			CloseAndRecv() (*Res, error)
			ClientStream
		}

	
	# type ClientStreamingServer[Req any, Res any] interface {
			Recv() (*Req, error)
			SendAndClose(*Res) error
			ServerStream
		}

	# type Codec interface {  // 过期
			Marshal(v any) ([]byte, error)
			Unmarshal(data []byte, v any) error
			String() string
		}

	# type Compressor interface { // 过期
			Do(w io.Writer, p []byte) error
			Type() string
		}

		func NewGZIPCompressor() Compressor
		func NewGZIPCompressorWithLevel(level int) (Compressor, error)


	# type CompressorCallOption struct {
			CompressorType string
		}

	# type ConnectParams struct {
			Backoff backoff.Config
			MinConnectTimeout time.Duration
		}

	# type ContentSubtypeCallOption struct {
			ContentSubtype string
		}

	# type CustomCodecCallOption struct {
			Codec Codec
		}

	type Decompressor

	# type Decompressor interface {  // 过期
			Do(r io.Reader) ([]byte, error)
			Type() string
		}

		func NewGZIPDecompressor() Decompressor
	
	# type DialOption interface {
		}

		// 请求配置

		func FailOnNonTempDialError(f bool) DialOption
		func WithAuthority(a string) DialOption
		func WithBackoffConfig(b BackoffConfig) DialOption
		func WithBackoffMaxDelay(md time.Duration) DialOption
		func WithBlock() DialOption
		func WithChainStreamInterceptor(interceptors ...StreamClientInterceptor) DialOption
		func WithChainUnaryInterceptor(interceptors ...UnaryClientInterceptor) DialOption
		func WithChannelzParentID(c channelz.Identifier) DialOption
		func WithCodec(c Codec) DialOption
		func WithCompressor(cp Compressor) DialOption
		func WithConnectParams(p ConnectParams) DialOption
		func WithContextDialer(f func(context.Context, string) (net.Conn, error)) DialOption
		func WithCredentialsBundle(b credentials.Bundle) DialOption
		func WithDecompressor(dc Decompressor) DialOption
		func WithDefaultCallOptions(cos ...CallOption) DialOption
		func WithDefaultServiceConfig(s string) DialOption
		func WithDialer(f func(string, time.Duration) (net.Conn, error)) DialOption
		func WithDisableHealthCheck() DialOption
		func WithDisableRetry() DialOption
		func WithDisableServiceConfig() DialOption
		func WithIdleTimeout(d time.Duration) DialOption
		func WithInitialConnWindowSize(s int32) DialOption
		func WithInitialWindowSize(s int32) DialOption
		func WithInsecure() DialOption
		func WithKeepaliveParams(kp keepalive.ClientParameters) DialOption
		func WithLocalDNSResolution() DialOption
		func WithMaxCallAttempts(n int) DialOption
		func WithMaxHeaderListSize(s uint32) DialOption
		func WithMaxMsgSize(s int) DialOption
		func WithNoProxy() DialOption
		func WithPerRPCCredentials(creds credentials.PerRPCCredentials) DialOption
		func WithReadBufferSize(s int) DialOption
		func WithResolvers(rs ...resolver.Builder) DialOption
		func WithReturnConnectionError() DialOption
		func WithSharedWriteBuffer(val bool) DialOption
		func WithStatsHandler(h stats.Handler) DialOption
		func WithStreamInterceptor(f StreamClientInterceptor) DialOption
		func WithTimeout(d time.Duration) DialOption
		func WithTransportCredentials(creds credentials.TransportCredentials) DialOption
		func WithUnaryInterceptor(f UnaryClientInterceptor) DialOption
		func WithUserAgent(s string) DialOption
		func WithWriteBufferSize(s int) DialOption
	
	# type EmptyCallOption struct{}
	# type EmptyDialOption struct{}
	# type EmptyServerOption struct{}

	# type FailFastCallOption struct {
			FailFast bool
		}

	# type ForceCodecCallOption struct {
			Codec encoding.Codec
		}

	# type ForceCodecV2CallOption struct {
			CodecV2 encoding.CodecV2
		}

	# type GenericClientStream[Req any, Res any] struct {
			ClientStream
		}

		func (x *GenericClientStream[Req, Res]) CloseAndRecv() (*Res, error)
		func (x *GenericClientStream[Req, Res]) Recv() (*Res, error)
		func (x *GenericClientStream[Req, Res]) Send(m *Req) error
	
	# type GenericServerStream[Req any, Res any] struct {
			ServerStream
		}

		func (x *GenericServerStream[Req, Res]) Recv() (*Req, error)
		func (x *GenericServerStream[Req, Res]) Send(m *Res) error
		func (x *GenericServerStream[Req, Res]) SendAndClose(m *Res) error
	
	# type HeaderCallOption struct {
			HeaderAddr *metadata.MD
		}

	# type MaxHeaderListSizeDialOption struct {
			MaxHeaderListSize uint32
		}

	# type MaxHeaderListSizeServerOption struct {
			MaxHeaderListSize uint32
		}

	# type MaxRecvMsgSizeCallOption struct {
			MaxRecvMsgSize int
		}

	# type MaxRetryRPCBufferSizeCallOption struct {
			MaxRetryRPCBufferSize int
		}

	# type MaxSendMsgSizeCallOption struct {
			MaxSendMsgSize int
		}

	# type MethodConfig = internalserviceconfig.MethodConfig  // 过期

	# type MethodDesc struct {
			MethodName string
			Handler    MethodHandler
		}

	# type MethodHandler func(srv any, ctx context.Context, dec func(any) error, interceptor UnaryServerInterceptor) (any, error)

	# type MethodInfo struct {
			Name string
			IsClientStream bool
			IsServerStream bool
		}

	# type OnFinishCallOption struct {
			OnFinish func(error)
		}

	# type PeerCallOption struct {
			PeerAddr *peer.Peer
		}

	# type PerRPCCredsCallOption struct {
			Creds credentials.PerRPCCredentials
		}

	# type PreparedMsg struct {
			// contains filtered or unexported fields
		}

		func (p *PreparedMsg) Encode(s Stream, msg any) error

	# type Server struct {
			// contains filtered or unexported fields
		}

		func NewServer(opt ...ServerOption) *Server
		func (s *Server) GetServiceInfo() map[string]ServiceInfo
		func (s *Server) GracefulStop()
		func (s *Server) RegisterService(sd *ServiceDesc, ss any)
		func (s *Server) Serve(lis net.Listener) error
		func (s *Server) ServeHTTP(w http.ResponseWriter, r *http.Request)
		func (s *Server) Stop()

	# type ServerOption interface {
		}

		func ChainStreamInterceptor(interceptors ...StreamServerInterceptor) ServerOption
		func ChainUnaryInterceptor(interceptors ...UnaryServerInterceptor) ServerOption
		func ConnectionTimeout(d time.Duration) ServerOption
		func Creds(c credentials.TransportCredentials) ServerOption
		func CustomCodec(codec Codec) ServerOption
		func ForceServerCodec(codec encoding.Codec) ServerOption
		func ForceServerCodecV2(codecV2 encoding.CodecV2) ServerOption
		func HeaderTableSize(s uint32) ServerOption
		func InTapHandle(h tap.ServerInHandle) ServerOption
		func InitialConnWindowSize(s int32) ServerOption
		func InitialWindowSize(s int32) ServerOption
		func KeepaliveEnforcementPolicy(kep keepalive.EnforcementPolicy) ServerOption
		func KeepaliveParams(kp keepalive.ServerParameters) ServerOption
		func MaxConcurrentStreams(n uint32) ServerOption
		func MaxHeaderListSize(s uint32) ServerOption
		func MaxMsgSize(m int) ServerOption
		func MaxRecvMsgSize(m int) ServerOption
		func MaxSendMsgSize(m int) ServerOption
		func NumStreamWorkers(numServerWorkers uint32) ServerOption
		func RPCCompressor(cp Compressor) ServerOption
		func RPCDecompressor(dc Decompressor) ServerOption
		func ReadBufferSize(s int) ServerOption
		func SharedWriteBuffer(val bool) ServerOption
		func StatsHandler(h stats.Handler) ServerOption
		func StreamInterceptor(i StreamServerInterceptor) ServerOption
		func UnaryInterceptor(i UnaryServerInterceptor) ServerOption
		func UnknownServiceHandler(streamHandler StreamHandler) ServerOption
		func WaitForHandlers(w bool) ServerOption
		func WriteBufferSize(s int) ServerOption
	
	# type ServerStream interface {
			SetHeader(metadata.MD) error
			SendHeader(metadata.MD) error
			SetTrailer(metadata.MD)
			Context() context.Context
			SendMsg(m any) error
			RecvMsg(m any) error
		}

	# type ServerStreamingClient[Res any] interface {
			Recv() (*Res, error)
			ClientStream
		}

	# type ServerStreamingServer[Res any] interface {
			Send(*Res) error
			ServerStream
		}

	# type ServerTransportStream interface {
			Method() string
			SetHeader(md metadata.MD) error
			SendHeader(md metadata.MD) error
			SetTrailer(md metadata.MD) error
		}

		func ServerTransportStreamFromContext(ctx context.Context) ServerTransportStream

	# type ServiceConfig struct {
			serviceconfig.Config
			Methods map[string]MethodConfig
		}

	# type ServiceDesc struct {
			ServiceName string
			HandlerType any
			Methods     []MethodDesc
			Streams     []StreamDesc
			Metadata    any
		}

	# type ServiceInfo struct {
			Methods []MethodInfo
			Metadata any
		}

	# type ServiceRegistrar interface {
			RegisterService(desc *ServiceDesc, impl any)
		}

	# type StaticMethodCallOption struct {
			EmptyCallOption
		}

	# type Stream interface {
			Context() context.Context
			SendMsg(m any) error
			RecvMsg(m any) error
		}

	# type StreamClientInterceptor func(ctx context.Context, desc *StreamDesc, cc *ClientConn, method string, streamer Streamer, opts ...CallOption) (ClientStream, error)

	# type StreamDesc struct {
			StreamName string        // the name of the method excluding the service
			Handler    StreamHandler // the handler called for the method
			ServerStreams bool // indicates the server can perform streaming sends
			ClientStreams bool // indicates the client can perform streaming sends
		}

	# type StreamHandler func(srv any, stream ServerStream) error
	# type StreamServerInfo struct {
			FullMethod string
			IsClientStream bool
			IsServerStream bool
		}

	# type StreamServerInterceptor func(srv any, ss ServerStream, info *StreamServerInfo, handler StreamHandler) error
	# type Streamer func(ctx context.Context, desc *StreamDesc, cc *ClientConn, method string, opts ...CallOption) (ClientStream, error)
	# type TrailerCallOption struct {
			TrailerAddr *metadata.MD
		}
	# type UnaryClientInterceptor func(ctx context.Context, method string, req, reply any, cc *ClientConn, invoker UnaryInvoker, opts ...CallOption) error
	# type UnaryHandler func(ctx context.Context, req any) (any, error)
	# type UnaryInvoker func(ctx context.Context, method string, req, reply any, cc *ClientConn, opts ...CallOption) error
	# type UnaryServerInfo struct {
			Server any
			FullMethod string
		}
	
	# type UnaryServerInterceptor func(ctx context.Context, req any, info *UnaryServerInfo, handler UnaryHandler) (resp any, err error)

------------------
func
------------------

	func ClientSupportedCompressors(ctx context.Context) ([]string, error)
	func Code(err error) codes.Code
	func ErrorDesc(err error) string
	func Errorf(c codes.Code, format string, a ...any) error
	func Invoke(ctx context.Context, method string, args, reply any, cc *ClientConn, ...) error
	func Method(ctx context.Context) (string, bool)
	func MethodFromServerStream(stream ServerStream) (string, bool)
	func NewContextWithServerTransportStream(ctx context.Context, stream ServerTransportStream) context.Context
	func SendHeader(ctx context.Context, md metadata.MD) error
	func SetHeader(ctx context.Context, md metadata.MD) error
	func SetSendCompressor(ctx context.Context, name string) error
	func SetTrailer(ctx context.Context, md metadata.MD) error