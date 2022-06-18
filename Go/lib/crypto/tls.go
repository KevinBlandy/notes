-------------------------
tls
-------------------------
	# tls包部分实现了RFC 5246中规定的TLS 1.2，以及RFC 8446中规定的TLS 1.3。


-------------------------
var
-------------------------
	const (
		// TLS 1.0 - 1.2 cipher suites.
		TLS_RSA_WITH_RC4_128_SHA                      uint16 = 0x0005
		TLS_RSA_WITH_3DES_EDE_CBC_SHA                 uint16 = 0x000a
		TLS_RSA_WITH_AES_128_CBC_SHA                  uint16 = 0x002f
		TLS_RSA_WITH_AES_256_CBC_SHA                  uint16 = 0x0035
		TLS_RSA_WITH_AES_128_CBC_SHA256               uint16 = 0x003c
		TLS_RSA_WITH_AES_128_GCM_SHA256               uint16 = 0x009c
		TLS_RSA_WITH_AES_256_GCM_SHA384               uint16 = 0x009d
		TLS_ECDHE_ECDSA_WITH_RC4_128_SHA              uint16 = 0xc007
		TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA          uint16 = 0xc009
		TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA          uint16 = 0xc00a
		TLS_ECDHE_RSA_WITH_RC4_128_SHA                uint16 = 0xc011
		TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA           uint16 = 0xc012
		TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA            uint16 = 0xc013
		TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA            uint16 = 0xc014
		TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256       uint16 = 0xc023
		TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256         uint16 = 0xc027
		TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256         uint16 = 0xc02f
		TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256       uint16 = 0xc02b
		TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384         uint16 = 0xc030
		TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384       uint16 = 0xc02c
		TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256   uint16 = 0xcca8
		TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 uint16 = 0xcca9

		// TLS 1.3 cipher suites.
		TLS_AES_128_GCM_SHA256       uint16 = 0x1301
		TLS_AES_256_GCM_SHA384       uint16 = 0x1302
		TLS_CHACHA20_POLY1305_SHA256 uint16 = 0x1303

		// TLS_FALLBACK_SCSV isn't a standard cipher suite but an indicator
		// that the client is doing version fallback. See RFC 7507.
		TLS_FALLBACK_SCSV uint16 = 0x5600

		// Legacy names for the corresponding cipher suites with the correct _SHA256
		// suffix, retained for backward compatibility.
		TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305   = TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256
		TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305 = TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256
	)

	const (
		VersionTLS10 = 0x0301
		VersionTLS11 = 0x0302
		VersionTLS12 = 0x0303
		VersionTLS13 = 0x0304

		// Deprecated: SSLv3 is cryptographically broken, and is no longer
		// supported by this package. See golang.org/issue/32716.
		VersionSSL30 = 0x0300
	)

-------------------------
type
-------------------------
	# type Certificate struct {
			Certificate [][]byte
			// PrivateKey contains the private key corresponding to the public key in
			// Leaf. This must implement crypto.Signer with an RSA, ECDSA or Ed25519 PublicKey.
			// For a server up to TLS 1.2, it can also implement crypto.Decrypter with
			// an RSA PublicKey.
			PrivateKey crypto.PrivateKey
			// SupportedSignatureAlgorithms is an optional list restricting what
			// signature algorithms the PrivateKey can be used for.
			SupportedSignatureAlgorithms []SignatureScheme
			// OCSPStaple contains an optional OCSP response which will be served
			// to clients that request it.
			OCSPStaple []byte
			// SignedCertificateTimestamps contains an optional list of Signed
			// Certificate Timestamps which will be served to clients that request it.
			SignedCertificateTimestamps [][]byte
			// Leaf is the parsed form of the leaf certificate, which may be initialized
			// using x509.ParseCertificate to reduce per-handshake processing. If nil,
			// the leaf certificate will be parsed as needed.
			Leaf *x509.Certificate
		}
	
		func LoadX509KeyPair(certFile, keyFile string) (Certificate, error)
		func X509KeyPair(certPEMBlock, keyPEMBlock []byte) (Certificate, error)
	
	# type CertificateRequestInfo struct {
			// AcceptableCAs contains zero or more, DER-encoded, X.501
			// Distinguished Names. These are the names of root or intermediate CAs
			// that the server wishes the returned certificate to be signed by. An
			// empty slice indicates that the server has no preference.
			AcceptableCAs [][]byte

			// SignatureSchemes lists the signature schemes that the server is
			// willing to verify.
			SignatureSchemes []SignatureScheme

			// Version is the TLS version that was negotiated for this connection.
			Version uint16
			// contains filtered or unexported fields
		}
		
		func (c *CertificateRequestInfo) Context() context.Context
		func (cri *CertificateRequestInfo) SupportsCertificate(c *Certificate) error
	
	# type CipherSuite struct {
			ID   uint16
			Name string

			// Supported versions is the list of TLS protocol versions that can
			// negotiate this cipher suite.
			SupportedVersions []uint16

			// Insecure is true if the cipher suite has known security issues
			// due to its primitives, design, or implementation.
			Insecure bool
		}

		func CipherSuites() []*CipherSuite
		func InsecureCipherSuites() []*CipherSuite
	
	# type ClientAuthType int
		const (
			// NoClientCert indicates that no client certificate should be requested
			// during the handshake, and if any certificates are sent they will not
			// be verified.
			NoClientCert ClientAuthType = iota
			// RequestClientCert indicates that a client certificate should be requested
			// during the handshake, but does not require that the client send any
			// certificates.
			RequestClientCert
			// RequireAnyClientCert indicates that a client certificate should be requested
			// during the handshake, and that at least one certificate is required to be
			// sent by the client, but that certificate is not required to be valid.
			RequireAnyClientCert
			// VerifyClientCertIfGiven indicates that a client certificate should be requested
			// during the handshake, but does not require that the client sends a
			// certificate. If the client does send a certificate it is required to be
			// valid.
			VerifyClientCertIfGiven
			// RequireAndVerifyClientCert indicates that a client certificate should be requested
			// during the handshake, and that at least one valid certificate is required
			// to be sent by the client.
			RequireAndVerifyClientCert
		)
	
		func (i ClientAuthType) String() string
	
	# type ClientHelloInfo struct {
			// CipherSuites lists the CipherSuites supported by the client (e.g.
			// TLS_AES_128_GCM_SHA256, TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256).
			CipherSuites []uint16

			// ServerName indicates the name of the server requested by the client
			// in order to support virtual hosting. ServerName is only set if the
			// client is using SNI (see RFC 4366, Section 3.1).
			ServerName string

			// SupportedCurves lists the elliptic curves supported by the client.
			// SupportedCurves is set only if the Supported Elliptic Curves
			// Extension is being used (see RFC 4492, Section 5.1.1).
			SupportedCurves []CurveID

			// SupportedPoints lists the point formats supported by the client.
			// SupportedPoints is set only if the Supported Point Formats Extension
			// is being used (see RFC 4492, Section 5.1.2).
			SupportedPoints []uint8

			// SignatureSchemes lists the signature and hash schemes that the client
			// is willing to verify. SignatureSchemes is set only if the Signature
			// Algorithms Extension is being used (see RFC 5246, Section 7.4.1.4.1).
			SignatureSchemes []SignatureScheme

			// SupportedProtos lists the application protocols supported by the client.
			// SupportedProtos is set only if the Application-Layer Protocol
			// Negotiation Extension is being used (see RFC 7301, Section 3.1).
			//
			// Servers can select a protocol by setting Config.NextProtos in a
			// GetConfigForClient return value.
			SupportedProtos []string

			// SupportedVersions lists the TLS versions supported by the client.
			// For TLS versions less than 1.3, this is extrapolated from the max
			// version advertised by the client, so values other than the greatest
			// might be rejected if used.
			SupportedVersions []uint16

			// Conn is the underlying net.Conn for the connection. Do not read
			// from, or write to, this connection; that will cause the TLS
			// connection to fail.
			Conn net.Conn
			// contains filtered or unexported fields
		}
		func (c *ClientHelloInfo) Context() context.Context
		func (chi *ClientHelloInfo) SupportsCertificate(c *Certificate) error

	# type ClientSessionCache interface {
			// Get searches for a ClientSessionState associated with the given key.
			// On return, ok is true if one was found.
			Get(sessionKey string) (session *ClientSessionState, ok bool)

			// Put adds the ClientSessionState to the cache with the given key. It might
			// get called multiple times in a connection if a TLS 1.3 server provides
			// more than one session ticket. If called with a nil *ClientSessionState,
			// it should remove the cache entry.
			Put(sessionKey string, cs *ClientSessionState)
		}
		func NewLRUClientSessionCache(capacity int) ClientSessionCache
	
	# type ClientSessionState struct {
			// contains filtered or unexported fields
		}
	
	# type Config struct {
			Rand io.Reader
			Time func() time.Time
			Certificates []Certificate
			NameToCertificate map[string]*Certificate
			GetCertificate func(*ClientHelloInfo) (*Certificate, error)
			GetClientCertificate func(*CertificateRequestInfo) (*Certificate, error)
			GetConfigForClient func(*ClientHelloInfo) (*Config, error)
			VerifyPeerCertificate func(rawCerts [][]byte, verifiedChains [][]*x509.Certificate) error
			VerifyConnection func(ConnectionState) error
			RootCAs *x509.CertPool
			NextProtos []string
			ServerName string
			ClientAuth ClientAuthType
			ClientCAs *x509.CertPool
			InsecureSkipVerify bool
			CipherSuites []uint16
			PreferServerCipherSuites bool
			SessionTicketsDisabled bool
			SessionTicketKey [32]byte
			ClientSessionCache ClientSessionCache
			MinVersion uint16
			MaxVersion uint16
			CurvePreferences []CurveID
			DynamicRecordSizingDisabled bool
			Renegotiation RenegotiationSupport
			KeyLogWriter io.Writer
		}

		* 服务端的TSL配置

		func (c *Config) BuildNameToCertificate()DEPRECATED
		func (c *Config) Clone() *Config
		func (c *Config) SetSessionTicketKeys(keys [][32]byte)
	
	# type Conn struct {
			// contains filtered or unexported fields
		}
		func Client(conn net.Conn, config *Config) *Conn
		func Dial(network, addr string, config *Config) (*Conn, error)
		func DialWithDialer(dialer *net.Dialer, network, addr string, config *Config) (*Conn, error)
		func Server(conn net.Conn, config *Config) *Conn
		func (c *Conn) Close() error
		func (c *Conn) CloseWrite() error
		func (c *Conn) ConnectionState() ConnectionState
		func (c *Conn) Handshake() error
		func (c *Conn) HandshakeContext(ctx context.Context) error
		func (c *Conn) LocalAddr() net.Addr
		func (c *Conn) NetConn() net.Conn
		func (c *Conn) OCSPResponse() []byte
		func (c *Conn) Read(b []byte) (int, error)
		func (c *Conn) RemoteAddr() net.Addr
		func (c *Conn) SetDeadline(t time.Time) error
		func (c *Conn) SetReadDeadline(t time.Time) error
		func (c *Conn) SetWriteDeadline(t time.Time) error
		func (c *Conn) VerifyHostname(host string) error
		func (c *Conn) Write(b []byte) (int, error)
	
	# type ConnectionState struct {
			// Version is the TLS version used by the connection (e.g. VersionTLS12).
			Version uint16

			// HandshakeComplete is true if the handshake has concluded.
			HandshakeComplete bool

			// DidResume is true if this connection was successfully resumed from a
			// previous session with a session ticket or similar mechanism.
			DidResume bool

			// CipherSuite is the cipher suite negotiated for the connection (e.g.
			// TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, TLS_AES_128_GCM_SHA256).
			CipherSuite uint16

			// NegotiatedProtocol is the application protocol negotiated with ALPN.
			NegotiatedProtocol string

			// NegotiatedProtocolIsMutual used to indicate a mutual NPN negotiation.
			//
			// Deprecated: this value is always true.
			NegotiatedProtocolIsMutual bool

			// ServerName is the value of the Server Name Indication extension sent by
			// the client. It's available both on the server and on the client side.
			ServerName string

			// PeerCertificates are the parsed certificates sent by the peer, in the
			// order in which they were sent. The first element is the leaf certificate
			// that the connection is verified against.
			//
			// On the client side, it can't be empty. On the server side, it can be
			// empty if Config.ClientAuth is not RequireAnyClientCert or
			// RequireAndVerifyClientCert.
			PeerCertificates []*x509.Certificate

			// VerifiedChains is a list of one or more chains where the first element is
			// PeerCertificates[0] and the last element is from Config.RootCAs (on the
			// client side) or Config.ClientCAs (on the server side).
			//
			// On the client side, it's set if Config.InsecureSkipVerify is false. On
			// the server side, it's set if Config.ClientAuth is VerifyClientCertIfGiven
			// (and the peer provided a certificate) or RequireAndVerifyClientCert.
			VerifiedChains [][]*x509.Certificate

			// SignedCertificateTimestamps is a list of SCTs provided by the peer
			// through the TLS handshake for the leaf certificate, if any.
			SignedCertificateTimestamps [][]byte

			// OCSPResponse is a stapled Online Certificate Status Protocol (OCSP)
			// response provided by the peer for the leaf certificate, if any.
			OCSPResponse []byte

			// TLSUnique contains the "tls-unique" channel binding value (see RFC 5929,
			// Section 3). This value will be nil for TLS 1.3 connections and for all
			// resumed connections.
			//
			// Deprecated: there are conditions in which this value might not be unique
			// to a connection. See the Security Considerations sections of RFC 5705 and
			// RFC 7627, and https://mitls.org/pages/attacks/3SHAKE#channelbindings.
			TLSUnique []byte
			// contains filtered or unexported fields
		}
		func (cs *ConnectionState) ExportKeyingMaterial(label string, context []byte, length int) ([]byte, error)

	# type CurveID uint16
		const (
			CurveP256 CurveID = 23
			CurveP384 CurveID = 24
			CurveP521 CurveID = 25
			X25519    CurveID = 29
		)
		
		func (i CurveID) String() string
	
	# type Dialer struct {
			// NetDialer is the optional dialer to use for the TLS connections'
			// underlying TCP connections.
			// A nil NetDialer is equivalent to the net.Dialer zero value.
			NetDialer *net.Dialer

			// Config is the TLS configuration to use for new connections.
			// A nil configuration is equivalent to the zero
			// configuration; see the documentation of Config for the
			// defaults.
			Config *Config
		}
		func (d *Dialer) Dial(network, addr string) (net.Conn, error)
		func (d *Dialer) DialContext(ctx context.Context, network, addr string) (net.Conn, error)
	
	# type RecordHeaderError struct {
			// Msg contains a human readable string that describes the error.
			Msg string
			// RecordHeader contains the five bytes of TLS record header that
			// triggered the error.
			RecordHeader [5]byte
			// Conn provides the underlying net.Conn in the case that a client
			// sent an initial handshake that didn't look like TLS.
			// It is nil if there's already been a handshake or a TLS alert has
			// been written to the connection.
			Conn net.Conn
		}
		func (e RecordHeaderError) Error() string
	
	# type RenegotiationSupport int

		const (
			// RenegotiateNever disables renegotiation.
			RenegotiateNever RenegotiationSupport = iota

			// RenegotiateOnceAsClient allows a remote server to request
			// renegotiation once per connection.
			RenegotiateOnceAsClient

			// RenegotiateFreelyAsClient allows a remote server to repeatedly
			// request renegotiation.
			RenegotiateFreelyAsClient
		)
	
	# type SignatureScheme uint16
	
		const (
			// RSASSA-PKCS1-v1_5 algorithms.
			PKCS1WithSHA256 SignatureScheme = 0x0401
			PKCS1WithSHA384 SignatureScheme = 0x0501
			PKCS1WithSHA512 SignatureScheme = 0x0601

			// RSASSA-PSS algorithms with public key OID rsaEncryption.
			PSSWithSHA256 SignatureScheme = 0x0804
			PSSWithSHA384 SignatureScheme = 0x0805
			PSSWithSHA512 SignatureScheme = 0x0806

			// ECDSA algorithms. Only constrained to a specific curve in TLS 1.3.
			ECDSAWithP256AndSHA256 SignatureScheme = 0x0403
			ECDSAWithP384AndSHA384 SignatureScheme = 0x0503
			ECDSAWithP521AndSHA512 SignatureScheme = 0x0603

			// EdDSA algorithms.
			Ed25519 SignatureScheme = 0x0807

			// Legacy signature and hash algorithms for TLS 1.2.
			PKCS1WithSHA1 SignatureScheme = 0x0201
			ECDSAWithSHA1 SignatureScheme = 0x0203
		)
		func (i SignatureScheme) String() string
	

-------------------------
func
-------------------------
	func CipherSuiteName(id uint16) string
	func Listen(network, laddr string, config *Config) (net.Listener, error)
	func NewListener(inner net.Listener, config *Config) net.Listener
