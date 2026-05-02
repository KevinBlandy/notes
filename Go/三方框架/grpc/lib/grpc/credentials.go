-------------------------------
credentials
-------------------------------

-------------------------------
var
-------------------------------
	var ErrConnDispatched = errors.New("credentials: rawConn is dispatched out of gRPC")


-------------------------------
type
-------------------------------
	# type AuthInfo interface {
			AuthType() string
		}
	
	# type AuthorityValidator interface {
			ValidateAuthority(authority string) error
		}
	
	
	# type Bundle interface {
			TransportCredentials() TransportCredentials
			PerRPCCredentials() PerRPCCredentials
			NewWithMode(mode string) (Bundle, error)
		}
	
	# type ChannelzSecurityInfo interface {
			GetSecurityValue() ChannelzSecurityValue
		}
	
	# type ChannelzSecurityValue interface {
		}
	
	# type ClientHandshakeInfo struct {
			Attributes *attributes.Attributes
		}

		func ClientHandshakeInfoFromContext(ctx context.Context) ClientHandshakeInfo
	
	# type CommonAuthInfo struct {
			SecurityLevel SecurityLevel
		}

		func (c CommonAuthInfo) GetCommonAuthInfo() CommonAuthInfo
	
	# type OtherChannelzSecurityValue struct {
			ChannelzSecurityValue
			Name  string
			Value proto.Message
		}
	
	# type PerRPCCredentials interface {
			GetRequestMetadata(ctx context.Context, uri ...string) (map[string]string, error)
			RequireTransportSecurity() bool
		}

	# type ProtocolInfo struct {
			ProtocolVersion string
			SecurityProtocol string
			SecurityVersion string
			ServerName string
		}
	
	# type RequestInfo struct {
			Method string
				* 请求方法
			AuthInfo AuthInfo
				* 认证信息
		}

		func RequestInfoFromContext(ctx context.Context) (ri RequestInfo, ok bool)
			* 从上下文获取懂啊请求信息
	
	# type SecurityLevel int
		const (
			InvalidSecurityLevel SecurityLevel = iota
			NoSecurity
			IntegrityOnly
			PrivacyAndIntegrity
		)
	
		func (s SecurityLevel) String() string
	
	# type TLSChannelzSecurityValue struct {
			ChannelzSecurityValue
			StandardName      string
			LocalCertificate  []byte
			RemoteCertificate []byte
		}
	
	# type TLSInfo struct {
			State tls.ConnectionState
			CommonAuthInfo
			SPIFFEID *url.URL
		}
		func (t TLSInfo) AuthType() string
		func (t TLSInfo) GetSecurityValue() ChannelzSecurityValue
		func (t TLSInfo) ValidateAuthority(authority string) error
	
	# type TransportCredentials interface {
			ClientHandshake(context.Context, string, net.Conn) (net.Conn, AuthInfo, error)
			ServerHandshake(net.Conn) (net.Conn, AuthInfo, error)
			Info() ProtocolInfo
			Clone() TransportCredentials
			OverrideServerName(string) error
		}
		
		* 传输层凭证信息

		func NewClientTLSFromCert(cp *x509.CertPool, serverNameOverride string) TransportCredentials
		func NewClientTLSFromFile(certFile, serverNameOverride string) (TransportCredentials, error)
			* 加载客户端证书

		func NewServerTLSFromCert(cert *tls.Certificate) TransportCredentials
		func NewServerTLSFromFile(certFile, keyFile string) (TransportCredentials, error)
			* 加载服务端证书和私钥

		func NewTLS(c *tls.Config) TransportCredentials
			* 直接根据 TLS 



-------------------------------
func
-------------------------------
	func CheckSecurityLevel(ai AuthInfo, level SecurityLevel) error
	func NewContextWithRequestInfo(ctx context.Context, ri RequestInfo) context.Context

