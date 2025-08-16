------------------------
crypto
------------------------

------------------------
var
------------------------

------------------------
type
------------------------
	# type Decrypter interface {
			Public() PublicKey
			Decrypt(rand io.Reader, msg []byte, opts DecrypterOpts) (plaintext []byte, err error)
		}
	
	# type DecrypterOpts interface{}

	# type Hash uint
		
		* 表示 Hash 算法
		
		func (h Hash) Available() bool
		func (h Hash) HashFunc() Hash
		func (h Hash) New() hash.Hash
		func (h Hash) Size() int
		func (h Hash) String() string
		
		const (
			MD4         Hash = 1 + iota // import golang.org/x/crypto/md4
			MD5                         // import crypto/md5
			SHA1                        // import crypto/sha1
			SHA224                      // import crypto/sha256
			SHA256                      // import crypto/sha256
			SHA384                      // import crypto/sha512
			SHA512                      // import crypto/sha512
			MD5SHA1                     // no implementation; MD5+SHA1 used for TLS RSA
			RIPEMD160                   // import golang.org/x/crypto/ripemd160
			SHA3_224                    // import golang.org/x/crypto/sha3
			SHA3_256                    // import golang.org/x/crypto/sha3
			SHA3_384                    // import golang.org/x/crypto/sha3
			SHA3_512                    // import golang.org/x/crypto/sha3
			SHA512_224                  // import crypto/sha512
			SHA512_256                  // import crypto/sha512
			BLAKE2s_256                 // import golang.org/x/crypto/blake2s
			BLAKE2b_256                 // import golang.org/x/crypto/blake2b
			BLAKE2b_384                 // import golang.org/x/crypto/blake2b
			BLAKE2b_512                 // import golang.org/x/crypto/blake2b
		)
		
		
	# type MessageSigner interface {
			Signer
			SignMessage(rand io.Reader, msg []byte, opts SignerOpts) (signature []byte, err error)
		}

	# type PrivateKey interface{}
	# type PublicKey interface{}
	# type Signer interface {
			Public() PublicKey
			Sign(rand io.Reader, digest []byte, opts SignerOpts) (signature []byte, err error)
		}
	
	# type SignerOpts interface {
			HashFunc() Hash
		}
	
------------------------
func
------------------------
	func RegisterHash(h Hash, f func() hash.Hash)
	