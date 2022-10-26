-----------------
ģ�鳣��
-----------------
	const (
		MaxRune         = '\U0010FFFF' // Maximum valid Unicode code point.
		ReplacementChar = '\uFFFD'     // Represents invalid code points.
		MaxASCII        = '\u007F'     // maximum ASCII value.
		MaxLatin1       = '\u00FF'     // maximum Latin-1 value.
	)
	const (
		UpperCase = iota		// ��д
		LowerCase			// Сд
		TitleCase
		MaxCase
	)
	const (
		UpperLower = MaxRune + 1 // (Cannot be a valid delta.)
	)
	var (
		...

		Han                    = _Han  //���������Unicode�ַ�����
			
		...
	)


-----------------
�ṹ��
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
����
-----------------
	func Is(rangeTab *RangeTable, r rune) bool
		* �ж� r �Ƿ��� rangeTab �е�����
			 unicode.Is(unicode.Han, '��')  // �Ƿ��������ַ���

	func IsDigit(r rune) bool
	func IsGraphic(r rune) bool 
	func IsPrint(r rune) bool 
		* �ж��ַ�rune���Ƿ���Դ�ӡ

	func IsOneOf(ranges []*RangeTable, r rune) bool 
	func In(r rune, ranges ...*RangeTable) bool 
	func IsControl(r rune) bool 
	func IsLetter(r rune) bool 
	func IsMark(r rune) bool 
	func IsNumber(r rune) bool 
	func IsPunct(r rune) bool 
	func IsSpace(r rune) bool 
		* �Ƿ��ǿո�ֻҪ����Щ�ַ����񷵻� true
			'\t', '\n', '\v', '\f', '\r', ' ', U+0085 (NEL), U+00A0 (NBSP).

	func IsSymbol(r rune) bool
	
	func IsUpper(r rune) bool
	func IsLower(r rune) bool 
		* �ж��ַ��Ƿ��Ǵ�/Сд
	
	func IsTitle(r rune) bool
	func To(_case int, r rune) rune 

	func ToUpper(r rune) rune 
	func ToLower(r rune) rune 
		* ת��Ϊ��Сд
	
	func ToTitle(r rune) rune
	
	func SimpleFold(r rune) rune

