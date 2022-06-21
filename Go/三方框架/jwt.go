------------------------
JWT
------------------------
	# Doc
		https://github.com/dgrijalva/jwt-go
		
	# import
		"github.com/golang-jwt/jwt"

------------------------
var
------------------------
	const (
		ValidationErrorMalformed        uint32 = 1 << iota // Token is malformed
		ValidationErrorUnverifiable                        // Token could not be verified because of signing problems
		ValidationErrorSignatureInvalid                    // Signature validation failed

		// Standard Claim validation errors ��Ԥ����ĸ���У���쳣��
		ValidationErrorAudience      // AUD validation failed
		ValidationErrorExpired       // EXP validation failed
		ValidationErrorIssuedAt      // IAT validation failed
		ValidationErrorIssuer        // ISS validation failed
		ValidationErrorNotValidYet   // NBF validation failed
		ValidationErrorId            // JTI validation failed
		ValidationErrorClaimsInvalid // Generic claims validation error


	)

	const UnsafeAllowNoneSignatureType unsafeNoneMagicConstant = "none signing method allowed"

	var (
		ErrNotECPublicKey  = errors.New("Key is not a valid ECDSA public key")
		ErrNotECPrivateKey = errors.New("Key is not a valid ECDSA private key")
	)
	var (
		ErrInvalidKey      = errors.New("key is invalid")
		ErrInvalidKeyType  = errors.New("key is of invalid type")
		ErrHashUnavailable = errors.New("the requested hash function is unavailable")
	)
	var (
		ErrKeyMustBePEMEncoded = errors.New("Invalid Key: Key must be PEM encoded PKCS1 or PKCS8 private key")
		ErrNotRSAPrivateKey    = errors.New("Key is not a valid RSA private key")
		ErrNotRSAPublicKey     = errors.New("Key is not a valid RSA public key")
	)
	var (
		// Sadly this is missing from crypto/ecdsa compared to crypto/rsa
		ErrECDSAVerification = errors.New("crypto/ecdsa: verification error")
	)
	var NoneSignatureTypeDisallowedError error
	var SigningMethodNone *signingMethodNone
	var TimeFunc = time.Now

------------------------
type
------------------------
	# ype Claims interface {
			Valid() error
		}
	# type Keyfunc func(*Token) (interface{}, error)
	# type MapClaims map[string]interface{}
		func (m MapClaims) Valid() error
		func (m MapClaims) VerifyAudience(cmp string, req bool) bool
		func (m MapClaims) VerifyExpiresAt(cmp int64, req bool) bool
		func (m MapClaims) VerifyIssuedAt(cmp int64, req bool) bool
		func (m MapClaims) VerifyIssuer(cmp string, req bool) bool
		func (m MapClaims) VerifyNotBefore(cmp int64, req bool) bool
	# type Parser struct {
			ValidMethods         []string // If populated, only these methods will be considered valid
			UseJSONNumber        bool     // Use JSON Number format in JSON decoder
			SkipClaimsValidation bool     // Skip claims validation during token parsing
				* ������ʱ���Ƿ�У��Token(token��Ч, δ����, ����Ч)
				* ���Ϊtrue,��ô��ִ�� ParseWithClaims/Parse У��ʧ�ܾͻ᷵���쳣
		}
		func (p *Parser) Parse(tokenString string, keyFunc Keyfunc) (*Token, error)
		func (p *Parser) ParseUnverified(tokenString string, claims Claims) (token *Token, parts []string, err error)
		func (p *Parser) ParseWithClaims(tokenString string, claims Claims, keyFunc Keyfunc) (*Token, error)

	# type SigningMethod interface {
			Verify(signingString, signature string, key interface{}) error // Returns nil if signature is valid
			Sign(signingString string, key interface{}) (string, error)    // Returns encoded signature or error
			Alg() string                                                   // returns the alg identifier for this method (example: 'HS256')
		}
		func GetSigningMethod(alg string) (method SigningMethod)

	# type SigningMethodECDSA struct {
			Name      string
			Hash      crypto.Hash
			KeySize   int
			CurveBits int
		}
		func (m *SigningMethodECDSA) Alg() string
		func (m *SigningMethodECDSA) Sign(signingString string, key interface{}) (string, error)
		func (m *SigningMethodECDSA) Verify(signingString, signature string, key interface{}) error

	# type SigningMethodHMAC struct {
			Name string
			Hash crypto.Hash
		}

		* HMAC ��֤
		* Ԥ�����ʵ��
			var (
				SigningMethodHS256  *SigningMethodHMAC
				SigningMethodHS384  *SigningMethodHMAC
				SigningMethodHS512  *SigningMethodHMAC
				ErrSignatureInvalid = errors.New("signature is invalid")
			)

		func (m *SigningMethodHMAC) Alg() string
		func (m *SigningMethodHMAC) Sign(signingString string, key interface{}) (string, error)
		func (m *SigningMethodHMAC) Verify(signingString, signature string, key interface{}) error
	
	# type SigningMethodRSA struct {
			Name string
			Hash crypto.Hash
		}
		
		* RSA��֤
		* Ԥ�����ʵ��
			var (
				SigningMethodRS256 *SigningMethodRSA
				SigningMethodRS384 *SigningMethodRSA
				SigningMethodRS512 *SigningMethodRSA
			)

		func (m *SigningMethodRSA) Alg() string
		func (m *SigningMethodRSA) Sign(signingString string, key interface{}) (string, error)
		func (m *SigningMethodRSA) Verify(signingString, signature string, key interface{}) error
	
	# type SigningMethodRSAPSS struct {
			*SigningMethodRSA
			Options *rsa.PSSOptions
		}
		
		* Ԥ�����ʵ��
			var (
				SigningMethodPS256 *SigningMethodRSAPSS
				SigningMethodPS384 *SigningMethodRSAPSS
				SigningMethodPS512 *SigningMethodRSAPSS
			)

		func (m *SigningMethodRSAPSS) Sign(signingString string, key interface{}) (string, error)
		func (m *SigningMethodRSAPSS) Verify(signingString, signature string, key interface{}) error
	
	# type StandardClaims struct {
			Audience  string `json:"aud,omitempty"`
			ExpiresAt int64  `json:"exp,omitempty"`
			Id        string `json:"jti,omitempty"`
			IssuedAt  int64  `json:"iat,omitempty"`
			Issuer    string `json:"iss,omitempty"`
			NotBefore int64  `json:"nbf,omitempty"`
			Subject   string `json:"sub,omitempty"`
		}
		func (c StandardClaims) Valid() error

		func (c *StandardClaims) VerifyAudience(cmp string, req bool) bool
		func (c *StandardClaims) VerifyExpiresAt(cmp int64, req bool) bool
		func (c *StandardClaims) VerifyIssuedAt(cmp int64, req bool) bool
		func (c *StandardClaims) VerifyIssuer(cmp string, req bool) bool
		func (c *StandardClaims) VerifyNotBefore(cmp int64, req bool) bool
			* ʹ�� cmp �� StandardClaims �ж�Ӧ���ֶν��бȽϡ�
			* req ��ʾ���Ƿ��Ǳ�����ڵ��ֶΡ�����ֶβ����ڣ�����true
	
	# type Token struct {
			Raw       string                 // The raw token.  Populated when you Parse a token
			Method    SigningMethod          // The signing method used or to be used
			Header    map[string]interface{} // The first segment of the token
			Claims    Claims                 // The second segment of the token
			Signature string                 // The third segment of the token.  Populated when you Parse a token
			Valid     bool                   // Is the token valid?  Populated when you Parse/Verify a token
		}
		func New(method SigningMethod) *Token
		func NewWithClaims(method SigningMethod, claims Claims) *Token
		func Parse(tokenString string, keyFunc Keyfunc) (*Token, error)
		func ParseWithClaims(tokenString string, claims Claims, keyFunc Keyfunc) (*Token, error)
		func (t *Token) SignedString(key interface{}) (string, error)
		func (t *Token) SigningString() (string, error)
	
	# type ValidationError struct {
			Inner  error  // stores the error returned by external dependencies, i.e.: KeyFunc
			Errors uint32 // bitfield.  see ValidationError... constants
			// contains filtered or unexported fields
		}
		func NewValidationError(errorText string, errorFlags uint32) *ValidationError
		func (e ValidationError) Error() string


------------------------
func
------------------------
	func DecodeSegment(seg string) ([]byte, error)
	func EncodeSegment(seg []byte) string
	func ParseECPrivateKeyFromPEM(key []byte) (*ecdsa.PrivateKey, error)
	func ParseECPublicKeyFromPEM(key []byte) (*ecdsa.PublicKey, error)
	func ParseRSAPrivateKeyFromPEM(key []byte) (*rsa.PrivateKey, error)
	func ParseRSAPrivateKeyFromPEMWithPassword(key []byte, password string) (*rsa.PrivateKey, error)
	func ParseRSAPublicKeyFromPEM(key []byte) (*rsa.PublicKey, error)
	func RegisterSigningMethod(alg string, f func() SigningMethod)


------------------------
HMAC-SHA ǩ����֤
------------------------
	import (
		"errors"
		"github.com/golang-jwt/jwt"
		"github.com/google/uuid"
		"strconv"
		"time"
	)

	// unverifiedParser ����֤�Ϸ��ԵĽ�����
	var unverifiedParser = &jwt.Parser{SkipClaimsValidation: true}

	// verifiedParser ��֤�Ϸ��ԵĽ�����
	var verifiedParser = new(jwt.Parser)

	// GenToken ����Token
	func GenToken(userId int, key []byte, expiresAt time.Time) (string, error) {
		now := time.Now()
		return jwt.NewWithClaims(jwt.SigningMethodHS512, &jwt.StandardClaims{
			// ����
			Audience: strconv.Itoa(userId),
			// ǩ����
			//Issuer:    "",
			// ����ʱ��
			ExpiresAt: expiresAt.Unix(),
			//Token Id
			Id: uuid.New().String(),
			// ����ʱ��
			IssuedAt: now.Unix(),
			// ��Чʱ��
			NotBefore: now.Unix(),
			// ����
			//Subject: "App Auth",
		}).SignedString(key)
	}

	// Decode ����JWT�������key����У��
	func Decode(signedString string, key []byte) (*jwt.StandardClaims, error) {
		token, err := verifiedParser.ParseWithClaims(signedString, &jwt.StandardClaims{}, func(token *jwt.Token) (i interface{}, err error) {
			return key, nil
		})
		if err != nil {
			// ���Token��Ч��KEY���ԣ������ˣ���δ��Ч���᷵��Error��
			return nil, err
		}
		if claims, ok := token.Claims.(*jwt.StandardClaims); ok && token.Valid {
			// ����StandardClaims
			// Token.Valid ��֤����ʱ������������磺����ʱ�䣨ExpiresAt����ǩ���ߣ�Issuer������Чʱ�䣨Not Before��
			// ��Ҫע����ǣ����û���κ������������У���Ȼ�ᱻ��Ϊ����Ч�ġ�
			return claims, nil
		}
		// �Ƿ�Token
		return nil, errors.New("bad token")
	}

	// ParseUnverified ��token�н��������ݣ����ǲ���У��
	func ParseUnverified(signedString string) (claim *jwt.StandardClaims, err error) {
		claim = &jwt.StandardClaims{}
		_, _, err = unverifiedParser.ParseUnverified(signedString, claim)
		return claim, err
	}
