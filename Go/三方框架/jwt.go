------------------------
JWT
------------------------
	# Doc
		https://pkg.go.dev/github.com/golang-jwt/jwt/v5
		
	# import
		"github.com/golang-jwt/jwt/v5"

------------------------
var
------------------------
	var (
		ErrNotECPublicKey  = errors.New("key is not a valid ECDSA public key")
		ErrNotECPrivateKey = errors.New("key is not a valid ECDSA private key")
	)

	var (
		ErrNotEdPrivateKey = errors.New("key is not a valid Ed25519 private key")
		ErrNotEdPublicKey  = errors.New("key is not a valid Ed25519 public key")
	)

	var (
		ErrInvalidKey                = errors.New("key is invalid")
		ErrInvalidKeyType            = errors.New("key is of invalid type")
		ErrHashUnavailable           = errors.New("the requested hash function is unavailable")
		ErrTokenMalformed            = errors.New("token is malformed")
		ErrTokenUnverifiable         = errors.New("token is unverifiable")
		ErrTokenSignatureInvalid     = errors.New("token signature is invalid")
		ErrTokenRequiredClaimMissing = errors.New("token is missing required claim")
		ErrTokenInvalidAudience      = errors.New("token has invalid audience")
		ErrTokenExpired              = errors.New("token is expired")
		ErrTokenUsedBeforeIssued     = errors.New("token used before issued")
		ErrTokenInvalidIssuer        = errors.New("token has invalid issuer")
		ErrTokenInvalidSubject       = errors.New("token has invalid subject")
		ErrTokenNotValidYet          = errors.New("token is not valid yet")
		ErrTokenInvalidId            = errors.New("token has invalid id")
		ErrTokenInvalidClaims        = errors.New("token has invalid claims")
		ErrInvalidType               = errors.New("invalid type for claim")
	)


	var (
		ErrKeyMustBePEMEncoded = errors.New("invalid key: Key must be a PEM encoded PKCS1 or PKCS8 key")
		ErrNotRSAPrivateKey    = errors.New("key is not a valid RSA private key")
		ErrNotRSAPublicKey     = errors.New("key is not a valid RSA public key")
	)

	var (
		// Sadly this is missing from crypto/ecdsa compared to crypto/rsa
		ErrECDSAVerification = errors.New("crypto/ecdsa: verification error")
	)

	var (
		ErrEd25519Verification = errors.New("ed25519: verification error")
	)

	var MarshalSingleStringAsArray = true

	var NoneSignatureTypeDisallowedError error

	var SigningMethodNone *signingMethodNone

	var TimePrecision = time.Second




------------------------
type
------------------------


------------------------
func
------------------------
	func GetAlgorithms() (algs []string)
	func ParseECPrivateKeyFromPEM(key []byte) (*ecdsa.PrivateKey, error)
	func ParseECPublicKeyFromPEM(key []byte) (*ecdsa.PublicKey, error)
	func ParseEdPrivateKeyFromPEM(key []byte) (crypto.PrivateKey, error)
	func ParseEdPublicKeyFromPEM(key []byte) (crypto.PublicKey, error)
	func ParseRSAPrivateKeyFromPEM(key []byte) (*rsa.PrivateKey, error)
	func ParseRSAPrivateKeyFromPEMWithPassword(key []byte, password string) (*rsa.PrivateKey, error)deprecated
	func ParseRSAPublicKeyFromPEM(key []byte) (*rsa.PublicKey, error)
	func RegisterSigningMethod(alg string, f func() SigningMethod)

------------------------
HMAC-SHA 签发验证
------------------------
		package auth

		import (
			"errors"
			"github.com/golang-jwt/jwt/v5"
			"strconv"
		)

		// encode 生成Token
		func encode(id string, subject int64, key []byte) (string, error) {
			return jwt.NewWithClaims(jwt.SigningMethodHS512, &jwt.RegisteredClaims{
				//Token Id
				ID: id,
				// 用户 ID
				Subject: strconv.FormatInt(subject, 10),
				// 过期时间
				//ExpiresAt: expiresAt.Unix(),
			}).SignedString(key)
		}

		// decode 解析JWT，会根据key进行校验
		func decode(signedString string, key []byte) (*jwt.RegisteredClaims, error) {
			token, err := jwt.NewParser().ParseWithClaims(signedString, &jwt.RegisteredClaims{}, func(token *jwt.Token) (i interface{}, err error) {
				return key, nil
			})
			if err != nil {
				// 如果Token无效（KEY不对，过期了，还未生效都会返回Error）
				return nil, err
			}
			if claims, ok := token.Claims.(*jwt.RegisteredClaims); ok && token.Valid {
				// 返回StandardClaims
				// Token.Valid 验证基于时间的声明，例如：过期时间（ExpiresAt）、签发者（Issuer）、生效时间（Not Before）
				// 需要注意的是，如果没有任何声明在令牌中，仍然会被认为是有效的。
				return claims, nil
			}
			// 非法Token
			return nil, errors.New("bad token")
		}
