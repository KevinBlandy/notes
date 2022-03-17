----------------------
rsa
----------------------
----------------------
var
----------------------
	const (
		PSSSaltLengthAuto = 0
		PSSSaltLengthEqualsHash = -1
	)

	var ErrDecryption = errors.New("crypto/rsa: decryption error")
	var ErrMessageTooLong = errors.New("crypto/rsa: message too long for RSA public key size")
	var ErrVerification = errors.New("crypto/rsa: verification error")

----------------------
type
----------------------
	# type CRTValue struct {
			Exp   *big.Int // D mod (prime-1).
			Coeff *big.Int // R·Coeff ≡ 1 mod Prime.
			R     *big.Int // product of primes prior to this (inc p and q).
		}
	
	# type OAEPOptions struct {
			Hash crypto.Hash
			Label []byte
		}
	
	# type PKCS1v15DecryptOptions struct {
			SessionKeyLen int
		}
	
	# type PSSOptions struct {
			SaltLength int
			Hash crypto.Hash
		}
		func (opts *PSSOptions) HashFunc() crypto.Hash
	
	# type PrecomputedValues struct {
			Dp, Dq *big.Int // D mod (P-1) (or mod Q-1)
			Qinv   *big.Int // Q^-1 mod P
			CRTValues []CRTValue
		}

	# type PrivateKey struct {
			PublicKey            // public part.
				* 公钥

			D         *big.Int   // private exponent
			Primes    []*big.Int // prime factors of N, has >= 2 elements.
			Precomputed PrecomputedValues
		}

		* RSA私钥，继承了公钥

		func GenerateKey(random io.Reader, bits int) (*PrivateKey, error)
			* 生成随机RSA密钥，指定密钥长度
				rsa.GenerateKey(rand.Reader, 256)
			* 源码
				return GenerateMultiPrimeKey(random, 2, bits)

		func GenerateMultiPrimeKey(random io.Reader, nprimes int, bits int) (*PrivateKey, error)
			* 生成RSA密钥
				
		func (priv *PrivateKey) Decrypt(rand io.Reader, ciphertext []byte, opts crypto.DecrypterOpts) (plaintext []byte, err error)
		func (priv *PrivateKey) Equal(x crypto.PrivateKey) bool
		func (priv *PrivateKey) Precompute()
		func (priv *PrivateKey) Public() crypto.PublicKey
			* 返回公钥，类型是 crypto.PublicKey

		func (priv *PrivateKey) Sign(rand io.Reader, digest []byte, opts crypto.SignerOpts) ([]byte, error)
		func (priv *PrivateKey) Validate() error
	
	# type PublicKey struct {
			N *big.Int // modulus
			E int      // public exponent
		}

		* RSA公钥

		func (pub *PublicKey) Equal(x crypto.PublicKey) bool
		func (pub *PublicKey) Size() int

----------------------
func
----------------------
	func EncryptOAEP(hash hash.Hash, random io.Reader, pub *PublicKey, msg []byte, label []byte) ([]byte, error)
	func DecryptOAEP(hash hash.Hash, random io.Reader, priv *PrivateKey, ciphertext []byte, ...) ([]byte, error)

	func EncryptPKCS1v15(rand io.Reader, pub *PublicKey, msg []byte) ([]byte, error)
		* RSA 公钥加密
	
	func DecryptPKCS1v15(rand io.Reader, priv *PrivateKey, ciphertext []byte) ([]byte, error)
		* RSA私钥解密

	func DecryptPKCS1v15SessionKey(rand io.Reader, priv *PrivateKey, ciphertext []byte, key []byte) error

	func SignPKCS1v15(rand io.Reader, priv *PrivateKey, hash crypto.Hash, hashed []byte) ([]byte, error)
	func SignPSS(rand io.Reader, priv *PrivateKey, hash crypto.Hash, digest []byte, ...) ([]byte, error)

	func VerifyPKCS1v15(pub *PublicKey, hash crypto.Hash, hashed []byte, sig []byte) error
	func VerifyPSS(pub *PublicKey, hash crypto.Hash, digest []byte, sig []byte, opts *PSSOptions) error

----------------------
rsa
----------------------
	# 加密解密
		package main

		import (
			"crypto/rand"
			"crypto/rsa"
			"encoding/hex"
			"log"
			"os"
		)

		func init() {
			log.Default().SetOutput(os.Stdout)
			log.Default().SetFlags(log.Ldate | log.Ltime | log.Lshortfile)
		}

		func main() {

			// 生成私钥
			privateKey, err := rsa.GenerateKey(rand.Reader, 512)
			if err != nil {
				log.Fatalln(err.Error())
			}

			content := []byte("Hello 世界")

			// 公钥加密
			cipher, err := rsa.EncryptPKCS1v15(rand.Reader, &privateKey.PublicKey, content)
			if err != nil {
				log.Fatalln(err.Error())
			}

			log.Printf("cipher: %s\n", hex.EncodeToString(cipher))

			// 公钥解密
			raw, err := rsa.DecryptPKCS1v15(rand.Reader, privateKey, cipher)
			if err != nil {
				log.Fatalln(err.Error())
			}

			log.Printf("result: %s\n", string(raw))
		}
	
	# 签名验证
		TODO
			