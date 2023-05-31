-------------
decimal
-------------
	# 不丢失精度的decimal类型
	# decimal
		https://github.com/shopspring/decimal
	
		
-------------
var
-------------
	var DivisionPrecision = 16
	var ExpMaxIterations = 1000
	var MarshalJSONWithoutQuotes = false
		* 是否使用数值的方式序列化 decimal 类型
		* 建议为false，如果为true的话，可能会导致JS之类的丢失精度

	var Zero = New(0, 1)

-------------
type
-------------

	# type Decimal struct {
			// contains filtered or unexported fields
		}
		func Avg(first Decimal, rest ...Decimal) Decimal
		func Max(first Decimal, rest ...Decimal) Decimal
		func Min(first Decimal, rest ...Decimal) Decimal
		func New(value int64, exp int32) Decimal
		func NewFromBigInt(value *big.Int, exp int32) Decimal
		func NewFromFloat(value float64) Decimal
		func NewFromFloat32(value float32) Decimal
		func NewFromFloatWithExponent(value float64, exp int32) Decimal
		func NewFromFormattedString(value string, replRegexp *regexp.Regexp) (Decimal, error)
		func NewFromInt(value int64) Decimal
		func NewFromInt32(value int32) Decimal
		func NewFromString(value string) (Decimal, error)
		func RequireFromString(value string) Decimal
		func Sum(first Decimal, rest ...Decimal) Decimal

		func (d Decimal) Abs() Decimal
		func (d Decimal) Add(d2 Decimal) Decimal
		func (d Decimal) Atan() Decimal
		func (d Decimal) BigFloat() *big.Float
		func (d Decimal) BigInt() *big.Int
		func (d Decimal) Ceil() Decimal
		func (d Decimal) Cmp(d2 Decimal) int
		func (d Decimal) Coefficient() *big.Int
		func (d Decimal) CoefficientInt64() int64
		func (d Decimal) Copy() Decimal
		func (d Decimal) Cos() Decimal
		func (d Decimal) Div(d2 Decimal) Decimal
		func (d Decimal) DivRound(d2 Decimal, precision int32) Decimal
		func (d Decimal) Equal(d2 Decimal) bool
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
		func (d Decimal) InexactFloat64() float64
		func (d Decimal) IntPart() int64
		func (d Decimal) IsInteger() bool
		func (d Decimal) IsNegative() bool
		func (d Decimal) IsPositive() bool
		func (d Decimal) IsZero() bool
		func (d Decimal) LessThan(d2 Decimal) bool
		func (d Decimal) LessThanOrEqual(d2 Decimal) bool
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
