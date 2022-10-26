-----------------
模块常量
-----------------
	const (
		MaxRune         = '\U0010FFFF' // Maximum valid Unicode code point.
		ReplacementChar = '\uFFFD'     // Represents invalid code points.
		MaxASCII        = '\u007F'     // maximum ASCII value.
		MaxLatin1       = '\u00FF'     // maximum Latin-1 value.
	)
	const (
		UpperCase = iota		// 大写
		LowerCase			// 小写
		TitleCase
		MaxCase
	)
	const (
		UpperLower = MaxRune + 1 // (Cannot be a valid delta.)
	)
	var (
		...

		Han                    = _Han  //汉字字体的Unicode字符集。
			
		...
	)


-----------------
结构体
-----------------
	
	# Range16
		type Range16 struct {
			Lo     uint16
			Hi     uint16
			Stride uint16
		}
	
	# Range32
		type Range32 struct {
			Lo     uint32
			Hi     uint32
			Stride uint32
		}

	# RangeTable
		type RangeTable struct {
			R16         []Range16
			R32         []Range32
			LatinOffset int // number of entries in R16 with Hi <= MaxLatin1
		}

	
	# CaseRange
		type CaseRange struct {
			Lo    uint32
			Hi    uint32
			Delta d
		}
	
	# SpecialCase
		type SpecialCase []CaseRange
		
		func (special SpecialCase) ToUpper(r rune) rune 
		func (special SpecialCase) ToTitle(r rune) rune
		func (special SpecialCase) ToTitle(r rune) rune 
		func (special SpecialCase) ToLower(r rune) rune 


-----------------
方法
-----------------
	func Is(rangeTab *RangeTable, r rune) bool
		* 判断 r 是否是 rangeTab 中的类型
			 unicode.Is(unicode.Han, '余')  // 是否中中文字符集

	func IsDigit(r rune) bool
	func IsGraphic(r rune) bool 
	func IsPrint(r rune) bool 
		* 判断字符rune，是否可以打印

	func IsOneOf(ranges []*RangeTable, r rune) bool 
	func In(r rune, ranges ...*RangeTable) bool 
	func IsControl(r rune) bool 
	func IsLetter(r rune) bool 
	func IsMark(r rune) bool 
	func IsNumber(r rune) bool 
	func IsPunct(r rune) bool 
	func IsSpace(r rune) bool 
		* 是否是空格，只要是这些字符，否返回 true
			'\t', '\n', '\v', '\f', '\r', ' ', U+0085 (NEL), U+00A0 (NBSP).

	func IsSymbol(r rune) bool
	
	func IsUpper(r rune) bool
	func IsLower(r rune) bool 
		* 判断字符是否是大/小写
	
	func IsTitle(r rune) bool
	func To(_case int, r rune) rune 

	func ToUpper(r rune) rune 
	func ToLower(r rune) rune 
		* 转换为大小写
	
	func ToTitle(r rune) rune
	
	func SimpleFold(r rune) rune

