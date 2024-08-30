---------------------------
big
---------------------------
	# ʵ�������⾫������(������)֧��������������:
		Float	Ĭ��ֵ0��������
			
		Int		Ĭ��ֵ0������
		Rat		Ĭ��ֵ0/1����������������
	
	# ��ʼ��ֵ�ļ��ַ�ʽ
		// ʹ�ù����������г�ʼ������Ҫָ�� int / float ����
		number := big.NewInt(21321)
		
		// �ֶ����������ʹ���ַ���������ʽ������ʼ��
		// ���ַ�ʽ��Ҫ���ݷ���ֵ�ж��Ƿ�Ok
		number, ok := new(big.Int).SetString("1999321392131221423432432532", 10)
	
	# JSON ���л��ͷ����л�

		* Int Ĭ�����л�Ϊ int
		* Float Ĭ�����л�Ϊ string

		type Foo struct {
			Amount  *big.Int   `json:"amount"`
			Balance *big.Float `json:"balance"`
		}

		func main() {
			j, _ := json.MarshalIndent(&Foo{Amount: new(big.Int).SetInt64(8832423), Balance: new(big.Float).SetFloat64(8843.442333)}, "", "	")
			fmt.Println(string(j))
		}

		{
			"amount": 8832423,
			"balance": "8843.442333"
		}
	
	# JDBC


---------------------------
����
---------------------------
	const (
		MaxExp  = math.MaxInt32  // ���֧��ָ��
		MinExp  = math.MinInt32  // ��С֧��ָ��
		MaxPrec = math.MaxUint32 // ��������ϣ�֧�־��ȣ��������ڴ�����
	)

	const MaxBase = 10 + ('z' - 'a' + 1) + ('Z' - 'A' + 1)
		* �ַ���ת���������ܵ�������



---------------------------
type
---------------------------
	# type Accuracy int8
		func (i Accuracy) String() string
		
		* Ԥ���峣��
			const (
				Below Accuracy = -1
				Exact Accuracy = 0
				Above Accuracy = +1
			)
		
	# type ErrNaN struct
		func (err ErrNaN) Error() string

		* �����ִ���
	
	# type Float struct

		func NewFloat(x float64) *Float
		func ParseFloat(s string, base int, prec uint, mode RoundingMode) (f *Float, b int, err error)
			* ������ f.Parse(s,base)��f ����Ϊ�������Ⱥ���������ģʽ��

		func (z *Float) Abs(x *Float) *Float
		func (x *Float) Acc() Accuracy
		func (z *Float) Add(x, y *Float) *Float
		func (x *Float) Append(buf []byte, fmt byte, prec int) []byte
		func (x *Float) Cmp(y *Float) int
			* �Ƚ� x �� y �Ĵ�С
				x > y ���� 1
				x < y ���� -1
				x = y ���� 0

		func (z *Float) Copy(x *Float) *Float
		func (x *Float) Float32() (float32, Accuracy)
		func (x *Float) Float64() (float64, Accuracy)
		func (x *Float) Format(s fmt.State, format rune)
		func (z *Float) GobDecode(buf []byte) error
		func (x *Float) GobEncode() ([]byte, error)
		func (x *Float) Int(z *Int) (*Int, Accuracy)
		func (x *Float) Int64() (int64, Accuracy)
		func (x *Float) IsInf() bool
		func (x *Float) IsInt() bool
		func (x *Float) MantExp(mant *Float) (exp int)
		func (x *Float) MarshalText() (text []byte, err error)
		func (x *Float) MinPrec() uint
		func (x *Float) Mode() RoundingMode
		func (z *Float) Mul(x, y *Float) *Float
		func (z *Float) Neg(x *Float) *Float
		func (z *Float) Parse(s string, base int) (f *Float, b int, err error)
		func (x *Float) Prec() uint
		func (z *Float) Quo(x, y *Float) *Float
			* ����

		func (x *Float) Rat(z *Rat) (*Rat, Accuracy)
		func (z *Float) Scan(s fmt.ScanState, ch rune) error

		func (z *Float) Set(x *Float) *Float
		func (z *Float) SetFloat64(x float64) *Float
		func (z *Float) SetInf(signbit bool) *Float
		func (z *Float) SetInt(x *Int) *Float
		func (z *Float) SetInt64(x int64) *Float
		func (z *Float) SetMantExp(mant *Float, exp int) *Float
		func (z *Float) SetMode(mode RoundingMode) *Float
		func (z *Float) SetPrec(prec uint) *Float
			* �� z �ľ�������Ϊ prec�������ؾ��������ܣ������ z ֵ�����β���޷��ڲ���ʧ���ȵ�������� prec λ��ʾ������� z ������ģʽ�������롣
			* SetPrec(0) ����������ֵӳ��Ϊ ��0������ֵ���ֲ��䡣��� prec > MaxPrec��������Ϊ MaxPrec��

			
		func (z *Float) SetRat(x *Rat) *Float
		func (z *Float) SetString(s string) (*Float, bool)
		func (z *Float) SetUint64(x uint64) *Float
			* ����ֵ

		func (x *Float) Sign() int
		func (x *Float) Signbit() bool
		func (z *Float) Sqrt(x *Float) *Float
		func (x *Float) String() string
		func (z *Float) Sub(x, y *Float) *Float
		func (x *Float) Text(format byte, prec int) string
		func (x *Float) Uint64() (uint64, Accuracy)
		func (z *Float) UnmarshalText(text []byte) error
	
	# type Int struct 
		func NewInt(x int64) *Int
		func (z *Int) Abs(x *Int) *Int
		func (z *Int) Add(x, y *Int) *Int
		func (z *Int) And(x, y *Int) *Int
		func (z *Int) AndNot(x, y *Int) *Int
		func (x *Int) Append(buf []byte, base int) []byte
		func (z *Int) Binomial(n, k int64) *Int
		func (x *Int) Bit(i int) uint
		func (x *Int) BitLen() int
		func (x *Int) Bits() []Word
		func (x *Int) Bytes() []byte
		func (x *Int) Cmp(y *Int) (r int)
		func (x *Int) CmpAbs(y *Int) int
		func (z *Int) Div(x, y *Int) *Int
		func (z *Int) DivMod(x, y, m *Int) (*Int, *Int)
		func (z *Int) Exp(x, y, m *Int) *Int
		func (x *Int) FillBytes(buf []byte) []byte
		func (x *Int) Float64() (float64, Accuracy)
		func (x *Int) Format(s fmt.State, ch rune)
		func (z *Int) GCD(x, y, a, b *Int) *Int
		func (z *Int) GobDecode(buf []byte) error
		func (x *Int) GobEncode() ([]byte, error)
		func (x *Int) Int64() int64
		func (x *Int) IsInt64() bool
		func (x *Int) IsUint64() bool
		func (z *Int) Lsh(x *Int, n uint) *Int
		func (x *Int) MarshalJSON() ([]byte, error)
		func (x *Int) MarshalText() (text []byte, err error)
		func (z *Int) Mod(x, y *Int) *Int
		func (z *Int) ModInverse(g, n *Int) *Int
		func (z *Int) ModSqrt(x, p *Int) *Int
		func (z *Int) Mul(x, y *Int) *Int
		func (z *Int) MulRange(a, b int64) *Int
		func (z *Int) Neg(x *Int) *Int
		func (z *Int) Not(x *Int) *Int
		func (z *Int) Or(x, y *Int) *Int
		func (x *Int) ProbablyPrime(n int) bool
		func (z *Int) Quo(x, y *Int) *Int
		func (z *Int) QuoRem(x, y, r *Int) (*Int, *Int)
		func (z *Int) Rand(rnd *rand.Rand, n *Int) *Int
		func (z *Int) Rem(x, y *Int) *Int
		func (z *Int) Rsh(x *Int, n uint) *Int
		func (z *Int) Scan(s fmt.ScanState, ch rune) error
		func (z *Int) Set(x *Int) *Int
		func (z *Int) SetBit(x *Int, i int, b uint) *Int
		func (z *Int) SetBits(abs []Word) *Int
		func (z *Int) SetBytes(buf []byte) *Int
		func (z *Int) SetInt64(x int64) *Int
		func (z *Int) SetString(s string, base int) (*Int, bool)
		func (z *Int) SetUint64(x uint64) *Int
		func (x *Int) Sign() int
		func (z *Int) Sqrt(x *Int) *Int
		func (x *Int) String() string
		func (z *Int) Sub(x, y *Int) *Int
		func (x *Int) Text(base int) string
		func (x *Int) TrailingZeroBits() uint
		func (x *Int) Uint64() uint64
		func (z *Int) UnmarshalJSON(text []byte) error
		func (z *Int) UnmarshalText(text []byte) error
		func (z *Int) Xor(x, y *Int) *Int
	
	# type Rat struct
		func NewRat(a, b int64) *Rat
			* ʹ�÷���a�ͷ�ĸb����һ��Rat
			* ������

		func (z *Rat) Abs(x *Rat) *Rat
		func (z *Rat) Add(x, y *Rat) *Rat
		func (x *Rat) Cmp(y *Rat) int
		func (x *Rat) Denom() *Int
		func (x *Rat) Float32() (f float32, exact bool)
		func (x *Rat) Float64() (f float64, exact bool)
		func (x *Rat) FloatString(prec int) string
		func (x *Rat) FloatPrec() (n int, exact bool)
		func (z *Rat) GobDecode(buf []byte) error
		func (x *Rat) GobEncode() ([]byte, error)
		func (z *Rat) Inv(x *Rat) *Rat
		func (x *Rat) IsInt() bool
		func (x *Rat) MarshalText() (text []byte, err error)
		func (z *Rat) Mul(x, y *Rat) *Rat
		func (z *Rat) Neg(x *Rat) *Rat
		func (x *Rat) Num() *Int
		func (z *Rat) Quo(x, y *Rat) *Rat
		func (x *Rat) RatString() string
		func (z *Rat) Scan(s fmt.ScanState, ch rune) error
		func (z *Rat) Set(x *Rat) *Rat
		func (z *Rat) SetFloat64(f float64) *Rat
		func (z *Rat) SetFrac(a, b *Int) *Rat
		func (z *Rat) SetFrac64(a, b int64) *Rat
		func (z *Rat) SetInt(x *Int) *Rat
		func (z *Rat) SetInt64(x int64) *Rat
		func (z *Rat) SetString(s string) (*Rat, bool)
		func (z *Rat) SetUint64(x uint64) *Rat
		func (x *Rat) Sign() int
		func (x *Rat) String() string
		func (z *Rat) Sub(x, y *Rat) *Rat
		func (z *Rat) UnmarshalText(text []byte) error
	
	# type RoundingMode byte

		* ����ģʽ

		func (i RoundingMode) String() string

		* Ԥ����
			const (
				ToNearestEven RoundingMode = iota // == IEEE 754-2008 roundTiesToEven
				ToNearestAway                     // == IEEE 754-2008 roundTiesToAway
				ToZero                            // == IEEE 754-2008 roundTowardZero
				AwayFromZero                      // no IEEE 754-2008 equivalent
				ToNegativeInf                     // == IEEE 754-2008 roundTowardNegative
				ToPositiveInf                     // == IEEE 754-2008 roundTowardPositive
			)
		
		* ��������

			   x  ToNearestEven  ToNearestAway  ToZero  AwayFromZero  ToNegativeInf  ToPositiveInf
			 2.6              3              3       2             3              2              3
			 2.5              2              3       2             3              2              3
			 2.1              2              2       2             3              2              3
			-2.1             -2             -2      -2            -3             -3             -2
			-2.5             -2             -3      -2            -3             -3             -2
			-2.6             -3             -3      -2            -3             -3             -2

	# type Word uint
		* ����һ���ྫ���޷��������ĵ�������

---------------------------
����
---------------------------
	func Jacobi(x, y *Int) int

---------------------------
demo
---------------------------
