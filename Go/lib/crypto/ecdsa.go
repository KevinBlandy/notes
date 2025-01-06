---------------------------
ecdsa
---------------------------
	# ecdsa 实现了 FIPS 186-4 和 SEC 1 2.0 版中定义的椭圆曲线数字签名算法（在ECC原理上实现的签名方法）。

---------------------------
var
---------------------------

---------------------------
type
---------------------------

	# type PrivateKey struct {
			PublicKey
			D *big.Int
		}

		func GenerateKey(c elliptic.Curve, rand io.Reader) (*PrivateKey, error)
			* 生成密钥对
			
		func (k *PrivateKey) ECDH() (*ecdh.PrivateKey, error)
		func (priv *PrivateKey) Equal(x crypto.PrivateKey) bool
		func (priv *PrivateKey) Public() crypto.PublicKey
		func (priv *PrivateKey) Sign(rand io.Reader, digest []byte, opts crypto.SignerOpts) ([]byte, error)
	
	# type PublicKey struct {
			elliptic.Curve
			X, Y *big.Int
		}
		func (k *PublicKey) ECDH() (*ecdh.PublicKey, error)
		func (pub *PublicKey) Equal(x crypto.PublicKey) bool
	

---------------------------
func
---------------------------

	func Sign(rand io.Reader, priv *PrivateKey, hash []byte) (r, s *big.Int, err error)
	func SignASN1(rand io.Reader, priv *PrivateKey, hash []byte) ([]byte, error)
	func Verify(pub *PublicKey, hash []byte, r, s *big.Int) bool
	func VerifyASN1(pub *PublicKey, hash, sig []byte) bool
