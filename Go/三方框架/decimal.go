-------------
decimal
-------------
	# 不丢失精度的decimal类型
		*  只能表示小数点后最多 2^31 位的数字。

	# decimal
		https://github.com/shopspring/decimal
	
	# 大小写比较，会忽略多余的 0 
		v1 := decimal.RequireFromString("19.00")
		v2 := decimal.RequireFromString("19")

		fmt.Println(v1.Equal(v2))   // true
		fmt.Println(v1.Compare(v2)) // 0
	
	# JSON 序列化/反序列化为字符串

		type Foo struct {
			Amount  decimal.Decimal `json:"amount"`
			Balance decimal.Decimal `json:"balance"`
		}

		func main() {
			foo := Foo{Amount: decimal.NewFromFloat(28.999), Balance: decimal.NewFromInt(23213)}
			j, _ := json.Marshal(foo)
			fmt.Println(string(j))
			// {"amount":"28.999","balance":"23213"}

			if err := json.Unmarshal(j, &foo); err != nil {
				panic(err.Error())
			}
			fmt.Println(foo)
			// {28.999 23213}
		}
	
	# SQL 中对应数据库的 decimal 类型

		_, _ = db.Exec("INSERT INTO test(id, title, stock) VALUES(?, ?, ?)", 1000, "Hi", decimal.NewFromFloat(3.1415926666))

		var stock decimal.Decimal

		_ = db.QueryRow("SELECT stock FROM test WHERE id = ?", 1000).Scan(&stock)
		fmt.Println(stock)
				
-------------
var
-------------
	var DivisionPrecision = 16
		* DivisionPrecision（除法精度），指除法不精确时结果中的小数位数。

	var ExpMaxIterations = 1000
		* 指定使用 ExpHullAbrham 方法计算精确自然指数值所需的最大迭代次数。

	var MarshalJSONWithoutQuotes = false
		* 是否使用数值的方式序列化 decimal 类型
		* 建议为false，如果为true的话，可能会导致JS之类的丢失精度
	
	var PowPrecisionNegativeExponent = 16
		* 指定计算小数幂时结果的最大精度（小数点后的位数）。
		* 仅用于指数为负数的情况。该常数适用于 Pow、PowInt32 和 PowBigInt 方法，PowWithPrecision 方法不受其限制。

	var Zero = New(0, 1)
		* 零常量，以加快计算速度。
		* Zero 不应该直接与 == 或 != 比较，请使用 decimal.Equal 或 decimal.Cmp。

-------------
type
-------------

	# type Decimal struct {
			// contains filtered or unexported fields
		}
		func Avg(first Decimal, rest ...Decimal) Decimal
		func Max(first Decimal, rest ...Decimal) Decimal
		func Min(first Decimal, rest ...Decimal) Decimal
		func Sum(first Decimal, rest ...Decimal) Decimal
			* 平均/最大/最小/求和

		func New(value int64, exp int32) Decimal

		func NewFromBigInt(value *big.Int, exp int32) Decimal
			* 通过 *big.Int 构建， exp 指定小数点应该向左移动多少位。
			* 即最终表示的值为：value * (10 ^ exp)

				decimal.NewFromBigInt(big.NewInt(1024), 3)  //1024000
				decimal.NewFromBigInt(big.NewInt(1024), -3) //1.024
				decimal.NewFromBigInt(big.NewInt(1024), 0)  //1024


		func NewFromFloat(value float64) Decimal
		func NewFromFloat32(value float32) Decimal

		func NewFromFloatWithExponent(value float64, exp int32) Decimal
			* 根据 Float 创建， 保留 -exp 位小数（即 exp 是控制小数点的位置），自动进行四舍五入。

		func NewFromFormattedString(value string, replRegexp *regexp.Regexp) (Decimal, error)
		func NewFromInt(value int64) Decimal
		func NewFromInt32(value int32) Decimal
		func NewFromString(value string) (Decimal, error)

		func RequireFromString(value string) Decimal
			* 从 string 解析，如果解析失败则异常
		
		func (d Decimal) Abs() Decimal
			* 绝对值

		func (d Decimal) Add(d2 Decimal) Decimal
			* 相加

		func (d Decimal) Atan() Decimal

		func (d Decimal) BigFloat() *big.Float
		func (d Decimal) BigInt() *big.Int
			* 返回 math 包下的 big.int/float 形式	

		func (d Decimal) Ceil() Decimal
		func (d Decimal) Cmp(d2 Decimal) int
			* 比较大小，会忽略多余的 0 

		func (d Decimal) Coefficient() *big.Int
		func (d Decimal) CoefficientInt64() int64
		func (d Decimal) Copy() Decimal
		func (d Decimal) Cos() Decimal
		func (d Decimal) Div(d2 Decimal) Decimal
			* 相除

		func (d Decimal) DivRound(d2 Decimal, precision int32) Decimal
			* 除，四舍五入指定保留的小数位数

		func (d Decimal) Equal(d2 Decimal) bool
			* 比较是否相等，会忽略多余的 0
				decimal.RequireFromString("10.0000").Equal(decimal.NewFromUint64(10)) // true

		func (d Decimal) Equals(d2 Decimal) bool
			* 判断是否相等，废弃了，建议用上面个

		func (d Decimal) ExpHullAbrham(overallPrecision uint32) (Decimal, error)
		func (d Decimal) ExpTaylor(precision int32) (Decimal, error)
		func (d Decimal) Exponent() int32
		func (d Decimal) Float64() (f float64, exact bool)
		func (d Decimal) Floor() Decimal
		func (d *Decimal) GobDecode(data []byte) error
		func (d Decimal) GobEncode() ([]byte, error)

		func (d Decimal) GreaterThan(d2 Decimal) bool
		func (d Decimal) GreaterThanOrEqual(d2 Decimal) bool
			* 大于和大于等于

		func (d Decimal) InexactFloat64() float64
		func (d Decimal) IntPart() int64
			* 返回 int，舍去了小数

		func (d Decimal) IsInteger() bool
			* 是否是整数
				decimal.RequireFromString("19.482399").Truncate(0).IsInteger() // true

		func (d Decimal) IsNegative() bool
		func (d Decimal) IsPositive() bool
			* 是否是正/负数

		func (d Decimal) IsZero() bool
			* 是否是 0
				decimal.RequireFromString("0.0000").IsZero() // 0

		func (d Decimal) LessThan(d2 Decimal) bool
		func (d Decimal) LessThanOrEqual(d2 Decimal) bool
			* 小于和小于等于

		func (d Decimal) MarshalBinary() (data []byte, err error)
		func (d Decimal) MarshalJSON() ([]byte, error)
		func (d Decimal) MarshalText() (text []byte, err error)
		func (d Decimal) Mod(d2 Decimal) Decimal
			* 取模
			
		func (d Decimal) Mul(d2 Decimal) Decimal
			* 相乘

		func (d Decimal) Neg() Decimal
			* 取反

		func (d Decimal) NumDigits() int
		func (d Decimal) Pow(d2 Decimal) Decimal
			* 计算 d 的 d2 次方
				decimal.RequireFromString("2").Pow(decimal.NewFromUint64(10)) // 1024

		func (d Decimal) QuoRem(d2 Decimal, precision int32) (Decimal, Decimal)
		func (d Decimal) Rat() *big.Rat
			* 返回 rat

		func (d Decimal) Round(places int32) Decimal
			* 四舍五入保留 n 位小数
				decimal.RequireFromString("19.482399").Round(4)) // 19.4824

		func (d Decimal) RoundBank(places int32) Decimal
		func (d Decimal) RoundCash(interval uint8) Decimal
		func (d Decimal) RoundCeil(places int32) Decimal
		func (d Decimal) RoundDown(places int32) Decimal
	
		func (d Decimal) RoundFloor(places int32) Decimal
			* 保留 N 位小数，直接截断

		func (d Decimal) RoundUp(places int32) Decimal
			* 保留 N 位小数，进位 1

		func (d *Decimal) Scan(value interface{}) error
		func (d Decimal) Shift(shift int32) Decimal
		func (d Decimal) Sign() int
		func (d Decimal) Sin() Decimal
		func (d Decimal) String() string
			* 输出值，无多余的 0

		func (d Decimal) StringFixed(places int32) string
			* 保留 N(places)位小数，表示保留小数点后 n 位。
				正数 n > 0：保留 n 位小数
				零 n = 0：不保留小数，整数显示
				负数 n < 0：表示向左取整（即对整数位做四舍五入）

				ewFromFloat(0).StringFixed(2) // output: "0.00"
				NewFromFloat(0).StringFixed(0) // output: "0"
				NewFromFloat(5.45).StringFixed(0) // output: "5"
				NewFromFloat(5.45).StringFixed(1) // output: "5.5"
				NewFromFloat(5.45).StringFixed(2) // output: "5.45"
				NewFromFloat(5.45).StringFixed(3) // output: "5.450"
				NewFromFloat(545).StringFixed(-1) // output: "550"

		func (d Decimal) StringFixedBank(places int32) string
			* 返回一个格式化为固定小数位数的字符串，并使用“银行家舍入（Banker's Rounding）”进行四舍五入。
			* 银行家舍入（又称四舍六入五留双）的规则如下：
				小数 < 0.5 → 舍去
				小数 > 0.5 → 进位
				小数 = 0.5 → 向最近的偶数靠拢
	
				NewFromFloat(0).StringFixedBank(2) // output: "0.00"
				NewFromFloat(0).StringFixedBank(0) // output: "0"
				NewFromFloat(5.45).StringFixedBank(0) // output: "5"
				NewFromFloat(5.45).StringFixedBank(1) // output: "5.4"
				NewFromFloat(5.45).StringFixedBank(2) // output: "5.45"
				NewFromFloat(5.45).StringFixedBank(3) // output: "5.450"
				NewFromFloat(545).StringFixedBank(-1) // output: "540"

		func (d Decimal) StringFixedCash(interval uint8) string

		func (d Decimal) StringScaled(exp int32) string
			* 过时，使用 StringFixed

		func (d Decimal) Sub(d2 Decimal) Decimal
			* 减

		func (d Decimal) Tan() Decimal
		func (d Decimal) Truncate(precision int32) Decimal
			* 保留 n 位小数，直接丢弃多余的
				decimal.RequireFromString("19.482399").Truncate(4) // 19.4823

		func (d *Decimal) UnmarshalBinary(data []byte) error
		func (d *Decimal) UnmarshalJSON(decimalBytes []byte) error
		func (d *Decimal) UnmarshalText(text []byte) error
		func (d Decimal) Value() (driver.Value, error)

	# type NullDecimal struct {
			Decimal Decimal
			Valid   bool
		}
		
		* NullDecimal表示一个可空的 Decimal，具有从数据库 scanl null 的兼容性。

		func NewNullDecimal(d Decimal) NullDecimal
		func (d NullDecimal) MarshalJSON() ([]byte, error)
		func (d NullDecimal) MarshalText() (text []byte, err error)
		func (d *NullDecimal) Scan(value interface{}) error
		func (d *NullDecimal) UnmarshalJSON(decimalBytes []byte) error
		func (d *NullDecimal) UnmarshalText(text []byte) error
		func (d NullDecimal) Value() (driver.Value, error)
	
-------------
func
-------------
	func RescalePair(d1 Decimal, d2 Decimal) (Decimal, Decimal)


-------------
Demo
-------------
	# +-*/

		// 加
		decimal.NewFromInt(1).Add(decimal.NewFromInt(10))         //11
		// 减
		decimal.NewFromInt(10).Sub(decimal.NewFromInt(1))         //9
		// 乘
		decimal.NewFromInt(10).Mul(decimal.NewFromInt(10))        //100
		// 除
		decimal.NewFromInt(10).Div(decimal.NewFromInt(3))         // 3.3333333333333333
		// 除，只保留 n 位小数，四舍五入
		decimal.NewFromInt(10).DivRound(decimal.NewFromInt(3), 3) // 3.333
	

	
	# 格式化显示

		// 格式 d ，最多保留 n 位小数（截断）
		// 如果小数不足，则不显示
		func FormatDecimalNoPad(d decimal.Decimal, n int32) string {
			// 保留 N 位小数
			truncated := d.Truncate(n)
			// 无小数
			integer := truncated.Truncate(0)
			if truncated.Equal(integer) {
				return integer.String()
			}
			return truncated.String()
		}


		FormatDecimalNoPad(decimal.RequireFromString("3.1415926"), 3) // 3.141
		FormatDecimalNoPad(decimal.RequireFromString("3"), 2)         // 3
		FormatDecimalNoPad(decimal.RequireFromString("3.1"), 2)       // 3.1
	
	
	# 和 big.Float 无损转换
		
		// 原始 big.*Float 3.14159265358979323846264338327950288419716939937510582097494459
		f := big.NewFloat(math.Pi)

		// 直接用 String() 可能丢失精度：其实只保留了 10 位小数，底层源码：x.Text('g', 10)
		// 3.141592654
		fmt.Println(decimal.RequireFromString(f.String()))

		// BigFloat 无损转换为 decimal
		// 手动指定精度 f.Text('f', 100)：指定输出的 string 要精确到几位，decimal 会忽略后面多余的 0
		// 3.141592653589793115997963468544185161590576171875
		fmt.Println(decimal.RequireFromString(f.Text('f', 100)))

		// decimal 无损转换为 BigFloat
		fmt.Println(decimal.RequireFromString(f.Text('f', 100)).BigFloat().Text('f', 100))