---------------------------------
Autocert
---------------------------------
	# Autocert 包可自动访问 Let's Encrypt 和其他基于 ACME 的 CA 的证书。


---------------------------------
var
---------------------------------

	const DefaultACMEDirectory = "https://acme-v02.api.letsencrypt.org/directory"

	var ErrCacheMiss = errors.New("acme/autocert: certificate cache miss")

---------------------------------
type
---------------------------------

	# type Cache interface {
			// Get returns a certificate data for the specified key.
			// If there's no such key, Get returns ErrCacheMiss.
			Get(ctx context.Context, key string) ([]byte, error)

			// Put stores the data in the cache under the specified key.
			// Underlying implementations may use any data storage format,
			// as long as the reverse operation, Get, results in the original data.
			Put(ctx context.Context, key string, data []byte) error

			// Delete removes a certificate data from the cache under the specified key.
			// If there's no such key in the cache, Delete returns nil.
			Delete(ctx context.Context, key string) error
		}

	
	# type DirCache string
		func (d DirCache) Delete(ctx context.Context, name string) error
		func (d DirCache) Get(ctx context.Context, name string) ([]byte, error)
		func (d DirCache) Put(ctx context.Context, name string, data []byte) error
	
	# type HostPolicy func(ctx context.Context, host string) error
		func HostWhitelist(hosts ...string) HostPolicy
	
	# type Manager struct {
			// Prompt specifies a callback function to conditionally accept a CA's Terms of Service (TOS).
			// The registration may require the caller to agree to the CA's TOS.
			// If so, Manager calls Prompt with a TOS URL provided by the CA. Prompt should report
			// whether the caller agrees to the terms.
			//
			// To always accept the terms, the callers can use AcceptTOS.
			Prompt func(tosURL string) bool

			// Cache optionally stores and retrieves previously-obtained certificates
			// and other state. If nil, certs will only be cached for the lifetime of
			// the Manager. Multiple Managers can share the same Cache.
			//
			// Using a persistent Cache, such as DirCache, is strongly recommended.
			Cache Cache

			// HostPolicy controls which domains the Manager will attempt
			// to retrieve new certificates for. It does not affect cached certs.
			//
			// If non-nil, HostPolicy is called before requesting a new cert.
			// If nil, all hosts are currently allowed. This is not recommended,
			// as it opens a potential attack where clients connect to a server
			// by IP address and pretend to be asking for an incorrect host name.
			// Manager will attempt to obtain a certificate for that host, incorrectly,
			// eventually reaching the CA's rate limit for certificate requests
			// and making it impossible to obtain actual certificates.
			//
			// See GetCertificate for more details.
			HostPolicy HostPolicy

			// RenewBefore optionally specifies how early certificates should
			// be renewed before they expire.
			//
			// If zero, they're renewed 30 days before expiration.
			RenewBefore time.Duration

			// Client is used to perform low-level operations, such as account registration
			// and requesting new certificates.
			//
			// If Client is nil, a zero-value acme.Client is used with DefaultACMEDirectory
			// as the directory endpoint.
			// If the Client.Key is nil, a new ECDSA P-256 key is generated and,
			// if Cache is not nil, stored in cache.
			//
			// Mutating the field after the first call of GetCertificate method will have no effect.
			Client *acme.Client

			// Email optionally specifies a contact email address.
			// This is used by CAs, such as Let's Encrypt, to notify about problems
			// with issued certificates.
			//
			// If the Client's account key is already registered, Email is not used.
			Email string

			// ForceRSA used to make the Manager generate RSA certificates. It is now ignored.
			//
			// Deprecated: the Manager will request the correct type of certificate based
			// on what each client supports.
			ForceRSA bool

			// ExtraExtensions are used when generating a new CSR (Certificate Request),
			// thus allowing customization of the resulting certificate.
			// For instance, TLS Feature Extension (RFC 7633) can be used
			// to prevent an OCSP downgrade attack.
			//
			// The field value is passed to crypto/x509.CreateCertificateRequest
			// in the template's ExtraExtensions field as is.
			ExtraExtensions []pkix.Extension

			// ExternalAccountBinding optionally represents an arbitrary binding to an
			// account of the CA to which the ACME server is tied.
			// See RFC 8555, Section 7.3.4 for more details.
			ExternalAccountBinding *acme.ExternalAccountBinding
			// contains filtered or unexported fields
		}

		func (m *Manager) GetCertificate(hello *tls.ClientHelloInfo) (*tls.Certificate, error)
		func (m *Manager) HTTPHandler(fallback http.Handler) http.Handler
		func (m *Manager) Listener() net.Listener
		func (m *Manager) TLSConfig() *tls.Config

---------------------------------
func
---------------------------------

	func AcceptTOS(tosURL string) bool
	func NewListener(domains ...string) net.Listener

