--------------------
elliptic
--------------------

	# elliptic 包实现了质域上的标准 NIST P-224、P-256、P-384 和 P-521 椭圆曲线。
		* 除了使用 crypto/ecdsa 所需的 P224、P256、P384 和 P521 值外，已不再直接使用该软件包。
		* 大多数其他用途应转用更高效、更安全的 crypto/ecdh 或转用第三方模块来实现较低级别的功能。

--------------------
var
--------------------


--------------------
type
--------------------
	# type Curve interface {
			Params() *CurveParams
			IsOnCurve(x, y *big.Int) bool
			Add(x1, y1, x2, y2 *big.Int) (x, y *big.Int)
			Double(x1, y1 *big.Int) (x, y *big.Int)
			ScalarMult(x1, y1 *big.Int, k []byte) (x, y *big.Int)
			ScalarBaseMult(k []byte) (x, y *big.Int)
		}

		func P224() Curve
		func P256() Curve
		func P384() Curve
		func P521() Curve
	
	# type CurveParams struct {
			P       *big.Int // the order of the underlying field
			N       *big.Int // the order of the base point
			B       *big.Int // the constant of the curve equation
			Gx, Gy  *big.Int // (x,y) of the base point
			BitSize int      // the size of the underlying field
			Name    string   // the canonical name of the curve
		}

		func (curve *CurveParams) Add(x1, y1, x2, y2 *big.Int) (*big.Int, *big.Int)
		func (curve *CurveParams) Double(x1, y1 *big.Int) (*big.Int, *big.Int)
		func (curve *CurveParams) IsOnCurve(x, y *big.Int) bool
		func (curve *CurveParams) Params() *CurveParams
		func (curve *CurveParams) ScalarBaseMult(k []byte) (*big.Int, *big.Int)
		func (curve *CurveParams) ScalarMult(Bx, By *big.Int, k []byte) (*big.Int, *big.Int)

--------------------
func
--------------------

	func GenerateKey(curve Curve, rand io.Reader) (priv []byte, x, y *big.Int, err error)
	func Marshal(curve Curve, x, y *big.Int) []byte
	func MarshalCompressed(curve Curve, x, y *big.Int) []byte
	func Unmarshal(curve Curve, data []byte) (x, y *big.Int)
	func UnmarshalCompressed(curve Curve, data []byte) (x, y *big.Int)