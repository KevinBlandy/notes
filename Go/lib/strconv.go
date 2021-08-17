----------------------
strconv
----------------------
	# string converter����Ҫ���ַ������ͺ������������͵�ת�����߰�

----------------------
����
----------------------
----------------------
�ṹ��
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
����
----------------------
	func ParseBool(str string) (bool, error)
		* ���Խ����ַ���Ϊboo����
		* ����ֵ�ᱻ����Ϊtrue
			"1", "t", "T", "true", "TRUE", "True"
		* ����ֵ�ᱻ����Ϊfalse
			"0", "f", "F", "false", "FALSE", "False"
		* ��������ǣ��򷵻� false, syntaxError("ParseBool", str)
	
	func FormatBool(b bool) string
		* ����bool������� true/false�ַ���
	
	func AppendBool(dst []byte, b bool) []byte 
		* ��true/false���ַ�����ʽת��Ϊ�ֽڣ���ӵ�dst�ֽ�������
		* ����
	
	func ParseComplex(s string, bitSize int) (complex128, error)
		* ���ַ�������Ϊ����
	
	func ParseFloat(s string, bitSize int) (float64, error) 
		* ���ַ�������ΪFloat
	
	func ParseUint(s string, base int, bitSize int) (uint64, error)
	func ParseInt(s string, base int, bitSize int) (i int64, err error) 
		* ��ָ�����ַ���ת��Ϊint���ݣ�����ָ�����ƺ�bit(0 - 64)λ

	func Atoi(s string) (int, error)
		* ���ַ�������Ϊһ��������Ĭ����10����
	
	func Itoa(i int) string 
		* �����֣�ת��Ϊʮ���Ƶ��ַ���
			return FormatInt(int64(i), 10)

	func FormatComplex(c complex128, fmt byte, prec, bitSize int) string 

	func FormatFloat(f float64, fmt byte, prec, bitSize int) string 
	func AppendFloat(dst []byte, f float64, fmt byte, prec, bitSize int) []byte

	func SetOptimize(b bool) bool
	func ParseFloatPrefix(s string, bitSize int) (float64, int, error)

	func FormatUint(i uint64, base int) string
	func FormatInt(i int64, base int) 
		* ���ݻ�������int����ת��Ϊ�ַ���



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
