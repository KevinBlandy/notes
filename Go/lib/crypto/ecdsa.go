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

			* elliptic.Curve 表示曲线，标准库支持多种常见曲线
				elliptic.P224()
				elliptic.P256()
				elliptic.P384()
				elliptic.P521()

		func ParseRawPrivateKey(curve elliptic.Curve, data []byte) (*PrivateKey, error)

		func (priv *PrivateKey) Bytes() ([]byte, error)
		func (k *PrivateKey) ECDH() (*ecdh.PrivateKey, error)
		func (priv *PrivateKey) Equal(x crypto.PrivateKey) bool
		func (priv *PrivateKey) Public() crypto.PublicKey
		func (priv *PrivateKey) Sign(rand io.Reader, digest []byte, opts crypto.SignerOpts) ([]byte, error)
	
	# type PublicKey struct {
			elliptic.Curve
			X, Y *big.Int
		}

		
		func ParseUncompressedPublicKey(curve elliptic.Curve, data []byte) (*PublicKey, error)

		func (pub *PublicKey) Bytes() ([]byte, error)
		func (k *PublicKey) ECDH() (*ecdh.PublicKey, error)
		func (pub *PublicKey) Equal(x crypto.PublicKey) bool
	

---------------------------
func
---------------------------

	func Sign(rand io.Reader, priv *PrivateKey, hash []byte) (r, s *big.Int, err error)
	func SignASN1(rand io.Reader, priv *PrivateKey, hash []byte) ([]byte, error)
	func Verify(pub *PublicKey, hash []byte, r, s *big.Int) bool
	func VerifyASN1(pub *PublicKey, hash, sig []byte) bool


---------------------------
Demo
---------------------------

	# ECC 密钥生成/压缩/推导
		package main

		import (
			"crypto/ecdsa"
			"crypto/elliptic"
			"crypto/rand"
			"encoding/hex"
			"fmt"
		)

		func main() {

			// 椭圆曲线算法
			curve := elliptic.P256()

			privateKey, err := ecdsa.GenerateKey(curve, rand.Reader)
			if err != nil {
				panic(err)
			}

			publicKey := privateKey.PublicKey

			// 私钥
			fmt.Printf("Private Key: %x\n", privateKey.D)
			// 公钥的 x 坐标
			fmt.Printf("Public Key X: %x\n", publicKey.X)
			// 公钥的 y 坐标
			fmt.Printf("Public Key Y: %x\n", publicKey.Y)

			// 压缩公钥为 33 字节
			content := elliptic.MarshalCompressed(curve, publicKey.X, publicKey.Y)
			fmt.Printf("压缩公钥：len=%d content=%s\n", len(content), hex.EncodeToString(content))

			// 解压缩公钥
			x, y := elliptic.UnmarshalCompressed(curve, content)
			// 公钥的 x 坐标
			fmt.Printf("Public Key X: %x\n", x)
			// 公钥的 y 坐标
			fmt.Printf("Public Key Y: %x\n", y)

			// 根据私钥推导出公钥
			x, y = curve.ScalarBaseMult(privateKey.D.Bytes())
			// 公钥的 x 坐标
			fmt.Printf("Public Key X: %x\n", x)
			// 公钥的 y 坐标
			fmt.Printf("Public Key Y: %x\n", y)
		}
