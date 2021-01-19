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
		func (h Hash) Available() bool
		func (h Hash) HashFunc() Hash
		func (h Hash) New() hash.Hash
		func (h Hash) Size() int
		func (h Hash) String() string

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
	