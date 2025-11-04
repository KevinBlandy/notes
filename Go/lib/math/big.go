---------------------------
big
---------------------------
	# 实现了任意精度算术(超大数)支持以下数字类型:
		Float	默认值0，浮点数
			
		Int		默认值0，整数
		Rat		默认值0/1，有理数（分数）
	
	# 初始化值的几种方式
		// 使用工厂方法进行初始化，需要指定 int / float 数据
		number := big.NewInt(21321)
		
		// 手动创建对象后，使用字符串调用链式方法初始化
		// 这种方式需要根据返回值判断是否Ok
		number, ok := new(big.Int).SetString("1999321392131221423432432532", 10)
	
	# JSON 序列化和反序列化

		* Int 默认序列化为 int
		* Float 默认序列化为 string

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
变量
---------------------------
	const (
		MaxExp  = math.MaxInt32  // 最大支持指数
		MinExp  = math.MinInt32  // 最小支持指数
		MaxPrec = math.MaxUint32 // 最大（理论上）支持精度；可能受内存限制 4294967295
	)

	const MaxBase = 10 + ('z' - 'a' + 1) + ('Z' - 'A' + 1)
		* 字符串转换函数接受的最大进制



---------------------------
type
---------------------------
	# type Accuracy int8
		func (i Accuracy) String() string


		* 描述了最近一次生成浮点数值的操作相对于精确数值所产生的舍入误差。
		
		* 预定义常量
			const (
				Below Accuracy = -1		// 结果小于真实值（被截断或向下舍入）
				Exact Accuracy = 0		// 结果是精确的
				Above Accuracy = +1		// 结果大于真实值（被向上舍入）
			)
		
	# type ErrNaN struct
		func (err ErrNaN) Error() string

		* 非数字错误
	
	# type Float struct

		func NewFloat(x float64) *Float
		func ParseFloat(s string, base int, prec uint, mode RoundingMode) (f *Float, b int, err error)
			* 类似于 f.Parse(s,base)，f 设置为给定精度和四舍五入模式。

		func (z *Float) Abs(x *Float) *Float
		func (x *Float) Acc() Accuracy
		func (z *Float) Add(x, y *Float) *Float
		func (x *Float) Append(buf []byte, fmt byte, prec int) []byte
		func (x *Float) Cmp(y *Float) int
			* 比较 x 和 y 的大小
				x > y 返回 1
				x < y 返回 -1
				x = y 返回 0

		func (z *Float) Copy(x *Float) *Float
		func (x *Float) Float32() (float32, Accuracy)
		func (x *Float) Float64() (float64, Accuracy)
		func (x *Float) Format(s fmt.State, format rune)
		func (z *Float) GobDecode(buf []byte) error
		func (x *Float) GobEncode() ([]byte, error)
		func (x *Float) Int(z *Int) (*Int, Accuracy)
			* 截断 x 的小数部分（向零取整），如果 x 是无穷大，则返回 nil。
			* 根据返回的 Accuracy 可以判断截断后的值是往下、上截断了，还是和原始值一样。
			* 参数可以传递 nil，改方法会自动创建新的实例返回
				// 预定义整数
				var number = &big.Int{}
				// 舍弃小数，返回整数
				result, accuracy := new(big.Float).SetFloat64(9.99999).Int(number)
				// 根据 accuracy 判断截断后的值和原始值的大小
				fmt.Println(result, accuracy) // 9 Below
				// 返回的整数就是传入的参数
				fmt.Println(result == number) // true


		func (x *Float) Int64() (int64, Accuracy)
		func (x *Float) IsInf() bool
		func (x *Float) IsInt() bool
			* 报告 x 是否为整数。

		func (x *Float) MantExp(mant *Float) (exp int)
		func (x *Float) MarshalText() (text []byte, err error)
		func (x *Float) MinPrec() uint
		func (x *Float) Mode() RoundingMode
		func (z *Float) Mul(x, y *Float) *Float
		func (z *Float) Neg(x *Float) *Float
		func (z *Float) Parse(s string, base int) (f *Float, b int, err error)
		func (x *Float) Prec() uint
		func (z *Float) Quo(x, y *Float) *Float
			* 除法

		func (x *Float) Rat(z *Rat) (*Rat, Accuracy)
		func (z *Float) Scan(s fmt.ScanState, ch rune) error

		func (z *Float) Set(x *Float) *Float
		func (z *Float) SetFloat64(x float64) *Float
		func (z *Float) SetInf(signbit bool) *Float
		func (z *Float) SetInt(x *Int) *Float
			* 设置为 *big.Int 的值

		func (z *Float) SetInt64(x int64) *Float
		func (z *Float) SetMantExp(mant *Float, exp int) *Float
		func (z *Float) SetMode(mode RoundingMode) *Float
		func (z *Float) SetPrec(prec uint) *Float
			* 设置浮点数计算的精度。它用于指定 big.Float 类型对象在计算时使用的二进制精度（以位为单位）。
			* 默认精度值在 Go 中是 64 位二进制精度。这相当于标准的 float64 的精度（约为 16 位十进制有效数字）。
			* SetPrec(0) 将所有有限值映射为 ±0；无限值保持不变。如果 prec > MaxPrec，则设置为 MaxPrec。

			
		func (z *Float) SetRat(x *Rat) *Float
		func (z *Float) SetString(s string) (*Float, bool)
		func (z *Float) SetUint64(x uint64) *Float
			* 设置值

		func (x *Float) Sign() int
		func (x *Float) Signbit() bool
		func (z *Float) Sqrt(x *Float) *Float
		func (x *Float) String() string
		func (z *Float) Sub(x, y *Float) *Float
		func (x *Float) Text(format byte, prec int) string
			* 格式化为字符串

				format 指定格式
					'f' 无指数
				
				prec 指定精度，根据 format 不同意义不同
					对于 'e'、'E'、'f' 和 'x'，它是小数点后的位数
					对于 'g'、'G'，则是总位数。
			
			* demo
				f.Text('f', 100) // 输出为字符串，保留 100 位小数，不足的话后面补 0
			
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
			* 返回大数对应的二进制数组

		func (x *Int) Cmp(y *Int) (r int)
		func (x *Int) CmpAbs(y *Int) int
		func (z *Int) Div(x, y *Int) *Int
		func (z *Int) DivMod(x, y, m *Int) (*Int, *Int)
		func (z *Int) Exp(x, y, m *Int) *Int
			* 指数计算，计算 x 的 y 次方是多少
			* Exp 函数计算 z = x**y mod |m|（忽略 m 的符号）并返回 z。其具体行为如下：
			* 当 m == nil 或 m == 0 时：
				若 y > 0 则 z = x**y
				若 y <= 0 则 z = 1
			* 当 m != 0 且 y < 0 时：
				* 若 x 与 m 互质则正常计算
				* 若非互质则保持 z 不变并返回 nil


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
			* 设置二进制形式表示的大数

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
			* 使用分子a和分母b创建一个Rat
			* 有理数

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

		* 舍入模式

		func (i RoundingMode) String() string

		* 预定义
			const (
				ToNearestEven RoundingMode = iota // == IEEE 754-2008 roundTiesToEven
				ToNearestAway                     // == IEEE 754-2008 roundTiesToAway
				ToZero                            // == IEEE 754-2008 roundTowardZero
					* 直接丢弃多余的位

				AwayFromZero                      // no IEEE 754-2008 equivalent
				ToNegativeInf                     // == IEEE 754-2008 roundTowardNegative
				ToPositiveInf                     // == IEEE 754-2008 roundTowardPositive
			)
		
		* 舍入如下

			   x  ToNearestEven  ToNearestAway  ToZero  AwayFromZero  ToNegativeInf  ToPositiveInf
			 2.6              3              3       2             3              2              3
			 2.5              2              3       2             3              2              3
			 2.1              2              2       2             3              2              3
			-2.1             -2             -2      -2            -3             -3             -2
			-2.5             -2             -3      -2            -3             -3             -2
			-2.6             -3             -3      -2            -3             -3             -2

	# type Word uint
		* 代表一个多精度无符号整数的单个数字

---------------------------
方法
---------------------------
	func Jacobi(x, y *Int) int

---------------------------
demo
---------------------------
	# 两数相除，截断指定的小数位
		func TruncateToTwoDecimals(a, b string, precision uint) *big.Float {
			// 设置初始值
			f1, _, _ := big.ParseFloat(a, 10, 256, big.ToZero)
			f2, _, _ := big.ParseFloat(b, 10, 256, big.ToZero)

			// 10 进制下的精度位数
			precisionFloat := big.NewFloat(math.Pow(10, float64(precision)))

			// 相除
			result := new(big.Float).Quo(f1, f2)

			// 将结果 x 以指定的精度
			result.Mul(result, precisionFloat)

			// 向下取整
			intPart, _ := result.Int(nil)

			// 转换为 float 再除以精度
			truncated := new(big.Float).Quo(new(big.Float).SetInt(intPart), precisionFloat)

			return truncated
		}