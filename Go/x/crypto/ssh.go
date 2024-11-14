----------------------
ssh
----------------------

----------------------
var
----------------------

	const (
		CertAlgoRSAv01        = "ssh-rsa-cert-v01@openssh.com"
		CertAlgoDSAv01        = "ssh-dss-cert-v01@openssh.com"
		CertAlgoECDSA256v01   = "ecdsa-sha2-nistp256-cert-v01@openssh.com"
		CertAlgoECDSA384v01   = "ecdsa-sha2-nistp384-cert-v01@openssh.com"
		CertAlgoECDSA521v01   = "ecdsa-sha2-nistp521-cert-v01@openssh.com"
		CertAlgoSKECDSA256v01 = "sk-ecdsa-sha2-nistp256-cert-v01@openssh.com"
		CertAlgoED25519v01    = "ssh-ed25519-cert-v01@openssh.com"
		CertAlgoSKED25519v01  = "sk-ssh-ed25519-cert-v01@openssh.com"

		// CertAlgoRSASHA256v01 and CertAlgoRSASHA512v01 can't appear as a
		// Certificate.Type (or PublicKey.Type), but only in
		// ClientConfig.HostKeyAlgorithms.
		CertAlgoRSASHA256v01 = "rsa-sha2-256-cert-v01@openssh.com"
		CertAlgoRSASHA512v01 = "rsa-sha2-512-cert-v01@openssh.com"
	)

	const (
		// Deprecated: use CertAlgoRSAv01.
		CertSigAlgoRSAv01 = CertAlgoRSAv01
		// Deprecated: use CertAlgoRSASHA256v01.
		CertSigAlgoRSASHA2256v01 = CertAlgoRSASHA256v01
		// Deprecated: use CertAlgoRSASHA512v01.
		CertSigAlgoRSASHA2512v01 = CertAlgoRSASHA512v01
	)

	const (
		UserCert = 1
		HostCert = 2
	)

	const (
		KeyAlgoRSA        = "ssh-rsa"
		KeyAlgoDSA        = "ssh-dss"
		KeyAlgoECDSA256   = "ecdsa-sha2-nistp256"
		KeyAlgoSKECDSA256 = "sk-ecdsa-sha2-nistp256@openssh.com"
		KeyAlgoECDSA384   = "ecdsa-sha2-nistp384"
		KeyAlgoECDSA521   = "ecdsa-sha2-nistp521"
		KeyAlgoED25519    = "ssh-ed25519"
		KeyAlgoSKED25519  = "sk-ssh-ed25519@openssh.com"

		// KeyAlgoRSASHA256 and KeyAlgoRSASHA512 are only public key algorithms, not
		// public key formats, so they can't appear as a PublicKey.Type. The
		// corresponding PublicKey.Type is KeyAlgoRSA. See RFC 8332, Section 2.
		KeyAlgoRSASHA256 = "rsa-sha2-256"
		KeyAlgoRSASHA512 = "rsa-sha2-512"
	)

	const (
		// Deprecated: use KeyAlgoRSA.
		SigAlgoRSA = KeyAlgoRSA
		// Deprecated: use KeyAlgoRSASHA256.
		SigAlgoRSASHA2256 = KeyAlgoRSASHA256
		// Deprecated: use KeyAlgoRSASHA512.
		SigAlgoRSASHA2512 = KeyAlgoRSASHA512
	)

	const (
		VINTR         = 1
		VQUIT         = 2
		VERASE        = 3
		VKILL         = 4
		VEOF          = 5
		VEOL          = 6
		VEOL2         = 7
		VSTART        = 8
		VSTOP         = 9
		VSUSP         = 10
		VDSUSP        = 11
		VREPRINT      = 12
		VWERASE       = 13
		VLNEXT        = 14
		VFLUSH        = 15
		VSWTCH        = 16
		VSTATUS       = 17
		VDISCARD      = 18
		IGNPAR        = 30
		PARMRK        = 31
		INPCK         = 32
		ISTRIP        = 33
		INLCR         = 34
		IGNCR         = 35
		ICRNL         = 36
		IUCLC         = 37
		IXON          = 38
		IXANY         = 39
		IXOFF         = 40
		IMAXBEL       = 41
		IUTF8         = 42 // RFC 8160
		ISIG          = 50
		ICANON        = 51
		XCASE         = 52
		ECHO          = 53
		ECHOE         = 54
		ECHOK         = 55
		ECHONL        = 56
		NOFLSH        = 57
		TOSTOP        = 58
		IEXTEN        = 59
		ECHOCTL       = 60
		ECHOKE        = 61
		PENDIN        = 62
		OPOST         = 70
		OLCUC         = 71
		ONLCR         = 72
		OCRNL         = 73
		ONOCR         = 74
		ONLRET        = 75
		CS7           = 90
		CS8           = 91
		PARENB        = 92
		PARODD        = 93
		TTY_OP_ISPEED = 128
		TTY_OP_OSPEED = 129
	)

	const CertTimeInfinity = 1<<64 - 1

	var ErrNoAuth = errors.New("ssh: no auth passed yet")

----------------------
type
----------------------

----------------------
func
----------------------

	func DiscardRequests(in <-chan *Request)
	func FingerprintLegacyMD5(pubKey PublicKey) string
	func FingerprintSHA256(pubKey PublicKey) string
	func Marshal(msg interface{}) []byte
	func MarshalAuthorizedKey(key PublicKey) []byte
	func MarshalPrivateKey(key crypto.PrivateKey, comment string) (*pem.Block, error)
	func MarshalPrivateKeyWithPassphrase(key crypto.PrivateKey, comment string, passphrase []byte) (*pem.Block, error)
	func ParseDSAPrivateKey(der []byte) (*dsa.PrivateKey, error)
	func ParseRawPrivateKey(pemBytes []byte) (interface{}, error)
	func ParseRawPrivateKeyWithPassphrase(pemBytes, passphrase []byte) (interface{}, error)
	func Unmarshal(data []byte, out interface{}) error

----------------------
Demo
----------------------
	