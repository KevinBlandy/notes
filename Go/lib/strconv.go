----------------------
strconv
----------------------
	# string converter，主要是字符串类型和其他数据类型的转换工具包

----------------------
变量
----------------------
----------------------
结构体
----------------------
	# decimal
		type decimal struct {
			d     [800]byte // digits, big-endian representation
			nd    int       // number of digits used
			dp    int       // decimal point
			neg   bool      // negative flag
			trunc bool      // discarded nonzero digits beyond d[:nd]
		}

		func NewDecimal(i uint64) *decimal

		func (a *decimal) String() string 
		func (a *decimal) Assign(v uint64)
		func (a *decimal) Shift(k int) 
		func (a *decimal) Round(nd int) 
		func (a *decimal) RoundDown(nd int) 
		func (a *decimal) RoundUp(nd int) 
		func (a *decimal) RoundedInteger() uint64 


----------------------
方法
----------------------
	func ParseBool(str string) (bool, error)
		* 尝试解析字符串为boo类型
		* 以下值会被解析为true
			"1", "t", "T", "true", "TRUE", "True"
		* 以下值会被解析为false
			"0", "f", "F", "false", "FALSE", "False"
		* 如果都不是，则返回 false, syntaxError("ParseBool", str)
	
	func FormatBool(b bool) string
		* 根据bool变量输出 true/false字符串
	
	func AppendBool(dst []byte, b bool) []byte 
		* 把true/false以字符串形式转换为字节，添加到dst字节数组中
		* 鸡肋
	
	func ParseComplex(s string, bitSize int) (complex128, error)
		* 把字符串解析为复数
	
	func ParseFloat(s string, bitSize int) (float64, error) 
		* 把字符串解析为Float
	
	func ParseUint(s string, base int, bitSize int) (uint64, error)
	func ParseInt(s string, base int, bitSize int) (i int64, err error) 
		* 把指定的字符串转换为int数据，可以指定进制和bit(0 - 64)位

	func Atoi(s string) (int, error)
		* 把字符串解析为一个整数，默认是10进制
	
	func Itoa(i int) string 
		* 把数字，转换为十进制的字符串
			return FormatInt(int64(i), 10)

	func FormatComplex(c complex128, fmt byte, prec, bitSize int) string 

	func FormatFloat(f float64, fmt byte, prec, bitSize int) string 
	func AppendFloat(dst []byte, f float64, fmt byte, prec, bitSize int) []byte

	func SetOptimize(b bool) bool
	func ParseFloatPrefix(s string, bitSize int) (float64, int, error)

	func FormatUint(i uint64, base int) string
	func FormatInt(i int64, base int) 
		* 根据基数，把int数据转换为字符串



	func AppendInt(dst []byte, i int64, base int) []byte 
	func AppendUint(dst []byte, i uint64, base int) []byte 

	func Quote(s string) string 
	func AppendQuote(dst []byte, s string) []byte
	func QuoteToASCII(s string) string 
	func AppendQuoteToASCII(dst []byte, s string) []byte 
	func QuoteToGraphic(s string) string 
	func AppendQuoteToGraphic(dst []byte, s string) []byte
	func QuoteRune(r rune) string
	func AppendQuoteRune(dst []byte, r rune) []byte
	func QuoteRuneToASCII(r rune) string
	func AppendQuoteRuneToASCII(dst []byte, r rune) []byte
	func QuoteRuneToGraphic(r rune) string
	func AppendQuoteRuneToGraphic(dst []byte, r rune) []byte
	func CanBackquote(s string) bool 
	func UnquoteChar(s string, quote byte) (value rune, multibyte bool, tail string, err error)
	func Unquote(s string) (string, error) 
	func IsPrint(r rune) bool
	func IsGraphic(r rune) bool 
	func QuotedPrefix(s string) (string, error)
