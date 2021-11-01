--------------------
x509
--------------------

--------------------
var
--------------------
	var ErrUnsupportedAlgorithm = errors.New("x509: cannot verify signature: algorithm unimplemented")
	var IncorrectPasswordError = errors.New("x509: decryption password incorrect")

--------------------
type
--------------------
	# type CertPool struct {
		}
		func NewCertPool() *CertPool
		func SystemCertPool() (*CertPool, error)
		func (s *CertPool) AddCert(cert *Certificate)
		func (s *CertPool) AppendCertsFromPEM(pemCerts []byte) (ok bool)
		func (s *CertPool) Subjects() [][]byte
	
	# type Certificate struct {
			Raw                     []byte // Complete ASN.1 DER content (certificate, signature algorithm and signature).
			RawTBSCertificate       []byte // Certificate part of raw ASN.1 DER content.
			RawSubjectPublicKeyInfo []byte // DER encoded SubjectPublicKeyInfo.
			RawSubject              []byte // DER encoded Subject
			RawIssuer               []byte // DER encoded Issuer

			Signature          []byte
			SignatureAlgorithm SignatureAlgorithm

			PublicKeyAlgorithm PublicKeyAlgorithm
			PublicKey          interface{}

			Version             int
			SerialNumber        *big.Int
			Issuer              pkix.Name
			Subject             pkix.Name
			NotBefore, NotAfter time.Time // Validity bounds.
			KeyUsage            KeyUsage

			Extensions []pkix.Extension

			ExtraExtensions []pkix.Extension

			UnhandledCriticalExtensions []asn1.ObjectIdentifier

			ExtKeyUsage        []ExtKeyUsage           // Sequence of extended key usages.
			UnknownExtKeyUsage []asn1.ObjectIdentifier // Encountered extended key usages unknown to this package.

			BasicConstraintsValid bool
			IsCA                  bool

			MaxPathLen int

			MaxPathLenZero bool

			SubjectKeyId   []byte
			AuthorityKeyId []byte

			OCSPServer            []string
			IssuingCertificateURL []string

			DNSNames       []string
			EmailAddresses []string
			IPAddresses    []net.IP
			URIs           []*url.URL

			PermittedDNSDomainsCritical bool // if true then the name constraints are marked critical.
			PermittedDNSDomains         []string
			ExcludedDNSDomains          []string
			PermittedIPRanges           []*net.IPNet
			ExcludedIPRanges            []*net.IPNet
			PermittedEmailAddresses     []string
			ExcludedEmailAddresses      []string
			PermittedURIDomains         []string
			ExcludedURIDomains          []string

			CRLDistributionPoints []string

			PolicyIdentifiers []asn1.ObjectIdentifier
		}
		func ParseCertificate(der []byte) (*Certificate, error)
		func ParseCertificates(der []byte) ([]*Certificate, error)
		func (c *Certificate) CheckCRLSignature(crl *pkix.CertificateList) error
		func (c *Certificate) CheckSignature(algo SignatureAlgorithm, signed, signature []byte) error
		func (c *Certificate) CheckSignatureFrom(parent *Certificate) error
		func (c *Certificate) CreateCRL(rand io.Reader, priv interface{}, revokedCerts []pkix.RevokedCertificate, ...) (crlBytes []byte, err error)
		func (c *Certificate) Equal(other *Certificate) bool
		func (c *Certificate) Verify(opts VerifyOptions) (chains [][]*Certificate, err error)
		func (c *Certificate) VerifyHostname(h string) error
	
	# type CertificateInvalidError struct {
			Cert   *Certificate
			Reason InvalidReason
			Detail string
		}
		func (e CertificateInvalidError) Error() string
	
	# type CertificateRequest struct {
			Raw                      []byte // Complete ASN.1 DER content (CSR, signature algorithm and signature).
			RawTBSCertificateRequest []byte // Certificate request info part of raw ASN.1 DER content.
			RawSubjectPublicKeyInfo  []byte // DER encoded SubjectPublicKeyInfo.
			RawSubject               []byte // DER encoded Subject.

			Version            int
			Signature          []byte
			SignatureAlgorithm SignatureAlgorithm

			PublicKeyAlgorithm PublicKeyAlgorithm
			PublicKey          interface{}

			Subject pkix.Name

			Attributes []pkix.AttributeTypeAndValueSET

			Extensions []pkix.Extension

			ExtraExtensions []pkix.Extension

			// Subject Alternate Name values.
			DNSNames       []string
			EmailAddresses []string
			IPAddresses    []net.IP
			URIs           []*url.URL
		}
		func ParseCertificateRequest(asn1Data []byte) (*CertificateRequest, error)
		func (c *CertificateRequest) CheckSignature() error
	
	# type ConstraintViolationError struct{}
		func (ConstraintViolationError) Error() string
	
	# type ExtKeyUsage int
		const (
			ExtKeyUsageAny ExtKeyUsage = iota
			ExtKeyUsageServerAuth
			ExtKeyUsageClientAuth
			ExtKeyUsageCodeSigning
			ExtKeyUsageEmailProtection
			ExtKeyUsageIPSECEndSystem
			ExtKeyUsageIPSECTunnel
			ExtKeyUsageIPSECUser
			ExtKeyUsageTimeStamping
			ExtKeyUsageOCSPSigning
			ExtKeyUsageMicrosoftServerGatedCrypto
			ExtKeyUsageNetscapeServerGatedCrypto
			ExtKeyUsageMicrosoftCommercialCodeSigning
			ExtKeyUsageMicrosoftKernelCodeSigning
		)
	
	# type HostnameError struct {
			Certificate *Certificate
			Host        string
		}
		func (h HostnameError) Error() string
	
	# type InsecureAlgorithmError SignatureAlgorithm
		func (e InsecureAlgorithmError) Error() string
	
	# type InvalidReason int
		const (
			NotAuthorizedToSign InvalidReason = iota
			Expired
			CANotAuthorizedForThisName
			TooManyIntermediates
			IncompatibleUsage
			NameMismatch
			NameConstraintsWithoutSANs
			UnconstrainedName
			TooManyConstraints
			CANotAuthorizedForExtKeyUsage
		)
	
	# type KeyUsage int
		const (
			KeyUsageDigitalSignature KeyUsage = 1 << iota
			KeyUsageContentCommitment
			KeyUsageKeyEncipherment
			KeyUsageDataEncipherment
			KeyUsageKeyAgreement
			KeyUsageCertSign
			KeyUsageCRLSign
			KeyUsageEncipherOnly
			KeyUsageDecipherOnly
		)
	
	# type PEMCipher int
		const (
			_ PEMCipher = iota
			PEMCipherDES
			PEMCipher3DES
			PEMCipherAES128
			PEMCipherAES192
			PEMCipherAES256
		)
	
	# type PublicKeyAlgorithm int
		func (algo PublicKeyAlgorithm) String() string
	
	# type RevocationList struct {
			SignatureAlgorithm SignatureAlgorithm
			RevokedCertificates []pkix.RevokedCertificate
			Number *big.Int
			ThisUpdate time.Time
			NextUpdate time.Time
			ExtraExtensions []pkix.Extension
		}
	
	# type SignatureAlgorithm int
		const (
			UnknownSignatureAlgorithm SignatureAlgorithm = iota

			MD2WithRSA // Unsupported.
			MD5WithRSA // Only supported for signing, not verification.
			SHA1WithRSA
			SHA256WithRSA
			SHA384WithRSA
			SHA512WithRSA
			DSAWithSHA1   // Unsupported.
			DSAWithSHA256 // Unsupported.
			ECDSAWithSHA1
			ECDSAWithSHA256
			ECDSAWithSHA384
			ECDSAWithSHA512
			SHA256WithRSAPSS
			SHA384WithRSAPSS
			SHA512WithRSAPSS
			PureEd25519
		)
		func (algo SignatureAlgorithm) String() string
	
	# type SystemRootsError struct {
			Err error
		}
		func (se SystemRootsError) Error() string
		func (se SystemRootsError) Unwrap() error
	
	# type UnhandledCriticalExtension struct{}
		func (h UnhandledCriticalExtension) Error() string
	
	# type UnknownAuthorityError struct {
			Cert *Certificate
		}
		func (e UnknownAuthorityError) Error() string
	
	# type VerifyOptions struct {
			DNSName string
			Intermediates *CertPool
			Roots *CertPool
			CurrentTime time.Time
			KeyUsages []ExtKeyUsage
			MaxConstraintComparisions int
		}

--------------------
func
--------------------
	func CreateCertificate(rand io.Reader, template, parent *Certificate, pub, priv interface{}) ([]byte, error)
	func CreateCertificateRequest(rand io.Reader, template *CertificateRequest, priv interface{}) (csr []byte, err error)
	func CreateRevocationList(rand io.Reader, template *RevocationList, issuer *Certificate, ...) ([]byte, error)
	func DecryptPEMBlock(b *pem.Block, password []byte) ([]byte, error)DEPRECATED
	func EncryptPEMBlock(rand io.Reader, blockType string, data, password []byte, alg PEMCipher) (*pem.Block, error)DEPRECATED
	func IsEncryptedPEMBlock(b *pem.Block) boolDEPRECATED
	func MarshalECPrivateKey(key *ecdsa.PrivateKey) ([]byte, error)
	func MarshalPKCS1PrivateKey(key *rsa.PrivateKey) []byte
	func MarshalPKCS1PublicKey(key *rsa.PublicKey) []byte
	func MarshalPKCS8PrivateKey(key interface{}) ([]byte, error)
	func MarshalPKIXPublicKey(pub interface{}) ([]byte, error)
	func ParseCRL(crlBytes []byte) (*pkix.CertificateList, error)
	func ParseDERCRL(derBytes []byte) (*pkix.CertificateList, error)
	func ParseECPrivateKey(der []byte) (*ecdsa.PrivateKey, error)
	func ParsePKCS1PrivateKey(der []byte) (*rsa.PrivateKey, error)
	func ParsePKCS1PublicKey(der []byte) (*rsa.PublicKey, error)
	func ParsePKCS8PrivateKey(der []byte) (key interface{}, err error)
	func ParsePKIXPublicKey(derBytes []byte) (pub interface{}, err error)