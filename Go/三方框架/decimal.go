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

		func NewFromFloat(value float64) Decimal
		func NewFromFloat32(value float32) Decimal

		func NewFromFloatWithExponent(value float64, exp int32) Decimal
			* 根据 Float 创建，并且设置精度为 exp

		func NewFromFormattedString(value string, replRegexp *regexp.Regexp) (Decimal, error)
		func NewFromInt(value int64) Decimal
		func NewFromInt32(value int32) Decimal
		func NewFromString(value string) (Decimal, error)

		func RequireFromString(value string) Decimal
			* 从 string 解析价格，如果解析失败则异常
		
		func (d Decimal) Abs() Decimal
			* 绝对值

		func (d Decimal) Add(d2 Decimal) Decimal
			* 相加

		func (d Decimal) Atan() Decimal
		func (d Decimal) BigFloat() *big.Float
		func (d Decimal) BigInt() *big.Int
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
			* 比较是否相等

		func (d Decimal) Equals(d2 Decimal) bool
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
		func (d Decimal) Mul(d2 Decimal) Decimal
		func (d Decimal) Neg() Decimal
		func (d Decimal) NumDigits() int
		func (d Decimal) Pow(d2 Decimal) Decimal
		func (d Decimal) QuoRem(d2 Decimal, precision int32) (Decimal, Decimal)
		func (d Decimal) Rat() *big.Rat
		func (d Decimal) Round(places int32) Decimal
			* 四舍五入保留 n 位小数
				decimal.RequireFromString("19.482399").Round(4)) // 19.4824

		func (d Decimal) RoundBank(places int32) Decimal
		func (d Decimal) RoundCash(interval uint8) Decimal
		func (d Decimal) RoundCeil(places int32) Decimal
		func (d Decimal) RoundDown(places int32) Decimal
		func (d Decimal) RoundFloor(places int32) Decimal
		func (d Decimal) RoundUp(places int32) Decimal

		func (d *Decimal) Scan(value interface{}) error
		func (d Decimal) Shift(shift int32) Decimal
		func (d Decimal) Sign() int
		func (d Decimal) Sin() Decimal
		func (d Decimal) String() string
		func (d Decimal) StringFixed(places int32) string
		func (d Decimal) StringFixedBank(places int32) string
		func (d Decimal) StringFixedCash(interval uint8) string
		func (d Decimal) StringScaled(exp int32) string
		func (d Decimal) Sub(d2 Decimal) Decimal
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
