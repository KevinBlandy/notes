---------------
cipher
---------------


---------------
var
---------------


---------------
type
---------------
	# type AEAD interface {
			NonceSize() int
			Overhead() int
			Seal(dst, nonce, plaintext, additionalData []byte) []byte
			Open(dst, nonce, ciphertext, additionalData []byte) ([]byte, error)
		}
		func NewGCM(cipher Block) (AEAD, error)
		func NewGCMWithNonceSize(cipher Block, size int) (AEAD, error)
		func NewGCMWithTagSize(cipher Block, tagSize int) (AEAD, error)
	
	# type Block interface {
			BlockSize() int
			Encrypt(dst, src []byte)
			Decrypt(dst, src []byte)
		}
	
	# type BlockMode interface {
			BlockSize() int
			CryptBlocks(dst, src []byte)
		}
		func NewCBCDecrypter(b Block, iv []byte) BlockMode
		func NewCBCEncrypter(b Block, iv []byte) BlockMode
	
	# type Stream interface {
			XORKeyStream(dst, src []byte)
		}
	
		func NewCFBDecrypter(block Block, iv []byte) Stream
		func NewCFBEncrypter(block Block, iv []byte) Stream
		func NewCTR(block Block, iv []byte) Stream
		func NewOFB(b Block, iv []byte) Stream
	
	# type StreamReader struct {
			S Stream
			R io.Reader
		}
		func (r StreamReader) Read(dst []byte) (n int, err error)
	
	# type StreamWriter struct {
			S   Stream
			W   io.Writer
			Err error // unused
		}
		func (w StreamWriter) Close() error
		func (w StreamWriter) Write(src []byte) (n int, err error)
	
---------------
func
---------------
